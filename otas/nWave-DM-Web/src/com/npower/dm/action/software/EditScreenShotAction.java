/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/software/EditScreenShotAction.java,v 1.2 2008/07/11 09:01:14 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2008/07/11 09:01:14 $
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

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.npower.dm.action.BaseAction;
import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwareScreenShot;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SoftwareBean;

/**
 * @author Liu AiHui
 * @version $Revision: 1.2 $ $Date: 2008/07/11 09:01:14 $
 */

public class EditScreenShotAction extends BaseAction {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    DynaActionForm softwareForm = (DynaActionForm) form;
    
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    SoftwareBean bean = factory.createSoftwareBean();
    
    String softwareID = softwareForm.getString("softwareID");
    if (this.isCancelled(request)) {
      request.setAttribute("softwareID", softwareID);
      return (mapping.findForward("cancel"));
    }
    
    // Get software
    Software software = bean.getSoftwareByID(Long.parseLong(softwareID));
    request.setAttribute("software", software);
    
    String screenShootId = softwareForm.getString("screenShotID");
    if (StringUtils.isNotEmpty(screenShootId)) {
      SoftwareScreenShot screenShot = bean.getScreenShotByID(Long.parseLong(screenShootId));
      DynaActionForm screenShotForm = (DynaActionForm) form;
      screenShotForm.getMap().putAll(BeanUtils.describe(screenShot));
      softwareForm.set("screenShotID", String.valueOf(screenShot.getId()));
      softwareForm.set("softwareID", String.valueOf(screenShot.getSoftware().getId()));
    }
    return (mapping.findForward("edit"));
  }

}
