/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.npower.dm.action.software;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.npower.dm.action.BaseAction;
import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwareCategory;
import com.npower.dm.core.SoftwareVendor;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SoftwareBean;
import com.npower.dm.msm.JADCreator;


/** 
 * MyEclipse Struts
 * Creation date: 03-02-2009
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class UploadFileAction extends BaseAction {
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
   * @throws IOException 
   */
  public ActionForward execute(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    SoftwareBean softwareBean = factory.createSoftwareBean();
    DynaActionForm form = (DynaActionForm) rawForm;
    FormFile binaryFile = (FormFile)form.get("binaryFile");
    JADCreator creator = new JADCreator();
 
    String filePath = System.getProperty("otas.dm.home") + "/upload/";    
    File file = new File(filePath);
    if (!file.exists()) {
      file.mkdirs();
    }    

    request.getSession().setAttribute("binaryFile", binaryFile);
    binaryFile = (FormFile)request.getSession().getAttribute("binaryFile");
    InputStream stream = binaryFile.getInputStream();
    OutputStream bos = new FileOutputStream(filePath + binaryFile.getFileName());    
    int byteRead = 0;
    while ((byteRead = stream.read()) != -1) {
      bos.write(byteRead);
    }
    bos.close();
    stream.close(); 


    String installInfo = "<InstOpts>\n" +
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
    "</InstOpts>\n";
    request.setAttribute("installInfo", installInfo);
      
    // Auto generate an externalId
    String externalId = "" + (System.currentTimeMillis() & 0xffffff);
    request.setAttribute("externalId", externalId);
    
    List<LabelValueBean> languageOptions = getSoftwarePackageLanguageOptions();
    request.setAttribute("languageOptions", languageOptions);
    String mimeType = "application/java-archive";
    request.setAttribute("mimeType", mimeType);
    String classification = "Java MIDP 2.0 Platform";
    request.setAttribute("classification", classification);
    
    JADCreator.SoftwareInformation info = creator.getSoftwareInformation(new File(filePath + binaryFile.getFileName()));
    
    SoftwareVendor vendor = softwareBean.getVendorByName(info.getVendor());
    if (vendor == null) {
      factory.beginTransaction();
      vendor = softwareBean.newVendorInstance();
      vendor.setName(info.getVendor());
      softwareBean.update(vendor);   
      factory.commit();
    }

    List<SoftwareCategory> categories = softwareBean.getAllOfCategories();
    request.setAttribute("categories", new TreeSet<SoftwareCategory>(categories));    
    request.setAttribute("softwareInfo", info);
    request.setAttribute("file", filePath + binaryFile.getFileName());
    Set<SoftwareCategory> category = new TreeSet<SoftwareCategory>();
    request.setAttribute("category", category);
    return mapping.findForward("viewSoftwareInfo");
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
  
}