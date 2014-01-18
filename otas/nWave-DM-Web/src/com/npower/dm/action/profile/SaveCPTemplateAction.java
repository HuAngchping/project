//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.npower.dm.action.profile;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import com.npower.dm.action.BaseLookupDispatchAction;
import com.npower.dm.core.ClientProvTemplate;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.management.ClientProvTemplateBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.ProfileTemplateBean;

/**
 */
public class SaveCPTemplateAction extends BaseLookupDispatchAction {

  // --------------------------------------------------------- Instance
  // Variables

  // --------------------------------------------------------- Methods

  protected Map getKeyMethodMap()
  {
    Map<String, String> map = new HashMap<String, String>();
    map.put("page.button.create.label", "create");
    map.put("page.button.update.label", "update");
    return(map);
  }

  /**
   * create a AttributeType
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward create(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    DynaValidatorForm form = (DynaValidatorForm) rawForm;

    String modelID = form.getString("modelID");

    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ProfileTemplateBean categoryBean = factory.createProfileTemplateBean();
    ModelBean modelBean = factory.createModelBean();
    ClientProvTemplateBean bean = factory.createClientProvTemplateBean();
    try {
        Model model = modelBean.getModelByID(modelID);
        ProfileCategory category = categoryBean.getProfileCategoryByID(form.getString("categoryID"));
        ClientProvTemplate entity = bean.findTemplate(model, category);
        if (entity == null) {
           entity = bean.newClientProvTemplateInstance();
        }

        factory.beginTransaction();
        // Copy form Data into POJO
        entity.setName(form.getString("name"));
        entity.setExternalID(form.getString("name"));
        entity.setDescription(form.getString("description"));
        entity.setContent(form.getString("content"));
        entity.setEncoder(form.getString("encoder"));
        
        entity.setProfileCategory(category);
        bean.update(entity);
        
        bean.attach(model, entity);
        
        factory.commit();
        
        // Pass target into Audit AOP
        request.setAttribute("template", entity);
        
    } catch (Exception ex) {
      factory.rollback();
      throw ex;
    } finally {
    }
    return (mapping.findForward("success"));
  }

  /**
   * Update a AttributeType
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward update(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    DynaValidatorForm form = (DynaValidatorForm) rawForm;

    String id = form.getString("templateID");
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ProfileTemplateBean categoryBean = factory.createProfileTemplateBean();
    ClientProvTemplateBean bean = factory.createClientProvTemplateBean();
    try {
        ClientProvTemplate entity = bean.getTemplate(id);

        factory.beginTransaction();
        // Copy form Data into POJO
        entity.setName(form.getString("name"));
        entity.setDescription(form.getString("description"));
        entity.setContent(form.getString("content"));
        entity.setEncoder(form.getString("encoder"));
        
        ProfileCategory category = categoryBean.getProfileCategoryByID(form.getString("categoryID"));
        entity.setProfileCategory(category);
        bean.update(entity);
        
        factory.commit();
        
        // Pass target into Audit AOP
        request.setAttribute("template", entity);
        
    } catch (Exception ex) {
      factory.rollback();
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
