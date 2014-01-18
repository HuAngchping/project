/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/daemon/DeviceEnrollMessageDecoder.java,v 1.4 2008/11/09 09:29:25 zhao Exp $
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

import javax.jms.JMSException;
import javax.jms.Message;

import org.apache.commons.lang.StringUtils;

import com.npower.dm.core.DMException;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2008/11/09 09:29:25 $
 */
public abstract class DeviceEnrollMessageDecoder {

  public static final String PROPERTY_DEVICE_MSISDN_NAME = "DEVICE_MSISDN";
  public static final String PROPERTY_RAW_MESSAGE = "PROPERTY_RAW_SMS_MESSAGE";

  /**
   * 
   */
  public DeviceEnrollMessageDecoder() {
    super();
  }
  
  /**
   * @param className
   * @return
   * @throws ClassNotFoundException
   * @throws InstantiationException
   * @throws IllegalAccessException
   */
  public static DeviceEnrollMessageDecoder getDecoder(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
    Class<?> clazz = Class.forName(className);
    DeviceEnrollMessageDecoder instance = (DeviceEnrollMessageDecoder)clazz.newInstance();
    return instance;
  }
  
  /**
   * @param message
   * @return
   * @throws JMSException
   * @throws ClassNotFoundException
   * @throws InstantiationException
   * @throws IllegalAccessException
   */
  public static DeviceEnrollMessageDecoder getDecoder(Message message) throws JMSException, ClassNotFoundException, InstantiationException, IllegalAccessException {
    String rawMsgInHex = message.getStringProperty(PROPERTY_RAW_MESSAGE);
    if (StringUtils.isNotEmpty(rawMsgInHex) && 
        (rawMsgInHex.startsWith("06050442664266") || rawMsgInHex.startsWith("544553542F"))) {
       // Is CMCC Auto Reg SMS
       return getDecoder(DeviceEnrollMessageDecoder4CMCC.class.getName());
    } else {
      // Is Unicom
      return getDecoder(DeviceEnrollMessageDecoder4UniCom.class.getName());
    }
  }
  
  /**
   * @param message
   * @return
   * @throws DMException
   */
  public abstract DeviceEnrollMessage decode(Message message) throws DMException;
}
