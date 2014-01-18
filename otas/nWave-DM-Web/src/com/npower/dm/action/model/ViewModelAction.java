/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/model/ViewModelAction.java,v 1.6 2008/06/05 10:34:36 zhao Exp $
 * $Revision: 1.6 $
 * $Date: 2008/06/05 10:34:36 $
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

package com.npower.dm.action.model;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.validator.DynaValidatorForm;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Model;

/**
 * MyEclipse Struts Creation date: 05-30-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/EditModel" name="ModelForm" scope="request"
 * @struts.action-forward name="success" path="/jsp/model.jsp"
 *                        contextRelative="true"
 */
public class ViewModelAction extends AbstractModelAction {

  // --------------------------------------------------------- Instance
  // Variables

  // --------------------------------------------------------- Methods

  /**
   * Method execute
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

    DynaActionForm modelForm = (DynaActionForm) form;
    String modelID = (String) modelForm.get("ID");
    if (StringUtils.isEmpty(modelID)) {
       // Guess from attributes, Maybe redirect from models search.
       modelID = (String)request.getAttribute("modelID");
    }
    try {
        this.loadAuthenticationSchemas(mapping, form, request, response);
        if (StringUtils.isNotEmpty(modelID)) {
           Model model = this.getModel(request, modelID);
           request.setAttribute("model", model);
           String announced = model.getCharacterValue("general", "announced");
           request.setAttribute("announced", announced);
           String os = model.getCharacterValue("general", "operating.system");
           request.setAttribute("os", os);
           String platform = model.getCharacterValue("general", "developer.platform");
           request.setAttribute("platform", platform);
           
           modelForm.getMap().putAll(BeanUtils.describe(model));
          
           String tacList = "";
           Set<String> tacs = model.getModelTAC();
           for (String tac: tacs) {
               tacList += tac + ",";
           }
           modelForm.set("taclist", tacList);
  
           request.getSession().setAttribute("modelID", model.getID());
           
        } else {
  
        }
        
        // Build UI Elements for Model Search Panel
        DynaValidatorForm searchForm = (DynaValidatorForm)modelForm;
        ModelsAction.buildUIElementsForSearchPanel(this.getManagementBeanFactory(request), request, searchForm);

        return (mapping.findForward("view"));
    } catch (DMException e) {
      throw e;
    }
  }

}
