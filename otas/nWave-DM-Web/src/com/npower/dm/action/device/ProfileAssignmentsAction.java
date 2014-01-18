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
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.npower.dm.action.BaseAction;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.ProvisionJobStatus;
import com.npower.dm.hibernate.entity.ProfileAssignmentEntity;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SearchBean;

/**
 * MyEclipse Struts Creation date: 06-16-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/device/ProfileAssignments"
 *                name="DeviceSearchAssignmentsForm" scope="request"
 * @struts.action-forward name="display" path="tile.device.profileassignments"
 *                        contextRelative="true"
 */
public class ProfileAssignmentsAction extends BaseAction {

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
    
    // Load profile assigngments for this device.
    this.loadProfileAssignments(mapping, rawForm, request, response);
    
    return (mapping.findForward("display"));
  }

  /**
   * @param mapping
   * @param rawForm
   * @param request
   * @param response
   */
  private void loadProfileAssignments(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) throws DMException {
    DynaValidatorForm form = (DynaValidatorForm) rawForm;

    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    DeviceBean deviceBean = factory.createDeviceBean();
    Device device = deviceBean.getDeviceByID(form.getString("deviceID"));
    String status = ProvisionJobStatus.DEVICE_JOB_STATE_DONE;
    /*
    ProfileAssignmentBean assignBean = factory.createProfileAssignmentBean();
    String status = null;
    List result = assignBean.findProfileAssignmentsByStatus(device, status);
    */
    SearchBean searchBean = factory.createSearchBean();
    Criteria mainCrt = searchBean.newCriteriaInstance(ProfileAssignmentEntity.class);
    if (status != null) {
       Criteria deviceStateCrt = mainCrt.createCriteria("jobAssignmentses").createCriteria("jobState").createCriteria("deviceProvisionRequests");
       deviceStateCrt.add(Expression.eq("device", device));
       deviceStateCrt.add(Expression.eq("state", ProvisionJobStatus.DEVICE_JOB_STATE_DONE));
    }
    mainCrt.add(Expression.eq("device", device));
    mainCrt.addOrder(Order.asc("assignmentIndex"));
    Set result = new TreeSet(mainCrt.list());
    request.setAttribute("profileAssignments", result);
    request.setAttribute("device", device);
  }

}
