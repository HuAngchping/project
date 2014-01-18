/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/software/CategoriesAction.java,v 1.9 2008/09/12 08:42:34 hcp Exp $
 * $Revision: 1.9 $
 * $Date: 2008/09/12 08:42:34 $
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

package com.npower.dm.action.software;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.navigator.menu.MenuComponent;
import net.sf.navigator.menu.MenuRepository;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.validator.DynaValidatorForm;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

import com.npower.dm.action.BaseAction;
import com.npower.dm.core.DDFNode;
import com.npower.dm.core.DMException;
import com.npower.dm.core.SoftwareCategory;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SearchBean;
import com.npower.dm.management.SoftwareBean;

/**
 * @author Liu AiHui
 * @version $Revision: 1.9 $ $Date: 2008/09/12 08:42:34 $
 */

public class CategoriesAction extends BaseAction{
  private static String nodeAction = "/software/categories.do";
  private static String nodeViewAction = "/software/category.do";
  public ActionForward execute(ActionMapping mapping, ActionForm Rowform, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    DynaValidatorForm searchForm = (DynaValidatorForm) Rowform;

    try {
      ManagementBeanFactory factory = getManagementBeanFactory(request);

      // Load data for Tree
      loadCategoryTree(factory, this.getServlet().getServletContext(), request);
      
      SearchBean searchBean = factory.createSearchBean();
      Criteria criteria = searchBean.newCriteriaInstance(SoftwareCategory.class);

      criteria.addOrder(Order.asc("name"));
      String searchName = searchForm.getString("searchName");
      String searchText = searchForm.getString("searchText");
      String id = request.getParameter("id");

      if (StringUtils.isNotEmpty(searchName)) {
        criteria.add(Expression.like("name", searchName, MatchMode.ANYWHERE));
      }
      if (StringUtils.isNotEmpty(searchText)) {
        LogicalExpression subCriteria = Expression.or(Expression.ilike("name", searchText, MatchMode.ANYWHERE),
            Expression.ilike("description", searchText, MatchMode.ANYWHERE));
        criteria.add(subCriteria);
      }

      List<SoftwareCategory> result = new ArrayList<SoftwareCategory>();
      if (StringUtils.isNotEmpty(searchName) || StringUtils.isNotEmpty(searchText)) {
        result = searchBean.find(criteria);
      } else {
        SoftwareBean bean = factory.createSoftwareBean();
        SoftwareCategory currentCategory = null;
        if (StringUtils.isNotEmpty(id)) {
          currentCategory = bean.getCategoryByID(Long.parseLong(id));
        }
        if (currentCategory != null) {
          for (SoftwareCategory category : currentCategory.getChildren()) {
            result.add(category);
          }
          request.setAttribute("AddLocationLabels", currentCategory.getPath());
        } else {
          for (SoftwareCategory category : bean.getAllOfCategories()) {
            if (category.getParent() == null) {
              result.add(category);
            }
          }
        }
      }
      Set<SoftwareCategory> categories = new TreeSet<SoftwareCategory>(result);
      request.setAttribute("categories", categories);

      // Set options for Records per page options.
      BaseAction.setRecordsPerPageOptions(request, searchForm);

      return (mapping.findForward("display"));
    } catch (Exception ex) {
      throw ex;
    }

  }

  /**
   * @param request
   * @throws DMException
   */
  static void loadCategoryTree(ManagementBeanFactory factory, ServletContext servletContext, HttpServletRequest request) throws DMException {
    // Load data for Tree
    // Get the repository from the application scope - and copy the
    // DisplayerMappings from it.
    MenuRepository defaultRepository = (MenuRepository) servletContext.getAttribute(
        MenuRepository.MENU_REPOSITORY_KEY);
    MenuRepository repository = loadCategoryTree(factory, defaultRepository, request);
    request.setAttribute("categoryTreeRepository", repository);
  }
  
  private static MenuRepository loadCategoryTree(ManagementBeanFactory factory, MenuRepository defaultRepository, HttpServletRequest request) throws DMException {
    MenuRepository repository = new MenuRepository();
    repository.setDisplayers(defaultRepository.getDisplayers());
    MenuComponent rootMenu = new MenuComponent();
    rootMenu.setName("root");
    repository.addMenu(rootMenu);

    Set<SoftwareCategory> rootCategories = rootNodes(factory, request);
    
    Locale locale = RequestUtils.getUserLocale(request, null);
    MessageResources messageResources = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
    String label = messageResources.getMessage(locale, "meta.software.category.root.label");
    rootMenu.setTitle(label);
    rootMenu.setAction(nodeAction);
    convert(rootCategories, repository, rootMenu);
    return repository;
  }

  /**
   * @param request
   * @return
   * @throws DMException
   */
  private static Set<SoftwareCategory> rootNodes(ManagementBeanFactory factory, HttpServletRequest request) throws DMException {
    // 找没有父节点的categories
    SoftwareBean softwareBean = factory.createSoftwareBean();
    List<SoftwareCategory> categories = softwareBean.getAllOfCategories();
    Set<SoftwareCategory> rootCategories = new TreeSet<SoftwareCategory>();
    for (SoftwareCategory category : categories) {
      if (category.getParent() == null) {
        rootCategories.add(category);
      }
    }
    return rootCategories;
  }

  // --------------------------------------------------------- Instance
  // Variables

  protected static void convert(Set<SoftwareCategory> nodes, MenuRepository repository, MenuComponent parentMenu) {
    for (SoftwareCategory node : nodes) {
      MenuComponent menu = new MenuComponent();
      menu.setName("ID" + node.getId());
      String title = node.getName();
      title = (title == null) ? DDFNode.NAME_OF_DYNAMIC_NODE : title;
      menu.setTitle(title);
      menu.setParent(parentMenu);

      menu.setTarget("_blank");
      menu.setAction(nodeViewAction + "?action=view&id=" + node.getId());
      repository.addMenu(menu);
      Set<SoftwareCategory> children = node.getChildren();
      if (!children.isEmpty()) {
        convert(children, repository, menu);
      }
    }
  }

}
