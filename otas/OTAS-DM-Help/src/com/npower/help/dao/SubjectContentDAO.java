package com.npower.help.dao;

import java.util.List;

import com.npower.help.core.Subject;
import com.npower.help.core.SubjectContent;
import com.npower.help.core.SubjectLocale;

public interface SubjectContentDAO {

  // public abstract SubjectContent newInstance();

  public abstract SubjectContent newInstance(Subject subject, SubjectLocale locale);

  public abstract void save(SubjectContent transientInstance);

  public abstract void delete(SubjectContent persistentInstance);

  public abstract SubjectContent findById(long id);

  public abstract List<SubjectContent> findByExample(SubjectContent instance);

  public abstract List<SubjectContent> findByProperty(String propertyName, Object value);

  public abstract List<SubjectContent> findByName(Object name);

  public abstract List<SubjectContent> findByDescription(Object description);

  public abstract List<SubjectContent> findByKeyeords(Object keyeords);

  public abstract List<SubjectContent> findByContent(Object content);

  public abstract SubjectContent getSubjectContent(Subject subject, SubjectLocale locale);

  public abstract List<SubjectContent> findAll();

}