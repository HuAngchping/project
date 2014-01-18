/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/audit/AuditLogsAction.java,v 1.8 2008/01/16 03:06:46 LAH Exp $
 * $Revision: 1.8 $
 * $Date: 2008/01/16 03:06:46 $
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

package com.npower.dm.action.audit;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.DynaValidatorForm;
import org.displaytag.pagination.PaginatedList;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

import com.npower.dm.action.ActionHelper;
import com.npower.dm.action.BaseAction;
import com.npower.dm.core.DMException;
import com.npower.dm.displaytag.PaginatedListAdapter;
import com.npower.dm.hibernate.entity.AuditLogEntity;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.PaginatedResult;
import com.npower.dm.management.SearchBean;
import com.npower.dm.util.DisplayTagHelper;

/**
 * MyEclipse Struts Creation date: 05-30-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/Countries" name="CountryForm" scope="request"
 *                validate="true"
 * @struts.action-forward name="display" path="/jsp/countries.jsp"
 *                        contextRelative="true"
 */
public class AuditLogsAction extends BaseAction {
  

  // --------------------------------------------------------- Instance
  // Variables

  // --------------------------------------------------------- Methods

  private PaginatedList search(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    DynaValidatorForm searchForm = (DynaValidatorForm) form;
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);

    SearchBean searchBean = factory.createSearchBean();
    Criteria criteria = searchBean.newCriteriaInstance(AuditLogEntity.class);
    criteria.addOrder(Order.desc("creationDate"));
    
    String searchAuditActionType = searchForm.getString("searchAuditActionType");
    Criteria criteria4ActionType = criteria.createCriteria("action");
    if (StringUtils.isNotEmpty(searchAuditActionType)) {
       criteria4ActionType.add(Expression.eq("type", searchAuditActionType));
    }
    
    String searchAuditActionValue = searchForm.getString("searchAuditActionValue");
    if (StringUtils.isNotEmpty(searchAuditActionValue)) {
       criteria4ActionType.add(Expression.eq("value", searchAuditActionValue));
    }
    
    String searchUserName = searchForm.getString("searchUserName");
    if (StringUtils.isNotEmpty(searchUserName)) {
       criteria.add(Expression.ilike("userName", searchUserName, MatchMode.ANYWHERE));
    }

    String searchIPAddress = searchForm.getString("searchIPAddress");
    if (StringUtils.isNotEmpty(searchIPAddress)) {
       criteria.add(Expression.ilike("ipAddress", searchIPAddress, MatchMode.ANYWHERE));
    }

    String searchText = searchForm.getString("searchText");
    if (StringUtils.isNotEmpty(searchText)) {
       criteria.add(Expression.ilike("description", searchText, MatchMode.ANYWHERE));
    }
  
    String searchBeginTimeString = searchForm.getString("searchBeginTime");
    String searchEndTimeString = searchForm.getString("searchEndTime");
    Date searchBeginTime = null;
    Date searchEndTime = null;
    if (StringUtils.isNotEmpty(searchBeginTimeString)) {
       try {
           DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");
           searchBeginTime = formatter.parse(searchBeginTimeString);
      } catch (ParseException e) {
        throw e;
      }
    }
    if (StringUtils.isNotEmpty(searchEndTimeString)) {
       try {
           DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");
           searchEndTime = formatter.parse(searchEndTimeString);
       } catch (ParseException e) {
         throw e;
       }
    }
    if (searchBeginTime != null && searchEndTime != null) {
       criteria.add(Expression.and(Expression.ge("creationDate", searchBeginTime), 
                                   Expression.le("creationDate", searchEndTime)));
    } else if (searchBeginTime != null) {
      criteria.add(Expression.ge("creationDate", searchBeginTime));
    } else if (searchEndTime != null) {
      criteria.add(Expression.le("creationDate", searchEndTime));
    }
    
    int pageNumber = DisplayTagHelper.getPageNumber(request);
    int recordsPerPage = getRecordsPerPage(request);
    PaginatedResult result = searchBean.getPaginatedList(criteria, pageNumber, recordsPerPage);
    PaginatedList list = new PaginatedListAdapter(result);
    //List<AuditLogEntity> result = searchBean.find(criteria);
    return list;
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
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
      
    DynaValidatorForm searchForm = (DynaValidatorForm) form;
    try {
        ManagementBeanFactory factory = getManagementBeanFactory(request);
        MessageResources messageResources = this.getResources(request);
        Collection<LabelValueBean> actionTypes = ActionHelper.getAuditActionTypeOptions(factory, messageResources, this.getLocale(request));
        request.setAttribute("actionTypes", actionTypes);
        String auditLogActionType = searchForm.getString("searchAuditActionType");
        Collection<LabelValueBean> actionValues = ActionHelper.getAuditActionOptions(factory, messageResources, this.getLocale(request), auditLogActionType);
        request.setAttribute("actionValues", actionValues);
      
        PaginatedList result = this.search(mapping, form, request, response);
        
        request.setAttribute("auditLogs", result);
        // Set options for Records per page options.
        BaseAction.setRecordsPerPageOptions(request, searchForm);
        if (request.getParameter("recordsPerPage") == null) {
           searchForm.set("recordsPerPage", new Integer(100));
        }
  
        return (mapping.findForward("display"));
    } catch (DMException e) {
      throw e;
    }
  }

}
