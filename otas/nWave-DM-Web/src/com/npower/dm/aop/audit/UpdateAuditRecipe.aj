/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/aop/audit/UpdateAuditRecipe.aj,v 1.1 2007/02/09 09:38:32 zhao Exp $
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.npower.dm.action.Constants;
import com.npower.dm.audit.AuditLoggerFactory;
import com.npower.dm.audit.UpdateAuditLogger;
import com.npower.dm.audit.action.UpdateAction;
import com.npower.dm.core.Update;
import com.npower.dm.security.DMWebPrincipal;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/02/09 09:38:32 $
 */
public aspect UpdateAuditRecipe extends BaseRecipe {
  
  private static Log  log         = LogFactory.getLog(UpdateAuditRecipe.class);
  
  pointcut createUpdate(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
            : execution(ActionForward com.npower.dm.action.model.ModelSaveFirmwareAction.create(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
              && args(mapping, rawForm, request, response)
              && this(action);

  /**
   * Audit: Create Update
   */
  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) 
  returning(ActionForward forward) 
       : createUpdate(action, mapping, rawForm, request, response) && !within(UpdateAuditRecipe +) {
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
        
        // Load update
        Update update = (Update)request.getAttribute("update");
        if (update == null) {
           throw new NullPointerException("Could not load update from HttpServletRequest!");
        }
        
        // Log audit information
        AuditLoggerFactory factory = this.getAuditLogFactory();
        UpdateAuditLogger logger = factory.getUpdateLog();
        logger.log(UpdateAction.CREATE, user.getUsername(), ipAddress, 
                   update.getFromImage().getModel().getID(),
                   update.getFromImage().getModel().getManufacturerModelId(),
                   update.getID(), null);
    } catch (Throwable e) {
      log.error("AOP failured!", e);
    }
  }

  pointcut updateUpdate(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
            : execution(ActionForward com.npower.dm.action.model.ModelSaveFirmwareAction.update(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
              && args(mapping, rawForm, request, response)
              && this(action);

  /**
   * Audit: Update Update
   */
  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) 
  returning(ActionForward forward) 
       : updateUpdate(action, mapping, rawForm, request, response) && !within(UpdateAuditRecipe +) {
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
        
        // Load update
        Update update = (Update)request.getAttribute("update");
        if (update == null) {
           throw new NullPointerException("Could not load update from HttpServletRequest!");
        }
        
        // Log audit information
        AuditLoggerFactory factory = this.getAuditLogFactory();
        UpdateAuditLogger logger = factory.getUpdateLog();
        logger.log(UpdateAction.EDIT, user.getUsername(), ipAddress, 
                   update.getFromImage().getModel().getID(),
                   update.getFromImage().getModel().getManufacturerModelId(),
                   update.getID(), null);
    } catch (Throwable e) {
      log.error("AOP failured!", e);
    }
  }
  
  pointcut deleteUpdate(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
            : execution(ActionForward com.npower.dm.action.model.ModelRemoveFirmwareAction.execute(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
              && args(mapping, rawForm, request, response)
              && this(action);

  /**
   * Audit: Delete Update
   */
  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) 
  returning(ActionForward forward) 
       : deleteUpdate(action, mapping, rawForm, request, response) && !within(UpdateAuditRecipe +) {
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
        
        // Load update
        Update update = (Update)request.getAttribute("update");
        if (update == null) {
           throw new NullPointerException("Could not load update from HttpServletRequest!");
        }
        
        // Log audit information
        AuditLoggerFactory factory = this.getAuditLogFactory();
        UpdateAuditLogger logger = factory.getUpdateLog();
        logger.log(UpdateAction.DELETE, user.getUsername(), ipAddress, 
                   update.getFromImage().getModel().getID(),
                   update.getFromImage().getModel().getManufacturerModelId(),
                   update.getID(), null);
    } catch (Throwable e) {
      log.error("AOP failured!", e);
    }
  }
  
  
}
