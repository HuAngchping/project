/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/aop/audit/BaseRecipe.java,v 1.1 2007/02/06 10:45:05 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2007/02/06 10:45:05 $
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
package com.npower.dm.aop.audit;

import com.npower.dm.audit.AuditLoggerFactory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/02/06 10:45:05 $
 */
public class BaseRecipe {

  /**
   * Default constructor
   */
  public BaseRecipe() {
    super();
  }
  
  /**
   * Return an instance of AuditLoggerFactory.
   * @return
   */
  protected AuditLoggerFactory getAuditLogFactory() {
    AuditLoggerFactory factory = AuditLoggerFactory.newInstance();
    return factory;
  }

}
