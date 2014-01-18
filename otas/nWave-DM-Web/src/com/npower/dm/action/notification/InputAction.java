/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.npower.dm.action.notification;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import com.npower.dm.action.BaseAction;


/** 
 * MyEclipse Struts
 * Creation date: 11-17-2006
 * 
 * XDoclet definition:
 * @struts.action path="/notification/send" name="NotificationForm" input="/notification/input" scope="request" validate="true"
 * @struts.action-forward name="success" path="aaaaa" contextRelative="true"
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
    
    if (this.isCancelled(request)) {
       return this.canceld(mapping, rawForm, request, response);
    }
    DynaValidatorForm form = (DynaValidatorForm) rawForm;
    try {
        // Set default value
        // USER_INTERACTION
        form.set("uiMode", "3");
        // Server Initiator
        form.set("initiator", "1");
        return mapping.findForward("input");
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
  public ActionForward canceld(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    return mapping.findForward("input");
  }
}