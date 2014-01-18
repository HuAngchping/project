/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/common/IMEITargetDevicesCaculator.java,v 1.1 2008/07/30 10:21:27 zhao Exp $
 * $Revision: 1.1 $
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

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;

import com.npower.dm.core.DMException;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.util.NumberGenerator;
import com.npower.dm.util.NumberGeneratorFactory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/07/30 10:21:27 $
 */
public class IMEITargetDevicesCaculator extends AbstractTargetDeviceCaculator implements TargetDevicesCaculator {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private String ruleText = null;

  /**
   * 
   */
  public IMEITargetDevicesCaculator() {
    super();
  }

  public IMEITargetDevicesCaculator(ManagementBeanFactory factory, String ruleText) {
    super(factory);
    this.ruleText = ruleText;
  }

  public String getRuleText() {
    return ruleText;
  }

  public void setRuleText(String ruleText) {
    this.ruleText = ruleText;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.action.common.AbstractTargetDeviceCaculator#getCriteria()
   */
  @Override
  protected Criterion getExpression(Criteria criteria) throws DMException {
    if (StringUtils.isEmpty(this.getRuleText())) {
      return null;
    }
    // Instance a Number generator factory
    NumberGeneratorFactory numberFactory = NumberGeneratorFactory.newInstance();

    // Get target devices by external ID template
    NumberGenerator phoneNumberGenerator = numberFactory.getNumberGenerator(this.getRuleText());
    List<String> numbers = phoneNumberGenerator.generate();

    return Expression.in("externalId", numbers);
  }

}
