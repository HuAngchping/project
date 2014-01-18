package com.npower.help.dao;

import java.util.List;

import com.npower.help.core.SubjectImage;

public interface SubjectImageDAO {

  public abstract SubjectImage newInstance();

  public abstract void save(SubjectImage transientInstance);

  public abstract void delete(SubjectImage persistentInstance);

  public abstract SubjectImage findById(long id);

  public abstract List<SubjectImage> findByExample(SubjectImage instance);

  public abstract List<SubjectImage> findByProperty(String propertyName, Object value);

  public abstract List<SubjectImage> findByMimeType(Object mimeType);

  public abstract List<SubjectImage> findByBinary(Object binary);

  public abstract List<SubjectImage> findByDescription(Object description);

  public abstract List<SubjectImage> findByFilename(Object filename);

  public abstract List<SubjectImage> findByWidth(Object width);

  public abstract List<SubjectImage> findByHeigth(Object heigth);

  public abstract List<SubjectImage> findAll();

}