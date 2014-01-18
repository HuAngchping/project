/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/subscriber/EditSubscriberAction.java,v 1.5 2008/06/16 10:11:22 zhao Exp $
 * $Revision: 1.5 $
 * $Date: 2008/06/16 10:11:22 $
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

package com.npower.dm.action.subscriber;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.validator.DynaValidatorForm;

import com.npower.dm.action.ActionHelper;
import com.npower.dm.action.BaseDispatchAction;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Subscriber;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SubscriberBean;

/**
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/EditCarrier" name="CarrierForm" scope="request"
 * @struts.action-forward name="success" path="/jsp/carrier.jsp"
 *                        contextRelative="true"
 */
public class EditSubscriberAction extends BaseDispatchAction {

  // --------------------------------------------------------- Instance
  // Variables

  // --------------------------------------------------------- Methods

  private Subscriber getSubscriber(HttpServletRequest request, String id) throws DMException {
    ManagementBeanFactory factory = getManagementBeanFactory(request);
    SubscriberBean subscriberBean = factory.createSubcriberBean();
    Subscriber subscriber = subscriberBean.getSubscriberByID(id);
    return subscriber;
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
  public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    DynaActionForm carrierForm = (DynaActionForm) form;
    try {
        Subscriber subscriber = this.getSubscriber(request, getSubscriberID(request, carrierForm));
        if (subscriber != null) {
           carrierForm.getMap().putAll(BeanUtils.describe(subscriber));
           carrierForm.set("carrierID", subscriber.getCarrier().getID() + "");
           if (subscriber.getServiceProvider() != null) {
              carrierForm.set("serviceProviderID", "" + subscriber.getServiceProvider().getID());
           }
        }
        request.setAttribute("serviceProviderOptions", ActionHelper.getServiceProviderOptions(this.getManagementBeanFactory(request)));
        request.setAttribute("carrierOptions", ActionHelper.getCarrierOptions(this.getManagementBeanFactory(request)));
        return (mapping.findForward("edit"));
    } catch (DMException e) {
      throw e;
    }
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
  public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
     return this.update(mapping, form, request, response);
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
  public ActionForward view(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
    DynaValidatorForm form = (DynaValidatorForm) rawForm;
    try {
        Subscriber subscriber = this.getSubscriber(request, getSubscriberID(request, form));
        request.setAttribute("subscriber", subscriber);
        request.setAttribute("devices", subscriber.getDevices());
        
        // For Search Panel
        SubscribersAction.setAttributes4SearchPanel(this.getManagementBeanFactory(request), request, form);
        
        return (mapping.findForward("view"));
    } catch (DMException e) {
      throw e;
    }
  }

  /**
   * @param form
   * @return
   */
  private String getSubscriberID(HttpServletRequest request, DynaActionForm form) {
    String id = (String) form.get("ID");
    if (StringUtils.isEmpty(id)) {
       id = (String)request.getAttribute("subscriberID");
       if (StringUtils.isNotEmpty(id)) {
          form.set("ID", id);
       }
    }
    return id;
  }

  public ActionForward cancelled(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    return (mapping.findForward("cancel"));
  }  
  
  public ActionForward unspecified(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    return (mapping.findForward("cancel"));
  }  
}
