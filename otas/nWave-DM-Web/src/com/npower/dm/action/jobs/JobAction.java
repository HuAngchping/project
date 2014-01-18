//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.npower.dm.action.jobs;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;

import com.npower.dm.action.BaseDispatchAction;
import com.npower.dm.core.DMException;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.hibernate.entity.ProvisionRequest;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProvisionJobBean;
import com.npower.dm.management.SearchBean;

/** 
 * MyEclipse Struts
 * Creation date: 06-19-2006
 * 
 * XDoclet definition:
 * @struts.action path="/jobs/JobView" name="jobs.JobViewForm" scope="request"
 */
public class JobAction extends BaseDispatchAction {

  // --------------------------------------------------------- Instance Variables

  // --------------------------------------------------------- Methods
  
  private ActionForward loadProvisionJob(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) throws DMException {
    DynaValidatorForm form = (DynaValidatorForm) rawForm;
    String jobID = form.getString("jobID");
    if (StringUtils.isNotEmpty(jobID)) {
       ManagementBeanFactory factory = this.getManagementBeanFactory(request);
       ProvisionJobBean jobBean = factory.createProvisionJobBean();
       ProvisionJob job = jobBean.loadJobByID(jobID);
       request.setAttribute("provisionJob", job);
       request.setAttribute("deviceJobStatus", job.getAllOfProvisionJobStatus());
    }
    return null;
  }


  /** 
   * Delete a job
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward removeJob(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) throws DMException {
    
    DynaValidatorForm form = (DynaValidatorForm) rawForm;
    String jobID = form.getString("jobID");
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    try {
      if (StringUtils.isNotEmpty(jobID)) {
         ProvisionJobBean jobBean = factory.createProvisionJobBean();
         ProvisionJob job = jobBean.loadJobByID(jobID);
         if (job != null) {
            factory.beginTransaction();
            jobBean.delete(job.getID());
            factory.commit();
         }
      }
    } catch (Exception e) {
      if (factory != null) {
         factory.rollback();
      }
    } finally {
      
    }
    
    return this.getActionForward4JobListViewer(mapping, request);
  }

  /** 
   * Delete job
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward removeJobs(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) throws DMException {
    
    DynaValidatorForm form = (DynaValidatorForm) rawForm;
    String[] jobIDs = (String[])form.get("jobIDs");
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    try {
      ProvisionJobBean jobBean = factory.createProvisionJobBean();
      for (int i = 0; jobIDs != null && i < jobIDs.length; i++) {
          String jobID = jobIDs[i];
          ProvisionJob job = jobBean.loadJobByID(jobID);
          if (job != null) {
             factory.beginTransaction();
             jobBean.delete(job.getID());
             factory.commit();
          }
      }
    } catch (Exception e) {
      if (factory != null) {
         factory.rollback();
      }
    } finally {
      
    }
    return this.getActionForward4JobListViewer(mapping, request);
  }

  /** 
   * 删除所有Queue的Job
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward removeQueuedJobs(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) throws DMException {
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    try {
      ProvisionJobBean jobBean = factory.createProvisionJobBean();
      SearchBean searchBean = factory.createSearchBean();
      Criteria mainCrt = searchBean.newCriteriaInstance(ProvisionRequest.class);
      mainCrt.add(Expression.eq("state", ProvisionJob.JOB_STATE_APPLIED));
      List<ProvisionJob> jobs = mainCrt.list();
      factory.beginTransaction();
      for (ProvisionJob job: jobs) {
          jobBean.delete(job.getID());
      }
      factory.commit();
    } catch (Exception e) {
      if (factory != null) {
         factory.rollback();
      }
    } finally {
      
    }
    return this.getActionForward4JobListViewer(mapping, request);
  }

  /** 
   * Delete a job
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward disableJob(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) throws DMException {
    
    DynaValidatorForm form = (DynaValidatorForm) rawForm;
    String jobID = form.getString("jobID");
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    try {
      if (StringUtils.isNotEmpty(jobID)) {
         ProvisionJobBean jobBean = factory.createProvisionJobBean();
         ProvisionJob job = jobBean.loadJobByID(jobID);
         if (job != null) {
            factory.beginTransaction();
            jobBean.disable(job.getID());
            factory.commit();
            
            this.loadProvisionJob(mapping, rawForm, request, response);
         }
      }
    } catch (Exception e) {
      if (factory != null) {
         factory.rollback();
      }
    } finally {
      
    }
    
    return (mapping.findForward("view"));
  }

  /** 
   * Delete a job
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward disableJobs(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) throws DMException {
    
    DynaValidatorForm form = (DynaValidatorForm) rawForm;
    String[] jobIDs = (String[])form.get("jobIDs");
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    try {
      ProvisionJobBean jobBean = factory.createProvisionJobBean();
      for (int i = 0; jobIDs != null && i < jobIDs.length; i++) {
          String jobID = jobIDs[i];
          ProvisionJob job = jobBean.loadJobByID(jobID);
          if (job != null) {
             factory.beginTransaction();
             jobBean.disable(job.getID());
             factory.commit();
          }
      }
    } catch (Exception e) {
      if (factory != null) {
         factory.rollback();
      }
    } finally {
      
    }
    
    return this.getActionForward4JobListViewer(mapping, request);
  }

  /** 
   * 删除所有Queue的Job
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward disableQueuedJobs(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) throws DMException {
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    try {
      ProvisionJobBean jobBean = factory.createProvisionJobBean();
      SearchBean searchBean = factory.createSearchBean();
      Criteria mainCrt = searchBean.newCriteriaInstance(ProvisionRequest.class);
      mainCrt.add(Expression.eq("state", ProvisionJob.JOB_STATE_APPLIED));
      List<ProvisionJob> jobs = mainCrt.list();
      factory.beginTransaction();
      for (ProvisionJob job: jobs) {
          jobBean.disable(job.getID());
      }
      factory.commit();
    } catch (Exception e) {
      if (factory != null) {
         factory.rollback();
      }
    } finally {
      
    }
    return this.getActionForward4JobListViewer(mapping, request);
  }

  /** 
   * Delete a job
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward enableJob(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) throws DMException {
    
    DynaValidatorForm form = (DynaValidatorForm) rawForm;
    String jobID = form.getString("jobID");
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    try {
      if (StringUtils.isNotEmpty(jobID)) {
         ProvisionJobBean jobBean = factory.createProvisionJobBean();
         ProvisionJob job = jobBean.loadJobByID(jobID);
         if (job != null) {
            factory.beginTransaction();
            jobBean.enable(job.getID());
            factory.commit();

            this.loadProvisionJob(mapping, rawForm, request, response);
         }
      }
    } catch (Exception e) {
      if (factory != null) {
         factory.rollback();
      }
    } finally {
      
    }
    
    return (mapping.findForward("view"));
  }

  /** 
   * Delete a job
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward cancelJob(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) throws DMException {
    
    DynaValidatorForm form = (DynaValidatorForm) rawForm;
    String jobID = form.getString("jobID");
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    try {
      if (StringUtils.isNotEmpty(jobID)) {
         ProvisionJobBean jobBean = factory.createProvisionJobBean();
         ProvisionJob job = jobBean.loadJobByID(jobID);
         if (job != null) {
            factory.beginTransaction();
            jobBean.cancel(job.getID());
            factory.commit();

            this.loadProvisionJob(mapping, rawForm, request, response);
         }
      }
    } catch (Exception e) {
      if (factory != null) {
         factory.rollback();
      }
    } finally {
      
    }
    
    return (mapping.findForward("view"));
  }

  /** 
   * Delete a job
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward cancelled(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) throws DMException {
    ActionForward forward = getActionForward4JobListViewer(mapping, request);
    return forward;
  }


  /**
   * 返回当前需要返回的Job List的视图
   * @param mapping
   * @param request
   * @return
   */
  private ActionForward getActionForward4JobListViewer(ActionMapping mapping, HttpServletRequest request) {
    ActionForward forward = null;
    HttpSession session = request.getSession(false);
    String returnViewer = (String)session.getAttribute(BaseJobsAction.CURRENT_JOB_LIST_VIEWER_NAME);
    
    if (StringUtils.isNotEmpty(returnViewer)) {
       forward = mapping.findForward(returnViewer);
    }
    if (forward == null) {
       // Default
       forward = mapping.findForward("job.list.all");
    }
    return forward;
  }    
}

