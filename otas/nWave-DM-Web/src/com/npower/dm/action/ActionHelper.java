/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/ActionHelper.java,v 1.36 2008/11/11 14:04:43 zhao Exp $
  * $Revision: 1.36 $
  * $Date: 2008/11/11 14:04:43 $
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
package com.npower.dm.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.DynaValidatorForm;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.npower.cp.OTAException;
import com.npower.cp.OTAInventory;
import com.npower.dm.action.device.EditJobAction;
import com.npower.dm.bootstrap.BootstrapService;
import com.npower.dm.bootstrap.BootstrapServiceFactory;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.Image;
import com.npower.dm.core.ImageUpdateStatus;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.ProfileAssignment;
import com.npower.dm.core.ProfileAttribute;
import com.npower.dm.core.ProfileAttributeType;
import com.npower.dm.core.ProfileAttributeValue;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.core.ServiceProvider;
import com.npower.dm.daemon.JobEventListener;
import com.npower.dm.daemon.JobEventListenerFactory;
import com.npower.dm.hibernate.entity.AuditLogAction;
import com.npower.dm.hibernate.entity.ManufacturerEntity;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.CountryBean;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileAssignmentBean;
import com.npower.dm.management.ProfileConfigBean;
import com.npower.dm.management.ProvisionJobBean;
import com.npower.dm.management.SearchBean;
import com.npower.dm.management.ServiceProviderBean;
import com.npower.dm.management.UpdateImageBean;
import com.npower.dm.notifcation.NotificationService;
import com.npower.dm.notifcation.NotificationServiceFactory;
import com.npower.dm.security.DMWebPrincipal;
import com.npower.dm.util.ConfigHelper;
import com.npower.sms.SmsException;
import com.npower.sms.client.SmsSender;
import com.npower.sms.client.SmsSenderFactory;
import com.npower.wap.omacp.notification.Initiator;
import com.npower.wap.omacp.notification.UIMode;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.36 $ $Date: 2008/11/11 14:04:43 $
 */
public class ActionHelper {

  /**
   * 
   */
  private ActionHelper() {
    super();
  }
  
  /**
   * Return current user.
   * @param request
   *        HttpServletRequest
   * @return
   *        Current user
   * @throws DMException
   */
  public static DMWebPrincipal getDMWebPrincipal(HttpServletRequest request) throws DMException {
    HttpSession session = request.getSession(false);
    DMWebPrincipal user = (DMWebPrincipal) session.getAttribute(Constants.ADMIN_USER_KEY);
    return user;
  }

  /**
   * Return all of carriers for select options.
   * @param factory
   * @return
   * @throws DMException
   */
  public static Collection<LabelValueBean> getCarrierOptions(ManagementBeanFactory factory) throws DMException {
    CarrierBean carrierBean = factory.createCarrierBean();
    List<Carrier> list = carrierBean.getAllCarriers();
    Collection<LabelValueBean> result = new ArrayList<LabelValueBean>();
    for (Carrier carrier: list) {
        LabelValueBean labelValue = new LabelValueBean(carrier.getName(), "" + carrier.getID());
        result.add(labelValue);
    }
    return result;
  }

  /**
   * Return all of ServiceProviders for select options.
   * @param factory
   * @return
   * @throws DMException
   */
  public static Collection<LabelValueBean> getServiceProviderOptions(ManagementBeanFactory factory) throws DMException {
    ServiceProviderBean bean = factory.createServiceProviderBean();
    List<ServiceProvider> list = bean.getAllServiceProviders();
    Collection<LabelValueBean> result = new ArrayList<LabelValueBean>();
    for (ServiceProvider sp: list) {
        LabelValueBean labelValue = new LabelValueBean(sp.getExternalID(), "" + sp.getID());
        result.add(labelValue);
    }
    return result;
  }

  /**
   * Return all of carriers for select options.
   * @param factory
   * @return
   * @throws DMException
   */
  public static Collection<LabelValueBean> getCarrierExternalIDOptions(ManagementBeanFactory factory) throws DMException {
    CarrierBean carrierBean = factory.createCarrierBean();
    List<Carrier> list = carrierBean.getAllCarriers();
    Collection<LabelValueBean> result = new ArrayList<LabelValueBean>();
    for (Carrier carrier: list) {
        LabelValueBean labelValue = new LabelValueBean(carrier.getExternalID(), carrier.getExternalID());
        result.add(labelValue);
    }
    return result;
  }

  /**
   * Return all of countries for select options.
   * @param factory
   * @return
   * @throws DMException
   */
  public static Collection<LabelValueBean> getCountryOptions(ManagementBeanFactory factory) throws DMException {
    CountryBean countryBean = factory.createCountryBean();
    List<Country> list = countryBean.getAllCountries();
    Collection<LabelValueBean> result = new ArrayList<LabelValueBean>();
    for (Country country: list) {
        LabelValueBean labelValue = new LabelValueBean(country.getName(), "" + country.getID());
        result.add(labelValue);
    }
    return result;
  }

  /**
   * Return a options list for Manufacturers.
   * @param factory
   * @return
   * @throws DMException
   */
  public static Collection<LabelValueBean> getManufacturerOptions(ManagementBeanFactory factory, HttpServletRequest request) throws DMException {
    DMWebPrincipal principal = ActionHelper.getDMWebPrincipal(request);

    SearchBean searchBean = factory.createSearchBean();
    Criteria criteria = searchBean.newCriteriaInstance(ManufacturerEntity.class);
    criteria.addOrder(Order.asc("externalId"));

    List<Manufacturer> list = criteria.list();
    Collection<LabelValueBean> result = new ArrayList<LabelValueBean>();
    for (Manufacturer manufacturer: list) {
        if (principal.isOwnManufacturerExternalID(manufacturer.getExternalId())) {
           LabelValueBean labelValue = new LabelValueBean(manufacturer.getExternalId(), "" + manufacturer.getID());
           result.add(labelValue);
        }
    }
    return result;
  }

  /**
   * Return a options list for Manufacturers.
   * @param factory
   * @return
   * @throws DMException
   */
  public static Collection<LabelValueBean> getManufacturerExternalOptions(ManagementBeanFactory factory, HttpServletRequest request) throws DMException {
    SearchBean searchBean = factory.createSearchBean();
    Criteria criteria = searchBean.newCriteriaInstance(ManufacturerEntity.class);
    criteria.addOrder(Order.asc("externalId"));

    List<Manufacturer> list = criteria.list();
    Collection<LabelValueBean> result = new ArrayList<LabelValueBean>();
    for (Manufacturer manufacturer: list) {
        LabelValueBean labelValue = new LabelValueBean(manufacturer.getExternalId(), manufacturer.getExternalId());
        result.add(labelValue);
    }
    return result;
  }

  /**
   * Return Options for Update Images Status
   * @param factory
   * @param messageResources
   * @param locale
   * @param request
   * @return
   * @throws DMException
   */
  public static Collection<LabelValueBean> getUpdateImageStatusOptions(ManagementBeanFactory factory, MessageResources messageResources, Locale locale, HttpServletRequest request) throws DMException {
    UpdateImageBean bean = factory.createUpdateImageBean();

    List<LabelValueBean> options = new ArrayList<LabelValueBean>();
    {
      ImageUpdateStatus status = bean.getImageUpdateStatus((long)(Image.STATUS_CREATED));
      LabelValueBean option = new LabelValueBean(status.getName(), "" + Image.STATUS_CREATED);
      options.add(option);      
    }
    
    {
      LabelValueBean option = new LabelValueBean(bean.getImageUpdateStatus(Image.STATUS_NEEDS_TESTING).getName(), "" + Image.STATUS_NEEDS_TESTING);
      options.add(option);      
    }

    {
      LabelValueBean option = new LabelValueBean(bean.getImageUpdateStatus(Image.STATUS_FAILED_TESTING).getName(), "" + Image.STATUS_FAILED_TESTING);
      options.add(option);      
    }

    {
      LabelValueBean option = new LabelValueBean(bean.getImageUpdateStatus(Image.STATUS_TESTED).getName(), "" + Image.STATUS_TESTED);
      options.add(option);      
    }
    {
      LabelValueBean option = new LabelValueBean(bean.getImageUpdateStatus(Image.STATUS_RELEASED).getName(), "" + Image.STATUS_RELEASED);
      options.add(option);      
    }
    {
      LabelValueBean option = new LabelValueBean(bean.getImageUpdateStatus(Image.STATUS_IN_PROGRESS).getName(), "" + Image.STATUS_IN_PROGRESS);
      options.add(option);      
    }
    {
      LabelValueBean option = new LabelValueBean(bean.getImageUpdateStatus(Image.STATUS_UNRECOGNIZED).getName(), "" + Image.STATUS_UNRECOGNIZED);
      options.add(option);      
    }
    return options;
  }
  
  /**
   * Generate default job name
   * @param messageResources
   *        Struts MessageResource
   * @param jobType
   *        Type of job, defined by ProvisionJob
   * @param parameters
   *        paremters for resources.
   * @return
   */
  public static String getDefaultJobName(MessageResources messageResources, Locale locale, String jobType, String[] parameters) {
    String jobName = messageResources.getMessage(locale, "meta.job." + jobType + ".name", parameters);
    return jobName;
  }

  /**
   * Trim a value to String.
   * If the value is null, will return "".
   * @param value
   * @return
   */
  private static String trimToEmpty(ProfileAttributeValue value) throws DMException {
    if (value == null) {
       return "";
    } else {
      return StringUtils.trimToEmpty(value.getStringValue());
    }
  }

  /**
   * Return collection of LabelValueBeans for display Type of AuditLogAction.
   * @param factory
   *        ManagementBeanFactory
   * @param request
   *        HttpServletRequest
   * @return
   * @throws DMException
   */
  public static Collection<LabelValueBean> getAuditActionTypeOptions(ManagementBeanFactory factory, 
                                                                     MessageResources messageResources, 
                                                                     Locale locale) throws DMException {
    SearchBean searchBean = factory.createSearchBean();
    Criteria criteria = searchBean.newCriteriaInstance(AuditLogAction.class);
    // Only list supported Types.
    criteria.add(Expression.eq("optional", new Boolean(true)));
    
    criteria.addOrder(Order.asc("type"));
    List<AuditLogAction> list = criteria.list();
    Map<String, LabelValueBean> result = new LinkedHashMap<String, LabelValueBean>();
    for (AuditLogAction record: list) {
        if (!result.containsKey(record.getType())) {
           String label = messageResources.getMessage(locale, "meta.auditLogAction.type." + record.getType() + ".label");
           LabelValueBean labelValue = new LabelValueBean(label, record.getType());
           result.put(record.getType(), labelValue);
        }
    }
    return result.values();
  }

  /**
   * Return collection of LabelValueBeans for display audit log action belongs into AuditLogActionType
   * @param factory
   *        ManagementBeanFactory
   * @param request
   *        HttpServletRequest
   * @param auditLogActionType
   *        Type of AuditLogAction.
   * @return
   * @throws DMException
   */
  public static Collection<LabelValueBean> getAuditActionOptions(ManagementBeanFactory factory, 
                                                                 MessageResources messageResources, 
                                                                 Locale locale, 
                                                                 String auditLogActionType) throws DMException {
    List<LabelValueBean> result = new ArrayList<LabelValueBean>();
    if (StringUtils.isEmpty(auditLogActionType)) {
       return result;
    }
    
    SearchBean searchBean = factory.createSearchBean();
    Criteria criteria = searchBean.newCriteriaInstance(AuditLogAction.class);
    // Only list supported Types.
    criteria.add(Expression.eq("optional", new Boolean(true)));
    criteria.add(Expression.eq("type", auditLogActionType));
    
    criteria.addOrder(Order.asc("value"));
    List<AuditLogAction> list = criteria.list();
    for (AuditLogAction record: list) {
        String label = messageResources.getMessage(locale, "meta.auditLogAction.value." + record.getValue() + ".label");
        LabelValueBean labelValue = new LabelValueBean(label, record.getValue());
        result.add(labelValue);
    }
    return result;
  }
  
  /**
   * Return a SmsSender for send SMS message.
   * @param carrier
   *        Carrier which included SMSC Properties.
   * @return
   * @throws Exception
   */
  public static SmsSender getSmsSender(Carrier carrier) throws Exception {
    // Load default SMS Gateway properties
    Properties props = ConfigHelper.getSmsGatewayProperties();
    
    if (carrier != null && StringUtils.isNotEmpty(carrier.getNotificationProperties())) {
       // Load from carrier SMSC properties.
       String content = carrier.getNotificationProperties();
       props.load(new ByteArrayInputStream(content.getBytes("UTF-8")));
    }
    SmsSenderFactory factory = SmsSenderFactory.newInstance(props);
    SmsSender sender = factory.getSmsSender();
    return sender;
  }

  /**
   * Return OTA Inventory.
   * @return
   *        OTA Inventory.
   * @throws OTAException
   */
  public static OTAInventory getOTAInventory() throws Exception {
    // Initialize OTAInventory.
    Properties props = ConfigHelper.getClientProvInventory();
    OTAInventory otaInventory = OTAInventory.newInstance(props);
    return otaInventory;
  }

  /**
   * Get Notification Service to send notification to mobile device..
   * @param beanFactory
   *        ManagementBeanFactory
   * @param carrier
   *        Carrier which included SMSC Properties.
   * @return
   *        NotificationService
   * @throws DMException
   * @throws Exception
   */
  public static NotificationService getNotificationService(ManagementBeanFactory beanFactory, Carrier carrier) throws DMException, Exception {
    NotificationServiceFactory serviceFactory = NotificationServiceFactory.newInstance(new Properties());
    NotificationService notifiService = serviceFactory.getNotificationService();
    notifiService.setBeanFactory(beanFactory);
    SmsSender smsSender = getSmsSender(carrier);
    notifiService.setSmsSender(smsSender);
    return notifiService;
  }

  /**
   * Return a Bootstrap service. The Bootstrap profile will read from carrier bootstrap profile.
   * @param carrier
   *        Carrier
   * @param beanFactory
   *        ManagementBeanFactory
   * @return
   *        BootstrapService.
   * @throws DMException
   * @throws Exception
   */
  public static BootstrapService getBootstrapService(ManagementBeanFactory beanFactory, Carrier carrier) throws DMException, Exception {
    // Load Global properties
    Properties profile = ConfigHelper.getGlobalBootstrapProfile();
    if (carrier != null) {
       ProfileConfig config = carrier.getBootstrapNapProfile();
       if (config != null) {
          // Overwrite global properties by Carrier properties.
          //profile.setProperty("napdef_napid",                 trimToEmpty(config.getProfileAttributeValue("NAME")));
          profile.setProperty("napdef_name",                  trimToEmpty(config.getProfileAttributeValue("NAME")));
          profile.setProperty("napdef_nap_addr",              trimToEmpty(config.getProfileAttributeValue("NAP-ADDRESS")));
          //profile.setProperty("napdef_bearer",                trimToEmpty(config.getProfileAttributeValue("BEARER")));
          profile.setProperty("napdef_apn",                   trimToEmpty(config.getProfileAttributeValue("NAP-ADDRTYPE")));
          //profile.setProperty("napdef_nap_auth_type",         trimToEmpty(config.getProfileAttributeValue("AUTHTYPE")));
          profile.setProperty("napdef_nap_auth_name",         trimToEmpty(config.getProfileAttributeValue("AUTHNAME")));
          profile.setProperty("napdef_nap_auth_secret",       trimToEmpty(config.getProfileAttributeValue("AUTHSECRET")));
          //profile.setProperty("pxlogic_id",                   trimToEmpty(config.getProfileAttributeValue("NAME")));
          profile.setProperty("pxlogic_name",                 trimToEmpty(config.getProfileAttributeValue("NAME")));
          profile.setProperty("pxlogic_startpage",            trimToEmpty(config.getProfileAttributeValue("STARTPAGE")));
          profile.setProperty("pxlogic_phy_proxy_addr",       trimToEmpty(config.getProfileAttributeValue("PXADDR")));
          //profile.setProperty("pxlogic_phy_proxy_addr_type",  trimToEmpty(config.getProfileAttributeValue("PXADDRTYPE")));
          profile.setProperty("pxlogic_phy_proxy_addr_port",  trimToEmpty(config.getProfileAttributeValue("PXPHYSICAL PORTNBR")));
       }
    }
    
    OTAInventory otaInventory = getOTAInventory();
    SmsSender smsSender = getSmsSender(carrier);
    
    BootstrapServiceFactory serviceFactory = BootstrapServiceFactory.newInstance(profile);
    BootstrapService service = serviceFactory.getBootstrapService();
    service.setBeanFactory(beanFactory);
    service.setOtaInventory(otaInventory);
    service.setSmsSender(smsSender);
    return service;
  }
  
  /**
   * @return
   * @throws IllegalAccessException 
   * @throws InstantiationException 
   * @throws ClassNotFoundException 
   */
  public static JobEventListener getJobEventListener() throws DMException, ClassNotFoundException, InstantiationException, IllegalAccessException {
    JobEventListenerFactory factory = JobEventListenerFactory.newInstance(ConfigHelper.getJobDaemonProperties());
    return factory.getJobEventListener();
  }
  
  /**
   * Send Notification
   * @param factory
   *        ManagementBeanFactory
   * @param deviceExternalID
   *        Target Device External ID
   * @param sessionID
   *        DM Session ID
   * @param scheduleTime
   *        Time for schedule this job
   * @param uiMode
   *        UI Mode
   * @param initiator
   *        Initiator
   * @throws DMException
   * @throws Exception
   * @throws SmsException
   */
  public static void sendNotification(ManagementBeanFactory factory, String deviceExternalID, int sessionID, long scheduleTime, String uiMode, String initiator) throws DMException, Exception, SmsException {
    DeviceBean deviceBean = factory.createDeviceBean();
    Device device = deviceBean.getDeviceByExternalID(deviceExternalID);
    
    // Get DM Server ID
    Properties profile = ConfigHelper.getGlobalBootstrapProfile();
    String dmServerID = profile.getProperty("dm_service_id");
 
    // Get a Notification Service.
    NotificationService notifiService = ActionHelper.getNotificationService(factory, device.getSubscriber().getCarrier());
    int maxRetry = (int)device.getSubscriber().getCarrier().getNotificationMaxNumRetries();
    long maxDuration = device.getSubscriber().getCarrier().getBootstrapTimeout() * 1000;
    notifiService.notificate(deviceExternalID, dmServerID, sessionID, UIMode.value(uiMode), Initiator.value(initiator), scheduleTime, maxRetry, maxDuration);
  }

  /**
   * @param request
   * @param messageResources
   */
  public static void setJobType(HttpServletRequest request, MessageResources messageResources, Locale locale, JobType jobType) {
    HttpSession session = request.getSession();
    session.setAttribute(EditJobAction.JOB_TYPE_FOR_ASSIGN_PROFILE, JobTypeDecorator.decorate(messageResources, locale, jobType));
  }

  /**
   * Submit a Profile Assignment Job for Struts Action.
   * @param request
   *        HttpServletRequest
   * @param factory
   * @param scheduledTime
   *        ManagementBeanFactory
   * @param device
   *        Target Device.
   * @param profileID
   * @param assignmentID
   * @param jobName
   * @param jobDescription
   * @param sendNotification
   * @param uiMode
   * @param initiator
   * @return
   * @throws DMException
   * @throws Exception
   * @throws SmsException
   */
  public static ProvisionJob submitAssignmentJob(HttpServletRequest request, ManagementBeanFactory factory, Date scheduledTime, Device device, String profileID, String assignmentID, String jobName, String jobDescription, Boolean sendNotification, String uiMode, String initiator) throws DMException, Exception, SmsException {
    ProfileConfigBean profileBean = factory.createProfileConfigBean();
    ProvisionJobBean jobBean = factory.createProvisionJobBean();
  
    // Get Profile
    ProfileConfig profile = profileBean.getProfileConfigByID(profileID);
  
    // Submit Jobs
    factory.beginTransaction();
    
    ProfileAssignment assignment = ActionHelper.getProfileAssignment(request, factory, device, assignmentID, profile);
    
    List<ProvisionJob> jobs = jobBean.newJobs4Assignment(assignment, jobName, jobDescription, scheduledTime);
    for (ProvisionJob job: jobs) {
      if (sendNotification != null && sendNotification.booleanValue()) {
         job.setUiMode(uiMode);
         jobBean.update(job);
      } 
    }
    factory.commit();
    
    // Send Notification
    ProvisionJob job = jobs.get(jobs.size() - 1);
    if (sendNotification != null && sendNotification.booleanValue()) {
       sendNotification(factory, device.getExternalId(), (int)job.getID(), scheduledTime.getTime(), uiMode, initiator);
    }
    return job;
  }

  /**
   * Submit a Profile Assignment Job for Struts Action.
   * @param request
   *        HttpServletRequest
   * @param form
   *        DynaValidatorForm
   *        ID         ID of ProfileAssignment, which needed in Profile Re-assignment mode
   *        profileID  ID of profile config.
   * @param factory
   *        ManagementBeanFactory
   * @param scheduledTime
   *        Job scheduled time
   * @param device
   *        Target Device.
   * @return
   *        Provision Job
   * @throws DMException
   * @throws Exception
   */
  private static ProvisionJob submitAssignmentJob(HttpServletRequest request, DynaValidatorForm form, ManagementBeanFactory factory, Date scheduledTime, Device device) throws DMException, Exception {
    String profileID = form.getString("profileID");
    String assignmentID = form.getString("ID");
    String jobName = form.getString("name");
    String jobDescription = form.getString("description");
    Boolean sendNotification = (Boolean)form.get("sendNotification");
    String uiMode = form.getString("uiMode");
    String initiator = form.getString("initiator");
    
    return submitAssignmentJob(request, factory, scheduledTime, device, profileID, assignmentID, jobName, jobDescription, sendNotification, uiMode, initiator);
  }

  /**
   * @param request
   * @param factory
   * @param device
   * @param assignmentID
   * @param profile
   * @return
   * @throws DMException
   * @throws IOException
   * @throws FileNotFoundException
   */
  private static ProfileAssignment getProfileAssignment(HttpServletRequest request, ManagementBeanFactory factory,
      Device device, String assignmentID, ProfileConfig profile) throws DMException, IOException, FileNotFoundException {
    // Create or Get Assignment
    ProfileAssignmentBean assignmentBean = factory.createProfileAssignmentBean();
    ProfileAssignment assignment = null;
    if (StringUtils.isNotEmpty(assignmentID)) {
       assignment = assignmentBean.getProfileAssignmentByID(assignmentID);
    } else {
      // Create a assignment
      assignment = assignmentBean.newProfileAssignmentInstance(profile, device);
    }
    
    // Processing user attributes
    Set<ProfileAttribute> attributes = profile.getProfileTemplate().getProfileAttributes();
    for (ProfileAttribute attribute: attributes) {
        if (attribute.getIsUserAttribute()) {
           long attrID = attribute.getID();
           String attrName = attribute.getName();
           String value = request.getParameter("attribute__" + attrID + "__value");
           if (attribute.getProfileAttribType() != null 
               && ProfileAttributeType.TYPE_BINARY.equalsIgnoreCase(attribute.getProfileAttribType().getName())) {
              // Binary
              String emptyIt = request.getParameter("attribute__" + attrID + "__emptyIt");
              if (StringUtils.isNotEmpty(emptyIt)) {
                 // Empty this field
                 assignmentBean.setAttributeValue(assignment, attrName, (InputStream)null);
              } else {
                if (StringUtils.isNotEmpty(value)) {
                   File file = new File(value);
                   if (file != null && file.exists()) {
                      InputStream in = new FileInputStream(file);
                      assignmentBean.setAttributeValue(assignment, attrName, in);
                      //in.close();
                   }
                }
              }
           } else {
             // Text
             assignmentBean.setAttributeValue(assignment, attrName, (String)value);
           }
        }
    }
    return assignment;
  }

  /**
   * @param nodeURIValuess
   */
  public static String[] checkingNodeURIs(String[] nodeURIValuess) {
    Set<String> nodeURIs = new TreeSet<String>();
    for (String nodeURI: nodeURIValuess) {
        if (StringUtils.isEmpty(nodeURI)) {
           continue;
        }
        if (nodeURI.startsWith("/")) {
           nodeURI = "." + nodeURI;
        }
        if (!nodeURI.startsWith(".") && !nodeURI.startsWith("./")) {
           nodeURI = "./" + nodeURI;
        }
        nodeURIs.add(nodeURI);
    }
    String[] result = nodeURIs.toArray(new String[]{});
    return result;
  }


}
