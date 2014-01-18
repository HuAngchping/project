/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/manufacturer/ManufacturersAction.java,v 1.21 2008/01/30 05:20:00 zhao Exp $
 * $Revision: 1.21 $
 * $Date: 2008/01/30 05:20:00 $
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

package com.npower.dm.action.manufacturer;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;
import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

import com.npower.dm.action.ActionHelper;
import com.npower.dm.action.BaseAction;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.hibernate.entity.ManufacturerEntity;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SearchBean;
import com.npower.dm.security.DMWebPrincipal;
import com.npower.dm.security.SecurityService;

/**
 * MyEclipse Struts Creation date: 05-30-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/Manufacturers" name="ManufacturerForm" scope="request"
 *                validate="true"
 * @struts.action-forward name="display" path="/jsp/manufacturers.jsp"
 *                        contextRelative="true"
 */
public class ManufacturersAction extends BaseAction {

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

    DynaValidatorForm searchForm = (DynaValidatorForm) form;

    try {
        ManagementBeanFactory factory = this.getManagementBeanFactory(request);
        SearchBean searchBean = factory.createSearchBean();
        Criteria criteria = searchBean.newCriteriaInstance(ManufacturerEntity.class);

        criteria.addOrder(Order.asc("externalId"));
        String searchManufacturerID = searchForm.getString("searchManufacturerID");
        String searchText = searchForm.getString("searchText");
        
        // Create OR criteria to retrieve manufacturers
        Disjunction manufacturersCriteria = Expression.disjunction();
        criteria.add(manufacturersCriteria);

        // Add Search criteria by role
        DMWebPrincipal principal = ActionHelper.getDMWebPrincipal(request);
        if (principal.hasRole(SecurityService.MANUFACTURER_OPERATOR_ROLE)) {
           List<String> ownedManufExternaIDs = principal.getOwnedManufacturerExternalIDs();
           for (String manufExternalID: ownedManufExternaIDs) {
               if (StringUtils.isNotEmpty(manufExternalID)) {
                  manufacturersCriteria.add(Expression.eq("externalId", manufExternalID));
               }
           }
        }
        
        if (StringUtils.isNotEmpty(searchManufacturerID)) {
           criteria.add(Expression.eq("ID", new Long(searchManufacturerID)));
        }
        if (StringUtils.isNotEmpty(searchText)) {
           LogicalExpression subCriteria = Expression.or(Expression.ilike("name", searchText, MatchMode.ANYWHERE), 
                                                         Expression.ilike("externalId", searchText, MatchMode.ANYWHERE));
           criteria.add(subCriteria);
        }
        
        List<Manufacturer> result = searchBean.find(criteria);
        request.setAttribute("manufacturers", result);
    
        // Set options
        request.setAttribute("manufacturerOptions", ActionHelper.getManufacturerOptions(factory, request));
        // Set options for Records per page options.
        BaseAction.setRecordsPerPageOptions(request, searchForm);
    
        return (mapping.findForward("display"));
    } catch (Exception ex) {
      throw ex;
    }

  }

}
