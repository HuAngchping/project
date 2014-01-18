/**
  * $Header: /home/master/OTAS-DM-MyPortal/src/com/npower/dm/myportal/dm/msm/SubmitWapPushAction.java,v 1.6 2008/06/16 10:11:24 zhao Exp $
  * $Revision: 1.6 $
  * $Date: 2008/06/16 10:11:24 $
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
package com.npower.dm.myportal.dm.msm;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import sync4j.framework.config.ConfigurationConstants;

import com.npower.dm.action.PersistentManager;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;
import com.npower.dm.core.Device;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.Software;
import com.npower.dm.decorator.DecoratorHelper;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileTemplateBean;
import com.npower.dm.management.SoftwareBean;
import com.npower.dm.myportal.ActionHelper4MyPortal;
import com.npower.dm.myportal.BaseWizardAction;
import com.npower.dm.myportal.cp.ClientProvWizardForm;
import com.npower.dm.util.ConfigHelper;
import com.npower.sms.SmsBuilder;
import com.npower.sms.client.SmsSender;
import com.npower.wap.push.SmsWapPushMessage;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.6 $ $Date: 2008/06/16 10:11:24 $
 */
public class SubmitWapPushAction extends BaseWizardAction {
  
  
  @Override
  protected Map<String, String> getKeyMethodMap() {
    Map<String, String> map = super.getKeyMethodMap();
    map.put("page.msm.send.wappush.button.label", "view");
    return(map);
  }
  
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
    if (model != null) {
      request.setAttribute("model", model);
      request.setAttribute("manufacturer", DecoratorHelper.decorate(model.getManufacturer(), this.getLocale(request)));
    }
    
    ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
    ProfileCategory category = templateBean.getProfileCategoryByName(ProfileCategory.MSM_CATEGORY_NAME);
    request.setAttribute("category", DecoratorHelper.decorate(category, this.getLocale(request)));
    
    // Get Software
    Software software = this.getSoftware(factory, form, request);
    if (software != null) {
       request.setAttribute("software", software);
    }
    
    String phoneNumber = getPhoneNumber(factory, request, form);

    // Enroll a device
    Device device = null;
    try {
        DeviceBean deviceBean = factory.createDeviceBean();
        factory.beginTransaction();
        device = deviceBean.enroll(phoneNumber, model, carrier, this.getServiceProvider(factory, request, form));
        factory.commit();
    } catch (Exception e) {
      if (factory != null) {
         factory.rollback();
      }
      throw e;
    } finally {
    }
    
    // Send a WapPush message which included download url.
    try {
        SmsSender smsSender = com.npower.dm.action.ActionHelper.getSmsSender(carrier);
        MessageResources messageResources = this.getResources(request);
        Locale locale = this.getLocale(request);
        String softwareName = software.getVendor().getName() + " " + software.getName();
        String text = messageResources.getMessage(locale, "message.msm.wappush.download.software", softwareName); 
        SoftwareBean bean = factory.createSoftwareBean();
        String serverDownlaodURIPattern = ConfigHelper.getSecurityProperties().getProperty(ConfigurationConstants.CFG_SERVER_SOFTWARE_DOWNLOAD_URI);
        String url = bean.getSoftwareDownloadURL(software, model, serverDownlaodURIPattern);
        SmsWapPushMessage msg = SmsBuilder.createWapSiPushMsg(url, text);
        smsSender.send(msg, phoneNumber, phoneNumber);
    } catch (Exception e) {
      // ∑¢ÀÕ–≈œ¢ ß∞‹
      throw e;
    } finally {
    }
            
    request.setAttribute("device", device);
    return (mapping.findForward("success"));
  }

  /* (non-Javadoc)
   * @see com.npower.dm.myportal.cp.BaseWizardAction#next(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  public ActionForward next(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
    return (mapping.findForward("next"));
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.myportal.cp.BaseWizardAction#prev(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  public ActionForward prev(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
    return (mapping.findForward("prev"));
  }
  
}
