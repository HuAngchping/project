/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/aop/audit/DeviceAuditRecipe.aj,v 1.3 2007/02/08 07:12:58 zhao Exp $
  * $Revision: 1.3 $
  * $Date: 2007/02/08 07:12:58 $
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import com.npower.dm.core.Device;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.action.Constants;
import com.npower.dm.audit.AuditLoggerFactory;
import com.npower.dm.audit.DeviceAuditLogger;
import com.npower.dm.audit.action.DeviceAction;
import com.npower.dm.security.DMWebPrincipal;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2007/02/08 07:12:58 $
 */
public aspect DeviceAuditRecipe extends BaseRecipe {

  private static Log  log         = LogFactory.getLog(DeviceAuditRecipe.class);
  
  /**
   * Audit: update device
   */
  pointcut updateDevice(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
           : execution(ActionForward com.npower.dm.action.device.SaveDeviceAction.update(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
             && args(mapping, rawForm, request, response)
             && this(action);
  
  /**
   * Audit: create device
   */
  pointcut activateDevice(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
           : execution(ActionForward com.npower.dm.action.device.SaveDeviceAction.create(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
             && args(mapping, rawForm, request, response)
             && this(action);

  /**
   * Audit: delete device
   */
  pointcut deleteDevice(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
           : execution(ActionForward com.npower.dm.action.device.RemoveDeviceAction.remove(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
             && args(mapping, rawForm, request, response)
             && this(action);

  /**
   * Audit: view device
   */
  pointcut viewDevice(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
           : execution(ActionForward com.npower.dm.action.device.ViewDeviceAction.execute(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
             && args(mapping, rawForm, request, response)
             && this(action);

  /**
   * Audit: discoverNodes
   */
  pointcut discoverNodes(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
           : execution(ActionForward com.npower.dm.action.device.SaveJobDiscoveryAction.update(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
             && args(mapping, rawForm, request, response)
             && this(action);

  /**
   * Audit: enterCommandScript
   */
  pointcut enterCommandScript(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response)
           : execution(ActionForward com.npower.dm.action.device.SaveJobScriptAction.update(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse))
             && args(mapping, rawForm, request, response)
             && this(action);

  
  /**
   * Audit: Update Device
   */
  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) 
  returning(ActionForward forward) 
       : updateDevice(action, mapping, rawForm, request, response) && !within(SecurityAuditRecipe +) {
    String forwardName = forward.getName();
    if (forwardName == null || forwardName.equalsIgnoreCase("success")) {
       // Login success!
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
           Device device = (Device)request.getAttribute("device");
           if (device == null) {
              throw new NullPointerException("Could not load device data from attributes of HttpServletRequest!");
           }
           AuditLoggerFactory factory = this.getAuditLogFactory();
           DeviceAuditLogger logger = factory.getDeviceLog();
           logger.log(DeviceAction.Update_Device, user.getUsername(), ipAddress, device.getID(), device.getExternalId(), null);
       } catch (Throwable e) {
         log.error("AOP failured!", e);
       }
    }
  }
  
  /**
   * Audit: Create Device
   */
  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) 
  returning(ActionForward forward) 
       : activateDevice(action, mapping, rawForm, request, response) && !within(SecurityAuditRecipe +) {
    String forwardName = forward.getName();
    if (forwardName == null || forwardName.equalsIgnoreCase("success")) {
       // Login success!
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
           Device device = (Device)request.getAttribute("device");
           if (device == null) {
              throw new NullPointerException("Could not load device data from attributes of HttpServletRequest!");
           }
           AuditLoggerFactory factory = this.getAuditLogFactory();
           DeviceAuditLogger logger = factory.getDeviceLog();
           logger.log(DeviceAction.Activate_Device, user.getUsername(), ipAddress, device.getID(), device.getExternalId(), null);
       } catch (Throwable e) {
         log.error("AOP failured!", e);
       }
    }
  }
  
  /**
   * Audit: Create Device
   */
  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) 
  returning(ActionForward forward) 
       : deleteDevice(action, mapping, rawForm, request, response) && !within(SecurityAuditRecipe +) {
    String forwardName = forward.getName();
    if (forwardName == null || forwardName.equalsIgnoreCase("success")) {
       // Login success!
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
           DynaValidatorForm deviceForm = (DynaValidatorForm) rawForm;
           String deviceIDString = (String) deviceForm.get("ID");
           if (deviceIDString == null) {
              throw new NullPointerException("Could not load deviceID from attributes of HttpServletRequest!");
           }
           long deviceID = Long.parseLong(deviceIDString);
           AuditLoggerFactory factory = this.getAuditLogFactory();
           DeviceAuditLogger logger = factory.getDeviceLog();
           logger.log(DeviceAction.Delete_Device, user.getUsername(), ipAddress, deviceID, null, null);
       } catch (Throwable e) {
         log.error("AOP failured!", e);
       }
    }
  }
  
  /**
   * Audit: View Device
   */
  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) 
  returning(ActionForward forward) 
       : viewDevice(action, mapping, rawForm, request, response) && !within(SecurityAuditRecipe +) {
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
         Device device = (Device)request.getAttribute("device");
         if (device == null) {
            throw new NullPointerException("Could not load device data from attributes of HttpServletRequest!");
         }
         AuditLoggerFactory factory = this.getAuditLogFactory();
         DeviceAuditLogger logger = factory.getDeviceLog();
         logger.log(DeviceAction.View_Device, user.getUsername(), ipAddress, device.getID(), device.getExternalId(), null);
     } catch (Throwable e) {
       log.error("AOP failured!", e);
     }
  }
  
  /**
   * Audit: discover nodes
   */
  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) 
  returning(ActionForward forward) 
       : discoverNodes(action, mapping, rawForm, request, response) && !within(SecurityAuditRecipe +) {
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
         Device device = (Device)request.getAttribute("device");
         if (device == null) {
            throw new NullPointerException("Could not load device data from attributes of HttpServletRequest!");
         }
         
         ProvisionJob job = (ProvisionJob)request.getAttribute("provisionJob");
         if (job == null) {
            throw new NullPointerException("Could not load job data from attributes of HttpServletRequest!");
         }
         AuditLoggerFactory factory = this.getAuditLogFactory();
         DeviceAuditLogger logger = factory.getDeviceLog();
         
         String result = "";
         for (String node: job.getNodes4Discovery()) {
             result += node + ",";
         }
         logger.log(DeviceAction.Discover_Nodes, user.getUsername(), ipAddress, device.getID(), null, result);
     } catch (Throwable e) {
       log.error("AOP failured!", e);
     }
  }

  /**
   * Audit: enterCommandScript
   */
  after(Action action, ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) 
  returning(ActionForward forward) 
       : enterCommandScript(action, mapping, rawForm, request, response) && !within(SecurityAuditRecipe +) {
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
         Device device = (Device)request.getAttribute("device");
         if (device == null) {
            throw new NullPointerException("Could not load device data from attributes of HttpServletRequest!");
         }
         
         ProvisionJob job = (ProvisionJob)request.getAttribute("provisionJob");
         if (job == null) {
            throw new NullPointerException("Could not load job data from attributes of HttpServletRequest!");
         }
         AuditLoggerFactory factory = this.getAuditLogFactory();
         DeviceAuditLogger logger = factory.getDeviceLog();
         
         logger.log(DeviceAction.Enter_Command_Script, user.getUsername(), ipAddress, device.getID(), null, job.getScriptString());
     } catch (Throwable e) {
       log.error("AOP failured!", e);
     }
  }
  
}
