/**
 * $Header: /home/master/OTAS-DM-Help/src/com/npower/help/dao/HelpDAOFactory.java,v 1.2 2008/07/25 10:47:36 hcp Exp $
 * $Revision: 1.2 $
 * $Date: 2008/07/25 10:47:36 $
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

package com.npower.help.dao;

import com.npower.help.hibernate.dao.HelpDAOFactoryImpl;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/07/25 10:47:36 $
 */
public abstract class HelpDAOFactory {

  /**
   * Constructor
   */
  protected HelpDAOFactory() {
    super();
  }

  /**
   * Create a factory instance
   * 
   * @return
   */
  public static HelpDAOFactory newInstance() throws Exception {
    return new HelpDAOFactoryImpl();
  }

  /**
   * Begin transaction
   */
  public abstract void beginTransaction() throws Exception;

  /**
   * Commit transaction
   */
  public abstract void commit() throws Exception;

  /**
   * Roll-back transaction
   */
  public abstract void rollback() throws Exception;

  /**
   * Release connection with database
   */
  public abstract void release() throws Exception;

  /**
   * Return an instance of SubjectDAOImpl
   * 
   * @return
   */
  public abstract SubjectDAO getSubjectDAO() throws Exception;

  /**
   * Return an instance of SubjectLocaleDAOImpl
   * 
   * @return
   */
  public abstract SubjectLocaleDAO getSubjectLocaleDAO() throws Exception;

  /**
   * Return an instance of SubjectContentDAOImpl
   * 
   * @return
   */
  public abstract SubjectContentDAO getSubjectContentDAO() throws Exception;

  /**
   * Return an instance of SubjectImageDAOImpl
   * 
   * @return
   */
  public abstract SubjectImageDAO getSubjectImageDAO() throws Exception;

}
