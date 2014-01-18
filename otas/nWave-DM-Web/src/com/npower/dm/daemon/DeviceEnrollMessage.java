/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/daemon/DeviceEnrollMessage.java,v 1.1 2008/08/05 11:23:20 zhao Exp $
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

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/08/05 11:23:20 $
 */
public class DeviceEnrollMessage {
  
  private String imei = null;
  private String msisdn = null;
  private String brandName = null;
  private String modelName = null;
  private String imsi = null;
  private String softwareVersion = null;

  /**
   * 
   */
  public DeviceEnrollMessage() {
    super();
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
   * @return the brandName
   */
  public String getBrandName() {
    return brandName;
  }

  /**
   * @param brandName the brandName to set
   */
  public void setBrandName(String brandName) {
    this.brandName = brandName;
  }

  /**
   * @return the modelName
   */
  public String getModelName() {
    return modelName;
  }

  /**
   * @param modelName the modelName to set
   */
  public void setModelName(String modelName) {
    this.modelName = modelName;
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
   * @return the softwareVersion
   */
  public String getSoftwareVersion() {
    return softwareVersion;
  }

  /**
   * @param softwareVersion the softwareVersion to set
   */
  public void setSoftwareVersion(String softwareVersion) {
    this.softwareVersion = softwareVersion;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  public String toString() {
    StringBuffer buf = new StringBuffer();
    buf.append("imei=");
    buf.append(this.getImei());
    buf.append(",imsi=");
    buf.append(this.getImsi());
    buf.append(",msisdn=");
    buf.append(this.getMsisdn());
    buf.append(",brand=");
    buf.append(this.getBrandName());
    buf.append(",model=");
    buf.append(this.getModelName());
    buf.append(",softwareV=");
    buf.append(this.getSoftwareVersion());
    return buf.toString();
  }
  
}
