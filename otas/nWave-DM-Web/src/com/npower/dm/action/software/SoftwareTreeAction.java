/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/software/SoftwareTreeAction.java,v 1.1 2008/02/22 03:50:10 LAH Exp $
 * $Revision: 1.1 $
 * $Date: 2008/02/22 03:50:10 $
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
import org.apache.struts.validator.DynaValidatorForm;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;

import com.npower.dm.action.BaseDispatchAction;
import com.npower.dm.core.DDFNode;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwareCategory;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SearchBean;
import com.npower.dm.management.SoftwareBean;

/**
 * @author Liu AiHui
 * @version $Revision: 1.1 $ $Date: 2008/02/22 03:50:10 $
 */

public class SoftwareTreeAction extends BaseDispatchAction {
  private static String nodeAction = "/software/softwareTree";

  // --------------------------------------------------------- Instance
  // Variables

  /**
   * Display mode
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward display(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    DynaValidatorForm form = (DynaValidatorForm) rawForm;

    // Load data for Tree
    MenuRepository repository = this.loadCategoryTree(request);
    request.setAttribute("categoryTreeRepository", repository);

    // Load data for nodes
    String nodeID = (String) form.get("nodeID");
    if (StringUtils.isNotEmpty(nodeID)) {
      SoftwareCategory currentNode = this.loadCategoryTreeNode(request, nodeID);
      request.setAttribute("categoryTreeNode", currentNode);
      List<Software> softwares = this.loadSoftwares(request, currentNode);
      request.setAttribute("softwares", softwares);
    } else {
      List<Software> softwares = this.loadSoftwares(request);
      request.setAttribute("children", softwares);
    }
    return mapping.findForward("display");
  }

  private MenuRepository loadCategoryTree(HttpServletRequest request) throws DMException {
    MenuRepository repository = new MenuRepository();
    // Get the repository from the application scope - and copy the
    // DisplayerMappings from it.
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
  private Set<SoftwareCategory> rootNodes(HttpServletRequest request) throws DMException {
    // 找没有父节点的categories
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
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

  // ---------------------------------------------------------

  private void convert(Set<SoftwareCategory> nodes, MenuRepository repository, MenuComponent parentMenu) {
    for (SoftwareCategory node : nodes) {
      MenuComponent menu = new MenuComponent();
      menu.setName("ID" + node.getId());
      String title = node.getName();
      title = (title == null) ? DDFNode.NAME_OF_DYNAMIC_NODE : title;
      menu.setTitle(title);
      menu.setParent(parentMenu);

      menu.setTarget("_blank");
      menu.setAction(nodeAction + "?action=display&nodeID=" + node.getId());
      repository.addMenu(menu);
      Set<SoftwareCategory> children = node.getChildren();
      if (!children.isEmpty()) {
        convert(children, repository, menu);
      }
    }
  }

  /**
   * @param request
   * @param nodeID
   * @return
   * @throws DMException
   */
  private SoftwareCategory loadCategoryTreeNode(HttpServletRequest request, String nodeID) throws DMException {
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    SoftwareBean softwareBean = factory.createSoftwareBean();
    SoftwareCategory node = softwareBean.getCategoryByID(Long.valueOf(nodeID));
    return node;
  }

  /**
   * @param request
   * @param category
   * @return
   * @throws DMException
   */
  private List<Software> loadSoftwares(HttpServletRequest request, SoftwareCategory category) throws DMException {
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    SearchBean searchBean = factory.createSearchBean();
    Criteria criteria = searchBean.newCriteriaInstance(Software.class);
    criteria.add(Expression.eq("category", category));
    List<Software> softwares = searchBean.find(criteria);
    return softwares;
  }

  /**
   * @param request
   * @return
   * @throws DMException
   */
  private List<Software> loadSoftwares(HttpServletRequest request) throws DMException {
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    SoftwareBean searchBean = factory.createSoftwareBean();
    List<Software> softwares = searchBean.getAllOfSoftwares();
    return softwares;
  }
}
