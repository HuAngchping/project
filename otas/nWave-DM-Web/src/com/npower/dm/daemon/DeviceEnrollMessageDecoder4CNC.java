/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/daemon/DeviceEnrollMessageDecoder4CNC.java,v 1.1 2008/08/05 11:23:20 zhao Exp $
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

import java.io.UnsupportedEncodingException;

import javax.jms.Message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.dm.core.DMException;
import com.npower.util.HelperUtil;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/08/05 11:23:20 $
 */
public class DeviceEnrollMessageDecoder4CNC extends DeviceEnrollMessageDecoder {
  private static Log log = LogFactory.getLog(DeviceEnrollMessageDecoder4CNC.class);

  /**
   * 
   */
  public DeviceEnrollMessageDecoder4CNC() {
    super();
  }

  /**
   * Decode Imei, orignial hex String: 494D45490A40047014676701FFFFFFFFFFFFFFFFFFFFFFFF00
   * Result: 004400741767618
   * @param hex
   * @return
   */
  private String decodeImei(byte[] bytes) {
    StringBuffer buf = new StringBuffer(bytes.length * 2);
    for (byte b: bytes) {
        int low = b & 0x0f;
        int high = (b & 0xf0) >> 4;
        buf.append(Integer.toHexString(low));
        buf.append(Integer.toHexString(high));
    }
    // trim first "A";
    String result = buf.toString();
    result = result.substring(1, 16);
    return result;
  }
  
  /**
   * Decode Imei, orignial hex String: 494D45490A40047014676701FFFFFFFFFFFFFFFFFFFFFFFF00
   * Result: 004400741767618
   * @param rawData
   * @return
   * @throws UnsupportedEncodingException
   */
  private String parseImei(String rawData) throws UnsupportedEncodingException {
    byte[] bytes = HelperUtil.hexStringToBytes(rawData);
    String text = new String(bytes, "iso8859-1");
    if (text.indexOf("imei:") >= 0) {
       // Text mode
       String imei = text.substring(text.indexOf("imei:") + 5, text.length());
       return imei;
    } else {
      // Binary mode
      int index = text.indexOf("IMEI");
      byte[] imeiBytes = new byte[10];
      System.arraycopy(bytes, index + 4, imeiBytes, 0, 10);
      System.out.println(HelperUtil.bytesToHexString(imeiBytes));
      String result = this.decodeImei(imeiBytes);
      return result;
    }
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
        DeviceEnrollMessage enrollMsg = new DeviceEnrollMessage();
        enrollMsg.setMsisdn(msisdn);
        
        // Get Raw message
        String rawMsg = message.getStringProperty(PROPERTY_RAW_MESSAGE);
        String imei = this.parseImei(rawMsg);
        enrollMsg.setImei(imei);
        
        if (log.isDebugEnabled()) {
           log.debug("Decode enroll device message: " + enrollMsg.toString());
        }
        
        return enrollMsg;
    } catch (Exception e) {
      throw new DMException("failure to decode device enroll message.", e);
    }
    
  }

}
