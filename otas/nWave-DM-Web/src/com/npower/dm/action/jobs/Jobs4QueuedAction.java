/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/jobs/Jobs4QueuedAction.java,v 1.3 2008/07/24 07:39:09 zhao Exp $
  * $Revision: 1.3 $
  * $Date: 2008/07/24 07:39:09 $
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
package com.npower.dm.action.jobs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.displaytag.pagination.PaginatedList;

import com.npower.dm.core.DMException;
import com.npower.dm.core.ProvisionJob;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/07/24 07:39:09 $
 */
public class Jobs4QueuedAction extends BaseJobsAction {

  /**
   * Method execute
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward execute(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    // Set search options 
    super.execute(mapping, rawForm, request, response);
    try {
        PaginatedList result = this.getjobs(request, rawForm, ProvisionJob.JOB_STATE_APPLIED);
        request.setAttribute("jobs", result);
        
        // 设置列表类型, 方便后续页面返回到正确的位置
        HttpSession session = request.getSession(false);
        session.setAttribute("current.job.list.viewer.name", "job.list.queued");
        return (mapping.findForward("list"));
    } catch (DMException e) {
      throw e;
    }
  }
}
