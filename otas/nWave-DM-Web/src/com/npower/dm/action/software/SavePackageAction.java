/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/software/SavePackageAction.java,v 1.11 2008/09/10 10:02:20 zhao Exp $
 * $Revision: 1.11 $
 * $Date: 2008/09/10 10:02:20 $
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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.RequestUtils;

import com.npower.dm.action.BaseLookupDispatchAction;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Model;
import com.npower.dm.core.ModelClassification;
import com.npower.dm.core.ModelFamily;
import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwarePackage;
import com.npower.dm.hibernate.entity.DMBinary;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.ModelClassificationBean;
import com.npower.dm.management.SoftwareBean;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.11 $ $Date: 2008/09/10 10:02:20 $
 */
public class SavePackageAction extends BaseLookupDispatchAction {

  @Override
  protected Map getKeyMethodMap() {
    Map<String, String> map = new HashMap<String, String>();
    map.put("page.button.create.label", "create");
    map.put("page.button.update.label", "update");
    return(map);
  }

  /**
   * @param mapping
   * @param request
   * @param modelID
   */
  private ActionForward validate(ActionMapping mapping, HttpServletRequest request,ActionForm rawForm) throws Exception {
    DynaActionForm form = (DynaActionForm) rawForm;
    ManagementBeanFactory factory = getManagementBeanFactory(request);
    ModelBean modelBean = factory.createModelBean();

    String packageID = form.getString("packageID");
    boolean isUpdate = true;
    if (StringUtils.isEmpty(packageID)) {
       isUpdate = false;
    }
    
    String name = form.getString("name");
    String mimeType = form.getString("mimeType");
    String language = form.getString("language");
    FormFile binaryFile = (FormFile)form.get("binaryFile");
    String url = form.getString("url");
    String[] targetModelIDs = (String[])form.get("targetModelIDs");
    String[] targetModelFamilyIDs = (String[])form.get("targetModelFamilyIDs");
    Long[] targetModelClassificationIDs = (Long[])form.get("targetModelClassificationIDs");
    
    boolean remoteMode = true;
    String s = form.getString("storeMode");
    if (s.equalsIgnoreCase("local")) {
       remoteMode = false;
    }
    
    List<LabelValueBean> targetModelFamilyOptions = new ArrayList<LabelValueBean>();
    for (String familyExtID: targetModelFamilyIDs) {
          ModelFamily family = modelBean.getModelFamilyByExtID(familyExtID);
          LabelValueBean lv = new LabelValueBean();
          lv.setLabel(family.getName());
          lv.setValue(familyExtID);
          targetModelFamilyOptions.add(lv);
    }
    request.setAttribute("targetModelFamilyOptions", targetModelFamilyOptions);
    
    
    // Validate Form input
    MessageResources messages = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
    Locale locale = RequestUtils.getUserLocale(request, null);
    if ((targetModelIDs == null || targetModelIDs.length == 0) 
        && (targetModelFamilyIDs == null || targetModelFamilyIDs.length == 0)
        && (targetModelClassificationIDs == null || targetModelClassificationIDs.length == 0)) {
       ActionMessages errors = new ActionMessages();
       errors.add("targetModelIDs", new ActionMessage("page.software.package.edit.validate.missing.targetModel.message"));
       saveErrors(request, errors);
       return mapping.getInputForward();
    }
    if (StringUtils.isEmpty(name)) {
       ActionMessages errors = new ActionMessages();
       String fieldLabel = messages.getMessage(locale, "entity.software.software.name.label");
       errors.add("name", new ActionMessage("errors.required", fieldLabel));
       saveErrors(request, errors);
       return mapping.getInputForward();
    }
    
    if (StringUtils.isEmpty(language)) {
       ActionMessages errors = new ActionMessages();
       String fieldLabel = messages.getMessage(locale, "entity.software.package.language.label");
       errors.add("language", new ActionMessage("errors.required", fieldLabel));
       saveErrors(request, errors);
       return mapping.getInputForward();
    }
    
    if (remoteMode) {
       if (StringUtils.isEmpty(url)) {
          ActionMessages errors = new ActionMessages();
          String fieldLabel = messages.getMessage(locale, "entity.software.package.url.label");
          errors.add("url", new ActionMessage("errors.required", fieldLabel));
          saveErrors(request, errors);
          return mapping.getInputForward();
       }
    } else {
      if (StringUtils.isEmpty(mimeType)) {
         ActionMessages errors = new ActionMessages();
         String fieldLabel = messages.getMessage(locale, "entity.software.package.mimeType.label");
         errors.add("mimeType", new ActionMessage("errors.required", fieldLabel));
         saveErrors(request, errors);
         return mapping.getInputForward();
      }
      if (!isUpdate && (binaryFile == null || binaryFile.getFileSize() == 0)) {
         ActionMessages errors = new ActionMessages();
         String fieldLabel = messages.getMessage(locale, "entity.software.package.upload.file.label");
         errors.add("binaryFile", new ActionMessage("errors.required", fieldLabel));
         saveErrors(request, errors);
         return mapping.getInputForward();
      }
    }
    
    return null;
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
  public ActionForward create(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    // Validate Form input
    //MessageResources messages = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
    //Locale locale = RequestUtils.getUserLocale(request, null);

    DynaActionForm form = (DynaActionForm) rawForm;

    if (isCancelled(request)) {
      return (mapping.findForward("cancel"));
    }

    ActionForward forward = validate(mapping, request, rawForm);
    if (forward != null) {
       return forward;
    }

    ManagementBeanFactory factory = getManagementBeanFactory(request);
    SoftwareBean bean = factory.createSoftwareBean();
    ModelBean modelBean = factory.createModelBean();
    ModelClassificationBean mcBean = factory.createModelClassificationBean();

    try {
      String[] targetModelIDs = (String[])form.get("targetModelIDs");
      String[] targetModelFamilyIDs = (String[])form.get("targetModelFamilyIDs");
      Long[] targetModelClassificationIDs = (Long[])form.get("targetModelClassificationIDs");
      String softwareID = form.getString("softwareID");
      FormFile binaryFile = (FormFile)form.get("binaryFile");
      String url = form.getString("url");
      String mimeType = form.getString("mimeType");
      String language = form.getString("language");
      String installationOptions = form.getString("installationOptions");
      String name = form.getString("name");
      String description = form.getString("description");

      Set<Model> targetModels = new HashSet<Model>();
      for (int i = 0; targetModelIDs != null && i < targetModelIDs.length; i++) {
          Model model = modelBean.getModelByID(targetModelIDs[i]);
          if (model != null) {
             targetModels.add(model);
          }
      }
      
      Set<ModelFamily> targetModelFamily = new HashSet<ModelFamily>();
      for (int i = 0; targetModelFamilyIDs != null && i < targetModelFamilyIDs.length; i++) {
          ModelFamily family = modelBean.getModelFamilyByExtID(targetModelFamilyIDs[i]);
          if (family != null) {
            targetModelFamily.add(family);
          }
      }
      
      Set<ModelClassification> targetClasses = new HashSet<ModelClassification>();
      for (int i = 0; targetModelClassificationIDs != null && i < targetModelClassificationIDs.length; i++) {
          ModelClassification mc = mcBean.getModelClassificationByID(targetModelClassificationIDs[i]);
          if (mc != null) {
            targetClasses.add(mc);
          }
      }
      
      if (targetModelFamily.isEmpty() && targetModels.isEmpty() && targetClasses.isEmpty()) {
        ActionMessages errors = new ActionMessages();
        errors.add("targetModelIDs", new ActionMessage("page.software.package.edit.validate.missing.targetModel.message"));
        saveErrors(request, errors);
        return mapping.getInputForward();
      }
      
      Software software = bean.getSoftwareByID(Long.parseLong(softwareID));
      SoftwarePackage pkg = bean.newPackageInstance();
      BeanUtils.populate(pkg, form.getMap());
      
      factory.beginTransaction();
      pkg.setSoftware(software);
      pkg.setUrl(url);
      if (binaryFile != null) {
         InputStream ins = binaryFile.getInputStream();
         if (ins.available() > 0) {
            DMBinary binary = new DMBinary(ins);
            binary.setFilename(binaryFile.getFileName());
            binary.setMimeType(mimeType);
            pkg.setBinary(binary);
            pkg.setBlobFilename(binaryFile.getFileName());
         }
      }
      pkg.setMimeType(mimeType);
      pkg.setLanguage(language);
      pkg.setInstallationOptions(installationOptions);
      pkg.setStatus(Software.STATUS_RELEASE);
      pkg.setName(name);
      pkg.setDescription(description);
      bean.update(pkg);
      
      bean.reassign(targetClasses, targetModels, targetModelFamily, pkg);
      factory.commit();

      request.setAttribute("packageID", "" + pkg.getId());
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
  public ActionForward update(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    // Validate Form input
    //MessageResources messages = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
    //Locale locale = RequestUtils.getUserLocale(request, null);

    DynaActionForm form = (DynaActionForm) rawForm;

    if (isCancelled(request)) {
      return (mapping.findForward("cancel"));
    }

    boolean remoteMode = true;
    String s = form.getString("storeMode");
    if (s.equalsIgnoreCase("local")) {
       remoteMode = false;
    }

    String packageID = form.getString("packageID");
    String mimeType = form.getString("mimeType");
    String language = form.getString("language");
    String installationOptions = form.getString("installationOptions");
    String name = form.getString("name");
    String description = form.getString("description");
    FormFile binaryFile = (FormFile)form.get("binaryFile");
    String url = form.getString("url");
    String[] targetModelIDs = (String[])form.get("targetModelIDs");
    String[] targetModelFamilyIDs = (String[])form.get("targetModelFamilyIDs");
    Long[] targetModelClassificationIDs = (Long[])form.get("targetModelClassificationIDs");
    
    ActionForward forward = validate(mapping, request, rawForm);
    if (forward != null) {
       return forward;
    }

    ManagementBeanFactory factory = getManagementBeanFactory(request);
    SoftwareBean bean = factory.createSoftwareBean();
    ModelBean modelBean = factory.createModelBean();
    ModelClassificationBean mcBean = factory.createModelClassificationBean();

    try {
      Set<ModelFamily> targetModelFamily = new HashSet<ModelFamily>();
      for (int i = 0; targetModelFamilyIDs != null && i < targetModelFamilyIDs.length; i++) {
          ModelFamily family = modelBean.getModelFamilyByExtID(targetModelFamilyIDs[i]);
          if (family != null) {
            targetModelFamily.add(family);
          }
      }

      Set<Model> targetModels = new HashSet<Model>();
      for (int i = 0; targetModelIDs != null && i < targetModelIDs.length; i++) {
          Model model = modelBean.getModelByID(targetModelIDs[i]);
          if (model != null) {
             targetModels.add(model);
          }
      }
            
      Set<ModelClassification> targetClasses = new HashSet<ModelClassification>();
      for (int i = 0; targetModelClassificationIDs != null && i < targetModelClassificationIDs.length; i++) {
          ModelClassification mc = mcBean.getModelClassificationByID(targetModelClassificationIDs[i]);
          if (mc != null) {
            targetClasses.add(mc);
          }
      }
      
      if (targetModelFamily.isEmpty() && targetModels.isEmpty() && targetClasses.isEmpty()) {
        ActionMessages errors = new ActionMessages();
        errors.add("targetModelIDs", new ActionMessage("page.software.package.edit.validate.missing.targetModel.message"));
        saveErrors(request, errors);
        return mapping.getInputForward();
      }
      
      SoftwarePackage pkg = bean.getPackageByID(Long.parseLong(packageID));
      factory.beginTransaction();
      if (remoteMode) {
         pkg.setUrl(url);
      } else {
        pkg.setUrl(null);
      }
      
      if (binaryFile != null) {
         InputStream ins = binaryFile.getInputStream();
         if (ins.available() > 0) {
            // Local mode
            DMBinary binary = new DMBinary(ins);
            binary.setFilename(binaryFile.getFileName());
            binary.setMimeType(mimeType);
            pkg.setBinary(binary);
            pkg.setBlobFilename(binaryFile.getFileName());
         }
      }
      pkg.setMimeType(mimeType);
      pkg.setLanguage(language);
      pkg.setInstallationOptions(installationOptions);
      pkg.setStatus(Software.STATUS_RELEASE);
      pkg.setName(name);
      pkg.setDescription(description);
      bean.update(pkg);
      
      bean.reassign(targetClasses, targetModels, targetModelFamily, pkg);
      factory.commit();

    } catch (DMException e) {
      throw e;
    } catch (Exception e) {
      throw e;
    } finally {
    }
    return (mapping.findForward("success"));
  }

  public ActionForward unspecified(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    DynaActionForm form = (DynaActionForm) rawForm;
    String softwareID = form.getString("softwareID");
    if (isCancelled(request)) {
      request.setAttribute("softwareID", softwareID);
      return (mapping.findForward("cancel"));
    }
    return null;
  }  

}
