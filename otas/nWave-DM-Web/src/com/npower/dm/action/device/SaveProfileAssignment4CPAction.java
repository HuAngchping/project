//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.npower.dm.action.device;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.MessageResources;

import com.npower.cp.OTAInventory;
import com.npower.cp.convertor.ValueFetcher;
import com.npower.dm.action.ActionHelper;
import com.npower.dm.action.BaseLookupDispatchAction;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.Device;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.daemon.JobEvent;
import com.npower.dm.daemon.JobEventListener;
import com.npower.dm.daemon.JobEventType;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.OTAClientProvJobBean;
import com.npower.dm.management.ProfileConfigBean;
import com.npower.sms.client.SmsSender;
import com.npower.wap.omacp.OMACPSecurityMethod;

/**
 * MyEclipse Struts Creation date: 06-15-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/device/job/SaveProfileAssignment"
 *                name="DeviceJobProfileAssignmentForm" parameter="action"
 *                scope="request" validate="true"
 * @struts.action-forward name="view" path="/jsp/device/job"
 *                        contextRelative="true"
 * @struts.action-forward name="success" path="/jsp/device/job/success.jsp"
 *                        contextRelative="true"
 */
public class SaveProfileAssignment4CPAction extends BaseLookupDispatchAction {

  public class Parameter {
    private String name;

    private Object value;

    /**
     * 
     */
    public Parameter() {
      super();

    }

    /**
     * @param name
     * @param value
     */
    public Parameter(String name, Object value) {
      super();
      this.name = name;
      this.value = value;
    }

    /**
     * @return the name
     */
    public String getName() {
      return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
      this.name = name;
    }

    /**
     * @return the value
     */
    public Object getValue() {
      return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(Object value) {
      this.value = value;
    }

  }

  // --------------------------------------------------------- Instance
  // Variables

  protected Map getKeyMethodMap() {
    Map<String, String> map = new HashMap<String, String>();
    map.put("page.button.submit.label", "update");
    map.put("page.button.update.label", "schedule");
    map.put("page.button.create.label", "schedule");
    map.put("page.button.previous.label", "edit");
    return (map);
  }

  private Device getDevice(ManagementBeanFactory factory, ActionMapping mapping, ActionForm rawForm,
      HttpServletRequest request, HttpServletResponse response) throws Exception {
    ProfileAssignmentForm4Save form = (ProfileAssignmentForm4Save) rawForm;

    DeviceBean deviceBean = factory.createDeviceBean();
    String deviceID = (String) form.getValue("deviceID");

    Device device = deviceBean.getDeviceByID(deviceID);

    request.getSession().setAttribute("device", device);
    return device;
  }

  // --------------------------------------------------------- Methods

  public ActionForward schedule(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    ProfileAssignmentForm4Save form = (ProfileAssignmentForm4Save) rawForm;
    
    ManagementBeanFactory factory = null;

    try {
      factory = this.getManagementBeanFactory(request);
      
      // Set default value
      OMACPSecurityMethod defaultPinType = OMACPSecurityMethod.USERPIN;
      form.setValue("sendNotification", new Boolean(true));
      // Get default pint type for current device.
      DeviceBean deviceBean = factory.createDeviceBean();
      Device device = deviceBean.getDeviceByID((String) form.getValue("deviceID"));
      String pTypeStr = device.getBootstrapPinType();
      // 如果设备 PinType不存在, 查找Subscriber的PIN Type
      if (StringUtils.isEmpty(pTypeStr)) {
         pTypeStr = device.getSubscriber().getBootstrapPinType();
      }
      // 如果Device和Subscriber的PinType都不存在, 使用Carrier的PinType
      if (StringUtils.isEmpty(pTypeStr)) {
         Carrier carrier = device.getSubscriber().getCarrier();
         pTypeStr = carrier.getDefaultBootstrapPinType();
      }
      // 使用缺省
      if (StringUtils.isNotEmpty(pTypeStr)) {
         // Return default;
        defaultPinType = OMACPSecurityMethod.value(Byte.parseByte(pTypeStr));
      }
      if (defaultPinType == OMACPSecurityMethod.NETWPIN) {
         form.setValue("pin", device.getSubscriber().getIMSI());
      }
      form.setValue("pinType", "" + defaultPinType.getValue());
      
      

      
      ProfileConfigBean profileBean = factory.createProfileConfigBean();
      String profileID = (String) form.getValue("profileID");
      ProfileConfig profile = profileBean.getProfileConfigByID(profileID);
      // Set default job name
      MessageResources messageResources = this.getResources(request);
      String jobName = ActionHelper.getDefaultJobName(messageResources, this.getLocale(request),
          ProvisionJob.JOB_TYPE_ASSIGN_PROFILE, new String[]{profile.getName()});
      form.setValue("name", jobName);
      request.setAttribute("profile", profile);

      // Iterator all of parameters
      Set<String> names = form.getMap().keySet();
      List<Parameter> parameters = new ArrayList<Parameter>();
      for (String paramName : names) {
        if (paramName.startsWith("attribute__")) {
          Object paramValue = form.getValue(paramName);
          if (paramValue == null) {
            continue;
          }
          if (paramValue instanceof FormFile) {
            FormFile file = (FormFile) paramValue;
            File outputFile = File.createTempFile("form_file_", ".bin");
            OutputStream out = new FileOutputStream(outputFile);
            InputStream in = file.getInputStream();
            byte[] buf = new byte[512];
            int len = in.read(buf);
            while (len > 0) {
              out.write(buf, 0, len);
              len = in.read(buf);
            }
            out.close();

            paramValue = outputFile.getAbsolutePath();
          }
          Parameter parameter = new Parameter(paramName, paramValue);
          parameters.add(parameter);
        }
      }
      request.setAttribute("parameters", parameters);

      // Set device into Session
      this.getDevice(factory, mapping, rawForm, request, response);
      return mapping.findForward("schedule");
    } catch (Exception e) {
      throw e;
    } finally {

    }
  }

  public ActionForward edit(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    return mapping.findForward("edit");
  }

  public ActionForward cancelled(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    String deviceID = request.getParameter("deviceID");
    if (StringUtils.isNotEmpty(deviceID)) {
      request.setAttribute("deviceID", deviceID);
    }
    return mapping.findForward("cancel");
  }

  public ActionForward unspecified(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    return null;
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
  public ActionForward update(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    ProfileAssignmentForm4Save form = (ProfileAssignmentForm4Save) rawForm;

    String profileID = (String) form.getValue("profileID");
    String jobName = (String) form.getValue("name");
    String jobDescription = (String) form.getValue("description");
    String pin = form.getString("pin");
    

    String promptType4Beginning = (String) form.getValue("startprompttype");
    String promptText4Beginning = (String) form.getValue("startprompttext");
    String promptType4Finished = (String) form.getValue("endprompttype");
    String promptText4Finished = (String) form.getValue("endprompttext");
    
    Boolean isPrompt4Beginning = new Boolean(false);
    if (StringUtils.isNotEmpty((String) form.getValue("startjob"))) {
      isPrompt4Beginning = new Boolean((String) form.getValue("startjob"));
    }
    
    Boolean isPrompt4Finished = new Boolean(false);
    if (StringUtils.isNotEmpty((String) form.getValue("endjob"))) {
      isPrompt4Finished = new Boolean((String) form.getValue("endjob"));
    }
    
    OMACPSecurityMethod pinType = OMACPSecurityMethod.value(Byte.parseByte(form.getString("pinType")));

    Boolean sendNotification = new Boolean(false);
    if (StringUtils.isNotEmpty((String) form.getValue("sendNotification"))) {
      sendNotification = new Boolean((String) form.getValue("sendNotification"));
    }

    ManagementBeanFactory factory = null;
    try {
      factory = this.getManagementBeanFactory(request);
      DeviceBean deviceBean = factory.createDeviceBean();
      ProfileConfigBean profileBean = factory.createProfileConfigBean();

      String jobScheduleString = (String) form.getValue("jobSchedule");
      Date scheduledTime = new Date();
      if (StringUtils.isNotEmpty(jobScheduleString)) {
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        scheduledTime = formatter.parse(jobScheduleString);
      }
      // Get target Device
      Device device = deviceBean.getDeviceByID((String) form.getValue("deviceID"));
      // Get Profile

      // Submit job
      OTAInventory otaInventory = ActionHelper.getOTAInventory();
      SmsSender smsSender = ActionHelper.getSmsSender(device.getSubscriber().getCarrier());
      OTAClientProvJobBean bean = factory.createOTAClientProvJobBean(otaInventory, smsSender);

      int maxRetry = -1;
      long maxDuration = 0;

      ProfileConfig profile = profileBean.getProfileConfigByID(profileID);

      // No required transaction control
      ValueFetcher<ProfileCategory, String, String> valueFetcher = new ActionFormValueFetcher(profile, form, request);
      ProvisionJob job = bean.provision(device, profile, valueFetcher, pinType, pin, scheduledTime.getTime(),
                                        maxRetry, maxDuration);

      // Update job's name and description
      factory.beginTransaction();
      job.setName(jobName);
      job.setDescription(jobDescription);
      String priority = (String)form.getValue("priority");
      job.setPriority(new Integer(priority));
      job.setPrompt4Beginning(isPrompt4Beginning);
      job.setPromptType4Beginning(promptType4Beginning);
      job.setPromptText4Beginning(promptText4Beginning);
      job.setPrompt4Finished(isPrompt4Finished);
      job.setPromptType4Finished(promptType4Finished);
      job.setPromptText4Finished(promptText4Finished);
      bean.update(job);
      factory.commit();
      
      // Send Event to Job Event Listener
      JobEventListener eventListener = ActionHelper.getJobEventListener();
      eventListener.notify(new JobEvent(JobEventType.Create, job.getID()));

      // Pass parameter into page.
      request.setAttribute("device", device);
      request.setAttribute("provisionJob", job);
      return (mapping.findForward("success"));
    } catch (Exception ex) {
      if (factory != null) {
        factory.rollback();
      }
      throw ex;
    } finally {
    }
  }
}
