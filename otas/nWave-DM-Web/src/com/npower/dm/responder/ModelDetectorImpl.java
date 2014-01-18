/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/responder/ModelDetectorImpl.java,v 1.2 2008/10/30 07:42:06 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2008/10/30 07:42:06 $
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
package com.npower.dm.responder;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;

import com.npower.dm.core.DMException;
import com.npower.dm.core.ManagementBeanFactoryAware;
import com.npower.dm.core.Model;
import com.npower.dm.hibernate.entity.ModelEntity;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.sms.SmsAddress;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/10/30 07:42:06 $
 */
public class ModelDetectorImpl implements ModelDetector, ManagementBeanFactoryAware {
  private static Log log = LogFactory.getLog(ModelDetectorImpl.class);
  
  private ManagementBeanFactory managementBeanFactory = null;
  /**
   * 
   */
  public ModelDetectorImpl() {
    super();
  }

  /**
   * @return Returns the managementBeanFactory.
   */
  public ManagementBeanFactory getManagementBeanFactory() {
    return managementBeanFactory;
  }

  /**
   * @param managementBeanFactory The managementBeanFactory to set.
   */
  public void setManagementBeanFactory(ManagementBeanFactory managementBeanFactory) {
    this.managementBeanFactory = managementBeanFactory;
  }

  /* (non-Javadoc)
   * @see com.npower.sms.responder.ModelDetector#detect(com.npower.sms.SmsAddress, byte[], com.npower.sms.SmsAddress)
   */
  public long detect(SmsAddress from, SimpleMessageData msg, SmsAddress serviceID) {
    String brand = msg.getBrand();
    String modelName = msg.getModel();
    // 仅用于演示目的,测试程序
    if (msg.getProfileType().equalsIgnoreCase("mtv")) {
       if ("SonyEricsson".equalsIgnoreCase(brand)) {
          modelName = "W810a";
       } else if ("nokia".equalsIgnoreCase(brand)) {
         modelName = "6120c";
       } else {
         brand = "nokia";
         modelName = "6120c";
       }
    }
    try {
        ModelBean modelBean = this.managementBeanFactory.createModelBean();
        if (StringUtils.isNotEmpty(brand) && StringUtils.isNotEmpty(modelName)) {
           Criteria criteria = getFullExactCriteria(brand, modelName);
           List<Model> result = modelBean.findModel(criteria);
           if (result.size() == 1) {
              return result.get(0).getID();
           }
        } else if (StringUtils.isNotEmpty(modelName)) {
          Criteria criteria = getCriteriaByModel(modelName);
          List<Model> result = modelBean.findModel(criteria);
          if (result.size() == 1) {
             return result.get(0).getID();
          }
        }
    } catch (Exception ex) {
      log.error("failure to detect by model by msg: " + msg, ex);
    }
    return -1;
  }

  /**
   * 返回精确模式的查询条件
   * @param brand
   * @param modelName
   * @return
   * @throws DMException
   */
  private Criteria getFullExactCriteria(String brand, String modelName) throws DMException {
    Criteria criteria = this.managementBeanFactory.createSearchBean().newCriteriaInstance(ModelEntity.class);
    criteria.createAlias("manufacturer", "manufacturer");
    criteria.add(Expression.ilike("manufacturer.externalId", brand, MatchMode.ANYWHERE));
    criteria.add(Expression.ilike("manufacturerModelId", modelName, MatchMode.ANYWHERE));
    return criteria;
  }

  private Criteria getCriteriaByModel(String modelName) throws DMException {
    Criteria criteria = this.managementBeanFactory.createSearchBean().newCriteriaInstance(ModelEntity.class);
    criteria.add(Expression.ilike("manufacturerModelId", modelName, MatchMode.ANYWHERE));
    return criteria;
  }

}
