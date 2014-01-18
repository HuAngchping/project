/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/updates/UpdateImagesAction.java,v 1.2 2008/07/11 03:02:05 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2008/07/11 03:02:05 $
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

package com.npower.dm.action.updates;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.DynaValidatorForm;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

import com.npower.dm.action.ActionHelper;
import com.npower.dm.action.BaseAction;
import com.npower.dm.core.DMException;
import com.npower.dm.core.ImageUpdateStatus;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.hibernate.entity.UpdateEntity;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.SearchBean;
import com.npower.dm.management.UpdateImageBean;

/**
 * MyEclipse Struts Creation date: 05-30-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/EditModel" name="ModelForm" scope="request"
 * @struts.action-forward name="success" path="/jsp/model.jsp"
 *                        contextRelative="true"
 */
public class UpdateImagesAction extends BaseAction {

  private Criteria _fromImageCriteria = null;
  private Criteria _toImageCriteria = null;

  // --------------------------------------------------------- Instance
  // Variables

  // --------------------------------------------------------- Methods

  /**
   * @param criteria
   * @throws HibernateException
   */
  private Criteria getFromImageCriteria(Criteria criteria) throws HibernateException {
    if (_fromImageCriteria == null) {
       _fromImageCriteria = criteria.createCriteria("fromImage");
    }
    return this._fromImageCriteria;
  }

  /**
   * @param criteria
   * @throws HibernateException
   */
  private Criteria getToImageCriteria(Criteria criteria) throws HibernateException {
    if (_toImageCriteria == null) {
       _toImageCriteria = criteria.createCriteria("toImage");
    }
    return this._toImageCriteria;
  }

  private Collection<LabelValueBean> getModelOptions(HttpServletRequest request, DynaValidatorForm form) throws DMException {
    Collection<LabelValueBean> result = new ArrayList<LabelValueBean>();
    
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
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
  
  private List search(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws DMException {
    DynaValidatorForm searchForm = (DynaValidatorForm) form;
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ModelBean modelBean = factory.createModelBean();
    UpdateImageBean updateBean = factory.createUpdateImageBean();
    
    SearchBean searchBean = factory.createSearchBean();
    Criteria criteria = searchBean.newCriteriaInstance(UpdateEntity.class);
    criteria.addOrder(Order.asc("ID"));

    String searchManufacturerID = searchForm.getString("searchManufacturerID");
    String searchMadelID = searchForm.getString("searchModelID");
    //String searchText = searchForm.getString("searchText");
    String searchFromVersion = searchForm.getString("searchFromVersion");
    String searchToVersion = searchForm.getString("searchToVersion");
    String searchStatus = searchForm.getString("searchStatus");
    
    this._fromImageCriteria = null;
    this._toImageCriteria = null;
    
    if (StringUtils.isNotEmpty(searchManufacturerID)) {
       Manufacturer manufactuer = modelBean.getManufacturerByID(searchManufacturerID);
       if (manufactuer != null) {
          Criteria fromImageCriteria = getFromImageCriteria(criteria);
          Criteria subCriteria = fromImageCriteria.createCriteria("model");
          subCriteria.add(Expression.eq("manufacturer", manufactuer));
       }
    }
    
    if (StringUtils.isNotEmpty(searchMadelID)) {
       Model model = modelBean.getModelByID(searchMadelID);
       if (model != null) {
          Criteria fromImageCriteria = getFromImageCriteria(criteria);
          fromImageCriteria.add(Expression.eq("model", model));
       }
    }
    
    if (StringUtils.isNotEmpty(searchFromVersion)) {
       Criteria fromImageCriteria = getFromImageCriteria(criteria);
       fromImageCriteria.add(Expression.ilike("versionId", searchFromVersion, MatchMode.ANYWHERE));
    }
    
    if (StringUtils.isNotEmpty(searchToVersion)) {
       Criteria toImageCriteria = getToImageCriteria(criteria);
       toImageCriteria.add(Expression.ilike("versionId", searchToVersion, MatchMode.ANYWHERE));
    }
   
    if (StringUtils.isNotEmpty(searchStatus)) {
       ImageUpdateStatus status = updateBean.getImageUpdateStatus(Long.parseLong(searchStatus));
       criteria.add(Expression.eq("status", status));
    }
  
    List result = searchBean.find(criteria);
    return result;
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
  public ActionForward execute(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) throws Exception {

    DynaValidatorForm searchForm = (DynaValidatorForm) rawForm;
    
    try {
        List result = this.search(mapping, rawForm, request, response);
        request.setAttribute("updates", result);
        
        
        // Set options
        MessageResources messageResources = getResources(request);
        request.setAttribute("updateStatusOptions", ActionHelper.getUpdateImageStatusOptions(this.getManagementBeanFactory(request), messageResources, this.getLocale(request), request));
        request.setAttribute("manufacturerOptions", ActionHelper.getManufacturerOptions(this.getManagementBeanFactory(request), request));
        request.setAttribute("modelOptions", this.getModelOptions(request, searchForm));
        // Set options for Records per page options.
        BaseAction.setRecordsPerPageOptions(request, searchForm);
        
        // Set options for Records per page options.
        BaseAction.setRecordsPerPageOptions(request, searchForm);
        return (mapping.findForward("list"));
    } catch (DMException e) {
      throw e;
    }
  }

}
