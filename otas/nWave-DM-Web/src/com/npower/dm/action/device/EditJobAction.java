//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.npower.dm.action.device;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.DynaValidatorForm;

import com.npower.dm.action.ActionHelper;
import com.npower.dm.action.BaseDispatchAction;
import com.npower.dm.action.JobType;
import com.npower.dm.action.JobTypeDecorator;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.Software;
import com.npower.dm.core.Update;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SoftwareBean;
import com.npower.dm.management.UpdateImageBean;
/** 
 * MyEclipse Struts
 * Creation date: 06-14-2006
 * 
 * XDoclet definition:
 * @struts.action path="/device/EditJob" name="DeviceJobTypeForm" scope="request"
 * @struts.action-forward name="jobTypeScript" path="/jsp/device/job/script.jsp" contextRelative="true"
 */
public class EditJobAction extends BaseDispatchAction {

  // --------------------------------------------------------- Instance
  // Variables

  public static final String JOB_TYPE_FOR_ASSIGN_PROFILE = "JOB_TYPE_FOR_ASSIGN_PROFILE";

  /**
   * Return 
   * @param mapping
   * @param rawForm
   * @param request
   * @param response
   * @throws Exception
   */
  private void loadJobTypes4Device(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    MessageResources messageResources = getResources(request);
    request.setAttribute("jobTypes", JobTypeDecorator.getJobTypeOptions4Device(messageResources, this.getLocale(request)));
  }

  /**
   * ��ȡ��ǰ��Device
   * @param factory
   * @param mapping
   * @param rawForm
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public static Device getDevice(ManagementBeanFactory factory, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    DynaValidatorForm deviceForm = (DynaValidatorForm) rawForm;
    
    DeviceBean deviceBean = factory.createDeviceBean();
    Device device = deviceBean.getDeviceByID((String) deviceForm.get("deviceID"));

    request.getSession().setAttribute("device", device);
    return device;
  }
  
  /**
   * @param request
   * @param factory
   * @param deviceBean
   * @param device
   * @throws DMException
   */
  public static void setFirmwares4View(HttpServletRequest request, ManagementBeanFactory factory, DeviceBean deviceBean, Device device) throws DMException {
    String currentVersionId = deviceBean.getCurrentFirmwareVersionId(device.getID());
    currentVersionId = (currentVersionId == null)?"":currentVersionId;
    List<Update> allUpdates = new ArrayList<Update>();
    if (StringUtils.isNotEmpty(currentVersionId)) {
       UpdateImageBean updateBean = factory.createUpdateImageBean();
       List<Update> upgrades = updateBean.findUpdates4Upgrade(device.getModel(), currentVersionId);
       List<Update> downgrades = updateBean.findUpdates4Downgrade(device.getModel(), currentVersionId);
       request.setAttribute("upgrades", upgrades);
       request.setAttribute("downgrades", downgrades);
       allUpdates.addAll(upgrades);
       allUpdates.addAll(downgrades);
    }
    
    request.setAttribute("allUpdates", allUpdates);
    if (allUpdates.size() > 0) {
       request.setAttribute("defaultUpdateID", "" + ((Update)allUpdates.get(0)).getID());
    }
    request.setAttribute("currentFirmwareVersionID", currentVersionId);
  }  
  
  /**
   * Search and list availliable softwares
   * @param request
   * @param factory
   * @param deviceBean
   * @param device
   */
  public static void setSoftwares4Installation(HttpServletRequest request, ManagementBeanFactory factory,
      DeviceBean deviceBean, Device device) throws DMException {
    SoftwareBean softwareBean = factory.createSoftwareBean();
    List<Software> softwares = softwareBean.getAllOfSoftwares();
    request.setAttribute("softwares", softwares);
  }

  // --------------------------------------------------------- Methods
  /**
   * δָ��Job���Ͳ���, ת��ѡ����������ҳ��
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward unspecified(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    
    // Load the job types for select box
    loadJobTypes4Device(mapping, rawForm, request, response);

    // Load the device
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    getDevice(factory, mapping, rawForm, request, response);

    return (mapping.findForward("SelectJobType"));
  }

  /* (non-Javadoc)
   * @see org.apache.struts.actions.DispatchAction#cancelled(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  public ActionForward cancelled(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    /*
    ActionForward forward = mapping.findForward("cancel");
    String path = forward.getPath();
    path += "?action=update&ID=" + request.getParameter("deviceID");
    forward = new ActionForward();
    forward.setPath(path);
    return forward;
    */
    String deviceID = request.getParameter("deviceID");
    if (StringUtils.isNotEmpty(deviceID)) {
       request.setAttribute("deviceID", deviceID);
    }
    // ���ء��鿴�豸��ҳ��
    return mapping.findForward("cancel");
  }  
  
  /**
   * ת�롰�ɼ��ն���Ϣ��ҳ�棬������Ҫ�ɼ����ն���Ϣ
   * @param mapping
   * @param rawForm
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward doDiscovery(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    
    // Load the device
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    getDevice(factory, mapping, rawForm, request, response);

    // Set job type for jsp view
    ActionHelper.setJobType(request, getResources(request), this.getLocale(request), JobType.DISCOVERY);

    return (mapping.findForward("EditDiscoveryNode"));
  }

  /**
   * ת�롰����ű���ҳ�棬��������ű�
   * @param mapping
   * @param rawForm
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward doScript(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    
    // Load the device
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    getDevice(factory, mapping, rawForm, request, response);

    // Set job type for jsp view
    ActionHelper.setJobType(request, getResources(request), this.getLocale(request), JobType.SCRIPT);

    return (mapping.findForward("EditScript"));
  }  

  /**
   * ת�������·�ҳ��
   * @param mapping
   * @param rawForm
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward doAssignProfile4DM(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    // Set job type for jsp view
    ActionHelper.setJobType(request, getResources(request), this.getLocale(request), JobType.ASSIGN_PROFILE);
    return (mapping.findForward("WizardAssignProfile4DM"));
  }  
  
  /**
   * ת�������·�ҳ��
   * @param mapping
   * @param rawForm
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward doAssignProfile4CP(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    // Set job type for jsp view
    ActionHelper.setJobType(request, getResources(request), this.getLocale(request), JobType.SEND_PROFILE);
    return (mapping.findForward("WizardAssignProfile4CP"));
  }  
  
  /**
   * ת�������·�ҳ��
   * @param mapping
   * @param rawForm
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward doReAssignProfile(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    return (mapping.findForward("cancel"));
  }  
  
  /**
   * ת��̼�������������ҳ��
   * @param mapping
   * @param rawForm
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward doFirmware(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    
    // Set job type for jsp view
    ActionHelper.setJobType(request, getResources(request), this.getLocale(request), JobType.FIRMWARE);

    // Load the device
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    DeviceBean deviceBean = factory.createDeviceBean();
    Device device = getDevice(factory, mapping, rawForm, request, response);
    
    setFirmwares4View(request, factory, deviceBean, device);
    return (mapping.findForward("firmwares"));
  }

  /**
   * ת�������װҳ��
   * @param mapping
   * @param rawForm
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward doSoftwareInstall(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    
    // Set job type for jsp view
    ActionHelper.setJobType(request, getResources(request), this.getLocale(request), JobType.SOFTWARE_INSTALL);

    // Load the device
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
//    DeviceBean deviceBean = factory.createDeviceBean();
    getDevice(factory, mapping, rawForm, request, response);
    
 //   setSoftwares4Installation(request, factory, deviceBean, device);
    return (mapping.findForward("selectSoftwares4Install"));
  }

  /**
   * ת�������װ����ɼ�ҳ��
   * @param mapping
   * @param rawForm
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward doSoftwareDiscovery(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    // Set job type for jsp view
    ActionHelper.setJobType(request, getResources(request), this.getLocale(request), JobType.SOFTWARE_DISCOVERY);

    return (mapping.findForward("software.discovery"));
  }

  /**
   * ת�����ж��ҳ��
   * @param mapping
   * @param rawForm
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward doSoftwareUnInstall(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    // Set job type for jsp view
    ActionHelper.setJobType(request, getResources(request), this.getLocale(request), JobType.SOFTWARE_UN_INSTALL);

    // Load the device
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    DeviceBean deviceBean = factory.createDeviceBean();
    Device device = getDevice(factory, mapping, rawForm, request, response);
    
    setSoftwares4Installation(request, factory, deviceBean, device);
    return (mapping.findForward("selectSoftwares4Uninstall"));
  }

  /**
   * ת�뼤�����ҳ�� 
   * @param mapping
   * @param rawForm
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward doSoftwareActive(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    // Set job type for jsp view
    ActionHelper.setJobType(request, getResources(request), this.getLocale(request), JobType.SOFTWARE_ACTIVE);

    // Load the device
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    DeviceBean deviceBean = factory.createDeviceBean();
    Device device = getDevice(factory, mapping, rawForm, request, response);
    
    setSoftwares4Installation(request, factory, deviceBean, device);
    return (mapping.findForward("selectSoftwares4Active"));
  }

  /**
   * ת���������ҳ��
   * @param mapping
   * @param rawForm
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward doSoftwareDeactive(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    // Set job type for jsp view
    ActionHelper.setJobType(request, getResources(request), this.getLocale(request), JobType.SOFTWARE_DEACTIVE);

    // Load the device
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    DeviceBean deviceBean = factory.createDeviceBean();
    Device device = getDevice(factory, mapping, rawForm, request, response);
    
    setSoftwares4Installation(request, factory, deviceBean, device);
    return (mapping.findForward("selectSoftwares4Deactive"));
  }

  /**
   * ת���������ҳ��
   * @param mapping
   * @param rawForm
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward doSoftwareUpgrade(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    // Set job type for jsp view
    ActionHelper.setJobType(request, getResources(request), this.getLocale(request), JobType.SOFTWARE_UPGRADE);

    // Load the device
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    DeviceBean deviceBean = factory.createDeviceBean();
    Device device = getDevice(factory, mapping, rawForm, request, response);
    
    setSoftwares4Installation(request, factory, deviceBean, device);
    return (mapping.findForward("selectSoftwares4Upgrade"));
  }

  /**
   * ת�빤��������ҳ��
   * @param mapping
   * @param rawForm
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  /*
  public ActionForward doWorkflow(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    // Set job type for jsp view
    ActionHelper.setJobType(request, getResources(request), this.getLocale(request), JobType.WORKFLOW);
    
    return this.unspecified(mapping, rawForm, request, response);
  } 
  */ 
}

