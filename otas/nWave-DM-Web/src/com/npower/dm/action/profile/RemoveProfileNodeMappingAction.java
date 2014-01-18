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
import com.npower.dm.core.DMException;
import com.npower.dm.core.ProfileNodeMapping;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileMappingBean;

/** 
 * MyEclipse Struts
 * Creation date: 06-12-2006
 * 
 * XDoclet definition:
 * @struts.action path="/RemoveProfileNodeMapping" name="ProfileNodeMappingForm" scope="request"
 * @struts.action-forward name="success" path="/EditProfileMapping" contextRelative="true"
 */
public class RemoveProfileNodeMappingAction extends BaseAction {

  // --------------------------------------------------------- Instance Variables

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
        String id = form.getString("ID");
        ManagementBeanFactory factory = this.getManagementBeanFactory(request);
        ProfileMappingBean bean = factory.createProfileMappingBean();
        ProfileNodeMapping entity = bean.getProfileNodeMappingByID(id);
        factory.beginTransaction();
        bean.delete(entity);
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

