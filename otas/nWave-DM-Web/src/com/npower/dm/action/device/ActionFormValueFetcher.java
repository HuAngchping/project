/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/device/ActionFormValueFetcher.java,v 1.2 2008/07/24 03:46:55 zhao Exp $
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
package com.npower.dm.action.device;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.npower.cp.convertor.ValueFetcher;
import com.npower.dm.core.DMException;
import com.npower.dm.core.ProfileAttribute;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ProfileTemplate;

/**
 * Fetch value from a profile
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/07/24 03:46:55 $
 */
public class ActionFormValueFetcher implements ValueFetcher<ProfileCategory, String, String> {
  
  private ProfileConfig profile = null;
  private ActionForm actionForm = null;
  private HttpServletRequest request;

  /**
   * Default constructor
   */
  public ActionFormValueFetcher() {
    super();
  }

  /**
   * Constructor
   * @param profile
   */
  public ActionFormValueFetcher(ProfileConfig profile, ActionForm form, HttpServletRequest request) {
    super();
    this.setProfile(profile);
    this.setActionForm(form);
    this.request = request;
  }

  /**
   * Return profile
   * @return the profile
   */
  public ProfileConfig getProfile() {
    return profile;
  }

  /**
   * Set profile
   * @param profile the profile to set
   */
  public void setProfile(ProfileConfig profile) {
    this.profile = profile;
  }

  /**
   * @return the actionForm
   */
  public ActionForm getActionForm() {
    return actionForm;
  }

  /**
   * @param actionForm the actionForm to set
   */
  public void setActionForm(ActionForm actionForm) {
    this.actionForm = actionForm;
  }

  /* (non-Javadoc)
   * @see com.npower.cp.convertor.ValueFetcher#getValue(java.lang.Object, java.lang.Object)
   */
  public String getValue(ProfileCategory category, String key) throws DMException {
    
    ProfileTemplate template = this.getProfile().getProfileTemplate();
    ProfileAttribute attribute = template.getAttributeByAttributeName(key);
    if (attribute == null) {
       return null;
    }
    long id = attribute.getID();
    String value = request.getParameter("attribute__" + id + "__value");
    return value;
  }

  /* (non-Javadoc)
   * @see com.npower.cp.convertor.ValueFetcher#getValue(java.lang.Object, java.lang.Object, java.lang.Object)
   */
  public String getValue(ProfileCategory category, String key, String defaultValue) throws DMException {
    String value = this.getValue(category, key);
    return (value == null)?defaultValue:value;
  }

}
