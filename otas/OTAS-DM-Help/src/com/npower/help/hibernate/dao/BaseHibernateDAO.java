package com.npower.help.hibernate.dao;

import org.hibernate.Session;

/**
 * Data access object (DAO) for domain model
 * 
 * @author MyEclipse Persistence Tools
 */
public class BaseHibernateDAO implements IBaseHibernateDAO {
  private Session session = null;

  /**
   * 
   */
  public BaseHibernateDAO() {
    super();
  }

  /**
   * @param session
   */
  public BaseHibernateDAO(Session session) {
    super();
    this.session = session;
  }

  public Session getSession() {
    return this.session;
  }

  /**
   * @param session
   *            the session to set
   */
  public void setSession(Session session) {
    this.session = session;
  }

}