/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/software/RemovePackageAction.java,v 1.3 2008/06/16 10:11:22 zhao Exp $
 * $Revision: 1.3 $
 * $Date: 2008/06/16 10:11:22 $
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
package com.npower.dm.action.software;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.npower.dm.action.BaseAction;
import com.npower.dm.core.DMException;
import com.npower.dm.core.SoftwarePackage;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SoftwareBean;

/**
 * @author Zhao Yang
 * @version $Revision: 1.3 $ $Date: 2008/06/16 10:11:22 $
 */
public class RemovePackageAction extends BaseAction {
  
  /**
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward execute(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    DynaActionForm form = (DynaActionForm) rawForm;

    if (isCancelled(request)) {
      return (mapping.findForward("cancel"));
    }

    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    SoftwareBean bean = factory.createSoftwareBean();

    try {
      String packageID = form.getString("packageID");
      String softwareID = form.getString("softwareID");
      SoftwarePackage pkg = bean.getPackageByID(Long.parseLong(packageID));
      factory.beginTransaction();
      bean.delete(pkg);
      factory.commit();
      request.setAttribute("softwareID", softwareID);
    } catch (DMException e) {
      throw e;
    } catch (Exception e) {
      throw e;
    } finally {
    }
    return (mapping.findForward("success"));
  }

}
