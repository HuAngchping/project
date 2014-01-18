//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.npower.dm.action.profile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.npower.dm.action.BaseAction;
import com.npower.dm.core.ClientProvTemplate;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Model;
import com.npower.dm.management.ClientProvTemplateBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;

/**
 * MyEclipse Struts Creation date: 06-05-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/RemoveProfileCategory" name="ProfileCategoryForm"
 *                parameter="action" scope="request"
 */
public class RemoveCPTemplateAction extends BaseAction {

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
    DynaActionForm form = (DynaActionForm) rawForm;

    try {
        String templateID = form.getString("templateID");
        String modelID = form.getString("modelID");
        ManagementBeanFactory factory = this.getManagementBeanFactory(request);
        ClientProvTemplateBean templateBean = factory.createClientProvTemplateBean();
        ClientProvTemplate template = templateBean.getTemplate(templateID);
        ModelBean modelBean = factory.createModelBean();
        Model model = modelBean.getModelByID(modelID);
        
        factory.beginTransaction();
        if (model != null && template != null) {
           templateBean.dettach(model, template);
        }
        if (template != null) {
           templateBean.delete(template);
        }
        factory.commit();
        
    } catch (DMException e) {
      throw e;
    } catch (Exception e) {
      throw e;
    } finally {
    }
    return (mapping.findForward("success"));
  }

}
