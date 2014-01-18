/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/software/SavaSoftwareAction.java,v 1.9 2009/02/20 05:50:29 chenlei Exp $
 * $Revision: 1.9 $
 * $Date: 2009/02/20 05:50:29 $
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
import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwareCategory;
import com.npower.dm.core.SoftwareVendor;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SoftwareBean;

/**
 * @author Liu AiHui
 * @version $Revision: 1.9 $ $Date: 2009/02/20 05:50:29 $
 */

public class SavaSoftwareAction extends BaseAction {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    if (this.isCancelled(request)) {
      return (mapping.findForward("display"));
    }

    DynaActionForm softwareForm = (DynaActionForm) form;
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    try {
      SoftwareBean softwareBean = factory.createSoftwareBean();
      factory.beginTransaction();
      Software software = null;
      String id = softwareForm.getString("id");
      if (StringUtils.isEmpty(id)) {
        // Create mode
        software = softwareBean.newSoftwareInstance();
        BeanUtils.populate(software, softwareForm.getMap());
      } else {
        // Modify mode
        software = softwareBean.getSoftwareByID(Long.valueOf(id));
        BeanUtils.populate(software, softwareForm.getMap());
      }

      String vendorID = softwareForm.getString("vendorID");
      if (StringUtils.isNotEmpty(vendorID)) {
        SoftwareVendor vendor = softwareBean.getVendorByID(Long.parseLong(vendorID));
        software.setVendor(vendor);
      }

      softwareBean.update(software);

      String[] selectCategoryIds = softwareForm.getStrings("categoryID");
      List<SoftwareCategory> selectCategoryList = new ArrayList<SoftwareCategory>();
      for (int i = 0; i < selectCategoryIds.length; i++) {
        String categoryId = selectCategoryIds[i];
        SoftwareCategory category = null;
        if (StringUtils.isNotEmpty(categoryId)){
          category = softwareBean.getCategoryByID(Long.parseLong(categoryId));
        }
        selectCategoryList.add(category);
      }
      softwareBean.update(software, selectCategoryList);

      factory.commit();

      request.setAttribute("softwareID", Long.toString(software.getId()));
      return (mapping.findForward("display"));
    } catch (Exception e) {
      factory.rollback();
      throw new DMException("Sava Software Failure!", e);
    }
  }
}
