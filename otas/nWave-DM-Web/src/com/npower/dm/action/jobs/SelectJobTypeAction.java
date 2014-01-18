//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.npower.dm.action.jobs;


import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.DynaValidatorForm;

import com.npower.dm.action.BaseLookupDispatchAction;
import com.npower.dm.action.JobType;
import com.npower.dm.action.JobTypeDecorator;
import com.npower.dm.action.device.EditJobAction;

/** 
 * MyEclipse Struts
 * Creation date: 06-14-2006
 * 
 * XDoclet definition:
 * @struts.action path="/device/EditJob" name="DeviceJobTypeForm" scope="request"
 * @struts.action-forward name="jobTypeScript" path="/jsp/device/job/script.jsp" contextRelative="true"
 */
public class SelectJobTypeAction extends BaseLookupDispatchAction {

  // --------------------------------------------------------- Instance
  // Variables
  @SuppressWarnings("unused")
  private Pattern            delimiters = Pattern.compile("(?<!\\\\),");

  // --------------------------------------------------------- Methods
  @Override
  protected Map getKeyMethodMap() {
    Map<String, String> map = new HashMap<String, String>();
    map.put("page.button.next.label", "setTargetDevices");
    return(map);
  }

  private void loadJobTypes(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    MessageResources messageResources = getResources(request);
    request.setAttribute("jobTypes", JobTypeDecorator.getJobTypeOptions4Device(messageResources, this.getLocale(request)));
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
  public ActionForward unspecified(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    
    if (this.isCancelled(request)) {
       return this.cancelled(mapping, rawForm, request, response);
    }
    // Load the job types for select box
    loadJobTypes(mapping, rawForm, request, response);
    return mapping.findForward("SelectJobType");
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
  public ActionForward setTargetDevices(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    DynaValidatorForm form = (DynaValidatorForm)rawForm;
    String jobType = form.getString("jobType");
    
    MessageResources messageResources = getResources(request);
    HttpSession session = request.getSession();
    session.setAttribute(EditJobAction.JOB_TYPE_FOR_ASSIGN_PROFILE, 
                         JobTypeDecorator.decorate(messageResources, this.getLocale(request), JobType.valueOfByType(jobType)));

    // Validate input
    if (StringUtils.isEmpty(jobType)) {
      MessageResources resources = this.getResources(request);
      String label = resources.getMessage(this.getLocale(request), "device.job.jobType");
      ActionMessages messages = new ActionMessages();
      ActionMessage message = new ActionMessage("errors.required", label);
      messages.add("jobType", message);
      this.saveErrors(request, messages);
      
      loadJobTypes(mapping, rawForm, request, response);
      return mapping.findForward("SelectJobType");
    }
    return mapping.findForward("setTargetDevices");
  }
  
  /**
   * @param mapping
   * @param rawForm
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward cancelled(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    return (mapping.findForward("cancel"));
  }

}

