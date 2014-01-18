/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/software/SavaScreenShotAction.java,v 1.2 2008/07/11 09:01:14 zhao Exp $
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

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;

import com.npower.dm.action.BaseAction;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwareScreenShot;
import com.npower.dm.hibernate.entity.DMBinary;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SoftwareBean;

/**
 * @author Liu AiHui
 * @version $Revision: 1.2 $ $Date: 2008/07/11 09:01:14 $
 */

public class SavaScreenShotAction extends BaseAction {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    DynaActionForm softwareForm = (DynaActionForm) form;
    String softwareID = softwareForm.getString("softwareID");

    if (this.isCancelled(request)) {
      request.setAttribute("softwareID", softwareID);
      return (mapping.findForward("display"));
    }

    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    try {
      SoftwareBean softwareBean = factory.createSoftwareBean();
      factory.beginTransaction();
      SoftwareScreenShot screenShot = null;
      String screenShotID = softwareForm.getString("screenShotID");
      if (StringUtils.isEmpty(screenShotID)) {
        screenShot = softwareBean.newScreenShotInstance();
        BeanUtils.populate(screenShot, softwareForm.getMap());
      } else {
        screenShot = softwareBean.getScreenShotByID(Long.valueOf(screenShotID));
        BeanUtils.populate(screenShot, softwareForm.getMap());
      }

      if (StringUtils.isNotEmpty(softwareID)) {
        Software software = softwareBean.getSoftwareByID(Long.parseLong(softwareID));
        screenShot.setSoftware(software);
      }

      FormFile binaryFile = (FormFile) softwareForm.get("binaryFile");
      String mimeType = binaryFile.getContentType();

      if (binaryFile != null) {
        InputStream ins = binaryFile.getInputStream();
        if (ins.available() > 0) {
          DMBinary binary = new DMBinary(ins);
          binary.setFilename(binaryFile.getFileName());
          binary.setMimeType(mimeType);
          screenShot.setBinary(binary);
        }
      }

      softwareBean.update(screenShot);
      factory.commit();

      request.setAttribute("softwareID", String.valueOf(screenShot.getSoftware().getId()));
    } catch (Exception e) {
      factory.rollback();
      throw new DMException("Sava Software Screen Shot Failure!");
    }
    return (mapping.findForward("display"));
  }
}
