/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/subscriber/SubscribersAction.java,v 1.13 2008/11/25 05:30:15 zhao Exp $
 * $Revision: 1.13 $
 * $Date: 2008/11/25 05:30:15 $
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

package com.npower.dm.action.subscriber;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.ServiceProvider;
import com.npower.dm.core.Subscriber;
import com.npower.dm.displaytag.PaginatedListAdapter;
import com.npower.dm.hibernate.entity.SubscriberEntity;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.PaginatedResult;
import com.npower.dm.management.SearchBean;
import com.npower.dm.management.ServiceProviderBean;
import com.npower.dm.util.DisplayTagHelper;

/**
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/Carriers" name="CarrierForm" scope="request"
 *                validate="true"
 * @struts.action-forward name="display" path="/jsp/carriers.jsp"
 *                        contextRelative="true"
 */
public class SubscribersAction extends BaseAction {

  // --------------------------------------------------------- Instance
  // Variables

  // --------------------------------------------------------- Methods

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

  /**
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

  private PaginatedList search(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws DMException {
    DynaValidatorForm searchForm = (DynaValidatorForm) form;
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    CarrierBean carrierBean = factory.createCarrierBean();
    ServiceProviderBean serviceProviderBean = factory.createServiceProviderBean();
    ModelBean modelBean = factory.createModelBean();
    
    SearchBean searchBean = factory.createSearchBean();
    Criteria criteria = searchBean.newCriteriaInstance(SubscriberEntity.class);
    criteria.addOrder(Order.asc("externalId"));

    String searchCarrierID = searchForm.getString("searchCarrierID");
    String searchServiceProviderID = searchForm.getString("searchServiceProviderID");
    String searchText = searchForm.getString("searchText");
    String searchExternalID = searchForm.getString("searchExternalID");
    String searchIMSI = searchForm.getString("searchIMSI");
    String searchPhoneNumber = searchForm.getString("searchPhoneNumber");
    String searchActivity = searchForm.getString("searchActivity");
    
    String searchManufacturerID = searchForm.getString("searchManufacturerID");
    String searchModelID = searchForm.getString("searchModelID");
    String searchDeviceExternalID = searchForm.getString("searchDeviceExternalID");

    if (StringUtils.isNotEmpty(searchCarrierID)) {
       Carrier carrier = carrierBean.getCarrierByID(searchCarrierID);
       if (carrier != null) {
          criteria.add(Expression.eq("carrier", carrier));
       }
    }

    if (StringUtils.isNotEmpty(searchServiceProviderID)) {
      ServiceProvider serviceProvider = serviceProviderBean.getServiceProviderByID(searchServiceProviderID);
       if (serviceProvider != null) {
          criteria.add(Expression.eq("serviceProvider", serviceProvider));
       }
    }

    if (StringUtils.isNotEmpty(searchExternalID)) {
       criteria.add(Expression.ilike("externalId", searchExternalID, MatchMode.ANYWHERE));
    }
 
    if (StringUtils.isNotEmpty(searchIMSI)) {
       criteria.add(Expression.ilike("IMSI", searchIMSI, MatchMode.ANYWHERE));
    }

    if (StringUtils.isNotEmpty(searchPhoneNumber)) {
       criteria.add(Expression.ilike("phoneNumber", searchPhoneNumber, MatchMode.ANYWHERE));
    }

    if (StringUtils.isNotEmpty(searchActivity)) {
       criteria.add(Expression.eq("isActivated", new Boolean(searchActivity)));
    }
    
    if (StringUtils.isNotEmpty(searchManufacturerID) ||
        StringUtils.isNotEmpty(searchModelID) ||
        StringUtils.isNotEmpty(searchDeviceExternalID)) {
      Criteria deviceCriteria = criteria.createCriteria("devices");
      if (StringUtils.isNotEmpty(searchManufacturerID)) {
         Manufacturer manufacturer = modelBean.getManufacturerByID(searchManufacturerID);
         if (manufacturer != null) {
            deviceCriteria.createCriteria("model").add(Expression.eq("manufacturer", manufacturer));
         }
      }

      if (StringUtils.isNotEmpty(searchModelID)) {
         Model model = modelBean.getModelByID(searchModelID);
         if (model != null) {
            deviceCriteria.add(Expression.eq("model", model));
         }
      }

      if (StringUtils.isNotEmpty(searchDeviceExternalID)) {
         deviceCriteria.add(Expression.ilike("externalId", searchDeviceExternalID, MatchMode.ANYWHERE));
      }
    }

    if (StringUtils.isNotEmpty(searchText)) {
       LogicalExpression subCriteria = Expression.or(Expression.ilike("externalId", searchText, MatchMode.ANYWHERE), 
                                                     Expression.ilike("phoneNumber", searchText, MatchMode.ANYWHERE));
       criteria.add(subCriteria);
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
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

    DynaValidatorForm searchForm = (DynaValidatorForm) form;
    try {
        // For Search Panel
        setAttributes4SearchPanel(this.getManagementBeanFactory(request), request, searchForm);
        
        PaginatedList result = this.search(mapping, form, request, response);
        if (result.getFullListSize() != 1) {
           // Multiple subscribers
           request.setAttribute("subscribers", result);
           return (mapping.findForward("multiple"));     
        } else {
          // Single subscriber
          Subscriber subscriber = (Subscriber)result.getList().get(0);
          request.setAttribute("subscriberID", "" + subscriber.getID());
          return (mapping.findForward("single"));     
        }
  
        
    } catch (DMException e) {
      throw e;
    }
  }

}
