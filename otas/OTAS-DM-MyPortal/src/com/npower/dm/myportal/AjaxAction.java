//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.npower.dm.myportal;

import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.npower.dm.action.PersistentManager;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.decorator.DecoratorHelper;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.ProfileConfigBean;

/**
 * MyEclipse Struts Creation date: 06-01-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action parameter="method" validate="true"
 */
public class AjaxAction extends DispatchAction {

  // --------------------------------------------------------- Instance
  // Variables

  private StringBuffer getModelsByManufacturerIdForAjax(HttpServletRequest request, String manufacturerID) throws DMException {
    ManagementBeanFactory factory = PersistentManager.getManagementBeanFactory(request);
    ModelBean modelBean = factory.createModelBean();
    StringBuffer result = new StringBuffer();
    Manufacturer manufacturer = modelBean.getManufacturerByID(manufacturerID);
    if (manufacturer == null) {
      return result;
    }
    Set<Model> mSet = manufacturer.getModels();
    // Sorting
    Set<Model> models = new TreeSet<Model>();
    models.addAll(mSet);
    boolean first = true;
    for (Model model : models) {
      String id = "" + model.getID();
      String name = model.getName();

      if (!first) {
        result.append("|");
      } else {
        first = false;
      }
      result.append(name);
      result.append(",");
      result.append(id);
    }
    return result;
  }

  // --------------------------------------------------------- Methods

  /**
   * Return Model belongs to manufacturerID.
   * @param mapping
   * @param form
   * @param req
   * @param res
   * @return
   * @throws Exception
   */
  public ActionForward getModels(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws Exception {

    String result = this.getModelsByManufacturerIdForAjax(req, req.getParameter("manufacturerID")).toString();
    res.getWriter().write(result);
    return null;
  }
  
  public ActionForward getProfileDescription(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws Exception {
    String profileID = req.getParameter("profileID");
    StringBuffer result = new StringBuffer();
    String content = null;
    if (StringUtils.isNotEmpty(profileID)) {
       ManagementBeanFactory factory = PersistentManager.getManagementBeanFactory(req);
       ProfileConfigBean bean = factory.createProfileConfigBean();
       ProfileConfig profile = bean.getProfileConfigByExternalID(profileID);
       if (profile != null && StringUtils.isNotEmpty(profile.getDescription())) {
          content = profile.getDescription();
       } else {
         content =  DecoratorHelper.decorate(profile.getProfileTemplate().getProfileCategory(), this.getLocale(req)).getDescription();
       }
    }
    result.append("<div class=\"ProfileDescription\">");
    result.append(content);
    result.append("</div>");
    
    res.setContentType("text/html;charset=UTF-8");
    res.getWriter().write(result.toString());
    return null;
  }
}
