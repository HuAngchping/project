/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/aop/audit/UserAuditRecipe.aj,v 1.1 2007/02/09 09:38:32 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2007/02/09 09:38:32 $
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

import com.npower.dm.action.Constants;
import com.npower.dm.audit.AuditLoggerFactory;
import com.npower.dm.audit.UserAuditLogger;
import com.npower.dm.audit.action.UserAction;
import com.npower.dm.security.DMWebPrincipal;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/02/09 09:38:32 $
 */
public aspect UserAuditRecipe extends BaseRecipe {
  
  private static Log  log         = LogFactory.getLog(UserAuditRecipe.class);
  
  pointcut createUser(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
            : execution(ActionForward com.npower.dm.action.security.SaveUserAction.create(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
              && args(mapping, rawForm, request, response)
              && this(action);

  /**
   * Audit: Create User
   */
  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) 
  returning(ActionForward forward) 
       : createUser(action, mapping, rawForm, request, response) && !within(UserAuditRecipe +) {
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
        
        // Load user
        String targetUser = request.getParameter("username");
        if (StringUtils.isEmpty(targetUser)) {
           throw new NullPointerException("Could not load user from HttpServletRequest!");
        }
        
        // Log audit information
        AuditLoggerFactory factory = this.getAuditLogFactory();
        UserAuditLogger logger = factory.getUserLog();
        logger.log(UserAction.CREATE, user.getUsername(), ipAddress, targetUser, null);
    } catch (Throwable e) {
      log.error("AOP failured!", e);
    }
  }

  pointcut updateUser(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
            : execution(ActionForward com.npower.dm.action.security.SaveUserAction.update(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
              && args(mapping, rawForm, request, response)
              && this(action);

  /**
   * Audit: Update User
   */
  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) 
  returning(ActionForward forward) 
       : updateUser(action, mapping, rawForm, request, response) && !within(UserAuditRecipe +) {
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
        
        // Load user
        String targetUser = request.getParameter("username");
        if (StringUtils.isEmpty(targetUser)) {
           throw new NullPointerException("Could not load user from HttpServletRequest!");
        }
        
        // Log audit information
        AuditLoggerFactory factory = this.getAuditLogFactory();
        UserAuditLogger logger = factory.getUserLog();
        logger.log(UserAction.MANAGE, user.getUsername(), ipAddress, targetUser, null);
    } catch (Throwable e) {
      log.error("AOP failured!", e);
    }
  }
  
  pointcut deleteUser(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
            : execution(ActionForward com.npower.dm.action.security.RemoveUserAction.remove(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
              && args(mapping, rawForm, request, response)
              && this(action);

  /**
   * Audit: Delete User
   */
  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) 
  returning(ActionForward forward) 
       : deleteUser(action, mapping, rawForm, request, response) && !within(UserAuditRecipe +) {
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
        
        // Load user
        String targetUser = request.getParameter("username");
        if (StringUtils.isEmpty(targetUser)) {
           throw new NullPointerException("Could not load user from HttpServletRequest!");
        }
        
        // Log audit information
        AuditLoggerFactory factory = this.getAuditLogFactory();
        UserAuditLogger logger = factory.getUserLog();
        logger.log(UserAction.DELETE, user.getUsername(), ipAddress, targetUser, null);
    } catch (Throwable e) {
      log.error("AOP failured!", e);
    }
  }
  
  
}
