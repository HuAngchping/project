/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/common/PhoneNumberRuleTargetDeviceCaculator.java,v 1.3 2008/07/30 10:21:27 zhao Exp $
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

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Subscriber;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SubscriberBean;
import com.npower.dm.util.NumberGenerator;
import com.npower.dm.util.NumberGeneratorFactory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/07/30 10:21:27 $
 */
public class PhoneNumberRuleTargetDeviceCaculator extends AbstractTargetDeviceCaculator implements TargetDevicesCaculator {

  private String ruleText = null;

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * 
   */
  public PhoneNumberRuleTargetDeviceCaculator() {
    super();
  }

  public PhoneNumberRuleTargetDeviceCaculator(ManagementBeanFactory factory, String ruleText) {
    super(factory);
    this.ruleText = ruleText;
  }

  public String getRuleText() {
    return ruleText;
  }

  public void setRuleText(String ruleText) {
    this.ruleText = ruleText;
  }

  /**
   * @return
   * @throws DMException
   */
  protected Criterion getExpression(Criteria criteria) throws DMException {
    if (StringUtils.isEmpty(this.getRuleText())) {
       return null;
    }
    // Instance a Number generator factory
    NumberGeneratorFactory numberFactory = NumberGeneratorFactory.newInstance();

    // Get target devices by external ID template
    NumberGenerator phoneNumberGenerator = numberFactory.getNumberGenerator(this.getRuleText());
    List<String> numbers = phoneNumberGenerator.generate();

    SubscriberBean subscriberBean = this.getFactory().createSubcriberBean();
    Collection<Subscriber> targetSubscribers = new HashSet<Subscriber>();
    for (String phoneNumber: numbers) {
        Subscriber subscriber = subscriberBean.getSubscriberByPhoneNumber(phoneNumber);
        if (subscriber != null) {
           targetSubscribers.add(subscriber);
        }
    }
    return Expression.in("subscriber", targetSubscribers);
  }

}
