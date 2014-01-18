/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/aop/audit/ProfileAuditRecipe.aj,v 1.3 2009/02/13 10:45:45 zhaowx Exp $
  * $Revision: 1.3 $
  * $Date: 2009/02/13 10:45:45 $
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
import com.npower.dm.audit.ProfileAuditLogger;
import com.npower.dm.audit.action.ProfileAction;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.security.DMWebPrincipal;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2009/02/13 10:45:45 $
 */
public aspect ProfileAuditRecipe extends BaseRecipe {
  
  private static Log  log         = LogFactory.getLog(ProfileAuditRecipe.class);
  
  pointcut createProfile(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
            : execution(ActionForward com.npower.dm.action.config.SaveProfileAction.create(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
              && args(mapping, rawForm, request, response)
              && this(action);

  /**
   * Audit: Create Profile
   */
  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) 
  returning(ActionForward forward) 
       : createProfile(action, mapping, rawForm, request, response) && !within(ProfileAuditRecipe +) {
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
        
        // Load profile
        ProfileConfig profile = (ProfileConfig)request.getAttribute("profile");
        if (profile == null) {
           throw new NullPointerException("Could not load profile from HttpServletRequest!");
        }
        
        // Log audit information
        AuditLoggerFactory factory = this.getAuditLogFactory();
        ProfileAuditLogger logger = factory.getProfileLog();
        logger.log(ProfileAction.CREATE, user.getUsername(), ipAddress, profile.getID(), profile.getName(), null);
    } catch (Throwable e) {
      log.error("AOP failured!", e);
    }
  }

  pointcut updateProfile(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
            : execution(ActionForward com.npower.dm.action.config.SaveProfileAction.update(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
              && args(mapping, rawForm, request, response)
              && this(action);

  /**
   * Audit: Update Profile
   */
  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) 
  returning(ActionForward forward) 
       : updateProfile(action, mapping, rawForm, request, response) && !within(ProfileAuditRecipe +) {
    try {
        if (forward == null || forward.getName() == null || !forward.getName().equals("success")) {
           return;
        }
        HttpSession session = request.getSession(false);
        if (session == null) {
           throw new NullPointerException("Could not load Http Session!");
        }
        DMWebPrincipal user = (DMWebPrincipal)session.getAttribute(Constants.ADMIN_USER_KEY);
        if (user == null) {
           throw new NullPointerException("Could not load user data from Http Session!");
        }
        String ipAddress = request.getRemoteAddr();
        
        // Load profile
        ProfileConfig profile = (ProfileConfig)request.getAttribute("profile");
        if (profile == null) {
           throw new NullPointerException("Could not load profile from HttpServletRequest!");
        }
        
        // Log audit information
        AuditLoggerFactory factory = this.getAuditLogFactory();
        ProfileAuditLogger logger = factory.getProfileLog();
        logger.log(ProfileAction.UPDATE, user.getUsername(), ipAddress, profile.getID(), profile.getName(), null);
    } catch (Throwable e) {
      log.error("AOP failured!", e);
    }
  }
  
  pointcut deleteProfile(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
            : execution(ActionForward com.npower.dm.action.config.RemoveProfileAction.execute(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
              && args(mapping, rawForm, request, response)
              && this(action);

  /**
   * Audit: Delete Profile
   */
  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) 
  returning(ActionForward forward) 
       : deleteProfile(action, mapping, rawForm, request, response) && !within(ProfileAuditRecipe +) {
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
        
        // Load profile ID
        DynaActionForm profileForm = (DynaActionForm) rawForm;
        String profileIDString = (String) profileForm.get("ID");
        if (StringUtils.isEmpty(profileIDString)) {
           throw new NullPointerException("Could not load profile ID from HttpServletRequest!");
        }
        long profileID = Long.parseLong(profileIDString);
        if (profileID == 0) {
           throw new NullPointerException("Could not load profile ID from HttpServletRequest!");
        }
        
        // Log audit information
        AuditLoggerFactory factory = this.getAuditLogFactory();
        ProfileAuditLogger logger = factory.getProfileLog();
        logger.log(ProfileAction.DELETE, user.getUsername(), ipAddress, profileID, null, null);
    } catch (Throwable e) {
      log.error("AOP failured!", e);
    }
  }
  
  pointcut viewProfile(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
  : execution(ActionForward com.npower.dm.action.config.ViewProfileAction.execute(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
    && args(mapping, rawForm, request, response)
    && this(action);
  
  /**
   * Audit: View Profile
   */
  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) 
  returning(ActionForward forward) 
       : viewProfile(action, mapping, rawForm, request, response) && !within(ProfileAuditRecipe +) {
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
        
        // Load profile
        ProfileConfig profile = (ProfileConfig)request.getAttribute("profile");
        if (profile == null) {
           throw new NullPointerException("Could not load profile from HttpServletRequest!");
        }
        
        // Log audit information
        AuditLoggerFactory factory = this.getAuditLogFactory();
        ProfileAuditLogger logger = factory.getProfileLog();
        logger.log(ProfileAction.View, user.getUsername(), ipAddress, profile.getID(), profile.getName(), null);
    } catch (Throwable e) {
      log.error("AOP failured!", e);
    }
  }
  
}
