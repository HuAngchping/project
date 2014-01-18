/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/daemon/AutoRegistrationBean.java,v 1.2 2008/08/06 07:59:23 zhao Exp $
  * $Revision: 1.2 $
  * $Date: 2008/08/06 07:59:23 $
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
package com.npower.dm.daemon;

import org.apache.commons.lang.StringUtils;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.Subscriber;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SubscriberBean;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/08/06 07:59:23 $
 */
public class AutoRegistrationBean {

  //private static Log log = LogFactory.getLog(AutoRegistrationBean.class);

  // Carrier ID for auth reg.
  private String carrierExternalID = "OTAS";
  
  private String clientUsername = "otasdm";
  
  private String clientPassword = "otasdm";
  
  private String serverPassword = "otasdm";
  
  private ManagementBeanFactory factory = null;

  /**
   * 
   */
  public AutoRegistrationBean() {
    super();
  }

  /**
   * @param factory
   * @param externalID
   * @param bean
   * @return
   * @throws DMException
   */
  /*
  private Device createDevice(String externalID, String msisdn) throws DMException {
    ModelBean modelBean = factory.createModelBean();
    DeviceBean bean = factory.createDeviceBean();
    SubscriberBean subBean = factory.createSubcriberBean();
    CarrierBean carrierBean = factory.createCarrierBean();
    
    String imei = externalID;
    if (imei.toUpperCase().startsWith("IMEI:")) {
       imei = imei.substring(5, imei.length());
    }
    Model model = modelBean.getModelbyTAC(imei);
    if (model == null) {
       throw new DMException("Could not found model for Auto Registration by TAC: " + imei);
    }
    
    Carrier carrier = carrierBean.getCarrierByExternalID(this.carrierExternalID);
    if (carrier == null) {
       throw new DMException("Could not find Carrier for auto registration service. carrierID: " + this.carrierExternalID);
    }
    try {
        // Create this device
        // Create a subscriber
        Subscriber subscriber = subBean.getSubscriberByExternalID(msisdn);
        if (subscriber != null) {
           subBean.delete(subscriber);
        }
        subscriber = subBean.getSubscriberByPhoneNumber(msisdn);
        if (subscriber != null) {
           subBean.delete(subscriber);
        }
        subscriber = subBean.newSubscriberInstance(carrier, msisdn, msisdn, this.clientPassword);
        subBean.update(subscriber);
        
        // Create a device.
        Device device = bean.newDeviceInstance(subscriber, model, externalID);
        device.setOMAClientUsername(this.clientUsername);
        device.setOMAClientPassword(this.clientPassword);
        device.setOMAServerPassword(this.serverPassword);
        device.setIsActivated(true);
        device.setSubscriberPhoneNumber(msisdn);
        bean.update(device);
        
        return device;
    } catch (Exception e) {
      throw new DMException("Failure in create a device for Auto Registration service.", e);
    } finally {
    }
  }
  */
  /**
   * Reboundle the phoneNumber with newExternalID
   * 
   * @param phoneNumber
   * @param newExternalID
   * @throws DMException
   */
  public Device bind(String msisdn, String imei) throws DMException {
    if (StringUtils.isEmpty(msisdn) || StringUtils.isEmpty(imei)) {
       return null;
    }
    
    String newExternalID = imei;
    if (!imei.toUpperCase().startsWith("IMEI:")) {
       newExternalID = "IMEI:" + imei;
    }
    DeviceEnrollMessage enrollMsg = new DeviceEnrollMessage();
    enrollMsg.setImei(newExternalID);
    enrollMsg.setMsisdn(msisdn);
    Device device = this.bind(enrollMsg);
    return device;
  }

  /**
   * Reboundle the phoneNumber with newExternalID
   * 
   * @param phoneNumber
   * @param newExternalID
   * @throws DMException
   */
  public Device bind(DeviceEnrollMessage enrollMsg) throws DMException {
    if (enrollMsg == null || StringUtils.isEmpty(enrollMsg.getImei()) || StringUtils.isEmpty(enrollMsg.getMsisdn())) {
       return null;
    }
    String imei = enrollMsg.getImei();
    if (!imei.toUpperCase().startsWith("IMEI:")) {
      imei = "IMEI:" + imei;
    }
    String msisdn = enrollMsg.getMsisdn();
    
    DeviceBean deviceBean = factory.createDeviceBean();
    Device device = deviceBean.bind(imei, msisdn, true);
    if (StringUtils.isEmpty(device.getOMAClientUsername())) {
       device.setOMAClientUsername(this.getClientUsername());
    }
    if (StringUtils.isEmpty(device.getOMAClientPassword())) {
       device.setOMAClientPassword(this.getClientPassword());
    }
    if (StringUtils.isEmpty(device.getOMAServerPassword())) {
       device.setOMAServerPassword(this.getServerPassword());
    }
    if (StringUtils.isNotEmpty(enrollMsg.getImsi())) {
       SubscriberBean subscriberBean = this.getFactory().createSubcriberBean();
       Subscriber subscriber = device.getSubscriber();
       subscriber.setIMSI(enrollMsg.getImsi());
       subscriberBean.update(subscriber );
    }
    return device;
  }

  /**
   * @return the carrierExternalID
   */
  public String getCarrierExternalID() {
    return carrierExternalID;
  }

  /**
   * @param carrierExternalID the carrierExternalID to set
   */
  public void setCarrierExternalID(String carrierExternalID) {
    this.carrierExternalID = carrierExternalID;
  }

  /**
   * @return the clientPassword
   */
  public String getClientPassword() {
    return clientPassword;
  }

  /**
   * @param clientPassword the clientPassword to set
   */
  public void setClientPassword(String clientPassword) {
    this.clientPassword = clientPassword;
  }

  /**
   * @return the clientUsername
   */
  public String getClientUsername() {
    return clientUsername;
  }

  /**
   * @param clientUsername the clientUsername to set
   */
  public void setClientUsername(String clientUsername) {
    this.clientUsername = clientUsername;
  }

  /**
   * @return the factory
   */
  public ManagementBeanFactory getFactory() {
    return factory;
  }

  /**
   * @param factory the factory to set
   */
  public void setFactory(ManagementBeanFactory factory) {
    this.factory = factory;
  }

  /**
   * @return the serverPassword
   */
  public String getServerPassword() {
    return serverPassword;
  }

  /**
   * @param serverPassword the serverPassword to set
   */
  public void setServerPassword(String serverPassword) {
    this.serverPassword = serverPassword;
  }
}
