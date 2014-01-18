/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/daemon/JobDispatcherTimerTask.java,v 1.5 2008/07/26 11:33:46 zhao Exp $
  * $Revision: 1.5 $
  * $Date: 2008/07/26 11:33:46 $
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
package com.npower.dm.daemon;

import java.util.Properties;
import java.util.TimerTask;

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
 * @version $Revision: 1.5 $ $Date: 2008/07/26 11:33:46 $
 */
public class JobDispatcherTimerTask extends TimerTask {

  private static Log log = LogFactory.getLog(JobDispatcherTimerTask.class);
  
  private boolean isRunning = false;
  /**
   * 
   */
  public JobDispatcherTimerTask() {
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
  public static ProvisionJobDispatcher getProvisionJobDispatcher(ManagementBeanFactory factory) throws Exception {
    ProvisionJobDispatcherFactory dispatcherFactory = ProvisionJobDispatcherFactory.newInstance(ConfigHelper.getJobDaemonProperties());
    ProvisionJobDispatcher dispatcher = dispatcherFactory.getProvisionJobDispatcher();
    JobNotificationSender notifier = getJobNotificationSender(factory);
    dispatcher.setJobNotificationSender(notifier);
    return dispatcher;
  }
  
  /* (non-Javadoc)
   * @see java.util.TimerTask#run()
   */
  @Override
  public void run() {
    synchronized (this) {
      if (isRunning) {
         log.debug("Another JobDispatcherTimerTask thread is running ...");
         return;
      } else {
        isRunning = true;
      }
    }
    ManagementBeanFactory factory = null;
    try {
        log.debug("Running JobDispatcherTimerTask ...");
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        ProvisionJobDispatcher dispatcher = getProvisionJobDispatcher(factory);
        dispatcher.dispatchAll();
        log.debug("End of running JobDispatcherTimerTask ...");
    } catch (Exception e) {
      log.error("Failure in running JobDispatcherTimerTask.", e);
    } finally {
      if (factory != null) {
         factory.release();
      }
      isRunning = false;
    }
  }

}
