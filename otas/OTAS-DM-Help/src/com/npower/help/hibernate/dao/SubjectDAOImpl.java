package com.npower.help.hibernate.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Expression;

import com.npower.help.action.FullTextSearchExpression;
import com.npower.help.core.Subject;
import com.npower.help.dao.SubjectDAO;
import com.npower.help.hibernate.entity.SubjectEntity;

/**
 * Data access object (DAO) for domain model class SubjectEntity.
 * 
 * @see com.npower.help.dao.entity.SubjectEntity
 * @author MyEclipse Persistence Tools
 */

public class SubjectDAOImpl extends BaseHibernateDAO implements SubjectDAO {
  private static final Log   log         = LogFactory.getLog(SubjectDAOImpl.class);

  // property constants
  public static final String EXTERNAL_ID = "externalId";

  /**
   * @return
   */
  public Subject newInstance() {
    return new SubjectEntity();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.dao.SubjectDAO#save(com.npower.help.dao.SubjectEntity)
   */
  public void save(Subject transientInstance) {
    log.debug("saving SubjectEntity instance");
    try {
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
   * @see com.npower.help.dao.dao.SubjectDAO#delete(com.npower.help.dao.SubjectEntity)
   */
  public void delete(Subject persistentInstance) {
    log.debug("deleting SubjectEntity instance");
    // É¾³ýÎªnullÊ±
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
   * @see com.npower.help.dao.dao.SubjectDAO#findById(java.lang.Long)
   */
  public Subject findById(long id) {
    log.debug("getting SubjectEntity instance with id: " + id);
    try {
      SubjectEntity instance = (SubjectEntity) getSession().get(SubjectEntity.class.getName(), id);
      return instance;
    } catch (RuntimeException re) {
      log.error("get failed", re);
      throw re;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.dao.SubjectDAO#findByExample(com.npower.help.dao.SubjectEntity)
   */
  public List<Subject> findByExample(Subject instance) {
    log.debug("finding SubjectEntity instance by example");
    try {
      List<Subject> results = getCriteria().add(Example.create(instance)).list();
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
   * @see com.npower.help.dao.dao.SubjectDAO#findByProperty(java.lang.String,
   *      java.lang.Object)
   */
  public List<Subject> findByProperty(String propertyName, Object value) {
    log.debug("finding SubjectEntity instance with property: " + propertyName + ", value: " + value);
    try {
      String queryString = "from SubjectEntity as model where model." + propertyName + "= ?";
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
   * @see com.npower.help.dao.dao.SubjectDAO#findByExternalId(java.lang.Object)
   */
  public List<Subject> findByExternalId(Object externalId) {
    return findByProperty(EXTERNAL_ID, externalId);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.dao.SubjectDAO#findAll()
   */
  public List<Subject> findAll() {
    log.debug("finding all SubjectEntity instances");
    try {
      String queryString = "from SubjectEntity";
      Query queryObject = getSession().createQuery(queryString);
      return queryObject.list();
    } catch (RuntimeException re) {
      log.error("find all failed", re);
      throw re;
    }
  }

  public SubjectEntity merge(SubjectEntity detachedInstance) {
    log.debug("merging SubjectEntity instance");
    try {
      SubjectEntity result = (SubjectEntity) getSession().merge(detachedInstance);
      log.debug("merge successful");
      return result;
    } catch (RuntimeException re) {
      log.error("merge failed", re);
      throw re;
    }
  }

  public void attachDirty(SubjectEntity instance) {
    log.debug("attaching dirty SubjectEntity instance");
    try {
      getSession().saveOrUpdate(instance);
      log.debug("attach successful");
    } catch (RuntimeException re) {
      log.error("attach failed", re);
      throw re;
    }
  }

  public void attachClean(SubjectEntity instance) {
    log.debug("attaching clean SubjectEntity instance");
    try {
      getSession().lock(instance, LockMode.NONE);
      log.debug("attach successful");
    } catch (RuntimeException re) {
      log.error("attach failed", re);
      throw re;
    }
  }

  public List<Subject> findRootSubjects() {
    log.debug("finding root subjects");
    try {
      Criteria criteria = getCriteria();
      criteria.add(Expression.isNull("parent"));
      List<Subject> results = criteria.list();
      log.debug("finding root subjects, result size: " + results.size());
      return results;
    } catch (RuntimeException re) {
      log.error("finding root subjects failed", re);
      throw re;
    }
  }

  public Criteria getCriteria() {
    return getSession().createCriteria(SubjectEntity.class.getName());
  }

  public List<Subject> find4SimpleMode(Subject currentSubject, String searchText, boolean oneLevel) {
    Criteria criteria = this.getCriteria();
    if (currentSubject != null) {
      Set<Subject> scopeSet = new HashSet<Subject>();
      if (oneLevel) {
        scopeSet.add(currentSubject);
      } else {
        getAllSubjects(scopeSet, currentSubject);
      }
      criteria.add(Expression.in("parent", scopeSet));
    } else {
      criteria.add(Expression.isNull("parent"));
    }

    if (StringUtils.isNotEmpty(searchText)) {
      searchText = searchText.trim();
      criteria.createAlias("subjectContents", "content", CriteriaSpecification.LEFT_JOIN);
      Disjunction orExpression = Expression.disjunction();
      orExpression.add(new FullTextSearchExpression("externalId", searchText));
      orExpression.add(new FullTextSearchExpression("content.name", searchText));
      orExpression.add(new FullTextSearchExpression("content.content", searchText));
      orExpression.add(new FullTextSearchExpression("content.keywords", searchText));
      orExpression.add(new FullTextSearchExpression("content.description", searchText));

      criteria.add(orExpression);
    }

    List<Subject> result = criteria.list();
    return result;
  }

  private void getAllSubjects(Set<Subject> scopeSet, Subject currentSubject) {
    scopeSet.add(currentSubject);
    for (Subject child : currentSubject.getChildren()) {
      scopeSet.add(child);
      this.getAllSubjects(scopeSet, child);
    }
  }

}