//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.npower.dm.action.profile;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import com.npower.dm.action.BaseLookupDispatchAction;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileTemplate;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileTemplateBean;

/**
 */
public class SaveProfileTemplateAction extends BaseLookupDispatchAction {

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

    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ProfileTemplateBean bean = factory.createProfileTemplateBean();
    try {
        ProfileTemplate entity = bean.newTemplateInstance();
        
        // Pass target into Audit AOP.
        request.setAttribute("profileTemplate", entity);

        factory.beginTransaction();
        // Copy form Data into POJO
        BeanUtils.populate(entity, form.getMap());
        
        ProfileCategory category = bean.getProfileCategoryByID(form.getString("profileCategoryID"));
        entity.setProfileCategory(category);
        bean.update(entity);
        
        factory.commit();
        
        // Pass target into Audit AOP
        request.setAttribute("profileTemplate", entity);
        
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

    String id = form.getString("ID");
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ProfileTemplateBean bean = factory.createProfileTemplateBean();
    try {
        ProfileTemplate entity = bean.getTemplateByID(id);

        factory.beginTransaction();
        // Copy form Data into POJO
        BeanUtils.populate(entity, form.getMap());
        
        ProfileCategory category = bean.getProfileCategoryByID(form.getString("profileCategoryID"));
        entity.setProfileCategory(category);
        bean.update(entity);
        
        factory.commit();
        
        // Pass target into Audit AOP
        request.setAttribute("profileTemplate", entity);
        
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
