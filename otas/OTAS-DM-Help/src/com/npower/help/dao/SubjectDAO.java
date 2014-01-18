package com.npower.help.dao;

import java.util.List;

import org.hibernate.Criteria;

import com.npower.help.core.Subject;

public interface SubjectDAO {

  public Subject newInstance();

  public abstract void save(Subject transientInstance);

  public abstract void delete(Subject persistentInstance);

  /**
   * 
   * @param id
   * @return
   */
  public abstract Subject findById(long id);

  public abstract List<Subject> findByExample(Subject instance);

  /**
   * @param propertyName
   * @param value
   * @return
   */
  public abstract List<Subject> findByProperty(String propertyName, Object value);

  public abstract List<Subject> findByExternalId(Object externalId);

  public abstract List<Subject> findAll();

  public abstract List<Subject> findRootSubjects();

  public abstract Criteria getCriteria();

  public List<Subject> find4SimpleMode(Subject currentSubject, String searchText, boolean oneLevel);
}