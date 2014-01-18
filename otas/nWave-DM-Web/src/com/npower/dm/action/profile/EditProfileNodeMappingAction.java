//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.npower.dm.action.profile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.DynaValidatorForm;

import com.npower.dm.action.BaseAction;
import com.npower.dm.core.DDFNode;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileMapping;
import com.npower.dm.core.ProfileNodeMapping;
import com.npower.dm.core.ProfileTemplate;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.ProfileMappingBean;
import com.npower.dm.management.ProfileTemplateBean;

/** 
 * MyEclipse Struts
 * Creation date: 06-12-2006
 * 
 * XDoclet definition:
 * @struts.action path="/EditProfileNodeMapping" name="ProfileNodeMappingForm" scope="request"
 */
public class EditProfileNodeMappingAction extends BaseAction {

  // --------------------------------------------------------- Instance Variables

  // --------------------------------------------------------- Methods

  /**
   * @param rawForm
   * @param request
   */
  private void loadProfileNodeMapping(ActionForm rawForm, HttpServletRequest request) throws Exception {
    DynaValidatorForm form = (DynaValidatorForm) rawForm;
     
    String nodeMappingID = form.getString("ID");
    if (nodeMappingID == null || nodeMappingID.trim().length() == 0) {
       return;
    }
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ProfileMappingBean mappingBean = factory.createProfileMappingBean();
    
    ProfileNodeMapping nodeMapping = mappingBean.getProfileNodeMappingByID(nodeMappingID);
    request.setAttribute("profileNodeMapping", nodeMapping);
    if (nodeMapping != null) {
       form.getMap().putAll(BeanUtils.describe(nodeMapping));
       form.set("ddfNodeID", "" + nodeMapping.getDdfNode().getID());
       if (nodeMapping.getMappingType().equals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE)) {
          form.set("profileAttributeID", "" + nodeMapping.getProfileAttribute().getID());
       }
    }
  }
  
  /**
   * Load the ProfileTemplate for select box
   * @param rawForm
   * @param request
   * @throws Exception
   */
  public void loadProfileTemplateAttributes(ActionForm rawForm, HttpServletRequest request) throws Exception {
    DynaValidatorForm form = (DynaValidatorForm) rawForm;
    
    String nodeMappingID = form.getString("ID");
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ProfileMappingBean mappingBean = factory.createProfileMappingBean();
    
    ProfileNodeMapping nodeMapping = null;
    String linkedCategoryName = null;
    if (StringUtils.isNotEmpty(nodeMappingID)) {
       nodeMapping = mappingBean.getProfileNodeMappingByID(nodeMappingID);
       if (nodeMapping != null) {
          linkedCategoryName = nodeMapping.getCategoryName();
       }
    }
    if (StringUtils.isEmpty(linkedCategoryName)) {
       // Load attributes from current template
      String templateID = form.getString("templateID");
      ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
      ProfileTemplate template = templateBean.getTemplateByID(templateID);
      if (template == null) {
         throw new DMException("Please specified a ProfileTemplate");
      }
     
      Set attributes = template.getProfileAttributes();
      request.setAttribute("attributes", attributes);
    } else {
      // Load attributes from Linked category
      ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
      ProfileCategory category = templateBean.getProfileCategoryByName(linkedCategoryName); 
      if (category != null && category.getProfileTemplates().size() > 0) {
         Iterator templates = category.getProfileTemplates().iterator();
         if (templates.hasNext()) {
            ProfileTemplate template = (ProfileTemplate)templates.next();
            Set attributes = template.getProfileAttributes();
            request.setAttribute("attributes", attributes);
         }
      }
    }
    
  }
  
  private void loadDDFNodes(ActionForm rawForm, HttpServletRequest request) throws Exception {
    
    DynaValidatorForm form = (DynaValidatorForm) rawForm;
    
    String modelID = form.getString("modelID");
    String templateID = form.getString("templateID");

    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ModelBean modelBean = factory.createModelBean();
    Model model = modelBean.getModelByID(modelID);
    if (model == null) {
       throw new DMException("Please specified a Model");
    }
    
    ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
    ProfileTemplate template = templateBean.getTemplateByID(templateID);
    if (template == null) {
       throw new DMException("Please specified a ProfileTemplate");
    }
    
    ProfileMapping profileMapping = model.getProfileMap(template);
    request.setAttribute("profileMapping", profileMapping);

    List<LabelValueBean> nodes = new ArrayList<LabelValueBean>();

    Set<DDFNode> children = profileMapping.getRootDDFNode().getChildren();
    for (DDFNode child: children) {
        this.addNodeAndChildren(nodes, profileMapping.getRootDDFNode(), child);
    }

    /*
    Set<DDFTree> trees = model.getDDFTrees();
    for (DDFTree tree: trees) {
        Set<DDFNode> children = tree.getRootDDFNodes();
        for (DDFNode child: children) {
            nodes.add(child);
            addNodeAndChildren(nodes, child);
        }
    }
    */
    request.setAttribute("ddfNodes", nodes);
  }
  
  
  /**
   * @param nodes
   * @param child
   */
  private void addNodeAndChildren(List<LabelValueBean> nodes, DDFNode baseNode, DDFNode node) throws DMException {
    if (node != null) {
       LabelValueBean labelValue = new LabelValueBean();
       String relativePath = node.caculateRelativeNodePath(baseNode);
       labelValue.setLabel(relativePath);
       labelValue.setValue(node.getID() + "");
       nodes.add(labelValue);
       Set<DDFNode> children = node.getChildren();
       if (!children.isEmpty()) {
          for (DDFNode child: children) {
              this.addNodeAndChildren(nodes, baseNode, child);
          }
       }
    }
    
  }
  
  private void loadNodeMappingTypes(ActionForm rawForm, HttpServletRequest request) throws Exception {
    MessageResources messageResources = this.getResources(request);
    String label = messageResources.getMessage(this.getLocale(request), "meta.profile.node.mapping.type." + ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE + ".label"); 
    LabelValueBean value1 = new LabelValueBean(label, ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE);
    label = messageResources.getMessage(this.getLocale(request), "meta.profile.node.mapping.type." + ProfileNodeMapping.NODE_MAPPING_TYPE_VALUE + ".label"); 
    LabelValueBean value2 = new LabelValueBean(label, ProfileNodeMapping.NODE_MAPPING_TYPE_VALUE);
    List<LabelValueBean> values = new ArrayList<LabelValueBean>();
    values.add(value1);
    values.add(value2);
    request.setAttribute("mappingTypes", values);
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
  public ActionForward execute(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    // Load ProfileNodeMapping
    this.loadProfileNodeMapping(rawForm, request);    
    
    // Load the profileAttributes for select box
    this.loadProfileTemplateAttributes(rawForm, request);
    
    // Load the ddf nodes for select box
    this.loadDDFNodes(rawForm, request);
    
    // Load the mappingTypes for select box
    this.loadNodeMappingTypes(rawForm, request);

    return (mapping.findForward("success"));
  }


}

