/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/software/CategoryAction.java,v 1.8 2009/02/23 03:15:34 zhaowx Exp $
 * $Revision: 1.8 $
 * $Date: 2009/02/23 03:15:34 $
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

import java.util.List;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.npower.dm.action.BaseDispatchAction;
import com.npower.dm.core.DMException;
import com.npower.dm.core.SoftwareCategory;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SoftwareBean;

/**
 * @author Liu AiHui
 * @version $Revision: 1.8 $ $Date: 2009/02/23 03:15:34 $
 */

public class CategoryAction extends BaseDispatchAction {

  /**
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    String id = request.getParameter("id");
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    // Load data for Tree
    CategoriesAction.loadCategoryTree(factory, this.getServlet().getServletContext(), request);
    
    SoftwareBean softwareBean = factory.createSoftwareBean();
    SoftwareCategory category = softwareBean.getCategoryByID(Long.valueOf(id));
    request.setAttribute("children", category.getChildren());
    request.setAttribute("category", category);
    request.setAttribute("parent", category.getParent());
    request.setAttribute("AddLocationLabels", category.getPath());
    return (mapping.findForward("view"));
  }

  /**
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    String id = request.getParameter("id");
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    try {
      factory.beginTransaction();
      SoftwareBean softwareBean = factory.createSoftwareBean();
      SoftwareCategory category = softwareBean.getCategoryByID(Long.valueOf(id));
      SoftwareCategory parentCategory = category.getParent();
      request.setAttribute("parent", parentCategory);
      softwareBean.delete(category);
      factory.commit();
    } catch (Exception e) {
      factory.rollback();
      throw new DMException("Remove Software Category Failure!");
    }
    return (mapping.findForward("display"));
  }
  // ************** 
  public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    
    String id = request.getParameter("id");
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    // Load data for Tree
    CategoriesAction.loadCategoryTree(factory, this.getServlet().getServletContext(), request);

    SoftwareBean softwareBean = factory.createSoftwareBean();
    List<SoftwareCategory> categories = softwareBean.getAllOfCategories();
    if (StringUtils.isNotEmpty(id)) {
      SoftwareCategory category = softwareBean.getCategoryByID(Long.valueOf(id));
      DynaActionForm categoryForm = (DynaActionForm) form;
      categoryForm.getMap().putAll(BeanUtils.describe(category));
      if (category.getParent() != null) {
        categoryForm.set("parentId", String.valueOf(category.getParent().getId()));
      }
      request.setAttribute("category", category);
    }

    request.setAttribute("parents", new TreeSet<SoftwareCategory>(categories));
    return mapping.findForward("edit");
  }
}
