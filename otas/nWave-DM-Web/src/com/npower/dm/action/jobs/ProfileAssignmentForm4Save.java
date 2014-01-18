/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/jobs/ProfileAssignmentForm4Save.java,v 1.2 2008/07/24 03:46:55 zhao Exp $
  * $Revision: 1.2 $
  * $Date: 2008/07/24 03:46:55 $
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
package com.npower.dm.action.jobs;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/07/24 03:46:55 $
 */
public class ProfileAssignmentForm4Save extends ActionForm {
  
  private Map<String, Object> map = new HashMap<String, Object>();


  /**
   * Method validate
   * 
   * @param mapping
   * @param request
   * @return ActionErrors
   */
  public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
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

  public String getString(String key) {
    Object v = this.getValue(key);
    if (v != null) {
       return v.toString();
    }
    return null;
  }
}
