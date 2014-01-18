/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/model/ModelsAction.java,v 1.23 2008/10/31 14:31:53 zhao Exp $
 * $Revision: 1.23 $
 * $Date: 2008/10/31 14:31:53 $
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

package com.npower.dm.action.model;

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
import org.apache.struts.validator.DynaValidatorForm;
import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

import com.npower.dm.action.ActionHelper;
import com.npower.dm.action.BaseAction;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.hibernate.entity.ModelEntity;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.SearchBean;
import com.npower.dm.security.DMWebPrincipal;
import com.npower.dm.security.SecurityService;

/**
 * MyEclipse Struts Creation date: 05-30-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/Models" name="ModelForm" scope="request"
 *                validate="true"
 * @struts.action-forward name="display" path="/jsp/models.jsp"
 *                        contextRelative="true"
 */
public class ModelsAction extends BaseAction {
  
  public class CharacterItem {
    private String category = null;
    private String name = null;
    
    public CharacterItem() {
      super();
    }
    
    public CharacterItem(String category, String name) {
      super();
      this.category = category;
      this.name = name;
    }

    public String getCategory() {
      return category;
    }
    public void setCategory(String category) {
      this.category = category;
    }
    public String getName() {
      return name;
    }
    public void setName(String name) {
      this.name = name;
    }
    
  }

  // --------------------------------------------------------- Instance
  // Variables

  // --------------------------------------------------------- Methods


  /**
   * Build UI Elements for Model Search Panel
   * @param request
   * @param searchForm
   * @throws DMException
   */
  public static void buildUIElementsForSearchPanel(ManagementBeanFactory factory, HttpServletRequest request, DynaValidatorForm searchForm)
      throws DMException {
    // Set options
    request.setAttribute("manufacturerOptions", ActionHelper.getManufacturerOptions(factory, request));
    request.setAttribute("modelOptions", getModelOptions(factory, request, searchForm));
    // Set options for Records per page options.
    BaseAction.setRecordsPerPageOptions(request, searchForm);
  }

  /**
   * @param factory
   * @param request
   * @param form
   * @return
   * @throws DMException
   */
  private static Collection<LabelValueBean> getModelOptions(ManagementBeanFactory factory, HttpServletRequest request, DynaValidatorForm form) throws DMException {
    Collection<LabelValueBean> result = new ArrayList<LabelValueBean>();

    ModelBean modelBean = factory.createModelBean();
    String manufacturerID = request.getParameter("searchManufacturerID");
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
   * Business methods for Searching models.
   * @param factory
   * @param mapping
   * @param searchForm
   * @param request
   * @param response
   * @return
   * @throws DMException
   */
  private List<Model> searchModels(ManagementBeanFactory factory, ActionMapping mapping, DynaValidatorForm searchForm, HttpServletRequest request, HttpServletResponse response) throws DMException {
    ModelBean modelBean = factory.createModelBean();
    SearchBean searchBean = factory.createSearchBean();
    Criteria criteria = searchBean.newCriteriaInstance(ModelEntity.class);
    criteria.addOrder(Order.asc("manufacturer"));
    criteria.addOrder(Order.asc("manufacturerModelId"));

    String searchManufacturerID = searchForm.getString("searchManufacturerID");
    String searchMadelID = searchForm.getString("searchModelID");
    String searchTAC = searchForm.getString("searchTAC");
    String searchText = searchForm.getString("searchText");
    
    // Create OR criteria to retrieve manufacturers
    Disjunction manufacturersCriteria = Expression.disjunction();
    criteria.add(manufacturersCriteria);

    // Add Search criteria by role
    DMWebPrincipal principal = ActionHelper.getDMWebPrincipal(request);
    if (principal.hasRole(SecurityService.MANUFACTURER_OPERATOR_ROLE)) {
        List<String> ownedManufExternaIDs = principal.getOwnedManufacturerExternalIDs();
        for (String manufExternalID: ownedManufExternaIDs) {
            Manufacturer manufactuer = modelBean.getManufacturerByExternalID(manufExternalID);
            if (manufactuer != null) {
               manufacturersCriteria.add(Expression.eq("manufacturer", manufactuer));
            }
        }
    }
    
    // Add Search criteria by SearchPannel
    if (StringUtils.isNotEmpty(searchManufacturerID)) {
       Manufacturer manufactuer = modelBean.getManufacturerByID(searchManufacturerID);
       if (manufactuer != null) {
          manufacturersCriteria.add(Expression.eq("manufacturer", manufactuer));
       }
    }
    
    // Add Search criteria by SearchPannel
    if (StringUtils.isNotEmpty(searchTAC)) {
       criteria.createAlias("modelTACs", "TAC");
       criteria.add(Expression.like("TAC.ID.TAC", searchTAC, MatchMode.START));
    }
    
    if (StringUtils.isNotEmpty(searchMadelID)) {
       criteria.add(Expression.eq("ID", new Long(searchMadelID)));
    }
   
    if (StringUtils.isNotEmpty(searchText)) {
       LogicalExpression subCriteria = Expression.or(Expression.ilike("name", searchText, MatchMode.ANYWHERE), 
                                                     Expression.ilike("manufacturerModelId", searchText, MatchMode.ANYWHERE));
       criteria.add(subCriteria);
    }
  
    String searchIsOmaDmEnabled = searchForm.getString("searchIsOmaDmEnabled");
    if (StringUtils.isNotEmpty(searchIsOmaDmEnabled)) {
       Boolean isOmaDmEnabled = new Boolean(searchIsOmaDmEnabled);
       criteria.add(Expression.eq("isOmaDmEnabled", isOmaDmEnabled));
    }

    String searchIsOmaCpEnabled = searchForm.getString("searchIsOmaCpEnabled");
    if (StringUtils.isNotEmpty(searchIsOmaCpEnabled)) {
       Boolean isOmaCpEnabled = new Boolean(searchIsOmaCpEnabled);
       criteria.add(Expression.eq("isOmaCpEnabled", isOmaCpEnabled));
    }

    String searchIsNokiaOtaEnabled = searchForm.getString("searchIsNokiaOtaEnabled");
    if (StringUtils.isNotEmpty(searchIsNokiaOtaEnabled)) {
       Boolean isNokiaOtaEnabled = new Boolean(searchIsNokiaOtaEnabled);
       criteria.add(Expression.eq("isNokiaOtaEnabled", isNokiaOtaEnabled));
    }
    
    String searchOmaDmVersion = searchForm.getString("searchOmaDmVersion");
    if (StringUtils.isNotEmpty(searchOmaDmVersion)) {
       criteria.add(Expression.eq("OmaDmVersion", searchOmaDmVersion));
    }

    String searchOmaCpVersion = searchForm.getString("searchOmaCpVersion");
    if (StringUtils.isNotEmpty(searchOmaCpVersion)) {
       criteria.add(Expression.eq("OmaCpVersion", searchOmaCpVersion));
    }

    String searchNokiaOtaVersion = searchForm.getString("searchNokiaOtaVersion");
    if (StringUtils.isNotEmpty(searchNokiaOtaVersion)) {
       criteria.add(Expression.eq("NokiaOtaVersion", searchNokiaOtaVersion));
    }
    
    String searchPlatform = searchForm.getString("searchPlatform");
    if (StringUtils.isNotEmpty(searchPlatform)) {
      // 定义查询的Character
      CharacterItem item1 = new CharacterItem("features", "os");
      CharacterItem item2 = new CharacterItem("general", "developer.platform");
      CharacterItem[] characters4Search = new CharacterItem[]{item1, item2};

      //Criteria specCriteria = criteria.createCriteria("modelSpec");
      Criteria specCharactersCriteria = criteria.createCriteria("characters");
      // 构造查询语句
      Criterion exp = getCriteria4CharacterSearch(searchPlatform, characters4Search);
      if (exp != null) {
         specCharactersCriteria.add(exp);
      }
    }

    List<Model> result = searchBean.find(criteria);
    return result;
  }

  /**
   * @param searchPlatform
   * @param specCharactersCriteria
   */
  private Criterion getCriteria4CharacterSearch(String value, CharacterItem[] characters4Search) {
    if (characters4Search == null || characters4Search.length == 0 || StringUtils.isEmpty(value)) {
       return null;
    }
    
    Disjunction disjunction = Expression.disjunction();
    for (CharacterItem item: characters4Search) {
        Conjunction conjunction = Expression.conjunction();
        conjunction.add(Expression.eq("category", item.getCategory()));
        conjunction.add(Expression.eq("name", item.getName()));
        conjunction.add(Expression.ilike("value", value, MatchMode.ANYWHERE));
        disjunction.add(conjunction);
    }
    return disjunction;
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
        List<Model> result = searchModels(this.getManagementBeanFactory(request), mapping, searchForm, request, response);
        if (result != null && result.size() == 1) {
           // Redirect to model view page
           Model model = result.get(0);
           request.setAttribute("modelID", "" + model.getID());
           return mapping.findForward("ViewModel");
        } else {
          // Redirect to model list page.
          request.setAttribute("models", result);
          // Build UI Elements for Model Search Panel
          buildUIElementsForSearchPanel(this.getManagementBeanFactory(request), request, searchForm);
          return (mapping.findForward("ModelList"));
        }
    } catch (DMException e) {
      throw e;
    }
  }

}
