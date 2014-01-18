/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/aop/audit/ProvisioningAuditRecipe.aj,v 1.2 2009/02/17 08:36:57 zhaowx Exp $
  * $Revision: 1.2 $
  * $Date: 2009/02/17 08:36:57 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2007 NPower Network Software Ltd.  All rights reserved.
  *
  * This SOURCE CODE FILE, which has been provided by NPower as part
  * of a NPower product for use ONLY by licensed users of the product,
  * includes CONFIDENTIAL and PROPRIETARY information of NPower.
  *
  * USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS
  * OF THE LICENSE STATEMENT AND LIMITED WARRANTY FURNISHED WITH
  * THE PRODUCT.
  *
  * IN PARTICULAR, YOU WILL INDEMNIFY AND HOLD NPower, ITS RELATED
  * COMPANIES AND ITS SUPPLIERS, HARMLESS FROM AND AGAINST ANY CLAIMS
  * OR LIABILITIES ARISING OUT OF THE USE, REPRODUCTION, OR DISTRIBUTION
  * OF YOUR PROGRAMS, INCLUDING ANY CLAIMS OR LIABILITIES ARISING OUT OF
  * OR RESULTING FROM THE USE, MODIFICATION, OR DISTRIBUTION OF PROGRAMS
  * OR FILES CREATED FROM, BASED ON, AND/OR DERIVED FROM THIS SOURCE
  * CODE FILE.
  * ===============================================================================================
  */
package com.npower.dm.aop.audit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import com.npower.dm.action.Constants;
import com.npower.dm.audit.AuditLoggerFactory;
import com.npower.dm.audit.ProvisioningAuditLogger;
import com.npower.dm.audit.action.ProvisioningAction;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.security.DMWebPrincipal;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2009/02/17 08:36:57 $
 */
public aspect ProvisioningAuditRecipe extends BaseRecipe {
  
  private static Log  log         = LogFactory.getLog(ProvisioningAuditRecipe.class);
  
  private void auditCreateJob(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) {
    try {
        HttpSession session = request.getSession(false);
        if (session == null) {
           throw new NullPointerException("Could not load Http Session!");
        }
        DMWebPrincipal user = (DMWebPrincipal)session.getAttribute(Constants.ADMIN_USER_KEY);
        if (user == null) {
           throw new NullPointerException("Could not load user data from Http Session!");
        }
        String ipAddress = request.getRemoteAddr();
        
        // Load job
        ProvisionJob job = (ProvisionJob)request.getAttribute("provisionJob");
        if (job == null) {
           throw new NullPointerException("Could not load job from HttpServletRequest!");
        }
        
        // Log audit information
        AuditLoggerFactory factory = this.getAuditLogFactory();
        ProvisioningAuditLogger logger = factory.getProvisioningLog();
        logger.log(ProvisioningAction.CREATE, user.getUsername(), ipAddress, 
                   job.getID(), null);
    } catch (Throwable e) {
      log.error("AOP failured!", e);
    }
  }
  
  /**
   * Audit: Create Provisioning
   */
  pointcut createProvisioning4Discovery(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
            : execution(ActionForward com.npower.dm.action.jobs.SaveJobDiscoveryAction.update(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
              && args(mapping, rawForm, request, response)
              && this(action);

  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) 
  returning(ActionForward forward) 
       : createProvisioning4Discovery(action, mapping, rawForm, request, response) && !within(ProvisioningAuditRecipe +) {
    this.auditCreateJob(action, mapping, rawForm, request, response);
  }

  /**
   * Audit: Create Provisioning
   */
  pointcut createProvisioning4CommandScript(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
            : execution(ActionForward com.npower.dm.action.jobs.SaveJobScriptAction.update(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
              && args(mapping, rawForm, request, response)
              && this(action);

  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) 
  returning(ActionForward forward) 
       : createProvisioning4CommandScript(action, mapping, rawForm, request, response) && !within(ProvisioningAuditRecipe +) {
    this.auditCreateJob(action, mapping, rawForm, request, response);
  }

  /**
   * Audit: Create Provisioning
   */
  pointcut createProvisioning4ProfileAssignment(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
            : execution(ActionForward com.npower.dm.action.jobs.SaveProfileAssignment4DMAction.update(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
              && args(mapping, rawForm, request, response)
              && this(action);

  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) 
  returning(ActionForward forward) 
       : createProvisioning4ProfileAssignment(action, mapping, rawForm, request, response) && !within(ProvisioningAuditRecipe +) {
    this.auditCreateJob(action, mapping, rawForm, request, response);
  }
  
  /**
   * Audit: Disable Provisioning
   */
  pointcut disableProvisioning(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
            : execution(ActionForward com.npower.dm.action.jobs.JobAction.disableJob(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
              && args(mapping, rawForm, request, response)
              && this(action);

  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) 
  returning(ActionForward forward) 
       : disableProvisioning(action, mapping, rawForm, request, response) && !within(ProvisioningAuditRecipe +) {
    try {
        HttpSession session = request.getSession(false);
        if (session == null) {
           throw new NullPointerException("Could not load Http Session!");
        }
        DMWebPrincipal user = (DMWebPrincipal)session.getAttribute(Constants.ADMIN_USER_KEY);
        if (user == null) {
           throw new NullPointerException("Could not load user data from Http Session!");
        }
        String ipAddress = request.getRemoteAddr();
        
        DynaValidatorForm form = (DynaValidatorForm) rawForm;
        String jobIDString = form.getString("jobID");
        if (StringUtils.isEmpty(jobIDString)) {
           throw new NullPointerException("Could not load jobID from Http Request!");
        }
        long jobID = Long.parseLong(jobIDString);
        
        // Log audit information
        AuditLoggerFactory factory = this.getAuditLogFactory();
        ProvisioningAuditLogger logger = factory.getProvisioningLog();
        logger.log(ProvisioningAction.DISABLE, user.getUsername(), ipAddress, jobID, null);
    } catch (Throwable e) {
      log.error("AOP failured!", e);
    }
  }
  
  /**
   * Audit: Enable Provisioning
   */
  pointcut enableProvisioning(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
            : execution(ActionForward com.npower.dm.action.jobs.JobAction.enableJob(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
              && args(mapping, rawForm, request, response)
              && this(action);

  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) 
  returning(ActionForward forward) 
       : enableProvisioning(action, mapping, rawForm, request, response) && !within(ProvisioningAuditRecipe +) {
    try {
        HttpSession session = request.getSession(false);
        if (session == null) {
           throw new NullPointerException("Could not load Http Session!");
        }
        DMWebPrincipal user = (DMWebPrincipal)session.getAttribute(Constants.ADMIN_USER_KEY);
        if (user == null) {
           throw new NullPointerException("Could not load user data from Http Session!");
        }
        String ipAddress = request.getRemoteAddr();
        
        DynaValidatorForm form = (DynaValidatorForm) rawForm;
        String jobIDString = form.getString("jobID");
        if (StringUtils.isEmpty(jobIDString)) {
           throw new NullPointerException("Could not load jobID from Http Request!");
        }
        long jobID = Long.parseLong(jobIDString);
        
        // Log audit information
        AuditLoggerFactory factory = this.getAuditLogFactory();
        ProvisioningAuditLogger logger = factory.getProvisioningLog();
        logger.log(ProvisioningAction.ENABLE, user.getUsername(), ipAddress, jobID, null);
    } catch (Throwable e) {
      log.error("AOP failured!", e);
    }
  }

  /**
   * Audit: Delete Provisioning
   */
  pointcut deleteProvisioning(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
            : execution(ActionForward com.npower.dm.action.jobs.JobAction.removeJob(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
              && args(mapping, rawForm, request, response)
              && this(action);

  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) 
  returning(ActionForward forward) 
       : deleteProvisioning(action, mapping, rawForm, request, response) && !within(ProvisioningAuditRecipe +) {
    try {
        HttpSession session = request.getSession(false);
        if (session == null) {
           throw new NullPointerException("Could not load Http Session!");
        }
        DMWebPrincipal user = (DMWebPrincipal)session.getAttribute(Constants.ADMIN_USER_KEY);
        if (user == null) {
           throw new NullPointerException("Could not load user data from Http Session!");
        }
        String ipAddress = request.getRemoteAddr();
        
        // Load provisioning ID
        DynaValidatorForm form = (DynaValidatorForm) rawForm;
        String jobIDString = form.getString("jobID");
        if (StringUtils.isEmpty(jobIDString)) {
           throw new NullPointerException("Could not load jobID from Http Request!");
        }
        long jobID = Long.parseLong(jobIDString);
        
        // Log audit information
        AuditLoggerFactory factory = this.getAuditLogFactory();
        ProvisioningAuditLogger logger = factory.getProvisioningLog();
        logger.log(ProvisioningAction.DELETE, user.getUsername(), ipAddress, jobID, null);
    } catch (Throwable e) {
      log.error("AOP failured!", e);
    }
  }
  
  /**
   * Audit: Cancel Provisioning
   */
  pointcut cancelProvisioning(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
            : execution(ActionForward com.npower.dm.action.jobs.JobAction.cancelJob(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
              && args(mapping, rawForm, request, response)
              && this(action);

  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) 
  returning(ActionForward forward) 
       : cancelProvisioning(action, mapping, rawForm, request, response) && !within(ProvisioningAuditRecipe +) {
    try {
        HttpSession session = request.getSession(false);
        if (session == null) {
           throw new NullPointerException("Could not load Http Session!");
        }
        DMWebPrincipal user = (DMWebPrincipal)session.getAttribute(Constants.ADMIN_USER_KEY);
        if (user == null) {
           throw new NullPointerException("Could not load user data from Http Session!");
        }
        String ipAddress = request.getRemoteAddr();
        
        DynaValidatorForm form = (DynaValidatorForm) rawForm;
        String jobIDString = form.getString("jobID");
        if (StringUtils.isEmpty(jobIDString)) {
           throw new NullPointerException("Could not load jobID from Http Request!");
        }
        long jobID = Long.parseLong(jobIDString);
        
        // Log audit information
        AuditLoggerFactory factory = this.getAuditLogFactory();
        ProvisioningAuditLogger logger = factory.getProvisioningLog();
        logger.log(ProvisioningAction.CANCEL, user.getUsername(), ipAddress, jobID, null);
    } catch (Throwable e) {
      log.error("AOP failured!", e);
    }
  }
  
}
