/**
  * $Header: /home/master/OTAS-DM-MyPortal/src/com/npower/dm/myportal/cp/SelectProfileCategoryAction.java,v 1.14 2008/04/15 10:18:21 zhao Exp $
  * $Revision: 1.14 $
  * $Date: 2008/04/15 10:18:21 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2007 NPower Network Software Ltd.  All rights reserved.
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
package com.npower.dm.myportal.cp;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.npower.dm.action.PersistentManager;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.ClientProvTemplate;
import com.npower.dm.core.Country;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.decorator.DecoratorHelper;
import com.npower.dm.hibernate.entity.ProfileConfigEntity;
import com.npower.dm.management.ClientProvTemplateBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileTemplateBean;
import com.npower.dm.management.SearchBean;
import com.npower.dm.msm.SoftwareManagementJobAdapter;
import com.npower.dm.msm.SoftwareManagementJobAdapterImpl;
import com.npower.dm.myportal.BaseWizardAction;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.14 $ $Date: 2008/04/15 10:18:21 $
 */
public class SelectProfileCategoryAction extends BaseWizardAction {
  
  /**
   * 将Profile Category列表转成包含有效的profile的列表
   * @param factory
   * @param list
   * @return
   * @throws DMException
   */
  private List<ProfileCategory> filter(ClientProvWizardForm form, HttpServletRequest request, ManagementBeanFactory factory , List<ProfileCategory> list, Model model) throws DMException {
    List<ProfileCategory> result = this.filterByProfileDefinitions(factory, list);
    String showAllOfCategory = request.getParameter("value(showAllOfCategory)");
    if (StringUtils.isEmpty(showAllOfCategory)) {
       form.setValue("showAllOfCategory", null);
       result = this.filterByModel(factory, result, model);
    }
    return result;
  }

  /**
   * 将Profile Category列表转成包含有效的profile的列表
   * 此方法用于解决:bug284问题1
   * @param factory
   * @param list
   * @return
   * @throws DMException
   */
  private List<ProfileCategory> filterByProfileDefinitions(ManagementBeanFactory factory , List<ProfileCategory> list ) throws DMException {
    List<ProfileCategory> resultlist = new ArrayList<ProfileCategory>();

    // 检查相应的category是否包含有效的Profile定义, 没有定义Profile的category，不出现在列表中
    SearchBean searchBean = factory.createSearchBean();
    for(ProfileCategory category : list){
      Criteria criteria = searchBean.newCriteriaInstance(ProfileConfigEntity.class);
      criteria.addOrder(Order.asc("name"));
      Criteria subCriteria = criteria.createCriteria("profileTemplate");
      subCriteria.add(Expression.eq("profileCategory", category));

      List<ProfileConfig> profiles = searchBean.find(criteria);
      if(profiles.size() > 0){
        resultlist.add(category);
      }      
    }
    return resultlist;
  }

  /**
   * 将Profile Category列表转成包含有效的profile的列表
   * 此方法用于解决:bug284问题1
   * @param factory
   * @param list
   * @return
   * @throws DMException
   */
  private List<ProfileCategory> filterByModel(ManagementBeanFactory factory , List<ProfileCategory> list, Model model) throws DMException {
    List<ProfileCategory> resultlist = new ArrayList<ProfileCategory>();

    // 检查相应的Model是否包含有效的CP Template定义, 没有定义Profile的category，不出现在列表中
    ClientProvTemplateBean bean = factory.createClientProvTemplateBean();
    
    for (ProfileCategory category : list){
        ClientProvTemplate template = bean.findTemplate(model, category);
        if(template != null){
          resultlist.add(category);
        }      
    }
    return resultlist;
  }


  /* (non-Javadoc)
   * @see com.npower.dm.myportal.BaseWizardAction#view(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  public ActionForward view(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
    ClientProvWizardForm form = (ClientProvWizardForm)rawForm;
    
    ManagementBeanFactory factory = PersistentManager.getManagementBeanFactory(request);
    
    // Get Country
    Country country = getCountry(factory, form, request);
    if (country == null) {
       // Missing country
       return mapping.getInputForward();
    }
    request.setAttribute("country", DecoratorHelper.decorate(country, this.getLocale(request)));

    // Get Carrier
    Carrier carrier = this.getCarrier(factory, form, request);
    if (carrier == null) {
       return mapping.getInputForward();
    }
    request.setAttribute("carrier", carrier);
    
    // Get Model
    Model model = getModel(factory, form, request);
    if (model == null) {
       return mapping.getInputForward();
    }
    request.setAttribute("model", model);
    request.setAttribute("manufacturer", DecoratorHelper.decorate(model.getManufacturer(), this.getLocale(request)));
    
    // Get categories
    ProfileTemplateBean bean = factory.createProfileTemplateBean();
    List<ProfileCategory> list = bean.findProfileCategories("from ProfileCategoryEntity");
   
    list = this.filter(form, request, factory, list, model);
    list = DecoratorHelper.decorateProfileCategory(list, this.getLocale(request));
    
    SoftwareManagementJobAdapter msmAdapter = new SoftwareManagementJobAdapterImpl(factory);
    if (msmAdapter.getProcessor(ProvisionJob.JOB_TYPE_SOFTWARE_INSTALL, model) != null) {
       // Model support software management, Add a MSM category
       ProfileCategory msmCategory = bean.getProfileCategoryByName(ProfileCategory.MSM_CATEGORY_NAME);
       list.add(DecoratorHelper.decorate(msmCategory, this.getLocale(request)));
    }
    
    // Set into request
    request.setAttribute("categories", list);
    
    return (mapping.findForward("view"));
  }

  /* (non-Javadoc)
   * @see com.npower.dm.myportal.BaseWizardAction#next(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  public ActionForward next(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
    ClientProvWizardForm form = (ClientProvWizardForm)rawForm;
    String profileCategoryID = form.getString("profileCategoryID");
    if (StringUtils.isEmpty(profileCategoryID)) {
       saveError4Category(request);
       return this.unspecified(mapping, rawForm, request, res);
    }
    form.setValue("profileID", "");
    
    // Get ProfileCategory
    ManagementBeanFactory factory = PersistentManager.getManagementBeanFactory(request);
    ProfileCategory category = this.getProfileCategory(factory, form, request);
    if (ProfileCategory.MSM_CATEGORY_NAME.equalsIgnoreCase(category.getName())) {
       // Redirect to page for select software
       return (mapping.findForward("next4SelectSoftware"));
    }
    // Redirect to page for select CP profile.
    return (mapping.findForward("next"));
  }


  /* (non-Javadoc)
   * @see com.npower.dm.myportal.BaseWizardAction#prev(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  public ActionForward prev(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
    return (mapping.findForward("prev"));
  }
  
}
