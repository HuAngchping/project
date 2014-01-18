/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/updates/SaveFirmwareAction.java,v 1.4 2009/02/12 10:22:10 zhaowx Exp $
 * $Revision: 1.4 $
 * $Date: 2009/02/12 10:22:10 $
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

package com.npower.dm.action.updates;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.MessageResources;

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
 * @version $Revision: 1.4 $ $Date: 2009/02/12 10:22:10 $
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/EditModel" name="ModelForm" input="jsp/model.jsp"
 *                scope="request" validate="true"
 * @struts.action-forward name="success" path="/jsp/success.jsp"
 *                        contextRelative="true"
 */
public class SaveFirmwareAction extends BaseAction {

  // --------------------------------------------------------- Instance
  // Variables

  // --------------------------------------------------------- Methods

  // Create mode
  public ActionForward create(ActionMapping mapping, ActionForm rawFrm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    DynaActionForm form = (DynaActionForm) rawFrm;

    String manufacturerID = form.getString("manufacturerID");
    String modelID = form.getString("modelID");
    String version_1_0 = form.getString("fromVersion");
    String version_2_0 = form.getString("toVersion");
    String statusID = form.getString("status");
    String description = form.getString("description");
    FormFile imageFile = (FormFile)form.get("imageFile");
   
    if (StringUtils.isEmpty(manufacturerID)) {
      // Step 1 产生错误信息
     MessageResources resources = this.getResources(request);
     String label = resources.getMessage(this.getLocale(request), "firmware.title.manufacturer");
     ActionMessages messages = new ActionMessages();
     ActionMessage message = new ActionMessage("errors.required", label);
     messages.add("manufacturerID", message);    
     this.saveErrors(request, messages);
          
     // Step 2 返回输入页面
     return mapping.getInputForward();
   }

    if (StringUtils.isEmpty(modelID)) {
     MessageResources resources = this.getResources(request);
     String label = resources.getMessage(this.getLocale(request), "firmware.title.model");
     ActionMessages messages = new ActionMessages();
     ActionMessage message = new ActionMessage("errors.required", label);
     messages.add("modelID", message);    
     this.saveErrors(request, messages);
     return mapping.getInputForward();
   }

    if (StringUtils.isEmpty(version_1_0)) {
      MessageResources resources = this.getResources(request);
      String label = resources.getMessage(this.getLocale(request), "firmware.title.fromVersion");
      ActionMessages messages = new ActionMessages();
      ActionMessage message = new ActionMessage("errors.required", label);
      messages.add("fromVersion", message);    
      this.saveErrors(request, messages);
      return mapping.getInputForward();
    }

    if (StringUtils.isEmpty(version_2_0)) {
      MessageResources resources = this.getResources(request);
      String label = resources.getMessage(this.getLocale(request), "firmware.title.toVersion");
      ActionMessages messages = new ActionMessages();
      ActionMessage message = new ActionMessage("errors.required", label);
      messages.add("toVersion", message);    
      this.saveErrors(request, messages);
      return mapping.getInputForward();
    }
    
    if (StringUtils.isEmpty(statusID)) {
      MessageResources resources = this.getResources(request);
      String label = resources.getMessage(this.getLocale(request), "firmware.title.status");
      ActionMessages messages = new ActionMessages();
      ActionMessage message = new ActionMessage("errors.required", label);
      messages.add("status", message);    
      this.saveErrors(request, messages);
      return mapping.getInputForward();
    }

    if (imageFile != null && StringUtils.isEmpty(imageFile.getFileName())) {
      MessageResources resources = this.getResources(request);
      String label = resources.getMessage(this.getLocale(request), "firmware.title.fotaUpdateForm");
      ActionMessages messages = new ActionMessages();
      ActionMessage message = new ActionMessage("errors.required", label);
      messages.add("FotaUpdateForm", message);    
      this.saveErrors(request, messages);
      return mapping.getInputForward();
    }

    
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
        
        if (model == null) {
           throw new DMException("Manufacturer and Model are required!");
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
        
        Update update = bean.getUpdateByID("" + update_1_to_2.getID());
        request.setAttribute("update", update);
      
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
    
    String manufacturerID = form.getString("manufacturerID");
    String modelID = form.getString("modelID");
    String version_1_0 = form.getString("fromVersion");
    String version_2_0 = form.getString("toVersion");
    String statusID = form.getString("status");
    String description = form.getString("description");
    FormFile imageFile = (FormFile)form.get("imageFile");
    if (StringUtils.isEmpty(version_1_0)) {
       // Step 1 产生错误信息
      MessageResources resources = this.getResources(request);
      String label = resources.getMessage(this.getLocale(request), "device.job.jobType");
      ActionMessages messages = new ActionMessages();
      ActionMessage message = new ActionMessage("errors.required", label);
      messages.add("jobType", message);
      this.saveErrors(request, messages);

      // Step 2 返回输入页面
       return mapping.getInputForward();
    }
    
    
    
    if (StringUtils.isEmpty(manufacturerID)) {
      // Step 1 产生错误信息
     MessageResources resources = this.getResources(request);
     String label = resources.getMessage(this.getLocale(request), "firmware.title.manufacturer");
     ActionMessages messages = new ActionMessages();
     ActionMessage message = new ActionMessage("errors.required", label);
     messages.add("manufacturerID", message);    
     this.saveErrors(request, messages);
     // Step 2 返回输入页面
     return mapping.getInputForward();
   }

    if (StringUtils.isEmpty(modelID)) {
     MessageResources resources = this.getResources(request);
     String label = resources.getMessage(this.getLocale(request), "firmware.title.model");
     ActionMessages messages = new ActionMessages();
     ActionMessage message = new ActionMessage("errors.required", label);
     messages.add("modelID", message);    
     this.saveErrors(request, messages);
     return mapping.getInputForward();
   }

    if (StringUtils.isEmpty(version_1_0)) {
      MessageResources resources = this.getResources(request);
      String label = resources.getMessage(this.getLocale(request), "firmware.title.fromVersion");
      ActionMessages messages = new ActionMessages();
      ActionMessage message = new ActionMessage("errors.required", label);
      messages.add("fromVersion", message);    
      this.saveErrors(request, messages);
      return mapping.getInputForward();
    }

    if (StringUtils.isEmpty(version_2_0)) {
      MessageResources resources = this.getResources(request);
      String label = resources.getMessage(this.getLocale(request), "firmware.title.toVersion");
      ActionMessages messages = new ActionMessages();
      ActionMessage message = new ActionMessage("errors.required", label);
      messages.add("toVersion", message);    
      this.saveErrors(request, messages);
      return mapping.getInputForward();
    }
    
    if (StringUtils.isEmpty(statusID)) {
      MessageResources resources = this.getResources(request);
      String label = resources.getMessage(this.getLocale(request), "firmware.title.status");
      ActionMessages messages = new ActionMessages();
      ActionMessage message = new ActionMessage("errors.required", label);
      messages.add("status", message);    
      this.saveErrors(request, messages);
      return mapping.getInputForward();
    }
    
               
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    try {
        UpdateImageBean bean = factory.createUpdateImageBean();
        ModelBean modelBean = factory.createModelBean();
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
        Model model = modelBean.getModelByID(modelID);
        image_1.setModel(model);
        bean.update(image_1);
        
        Image image_2 = update_1_to_2.getToImage();
        image_2.setVersionId(version_2_0);
        image_2.setModel(model);
        bean.update(image_2);
        
        // Update firmware bytes
        if (in != null) {
          update_1_to_2.setBinary(in);
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
