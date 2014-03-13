package com.weilai.swmf.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.weilai.swmf.hibernate.HibernateBaseDAO;

@SuppressWarnings("unchecked")
public class GameBaseDAO<T> extends HibernateBaseDAO implements GameManageBaseDAO<T> {

  private static final Log log = LogFactory.getLog(GameBaseDAO.class);

  public void save(T instance) {
    log.debug("saving " + instance.getClass().getName() + " instance");
    try {
      super.getSession().saveOrUpdate(instance);
      log.debug("save successful");
    } catch (RuntimeException re) {
      log.error("save failed", re);
      throw re;
    }
  }

  public T merge(T instance) {
    log.debug("merging " + instance.getClass().getName() + " instance");
    try {
      T result = (T) super.getSession().merge(instance);
      log.debug("merge successful");
      return result;
    } catch (RuntimeException re) {
      log.error("merge failed", re);
      throw re;
    }
  }

  public void delete(T instance) {
    log.debug("deleting " + instance.getClass().getName() + " instance");
    try {
      super.getSession().delete(instance);
      log.debug("delete successful");
    } catch (RuntimeException re) {
      log.error("delete failed", re);
      throw re;
    }

  }

  public T findById(Class<T> entityClass, long id) {
    log.debug("finding " + entityClass.getName() + " instance with id: " + id);
    try {
      T instance = (T) super.getSession().get(entityClass.getName(), id);
      return instance;
    } catch (RuntimeException re) {
      log.error("get failed", re);
      throw re;
    }
  }

}
