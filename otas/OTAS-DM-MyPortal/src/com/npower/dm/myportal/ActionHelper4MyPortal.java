/**
  * $Header: /home/master/OTAS-DM-MyPortal/src/com/npower/dm/myportal/ActionHelper4MyPortal.java,v 1.3 2008/05/29 08:02:10 zhao Exp $
  * $Revision: 1.3 $
  * $Date: 2008/05/29 08:02:10 $
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
package com.npower.dm.myportal;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.npower.dm.core.DMException;
import com.npower.dm.soap.client.DMSoapClientFactory;
import com.npower.dm.util.ConfigHelper;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/05/29 08:02:10 $
 */
public class ActionHelper4MyPortal {

  /**
   * 
   */
  private ActionHelper4MyPortal() {
    super();
  }

  /**
   * Return  DMSoapClientFactory
   * @return
   * @throws DMException
   */
  public static DMSoapClientFactory getDMSoapClientFactory() throws DMException {
    Properties props = ConfigHelper.getDMSoapClientProperties();
    DMSoapClientFactory factory = DMSoapClientFactory.newInstance(props);
    return factory;
  }

  /**
   * Get the UA from request if any otherwise use the parameter passed
   */

  public static String getUA(HttpServletRequest request) {
    String UA = "";
    if (request.getParameter("UA") != null) {
      UA = request.getParameter("UA");
    } else {
      UA = request.getHeader("User-Agent");
    }
    
    return UA;
  }

}
