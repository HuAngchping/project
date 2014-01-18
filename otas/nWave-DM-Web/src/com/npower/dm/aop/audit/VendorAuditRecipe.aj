/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/aop/audit/VendorAuditRecipe.aj,v 1.1 2009/02/23 09:48:19 hcp Exp $
 * $Revision: 1.1 $
 * $Date: 2009/02/23 09:48:19 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2009 NPower Network Software Ltd.  All rights reserved.
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
import com.npower.dm.audit.VendorAuditLogger;
import com.npower.dm.audit.action.VendorAction;
import com.npower.dm.core.SoftwareVendor;
import com.npower.dm.security.DMWebPrincipal;

/**
 * @author Huang ChunPing
 * @version $Revision: 1.1 $ ${date}7:33:50 AM$ com.npower.dm.aop.audit
 *          nWave-DM-Web VendorAuditRecipe.aj
 */
public aspect VendorAuditRecipe extends BaseRecipe {
  private static Log log = LogFactory.getLog(VendorAuditRecipe.class);

  pointcut create(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response)
            : execution(ActionForward com.npower.dm.action.software.SaveVendorAction.create(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
              && args(mapping, rawForm, request, response)
              && this(action);

  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) 
  returning(ActionForward forward) : create(action, mapping, rawForm, request, response) && !within(VendorAuditRecipe +) {
    try {
      HttpSession session = request.getSession(false);
      if (session == null) {
        throw new NullPointerException("Could not load Http Session!");
      }
      DMWebPrincipal user = (DMWebPrincipal) session.getAttribute(Constants.ADMIN_USER_KEY);
      if (user == null) {
        throw new NullPointerException("Could not load user data from Http Session!");
      }
      String ipAddress = request.getRemoteAddr();

      // Load softwareVendor
      SoftwareVendor vendor = (SoftwareVendor) request.getAttribute("vendor");
      if (vendor == null) {
        throw new NullPointerException("Could not load manufacturer from HttpServletRequest!");
      }

      // Log audit information
      AuditLoggerFactory factory = this.getAuditLogFactory();
      VendorAuditLogger logger = factory.getVendorLog();
      logger.log(VendorAction.CREATE, user.getUsername(), ipAddress, vendor.getId(), vendor.getName(), null);
    } catch (Throwable e) {
      log.error("AOP failured!", e);
    }
  }

  pointcut update(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response)
            : execution(ActionForward com.npower.dm.action.software.SaveVendorAction.update(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
              && args(mapping, rawForm, request, response)
              && this(action);

  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) 
  returning(ActionForward forward) : update(action, mapping, rawForm, request, response) && !within(VendorAuditRecipe +) {
    try {
      HttpSession session = request.getSession(false);
      if (session == null) {
        throw new NullPointerException("Could not load Http Session!");
      }
      DMWebPrincipal user = (DMWebPrincipal) session.getAttribute(Constants.ADMIN_USER_KEY);
      if (user == null) {
        throw new NullPointerException("Could not load user data from Http Session!");
      }
      String ipAddress = request.getRemoteAddr();

      // Load softwareVendor
      SoftwareVendor vendor = (SoftwareVendor) request.getAttribute("vendor");
      if (vendor == null) {
        throw new NullPointerException("Could not load manufacturer from HttpServletRequest!");
      }

      // Log audit information
      AuditLoggerFactory factory = this.getAuditLogFactory();
      VendorAuditLogger logger = factory.getVendorLog();
      logger.log(VendorAction.UPDATE, user.getUsername(), ipAddress, vendor.getId(), vendor.getName(), null);
    } catch (Throwable e) {
      log.error("AOP failured!", e);
    }
  }

  pointcut view(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response)
            : execution(ActionForward com.npower.dm.action.software.ViewVendorAction.execute(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
              && args(mapping, rawForm, request, response)
              && this(action);

  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) 
  returning(ActionForward forward) : view(action, mapping, rawForm, request, response) && !within(VendorAuditRecipe +) {
    try {
      HttpSession session = request.getSession(false);
      if (session == null) {
        throw new NullPointerException("Could not load Http Session!");
      }
      DMWebPrincipal user = (DMWebPrincipal) session.getAttribute(Constants.ADMIN_USER_KEY);
      if (user == null) {
        throw new NullPointerException("Could not load user data from Http Session!");
      }
      String ipAddress = request.getRemoteAddr();

      // Load softwareVendor
      SoftwareVendor vendor = (SoftwareVendor) request.getAttribute("vendor");
      if (vendor == null) {
        throw new NullPointerException("Could not load manufacturer from HttpServletRequest!");
      }

      // Log audit information
      AuditLoggerFactory factory = this.getAuditLogFactory();
      VendorAuditLogger logger = factory.getVendorLog();
      logger.log(VendorAction.VIEW, user.getUsername(), ipAddress, vendor.getId(), vendor.getName(), null);
    } catch (Throwable e) {
      log.error("AOP failured!", e);
    }
  }

  pointcut delete(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response)
            : execution(ActionForward com.npower.dm.action.software.RemoveVendorAction.remove(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
              && args(mapping, rawForm, request, response)
              && this(action);

  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) 
  returning(ActionForward forward) : delete(action, mapping, rawForm, request, response) && !within(VendorAuditRecipe +) {
    try {
      DynaActionForm vendorForm = (DynaActionForm) rawForm;
      HttpSession session = request.getSession(false);
      if (session == null) {
        throw new NullPointerException("Could not load Http Session!");
      }
      DMWebPrincipal user = (DMWebPrincipal) session.getAttribute(Constants.ADMIN_USER_KEY);
      if (user == null) {
        throw new NullPointerException("Could not load user data from Http Session!");
      }
      String ipAddress = request.getRemoteAddr();

      // Load softwareVendor id
      String vendorId = (String) vendorForm.get("id");
      if (StringUtils.isEmpty(vendorId)) {
        throw new NullPointerException("Could not load manufacturer from HttpServletRequest!");
      }

      // Log audit information
      AuditLoggerFactory factory = this.getAuditLogFactory();
      VendorAuditLogger logger = factory.getVendorLog();
      logger.log(VendorAction.DELETE, user.getUsername(), ipAddress, Long.parseLong(vendorId), null, null);
    } catch (Throwable e) {
      log.error("AOP failured!", e);
    }
  }
}
