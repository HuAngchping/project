/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/device/DeviceChangeLogsAction.java,v 1.3 2008/11/11 14:04:42 zhao Exp $
  * $Revision: 1.3 $
  * $Date: 2008/11/11 14:04:42 $
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
package com.npower.dm.action.device;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.validator.DynaValidatorForm;
import org.displaytag.pagination.PaginatedList;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

import com.npower.dm.action.ActionHelper;
import com.npower.dm.action.BaseAction;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.displaytag.PaginatedListAdapter;
import com.npower.dm.hibernate.entity.DmDeviceChangeLog;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.PaginatedResult;
import com.npower.dm.management.SearchBean;
import com.npower.dm.util.DisplayTagHelper;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/11/11 14:04:42 $
 */
public class DeviceChangeLogsAction extends BaseAction {
  // --------------------------------------------------------- Instance
  // Variables

  // --------------------------------------------------------- Methods

  /**
   * Set attributes for Search panel
   * @param request
   * @param searchForm
   * @throws DMException
   */
  public static void setAttributes4SearchPanel(ManagementBeanFactory factory, HttpServletRequest request, DynaValidatorForm searchForm) throws DMException {
    request.setAttribute("manufacturerOptions", ActionHelper.getManufacturerOptions(factory, request));
    request.setAttribute("modelOptions", getModelOptions(factory, request, searchForm));
    // Set options for Records per page options.
    BaseAction.setRecordsPerPageOptions(request, searchForm);
  }

  private static Collection<LabelValueBean> getModelOptions(ManagementBeanFactory factory, HttpServletRequest request, DynaValidatorForm form) throws DMException {
    Collection<LabelValueBean> result = new ArrayList<LabelValueBean>();
    
    ModelBean modelBean = factory.createModelBean();
    String manufacturerID = (String)form.get("searchManufacturerID");
    Manufacturer manufacturer = null;
    if (manufacturerID != null && manufacturerID.trim().length() > 0) {
       manufacturer = modelBean.getManufacturerByID(manufacturerID);
    }
    
    if (manufacturer == null) {
       return result;
    }
    Set<Model> set = new TreeSet<Model>(manufacturer.getModels());
    for (Model model: set) {
        LabelValueBean labelValue = new LabelValueBean(model.getManufacturerModelId(), "" + model.getID());
        result.add(labelValue);
    }
    return result;
  }
  
  private PaginatedList search(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    DynaValidatorForm searchForm = (DynaValidatorForm) form;
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ModelBean modelBean = factory.createModelBean();
    
    SearchBean searchBean = factory.createSearchBean();
    Criteria criteria = searchBean.newCriteriaInstance(DmDeviceChangeLog.class);
    criteria.addOrder(Order.desc("lastUpdate"));

    String searchManufacturerID = searchForm.getString("searchManufacturerID");
    String searchMadelID = searchForm.getString("searchModelID");
    String searchText = searchForm.getString("searchText");
    String searchExternalID = searchForm.getString("searchExternalID");
    String searchIMSI = searchForm.getString("searchIMSI");
    String searchPhoneNumber = searchForm.getString("searchPhoneNumber");
    
    if (StringUtils.isNotEmpty(searchManufacturerID)) {
       Manufacturer manufactuer = modelBean.getManufacturerByID(searchManufacturerID);
       if (manufactuer != null) {
          criteria.add(Expression.or(Expression.eq("brand", manufactuer.getName()), 
                                     Expression.eq("brand", manufactuer.getExternalId()))
          );
       }
    }
    
    if (StringUtils.isNotEmpty(searchMadelID)) {
       Model model = modelBean.getModelByID(searchMadelID);
       if (model != null) {
         criteria.add(Expression.or(Expression.eq("brand", model.getName()), 
                                    Expression.eq("brand", model.getManufacturerModelId()))
         );
       }
    }
    
    if (StringUtils.isNotEmpty(searchExternalID)) {
       criteria.add(Expression.ilike("imei", searchExternalID, MatchMode.ANYWHERE));
    }
    
    if (StringUtils.isNotEmpty(searchPhoneNumber)) {
       criteria.add(Expression.ilike("phoneNumber", searchPhoneNumber, MatchMode.ANYWHERE));
    }
   
    if (StringUtils.isNotEmpty(searchIMSI)) {
       criteria.add(Expression.ilike("imsi", searchIMSI, MatchMode.ANYWHERE));
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
       criteria.add(Expression.and(Expression.ge("lastUpdate", searchBeginTime), 
                                  Expression.le("lastUpdate", searchEndTime)));
    } else if (searchBeginTime != null) {
      criteria.add(Expression.ge("lastUpdate", searchBeginTime));
    } else if (searchEndTime != null) {
      criteria.add(Expression.le("lastUpdate", searchEndTime));
    }

    if (StringUtils.isNotEmpty(searchText)) {
       LogicalExpression subCriteria = Expression.or(Expression.ilike("imei", searchText, MatchMode.ANYWHERE), 
                                                     Expression.ilike("phoneNumber", searchText, MatchMode.ANYWHERE));
       criteria.add(subCriteria);
    }
    
    int pageNumber = DisplayTagHelper.getPageNumber(request);
    int recordsPerPage = getRecordsPerPage(request);
    PaginatedResult result = searchBean.getPaginatedList(criteria, pageNumber, recordsPerPage);
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
    
    // Clear device from HttpSession
    HttpSession session = request.getSession(false);
    session.removeAttribute("device");

    try {
        // Set options for SearchPanel
        setAttributes4SearchPanel(this.getManagementBeanFactory(request), request, searchForm);
      
        PaginatedList result = this.search(mapping, rawForm, request, response);
        // Multiple devices view
        request.setAttribute("deviceChangeLogs", result);
        return (mapping.findForward("list"));
    } catch (DMException e) {
      throw e;
    }
  }

}
