package com.npower.help.core;

import java.util.Set;

public interface SubjectLocale {

  public abstract String getId();

  public abstract void setId(String id);

  public abstract String getLanguage();

  public abstract void setLanguage(String language);

  public abstract String getCountry();

  public abstract void setCountry(String country);

  public abstract Set<SubjectContent> getSubjectContents();

  public abstract void setSubjectContents(Set<SubjectContent> subjectContents);

}