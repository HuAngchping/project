/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/carrier/ViewCarrierAction.java,v 1.2 2008/02/04 10:08:14 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2008/02/04 10:08:14 $
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

package com.npower.dm.action.carrier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.npower.dm.action.ActionHelper;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;

/**
 * MyEclipse Struts Creation date: 05-30-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/EditCarrier" name="CarrierForm" scope="request"
 * @struts.action-forward name="success" path="/jsp/carrier.jsp"
 *                        contextRelative="true"
 */
public class ViewCarrierAction extends AbstractCarrierAction {

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
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    DynaActionForm carrierForm = (DynaActionForm) form;
    String carrierID = (String) carrierForm.get("ID");
    try {
        this.loadAuthenticationSchemas(mapping, form, request, response);
        if (StringUtils.isNotEmpty(carrierID)) {
           // Update mode
           Carrier carrier = this.getCarrier(request, carrierID);
           request.setAttribute("carrier", carrier);
           
           carrierForm.getMap().putAll(BeanUtils.describe(carrier));
          
           carrierForm.set("countryID", carrier.getCountry().getID() + "");
           
           // Load default SMSC Properties
           if (StringUtils.isEmpty(carrier.getNotificationProperties())) {
              //Properties props = ConfigHelper.getSmsGatewayProperties();
              //StringWriter propsContent = new StringWriter();
              //PrintWriter writer = new PrintWriter(propsContent);
              //props.list(writer);
              //carrierForm.set("notificationProperties", propsContent.toString());
           }
        } else {
          // Create mode
          carrierForm.set("serverAuthType", Device.AUTH_TYPE_BASIC);
          carrierForm.set("notificationMaxNumRetries", "3");
          carrierForm.set("notificationStateTimeout", "3600");
          carrierForm.set("bootstrapTimeout", "3600");
        }
        
        request.setAttribute("countryOptions", ActionHelper.getCountryOptions(this.getManagementBeanFactory(request)));
        //request.setAttribute("profileOptions", this.getCountryOptions());
        
        return (mapping.findForward("view"));
    } catch (DMException e) {
      throw e;
    }
  }

}
