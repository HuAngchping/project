/**
 * $Header: /home/master/OTAS-DM-Help/src/com/npower/help/hibernate/dao/HelpDAOFactoryImpl.java,v 1.2 2008/07/25 10:47:36 hcp Exp $
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

package com.npower.help.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.npower.help.dao.HelpDAOFactory;
import com.npower.help.dao.SubjectContentDAO;
import com.npower.help.dao.SubjectDAO;
import com.npower.help.dao.SubjectImageDAO;
import com.npower.help.dao.SubjectLocaleDAO;
import com.npower.help.hibernate.HibernateSessionFactory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/07/25 10:47:36 $
 */
public class HelpDAOFactoryImpl extends HelpDAOFactory {

  private Transaction transaction;

  /**
   * 
   */
  public HelpDAOFactoryImpl() {
    super();
  }

  private Session getSession() throws Exception {
    Session session = HibernateSessionFactory.getSession();
    return session;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.HelpDAOFactory#beginTransaction()
   */
  @Override
  public void beginTransaction() throws Exception {
    Session session = this.getSession();
    transaction = session.beginTransaction();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.HelpDAOFactory#commit()
   */
  @Override
  public void commit() throws Exception {
    this.transaction.commit();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.HelpDAOFactory#release()
   */
  @Override
  public void release() throws Exception {
    HibernateSessionFactory.closeSession();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.HelpDAOFactory#rollback()
   */
  @Override
  public void rollback() throws Exception {
    this.transaction.rollback();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.HelpDAOFactory#getSubjectContentDAO()
   */
  @Override
  public SubjectContentDAO getSubjectContentDAO() throws Exception {
    SubjectContentDAOImpl dao = new SubjectContentDAOImpl();
    dao.setSession(this.getSession());
    return dao;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.HelpDAOFactory#getSubjectDAO()
   */
  @Override
  public SubjectDAO getSubjectDAO() throws Exception {
    SubjectDAOImpl dao = new SubjectDAOImpl();
    dao.setSession(this.getSession());
    return dao;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.HelpDAOFactory#getSubjectLocaleDAO()
   */
  @Override
  public SubjectLocaleDAO getSubjectLocaleDAO() throws Exception {
    SubjectLocaleDAOImpl dao = new SubjectLocaleDAOImpl();
    dao.setSession(this.getSession());
    return dao;
  }

  @Override
  public SubjectImageDAO getSubjectImageDAO() throws Exception {
    SubjectImageDAOImpl dao = new SubjectImageDAOImpl();
    dao.setSession(this.getSession());
    return dao;
  }

}
