/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/daemon/SimpleMessage4Test.java,v 1.1 2008/08/05 11:23:20 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/08/05 11:23:20 $
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

import java.util.Enumeration;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/08/05 11:23:20 $
 */
public class SimpleMessage4Test implements Message {
  
  private String msisdn = null;
  private String rawSmsMessage = null;

  /**
   * 
   */
  public SimpleMessage4Test() {
    super();
  }

  
  /**
   * @param smsMessageDecoder
   * @param msisdn
   * @param rawSmsMessage
   */
  public SimpleMessage4Test(String smsMessageDecoder, String msisdn, String rawSmsMessage) {
    super();
    this.msisdn = msisdn;
    this.rawSmsMessage = rawSmsMessage;
  }


  /* (non-Javadoc)
   * @see javax.jms.Message#acknowledge()
   */
  public void acknowledge() throws JMSException {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see javax.jms.Message#clearBody()
   */
  public void clearBody() throws JMSException {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see javax.jms.Message#clearProperties()
   */
  public void clearProperties() throws JMSException {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see javax.jms.Message#getBooleanProperty(java.lang.String)
   */
  public boolean getBooleanProperty(String name) throws JMSException {
    // TODO Auto-generated method stub
    return false;
  }

  /* (non-Javadoc)
   * @see javax.jms.Message#getByteProperty(java.lang.String)
   */
  public byte getByteProperty(String name) throws JMSException {
    // TODO Auto-generated method stub
    return 0;
  }

  /* (non-Javadoc)
   * @see javax.jms.Message#getDoubleProperty(java.lang.String)
   */
  public double getDoubleProperty(String name) throws JMSException {
    // TODO Auto-generated method stub
    return 0;
  }

  /* (non-Javadoc)
   * @see javax.jms.Message#getFloatProperty(java.lang.String)
   */
  public float getFloatProperty(String name) throws JMSException {
    // TODO Auto-generated method stub
    return 0;
  }

  /* (non-Javadoc)
   * @see javax.jms.Message#getIntProperty(java.lang.String)
   */
  public int getIntProperty(String name) throws JMSException {
    // TODO Auto-generated method stub
    return 0;
  }

  /* (non-Javadoc)
   * @see javax.jms.Message#getJMSCorrelationID()
   */
  public String getJMSCorrelationID() throws JMSException {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see javax.jms.Message#getJMSCorrelationIDAsBytes()
   */
  public byte[] getJMSCorrelationIDAsBytes() throws JMSException {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see javax.jms.Message#getJMSDeliveryMode()
   */
  public int getJMSDeliveryMode() throws JMSException {
    // TODO Auto-generated method stub
    return 0;
  }

  /* (non-Javadoc)
   * @see javax.jms.Message#getJMSDestination()
   */
  public Destination getJMSDestination() throws JMSException {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see javax.jms.Message#getJMSExpiration()
   */
  public long getJMSExpiration() throws JMSException {
    // TODO Auto-generated method stub
    return 0;
  }

  /* (non-Javadoc)
   * @see javax.jms.Message#getJMSMessageID()
   */
  public String getJMSMessageID() throws JMSException {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see javax.jms.Message#getJMSPriority()
   */
  public int getJMSPriority() throws JMSException {
    // TODO Auto-generated method stub
    return 0;
  }

  /* (non-Javadoc)
   * @see javax.jms.Message#getJMSRedelivered()
   */
  public boolean getJMSRedelivered() throws JMSException {
    // TODO Auto-generated method stub
    return false;
  }

  /* (non-Javadoc)
   * @see javax.jms.Message#getJMSReplyTo()
   */
  public Destination getJMSReplyTo() throws JMSException {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see javax.jms.Message#getJMSTimestamp()
   */
  public long getJMSTimestamp() throws JMSException {
    // TODO Auto-generated method stub
    return 0;
  }

  /* (non-Javadoc)
   * @see javax.jms.Message#getJMSType()
   */
  public String getJMSType() throws JMSException {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see javax.jms.Message#getLongProperty(java.lang.String)
   */
  public long getLongProperty(String name) throws JMSException {
    // TODO Auto-generated method stub
    return 0;
  }

  /* (non-Javadoc)
   * @see javax.jms.Message#getObjectProperty(java.lang.String)
   */
  public Object getObjectProperty(String name) throws JMSException {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see javax.jms.Message#getPropertyNames()
   */
  public Enumeration getPropertyNames() throws JMSException {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see javax.jms.Message#getShortProperty(java.lang.String)
   */
  public short getShortProperty(String name) throws JMSException {
    // TODO Auto-generated method stub
    return 0;
  }

  /* (non-Javadoc)
   * @see javax.jms.Message#getStringProperty(java.lang.String)
   */
  public String getStringProperty(String name) throws JMSException {
    if (name.equals(DeviceEnrollMessageDecoder.PROPERTY_DEVICE_MSISDN_NAME)) {
      return this.msisdn;
     } else if (name.equals(DeviceEnrollMessageDecoder.PROPERTY_RAW_MESSAGE)) {
      return this.rawSmsMessage;
    } else {
      return null;
    }
  }

  /* (non-Javadoc)
   * @see javax.jms.Message#propertyExists(java.lang.String)
   */
  public boolean propertyExists(String name) throws JMSException {
    // TODO Auto-generated method stub
    return false;
  }

  /* (non-Javadoc)
   * @see javax.jms.Message#setBooleanProperty(java.lang.String, boolean)
   */
  public void setBooleanProperty(String name, boolean value) throws JMSException {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see javax.jms.Message#setByteProperty(java.lang.String, byte)
   */
  public void setByteProperty(String name, byte value) throws JMSException {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see javax.jms.Message#setDoubleProperty(java.lang.String, double)
   */
  public void setDoubleProperty(String name, double value) throws JMSException {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see javax.jms.Message#setFloatProperty(java.lang.String, float)
   */
  public void setFloatProperty(String name, float value) throws JMSException {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see javax.jms.Message#setIntProperty(java.lang.String, int)
   */
  public void setIntProperty(String name, int value) throws JMSException {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see javax.jms.Message#setJMSCorrelationID(java.lang.String)
   */
  public void setJMSCorrelationID(String correlationID) throws JMSException {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see javax.jms.Message#setJMSCorrelationIDAsBytes(byte[])
   */
  public void setJMSCorrelationIDAsBytes(byte[] correlationID) throws JMSException {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see javax.jms.Message#setJMSDeliveryMode(int)
   */
  public void setJMSDeliveryMode(int deliveryMode) throws JMSException {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see javax.jms.Message#setJMSDestination(javax.jms.Destination)
   */
  public void setJMSDestination(Destination destination) throws JMSException {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see javax.jms.Message#setJMSExpiration(long)
   */
  public void setJMSExpiration(long expiration) throws JMSException {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see javax.jms.Message#setJMSMessageID(java.lang.String)
   */
  public void setJMSMessageID(String id) throws JMSException {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see javax.jms.Message#setJMSPriority(int)
   */
  public void setJMSPriority(int priority) throws JMSException {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see javax.jms.Message#setJMSRedelivered(boolean)
   */
  public void setJMSRedelivered(boolean redelivered) throws JMSException {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see javax.jms.Message#setJMSReplyTo(javax.jms.Destination)
   */
  public void setJMSReplyTo(Destination replyTo) throws JMSException {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see javax.jms.Message#setJMSTimestamp(long)
   */
  public void setJMSTimestamp(long timestamp) throws JMSException {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see javax.jms.Message#setJMSType(java.lang.String)
   */
  public void setJMSType(String type) throws JMSException {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see javax.jms.Message#setLongProperty(java.lang.String, long)
   */
  public void setLongProperty(String name, long value) throws JMSException {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see javax.jms.Message#setObjectProperty(java.lang.String, java.lang.Object)
   */
  public void setObjectProperty(String name, Object value) throws JMSException {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see javax.jms.Message#setShortProperty(java.lang.String, short)
   */
  public void setShortProperty(String name, short value) throws JMSException {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see javax.jms.Message#setStringProperty(java.lang.String, java.lang.String)
   */
  public void setStringProperty(String name, String value) throws JMSException {
  }

}
