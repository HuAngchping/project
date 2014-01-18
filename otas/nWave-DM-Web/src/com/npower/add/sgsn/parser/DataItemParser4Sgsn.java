/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/add/sgsn/parser/DataItemParser4Sgsn.java,v 1.3 2008/11/09 09:29:25 zhao Exp $
 * $Revision: 1.3 $
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
package com.npower.add.sgsn.parser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.add.sgsn.core.DataItem4sgsn;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/11/09 09:29:25 $
 */
public class DataItemParser4Sgsn implements DataItemParser {

  Log log = LogFactory.getLog(DataItemParser4Sgsn.class);
  
  private String countryCode = null;

  
  
  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  /**
   * 
   */
  public DataItemParser4Sgsn() {
    super();
  }

  
  
  /**
   * @param strPosition4imei
   */
  public DataItemParser4Sgsn(String countryCode) {
    super();
    this.countryCode = countryCode;
  }

  /* (non-Javadoc)
   * @see com.npower.sgsn.parser.DateItemParser#parse(int, java.lang.String)
   */
  public DataItem4sgsn parse(int lineNumber, String line) throws ParseException {
    if (StringUtils.isEmpty(line)) {
       return null;
    }
    String[] cols = StringUtils.split(line, ":");
    if (cols == null || cols.length < 3) {
       throw new ParseException("Error in line#" + lineNumber + ":" + line);
    }
    String imsi = cols[0];
    String msisdn = cols[1];
    String imei = cols[2];
    String time1 = cols[3];
    String time2 = cols[4];
    
    String time = time1+time2;
    
    Date  checkInvalidationDate = parseTime(time);
    imei = (!imei.toUpperCase().startsWith("IMEI:"))?"IMEI:" + imei:imei;
    
    if (msisdn.startsWith("+")) {
      msisdn = msisdn.substring(1);
    }
    if (StringUtils.isNotEmpty(this.getCountryCode()) && msisdn.startsWith(this.getCountryCode())) {
       msisdn = msisdn.substring(this.getCountryCode().length());
    }
    
    DataItem4sgsn item = new DataItem4sgsn();
    item.setLineNumber(lineNumber);
    item.setImei(imei);
    item.setImsi(imsi);
    item.setMsisdn(msisdn);
    item.setTimestamp(checkInvalidationDate);
    return item;
  }

  private Date parseTime(String time){
    SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss",Locale.CHINA);
    
    Date date = null;
    try {
        date = (Date) sdf.parse(time);
        if (log.isDebugEnabled()) {
           log.debug("sgsn item date: " + date);
        }
    } catch (Exception e) {
      log.error("parse time error, text: " + time, e);
    }
    return date;
      
  }
}
