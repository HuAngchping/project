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
import com.npower.dm.core.AttributeTypeValue;
import com.npower.dm.core.ProfileAttributeType;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileTemplateBean;

/** 
 * MyEclipse Struts
 * Creation date: 06-07-2006
 * 
 * XDoclet definition:
 * @struts.action path="/SaveProfileAttributeTypeValue" name="ProfileAttributeTypeValueForm" parameter="action" scope="request" validate="true"
 */
public class SaveProfileAttributeTypeValue extends BaseLookupDispatchAction {

  // --------------------------------------------------------- Instance Variables

  protected Map getKeyMethodMap()
  {
    Map<String, String> map = new HashMap<String, String>();
    map.put("page.attributetype.button.createAttributeTypeValue", "addValue");
    map.put("page.attributetype.button.removeAttributeTypeValue", "removeValue");
    map.put("page.attributetype.button.updateAttributeTypeValue", "updateValue");
    return(map);
  }
  // --------------------------------------------------------- Methods

  /**
   * Update a AttributeType
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward addValue(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    DynaValidatorForm form = (DynaValidatorForm) rawForm;
    String value = request.getParameter("value");
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ProfileTemplateBean bean = factory.createProfileTemplateBean();
    
    String id = form.getString("ID");
    try {
        ProfileAttributeType aType = bean.getProfileAttributeTypeByID(id);
        AttributeTypeValue typeValue = bean.newAttributeTypeValueInstance(aType);
        typeValue.setValue(value);
        
        factory.beginTransaction();
        bean.update(typeValue);
        factory.commit();
        
    } catch (Exception ex) {
      factory.rollback();
      throw ex;
    } finally {
    }
  
    return (mapping.findForward("edit"));
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
  public ActionForward removeValue(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    DynaValidatorForm form = (DynaValidatorForm) rawForm;

    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ProfileTemplateBean bean = factory.createProfileTemplateBean();
    
    String id = form.getString("valueID");
    try {
        AttributeTypeValue typeValue = bean.getAttributeTypeValueByID(id);
        
        factory.beginTransaction();
        bean.delete(typeValue);
        factory.commit();
        
    } catch (Exception ex) {
      factory.rollback();
      throw ex;
    } finally {
    }
  
    return (mapping.findForward("edit"));
  }

}

