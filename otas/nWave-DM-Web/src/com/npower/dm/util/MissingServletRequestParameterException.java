/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/util/MissingServletRequestParameterException.java,v 1.1 2007/02/13 04:10:40 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2007/02/13 04:10:40 $
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

package com.npower.dm.util;

/**
 * ServletRequestBindingException subclass that indicates a missing parameter.
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/02/13 04:10:40 $
 */
public class MissingServletRequestParameterException extends ServletRequestBindingException {

  private String parameterName;

  private String parameterType;

  /**
   * Constructor for MissingServletRequestParameterException.
   * 
   * @param parameterName
   *          the name of the missing parameter
   * @param parameterType
   *          the expected type of the missing parameter
   */
  public MissingServletRequestParameterException(String parameterName, String parameterType) {
    super("");
    this.parameterName = parameterName;
    this.parameterType = parameterType;
  }

  public String getMessage() {
    return "Required " + this.parameterType + " parameter '" + parameterName + "' is not present";
  }

  /**
   * Return the name of the offending parameter.
   */
  public String getParameterName() {
    return this.parameterName;
  }

  /**
   * Return the expected type of the offending parameter.
   */
  public String getParameterType() {
    return this.parameterType;
  }

}
