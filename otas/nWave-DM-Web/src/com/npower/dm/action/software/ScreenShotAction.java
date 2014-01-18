/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/software/ScreenShotAction.java,v 1.3 2008/07/11 09:01:14 zhao Exp $
 * $Revision: 1.3 $
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

import java.io.OutputStream;
import java.sql.Blob;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.npower.dm.action.BaseDispatchAction;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwareScreenShot;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SoftwareBean;

/**
 * @author Liu AiHui
 * @version $Revision: 1.3 $ $Date: 2008/07/11 09:01:14 $
 */

public class ScreenShotAction extends BaseDispatchAction {
  public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    String screenShotID = request.getParameter("screenShotID");
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    SoftwareBean softwareBean = factory.createSoftwareBean();
    SoftwareScreenShot screenShot = softwareBean.getScreenShotByID(Long.valueOf(screenShotID));
    request.setAttribute("screenShot", screenShot);
    Software software = softwareBean.getSoftwareByID(screenShot.getSoftware().getId());
    request.setAttribute("software", software);

    DynaActionForm softwareForm = (DynaActionForm) form;
    softwareForm.set("softwareID", String.valueOf(software.getId()));
    softwareForm.set("screenShotID", screenShotID);

    return (mapping.findForward("view"));
  }

  /**
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    String screenShootId = request.getParameter("screenShotID");

    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    try {
      factory.beginTransaction();
      SoftwareBean softwareBean = factory.createSoftwareBean();
      SoftwareScreenShot screenShot = softwareBean.getScreenShotByID(Long.valueOf(screenShootId));
      request.setAttribute("softwareID", String.valueOf(screenShot.getSoftware().getId()));
      softwareBean.delete(screenShot);
      factory.commit();
    } catch (Exception e) {
      factory.rollback();
      throw new DMException("Remove Software Screen Shot Failure!");
    }
    return (mapping.findForward("success"));
  }

  /**
   * œ‘ æÕº∆¨–≈œ¢
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward showImg(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    String screenShootId = request.getParameter("screenShotID");
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    SoftwareBean softwareBean = factory.createSoftwareBean();
    SoftwareScreenShot screenShot = softwareBean.getScreenShotByID(Long.valueOf(screenShootId));

    if (screenShot.getBinary() != null) {
      Blob img = screenShot.getBinary().getBinaryBlob();
      byte[] iconBytes = img.getBytes(1, (int) img.length());
      response.setHeader("Content-Disposition", "inline;filename=\"" + screenShot.getId() + "\";");
      response.setContentType(screenShot.getBinary().getMimeType());
      response.setContentLength(iconBytes.length);
      OutputStream out = response.getOutputStream();
      out.write(iconBytes);
      out.flush();
    }
    return null;
  }
}
