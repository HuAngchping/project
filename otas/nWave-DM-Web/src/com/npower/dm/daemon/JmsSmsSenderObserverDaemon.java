/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/daemon/JmsSmsSenderObserverDaemon.java,v 1.4 2008/11/09 09:29:25 zhao Exp $
 * $Revision: 1.4 $
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
import javax.jms.MessageListener;
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

import com.npower.jms.JMSManager;
import com.npower.jndi.JndiContextFactory;
import com.npower.sms.client.jms.JmsSmsSenderConstance;
import com.npower.common.plugins.AbstractDisablePlugIn;
import com.npower.dm.service.SmsSenderObserverImpl;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2008/11/09 09:29:25 $
 */
public class JmsSmsSenderObserverDaemon extends AbstractDisablePlugIn implements PlugIn, JmsSmsSenderConstance {

  private static Log log = LogFactory.getLog(JmsSmsSenderObserverDaemon.class);
  private QueueConnection connection;
  private QueueSession session;
  

  /**
   * 
   */
  public JmsSmsSenderObserverDaemon() {
    super();
  }

  /**
   * 返回SMS发送状态的队列名称
   * @return
   */
  private String getQueueName() {
    return JmsSmsSenderConstance.DEFAULT_OUTGOING_SENT_EVENT_QUEUE_NAME;
  }

  public void destroy() {
    log.info("Stopping SMS event observer ...");
    try {
      if (session != null) {
         session.close();
      } 
      if (connection != null) {
         connection.close();
      }
      log.info("SMS event observer Daemon has been stopped.");
    } catch (JMSException e) {
      log.error("failure to stop " + this.getClass().getCanonicalName(), e);
    }
  }

  public void init(ActionServlet servlet, ModuleConfig config) throws ServletException {
    log.info("Starting SMS event observer ...");
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
        Queue queue = jmsManager.getQueue(this.getQueueName(), null);
        QueueReceiver receiver = session.createReceiver(queue);

        MessageListener observer = new SmsSenderObserverImpl();
        receiver.setMessageListener(observer);
        
        connection.start();
        
        log.info("event observer has been started.");
    } catch (Exception e) {
      log.error("failure to initialize " + this.getClass().getCanonicalName(), e);
    } finally {
    }
  }

}
