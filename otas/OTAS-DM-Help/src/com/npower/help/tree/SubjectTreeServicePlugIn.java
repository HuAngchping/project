/**
 * $Header: /home/master/OTAS-DM-Help/src/com/npower/help/tree/SubjectTreeServicePlugIn.java,v 1.2 2008/08/13 09:16:18 hcp Exp $
 * $Revision: 1.2 $
 * $Date: 2008/08/13 09:16:18 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2008 NPower Network Software Ltd.  All rights reserved.
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

package com.npower.help.tree;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

/**
 * @author Huang ChunPing
 * @version $Revision: 1.2 $ ${date}ÏÂÎç01:59:16$ com.npower.help.tree
 *          OTAS-DM-Help SubjectTreeServicePlugIn.java
 */
public class SubjectTreeServicePlugIn implements PlugIn, Runnable {

  private static Log          log                            = LogFactory.getLog(SubjectTreeServicePlugIn.class);

  private static final String ATTRIBUTE_SUBJECT_TREE_SERVICE = "ATTRIBUTE_SUBJECT_TREE_SERVICE";

  private SubjectTreeService  service;

  private Thread              thread;

  private long                expiredTime                    = 60 * 1000;

  /**
   * 
   */
  public SubjectTreeServicePlugIn() {
    super();
  }

  public static SubjectTreeService getSubjectTreeService(ServletContext servlet) {
    return (SubjectTreeService) servlet.getAttribute(ATTRIBUTE_SUBJECT_TREE_SERVICE);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.apache.struts.action.PlugIn#init(org.apache.struts.action.ActionServlet,
   *      org.apache.struts.config.ModuleConfig)
   */
  public void init(ActionServlet servlet, ModuleConfig arg1) throws ServletException {
    service = new SimpleSubjectTreeServiceImpl();
    service.reload();

    thread = new Thread(this);
    thread.start();

    servlet.getServletContext().setAttribute(ATTRIBUTE_SUBJECT_TREE_SERVICE, this.service);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.apache.struts.action.PlugIn#destroy()
   */
  public void destroy() {
    this.thread.interrupt();
  }

  public void run() {
    try {
      while (true) {
        service.reload();
        Thread.sleep(expiredTime);
        log.debug("Tree list one minute interval refresh");
      }
    } catch (InterruptedException e) {
      log.error("Failure to thread load subject tree.", e);
    }
  }

}
