/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/form/ErrorFormBean.java,v 1.2 2006/05/31 11:49:43 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2006/05/31 11:49:43 $
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

package com.npower.dm.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/** 
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2006/05/31 11:49:43 $
 * 
 * XDoclet definition:
 * @struts.form name="errorFormBeanForm"
 */
public class ErrorFormBean extends ActionForm {

  // --------------------------------------------------------- Instance Variables

  /** message property */
  private String message;

  // --------------------------------------------------------- Methods

  /** 
   * Method validate
   * @param mapping
   * @param request
   * @return ActionErrors
   */
  public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

    // TODO Auto-generated method stub
    return null;
  }

  /** 
   * Method reset
   * @param mapping
   * @param request
   */
  public void reset(ActionMapping mapping, HttpServletRequest request) {

    // TODO Auto-generated method stub
  }

  /** 
   * Returns the message.
   * @return String
   */
  public String getMessage() {
    return message;
  }

  /** 
   * Set the message.
   * @param message The message to set
   */
  public void setMessage(String message) {
    this.message = message;
  }

}

