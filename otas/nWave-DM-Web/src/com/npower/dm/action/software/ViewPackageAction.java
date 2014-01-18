/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/software/ViewPackageAction.java,v 1.2 2008/03/04 06:04:34 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2008/03/04 06:04:34 $
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

import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Model;
import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwarePackage;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SoftwareBean;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/03/04 06:04:34 $
 */
public class ViewPackageAction extends AbstractSoftwareVendorAction {

  /**
   * 
   */
  public ActionForward execute(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    DynaActionForm form = (DynaActionForm) rawForm;
    String packageID = form.getString("packageID");
    if (StringUtils.isEmpty(packageID)) {
      packageID = (String)request.getAttribute("packageID");
    }
    String softwareID = form.getString("softwareID");
    if (StringUtils.isEmpty(softwareID)) {
      softwareID = (String)request.getAttribute("softwareID");
    }
    
    try {
      ManagementBeanFactory factory = this.getManagementBeanFactory(request);
      SoftwareBean softwareBean = factory.createSoftwareBean();
      if (StringUtils.isNotEmpty(softwareID)) {
         Software software = softwareBean.getSoftwareByID(Long.parseLong(softwareID));
         request.setAttribute("software", software);
      }
      if (StringUtils.isNotEmpty(packageID)) {
        SoftwarePackage pkg = softwareBean.getPackageByID(Long.valueOf(packageID));
        request.setAttribute("softwarePackage", pkg);
        Set<Model> targetModels = softwareBean.getTargetModels(pkg);
        request.setAttribute("targetModels", new TreeSet<Model>(targetModels));
      }
      return (mapping.findForward("view"));
    } catch (DMException e) {
      throw e;
    }
  }

}
