/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/soap/common/ManufacturerWS.java,v 1.3 2008/02/01 03:59:40 LAH Exp $
  * $Revision: 1.3 $
  * $Date: 2008/02/01 03:59:40 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2007 NPower Network Software Ltd.  All rights reserved.
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
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/02/01 03:59:40 $
 */
public class ManufacturerWS implements java.io.Serializable, Comparable<ManufacturerWS> {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private String name;
  private String description;
  private String externalID;
  private String displayName;

  /**
   * Default Constructor
   */
  public ManufacturerWS() {
    super();
  }
  
  /**
   * Return the name.
   * @return
   */
  public String getName() {
    return this.name;
  }

  /**
   * Set a name for this manufacturer.
   * @param name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Return the description.
   * @return
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * Set the description.
   * @param description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Return the externalID of this manufacturer.
   * @return
   */
  public String getExternalID() {
    return this.externalID;
  }

  /**
   * Set the external ID for this manufacturer. 
   * @param manufacturerExternalId
   */
  public void setExternalID(String manufacturerExternalId) {
    this.externalID = manufacturerExternalId;
  }

  /* (non-Javadoc)
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo(ManufacturerWS o) {
    if (o == null) {
       return 1;
    }
    return this.getExternalID().compareTo(o.getExternalID());
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }


}
