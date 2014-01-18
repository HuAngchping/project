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
import org.apache.struts.validator.DynaValidatorForm;

import com.npower.dm.action.BaseAction;
import com.npower.dm.core.Device;
import com.npower.dm.core.Software;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SoftwareBean;
import com.npower.dm.msm.SoftwareManagementJobAdapter;
import com.npower.dm.msm.SoftwareManagementJobAdapterImpl;
import com.npower.dm.msm.SoftwareNodeInfo;

/**
* @author Zhao DongLu
* @version $Revision: 1.2 $ $Date: 2008/11/10 14:53:36 $
*/
public class SoftwaresAction extends BaseAction {
  
  public class SoftwareRecord {
    private Software software = null;
    private String state = null;
    
    public SoftwareRecord() {
      super();
    }
    
    public SoftwareRecord(Software software, String state) {
      super();
      this.software = software;
      this.state = state;
    }

    public Software getSoftware() {
      return software;
    }
    
    public void setSoftware(Software software) {
      this.software = software;
    }
    
    public String getState() {
      return state;
    }
    
    public void setState(String state) {
      this.state = state;
    }
  }

  // --------------------------------------------------------- Instance
  // Variables

  // --------------------------------------------------------- Methods

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
    
    DynaValidatorForm form = (DynaValidatorForm) rawForm;

    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    DeviceBean deviceBean = factory.createDeviceBean();
    Device device = deviceBean.getDeviceByID(form.getString("deviceID"));
    request.setAttribute("device", device);
    
    SoftwareBean softwareBean = factory.createSoftwareBean();
    
    List<SoftwareRecord> softwares = new ArrayList<SoftwareRecord>();
    
    SoftwareManagementJobAdapter adapter = new SoftwareManagementJobAdapterImpl(factory);
    List<SoftwareNodeInfo> infos = adapter.getDeployedSoftwares(device.getExternalId());
    for (SoftwareNodeInfo info: infos) {
        String softwareExtID = info.getSoftwareExternalId();
        if (StringUtils.isNotEmpty(softwareExtID)) {
          Software software = softwareBean.getSoftwareByExternalID(softwareExtID);
          String state = adapter.getDeployedSoftwareState(device.getExternalId(), softwareExtID);
          if (software != null) {
             SoftwareRecord record = new SoftwareRecord(software, state);
             softwares.add(record);
          }
        }
    }
    request.setAttribute("softwareInfos", infos);
    request.setAttribute("deployedSoftwares", softwares);
    return (mapping.findForward("display"));
  }

}
