package com.npower.help.hibernate.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.npower.help.core.SubjectLocale;
import com.npower.help.dao.SubjectLocaleDAO;
import com.npower.help.hibernate.entity.SubjectLocaleEntity;

/**
 * Data access object (DAO) for domain model class SubjectLocaleEntity.
 * 
 * @see com.npower.help.dao.entity.SubjectLocaleEntity
 * @author MyEclipse Persistence Tools
 */

public class SubjectLocaleDAOImpl extends BaseHibernateDAO implements SubjectLocaleDAO {
  private static final Log   log      = LogFactory.getLog(SubjectLocaleDAOImpl.class);

  // property constants
  public static final String LANGUAGE = "language";

  public static final String COUNTRY  = "country";

  public SubjectLocale newInstance() {
    return new SubjectLocaleEntity();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.dao.SubjectLocaleDAO#save(com.npower.help.dao.SubjectLocaleEntity)
   */
  public void save(SubjectLocale transientInstance) {
    log.debug("saving SubjectLocaleEntity instance");
    try {
      getSession().save(transientInstance);
      log.debug("save successful");
    } catch (RuntimeException re) {
      log.error("save failed", re);
      throw re;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.dao.SubjectLocaleDAO#delete(com.npower.help.dao.SubjectLocaleEntity)
   */
  public void delete(SubjectLocale persistentInstance) {
    log.debug("deleting SubjectLocaleEntity instance");
    // µ±É¾³ýÎª¿ÕÊ±
    if (null == persistentInstance) {
      return;
    }
    try {
      getSession().delete(persistentInstance);
      log.debug("delete successful");
    } catch (RuntimeException re) {
      log.error("delete failed", re);
      throw re;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.dao.SubjectLocaleDAO#findById(java.lang.String)
   */
  public SubjectLocale findById(java.lang.String id) {
    log.debug("getting SubjectLocaleEntity instance with id: " + id);
    try {
      SubjectLocaleEntity instance = (SubjectLocaleEntity) getSession().get(SubjectLocaleEntity.class.getName(), id);
      return instance;
    } catch (RuntimeException re) {
      log.error("get failed", re);
      throw re;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.dao.SubjectLocaleDAO#findByExample(com.npower.help.dao.SubjectLocaleEntity)
   */
  public List<SubjectLocale> findByExample(SubjectLocale instance) {
    log.debug("finding SubjectLocaleEntity instance by example");
    try {
      List<SubjectLocale> results = getSession().createCriteria(SubjectLocaleEntity.class.getName()).add(
          Example.create(instance)).list();
      log.debug("find by example successful, result size: " + results.size());
      return results;
    } catch (RuntimeException re) {
      log.error("find by example failed", re);
      throw re;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.dao.SubjectLocaleDAO#findByProperty(java.lang.String,
   *      java.lang.Object)
   */
  public List<SubjectLocale> findByProperty(String propertyName, Object value) {
    log.debug("finding SubjectLocaleEntity instance with property: " + propertyName + ", value: " + value);
    try {
      String queryString = "from SubjectLocaleEntity as model where model." + propertyName + "= ?";
      Query queryObject = getSession().createQuery(queryString);
      queryObject.setParameter(0, value);
      return queryObject.list();
    } catch (RuntimeException re) {
      log.error("find by property name failed", re);
      throw re;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.dao.SubjectLocaleDAO#findByLanguage(java.lang.Object)
   */
  public List<SubjectLocale> findByLanguage(Object language) {
    return findByProperty(LANGUAGE, language);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.dao.SubjectLocaleDAO#findByCountry(java.lang.Object)
   */
  public List<SubjectLocale> findByCountry(Object country) {
    return findByProperty(COUNTRY, country);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.dao.SubjectLocaleDAO#findAll()
   */
  public List<SubjectLocale> findAll() {
    log.debug("finding all SubjectLocaleEntity instances");
    try {
      String queryString = "from SubjectLocaleEntity";
      Query queryObject = getSession().createQuery(queryString);
      return queryObject.list();
    } catch (RuntimeException re) {
      log.error("find all failed", re);
      throw re;
    }
  }

  public SubjectLocale merge(SubjectLocaleEntity detachedInstance) {
    log.debug("merging SubjectLocaleEntity instance");
    try {
      SubjectLocaleEntity result = (SubjectLocaleEntity) getSession().merge(detachedInstance);
      log.debug("merge successful");
      return result;
    } catch (RuntimeException re) {
      log.error("merge failed", re);
      throw re;
    }
  }

  public void attachDirty(SubjectLocaleEntity instance) {
    log.debug("attaching dirty SubjectLocaleEntity instance");
    try {
      getSession().saveOrUpdate(instance);
      log.debug("attach successful");
    } catch (RuntimeException re) {
      log.error("attach failed", re);
      throw re;
    }
  }

  public void attachClean(SubjectLocaleEntity instance) {
    log.debug("attaching clean SubjectLocaleEntity instance");
    try {
      getSession().lock(instance, LockMode.NONE);
      log.debug("attach successful");
    } catch (RuntimeException re) {
      log.error("attach failed", re);
      throw re;
    }
  }

}