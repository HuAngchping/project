/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/security/UsersAction.java,v 1.4 2007/03/31 08:11:24 zhao Exp $
 * $Revision: 1.4 $
 * $Date: 2007/03/31 08:11:24 $
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

package com.npower.dm.action.security;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import com.npower.dm.action.ActionHelper;
import com.npower.dm.action.BaseAction;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.security.DMWebPrincipal;
import com.npower.dm.security.DMWebPrincipalImpl;
import com.npower.dm.security.SecurityService;
import com.npower.dm.security.SecurityServiceFactory;

/**
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/Carriers" name="CarrierForm" scope="request"
 *                validate="true"
 * @struts.action-forward name="display" path="/jsp/carriers.jsp"
 *                        contextRelative="true"
 */
public class UsersAction extends BaseAction {

  // --------------------------------------------------------- Instance
  // Variables

  // --------------------------------------------------------- Methods

  private List<DMWebPrincipal> search(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws DMException {
    DynaValidatorForm searchForm = (DynaValidatorForm) form;
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    CarrierBean carrierBean = factory.createCarrierBean();
    ModelBean modelBean = factory.createModelBean();
    
    String searchCarrierID = searchForm.getString("searchCarrierID");
    String searchText = searchForm.getString("searchText");
    String searchActivityString = searchForm.getString("searchActivity");
    String searchManufacturerID = searchForm.getString("searchManufacturerID");
    String searchRole = searchForm.getString("searchRole");
    
    Boolean searchActive = null;
    if (StringUtils.isNotEmpty(searchActivityString)) {
      searchActive = new Boolean(searchActivityString);
    }
    
    String manufacturerExtID = null;
    if (StringUtils.isNotEmpty(searchManufacturerID)) {
       Manufacturer manufacturer = modelBean.getManufacturerByID(searchManufacturerID);
       if (manufacturer != null) {
          manufacturerExtID = manufacturer.getExternalId();
       }
    }
    
    String carrierExtID = null;
    if (StringUtils.isNotEmpty(searchCarrierID)) {
       Carrier carrier = carrierBean.getCarrierByID(searchCarrierID);
       if (carrier != null) {
          carrierExtID = carrier.getExternalID();
       }
    }
    
    SecurityServiceFactory securityFactory = SecurityServiceFactory.newInstance();
    SecurityService service = securityFactory.getSecurityService(); 
    List<DMWebPrincipal> users = service.list();
    
    List<DMWebPrincipal> result = new ArrayList<DMWebPrincipal>();
    for (DMWebPrincipal user: users) {
        if (StringUtils.isNotEmpty(searchText)
            && user.getUsername().toLowerCase().indexOf(searchText.toLowerCase()) < 0) {
           continue;
        }
        
        if (StringUtils.isNotEmpty(searchRole)
            && !user.hasRole(searchRole)) {
           continue;
        }
        
        if (StringUtils.isNotEmpty(manufacturerExtID) &&
            !user.isOwnManufacturerExternalID(manufacturerExtID)) {
           continue;
        }
        
        if (StringUtils.isNotEmpty(carrierExtID)
            && !user.isOwnCarrierExternalID(carrierExtID)) {
          continue;
        }
        
        if (searchActive != null 
            && searchActive.booleanValue() != ((DMWebPrincipalImpl)user).isActive()) {
           continue;
        }
        
        result.add(user);
    }
    return result;
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
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

    DynaValidatorForm searchForm = (DynaValidatorForm) form;
    try {
        List<DMWebPrincipal> result = this.search(mapping, form, request, response);
        request.setAttribute("users", result);
  
        request.setAttribute("carrierOptions", ActionHelper.getCarrierOptions(this.getManagementBeanFactory(request)));
        request.setAttribute("manufacturerOptions", ActionHelper.getManufacturerOptions(this.getManagementBeanFactory(request), request));
        
        // Set options for Records per page options.
        BaseAction.setRecordsPerPageOptions(request, searchForm);
  
        return (mapping.findForward("display"));
        
    } catch (DMException e) {
      throw e;
    }
  }

}
