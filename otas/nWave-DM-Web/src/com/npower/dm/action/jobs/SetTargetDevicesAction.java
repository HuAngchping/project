//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.npower.dm.action.jobs;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.validator.DynaValidatorForm;

import com.npower.dm.action.ActionHelper;
import com.npower.dm.action.BaseAction;
import com.npower.dm.action.common.CarrierIDTargetDeviceCaculator;
import com.npower.dm.action.common.IMEITargetDevicesCaculator;
import com.npower.dm.action.common.ManufacturerIDTargetDeviceCaculator;
import com.npower.dm.action.common.ModelIDTargetDeviceCaculator;
import com.npower.dm.action.common.MultipleTargetDevicesCaculator;
import com.npower.dm.action.common.PhoneNumberRuleTargetDeviceCaculator;
import com.npower.dm.action.common.TargetDevicesCaculator;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.displaytag.PaginatedListAdapter;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.PaginatedResult;
import com.npower.dm.util.DisplayTagHelper;

/** 
 * MyEclipse Struts
 * Creation date: 06-14-2006
 * 
 * XDoclet definition:
 * @struts.action path="/device/EditJob" name="DeviceJobTypeForm" scope="request"
 * @struts.action-forward name="jobTypeScript" path="/jsp/device/job/script.jsp" contextRelative="true"
 */
public class SetTargetDevicesAction extends BaseAction {

  // --------------------------------------------------------- Instance

  // --------------------------------------------------------- Methods
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
  
  private Collection<LabelValueBean> getModelOptions(HttpServletRequest request, DynaActionForm form) throws DMException {
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
  
  public static List<Long> loadTargetDevices(ManagementBeanFactory factory, HttpServletRequest request, ActionForm rawForm) throws Exception {
    TargetDevicesCaculator caculator = createTargetDevicesCaculator(factory, rawForm);
    
    // Caculating target deviceIDs
    int pageNumber = DisplayTagHelper.getPageNumber(request);
    int recordsPerPage = getRecordsPerPage(request);
    PaginatedResult targetDevices = caculator.getPaginatedDevices(pageNumber, recordsPerPage);
    List<Long> deviceIDs = new ArrayList<Long>();
    //deviceIDs.addAll(caculator.getDeviceIDs());
    
    HttpSession session = request.getSession(false);
    session.setAttribute("targetDeviceIDs", deviceIDs);
    session.setAttribute("targetDevices",  new PaginatedListAdapter(targetDevices));
    return deviceIDs;
  }

  /**
   * @param factory
   * @param rawForm
   * @return
   * @throws FileNotFoundException
   * @throws IOException
   */
  public static TargetDevicesCaculator createTargetDevicesCaculator(ManagementBeanFactory factory, ActionForm rawForm) throws FileNotFoundException, IOException {
    DynaValidatorForm form = (DynaValidatorForm)rawForm;
    
    MultipleTargetDevicesCaculator caculator = new MultipleTargetDevicesCaculator(factory);
    caculator.addCaculator(new IMEITargetDevicesCaculator(factory, form.getString("deviceExternalIDs")));
    caculator.addCaculator(new PhoneNumberRuleTargetDeviceCaculator(factory, form.getString("phoneNumbers")));
    caculator.addCaculator(new ModelIDTargetDeviceCaculator(factory, (String[])form.get("modelIDs")));
    caculator.addCaculator(new ManufacturerIDTargetDeviceCaculator(factory, (String[])form.get("manufacturerIDs")));
    caculator.addCaculator(new CarrierIDTargetDeviceCaculator(factory, (String[])form.get("carrierIDs")));
    
    FormFile msisdnFile = (FormFile)form.get("phoneNumbersFile");
    if (msisdnFile != null) {
       byte[] bs = msisdnFile.getFileData();
       if (bs != null && bs.length > 0) {
          caculator.addCaculator(new PhoneNumberRuleTargetDeviceCaculator(factory, new String(bs)));
       }
    }
    
    FormFile imeiFile = (FormFile)form.get("deviceExternalIDsFile");
    if (imeiFile != null) {
       byte[] bs = imeiFile.getFileData();
       if (bs != null && bs.length > 0) {
          caculator.addCaculator(new IMEITargetDevicesCaculator(factory, new String(bs)));
       }
    }
    return caculator;
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
  public ActionForward execute(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    DynaActionForm form = (DynaActionForm) rawForm;
    
    request.setAttribute("manufacturerOptions", this.getManufacturerOptions(request));
    request.setAttribute("modelOptions", this.getModelOptions(request, form));
    request.setAttribute("carrierOptions", ActionHelper.getCarrierOptions(this.getManagementBeanFactory(request)));
    return (mapping.findForward("view"));
  }

  public ActionForward cancelled(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    return (mapping.findForward("cancel"));
  }

}

