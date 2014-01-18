/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/add/sgsn/core/DataItem4sgsn.java,v 1.1 2008/11/05 06:28:53 zhaowx Exp $
 * $Revision: 1.1 $
 * $Date: 2008/11/05 06:28:53 $
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
package com.npower.add.sgsn.core;

import java.util.Date;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/11/05 06:28:53 $
 */
public class DataItem4sgsn {
  
  private int lineNumber = 0;
  
  private String imei = null;
  private String imsi = null;
  private String msisdn = null;
  private String wapGateway = null;
  private Date timestamp = null;
  

  /**
   * 
   */
  public DataItem4sgsn() {
    super();
  }


  /**
   * @return the lineNumber
   */
  public int getLineNumber() {
    return lineNumber;
  }


  /**
   * @param lineNumber the lineNumber to set
   */
  public void setLineNumber(int lineNumber) {
    this.lineNumber = lineNumber;
  }


  /**
   * @return the imei
   */
  public String getImei() {
    return imei;
  }


  /**
   * @param imei the imei to set
   */
  public void setImei(String imei) {
    this.imei = imei;
  }


  /**
   * @return the imsi
   */
  public String getImsi() {
    return imsi;
  }


  /**
   * @param imsi the imsi to set
   */
  public void setImsi(String imsi) {
    this.imsi = imsi;
  }


  /**
   * @return the msisdn
   */
  public String getMsisdn() {
    return msisdn;
  }


  /**
   * @param msisdn the msisdn to set
   */
  public void setMsisdn(String msisdn) {
    this.msisdn = msisdn;
  }


  /**
   * @return the wapGateway
   */
  public String getWapGateway() {
    return wapGateway;
  }


  /**
   * @param wapGateway the wapGateway to set
   */
  public void setWapGateway(String wapGateway) {
    this.wapGateway = wapGateway;
  }


  /**
   * @return the timestamp
   */
  public Date getTimestamp() {
    return timestamp;
  }


  /**
   * @param timestamp the timestamp to set
   */
  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

}
