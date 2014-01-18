/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/handler/GlobalExceptionHandler.java,v 1.3 2006/12/11 03:38:52 zhao Exp $
 * $Revision: 1.3 $
 * $Date: 2006/12/11 03:38:52 $
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
package com.npower.dm.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;

import com.npower.dm.form.ErrorFormBean;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2006/12/11 03:38:52 $
 */
public class GlobalExceptionHandler extends ExceptionHandler {
  
  private static Log log = LogFactory.getLog(GlobalExceptionHandler.class);

  public ActionForward execute(java.lang.Exception ex, ExceptionConfig ae, ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response) {
    
    // Dump to log
    log.error("Failure in web action", ex);
    
    ActionMessages messages = new ActionMessages();
    //ActionMessage message = new ActionMessage("page.login.failure.message");
    Throwable t = ex;
    int total = 10;
    while (t != null && total > 0) {
          ActionMessage message = new ActionMessage("common.exception.error.message", t.getMessage());
          messages.add(ActionMessages.GLOBAL_MESSAGE, message);

          total--;
          if ( t.getCause() == null || t == t.getCause()) {
             break;
          } else {
            t = t.getCause();
          }
    }

    //ActionMessages errors = new ActionMessages();
    //errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("REALLY.BIG.ERROR"));
    saveErrors(request, messages);
    
    HttpSession session = request.getSession();

    ErrorFormBean errorForm = new ErrorFormBean();
    errorForm.setMessage(ex.getMessage());
    session.setAttribute("errorBean", errorForm);

    return (mapping.findForward("Error"));
  }
  
  /**
   * <p>Save the specified error messages keys into the appropriate request
   * attribute for use by the &lt;html:errors&gt; tag, if any messages
   * are required. Otherwise, ensure that the request attribute is not
   * created.</p>
   *
   * @param request The servlet request we are processing
   * @param errors Error messages object
   * @since Struts 1.2
   */
  private void saveErrors(HttpServletRequest request, ActionMessages errors) {

      // Remove any error messages attribute if none are required
      if ((errors == null) || errors.isEmpty()) {
          request.removeAttribute(Globals.ERROR_KEY);
          return;
      }

      // Save the error messages we need
      request.setAttribute(Globals.ERROR_KEY, errors);

  }
  
}
