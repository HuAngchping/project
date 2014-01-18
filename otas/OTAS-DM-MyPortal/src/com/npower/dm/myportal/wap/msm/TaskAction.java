/**
 * $Header: /home/master/OTAS-DM-MyPortal/src/com/npower/dm/myportal/wap/msm/TaskAction.java,v 1.1 2008/06/16 10:11:24 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/06/16 10:11:24 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2008 NPower Network Software Ltd.  All rights reserved.
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
package com.npower.dm.myportal.wap.msm;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import com.npower.dm.action.PersistentManager;
import com.npower.dm.core.Device;
import com.npower.dm.core.Model;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.msm.SoftwareManagementJobAdapter;
import com.npower.dm.msm.SoftwareManagementJobAdapterImpl;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/06/16 10:11:24 $
 */
public class TaskAction extends Action {

  /** 
   * Request Parameters:
   * 1. imei
   * 2. softwareID
   */
  public ActionForward execute(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
    DynaValidatorForm form = (DynaValidatorForm) rawForm;
    String imei = form.getString("imei");
    String softwareID = form.getString("softwareID");
    
    if (StringUtils.isEmpty(imei)) {
       return mapping.findForward("failure");
    }
    
    ManagementBeanFactory factory = PersistentManager.getManagementBeanFactory(request);
    DeviceBean deviceBean = factory.createDeviceBean();
    Device device = deviceBean.getDeviceByExternalID(imei);
    if (device == null) {
       return mapping.findForward("failure");
    }
    Model model = device.getModel();
    SoftwareManagementJobAdapter msmAdapter = new SoftwareManagementJobAdapterImpl(factory);
    ActionForward forward = new ActionForward();
    if (msmAdapter.isSupported(model)) {
       // Redirect to MSM install method
      ActionForward frozenForward = mapping.findForward("msm.install");
      BeanUtils.copyProperties(forward, frozenForward);
      forward.setRedirect(true);
    } else {
      // Redirect to WAP install method
      ActionForward frozenForward = mapping.findForward("wap.install");
      BeanUtils.copyProperties(forward, frozenForward);
      forward.setRedirect(true);
    }
    appendParameters(softwareID, device, model, forward);
    return forward;
  }

  /**
   * @param softwareID
   * @param device
   * @param model
   * @param forward
   * @throws UnsupportedEncodingException
   */
  private void appendParameters(String softwareID, Device device, Model model, ActionForward forward)
      throws UnsupportedEncodingException {
    String url = forward.getPath();
    StringBuffer params = new StringBuffer(url);
    params.append("?brand=");
    params.append(URLEncoder.encode(model.getManufacturer().getExternalId(), "UTF-8"));
    params.append("&model=");
    params.append(URLEncoder.encode(model.getManufacturerModelId(), "UTF-8"));
    params.append("&carrierID=");
    params.append(URLEncoder.encode(device.getSubscriber().getCarrier().getExternalID(), "UTF-8"));
    params.append("&countryID=");
    params.append(URLEncoder.encode("" + device.getSubscriber().getCarrier().getCountry().getID(), "UTF-8"));
    params.append("&softwareID=");
    params.append(URLEncoder.encode(softwareID, "UTF-8"));
    
    params.append("&value(phoneNumber)=");
    params.append(URLEncoder.encode(device.getSubscriber().getPhoneNumber(), "UTF-8"));
    forward.setPath(params.toString());
  }
}
