/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/aop/audit/SoftwareCategoryAuditRecipe.aj,v 1.1 2009/02/23 03:15:34 zhaowx Exp $
 * $Revision: 1.1 $
 * $Date: 2009/02/23 03:15:34 $
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
import com.npower.dm.audit.SoftwareCategoryAuditLogger;
import com.npower.dm.audit.action.SoftwareCategoryAction;
import com.npower.dm.core.SoftwareCategory;
import com.npower.dm.security.DMWebPrincipal;

/**
 * @author Zhao wanxiang
 * @version $Revision: 1.1 $ $Date: 2009/02/23 03:15:34 $
 */

public aspect SoftwareCategoryAuditRecipe extends BaseRecipe {
  
  private static Log  log         = LogFactory.getLog(SoftwareCategoryAuditRecipe.class);

  /**
   * Audit: create or update SoftwareCategory
   */
  pointcut createOrUpdateSoftwareCategory(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
           : execution(ActionForward com.npower.dm.action.software.CategorySavaAction.execute(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
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
       : createOrUpdateSoftwareCategory(action, mapping, rawForm, request, response) && !within(SoftwareCategoryAuditRecipe +) {
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
      
      SoftwareCategory category = (SoftwareCategory)request.getAttribute("category");
      
      if (category == null) {
        throw new NullPointerException("Could not load SoftwareCategory data from attributes of HttpServletRequest!");
      }
            
      DynaActionForm categoryForm = (DynaActionForm) rawForm;      
      String id = categoryForm.getString("id");
      SoftwareCategory parent = (SoftwareCategory)request.getAttribute("parent");

      AuditLoggerFactory factory = this.getAuditLogFactory();
      SoftwareCategoryAuditLogger logger = factory.getSoftwareCategoryLog();
      
      String parenName = (parent != null)?parent.getName():null;
      
      if (StringUtils.isEmpty(id)) {
         logger.log(SoftwareCategoryAction.Create,user.getName(), ipAddress, category.getId(), category.getName(),parenName, null);
      }else {
         logger.log(SoftwareCategoryAction.Edit,user.getName(), ipAddress, category.getId(), category.getName(),parenName, null);
      }
    } catch (Throwable e) {
      log.error("Aop failured!", e);
    }
  } 


  /**
   * Audit: delte SoftwareCategory
   */
  pointcut delteSoftwareCategory(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
           : execution(ActionForward com.npower.dm.action.software.CategoryAction.remove(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
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
       : delteSoftwareCategory(action, mapping, rawForm, request, response) && !within(SoftwareCategoryAuditRecipe +) {
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
      
      String id = request.getParameter("id");

      if (StringUtils.isEmpty(id)) {
        throw new NullPointerException("Could not load SoftwareCategory ID from HttpServletRequest!");
    
      }
      long softwareCategoryID = Long.parseLong(id);
     
      if (softwareCategoryID == 0) {
        throw new NullPointerException("Could not load SoftwareCategory ID from HttpServletRequest!");
      }
      
      SoftwareCategory parent = (SoftwareCategory)request.getAttribute("parent");
      String parenName = (parent != null)?parent.getName():null;
      
      // Log audit information
      AuditLoggerFactory factory = this.getAuditLogFactory();
      SoftwareCategoryAuditLogger logger = factory.getSoftwareCategoryLog();
      logger.log(SoftwareCategoryAction.Delete, user.getUsername(), ipAddress, softwareCategoryID, null, parenName,null);
    } catch (Throwable e) {
      log.error("AOP failured!", e);
    }
  } 

}
