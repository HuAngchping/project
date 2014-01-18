/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/jobs/BaseJobsAction.java,v 1.8 2009/02/13 09:37:13 chenlei Exp $
  * $Revision: 1.8 $
  * $Date: 2009/02/13 09:37:13 $
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.DynaValidatorForm;
import org.displaytag.pagination.PaginatedList;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

import com.npower.dm.action.ActionHelper;
import com.npower.dm.action.BaseAction;
import com.npower.dm.action.GlobalUtils;
import com.npower.dm.action.common.TargetDevicesCaculator;
import com.npower.dm.core.DMException;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.displaytag.PaginatedListAdapter;
import com.npower.dm.hibernate.entity.ProvisionRequest;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.PaginatedResult;
import com.npower.dm.management.SearchBean;
import com.npower.dm.util.DisplayTagHelper;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.8 $ $Date: 2009/02/13 09:37:13 $
 */
public abstract class BaseJobsAction extends BaseAction {

  static final String CURRENT_JOB_LIST_VIEWER_NAME = "current.job.list.viewer.name";

  protected PaginatedList getjobs(HttpServletRequest request, ActionForm rawForm, String jobState) throws DMException {
    DynaActionForm form = (DynaActionForm) rawForm;

    ManagementBeanFactory factory = getManagementBeanFactory(request);
    SearchBean searchBean = factory.createSearchBean();
    Criteria mainCrt = searchBean.newCriteriaInstance(ProvisionRequest.class);

    mainCrt.addOrder(Order.asc("scheduledTime"));

    String jobType = form.getString("jobType");
    String deviceJobStatus = form.getString("deviceJobStatus");
    String deviceID = form.getString("deviceID");
    String searchText = form.getString("searchText");
    String phoneNumber = form.getString("phoneNumber");
    String jobMode = form.getString("jobMode");
    
    if (StringUtils.isNotEmpty(jobType)) {
       mainCrt.add(Expression.eq("jobType", jobType));
    }
    if (StringUtils.isNotEmpty(jobMode)) {
       mainCrt.add(Expression.eq("jobMode", jobMode));
    }
    if (StringUtils.isNotEmpty(jobState)) {
       if (jobState.equals(ProvisionJob.JOB_STATE_APPLIED)) {
         mainCrt.add(Expression.and(Expression.eq("state", jobState), Expression.ne("jobMode", ProvisionJob.JOB_MODE_CP)));
       } else {
         mainCrt.add(Expression.eq("state", jobState));
       }
    }
    if (StringUtils.isNotEmpty(searchText)) {
       mainCrt.add(Expression.or(Expression.ilike("name", searchText, MatchMode.ANYWHERE), 
                                 Expression.ilike("description", searchText, MatchMode.ANYWHERE)));
    }
    
    Criteria elementCriteria = null;
    Criteria deviceStatusCriteria = null;
    Criteria deviceCriteria = null;
    Criteria subscriberCriteria = null;
    
    if (StringUtils.isNotEmpty(deviceJobStatus)) {
       if (elementCriteria == null) {
          elementCriteria = mainCrt.createCriteria("provisionElements");
       }
       if (deviceStatusCriteria == null) {
          deviceStatusCriteria = elementCriteria.createCriteria("deviceProvReqs");
       }
       deviceStatusCriteria.add(Expression.eq("state", deviceJobStatus));
    }
    
    if (StringUtils.isNotEmpty(deviceID)) {
       if (elementCriteria == null) {
          elementCriteria = mainCrt.createCriteria("provisionElements");
       }
       if (deviceStatusCriteria == null) {
          deviceStatusCriteria = elementCriteria.createCriteria("deviceProvReqs");
       }
       if (deviceCriteria == null) {
          deviceCriteria = deviceStatusCriteria.createCriteria("device");
       }
       deviceCriteria.add(Expression.ilike("externalId", deviceID, MatchMode.ANYWHERE));
    }
    
    if (StringUtils.isNotEmpty(phoneNumber)) {
       if (elementCriteria == null) {
          elementCriteria = mainCrt.createCriteria("provisionElements");
       }
       if (deviceStatusCriteria == null) {
          deviceStatusCriteria = elementCriteria.createCriteria("deviceProvReqs");
       }
       if (deviceCriteria == null) {
          deviceCriteria = deviceStatusCriteria.createCriteria("device");
       }
       if (subscriberCriteria == null) {
          subscriberCriteria = deviceCriteria.createCriteria("subscriber");
       }
       subscriberCriteria.add(Expression.ilike("phoneNumber", phoneNumber, MatchMode.ANYWHERE));
    }
   
    int pageNumber = DisplayTagHelper.getPageNumber(request);
    int recordsPerPage = getRecordsPerPage(request);
    PaginatedResult result = searchBean.getPaginatedList(mainCrt, pageNumber, recordsPerPage);
    PaginatedList list = new PaginatedListAdapter(result);
    
    //List<ProvisionRequest> result = searchBean.find(mainCrt);
    // Unique result
    //Set<ProvisionRequest> uniqueResult = new LinkedHashSet<ProvisionRequest>(result);
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

    // Load Job modes for select box
    GlobalUtils.loadJobModes(mapping, rawForm, request, response);
    // Load Job types for select box
    GlobalUtils.loadJobTypes(mapping, rawForm, request, response);
    // Load job states for select box
    GlobalUtils.loadJobStates(mapping, rawForm, request, response);
    // Load Device Job status for select box
    GlobalUtils.loadDeviceJobStates(mapping, rawForm, request, response);
    
    // Set options for Records per page options.
    BaseAction.setRecordsPerPageOptions(request, rawForm);

    return null;
    
  }

  /**
   * @param rawForm
   * @param request
   * @param jobType
   * @param parameters4JobName
   * @return
   */
  public static void setDefaultJobParameters(
      ActionForm rawForm, 
      MessageResources messageResources, 
      Locale locale, 
      String jobType,
      String[] parameters4JobName) {
    // Set default job name
    String jobName = ActionHelper.getDefaultJobName(messageResources, locale, jobType, parameters4JobName);
    Boolean defaultSendNotification = new Boolean(true);
    Long defaultConcurrentSize = new Long(50);
    Long defaultConcurrentInterval = new Long(100);
    
    if (rawForm instanceof DynaValidatorForm) {
       DynaValidatorForm form = (DynaValidatorForm) rawForm;
       form.set("sendNotification", defaultSendNotification);
       form.set("concurrentSize", defaultConcurrentSize);
       form.set("concurrentInterval", defaultConcurrentInterval);
       form.set("name", jobName);
    } else if (rawForm instanceof ProfileAssignmentForm4Save) {
      ProfileAssignmentForm4Save form = (ProfileAssignmentForm4Save) rawForm;
      form.setValue("sendNotification", defaultSendNotification);
      form.setValue("concurrentSize", defaultConcurrentSize);
      form.setValue("concurrentInterval", defaultConcurrentInterval);
      form.setValue("name", jobName);
    }
  }
  
  /**
   * @param factory
   * @param rawForm
   * @return
   * @throws FileNotFoundException
   * @throws IOException
   */
  public static TargetDevicesCaculator loadTargetDevicesCaculator(ManagementBeanFactory factory, HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    
    // Create a target devices caculator
    TargetDevicesCaculator targetDevicesCaculator = (TargetDevicesCaculator)session.getAttribute("TARGET_DEVICES_CACULATOR");
    // Reset transient field.
    targetDevicesCaculator.setFactory(factory);
    return targetDevicesCaculator;
  }

  /**
   * @param request
   * @param targetDevicesCaculator
   */
  public static void saveTargetDevicesCaculator(HttpServletRequest request, TargetDevicesCaculator targetDevicesCaculator) {
    HttpSession session = request.getSession(false);
    session.setAttribute("TARGET_DEVICES_CACULATOR", targetDevicesCaculator);
  }  

  /**
   * @param request
   * @param targetDevicesCaculator
   * @throws DMException
   */
  public static void setTargetDevices(ManagementBeanFactory factory, HttpServletRequest request)
      throws DMException {
    TargetDevicesCaculator targetDevicesCaculator = loadTargetDevicesCaculator(factory, request);
    int pageNumber = DisplayTagHelper.getPageNumber(request);
    int recordsPerPage = BaseAction.getRecordsPerPage(request);
    PaginatedResult targetDevices = targetDevicesCaculator.getPaginatedDevices(pageNumber, recordsPerPage);
    request.setAttribute("targetDevices",  new PaginatedListAdapter(targetDevices));
  }

}
