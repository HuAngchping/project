/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/add/sgsn/parser/DataItemParser4CDR.java,v 1.2 2008/11/05 08:46:54 zhaowx Exp $
 * $Revision: 1.2 $
 * $Date: 2008/11/05 08:46:54 $
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

package com.npower.add.sgsn.parser;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.add.sgsn.core.DataItem4cdr;
import com.npower.add.sgsn.core.DataItem4sgsn;

/**
 * @author Zhao wanxiang
 * @version $Revision: 1.2 $ $Date: 2008/11/05 08:46:54 $
 */

public class DataItemParser4CDR implements DataItemParser {

  Log log = LogFactory.getLog(DataItemParser4CDR.class);

  /**
   * 
   */
  public DataItemParser4CDR() {
    super();
  }

  /* (non-Javadoc)
   * @see com.npower.add.sgsn.parser.DataItemParser#parse(int, java.lang.String)
   */
  public DataItem4sgsn parse(int lineNumber, String line) throws ParseException {
    if (StringUtils.isEmpty(line)) {
      throw new ParseException("Request file body is null in line#" + lineNumber + ":" + line);
    }
    String[] cols = StringUtils.split(line, " ");
    if (cols == null || cols.length < 4) {
      throw new ParseException("Error in line#" + lineNumber + ":" + line);
    }
    String serialNumber = cols[0];
    String msisdn= cols[1];
    String imei= cols[2];
    String imsi= cols[3];
      
    imei = (!imei.toUpperCase().startsWith("IMEI:"))?"IMEI:" + imei:imei;
    if (msisdn.startsWith("+")) {
      msisdn = msisdn.substring(1);
    }
    if (msisdn.startsWith("86")) {
      msisdn = msisdn.substring(2);
    }
   
    DataItem4cdr item = new DataItem4cdr();
    item.setLineNumber(lineNumber);
    item.setImei(imei);
    item.setImsi(imsi);
    item.setMsisdn(msisdn);
    item.setSerialNumber(serialNumber);
    return item;
  }

}
