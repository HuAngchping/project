package com.npower.dm.responder;

import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.Message;
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

import com.npower.common.plugins.AbstractDisablePlugIn;
import com.npower.jms.JMSManager;
import com.npower.jndi.JndiContextFactory;

public abstract class AbstractResponderDaemon extends AbstractDisablePlugIn implements PlugIn, MessageListener {

  private static Log log = LogFactory.getLog(AbstractResponderDaemon.class);
  
  private String name = "Default";
  private QueueConnection connection;
  private QueueSession session;
  private String incomingQueueName = null;

  public AbstractResponderDaemon() {
    super();
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the incomingQueue
   */
  public String getIncomingQueueName() {
    return incomingQueueName;
  }

  /**
   * @param incomingQueue the incomingQueue to set
   */
  public void setIncomingQueueName(String incomingQueue) {
    this.incomingQueueName = incomingQueue;
  }

  public void destroy() {
    log.info("Stopping" +  this.getClass().getSimpleName() + "[" + this.getName() + "] Daemon ...");
    try {
      if (session != null) {
         session.close();
      } 
      if (connection != null) {
         connection.close();
      }
      log.info(this.getClass().getSimpleName() + "[" + this.getName() + "] Daemon has been stopped.");
    } catch (JMSException e) {
      log.error("failure to stop " + this.getClass().getCanonicalName(), e);
    }
  }

  public void init(ActionServlet servlet, ModuleConfig config) throws ServletException {
    log.info("Starting " + this.getClass().getSimpleName() + "[" + this.getName() + "] Daemon ...");
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
        Queue queue = jmsManager.getQueue(this.getIncomingQueueName(), null);
        QueueReceiver receiver = session.createReceiver(queue);
        receiver.setMessageListener(this);
        
        connection.start();
        
        log.info(this.getClass().getSimpleName() + "[" + this.getName() + "] daemon has been started.");
    } catch (Exception e) {
      log.error("failure to initialize " + this.getClass().getCanonicalName(), e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
   */
  public abstract void onMessage(Message message);

}