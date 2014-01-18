//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.npower.dm.action.config;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

import com.npower.dm.action.BaseLookupDispatchAction;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.ProfileAttribute;
import com.npower.dm.core.ProfileAttributeType;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ProfileTemplate;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileConfigBean;
import com.npower.dm.management.ProfileTemplateBean;

/**
 * MyEclipse Struts Creation date: 06-13-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/SaveProfile" name="ProfileConfigForm"
 *                input="/EditProfile.do" parameter="action" scope="request"
 *                validate="true"
 * @struts.action-forward name="success" path="/ProfileConfigs.do"
 *                        contextRelative="true"
 * @struts.action-forward name="cancel" path="/ProfileConfigs.do"
 *                        contextRelative="true"
 */
public class SaveProfileAction extends BaseLookupDispatchAction {

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
   * Method execute
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward update(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    ProfileConfigForm4Save form = (ProfileConfigForm4Save) rawForm;
    
    String id = (String)form.getValue("ID");
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ProfileConfigBean bean = factory.createProfileConfigBean();
    ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
    CarrierBean carrierBean = factory.createCarrierBean();
    try {
        ProfileConfig entity = bean.getProfileConfigByID(id);

        // Copy form Data into POJO
        BeanUtils.populate(entity, form.getMap());
        if (form.getValue("isUserProfile") == null) {
           entity.setIsUserProfile(false);
        }
        
        ProfileTemplate template = templateBean.getTemplateByID((String)form.getValue("templateID"));
        // Validate attribute
        ActionMessages messages = new ActionMessages();
        Set<ProfileAttribute> attributes = template.getProfileAttributes();
        boolean isValidate = true;
        for (ProfileAttribute attribute: attributes) {
            long attrID = attribute.getID();
            String attrName = attribute.getName();
            //String value = request.getParameter("attribute." + attrID + ".value");
            Object value = form.getValue("attribute__" + attrID + "__value");
            if (attribute.getProfileAttribType() != null 
                && ProfileAttributeType.TYPE_BINARY.equalsIgnoreCase(attribute.getProfileAttribType().getName())) {
               if (value == null && attribute.getIsRequired() && !attribute.getIsUserAttribute()) {
                 ActionMessage message = new ActionMessage("errors.required", attrName);
                 messages.add("attribute.validate.message", message);
                 isValidate = false;
              }
            } else {
              String v = (String)value;
              if (StringUtils.isEmpty(v) && attribute.getIsRequired() && !attribute.getIsUserAttribute()) {
                 ActionMessage message = new ActionMessage("errors.required", attrName);
                 messages.add("attribute.validate.message", message);
                 isValidate = false;
              }
            }
        }
        if (!isValidate) {
           this.saveErrors(request, messages);
           request.setAttribute("ID", id);
           return (mapping.findForward("continue"));
        }
        
        
        Carrier carrier = carrierBean.getCarrierByID((String)form.getValue("carrierID"));
        entity.setCarrier(carrier);
        if (template.getNeedsNap()) {
           ProfileConfig napProfile = bean.getProfileConfigByID((String)form.getValue("napProfileID"));
           entity.setNAPProfile(napProfile);
        }
        if (template.getNeedsProxy()) {
           ProfileConfig proxyProfile = bean.getProfileConfigByID((String)form.getValue("proxyProfileID"));
           entity.setProxyProfile(proxyProfile);
        }

        // Get ProfileType from Category of Template
        String profileType = template.getProfileCategory().getName();
        entity.setProfileType(profileType);
        
        factory.beginTransaction();
        bean.update(entity);
        
        // Save attribute
        attributes = template.getProfileAttributes();
        for (ProfileAttribute attribute: attributes) {
            long attrID = attribute.getID();
            String attrName = attribute.getName();
            Object value = form.getValue("attribute__" + attrID + "__value");
            //String value = request.getParameter("attribute." + attrID + ".value");
            if (attribute.getProfileAttribType() != null 
                && ProfileAttributeType.TYPE_BINARY.equalsIgnoreCase(attribute.getProfileAttribType().getName())) {
               // Binary
               String emptyIt = (String)form.getValue("attribute__" + attrID + "__emptyIt");
               if (StringUtils.isNotEmpty(emptyIt)) {
                  // Empty this field
                  bean.setAttributeValue(entity, attrName, (InputStream)null);
               } else {
                 FormFile file = (FormFile)value;
                 if (file != null && StringUtils.isNotEmpty(file.getFileName()) &&  file.getInputStream() != null) {
                    bean.setAttributeValue(entity, attrName, file.getInputStream());
                 }
               }
            } else {
              // Text
              bean.setAttributeValue(entity, attrName, (String)value);
            }
        }
        
        factory.commit();
        
        // Pass target into AOP Audit
        request.setAttribute("profile", entity);
        
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
  public ActionForward create(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    ProfileConfigForm4Save form = (ProfileConfigForm4Save) rawForm;
    
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ProfileConfigBean bean = factory.createProfileConfigBean();
    ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
    CarrierBean carrierBean = factory.createCarrierBean();
    try {
        ProfileTemplate template = templateBean.getTemplateByID((String)form.getValue("templateID"));
        Carrier carrier = carrierBean.getCarrierByID((String)form.getValue("carrierID"));
        String name = (String)form.getValue("name");
        // Get ProfileType from Category of Template
        String profileType = template.getProfileCategory().getName();
        ProfileConfig entity = bean.newProfileConfigInstance(name, carrier, template, profileType);

        factory.beginTransaction();
        // Copy form Data into POJO
        BeanUtils.populate(entity, form.getMap());
        
        bean.update(entity);
        
        factory.commit();
        
        request.setAttribute("ID", "" + entity.getID());
        
        // Pass target into AOP Audit
        request.setAttribute("profile", entity);
        
        return mapping.findForward("continue");
    } catch (Exception ex) {
      factory.rollback();
      throw ex;
    } finally {
    }
  }
}
