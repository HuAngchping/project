/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/aop/audit/ProfileAssignmentAuditRecipe.aj,v 1.2 2009/02/17 07:30:46 zhaowx Exp $
  * $Revision: 1.2 $
  * $Date: 2009/02/17 07:30:46 $
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
package com.npower.dm.aop.audit;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.npower.dm.action.Constants;
import com.npower.dm.audit.AuditLoggerFactory;
import com.npower.dm.audit.ProfileAssignmentAuditLogger;
import com.npower.dm.audit.action.ProfileAssignmentAction;
import com.npower.dm.core.Device;
import com.npower.dm.core.ProfileAssignment;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.security.DMWebPrincipal;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2009/02/17 07:30:46 $
 */
public aspect ProfileAssignmentAuditRecipe extends BaseRecipe {
  
  private static Log  log         = LogFactory.getLog(ProfileAssignmentAuditRecipe.class);
  
  pointcut createProfileAssignment(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
            : execution(ActionForward com.npower.dm.action.device.SaveProfileAssignment4DMAction.update(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
              && args(mapping, rawForm, request, response)
              && this(action);

  /**
   * Audit: Create ProfileAssignment
   */
  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) 
  returning(ActionForward forward) 
       : createProfileAssignment(action, mapping, rawForm, request, response) && !within(ProfileAssignmentAuditRecipe +) {
    try {
        HttpSession session = request.getSession(false);
        if (session == null) {
           throw new NullPointerException("Could not load Http Session!");
        }
        DMWebPrincipal user = (DMWebPrincipal)session.getAttribute(Constants.ADMIN_USER_KEY);
        if (user == null) {
           throw new NullPointerException("Could not load user data from Http Session!");
        }
        String ipAddress = request.getRemoteAddr();
        
        // Load target device
        Device device = (Device)request.getAttribute("device");
        if (device == null) {
           throw new NullPointerException("Could not load device from HttpServletRequest!");
        }
        
        // Load profileAssignment
        ProvisionJob job = (ProvisionJob)request.getAttribute("provisionJob");
        if (job == null) {
           throw new NullPointerException("Could not load job from HttpServletRequest!");
        }
        List<ProfileAssignment> assignments = job.getProfileAssignments();
        
        String isSend = (String)request.getAttribute("isSend");
        
        // Log audit information
        AuditLoggerFactory factory = this.getAuditLogFactory();
        ProfileAssignmentAuditLogger logger = factory.getProfileAssignmentLog();
        for (ProfileAssignment assignment: assignments) {
            String profileName = assignment.getProfileConfig().getName();
            if (StringUtils.isEmpty(isSend)) {
              logger.log(ProfileAssignmentAction.CREATE, user.getUsername(), ipAddress, assignment.getID(), profileName, device.getID(), device.getExternalId(), null);
            }else {
              logger.log(ProfileAssignmentAction.RE_SEND, user.getUsername(), ipAddress, assignment.getID(), profileName, device.getID(), device.getExternalId(), null);
            }
        }
    } catch (Throwable e) {
      log.error("AOP failured!", e);
    }
  }
  
}
