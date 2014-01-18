/**
  * $Header: /home/master/OTAS-DM-MyPortal/src/com/npower/dm/myportal/dm/InputPhoneAction.java,v 1.11 2008/04/18 11:46:27 zhao Exp $
  * $Revision: 1.11 $
  * $Date: 2008/04/18 11:46:27 $
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
package com.npower.dm.myportal.dm;

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
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwarePackage;
import com.npower.dm.decorator.DecoratorHelper;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileTemplateBean;
import com.npower.dm.management.SoftwareBean;
import com.npower.dm.msm.SoftwareManagementJobAdapter;
import com.npower.dm.msm.SoftwareManagementJobAdapterImpl;
import com.npower.dm.myportal.BaseWizardAction;
import com.npower.dm.myportal.CheckCodeAction;
import com.npower.dm.myportal.cp.ClientProvWizardForm;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.11 $ $Date: 2008/04/18 11:46:27 $
 */
public class InputPhoneAction extends BaseWizardAction {
  
  
  /* (non-Javadoc)
   * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
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
    // 检查用户选择的型号是否支持软件管理
    boolean supported_model = false;
    if (model != null) {
       SoftwareManagementJobAdapter msmAdapter = new SoftwareManagementJobAdapterImpl(factory);
       supported_model = msmAdapter.getProcessor(ProvisionJob.JOB_TYPE_SOFTWARE_INSTALL, model) != null;
    }
   
    if (model != null && supported_model) {
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
    
    ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
    ProfileCategory category = templateBean.getProfileCategoryByName(ProfileCategory.MSM_CATEGORY_NAME);
    request.setAttribute("category", DecoratorHelper.decorate(category, this.getLocale(request)));
    
    // Get Software
    Software software = this.getSoftware(factory, form, request);
    if (software == null) {
       // 返回选择软件页面
       return this.prev(mapping, rawForm, request, res);
    }
    request.setAttribute("software", software);
    
    return (mapping.findForward("view"));
  }

  /* (non-Javadoc)
   * @see com.npower.dm.myportal.cp.BaseWizardAction#next(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  public ActionForward next(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
    ClientProvWizardForm form = (ClientProvWizardForm)rawForm;
    String manufacturerID = form.getString("manufacturerID");
    if (StringUtils.isEmpty(manufacturerID)) {
       this.saveError4MissManufacturer(request);
       return this.unspecified(mapping, rawForm, request, res);
    }
    
    String modelID = form.getString("modelID");
    if (StringUtils.isEmpty(modelID)) {
       this.saveError4MissModel(request);
       return this.unspecified(mapping, rawForm, request, res);
    }
    
    String phoneNumber = form.getString("phoneNumber");
    if (StringUtils.isEmpty(phoneNumber)) {
       this.saveError4MissingPhoneNumber(request);
       return this.unspecified(mapping, rawForm, request, res);
    }
    
    String checkCode = request.getParameter("checkcode");
    HttpSession session = request.getSession(false);
    if (StringUtils.isEmpty(checkCode) 
        || !checkCode.equals((String)session.getAttribute(CheckCodeAction.CHECKCODE_ATTRIBUTE_NAME))) {
      this.saveError4MissCheckCode(request);
      return this.unspecified(mapping, rawForm, request, res);
    }
    
    ManagementBeanFactory factory = PersistentManager.getManagementBeanFactory(request);
    Model model = this.getModel(factory, form, request);

    // 检查用户所选择的型号是否有兼容的软件包
    SoftwareBean softwareBean = factory.createSoftwareBean();
    Software software = this.getSoftware(factory, form, request);
    SoftwarePackage pkg = softwareBean.getPackage(software, model);
    if (pkg == null) {
      return this.noAvailiablePackage(mapping, rawForm, request, res);
    }

    // 检查用户选择的型号是否支持软件管理
    SoftwareManagementJobAdapter msmAdapter = new SoftwareManagementJobAdapterImpl(factory);
    if (msmAdapter.getProcessor(ProvisionJob.JOB_TYPE_SOFTWARE_INSTALL, model) == null) {
       saveError4NotSupportSoftwareInstallInDMMode(request);
       return (mapping.findForward("next4wapush"));
    }
    
    return (mapping.findForward("next4dm"));
  }

  /* (non-Javadoc)
   * @see com.npower.dm.myportal.cp.BaseWizardAction#prev(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  public ActionForward prev(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
    return (mapping.findForward("prev"));
  }
  
  /**
   * 当前软件没有兼容于用户选择的终端型号
   * @param mapping
   * @param rawForm
   * @param request
   * @param res
   * @return
   * @throws Exception
   */
  public ActionForward noAvailiablePackage(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
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
    if (model != null) {
      request.setAttribute("model", model);
      request.setAttribute("manufacturer", DecoratorHelper.decorate(model.getManufacturer(), this.getLocale(request)));
    }
    
    ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
    ProfileCategory category = templateBean.getProfileCategoryByName(ProfileCategory.MSM_CATEGORY_NAME);
    request.setAttribute("category", DecoratorHelper.decorate(category, this.getLocale(request)));
    
    // Get Software
    Software software = this.getSoftware(factory, form, request);
    request.setAttribute("software", software);
    return (mapping.findForward("next4no_availiable_package"));
  }
  
}
