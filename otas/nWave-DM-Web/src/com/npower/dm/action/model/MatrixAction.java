/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/model/MatrixAction.java,v 1.3 2007/12/29 07:14:32 zhao Exp $
  * $Revision: 1.3 $
  * $Date: 2007/12/29 07:14:32 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2007 NPower Network Software Ltd.  All rights reserved.
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
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.npower.dm.action.BaseDispatchAction;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.ProfileTemplateBean;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2007/12/29 07:14:32 $
 */
public class MatrixAction extends BaseDispatchAction {
  
  
  /**
   * Show Models matrix.
   * @param mapping
   * @param form
   * @param req
   * @param res
   * @return
   * @throws Exception
   */
  public ActionForward showMatrix(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ModelBean modelBean = factory.createModelBean();

    ProfileTemplateBean templateBean = factory.createProfileTemplateBean();

    List<ProfileCategory> categories = templateBean.findProfileCategories("from ProfileCategoryEntity");
    request.setAttribute("categories", categories);

    List<Manufacturer> mans = modelBean.getAllManufacturers();
    
    List<ModelMatrixItem> result = new ArrayList<ModelMatrixItem>();
    for (Manufacturer manufacturer: mans) {
        //if (result.size() > 30) {
           // Only for debug
        //   break;
        //}
        // Sorting
        Set<Model> models = new TreeSet<Model>();
        models.addAll(manufacturer.getModels());
        
        for (Model model: models) {
            ModelMatrixItem item = ModelMatrixItem.getModelMatrixItem(factory, model);
            result.add(item);
        }
    }
    request.setAttribute("model_matrix_items", result);
    return mapping.findForward("view4matrix");
  }

}
