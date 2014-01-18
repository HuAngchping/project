/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/software/SoftwareAction.java,v 1.3 2008/03/01 04:25:17 zhao Exp $
 * $Revision: 1.3 $
 * $Date: 2008/03/01 04:25:17 $
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

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.npower.dm.action.BaseDispatchAction;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Software;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SoftwareBean;

/**
 * @author Liu AiHui
 * @version $Revision: 1.3 $ $Date: 2008/03/01 04:25:17 $
 */

public class SoftwareAction extends BaseDispatchAction {
  public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    String id = request.getParameter("id");
    if (StringUtils.isEmpty(id)) {
       // Redirect from Software Package pages.
       id = (String)request.getAttribute("softwareID");
    }
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    SoftwareBean softwareBean = factory.createSoftwareBean();
    Software software = softwareBean.getSoftwareByID(Long.valueOf(id));
    request.setAttribute("software", software);
    return (mapping.findForward("view"));
  }

  public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    String id = request.getParameter("id");
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    try {
      factory.beginTransaction();
      SoftwareBean softwareBean = factory.createSoftwareBean();
      Software software = softwareBean.getSoftwareByID(Long.valueOf(id));
      softwareBean.delete(software);
      factory.commit();
    } catch (Exception e) {
      factory.rollback();
      throw new DMException("Remove Software Failure!");
    }
    return (mapping.findForward("display"));
  }
  
  public ActionForward unspecified(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    return this.view(mapping, rawForm, request, response);
  }  

}
