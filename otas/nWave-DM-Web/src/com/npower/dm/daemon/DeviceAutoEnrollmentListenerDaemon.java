/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/daemon/DeviceAutoEnrollmentListenerDaemon.java,v 1.4 2008/12/25 10:53:00 zhao Exp $
 * $Revision: 1.4 $
 * $Date: 2008/12/25 10:53:00 $
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

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.responder.AbstractResponderDaemon;
import com.npower.dm.server.engine.EngineConfig;
import com.npower.dm.tracking.DeviceChangeLogger;
import com.npower.dm.tracking.DeviceChangeLoggerFactory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2008/12/25 10:53:00 $
 */
public class DeviceAutoEnrollmentListenerDaemon extends AbstractResponderDaemon implements PlugIn, MessageListener {

  private static Log log = LogFactory.getLog(DeviceAutoEnrollmentListenerDaemon.class);
  
  private String defaultCarrierExtId = "ChinaMobile";
  private String defaultClientPassword = "otasdm";
  private String defaultClientUsername = "otasdm";
  private String defaultServerPassword = "otasdm";
  
  /**
   * 
   */
  public DeviceAutoEnrollmentListenerDaemon() {
    super();
  }

  /**
   * @return the defaultCarrierExtId
   */
  public String getDefaultCarrierExtId() {
    return defaultCarrierExtId;
  }

  /**
   * @param defaultCarrierExtId the defaultCarrierExtId to set
   */
  public void setDefaultCarrierExtId(String defaultCarrierExtId) {
    this.defaultCarrierExtId = defaultCarrierExtId;
  }

  /**
   * @return the defaultClientPassword
   */
  public String getDefaultClientPassword() {
    return defaultClientPassword;
  }

  /**
   * @param defaultClientPassword the defaultClientPassword to set
   */
  public void setDefaultClientPassword(String defaultClientPassword) {
    this.defaultClientPassword = defaultClientPassword;
  }

  /**
   * @return the defaultClientUsername
   */
  public String getDefaultClientUsername() {
    return defaultClientUsername;
  }

  /**
   * @param defaultClientUsername the defaultClientUsername to set
   */
  public void setDefaultClientUsername(String defaultClientUsername) {
    this.defaultClientUsername = defaultClientUsername;
  }

  /**
   * @return the defaultServerPassword
   */
  public String getDefaultServerPassword() {
    return defaultServerPassword;
  }

  /**
   * @param defaultServerPassword the defaultServerPassword to set
   */
  public void setDefaultServerPassword(String defaultServerPassword) {
    this.defaultServerPassword = defaultServerPassword;
  }

  /* (non-Javadoc)
   * @see org.apache.struts.action.PlugIn#destroy()
   */
  public void destroy() {
    super.destroy();
  }

  /* (non-Javadoc)
   * @see org.apache.struts.action.PlugIn#init(org.apache.struts.action.ActionServlet, org.apache.struts.config.ModuleConfig)
   */
  public void init(ActionServlet servlet, ModuleConfig config) throws ServletException {
    super.init(servlet, config);
  }

  /* (non-Javadoc)
   * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
   */
  public void onMessage(Message message) {
    ManagementBeanFactory factory = null;
    try {
        DeviceEnrollMessage enrollMsg = null;
        if (message != null) {
           DeviceEnrollMessageDecoder decoder = DeviceEnrollMessageDecoder.getDecoder(message);
           enrollMsg = decoder.decode(message);
        }
        if (enrollMsg == null) {
           log.error("unkonw device enroll message in listener queue: " + message);
           return;
        }
        if (log.isDebugEnabled()) {
           log.debug("processing device enroll message: " + enrollMsg);
        }
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());

        AutoRegistrationBean reg = new AutoRegistrationBean();
        
        reg.setFactory(factory);
        reg.setCarrierExternalID(this.getDefaultCarrierExtId());
        reg.setClientPassword(this.getDefaultClientPassword());
        reg.setClientUsername(this.getDefaultClientUsername());
        reg.setServerPassword(this.getDefaultServerPassword());
        
        // Tracking it
        this.track(enrollMsg);

        factory.beginTransaction();
        reg.bind(enrollMsg);
        factory.commit();

        if (log.isDebugEnabled()) {
           log.debug("processing device enroll message: " + enrollMsg);
        }
    } catch (Exception e) {
      log.error("Failure in processing device enroll message.", e);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  /**
   * Tracking it and save into device change log.
   * @param enrollMsg
   */
  private void track(DeviceEnrollMessage enrollMsg) {
    DeviceChangeLoggerFactory logFactory = null;
    try {
      logFactory = DeviceChangeLoggerFactory.newInstance();
      DeviceChangeLogger logger = logFactory.getLogger();
      logger.log(enrollMsg.getImei(), enrollMsg.getMsisdn(), enrollMsg.getImsi(), enrollMsg.getBrandName(), enrollMsg.getModelName(), enrollMsg.getSoftwareVersion());
    } catch (Exception ex) {
      log.error("Failure to write a device change log: " + enrollMsg, ex);
    } finally {
    }
  }

}
