/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/model/SaveModelAction.java,v 1.10 2007/03/08 10:17:45 zhao Exp $
 * $Revision: 1.10 $
 * $Date: 2007/03/08 10:17:45 $
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

package com.npower.dm.action.model;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.10 $ $Date: 2007/03/08 10:17:45 $
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/EditModel" name="ModelForm" input="jsp/model.jsp"
 *                scope="request" validate="true"
 * @struts.action-forward name="success" path="/jsp/success.jsp"
 *                        contextRelative="true"
 */
public class SaveModelAction extends BaseDispatchAction {

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

    DynaActionForm modelForm = (DynaActionForm) form;

    // Was this transaction cancelled?
    if (isCancelled(request)) {
      return (mapping.findForward("cancel"));
    }

    ManagementBeanFactory factory = getManagementBeanFactory(request);
    ModelBean modelBean = factory.createModelBean();
    try {
      // Get tac list
      List<String> tacList = new ArrayList<String>();
      String tacs = modelForm.getString("taclist");
      tacs += ",";
      if (StringUtils.isNotEmpty(tacs)) {
         StringTokenizer tokenizer = new StringTokenizer(tacs, ",");
         while (tokenizer.hasMoreTokens()) {
               String tac = tokenizer.nextToken();
               if (StringUtils.isNotEmpty(tac) && tac.length() >= 8) {
                  tacList.add(tac.trim());
               }
         }
      }

      Model model = modelBean.newModelInstance();
      
      Manufacturer manufacturer = modelBean.getManufacturerByID(String.valueOf(modelForm.get("manufacturerID")));
      model.setManufacturer(manufacturer);
      
      BeanUtils.populate(model, modelForm.getMap());
      factory.beginTransaction();
      modelBean.update(model);
      modelBean.setTACInfos(model, tacList);
      factory.commit();
      
      // Pass target to AOP Audit
      request.setAttribute("model", model);
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

    DynaActionForm modelForm = (DynaActionForm) form;

    // Was this transaction cancelled?
    if (isCancelled(request)) {
      return (mapping.findForward("cancel"));
    }

    ManagementBeanFactory factory = getManagementBeanFactory(request);
    ModelBean modelBean = factory.createModelBean();
    try {
      // Get tac list
      List<String> tacList = new ArrayList<String>();
      String tacs = modelForm.getString("taclist");
      tacs += ",";
      if (StringUtils.isNotEmpty(tacs)) {
         StringTokenizer tokenizer = new StringTokenizer(tacs, ",");
         while (tokenizer.hasMoreTokens()) {
               String tac = tokenizer.nextToken();
               if (StringUtils.isNotEmpty(tac) && tac.length() >= 8) {
                  tacList.add(tac.trim());
               }
         }
      }

      String id = (String) modelForm.get("ID");
      Model model = modelBean.getModelByID(id);     
      
      Manufacturer manufacturer = modelBean.getManufacturerByID(String.valueOf(modelForm.get("manufacturerID")));
      model.setManufacturer(manufacturer);

      // Copy form Data into POJO
      BeanUtils.populate(model, modelForm.getMap());
      
      factory.beginTransaction();
      modelBean.update(model);
      modelBean.setTACInfos(model, tacList);
      factory.commit();
      
      // Pass target to AOP Audit
      request.setAttribute("model", model);
    } catch (DMException e) {
      throw e;
    } catch (Exception e) {
      throw e;
    } finally {
    }
    return (mapping.findForward("success"));
  }
}
