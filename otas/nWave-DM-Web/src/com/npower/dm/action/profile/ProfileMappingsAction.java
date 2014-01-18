//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.npower.dm.action.profile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.validator.DynaValidatorForm;
import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;

import com.npower.dm.action.ActionHelper;
import com.npower.dm.action.BaseAction;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileTemplate;
import com.npower.dm.hibernate.entity.ModelProfileMap;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.ProfileTemplateBean;
import com.npower.dm.management.SearchBean;
import com.npower.dm.security.DMWebPrincipal;
import com.npower.dm.security.SecurityService;

/**
 * MyEclipse Struts Creation date: 06-12-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/ProfileMappings" name="ProfileMappingForm"
 *                scope="request"
 * @struts.action-forward name="display" path="/jsp/profile/profilemappings.jsp"
 *                        contextRelative="true"
 */
public class ProfileMappingsAction extends BaseAction {

  // --------------------------------------------------------- Instance
  // Variables
  
  public class ProfileTemplateModelMapping {
    ProfileTemplate template;
    Model model;
    /**
     * @return Returns the template.
     */
    public ProfileTemplate getTemplate() {
      return template;
    }
    /**
     * @param template The template to set.
     */
    public void setTemplate(ProfileTemplate template) {
      this.template = template;
    }
    /**
     * @return Returns the model.
     */
    public Model getModel() {
      return model;
    }
    /**
     * @param model The model to set.
     */
    public void setModel(Model model) {
      this.model = model;
    }
  }

  // --------------------------------------------------------- Methods
  
  private void loadCategories(ActionForm rawForm, HttpServletRequest request) throws Exception {
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ProfileTemplateBean bean = factory.createProfileTemplateBean();
    List<ProfileCategory> categories = bean.findProfileCategories("from ProfileCategoryEntity");
    List<LabelValueBean> result = new ArrayList<LabelValueBean>();
    for (ProfileCategory category: categories) {
        LabelValueBean labelValue = new LabelValueBean(category.getName(), "" + category.getID());
        result.add(labelValue);
    }
    request.setAttribute("categoryOptions", result);
  }
  
  private void loadProfileTemplates(ActionForm rawForm, HttpServletRequest request) throws Exception {
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ProfileTemplateBean bean = factory.createProfileTemplateBean();
    List<ProfileTemplate> templates = bean.findTemplates("from ProfileTemplateEntity");
    List<LabelValueBean> result = new ArrayList<LabelValueBean>();
    for (ProfileTemplate template: templates) {
        LabelValueBean labelValue = new LabelValueBean(template.getName(), "" + template.getID());
        result.add(labelValue);
    }
    request.setAttribute("templateOptions", result);
  }
  
  

  private Collection<LabelValueBean> getModelOptions(HttpServletRequest request, DynaValidatorForm form) throws DMException {
    Collection<LabelValueBean> result = new ArrayList<LabelValueBean>();
    
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ModelBean modelBean = factory.createModelBean();
    String manufacturerID = (String)form.get("searchManufacturerID");
    Manufacturer manufacturer = null;
    if (manufacturerID != null && manufacturerID.trim().length() > 0) {
       manufacturer = modelBean.getManufacturerByID(manufacturerID);
    }
    
    if (manufacturer == null) {
       return result;
    }
    Set<Model> set = new TreeSet<Model>(manufacturer.getModels());
    for (Model model: set) {
        LabelValueBean labelValue = new LabelValueBean(model.getManufacturerModelId(), "" + model.getID());
        result.add(labelValue);
    }
    return result;
  }
  

  private ActionForward loadProfileMappings(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    String searchManufacturerID = request.getParameter("searchManufacturerID");
    String searchModelID = request.getParameter("searchModelID");
    String searchTemplateID = request.getParameter("searchTemplateID");
    String searchCategoryID = request.getParameter("searchCategoryID");
    String searchText = request.getParameter("searchText");
    
    List result = new ArrayList();
    
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ModelBean modelBean = factory.createModelBean();
    ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
    
    SearchBean searchBean = factory.createSearchBean();
    Criteria criteria = searchBean.newCriteriaInstance(ModelProfileMap.class);
    
    Criteria modelCriteria = criteria.createCriteria("model");
    // Create OR criteria to retrieve manufacturers
    Disjunction manufacturersCriteria = Expression.disjunction();
    modelCriteria.add(manufacturersCriteria);

    // Add Search criteria by role
    DMWebPrincipal principal = ActionHelper.getDMWebPrincipal(request);
    if (principal.hasRole(SecurityService.MANUFACTURER_OPERATOR_ROLE)) {
       List<String> ownedManufExternaIDs = principal.getOwnedManufacturerExternalIDs();
       for (String manufExternalID: ownedManufExternaIDs) {
           Manufacturer manufactuer = modelBean.getManufacturerByExternalID(manufExternalID);
           if (manufactuer != null) {
              manufacturersCriteria.add(Expression.eq("manufacturer", manufactuer));
           }
       }
    }
    
    // Add Search criteria by SearchPannel
    if (StringUtils.isNotEmpty(searchManufacturerID)) {
       Manufacturer manufacturer = modelBean.getManufacturerByID(searchManufacturerID);
       if (manufacturer != null) {
          manufacturersCriteria.add(Expression.eq("manufacturer", manufacturer));
       }
    }
    
    if (StringUtils.isNotEmpty(searchModelID)) {
       Model model = modelBean.getModelByID(searchModelID);
       if (model != null) {
          criteria.add(Expression.eq("model", model));
       }
    }
    Criteria profileMappingCriteria = criteria.createCriteria("profileMapping");
    Criteria templateCriteria = profileMappingCriteria.createCriteria("profileTemplate");
    if (StringUtils.isNotEmpty(searchTemplateID)) {
       ProfileTemplate template = templateBean.getTemplateByID(searchTemplateID);
       if (template != null) {
         profileMappingCriteria.add(Expression.eq("profileTemplate", template));
       }
    }
  
    if (StringUtils.isNotEmpty(searchCategoryID)) {
       ProfileCategory category = templateBean.getProfileCategoryByID(searchCategoryID);
       if (category != null) {
         templateCriteria.add(Expression.eq("profileCategory", category));
       }
    }

    if (StringUtils.isNotEmpty(searchText)) {
       templateCriteria.add(Expression.ilike("name", searchText.trim(), MatchMode.ANYWHERE));
    }
    
    List<ModelProfileMap> modelMappings = searchBean.find(criteria);
    for (ModelProfileMap modelMapping: modelMappings) {
        Model model = modelMapping.getModel();
        ProfileTemplate template = modelMapping.getProfileMapping().getProfileTemplate();
        ProfileTemplateModelMapping item = new ProfileTemplateModelMapping();
        item.setModel(model);
        item.setTemplate(template);
        result.add(item);
    }
    
    request.setAttribute("results", result);
    return null;
  }

  /**
   * Method execute
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward execute(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    DynaValidatorForm searchForm = (DynaValidatorForm) rawForm;
    
    try {
        // Load mappings
        this.loadProfileMappings(mapping, rawForm, request, response);    
        // Set options
        this.loadCategories(rawForm, request);
        this.loadProfileTemplates(rawForm, request);
        
        request.setAttribute("manufacturerOptions", ActionHelper.getManufacturerOptions(this.getManagementBeanFactory(request), request));
        request.setAttribute("modelOptions", this.getModelOptions(request, searchForm));
        // Set options for Records per page options.
        BaseAction.setRecordsPerPageOptions(request, searchForm);
    
        return (mapping.findForward("display"));
    } catch (Exception ex) {
      throw ex;
    }
  }
}
