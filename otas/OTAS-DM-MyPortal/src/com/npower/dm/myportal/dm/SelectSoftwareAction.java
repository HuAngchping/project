/**
  * $Header: /home/master/OTAS-DM-MyPortal/src/com/npower/dm/myportal/dm/SelectSoftwareAction.java,v 1.7 2008/04/25 09:27:05 zhao Exp $
  * $Revision: 1.7 $
  * $Date: 2008/04/25 09:27:05 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2007 NPower Network Software Ltd.  All rights reserved.
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
package com.npower.dm.myportal.dm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.navigator.menu.MenuComponent;
import net.sf.navigator.menu.MenuRepository;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.displaytag.pagination.PaginatedList;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

import com.npower.dm.action.PersistentManager;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;
import com.npower.dm.core.DDFNode;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Model;
import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwareCategory;
import com.npower.dm.core.SoftwareVendor;
import com.npower.dm.decorator.DecoratorHelper;
import com.npower.dm.displaytag.PaginatedListAdapter;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.PaginatedResult;
import com.npower.dm.management.SearchBean;
import com.npower.dm.management.SoftwareBean;
import com.npower.dm.myportal.BaseWizardAction;
import com.npower.dm.myportal.cp.ClientProvWizardForm;
import com.npower.dm.util.DisplayTagHelper;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.7 $ $Date: 2008/04/25 09:27:05 $
 */
public class SelectSoftwareAction extends BaseWizardAction {
  
  private static String nodeAction = "wizard/dm/SelectSoftware.do";

  private MenuRepository loadCategoryTree(HttpServletRequest request) throws Exception {
    MenuRepository repository = new MenuRepository();
    MenuRepository defaultRepository = (MenuRepository) this.getServlet().getServletContext().getAttribute(
        MenuRepository.MENU_REPOSITORY_KEY);
    repository.setDisplayers(defaultRepository.getDisplayers());
    MenuComponent rootMenu = new MenuComponent();
    rootMenu.setName("root");
    repository.addMenu(rootMenu);
  
    Set<SoftwareCategory> rootCategories = rootNodes(request);
  
    rootMenu.setTitle(".");
    convert(rootCategories, repository, rootMenu);
    return repository;
  }

  /**
   * @param request
   * @return
   * @throws DMException
   */
  private Set<SoftwareCategory> rootNodes(HttpServletRequest request) throws Exception {
    // 找没有父节点的categories
    ManagementBeanFactory factory = PersistentManager.getManagementBeanFactory(request);
    SoftwareBean softwareBean = factory.createSoftwareBean();
    List<SoftwareCategory> categories = softwareBean.getAllOfCategories();
    Set<SoftwareCategory> rootCategories = new HashSet<SoftwareCategory>();
    for (SoftwareCategory category : categories) {
      if (category.getParent() == null) {
        rootCategories.add(category);
      }
    }
    return rootCategories;
  }

  
  private void convert(Set<SoftwareCategory> nodes, MenuRepository repository, MenuComponent parentMenu) {
    for (SoftwareCategory node : nodes) {
      MenuComponent menu = new MenuComponent();
      menu.setName("ID" + node.getId());
      String title = node.getName();
      title = (title == null) ? DDFNode.NAME_OF_DYNAMIC_NODE : title;
      menu.setTitle(title);
      menu.setParent(parentMenu);
  
      menu.setTarget("_blank");
      menu.setAction(nodeAction + "?nodeID=" + node.getId()+"&navigation=");
      repository.addMenu(menu);
      Set<SoftwareCategory> children = node.getChildren();
      if (!children.isEmpty()) {
        convert(children, repository, menu);
      }
    }
  }

  /**
   * @param request
   * @param category
   * @param form 
   * @return
   * @throws DMException
   */
  private PaginatedList loadSoftwares(HttpServletRequest request, ClientProvWizardForm form) throws Exception {    
    String nodeID = request.getParameter("nodeID");
    if(StringUtils.isEmpty(nodeID)){
      nodeID = form.getString("nodeID");
    }
    
    ManagementBeanFactory factory = PersistentManager.getManagementBeanFactory(request);
    SoftwareBean softwareBean = factory.createSoftwareBean(); 
    
    SearchBean searchBean = factory.createSearchBean();
    Criteria criteria = searchBean.newCriteriaInstance(Software.class);
    criteria.addOrder(Order.asc("name"));
    criteria.addOrder(Order.asc("version"));
    
    if (StringUtils.isNotEmpty(nodeID)) {
      SoftwareCategory currentNode = softwareBean.getCategoryByID(Long.valueOf(nodeID));
      criteria.add(Expression.eq("category", currentNode));
     }

    String vendorID = form.getString("vendorID");
    String searchText = form.getString("searchText");
    if(StringUtils.isNotEmpty(vendorID)){      
      SoftwareVendor vendor = softwareBean.getVendorByID(Long.parseLong(vendorID));
      criteria.add(Expression.eq("vendor", vendor));
    }
    if(StringUtils.isNotEmpty(searchText)){
      LogicalExpression subCriteria = Expression.or(
          Expression.ilike("name", searchText, MatchMode.ANYWHERE), 
          Expression.ilike("description", searchText, MatchMode.ANYWHERE));
      criteria.add(subCriteria);
    }
    
    //List<Software> softwares = searchBean.find(criteria);
    int pageNumber = DisplayTagHelper.getPageNumber(request);
    int recordsPerPage = getRecordsPerPage(request);
    
    PaginatedResult result = searchBean.getPaginatedList(criteria, pageNumber, recordsPerPage);
    PaginatedList softwares = new PaginatedListAdapter(result);

    return softwares;
  }  

  /* (non-Javadoc)
   * @see com.npower.dm.myportal.BaseWizardAction#view(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  public ActionForward view(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
    ClientProvWizardForm form = (ClientProvWizardForm)rawForm;

    ManagementBeanFactory factory = PersistentManager.getManagementBeanFactory(request);
    // Get Country
    Country country = getCountry(factory, form, request);
    if (country == null) {
       // Missing country
       return mapping.getInputForward();
    }
    request.setAttribute("country", DecoratorHelper.decorate(country, this.getLocale(request)));

    // Get Carrier
    Carrier carrier = this.getCarrier(factory, form, request);
    if (carrier == null) {
       return mapping.getInputForward();
    }
    request.setAttribute("carrier", carrier);

    // Get Model
    Model model = getModel(factory, form, request);
    if (model != null) {
      request.setAttribute("model", model);
      request.setAttribute("manufacturer", DecoratorHelper.decorate(model.getManufacturer(), this.getLocale(request)));
    }
     //Software Tree Part
    MenuRepository repository = this.loadCategoryTree(request);
    request.setAttribute("softwareTreeRepository", repository);

    SoftwareBean softwareBean = factory.createSoftwareBean();
    List<SoftwareVendor> vendors = softwareBean.getAllOfVendors();
    request.setAttribute("vendorOptions", vendors);

    //List<Software> softwares = this.loadSoftwares(request, form);
    PaginatedList softwares = this.loadSoftwares(request, form);

    request.setAttribute("softwares", softwares);
    BaseWizardAction.setRecordsPerPageOptions(request, form);
    
    return (mapping.findForward("view"));
  }

  /* (non-Javadoc)
   * @see com.npower.dm.myportal.BaseWizardAction#next(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  public ActionForward next(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
    ClientProvWizardForm form = (ClientProvWizardForm)rawForm;
    String softwareID = form.getString("softwareID");
    if (StringUtils.isEmpty(softwareID)) {
       saveError4MissSoftware(request);
       return this.unspecified(mapping, rawForm, request, res);
    }
    return (mapping.findForward("next"));
  }

  /* (non-Javadoc)
   * @see com.npower.dm.myportal.BaseWizardAction#prev(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  public ActionForward prev(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
    return (mapping.findForward("prev"));
  }  

}
