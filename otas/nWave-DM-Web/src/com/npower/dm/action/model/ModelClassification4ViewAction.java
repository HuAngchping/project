/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.npower.dm.action.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.validator.DynaValidatorForm;

import com.npower.dm.action.BaseAction;
import com.npower.dm.core.Model;
import com.npower.dm.core.ModelClassification;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelClassificationBean;

/** 
 * MyEclipse Struts
 * Creation date: 09-05-2008
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class ModelClassification4ViewAction extends BaseAction {
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
    try {
      factory = this.getManagementBeanFactory(request);
      ModelClassificationBean bean = factory.createModelClassificationBean();
      ModelClassification classification = bean.getModelClassificationByID(Long.parseLong(id));
      Set<Model> models = new TreeSet<Model>();
      List<String> locationList = new ArrayList<String>();
      locationList.add(classification.getName());
      models = classification.getModelSelector().getModels();

      request.setAttribute("AddLocationLabels", locationList);
      request.setAttribute("classification", classification);
      request.setAttribute("models", models);        

      DynaValidatorForm searchForm = (DynaValidatorForm)form;
      ModelClassificationsAction.buildUIElementsForSearchPanel(this.getManagementBeanFactory(request), request, searchForm);
      
    } catch (Exception e) {
      throw e;
    }
    return mapping.findForward("view");
  }
}