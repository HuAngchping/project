//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.npower.dm.action.jobs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import com.npower.dm.action.ActionHelper;
import com.npower.dm.action.BaseLookupDispatchAction;
import com.npower.dm.action.common.TargetDevicesCaculator;
import com.npower.dm.core.DMException;
import com.npower.dm.core.DeviceGroup;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.core.Software;
import com.npower.dm.daemon.JobEvent;
import com.npower.dm.daemon.JobEventListener;
import com.npower.dm.daemon.JobEventType;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProvisionJobBean;
import com.npower.dm.management.SoftwareBean;

/**
 * MyEclipse Struts Creation date: 06-15-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/device/job/SaveJobScirpt" name="DeviceJobScriptForm"
 *                parameter="action" scope="request" validate="true"
 */
public class SaveJob4SoftwareDeactiveAction extends BaseLookupDispatchAction {

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

  /**
   * Return software list for software install
   * @param factory
   * @param request
   * @throws DMException
   */
  public static List<Software> getSoftwares4Install(ManagementBeanFactory factory, HttpServletRequest request) throws DMException {
    SoftwareBean softwareBean = factory.createSoftwareBean();
    List<Software> softwares = softwareBean.getAllOfSoftwares();
    return softwares;
  }  
  
  public ActionForward edit(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    // Set Target Devices into request attribute "targetDevices" for JSP
    BaseJobsAction.setTargetDevices(factory, request);

    List<Software> softwares = SaveJob4SoftwareDeactiveAction.getSoftwares4Install(factory, request);
    request.setAttribute("softwares", softwares);
    return mapping.findForward("edit");
  }

  public ActionForward schedule(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    DynaValidatorForm form = (DynaValidatorForm) rawForm;
    
    

    // Set Target Devices into request attribute "targetDevices" for JSP
    BaseJobsAction.setTargetDevices(this.getManagementBeanFactory(request), request);

    ManagementBeanFactory factory = null;
    try {
        factory = this.getManagementBeanFactory(request);
        String softwareID = form.getString("softwareID");
        SoftwareBean softwareBean = factory.createSoftwareBean();
        Software software = softwareBean.getSoftwareByID(Long.parseLong(softwareID));
        // Set Default Job Parameters
        String jobType = ProvisionJob.JOB_TYPE_SOFTWARE_DEACTIVE;
        String[] parameters4JobName = {software.getName()};
        BaseJobsAction.setDefaultJobParameters(rawForm, this.getResources(request), this.getLocale(request), jobType, parameters4JobName);
        request.setAttribute("software", software);
        return mapping.findForward("schedule");
    } catch (Exception e) {
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
  public ActionForward update(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    DynaValidatorForm form = (DynaValidatorForm) rawForm;
    ManagementBeanFactory factory = null;
    try {
        factory = this.getManagementBeanFactory(request);
        
        TargetDevicesCaculator targetDevicesCaculator = BaseJobsAction.loadTargetDevicesCaculator(factory, request);
        if (targetDevicesCaculator == null || targetDevicesCaculator.isEmpty()) {
           throw new Exception("Must sppecify device list.");
        }

        String softwareID = form.getString("softwareID");
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
        String promptType4Beginning = form.getString("startprompttype");
        String promptText4Beginning = form.getString("startprompttext");
        String promptType4Finished =  form.getString("endprompttype");
        String promptText4Finished =  form.getString("endprompttext");
        
        Boolean isPrompt4Beginning = new Boolean(false);
        if (StringUtils.isNotEmpty(form.getString("startjob"))) {
          isPrompt4Beginning = new Boolean(form.getString("startjob"));
        }
        
        Boolean isPrompt4Finished = new Boolean(false);
        if (StringUtils.isNotEmpty( form.getString("endjob"))) {
          isPrompt4Finished = new Boolean(form.getString("endjob"));
        }
        ProvisionJobBean jobBean = factory.createProvisionJobBean();
        SoftwareBean softwareBean = factory.createSoftwareBean();
        Software software = softwareBean.getSoftwareByID(Long.parseLong(softwareID));

        factory.beginTransaction();
        DeviceGroup group = targetDevicesCaculator.getDeviceGroup();
        
        ProvisionJob job = jobBean.newJob4SoftwareDeactivation(group, software);
        job.setName(form.getString("name"));
        job.setDescription(form.getString("description"));
        job.setScheduledTime(scheduledTime);
        job.setUiMode(uiMode);
        job.setRequiredNotification(sendNotification);
        
        Long concurrentSize = (Long)form.get("concurrentSize");
        Long concurrentInterval= (Long)form.get("concurrentInterval");
        Integer priority = (Integer)form.get("priority");
        job.setConcurrentSize(concurrentSize);
        job.setConcurrentInterval(concurrentInterval);
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
    return mapping.findForward("cancel");
  }  
  
  /*
  public ActionForward unspecified(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    return (mapping.findForward("cancel"));
  } 
  */ 
}
