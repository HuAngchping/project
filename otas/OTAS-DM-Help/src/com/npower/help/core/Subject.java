package com.npower.help.core;

import java.util.Set;

public interface Subject {

  public abstract long getSubjectId();

  public abstract void setSubjectId(long subjectId);

  public abstract Subject getParent();

  public abstract void setParent(Subject subject);

  public abstract String getExternalId();

  public abstract void setExternalId(String externalId);

  public abstract Set<Subject> getChildren();

  public abstract void setChildren(Set<Subject> subjects);

  public abstract Set<SubjectContent> getSubjectContents();

  public abstract void setSubjectContents(Set<SubjectContent> subjectContents);

  public boolean isAncestors(Subject child);

}