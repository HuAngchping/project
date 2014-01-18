/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/config/ProfileConfigForm4Save.java,v 1.1 2007/04/10 10:52:22 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2007/04/10 10:52:22 $
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
package com.npower.dm.action.config;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.RequestUtils;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/04/10 10:52:22 $
 */
public class ProfileConfigForm4Save extends ActionForm {
  
  private Map<String, Object> map = new HashMap<String, Object>();


  /**
   * Method validate
   * 
   * @param mapping
   * @param request
   * @return ActionErrors
   */
  public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
    ActionErrors errors = super.validate(mapping, request);
    if (errors == null) {
       errors  =  new  ActionErrors();
    }
    MessageResources messages = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
    Locale locale = RequestUtils.getUserLocale(request, null);
    
    // templateID required
    String templateID = (String)this.getValue("templateID");
    if (StringUtils.isEmpty(templateID)) {
      String fieldLabel = messages.getMessage(locale, "profile.config.template.label");
      errors.add ("templateID", new ActionMessage("errors.required", fieldLabel));
    }
    
    // name required
    String name = (String)this.getValue("name");
    if (StringUtils.isEmpty(name)) {
      String fieldLabel = messages.getMessage(locale, "profile.config.name.label");
      errors.add ("name", new ActionMessage("errors.required", fieldLabel));
    }
    
    if (!errors.isEmpty()) {
       String profileID = (String)this.getValue("ID");
       request.setAttribute("ID", profileID);
      
       return errors;
    }
    return null;
  }

  /**
   * Method reset
   * 
   * @param mapping
   * @param request
   */
  public void reset(ActionMapping mapping, HttpServletRequest request) {
    if (this.map != null) {
       this.map.clear();
    }
  }

  public void setMap(Map<String, Object> map) {
    this.map = map;
  }

  public Map<String, Object> getMap() {
    return this.map;
  }

  public void setValue(String key, Object value) {
    //System.out.println("map.putting(" + key + ", " + value + ")");
    map.put(key, value);
  }

  public Object getValue(String key) {
    //System.out.println("map.getting(" + key + ", " + map.get(key) + ")");
    return map.get(key);
  }

}
