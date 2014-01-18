/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/servlet/StrutsMenuFilter.java,v 1.5 2008/02/22 07:40:46 zhao Exp $
  * $Revision: 1.5 $
  * $Date: 2008/02/22 07:40:46 $
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
package com.npower.servlet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.dm.security.RolesStrutsMenuPermissionsAdapter;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.5 $ $Date: 2008/02/22 07:40:46 $
 */
public class StrutsMenuFilter extends HttpServlet implements Filter {
  
  private Log log = LogFactory.getLog(StrutsMenuFilter.class);
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private FilterConfig filterConfig;

  //Handle the passed-in FilterConfig
  public void init(FilterConfig filterConfig) throws ServletException {
    this.filterConfig = filterConfig;
  }

  //Process the request/response pair
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) {
    try {
        // Install Struts Menus Adapter
        RolesStrutsMenuPermissionsAdapter applicationMenuPermission = new RolesStrutsMenuPermissionsAdapter((HttpServletRequest)request);
        request.setAttribute("applicationMenuPermission", applicationMenuPermission);
        filterChain.doFilter(request, response);
        
    } catch(ServletException sx) {
      filterConfig.getServletContext().log(sx.getMessage());
      log.error(sx.getMessage(), sx);
      log.error(sx.getMessage(), sx.getCause());
      log.error(sx.getMessage(), sx.getRootCause());
    } catch(Throwable ex) {
      filterConfig.getServletContext().log(ex.getMessage());
      log.error(ex.getMessage(), ex);
    }
  }

  //Clean up resources
  public void destroy() {
    super.destroy();
  }

}
