/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/security/SecurityServiceFactory.java,v 1.3 2007/04/12 02:05:20 LAH Exp $
  * $Revision: 1.3 $
  * $Date: 2007/04/12 02:05:20 $
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

import java.io.File;
import java.util.Properties;

import com.npower.dm.core.DMException;
import com.npower.dm.util.ConfigHelper;

/**
 * Factory of sercurity service.
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2007/04/12 02:05:20 $
 */
public class SecurityServiceFactory {

  private Properties properties;

  /**
   * Default constructor
   */
  private SecurityServiceFactory(Properties securityServiceProperties) {
    super();
    this.properties = securityServiceProperties;
  }

  /**
   * Create an instance of SecurityServiceFactory.
   * @param securityServiceProperties
   * @return
   */
  public static SecurityServiceFactory newInstance() throws DMException {
    return new SecurityServiceFactory(ConfigHelper.getSecurityProperties());
  }
  
  /**
   * Create an instance of SecurityServiceFactory.
   * @param securityServiceProperties
   * @return
   */
  public static SecurityServiceFactory newInstance(Properties securityServiceProperties) throws DMException {
    return new SecurityServiceFactory(securityServiceProperties);
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
   * Return an instance of Security Service.
   * @return
   *        Instance of Security Service.
   * @throws DMException
   */
  public SecurityService getSecurityService() throws DMException {
    String className = this.properties.getProperty("com.npower.dm.SecurityService.class");
    try {
        Class clazz = Class.forName(className);
        SecurityService service = (SecurityService)clazz.newInstance();
        // Set the base dir for User Database.
        if (service instanceof FileDBSecurityServiceImpl) {
           String home = System.getProperty("otas.dm.web", System.getProperty("otas.dm.home"));
           if (home != null) {
              File configDir = new File(home, "config/userdb");
              if (configDir.exists()) {
                 ((FileDBSecurityServiceImpl)service).setUserDatabaseDir(configDir.getAbsolutePath());
              }
           }
        }
        // Initialize the security service.
        service.setProperties(this.properties);
        service.initilize();
        return service;
    } catch (ClassNotFoundException e) {
      throw new DMException("Could not instance a security service.", e);
    } catch (InstantiationException e) {
      throw new DMException("Could not instance a security service.", e);
    } catch (IllegalAccessException e) {
      throw new DMException("Could not instance a security service.", e);
    }
  }

}
