/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/JobTypeDecorator.java,v 1.1 2008/07/22 16:12:21 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/07/22 16:12:21 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2008 NPower Network Software Ltd.  All rights reserved.
 *
 * This SOURCE CODE FILE, which has been provided by NPower as part
 * of a NPower product for use ONLY by licensed users of the product,
 * includes CONFIDENTIAL and PROPRIETARY information of NPower.
 *
 * USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS
 * OF THE LICENSE STATEMENT AND LIMITED WARRANTY FURNISHED WITH
 * THE PRODUCT.
 *
 * IN PARTICULAR, YOU WILL INDEMNIFY AND HOLD NPower, ITS RELATED
 * COMPANIES AND ITS SUPPLIERS, HARMLESS FROM AND AGAINST ANY CLAIMS
 * OR LIABILITIES ARISING OUT OF THE USE, REPRODUCTION, OR DISTRIBUTION
 * OF YOUR PROGRAMS, INCLUDING ANY CLAIMS OR LIABILITIES ARISING OUT OF
 * OR RESULTING FROM THE USE, MODIFICATION, OR DISTRIBUTION OF PROGRAMS
 * OR FILES CREATED FROM, BASED ON, AND/OR DERIVED FROM THIS SOURCE
 * CODE FILE.
 * ===============================================================================================
 */
package com.npower.dm.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.struts.util.MessageResources;

import com.npower.dm.core.ProvisionJob;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/07/22 16:12:21 $
 */
public class JobTypeDecorator implements Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private String id = null;
  private String name = null;
  private String description = null;
  private String mode = ProvisionJob.JOB_MODE_DM;

  /**
   * 
   */
  public JobTypeDecorator() {
    super();
  }

  /**
   * @param id
   * @param label
   * @param description
   */
  public JobTypeDecorator(String label, String value, String description) {
    super();
    this.id = value;
    this.name = label;
    this.description = description;
  }

  /**
   * @param id
   * @param label
   * @param mode
   * @param description
   */
  public JobTypeDecorator(String label, String value, String mode, String description) {
    super();
    this.id = value;
    this.name = label;
    this.mode = mode;
    this.description = description;
  }

  /**
   * Get key of job type. This key will use to get label from resources.
   * @param jobType
   * @return
   */
  private static String getJobTypeKey(String jobType) {
    String key = "meta.job.type." + jobType + ".label";
    return key;
  }

  /**
   * Get key of job type. This key will use to get label from resources.
   * @param jobType
   * @return
   */
  private static String getJobModeKey(String jobMode) {
    String key = "meta.job.mode." + jobMode + ".label";
    return key;
  }

  /**
   * Get key of job type description. This key will use to get label from resources.
   * @param jobType
   * @return
   */
  private static String getJobTypeDescriptionKey(String jobType) {
    String key = "meta.job.type." + jobType + ".description";
    return key;
  }

  /**
   * Return Job Type options for select.
   * @return
   */
  public static List<JobTypeDecorator> getJobTypeOptions4Device(MessageResources messageResources, Locale locale) {
    List<JobTypeDecorator> types = new ArrayList<JobTypeDecorator>();
    for (JobType jobType: JobType.values()) {
      JobTypeDecorator labelValue = decorate(messageResources, locale, jobType);
        types.add(labelValue);
    }
    return types;
  }

  /**
   * @param messageResources
   * @param locale
   * @param jobType
   * @return
   */
  public static JobTypeDecorator decorate(MessageResources messageResources, Locale locale, JobType jobType) {
    String label = messageResources.getMessage(locale, getJobTypeKey(jobType.getName())); 
    String description = messageResources.getMessage(locale, getJobTypeDescriptionKey(jobType.getName())); 
    String mode = messageResources.getMessage(locale, getJobModeKey(jobType.getMode()));
    JobTypeDecorator labelValue = new JobTypeDecorator(label, jobType.getId(), mode, description);
    return labelValue;
  }

  public String getId() {
    return id;
  }

  public void setId(String value) {
    this.id = value;
  }

  public String getName() {
    return name;
  }

  public void setName(String label) {
    this.name = label;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getMode() {
    return mode;
  }

  public void setMode(String mode) {
    this.mode = mode;
  }

}
