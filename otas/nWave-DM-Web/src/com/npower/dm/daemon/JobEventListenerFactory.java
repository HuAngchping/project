/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/daemon/JobEventListenerFactory.java,v 1.1 2008/07/26 07:52:56 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/07/26 07:52:56 $
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
package com.npower.dm.daemon;

import java.util.Properties;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/07/26 07:52:56 $
 */
public class JobEventListenerFactory {
  
  private final static String PROPERTY_CLASS_NAME = "dm.daemon.job.event.listener.class";
  
  private Properties properties = System.getProperties();

  /**
   * 
   */
  protected JobEventListenerFactory() {
    super();
  }
  
  public JobEventListenerFactory(Properties properties) {
    super();
    this.properties = properties;
  }

  /**
   * @return the properties
   */
  public Properties getProperties() {
    return properties;
  }

  /**
   * @param properties the properties to set
   */
  public void setProperties(Properties properties) {
    this.properties = properties;
  }

  /**
   * @param properties
   * @return
   */
  public static JobEventListenerFactory newInstance(Properties properties) {
    return new JobEventListenerFactory(properties);
  }
  
  public JobEventListener getJobEventListener() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
    String className = this.getProperties().getProperty(PROPERTY_CLASS_NAME, SimpleJobEventListener.class.getName());
    Class<?> clazz = Class.forName(className);
    Object instance = clazz.newInstance();
    JobEventListener listener = (JobEventListener)instance;
    return listener;
  }

}
