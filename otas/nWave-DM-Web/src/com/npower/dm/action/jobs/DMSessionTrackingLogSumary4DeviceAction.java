/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/jobs/DMSessionTrackingLogSumary4DeviceAction.java,v 1.4 2009/02/12 06:26:45 zhao Exp $
 * $Revision: 1.4 $
 * $Date: 2009/02/12 06:26:45 $
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

package com.npower.dm.action.jobs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.displaytag.pagination.PaginatedList;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.npower.dm.action.BaseAction;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.displaytag.PaginatedListAdapter;
import com.npower.dm.hibernate.entity.DmTrackingLogSum;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.PaginatedResult;
import com.npower.dm.management.ProvisionJobBean;
import com.npower.dm.management.SearchBean;
import com.npower.dm.util.DisplayTagHelper;

/**
 * @author Zhao wanxiang
 * @version $Revision: 1.4 $ $Date: 2009/02/12 06:26:45 $
 */

public class DMSessionTrackingLogSumary4DeviceAction extends BaseAction {

  /**
   * 
   */
  public DMSessionTrackingLogSumary4DeviceAction() {
    super();
  }

  public ActionForward execute(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {   
         
    try {        
      ManagementBeanFactory factory = this.getManagementBeanFactory(request);
      ProvisionJobBean jobBean = factory.createProvisionJobBean();
      DeviceBean deviceBean = factory.createDeviceBean();
      
      SearchBean searchBean = factory.createSearchBean();
      String deviceID = request.getParameter("deviceID");
      String jobID = request.getParameter("jobID");

      ProvisionJob job = jobBean.loadJobByID(jobID);
      request.setAttribute("provisionJob", job);
      
      Device device = deviceBean.getDeviceByExternalID(deviceID);
      request.setAttribute("device", device);
      
      long jid = Long.parseLong(jobID);           
      Criteria criteria = searchBean.newCriteriaInstance(DmTrackingLogSum.class);
      criteria.addOrder(Order.desc("beginTimeStamp")); 
      criteria.add(Expression.eq("deviceId", deviceID)); 
      criteria.add(Expression.eq("jobId", jid));
      int pageNumber = DisplayTagHelper.getPageNumber(request);
      int recordsPerPage = getRecordsPerPage(request);
      PaginatedResult result = searchBean.getPaginatedList(criteria, pageNumber, recordsPerPage);
      PaginatedList results = new PaginatedListAdapter(result);                  
      request.setAttribute("results", results);
      return (mapping.findForward("list"));
    } catch (DMException e) {
      throw e;
    }
  }
  
}
