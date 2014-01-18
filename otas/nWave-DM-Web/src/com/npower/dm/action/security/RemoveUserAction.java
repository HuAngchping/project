/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/security/RemoveUserAction.java,v 1.1 2007/01/15 09:39:33 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2007/01/15 09:39:33 $
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

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.npower.dm.action.BaseDispatchAction;
import com.npower.dm.security.DMWebPrincipal;
import com.npower.dm.security.SecurityService;
import com.npower.dm.security.SecurityServiceFactory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/01/15 09:39:33 $
 */
public class RemoveUserAction extends BaseDispatchAction {

  /**
   * Method execute
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward remove(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) throws Exception {

    // Was this transaction cancelled?
    if (isCancelled(request)) {
      return (mapping.findForward("cancel"));
    }

    try {
        SecurityServiceFactory securityFactory = SecurityServiceFactory.newInstance();
        SecurityService service = securityFactory.getSecurityService(); 
        String username = request.getParameter("username");
        DMWebPrincipal user = service.get(username);
        if (user != null) {
           service.delete(user.getUsername());
        }
    } catch (Exception e) {
      throw e;
    } finally {
    }
    return (mapping.findForward("success"));
  }
}
