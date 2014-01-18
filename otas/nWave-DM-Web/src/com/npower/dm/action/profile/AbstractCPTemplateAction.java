/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/profile/AbstractCPTemplateAction.java,v 1.2 2007/11/12 05:17:56 zhao Exp $
  * $Revision: 1.2 $
  * $Date: 2007/11/12 05:17:56 $
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import com.npower.dm.action.BaseAction;
import com.npower.dm.core.ClientProvTemplate;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.management.ClientProvTemplateBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.ProfileTemplateBean;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2007/11/12 05:17:56 $
 */
public abstract class AbstractCPTemplateAction extends BaseAction {

  protected void loadCPTemplate(ActionForm rawForm, HttpServletRequest request) throws Exception {
    DynaValidatorForm form = (DynaValidatorForm) rawForm;
    // Fetch Template ID from request
    String templateID = form.getString("templateID");
    String modelID = form.getString("modelID");
    
    if (modelID != null && modelID.trim().length() >= 0 && templateID != null && templateID.trim().length() > 0) {
       ManagementBeanFactory factory = this.getManagementBeanFactory(request);
       ClientProvTemplateBean templateBean = factory.createClientProvTemplateBean();
       ModelBean modelBean = factory.createModelBean();
       ClientProvTemplate template = templateBean.getTemplate(templateID);
       Model model = modelBean.getModelByID(modelID);
       if (model == null) {
          throw new DMException("Please specified a Model");
       }
       if (template == null) {
          throw new DMException("Please specified a ClientProvTemplate");
       }
       
       request.setAttribute("template", template);
       request.setAttribute("model", model);
       
       form.set("name", template.getName());
       form.set("description", template.getDescription());
       form.set("templateID", template.getID().toString());
       form.set("modelID", model.getID() + "");
       form.set("categoryID", template.getProfileCategory().getID() + "");
       form.set("content", template.getContent());
       form.set("encoder", template.getEncoder());
    }
  }
  
   protected void loadCategories(ActionForm rawForm, HttpServletRequest request) throws Exception {
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ProfileTemplateBean bean = factory.createProfileTemplateBean();
    List<ProfileCategory> categories = bean.findProfileCategories("from ProfileCategoryEntity");
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
