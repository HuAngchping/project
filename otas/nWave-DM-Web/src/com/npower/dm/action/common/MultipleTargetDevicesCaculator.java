/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/common/MultipleTargetDevicesCaculator.java,v 1.2 2008/07/30 10:21:27 zhao Exp $
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

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Expression;

import com.npower.dm.core.DMException;
import com.npower.dm.management.ManagementBeanFactory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/07/30 10:21:27 $
 */
public class MultipleTargetDevicesCaculator extends AbstractTargetDeviceCaculator implements TargetDevicesCaculator {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private List<TargetDevicesCaculator> caculators = new ArrayList<TargetDevicesCaculator>();

  /**
   * 
   */
  public MultipleTargetDevicesCaculator() {
    super();
  }

  public MultipleTargetDevicesCaculator(ManagementBeanFactory factory) {
    this.setFactory(factory);
  }

  public void setFactory(ManagementBeanFactory factory) {
    super.setFactory(factory);
    for (TargetDevicesCaculator caculator: this.getCaculators()) {
        caculator.setFactory(this.getFactory());
    }
  }

  public List<TargetDevicesCaculator> getCaculators() {
    return caculators;
  }

  public void setCaculators(List<TargetDevicesCaculator> caculators) {
    this.caculators = caculators;
  }
  
  public void addCaculator(TargetDevicesCaculator caculator) {
    this.caculators.add(caculator);
  }

  @Override
  public boolean isEmpty() throws DMException {
    for (TargetDevicesCaculator caculator: this.getCaculators()) {
        if (!caculator.isEmpty()) {
           return false;
        }
    }
    return true;
  }

  @Override
  protected Criterion getExpression(Criteria criteria) throws DMException {
    Disjunction expression = Expression.disjunction();
    for (TargetDevicesCaculator caculator: this.getCaculators()) {
        Criterion criterion = ((AbstractTargetDeviceCaculator)caculator).getExpression(criteria);
        if (criterion != null) {
           expression.add(criterion);
        }
    }
    return expression;
  }

}
