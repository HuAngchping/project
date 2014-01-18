/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/security/RolesStrutsMenuPermissionsAdapter.java,v 1.3 2006/12/22 08:39:21 zhao Exp $
 * $Revision: 1.3 $
 * $Date: 2006/12/22 08:39:21 $
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
package com.npower.dm.security;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.navigator.menu.MenuComponent;
import net.sf.navigator.menu.PermissionsAdapter;

import com.npower.dm.action.Constants;

/**
 * This class used application-managed security to check access to menus. The
 * roles are set in menu-config.xml.
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2006/12/22 08:39:21 $
 */
public class RolesStrutsMenuPermissionsAdapter implements PermissionsAdapter  {

  private Pattern            delimiters = Pattern.compile("(?<!\\\\),");

  private HttpServletRequest request;

  public RolesStrutsMenuPermissionsAdapter(HttpServletRequest request) {
    this.request = request;
  }

  /**
   * If the menu is allowed, this should return true.
   * 
   * @return whether or not the menu is allowed.
   */
  public boolean isAllowed(MenuComponent menu) {
    if (menu.getRoles() == null) {
      return true; // no roles define, allow everyone
    } else {
      // Check the current user against the list of required roles
      HttpSession session = request.getSession(false);
      DMWebPrincipal user = (DMWebPrincipal) session.getAttribute(Constants.ADMIN_USER_KEY);
      if (user == null) {
         return false;
      }
      // Get the list of roles this menu allows
      String[] allowedRoles = delimiters.split(menu.getRoles());
      for (int i = 0; i < allowedRoles.length; i++) {
        if (user.hasRole(allowedRoles[i].trim())) {
          return true;
        }
      }
    }
    return false;
  }
}
