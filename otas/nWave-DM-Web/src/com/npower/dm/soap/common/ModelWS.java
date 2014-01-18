/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/soap/common/ModelWS.java,v 1.4 2008/01/23 10:39:10 zhao Exp $
 * $Revision: 1.4 $
 * $Date: 2008/01/23 10:39:10 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2006 NPower Network Software Ltd.  All rights reserved.
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
package com.npower.dm.soap.common;



/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2008/01/23 10:39:10 $
 */
public class ModelWS implements java.io.Serializable, Comparable<ModelWS> {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String manufacturerExternalID;

  private String externalID;

  private String name;

  private String description;

  private boolean supportedDownloadMethods;

  private boolean isOmaDmEnabled;

  private String serverAuthType;
  
  private byte[] iconImage;


  /** default constructor */
  public ModelWS() {
    super();
  }


  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }


  /**
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }


  /**
   * @return the externalID
   */
  public String getExternalID() {
    return externalID;
  }


  /**
   * @param externalID the externalID to set
   */
  public void setExternalID(String externalID) {
    this.externalID = externalID;
  }



  /**
   * @return the isOMAEnabled
   */
  public boolean isOmaDmEnabled() {
    return isOmaDmEnabled;
  }


  /**
   * @param isOMAEnabled the isOMAEnabled to set
   */
  public void setOmaDmEnabled(boolean isOMAEnabled) {
    this.isOmaDmEnabled = isOMAEnabled;
  }



  /**
   * @return the manufacturerExternalID
   */
  public String getManufacturerExternalID() {
    return manufacturerExternalID;
  }


  /**
   * @param manufacturerExternalID the manufacturerExternalID to set
   */
  public void setManufacturerExternalID(String manufacturerExternalID) {
    this.manufacturerExternalID = manufacturerExternalID;
  }


  /**
   * @return the name
   */
  public String getName() {
    return name;
  }


  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }


  /**
   * @return the serverAuthType
   */
  public String getServerAuthType() {
    return serverAuthType;
  }


  /**
   * @param serverAuthType the serverAuthType to set
   */
  public void setServerAuthType(String serverAuthType) {
    this.serverAuthType = serverAuthType;
  }


  /**
   * @return the supportedDownloadMethods
   */
  public boolean isSupportedDownloadMethods() {
    return supportedDownloadMethods;
  }


  /**
   * @param supportedDownloadMethods the supportedDownloadMethods to set
   */
  public void setSupportedDownloadMethods(boolean supportedDownloadMethods) {
    this.supportedDownloadMethods = supportedDownloadMethods;
  }


  /* (non-Javadoc)
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo(ModelWS o) {
    if (o == null) {
       return 1;
    }
    return this.getExternalID().compareTo(o.getExternalID());
    
  }


  public byte[] getIconImage() {
    return iconImage;
  }


  public void setIconImage(byte[] iconImage) {
    this.iconImage = iconImage;
  }

}