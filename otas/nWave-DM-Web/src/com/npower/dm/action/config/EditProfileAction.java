//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.npower.dm.action.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * MyEclipse Struts Creation date: 06-13-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/EditProfileConfig" name="ProfileConfigForm"
 *                scope="request"
 * @struts.action-forward name="edit" path="aaaa" contextRelative="true"
 */
public class EditProfileAction extends AbstractProfileAction {

  // --------------------------------------------------------- Instance
  // Variables

  // --------------------------------------------------------- Methods
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
    ProfileConfigForm4Save form = (ProfileConfigForm4Save) rawForm;

    String profileID = (String)request.getAttribute("ID");
    if (StringUtils.isNotEmpty(profileID)) {
       form.setValue("ID", profileID);
    }
    
    // Load Profile templates for select box
    this.loadProfileTemplates(mapping, rawForm, request, response);
    
    // Load Carriers for select box
    this.loadCarriers(mapping, rawForm, request, response);
    
    // Load Nap Profiles for select box
    this.loadNapProfiles(mapping, rawForm,request, response);
    
    // Load Proxy Profiles for select box
    this.loadProxyProfiles(mapping, rawForm, request, response);
    
    // Load Profile
    this.loadProfile(mapping, rawForm, request, response);
    
    return (mapping.findForward("edit"));
  }

}
