//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.npower.dm.action.device;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorForm;

import com.npower.dm.action.BaseLookupDispatchAction;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.Model;
import com.npower.dm.core.Subscriber;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.SubscriberBean;

/**
 * MyEclipse Struts Creation date: 06-01-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/SaveDevice" name="DeviceForm"
 *                input="/jsp/device/device.jsp" scope="request" validate="true"
 */
public class SaveDeviceAction extends BaseLookupDispatchAction {

  // --------------------------------------------------------- Instance
  // Variables

  // --------------------------------------------------------- Methods

  protected Map getKeyMethodMap()
  {
    Map<String, String> map = new HashMap<String, String>();
    map.put("page.button.create.label", "create");
    map.put("page.button.update.label", "update");
    return(map);
  }
  
  /**
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @throws DMException
   * @throws IllegalAccessException
   * @throws InvocationTargetException
   */
  public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                            HttpServletResponse response) throws Exception, IllegalAccessException, InvocationTargetException {
    // Was this transaction cancelled?
    if (isCancelled(request)) {
      return (mapping.findForward("cancel"));
    }

    DynaValidatorForm deviceForm = (DynaValidatorForm) form;

    ManagementBeanFactory factory = getManagementBeanFactory(request);
    DeviceBean deviceBean = factory.createDeviceBean();
    ModelBean modelBean = factory.createModelBean();
    CarrierBean carrierBean = factory.createCarrierBean();
    SubscriberBean subscriberBean = factory.createSubcriberBean();

    String deviceID = (String) deviceForm.get("ID");
    Device device = deviceBean.getDeviceByID(deviceID);
    
    if (device != null) {
       String modelID = (String) deviceForm.get("modelID");
       Model model = modelBean.getModelByID(modelID);
       if (model == null) {
          throw new DMException("Could not found the model: " + modelID);
       }
       
       String carrierID = (String)deviceForm.get("carrierID");
       Carrier carrier = carrierBean.getCarrierByID(carrierID);
       if (carrier == null) {
          throw new DMException("Could not found the carrier: " + carrierID);
       }
       if (model != null) {
          // Copy form Data into POJO
          BeanUtils.populate(device, deviceForm.getMap());
          device.setModel(model);
          String pinTypeStr = request.getParameter("bootstrapPinType");
          if (StringUtils.isEmpty(pinTypeStr)) {
             // Byte 类型，Struts Form将null解释为0, 与NETWPIN取值相同, 故而作此额外处理
             device.setBootstrapPinType(null);
          }

          String newPhoneNumber = device.getSubscriberPhoneNumber();
          Subscriber subscriber = subscriberBean.getSubscriberByPhoneNumber(newPhoneNumber);
          if (subscriber == null) {
             subscriber = subscriberBean.newSubscriberInstance();
          } else {
            String oldPhoneNumber = subscriber.getPhoneNumber();
            if (StringUtils.isNotEmpty(oldPhoneNumber) && StringUtils.isNotEmpty(newPhoneNumber)
                  && !oldPhoneNumber.equals(newPhoneNumber)) {
               // PhoneNumber had been changed, relink with new carrier
               Carrier newCarrier = carrierBean.findCarrierByPhoneNumberPolicy(newPhoneNumber);
               if (newCarrier != null && newCarrier.getID() != carrier.getID()) {
                  // Carrier changed!
                  carrier = newCarrier;
               }
            }
          }
          subscriber.setCarrier(carrier);
          subscriber.setExternalId(device.getSubscriberPhoneNumber());
          subscriber.setPhoneNumber(device.getSubscriberPhoneNumber());
          subscriber.setPassword(device.getOMAClientPassword());
          try {
              factory.beginTransaction();
              subscriberBean.update(subscriber);
              device.setSubscriber(subscriber);
              deviceBean.update(device);
              factory.commit();
              request.setAttribute("device", device);
          } catch (Exception e) {
            factory.rollback();
            throw e;
          }
       }
    }
    
    return (mapping.findForward("success"));
  }

  /**
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @throws DMException
   * @throws IllegalAccessException
   * @throws InvocationTargetException
   */
  public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                            HttpServletResponse response) throws Exception, IllegalAccessException, InvocationTargetException {
    
    // Was this transaction cancelled?
    if (isCancelled(request)) {
      return (mapping.findForward("cancel"));
    }

    DynaValidatorForm deviceForm = (DynaValidatorForm) form;

    ManagementBeanFactory factory = getManagementBeanFactory(request);
    DeviceBean deviceBean = factory.createDeviceBean();
    ModelBean modelBean = factory.createModelBean();
    CarrierBean carrierBean = factory.createCarrierBean();
    SubscriberBean subscriberBean = factory.createSubcriberBean();

    String carrierID = (String)deviceForm.get("carrierID");
    Carrier carrier = carrierBean.getCarrierByID(carrierID);
    
    String phoneNumber = (String)deviceForm.get("subscriberPhoneNumber");
    String subscriberExtID = phoneNumber;
    String subscriberPassword = (String)deviceForm.get("OMAClientPassword");
    
    String deviceExternalID = (String)deviceForm.get("externalId");
    String modelID = (String) deviceForm.get("modelID");
    Model model = null;
    if (StringUtils.isNotEmpty(modelID)) {
       model = modelBean.getModelByID(modelID);
    }
    if (model == null) {
       // Found model by TAC Info.
       model = modelBean.getModelbyTAC(deviceExternalID);
    }
    if (model == null) {
       ActionMessages errors = new ActionMessages();
       errors.add("device.error.missingModel", new ActionMessage("page.device.message.missingModel"));
       saveErrors(request, errors);
       return mapping.getInputForward();
    }
    

    try {
        factory.beginTransaction();
        
        if (StringUtils.isNotEmpty(phoneNumber)) {
           // Relink with new carrier
           Carrier newCarrier = carrierBean.findCarrierByPhoneNumberPolicy(phoneNumber);
           if (newCarrier != null && newCarrier.getID() != carrier.getID()) {
              // Carrier changed!
              carrier = newCarrier;
           }
        }
        
        Subscriber subscriber = subscriberBean.getSubscriberByPhoneNumber(phoneNumber);
        if (subscriber == null) {
          subscriber = subscriberBean.newSubscriberInstance(carrier, subscriberExtID, phoneNumber, subscriberPassword);
        } else {
          subscriber.setCarrier(carrier);
          subscriber.setExternalId(subscriberExtID);
          subscriber.setPhoneNumber(phoneNumber);
          subscriber.setPassword(subscriberPassword);
        }
        subscriberBean.update(subscriber);
        
        
        Device device = deviceBean.newDeviceInstance(subscriber, model, deviceExternalID);
        // Copy form Data into POJO
        BeanUtils.populate(device, deviceForm.getMap());
        String pinTypeStr = request.getParameter("bootstrapPinType");
        if (StringUtils.isEmpty(pinTypeStr)) {
           // Byte 类型，Struts Form将null解释为0, 与NETWPIN取值相同, 故而作此额外处理
           device.setBootstrapPinType(null);
        }
        deviceBean.update(device);
        
        factory.commit();
        
        request.setAttribute("device", device);
        
    } catch (Exception ex) {
      factory.rollback();
      throw ex;
    } finally {
    }
    
    return (mapping.findForward("success"));
  }

  public ActionForward cancelled(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    return (mapping.findForward("cancel"));
  }  
  
  public ActionForward unspecified(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    return (mapping.findForward("cancel"));
  }  
}
