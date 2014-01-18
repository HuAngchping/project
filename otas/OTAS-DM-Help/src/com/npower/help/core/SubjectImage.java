package com.npower.help.core;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;

public interface SubjectImage {

  public abstract long getImageId();

  public abstract void setImageId(long imageId);

  public abstract SubjectContent getSubjectContent();

  public abstract void setSubjectContent(SubjectContent subjectContent);

  public abstract String getMimeType();

  public abstract void setMimeType(String mimeType);

  public abstract Blob getBinary();

  public abstract void setBinary(Blob binary);

  public abstract void setBinary(InputStream in) throws IOException;

  public abstract String getDescription();

  public abstract void setDescription(String description);

  public abstract String getFilename();

  public abstract void setFilename(String filename);

  public abstract long getWidth();

  public abstract void setWidth(long width);

  public abstract long getHeigth();

  public abstract void setHeigth(long heigth);

}