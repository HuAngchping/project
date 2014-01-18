/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/security/EditUserAction.java,v 1.3 2007/03/30 11:02:21 zhao Exp $
 * $Revision: 1.3 $
 * $Date: 2007/03/30 11:02:21 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2006 NPower Network Software Ltd.  All rights reserved.
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

package com.npower.dm.action.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.npower.dm.action.ActionHelper;
import com.npower.dm.action.BaseDispatchAction;
import com.npower.dm.core.DMException;
import com.npower.dm.security.DMWebPrincipal;
import com.npower.dm.security.SecurityService;
import com.npower.dm.security.SecurityServiceFactory;

/**
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/EditCarrier" name="CarrierForm" scope="request"
 * @struts.action-forward name="success" path="/jsp/carrier.jsp"
 *                        contextRelative="true"
 */
public class EditUserAction extends BaseDispatchAction {

  // --------------------------------------------------------- Instance
  // Variables

  // --------------------------------------------------------- Methods

  private DMWebPrincipal getUser(String username) throws DMException {
    SecurityServiceFactory securityFactory = SecurityServiceFactory.newInstance();
    SecurityService service = securityFactory.getSecurityService(); 
    DMWebPrincipal user = service.get(username);
    return user;
  }
  
  /**
   * Method execute
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward update(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    DynaActionForm form = (DynaActionForm) rawForm;
    try {
        DMWebPrincipal user = this.getUser(form.getString("username"));
        if (user != null) {
           form.getMap().putAll(BeanUtils.describe(user));
           form.set("formRoles", user.getRoles().toArray(new String[0]));
           form.set("formManufacturerExternalIDs", user.getOwnedManufacturerExternalIDs().toArray(new String[0]));
           form.set("formCarrierExternalIDs", user.getOwnedCarrierExternalIDs().toArray(new String[0]));
           
           request.setAttribute("user", user);
        }
        request.setAttribute("manufacturers", ActionHelper.getManufacturerExternalOptions(this.getManagementBeanFactory(request), request));
        request.setAttribute("carriers", ActionHelper.getCarrierExternalIDOptions(this.getManagementBeanFactory(request)));
        return (mapping.findForward("edit"));
    } catch (DMException e) {
      throw e;
    }
  }

  /**
   * Method execute
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward create(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
    return this.update(mapping, rawForm, request, response);
  }

  /**
   * Method execute
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward view(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
    DynaActionForm form = (DynaActionForm) rawForm;
    try {
        DMWebPrincipal user = this.getUser(form.getString("username"));
        form.getMap().putAll(BeanUtils.describe(user));
        request.setAttribute("user", user);
        return (mapping.findForward("view"));
    } catch (DMException e) {
      throw e;
    }
  }

  public ActionForward cancelled(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    return (mapping.findForward("cancel"));
  }  
  
  public ActionForward unspecified(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    return (mapping.findForward("cancel"));
  }  
}
