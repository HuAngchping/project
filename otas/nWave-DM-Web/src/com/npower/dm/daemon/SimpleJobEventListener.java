/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/daemon/SimpleJobEventListener.java,v 1.1 2008/07/26 07:52:56 zhao Exp $
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.cp.OTAInventory;
import com.npower.dm.action.ActionHelper;
import com.npower.dm.bootstrap.BootstrapService;
import com.npower.dm.bootstrap.BootstrapServiceFactory;
import com.npower.dm.core.DMException;
import com.npower.dm.dispatch.JobNotificationSender;
import com.npower.dm.dispatch.JobNotificationSenderImpl;
import com.npower.dm.dispatch.ProvisionJobDispatcher;
import com.npower.dm.dispatch.ProvisionJobDispatcherFactory;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.notifcation.NotificationService;
import com.npower.dm.notifcation.NotificationServiceFactory;
import com.npower.dm.server.engine.EngineConfig;
import com.npower.dm.util.ConfigHelper;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/07/26 07:52:56 $
 */
public class SimpleJobEventListener implements JobEventListener {

  private static Log log = LogFactory.getLog(SimpleJobEventListener.class);
  /**
   * 
   */
  public SimpleJobEventListener() {
    super();
  }

  /**
   * @param factory
   * @return
   * @throws DMException
   */
  private static JobNotificationSender getJobNotificationSender(ManagementBeanFactory factory) throws Exception {
    NotificationServiceFactory serviceFactory = NotificationServiceFactory.newInstance(new Properties());
    NotificationService notifiService = serviceFactory.getNotificationService();
    notifiService.setBeanFactory(factory);
    
    OTAInventory otaInventory = ActionHelper.getOTAInventory();
    BootstrapServiceFactory bsFactory = BootstrapServiceFactory.newInstance(new Properties());
    BootstrapService bootstrapService = bsFactory.getBootstrapService();
    bootstrapService.setBeanFactory(factory);
    bootstrapService.setOtaInventory(otaInventory);

    JobNotificationSender sender = new JobNotificationSenderImpl();
    // Load default SMS Gateway properties
    Properties props = ConfigHelper.getSmsGatewayProperties();
    sender.setDefaultSmsGatewayProperties(props);
    sender.setNotificationService(notifiService);
    sender.setBootstrapService(bootstrapService);
    return sender;
  }
  
  /**
   * @param factory
   * @return
   * @throws DMException
   */
  private static ProvisionJobDispatcher getProvisionJobDispatcher(ManagementBeanFactory factory) throws Exception {
    ProvisionJobDispatcherFactory dispatcherFactory = ProvisionJobDispatcherFactory.newInstance(ConfigHelper.getJobDaemonProperties());
    ProvisionJobDispatcher dispatcher = dispatcherFactory.getProvisionJobDispatcher();
    JobNotificationSender notifier = getJobNotificationSender(factory);
    dispatcher.setJobNotificationSender(notifier);
    return dispatcher;
  }

  /**
   * @param jobID
   */
  private void dispatch(long jobID) {
    if (jobID <= 0) {
       return;
    }
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        ProvisionJobDispatcher dispatcher = getProvisionJobDispatcher(factory);
        dispatcher.dispatch(jobID);
    } catch (Exception ex) {
      log.fatal("Failure in Job Event Listener.", ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }
  /* (non-Javadoc)
   * @see com.npower.dm.action.JobEventListener#notify(com.npower.dm.action.JobEvent)
   */
  public void notify(JobEvent event) {
    if (log.isDebugEnabled()) {
       log.debug("receive a job event, type=" + event.getType().toString() + ", jobID=" + event.getJobID());
    }
    switch (event.getType()) {
    case Create:
      this.dispatch(event.getJobID());
      break;
    }
  }

}
