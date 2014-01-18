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
import com.npower.dm.core.ProfileAttribute;
import com.npower.dm.core.ProfileAttributeType;
import com.npower.dm.core.ProfileTemplate;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileTemplateBean;

/**
 * MyEclipse Struts Creation date: 06-08-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/SaveProfileAttribute" name="ProfileAttributeForm"
 *                parameter="action" scope="request" validate="true"
 */
public class SaveProfileAttributeAction extends BaseLookupDispatchAction {

  // --------------------------------------------------------- Instance
  // Variables

  protected Map getKeyMethodMap()
  {
    Map<String, String> map = new HashMap<String, String>();
    map.put("page.button.create.label", "create");
    map.put("page.button.update.label", "update");
    return(map);
  }

  // --------------------------------------------------------- Methods

  public ActionForward cancelled(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    return (mapping.findForward("cancel"));
  }  
  
  public ActionForward unspecified(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    return (mapping.findForward("cancel"));
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
        ProfileAttribute entity = bean.newAttributeInstance();

        factory.beginTransaction();
        // Copy form Data into POJO
        BeanUtils.populate(entity, form.getMap());
        
        ProfileAttributeType type = bean.getProfileAttributeTypeByID(form.getString("attributeTypeID"));
        ProfileTemplate template = bean.getTemplateByID(form.getString("templateID"));
        entity.setProfileAttribType(type);
        entity.setProfileTemplate(template);
        bean.update(entity);
        
        factory.commit();
        
    } catch (Exception ex) {
      factory.rollback();
      throw ex;
    } finally {
    }
    
    return (mapping.findForward("success"));
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
  public ActionForward update(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    DynaValidatorForm form = (DynaValidatorForm) rawForm;

    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ProfileTemplateBean bean = factory.createProfileTemplateBean();
    try {
        ProfileAttribute entity = bean.getProfileAttributeByID(form.getString("attributeID"));

        factory.beginTransaction();
        // Copy form Data into POJO
        BeanUtils.populate(entity, form.getMap());
        
        ProfileAttributeType type = bean.getProfileAttributeTypeByID(form.getString("attributeTypeID"));
        ProfileTemplate template = bean.getTemplateByID(form.getString("templateID"));
        entity.setProfileAttribType(type);
        entity.setProfileTemplate(template);
        bean.update(entity);
        
        factory.commit();
        
    } catch (Exception ex) {
      factory.rollback();
      throw ex;
    } finally {
    }
    
    return (mapping.findForward("success"));
  }
}
