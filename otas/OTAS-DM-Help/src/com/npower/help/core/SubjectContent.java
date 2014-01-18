package com.npower.help.core;

import java.util.Date;
import java.util.Set;

public interface SubjectContent {

  public abstract long getId();

  public abstract void setId(long id);

  public abstract Subject getSubject();

  public abstract void setSubject(Subject subject);

  public abstract SubjectLocale getSubjectLocale();

  public abstract void setSubjectLocale(SubjectLocale subjectLocale);

  public abstract String getName();

  public abstract void setName(String name);

  public abstract String getDescription();

  public abstract void setDescription(String description);

  public abstract String getKeywords();

  public abstract void setKeywords(String keywords);

  public abstract String getContent();

  public abstract void setContent(String content);

  public abstract Date getLastUpdatedTime();

  public abstract void setLastUpdatedTime(Date lastUpdatedTime);

  public abstract Date getLastUpdatedBy();

  public abstract void setLastUpdatedBy(Date lastUpdatedBy);

  public abstract Set<SubjectImage> getSubjectImages();

  public abstract void setSubjectImages(Set<SubjectImage> subjectImages);

}