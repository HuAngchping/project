/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/daemon/JobDispatcherDaemon.java,v 1.4 2008/11/09 09:29:25 zhao Exp $
  * $Revision: 1.4 $
  * $Date: 2008/11/09 09:29:25 $
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
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import com.npower.common.plugins.AbstractDisablePlugIn;
import com.npower.dm.util.ConfigHelper;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2008/11/09 09:29:25 $
 */
public class JobDispatcherDaemon extends AbstractDisablePlugIn implements PlugIn {

  public static final String PROPERTY_DM_DAEMON_JOB_DISPATCHER = "dm.daemon.job.dispatcher.enable";

  private static Log log = LogFactory.getLog(JobDispatcherDaemon.class);

  private long delay = 60000;
  private long interval = 60000;
  
  /**
   * 
   */
  public JobDispatcherDaemon() {
    super();
  }

  /**
   * @return the delay
   */
  public long getDelay() {
    return this.delay;
  }

  /**
   * @param delay the delay to set
   */
  public void setDelay(long delay) {
    this.delay = delay;
  }

  /**
   * @return the interval
   */
  public long getInterval() {
    return this.interval;
  }

  /**
   * @param interval the interval to set
   */
  public void setInterval(long interval) {
    this.interval = interval;
  }

  /* (non-Javadoc)
   * @see org.apache.struts.action.PlugIn#init(org.apache.struts.action.ActionServlet, org.apache.struts.config.ModuleConfig)
   */
  public void init(ActionServlet servlet, ModuleConfig config) throws ServletException {
    boolean enable = true;
    try {
      Properties props = ConfigHelper.getJobDaemonProperties();
      String value = props.getProperty(PROPERTY_DM_DAEMON_JOB_DISPATCHER, "true");
      if (StringUtils.isNotEmpty(value)) {
         enable = Boolean.parseBoolean(value);
      }
    } catch (Exception ex) {
      log.error("Could not get property: " + PROPERTY_DM_DAEMON_JOB_DISPATCHER, ex);
    }
    if (!enable) {
       log.info("Job Dispatcher Daemon disabled ...");
       return;
    }
    log.info("Starting Job Dispatcher Daemon [ delay=" + this.getDelay() + "ms, interval=" + this.getInterval() + "ms ] ...");
    Timer timer = new Timer(this.getClass().getName(), true);
    TimerTask task = new JobDispatcherTimerTask();
    timer.schedule(task, this.getDelay(), this.getInterval());
    log.info("Job Dispatcher Daemon has been started ...");
  }
  
  /* (non-Javadoc)
   * @see org.apache.struts.action.PlugIn#destroy()
   */
  public void destroy() {
  }


}
