/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/common/TargetDevicesCaculator.java,v 1.2 2008/07/30 10:21:27 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2008/07/30 10:21:27 $
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
package com.npower.dm.action.common;

import java.io.Serializable;

import com.npower.dm.core.DMException;
import com.npower.dm.core.DeviceGroup;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.PaginatedResult;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/07/30 10:21:27 $
 */
public interface TargetDevicesCaculator extends Serializable {
  
  public void setFactory(ManagementBeanFactory factory);
  
  public PaginatedResult getPaginatedDevices(int pageNumber, int objectsPerPage) throws DMException;
  
  public int getTotalDevices() throws DMException;

  public boolean isEmpty() throws DMException;
  
  public DeviceGroup getDeviceGroup() throws DMException;
  
  //public Collection<Long> getDeviceIDs() throws DMException;

}
