/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.npower.dm.action.model;

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
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

import com.npower.dm.action.BaseAction;
import com.npower.dm.core.DMException;
import com.npower.dm.core.ModelClassification;
import com.npower.dm.hibernate.entity.ModelClassificationEntity;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelClassificationBean;
import com.npower.dm.management.SearchBean;

/**
 * MyEclipse Struts Creation date: 09-04-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class ModelClassificationsAction extends BaseAction {

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
    ManagementBeanFactory factory = null;
    try {
      factory = this.getManagementBeanFactory(request);
      String searchText = searchForm.getString("searchText");
      if (StringUtils.isNotEmpty(searchText)) {
        List<ModelClassification> result = searchModelClassification(this.getManagementBeanFactory(request), mapping, searchForm, request, response);
        request.setAttribute("modelClassifications", result);
      } else {
        ModelClassificationBean bean = factory.createModelClassificationBean();
        List<ModelClassification> modelClassificationList = bean.getAllOfModelClassifications();
        request.setAttribute("modelClassifications", modelClassificationList);
      }
      buildUIElementsForSearchPanel(this.getManagementBeanFactory(request), request, searchForm);
      return mapping.findForward("view");
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * @param request
   * @param searchForm
   * @throws DMException
   */
  public static void buildUIElementsForSearchPanel(ManagementBeanFactory factory, HttpServletRequest request,
      DynaValidatorForm searchForm) throws DMException {
    BaseAction.setRecordsPerPageOptions(request, searchForm);
  }

  /**
   * @param factory
   * @param mapping
   * @param searchForm
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  private List<ModelClassification> searchModelClassification(ManagementBeanFactory factory, ActionMapping mapping,
      DynaValidatorForm searchForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
    SearchBean searchBean = factory.createSearchBean();
    Criteria criteria = searchBean.newCriteriaInstance(ModelClassificationEntity.class);
    criteria.addOrder(Order.asc("externalID"));

    String searchText = searchForm.getString("searchText");

    if (StringUtils.isNotEmpty(searchText)) {
      Disjunction orExpression = Expression.disjunction();
      orExpression.add(Expression.ilike("externalID", searchText, MatchMode.ANYWHERE));
      orExpression.add(Expression.ilike("name", searchText, MatchMode.ANYWHERE));
      orExpression.add(Expression.ilike("description", searchText, MatchMode.ANYWHERE));
      criteria.add(orExpression);
    }
    List<ModelClassification> result = searchBean.find(criteria);
    return result;
  }
}