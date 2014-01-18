/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/daemon/DeviceEnrollMessageDecoder4UniCom.java,v 1.2 2008/11/09 09:29:25 zhao Exp $
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


import javax.jms.Message;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.dm.core.DMException;
import com.npower.util.HelperUtil;

/**
 * @author chenlei
 * @version $Revision: 1.2 $ $Date: 2008/11/09 09:29:25 $
 */

public class DeviceEnrollMessageDecoder4UniCom extends DeviceEnrollMessageDecoder {

  private static Log log = LogFactory.getLog(DeviceEnrollMessageDecoder4UniCom.class);

  /**
   * 
   */
  public DeviceEnrollMessageDecoder4UniCom() {
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

      String text = new String(bytes, "iso8859-1");
      String[] columns = StringUtils.split(text, ' ');
      enrollMsg.setImsi(columns[0].trim());
      
      String imei = columns[1].trim();
      imei = imei.toUpperCase();
      imei = (imei.startsWith("IMEI:"))?imei:"IMEI:" + imei;
      enrollMsg.setImei(imei);
      
      String brandName = columns[2];
      String modelName = columns[3];
      enrollMsg.setBrandName(brandName.trim());
      enrollMsg.setModelName(modelName.trim());
      enrollMsg.setSoftwareVersion(columns[4].trim());

      if (log.isDebugEnabled()) {
        log.debug("Decode enroll device message: " + enrollMsg.toString());
      }

      return enrollMsg;
    } catch (Exception e) {
      throw new DMException("failure to decode device enroll message.", e);
    }
  }

}
