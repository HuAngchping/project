/**
  * $Header: /home/master/OTAS-DM-MyPortal/src/com/npower/dm/myportal/UserServiceDataFilter.java,v 1.1 2008/06/16 10:11:24 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2008/06/16 10:11:24 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2003 IEI Technology (China) Ltd.  All rights reserved.
  *
  * This SOURCE CODE FILE, which has been provided by IEI as part
  * of a IEI product for use ONLY by licensed users of the product,
  * includes CONFIDENTIAL and PROPRIETARY information of IEI.
  *
  * USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS
  * OF THE LICENSE STATEMENT AND LIMITED WARRANTY FURNISHED WITH
  * THE PRODUCT.
  *
  * IN PARTICULAR, YOU WILL INDEMNIFY AND HOLD IEI, ITS RELATED
  * COMPANIES AND ITS SUPPLIERS, HARMLESS FROM AND AGAINST ANY CLAIMS
  * OR LIABILITIES ARISING OUT OF THE USE, REPRODUCTION, OR DISTRIBUTION
  * OF YOUR PROGRAMS, INCLUDING ANY CLAIMS OR LIABILITIES ARISING OUT OF
  * OR RESULTING FROM THE USE, MODIFICATION, OR DISTRIBUTION OF PROGRAMS
  * OR FILES CREATED FROM, BASED ON, AND/OR DERIVED FROM THIS SOURCE
  * CODE FILE.
  * ===============================================================================================
  */

package com.npower.dm.myportal;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
  * @author $Author: zhao $
  * @version $Revision: 1.1 $
  */


public class UserServiceDataFilter extends HttpServlet implements Filter {
  
  private Log log = LogFactory.getLog(UserServiceDataFilter.class);
  
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
        if (request instanceof HttpServletRequest) {
           HttpServletRequest req = (HttpServletRequest)request;
           // Get User Service Data from HTTP Session
           UserServiceData serviceData = BaseWizardAction.getUserServiceData(req);
           String imei = req.getParameter("usd_imei");
           if (StringUtils.isNotEmpty(imei)) {
             serviceData.setImei(imei);
           }
           String msisdn = req.getParameter("usd_msisdn");
           if (StringUtils.isNotEmpty(msisdn)) {
             serviceData.setMsisdn(msisdn);
           }
        }
        filterChain.doFilter(request, response);
    } catch(ServletException sx) {
      filterConfig.getServletContext().log(sx.getMessage());
      log.error(sx.getMessage(), sx);
      log.error(sx.getMessage(), sx.getCause());
    } catch(Throwable ex) {
      filterConfig.getServletContext().log(ex.getMessage());
      log.error(ex.getMessage(), ex);
    } finally {
    }
  }

  //Clean up resources
  public void destroy() {
    super.destroy();
  }
}
