/**
  * $Header: /home/master/OTAS-DM-MyPortal/src/com/npower/dm/myportal/cp/SelectModelAction.java,v 1.11 2008/04/15 10:18:21 zhao Exp $
  * $Revision: 1.11 $
  * $Date: 2008/04/15 10:18:21 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2007 NPower Network Software Ltd.  All rights reserved.
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
package com.npower.dm.myportal.cp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.npower.dm.action.PersistentManager;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;
import com.npower.dm.decorator.DecoratorHelper;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.myportal.BaseWizardAction;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.11 $ $Date: 2008/04/15 10:18:21 $
 */
public class SelectModelAction extends BaseWizardAction {
  
  /* (non-Javadoc)
   * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  public ActionForward view(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
    ClientProvWizardForm form = (ClientProvWizardForm)rawForm;

    ManagementBeanFactory factory = PersistentManager.getManagementBeanFactory(request);
    
    // Get Country
    Country country = getCountry(factory, form, request);
    if (country == null) {
       // Missing country
       return mapping.getInputForward();
    }
    request.setAttribute("country", DecoratorHelper.decorate(country, this.getLocale(request)));

    Carrier carrier = getCarrier(factory, form, request);
    if (carrier == null) {
       return mapping.getInputForward();
    }
    request.setAttribute("carrier", carrier);
    
    // 返回型号选择页面的厂商列表
    request.setAttribute("manufacturerOptions", getManufacturerOptions(factory, request));
    // 返回型号选择页面的主流厂商列表
    request.setAttribute("specialManufacturerOptions", getSpecialManufacturerOptions(factory, request));
    // 返回型号选择页面的型号列表
    request.setAttribute("modelOptions", getModelOptions(factory, form));
    
    return (mapping.findForward("view"));
  }

  /* (non-Javadoc)
   * @see com.npower.dm.myportal.cp.BaseWizardAction#next(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  public ActionForward next(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
    ClientProvWizardForm form = (ClientProvWizardForm)rawForm;
    String manufacturerID = form.getString("manufacturerID");
    if (StringUtils.isEmpty(manufacturerID)) {
       saveError4MissManufacturer(request);
       return this.unspecified(mapping, rawForm, request, res);
    }
    String modelID = form.getString("modelID");
    if (StringUtils.isEmpty(modelID)) {
       saveError4MissModel(request);
       return this.unspecified(mapping, rawForm, request, res);
    }
    return (mapping.findForward("next"));
  }

  /* (non-Javadoc)
   * @see com.npower.dm.myportal.cp.BaseWizardAction#prev(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  public ActionForward prev(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
    return (mapping.findForward("prev"));
  }
  
}
