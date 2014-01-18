/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/profile/TemplatesAction.java,v 1.6 2007/03/28 08:22:08 zhao Exp $
  * $Revision: 1.6 $
  * $Date: 2007/03/28 08:22:08 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2006 NPower Network Software Ltd.  All rights reserved.
  *
  * This SOURCE CODE FILE, which has been provided by NPower as part
  * of a NPower product for use ONLY by licensed users of the product,
  * includes CONFIDENTIAL and PROPRIETARY information of NPower.
  *
  * USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS
  * OF THE LICENSE STATEMENT AND LIMITED WARRANTY FURNISHED WITH
  * THE PRODUCT.
  *
  * IN PARTICULAR, YOU WILL INDEMNIFY AND HOLD NPower, ITS RELATED
  * COMPANIES AND ITS SUPPLIERS, HARMLESS FROM AND AGAINST ANY CLAIMS
  * OR LIABILITIES ARISING OUT OF THE USE, REPRODUCTION, OR DISTRIBUTION
  * OF YOUR PROGRAMS, INCLUDING ANY CLAIMS OR LIABILITIES ARISING OUT OF
  * OR RESULTING FROM THE USE, MODIFICATION, OR DISTRIBUTION OF PROGRAMS
  * OR FILES CREATED FROM, BASED ON, AND/OR DERIVED FROM THIS SOURCE
  * CODE FILE.
  * ===============================================================================================
  */
package com.npower.dm.action.profile;

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

import com.npower.dm.action.BaseAction;
import com.npower.dm.core.DMException;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.hibernate.entity.ProfileTemplateEntity;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileTemplateBean;
import com.npower.dm.management.SearchBean;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.6 $ $Date: 2007/03/28 08:22:08 $
 */
public class TemplatesAction extends BaseAction {

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

  private List search(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws DMException {
    DynaValidatorForm searchForm = (DynaValidatorForm) form;
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ProfileTemplateBean templateBean = factory.createProfileTemplateBean();

    SearchBean searchBean = factory.createSearchBean();
    Criteria criteria = searchBean.newCriteriaInstance(ProfileTemplateEntity.class);

    String searchCategoryID = searchForm.getString("searchCategoryID");
    String searchText = searchForm.getString("searchText");
    String searchNeedNap = searchForm.getString("searchNeedNap");
    String searchNeedProxy = searchForm.getString("searchNeedProxy");
    
    if (StringUtils.isNotEmpty(searchCategoryID)) {
       ProfileCategory category = templateBean.getProfileCategoryByID(searchCategoryID);
       if (category != null) {
          criteria.add(Expression.eq("profileCategory", category));
       }
    }

    if (StringUtils.isNotEmpty(searchText)) {
       criteria.add(Expression.ilike("name", searchText, MatchMode.ANYWHERE));
    }
    
    if (StringUtils.isNotEmpty(searchNeedNap)) {
       criteria.add(Expression.eq("needsNap", new Boolean(searchNeedNap)));
    }
  
    if (StringUtils.isNotEmpty(searchNeedProxy)) {
       criteria.add(Expression.eq("needsProxy", new Boolean(searchNeedProxy)));
    }
 
    List result = searchBean.find(criteria);
    return result;
  }

  /**
   * Retrieve all of profile templates and put these into request.
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
        List list = this.search(mapping, rawForm, request, response);
        
        request.setAttribute("templates", list);
        this.loadCategories(rawForm, request);
    
        // Set options for Records per page options.
        BaseAction.setRecordsPerPageOptions(request, searchForm);

        return (mapping.findForward("templates"));
    } catch (Exception ex) {
      throw ex;
    }
  }
  
}
