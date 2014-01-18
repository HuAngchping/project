/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/PersistentManagerPlugIn.java,v 1.7 2008/06/05 10:34:36 zhao Exp $
  * $Revision: 1.7 $
  * $Date: 2008/06/05 10:34:36 $
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

import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import com.npower.dm.core.DMException;
import com.npower.dm.management.ManagementBeanFactory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.7 $ $Date: 2008/06/05 10:34:36 $
 */
public class PersistentManagerPlugIn implements PlugIn {

  /**
   * 
   */
  public PersistentManagerPlugIn() {
    super();
  }

  /* (non-Javadoc)
   * @see org.apache.struts.action.PlugIn#destroy()
   */
  public void destroy() {
  }

  /* (non-Javadoc)
   * @see org.apache.struts.action.PlugIn#init(org.apache.struts.action.ActionServlet, org.apache.struts.config.ModuleConfig)
   */
  public void init(ActionServlet arg0, ModuleConfig arg1) throws ServletException {
    try {
        // Fire-up Hibernate initializing.
        ManagementBeanFactory factory = PersistentManager.getManagementBeanFactory(null);
        // Fire-up WURFL initializing
        factory.createModelBean();
    } catch (DMException e) {
      throw new ServletException("Failure initialize the PersistentManager", e);
    } finally {
      // Release factory
      PersistentManager.releaseManagementBeanFactory(null);
    }
  }

}
