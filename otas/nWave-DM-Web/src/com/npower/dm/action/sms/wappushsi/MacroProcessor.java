/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/sms/wappushsi/MacroProcessor.java,v 1.2 2008/06/21 01:14:28 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2008/06/21 01:14:28 $
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
package com.npower.dm.action.sms.wappushsi;

import org.apache.commons.lang.StringUtils;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.Subscriber;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SubscriberBean;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/06/21 01:14:28 $
 */
public class MacroProcessor {
  
  private static final String IMEI_PATTERN = "$(IMEI)";
  private static final String IMEI_NO_PREFIX_PATTERN = "$(imei)";
  private static final String MSISDN_PATTERN = "$(msisdn)";
  private static final String DEVICE_PATTERN = "$(device_id)";
  //private static final String SUBSCRIBER_PATTERN = "$(subscriber_id)";
  private ManagementBeanFactory factory = null;

  /**
   * 
   */
  public MacroProcessor() {
    super();
  }

  public ManagementBeanFactory getFactory() {
    return factory;
  }

  public void setFactory(ManagementBeanFactory factory) {
    this.factory = factory;
  }
  
  /**
   * @param msisdn
   * @return
   * @throws DMException
   */
  public String getImeiFromMsisdn(String msisdn) throws DMException {
    Device device = getDevice(msisdn);
    if (device != null) {
       return device.getExternalId();
    } else {
      return null;
    }
  }

  /**
   * @param msisdn
   * @return
   * @throws DMException
   */
  private Device getDevice(String msisdn) throws DMException {
    SubscriberBean bean = factory.createSubcriberBean();
    Subscriber subscriber = bean.getSubscriberByPhoneNumber(msisdn);
    Device device = null;
    if (subscriber != null && subscriber.getDevices() != null && !subscriber.getDevices().isEmpty()) {
       for (Device d : subscriber.getDevices()) {
           device = d;
           break;
       }
    }
    return device;
  }
  
  /**
   * @param raw
   * @param msisdn
   * @return
   * @throws DMException
   */
  public String process(String raw, String msisdn) throws DMException {
    if (StringUtils.isEmpty(raw)) {
       return raw;
    }
    
    String result = raw;
    if (result.indexOf(MacroProcessor.MSISDN_PATTERN) >= 0) {
       if (StringUtils.isEmpty(msisdn)) {
         msisdn = "";
       }
       result = StringUtils.replace(result, MacroProcessor.MSISDN_PATTERN, msisdn);
    }
    if (result.indexOf(MacroProcessor.IMEI_PATTERN) >= 0) {
       String imei = this.getImeiFromMsisdn(msisdn);
       if (StringUtils.isEmpty(imei)) {
          imei = "";
       }
       result = StringUtils.replace(result, MacroProcessor.IMEI_PATTERN, imei);
    }
    if (result.indexOf(MacroProcessor.IMEI_NO_PREFIX_PATTERN) >= 0) {
       String imei = this.getImeiFromMsisdn(msisdn);
       if (StringUtils.isEmpty(imei)) {
          imei = "";
       } else if (imei.startsWith("IMEI:")){
         imei = imei.substring(5, imei.length());
       }
       result = StringUtils.replace(result, MacroProcessor.IMEI_NO_PREFIX_PATTERN, imei);
    }
    if (result.indexOf(MacroProcessor.DEVICE_PATTERN) >= 0) {
       Device device = this.getDevice(msisdn);
       String deviceID = "";
       if (device != null) {
          deviceID = "" + device.getID();
       }
      result = StringUtils.replace(result, MacroProcessor.DEVICE_PATTERN, deviceID);
    }
    return result;
  }

}
