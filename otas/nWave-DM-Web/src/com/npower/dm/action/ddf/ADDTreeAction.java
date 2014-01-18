package com.npower.dm.action.ddf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import com.npower.dm.action.BaseAction;
import com.npower.dm.core.Model;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;

public class ADDTreeAction extends BaseAction {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

    DynaValidatorForm ddfTreeForm = (DynaValidatorForm) form;
    String modelID = (String) ddfTreeForm.get("modelID");

    if (modelID == null || modelID.trim().length() == 0) {
      modelID = "10026950";
    }
    
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ModelBean modelBean = factory.createModelBean();
    
    Model model = modelBean.getModelByID(modelID);
    
    String modelName = model.getName();
    String manufacturerName = model.getManufacturer().getName();
    
    request.getSession().setAttribute("modelName", modelName);
    request.getSession().setAttribute("manufacturerName", manufacturerName);

    return (mapping.findForward("uploadfile"));

  }

}
