package com.npower.help.hibernate.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

import com.npower.help.core.SubjectImage;
import com.npower.help.dao.SubjectImageDAO;
import com.npower.help.hibernate.entity.SubjectImageEntity;

/**
 * Data access object (DAO) for domain model class SubjectImageEntity.
 * 
 * @see com.npower.help.dao.entity.SubjectImageEntity
 * @author MyEclipse Persistence Tools
 */

public class SubjectImageDAOImpl extends BaseHibernateDAO implements SubjectImageDAO {
  private static final Log   log         = LogFactory.getLog(SubjectImageDAOImpl.class);

  // property constants
  public static final String MIME_TYPE   = "mimeType";

  public static final String BINARY      = "binary";

  public static final String DESCRIPTION = "description";

  public static final String FILENAME    = "filename";

  public static final String WIDTH       = "width";

  public static final String HEIGTH      = "heigth";

  public SubjectImage newInstance() {
    return new SubjectImageEntity();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.dao.SubjectImageDAO#save(com.npower.help.dao.SubjectImageEntity)
   */
  public void save(SubjectImage transientInstance) {
    log.debug("saving SubjectImageEntity instance");
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
   * @see com.npower.help.dao.dao.SubjectImageDAO#delete(com.npower.help.dao.SubjectImageEntity)
   */
  public void delete(SubjectImage persistentInstance) {
    log.debug("deleting SubjectImageEntity instance");
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
   * @see com.npower.help.dao.dao.SubjectImageDAO#findById(java.lang.Long)
   */
  public SubjectImage findById(long id) {
    log.debug("getting SubjectImageEntity instance with id: " + id);
    try {
      Session session = getSession();
      SubjectImageEntity instance = (SubjectImageEntity) session.get(SubjectImageEntity.class.getName(), id);
      return instance;
    } catch (RuntimeException re) {
      log.error("get failed", re);
      throw re;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.dao.SubjectImageDAO#findByExample(com.npower.help.dao.SubjectImageEntity)
   */
  public List<SubjectImage> findByExample(SubjectImage instance) {
    log.debug("finding SubjectImageEntity instance by example");
    try {
      List<SubjectImage> results = getSession().createCriteria(SubjectImageEntity.class.getName()).add(
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
   * @see com.npower.help.dao.dao.SubjectImageDAO#findByProperty(java.lang.String,
   *      java.lang.Object)
   */
  public List<SubjectImage> findByProperty(String propertyName, Object value) {
    log.debug("finding SubjectImageEntity instance with property: " + propertyName + ", value: " + value);
    try {
      String queryString = "from SubjectImageEntity as model where model." + propertyName + "= ?";
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
   * @see com.npower.help.dao.dao.SubjectImageDAO#findByMimeType(java.lang.Object)
   */
  public List<SubjectImage> findByMimeType(Object mimeType) {
    return findByProperty(MIME_TYPE, mimeType);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.dao.SubjectImageDAO#findByBinary(java.lang.Object)
   */
  public List<SubjectImage> findByBinary(Object binary) {
    return findByProperty(BINARY, binary);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.dao.SubjectImageDAO#findByDescription(java.lang.Object)
   */
  public List<SubjectImage> findByDescription(Object description) {
    return findByProperty(DESCRIPTION, description);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.dao.SubjectImageDAO#findByFilename(java.lang.Object)
   */
  public List<SubjectImage> findByFilename(Object filename) {
    return findByProperty(FILENAME, filename);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.dao.SubjectImageDAO#findByWidth(java.lang.Object)
   */
  public List<SubjectImage> findByWidth(Object width) {
    return findByProperty(WIDTH, width);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.dao.SubjectImageDAO#findByHeigth(java.lang.Object)
   */
  public List<SubjectImage> findByHeigth(Object heigth) {
    return findByProperty(HEIGTH, heigth);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.dao.SubjectImageDAO#findAll()
   */
  public List<SubjectImage> findAll() {
    log.debug("finding all SubjectImageEntity instances");
    try {
      String queryString = "from SubjectImageEntity";
      Query queryObject = getSession().createQuery(queryString);
      return queryObject.list();
    } catch (RuntimeException re) {
      log.error("find all failed", re);
      throw re;
    }
  }

  public SubjectImage merge(SubjectImage detachedInstance) {
    log.debug("merging SubjectImageEntity instance");
    try {
      SubjectImage result = (SubjectImage) getSession().merge(detachedInstance);
      log.debug("merge successful");
      return result;
    } catch (RuntimeException re) {
      log.error("merge failed", re);
      throw re;
    }
  }

  public void attachDirty(SubjectImage instance) {
    log.debug("attaching dirty SubjectImageEntity instance");
    try {
      getSession().saveOrUpdate(instance);
      log.debug("attach successful");
    } catch (RuntimeException re) {
      log.error("attach failed", re);
      throw re;
    }
  }

  public void attachClean(SubjectImage instance) {
    log.debug("attaching clean SubjectImageEntity instance");
    try {
      getSession().lock(instance, LockMode.NONE);
      log.debug("attach successful");
    } catch (RuntimeException re) {
      log.error("attach failed", re);
      throw re;
    }
  }

}