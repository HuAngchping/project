/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/util/ConfigHelper.java,v 1.6 2008/04/25 11:01:20 zhao Exp $
  * $Revision: 1.6 $
  * $Date: 2008/04/25 11:01:20 $
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
package com.npower.dm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.npower.dm.core.DMException;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.6 $ $Date: 2008/04/25 11:01:20 $
 */
public class ConfigHelper {

  /**
   * Sms gateway config filename
   */
  private static final String SECURITY_SERVICE_PROPERTIES = "otasdm/otasdm.properties";

  /**
   * Sms gateway config filename
   */
  private static final String SMS_GATEWAY_PROPERTIES = "otasdm/otasdm.properties";

  /**
   * Global bootstrap profile filename
   */
  private static final String BOOTSTRAP_PROFILE_PROPERTIES = "otasdm/bootstrap.properties";

  /**
   * ClientProv Inventory config filename
   */
  private static final String CLIENT_PROV_INVENTORY_PROPERTIES = "otasdm/otasdm.properties";

  /**
   * Job Daemon config filename
   */
  private static final String JOB_DAEMON_PROPERTIES = "otasdm/otasdm.properties";

  /**
   * DM SOAP Client config filename
   */
  private static final String DM_SOAP_CLIENT_PROPERTIES = "otasdm/otasdm.properties";

  /**
   * Default constructor.
   */
  public ConfigHelper() {
    super();
  }

  // Private methods -----------------------------------------------------------------
  /**
   * @param filename
   * @return
   * @throws FileNotFoundException
   * @throws DMException
   * @throws IOException
   */
  private static Properties loadProperties(String filename) throws FileNotFoundException, DMException, IOException {
    InputStream ins = getPropertiesFile(filename);
    if (ins == null) {
       throw new DMException("Could not found config file: " + filename);
    }
    Properties props = new Properties(System.getProperties());
    props.load(ins);
    return props;
  }

  /**
   * @param filename
   * @return
   * @throws FileNotFoundException
   */
  private static InputStream getPropertiesFile(String filename) throws FileNotFoundException {
    String home = System.getProperty("otas.dm.web", System.getProperty("otas.dm.home"));
    InputStream ins = null;
    if (home != null) {
       File configDir = new File(home, "config");
       if (configDir.exists()) {
          File propFile = new File(configDir, filename);
          if (propFile.exists()) {
             ins = new FileInputStream(propFile);
          }
       }
    }
    if (ins == null) {
       ins = ConfigHelper.class.getClassLoader().getResourceAsStream(filename);
    }
    return ins;
  }
  
  // Public methods -----------------------------------------------------------------
  /**
   * Return properties for SMS Gateway
   * @return
   *        Properties of SMS Gateway
   * @throws DMException
   */
  public static Properties getSmsGatewayProperties() throws DMException {
    String filename = ConfigHelper.SMS_GATEWAY_PROPERTIES;
    try {
        Properties props = loadProperties(filename);
        return props;
    } catch (FileNotFoundException e) {
      throw new DMException("Failure in loading sms gateway config file: " + filename);
    } catch (IOException e) {
      throw new DMException("Failure in loading sms gateway config file: " + filename);
    }
  }

  /**
   * Return properties for Bootstrap
   * @return
   *        Properties of Bootstrap
   * @throws DMException
   */
  public static Properties getGlobalBootstrapProfile() throws DMException {
    String filename = ConfigHelper.BOOTSTRAP_PROFILE_PROPERTIES;
    try {
        Properties props = loadProperties(filename);
        return props;
    } catch (FileNotFoundException e) {
      throw new DMException("Failure in loading bootstrap config file: " + filename);
    } catch (IOException e) {
      throw new DMException("Failure in loading bootstrap config file: " + filename);
    }
  }

  /**
   * Return properties for CP template inventory.
   * @return
   *        Proeprties of CP template inventory.
   * @throws DMException
   */
  public static Properties getClientProvInventory() throws DMException {
    String filename = ConfigHelper.CLIENT_PROV_INVENTORY_PROPERTIES;
    try {
        Properties props = loadProperties(filename);
        return props;
    } catch (FileNotFoundException e) {
      throw new DMException("Failure in loading client prov inventory config file: " + filename);
    } catch (IOException e) {
      throw new DMException("Failure in loading client prov inveotry config file: " + filename);
    }
  }

  /**
   * Return properties for Security service
   * @return
   *        Properties of Security service.
   * @throws DMException
   */
  public static Properties getSecurityProperties() throws DMException {
    String filename = ConfigHelper.SECURITY_SERVICE_PROPERTIES;
    try {
        Properties props = loadProperties(filename);
        return props;
    } catch (FileNotFoundException e) {
      throw new DMException("Failure in loading security service config file: " + filename);
    } catch (IOException e) {
      throw new DMException("Failure in loading security service config file: " + filename);
    }
  }

  /**
   * Return properties for Job Daemon
   * @return
   *        Properties of Job Daemon.
   * @throws DMException
   */
  public static Properties getJobDaemonProperties() throws DMException {
    String filename = ConfigHelper.JOB_DAEMON_PROPERTIES;
    try {
        Properties props = loadProperties(filename);
        return props;
    } catch (FileNotFoundException e) {
      throw new DMException("Failure in loading job daemon config file: " + filename);
    } catch (IOException e) {
      throw new DMException("Failure in loading job daemon config file: " + filename);
    }
  }

  /**
   * Return properties for Job Daemon
   * @return
   *        Properties of Job Daemon.
   * @throws DMException
   */
  public static Properties getDMSoapClientProperties() throws DMException {
    String filename = ConfigHelper.DM_SOAP_CLIENT_PROPERTIES;
    try {
        Properties props = loadProperties(filename);
        return props;
    } catch (FileNotFoundException e) {
      throw new DMException("Failure in loading dm soap client config file: " + filename);
    } catch (IOException e) {
      throw new DMException("Failure in loading dm soap client config file: " + filename);
    }
  }

}
