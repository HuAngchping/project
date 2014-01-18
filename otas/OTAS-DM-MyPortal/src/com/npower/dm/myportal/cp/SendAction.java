/**
  * $Header: /home/master/OTAS-DM-MyPortal/src/com/npower/dm/myportal/cp/SendAction.java,v 1.20 2008/11/09 14:08:04 zhao Exp $
  * $Revision: 1.20 $
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

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.npower.cp.OTAInventory;
import com.npower.cp.convertor.ValueFetcher;
import com.npower.dm.action.ActionHelper;
import com.npower.dm.action.PersistentManager;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.Device;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileAssignment;
import com.npower.dm.core.ProfileAttribute;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.OTAClientProvJobBean;
import com.npower.dm.management.ProfileAssignmentBean;
import com.npower.dm.management.ProvisionJobBean;
import com.npower.dm.myportal.ActionHelper4MyPortal;
import com.npower.dm.myportal.BaseWizardAction;
import com.npower.dm.soap.client.DMSoapClientFactory;
import com.npower.dm.soap.common.ProvisionJobDispatcherService;
import com.npower.sms.client.SmsSender;
import com.npower.wap.omacp.OMACPSecurityMethod;
import com.npower.wap.omacp.notification.UIMode;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.20 $ $Date: 2008/11/09 14:08:04 $
 */
public class SendAction extends BaseWizardAction {

  private OMACPSecurityMethod defaultSecurityMethod = OMACPSecurityMethod.USERPIN;
  private String defaultPin = "1234";

  /* (non-Javadoc)
   * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  public ActionForward view(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
    ClientProvWizardForm form = (ClientProvWizardForm)rawForm;
    
    ManagementBeanFactory factory = PersistentManager.getManagementBeanFactory(request);

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
    ProfileCategory category = this.getProfileCategory(factory, form, request);
    if (category == null) {
       return mapping.getInputForward();
    }
    request.setAttribute("category", category);
    
    // Get Profile
    ProfileConfig profile = getProfile(factory, form, request);
    if (profile == null) {
       return mapping.getInputForward();
    }
    request.setAttribute("profile", profile);
    
    // Submit job
    int maxRetry = -1;
    long maxDuration = 0;
    
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
    
    ProvisionJob job = null;
    // 判断是否采用DM模式
    boolean alwaysCPMode = false;
    String mode = form.getString("cpMode");
    if (StringUtils.isNotEmpty(mode)) {
       alwaysCPMode = true;
    } else {
      form.setValue("cpMode", null);
    }
    if (!alwaysCPMode && device.getModel().getIsOmaDmEnabled()) {
       // Submit a DM job
       job = this.submitJobInDMMode(factory, form, device, profile, maxRetry, maxDuration);
    } else {
      // Submit a CP job
      job = this.submitJobInCPMode(factory, form, device, profile, maxRetry, maxDuration);
    }
    
    // Dispatch immediately
    try {
      DMSoapClientFactory soapFactory = ActionHelper4MyPortal.getDMSoapClientFactory();
      ProvisionJobDispatcherService dispatcher = soapFactory.getProvisionJobDispatcherService();
      dispatcher.dispatch("" + job.getID());
    } catch (Exception e) {
      throw e;
    }
            
    request.setAttribute("pin", defaultPin);
    return (mapping.findForward("success"));
  }

  /**
   * 提交CP模式的配置下发任务
   * @param factory
   * @param form
   * @param device
   * @param profile
   * @param maxRetry
   * @param maxDuration
   * @return
   * @throws Exception
   */
  private ProvisionJob submitJobInCPMode(ManagementBeanFactory factory, ClientProvWizardForm form, Device device,
      ProfileConfig profile, int maxRetry, long maxDuration) throws Exception {
    ProvisionJob job = null;
    try {
        // No required transaction control
        OTAInventory otaInventory = ActionHelper.getOTAInventory();
        SmsSender smsSender = ActionHelper.getSmsSender(device.getSubscriber().getCarrier());
        OTAClientProvJobBean bean = factory.createOTAClientProvJobBean(otaInventory, smsSender);
        
        ValueFetcher<ProfileCategory, String, String> valueFetcher = new ActionFormValueFetcher(profile, form);
        OMACPSecurityMethod pinType4Device = defaultSecurityMethod;
        String pin4Device = defaultPin;
        if (StringUtils.isNotEmpty(device.getSubscriber().getIMSI())) {
           // 尽可能使用NETWPIN
           pinType4Device = OMACPSecurityMethod.NETWPIN;
           pin4Device = device.getSubscriber().getIMSI();
        }
        job = bean.provision(device, profile, valueFetcher, pinType4Device, pin4Device, System.currentTimeMillis(), maxRetry, maxDuration);
    } catch (Exception e) {
      throw e;
    } finally {
    }
    return job;
  }

  /**
   * 提交DM模式的配置下发任务
   * @param factory
   * @param form
   * @param device
   * @param profile
   * @param maxRetry
   * @param maxDuration
   * @return
   * @throws Exception
   */
  private ProvisionJob submitJobInDMMode(ManagementBeanFactory factory, ClientProvWizardForm form, Device device,
      ProfileConfig profile, int maxRetry, long maxDuration) throws Exception {
    boolean sendNotification = true;
    try {
        ProfileAssignmentBean assignmentBean = factory.createProfileAssignmentBean();
        ProvisionJobBean jobBean = factory.createProvisionJobBean();
        factory.beginTransaction();
        ProfileAssignment assignment = assignmentBean.newProfileAssignmentInstance(profile, device);
        // Set Value to assignment
        // Processing user attributes
        Set<ProfileAttribute> attributes = profile.getProfileTemplate().getProfileAttributes();
        for (ProfileAttribute attribute: attributes) {
            if (attribute.getIsUserAttribute()) {
               long attrID = attribute.getID();
               String attrName = attribute.getName();
               String value = form.getString("attribute__" + attrID + "__value");
               assignmentBean.setAttributeValue(assignment, attrName, (String)value);
            }
        }
        
        String jobName = "门户用户请求发送配置:" + profile.getName();
        String jobDescription = "门户用户请求发送配置:" + profile.getName();
        Date scheduledTime = new Date();
        List<ProvisionJob> jobs = jobBean.newJobs4Assignment(assignment, jobName, jobDescription, scheduledTime );
        ProvisionJob firstJob = null;
        for (ProvisionJob job: jobs) {
          firstJob = job;
          if (sendNotification) {
             job.setUiMode(UIMode.USER_INTERACTION.toString());
             jobBean.update(job);
          }
        }
        factory.commit();
        return firstJob;
    } catch (Exception e) {
      throw e;
    } finally {
    }
  }

  public ActionForward next(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
    return (mapping.findForward("next"));
  }
  
  public ActionForward prev(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
    return (mapping.findForward("prev"));
  }
  
}
