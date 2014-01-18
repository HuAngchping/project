package com.npower.help.hibernate.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Expression;

import com.npower.help.core.Subject;
import com.npower.help.core.SubjectContent;
import com.npower.help.core.SubjectLocale;
import com.npower.help.dao.SubjectContentDAO;
import com.npower.help.hibernate.entity.SubjectContentEntity;

/**
 * Data access object (DAO) for domain model class SubjectContentEntity.
 * 
 * @see com.npower.help.dao.entity.SubjectContentEntity
 * @author MyEclipse Persistence Tools
 */

public class SubjectContentDAOImpl extends BaseHibernateDAO implements SubjectContentDAO {
  private static final Log   log         = LogFactory.getLog(SubjectContentDAOImpl.class);

  // property constants
  public static final String NAME        = "name";

  public static final String DESCRIPTION = "description";

  public static final String KEYEORDS    = "keyeords";

  public static final String CONTENT     = "content";

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.SubjectContentDAO#newInstance()
   */
  protected SubjectContent newInstance() {
    return new SubjectContentEntity();
  }

  public SubjectContent newInstance(Subject subject, SubjectLocale locale) {
    SubjectContent content = this.getSubjectContent(subject, locale);
    if (content == null) {
      content = this.newInstance();
      content.setSubject(subject);
      content.setSubjectLocale(locale);
    }
    return content;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.dao.SubjectContentDAO#save(com.npower.help.dao.SubjectContentEntity)
   */
  public void save(SubjectContent transientInstance) {
    log.debug("saving SubjectContentEntity instance");
    try {
      transientInstance.setLastUpdatedTime(new Date());
      getSession().saveOrUpdate(transientInstance);
      log.debug("save successful");
    } catch (RuntimeException re) {
      log.error("save failed", re);
      throw re;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.dao.SubjectContentDAO#delete(com.npower.help.dao.SubjectContentEntity)
   */
  public void delete(SubjectContent persistentInstance) {
    log.debug("deleting SubjectContentEntity instance");
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
   * @see com.npower.help.dao.dao.SubjectContentDAO#findById(java.lang.Long)
   */
  public SubjectContentEntity findById(long id) {
    log.debug("getting SubjectContentEntity instance with id: " + id);
    try {
      SubjectContentEntity instance = (SubjectContentEntity) getSession().get(SubjectContentEntity.class.getName(), id);
      return instance;
    } catch (RuntimeException re) {
      log.error("get failed", re);
      throw re;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.dao.SubjectContentDAO#findByExample(com.npower.help.dao.SubjectContentEntity)
   */
  @SuppressWarnings("unchecked")
  public List<SubjectContent> findByExample(SubjectContent instance) {
    log.debug("finding SubjectContentEntity instance by example");
    try {
      List<SubjectContent> results = getSession().createCriteria(SubjectContentEntity.class.getName()).add(
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
   * @see com.npower.help.dao.dao.SubjectContentDAO#findByProperty(java.lang.String,
   *      java.lang.Object)
   */
  @SuppressWarnings("unchecked")
  public List<SubjectContent> findByProperty(String propertyName, Object value) {
    log.debug("finding SubjectContentEntity instance with property: " + propertyName + ", value: " + value);
    try {
      String queryString = "from SubjectContentEntity as model where model." + propertyName + "= ?";
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
   * @see com.npower.help.dao.dao.SubjectContentDAO#findByName(java.lang.Object)
   */
  public List<SubjectContent> findByName(Object name) {
    return findByProperty(NAME, name);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.dao.SubjectContentDAO#findByDescription(java.lang.Object)
   */
  public List<SubjectContent> findByDescription(Object description) {
    return findByProperty(DESCRIPTION, description);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.dao.SubjectContentDAO#findByKeyeords(java.lang.Object)
   */
  public List<SubjectContent> findByKeyeords(Object keyeords) {
    return findByProperty(KEYEORDS, keyeords);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.dao.SubjectContentDAO#findByContent(java.lang.Object)
   */
  public List<SubjectContent> findByContent(Object content) {
    return findByProperty(CONTENT, content);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.dao.SubjectContentDAO#findAll()
   */
  @SuppressWarnings("unchecked")
  public List<SubjectContent> findAll() {
    log.debug("finding all SubjectContentEntity instances");
    try {
      String queryString = "from SubjectContentEntity";
      Query queryObject = getSession().createQuery(queryString);
      return queryObject.list();
    } catch (RuntimeException re) {
      log.error("find all failed", re);
      throw re;
    }
  }

  public SubjectContent merge(SubjectContent detachedInstance) {
    log.debug("merging SubjectContentEntity instance");
    try {
      SubjectContent result = (SubjectContent) getSession().merge(detachedInstance);
      log.debug("merge successful");
      return result;
    } catch (RuntimeException re) {
      log.error("merge failed", re);
      throw re;
    }
  }

  public void attachDirty(SubjectContent instance) {
    log.debug("attaching dirty SubjectContentEntity instance");
    try {
      getSession().saveOrUpdate(instance);
      log.debug("attach successful");
    } catch (RuntimeException re) {
      log.error("attach failed", re);
      throw re;
    }
  }

  public void attachClean(SubjectContent instance) {
    log.debug("attaching clean SubjectContentEntity instance");
    try {
      getSession().lock(instance, LockMode.NONE);
      log.debug("attach successful");
    } catch (RuntimeException re) {
      log.error("attach failed", re);
      throw re;
    }
  }

  public SubjectContent getSubjectContent(Subject subject, SubjectLocale locale) {
    log.debug("finding SubjectContentEntity instance by example");
    try {
      Criteria criteria = getSession().createCriteria(SubjectContentEntity.class.getName());
      criteria.add(Expression.eq("subject", subject));
      criteria.add(Expression.eq("subjectLocale", locale));
      List<SubjectContent> result = criteria.list();
      if (result.size() == 0) {
        return null;
      } else if (result.size() > 1) {
        throw new RuntimeException("More Subject:" + subject.getSubjectId() + " content with same locale: "
            + locale.getId());
      } else {
        return result.get(0);
      }
    } catch (RuntimeException re) {
      log.error("getSubjectContent by subject and locale failed", re);
      throw re;
    }
  }

}