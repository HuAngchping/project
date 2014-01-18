//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.npower.dm.action.profile;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import com.npower.dm.action.BaseAction;
import com.npower.dm.core.ProfileAttribute;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileTemplateBean;

/**
 * MyEclipse Struts Creation date: 06-08-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/EditProfileAttribute" name="ProfileAttributeForm"
 *                scope="request"
 * @struts.action-forward name="success" path="/jsp/profile/attribute.jsp"
 *                        contextRelative="true"
 */
public class EditProfileAttributeAction extends BaseAction {

  // --------------------------------------------------------- Instance
  // Variables

  // --------------------------------------------------------- Methods

  private void loadProfileAttribute(ActionForm rawForm, HttpServletRequest request) throws Exception {
    DynaValidatorForm form = (DynaValidatorForm) rawForm;
    // Fetch Template ID from request
    String templateID = request.getParameter("templateID");
    templateID = (templateID == null || templateID.trim().length() == 0)?request.getParameter("ID"):templateID;
    form.set("templateID", templateID);
    
    String id = form.getString("attributeID");
    if (id != null && id.trim().length() >= 0) {
       ManagementBeanFactory factory = this.getManagementBeanFactory(request);
       ProfileTemplateBean bean = factory.createProfileTemplateBean();
       ProfileAttribute entity = bean.getProfileAttributeByID(id);
       if (entity != null) {
          form.getMap().putAll(BeanUtils.describe(entity));
          form.set("attributeTypeID", "" + entity.getProfileAttribType().getID());
          if (entity.getIsMultivalued()) {
             request.setAttribute("values", entity.getProfileAttribValues());
          }
       }
    }
  }

  private void loadAttributeTypes(ActionForm rawForm, HttpServletRequest request) throws Exception {
    DynaValidatorForm form = (DynaValidatorForm) rawForm;
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ProfileTemplateBean bean = factory.createProfileTemplateBean();
    List options = bean.findProfileAttributeTypes("from ProfileAttributeTypeEntity");
    request.setAttribute("profileAttributeTypes", options);
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
    // Load template
    this.loadProfileAttribute(rawForm, request);    

    // load categories
    this.loadAttributeTypes(rawForm, request);
    
    return (mapping.findForward("success"));
  }

}
