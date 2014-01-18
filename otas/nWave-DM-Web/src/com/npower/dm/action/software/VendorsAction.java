/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/software/VendorsAction.java,v 1.2 2008/02/02 07:48:46 zhaoyang Exp $
 * $Revision: 1.2 $
 * $Date: 2008/02/02 07:48:46 $
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
package com.npower.dm.action.software;

import java.util.ArrayList;
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

import com.npower.dm.action.BaseAction;
import com.npower.dm.core.SoftwareVendor;
import com.npower.dm.hibernate.entity.SoftwareEntity;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SearchBean;
import com.npower.dm.management.SoftwareBean;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/02/02 07:48:46 $
 */
public class VendorsAction extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    DynaValidatorForm searchForm = (DynaValidatorForm) form;

    try {
      ManagementBeanFactory factory = this.getManagementBeanFactory(request);

      SearchBean searchBean = factory.createSearchBean();
      Criteria criteria = searchBean.newCriteriaInstance(SoftwareEntity.class);

      criteria.addOrder(Order.asc("name"));
      String searchName = searchForm.getString("searchName");
      String searchText = searchForm.getString("searchText");

      // Create OR criteria to retrieve manufacturers
      Disjunction manufacturersCriteria = Expression.disjunction();
      criteria.add(manufacturersCriteria);

      if (StringUtils.isNotEmpty(searchName)) {
        criteria.add(Expression.like("name", searchName, MatchMode.ANYWHERE));
      }
      if (StringUtils.isNotEmpty(searchText)) {
        LogicalExpression subCriteria = Expression.or(Expression.ilike("name", searchText, MatchMode.ANYWHERE),
            Expression.ilike("description", searchText, MatchMode.ANYWHERE));
        criteria.add(subCriteria);
      }

      List<SoftwareVendor> result = new ArrayList<SoftwareVendor>();
      if (StringUtils.isNotEmpty(searchName) || StringUtils.isNotEmpty(searchText)) {
        result = searchBean.find(criteria);
      } else {
        SoftwareBean bean = factory.createSoftwareBean();
        result = bean.getAllOfVendors();
      }

      request.setAttribute("vendors", result);

      // Set options for Records per page options.
      BaseAction.setRecordsPerPageOptions(request, searchForm);

      return (mapping.findForward("display"));
    } catch (Exception ex) {
      throw ex;
    }

  }
}
