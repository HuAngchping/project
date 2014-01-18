//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.npower.dm.action.subscriber;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import com.npower.dm.action.BaseDispatchAction;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.DMException;
import com.npower.dm.core.ServiceProvider;
import com.npower.dm.core.Subscriber;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ServiceProviderBean;
import com.npower.dm.management.SubscriberBean;

/**
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/SaveDevice" name="DeviceForm"
 *                input="/jsp/device/device.jsp" scope="request" validate="true"
 */
public class SaveSubscriberAction extends BaseDispatchAction {

  // --------------------------------------------------------- Instance
  // Variables

  // --------------------------------------------------------- Methods
  
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
  public ActionForward update(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
                            HttpServletResponse response) throws Exception, IllegalAccessException, InvocationTargetException {
    // Was this transaction cancelled?
    if (isCancelled(request)) {
      return (mapping.findForward("cancel"));
    }

    DynaValidatorForm form = (DynaValidatorForm) rawForm;

    ManagementBeanFactory factory = getManagementBeanFactory(request);
    CarrierBean carrierBean = factory.createCarrierBean();
    ServiceProviderBean spBean = factory.createServiceProviderBean();
    SubscriberBean subscriberBean = factory.createSubcriberBean();

    String subscriberID = (String) form.get("ID");
    Subscriber subscriber = subscriberBean.getSubscriberByID(subscriberID);
    String oldPhoneNumber = subscriber.getPhoneNumber();
    
    if (subscriber != null) {
       String carrierID = (String)form.get("carrierID");
       Carrier carrier = carrierBean.getCarrierByID(carrierID);
       if (carrier == null) {
          throw new DMException("Could not found the carrier: " + carrierID);
       }
       
       String serviceProviderID = (String)form.get("serviceProviderID");
       ServiceProvider sp = spBean.getServiceProviderByID(serviceProviderID);

       // Copy form Data into POJO
       BeanUtils.populate(subscriber, form.getMap());
        
       String newPhoneNumber = subscriber.getPhoneNumber();
       if (StringUtils.isNotEmpty(oldPhoneNumber) && StringUtils.isNotEmpty(newPhoneNumber)
           && !oldPhoneNumber.equals(newPhoneNumber)) {
          // PhoneNumber had been changed, relink with new carrier
          Carrier newCarrier = carrierBean.findCarrierByPhoneNumberPolicy(newPhoneNumber);
          if (newCarrier != null && newCarrier.getID() != carrier.getID()) {
             // Carrier changed!
             carrier = newCarrier;
          }
       }
       subscriber.setCarrier(carrier);
       subscriber.setServiceProvider(sp);
       
       try {
           factory.beginTransaction();
           subscriberBean.update(subscriber);
           factory.commit();
       } catch (Exception e) {
         factory.rollback();
         throw e;
       }
    }
    return (mapping.findForward("view"));
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

    DynaValidatorForm subscriberForm = (DynaValidatorForm) form;

    ManagementBeanFactory factory = getManagementBeanFactory(request);
    CarrierBean carrierBean = factory.createCarrierBean();
    SubscriberBean subscriberBean = factory.createSubcriberBean();
    ServiceProviderBean spBean = factory.createServiceProviderBean();

    String carrierID = (String)subscriberForm.get("carrierID");
    Carrier carrier = null;
    if (StringUtils.isNotEmpty(carrierID)) {
       carrier = carrierBean.getCarrierByID(carrierID);
    }
    
    String serviceProviderID = (String)subscriberForm.get("serviceProviderID");
    ServiceProvider sp = spBean.getServiceProviderByID(serviceProviderID);

    String phoneNumber = subscriberForm.getString("phoneNumber");
    try {
        factory.beginTransaction();
        
        Subscriber subscriber = subscriberBean.newSubscriberInstance();
        // Copy form Data into POJO
        BeanUtils.populate(subscriber, subscriberForm.getMap());
        
        if (StringUtils.isNotEmpty(phoneNumber)) {
          // Relink with new carrier
          Carrier newCarrier = carrierBean.findCarrierByPhoneNumberPolicy(phoneNumber);
          if (newCarrier != null && newCarrier.getID() != carrier.getID()) {
             // Carrier changed!
             carrier = newCarrier;
          }
        }
        if (carrier == null) {
           throw new DMException("Please specify a carrier for subscriber.");
        }
        subscriber.setCarrier(carrier);

        if (sp != null) {
           subscriber.setServiceProvider(sp);
        }
       
        subscriberBean.update(subscriber);
        factory.commit();
        
        request.setAttribute("subscriber", subscriber);
        request.setAttribute("subscriberID", "" + subscriber.getID());
        
    } catch (Exception ex) {
      factory.rollback();
      throw ex;
    } finally {
    }
    return (mapping.findForward("view"));
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
