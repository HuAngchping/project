/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/software/EditSoftwareAction.java,v 1.5 2008/08/30 10:09:39 hcp Exp $
 * $Revision: 1.5 $
 * $Date: 2008/08/30 10:09:39 $
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
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.npower.dm.action.BaseAction;
import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwareCategory;
import com.npower.dm.core.SoftwareVendor;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SoftwareBean;

/**
 * @author Liu AiHui
 * @version $Revision: 1.5 $ $Date: 2008/08/30 10:09:39 $
 */

public class EditSoftwareAction extends BaseAction {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    if (this.isCancelled(request)) {
      return (mapping.findForward("display"));
    }
    String id = request.getParameter("id");
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    SoftwareBean softwareBean = factory.createSoftwareBean();
    DynaActionForm softwareForm = (DynaActionForm) form;
    if (StringUtils.isNotEmpty(id)) {
       Software software = softwareBean.getSoftwareByID(Long.valueOf(id));
       Set<SoftwareCategory> selectCategories = software.getCategoryies();
       if (selectCategories.isEmpty()) {
         selectCategories.add(software.getCategory());
       }
       softwareForm.getMap().putAll(BeanUtils.describe(software));
       softwareForm.set("vendorID", String.valueOf(software.getVendor().getId()));
       request.setAttribute("selectCategories", selectCategories);
    } else {
      // Auto generate an externalId
      String externalId = "" + (System.currentTimeMillis() & 0xffffff);
      softwareForm.set("externalId", externalId);
    }
    List<SoftwareVendor> vendors = softwareBean.getAllOfVendors();
    List<SoftwareCategory> categories = softwareBean.getAllOfCategories();

    request.setAttribute("categories", new TreeSet<SoftwareCategory>(categories));
    request.setAttribute("vendors", vendors);
    return (mapping.findForward("edit"));
  }
}

