/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/PersistentManager.java,v 1.9 2008/06/16 10:11:22 zhao Exp $
  * $Revision: 1.9 $
  * $Date: 2008/06/16 10:11:22 $
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

import com.npower.dm.core.DMException;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.server.engine.EngineConfig;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.9 $ $Date: 2008/06/16 10:11:22 $
 */
public class PersistentManager extends HttpServlet implements Filter  {
  
  private static Log log = LogFactory.getLog(PersistentManager.class);

  private FilterConfig filterConfig;

  /**
   * ThreadLocal hold the factory instance for current thread.
   */
  private static final ThreadLocal<ManagementBeanFactory> threadManagmentBeanFactory = new ThreadLocal<ManagementBeanFactory>();

  /**
   * Return the factory instance 
   * @return
   */
  public static ManagementBeanFactory getManagementBeanFactory(HttpServletRequest request) throws DMException {
    synchronized (threadManagmentBeanFactory) {
        ManagementBeanFactory factory = threadManagmentBeanFactory.get();
        if (factory == null) {
           factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
           threadManagmentBeanFactory.set(factory);
        }
        return factory;
    }
  }
  
  /**
   * Release the factory instance.
   *
   */
  static void releaseManagementBeanFactory(HttpServletRequest request) {
    ManagementBeanFactory factory = threadManagmentBeanFactory.get();
    if (factory != null) {
       factory.release();
    }
    threadManagmentBeanFactory.set(null);
  }


  //Handle the passed-in FilterConfig
  public void init(FilterConfig filterConfig) throws ServletException {
    this.filterConfig = filterConfig;
  }

  //Process the request/response pair
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) {
    try {
        
        filterChain.doFilter(request, response);
        
    } catch(ServletException sx) {
      filterConfig.getServletContext().log(sx.getMessage());
      sx.printStackTrace();
      sx.getCause().printStackTrace();
      log.error("Error in PersistentManager", sx);
    } catch(IOException iox) {
      filterConfig.getServletContext().log(iox.getMessage());
      iox.printStackTrace();
      log.error("Error in PersistentManager", iox);
    } catch(Throwable ex) {
      filterConfig.getServletContext().log(ex.getMessage());
      ex.printStackTrace();
      log.error("Error in PersistentManager", ex);
    } finally {
      // Release the factory
      releaseManagementBeanFactory((HttpServletRequest)request);
    }
  }

  //Clean up resources
  public void destroy() {
    super.destroy();
  }

}
