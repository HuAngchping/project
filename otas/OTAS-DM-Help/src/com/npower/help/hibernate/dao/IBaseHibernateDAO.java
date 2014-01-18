package com.npower.help.hibernate.dao;

import org.hibernate.Session;

/**
 * Data access interface for domain model
 * 
 * @author MyEclipse Persistence Tools
 */
public interface IBaseHibernateDAO {
  
  public Session getSession();

  public void setSession(Session session);
  
}