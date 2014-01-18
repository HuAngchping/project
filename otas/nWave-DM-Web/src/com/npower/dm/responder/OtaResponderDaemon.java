/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/responder/OtaResponderDaemon.java,v 1.10 2008/12/25 10:53:00 zhao Exp $
 * $Revision: 1.10 $
 * $Date: 2008/12/25 10:53:00 $
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

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import com.npower.dm.action.ActionHelper;
import com.npower.dm.core.Carrier;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.server.engine.EngineConfig;
import com.npower.sms.SmsAddress;
import com.npower.sms.SmsMessage;
import com.npower.sms.client.SmsSender;
import com.npower.sms.transport.JmsSmsReceiverImpl;
import com.npower.wap.omacp.OMACPSecurityMethod;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.10 $ $Date: 2008/12/25 10:53:00 $
 */
public class OtaResponderDaemon extends AbstractResponderDaemon implements PlugIn, MessageListener {

  private static Log log = LogFactory.getLog(OtaResponderDaemon.class);
  
  // Internal Variable
  private String defaultUserPin = "1234";

  private String defaultLanguage = "en";

  private OMACPSecurityMethod defaultPinType = OMACPSecurityMethod.USERPIN;

  private String defaultTargetCarrier = null;
  
  /**
   * true - 总是使用CP Mode发送配置信息; false - 如果设备支持, 则采用DM模式
   */
  private boolean alwaysCPMode = true;

  /**
   * true - 表示去除来源终端MSISDN前缀中包含的国家前缀号码
   */
  private boolean eraseCountryCode = false;

  /**
   * 
   */
  public OtaResponderDaemon() {
    super();
  }

  /**
   * @return the defaultUserPin
   */
  public String getDefaultUserPin() {
    return defaultUserPin;
  }

  /**
   * @param defaultUserPin the defaultUserPin to set
   */
  public void setDefaultUserPin(String defaultUserPin) {
    this.defaultUserPin = defaultUserPin;
  }

  /**
   * @return the defaultLanguage
   */
  public String getDefaultLanguage() {
    return defaultLanguage;
  }

  /**
   * @param defaultLanguage the defaultLanguage to set
   */
  public void setDefaultLanguage(String defaultLanguage) {
    this.defaultLanguage = defaultLanguage;
  }

  /**
   * @return the defaultPinType
   */
  public OMACPSecurityMethod getDefaultPinType() {
    return defaultPinType;
  }

  /**
   * @param defaultPinType the defaultPinType to set
   */
  public void setDefaultPinTypeByByte(byte pinType) {
    this.defaultPinType = OMACPSecurityMethod.value(pinType);
  }

  /**
   * @param defaultPinType the defaultPinType to set
   */
  public void setDefaultPinTypeByString(String pinType) {
    this.defaultPinType = OMACPSecurityMethod.valueByString(pinType);
  }

  /**
   * @return the defaultTargetCarrier
   */
  public String getDefaultTargetCarrier() {
    return defaultTargetCarrier;
  }

  /**
   * @param defaultTargetCarrier the defaultTargetCarrier to set
   */
  public void setDefaultTargetCarrier(String defaultTargetCarrier) {
    this.defaultTargetCarrier = defaultTargetCarrier;
  }

  /**
   * @return the eraseCountryCode
   */
  public boolean isEraseCountryCode() {
    return eraseCountryCode;
  }

  /**
   * @param eraseCountryCode the eraseCountryCode to set
   */
  public void setEraseCountryCode(boolean eraseCountryCode) {
    this.eraseCountryCode = eraseCountryCode;
  }

  /**
   * @return the alwaysCPMode
   */
  public boolean isAlwaysCPMode() {
    return alwaysCPMode;
  }

  /**
   * @param alwaysCPMode the alwaysCPMode to set
   */
  public void setAlwaysCPMode(boolean alwaysCPMode) {
    this.alwaysCPMode = alwaysCPMode;
  }

  /* (non-Javadoc)
   * @see org.apache.struts.action.PlugIn#destroy()
   */
  public void destroy() {
    super.destroy();
  }

  /* (non-Javadoc)
   * @see org.apache.struts.action.PlugIn#init(org.apache.struts.action.ActionServlet, org.apache.struts.config.ModuleConfig)
   */
  public void init(ActionServlet servlet, ModuleConfig config) throws ServletException {
    super.init(servlet, config);
  }

  /* (non-Javadoc)
   * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
   */
  public void onMessage(Message message) {
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        ModelDetectorImpl modelDetector = new ModelDetectorImpl();
        modelDetector.setManagementBeanFactory(factory);
        MessageParserImpl parser = new MessageParserImpl();
        ProfileDetectorImpl profileDetector = new ProfileDetectorImpl();
        profileDetector.setManagementBeanFactory(factory);
        profileDetector.setTargetCarrierExtId(this.defaultTargetCarrier);
        
        OtaResponder responder = new OtaResponder();
        responder.setManagementBeanFactory(factory);
        responder.setModelDetector(modelDetector);
        responder.setParser(parser);
        responder.setProfileDetector(profileDetector);
        // Set parameters
        responder.setPinType(this.defaultPinType);
        responder.setUserPin(this.defaultUserPin);
        responder.setTargetCarrier(this.defaultTargetCarrier);
        responder.setDefaultLanguage(this.defaultLanguage);
        responder.setAlwaysCPMode(this.alwaysCPMode);
        responder.setEraseCountryCode(this.eraseCountryCode);
        
        String msisdn = message.getStringProperty(JmsSmsReceiverImpl.PROPERTY_DEVICE_MSISDN_NAME);
        String serviceId = message.getStringProperty(JmsSmsReceiverImpl.PROPERTY_DEVICE_SERVICE_CODE);
        String text = message.getStringProperty(JmsSmsReceiverImpl.PROPERTY_TEXT_MESSAGE);
        if (log.isDebugEnabled()) {
           log.debug("Make a response to: " + msisdn + ", request id: " + serviceId + ", text=" + text);
        }
        List<SmsMessage> messages = responder.response(new SmsAddress(msisdn), 
                                                       text.getBytes("UTF-8"), 
                                                       new SmsAddress(serviceId));

        CarrierBean carrierBean = factory.createCarrierBean();
        Carrier carrier = carrierBean.getCarrierByExternalID(defaultTargetCarrier);
        SmsSender smsSender = ActionHelper.getSmsSender(carrier);
        for (SmsMessage msg: messages) {
            smsSender.send(msg, msisdn, serviceId);
        }
    } catch (Exception e) {
      log.error("Failure to response for a incoming message.", e);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

}
