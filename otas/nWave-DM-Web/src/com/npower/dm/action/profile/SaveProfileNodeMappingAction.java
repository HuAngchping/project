//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.npower.dm.action.profile;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorForm;

import com.npower.dm.action.BaseLookupDispatchAction;
import com.npower.dm.core.DDFNode;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileAttribute;
import com.npower.dm.core.ProfileMapping;
import com.npower.dm.core.ProfileNodeMapping;
import com.npower.dm.core.ProfileTemplate;
import com.npower.dm.management.DDFTreeBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.ProfileMappingBean;
import com.npower.dm.management.ProfileTemplateBean;

/**
 * MyEclipse Struts Creation date: 06-12-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/SaveProfileNodeMapping" name="ProfileNodeMappingForm"
 *                input="/EditProfileNodeMapping.do" parameter="action"
 *                scope="request" validate="true"
 */
public class SaveProfileNodeMappingAction extends BaseLookupDispatchAction {

  // --------------------------------------------------------- Instance
  // Variables

  protected Map getKeyMethodMap() {
    Map<String, String> map = new HashMap<String, String>();
    map.put("page.button.create.label", "create");
    map.put("page.button.update.label", "update");
    return (map);
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
    ProfileMappingBean bean = factory.createProfileMappingBean();
    ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
    DDFTreeBean ddfBean = factory.createDDFTreeBean();
    try {
        ProfileNodeMapping entity = bean.newProfileNodeMappingInstance();

        factory.beginTransaction();
        // Copy form Data into POJO
        BeanUtils.populate(entity, form.getMap());
        
        ModelBean modelBean = factory.createModelBean();
        ProfileTemplate template = templateBean.getTemplateByID(form.getString("templateID"));
        Model model = modelBean.getModelByID(form.getString("modelID"));
        if (model == null) {
           throw new DMException("Please specified a Model");
        }
        if (template == null) {
           throw new DMException("Please specified a ProfileTemplate");
        }
        
        String nodeRelativePath = form.getString("nodeRelativePath");
        ProfileMapping profileMapping = model.getProfileMap(template);
        DDFNode rootNode = profileMapping.getRootDDFNode();
        DDFNode ddfNode = ddfBean.findDDFNodeByRelativePath(model, rootNode, nodeRelativePath);
        if (ddfNode == null) {
           ddfNode = ddfBean.getDDFNodeByID(form.getString("ddfNodeID"));
        }

        String mappingType = form.getString("mappingType");
        if (mappingType.equals(ProfileNodeMapping.NODE_MAPPING_TYPE_VALUE)) {
           // Value mapping
           entity.setMappingType(ProfileNodeMapping.NODE_MAPPING_TYPE_VALUE);
           
           String value = form.getString("value");
           entity.setValue(value);
           
           String displayName = form.getString("displayName");
           entity.setDisplayName(displayName);
        } else if (mappingType.equals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE)) {
          // Attribute mapping
          entity.setMappingType(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE);
          ProfileAttribute attribute = templateBean.getProfileAttributeByID(form.getString("profileAttributeID"));
          entity.setProfileAttribute(attribute);
          
          entity.setValue(null);
        }

        entity.setDdfNode(ddfNode);
        entity.setProfileMapping(profileMapping);
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

    String mappingType = form.getString("mappingType");
    String attributeID = form.getString("profileAttributeID");
    if (mappingType.equals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE)) {
       if (StringUtils.isEmpty(attributeID)) {
          ActionMessages errors = new ActionMessages();
          errors.add("errors.profileAttributeID", new ActionMessage("profileNodeMapping.message.attributeRequired"));
          saveErrors(request, errors);
          return mapping.getInputForward();
       }
    }

    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ProfileMappingBean bean = factory.createProfileMappingBean();
    ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
    DDFTreeBean ddfBean = factory.createDDFTreeBean();
    ModelBean modelBean = factory.createModelBean();
    try {
        ProfileNodeMapping entity = bean.getProfileNodeMappingByID(form.getString("ID"));

        factory.beginTransaction();
        // Copy form Data into POJO
        BeanUtils.populate(entity, form.getMap());
        
        Model model = modelBean.getModelByID(form.getString("modelID"));
        if (model == null) {
           throw new DMException("Please specified a Model");
        }

        String nodeRelativePath = form.getString("nodeRelativePath");
        DDFNode rootNode = entity.getProfileMapping().getRootDDFNode();
        DDFNode ddfNode = ddfBean.findDDFNodeByRelativePath(model, rootNode, nodeRelativePath);
        if (ddfNode == null) {
           ddfNode = ddfBean.getDDFNodeByID(form.getString("ddfNodeID"));
        }
        
        if (mappingType.equals(ProfileNodeMapping.NODE_MAPPING_TYPE_VALUE)) {
           // Value mapping
           entity.setMappingType(ProfileNodeMapping.NODE_MAPPING_TYPE_VALUE);
           // Clean unused property: profileAttribute.
           entity.setProfileAttribute(null);
           
           String value = form.getString("value");
           entity.setValue(value);
           
           String displayName = form.getString("displayName");
           entity.setDisplayName(displayName);
        } else if (mappingType.equals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE)) {
          // Attribute mapping
          entity.setMappingType(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE);
          ProfileAttribute attribute = templateBean.getProfileAttributeByID(attributeID);
          entity.setProfileAttribute(attribute);
          // Clean unused properties.
          entity.setValue(null);
          entity.setDisplayName(null);
        }
        
        entity.setDdfNode(ddfNode);
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
