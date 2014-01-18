/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/JobType.java,v 1.3 2008/10/23 03:14:41 zhaowx Exp $
 * $Revision: 1.3 $
 * $Date: 2008/10/23 03:14:41 $
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

import com.npower.dm.core.ProvisionJob;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/10/23 03:14:41 $
 */
public enum JobType implements Serializable {
  SEND_PROFILE        ("doAssignProfile4CP", ProvisionJob.JOB_TYPE_ASSIGN_PROFILE, ProvisionJob.JOB_MODE_CP),

  DISCOVERY             ("doDiscovery", ProvisionJob.JOB_TYPE_DISCOVERY, ProvisionJob.JOB_MODE_DM),
  ASSIGN_PROFILE        ("doAssignProfile4DM", ProvisionJob.JOB_TYPE_ASSIGN_PROFILE, ProvisionJob.JOB_MODE_DM),
  FIRMWARE              ("doFirmware", ProvisionJob.JOB_TYPE_FIRMWARE, ProvisionJob.JOB_MODE_DM),
  SCRIPT                ("doScript", ProvisionJob.JOB_TYPE_SCRIPT, ProvisionJob.JOB_MODE_DM),
//  WORKFLOW              ("doWorkflow", ProvisionJob.JOB_TYPE_WORKFLOW, ProvisionJob.JOB_MODE_DM),
  SOFTWARE_INSTALL      ("doSoftwareInstall", ProvisionJob.JOB_TYPE_SOFTWARE_INSTALL, ProvisionJob.JOB_MODE_DM),
  SOFTWARE_UN_INSTALL   ("doSoftwareUnInstall", ProvisionJob.JOB_TYPE_SOFTWARE_UN_INSTALL, ProvisionJob.JOB_MODE_DM),
  SOFTWARE_ACTIVE       ("doSoftwareActive", ProvisionJob.JOB_TYPE_SOFTWARE_ACTIVE, ProvisionJob.JOB_MODE_DM),
  SOFTWARE_DEACTIVE     ("doSoftwareDeactive", ProvisionJob.JOB_TYPE_SOFTWARE_DEACTIVE, ProvisionJob.JOB_MODE_DM),
  SOFTWARE_UPGRADE      ("doSoftwareUpgrade", ProvisionJob.JOB_TYPE_SOFTWARE_UPGRADE, ProvisionJob.JOB_MODE_DM),
  SOFTWARE_DISCOVERY    ("doSoftwareDiscovery", ProvisionJob.JOB_TYPE_SOFTWARE_DISCOVERY, ProvisionJob.JOB_MODE_DM);
  
  //SOFTWARE_INSTALL_STAGE_2      ("", "Software-Install-Stage2", ProvisionJob.JOB_MODE_DM),
  //RE_ASSIGN_PROFILE     ("un_support_re_send", "Re-Send", ProvisionJob.JOB_MODE_DM),
  //DELETE_PROFILE        ("un_support_delete_profile", "DeleteProfile", ProvisionJob.JOB_MODE_DM);
  
  private String id = null;
  private String name = null;
  private String mode = null;
  
  private JobType(String id, String name, String mode) {
    this.id = id;
    this.name = name;
    this.mode = mode;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMode() {
    return mode;
  }

  public void setMode(String mode) {
    this.mode = mode;
  }
  
  /**
   * Find a JobType by id.
   * @param id
   * @return
   */
  public static JobType valueOfByType(String id) {
    for (JobType type: values()) {
        if (type.getId().equals(id)) {
          return type;
        }
    }
    return null;
  }

}
