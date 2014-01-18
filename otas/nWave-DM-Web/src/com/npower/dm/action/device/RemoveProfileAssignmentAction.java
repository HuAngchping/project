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
import com.npower.dm.core.ProfileAssignment;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.daemon.JobEvent;
import com.npower.dm.daemon.JobEventListener;
import com.npower.dm.daemon.JobEventType;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileAssignmentBean;
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
public class RemoveProfileAssignmentAction extends BaseLookupDispatchAction {

  // --------------------------------------------------------- Instance
  // Variables

  protected Map getKeyMethodMap() {
    Map<String, String> map = new HashMap<String, String>();
    map.put("page.button.submit.label", "update");
    map.put("page.button.update.label", "schedule");
    map.put("page.button.create.label", "schedule");
    map.put("page.button.previous.label", "view");
    return(map);
  }

  // --------------------------------------------------------- Methods

  public ActionForward schedule(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    DynaValidatorForm form = (DynaValidatorForm) rawForm;
    form.set("sendNotification", new Boolean(true));
    ManagementBeanFactory factory = null;

    // Set default job name
    MessageResources messageResources = this.getResources(request);
    String jobName = ActionHelper.getDefaultJobName(messageResources, this.getLocale(request), ProvisionJob.JOB_TYPE_DELETE_PROFILE, null);
    form.set("name", jobName);
    
    try {
        factory = this.getManagementBeanFactory(request);
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device = deviceBean.getDeviceByID(form.getString("deviceID"));
        request.setAttribute("device", device);
        
        String assignmentID = form.getString("ID");
        ProfileAssignmentBean assignmentBean = factory.createProfileAssignmentBean();
        ProfileAssignment assignment = assignmentBean.getProfileAssignmentByID(assignmentID);
        request.setAttribute("profileAssignment", assignment);
        return mapping.findForward("schedule");
    } catch (Exception e) {
      throw e;
    } finally {
      
    }
  }

  public ActionForward view(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    return mapping.findForward("cancel");
  }

  public ActionForward cancelled(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    String deviceID = request.getParameter("deviceID");
    if (StringUtils.isNotEmpty(deviceID)) {
       request.setAttribute("deviceID", deviceID);
    }
    return mapping.findForward("cancel");
  }  
  
  public ActionForward unspecified(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    return this.schedule(mapping, rawForm, request, response);
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
        DeviceBean deviceBean = factory.createDeviceBean();
        ProfileAssignmentBean assignmentBean = factory.createProfileAssignmentBean();
        
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
        // Get target Device
        Device device = deviceBean.getDeviceByID(form.getString("deviceID"));
        
        // Get Assignment whihc will be remove
        String assignmentID = form.getString("ID");
        ProfileAssignment assignment = assignmentBean.getProfileAssignmentByID(assignmentID);
        String profileRootNodePath = assignment.getProfileRootNodePath();
        
        // Generate scripts for delete
        String scripts = "<Script><Delete><Target>" + profileRootNodePath + "</Target></Delete></Script>";
        
        if (StringUtils.isNotEmpty(profileRootNodePath)) {
           ProvisionJobBean jobBean = factory.createProvisionJobBean();
           // Submit Jobs
           factory.beginTransaction();
  
           // Submit Job for delete
           ProvisionJob job = jobBean.newJob4Command(device, scripts);
           job.setName(form.getString("name"));
           job.setDescription(form.getString("description"));
           job.setScheduledTime(scheduledTime);
           job.setUiMode(uiMode);
           job.setRequiredNotification(sendNotification);
           jobBean.update(job);
  
           // Submit job for discovery.
           String parentPath = ".";
           if (profileRootNodePath.lastIndexOf('/') > 0) {
              parentPath = profileRootNodePath.substring(0, profileRootNodePath.lastIndexOf('/'));
           }
           ProvisionJob job4Discovery = jobBean.newJob4Discovery(device, new String[]{parentPath});
           job4Discovery.setName(form.getString("name"));
           job4Discovery.setDescription(form.getString("description"));
           job4Discovery.setScheduledTime(scheduledTime);
           job4Discovery.setUiMode(uiMode);
           job4Discovery.setRequiredNotification(false);
           jobBean.update(job4Discovery);
          
           // Remove ProfileAssignment
           assignmentBean.deleteProfileAssignment(assignment);
           factory.commit();
           
           // Send Event to Job Event Listener
           JobEventListener eventListener = ActionHelper.getJobEventListener();
           eventListener.notify(new JobEvent(JobEventType.Create, job.getID()));

           // Pass parameter into page.
           request.setAttribute("device", device);
           request.setAttribute("provisionJob", job);
           return (mapping.findForward("success"));
        } else {
          return this.cancelled(mapping, rawForm, request, response);
        }
    } catch (Exception ex) {
      if (factory != null) {
         factory.rollback();
      }
      throw ex;
    } finally {
    }
  }

}
