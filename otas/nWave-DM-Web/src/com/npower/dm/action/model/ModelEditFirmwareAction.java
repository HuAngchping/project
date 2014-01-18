/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/model/ModelEditFirmwareAction.java,v 1.6 2007/03/30 11:02:21 zhao Exp $
 * $Revision: 1.6 $
 * $Date: 2007/03/30 11:02:21 $
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.util.MessageResources;

import com.npower.dm.action.ActionHelper;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Model;
import com.npower.dm.core.Update;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.UpdateImageBean;

/**
 * MyEclipse Struts Creation date: 05-30-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/EditModel" name="ModelForm" scope="request"
 * @struts.action-forward name="success" path="/jsp/model.jsp"
 *                        contextRelative="true"
 */
public class ModelEditFirmwareAction extends AbstractModelFirmwareAction {

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
  public ActionForward execute(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) throws Exception {

    DynaActionForm form = (DynaActionForm) rawForm;
    String updateID = form.getString("updateID");
    try {
      if (StringUtils.isNotEmpty(updateID)) {
         ManagementBeanFactory factory = this.getManagementBeanFactory(request);
         UpdateImageBean bean = factory.createUpdateImageBean();
         Update update = bean.getUpdateByID(updateID);
         
         form.set("modelID", update.getFromImage().getModel().getID() + "");
         form.set("description", update.getDescription());
         form.set("status", update.getStatus().getStatusId() + "");
         form.set("fromVersion", update.getFromImage().getVersionId());
         form.set("toVersion", update.getToImage().getVersionId());

      }
      MessageResources messageResources = getResources(request);
      request.setAttribute("updateStatusOptions", ActionHelper.getUpdateImageStatusOptions(this.getManagementBeanFactory(request), messageResources, this.getLocale(request), request));

      String modelID = (String) form.get("modelID");
      if (StringUtils.isEmpty(modelID)) {
         Long id = (Long)request.getSession().getAttribute("modelID");
         if (id != null) {
            modelID = id.toString();
         }
      }
      Model model = this.getModel(request, modelID);
      request.setAttribute("model", model);
      return (mapping.findForward("edit"));
    } catch (DMException e) {
      throw e;
    }
  }

}
