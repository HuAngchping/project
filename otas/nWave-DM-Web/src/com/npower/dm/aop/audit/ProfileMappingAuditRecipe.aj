/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/aop/audit/ProfileMappingAuditRecipe.aj,v 1.2 2009/02/17 08:36:57 zhaowx Exp $
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.npower.dm.action.Constants;
import com.npower.dm.audit.AuditLoggerFactory;
import com.npower.dm.audit.ProfileMappingAuditLogger;
import com.npower.dm.audit.action.ProfileMappingAction;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileMapping;
import com.npower.dm.security.DMWebPrincipal;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2009/02/17 08:36:57 $
 */
public aspect ProfileMappingAuditRecipe extends BaseRecipe {
  
  private static Log  log         = LogFactory.getLog(ProfileMappingAuditRecipe.class);
  
  pointcut manageProfileMapping(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
            : execution(ActionForward com.npower.dm.action.profile.SaveProfileMappingAction.update(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
              && args(mapping, rawForm, request, response)
              && this(action);

  /**
   * Audit: Create ProfileMapping
   */
  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) 
  returning(ActionForward forward) 
       : manageProfileMapping(action, mapping, rawForm, request, response) && !within(ProfileMappingAuditRecipe +) {
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
        
        // Load profileMapping
        ProfileMapping profileMapping = (ProfileMapping)request.getAttribute("profileMapping");
        if (profileMapping == null) {
           throw new NullPointerException("Could not load ProfileMapping from HttpServletRequest!");
        }
        Model model = (Model)request.getAttribute("model");
        if (model == null) {
           throw new NullPointerException("Could not load Model from HttpServletRequest!");
        }

        // Log audit information
        AuditLoggerFactory factory = this.getAuditLogFactory();
        ProfileMappingAuditLogger logger = factory.getProfileMappingLog();
        logger.log(ProfileMappingAction.MANAGE, user.getUsername(), ipAddress, model.getID(), model.getManufacturerModelId(), profileMapping.getProfileTemplate().getID(), profileMapping.getProfileTemplate().getName(), null);
    } catch (Throwable e) {
      log.error("AOP failured!", e);
    }
  }

  pointcut deleteProfileMapping(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
            : execution(ActionForward com.npower.dm.action.profile.RemoveProfileMappingAction.execute(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
              && args(mapping, rawForm, request, response)
              && this(action);

  /**
   * Audit: Delete ProfileMapping
   */
  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) 
  returning(ActionForward forward) 
       : deleteProfileMapping(action, mapping, rawForm, request, response) && !within(ProfileMappingAuditRecipe +) {
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
        
        // Load profileMapping
        ProfileMapping profileMapping = (ProfileMapping)request.getAttribute("profileMapping");
        if (profileMapping == null) {
           throw new NullPointerException("Could not load ProfileMapping from HttpServletRequest!");
        }
        Model model = (Model)request.getAttribute("model");
        if (model == null) {
           throw new NullPointerException("Could not load Model from HttpServletRequest!");
        }

        // Log audit information
        AuditLoggerFactory factory = this.getAuditLogFactory();
        ProfileMappingAuditLogger logger = factory.getProfileMappingLog();
        logger.log(ProfileMappingAction.DELETE, user.getUsername(), ipAddress, model.getID(), model.getManufacturerModelId(), profileMapping.getProfileTemplate().getID(), profileMapping.getProfileTemplate().getName(), null);
    } catch (Throwable e) {
      log.error("AOP failured!", e);
    }
  }

  
}
