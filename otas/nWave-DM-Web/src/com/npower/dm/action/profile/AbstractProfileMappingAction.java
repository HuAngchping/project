/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/profile/AbstractProfileMappingAction.java,v 1.1 2007/03/30 06:56:05 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2007/03/30 06:56:05 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2006 NPower Network Software Ltd.  All rights reserved.
  *
  * This SOURCE CODE FILE, which has been provided by NPower as part
  * of a NPower product for use ONLY by licensed users of the product,
  * includes CONFIDENTIAL and PROPRIETARY information of NPower.
  *
  * USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS
  * OF THE LICENSE STATEMENT AND LIMITED WARRANTY FURNISHED WITH
  * THE PRODUCT.
  *
  * IN PARTICULAR, YOU WILL INDEMNIFY AND HOLD NPower, ITS RELATED
  * COMPANIES AND ITS SUPPLIERS, HARMLESS FROM AND AGAINST ANY CLAIMS
  * OR LIABILITIES ARISING OUT OF THE USE, REPRODUCTION, OR DISTRIBUTION
  * OF YOUR PROGRAMS, INCLUDING ANY CLAIMS OR LIABILITIES ARISING OUT OF
  * OR RESULTING FROM THE USE, MODIFICATION, OR DISTRIBUTION OF PROGRAMS
  * OR FILES CREATED FROM, BASED ON, AND/OR DERIVED FROM THIS SOURCE
  * CODE FILE.
  * ===============================================================================================
  */
package com.npower.dm.action.profile;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import com.npower.dm.action.BaseAction;
import com.npower.dm.core.DDFNode;
import com.npower.dm.core.DDFTree;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileMapping;
import com.npower.dm.core.ProfileTemplate;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.ProfileTemplateBean;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/03/30 06:56:05 $
 */
public abstract class AbstractProfileMappingAction extends BaseAction {

  protected void loadProfileMapping(ActionForm rawForm, HttpServletRequest request) throws Exception {
    DynaValidatorForm form = (DynaValidatorForm) rawForm;
    // Fetch Template ID from request
    String templateID = form.getString("templateID");
    String modelID = form.getString("modelID");
    
    if (modelID != null && modelID.trim().length() >= 0 && templateID != null && templateID.trim().length() > 0) {
       ManagementBeanFactory factory = this.getManagementBeanFactory(request);
       ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
       ModelBean modelBean = factory.createModelBean();
       ProfileTemplate template = templateBean.getTemplateByID(templateID);
       Model model = modelBean.getModelByID(modelID);
       if (model == null) {
          throw new DMException("Please specified a Model");
       }
       if (template == null) {
          throw new DMException("Please specified a ProfileTemplate");
       }
       
       request.setAttribute("template", template);
       request.setAttribute("model", model);
       // Load DDFNodes for select box
       this.loadDDFNodes(model, request);
       
       ProfileMapping profileMapping = model.getProfileMap(template);
       
       if (profileMapping != null) {
          form.getMap().putAll(BeanUtils.describe(profileMapping));
          DDFNode rootNode = profileMapping.getRootDDFNode();
          form.set("rootDDFNodeID", "" + rootNode.getID());
          Set nodeMappings = profileMapping.getProfileNodeMappings();
          request.setAttribute("nodeMappings", nodeMappings);
          
          form.set("rootNodePath", profileMapping.getRootNodePath());
          
          request.setAttribute("profileMapping", profileMapping);
       }
    }
  }
  
  protected void loadDDFNodes(Model model, HttpServletRequest request) throws Exception {
    Set<DDFNode> nodes = new TreeSet<DDFNode>();
    
    Set<DDFTree> trees = model.getDDFTrees();
    for (DDFTree tree: trees) {
        Set<DDFNode> children = tree.getRootDDFNodes();
        for (DDFNode child: children) {
            nodes.add(child);
            addNodeAndChildren(nodes, child);
        }
    }
    request.setAttribute("ddfNodes", nodes);
  }
  
  
  /**
   * @param nodes
   * @param child
   */
  protected void addNodeAndChildren(Set<DDFNode> nodes, DDFNode node) {
    if (node != null) {
       nodes.add(node);
       Set<DDFNode> children = node.getChildren();
       if (!children.isEmpty()) {
          for (DDFNode child: children) {
              this.addNodeAndChildren(nodes, child);
          }
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
