//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.npower.dm.action.jobs;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.npower.dm.action.ActionHelper;
import com.npower.dm.action.BaseDispatchAction;
import com.npower.dm.action.JobType;
import com.npower.dm.action.common.TargetDevicesCaculator;
import com.npower.dm.core.Software;
import com.npower.dm.core.Update;
import com.npower.dm.management.ManagementBeanFactory;

/** 
 * MyEclipse Struts
 * Creation date: 06-14-2006
 * 
 * XDoclet definition:
 * @struts.action path="/device/EditJob" name="DeviceJobTypeForm" scope="request"
 * @struts.action-forward name="jobTypeScript" path="/jsp/device/job/script.jsp" contextRelative="true"
 */
public class EditJobAction extends BaseDispatchAction {

  // --------------------------------------------------------- Instance

  // --------------------------------------------------------- Methods
  /**
   * Method execute
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward unspecified(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    
    return (mapping.findForward("SelectJobType"));
  }

  public ActionForward cancelled(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    return (mapping.findForward("cancel"));
  }

  public ActionForward doDiscovery(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    
    // Set job type for jsp view
    ActionHelper.setJobType(request, getResources(request), this.getLocale(request), JobType.DISCOVERY);

    // Load target devices
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    // Load a TargetDevicesCaculator
    TargetDevicesCaculator targetDevicesCaculator = SetTargetDevicesAction.createTargetDevicesCaculator(factory, rawForm);
    // Validate target devices.
    if (targetDevicesCaculator.isEmpty()) {
       ActionMessages messages = new ActionMessages();
       ActionMessage message = new ActionMessage("page.jobs.jobtypes.missingTargetDevices.message");
       messages.add("missingTargetDevices", message);
       this.saveErrors(request, messages);
       return mapping.getInputForward();
    }
    
    // Save caculator into Session
    BaseJobsAction.saveTargetDevicesCaculator(request, targetDevicesCaculator);
    
    // Set Target Devices into request attribute "targetDevices" for JSP
    BaseJobsAction.setTargetDevices(factory, request);

    return (mapping.findForward("EditDiscoveryNode"));
  }

  public ActionForward doScript(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    
    // Set job type for jsp view
    ActionHelper.setJobType(request, getResources(request), this.getLocale(request), JobType.SCRIPT);

    // Load target devices
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    // Load a TargetDevicesCaculator
    TargetDevicesCaculator targetDevicesCaculator = SetTargetDevicesAction.createTargetDevicesCaculator(factory, rawForm);
    // Validate target devices.
    if (targetDevicesCaculator.isEmpty()) {
       ActionMessages messages = new ActionMessages();
       ActionMessage message = new ActionMessage("page.jobs.jobtypes.missingTargetDevices.message");
       messages.add("missingTargetDevices", message);
       this.saveErrors(request, messages);
       return mapping.getInputForward();
    }
    
    // Save caculator into Session
    BaseJobsAction.saveTargetDevicesCaculator(request, targetDevicesCaculator);
    
    // Set Target Devices into request attribute "targetDevices" for JSP
    BaseJobsAction.setTargetDevices(factory, request);

    return (mapping.findForward("EditScript"));
  }  

  public ActionForward doAssignProfile4CP(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    // Set job type for jsp view
    ActionHelper.setJobType(request, getResources(request), this.getLocale(request), JobType.SEND_PROFILE);

    // Load target devices
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    // Load a TargetDevicesCaculator
    TargetDevicesCaculator targetDevicesCaculator = SetTargetDevicesAction.createTargetDevicesCaculator(factory, rawForm);
    // Validate target devices.
    if (targetDevicesCaculator.isEmpty()) {
       ActionMessages messages = new ActionMessages();
       ActionMessage message = new ActionMessage("page.jobs.jobtypes.missingTargetDevices.message");
       messages.add("missingTargetDevices", message);
       this.saveErrors(request, messages);
       return mapping.getInputForward();
    }
    
    // Save caculator into Session
    BaseJobsAction.saveTargetDevicesCaculator(request, targetDevicesCaculator);
    
    // Set Target Devices into request attribute "targetDevices" for JSP
    BaseJobsAction.setTargetDevices(factory, request);

    return (mapping.findForward("WizardAssignProfile4CP"));
  }  
  
  public ActionForward doAssignProfile4DM(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    // Set job type for jsp view
    ActionHelper.setJobType(request, getResources(request), this.getLocale(request), JobType.ASSIGN_PROFILE);

    // Load target devices
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    // Load a TargetDevicesCaculator
    TargetDevicesCaculator targetDevicesCaculator = SetTargetDevicesAction.createTargetDevicesCaculator(factory, rawForm);
    // Validate target devices.
    if (targetDevicesCaculator.isEmpty()) {
       ActionMessages messages = new ActionMessages();
       ActionMessage message = new ActionMessage("page.jobs.jobtypes.missingTargetDevices.message");
       messages.add("missingTargetDevices", message);
       this.saveErrors(request, messages);
       return mapping.getInputForward();
    }
    
    // Save caculator into Session
    BaseJobsAction.saveTargetDevicesCaculator(request, targetDevicesCaculator);
    
    // Set Target Devices into request attribute "targetDevices" for JSP
    BaseJobsAction.setTargetDevices(factory, request);

    return (mapping.findForward("WizardAssignProfile4DM"));
  }  
  
  public ActionForward doReAssignProfile(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    // Load target devices
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    // Load a TargetDevicesCaculator
    TargetDevicesCaculator targetDevicesCaculator = SetTargetDevicesAction.createTargetDevicesCaculator(factory, rawForm);
    // Validate target devices.
    if (targetDevicesCaculator.isEmpty()) {
       ActionMessages messages = new ActionMessages();
       ActionMessage message = new ActionMessage("page.jobs.jobtypes.missingTargetDevices.message");
       messages.add("missingTargetDevices", message);
       this.saveErrors(request, messages);
       return mapping.getInputForward();
    }
    
    // Save caculator into Session
    BaseJobsAction.saveTargetDevicesCaculator(request, targetDevicesCaculator);
    
    // Set Target Devices into request attribute "targetDevices" for JSP
    BaseJobsAction.setTargetDevices(factory, request);

    return (mapping.findForward("cancel"));
  }  
  
  public ActionForward doSoftwareInstall(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    // Set job type for jsp view
    ActionHelper.setJobType(request, getResources(request), this.getLocale(request), JobType.SOFTWARE_INSTALL);

    // Load target devices
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    // Load a TargetDevicesCaculator
    TargetDevicesCaculator targetDevicesCaculator = SetTargetDevicesAction.createTargetDevicesCaculator(factory, rawForm);
    // Validate target devices.
    if (targetDevicesCaculator.isEmpty()) {
       ActionMessages messages = new ActionMessages();
       ActionMessage message = new ActionMessage("page.jobs.jobtypes.missingTargetDevices.message");
       messages.add("missingTargetDevices", message);
       this.saveErrors(request, messages);
       return mapping.getInputForward();
    }
    
    // Save caculator into Session
    BaseJobsAction.saveTargetDevicesCaculator(request, targetDevicesCaculator);
    
    // Set Target Devices into request attribute "targetDevices" for JSP
    BaseJobsAction.setTargetDevices(factory, request);

    return (mapping.findForward("software.install.select.software"));
  }

  public ActionForward doSoftwareUnInstall(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    // Set job type for jsp view
    ActionHelper.setJobType(request, getResources(request), this.getLocale(request), JobType.SOFTWARE_UN_INSTALL);

    // Load target devices
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    // Load a TargetDevicesCaculator
    TargetDevicesCaculator targetDevicesCaculator = SetTargetDevicesAction.createTargetDevicesCaculator(factory, rawForm);
    // Validate target devices.
    if (targetDevicesCaculator.isEmpty()) {
       ActionMessages messages = new ActionMessages();
       ActionMessage message = new ActionMessage("page.jobs.jobtypes.missingTargetDevices.message");
       messages.add("missingTargetDevices", message);
       this.saveErrors(request, messages);
       return mapping.getInputForward();
    }
    
    // Save caculator into Session
    BaseJobsAction.saveTargetDevicesCaculator(request, targetDevicesCaculator);
    
    // Set Target Devices into request attribute "targetDevices" for JSP
    BaseJobsAction.setTargetDevices(factory, request);

    List<Software> softwares = SaveJob4SoftwareInstallAction.getSoftwares4Install(factory, request);
    request.setAttribute("softwares", softwares);
    return (mapping.findForward("software.uninstall.select.software"));
  }

  public ActionForward doSoftwareDiscovery(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    // Set job type for jsp view
    ActionHelper.setJobType(request, getResources(request), this.getLocale(request), JobType.SOFTWARE_DISCOVERY);

    // Load target devices
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    // Load a TargetDevicesCaculator
    TargetDevicesCaculator targetDevicesCaculator = SetTargetDevicesAction.createTargetDevicesCaculator(factory, rawForm);
    // Validate target devices.
    if (targetDevicesCaculator.isEmpty()) {
       ActionMessages messages = new ActionMessages();
       ActionMessage message = new ActionMessage("page.jobs.jobtypes.missingTargetDevices.message");
       messages.add("missingTargetDevices", message);
       this.saveErrors(request, messages);
       return mapping.getInputForward();
    }
    
    // Save caculator into Session
    BaseJobsAction.saveTargetDevicesCaculator(request, targetDevicesCaculator);
    
    // Set Target Devices into request attribute "targetDevices" for JSP
    BaseJobsAction.setTargetDevices(factory, request);

    return (mapping.findForward("software.discovery"));
  }

  public ActionForward doSoftwareActive(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    // Set job type for jsp view
    ActionHelper.setJobType(request, getResources(request), this.getLocale(request), JobType.SOFTWARE_ACTIVE);

    // Load target devices
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    // Load a TargetDevicesCaculator
    TargetDevicesCaculator targetDevicesCaculator = SetTargetDevicesAction.createTargetDevicesCaculator(factory, rawForm);
    // Validate target devices.
    if (targetDevicesCaculator.isEmpty()) {
       ActionMessages messages = new ActionMessages();
       ActionMessage message = new ActionMessage("page.jobs.jobtypes.missingTargetDevices.message");
       messages.add("missingTargetDevices", message);
       this.saveErrors(request, messages);
       return mapping.getInputForward();
    }
    
    // Save caculator into Session
    BaseJobsAction.saveTargetDevicesCaculator(request, targetDevicesCaculator);
    
    // Set Target Devices into request attribute "targetDevices" for JSP
    BaseJobsAction.setTargetDevices(factory, request);

    List<Software> softwares = SaveJob4SoftwareInstallAction.getSoftwares4Install(factory, request);
    request.setAttribute("softwares", softwares);
    return (mapping.findForward("software.active.select.software"));
  }

  public ActionForward doSoftwareDeactive(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    // Set job type for jsp view
    ActionHelper.setJobType(request, getResources(request), this.getLocale(request), JobType.SOFTWARE_DEACTIVE);

    // Load target devices
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    // Load a TargetDevicesCaculator
    TargetDevicesCaculator targetDevicesCaculator = SetTargetDevicesAction.createTargetDevicesCaculator(factory, rawForm);
    // Validate target devices.
    if (targetDevicesCaculator.isEmpty()) {
       ActionMessages messages = new ActionMessages();
       ActionMessage message = new ActionMessage("page.jobs.jobtypes.missingTargetDevices.message");
       messages.add("missingTargetDevices", message);
       this.saveErrors(request, messages);
       return mapping.getInputForward();
    }
    
    // Save caculator into Session
    BaseJobsAction.saveTargetDevicesCaculator(request, targetDevicesCaculator);
    
    // Set Target Devices into request attribute "targetDevices" for JSP
    BaseJobsAction.setTargetDevices(factory, request);

    List<Software> softwares = SaveJob4SoftwareInstallAction.getSoftwares4Install(factory, request);
    request.setAttribute("softwares", softwares);
    return (mapping.findForward("software.deactive.select.software"));
  }

  public ActionForward doSoftwareUpgrade(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    // Set job type for jsp view
    ActionHelper.setJobType(request, getResources(request), this.getLocale(request), JobType.SOFTWARE_UPGRADE);

    // Load target devices
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    // Load a TargetDevicesCaculator
    TargetDevicesCaculator targetDevicesCaculator = SetTargetDevicesAction.createTargetDevicesCaculator(factory, rawForm);
    // Validate target devices.
    if (targetDevicesCaculator.isEmpty()) {
       ActionMessages messages = new ActionMessages();
       ActionMessage message = new ActionMessage("page.jobs.jobtypes.missingTargetDevices.message");
       messages.add("missingTargetDevices", message);
       this.saveErrors(request, messages);
       return mapping.getInputForward();
    }
    
    // Save caculator into Session
    BaseJobsAction.saveTargetDevicesCaculator(request, targetDevicesCaculator);
    
    // Set Target Devices into request attribute "targetDevices" for JSP
    BaseJobsAction.setTargetDevices(factory, request);

    List<Software> softwares = SaveJob4SoftwareInstallAction.getSoftwares4Install(factory, request);
    request.setAttribute("softwares", softwares);
    return (mapping.findForward("software.upgrade.select.software"));
  }

  public ActionForward doFirmware(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    
    // Set job type for jsp view
    ActionHelper.setJobType(request, getResources(request), this.getLocale(request), JobType.FIRMWARE);

    // Load target devices
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    // Load a TargetDevicesCaculator
    TargetDevicesCaculator targetDevicesCaculator = SetTargetDevicesAction.createTargetDevicesCaculator(factory, rawForm);
    // Validate target devices.
    if (targetDevicesCaculator.isEmpty()) {
       ActionMessages messages = new ActionMessages();
       ActionMessage message = new ActionMessage("page.jobs.jobtypes.missingTargetDevices.message");
       messages.add("missingTargetDevices", message);
       this.saveErrors(request, messages);
       return mapping.getInputForward();
    }
    
    // Save caculator into Session
    BaseJobsAction.saveTargetDevicesCaculator(request, targetDevicesCaculator);
    
    // Set Target Devices into request attribute "targetDevices" for JSP
    BaseJobsAction.setTargetDevices(factory, request);

    List<Update> updates = SaveJob4FotaAction.getUpdates4Install(factory, request);
    request.setAttribute("updates", updates);
    return (mapping.findForward("fota.select.update"));
  }  
  
  /*
  public ActionForward doWorkflow(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    // Set job type for jsp view
    ActionHelper.setJobType(request, getResources(request), this.getLocale(request), JobType.WORKFLOW);
    
    // Load target devices
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    // Load a TargetDevicesCaculator
    TargetDevicesCaculator targetDevicesCaculator = SetTargetDevicesAction.createTargetDevicesCaculator(factory, rawForm);
    // Validate target devices.
    if (targetDevicesCaculator.isEmpty()) {
       ActionMessages messages = new ActionMessages();
       ActionMessage message = new ActionMessage("page.jobs.jobtypes.missingTargetDevices.message");
       messages.add("missingTargetDevices", message);
       this.saveErrors(request, messages);
       return mapping.getInputForward();
    }
    
    // Save caculator into Session
    BaseJobsAction.saveTargetDevicesCaculator(request, targetDevicesCaculator);
    
    // Set Target Devices into request attribute "targetDevices" for JSP
    BaseJobsAction.setTargetDevices(factory, request);

    return (mapping.findForward("cancel"));
  }  
  */
}

