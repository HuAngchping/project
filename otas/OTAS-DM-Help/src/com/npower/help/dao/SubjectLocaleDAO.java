package com.npower.help.dao;

import java.util.List;

import com.npower.help.core.SubjectLocale;

public interface SubjectLocaleDAO {

  public abstract SubjectLocale newInstance();

  public abstract void save(SubjectLocale transientInstance);

  public abstract void delete(SubjectLocale persistentInstance);

  public abstract SubjectLocale findById(java.lang.String id);

  public abstract List<SubjectLocale> findByExample(SubjectLocale instance);

  public abstract List<SubjectLocale> findByProperty(String propertyName, Object value);

  public abstract List<SubjectLocale> findByLanguage(Object language);

  public abstract List<SubjectLocale> findByCountry(Object country);

  public abstract List<SubjectLocale> findAll();

}