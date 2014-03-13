package com.weilai.swmf.hibernate;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateBaseDAO {
  
  private Transaction transaction;

  public Session getSession() {
    Session session = HibernateSessionFactory.getSession();
    return session;
  }


  public void beginTransaction() throws Exception {
    Session session = this.getSession();
    transaction = session.beginTransaction();
  }


  public void commit() throws Exception {
    this.transaction.commit();
  }


  public void rollback() throws Exception {
    this.transaction.rollback();
  }


  public void release() throws Exception {
    HibernateSessionFactory.closeSession();
  }


  public Criteria getCriteria(String className) throws Exception {
    return getSession().createCriteria(className);
  }


  public Query getQuery(String hql) throws Exception {
    return getSession().createQuery(hql);
  }


  public SQLQuery getSQLQuery(String sql) throws Exception {
    return getSession().createSQLQuery(sql);
  }

}