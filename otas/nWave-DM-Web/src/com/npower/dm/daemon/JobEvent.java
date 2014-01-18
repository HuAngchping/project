/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/daemon/JobEvent.java,v 1.1 2008/07/26 07:52:56 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/07/26 07:52:56 $
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
package com.npower.dm.daemon;

import java.io.Serializable;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/07/26 07:52:56 $
 */
public class JobEvent implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private JobEventType type = null;
  
  private long jobID = 0;
  
  /**
   * 
   */
  public JobEvent() {
    super();
  }

  public JobEvent(JobEventType type, long jobID) {
    super();
    this.type = type;
    this.jobID = jobID;
  }

  /**
   * @return the type
   */
  public JobEventType getType() {
    return type;
  }

  /**
   * @param type the type to set
   */
  public void setType(JobEventType type) {
    this.type = type;
  }

  /**
   * @return the jobID
   */
  public long getJobID() {
    return jobID;
  }

  /**
   * @param jobID the jobID to set
   */
  public void setJobID(long jobID) {
    this.jobID = jobID;
  }

}
