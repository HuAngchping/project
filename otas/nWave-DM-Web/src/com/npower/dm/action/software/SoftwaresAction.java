/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/software/SoftwaresAction.java,v 1.10 2008/08/29 03:29:05 zhao Exp $
 * $Revision: 1.10 $
 * $Date: 2008/08/29 03:29:05 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2008 NPower Network Software Ltd.  All rights reserved.
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
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.validator.DynaValidatorForm;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

import com.npower.dm.action.BaseAction;
import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwareCategory;
import com.npower.dm.core.SoftwareVendor;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SearchBean;
import com.npower.dm.management.SoftwareBean;

/**
 * @author Liu AiHui
 * @version $Revision: 1.10 $ $Date: 2008/08/29 03:29:05 $
 */

public class SoftwaresAction extends BaseAction {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    DynaValidatorForm searchForm = (DynaValidatorForm) form;

    try {
      ManagementBeanFactory factory = getManagementBeanFactory(request);

      SearchBean searchBean = factory.createSearchBean();
      SoftwareBean softwareBean = factory.createSoftwareBean();
      Criteria criteria = searchBean.newCriteriaInstance(Software.class);

      criteria.addOrder(Order.asc("name"));
      criteria.addOrder(Order.asc("version"));

      String searchName = searchForm.getString("searchName");
      String searchText = searchForm.getString("searchText");
      String searchStatus = searchForm.getString("searchStatus");
      String searchVendor = searchForm.getString("searchVendor");
      String searchCategory = searchForm.getString("searchCategory");

      if (StringUtils.isNotEmpty(searchName)) {
        criteria.add(Expression.like("name", searchName, MatchMode.ANYWHERE));
      }
      if (StringUtils.isNotEmpty(searchText)) {
        LogicalExpression subCriteria = Expression.or(Expression.ilike("name", searchText, MatchMode.ANYWHERE),
            Expression.ilike("description", searchText, MatchMode.ANYWHERE));
        criteria.add(subCriteria);
      }
      if (StringUtils.isNotEmpty(searchCategory)) {
        SoftwareCategory category = softwareBean.getCategoryByID(Long.parseLong(searchCategory));
        Criteria categoriesCrit = criteria.createCriteria("softwareCategoriesSet");
        categoriesCrit.add(Expression.eq("id.category", category));
      }
      if (StringUtils.isNotEmpty(searchVendor)) {
        SoftwareVendor vendor = softwareBean.getVendorByID(Long.parseLong(searchVendor));
        criteria.add(Expression.eq("vendor", vendor));
      }
      if (StringUtils.isNotEmpty(searchStatus)) {
        criteria.add(Expression.eq("status", searchStatus));
      }

      List<Software> result = searchBean.find(criteria);

      request.setAttribute("softwares", result);

      List<SoftwareVendor> vendors = softwareBean.getAllOfVendors();
      List<SoftwareCategory> categories = softwareBean.getAllOfCategories();

      List<LabelValueBean> vendorLVs = new ArrayList<LabelValueBean>();
      for (SoftwareVendor v: vendors) {
    	  String label = v.getName();
    	  if (v.getName().length() > 6) {
    		 label = v.getName().substring(0, 6) + ".";
    	  }
    	  vendorLVs.add(new LabelValueBean(label, Long.toString(v.getId())));
      }
      request.setAttribute("categories", new TreeSet<SoftwareCategory>(categories));
      request.setAttribute("vendors", vendorLVs);

      // Set options for Records per page options.
      BaseAction.setRecordsPerPageOptions(request, searchForm);

      return (mapping.findForward("display"));
    } catch (Exception ex) {
      throw ex;
    }
  }
  
}
