/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/security/SecurityService.java,v 1.10 2008/04/07 05:16:35 zhao Exp $
  * $Revision: 1.10 $
  * $Date: 2008/04/07 05:16:35 $
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
package com.npower.dm.security;

import java.util.List;
import java.util.Properties;

import com.npower.dm.core.DMException;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.10 $ $Date: 2008/04/07 05:16:35 $
 */
public interface SecurityService {
  
  /**
   * General role (magic string :)) to represent authenticated users.
   */
  public static final String AUTHENTICATED_ROLE = "authenticated";

  /**
   * General role (magic string :)) to represent authenticated users.
   */
  public static final String ADMINISTRATOR_ROLE = "dm.admin";

  /**
   * Role for manage all of jobs.
   */
  public static final String JOB_ADMIN_ROLE      = "dm.admin.jobs";
  
  /**
   * Role for manage all of devices.
   */
  public static final String DEVICE_ADMIN_ROLE   = "dm.admin.devices";
  
  /**
   * Role for view all of devices.
   */
  public static final String DEVICE_OPERATOR_ROLE   = "dm.operator.devices";
  
  /**
   * Role for manage all of profile.
   */
  public static final String PROFILE_ADMIN_ROLE  = "dm.admin.profiles";
  
  /**
   * Role for manage all of manufacturs and models.
   */
  public static final String MANUFACTURER_ADMIN_ROLE    = "dm.admin.manufacturers";
  
  /**
   * Role for manage models under a manufacturer .
   */
  public static final String MANUFACTURER_OPERATOR_ROLE    = "dm.operator.manufacturer";
  
  /**
   * Role for manage all of models.
   */
  public static final String MODEL_ADMIN_ROLE    = "dm.admin.models";
  
  /**
   * Role for manage all of carriers.
   */
  public static final String CARRIER_ADMIN_ROLE  = "dm.admin.carriers";
  
  
  public static final String SECURITY_ADMIN_ROLE = "dm.admin.security";
  public static final String AUDIT_ADMIN_ROLE    = "dm.admin.audit";
  
  /**
   * Role for manage all of softwares.
   */
  public static final String SOFTWARE_ADMIN_ROLE    = "dm.admin.softwares";
  
  /**
   * Set properties for this service.
   * @param props
   */
  public void setProperties(Properties props);
  
  /**
   * Initialize the Sercurity Service.
   * @throws DMException
   */
  public void initilize() throws DMException;

  /**
   * Authenticate a user.
   * @param username
   *        Username
   * @param password
   *        Password
   * @return
   *        Principal
   * @throws AuthenticationException
   */
  public DMWebPrincipal authenticate(String username, String password) throws AuthenticationException;

  /**
   * List all of DMWebPrincipal in User database.
   * @return
   * @throws DMException
   */
  public List<DMWebPrincipal> list() throws DMException;
  
  /**
   * Add a user into user database.
   * @param user
   * @throws DMException
   */
  public void add(DMWebPrincipal user) throws DMException;
  
  /**
   * Update a user into user database.
   * @param user
   * @throws DMException
   */
  public void update(DMWebPrincipal user) throws DMException;
  
  /**
   * Delete a user from user database.
   * @param username
   * @throws DMException
   */
  public void delete(String username) throws DMException;
  
  /**
   * Modify password 
   * @param user
   * @param password
   * @throws DMException
   */
  public void updatePassword(DMWebPrincipal user, String password) throws DMException;
  
  /**
   * Find a user by username.
   * @param username
   * @return
   * @throws DMException
   */
  public DMWebPrincipal get(String username) throws DMException;
  
  /**
   * Authorize a user with the role.
   * @param principal
   *        principal of user
   * @param role
   *        Role
   * @return
   *        true, permitted
   * @throws AuthorizationException
   */
  //public boolean imply(DMWebPrincipalImpl principal, String role) throws AuthorizationException;
  
}
