/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/device/DevicesAction.java,v 1.17 2008/10/31 09:30:29 hcp Exp $
  * $Revision: 1.17 $
  * $Date: 2008/10/31 09:30:29 $
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

import java.util.ArrayList;
import java.util.Collection;
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
import com.npower.dm.core.Carrier;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.ServiceProvider;
import com.npower.dm.displaytag.PaginatedListAdapter;
import com.npower.dm.hibernate.entity.DeviceEntity;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.PaginatedResult;
import com.npower.dm.management.SearchBean;
import com.npower.dm.management.ServiceProviderBean;
import com.npower.dm.util.DisplayTagHelper;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.17 $ $Date: 2008/10/31 09:30:29 $
 */
public class DevicesAction extends BaseAction {
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
    request.setAttribute("carrierOptions", ActionHelper.getCarrierOptions(factory));
    request.setAttribute("serviceProviderOptions", ActionHelper.getServiceProviderOptions(factory));
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
  
  private PaginatedList search(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws DMException {
    DynaValidatorForm searchForm = (DynaValidatorForm) form;
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ModelBean modelBean = factory.createModelBean();
    CarrierBean carrierBean = factory.createCarrierBean();
    ServiceProviderBean serviceProviderBean = factory.createServiceProviderBean();
    
    SearchBean searchBean = factory.createSearchBean();
    Criteria criteria = searchBean.newCriteriaInstance(DeviceEntity.class);
    criteria.addOrder(Order.asc("externalId"));

    String searchManufacturerID = searchForm.getString("searchManufacturerID");
    String searchMadelID = searchForm.getString("searchModelID");
    String searchCarrierID = searchForm.getString("searchCarrierID");
    String searchServiceProviderID = searchForm.getString("searchServiceProviderID");
    String searchText = searchForm.getString("searchText");
    String searchExternalID = searchForm.getString("searchExternalID");
    String searchPhoneNumber = searchForm.getString("searchPhoneNumber");
    String searchActivity = searchForm.getString("searchActivity");
    String searchBooted = searchForm.getString("searchBooted");
    String searchIMSI = searchForm.getString("searchIMSI");
    
    if (StringUtils.isNotEmpty(searchManufacturerID)) {
       Manufacturer manufactuer = modelBean.getManufacturerByID(searchManufacturerID);
       if (manufactuer != null) {
          Criteria subCriteria = criteria.createCriteria("model");
          subCriteria.add(Expression.eq("manufacturer", manufactuer));
       }
    }
    
    if (StringUtils.isNotEmpty(searchMadelID)) {
       Model model = modelBean.getModelByID(searchMadelID);
       if (model != null) {
          criteria.add(Expression.eq("model", model));
       }
    }
    
    Criteria subscriberCriteria = null;
    if (StringUtils.isNotEmpty(searchCarrierID)) {
       Carrier carrier = carrierBean.getCarrierByID(searchCarrierID);
       if (carrier != null) {
          if (subscriberCriteria == null) {
             subscriberCriteria = criteria.createCriteria("subscriber");
          }
          subscriberCriteria.add(Expression.eq("carrier", carrier));
       }
    }
    
    if (StringUtils.isNotEmpty(searchServiceProviderID)) {
       ServiceProvider serviceProvider = serviceProviderBean.getServiceProviderByID(searchServiceProviderID);
       if (serviceProvider != null) {
          if (subscriberCriteria == null) {
             subscriberCriteria = criteria.createCriteria("subscriber");
          }
          subscriberCriteria.add(Expression.eq("serviceProvider", serviceProvider));
       }
    }
    
    if (StringUtils.isNotEmpty(searchIMSI)) {
      if (subscriberCriteria == null) {
         subscriberCriteria = criteria.createCriteria("subscriber");
      }
      subscriberCriteria.add(Expression.like("IMSI", searchIMSI, MatchMode.ANYWHERE));
    }

    if (StringUtils.isNotEmpty(searchExternalID)) {
       criteria.add(Expression.ilike("externalId", searchExternalID, MatchMode.ANYWHERE));
    }
    
    if (StringUtils.isNotEmpty(searchPhoneNumber)) {
       criteria.add(Expression.ilike("subscriberPhoneNumber", searchPhoneNumber, MatchMode.ANYWHERE));
    }
   
    if (StringUtils.isNotEmpty(searchText)) {
       LogicalExpression subCriteria = Expression.or(Expression.ilike("externalId", searchText, MatchMode.ANYWHERE), 
                                                     Expression.ilike("subscriberPhoneNumber", searchText, MatchMode.ANYWHERE));
       criteria.add(subCriteria);
    }
    
    if (StringUtils.isNotEmpty(searchActivity)) {
       Boolean activity = new Boolean(searchActivity);
       criteria.add(Expression.eq("isActivated", activity));
    }
    
    if (StringUtils.isNotEmpty(searchBooted)) {
       Boolean booted = new Boolean(searchBooted);
       criteria.add(Expression.eq("booted", booted));
    }
   
    int pageNumber = DisplayTagHelper.getPageNumber(request);
    int recordsPerPage = getRecordsPerPage(request);
    PaginatedResult result = searchBean.getPaginatedList(criteria, pageNumber, recordsPerPage);
    PaginatedList list = new PaginatedListAdapter(result);
    //List result = searchBean.find(criteria);
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
        if (result.getFullListSize() != 1) {
           // Multiple devices view
           request.setAttribute("devices", result);
           return (mapping.findForward("multiple"));
        } else {
          // Single Device View
          Device device = (Device)result.getList().get(0);
          request.setAttribute("deviceID", "" + device.getID());
          return (mapping.findForward("single"));          
        }
        
    } catch (DMException e) {
      throw e;
    }
  }

}
