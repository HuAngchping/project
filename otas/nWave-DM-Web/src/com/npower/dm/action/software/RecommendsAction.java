/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
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

import com.npower.dm.action.BaseAction;
import com.npower.dm.core.DDFNode;
import com.npower.dm.core.DMException;
import com.npower.dm.core.SoftwareCategory;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SoftwareBean;
import com.npower.dm.management.SoftwareTopListBean;

/**
 * MyEclipse Struts Creation date: 08-22-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class RecommendsAction extends BaseAction {
  private static String nodeAction = "/software/recommends.do";

  /**
   * Method execute
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward execute(ActionMapping mapping, ActionForm Rowform, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    String id = request.getParameter("id");
    ManagementBeanFactory factory = null;
    try {
      factory = this.getManagementBeanFactory(request);
      SoftwareTopListBean topListBean = factory.createSoftwareTopListBean();
      // Load data for Tree
      loadRecommendTree(factory, this.getServlet().getServletContext(), request);

      SoftwareBean bean = factory.createSoftwareBean();
      List<SoftwareCategory> result = new ArrayList<SoftwareCategory>();
      
      if (StringUtils.isNotEmpty(id)) {
        
        SoftwareCategory currentCategory = bean.getCategoryByID(Long.parseLong(id));
        request.setAttribute("category", currentCategory);
        request.setAttribute("recommendSoftwares", topListBean.getRecommendedSoftwares(currentCategory));
        request.setAttribute("AddLocationLabels", currentCategory.getPath());
        return mapping.findForward("view");       
      } else {
        
        for (SoftwareCategory category : bean.getAllOfCategories()) {
          if (category.getParent() == null) {
            result.add(category);
          }
        }
        Set<SoftwareCategory> categories = new TreeSet<SoftwareCategory>(result);
        List<String> locationList = new ArrayList<String>();
        Locale locale = RequestUtils.getUserLocale(request, null);
        MessageResources messageResources = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
        String label = messageResources.getMessage(locale, "meta.software.recommend.category.root.label");
        locationList.add(label);
        request.setAttribute("categories", categories);
        request.setAttribute("recommendSoftwares", topListBean.getRecommendedSoftwares(null));
        request.setAttribute("AddLocationLabels", locationList);
        return mapping.findForward("display");
      }
    } catch (Exception ex) {
      throw ex;
    }

  }

  /**
   * @param request
   * @throws DMException
   */
  static void loadRecommendTree(ManagementBeanFactory factory, ServletContext servletContext, HttpServletRequest request)
      throws DMException {
    // Load data for Tree
    // Get the repository from the application scope - and copy the
    // DisplayerMappings from it.
    MenuRepository defaultRepository = (MenuRepository) servletContext.getAttribute(MenuRepository.MENU_REPOSITORY_KEY);
    MenuRepository repository = loadRecommendTree(factory, defaultRepository, request);
    request.setAttribute("categoryTreeRepository", repository);
  }

  private static MenuRepository loadRecommendTree(ManagementBeanFactory factory, MenuRepository defaultRepository,
      HttpServletRequest request) throws DMException {
    MenuRepository repository = new MenuRepository();
    repository.setDisplayers(defaultRepository.getDisplayers());
    MenuComponent rootMenu = new MenuComponent();
    rootMenu.setName("root");
    repository.addMenu(rootMenu);

    Set<SoftwareCategory> rootCategories = rootNodes(factory, request);

    Locale locale = RequestUtils.getUserLocale(request, null);
    MessageResources messageResources = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
    String label = messageResources.getMessage(locale, "meta.software.recommend.root.label");
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
  private static Set<SoftwareCategory> rootNodes(ManagementBeanFactory factory, HttpServletRequest request)
      throws DMException {
    // ��û�и��ڵ��categories
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
      menu.setAction(nodeAction + "?id=" + node.getId());
      repository.addMenu(menu);
      Set<SoftwareCategory> children = node.getChildren();
      if (!children.isEmpty()) {
        convert(children, repository, menu);
      }
    }
  }

}