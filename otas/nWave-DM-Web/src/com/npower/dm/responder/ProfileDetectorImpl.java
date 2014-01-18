/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/responder/ProfileDetectorImpl.java,v 1.3 2008/10/29 04:26:17 zhao Exp $
  * $Revision: 1.3 $
  * $Date: 2008/10/29 04:26:17 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2006 NPower Network Software Ltd.  All rights reserved.
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;

import com.npower.dm.core.Carrier;
import com.npower.dm.core.DMException;
import com.npower.dm.core.ManagementBeanFactoryAware;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.hibernate.entity.ProfileConfigEntity;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileTemplateBean;
import com.npower.dm.management.SearchBean;
import com.npower.sms.SmsAddress;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/10/29 04:26:17 $
 */
public class ProfileDetectorImpl implements ProfileDetector, ManagementBeanFactoryAware {
  private static Log log = LogFactory.getLog(ProfileDetectorImpl.class);
  
  private ManagementBeanFactory managementBeanFactory = null;
  
  private String targetCarrierExtId = null;
  
  /**
   * 存放配置类型所对应的ProfileCategory
   */
  private static Map<String, String> profiles = new HashMap<String, String>();
  
  static {
    profiles.put("mms", ProfileCategory.MMS_CATEGORY_NAME);
    profiles.put("wap", ProfileCategory.PROXY_CATEGORY_NAME);
    profiles.put("mtv", ProfileCategory.STREAMING_3GPP_CATEGORY_NAME);
  }

  /**
   * 
   */
  public ProfileDetectorImpl() {
    super();
  }

  /**
   * @return the targetCarrierExtId
   */
  public String getTargetCarrierExtId() {
    return targetCarrierExtId;
  }

  /**
   * @param targetCarrierExtId the targetCarrierExtId to set
   */
  public void setTargetCarrierExtId(String targetCarrierExtId) {
    this.targetCarrierExtId = targetCarrierExtId;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.responder.ProfileDetector#detect(com.npower.sms.SmsAddress, com.npower.dm.responder.SimpleMessageData, com.npower.sms.SmsAddress)
   */
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
   * @see com.npower.dm.responder.ProfileDetector#detect(com.npower.sms.SmsAddress, com.npower.dm.responder.SimpleMessageData, com.npower.sms.SmsAddress)
   */
  public long detect(SmsAddress from, SimpleMessageData message, SmsAddress serviceID) {
    try {
        String type = message.getProfileType();
        if (StringUtils.isNotEmpty(type) && profiles.containsKey(type)) {
           CarrierBean carrierBean = this.managementBeanFactory.createCarrierBean();
           Carrier carrier = carrierBean.getCarrierByExternalID(this.targetCarrierExtId);
           
           ProfileTemplateBean templateBean = this.managementBeanFactory.createProfileTemplateBean();
           ProfileCategory category = templateBean.getProfileCategoryByName(profiles.get(type));
           
           SearchBean searchBean = this.managementBeanFactory.createSearchBean();
           Criteria criteria = searchBean.newCriteriaInstance(ProfileConfigEntity.class);
           criteria.createAlias("profileTemplate", "template");
           criteria.add(Expression.eq("carrier", carrier));
           criteria.add(Expression.eq("isUserProfile", new Boolean(true)));
           criteria.add(Expression.eq("template.profileCategory", category));
          
           List<ProfileConfig> profiles = criteria.list();
           if (profiles.size() > 0) {
              return profiles.get(0).getID();
           }
        }
    } catch (DMException e) {
      log.error("failure to detect profile from sms: " + message.toString(), e);
    }
    return -1;
  }

}
