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
import com.npower.dm.core.ProfileTemplate;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileTemplateBean;

/**
 * MyEclipse Struts Creation date: 06-07-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 * @struts.action-forward name="success" path="/jsp/profile/template.jsp"
 *                        contextRelative="true"
 */
public abstract class AbstractProfileTemplateAction extends BaseAction {

  // --------------------------------------------------------- Instance
  // Variables

  // --------------------------------------------------------- Methods

  protected void loadProfileTemplate(ActionForm rawForm, HttpServletRequest request) throws Exception {
    DynaValidatorForm form = (DynaValidatorForm) rawForm;
    String id = request.getParameter("templateID");
    
    id = (id == null || id.trim().length() == 0)?form.getString("ID"):id;
    
    request.getSession().setAttribute("tempateID", id);
    
    if (id != null && id.trim().length() >= 0) {
       ManagementBeanFactory factory = this.getManagementBeanFactory(request);
       ProfileTemplateBean bean = factory.createProfileTemplateBean();
       ProfileTemplate template = bean.getTemplateByID(id);
       if (template != null) {
          form.getMap().putAll(BeanUtils.describe(template));
          request.setAttribute("profileTemplate", template);
          form.set("profileCategoryID", "" + template.getProfileCategory().getID());
          request.setAttribute("attributes", template.getProfileAttributes());
          request.setAttribute("models", template.getModels());
          request.setAttribute("profileCategory", template.getProfileCategory());
       }
    }
  }
  
  protected void loadCategories(ActionForm rawForm, HttpServletRequest request) throws Exception {
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ProfileTemplateBean bean = factory.createProfileTemplateBean();
    List categories = bean.findProfileCategories("from ProfileCategoryEntity");
    request.setAttribute("categories", categories);
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
  public abstract ActionForward execute(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception;

}
