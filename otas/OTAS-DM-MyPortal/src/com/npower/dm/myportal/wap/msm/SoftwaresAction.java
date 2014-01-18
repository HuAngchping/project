/**
  * $Header: /home/master/OTAS-DM-MyPortal/src/com/npower/dm/myportal/wap/msm/SoftwaresAction.java,v 1.5 2008/07/08 04:44:48 zhao Exp $
  * $Revision: 1.5 $
  * $Date: 2008/07/08 04:44:48 $
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

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.displaytag.pagination.PaginatedList;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

import com.npower.dm.action.PersistentManager;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Software;
import com.npower.dm.decorator.DecoratorHelper;
import com.npower.dm.displaytag.PaginatedListAdapter;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.PaginatedResult;
import com.npower.dm.management.SearchBean;
import com.npower.dm.myportal.BaseWizardAction;
import com.npower.dm.myportal.cp.ClientProvWizardForm;
import com.npower.dm.util.DisplayTagHelper;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.5 $ $Date: 2008/07/08 04:44:48 $
 */
public class SoftwaresAction extends BaseWizardAction {
  
  /**
   * @param request
   * @param category
   * @param form 
   * @return
   * @throws DMException
   */
  private PaginatedList loadSoftwares(HttpServletRequest request, ClientProvWizardForm form) throws Exception {    
    ManagementBeanFactory factory = PersistentManager.getManagementBeanFactory(request);
    
    SearchBean searchBean = factory.createSearchBean();
    Criteria criteria = searchBean.newCriteriaInstance(Software.class);
    criteria.addOrder(Order.asc("name"));
    criteria.addOrder(Order.asc("version"));
    
    String searchText = form.getString("searchText");
    if(StringUtils.isNotEmpty(searchText)){
      LogicalExpression subCriteria = Expression.or(
          Expression.ilike("name", searchText, MatchMode.ANYWHERE), 
          Expression.ilike("description", searchText, MatchMode.ANYWHERE));
      criteria.add(subCriteria);
    }
    // Only show released software
    criteria.add(Expression.eq("status", Software.STATUS_RELEASE));
    
    //List<Software> softwares = searchBean.find(criteria);
    int pageNumber = DisplayTagHelper.getPageNumber(request);
    int recordsPerPage = 10;
    
    PaginatedResult result = searchBean.getPaginatedList(criteria, pageNumber, recordsPerPage);
    PaginatedList softwares = new PaginatedListAdapter(result);

    return softwares;
  }  

  /* (non-Javadoc)
   * @see com.npower.dm.myportal.BaseWizardAction#view(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
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

    //List<Software> softwares = this.loadSoftwares(request, form);
    PaginatedList softwares = this.loadSoftwares(request, form);

    request.setAttribute("softwares", softwares);
    BaseWizardAction.setRecordsPerPageOptions(request, form);
    
    return (mapping.findForward("view"));
  }

  /* (non-Javadoc)
   * @see com.npower.dm.myportal.BaseWizardAction#next(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  public ActionForward next(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
    ClientProvWizardForm form = (ClientProvWizardForm)rawForm;
    String softwareID = form.getString("softwareID");
    if (StringUtils.isEmpty(softwareID)) {
       saveError4MissSoftware(request);
       return this.unspecified(mapping, rawForm, request, res);
    }
    return (mapping.findForward("next"));
  }

  /* (non-Javadoc)
   * @see com.npower.dm.myportal.BaseWizardAction#prev(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  public ActionForward prev(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
    return (mapping.findForward("prev"));
  }  

}
