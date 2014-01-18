//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.npower.dm.action;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;

import com.npower.dm.chart.bean.RuntimeInfoAccessorBean;
import com.npower.dm.chart.jmx.RuntimeInformation;
import com.npower.dm.core.Country;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileAttribute;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileTemplate;
import com.npower.dm.management.CountryBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.ProfileTemplateBean;

/**
 * MyEclipse Struts Creation date: 06-01-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action parameter="method" validate="true"
 */
public class AjaxAction extends BaseDispatchAction {

  // --------------------------------------------------------- Instance
  // Variables

  private StringBuffer getModelsByManufacturerIdForAjax(HttpServletRequest request, String manufacturerID) throws DMException {
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
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
      String name = model.getManufacturerModelId();

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

  private StringBuffer getModelsByCountryIdForAjax(HttpServletRequest request, String countryID) throws DMException {
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    CountryBean countryBean = factory.createCountryBean();
    StringBuffer result = new StringBuffer();

    List<Country> countries = countryBean.getAllCountries();
    boolean first = true;
    for (Country country : countries) {
      String id = "" + country.getID();
      String name = country.getName();

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

  private StringBuffer getAuditLogActions4Ajax(HttpServletRequest request, String actionType) throws DMException {
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    StringBuffer result = new StringBuffer();

    MessageResources messageResources = this.getResources(request);
    Collection<LabelValueBean> beans = ActionHelper.getAuditActionOptions(factory, messageResources, this.getLocale(request), actionType);
    boolean first = true;
    for (LabelValueBean bean : beans) {
      String id = "" + bean.getValue();
      String name = bean.getLabel();

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

  /**
   * 
   * @param mapping
   * @param form
   * @param req
   * @param res
   * @return
   * @throws Exception
   */
  public ActionForward getCountrys(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws Exception {

    String result = this.getModelsByCountryIdForAjax(req, req.getParameter("countryID")).toString();
    res.getWriter().write(result);
    return null;
  }

  /**
   * Return AuditAction Types for dropdown select box.
   * @param mapping
   * @param form
   * @param req
   * @param res
   * @return
   * @throws Exception
   */
  public ActionForward getAuditLogActions(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws Exception {

    String result = this.getAuditLogActions4Ajax(req, req.getParameter("auditActionType")).toString();
    res.getWriter().write(result);
    return null;
  }
  
  /**
   * Return Memory information in Ajax mode.
   * @param mapping
   * @param form
   * @param req
   * @param res
   * @return
   * @throws Exception
   */
  public ActionForward getOSMemoInfoAction(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws Exception {
    RuntimeInfoAccessorBean runtimeInfoAccessor = new RuntimeInfoAccessorBean();
    RuntimeInformation runtimeInfo = runtimeInfoAccessor.getRuntimeInformation();
    req.setAttribute("runtime", runtimeInfo);
    
    return mapping.findForward("osMemoInfo");
  }
  
  /**
   * 为ProfileNodeMapping编辑页面提供支持, 根据categoryName提取对应ProfileTemplate的属性列表.
   * 如果categoryName为null, 将根据templateID提取对应ProfileTemplate的属性列表.
   * @param mapping
   * @param form
   * @param request
   * @param res
   * @return
   * @throws Exception
   */
  public ActionForward getAttributes4NodeMapping(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse res) throws Exception {
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    String linkedCategoryName = request.getParameter("categoryName");
    StringBuffer result = new StringBuffer();
   
    Set<ProfileAttribute> attributes = new java.util.HashSet<ProfileAttribute>();
    
    ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
    if (StringUtils.isEmpty(linkedCategoryName)) {
       String templateID = request.getParameter("templateID");
       if (StringUtils.isNotEmpty(templateID)) {
          ProfileTemplate template = templateBean.getTemplateByID(templateID);
          if (template != null) {
             attributes = template.getProfileAttributes();
          }
       }
    } else {
      ProfileCategory category = templateBean.getProfileCategoryByName(linkedCategoryName); 
      if (category != null && category.getProfileTemplates().size() > 0) {
         Iterator templates = category.getProfileTemplates().iterator();
         if (templates.hasNext()) {
            ProfileTemplate template = (ProfileTemplate)templates.next();
            attributes = template.getProfileAttributes();
         }
      }
    }

    boolean first = true;
    for (ProfileAttribute attribute : attributes) {
      String id = "" + attribute.getID();
      String name = attribute.getName();

      if (!first) {
        result.append("|");
      } else {
        first = false;
      }
      result.append(name);
      result.append(",");
      result.append(id);
    }
    res.getWriter().write(result.toString());
    return null;
  }
  
  
  

}
