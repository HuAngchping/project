/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/software/EditPackageAction.java,v 1.10 2008/09/10 10:02:20 zhao Exp $
 * $Revision: 1.10 $
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

import java.util.ArrayList;
import java.util.Collection;
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
import org.apache.struts.util.LabelValueBean;

import com.npower.dm.action.BaseAction;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.ModelClassification;
import com.npower.dm.core.ModelFamily;
import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwarePackage;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.ModelClassificationBean;
import com.npower.dm.management.SoftwareBean;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.10 $ $Date: 2008/09/10 10:02:20 $
 */

public class EditPackageAction extends BaseAction {
  
  private Collection<LabelValueBean> getManufacturerOptions(HttpServletRequest request) throws DMException {
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ModelBean modelBean = factory.createModelBean();
    List<Manufacturer> list = new ArrayList<Manufacturer>(new TreeSet<Manufacturer>(modelBean.getAllManufacturers()));
    Collection<LabelValueBean> result = new ArrayList<LabelValueBean>();
    for (Manufacturer manufacturer: list) {
        LabelValueBean labelValue = new LabelValueBean(manufacturer.getExternalId(), "" + manufacturer.getID());
        result.add(labelValue);
    }
    return result;
  }
  
  private Collection<LabelValueBean> getModelOptions(HttpServletRequest request, DynaActionForm form, SoftwarePackage pkg) throws DMException {
    Collection<LabelValueBean> result = new ArrayList<LabelValueBean>();
    
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ModelBean modelBean = factory.createModelBean();
    String manufacturerID = (String)form.get("manufacturerID");
    Manufacturer manufacturer = null;
    if (manufacturerID != null && manufacturerID.trim().length() > 0) {
       manufacturer = modelBean.getManufacturerByID(manufacturerID);
    }
    
    if (manufacturer == null) {
       return result;
    }
    Set<Model> set = new TreeSet<Model>(manufacturer.getModels());
    for (Model model: set) {
        LabelValueBean labelValue = new LabelValueBean(model.getManufacturerModelId(), "" + model.getID());
        result.add(labelValue);
    }
    return result;
  }
  
  
  public ActionForward execute(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    
    if (this.isCancelled(request)) {
      return (mapping.findForward("display"));
    }
    DynaActionForm form = (DynaActionForm) rawForm;
    String packageID = form.getString("packageID");
    String softwareID = form.getString("softwareID");
    
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ModelBean modelBean = factory.createModelBean();
    SoftwareBean softwareBean = factory.createSoftwareBean();
    
    if (StringUtils.isNotEmpty(softwareID)) {
       Software software = softwareBean.getSoftwareByID(Long.parseLong(softwareID));
       request.setAttribute("software", software);
    }
    // Set default mode: local
    if (StringUtils.isEmpty(form.getString("storeMode"))) {
      form.set("storeMode", "local");
    }
    SoftwarePackage pkg = null;
    List<LabelValueBean> targetModelOptions = new ArrayList<LabelValueBean>();
    List<LabelValueBean> targetModelClassificationOptions = new ArrayList<LabelValueBean>();
    List<LabelValueBean> targetModelFamilyOptions = new ArrayList<LabelValueBean>();
    if (StringUtils.isNotEmpty(packageID)) {
      // Update mode
      pkg = softwareBean.getPackageByID(Long.valueOf(packageID));
      form.getMap().putAll(BeanUtils.describe(pkg));
      if (StringUtils.isNotEmpty(pkg.getUrl())) {
        form.set("storeMode", "remote");
      }
      
      Set<Model> targetModels = pkg.getModels();
      String[] targetModelIDs = new String[targetModels.size()];
      for (Model model: targetModels) {
         LabelValueBean lv = new LabelValueBean();
         lv.setLabel(model.getManufacturer().getExternalId() + "-" + model.getManufacturerModelId());
         lv.setValue("" + model.getID());
         targetModelOptions.add(lv);
      }
      form.set("targetModelIDs", targetModelIDs);
      
      Collection<ModelClassification> targetModelClassifications = pkg.getModelClassifications();
      Long[] targetModelClassificationIDs = new Long[targetModelClassifications.size()];
      for (ModelClassification mc: targetModelClassifications) {
         LabelValueBean lv = new LabelValueBean();
         lv.setLabel(mc.getName());
         lv.setValue(Long.toString(mc.getId()));
         targetModelClassificationOptions.add(lv);
      }
      form.set("targetModelClassificationIDs", targetModelClassificationIDs);
      
      List<String> targetModelFamilyIDs = new ArrayList<String>(pkg.getModelFamilyExternalIDs());
      for (String familyExtID: targetModelFamilyIDs) {
            ModelFamily family = modelBean.getModelFamilyByExtID(familyExtID);
            LabelValueBean lv = new LabelValueBean();
            lv.setLabel(family.getName());
            lv.setValue(familyExtID);
            targetModelFamilyOptions.add(lv);
      }
      form.set("targetModelFamilyIDs", targetModelFamilyIDs.toArray(new String[0]));
    } else {
      // Create mode
      form.set("installationOptions", "<InstOpts>\n" +
          "  <StdOpt name=\"drive\" value=\"!\"/>\n" +
          "  <StdOpt name=\"lang\" value=\"*\" />\n" +
          "  <StdOpt name=\"upgrade\" value=\"yes\"/>\n" +
          "  <StdOpt name=\"kill\" value=\"yes\"/>\n" +
          "  <StdSymOpt name=\"pkginfo\" value=\"yes\"/>\n" +
          "  <StdSymOpt name=\"optionals\" value=\"yes\"/>\n" +
          "  <StdSymOpt name=\"ocsp\" value=\"no\"/>\n" +
          "  <StdSymOpt name=\"capabilities\" value=\"yes\"/>\n" +
          "  <StdSymOpt name=\"untrusted\" value=\"yes\"/>\n" +
          "  <StdSymOpt name=\"ignoreocspwarn\" value=\"yes\"/>\n" +
          "  <StdSymOpt name=\"ignorewarn\" value=\"yes\"/>\n" +
          "  <StdSymOpt name=\"fileoverwrite\" value=\"yes\"/>\n" +
          "</InstOpts>\n");
    }
    
    request.setAttribute("targetModelOptions", targetModelOptions);
    request.setAttribute("targetModelClassificationOptions", targetModelClassificationOptions);
    request.setAttribute("targetModelFamilyOptions", targetModelFamilyOptions);
    
    List<LabelValueBean> mimeTypeOptions = getSoftwarePackageMimeTypeOptions();
    request.setAttribute("mimeTypeOptions", mimeTypeOptions);
    
    List<LabelValueBean> languageOptions = getSoftwarePackageLanguageOptions();
    request.setAttribute("languageOptions", languageOptions);
    
    request.setAttribute("modelFamilyOptions", getModelFamilyOptions(modelBean));
    request.setAttribute("modelClassificationOptions", this.getModelClassificationOptions(factory.createModelClassificationBean()));
    
    request.setAttribute("manufacturerOptions", this.getManufacturerOptions(request));
    request.setAttribute("modelOptions", this.getModelOptions(request, form, pkg));

    return (mapping.findForward("edit"));
  }

  /**
   * @return
   */
  private List<LabelValueBean> getSoftwarePackageMimeTypeOptions() {
    List<LabelValueBean> mimeTypeOptions = new ArrayList<LabelValueBean>();
    mimeTypeOptions.add(new LabelValueBean("x-epoc/x-sisx-app", "x-epoc/x-sisx-app"));
    mimeTypeOptions.add(new LabelValueBean("application/vnd.symbian.install", "application/vnd.symbian.install"));
    mimeTypeOptions.add(new LabelValueBean("application/x-pip", "application/x-pip"));
    mimeTypeOptions.add(new LabelValueBean("text/vnd.sun.j2me.app-descriptor", "text/vnd.sun.j2me.app-descriptor"));
    mimeTypeOptions.add(new LabelValueBean("application/java-archive", "application/java-archive"));
    mimeTypeOptions.add(new LabelValueBean("application/java", "application/java"));
    mimeTypeOptions.add(new LabelValueBean("application/x-java-archive", "application/x-java-archive"));
    return mimeTypeOptions;
  }

  /**
   * @return
   */
  private List<LabelValueBean> getSoftwarePackageLanguageOptions() {
    List<LabelValueBean> languageOptions = new ArrayList<LabelValueBean>();
    languageOptions.add(new LabelValueBean("Simplified Chinese", "zh_CN"));
    languageOptions.add(new LabelValueBean("Traditioanl Chinese", "zh_TW"));
    languageOptions.add(new LabelValueBean("All", "all"));
    return languageOptions;
  }

  /**
   * @param modelBean
   * @return
   * @throws DMException
   */
  private List<LabelValueBean> getModelFamilyOptions(ModelBean modelBean) throws DMException {
    List<LabelValueBean> modelFamilyOptions = new ArrayList<LabelValueBean>();
    List<ModelFamily> families = modelBean.getAllModelFamily();
    for (ModelFamily family: families) {
      if (family == null) {
         continue;
      }
      String externalID = family.getExternalID();
      if (StringUtils.isNotEmpty(externalID)) {
         LabelValueBean lv = new LabelValueBean();
         lv.setLabel(family.getName());
         lv.setValue(externalID);
         modelFamilyOptions.add(lv);
      }
    }
    return modelFamilyOptions;
  }

  /**
   * @param modelBean
   * @return
   * @throws DMException
   */
  private List<LabelValueBean> getModelClassificationOptions(ModelClassificationBean bean) throws DMException {
    List<LabelValueBean> options = new ArrayList<LabelValueBean>();
    for (ModelClassification mc: bean.getAllOfModelClassifications()) {
      if (mc != null) {
          LabelValueBean lv = new LabelValueBean();
          lv.setLabel(mc.getName());
          lv.setValue(Long.toString(mc.getId()));
          options.add(lv);
      }
    }
    return options;
  }
}
