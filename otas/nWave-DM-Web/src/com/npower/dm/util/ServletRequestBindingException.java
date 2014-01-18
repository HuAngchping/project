/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/util/ServletRequestBindingException.java,v 1.1 2007/02/13 04:10:40 zhao Exp $
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

import org.springframework.web.util.NestedServletException;

/**
 * Fatal binding exception, thrown when we want to treat binding exceptions as
 * unrecoverable.
 * 
 * <p>
 * Extends ServletException for convenient throwing in any Servlet resource
 * (such as a Filter), and NestedServletException for proper root cause handling
 * (as the plain ServletException doesn't expose its root cause at all).
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/02/13 04:10:40 $
 */
public class ServletRequestBindingException extends NestedServletException {

  /**
   * Constructor for ServletRequestBindingException.
   * 
   * @param msg
   *          the detail message
   */
  public ServletRequestBindingException(String msg) {
    super(msg);
  }

  /**
   * Constructor for ServletRequestBindingException.
   * 
   * @param msg
   *          the detail message
   * @param cause
   *          the root cause
   */
  public ServletRequestBindingException(String msg, Throwable cause) {
    super(msg, cause);
  }

}
