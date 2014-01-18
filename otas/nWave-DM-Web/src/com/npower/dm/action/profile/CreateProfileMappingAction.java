/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/profile/CreateProfileMappingAction.java,v 1.4 2007/02/05 11:02:20 zhao Exp $
  * $Revision: 1.4 $
  * $Date: 2007/02/05 11:02:20 $
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

import com.npower.dm.action.ActionHelper;
import com.npower.dm.action.BaseAction;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileTemplateBean;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2007/02/05 11:02:20 $
 */
public class CreateProfileMappingAction extends BaseAction {

  private void loadProfileTemplates(ActionForm rawForm, HttpServletRequest request) throws Exception {
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
    List templates = templateBean.findTemplates("from ProfileTemplateEntity");
    request.setAttribute("profileTemplateOptions", templates);
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
    
    request.setAttribute("manufacturerOptions", ActionHelper.getManufacturerOptions(this.getManagementBeanFactory(request), request));
    
    this.loadProfileTemplates(rawForm, request);
    
    return (mapping.findForward("create"));
  }

}
