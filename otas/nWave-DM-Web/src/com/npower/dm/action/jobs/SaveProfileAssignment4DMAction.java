//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.npower.dm.action.jobs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.npower.dm.action.ActionHelper;
import com.npower.dm.action.BaseLookupDispatchAction;
import com.npower.dm.action.common.TargetDevicesCaculator;
import com.npower.dm.core.Device;
import com.npower.dm.core.DeviceGroup;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.daemon.JobEvent;
import com.npower.dm.daemon.JobEventListener;
import com.npower.dm.daemon.JobEventType;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileConfigBean;
import com.npower.dm.management.ProvisionJobBean;

/**
 * MyEclipse Struts Creation date: 06-15-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/device/job/SaveProfileAssignment"
 *                name="DeviceJobProfileAssignmentForm" parameter="action"
 *                scope="request" validate="true"
 * @struts.action-forward name="view" path="/jsp/device/job"
 *                        contextRelative="true"
 * @struts.action-forward name="success" path="/jsp/device/job/success.jsp"
 *                        contextRelative="true"
 */
public class SaveProfileAssignment4DMAction extends BaseLookupDispatchAction {

  public class Parameter {
    private String name;
    private Object value;

    /**
     * 
     */
    public Parameter() {
      super();
      
    }
    
    /**
     * @param name
     * @param value
     */
    public Parameter(String name, Object value) {
      super();
      this.name = name;
      this.value = value;
    }

    /**
     * @return the name
     */
    public String getName() {
      return name;
    }
    
    /**
     * @param name the name to set
     */
    public void setName(String name) {
      this.name = name;
    }
    
    /**
     * @return the value
     */
    public Object getValue() {
      return value;
    }
    
    /**
     * @param value the value to set
     */
    public void setValue(Object value) {
      this.value = value;
    }
    
  }
  // --------------------------------------------------------- Instance
  // Variables

  protected Map getKeyMethodMap() {
    Map<String, String> map = new HashMap<String, String>();
    map.put("page.button.submit.label", "update");
    map.put("page.button.update.label", "schedule");
    map.put("page.button.create.label", "schedule");
    map.put("page.button.previous.label", "edit");
    return(map);
  }

  // --------------------------------------------------------- Methods

  public ActionForward schedule(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    ProfileAssignmentForm4Save form = (ProfileAssignmentForm4Save) rawForm;
    form.setValue("sendNotification", new Boolean(true));

    // Set Target Devices into request attribute "targetDevices" for JSP
    BaseJobsAction.setTargetDevices(this.getManagementBeanFactory(request), request);

    
    
    ManagementBeanFactory factory = null;
    try {
        factory = this.getManagementBeanFactory(request);
        ProfileConfigBean profileBean = factory.createProfileConfigBean();
        ProfileConfig profile = profileBean.getProfileConfigByID((String)form.getValue("profileID"));
        // Set Default Job Parameters
        String jobType = ProvisionJob.JOB_TYPE_ASSIGN_PROFILE;
        String[] parameters4JobName = {profile.getName()};
        BaseJobsAction.setDefaultJobParameters(rawForm, this.getResources(request), this.getLocale(request), jobType, parameters4JobName);
        
        request.setAttribute("profile", profile);
        
        // Iterator all of parameters
        Set<String> names = form.getMap().keySet();
        List<Parameter> parameters = new ArrayList<Parameter>();
        for (String paramName: names) {
            if (paramName.startsWith("attribute__")) {
               Object paramValue = form.getValue(paramName);
               if (paramValue == null) {
                  continue;
               }
               if (paramValue instanceof FormFile) {
                  FormFile file = (FormFile)paramValue;
                  File outputFile = File.createTempFile("form_file_", ".bin");
                  OutputStream out = new FileOutputStream(outputFile);
                  InputStream in = file.getInputStream();
                  byte[] buf = new byte[512];
                  int len = in.read(buf);
                  while (len > 0) {
                        out.write(buf, 0, len);
                        len = in.read(buf);
                  }
                  out.close();
                  
                  paramValue = outputFile.getAbsolutePath();
               }
               Parameter parameter = new Parameter(paramName, paramValue);
               parameters.add(parameter);
            }
        }
        request.setAttribute("parameters", parameters);

        return mapping.findForward("schedule");
    } catch (Exception e) {
      throw e;
    } finally {
      
    }
  }

  public ActionForward edit(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    // Set Target Devices into request attribute "targetDevices" for JSP
    BaseJobsAction.setTargetDevices(this.getManagementBeanFactory(request), request);

    return mapping.findForward("edit");
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
    ProfileAssignmentForm4Save form = (ProfileAssignmentForm4Save) rawForm;
    String profileID = (String)form.getValue("profileID");
    String assignmentID = (String)form.getValue("ID");
    String jobName = (String)form.getValue("name");
    String jobDescription = (String)form.getValue("description");
    Boolean sendNotification = new Boolean(false);
    if (StringUtils.isNotEmpty((String)form.getValue("sendNotification"))) {
       sendNotification = new Boolean((String)form.getValue("sendNotification"));
    }
    
    String uiMode = (String)form.getValue("uiMode");
    String initiator = (String)form.getValue("initiator");
    
    String promptType4Beginning = (String) form.getValue("startprompttype");
    String promptText4Beginning = (String) form.getValue("startprompttext");
    String promptType4Finished = (String) form.getValue("endprompttype");
    String promptText4Finished = (String) form.getValue("endprompttext");
    
    Boolean isPrompt4Beginning = new Boolean(false);
    if (StringUtils.isNotEmpty((String) form.getValue("startjob"))) {
      isPrompt4Beginning = new Boolean((String) form.getValue("startjob"));
    }
    
    Boolean isPrompt4Finished = new Boolean(false);
    if (StringUtils.isNotEmpty((String) form.getValue("endjob"))) {
      isPrompt4Finished = new Boolean((String) form.getValue("endjob"));
    }
    
    ManagementBeanFactory factory = null;
    try {
        factory = this.getManagementBeanFactory(request);
        DeviceBean deviceBean = factory.createDeviceBean();
        ProvisionJobBean jobBean = factory.createProvisionJobBean();
       
        String jobScheduleString = (String)form.getValue("jobSchedule");
        Date scheduledTime = new Date();
        if (StringUtils.isNotEmpty(jobScheduleString)) {
           DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
           scheduledTime = formatter.parse(jobScheduleString);
        }

        TargetDevicesCaculator targetDevicesCaculator = BaseJobsAction.loadTargetDevicesCaculator(factory, request);
        if (targetDevicesCaculator == null || targetDevicesCaculator.isEmpty()) {
           throw new Exception("Must sppecify device list.");
        }
        DeviceGroup group = targetDevicesCaculator.getDeviceGroup();
        for (Device device: group.getDevices()) {
            ProvisionJob job = ActionHelper.submitAssignmentJob(request, factory, scheduledTime, device, profileID, assignmentID, jobName, jobDescription, sendNotification, uiMode, initiator);
            
            factory.beginTransaction();
            String concurrentSize = (String)form.getValue("concurrentSize");
            String concurrentInterval= (String)form.getValue("concurrentInterval");
            String priority = (String)form.getValue("priority");
            job.setConcurrentSize(new Long(concurrentSize));
            job.setConcurrentInterval(new Long(concurrentInterval));
            job.setPriority(new Integer(priority));
            job.setPrompt4Beginning(isPrompt4Beginning);
            job.setPromptType4Beginning(promptType4Beginning);
            job.setPromptText4Beginning(promptText4Beginning);
            job.setPrompt4Finished(isPrompt4Finished);
            job.setPromptType4Finished(promptType4Finished);
            job.setPromptText4Finished(promptText4Finished);
            jobBean.update(job);
            factory.commit();
            
            request.setAttribute("provisionJob", job);

            // Send Event to Job Event Listener
            JobEventListener eventListener = ActionHelper.getJobEventListener();
            eventListener.notify(new JobEvent(JobEventType.Create, job.getID()));
        }

        return (mapping.findForward("success"));
    } catch (Exception ex) {
      if (factory != null) {
         factory.rollback();
      }
      throw ex;
    } finally {
    }
    
  }

}
