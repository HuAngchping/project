/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/responder/SimpleMessageData.java,v 1.1 2008/10/22 00:15:05 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2008/10/22 00:15:05 $
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
package com.npower.dm.responder;

import java.io.UnsupportedEncodingException;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/10/22 00:15:05 $
 */
public class SimpleMessageData implements MessageData {
  private String brand = null;
  private String model = null;
  private String profileType = null;
  private byte[] rawMessage = null;

  /**
   * 
   */
  public SimpleMessageData() {
    super();
  }

  public SimpleMessageData(String brand, String model, String profileType, byte[] rawMessage) {
    super();
    this.brand = brand;
    this.model = model;
    this.profileType = profileType;
    this.rawMessage = rawMessage;
  }

  /**
   * @return Returns the brand.
   */
  public String getBrand() {
    return brand;
  }

  /**
   * @param brand The brand to set.
   */
  public void setBrand(String brand) {
    this.brand = brand;
  }

  /**
   * @return Returns the model.
   */
  public String getModel() {
    return model;
  }

  /**
   * @param model The model to set.
   */
  public void setModel(String model) {
    this.model = model;
  }

  /**
   * @return Returns the profileType.
   */
  public String getProfileType() {
    return profileType;
  }

  /**
   * @param profileType The profileType to set.
   */
  public void setProfileType(String profileType) {
    this.profileType = profileType;
  }

  /* (non-Javadoc)
   * @see com.npower.sms.responder.MessageData#getRawMessage()
   */
  public byte[] getRawMessage() {
    return rawMessage;
  }

  /* (non-Javadoc)
   * @see com.npower.sms.responder.MessageData#setRawMessage(byte[])
   */
  public void setRawMessage(byte[] rawMessage) {
    this.rawMessage = rawMessage;
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  public String toString() {
    if (this.getRawMessage() == null) {
       return null;
    }
    try {
        return new String(this.getRawMessage(), "iso8859-1");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

}
