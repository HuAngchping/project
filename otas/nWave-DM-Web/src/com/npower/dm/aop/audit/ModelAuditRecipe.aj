/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/aop/audit/ModelAuditRecipe.aj,v 1.2 2007/03/22 11:04:06 zhao Exp $
  * $Revision: 1.2 $
  * $Date: 2007/03/22 11:04:06 $
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
import com.npower.dm.audit.ModelAuditLogger;
import com.npower.dm.audit.action.ModelAction;
import com.npower.dm.core.Model;
import com.npower.dm.security.DMWebPrincipal;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2007/03/22 11:04:06 $
 */
public aspect ModelAuditRecipe extends BaseRecipe {
  
  private static Log  log         = LogFactory.getLog(ModelAuditRecipe.class);
  
  pointcut createModel(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
            : execution(ActionForward com.npower.dm.action.model.SaveModelAction.create(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
              && args(mapping, rawForm, request, response)
              && this(action);

  /**
   * Audit: Create Model
   */
  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) 
  returning(ActionForward forward) 
       : createModel(action, mapping, rawForm, request, response) && !within(ModelAuditRecipe +) {
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
        
        // Load model
        Model model = (Model)request.getAttribute("model");
        if (model == null) {
           throw new NullPointerException("Could not load model from HttpServletRequest!");
        }
        
        // Log audit information
        AuditLoggerFactory factory = this.getAuditLogFactory();
        ModelAuditLogger logger = factory.getModelLog();
        logger.log(ModelAction.CREATE, user.getUsername(), ipAddress, model.getID(), model.getManufacturerModelId(), null);
    } catch (Throwable e) {
      log.error("AOP failured!", e);
    }
  }

  pointcut updateModel(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
            : execution(ActionForward com.npower.dm.action.model.SaveModelAction.update(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
              && args(mapping, rawForm, request, response)
              && this(action);

  /**
   * Audit: Update Model
   */
  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) 
  returning(ActionForward forward) 
       : updateModel(action, mapping, rawForm, request, response) && !within(ModelAuditRecipe +) {
    try {
        if (forward != null && forward.getName() != null && forward.getName().equals("cancel")) {
           // Do not audit, is cancel operation.
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
        
        // Load model
        Model model = (Model)request.getAttribute("model");
        if (model == null) {
           throw new NullPointerException("Could not load model from HttpServletRequest!");
        }
        
        // Log audit information
        AuditLoggerFactory factory = this.getAuditLogFactory();
        ModelAuditLogger logger = factory.getModelLog();
        logger.log(ModelAction.UPDATE, user.getUsername(), ipAddress, model.getID(), model.getManufacturerModelId(), null);
    } catch (Throwable e) {
      log.error("AOP failured!", e);
    }
  }
  
  pointcut deleteModel(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
            : execution(ActionForward com.npower.dm.action.model.RemoveModelAction.remove(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
              && args(mapping, rawForm, request, response)
              && this(action);

  /**
   * Audit: Delete Model
   */
  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) 
  returning(ActionForward forward) 
       : deleteModel(action, mapping, rawForm, request, response) && !within(ModelAuditRecipe +) {
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
        
        // Load model ID
        DynaActionForm modelForm = (DynaActionForm) rawForm;
        String modelIDString = (String) modelForm.get("ID");
        if (StringUtils.isEmpty(modelIDString)) {
           throw new NullPointerException("Could not load model ID from HttpServletRequest!");
        }
        long modelID = Long.parseLong(modelIDString);
        if (modelID == 0) {
           throw new NullPointerException("Could not load model ID from HttpServletRequest!");
        }
        
        // Log audit information
        AuditLoggerFactory factory = this.getAuditLogFactory();
        ModelAuditLogger logger = factory.getModelLog();
        logger.log(ModelAction.DELETE, user.getUsername(), ipAddress, modelID, null, null);
    } catch (Throwable e) {
      log.error("AOP failured!", e);
    }
  }
  
  
}
