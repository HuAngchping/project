/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/add/sgsn/dispatcher/SimpleJobDispatcher.java,v 1.3 2008/11/05 06:28:53 zhaowx Exp $
 * $Revision: 1.3 $
 * $Date: 2008/11/05 06:28:53 $
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
package com.npower.add.sgsn.dispatcher;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.add.sgsn.core.DataItem4sgsn;
import com.npower.dm.management.ManagementBeanFactory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/11/05 06:28:53 $
 */
public class SimpleJobDispatcher implements JobDispatcher {
  
  private static Log log = LogFactory.getLog(SimpleJobDispatcher.class);
  private ManagementBeanFactory factory = null;

  /**
   * 
   */
  public SimpleJobDispatcher() {
    super();
  }

  /* (non-Javadoc)
   * @see com.npower.sgsn.dispatcher.JobDispatcher#dispatch(com.npower.sgsn.core.DataItem)
   */
  public boolean dispatch(DataItem4sgsn item) throws Exception {
    log.info("Dispatcher:" + item.getLineNumber() + "imei[" + item.getImei() + "], [" + item.getMsisdn() + "]");
    return true;
  }

  public ManagementBeanFactory getFactory() {
    return factory;
  }

}
