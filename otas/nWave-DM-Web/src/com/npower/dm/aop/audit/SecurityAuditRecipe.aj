/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/aop/audit/SecurityAuditRecipe.aj,v 1.2 2009/02/13 10:33:15 zhao Exp $
  * $Revision: 1.2 $
  * $Date: 2009/02/13 10:33:15 $
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;

import com.npower.dm.action.Constants;
import com.npower.dm.audit.AuditLoggerFactory;
import com.npower.dm.audit.SecurityAuditLogger;
import com.npower.dm.audit.action.SecurityAction;
import com.npower.dm.security.DMWebPrincipal;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2009/02/13 10:33:15 $
 */
public aspect SecurityAuditRecipe extends BaseRecipe {
  
  private static Log  log         = LogFactory.getLog(SecurityAuditRecipe.class);
  
  /**
   * Audit: Login
   */
  pointcut login(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
           : execution(ActionForward com.npower.dm.action.LoginAction.execute(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
             && args(mapping, rawForm, request, response)
             && this(action);
  
  /**
   * Audit: Logout
   */
  pointcut logout(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
           : execution(ActionForward com.npower.dm.action.LogoutAction.execute(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
             && args(mapping, rawForm, request, response)
             && this(action);
  
  /**
   * Audit: Login
   */
  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) 
  returning(ActionForward forward) 
       : login(action, mapping, rawForm, request, response) && !within(SecurityAuditRecipe +) {
    if (forward == null || (forward.getName() != null && forward.getName().equalsIgnoreCase("success"))) {
       // Login success!
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
           AuditLoggerFactory factory = this.getAuditLogFactory();
           SecurityAuditLogger logger = factory.getSecurityLog();
           logger.log(SecurityAction.Login, user.getUsername(), ipAddress);
       } catch (Throwable e) {
         log.error("AOP failured!", e);
       }
    }
  }

  /**
   * Audit: Logout
   */
  before(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) 
       : logout(action, mapping, rawForm, request, response) && !within(SecurityAuditRecipe +) {
    // Logout
    try {
        HttpSession session = request.getSession(false);
        if (session != null) {
           DMWebPrincipal user = (DMWebPrincipal)session.getAttribute(Constants.ADMIN_USER_KEY);
           if (user != null) {
              String ipAddress = request.getRemoteAddr();
              AuditLoggerFactory factory = AuditLoggerFactory.newInstance();
              SecurityAuditLogger logger = factory.getSecurityLog();
              logger.log(SecurityAction.Logout, user.getUsername(), ipAddress);
           }
        }
    } catch (Throwable e) {
      log.error("AOP failured!", e);
    }
  }
}
