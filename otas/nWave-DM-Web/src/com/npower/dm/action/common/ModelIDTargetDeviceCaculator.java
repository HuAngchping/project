/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/common/ModelIDTargetDeviceCaculator.java,v 1.3 2008/07/30 10:21:27 zhao Exp $
 * $Revision: 1.3 $
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
 * @version $Revision: 1.3 $ $Date: 2008/07/30 10:21:27 $
 */
public class ModelIDTargetDeviceCaculator extends AbstractTargetDeviceCaculator implements TargetDevicesCaculator {

  private Long[] modelIDs = null;
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * 
   */
  public ModelIDTargetDeviceCaculator() {
    super();
  }

  public ModelIDTargetDeviceCaculator(ManagementBeanFactory factory, Long[] modelIDs) {
    super(factory);
    this.modelIDs = modelIDs;
  }

  public ModelIDTargetDeviceCaculator(ManagementBeanFactory factory, String[] ids) {
    super(factory);
    if (ids != null) {
       this.modelIDs = new Long[ids.length];
       for (int i = 0; i < ids.length; i++) {
           this.modelIDs[i] = new Long(ids[i]);
       }
    }
  }

  public Long[] getModelIDs() {
    return modelIDs;
  }

  public void setModelIDs(Long[] modelIDs) {
    this.modelIDs = modelIDs;
  }

  /**
   * @return
   * @throws DMException
   */
  protected Criterion getExpression(Criteria criteria) throws DMException {
    if (this.modelIDs == null || this.modelIDs.length == 0) {
       return null;
    }
    return Expression.in("model.ID", Arrays.asList(this.modelIDs));
  }

}
