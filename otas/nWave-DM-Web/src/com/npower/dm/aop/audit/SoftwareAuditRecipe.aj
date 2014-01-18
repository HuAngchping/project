/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/aop/audit/SoftwareAuditRecipe.aj,v 1.4 2009/02/20 10:11:11 chenlei Exp $
 * $Revision: 1.4 $
 * $Date: 2009/02/20 10:11:11 $
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
import com.npower.dm.audit.SoftwareAuditLogger;
import com.npower.dm.audit.action.SoftwareAction;
import com.npower.dm.core.Software;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SoftwareBean;
import com.npower.dm.security.DMWebPrincipal;
import com.npower.dm.server.engine.EngineConfig;

/**
 * @author chenlei
 * @version $Revision: 1.4 $ $Date: 2009/02/20 10:11:11 $
 */

public aspect SoftwareAuditRecipe extends BaseRecipe {

  private static Log  log         = LogFactory.getLog(SoftwareAuditRecipe.class);
  
  /**
   * Audit: view Software
   */
  pointcut viewSoftware(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
           : execution(ActionForward com.npower.dm.action.software.SoftwareAction.view(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
             && args(mapping, rawForm, request, response)
             && this(action);
  
  
  /**
   * Audit: delete Software
   */
  pointcut removeSoftware(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
           : execution(ActionForward com.npower.dm.action.software.SoftwareAction.remove(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
             && args(mapping, rawForm, request, response)
             && this(action);
  
  
  /**
   * Audit: create Software
   */
  pointcut createSoftware(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
           : execution(ActionForward com.npower.dm.action.software.SavaSoftwareAction.execute(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
             && args(mapping, rawForm, request, response)
             && this(action);
  /**
   * @param action
   * @param mapping
   * @param rawForm
   * @param request
   * @param response
   */
  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
  returning(ActionForward forward)
       : viewSoftware(action, mapping, rawForm, request, response) && !within(SecurityAuditRecipe +) {
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
         Software software = (Software)request.getAttribute("software");
         if (software == null) {
           throw new NullPointerException("Could not load software data from attributes of HttpServletRequest!");
         }
         AuditLoggerFactory factory = this.getAuditLogFactory();
         SoftwareAuditLogger logger = factory.getSoftwareLog();
         logger.log(SoftwareAction.View_Software, user.getUsername(), ipAddress, software.getName(), software.getVendor().getName(), software.getCategory().getName(), software.getDescription());
     } catch (Throwable e) {
       log.error("Aop failured!", e);
     }
  }
  
  /**
   * @param action
   * @param mapping
   * @param rawForm
   * @param request
   * @param response
   */
  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
  returning(ActionForward forward)
       : removeSoftware(action, mapping, rawForm, request, response) && !within(SecurityAuditRecipe +) {
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
      String softwareID = request.getParameter("id");
      if (softwareID == null) {
        throw new NullPointerException("Could not load software data from attributes of HttpServletRequest!");
      }
      AuditLoggerFactory factory = this.getAuditLogFactory();
      SoftwareAuditLogger logger = factory.getSoftwareLog();
      logger.log(SoftwareAction.Delete_Software, user.getUsername(), ipAddress, softwareID, null, null, null);
    } catch (Throwable e) {
      log.error("Aop failured!", e);
    }
  }
  
  
  /**
   * @param action
   * @param mapping
   * @param rawForm
   * @param request
   * @param response
   */
  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
  returning(ActionForward forward)
       : createSoftware(action, mapping, rawForm, request, response) && !within(SecurityAuditRecipe +) {
    ManagementBeanFactory mFactory = null;
    try {
      mFactory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
      HttpSession session = request.getSession(false);
      DynaActionForm softwareForm = (DynaActionForm) rawForm;
      if (session == null) {
        throw new NullPointerException("Could not load Http Session!");
      }
      DMWebPrincipal user = (DMWebPrincipal)session.getAttribute(Constants.ADMIN_USER_KEY);
      if (user == null) {
        throw new NullPointerException("Could not load user data from Http Session!");
      }
      String ipAddress = request.getRemoteAddr();
      SoftwareBean softwareBean = mFactory.createSoftwareBean();
      Software software = null;
      String id = softwareForm.getString("id");
      AuditLoggerFactory factory = this.getAuditLogFactory();
      SoftwareAuditLogger logger = factory.getSoftwareLog();
      if (StringUtils.isEmpty(id)) {
        //Create mode
        String softwareID = (String)request.getAttribute("softwareID");
        software = softwareBean.getSoftwareByID(Long.valueOf(softwareID));
        logger.log(SoftwareAction.Create_Software, user.getUsername(), ipAddress, software.getName(), software.getVendor().getName(), software.getCategory().getName(), software.getDescription());
      } else {
        //Edit mode
        software = softwareBean.getSoftwareByID(Long.valueOf(id));
        logger.log(SoftwareAction.Edit_Software, user.getUsername(), ipAddress, software.getName(), software.getVendor().getName(), software.getCategory().getName(), software.getDescription());
      }
    } catch (Throwable e) {
      log.error("Aop failured!", e);
    } finally {
      mFactory.release();
    }
  } 
}
