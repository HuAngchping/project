//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.npower.dm.action.security;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import com.npower.dm.action.BaseDispatchAction;
import com.npower.dm.core.DMException;
import com.npower.dm.security.DMWebPrincipal;
import com.npower.dm.security.DMWebPrincipalImpl;
import com.npower.dm.security.SecurityService;
import com.npower.dm.security.SecurityServiceFactory;

/**
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/SaveDevice" name="DeviceForm"
 *                input="/jsp/device/device.jsp" scope="request" validate="true"
 */
public class SaveUserAction extends BaseDispatchAction {

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

    SecurityServiceFactory securityFactory = SecurityServiceFactory.newInstance();
    SecurityService service = securityFactory.getSecurityService(); 
    DMWebPrincipal user = service.get(form.getString("username"));
    
    String password = form.getString("password");
    String confirmPassword = form.getString("confirmPassword");
    
    if (user != null) {
       // Copy form Data into POJO
       BeanUtils.populate(user, form.getMap());
       
       String[] roles = form.getStrings("formRoles");
       List<String> result = new ArrayList<String>();
       for (String role: roles) {
           result.add(role);
       }
       user.setRoles(result);
       
       String[] manufacturersExtIDs = form.getStrings("formManufacturerExternalIDs");
       result = new ArrayList<String>();
       for (String extID: manufacturersExtIDs) {
           result.add(extID);
       }
       user.setOwnedManufacturerExternalIDs(result);
       
       String[] carrierExtIDs = form.getStrings("formCarrierExternalIDs");
       result = new ArrayList<String>();
       for (String extID: carrierExtIDs) {
           result.add(extID);
       }
       user.setOwnedCarrierExternalIDs(result); 
       
       try {
           service.update(user);
           
           if (StringUtils.isNotEmpty(password) && password.equals(confirmPassword)) {
              service.updatePassword(user, password);
           }
       } catch (Exception e) {
         throw e;
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
  public ActionForward create(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
                            HttpServletResponse response) throws Exception, IllegalAccessException, InvocationTargetException {
    
    // Was this transaction cancelled?
    if (isCancelled(request)) {
      return (mapping.findForward("cancel"));
    }

    DynaValidatorForm form = (DynaValidatorForm) rawForm;

    String password = form.getString("password");
    String confirmPassword = form.getString("confirmPassword");
    try {
        SecurityServiceFactory securityFactory = SecurityServiceFactory.newInstance();
        SecurityService service = securityFactory.getSecurityService(); 
        
        DMWebPrincipal user = new DMWebPrincipalImpl();
        // Copy form Data into POJO
        BeanUtils.populate(user, form.getMap());

        String[] roles = form.getStrings("formRoles");
        List<String> result = new ArrayList<String>();
        for (String role: roles) {
            result.add(role);
        }
        user.setRoles(result);
        
        String[] manufacturersExtIDs = form.getStrings("formManufacturerExternalIDs");
        result = new ArrayList<String>();
        for (String extID: manufacturersExtIDs) {
            result.add(extID);
        }
        user.setOwnedManufacturerExternalIDs(result);
        
        String[] carrierExtIDs = form.getStrings("formCarrierExternalIDs");
        result = new ArrayList<String>();
        for (String extID: carrierExtIDs) {
            result.add(extID);
        }
        user.setOwnedCarrierExternalIDs(result); 
        
        service.add(user);
        
        if (StringUtils.isNotEmpty(password) && password.equals(confirmPassword)) {
           service.updatePassword(user, password);
        }
    } catch (Exception ex) {
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
