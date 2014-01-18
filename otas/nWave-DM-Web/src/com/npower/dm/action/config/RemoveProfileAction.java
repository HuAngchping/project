//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.npower.dm.action.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.npower.dm.action.BaseAction;
import com.npower.dm.core.DMException;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileConfigBean;

/** 
 * MyEclipse Struts
 * Creation date: 06-13-2006
 * 
 * XDoclet definition:
 * @struts.action path="/RemoveProfileConfig" name="ProfileConfigForm" scope="request"
 * @struts.action-forward name="success" path="/ProfileConfigs.do" contextRelative="true"
 */
public class RemoveProfileAction extends BaseAction {

  // --------------------------------------------------------- Instance Variables

  // --------------------------------------------------------- Methods

  /** 
   * Method execute
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
        ProfileConfigBean bean = factory.createProfileConfigBean();
        ProfileConfig profile = bean.getProfileConfigByID(id);
        factory.beginTransaction();
        bean.deleteProfileConfig(profile);
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

