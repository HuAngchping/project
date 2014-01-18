//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.npower.dm.action.device;

import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import com.npower.dm.action.BaseAction;
import com.npower.dm.core.Device;
import com.npower.dm.core.ProvisionJobStatus;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;

/**
 * MyEclipse Struts Creation date: 06-15-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/device/Jobs" name="SearchDeviceJobsForm"
 *                scope="request"
 * @struts.action-forward name="display" path="/jsp/device/jobs.jsp"
 *                        contextRelative="true"
 */
public class HistoryAction extends BaseAction {

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
    
    Set<ProvisionJobStatus> allOfStatus = device.getProvisionJobStatus();
    Set<ProvisionJobStatus> histories = new TreeSet<ProvisionJobStatus>();
    for (ProvisionJobStatus jobStatus: allOfStatus) {
        if (jobStatus.getState() == null
            || jobStatus.getState().equals(ProvisionJobStatus.DEVICE_JOB_STATE_CANCELLED)
            || jobStatus.getState().equals(ProvisionJobStatus.DEVICE_JOB_STATE_READY)
            || jobStatus.getState().equals(ProvisionJobStatus.DEVICE_JOB_STATE_NOTIFIED)) {
           continue;
        }
        histories.add(jobStatus);
    }
    request.setAttribute("histories", histories);
    request.setAttribute("device", device);
    return (mapping.findForward("display"));
  }

}
