/**
 * $Header: /home/master/OTAS-DM-MyPortal/src/com/npower/dm/myportal/BaseWizardAction.java,v 1.4 2008/06/16 10:11:24 zhao Exp $
 * $Revision: 1.4 $
 * $Date: 2008/06/16 10:11:24 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2007 NPower Network Software Ltd.  All rights reserved.
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
package com.npower.dm.myportal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ServiceProvider;
import com.npower.dm.core.Software;
import com.npower.dm.decorator.DecoratorHelper;
import com.npower.dm.hibernate.entity.ManufacturerEntity;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.CountryBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.ProfileConfigBean;
import com.npower.dm.management.ProfileTemplateBean;
import com.npower.dm.management.SearchBean;
import com.npower.dm.management.SoftwareBean;
import com.npower.dm.myportal.cp.ClientProvWizardForm;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2008/06/16 10:11:24 $
 */
public abstract class BaseWizardAction extends LookupDispatchAction {

  public static final String PARAMETER_RECORDS_PER_PAGE = "recordsPerPage";
  
  private static final String[] SpecialManufacturerExtIDs = new String[]{"NOKIA", "Motorola", "Samsung", "SonyEricsson"};

  /**
   * 
   */
  public BaseWizardAction() {
    super();
  }

  @Override
  protected Map<String, String> getKeyMethodMap() {
    Map<String, String> map = new HashMap<String, String>();
    map.put("page.button.next.label", "next");
    map.put("page.button.prev.label", "prev");
    return(map);
  }

  // Protected methods ----------------------------------------------
  /**
   * 返回缺省国家.
   * @param request
   * @param countryBean
   * @return
   * @throws DMException
   */
  protected Country getDefaultCountry(HttpServletRequest request, CountryBean countryBean) throws DMException {
    Locale locale = this.getLocale(request);
     String defaultCountryCode = locale.getCountry();
     Country defaultCountry = countryBean.getCountryByISOCode(defaultCountryCode);
    return defaultCountry;
  }

  /**
   * Return Country
   * @param factory
   * @param form
   * @return
   * @throws DMException
   */
  protected Country getCountry(ManagementBeanFactory factory, ClientProvWizardForm form, HttpServletRequest request) throws DMException {
    String countryID = request.getParameter("countryID");
    if (StringUtils.isNotEmpty(countryID)) {
       form.setValue("countryID", countryID);
    } else {
      countryID = form.getString("countryID");
    }
    Country country = null;
    if (StringUtils.isNotEmpty(countryID)) {
       CountryBean countryBean = factory.createCountryBean();
       country = countryBean.getCountryByID(Long.parseLong(countryID));
    }
    return country;
  }

  /**
   * Return Carrier
   * @param factory
   * @param form
   * @return
   * @throws DMException
   */
  protected Carrier getCarrier(ManagementBeanFactory factory, ClientProvWizardForm form, HttpServletRequest request) throws DMException {
    String carrierID = request.getParameter("carrierID");
    if (StringUtils.isNotEmpty(carrierID)) {
       form.setValue("carrierID", carrierID);
    } else {
      carrierID = form.getString("carrierID");
    }
    Carrier carrier = null;
    if (StringUtils.isNotEmpty(carrierID)) {
       CarrierBean carrierBean = factory.createCarrierBean();
       carrier = carrierBean.getCarrierByExternalID(carrierID);
    }
    return carrier;
  }

  /**
   * @param factory
   * @param form
   * @return
   * @throws DMException
   */
  protected Model getModel(ManagementBeanFactory factory, ClientProvWizardForm form, HttpServletRequest request) throws DMException {
    ModelBean modelBean = factory.createModelBean();
    String modelID = form.getString("modelID");
    Model model = null;
    if (StringUtils.isEmpty(modelID)) {
       // Get model information from request
       String brand = request.getParameter("brand");
       String modelExtID = request.getParameter("model");
       if (StringUtils.isNotEmpty(brand) && StringUtils.isNotEmpty(modelExtID)) {
          Manufacturer manufacturer = modelBean.getManufacturerByExternalID(brand);
          if (manufacturer != null) {
             model = modelBean.getModelByManufacturerModelID(manufacturer, modelExtID);
          }
       }
       if (model != null) {
         form.setValue("modelID", "" + model.getID());
         form.setValue("manufacturerID", "" + model.getManufacturer().getID());
       }
    } else {
       model = modelBean.getModelByID(modelID);
    }
    return model;
  }
  
  /**
   * @param factory
   * @param form
   * @return
   * @throws DMException
   */
  protected Manufacturer getManufacturer(ManagementBeanFactory factory, ClientProvWizardForm form, HttpServletRequest request) throws DMException {
    ModelBean modelBean = factory.createModelBean();
    String manufacturerID = form.getString("manufacturerID");
    Manufacturer manufacturer = null;
    if (StringUtils.isEmpty(manufacturerID)) {
       // Get model information from request
       String brand = request.getParameter("brand");
       if (StringUtils.isNotEmpty(brand)) {
          manufacturer = modelBean.getManufacturerByExternalID(brand);
       }
    } else {
      manufacturer = modelBean.getManufacturerByExternalID(manufacturerID);
    }
    return manufacturer;
  }
  
  /**
   * @param factory
   * @param form
   * @return
   * @throws DMException
   */
  protected ProfileCategory getProfileCategory(ManagementBeanFactory factory, ClientProvWizardForm form, HttpServletRequest request) throws DMException {
    String categoryID = request.getParameter("profileCategoryID");
    if (StringUtils.isNotEmpty(categoryID)) {
       form.setValue("profileCategoryID", categoryID);
    } else {
      categoryID = form.getString("profileCategoryID");
    }
    ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
    ProfileCategory category = null;
    if (StringUtils.isNotEmpty(categoryID)) {
      category = templateBean.getProfileCategoryByName(categoryID);
    }
    return category;
  }
  
  /**
   * @param factory
   * @param form
   * @return
   * @throws DMException
   */
  protected ProfileConfig getProfile(ManagementBeanFactory factory, ClientProvWizardForm form, HttpServletRequest request) throws DMException {
    String profileID = request.getParameter("profileID");
    if (StringUtils.isNotEmpty(profileID)) {
       form.setValue("profileID", profileID);
    } else {
      profileID = form.getString("profileID");
    }
    ProfileConfigBean profileBean = factory.createProfileConfigBean();
    ProfileConfig profile = null;
    if (StringUtils.isNotEmpty(profileID)) {
       profile = profileBean.getProfileConfigByExternalID(profileID);
    }
    return profile;
  }
  
  /**
   * @param factory
   * @param form
   * @return
   * @throws DMException
   */
  protected Software getSoftware(ManagementBeanFactory factory, ClientProvWizardForm form, HttpServletRequest request) throws DMException {
    // Get Software
    String softwareID = request.getParameter("softwareID");
    if (StringUtils.isNotEmpty(softwareID)) {
       form.setValue("softwareID", softwareID);
    } else {
      softwareID = form.getString("softwareID");
    }

    SoftwareBean softwareBean = factory.createSoftwareBean();
    Software software = null;
    if (StringUtils.isNotEmpty(softwareID)) {
       software = softwareBean.getSoftwareByExternalID(softwareID);
    }

    return software;
  }
  
  /**
   * 提取用户的手机号码
   * @param form
   * @return
   */
  protected String getPhoneNumber(ManagementBeanFactory factory, HttpServletRequest request, ClientProvWizardForm form) {
    String phoneNumber = form.getString("phoneNumber");
    if (StringUtils.isEmpty(phoneNumber)) {
       return null;
    }
    if (phoneNumber.startsWith("0")) {
       phoneNumber = phoneNumber.substring(1, phoneNumber.length());
    }
    if (phoneNumber.startsWith("+")) {
       phoneNumber = phoneNumber.substring(1, phoneNumber.length());
    }
    if (phoneNumber.startsWith("86")) {
       phoneNumber = phoneNumber.substring(2, phoneNumber.length());
    }
    return phoneNumber;
  }

  /**
   * @param form
   * @return
   */
  protected String getDeviceExternalID(ManagementBeanFactory factory, HttpServletRequest request, ClientProvWizardForm form) {
    //String deviceExternalID = form.getString("deviceID");
    UserServiceData serviceData = BaseWizardAction.getUserServiceData(request);
    String deviceExternalID = serviceData.getImei();
    return deviceExternalID;
  }

  /**
   * @param form
   * @return
   */
  protected ServiceProvider getServiceProvider(ManagementBeanFactory factory, HttpServletRequest request, ClientProvWizardForm form) {
    return null;
  }

  /**
   * 返回用于型号选择页面的Manufacturer数组
   * @param factory
   * @param request
   * @return
   * @throws DMException
   */
  protected Collection<LabelValueBean> getManufacturerOptions(ManagementBeanFactory factory, HttpServletRequest request)
      throws DMException {
    SearchBean searchBean = factory.createSearchBean();
    Criteria criteria = searchBean.newCriteriaInstance(ManufacturerEntity.class);
    criteria.addOrder(Order.asc("externalId"));

    List<Manufacturer> list = criteria.list();
    Collection<LabelValueBean> result = new ArrayList<LabelValueBean>();
    for (Manufacturer decoratee: list) {
        Manufacturer manufacturer = DecoratorHelper.decorate(decoratee, this.getLocale(request));
        String label = manufacturer.getName();
        String value = "" + manufacturer.getID();
        LabelValueBean labelValue = new LabelValueBean(label, value);
        result.add(labelValue);
    }
    return result;
  }

  /**
   * @param factory
   * @return
   * @throws DMException
   */
  protected List<Manufacturer> getManufacturers(ManagementBeanFactory factory,
      HttpServletRequest request) throws DMException {
    SearchBean searchBean = factory.createSearchBean();
    Criteria criteria = searchBean.newCriteriaInstance(ManufacturerEntity.class);
    criteria.addOrder(Order.asc("externalId"));

    List<Manufacturer> list = criteria.list();
    return DecoratorHelper.decorateManufacturer(list, this.getLocale(request));
  }

  /**
   *  返回用于用于型号选择页面的主流Manufacturer数组
   * @param factory
   * @param request
   * @return
   * @throws DMException
   */
  protected Collection<LabelValueBean> getSpecialManufacturerOptions(ManagementBeanFactory factory,
      HttpServletRequest request) throws DMException {
    ModelBean modelBean = factory.createModelBean();
    Collection<LabelValueBean> result = new ArrayList<LabelValueBean>();
    for (String extID: SpecialManufacturerExtIDs) {
        Manufacturer decoratee = modelBean.getManufacturerByExternalID(extID);
        Manufacturer manufacturer = DecoratorHelper.decorate(decoratee, this.getLocale(request));
        String label = manufacturer.getName();
        String value = "" + manufacturer.getID();
        LabelValueBean labelValue = new LabelValueBean(label, value);
        result.add(labelValue);
    }
    return result;
  }

  /**
   *  返回用于用于型号选择页面的主流Manufacturer数组
   * @param factory
   * @param request
   * @return
   * @throws DMException
   */
  protected Collection<Manufacturer> getSpecialManufacturers(ManagementBeanFactory factory,
      HttpServletRequest request) throws DMException {
    ModelBean modelBean = factory.createModelBean();
    Collection<Manufacturer> result = new ArrayList<Manufacturer>();
    for (String extID: SpecialManufacturerExtIDs) {
        Manufacturer decoratee = modelBean.getManufacturerByExternalID(extID);
        Manufacturer manufacturer = DecoratorHelper.decorate(decoratee, this.getLocale(request));
        result.add(manufacturer);
    }
    return result;
  }

  /**
   * 返回用于用于型号选择页面的型号数组
   * @param factory
   * @param form
   * @return
   * @throws DMException
   */
  protected Collection<LabelValueBean> getModelOptions(ManagementBeanFactory factory, ClientProvWizardForm form)
      throws DMException {
    String manufacturerID = form.getString("manufacturerID");
    Collection<LabelValueBean> result = new ArrayList<LabelValueBean>();
    if (StringUtils.isNotEmpty(manufacturerID)) {
       ModelBean modelBean = factory.createModelBean();
       Manufacturer manufacturer = modelBean.getManufacturerByID(manufacturerID);
       Set<Model> models = new TreeSet<Model>();
       models.addAll(manufacturer.getModels());
       for (Model model: models) {
           LabelValueBean labelValue = new LabelValueBean(model.getName(), "" + model.getID());
           result.add(labelValue);
       }
    }
    return result;
  }

  /**
   * 返回用于用于型号选择页面的型号数组
   * @param factory
   * @param form
   * @return
   * @throws DMException
   */
  protected Collection<Model> getModels(ManagementBeanFactory factory, ClientProvWizardForm form)
      throws DMException {
    String manufacturerID = form.getString("manufacturerID");
    Collection<Model> result = new ArrayList<Model>();
    if (StringUtils.isNotEmpty(manufacturerID)) {
       ModelBean modelBean = factory.createModelBean();
       Manufacturer manufacturer = modelBean.getManufacturerByID(manufacturerID);
       Set<Model> models = new TreeSet<Model>();
       models.addAll(manufacturer.getModels());
       result.addAll(models);
    }
    return result;
  }

  /**
   * Generate Option list for Records of per page.
   * @return
   * @throws DMException
   */
  protected static Collection<LabelValueBean> getRecordsPerPageOptions(HttpServletRequest request, ClientProvWizardForm form) throws DMException {
    Collection<LabelValueBean> result = new ArrayList<LabelValueBean>();
    //result.add(new LabelValueBean("2", "2"));
    result.add(new LabelValueBean("5", "5"));
    result.add(new LabelValueBean("10", "10"));
    result.add(new LabelValueBean("15", "15"));
    result.add(new LabelValueBean("25", "25"));
    result.add(new LabelValueBean("50", "50"));
    result.add(new LabelValueBean("100", "100"));
    result.add(new LabelValueBean("All", "" + Integer.MAX_VALUE));
    
    int recordsPerPage = getRecordsPerPage(request);
    if (form instanceof ClientProvWizardForm) {
       ((ClientProvWizardForm)form).setValue(BaseWizardAction.PARAMETER_RECORDS_PER_PAGE, recordsPerPage);
    }
    
    return result;
  }

  /**
   * Set options for Records per page
   * @param request
   * @param searchForm
   * @throws DMException
   */
  public static void setRecordsPerPageOptions(HttpServletRequest request, ClientProvWizardForm searchForm) throws DMException {
    request.setAttribute("recordsPerPageOptions", getRecordsPerPageOptions(request, searchForm));
  }

  /**
   * Get parameter fro request.
   * @return
   * @throws DMException
   */
  public static int getRecordsPerPage(HttpServletRequest request) throws DMException {
    // Default is 15
    int result = 15;
    String t = request.getParameter(BaseWizardAction.PARAMETER_RECORDS_PER_PAGE);
    if (StringUtils.isEmpty(t)) {
       // Map backed Form
       t = request.getParameter("value(" + BaseWizardAction.PARAMETER_RECORDS_PER_PAGE + ")");
    }
    if (StringUtils.isNotEmpty(t)) {
       try {
           result = Integer.parseInt(t);
       } catch (Exception ex) {
         
       }
    } else {
      Enumeration names = request.getAttributeNames();
      while (names.hasMoreElements()) {
            String name = (String)names.nextElement();
            Object object = request.getAttribute(name);
            if (object != null && object instanceof ClientProvWizardForm) {
              ClientProvWizardForm form = (ClientProvWizardForm)object;
               try {
                   Object v = form.getString("recordsPerPage");
                   if (v != null && v instanceof Integer) {
                      return ((Integer)v).intValue();
                   }
               } catch (IllegalArgumentException ex) {
                 // If DynaFomr don't contains the "recordsPerPage" will throw this exception
                 
               } catch (Exception ex) {
                 // Unknow error!
                 log.error("Error in finding recordsPerPage.", ex);
               }
            }
      }
    }
    return result;
  }

  /**
   * 存储缺少Country信息到Request对象中
   * @param request
   */
  protected void saveError4MissingCountry(HttpServletRequest request) {
    ActionMessages errors = new ActionMessages();
    MessageResources messageResources = this.getResources(request);
    Locale locale = this.getLocale(request);
    String label = messageResources.getMessage(locale, "page.cp.wizard.select.country.country.label"); 
    errors.add("countryID", new ActionMessage("errors.required", label));
    saveErrors(request, errors);
  }
  
  /**
   * 保存缺少运营商信息
   * @param request
   */
  protected void saveError4MissCarrier(HttpServletRequest request) {
    ActionMessages errors = new ActionMessages();
     MessageResources messageResources = this.getResources(request);
     Locale locale = this.getLocale(request);
     String label = messageResources.getMessage(locale, "page.cp.wizard.select.carrier.carrier.label"); 
     errors.add("carrierID", new ActionMessage("errors.required", label));
     saveErrors(request, errors);
  }
  
  /**
   * 保存缺少终端生产厂商信息
   * @param request
   */
  protected void saveError4MissManufacturer(HttpServletRequest request) {
    ActionMessages errors = new ActionMessages();
     MessageResources messageResources = this.getResources(request);
     Locale locale = this.getLocale(request);
     String label = messageResources.getMessage(locale, "page.cp.wizard.select.manufacturer.manufacturer.label"); 
     errors.add("manufacturerID", new ActionMessage("errors.required", label));
     saveErrors(request, errors);
  }
  
  /**
   * 保存缺少终端型号信息
   * @param request
   */
  protected void saveError4MissModel(HttpServletRequest request) {
    ActionMessages errors = new ActionMessages();
     MessageResources messageResources = this.getResources(request);
     Locale locale = this.getLocale(request);
     String label = messageResources.getMessage(locale, "page.cp.wizard.select.model.model.label"); 
     errors.add("modelID", new ActionMessage("errors.required", label));
     saveErrors(request, errors);
  }

  /**
   * 保存缺少配置类型信息
   * @param request
   */
  protected void saveError4Category(HttpServletRequest request) {
    ActionMessages errors = new ActionMessages();
     MessageResources messageResources = this.getResources(request);
     Locale locale = this.getLocale(request);
     String label = messageResources.getMessage(locale, "page.cp.wizard.select.category.category.name.label"); 
     errors.add("profileCategoryID", new ActionMessage("errors.required", label));
     saveErrors(request, errors);
  }
  
  /**
   * 保存缺少配置信息
   * @param request
   */
  protected void saveError4MissProfile(HttpServletRequest request) {
    ActionMessages errors = new ActionMessages();
     MessageResources messageResources = this.getResources(request);
     Locale locale = this.getLocale(request);
     String label = messageResources.getMessage(locale, "page.cp.wizard.select.profile.profile.label"); 
     errors.add("profileID", new ActionMessage("errors.required", label));
     saveErrors(request, errors);
  }

  /**
   * 保存缺少电话号码
   * @param request
   */
  protected void saveError4MissingPhoneNumber(HttpServletRequest request) {
    ActionMessages errors = new ActionMessages();
     MessageResources messageResources = this.getResources(request);
     Locale locale = this.getLocale(request);
     String label = messageResources.getMessage(locale, "page.cp.wizard.input.phone.phone.label"); 
     errors.add("phoneNumber", new ActionMessage("errors.required", label));
     saveErrors(request, errors);
  }
  
  /**
   * 保存缺少验证码信息
   * @param request
   */
  protected void saveError4MissCheckCode(HttpServletRequest request) {
    ActionMessages errors = new ActionMessages();
     MessageResources messageResources = this.getResources(request);
     Locale locale = this.getLocale(request);
     String label = messageResources.getMessage(locale, "page.cp.wizard.input.phone.checkcode.label"); 
     errors.add("checkcode", new ActionMessage("errors.required", label));
     saveErrors(request, errors);
  }

  /**
   * 保存缺少目标软件信息
   * @param request
   */
  protected void saveError4MissSoftware(HttpServletRequest request) {
    ActionMessages errors = new ActionMessages();
     MessageResources messageResources = this.getResources(request);
     Locale locale = this.getLocale(request);
     String label = messageResources.getMessage(locale, "page.dm.wizard.select.software.label"); 
     errors.add("softwareID", new ActionMessage("errors.required", label));
     saveErrors(request, errors);
  }
  
  /**
   * 保存终端型号不支持DM模式的软件安装信息
   * @param request
   */
  protected void saveError4NotSupportSoftwareInstallInDMMode(HttpServletRequest request) {
    ActionMessages errors = new ActionMessages();
     MessageResources messageResources = this.getResources(request);
     Locale locale = this.getLocale(request);
     String label = messageResources.getMessage(locale, "page.cp.wizard.select.model.model.label"); 
     errors.add("modelID", new ActionMessage("page.dm.wizard.select.model.could_not_support_software_install.msg", label));
     saveErrors(request, errors);
  }
  
  /* (non-Javadoc)
   * @see org.apache.struts.actions.DispatchAction#unspecified(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  public ActionForward unspecified(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
     return this.view(mapping, rawForm, request, res);
  }

  /* (non-Javadoc)
   * @see org.apache.struts.actions.DispatchAction#unspecified(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  public ActionForward cancelled(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
    return this.prev(mapping, rawForm, request, res);
  }

  /**
   * View for this step.
   * @param mapping
   * @param rawForm
   * @param request
   * @param res
   * @return
   * @throws Exception
   */
  public abstract ActionForward prev(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception;
  
  /**
   * Previous step
   * @param mapping
   * @param rawForm
   * @param request
   * @param res
   * @return
   * @throws Exception
   */
  public abstract ActionForward next(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception;

  /**
   * Next step
   * @param mapping
   * @param rawForm
   * @param request
   * @param res
   * @return
   * @throws Exception
   */
  public abstract ActionForward view(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception;

  /**
   * @param req
   * @return
   */
  public static UserServiceData getUserServiceData(HttpServletRequest req) {
    HttpSession session = req.getSession();
     UserServiceData serviceData = (UserServiceData)session.getAttribute("USER_SERVICE_DATA");
     if (serviceData == null) {
       serviceData =  new UserServiceData();
       session.setAttribute("USER_SERVICE_DATA", serviceData);
     }
    return serviceData;
  }

}