/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.npower.dm.action.ota.omacp;



import org.apache.struts.validator.DynaValidatorForm;

import com.npower.wap.omacp.OMAClientProvSettings;


/** 
 * MyEclipse Struts
 * Creation date: 11-17-2006
 * 
 * XDoclet definition:
 * @struts.action path="/notification/send" name="NotificationForm" input="/notification/input" scope="request" validate="true"
 * @struts.action-forward name="success" path="aaaaa" contextRelative="true"
 */
public class SendAction4Email extends BaseSendAction {
  
  
  /**
   * Return Email Settings
   * @param form
   * @return
   */
  protected OMAClientProvSettings getSettings(DynaValidatorForm form) {
    OMAClientProvSettings settings = getEmailSettings(form);
    return settings;
  }
  
}