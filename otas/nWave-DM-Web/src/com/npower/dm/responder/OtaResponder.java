/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/responder/OtaResponder.java,v 1.7 2008/11/11 10:24:25 zhao Exp $
 * $Revision: 1.7 $
 * $Date: 2008/11/11 10:24:25 $
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.cp.OTAInventory;
import com.npower.dm.action.ActionHelper;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.ManagementBeanFactoryAware;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileAssignment;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.daemon.JobEvent;
import com.npower.dm.daemon.JobEventListener;
import com.npower.dm.daemon.JobEventType;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.OTAClientProvJobBean;
import com.npower.dm.management.ProfileAssignmentBean;
import com.npower.dm.management.ProfileConfigBean;
import com.npower.dm.management.ProvisionJobBean;
import com.npower.dm.util.MessageResources;
import com.npower.dm.util.MessageResourcesFactory;
import com.npower.sms.SmsAddress;
import com.npower.sms.SmsBuilder;
import com.npower.sms.SmsException;
import com.npower.sms.SmsMessage;
import com.npower.sms.client.SmsSender;
import com.npower.wap.omacp.OMACPSecurityMethod;
import com.npower.wap.omacp.notification.UIMode;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.7 $ $Date: 2008/11/11 10:24:25 $
 */
public class OtaResponder implements Responder, ManagementBeanFactoryAware {
  
  private static Log log = LogFactory.getLog(OtaResponder.class);
  
  private static MessageResources resources = null;
  
  static {
    MessageResourcesFactory factory = MessageResourcesFactory.createFactory();
    resources = factory.createResources("com/npower/dm/responder/ota_responder");
  }

  private ManagementBeanFactory managementBeanFactory = null;
  
  /**
   * 终端型号检测器
   */
  private ModelDetector modelDetector;
  
  /**
   * 配置类型检测器
   */
  private ProfileDetector profileDetector;

  /**
   * 消息解析器
   */
  private MessageParser<SimpleMessageData> parser;
  
  /**
   * 运营商ExternalID, 表明本服务对应的运营商, 
   * 服务程序将根据运营商确定其SMS Sender和ProfileConfig的来源
   */
  private String targetCarrier = "OTA_Test_Sudan";
  
  /**
   * 当使用UserPin时的PIN
   */
  private String userPin = "1234";
  
  /**
   * CP的PIN类型
   */
  private OMACPSecurityMethod pinType= OMACPSecurityMethod.USERPIN;

  private String defaultLanguage = "en";

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
  public OtaResponder() {
    super();
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
   * @return Returns the targetCarrier.
   */
  public String getTargetCarrier() {
    return targetCarrier;
  }

  /**
   * @param targetCarrier The targetCarrier to set.
   */
  public void setTargetCarrier(String targetCarrier) {
    this.targetCarrier = targetCarrier;
  }

  /**
   * @return Returns the userPin.
   */
  public String getUserPin() {
    return userPin;
  }

  /**
   * @param userPin The userPin to set.
   */
  public void setUserPin(String userPin) {
    this.userPin = userPin;
  }

  /**
   * @return Returns the pinType.
   */
  public OMACPSecurityMethod getPinType() {
    return pinType;
  }

  /**
   * @param pinType The pinType to set.
   */
  public void setPinType(OMACPSecurityMethod pinType) {
    this.pinType = pinType;
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

  /**
   * @return Returns the modelDetector.
   */
  public ModelDetector getModelDetector() {
    return modelDetector;
  }

  /**
   * @param modelDetector The modelDetector to set.
   */
  public void setModelDetector(ModelDetector modelDetector) {
    this.modelDetector = modelDetector;
  }

  /**
   * @return Returns the profileDetector.
   */
  public ProfileDetector getProfileDetector() {
    return profileDetector;
  }

  /**
   * @param profileDetector The profileDetector to set.
   */
  public void setProfileDetector(ProfileDetector profileDetector) {
    this.profileDetector = profileDetector;
  }

  /**
   * @return Returns the parser.
   */
  public MessageParser<SimpleMessageData> getParser() {
    return parser;
  }

  /**
   * @param parser The parser to set.
   */
  public void setParser(MessageParser<SimpleMessageData> parser) {
    this.parser = parser;
  }

  /**
   * 返回使用方法短信.
   * @throws SmsException
   * @throws IOException
   */
  private SmsMessage getUsageMessage() throws SmsException {
    try {
      String text = resources.getMessage(getLocale(), "ota.responder.usage.msg");
      SmsMessage msg = SmsBuilder.createUnicodeTextSms(text);
      return msg;
    } catch (SmsException e) {
      throw new SmsException("Error to create a Unicode Text SMS.", e);
    } catch (IOException e) {
      throw new SmsException("Error to create a Unicode Text SMS.", e);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.sms.responder.Responder#response(com.npower.sms.SmsAddress, byte[], com.npower.sms.SmsAddress)
   */
  public List<SmsMessage> response(SmsAddress from, byte[] message, SmsAddress serviceID) throws SmsException {
    List<SmsMessage> messages = new ArrayList<SmsMessage>();
    
    SimpleMessageData data = null;
    try {
        data = parser.parse(from, message, serviceID);
    } catch (InvalidateMessageException e) {
      // 短信内容不正确, 发送服务说明SMS
      SmsMessage msg = this.getUsageMessage();
      messages.add(msg);
      return messages;
    }
    
    long modelId = this.modelDetector.detect(from, data, serviceID);
    if (modelId <= 0) {
       // 无法识别终端型号
       // 发送提示SMS, 要求用户在SMS中输入正确的终端型号
       // 发送服务说明SMS
       SmsMessage msg = this.getUsageMessage();
       messages.add(msg);
       return messages;
    }
    
    long profileId = this.profileDetector.detect(from, data, serviceID);
    if (profileId <= 0) {
       // 无法识别需要发送的Profile
       // 发送提示SMS, 要求用户在SMS中输入正确的配置类型
       // 发送服务说明SMS
       SmsMessage msg = this.getUsageMessage();
       messages.add(msg);
       return messages;
    }
    
    try {
        // 提交任务
        String msisdn = from.getAddress();
        
        ManagementBeanFactory factory = this.managementBeanFactory;
        ProfileConfigBean profileBean = factory.createProfileConfigBean();
        Model model = factory.createModelBean().getModelByID(Long.toString(modelId));
        Carrier carrier = factory.createCarrierBean().getCarrierByExternalID(this.getTargetCarrier());
        
        if (this.eraseCountryCode) {
           // 去除来源电话的国家码
           if (msisdn.startsWith("+")) {
              msisdn = msisdn.substring(1);
           }
           if (StringUtils.isNotEmpty(carrier.getCountry().getCountryCode()) && msisdn.startsWith(carrier.getCountry().getCountryCode())) {
              msisdn = msisdn.substring(carrier.getCountry().getCountryCode().length());
           }
        }

        // Enroll a device
        DeviceBean deviceBean = factory.createDeviceBean();
        factory.beginTransaction();
        Device device = deviceBean.enroll(msisdn, model, carrier, null);
        factory.commit();

        ProfileConfig profile = profileBean.getProfileConfigByID(Long.toString(profileId));
        
        ProvisionJob job = null;
        if (!this.isAlwaysCPMode() && device.getModel().getIsOmaDmEnabled()) {
           // DM Mode
           job = this.processInDMMode(factory, carrier, device, profile, messages);
        } else {
          // CP Mode
          job = processInCPMode(factory, carrier, device, profile, messages);
        }
        
        // Send Event to Job Event Listener
        JobEventListener eventListener = ActionHelper.getJobEventListener();
        eventListener.notify(new JobEvent(JobEventType.Create, job.getID()));
    } catch (DMException e) {
      log.error("failure to create a provsion job, sms: " + data.toString(), e);
    } catch (ClassNotFoundException e) {
      log.error("failure to create a provsion job, sms: " + data.toString(), e);
    } catch (InstantiationException e) {
      log.error("failure to create a provsion job, sms: " + data.toString(), e);
    } catch (IllegalAccessException e) {
      log.error("failure to create a provsion job, sms: " + data.toString(), e);
    } catch (Exception e) {
      log.error("failure to create a provsion job, sms: " + data.toString(), e);
    }

    return messages;
  }

  /**
   * @param factory
   * @param carrier
   * @param device
   * @param profile
   * @param messages
   * @return
   * @throws Exception
   * @throws SmsException
   * @throws IOException
   * @throws DMException
   */
  private ProvisionJob processInCPMode(ManagementBeanFactory factory, Carrier carrier, Device device,
      ProfileConfig profile, List<SmsMessage> messages) throws Exception, SmsException, IOException, DMException {
    // Submit job
    OTAInventory otaInventory = ActionHelper.getOTAInventory();
    SmsSender smsSender = ActionHelper.getSmsSender(carrier);
    OTAClientProvJobBean bean = factory.createOTAClientProvJobBean(otaInventory, smsSender);
 
    int maxRetry = -1;
    long maxDuration = 0;
 
    OMACPSecurityMethod pinType = this.getPinType();
    String pin = this.getUserPin();
    if (OMACPSecurityMethod.USERPIN.equals(this.getPinType())
        || device.getSubscriber() == null
        || StringUtils.isEmpty(device.getSubscriber().getIMSI())) {
       pin = this.getUserPin();
       pinType = OMACPSecurityMethod.USERPIN;
    } else if (OMACPSecurityMethod.NETWPIN.equals(this.getPinType())) {
      pin = device.getSubscriber().getIMSI();
    }
    
    if (OMACPSecurityMethod.USERPIN.equals(this.getPinType())) {
       // 发送UserPin提示SMS
       String userPinMsg = resources.getMessage(getLocale(), "ota.responder.userPin.msg", pin);
       SmsMessage msg = SmsBuilder.createUnicodeTextSms(userPinMsg);
       messages.add(msg);
    }
    
    // No required transaction control
    ProvisionJob job = bean.provision(device, 
                                      profile, null, 
                                      pinType, pin, 
                                      System.currentTimeMillis() + 10 * 1000, maxRetry, maxDuration); 
 
    // Update job's name and description
    factory.beginTransaction();
    job.setName("Auto Responder for SMS self-service");
    job.setDescription("Auto Responder for SMS self-service");
    bean.update(job);
    factory.commit();
    return job;
  }

  /**
   * @param factory
   * @param carrier
   * @param device
   * @param profile
   * @param messages
   * @return
   * @throws Exception
   * @throws SmsException
   * @throws IOException
   * @throws DMException
   */
  private ProvisionJob processInDMMode(ManagementBeanFactory factory, Carrier carrier, Device device,
      ProfileConfig profile, List<SmsMessage> messages) throws Exception, SmsException, IOException, DMException {
    // Submit job
    boolean sendNotification = true;
    try {
        ProfileAssignmentBean assignmentBean = factory.createProfileAssignmentBean();
        ProvisionJobBean jobBean = factory.createProvisionJobBean();
        factory.beginTransaction();
        ProfileAssignment assignment = assignmentBean.newProfileAssignmentInstance(profile, device);
        
        String jobName = "Auto Responder for SMS self-service, assignment:" + profile.getName();
        String jobDescription = "Auto Responder for SMS self-service, assignment:" + profile.getName();
        Date scheduledTime = new Date();
        List<ProvisionJob> jobs = jobBean.newJobs4Assignment(assignment, jobName, jobDescription, scheduledTime );
        ProvisionJob firstJob = null;
        for (ProvisionJob job: jobs) {
          firstJob = job;
          if (sendNotification) {
             job.setUiMode(UIMode.USER_INTERACTION.toString());
             jobBean.update(job);
          }
        }
        factory.commit();
        return firstJob;
    } catch (Exception e) {
      throw e;
    } finally {
    }
  }

  /**
   * @return
   */
  private Locale getLocale() {
    Locale locale = new Locale(this.getDefaultLanguage());
    return locale;
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

}
