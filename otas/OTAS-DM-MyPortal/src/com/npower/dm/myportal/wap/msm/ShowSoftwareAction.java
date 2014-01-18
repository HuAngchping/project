/**
  * $Header: /home/master/OTAS-DM-MyPortal/src/com/npower/dm/myportal/wap/msm/ShowSoftwareAction.java,v 1.1 2008/04/18 11:46:26 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2008/04/18 11:46:26 $
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
package com.npower.dm.myportal.wap.msm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.npower.dm.action.PersistentManager;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;
import com.npower.dm.core.Software;
import com.npower.dm.decorator.DecoratorHelper;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.myportal.BaseWizardAction;
import com.npower.dm.myportal.cp.ClientProvWizardForm;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/04/18 11:46:26 $
 */
public class ShowSoftwareAction extends BaseWizardAction {
  
  
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

    // Get Carrier
    Carrier carrier = this.getCarrier(factory, form, request);
    if (carrier == null) {
       return mapping.getInputForward();
    }
    request.setAttribute("carrier", carrier);

    // Get Software
    Software software = this.getSoftware(factory, form, request);
    if (software == null) {
       // 返回选择软件页面
       return this.prev(mapping, rawForm, request, res);
    }
    request.setAttribute("software", software);

    return (mapping.findForward("view"));
  }

  /* (non-Javadoc)
   * @see com.npower.dm.myportal.cp.BaseWizardAction#next(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  public ActionForward next(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
    return (mapping.findForward("next4dm"));
  }

  /* (non-Javadoc)
   * @see com.npower.dm.myportal.cp.BaseWizardAction#prev(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  public ActionForward prev(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
    return (mapping.findForward("prev"));
  }
  
}
