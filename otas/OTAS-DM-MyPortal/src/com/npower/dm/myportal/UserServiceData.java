/**
 * $Header: /home/master/OTAS-DM-MyPortal/src/com/npower/dm/myportal/UserServiceData.java,v 1.1 2008/06/16 10:11:24 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/06/16 10:11:24 $
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
package com.npower.dm.myportal;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/06/16 10:11:24 $
 */
public class UserServiceData implements Serializable {

  private static final String MSISDN_PROPERTY = "MSISDN";

  private static final String IMEI_PROPERTY = "IMEI";

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Map<String, String> map = new HashMap<String, String>();
  
  /**
   * 
   */
  public UserServiceData() {
    super();
  }
  
  public String getImei() {
    return this.map.get(UserServiceData.IMEI_PROPERTY);
  }
  
  public void setImei(String value) {
    this.map.put(UserServiceData.IMEI_PROPERTY, value);
  }
  
  public String getMsisdn() {
    return this.map.get(UserServiceData.MSISDN_PROPERTY);
  }

  public void setMsisdn(String value) {
    this.map.put(UserServiceData.MSISDN_PROPERTY, value);
  }
  
  
}
