//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.npower.dm.action.jobs;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import com.npower.dm.action.BaseAction;
import com.npower.dm.core.DMException;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProvisionJobBean;

/** 
 * MyEclipse Struts
 * Creation date: 06-19-2006
 * 
 * XDoclet definition:
 * @struts.action path="/jobs/JobView" name="jobs.JobViewForm" scope="request"
 */
public class JobViewAction extends BaseAction {

  // --------------------------------------------------------- Instance Variables

  // --------------------------------------------------------- Methods
  
  private static final Map<String, String> DM_FORWARD_MAPPING = new HashMap<String, String>();
  private static final Map<String, String> CP_FORWARD_MAPPING = new HashMap<String, String>();
  
  static {
    DM_FORWARD_MAPPING.put(ProvisionJob.JOB_TYPE_ASSIGN_PROFILE,    "view.dm.assign.profile");
    DM_FORWARD_MAPPING.put(ProvisionJob.JOB_TYPE_DELETE_PROFILE,    "view.dm.assign.profile");
    DM_FORWARD_MAPPING.put(ProvisionJob.JOB_TYPE_RE_ASSIGN_PROFILE, "view.dm.re_assign.profile");
    DM_FORWARD_MAPPING.put(ProvisionJob.JOB_TYPE_DISCOVERY,         "view.dm.discovery");
    DM_FORWARD_MAPPING.put(ProvisionJob.JOB_TYPE_SCRIPT,            "view.dm.command.script");
    DM_FORWARD_MAPPING.put(ProvisionJob.JOB_TYPE_FIRMWARE,          "view.dm.firmware.update");
    DM_FORWARD_MAPPING.put(ProvisionJob.JOB_TYPE_SOFTWARE_INSTALL,          "view.dm.software.install");
    DM_FORWARD_MAPPING.put(ProvisionJob.JOB_TYPE_SOFTWARE_INSTALL_STAGE_2,          "view.dm.software.install");
    DM_FORWARD_MAPPING.put(ProvisionJob.JOB_TYPE_SOFTWARE_UN_INSTALL,       "view.dm.software.uninstall");
    DM_FORWARD_MAPPING.put(ProvisionJob.JOB_TYPE_SOFTWARE_ACTIVE,           "view.dm.software.active");
    DM_FORWARD_MAPPING.put(ProvisionJob.JOB_TYPE_SOFTWARE_DEACTIVE,         "view.dm.software.deactive");
    DM_FORWARD_MAPPING.put(ProvisionJob.JOB_TYPE_SOFTWARE_UPGRADE,          "view.dm.software.upgrade");
    DM_FORWARD_MAPPING.put(ProvisionJob.JOB_TYPE_SOFTWARE_DISCOVERY,        "view.dm.software.discovery");
    
    CP_FORWARD_MAPPING.put(ProvisionJob.JOB_TYPE_ASSIGN_PROFILE,    "view.cp.assign.profile");
  }

  private ProvisionJob loadProvisionJob(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) throws DMException {
    DynaValidatorForm form = (DynaValidatorForm) rawForm;
    String jobID = form.getString("jobID");
    if (StringUtils.isNotEmpty(jobID)) {
       ManagementBeanFactory factory = this.getManagementBeanFactory(request);
       ProvisionJobBean jobBean = factory.createProvisionJobBean();
       ProvisionJob job = jobBean.loadJobByID(jobID);
       request.setAttribute("provisionJob", job);
       if (ProvisionJob.JOB_MODE_DM.equalsIgnoreCase(job.getJobMode())) {
          request.setAttribute("deviceJobStatus", job.getAllOfProvisionJobStatus());
       } else if (ProvisionJob.JOB_MODE_CP.equalsIgnoreCase(job.getJobMode())) {
         request.setAttribute("deviceJobStatus", job.getOtaTargetDevices());
       }
       return job;
    }
    return null;
  }

  /**
   * @param mapping
   * @param job
   * @return
   */
  private ActionForward findForward(ActionMapping mapping, ProvisionJob job) {
    String jobMode = job.getJobMode();
    if (ProvisionJob.JOB_MODE_DM.equalsIgnoreCase(jobMode)) {
       return mapping.findForward(DM_FORWARD_MAPPING.get(job.getJobType()));
    } else if (ProvisionJob.JOB_MODE_CP.equalsIgnoreCase(jobMode)) {
      return mapping.findForward(CP_FORWARD_MAPPING.get(job.getJobType()));
    } else {
      return null;
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
  public ActionForward execute(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) throws DMException {
    ProvisionJob job = this.loadProvisionJob(mapping, rawForm, request, response);
    
    ActionForward forward = this.findForward(mapping, job);
    return forward;
  }


}

