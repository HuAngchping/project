/**
  * $Header: /home/master/OTAS-DM-MyPortal/src/com/npower/dm/myportal/cp/SelectProfileAction.java,v 1.17 2008/04/28 10:43:00 zhao Exp $
  * $Revision: 1.17 $
  * $Date: 2008/04/28 10:43:00 $
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
import java.util.Collection;
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
import org.hibernate.criterion.Order;

import com.npower.dm.action.PersistentManager;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileAttribute;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ProfileTemplate;
import com.npower.dm.decorator.DecoratorHelper;
import com.npower.dm.hibernate.entity.ProfileConfigEntity;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SearchBean;
import com.npower.dm.myportal.BaseWizardAction;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.17 $ $Date: 2008/04/28 10:43:00 $
 */
public class SelectProfileAction extends BaseWizardAction {
  
  /**
   * 检查是否要求用户输入数据
   * @param profile
   * @return
   */
  private boolean isRequiredUserInput(ProfileConfig profile) {
    boolean userInputRequired = false;
    ProfileTemplate template = profile.getProfileTemplate();
    for (ProfileAttribute attribute: template.getProfileAttributes()) {
        if (attribute.getIsUserAttribute()) {
           userInputRequired =  true;
           break;
        }
    }
    return userInputRequired;
  }
  
  /* (non-Javadoc)
   * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  public ActionForward view(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
    ManagementBeanFactory factory = PersistentManager.getManagementBeanFactory(request);
  
    ClientProvWizardForm form = (ClientProvWizardForm)rawForm;
    String carrierID = form.getString("carrierID");
    
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

    // Get ProfileCategory
    ProfileCategory category = this.getProfileCategory(factory, form, request);
    if (category == null) {
       return mapping.getInputForward();
    }
    request.setAttribute("category",  DecoratorHelper.decorate(category, this.getLocale(request)));
    

    SearchBean searchBean = factory.createSearchBean();
    Criteria criteria = searchBean.newCriteriaInstance(ProfileConfigEntity.class);
    criteria.add(Expression.eq("isUserProfile", new Boolean(true)));
    criteria.addOrder(Order.asc("name"));
    Criteria subCriteria = criteria.createCriteria("profileTemplate");
    subCriteria.add(Expression.eq("profileCategory", category));

    if (StringUtils.isNotEmpty(carrierID)) {
       criteria.add(Expression.or(Expression.eq("carrier", carrier), Expression.isNull("carrier")));
    }

    List<ProfileConfig> profiles = (List<ProfileConfig>)searchBean.find(criteria);
    Collection<LabelValueBean> result = new ArrayList<LabelValueBean>();
    for (ProfileConfig profile: profiles) {
        LabelValueBean labelValue = new LabelValueBean(profile.getName(), "" + profile.getExternalID());
        result.add(labelValue);
    }
    request.setAttribute("profileOptions", result);
    
    // Get Profile
    ProfileConfig profile = getProfile(factory, form, request);
    if (profile != null) {
       request.setAttribute("profile", profile);
    }

    return (mapping.findForward("view"));
  }

  public ActionForward next(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
    ClientProvWizardForm form = (ClientProvWizardForm)rawForm;
    String profileID = form.getString("profileID");
    if (StringUtils.isEmpty(profileID)) {
       saveError4MissProfile(request);
       return this.unspecified(mapping, rawForm, request, res);
    }
    // Clean checkbox
    if (StringUtils.isEmpty(request.getParameter("value(advanceProfileParameterMode)"))) {
       form.setValue("advanceProfileParameterMode", null);
    }
    
    // Get Profile
    ManagementBeanFactory factory = PersistentManager.getManagementBeanFactory(request);
    ProfileConfig profile = getProfile(factory, form, request);
    // 检查是否要求用户输入数据
    boolean userInputRequired = isRequiredUserInput(profile);
    if (userInputRequired) {
      return (mapping.findForward("next4InputProfileParameters"));
    } else {
      return (mapping.findForward("next4InputPhone"));
    }
  }

  public ActionForward prev(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
    ClientProvWizardForm form = (ClientProvWizardForm)rawForm;
    // Clean checkbox
    if (StringUtils.isEmpty(request.getParameter("value(advanceProfileParameterMode)"))) {
       form.setValue("advanceProfileParameterMode", null);
    }
    return (mapping.findForward("prev"));
  }
  
}
