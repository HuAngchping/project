package com.npower.dm.action.bootstrap;
/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorForm;

import com.npower.dm.action.ActionHelper;
import com.npower.dm.action.BaseDispatchAction;
import com.npower.dm.bootstrap.BootstrapService;
import com.npower.dm.core.Carrier;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.util.ConfigHelper;
import com.npower.dm.util.NumberGenerator;
import com.npower.dm.util.NumberGeneratorFactory;
import com.npower.sms.client.SMSGWConnectionException;
import com.npower.wap.omacp.OMACPSecurityMethod;

/** 
 * @struts.action
 */
public class BootstrapAction extends BaseDispatchAction {
  
  /** 
   * Method execute
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward input(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    try {
        DynaValidatorForm form = (DynaValidatorForm) rawForm;
        form.set("pinType", OMACPSecurityMethod.USERPIN.getValue());
        return mapping.findForward("input");
    } catch (Exception e) {
      throw e;
    }
  }
  
  /** 
   * Method execute
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward send(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    
    DynaValidatorForm form = (DynaValidatorForm) rawForm;
    ActionMessages errors = form.validate(mapping, request);
    if (errors == null || errors.size() > 0) {
       this.saveErrors(request, errors);
       return this.input(mapping, rawForm, request, response);  
    }
    
    String pin = form.getString("pin");
    
    String numberTemplate = form.getString("msisdn");
    NumberGeneratorFactory numberGeneratorFactory = NumberGeneratorFactory.newInstance();
    NumberGenerator numberGenerator = numberGeneratorFactory.getNumberGenerator(numberTemplate);
    List<String> numbers = numberGenerator.generate();
    
    String serverPassword = form.getString("serverPassword");
    String serverNonce = null;
    String clientUsername = form.getString("clientUsername");
    String clientPassword = form.getString("clientPassword");
    String clientNonce = null;
    
    OMACPSecurityMethod pinType = OMACPSecurityMethod.value((Byte)form.get("pinType"));

    ManagementBeanFactory beanFactory = this.getManagementBeanFactory(request);
    try {
        CarrierBean carrierBean = beanFactory.createCarrierBean();
        
        // Get Time of schedule
        String jobScheduleString = form.getString("jobSchedule");
        Date scheduledTime = new Date();
        if (StringUtils.isNotEmpty(jobScheduleString)) {
           DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
           scheduledTime = formatter.parse(jobScheduleString);
        }
        
        for (String msisdn: numbers) {
            // Get BootstrapService.
            Carrier carrier = carrierBean.findCarrierByPhoneNumberPolicy(msisdn);
            BootstrapService service = ActionHelper.getBootstrapService(beanFactory, carrier);
            int maxRetry = -1;
            long maxDuration = -1;
            if (carrier != null) {
               maxRetry = (int)carrier.getNotificationMaxNumRetries();
               maxDuration = carrier.getBootstrapTimeout() * 1000;
            }
            service.bootstrap(msisdn, serverPassword, serverNonce, clientUsername, clientPassword, clientNonce, pinType, pin, 
                              scheduledTime.getTime(), maxRetry, maxDuration);
        }
        
        return mapping.findForward("success");
    } catch (SMSGWConnectionException e) {
      return mapping.findForward("failure");
    } catch (Exception e) {
      throw e;
    } finally {
    }
  }

  /** 
   * Method execute
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward unspecified(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    Properties profile = ConfigHelper.getGlobalBootstrapProfile();
    DynaValidatorForm form = (DynaValidatorForm) rawForm;
    form.set("serverURL", profile.getProperty("dm_service_url"));    
    form.set("serverID", profile.getProperty("dm_service_id"));    
    return this.input(mapping, rawForm, request, response);
  }
}