/**
  * $Header: /home/master/OTAS-DM-MyPortal/src/com/npower/dm/myportal/cp/SelectCarrierAction.java,v 1.8 2008/04/15 10:18:21 zhao Exp $
  * $Revision: 1.8 $
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;

import com.npower.dm.action.PersistentManager;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;
import com.npower.dm.decorator.DecoratorHelper;
import com.npower.dm.hibernate.entity.CarrierEntity;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SearchBean;
import com.npower.dm.myportal.BaseWizardAction;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.8 $ $Date: 2008/04/15 10:18:21 $
 */
public class SelectCarrierAction extends BaseWizardAction {
  
  /* (non-Javadoc)
   * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  public ActionForward view(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
    ManagementBeanFactory factory = PersistentManager.getManagementBeanFactory(request);
    SearchBean searchBean = factory.createSearchBean();

    ClientProvWizardForm form = (ClientProvWizardForm)rawForm;
    Country country = getCountry(factory, form, request);
    if (country == null) {
       // Missing country
       return mapping.getInputForward();
    }
    request.setAttribute("country", DecoratorHelper.decorate(country, this.getLocale(request)));
    
    Criteria criteria = searchBean.newCriteriaInstance(CarrierEntity.class);
    criteria.add(Expression.eq("country", country));
    List<Carrier> list = searchBean.find(criteria);
    Collection<LabelValueBean> result = new ArrayList<LabelValueBean>();
    for (Carrier carrier: list) {
        if (carrier.getExternalID().equalsIgnoreCase("DefaultCarrier")) {
           continue;
        }
        LabelValueBean labelValue = new LabelValueBean(carrier.getName(), "" + carrier.getExternalID());
        result.add(labelValue);
    }
    request.setAttribute("carrierOptions", result);
    
    return (mapping.findForward("view"));
  }

  public ActionForward next(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
    ClientProvWizardForm form = (ClientProvWizardForm)rawForm;
    String carrierID = form.getString("carrierID");
    if (StringUtils.isEmpty(carrierID)) {
       saveError4MissCarrier(request);
       return this.unspecified(mapping, rawForm, request, res);
    }
    return (mapping.findForward("next"));
  }

  public ActionForward prev(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
    return (mapping.findForward("prev"));
  }
  
}
