/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/SecurityRequestProcessor.java,v 1.9 2009/02/13 10:33:15 zhao Exp $
 * $Revision: 1.9 $
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

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.tiles.TilesRequestProcessor;

import com.npower.dm.security.DMWebPrincipal;
import com.npower.dm.security.SecurityService;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.9 $ $Date: 2009/02/13 10:33:15 $
 */
public class SecurityRequestProcessor extends TilesRequestProcessor {
  private static Log log = LogFactory.getLog(SecurityRequestProcessor.class);

  protected boolean processRoles(HttpServletRequest request, HttpServletResponse response, ActionMapping mapping)
      throws IOException, ServletException {
    String uri = request.getRequestURI();
    
    // Is this action protected by role requirements?
    String roles[] = mapping.getRoleNames();
    if ((roles == null) || (roles.length < 1)) {
       return true;
    }
    // Check the current user against the list of required roles
    HttpSession session = request.getSession(false);
    ActionMessages messages = new ActionMessages();
    if (session == null) {
       log.info("forbidden:" + uri);
       messages.add("login.failure.message", new ActionMessage("page.login.session.expired.message"));
       this.saveErrors(request, messages);
       this.setReturnURL(request);
       response.sendError(HttpServletResponse.SC_FORBIDDEN, getInternal().getMessage("notAuthenticated", mapping.getPath()));
       return false;
    }
    DMWebPrincipal user = (DMWebPrincipal) session.getAttribute(Constants.ADMIN_USER_KEY);
    if (user == null) {
       log.info("forbidden:" + uri);
       messages.add("login.failure.message", new ActionMessage("page.login.session.expired.message"));
       this.saveErrors(request, messages);
       this.setReturnURL(request);
       response.sendError(HttpServletResponse.SC_FORBIDDEN, getInternal().getMessage("notAuthenticated", mapping.getPath()));
       return false;
    }
    for (int i = 0; i < roles.length; i++) {
      if (roles[i].equalsIgnoreCase(SecurityService.AUTHENTICATED_ROLE) || user.hasRole(roles[i])) {
         return true;
      }
    }
    log.info("forbidden:" + uri + ":" + user.getUsername());
    this.setReturnURL(request);
    messages.add("login.failure.message", new ActionMessage("page.login.session.expired.message"));
    this.saveErrors(request, messages);
    response.sendError(HttpServletResponse.SC_FORBIDDEN, getInternal().getMessage("notAuthorized", mapping.getPath()));
    return (false);
  }

  /**
   * @param request
   */
  private void setReturnURL(HttpServletRequest request) {
    StringBuffer url = request.getRequestURL();
    String queryString = request.getQueryString();
    url.append('?').append((queryString != null)?queryString:"");
    if (log.isDebugEnabled()) {
       log.debug("session expired, login return url: " + url);
    }
    request.setAttribute("returnRequestURL", url);
  }

  /**
   * <p>Save the specified error messages keys into the appropriate request
   * attribute for use by the &lt;html:errors&gt; tag, if any messages
   * are required. Otherwise, ensure that the request attribute is not
   * created.</p>
   *
   * @param request The servlet request we are processing
   * @param errors Error messages object
   * @since Struts 1.2
   */
  private void saveErrors(HttpServletRequest request, ActionMessages errors) {

      // Remove any error messages attribute if none are required
      if ((errors == null) || errors.isEmpty()) {
          request.removeAttribute(Globals.ERROR_KEY);
          return;
      }

      // Save the error messages we need
      request.setAttribute(Globals.ERROR_KEY, errors);

  }
}
