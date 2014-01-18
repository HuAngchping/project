/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/add/sgsn/SGSNDaemonPlugIn.java,v 1.9 2008/11/12 04:44:57 zhao Exp $
 * $Revision: 1.9 $
 * $Date: 2008/11/12 04:44:57 $
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
package com.npower.add.sgsn;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import com.npower.add.processor.Processor;
import com.npower.add.processor.Processor4Sgsn;
import com.npower.add.sgsn.dispatcher.JobDispatcher;
import com.npower.add.sgsn.dispatcher.JobDispatcherImpl;
import com.npower.add.sgsn.parser.DataItemParser;
import com.npower.add.sgsn.parser.DataItemParser4Sgsn;
import com.npower.common.plugins.AbstractDisablePlugIn;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.server.engine.EngineConfig;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.9 $ $Date: 2008/11/12 04:44:57 $
 */
public class SGSNDaemonPlugIn extends AbstractDisablePlugIn implements PlugIn {

  private static Log log = LogFactory.getLog(SGSNDaemonPlugIn.class);
  
  private String directory = "./sgsn";
  private int intervalInSeconds = 2;
  private boolean enable = true;
  private String carrierExternalID = null;
  private String profileConfigExternalID = null;
  private ManagementBeanFactory factory;
  private int sendIntervalInMinutes = 1;
  private Thread thread;
  private String successFlags = null;

  /**
   * 
   */
  public SGSNDaemonPlugIn() {
    super();
  }

  /**
   * @return the directory
   */
  public String getDirectory() {
    return directory;
  }

  /**
   * @param directory the directory to set
   */
  public void setDirectory(String directory) {
    this.directory = directory;
  }

  /**
   * @return the intervalInSeconds
   */
  public int getIntervalInSeconds() {
    return intervalInSeconds;
  }

  /**
   * @param intervalInSeconds the intervalInSeconds to set
   */
  public void setIntervalInSeconds(int intervalInSeconds) {
    this.intervalInSeconds = intervalInSeconds;
  }

  
  
  public String getCarrierExternalID() {
    return carrierExternalID;
  }

  public void setCarrierExternalID(String carrierExternalID) {
    this.carrierExternalID = carrierExternalID;
  }

  
  
  public String getProfileConfigExternalID() {
    return profileConfigExternalID;
  }

  public void setProfileConfigExternalID(String profileConfigExternalID) {
    this.profileConfigExternalID = profileConfigExternalID;
  }

  
  
  public int getSendIntervalInMinutes() {
    return sendIntervalInMinutes;
  }

  public void setSendIntervalInMinutes(int sendIntervalInMinutes) {
    this.sendIntervalInMinutes = sendIntervalInMinutes;
  }

  /**
   * @return the enable
   */
  public boolean isEnable() {
    return enable;
  }

  /**
   * @param enable the enable to set
   */
  public void setEnable(boolean enable) {
    this.enable = enable;
  }

  
  
  public String getSuccessFlags() {
    return successFlags;
  }

  public void setSuccessFlags(String successFlags) {
    this.successFlags = successFlags;
  }

  /* (non-Javadoc)
   * @see org.apache.struts.action.PlugIn#destroy()
   */
  public void destroy() {
    if (!this.enable) {
       return;
    }
    try {
        if (this.thread != null) {
           this.thread.interrupt();
        }
    } catch (Exception ex) {
      log.info("failure to destroy SGSN File Monitor Daemon", ex);
    } finally {
      if (this.factory != null) {
         this.factory.release();
      }
    }
  }

  public void init(ActionServlet arg0, ModuleConfig arg1) {
    if (this.isEnable()) {
       log.info("Starting SGSN File Monitor Daemon ...");
    } else {
      log.info("SGSN File Monitor Daemon Disabled!");
      return;
    }
    factory = null;
    try {
      factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
      // 提取对应的国家区号
      CarrierBean bean = factory.createCarrierBean();
      Carrier carrier = bean.getCarrierByExternalID(this.getCarrierExternalID());
      Country country = carrier.getCountry();
      DataItemParser parser = new DataItemParser4Sgsn(country.getCountryCode());
      String[] flags = StringUtils.split(this.getSuccessFlags(), ',');
      JobDispatcher dispatcher = new JobDispatcherImpl(factory, carrierExternalID, profileConfigExternalID, sendIntervalInMinutes, flags);
      Processor processor4Sgsn = new Processor4Sgsn(parser, dispatcher);

      FileMonitorDaemon4SGSN daemon = new FileMonitorDaemon4SGSN(directory, processor4Sgsn, this.getIntervalInSeconds());
      
      thread = new Thread(daemon);
      thread.start();

      log.info("SGSN File Monitor Daemon has been started.");
    } catch (Exception e) {
      log.error("failure to initialize " + this.getClass().getCanonicalName(), e);
    } finally {
    }
  }

}
