/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/jobs/DevicesInProgressAction.java,v 1.1 2008/02/13 10:28:38 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2008/02/13 10:28:38 $
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
package com.npower.dm.action.jobs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.validator.DynaValidatorForm;
import org.displaytag.pagination.PaginatedList;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

import com.npower.dm.action.BaseAction;
import com.npower.dm.action.GlobalUtils;
import com.npower.dm.core.DMException;
import com.npower.dm.displaytag.PaginatedListAdapter;
import com.npower.dm.hibernate.entity.DeviceEntity;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.PaginatedResult;
import com.npower.dm.management.SearchBean;
import com.npower.dm.util.DisplayTagHelper;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/02/13 10:28:38 $
 */
public class DevicesInProgressAction extends BaseAction {

  private PaginatedList getDevices(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws DMException {
    DynaActionForm form = (DynaActionForm) rawForm;

    ManagementBeanFactory factory = getManagementBeanFactory(request);
    SearchBean searchBean = factory.createSearchBean();
    Criteria mainCrt = searchBean.newCriteriaInstance(DeviceEntity.class);

    mainCrt.addOrder(Order.asc("lastUpdatedTime"));

    String jobType = form.getString("jobType");
    String deviceID = form.getString("deviceID");
    String searchText = form.getString("searchText");
    String phoneNumber = form.getString("phoneNumber");
    
    mainCrt.add(Expression.isNotNull("inProgressDeviceProvReq"));
    
    if (StringUtils.isNotEmpty(searchText)) {
      mainCrt.add(Expression.or(Expression.ilike("externalId", searchText, MatchMode.ANYWHERE), 
                                Expression.ilike("subscriberPhoneNumber", searchText, MatchMode.ANYWHERE)));
    }
   
    if (StringUtils.isNotEmpty(phoneNumber)) {
        mainCrt.add(Expression.ilike("subscriberPhoneNumber", phoneNumber, MatchMode.ANYWHERE));
    }
    
    if (StringUtils.isNotEmpty(deviceID)) {
       mainCrt.add(Expression.ilike("externalId", deviceID, MatchMode.ANYWHERE));
    }
  
    if (StringUtils.isNotEmpty(jobType)) {
      
    }
    
    int pageNumber = DisplayTagHelper.getPageNumber(request);
    int recordsPerPage = getRecordsPerPage(request);
    PaginatedResult result = searchBean.getPaginatedList(mainCrt, pageNumber, recordsPerPage);
    PaginatedList list = new PaginatedListAdapter(result);
    
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
  public ActionForward execute(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    DynaValidatorForm searchForm = (DynaValidatorForm) rawForm;
    
    // Load Job types for select box
    GlobalUtils.loadJobTypes(mapping, rawForm, request, response);
    try {
        PaginatedList result = this.getDevices(mapping, rawForm, request, response);
        request.setAttribute("devices", result);
        
        // Set options for Records per page options.
        BaseAction.setRecordsPerPageOptions(request, searchForm);

        return (mapping.findForward("list"));
    } catch (DMException e) {
      throw e;
    }
  }
}
