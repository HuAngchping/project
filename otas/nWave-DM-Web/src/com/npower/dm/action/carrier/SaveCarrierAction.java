/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/carrier/SaveCarrierAction.java,v 1.10 2008/02/04 10:08:14 zhao Exp $
 * $Revision: 1.10 $
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

import com.npower.dm.action.BaseDispatchAction;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;
import com.npower.dm.core.DMException;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.CountryBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileConfigBean;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.10 $ $Date: 2008/02/04 10:08:14 $
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/EditCarrier" name="CarrierForm" input="jsp/carrier.jsp"
 *                scope="request" validate="true"
 * @struts.action-forward name="success" path="/jsp/success.jsp"
 *                        contextRelative="true"
 */
public class SaveCarrierAction extends BaseDispatchAction {

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
  public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

    DynaActionForm carrierForm = (DynaActionForm) form;

    // Was this transaction cancelled?
    if (isCancelled(request)) {
      return (mapping.findForward("cancel"));
    }

    ManagementBeanFactory factory = getManagementBeanFactory(request);
    CarrierBean carrierBean = factory.createCarrierBean();
    CountryBean countryBean = factory.createCountryBean();
    try {
        Carrier carrier = carrierBean.newCarrierInstance();

        // Copy Properties from form into carrier.
        BeanUtils.populate(carrier, carrierForm.getMap());

        // Set Country for this carrier
        Country country = countryBean.getCountryByID(Long.valueOf((String) carrierForm.get("countryID")));
        carrier.setCountry(country);

        factory.beginTransaction();
        carrierBean.update(carrier);
        factory.commit();

        // Pass target to AOP Audit
        request.setAttribute("carrier", carrier);
        
        return (mapping.findForward("success"));
    } catch (DMException e) {
      if (factory != null) {
        factory.rollback();
      }
      throw e;
    } catch (Exception e) {
      if (factory != null) {
        factory.rollback();
      }
      throw e;
    } finally {
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
  public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

    DynaActionForm carrierForm = (DynaActionForm) form;

    // Was this transaction cancelled?
    if (isCancelled(request)) {
      return (mapping.findForward("cancel"));
    }

    ManagementBeanFactory factory = getManagementBeanFactory(request);
    CarrierBean carrierBean = factory.createCarrierBean();
    CountryBean countryBean = factory.createCountryBean();
    ProfileConfigBean profileBean = factory.createProfileConfigBean();

    try {
        String id = (String) carrierForm.get("ID");
        Carrier carrier = carrierBean.getCarrierByID(id);
  
        // Copy Properties from form into carrier.
        BeanUtils.populate(carrier, carrierForm.getMap());
  
        // Set Country for this carrier
        Country country = countryBean.getCountryByID(Long.valueOf((String) carrierForm.get("countryID")));
        carrier.setCountry(country);
        
        // Set Bootstrap NAP or Proxy Profile for this carrier.
        String profileID4NAP = carrierForm.getString("profileID4NAP");
        ProfileConfig bootstrapNAPProfile = null;
        if (StringUtils.isNotEmpty(profileID4NAP)) {
           bootstrapNAPProfile = profileBean.getProfileConfigByID(profileID4NAP);
        }
        carrier.setBootstrapNapProfile(bootstrapNAPProfile);
  
        // Set Bootstrap DM Profile for this carrier.
        String profileID4DM = carrierForm.getString("profileID4DM");
        ProfileConfig bootstrapDMProfile = null;
        if (StringUtils.isNotEmpty(profileID4DM)) {
          bootstrapDMProfile = profileBean.getProfileConfigByID(profileID4DM);
        }
        carrier.setBootstrapDmProfile(bootstrapDMProfile);
  
        factory.beginTransaction();
        carrierBean.update(carrier);
        factory.commit();

        // Pass target to AOP Audit
        request.setAttribute("carrier", carrier);
        
        return (mapping.findForward("success"));
    } catch (DMException e) {
      throw e;
    } catch (Exception e) {
      throw e;
    } finally {
    }
  }
}
