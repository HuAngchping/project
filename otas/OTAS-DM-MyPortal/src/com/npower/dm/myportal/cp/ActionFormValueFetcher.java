/**
  * $Header: /home/master/OTAS-DM-MyPortal/src/com/npower/dm/myportal/cp/ActionFormValueFetcher.java,v 1.2 2007/12/24 03:01:15 zhao Exp $
  * $Revision: 1.2 $
  * $Date: 2007/12/24 03:01:15 $
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

import com.npower.cp.convertor.ValueFetcher;
import com.npower.dm.core.DMException;
import com.npower.dm.core.ProfileAttribute;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ProfileTemplate;

/**
 * Fetch value from a profile
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2007/12/24 03:01:15 $
 */
public class ActionFormValueFetcher implements ValueFetcher<ProfileCategory, String, String> {
  
  private ProfileConfig profile = null;
  private ClientProvWizardForm actionForm = null;

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
  public ActionFormValueFetcher(ProfileConfig profile, ClientProvWizardForm form) {
    super();
    this.setProfile(profile);
    this.setActionForm(form);
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
  public ClientProvWizardForm getActionForm() {
    return actionForm;
  }

  /**
   * @param actionForm the actionForm to set
   */
  public void setActionForm(ClientProvWizardForm actionForm) {
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
    String value = this.getActionForm().getString("attribute__" + id + "__value");
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
