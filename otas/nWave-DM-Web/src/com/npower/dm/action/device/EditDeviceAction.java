//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.npower.dm.action.device;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.validator.DynaValidatorForm;

import com.npower.dm.action.ActionHelper;
import com.npower.dm.action.BaseAction;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;

/**
 * MyEclipse Struts Creation date: 06-01-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/EditDevice" name="DeviceForm" scope="request"
 * @struts.action-forward name="success" path="/jsp/device/device.jsp"
 *                        contextRelative="true"
 */
public class EditDeviceAction extends BaseAction {

  // --------------------------------------------------------- Instance
  // Variables

  // --------------------------------------------------------- Methods
  
  public Device getDevice(HttpServletRequest request, String id) throws DMException {
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    DeviceBean deviceBean = factory.createDeviceBean();
    Device device = deviceBean.getDeviceByID(id);
    return device;
  }
  
  public Collection<LabelValueBean> getManufacturerOptions(HttpServletRequest request) throws DMException {
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ModelBean modelBean = factory.createModelBean();
    List<Manufacturer> list = modelBean.getAllManufacturers();
    Collection<LabelValueBean> result = new ArrayList<LabelValueBean>();
    for (Manufacturer manufacturer: list) {
        LabelValueBean labelValue = new LabelValueBean(manufacturer.getExternalId(), "" + manufacturer.getID());
        result.add(labelValue);
    }
    return result;
  }
  
  public Collection<LabelValueBean> getModelOptions(HttpServletRequest request, DynaValidatorForm deviceForm) throws DMException {
    Collection<LabelValueBean> result = new ArrayList<LabelValueBean>();
    
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ModelBean modelBean = factory.createModelBean();
    String manufacturerID = (String)deviceForm.get("manufacturerID");
    Manufacturer manufacturer = null;
    if (manufacturerID != null && manufacturerID.trim().length() > 0) {
       manufacturer = modelBean.getManufacturerByID(manufacturerID);
    } else {
      String deviceID = (String)deviceForm.get("ID");
      if (deviceID != null && deviceID.trim().length() > 0) {
         Device device = this.getDevice(request, deviceID);
         if (device != null) {
            manufacturer = device.getModel().getManufacturer();
         }
      }
    }
    
    if (manufacturer == null) {
       return result;
    }
    Set<Model> set = new TreeSet<Model>(manufacturer.getModels());
    for (Model model: set) {
        LabelValueBean labelValue = new LabelValueBean(model.getManufacturerModelId(), "" + model.getID());
        result.add(labelValue);
    }
    return result;
  }
  
  private void loadAuthenticationSchemas(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    List<LabelValueBean> schemas = new ArrayList<LabelValueBean>();
    LabelValueBean label = new LabelValueBean(Device.AUTH_TYPE_BASIC, Device.AUTH_TYPE_BASIC);
    schemas.add(label);
    label = new LabelValueBean(Device.AUTH_TYPE_MD5, Device.AUTH_TYPE_MD5);
    schemas.add(label);
    label = new LabelValueBean(Device.AUTH_TYPE_HMAC, Device.AUTH_TYPE_HMAC);
    schemas.add(label);
    request.setAttribute("authSchemas", schemas);
  }

  /**
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @throws Exception
   * @throws IllegalAccessException
   * @throws InvocationTargetException
   * @throws NoSuchMethodException
   * @throws DMException
   */
  private void loadDeviceData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception, IllegalAccessException, InvocationTargetException, NoSuchMethodException, DMException {
    DynaValidatorForm deviceForm = (DynaValidatorForm) form;
    String deviceID = (String)request.getAttribute("deviceID");
    if (StringUtils.isNotEmpty(deviceID)) {
       deviceForm.set("ID", deviceID);
    }
    
    // Load auth schemas for select box
    this.loadAuthenticationSchemas(mapping, form, request, response);
    
    try {
        if (StringUtils.isNotEmpty((String) deviceForm.get("ID"))) {
           // Load data from DB
           Device device = this.getDevice(request, (String) deviceForm.get("ID"));
           // Copy data from POJO into FormBean
           deviceForm.getMap().putAll(BeanUtils.describe(device));
           deviceForm.set("manufacturerID", device.getModel().getManufacturer().getID() + "");
           deviceForm.set("modelID", device.getModel().getID() + "");
           deviceForm.set("carrierID", device.getSubscriber().getCarrier().getID() + "");
          
           request.setAttribute("model", device.getModel());
           request.setAttribute("subscriber", device.getSubscriber());
           
           request.setAttribute("device", device);
           if (StringUtils.isEmpty(device.getOMAClientAuthScheme())) {
              deviceForm.set("OMAClientAuthScheme", Device.AUTH_TYPE_BASIC);
           }
          
        } else {
          // Create a device, set default value into form.
          deviceForm.set("isActivated", true);
          deviceForm.set("OMAClientAuthScheme", Device.AUTH_TYPE_BASIC);
        }
        
        request.setAttribute("manufacturerOptions", this.getManufacturerOptions(request));
        request.setAttribute("modelOptions", this.getModelOptions(request, deviceForm));
        request.setAttribute("carrierOptions", ActionHelper.getCarrierOptions(this.getManagementBeanFactory(request)));
        
    } catch (DMException e) {
      throw e;
    }
  }

  /**
   * Method execute
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward execute(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    
    loadDeviceData(mapping, rawForm, request, response);
    
    return (mapping.findForward("edit"));
  }


}
