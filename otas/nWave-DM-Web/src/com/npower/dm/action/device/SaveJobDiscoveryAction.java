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
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.DynaValidatorForm;

import com.npower.dm.action.ActionHelper;
import com.npower.dm.action.BaseLookupDispatchAction;
import com.npower.dm.core.Device;
import com.npower.dm.core.DeviceGroup;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.daemon.JobEvent;
import com.npower.dm.daemon.JobEventListener;
import com.npower.dm.daemon.JobEventType;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProvisionJobBean;

/**
 * MyEclipse Struts Creation date: 06-15-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/device/job/SaveJobScirpt" name="DeviceJobScriptForm"
 *                parameter="action" scope="request" validate="true"
 */
public class SaveJobDiscoveryAction extends BaseLookupDispatchAction {

  // --------------------------------------------------------- Instance
  // Variables

  protected Map getKeyMethodMap()
  {
    Map<String, String> map = new HashMap<String, String>();
    map.put("page.button.submit.label", "update");
    map.put("page.button.next.label", "schedule");
    map.put("page.button.previous.label", "edit");
    return(map);
  }

  // --------------------------------------------------------- Methods

  public ActionForward edit(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    return mapping.findForward("edit");
  }

  public ActionForward schedule(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    
    DynaValidatorForm form = (DynaValidatorForm) rawForm;
    form.set("sendNotification", new Boolean(true));
    
    // Set default job name
    MessageResources messageResources = this.getResources(request);
    String jobName = ActionHelper.getDefaultJobName(messageResources, this.getLocale(request), ProvisionJob.JOB_TYPE_DISCOVERY, null);
    form.set("name", jobName);

    form.set("nodeURIs", ActionHelper.checkingNodeURIs((String[])form.get("nodeURIs")));
    
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
        
        String promptType4Beginning = form.getString("startprompttype");
        String promptText4Beginning = form.getString("startprompttext");
        String promptType4Finished =  form.getString("endprompttype");
        String promptText4Finished =  form.getString("endprompttext");
        
        Boolean isPrompt4Beginning = new Boolean(false);
        if (StringUtils.isNotEmpty(form.getString("startjob"))) {
          isPrompt4Beginning = new Boolean(form.getString("startjob"));
        }
        
        Boolean isPrompt4Finished = new Boolean(false);
        if (StringUtils.isNotEmpty(form.getString("endjob"))) {
          isPrompt4Finished = new Boolean(form.getString("endjob"));
        }
        
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
        
        String[] nodeURIs = ActionHelper.checkingNodeURIs((String[])form.get("nodeURIs"));
        
        factory = this.getManagementBeanFactory(request);
        ProvisionJobBean jobBean = factory.createProvisionJobBean();

        // Create a device group.
        DeviceBean deviceBean = factory.createDeviceBean();
        DeviceGroup group = deviceBean.newDeviceGroup();
        Device device = deviceBean.getDeviceByID(deviceID);
        
        factory.beginTransaction();
        deviceBean.add(group, device);
        deviceBean.update(group);
        
        // Create Job
        ProvisionJob job = jobBean.newJob4Discovery(group, nodeURIs);
        job.setName(form.getString("name"));
        job.setDescription(form.getString("description"));
        job.setScheduledTime(scheduledTime);
        job.setUiMode(uiMode);
        job.setRequiredNotification(sendNotification);
        Integer priority = (Integer)form.get("priority");
        job.setPriority(priority);
        job.setPrompt4Beginning(isPrompt4Beginning);
        job.setPromptType4Beginning(promptType4Beginning);
        job.setPromptText4Beginning(promptText4Beginning);
        job.setPrompt4Finished(isPrompt4Finished);
        job.setPromptType4Finished(promptType4Finished);
        job.setPromptText4Finished(promptText4Finished);
        jobBean.update(job);
        
        factory.commit();
        
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
