/**
  * $Header: /home/master/OTAS-DM-MyPortal/src/com/npower/dm/myportal/cp/IntroAction.java,v 1.5 2008/04/15 10:18:21 zhao Exp $
  * $Revision: 1.5 $
  * $Date: 2008/04/15 10:18:21 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2007 NPower Network Software Ltd.  All rights reserved.
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
package com.npower.dm.myportal.cp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.npower.dm.myportal.BaseWizardAction;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.5 $ $Date: 2008/04/15 10:18:21 $
 */
public class IntroAction extends BaseWizardAction {
  
  /* (non-Javadoc)
   * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  public ActionForward view(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
    // Clean ClientProvForm4Wizard from HttpSession
    HttpSession session = request.getSession();
    //Locale locale = this.getLocale(request);
    session.setAttribute("ClientProvForm4Wizard", null);
    return (mapping.findForward("view"));
  }

  public ActionForward next(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
    return (mapping.findForward("next"));
  }
  
  public ActionForward prev(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
    return (mapping.findForward("prev"));
  }
  
}
