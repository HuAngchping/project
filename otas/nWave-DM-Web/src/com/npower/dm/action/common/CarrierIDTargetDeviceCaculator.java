/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/common/CarrierIDTargetDeviceCaculator.java,v 1.2 2008/07/30 10:21:27 zhao Exp $
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

import java.util.Arrays;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;

import com.npower.dm.core.DMException;
import com.npower.dm.management.ManagementBeanFactory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/07/30 10:21:27 $
 */
public class CarrierIDTargetDeviceCaculator extends AbstractTargetDeviceCaculator implements TargetDevicesCaculator {

  private Long[] carrierIDs = null;
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * 
   */
  public CarrierIDTargetDeviceCaculator() {
    super();
  }

  public CarrierIDTargetDeviceCaculator(ManagementBeanFactory factory, Long[] carrierIDs) {
    super(factory);
    this.carrierIDs = carrierIDs;
  }

  public CarrierIDTargetDeviceCaculator(ManagementBeanFactory factory, String[] ids) {
    super(factory);
    if (ids != null) {
      this.carrierIDs = new Long[ids.length];
      for (int i = 0; i < ids.length; i++) {
          this.carrierIDs[i] = new Long(ids[i]);
      }
    }
  }

  public Long[] getCarrierIDs() {
    return carrierIDs;
  }

  public void setCarrierIDs(Long[] carrierIDs) {
    this.carrierIDs = carrierIDs;
  }

  /**
   * @return
   * @throws DMException
   */
  protected Criterion getExpression(Criteria criteria) throws DMException {
    if (this.carrierIDs == null || this.carrierIDs.length == 0) {
       return null;
    }
    return Expression.in("subscriber.carrier.ID", Arrays.asList(this.carrierIDs));
  }

}
