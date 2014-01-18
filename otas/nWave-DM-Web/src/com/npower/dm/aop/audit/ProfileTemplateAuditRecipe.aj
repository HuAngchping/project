/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/aop/audit/ProfileTemplateAuditRecipe.aj,v 1.3 2009/02/19 10:35:03 zhaowx Exp $
  * $Revision: 1.3 $
  * $Date: 2009/02/19 10:35:03 $
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
import org.apache.struts.action.DynaActionForm;

import com.npower.dm.action.Constants;
import com.npower.dm.audit.AuditLoggerFactory;
import com.npower.dm.audit.ProfileTemplateAuditLogger;
import com.npower.dm.audit.action.ProfileTemplateAction;
import com.npower.dm.core.ProfileTemplate;
import com.npower.dm.security.DMWebPrincipal;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2009/02/19 10:35:03 $
 */
public aspect ProfileTemplateAuditRecipe extends BaseRecipe {
  
  private static Log  log         = LogFactory.getLog(ProfileTemplateAuditRecipe.class);
  
  pointcut createProfileTemplate(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
            : execution(ActionForward com.npower.dm.action.profile.SaveProfileTemplateAction.create(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
              && args(mapping, rawForm, request, response)
              && this(action);

  /**
   * Audit: Create ProfileTemplate
   */
  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) 
  returning(ActionForward forward) 
       : createProfileTemplate(action, mapping, rawForm, request, response) && !within(ProfileTemplateAuditRecipe +) {
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
        
        // Load profileTemplate
        ProfileTemplate profileTemplate = (ProfileTemplate)request.getAttribute("profileTemplate");
        if (profileTemplate == null) {
           throw new NullPointerException("Could not load ProfileTemplate from HttpServletRequest!");
        }

        // Log audit information
        AuditLoggerFactory factory = this.getAuditLogFactory();
        ProfileTemplateAuditLogger logger = factory.getProfileTemplateLog();
        logger.log(ProfileTemplateAction.CREATE, user.getUsername(), ipAddress, profileTemplate.getID(), profileTemplate.getName(), null);
    } catch (Throwable e) {
      log.error("AOP failured!", e);
    }
  }

  pointcut manageProfileTemplate(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
            : execution(ActionForward com.npower.dm.action.profile.SaveProfileTemplateAction.update(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
              && args(mapping, rawForm, request, response)
              && this(action);

  /**
   * Audit: Manage ProfileTemplate
   */
  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) 
  returning(ActionForward forward) 
       : manageProfileTemplate(action, mapping, rawForm, request, response) && !within(ProfileTemplateAuditRecipe +) {
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
        
        // Load profileTemplate
        ProfileTemplate profileTemplate = (ProfileTemplate)request.getAttribute("profileTemplate");
        if (profileTemplate == null) {
           throw new NullPointerException("Could not load ProfileTemplate from HttpServletRequest!");
        }

        // Log audit information
        AuditLoggerFactory factory = this.getAuditLogFactory();
        ProfileTemplateAuditLogger logger = factory.getProfileTemplateLog();
        logger.log(ProfileTemplateAction.MANAGE, user.getUsername(), ipAddress, profileTemplate.getID(), profileTemplate.getName(), null);
    } catch (Throwable e) {
      log.error("AOP failured!", e);
    }
  }

  pointcut deleteProfileTemplate(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
            : execution(ActionForward com.npower.dm.action.profile.RemoveProfileTemplateAction.execute(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
              && args(mapping, rawForm, request, response)
              && this(action);

  /**
   * Audit: Delete ProfileTemplate
   */
  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) 
  returning(ActionForward forward) 
       : deleteProfileTemplate(action, mapping, rawForm, request, response) && !within(ProfileTemplateAuditRecipe +) {
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
        
        // Load profileTemplate ID        
        DynaActionForm profileTemplateForm = (DynaActionForm) rawForm;
        String profileTemplateIDString = (String) profileTemplateForm.get("ID");
        if (StringUtils.isEmpty(profileTemplateIDString)) {
           throw new NullPointerException("Could not load profileTemplate ID from HttpServletRequest!");
        }
        long profileTemplateID = Long.parseLong(profileTemplateIDString);
        if (profileTemplateID == 0) {
           throw new NullPointerException("Could not load profileTemplate ID from HttpServletRequest!");
        }

        // Log audit information
        AuditLoggerFactory factory = this.getAuditLogFactory();
        ProfileTemplateAuditLogger logger = factory.getProfileTemplateLog();
        logger.log(ProfileTemplateAction.DELETE, user.getUsername(), ipAddress, profileTemplateID, null, null);
    } catch (Throwable e) {
      log.error("AOP failured!", e);
    }
  }

}
