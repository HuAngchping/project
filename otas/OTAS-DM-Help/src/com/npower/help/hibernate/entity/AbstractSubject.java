package com.npower.help.hibernate.entity;

import java.util.HashSet;
import java.util.Set;

import com.npower.help.core.Subject;
import com.npower.help.core.SubjectContent;

/**
 * AbstractSubject generated by MyEclipse Persistence Tools
 */

public abstract class AbstractSubject implements java.io.Serializable, Subject {

  // Fields

  private long                subjectId;

  private Subject             parent;

  private String              externalId;

  private Set<Subject>        children        = new HashSet<Subject>(0);

  private Set<SubjectContent> subjectContents = new HashSet<SubjectContent>(0);

  // Constructors

  /** default constructor */
  public AbstractSubject() {
  }

  /** minimal constructor */
  public AbstractSubject(Long subjectId, String externalId) {
    this.subjectId = subjectId;
    this.externalId = externalId;
  }

  /** full constructor */
  public AbstractSubject(Long subjectId, SubjectEntity subject, String externalId, Set<Subject> subjects,
      Set<SubjectContent> subjectContents) {
    this.subjectId = subjectId;
    this.parent = subject;
    this.externalId = externalId;
    this.children = subjects;
    this.subjectContents = subjectContents;
  }

  // Property accessors

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.Subject#getSubjectId()
   */
  public long getSubjectId() {
    return this.subjectId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.Subject#setSubjectId(java.lang.Long)
   */
  public void setSubjectId(long subjectId) {
    this.subjectId = subjectId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.Subject#getParent()
   */
  public Subject getParent() {
    return this.parent;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.Subject#setParent(com.npower.help.dao.SubjectEntity)
   */
  public void setParent(Subject subject) {
    this.parent = subject;
    if (this.parent != null) {
      // this.parent.getChildren().add((SubjectEntity) this);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.Subject#getExternalId()
   */
  public String getExternalId() {
    return this.externalId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.Subject#setExternalId(java.lang.String)
   */
  public void setExternalId(String externalId) {
    this.externalId = externalId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.Subject#getChildren()
   */
  public Set<Subject> getChildren() {
    return this.children;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.Subject#setChildren(java.util.Set)
   */
  public void setChildren(Set<Subject> subjects) {
    this.children = subjects;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.Subject#getSubjectContents()
   */
  public Set<SubjectContent> getSubjectContents() {
    return this.subjectContents;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.help.dao.Subject#setSubjectContents(java.util.Set)
   */
  public void setSubjectContents(Set<SubjectContent> subjectContents) {
    this.subjectContents = subjectContents;
  }

}