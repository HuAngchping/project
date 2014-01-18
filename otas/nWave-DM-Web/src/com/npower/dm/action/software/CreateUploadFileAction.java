/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.npower.dm.action.software;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.npower.dm.action.BaseAction;
import com.npower.dm.core.ModelClassification;
import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwareCategory;
import com.npower.dm.core.SoftwarePackage;
import com.npower.dm.core.SoftwareVendor;
import com.npower.dm.hibernate.entity.DMBinary;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelClassificationBean;
import com.npower.dm.management.SoftwareBean;

/** 
 * MyEclipse Struts
 * Creation date: 03-05-2009
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class CreateUploadFileAction extends BaseAction {
  /*
   * Generated Methods
   */

  /** 
   * Method execute
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward execute(ActionMapping mapping, ActionForm rawform, HttpServletRequest request,
      HttpServletResponse response) throws Exception{

    DynaActionForm form = (DynaActionForm)rawform;
    
    String vendor = form.getString("vendor");
    String name = form.getString("name");
    String version = form.getString("version");
    String id = form.getString("externalId");
    String[] selectCategoryIds = form.getStrings("categoryID");
    String status = form.getString("status");
    String licenseType = form.getString("licenseType");
    String description = form.getString("description");
    String language = form.getString("language");
    String mimeType = form.getString("mimeType");
    String classification = form.getString("classification");
    String installationOptions = form.getString("installationOptions");
    String filename = request.getParameter("file"); 
    
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);  
    factory.beginTransaction();
    SoftwareBean softwareBean = factory.createSoftwareBean();
    Software software = softwareBean.getSoftware(vendor, name, version);
    if (software == null) {
      software = softwareBean.newSoftwareInstance();
      SoftwareVendor svendor = softwareBean.getVendorByName(vendor);  
      
      software.setVendor(svendor);
      software.setName(name);
      software.setExternalId(id);
      software.setVersion(version);
      software.setStatus(status);
      software.setLicenseType(licenseType);
      software.setDescription(description);
    }
    softwareBean.update(software);
    
    List<SoftwareCategory> categoryList = new ArrayList<SoftwareCategory>();
    for (int i = 0; i< selectCategoryIds.length; i++) {
      String categoryId = selectCategoryIds[i];
      SoftwareCategory category = null;
      if (StringUtils.isNotEmpty(categoryId)) {
        category = softwareBean.getCategoryByID(Long.parseLong(categoryId));
      }
      categoryList.add(category);
    }
    softwareBean.update(software, categoryList);  
    
    request.setAttribute("software", software);
    
    factory.commit();
    
    
    factory.beginTransaction();
    ModelClassificationBean mcBean = factory.createModelClassificationBean();
    Software softwarea = softwareBean.getSoftwareByID(software.getId());
    SoftwarePackage pkg = softwareBean.newPackageInstance();
    // ��ȡ���ϴ����ļ��������ļ���������
    File file = new File(filename);
    FileInputStream in = new FileInputStream(file);
    String[] names = filename.split("/");
    String jarname = names[names.length - 1];
    if (in.available() > 0) {
      DMBinary binary = new DMBinary(in);
      binary.setFilename(jarname);
      binary.setMimeType(mimeType);
      pkg.setBinary(binary);
      pkg.setBlobFilename(jarname);
    }
    pkg.setMimeType(mimeType);
    pkg.setLanguage(language);
    pkg.setInstallationOptions(installationOptions);
    pkg.setStatus(softwarea.getStatus());
    pkg.setName(softwarea.getName());
    pkg.setDescription(softwarea.getDescription());
    pkg.setSoftware(softwarea);
    
    pkg.setUrl(null);
    Set<ModelClassification> targetClasses = new HashSet<ModelClassification>();
    ModelClassification mc = mcBean.getModelClassificationByExtID(classification);
    if (mc != null) {
      targetClasses.add(mc);
    }
    softwareBean.update(pkg);
    softwareBean.reassign(targetClasses, null, null, pkg);
    factory.commit();
    return mapping.findForward("success");
  }
}