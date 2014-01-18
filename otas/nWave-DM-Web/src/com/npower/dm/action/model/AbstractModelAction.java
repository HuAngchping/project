/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/model/AbstractModelAction.java,v 1.1 2007/03/30 11:02:21 zhao Exp $
 * $Revision: 1.1 $
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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import com.npower.dm.action.BaseAction;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.Model;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;

/**
 * MyEclipse Struts Creation date: 05-30-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/EditModel" name="ModelForm" scope="request"
 * @struts.action-forward name="success" path="/jsp/model.jsp"
 *                        contextRelative="true"
 */
public abstract class AbstractModelAction extends BaseAction {

  // --------------------------------------------------------- Instance
  // Variables

  // --------------------------------------------------------- Methods

  protected Model getModel(HttpServletRequest request, String id) throws DMException {
    ManagementBeanFactory factory = getManagementBeanFactory(request);
    ModelBean modelBean = factory.createModelBean();
    Model model = modelBean.getModelByID(id);
    return model;
  }

  protected void loadAuthenticationSchemas(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    List<LabelValueBean> schemas = new ArrayList<LabelValueBean>();
    LabelValueBean label = new LabelValueBean(Device.AUTH_TYPE_BASIC, Device.AUTH_TYPE_BASIC);
    schemas.add(label);
    label = new LabelValueBean(Device.AUTH_TYPE_MD5, Device.AUTH_TYPE_MD5);
    schemas.add(label);
    label = new LabelValueBean(Device.AUTH_TYPE_HMAC, Device.AUTH_TYPE_HMAC);
    schemas.add(label);
    request.setAttribute("authSchemas", schemas);
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
  public abstract ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
