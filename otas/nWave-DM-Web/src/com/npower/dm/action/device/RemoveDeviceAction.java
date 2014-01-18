/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/device/RemoveDeviceAction.java,v 1.4 2008/03/12 05:14:14 zhao Exp $
  * $Revision: 1.4 $
  * $Date: 2008/03/12 05:14:14 $
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
package com.npower.dm.action.device;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import com.npower.dm.action.BaseDispatchAction;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2008/03/12 05:14:14 $
 */
public class RemoveDeviceAction extends BaseDispatchAction {

  /**
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @throws DMException
   */
  public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                            HttpServletResponse response) throws DMException {
    // Was this transaction cancelled?
    if (isCancelled(request)) {
      return (mapping.findForward("cancel"));
    }

    DynaValidatorForm deviceForm = (DynaValidatorForm) form;
    String deviceID = (String) deviceForm.get("ID");

    ManagementBeanFactory factory = getManagementBeanFactory(request);
    try {
      DeviceBean deviceBean = factory.createDeviceBean();
      //SubscriberBean subscriberBean = factory.createSubcriberBean();
      Device device = deviceBean.getDeviceByID(deviceID);
      factory.beginTransaction();
      deviceBean.delete(device);
      //subscriberBean.delete(device.getSubscriber());
      factory.commit();
      
      return (mapping.findForward("success"));
    } catch (Exception e) {
      factory.rollback();
      throw new DMException("Failure in delete device: " + deviceID);
    }
  }

}
