/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/software/SaveVendorAction.java,v 1.1 2008/02/02 07:48:46 zhaoyang Exp $
 * $Revision: 1.1 $
 * $Date: 2008/02/02 07:48:46 $
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
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.npower.dm.action.BaseDispatchAction;
import com.npower.dm.core.DMException;
import com.npower.dm.core.SoftwareVendor;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SoftwareBean;

/**
 * @author Zhao Yang
 * @version $Revision: 1.1 $ $Date: 2008/02/02 07:48:46 $
 */
public class SaveVendorAction extends BaseDispatchAction {

  /**
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    DynaActionForm vendorForm = (DynaActionForm) form;

    if (isCancelled(request)) {
      return (mapping.findForward("cancel"));
    }

    ManagementBeanFactory factory = getManagementBeanFactory(request);
    SoftwareBean vendorbean = factory.createSoftwareBean();

    try {
      SoftwareVendor vendor = vendorbean.newVendorInstance();
      BeanUtils.populate(vendor, vendorForm.getMap());
      factory.beginTransaction();
      vendorbean.update(vendor);
      factory.commit();

      request.setAttribute("vendor", vendor);
    } catch (DMException e) {
      throw e;
    } catch (Exception e) {
      throw e;
    } finally {
    }
    return (mapping.findForward("success"));
  }

  /**
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    DynaActionForm vendorForm = (DynaActionForm) form;

    if (isCancelled(request)) {
      return (mapping.findForward("cancel"));
    }

    ManagementBeanFactory factory = getManagementBeanFactory(request);
    SoftwareBean vendorbean = factory.createSoftwareBean();

    try {
      String id = (String) vendorForm.get("id");
      SoftwareVendor vendor = vendorbean.getVendorByID(Long.parseLong(id));
      BeanUtils.populate(vendor, vendorForm.getMap());
      factory.beginTransaction();
      vendorbean.update(vendor);
      factory.commit();

      request.setAttribute("vendor", vendor);
    } catch (DMException e) {
      throw e;
    } catch (Exception e) {
      throw e;
    } finally {
    }
    return (mapping.findForward("success"));
  }

}
