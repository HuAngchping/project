//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.npower.dm.action.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.validator.DynaValidatorForm;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;

import com.npower.dm.action.ActionHelper;
import com.npower.dm.action.BaseAction;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.hibernate.entity.ProfileConfigEntity;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileTemplateBean;
import com.npower.dm.management.SearchBean;

/**
 * MyEclipse Struts Creation date: 06-13-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/ProfileConfigs" name="ProfileConfigSearchForm"
 *                scope="request"
 */
public class ProfilesAction extends BaseAction {

  // --------------------------------------------------------- Instance
  // Variables

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

  private ActionForward loadProfiles(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    DynaValidatorForm searchForm = (DynaValidatorForm) rawForm;

    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    CarrierBean carrierBean = factory.createCarrierBean();
    ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
    
    SearchBean searchBean = factory.createSearchBean();
    Criteria criteria = searchBean.newCriteriaInstance(ProfileConfigEntity.class);
    
    String searchCarrierID = searchForm.getString("searchCarrierID");
    String searchText = searchForm.getString("searchText");
    String searchCategoryID = searchForm.getString("searchCategoryID");
    String searchIsUserProfile = searchForm.getString("searchIsUserProfile");
    
    Boolean isUserProfile = null;
    if (StringUtils.isNotEmpty(searchIsUserProfile)) {
      isUserProfile = new Boolean(searchIsUserProfile);
    }
    
    if (StringUtils.isNotEmpty(searchCategoryID)) {
       ProfileCategory category = templateBean.getProfileCategoryByID("" + searchCategoryID);
       if (category != null) {
          Criteria subCriteria = criteria.createCriteria("profileTemplate");
          subCriteria.add(Expression.eq("profileCategory", category));
       }
    }

    if (StringUtils.isNotEmpty(searchCarrierID)) {
       Carrier carrier = carrierBean.getCarrierByID(searchCarrierID);
       criteria.add(Expression.eq("carrier", carrier));
    }
    
    if (isUserProfile != null) {
       criteria.add(Expression.eq("isUserProfile", isUserProfile));
    }
    
    if (StringUtils.isNotEmpty(searchText)) {
       criteria.add(Expression.ilike("name", searchText, MatchMode.ANYWHERE));
    }

    List<?> result = searchBean.find(criteria);
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
        // Load ProfileConfigs
        this.loadProfiles(mapping, rawForm, request, response);
        this.loadCategories(rawForm, request);
        
        // Set options
        request.setAttribute("carrierOptions", ActionHelper.getCarrierOptions(this.getManagementBeanFactory(request)));
        // Set options for Records per page options.
        BaseAction.setRecordsPerPageOptions(request, searchForm);

        return (mapping.findForward("success"));
    } catch (Exception ex) {
      throw ex;
    }
  }

}
