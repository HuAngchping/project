/**
  * $Header: /home/master/OTAS-DM-MyPortal/src/com/npower/dm/myportal/cp/InputPhoneAction.java,v 1.12 2008/11/09 14:08:04 zhao Exp $
  * $Revision: 1.12 $
  * $Date: 2008/11/09 14:08:04 $
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.npower.dm.action.PersistentManager;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.decorator.DecoratorHelper;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.myportal.BaseWizardAction;
import com.npower.dm.myportal.CheckCodeAction;
import com.npower.dm.myportal.UserServiceData;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.12 $ $Date: 2008/11/09 14:08:04 $
 */
public class InputPhoneAction extends BaseWizardAction {
  
  
  /* (non-Javadoc)
   * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  public ActionForward view(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
    ClientProvWizardForm form = (ClientProvWizardForm)rawForm;

    ManagementBeanFactory factory = PersistentManager.getManagementBeanFactory(request);
    
    // Clean checkbox
    if (StringUtils.isEmpty(request.getParameter("value(cpMode)"))) {
       form.setValue("cpMode", null);
    }

    UserServiceData serviceData = BaseWizardAction.getUserServiceData(request);
    if (serviceData != null) {
       String phoneNumber = serviceData.getMsisdn();
       if (StringUtils.isNotEmpty(phoneNumber)) {
          form.setValue("phoneNumber", phoneNumber);
       }
    }
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

    // Get ProfileCategory
    ProfileCategory category = this.getProfileCategory(factory, form, request);
    if (category == null) {
       return mapping.getInputForward();
    }
    request.setAttribute("category",  DecoratorHelper.decorate(category, this.getLocale(request)));
    
    // Get Profile
    ProfileConfig profile = getProfile(factory, form, request);
    if (profile == null) {
       return mapping.getInputForward();
    }
    request.setAttribute("profile", profile);
    
    // Get Model
    Model model = getModel(factory, form, request);
    if (model != null) {
       request.setAttribute("model", model);
       request.setAttribute("manufacturer", DecoratorHelper.decorate(model.getManufacturer(), this.getLocale(request)));
    } else {
      // 缺少型号
      // 返回型号选择页面的厂商列表
      request.setAttribute("manufacturerOptions", getManufacturerOptions(factory, request));
      // 返回型号选择页面的主流厂商列表
      request.setAttribute("specialManufacturerOptions", getSpecialManufacturerOptions(factory, request));
      // 返回型号选择页面的型号列表
      request.setAttribute("modelOptions", getModelOptions(factory, form));
    }

    return (mapping.findForward("view"));
  }

  public ActionForward next(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
    ClientProvWizardForm form = (ClientProvWizardForm)rawForm;

    String manufacturerID = form.getString("manufacturerID");
    if (StringUtils.isEmpty(manufacturerID)) {
       saveError4MissManufacturer(request);
       return this.unspecified(mapping, rawForm, request, res);
    }
    String modelID = form.getString("modelID");
    if (StringUtils.isEmpty(modelID)) {
       saveError4MissModel(request);
       return this.unspecified(mapping, rawForm, request, res);
    }
    
    String phoneNumber = form.getString("phoneNumber");
    if (StringUtils.isEmpty(phoneNumber)) {
       saveError4MissingPhoneNumber(request);
       return this.unspecified(mapping, rawForm, request, res);
    }
    String checkCode = request.getParameter("checkcode");
    HttpSession session = request.getSession(false);
    if (StringUtils.isEmpty(checkCode) 
        || !checkCode.equals((String)session.getAttribute(CheckCodeAction.CHECKCODE_ATTRIBUTE_NAME))) {
       saveError4MissCheckCode(request);
       return this.unspecified(mapping, rawForm, request, res);
    }
    return (mapping.findForward("next"));
  }

  public ActionForward prev(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
    return (mapping.findForward("prev"));
  }
  
}
