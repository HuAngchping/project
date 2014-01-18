/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/profile/SaveProfileMappingAction.java,v 1.6 2007/02/08 10:50:28 zhao Exp $
  * $Revision: 1.6 $
  * $Date: 2007/02/08 10:50:28 $
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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import com.npower.dm.action.BaseLookupDispatchAction;
import com.npower.dm.core.DDFNode;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileMapping;
import com.npower.dm.core.ProfileTemplate;
import com.npower.dm.management.DDFTreeBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.ProfileMappingBean;
import com.npower.dm.management.ProfileTemplateBean;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.6 $ $Date: 2007/02/08 10:50:28 $
 */
public class SaveProfileMappingAction extends BaseLookupDispatchAction {

  protected Map getKeyMethodMap()
  {
    Map<String, String> map = new HashMap<String, String>();
    map.put("page.button.update.label", "update");
    return(map);
  }

  /**
   * Update a ProfileMapping
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

    String templateID = form.getString("templateID");
    String modelID = form.getString("modelID");
    String rootDDFNodeID = form.getString("rootDDFNodeID");
    
    ManagementBeanFactory factory = null;
    try {
       factory = this.getManagementBeanFactory(request);
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

       ProfileMapping profileMapping = model.getProfileMap(template);
       
       if (profileMapping == null) {
          throw new DMException("Could not found the ProfileMapping.");
       }  
       
       ProfileMappingBean mappingBean = factory.createProfileMappingBean();
       factory.beginTransaction();
       // Copy form Data into POJO
       BeanUtils.populate(profileMapping, form.getMap());
        
       DDFTreeBean ddfBean = factory.createDDFTreeBean();
       
       String rootNodePath = form.getString("rootNodePath");
       DDFNode ddfNode4RootNodePath = ddfBean.findDDFNodeByNodePath(model, rootNodePath);
       if (ddfNode4RootNodePath == null) {
          ddfNode4RootNodePath = ddfBean.getDDFNodeByID(rootDDFNodeID);
       }

       profileMapping.setRootDDFNode(ddfNode4RootNodePath);
       mappingBean.update(profileMapping);
        
       factory.commit();
       
       // Pass target into audit AOP
       request.setAttribute("profileMapping", profileMapping);
       request.setAttribute("model", model);
        
    } catch (Exception ex) {
      factory.rollback();
      throw ex;
    } finally {
    }
    return (mapping.findForward("success"));
  }
  
  
  public ActionForward cancelled(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    return (mapping.findForward("cancel"));
  }  
  
  public ActionForward unspecified(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    return (mapping.findForward("cancel"));
  }  


}