/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/daemon/DeviceEnrollMessageDecoder4CMCC.java,v 1.2 2008/08/06 07:59:23 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2008/08/06 07:59:23 $
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

import java.io.UnsupportedEncodingException;

import javax.jms.Message;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.dm.core.DMException;
import com.npower.util.HelperUtil;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/08/06 07:59:23 $
 */
public class DeviceEnrollMessageDecoder4CMCC extends DeviceEnrollMessageDecoder {
  private static Log log = LogFactory.getLog(DeviceEnrollMessageDecoder4CMCC.class);

  /**
   * 
   */
  public DeviceEnrollMessageDecoder4CMCC() {
    super();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.daemon.DeviceEnrollMessageDecoder#decode(javax.jms.Message)
   */
  @Override
  public DeviceEnrollMessage decode(Message message) throws DMException {
    try {
        String msisdn = message.getStringProperty(PROPERTY_DEVICE_MSISDN_NAME);
        msisdn = msisdn.trim();
        if (msisdn.startsWith("+")) {
           msisdn = msisdn.substring(1, msisdn.length());
        }
        if (msisdn.startsWith("86")) {
           msisdn = msisdn.substring(2, msisdn.length());
        }
        if (msisdn.length() > 11) {
           msisdn = msisdn.substring(0, 11);
        }
        DeviceEnrollMessage enrollMsg = new DeviceEnrollMessage();
        enrollMsg.setMsisdn(msisdn);
        
        // Get Raw message
        String rawMsg = message.getStringProperty(PROPERTY_RAW_MESSAGE);
        byte[] bytes = HelperUtil.hexStringToBytes(rawMsg);
        
        // Validate message content
        validate(bytes);
        
        String text = new String(bytes, "iso8859-1");
        int begin = text.indexOf("IMEI:");
        if (begin < 0) {
           throw new UnsupportedEncodingException("SMS not contains 'IMEI:', raw msg: " + text);
        }
        text = text.substring(begin);
        if (!text.toUpperCase().startsWith("IMEI:")) {
           throw new UnsupportedEncodingException("SMS not start with 'IMEI:', raw msg: " + text);
        }
        String[] columns = StringUtils.split(text, '/');
        enrollMsg.setImei(columns[0].trim());
        String brandName = columns[1];
        enrollMsg.setBrandName(brandName.trim());
        String modelName = columns[2];
        if (modelName.startsWith(brandName)) {
           modelName = modelName.substring(brandName.length());
        }
        enrollMsg.setModelName(modelName.trim());
        enrollMsg.setSoftwareVersion(columns[3].trim());
        
        if (log.isDebugEnabled()) {
           log.debug("Decode enroll device message: " + enrollMsg.toString());
        }
        
        return enrollMsg;
    } catch (Exception e) {
      throw new DMException("failure to decode device enroll message.", e);
    }
    
  }

  /**
   * @param bytes
   * @param rawMsg
   * @throws UnsupportedEncodingException
   */
  private void validate(byte[] bytes) throws UnsupportedEncodingException {
    String hexString = HelperUtil.bytesToHexString(bytes);
    if (bytes == null || bytes.length < 32) {
       throw new UnsupportedEncodingException("Unkown enroll sms message format, raw msg: " + hexString);
    }
    if (hexString.startsWith("06050442664266")) {
       return;
    } else if (hexString.startsWith("544553542F")) {
      // Test only format: TEST/IMEI: 004401020858052/Motorola/MOTOROKR E6/R533_G_11.10.28R
      return;
    }
    throw new UnsupportedEncodingException("Invalidate destination port in enroll device sms message, raw msg: " + hexString);
    /*
    int destPort = bytes[3] * 256 + bytes[4];
    if (destPort != 16998) {
       throw new UnsupportedEncodingException("Invalidate destination port in enroll device sms message, raw msg: " + hexString);
    }
    int sourcePort = bytes[5] * 256 + bytes[6];
    if (sourcePort != 16998) {
       throw new UnsupportedEncodingException("Invalidate source port in enroll device sms message, raw msg: " + hexString);
    }
    */
  }

}
