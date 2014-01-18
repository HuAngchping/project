/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.npower.dm.action.sms.wappushsi;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import com.npower.dm.action.ActionHelper;
import com.npower.dm.action.BaseAction;


/** 
 */
public class InputAction extends BaseAction {
  
  /** 
   * Method execute
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward execute(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    request.setAttribute("carrierOptions", ActionHelper.getCarrierOptions(this.getManagementBeanFactory(request)));
    DynaValidatorForm form = (DynaValidatorForm)rawForm;
    try {
        form.set("minDuration", new Integer(0));
        form.set("maxDuration", new Integer(0));
        return mapping.findForward("input");
    } catch (Exception e) {
      throw e;
    } finally {
    }
  }
  
}