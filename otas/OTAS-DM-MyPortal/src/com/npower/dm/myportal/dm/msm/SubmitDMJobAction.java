/**
  * $Header: /home/master/OTAS-DM-MyPortal/src/com/npower/dm/myportal/dm/msm/SubmitDMJobAction.java,v 1.5 2008/06/16 10:11:24 zhao Exp $
  * $Revision: 1.5 $
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

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.npower.dm.action.PersistentManager;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.Device;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.core.Software;
import com.npower.dm.decorator.DecoratorHelper;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileTemplateBean;
import com.npower.dm.management.ProvisionJobBean;
import com.npower.dm.myportal.ActionHelper4MyPortal;
import com.npower.dm.myportal.BaseWizardAction;
import com.npower.dm.myportal.cp.ClientProvWizardForm;
import com.npower.dm.soap.client.DMSoapClientFactory;
import com.npower.dm.soap.common.ProvisionJobDispatcherService;
import com.npower.wap.omacp.notification.UIMode;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.5 $ $Date: 2008/06/16 10:11:24 $
 */
public class SubmitDMJobAction extends BaseWizardAction {

  /* (non-Javadoc)
   * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  public ActionForward view(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
    ClientProvWizardForm form = (ClientProvWizardForm)rawForm;
    
    ManagementBeanFactory factory = PersistentManager.getManagementBeanFactory(request);
    String deviceExternalID = getDeviceExternalID(factory, request, form);
    
    String phoneNumber = getPhoneNumber(factory, request, form);

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

    // Get ProfileCategory
    ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
    ProfileCategory category = templateBean.getProfileCategoryByName(ProfileCategory.MSM_CATEGORY_NAME);
    request.setAttribute("category", DecoratorHelper.decorate(category, this.getLocale(request)));
    
    // Get Software
    Software software = this.getSoftware(factory, form, request);
    request.setAttribute("software", software);
    
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
    
    // Submit a job
    ProvisionJob job = null;
    try {
        factory.beginTransaction();
        // Submit job
        ProvisionJobBean bean = factory.createProvisionJobBean();
        job = bean.newJob4SoftwareInstall(device, software);
        //job.setName("");
        //job.setDescription("");
        Date scheduleTime = new Date();
        job.setScheduledTime(scheduleTime);
        job.setRequiredNotification(true);
        job.setUiMode("" + UIMode.USER_INTERACTION.getValue());
        bean.update(job);
        factory.commit();
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
    }
    
    // Dispatch immediately
    try {
      DMSoapClientFactory soapFactory = ActionHelper4MyPortal.getDMSoapClientFactory();
      ProvisionJobDispatcherService dispatcher = soapFactory.getProvisionJobDispatcherService();
      dispatcher.dispatch("" + job.getID());
    } catch (Exception e) {
      throw e;
    }
    
    request.setAttribute("device", device);
    request.setAttribute("userPin", carrier.getDefaultBootstrapUserPin());
    return (mapping.findForward("success"));
  }

  public ActionForward next(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
    return (mapping.findForward("next"));
  }
  
  public ActionForward prev(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
    return (mapping.findForward("prev"));
  }
  
}
