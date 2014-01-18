/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/aop/audit/ManufacturerAuditRecipe.aj,v 1.2 2007/03/30 11:02:21 zhao Exp $
  * $Revision: 1.2 $
  * $Date: 2007/03/30 11:02:21 $
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
import com.npower.dm.audit.ManufacturerAuditLogger;
import com.npower.dm.audit.action.ManufacturerAction;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.security.DMWebPrincipal;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2007/03/30 11:02:21 $
 */
public aspect ManufacturerAuditRecipe extends BaseRecipe {
  
  private static Log  log         = LogFactory.getLog(ManufacturerAuditRecipe.class);
  
  pointcut createManufacturer(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
            : execution(ActionForward com.npower.dm.action.manufacturer.SaveManufacturerAction.create(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
              && args(mapping, rawForm, request, response)
              && this(action);

  /**
   * Audit: Create Manufacturer
   */
  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) 
  returning(ActionForward forward) 
       : createManufacturer(action, mapping, rawForm, request, response) && !within(ManufacturerAuditRecipe +) {
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
        
        // Load manufacturer
        Manufacturer manufacturer = (Manufacturer)request.getAttribute("manufacturer");
        if (manufacturer == null) {
           throw new NullPointerException("Could not load manufacturer from HttpServletRequest!");
        }
        
        // Log audit information
        AuditLoggerFactory factory = this.getAuditLogFactory();
        ManufacturerAuditLogger logger = factory.getManufacturerLog();
        logger.log(ManufacturerAction.CREATE, user.getUsername(), ipAddress, manufacturer.getID(), manufacturer.getExternalId(), null);
    } catch (Throwable e) {
      log.error("AOP failured!", e);
    }
  }

  pointcut updateManufacturer(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
            : execution(ActionForward com.npower.dm.action.manufacturer.SaveManufacturerAction.update(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
              && args(mapping, rawForm, request, response)
              && this(action);

  /**
   * Audit: Update Manufacturer
   */
  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) 
  returning(ActionForward forward) 
       : updateManufacturer(action, mapping, rawForm, request, response) && !within(ManufacturerAuditRecipe +) {
    try {
        if (forward == null || "cancel".equalsIgnoreCase(forward.getName())){ 
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
        
        // Load manufacturer
        Manufacturer manufacturer = (Manufacturer)request.getAttribute("manufacturer");
        if (manufacturer == null) {
           throw new NullPointerException("Could not load manufacturer from HttpServletRequest!");
        }
        
        // Log audit information
        AuditLoggerFactory factory = this.getAuditLogFactory();
        ManufacturerAuditLogger logger = factory.getManufacturerLog();
        logger.log(ManufacturerAction.UPDATE, user.getUsername(), ipAddress, manufacturer.getID(), manufacturer.getExternalId(), null);
    } catch (Throwable e) {
      log.error("AOP failured!", e);
    }
  }
  
  pointcut deleteManufacturer(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
            : execution(ActionForward com.npower.dm.action.manufacturer.RemoveManufacturerAction.remove(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
              && args(mapping, rawForm, request, response)
              && this(action);

  /**
   * Audit: Delete Manufacturer
   */
  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) 
  returning(ActionForward forward) 
       : deleteManufacturer(action, mapping, rawForm, request, response) && !within(ManufacturerAuditRecipe +) {
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
        
        // Load manufacturer ID
        DynaActionForm manufacturerForm = (DynaActionForm) rawForm;
        String manufacturerIDString = (String) manufacturerForm.get("ID");
        if (StringUtils.isEmpty(manufacturerIDString)) {
           throw new NullPointerException("Could not load manufacturer ID from HttpServletRequest!");
        }
        long manufacturerID = Long.parseLong(manufacturerIDString);
        if (manufacturerID == 0) {
           throw new NullPointerException("Could not load manufacturer ID from HttpServletRequest!");
        }
        
        // Log audit information
        AuditLoggerFactory factory = this.getAuditLogFactory();
        ManufacturerAuditLogger logger = factory.getManufacturerLog();
        logger.log(ManufacturerAction.DELETE, user.getUsername(), ipAddress, manufacturerID, null, null);
    } catch (Throwable e) {
      log.error("AOP failured!", e);
    }
  }
  
  
}
