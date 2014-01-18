/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/soap/common/ProvisionJobDispatcherService.java,v 1.1 2008/04/25 11:01:20 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/04/25 11:01:20 $
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
package com.npower.dm.soap.common;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/04/25 11:01:20 $
 */
public interface ProvisionJobDispatcherService {

  /**
   * End point of this service
   */
  public static final String SERVICE_END_POINT = "JobDispatcherService";

  /**
   * Testing
   * 
   * @param message
   * @return
   */
  public String echo(String message);

  /**
   * Return Version of DM Server
   * 
   * @return
   */
  public String version();

  /**
   * 通知JobDispatchDaemon立即调度指定的任务.
   * @param jobID
   */
  public void dispatch(String jobID);
}
