/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/security/DMWebPrincipal.java,v 1.4 2007/01/15 09:39:33 zhao Exp $
 * $Revision: 1.4 $
 * $Date: 2007/01/15 09:39:33 $
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

import java.io.Serializable;
import java.security.Principal;
import java.util.List;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2007/01/15 09:39:33 $
 */
public interface DMWebPrincipal extends Serializable, Principal {

  /**
   * Return username
   * @return
   */
  public abstract String getUsername();

  /**
   * Return Firstname
   * @return
   */
  public abstract String getFirstName();

  /**
   * Return Lastname
   * @return
   */
  public abstract String getLastName();

  /**
   * Set Username
   * @param username
   */
  public abstract void setUsername(String username);

  /**
   * Set Firstname
   * @param firstname
   */
  public abstract void setFirstName(String firstname);

  /**
   * Set Lastname
   * @param lastname
   */
  public abstract void setLastName(String lastname);

  /**
   * Return roles of this principal
   * @return 
   *        Roles
   */
  public abstract List<String> getRoles();

  /**
   * Return roles of this principal
   * @return 
   *        Roles
   */
  public abstract void  setRoles(List<String> roles);

  /**
   * Imply role.
   * @param role
   * @return
   */
  public abstract boolean hasRole(String role);
  
  
  /**
   * Return owned manufacturers by the user.
   * @return
   *        ExternalIDs owened by this user.
   */
  public abstract List<String> getOwnedManufacturerExternalIDs();
  
  /**
   * Set owned manufacturers by the user.
   * @param manufacturers
   *        ExternalIDs owened by this user.
   */
  public abstract void setOwnedManufacturerExternalIDs(List<String>  manufacturers);

  /**
   * Check permission
   * @param externalID
   * @return
   *        true: own the manufacturer.
   */
  public abstract boolean isOwnManufacturerExternalID(String externalID);
  
  /**
   * Return owned carriers by the user.
   * @return
   *        ExternalIDs owened by this user.
   */
  public abstract List<String> getOwnedCarrierExternalIDs();
  
  /**
   * Set owned carriers by the user.
   * @param carriers
   *        ExternalIDs owened by this user.
   */
  public abstract void setOwnedCarrierExternalIDs(List<String> carriers);
  

  /**
   * Check permission
   * @param externalID
   * @return
   *        true: own the carrier.
   */
  public abstract boolean isOwnCarrierExternalID(String externalID);
  
  /* (non-Javadoc)
   * @see java.security.Principal#getName()
   */
  public abstract String getName();

}