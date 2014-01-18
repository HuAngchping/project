//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.npower.dm.action.device;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.DynaValidatorForm;

import com.npower.dm.action.ActionHelper;
import com.npower.dm.action.BaseLookupDispatchAction;
import com.npower.dm.command.Compiler;
import com.npower.dm.command.Compiler4CommandScript;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.DeviceGroup;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.daemon.JobEvent;
import com.npower.dm.daemon.JobEventListener;
import com.npower.dm.daemon.JobEventType;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProvisionJobBean;
import com.npower.dm.server.engine.EngineConfig;
import com.npower.dm.util.XMLPrettyFormatter;

/**
 * MyEclipse Struts Creation date: 06-15-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/device/job/SaveJobScirpt" name="DeviceJobScriptForm"
 *                parameter="action" scope="request" validate="true"
 */
public class SaveJobScriptAction extends BaseLookupDispatchAction {

  // --------------------------------------------------------- Instance
  // Variables

  protected Map getKeyMethodMap()
  {
    Map<String, String> map = new HashMap<String, String>();
    map.put("page.device.job.script.button.compile", "view");
    map.put("page.button.submit.label", "update");
    map.put("page.button.modify.label", "edit");
    map.put("page.button.next.label", "schedule");
    map.put("page.button.previous.label", "edit");
    return(map);
  }

  private void getDevice(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    DynaValidatorForm deviceForm = (DynaValidatorForm) rawForm;
    
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    DeviceBean deviceBean = factory.createDeviceBean();
    Device device = deviceBean.getDeviceByID((String) deviceForm.get("deviceID"));

    request.setAttribute("device", device);
  }
  
  // --------------------------------------------------------- Methods

  public ActionForward schedule(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    DynaValidatorForm form = (DynaValidatorForm) rawForm;
    form.set("sendNotification", new Boolean(true));
    
    // Set default job name
    MessageResources messageResources = this.getResources(request);
    String jobName = ActionHelper.getDefaultJobName(messageResources, this.getLocale(request), ProvisionJob.JOB_TYPE_SCRIPT, null);
    form.set("name", jobName);
    
    return mapping.findForward("schedule");
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
  public ActionForward update(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    DynaValidatorForm form = (DynaValidatorForm) rawForm;
    ManagementBeanFactory factory = null;
    try {
        String deviceID = form.getString("deviceID");
        String uiMode = form.getString("uiMode");
        String initiator = form.getString("initiator");
        boolean sendNotification = false;
        Boolean tmp = (Boolean)form.get("sendNotification");
        if (tmp != null && tmp.booleanValue()) {
          sendNotification = true;
        }

        String jobScheduleString = form.getString("jobSchedule");
        Date scheduledTime = new Date();
        if (StringUtils.isNotEmpty(jobScheduleString)) {
           DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
           scheduledTime = formatter.parse(jobScheduleString);
        }

        String commandScript = form.getString("commandScript");
        // Pretty format 
        XMLPrettyFormatter formatter = new XMLPrettyFormatter(commandScript);
        commandScript = formatter.format();
        
        factory = this.getManagementBeanFactory(request);
        ProvisionJobBean jobBean = factory.createProvisionJobBean();

        DeviceBean deviceBean = factory.createDeviceBean();
        DeviceGroup group = deviceBean.newDeviceGroup();
        Device device = deviceBean.getDeviceByID(deviceID);
        
        factory.beginTransaction();
        deviceBean.add(group, device);
        deviceBean.update(group);
        
        ProvisionJob job = jobBean.newJob4Command(group, commandScript);
        //job.setName(form.getString("name"));
        //job.setDescription(form.getString("description"));
        //job.setScheduledTime(scheduledTime);
        jobBean.update(job);
        
        factory.commit();
        
        // 为了纠正Bug318, 重新创建连接, 滞后修改name, description等信息, 否则Hibernate抛出异常
        ManagementBeanFactory anotherFactory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        try {
            anotherFactory.beginTransaction();
            ProvisionJobBean anotherJobBean = anotherFactory.createProvisionJobBean();
            ProvisionJob job1 = anotherJobBean.loadJobByID(job.getID());
            job1.setName(form.getString("name"));
            job1.setDescription(form.getString("description"));
            job1.setScheduledTime(scheduledTime);
            job1.setUiMode(uiMode);
            job1.setRequiredNotification(sendNotification);
            Integer priority = (Integer)form.get("priority");
            job.setPriority(priority);
            anotherJobBean.update(job1);
            anotherFactory.commit();
        } catch (Exception e) {
        } finally {
          if (anotherFactory != null) {
             anotherFactory.release();
          }
        }
        
        // Send Event to Job Event Listener
        JobEventListener eventListener = ActionHelper.getJobEventListener();
        eventListener.notify(new JobEvent(JobEventType.Create, job.getID()));

        request.setAttribute("device", device);
        request.setAttribute("provisionJob", job);
        return (mapping.findForward("success"));
    } catch (Exception e) {
      if (factory != null ) {
         factory.rollback();
      }
      throw e;
    } finally {
    }
    
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
  public ActionForward view(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    DynaValidatorForm form = (DynaValidatorForm) rawForm;

    // Load the device
    this.getDevice(mapping, rawForm, request, response);
    
    String commandScript = form.getString("commandScript");
    Compiler compiler = new Compiler4CommandScript(commandScript);
    boolean verify;
    try {
        verify = compiler.verify();
        request.setAttribute("compileVerify", new Boolean(verify));
    } catch (DMException e) {
      //String cause = e.getMessage();
      //request.setAttribute("compileVerifyMessage", cause);
      
      request.setAttribute("compileVerify", new Boolean(false));
      ActionMessages messages = new ActionMessages();
      ActionMessage message = new ActionMessage("page.device.job.script.checkErrorMessage", e.getLocalizedMessage());
      messages.add("checkErrorMessage", message);
      this.saveMessages(request, messages);
      
    }
    
    return (mapping.findForward("ViewScript"));
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
  public ActionForward edit(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    // Load the device
    this.getDevice(mapping, rawForm, request, response);

    return (mapping.findForward("EditScript"));
  }

  public ActionForward cancelled(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    String deviceID = request.getParameter("deviceID");
    if (StringUtils.isNotEmpty(deviceID)) {
       request.setAttribute("deviceID", deviceID);
    }
    return mapping.findForward("cancel");
  }  
  
  /*
  public ActionForward unspecified(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    return (mapping.findForward("cancel"));
  } 
  */ 
}
