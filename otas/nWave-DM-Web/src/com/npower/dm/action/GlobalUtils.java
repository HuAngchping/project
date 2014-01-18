/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/GlobalUtils.java,v 1.7 2008/02/24 06:01:07 zhao Exp $
  * $Revision: 1.7 $
  * $Date: 2008/02/24 06:01:07 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2006 NPower Network Software Ltd.  All rights reserved.
  *
  * This SOURCE CODE FILE, which has been provided by NPower as part
  * of a NPower product for use ONLY by licensed users of the product,
  * includes CONFIDENTIAL and PROPRIETARY information of NPower.
  *
  * USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS
  * OF THE LICENSE STATEMENT AND LIMITED WARRANTY FURNISHED WITH
  * THE PRODUCT.
  *
  * IN PARTICULAR, YOU WILL INDEMNIFY AND HOLD NPower, ITS RELATED
  * COMPANIES AND ITS SUPPLIERS, HARMLESS FROM AND AGAINST ANY CLAIMS
  * OR LIABILITIES ARISING OUT OF THE USE, REPRODUCTION, OR DISTRIBUTION
  * OF YOUR PROGRAMS, INCLUDING ANY CLAIMS OR LIABILITIES ARISING OUT OF
  * OR RESULTING FROM THE USE, MODIFICATION, OR DISTRIBUTION OF PROGRAMS
  * OR FILES CREATED FROM, BASED ON, AND/OR DERIVED FROM THIS SOURCE
  * CODE FILE.
  * ===============================================================================================
  */
package com.npower.dm.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.RequestUtils;

import com.npower.dm.core.ClientProvTemplate;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.core.ProvisionJobStatus;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.7 $ $Date: 2008/02/24 06:01:07 $
 */
public class GlobalUtils {

  public static void loadJobModes(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    MessageResources messageResources = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
    Locale locale = RequestUtils.getUserLocale(request, null);
    
    List<LabelValueBean> types = new ArrayList<LabelValueBean>();
    LabelValueBean labelValue = null;
    
    String label = messageResources.getMessage(locale, "meta.job.mode." + ProvisionJob.JOB_MODE_CP + ".label"); 
    labelValue = new LabelValueBean(label, ProvisionJob.JOB_MODE_CP);
    types.add(labelValue);
    
    label = messageResources.getMessage(locale, "meta.job.mode." + ProvisionJob.JOB_MODE_DM + ".label"); 
    labelValue = new LabelValueBean(label, ProvisionJob.JOB_MODE_DM);
    types.add(labelValue);
    
    request.setAttribute("jobModes", types);
  }

  public static void loadJobTypes(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    MessageResources messageResources = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
    Locale locale = RequestUtils.getUserLocale(request, null);
    
    List<LabelValueBean> types = new ArrayList<LabelValueBean>();
    LabelValueBean labelValue = null;
    
    labelValue = new LabelValueBean(ProvisionJob.JOB_TYPE_ASSIGN_PROFILE, ProvisionJob.JOB_TYPE_ASSIGN_PROFILE);
    types.add(labelValue);
    
    labelValue = new LabelValueBean(ProvisionJob.JOB_TYPE_DISCOVERY, ProvisionJob.JOB_TYPE_DISCOVERY);
    types.add(labelValue);
    
    labelValue = new LabelValueBean(ProvisionJob.JOB_TYPE_SCRIPT, ProvisionJob.JOB_TYPE_SCRIPT);
    types.add(labelValue);
    
    labelValue = new LabelValueBean(ProvisionJob.JOB_TYPE_RE_ASSIGN_PROFILE, ProvisionJob.JOB_TYPE_RE_ASSIGN_PROFILE);
    types.add(labelValue);
    
    labelValue = new LabelValueBean(ProvisionJob.JOB_TYPE_FIRMWARE, ProvisionJob.JOB_TYPE_FIRMWARE);
    types.add(labelValue);
    
    labelValue = new LabelValueBean(ProvisionJob.JOB_TYPE_WORKFLOW, ProvisionJob.JOB_TYPE_WORKFLOW);
    types.add(labelValue);
  
    request.setAttribute("jobTypes", types);
  }

  public static void loadJobStates(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    MessageResources messageResources = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
    Locale locale = RequestUtils.getUserLocale(request, null);
    
    List<LabelValueBean> types = new ArrayList<LabelValueBean>();
    LabelValueBean labelValue = null;
    
    labelValue = new LabelValueBean("Applied", ProvisionJob.JOB_STATE_APPLIED);
    types.add(labelValue);
    
    labelValue = new LabelValueBean("Finished", ProvisionJob.JOB_STATE_FINISHED);
    types.add(labelValue);
    
    labelValue = new LabelValueBean("Cancelled", ProvisionJob.JOB_STATE_CANCELLED);
    types.add(labelValue);
    
    labelValue = new LabelValueBean("Disable", ProvisionJob.JOB_STATE_DISABLE);
    types.add(labelValue);
    
    request.setAttribute("jobStates", types);
  }

  public static void loadDeviceJobStates(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    MessageResources messageResources = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
    Locale locale = RequestUtils.getUserLocale(request, null);
    
    List<LabelValueBean> types = new ArrayList<LabelValueBean>();
    LabelValueBean labelValue = null;
    
    labelValue = new LabelValueBean("Pending", ProvisionJobStatus.DEVICE_JOB_STATE_READY);
    types.add(labelValue);
    
    labelValue = new LabelValueBean("Doing", ProvisionJobStatus.DEVICE_JOB_STATE_DOING);
    types.add(labelValue);
    
    labelValue = new LabelValueBean("Waiting", ProvisionJobStatus.DEVICE_JOB_STATE_WAITING_CLIENT_INITIALIZED_SESSION);
    types.add(labelValue);
    
    labelValue = new LabelValueBean("Done", ProvisionJobStatus.DEVICE_JOB_STATE_DONE);
    types.add(labelValue);
    
    labelValue = new LabelValueBean("Error", ProvisionJobStatus.DEVICE_JOB_STATE_ERROR);
    types.add(labelValue);
    
    request.setAttribute("deviceJobStates", types);
  }

  /**
   * Return List<LabelValueBean> to hold the encoders of CPTemplate
   * @param resources
   * @param locale
   * @return
   * @throws Exception
   */
  public static List<LabelValueBean> loadCPtemplateEncoders(MessageResources resources, Locale locale) throws Exception {
    
    List<LabelValueBean> result = new ArrayList<LabelValueBean>();
    String v1 = resources.getMessage(locale, "meta.cptemplate.encoder.omacp.label");
    LabelValueBean lv1 = new LabelValueBean(v1, ClientProvTemplate.OMA_CP_1_1_ENCODER);
    result.add(lv1);
    
    String v2 = resources.getMessage(locale, "meta.cptemplate.encoder.nokia_ota.label");
    LabelValueBean lv2 = new LabelValueBean(v2, ClientProvTemplate.NOKIA_OTA_7_0_ENCODER);
    result.add(lv2);
    
    return result;
  }
}
