/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/LoginAction.java,v 1.10 2009/02/13 10:33:15 zhao Exp $
  * $Revision: 1.10 $
  * $Date: 2009/02/13 10:33:15 $
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
package com.npower.dm.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.npower.dm.security.AuthenticationException;
import com.npower.dm.security.DMWebPrincipal;
import com.npower.dm.security.SecurityService;
import com.npower.dm.security.SecurityServiceFactory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.10 $ $Date: 2009/02/13 10:33:15 $
 */
public class LoginAction extends BaseAction {

  // --------------------------------------------------------- Methods

  public ActionForward execute(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {

    // Checking username and password
    HttpSession session = request.getSession();
    String username = (String) PropertyUtils.getSimpleProperty(rawForm, "username");
    String password = (String) PropertyUtils.getSimpleProperty(rawForm, "password");
    String returnURL = request.getParameter("returnRequestURL");
    
    SecurityServiceFactory securityFactory = SecurityServiceFactory.newInstance();
    SecurityService service = securityFactory.getSecurityService(); 
    try {
        DMWebPrincipal user = service.authenticate(username, password);
        if (user != null) {
           session.setAttribute(Constants.ADMIN_USER_KEY, user);
           if (StringUtils.isNotEmpty(returnURL)) {
              res.sendRedirect(returnURL);
              return null;
           }
           
           // Find first permitted menu, and get action, forward to that action.
           String menuName = "MainMenu";
           ActionForward forward = getDefaultMenuItem(request, menuName);
           if (forward == null) {
              // Forward control to this Action's success forward 
              forward = (mapping.findForward("success"));
           }
           return forward;
        } else {
          throw new AuthenticationException("Login failure!");
        }
    } catch (AuthenticationException e) {
      ActionMessages messages = new ActionMessages();
      ActionMessage message = new ActionMessage("page.login.failure.message");
      messages.add("login.failure", message);
      this.saveErrors(request, messages);
    }
    // Forward control to this Action's success forward 
    return (mapping.findForward("failure"));
  }

}
