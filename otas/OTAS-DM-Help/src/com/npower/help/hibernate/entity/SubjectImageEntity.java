package com.npower.help.hibernate.entity;

import java.sql.Blob;

// Generated by MyEclipse Persistence Tools

/**
 * SubjectImageEntity generated by MyEclipse Persistence Tools
 */
public class SubjectImageEntity extends AbstractSubjectImage implements java.io.Serializable {

  // Constructors

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /** default constructor */
  public SubjectImageEntity() {
  }

  /** minimal constructor */
  public SubjectImageEntity(Long imageId, SubjectContentEntity subjectContent, String mimeType, Blob binary) {
    super(imageId, subjectContent, mimeType, binary);
  }

  /** full constructor */
  public SubjectImageEntity(Long imageId, SubjectContentEntity subjectContent, String mimeType, Blob binary,
      String description, String filename, Long width, Long heigth) {
    super(imageId, subjectContent, mimeType, binary, description, filename, width, heigth);
  }

}
