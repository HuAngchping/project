/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.npower.dm.action.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.validator.DynaValidatorForm;

import com.npower.dm.action.BaseAction;
import com.npower.dm.core.ModelClassification;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelClassificationBean;

/** 
 * MyEclipse Struts
 * Creation date: 09-08-2008
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class ModelClassification4EditAction extends BaseAction {
  /*
   * Generated Methods
   */

  /** 
   * Method execute
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward execute(ActionMapping mapping, ActionForm Rowform, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    DynaActionForm form = (DynaActionForm) Rowform;
    String id = form.getString("id");
    ManagementBeanFactory factory = null;
    if (StringUtils.isNotEmpty(id)) {
      factory = this.getManagementBeanFactory(request);
      ModelClassificationBean bean = factory.createModelClassificationBean();
      ModelClassification mc = bean.getModelClassificationByID(Long.parseLong(id));
      form.set("name", mc.getName());
      form.set("externalID", mc.getExternalID());
      form.set("description", mc.getDescription());
      request.setAttribute("modelClassification", mc);
      
    }
    DynaValidatorForm searchForm = (DynaValidatorForm)form;
    ModelClassificationsAction.buildUIElementsForSearchPanel(this.getManagementBeanFactory(request), request, searchForm);
    return mapping.findForward("edit");
  }
}