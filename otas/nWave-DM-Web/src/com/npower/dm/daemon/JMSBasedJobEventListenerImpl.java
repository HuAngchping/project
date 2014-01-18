/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/daemon/JMSBasedJobEventListenerImpl.java,v 1.1 2008/07/26 11:33:46 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/07/26 11:33:46 $
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
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.jms.JMSManager;
import com.npower.jndi.JndiContextFactory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/07/26 11:33:46 $
 */
public class JMSBasedJobEventListenerImpl implements JobEventListener {

  public static final String QUEUE_NAME_OTAS_JOB_EVENT = "OTAS_JOB_EVENT_QUEUE";
  private static Log log = LogFactory.getLog(JMSBasedJobEventListenerImpl.class);
  /**
   * 
   */
  public JMSBasedJobEventListenerImpl() {
    super();
  }

  private Message createMessage4Outgoing(QueueSession session, JobEvent event) throws JMSException {
    Message jmsMessage = session.createObjectMessage(event);
    return jmsMessage;
  }
  /**
   * @param jobID
   */
  private void dispatch(JobEvent event) {
    QueueConnection connection = null;
    QueueSession session = null;
    try {
        JndiContextFactory jndiFactory = JndiContextFactory.newInstance(new Properties());
        Context jndiCtx = jndiFactory.getJndiContext();
        JMSManager jmsManager = JMSManager.newInstance(jndiCtx);
        
        QueueConnectionFactory connectionFactory = jmsManager.getQueueConnectionFactory();
        connection = connectionFactory.createQueueConnection();
        session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        
        // Create or get outgoing queue
        Queue queue = jmsManager.getQueue(JMSBasedJobEventListenerImpl.QUEUE_NAME_OTAS_JOB_EVENT, null);
        QueueSender sender = session.createSender(queue);
        Message msg = this.createMessage4Outgoing(session, event);
        sender.send(msg);
    } catch (Exception e) {
      log.error("failure to dispatch job event into queue.", e);
    } finally {
      try {
        if (session != null) {
           session.close();
        } 
        if (connection != null) {
           connection.close();
        }
      } catch (JMSException e) {
        log.error("failure to dispatch job event into queue.", e);
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
      this.dispatch(event);
      break;
    }
  }

}
