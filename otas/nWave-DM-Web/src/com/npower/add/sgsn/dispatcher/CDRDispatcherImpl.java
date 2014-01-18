/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/add/sgsn/dispatcher/CDRDispatcherImpl.java,v 1.3 2008/11/12 08:30:01 zhao Exp $
 * $Revision: 1.3 $
 * $Date: 2008/11/12 08:30:01 $
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

package com.npower.add.sgsn.dispatcher;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.add.sgsn.core.DataItem4sgsn;
import com.npower.add.sgsn.dispatcher.JobDispatcherImpl;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.Subscriber;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.SubscriberBean;
import com.npower.dm.tracking.DeviceChangeLogger;
import com.npower.dm.tracking.DeviceChangeLoggerFactory;

/**
 * @author Zhao wanxiang
 * @version $Revision: 1.3 $ $Date: 2008/11/12 08:30:01 $
 */

public class CDRDispatcherImpl implements JobDispatcher {

  private static Log log = LogFactory.getLog(JobDispatcherImpl.class);

  private ManagementBeanFactory factory = null;
  
  private String carrierExternalID = null;

  /**
   * 
   */
  public CDRDispatcherImpl() {
    super();
  }

  
  
  /**
   * @param factory
   * @param carrierExternalID
   */
  public CDRDispatcherImpl(ManagementBeanFactory factory, String carrierExternalID) {
    super();
    this.factory = factory;
    this.carrierExternalID = carrierExternalID;
  }



  public String getCarrierExternalID() {
    return carrierExternalID;
  }



  public void setCarrierExternalID(String carrierExternalID) {
    this.carrierExternalID = carrierExternalID;
  }



  /* (non-Javadoc)
   * @see com.npower.unicom.CRDDispatcher#dispatch(com.npower.add.sgsn.core.DataItem4sgsn)
   */
  public boolean dispatch(DataItem4sgsn item) throws Exception {
    String imei = item.getImei();
    String imsi = item.getImsi();
    String msisdn = item.getMsisdn();
    
    DeviceBean deviceBean = factory.createDeviceBean(); 
    ModelBean modelBean = factory.createModelBean();
    CarrierBean carrierBean = factory.createCarrierBean();
    SubscriberBean subscriberBean = factory.createSubcriberBean();
    
    try {
      // Enroll Device and subscriber
      Model model = modelBean.getModelbyTAC(imei);
      if (model == null) {
         Manufacturer manufacturer = modelBean.getManufacturerByExternalID("NOKIA");
         model = modelBean.getModelByManufacturerModelID(manufacturer, "N80");
      }
      Carrier carrier = carrierBean.getCarrierByExternalID(carrierExternalID);
      
      factory.beginTransaction();
      Subscriber subscriber = subscriberBean.getSubscriberByPhoneNumber(msisdn);
      if (subscriber == null) {
         String password = "otasdm";
         subscriber = subscriberBean.newSubscriberInstance(carrier, msisdn, msisdn, password);
      }
      subscriber.setIMSI(imsi);

      subscriberBean.update(subscriber);

      Device device = deviceBean.getDeviceByExternalID(imei);
      if (device == null) {
         device = deviceBean.newDeviceInstance(subscriber, model, imei);
      } else {
        device.setSubscriber(subscriber);
      }
      device.setSubscriberPhoneNumber(subscriber.getPhoneNumber());
      deviceBean.update(device);
      factory.commit();
      
      // 记录到设备变化日志
      this.track(imei, imsi, msisdn);
      
      return true;
    } catch (Exception e) {
      if (this.factory != null) {
         this.factory.rollback();
      }
      log.error("enroll device error!, date item: " + item.toString(), e);
      throw new DMException("enroll device error",e);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.unicom.CRDDispatcher#getFactory()
   */
  public ManagementBeanFactory getFactory() {
    return factory;
  }

  /**
   * Tracking it and save into device change log.
   * @param enrollMsg
   */
  private void track(String imei, String imsi, String msisdn) {
    DeviceChangeLoggerFactory logFactory = null;
    try {
      logFactory = DeviceChangeLoggerFactory.newInstance();
      DeviceChangeLogger logger = logFactory.getLogger();
      logger.log(imei, msisdn, imsi, null, null, null);
    } catch (Exception ex) {
      log.error("Failure to write a device change log: " + imei + ", " + imsi + ", " + msisdn, ex);
    } finally {
    }
  }

}
