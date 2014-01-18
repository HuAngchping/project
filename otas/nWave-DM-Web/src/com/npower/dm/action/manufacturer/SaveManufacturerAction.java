/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/manufacturer/SaveManufacturerAction.java,v 1.3 2007/02/08 07:12:58 zhao Exp $
 * $Revision: 1.3 $
 * $Date: 2007/02/08 07:12:58 $
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

package com.npower.dm.action.manufacturer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.npower.dm.action.BaseDispatchAction;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2007/02/08 07:12:58 $
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/EditManufacturer" name="ManufacturerForm" input="jsp/manufacturer.jsp"
 *                scope="request" validate="true"
 * @struts.action-forward name="success" path="/jsp/success.jsp"
 *                        contextRelative="true"
 */
public class SaveManufacturerAction extends BaseDispatchAction {

  // --------------------------------------------------------- Instance
  // Variables

  // --------------------------------------------------------- Methods

  /**
   * Method execute
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

    DynaActionForm manufacturerForm = (DynaActionForm) form;

    // Was this transaction cancelled?
    if (isCancelled(request)) {
      return (mapping.findForward("cancel"));
    }

    ManagementBeanFactory factory = getManagementBeanFactory(request);
    ModelBean modelBean = factory.createModelBean();
    try {
      Manufacturer manufacturer = modelBean.newManufacturerInstance();
      BeanUtils.populate(manufacturer, manufacturerForm.getMap());
      factory.beginTransaction();
      modelBean.update(manufacturer);
      factory.commit();
      
      // Pass target to AOP Audit
      request.setAttribute("manufacturer", manufacturer);
    } catch (DMException e) {
      throw e;
    } catch (Exception e) {
      throw e;
    } finally {
    }
    return (mapping.findForward("success"));
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
  public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

    DynaActionForm manufacturerForm = (DynaActionForm) form;

    // Was this transaction cancelled?
    if (isCancelled(request)) {
      return (mapping.findForward("cancel"));
    }

    ManagementBeanFactory factory = getManagementBeanFactory(request);
    ModelBean modelBean = factory.createModelBean();
    try {
      String id = (String) manufacturerForm.get("ID");
      Manufacturer manufacturer = modelBean.getManufacturerByID(id);
      // Copy form Data into POJO
      BeanUtils.populate(manufacturer, manufacturerForm.getMap());
      factory.beginTransaction();
      modelBean.update(manufacturer);
      factory.commit();
      
      // Pass target to AOP Audit
      request.setAttribute("manufacturer", manufacturer);
    } catch (DMException e) {
      throw e;
    } catch (Exception e) {
      throw e;
    } finally {
    }
    return (mapping.findForward("success"));
  }
}
