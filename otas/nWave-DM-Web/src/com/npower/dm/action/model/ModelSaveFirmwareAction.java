/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/model/ModelSaveFirmwareAction.java,v 1.4 2007/02/09 09:38:32 zhao Exp $
 * $Revision: 1.4 $
 * $Date: 2007/02/09 09:38:32 $
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

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;

import com.npower.dm.action.BaseAction;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Image;
import com.npower.dm.core.ImageUpdateStatus;
import com.npower.dm.core.Model;
import com.npower.dm.core.Update;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.UpdateImageBean;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2007/02/09 09:38:32 $
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/EditModel" name="ModelForm" input="jsp/model.jsp"
 *                scope="request" validate="true"
 * @struts.action-forward name="success" path="/jsp/success.jsp"
 *                        contextRelative="true"
 */
public class ModelSaveFirmwareAction extends BaseAction {

  // --------------------------------------------------------- Instance
  // Variables

  // --------------------------------------------------------- Methods

  // Create mode
  public ActionForward create(ActionMapping mapping, ActionForm rawFrm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    DynaActionForm form = (DynaActionForm) rawFrm;

    String modelID = form.getString("modelID");
    String version_1_0 = form.getString("fromVersion");
    String version_2_0 = form.getString("toVersion");
    String statusID = form.getString("status");
    String description = form.getString("description");
    FormFile imageFile = (FormFile)form.get("imageFile");
    
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    try {
        ModelBean modelBean = factory.createModelBean();
        UpdateImageBean bean = factory.createUpdateImageBean();
  
        Model model = modelBean.getModelByID(modelID);
  
        InputStream in = null;
        if (imageFile != null && StringUtils.isNotEmpty(imageFile.getFileName())) {
           byte[] data = imageFile.getFileData();
           if (data.length > 0) {
              in = imageFile.getInputStream();
           }
        }
  
        // Begin transaction
        factory.beginTransaction();
        
        Update update_1_to_2 = null; 
        if (in == null) {
           throw new DMException("Image file is required!");
        }
        Image image_1 = bean.getImageByVersionID(model, version_1_0);
        if (image_1 == null) {
           image_1 = bean.newImageInstance(model, version_1_0, true, version_1_0);
        }
        Image image_2 = bean.getImageByVersionID(model, version_2_0);
        if (image_2 == null) {
           image_2 = bean.newImageInstance(model, version_2_0, true, version_2_0);
        }
        // Image will be saved by newUpdateInstance().
        update_1_to_2 = bean.newUpdateInstance(image_1, image_2, in);
  
        update_1_to_2.setDescription(description);
  
        ImageUpdateStatus status = bean.getImageUpdateStatus(Long.parseLong(statusID));
        update_1_to_2.setStatus(status);
        bean.update(update_1_to_2);
  
        // Commit
        factory.commit();
        
        request.setAttribute("update", update_1_to_2);
      
        return (mapping.findForward("success"));
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
    }

  }

  // Update mode
  public ActionForward update(ActionMapping mapping, ActionForm rawFrm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    DynaActionForm form = (DynaActionForm) rawFrm;

    String updateID = form.getString("updateID");
    if (StringUtils.isEmpty(updateID)) {
       throw new NullPointerException("Missing updateID!");
    }
    
    String version_1_0 = form.getString("fromVersion");
    String version_2_0 = form.getString("toVersion");
    String statusID = form.getString("status");
    String description = form.getString("description");
    FormFile imageFile = (FormFile)form.get("imageFile");
    
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    try {
        UpdateImageBean bean = factory.createUpdateImageBean();
  
        InputStream in = null;
        if (imageFile != null && StringUtils.isNotEmpty(imageFile.getFileName())) {
           byte[] data = imageFile.getFileData();
           if (data.length > 0) {
              in = imageFile.getInputStream();
           }
        }
  
        // Begin transaction
        factory.beginTransaction();
        
        Update update_1_to_2 = null; 
        update_1_to_2 = bean.getUpdateByID(updateID);
        if (update_1_to_2 == null) {
           throw new DMException("Could not found update: " + updateID + " to modifiy.");
        }
        Image image_1 = update_1_to_2.getFromImage();
        image_1.setVersionId(version_1_0);
        bean.update(image_1);
       
        Image image_2 = update_1_to_2.getToImage();
        image_2.setVersionId(version_2_0);
        bean.update(image_2);
       
        // Update firmware bytes
        if (in != null) {
           //update_1_to_2.setInputStream(in);
        }
  
        update_1_to_2.setDescription(description);
  
        ImageUpdateStatus status = bean.getImageUpdateStatus(Long.parseLong(statusID));
        update_1_to_2.setStatus(status);
        bean.update(update_1_to_2);
  
        // Commit
        factory.commit();
        
        request.setAttribute("update", update_1_to_2);
      
        return (mapping.findForward("success"));
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
    }

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
  public ActionForward execute(ActionMapping mapping, ActionForm rawFrm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    // Was this transaction cancelled?
    if (isCancelled(request)) {
      return (mapping.findForward("cancel"));
    }

    DynaActionForm form = (DynaActionForm) rawFrm;

    String updateID = form.getString("updateID");
    
    try {
        if (StringUtils.isEmpty(updateID)) {
           return this.create(mapping, rawFrm, request, response);
        } else {
          return this.update(mapping, rawFrm, request, response);
        }
    } catch (DMException e) {
      throw e;
    } catch (Exception e) {
      throw e;
    } finally {
    }
  }

}
