/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/responder/SimpleResponderDaemon.java,v 1.5 2008/12/25 10:53:00 zhao Exp $
 * $Revision: 1.5 $
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
package com.npower.dm.responder;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.PlugIn;
import com.npower.sms.transport.JmsSmsReceiverImpl;


/**
 * <code>
 * <pre>
 * 用于测试和调试的目的, 仅仅将接收到的消息显示在日志文件中, 可以通过如下方式创建一个或多个ResponderDaemon:
 *  <plug-in className="com.npower.dm.responder.SimpleResponderDaemon">
 *   <!-- Flag for disable this plugin -->
 *   <set-property property="disabled" value="false" />
 *   <set-property property="incomingQueueName" value="Q2" />
 *   <set-property property="name" value="Q2" />
 *  </plug-in>
 *
 * </pre>
 * </code>
 * @author Zhao DongLu
 * @version $Revision: 1.5 $ $Date: 2008/12/25 10:53:00 $
 */
public class SimpleResponderDaemon extends AbstractResponderDaemon implements PlugIn, MessageListener {

  private static Log log = LogFactory.getLog(SimpleResponderDaemon.class);
  /**
   * 
   */
  public SimpleResponderDaemon() {
    super();
  }

  /* (non-Javadoc)
   * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
   */
  public void onMessage(Message message) {
    try {
        String msisdn = message.getStringProperty(JmsSmsReceiverImpl.PROPERTY_DEVICE_MSISDN_NAME);
        String serviceId = message.getStringProperty(JmsSmsReceiverImpl.PROPERTY_DEVICE_SERVICE_CODE);
        //String text = message.getStringProperty(JmsSmsReceiverImpl.PROPERTY_TEXT_MESSAGE);
        String bytesInHex = message.getStringProperty(JmsSmsReceiverImpl.PROPERTY_RAW_MESSAGE);
        log.info(this.getClass().getSimpleName() + "[" + this.getName() + "] receive a incoming message : from " + msisdn +" , to " + serviceId +" , content " + bytesInHex);
    } catch (Exception e) {
      log.error("Failure to response for a incoming message.", e);
    } finally {
    }
  }

}
