/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/daemon/JobEventListenerDaemon.java,v 1.2 2008/11/09 09:29:25 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2008/11/09 09:29:25 $
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

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import com.npower.common.plugins.AbstractDisablePlugIn;
import com.npower.dm.dispatch.ProvisionJobDispatcher;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.server.engine.EngineConfig;
import com.npower.jms.JMSManager;
import com.npower.jndi.JndiContextFactory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/11/09 09:29:25 $
 */
public class JobEventListenerDaemon extends AbstractDisablePlugIn implements PlugIn, MessageListener {

  private static Log log = LogFactory.getLog(JobEventListenerDaemon.class);
  private QueueConnection connection;
  private QueueSession session;
  /**
   * 
   */
  public JobEventListenerDaemon() {
    super();
  }

  /* (non-Javadoc)
   * @see org.apache.struts.action.PlugIn#destroy()
   */
  public void destroy() {
    log.info("Stopping Job Event Listener ...");
    try {
      if (session != null) {
         session.close();
      } 
      if (connection != null) {
         connection.close();
      }
      log.info("Job Event Listener Daemon has been stopped.");
    } catch (JMSException e) {
      log.error("failure to stop " + this.getClass().getCanonicalName(), e);
    }
  }

  /* (non-Javadoc)
   * @see org.apache.struts.action.PlugIn#init(org.apache.struts.action.ActionServlet, org.apache.struts.config.ModuleConfig)
   */
  public void init(ActionServlet servlet, ModuleConfig config) throws ServletException {
    log.info("Starting Job Event Listener ...");
    connection = null;
    session = null;
    try {
        JndiContextFactory jndiFactory = JndiContextFactory.newInstance(new Properties());
        Context jndiCtx = jndiFactory.getJndiContext();
        JMSManager jmsManager = JMSManager.newInstance(jndiCtx);
        
        QueueConnectionFactory connectionFactory = jmsManager.getQueueConnectionFactory();
        connection = connectionFactory.createQueueConnection();
        session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        
        // Create or get outgoing queue
        Queue queue = jmsManager.getQueue(JMSBasedJobEventListenerImpl.QUEUE_NAME_OTAS_JOB_EVENT, null);
        QueueReceiver receiver = session.createReceiver(queue);
        receiver.setMessageListener(this);
        
        connection.start();
        
        log.info("Job Event Listener Daemon has been started.");
    } catch (Exception e) {
      log.error("failure to initialize " + this.getClass().getCanonicalName(), e);
    } finally {
    }
  }

  public void onMessage(Message message) {
    ManagementBeanFactory factory = null;
    try {
        JobEvent event = null;
        if (message != null) {
          if (message instanceof ObjectMessage) {
            ObjectMessage m = (ObjectMessage) message;
            event = (JobEvent)m.getObject();
          }
        }
        if (event == null) {
           log.error("unkonw message type in job event listener queue: " + message);
           return;
        }
        if (log.isDebugEnabled()) {
           log.debug("processing job event, type: " + event.getType() + ", job id: " + event.getJobID());
        }
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        ProvisionJobDispatcher dispatcher = JobDispatcherTimerTask.getProvisionJobDispatcher(factory);
        dispatcher.dispatch(event.getJobID());
        if (log.isDebugEnabled()) {
           log.debug("end of processing job event, type: " + event.getType() + ", job id: " + event.getJobID());
        }
    } catch (Exception e) {
      log.error("Failure in processing job event", e);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

}
