/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/carrier/CarriersAction.java,v 1.11 2007/03/28 08:22:08 zhao Exp $
 * $Revision: 1.11 $
 * $Date: 2007/03/28 08:22:08 $
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

package com.npower.dm.action.carrier;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;

import com.npower.dm.action.ActionHelper;
import com.npower.dm.action.BaseAction;
import com.npower.dm.core.Country;
import com.npower.dm.core.DMException;
import com.npower.dm.hibernate.entity.CarrierEntity;
import com.npower.dm.management.CountryBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SearchBean;

/**
 * MyEclipse Struts Creation date: 05-30-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/Carriers" name="CarrierForm" scope="request"
 *                validate="true"
 * @struts.action-forward name="display" path="/jsp/carriers.jsp"
 *                        contextRelative="true"
 */
public class CarriersAction extends BaseAction {

  // --------------------------------------------------------- Instance
  // Variables

  // --------------------------------------------------------- Methods

  private List search(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws DMException {
    DynaValidatorForm searchForm = (DynaValidatorForm) form;
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    CountryBean countryBean = factory.createCountryBean();
    SearchBean searchBean = factory.createSearchBean();
    Criteria criteria = searchBean.newCriteriaInstance(CarrierEntity.class);

    String searchCountryID = searchForm.getString("searchCountryID");
    String searchText = searchForm.getString("searchText");
    
    if (StringUtils.isNotEmpty(searchCountryID)) {
       Country country = countryBean.getCountryByID(Long.parseLong(searchCountryID));
       if (country != null) {
          criteria.add(Expression.eq("country", country));
       }
    }

    if (StringUtils.isNotEmpty(searchText)) {
       LogicalExpression subCriteria = Expression.or(Expression.ilike("name", searchText, MatchMode.ANYWHERE), 
                                                     Expression.ilike("externalID", searchText, MatchMode.ANYWHERE));
       criteria.add(subCriteria);
    }
  
    List result = searchBean.find(criteria);
    return result;
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
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

    DynaValidatorForm searchForm = (DynaValidatorForm) form;
    try {
        List result = this.search(mapping, form, request, response);
        request.setAttribute("carriers", result);
  
        request.setAttribute("countryOptions", ActionHelper.getCountryOptions(this.getManagementBeanFactory(request)));
        // Set options for Records per page options.
        BaseAction.setRecordsPerPageOptions(request, searchForm);
  
        return (mapping.findForward("display"));
        
    } catch (DMException e) {
      throw e;
    }
  }

}
