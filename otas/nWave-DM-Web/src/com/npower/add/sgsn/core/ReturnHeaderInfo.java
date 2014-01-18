/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/add/sgsn/core/ReturnHeaderInfo.java,v 1.1 2008/11/05 06:28:53 zhaowx Exp $
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


/**
 * @author Zhao wanxiang
 * @version $Revision: 1.1 $ $Date: 2008/11/05 06:28:53 $
 */

public class ReturnHeaderInfo {

  private String seriesNumber = null;
  private String versionNumber = null;
  private String createFileTime = null;
  private String fileCreatorCode = null;
  private String records = null;
  private String successRecords = null;
  private String remark = null;

  /**
   * 
   */
  public ReturnHeaderInfo() {
    super();
  }

  public String getSeriesNumber() {
    return seriesNumber;
  }

  public void setSeriesNumber(String seriesNumber) {
    this.seriesNumber = seriesNumber;
  }

  public String getVersionNumber() {
    return versionNumber;
  }

  public void setVersionNumber(String versionNumber) {
    this.versionNumber = versionNumber;
  }

  public String getCreateFileTime() {
    return createFileTime;
  }

  public void setCreateFileTime(String createFileTime) {
    this.createFileTime = createFileTime;
  }

  public String getFileCreatorCode() {
    return fileCreatorCode;
  }

  public void setFileCreatorCode(String fileCreatorCode) {
    this.fileCreatorCode = fileCreatorCode;
  }

  public String getRecords() {
    return records;
  }

  public void setRecords(String records) {
    this.records = records;
  }

  public String getSuccessRecords() {
    return successRecords;
  }

  public void setSuccessRecords(String successRecords) {
    this.successRecords = successRecords;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  
}
