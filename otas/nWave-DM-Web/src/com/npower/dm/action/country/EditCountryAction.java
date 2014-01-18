/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/country/EditCountryAction.java,v 1.10 2007/03/30 11:02:21 zhao Exp $
 * $Revision: 1.10 $
 * $Date: 2007/03/30 11:02:21 $
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

package com.npower.dm.action.country;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.npower.dm.core.Country;
import com.npower.dm.core.DMException;

/**
 * MyEclipse Struts Creation date: 05-30-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/EditCountry" name="CountryForm" scope="request"
 * @struts.action-forward name="success" path="/jsp/country.jsp"
 *                        contextRelative="true"
 */
public class EditCountryAction extends AbstractCountryAction {

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
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    
    DynaActionForm countryForm = (DynaActionForm) form;
    String action = request.getParameter("action");
    try {
        if (action != null && action.equalsIgnoreCase("update")) {
           // Load data from DB
           Country country = this.getCountry(request, (String) countryForm.get("ID"));
           request.setAttribute("country", country);
           // Copy data from POJO into FormBean
           countryForm.getMap().putAll(BeanUtils.describe(country));
           //BeanUtils.copyProperties(country, countryForm);
        } else {
          // countryForm.set("name", "Create country");
        }
        return (mapping.findForward("edit"));
    } catch (DMException e) {
      throw e;
    }
  }

}
