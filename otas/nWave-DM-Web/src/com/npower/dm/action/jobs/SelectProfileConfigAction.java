//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.npower.dm.action.jobs;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

import com.npower.dm.action.BaseAction;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileTemplate;
import com.npower.dm.hibernate.entity.ProfileConfigEntity;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileTemplateBean;
import com.npower.dm.management.SearchBean;

/**
 */
public class SelectProfileConfigAction extends BaseAction {

  // --------------------------------------------------------- Instance
  // Variables

  private ActionForward loadProfiles(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    ProfileAssignmentForm4Save form = (ProfileAssignmentForm4Save) rawForm;
    String searchProfileConfigName = (String)form.getValue("searchProfileConfigName");
    String searchProfileTemplateID = (String)form.getValue("searchProfileTemplateID");
    String searchProfileCategoryID = (String)form.getValue("searchProfileCategoryID");
    String searchProfileCarrierID = (String)form.getValue("searchProfileCarrierID");
    
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
    CarrierBean carrierBean = factory.createCarrierBean();
    
    //DeviceBean deviceBean = factory.createDeviceBean();
    //Device device = deviceBean.getDeviceByID((String) form.get("deviceID"));
    //Model model = device.getModel();
    
    SearchBean searchBean = factory.createSearchBean();
    Criteria criteria = searchBean.newCriteriaInstance(ProfileConfigEntity.class);
    criteria.add(Expression.eq("isUserProfile", new Boolean(true)));
    Criteria templateCriteria = criteria.createCriteria("profileTemplate");
    //Criteria profileMappingCriteria = templateCriteria.createCriteria("profileMappings");
    //Criteria modelCriteria = profileMappingCriteria.createCriteria("modelProfileMaps");
    //modelCriteria.add(Expression.eq("model", model));
    
    if (StringUtils.isNotEmpty(searchProfileCategoryID)) {
       ProfileCategory category = templateBean.getProfileCategoryByID(searchProfileCategoryID);
       if (category != null) {
          templateCriteria.add(Expression.eq("profileCategory", category));
       }
    }
   
    if (StringUtils.isNotEmpty(searchProfileTemplateID)) {
       ProfileTemplate template = templateBean.getTemplateByID(searchProfileTemplateID);
       if (template != null) {
          criteria.add(Expression.eq("profileTemplate", template));
       }
    }
  
    if (StringUtils.isNotEmpty(searchProfileConfigName)) {
       criteria.add(Expression.ilike("name", searchProfileConfigName, MatchMode.ANYWHERE));
    }
   
    if (StringUtils.isNotEmpty(searchProfileCarrierID)) {
      Carrier carrier = carrierBean.getCarrierByID(searchProfileCarrierID);
      criteria.add(Expression.eq("carrier", carrier));
    }

    criteria.addOrder(Order.asc("carrier"));
    templateCriteria.addOrder(Order.asc("profileCategory"));
    criteria.addOrder(Order.asc("name"));
    
    // Set Target Devices into request attribute "targetDevices" for JSP
    BaseJobsAction.setTargetDevices(factory, request);
    
    List result = criteria.list();
    request.setAttribute("results", result);
    return null;
  }

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
  
  private void loadCarriers(ActionForm rawForm, HttpServletRequest request) throws Exception {
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    CarrierBean carrierBean = factory.createCarrierBean();
    List<Carrier> carriers = carrierBean.findCarriers("from CarrierEntity");
    List<LabelValueBean> result = new ArrayList<LabelValueBean>();
    for (Carrier carrier: carriers) {
        LabelValueBean labelValue = new LabelValueBean(carrier.getName(), "" + carrier.getID());
        result.add(labelValue);
    }
    request.setAttribute("carrierOptions", result);

  }  
  
  
  // --------------------------------------------------------- Methods

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
    
    ProfileAssignmentForm4Save form = (ProfileAssignmentForm4Save) rawForm;

    // Load profile list
    this.loadProfiles(mapping, rawForm, request, response);
    
    this.loadCategories(rawForm, request);
    this.loadProfileTemplates(rawForm, request);
    this.loadCarriers(rawForm, request);
    
    // Set options for Records per page options.
    BaseAction.setRecordsPerPageOptions(request, form);    

    return (mapping.findForward("display"));
  }

}
