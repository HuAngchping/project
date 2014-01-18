/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/add/sgsn/dispatcher/JobDispatcherImpl.java,v 1.7 2008/11/12 04:44:57 zhao Exp $
 * $Revision: 1.7 $
 * $Date: 2008/11/12 04:44:57 $
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

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.add.sgsn.core.DataItem4sgsn;
import com.npower.cp.OTAInventory;
import com.npower.dm.action.ActionHelper;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.core.Subscriber;
import com.npower.dm.daemon.JobEvent;
import com.npower.dm.daemon.JobEventListener;
import com.npower.dm.daemon.JobEventType;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.ClientProvAuditLogBean;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.OTAClientProvJobBean;
import com.npower.dm.management.ProfileConfigBean;
import com.npower.dm.management.SubscriberBean;
import com.npower.dm.tracking.DeviceChangeLogger;
import com.npower.dm.tracking.DeviceChangeLoggerFactory;
import com.npower.sms.client.SmsSender;
import com.npower.wap.omacp.OMACPSecurityMethod;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.7 $ $Date: 2008/11/12 04:44:57 $
 */
public class JobDispatcherImpl implements JobDispatcher {

  private static Log log = LogFactory.getLog(JobDispatcherImpl.class);

  private ManagementBeanFactory factory = null;
  
  private String carrierExternalID = null;
 
  private String profileConfigExternalID = null;
  
  private long sendIntervalInMinutes = 1;

  private String[] successFlags = null;

  /**
   * 
   */
  public JobDispatcherImpl() {
    super();
  }

  /**
   * @param factory
   */
  public JobDispatcherImpl(ManagementBeanFactory factory, String carrierExternalID, String profileConfigExternalID, long sendIntervalInMinutes, String[] successFlags) {
    super();
    this.factory = factory;
    this.carrierExternalID = carrierExternalID;
    this.profileConfigExternalID = profileConfigExternalID;
    this.sendIntervalInMinutes = sendIntervalInMinutes;
    this.successFlags = successFlags;
  }

  public long getSendIntervalInMinutes() {
    return sendIntervalInMinutes;
  }

  public void setSendIntervalInMinutes(long sendIntervalInMinutes) {
    this.sendIntervalInMinutes = sendIntervalInMinutes;
  }

  
  
  public String[] getSuccessFlags() {
    return successFlags;
  }

  public void setSuccessFlags(String[] successFlags) {
    this.successFlags = successFlags;
  }

  
  public ManagementBeanFactory getFactory() {
    return factory;
  }



  /* (non-Javadoc)
   * @see com.npower.sgsn.dispatcher.JobDispatcher#dispatch(com.npower.sgsn.core.DataItem)
   */
  public boolean dispatch(DataItem4sgsn item) throws Exception {
    String imei = item.getImei();
    String imsi = item.getImsi();
    String msisdn = item.getMsisdn();
    
    DeviceBean deviceBean = factory.createDeviceBean(); 
    ModelBean modelBean = factory.createModelBean();
    CarrierBean carrierBean = factory.createCarrierBean();
    SubscriberBean subscriberBean = factory.createSubcriberBean();
    ProfileConfigBean profileBean = factory.createProfileConfigBean();
    
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
         String subscriberExtID = msisdn;
         String password = "otasdm";
         subscriber = subscriberBean.newSubscriberInstance(carrier, subscriberExtID, msisdn, password);
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
      
      if (StringUtils.isEmpty(profileConfigExternalID)) {
         // 如果未设置发送的配置文件, 则仅注册设备, 不发送配置信息
         return true;
      }
      
      // 根据电话号码, 提取上一次发送的时间
      long latestSendTime = getLastSentTime(item);
      if (!this.needToSendProfile(item, latestSendTime)) {
        // 如果未设置发送的配置文件, 则仅注册设备, 不发送配置信息
        if (log.isDebugEnabled()) {
           Date date = new Date(latestSendTime);
          log.debug("discard auto configuration:[" + item.getImei() + ", " + item.getMsisdn() + "] last sent: " + date +  " in delay cycle: " + this.getSendIntervalInMinutes() + " minutes.");
        }
        return true;
     }
     
      // Submit job
      OTAInventory otaInventory = ActionHelper.getOTAInventory();
      SmsSender smsSender = ActionHelper.getSmsSender(device.getSubscriber().getCarrier());
      OTAClientProvJobBean bean = factory.createOTAClientProvJobBean(otaInventory, smsSender);

      int maxRetry = -1;
      long maxDuration = 0;

      ProfileConfig profile = profileBean.getProfileConfigByExternalID(profileConfigExternalID);

      // No required transaction control
      ProvisionJob job = bean.provision(device, profile, null, OMACPSecurityMethod.NETWPIN, imsi, System.currentTimeMillis(),
                                        maxRetry, maxDuration);

      // Update job's name and description
      factory.beginTransaction();
      job.setName("SGSN WAP Auto Processor4Sgsn");
      job.setDescription("SGSN WAP Auto Processor4Sgsn");
      bean.update(job);
      factory.commit();
      
      // Send Event to Job Event Listener
      JobEventListener eventListener = ActionHelper.getJobEventListener();
      eventListener.notify(new JobEvent(JobEventType.Create, job.getID()));
      
      return true;
    } catch (Exception e) {
      if (this.factory != null) {
         this.factory.rollback();
      }
      log.error("enroll device error!, date item: " + item.toString(), e);
    }
    return false;
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

  /**
   * @param item
   * @return
   * @throws DMException
   */
  protected long getLastSentTime(DataItem4sgsn item) throws DMException {
    //ManagementBeanFactory factory = null;
    try {
      //factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
      ClientProvAuditLogBean clientProvAuditLogBean = this.getFactory().createClientProvAuditLogBean();
      long latestSendTime = clientProvAuditLogBean.getLatestSendTime(item.getMsisdn(), successFlags);
      return latestSendTime;
    } catch (Exception ex) {
      throw new DMException("Failure to getLastSentTime.", ex);
    } finally {
      //if (factory != null) {
      //   factory.release();
      //}
    }
  }
  
  /**
   * 判断是否需要发送
   * @param item
   * @return
   */
  private boolean needToSendProfile(DataItem4sgsn item, long latestSendTime) {
    //long now = item.getTimestamp().getTime();
    long now = System.currentTimeMillis();
    if (now - latestSendTime < sendIntervalInMinutes * 60 *1000) {
      return false;
    } else {
      return true;
    }
    
  }
}
