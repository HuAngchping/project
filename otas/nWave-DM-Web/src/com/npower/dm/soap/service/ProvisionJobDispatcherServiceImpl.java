/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/soap/service/ProvisionJobDispatcherServiceImpl.java,v 1.5 2008/07/28 14:54:46 zhao Exp $
 * $Revision: 1.5 $
 * $Date: 2008/07/28 14:54:46 $
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
package com.npower.dm.soap.service;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.dm.action.ActionHelper;
import com.npower.dm.daemon.JobEvent;
import com.npower.dm.daemon.JobEventListener;
import com.npower.dm.daemon.JobEventType;
import com.npower.dm.soap.common.ProvisionJobDispatcherService;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.5 $ $Date: 2008/07/28 14:54:46 $
 */
public class ProvisionJobDispatcherServiceImpl extends BaseService implements ProvisionJobDispatcherService {

  private static Log log = LogFactory.getLog(ProvisionJobDispatcherServiceImpl.class);
  /**
   * 
   */
  public ProvisionJobDispatcherServiceImpl() {
    super();
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.soap.common.ProvisionJobDispatcherService#dispatch(java.lang.String)
   */
  public void dispatch(String jobID) {
    if (StringUtils.isEmpty(jobID)) {
       return;
    }
    try {
        // Send Event to Job Event Listener
        JobEventListener eventListener = ActionHelper.getJobEventListener();
        eventListener.notify(new JobEvent(JobEventType.Create, Long.parseLong(jobID)));
    } catch (Exception ex) {
      log.fatal("Failure in SOAP Service.", ex);
    } finally {
    }
  }

}
