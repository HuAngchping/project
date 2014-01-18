/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/profile/AttributeTypesAction.java,v 1.8 2007/10/09 08:55:36 LAH Exp $
 * $Revision: 1.8 $
 * $Date: 2007/10/09 08:55:36 $
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;
import org.displaytag.pagination.PaginatedList;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

import com.npower.dm.action.BaseAction;
import com.npower.dm.core.DMException;
import com.npower.dm.core.ProfileAttributeType;
import com.npower.dm.displaytag.PaginatedListAdapter;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.PaginatedResult;
import com.npower.dm.management.SearchBean;
import com.npower.dm.util.DisplayTagHelper;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.8 $ $Date: 2007/10/09 08:55:36 $
 */
public class AttributeTypesAction extends BaseAction {

  private PaginatedList search(ActionMapping mapping, String searchText, HttpServletRequest request, HttpServletResponse response) throws DMException {
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);

    SearchBean searchBean = factory.createSearchBean();
    Criteria criteria = searchBean.newCriteriaInstance(ProfileAttributeType.class);
    criteria.addOrder(Order.asc("ID"));
    

    if (StringUtils.isNotEmpty(searchText)) {
      LogicalExpression subCriteria = Expression.or(Expression.ilike("name", searchText, MatchMode.ANYWHERE), 
          Expression.ilike("description", searchText, MatchMode.ANYWHERE));
      criteria.add(subCriteria);
    }
        
    int pageNumber = DisplayTagHelper.getPageNumber(request);
    int recordsPerPage = getRecordsPerPage(request);
    PaginatedResult result = searchBean.getPaginatedList(criteria, pageNumber, recordsPerPage);
    PaginatedList list = new PaginatedListAdapter(result);
    return list;
  }

  /**
   * Retrieve all of profile attribute types and put these into request.
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward execute(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) throws Exception {

    //ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    //ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
    //List list = templateBean.findProfileAttributeTypes("from ProfileAttributeTypeEntity");
    
    DynaValidatorForm searchForm = (DynaValidatorForm) rawForm;
    String searchText = searchForm.getString("searchText");
    PaginatedList list = this.search(mapping, searchText, request, response);
    request.setAttribute("attributeTypes", list);
    

    BaseAction.setRecordsPerPageOptions(request, searchForm);
    if (request.getParameter("recordsPerPage") == null) {
       searchForm.set("recordsPerPage", new Integer(15));
    }

    return (mapping.findForward("attributeTypes"));
  }

}
