//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.npower.dm.action.device;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.npower.dm.action.ActionHelper;
import com.npower.dm.action.BaseAction;
import com.npower.dm.action.JobType;
import com.npower.dm.core.Device;
import com.npower.dm.core.ProfileAssignment;
import com.npower.dm.core.ProfileAttribute;
import com.npower.dm.core.ProfileAttributeType;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileAssignmentBean;
import com.npower.dm.management.ProfileConfigBean;

/**
 * MyEclipse Struts Creation date: 06-15-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/device/EditProfileAssignment"
 *                name="DeviceJobProfileAssignmentForm" input="/jsp"
 *                scope="request"
 * @struts.action-forward name="edit"
 *                        path="/jsp/device/job/assignment/profileassignment.jsp"
 *                        contextRelative="true"
 */
public class EditProfileAssignmentAction extends BaseAction {

  // --------------------------------------------------------- Instance
  // Variables

  /**
   * @param mapping
   * @param rawForm
   * @param request
   * @param response
   */
  private void loadProfileAssignment(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
    ProfileAssignmentForm4Save form = (ProfileAssignmentForm4Save) rawForm;
    String assignmentID = (String)form.getValue("ID");
    if (StringUtils.isNotEmpty(assignmentID)) {
       ManagementBeanFactory factory = this.getManagementBeanFactory(request);
       ProfileAssignmentBean bean = factory.createProfileAssignmentBean();
       ProfileAssignment assignment = bean.getProfileAssignmentByID(assignmentID);
       request.setAttribute("profileAssignment", assignment);
    }
  }

  private void loadProfile(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    ProfileAssignmentForm4Save form = (ProfileAssignmentForm4Save) rawForm;

    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ProfileConfigBean profileBean = factory.createProfileConfigBean();
    String profileID = (String)form.getValue("profileID");
    ProfileConfig profile = profileBean.getProfileConfigByID(profileID);

    request.setAttribute("profile", profile);
    
    List<ProfileAttribute> attributes = new ArrayList<ProfileAttribute>();
    for (ProfileAttribute attribute: (Set<ProfileAttribute>)profile.getProfileTemplate().getProfileAttributes()) {
        if (ProfileAttributeType.TYPE_APLINK.equals(attribute.getProfileAttribType().getName())) {
           // APLink will be bypassed
           continue;
        }
        attributes.add(attribute);
    }
    request.setAttribute("attributes", attributes);
  }

  private ActionForward loadDevice(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    ProfileAssignmentForm4Save deviceForm = (ProfileAssignmentForm4Save) rawForm;
    
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    DeviceBean deviceBean = factory.createDeviceBean();
    String deviceID = (String)deviceForm.getValue("deviceID");
    Device device = deviceBean.getDeviceByID(deviceID);

    request.setAttribute("device", device);
    return null;
  }

  // --------------------------------------------------------- Methods

  public ActionForward cancelled(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    ProfileAssignmentForm4Save deviceForm = (ProfileAssignmentForm4Save) rawForm;
    String deviceID = (String)deviceForm.getValue("deviceID");
    if (StringUtils.isNotEmpty(deviceID)) {
       request.setAttribute("deviceID", deviceID);
    }
    return mapping.findForward("cancel");
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
    ProfileAssignmentForm4Save form = (ProfileAssignmentForm4Save) rawForm;
    
    if (this.isCancelled(request)) {
       return this.cancelled(mapping, rawForm, request, response);
    }
    
    String profileID = (String)form.getValue("profileID");
    if (StringUtils.isEmpty(profileID)) {
       ActionMessages messages = new ActionMessages();
       ActionMessage message = new ActionMessage("page.device.job.assignment.profileID.required.message");
       messages.add("invalidate.input", message);
       this.saveMessages(request, messages);
       return (mapping.findForward("inputProfileID"));
    }
    
    // Load ProfileAssignment
    this.loadProfileAssignment(mapping, rawForm, request, response);
    
    // Load ProfileConfig
    this.loadProfile(mapping, rawForm, request, response);
    
    // Load device
    this.loadDevice(mapping, rawForm, request, response);
    
    String assignmentID = (String)form.getValue("ID");
    
    ActionHelper.setJobType(request, getResources(request), this.getLocale(request), JobType.ASSIGN_PROFILE);

    if (StringUtils.isEmpty(assignmentID)) {
       return (mapping.findForward("assign.new.profile"));
    } else {
      return (mapping.findForward("re-assign.profile"));
    }
  }

}
