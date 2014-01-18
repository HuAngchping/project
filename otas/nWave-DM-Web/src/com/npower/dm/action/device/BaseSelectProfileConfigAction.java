package com.npower.dm.action.device;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import com.npower.dm.action.BaseAction;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.Device;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileTemplate;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileTemplateBean;

public abstract class BaseSelectProfileConfigAction extends BaseAction {

  public BaseSelectProfileConfigAction() {
    super();
  }

  private void getDevice(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
    ProfileAssignmentForm4Save deviceForm = (ProfileAssignmentForm4Save) rawForm;
    
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    DeviceBean deviceBean = factory.createDeviceBean();
    Device device = deviceBean.getDeviceByID((String)deviceForm.getValue("deviceID"));
  
    request.setAttribute("device", device);
  }

  private void loadCategories(ActionForm rawForm, HttpServletRequest request) throws Exception {
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ProfileTemplateBean bean = factory.createProfileTemplateBean();
    List<ProfileCategory> categories = bean.findProfileCategories("from ProfileCategoryEntity");
    List<LabelValueBean> result = new ArrayList<LabelValueBean>();
    for (ProfileCategory category: categories) {
        LabelValueBean labelValue = new LabelValueBean(category.getName(), "" + category.getID());
        result.add(labelValue);
    }
    request.setAttribute("categoryOptions", result);
  }

  private void loadProfileTemplates(ActionForm rawForm, HttpServletRequest request) throws Exception {
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ProfileTemplateBean bean = factory.createProfileTemplateBean();
    List<ProfileTemplate> templates = bean.findTemplates("from ProfileTemplateEntity");
    List<LabelValueBean> result = new ArrayList<LabelValueBean>();
    for (ProfileTemplate template: templates) {
        LabelValueBean labelValue = new LabelValueBean(template.getName(), "" + template.getID());
        result.add(labelValue);
    }
    request.setAttribute("templateOptions", result);
  }
  
  private void loadCarriers(ActionForm rawForm, HttpServletRequest request) throws Exception {
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    CarrierBean carrierBean = factory.createCarrierBean();
    List<Carrier> carriers = carrierBean.findCarriers("from CarrierEntity");
    List<LabelValueBean> result = new ArrayList<LabelValueBean>();
    for (Carrier carrier: carriers) {
        LabelValueBean labelValue = new LabelValueBean(carrier.getName(), "" + carrier.getID());
        result.add(labelValue);
    }
    request.setAttribute("carrierOptions", result);

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
  public ActionForward execute(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    ProfileAssignmentForm4Save form = (ProfileAssignmentForm4Save) rawForm;
  
    // Load profile list
    this.loadProfiles(mapping, rawForm, request, response);
    
    // Load the device
    this.getDevice(mapping, rawForm, request, response);
    
    this.loadCategories(rawForm, request);
    this.loadProfileTemplates(rawForm, request);
    this.loadCarriers(rawForm, request);
    
    // Set options for Records per page options.
    int recordsPerPage = BaseAction.getRecordsPerPage(request);
    form.setValue("recordsPerPage", recordsPerPage);
    BaseAction.setRecordsPerPageOptions(request, form);    
  
    return (mapping.findForward("display"));
  }
  
  protected abstract ActionForward loadProfiles(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception;

}