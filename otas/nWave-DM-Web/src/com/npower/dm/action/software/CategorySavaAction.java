/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/software/CategorySavaAction.java,v 1.1 2008/09/12 08:42:34 hcp Exp $
 * $Revision: 1.1 $
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.npower.dm.action.BaseAction;
import com.npower.dm.core.DMException;
import com.npower.dm.core.SoftwareCategory;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SoftwareBean;

/**
 * @author Liu AiHui
 * @version $Revision: 1.1 $ $Date: 2008/09/12 08:42:34 $
 */

public class CategorySavaAction extends BaseAction {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    DynaActionForm categoryForm = (DynaActionForm) form;
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    // Load data for Tree
    CategoriesAction.loadCategoryTree(factory, this.getServlet().getServletContext(), request);
    try {
      SoftwareBean softwareBean = factory.createSoftwareBean();
      factory.beginTransaction();
      SoftwareCategory category = null;
      String id = categoryForm.getString("id");
      if (StringUtils.isEmpty(id)) {
        category = softwareBean.newCategoryInstance();
        BeanUtils.populate(category, categoryForm.getMap());
      } else {
        category = softwareBean.getCategoryByID(Long.valueOf(id));
        BeanUtils.populate(category, categoryForm.getMap());
      }
      String parentId = categoryForm.getString("parentId");
      SoftwareCategory parent = null;
      if (StringUtils.isNotEmpty(parentId)) {
        parent = softwareBean.getCategoryByID(Long.valueOf(parentId));
        category.setParent(parent);
      } else {
        category.setParent(null);
      }
      softwareBean.update(category);
      factory.commit();
      request.setAttribute("children", category.getChildren());
      request.setAttribute("category", category);
      request.setAttribute("parent", category.getParent());
      request.setAttribute("AddLocationLabels", category.getPath());
    } catch (Exception e) {
      factory.rollback();
      throw new DMException("Sava Software Category Failure!");
    }
    return (mapping.findForward("view"));
  }
}
