/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/tags/PermissionTag.java,v 1.1 2008/04/07 05:16:35 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2008/04/07 05:16:35 $
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
package com.npower.dm.tags;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.dm.action.ActionHelper;
import com.npower.dm.security.DMWebPrincipal;
import com.npower.dm.security.SecurityService;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/04/07 05:16:35 $
 */
public class PermissionTag extends TagSupport {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private static Log                log    = LogFactory.getLog(PermissionTag.class);

  private String roles = null;
  
  private Set<String> setOfRoles = new HashSet<String>();

  /**
   * 
   */
  public PermissionTag() {
    super();
  }

  /**
   * @return
   */
  public String getRoles() {
    return roles;
  }

  /**
   * @param roles
   */
  public void setRoles(String roles) {
    this.roles = roles;
    this.setOfRoles = toSet(roles);
  }
  
  /**
   * @param roles
   * @return
   */
  private static Set<String> toSet(String roles) {
    Set<String> result = new HashSet<String>();
    if (StringUtils.isEmpty(roles)) {
       return result;
    }
    String[] list = StringUtils.split(roles, ',');
    if (list == null) {
       return result;
    }
    for (String s: list) {
        result.add(s.trim());
    }
    return result;
  }


  /* (non-Javadoc)
   * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
   */
  public int doStartTag() throws JspException {
    try {
      DMWebPrincipal principal = ActionHelper.getDMWebPrincipal((HttpServletRequest)this.pageContext.getRequest());
      if (principal == null) {
         return SKIP_BODY;
      }
      if (this.setOfRoles.contains(SecurityService.AUTHENTICATED_ROLE)) {
         return EVAL_BODY_INCLUDE;
      }
      for (String role: this.setOfRoles) {
          if (principal.hasRole(role)) {
            return EVAL_BODY_INCLUDE;
          }
      }
      return SKIP_BODY;
    } catch (Exception e) {
      log.error("Error in PermissionTag", e);
      throw new JspException(e);
    }
  }

}
