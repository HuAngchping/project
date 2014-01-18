/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.npower.dm.action.device;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import com.npower.dm.action.ActionHelper;
import com.npower.dm.action.BaseAction;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.sms.client.SMSGWConnectionException;


/** 
 * XDoclet definition:
 * @struts.action path="/notification/send" name="NotificationForm" input="/notification/input" scope="request" validate="true"
 * @struts.action-forward name="success" path="aaaaa" contextRelative="true"
 */
public class SendNotificationAction extends BaseAction {
  
  private Device findDevice(ManagementBeanFactory beanFactory, String deviceID) throws DMException {
    DeviceBean deviceBean = beanFactory.createDeviceBean();
    Device device = deviceBean.getDeviceByID(deviceID);
    if (device == null) {
       throw new DMException("Could not find device for Bootstrap by deviceID: " + deviceID);
    }
    return device;
  }

  /** 
   * Method execute
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward execute(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    
    if (this.isCancelled(request)) {
      return this.canceld(mapping, rawForm, request, response);
   }
    
    DynaValidatorForm form = (DynaValidatorForm) rawForm;

    String deviceID = form.getString("deviceID");
    String jobID = form.getString("jobID");
    
    ManagementBeanFactory beanFactory = this.getManagementBeanFactory(request);
    try {
        Device device = this.findDevice(beanFactory, deviceID);
        request.setAttribute("device", device);
        String deviceExternalID = device.getExternalId();
        int sessionID = (int)System.currentTimeMillis();
        if (!StringUtils.isEmpty(jobID)) {
           try {
               sessionID = (int)Long.parseLong(jobID);
           } catch (Exception e) {
           }
        }
        // Send Notification
        long scheduleTime = 0L;
        String uiMode = form.getString("uiMode");
        String initiator = form.getString("initiator");
        ActionHelper.sendNotification(beanFactory, deviceExternalID, sessionID, scheduleTime, uiMode, initiator);

        return mapping.findForward("success");
    } catch (SMSGWConnectionException e) {
      return mapping.findForward("failure");
    } catch (Exception e) {
      throw e;
    } finally {
    }
  }

  /** 
   * Method execute
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward canceld(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    
    DynaValidatorForm form = (DynaValidatorForm) rawForm;
    String deviceID = form.getString("deviceID");

    try {
        ManagementBeanFactory beanFactory = this.getManagementBeanFactory(request);
        Device device = this.findDevice(beanFactory, deviceID);
        request.setAttribute("device", device);
  
        return mapping.findForward("cancel");
    } catch (DMException e) {
      throw e;
    }
  }
}