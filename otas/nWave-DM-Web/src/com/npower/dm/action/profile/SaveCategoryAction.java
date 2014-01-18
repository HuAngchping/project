//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.npower.dm.action.profile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import com.npower.dm.action.BaseDispatchAction;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileTemplateBean;

/**
 * MyEclipse Struts Creation date: 06-05-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/SaveProfileCategory" name="ProfileCategoryForm"
 *                input="/Categories.do" scope="request" validate="true"
 */
public class SaveCategoryAction extends BaseDispatchAction {

  // --------------------------------------------------------- Instance
  // Variables

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
   * Method execute
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    DynaValidatorForm profileCategoryForm = (DynaValidatorForm) form;

    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ProfileTemplateBean bean = factory.createProfileTemplateBean();
    try {
        ProfileCategory category = bean.newProfileCategoryInstance();

        factory.beginTransaction();
        // Copy form Data into POJO
        BeanUtils.populate(category, profileCategoryForm.getMap());
        bean.updateCategory(category);
        
        factory.commit();
        
    } catch (Exception ex) {
      factory.rollback();
      throw ex;
    } finally {
    }
    
    return (mapping.findForward("success"));
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
  public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    DynaValidatorForm profileCategoryForm = (DynaValidatorForm) form;

    // Was this transaction cancelled?
    if (isCancelled(request)) {
       return (mapping.findForward("cancel"));
    }
    String categoryID = profileCategoryForm.getString("ID");
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ProfileTemplateBean bean = factory.createProfileTemplateBean();
    try {
        ProfileCategory category = bean.getProfileCategoryByID(categoryID);

        factory.beginTransaction();
        // Copy form Data into POJO
        BeanUtils.populate(category, profileCategoryForm.getMap());
        bean.updateCategory(category);
        
        factory.commit();
        
    } catch (Exception ex) {
      factory.rollback();
      throw ex;
    } finally {
    }
    
    return (mapping.findForward("success"));
  }
}
