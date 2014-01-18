/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/security/DMWebPrincipalImpl.java,v 1.5 2007/03/31 08:11:24 zhao Exp $
 * $Revision: 1.5 $
 * $Date: 2007/03/31 08:11:24 $
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * Web User.
 * @author Zhao DongLu
 * @version $Revision: 1.5 $ $Date: 2007/03/31 08:11:24 $
 */
public class DMWebPrincipalImpl implements DMWebPrincipal, Serializable, Comparable<DMWebPrincipal> {
  
  private String   firstName;

  private String   lastName;

  private String   username;

  private boolean active = true;

  private List<String> roles;
  
  private List<String> manufacturers = new ArrayList<String>();
  
  private List<String> carriers = new ArrayList<String>();
  /**
   * Default constructor
   */
  public DMWebPrincipalImpl() {
    super();
  }

  /**
   * Constructor
   * @param username
   *        Username
   * @param firstname
   *        Firstname
   * @param lastname
   *        Lastname
   * @param assignedRoles
   *        Roles
   */
  public DMWebPrincipalImpl(String username, String firstname, String lastname, String[] assignedRoles) {
    this.username = username;
    this.firstName = firstname;
    this.lastName = lastname;
    this.roles = new ArrayList<String>(Arrays.asList(assignedRoles));
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.security.DMWebPrincipal#getUsername()
   */
  public String getUsername() {
    return username;
  }

  /**
   * @param username the username to set
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.security.DMWebPrincipal#getFirstName()
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @param firstName the firstName to set
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.security.DMWebPrincipal#getLastName()
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @param lastName the lastName to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * @param roles the roles to set
   */
  public void setRoles(List<String> roles) {
    this.roles = roles;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.security.DMWebPrincipal#getRoles()
   */
  public List<String> getRoles() {
    if (roles == null) {
       this.roles = new ArrayList<String>();
    }
    return this.roles;
  }
  
  /**
   * Set owned manufacturers
   * @param externalIDs
   *        manufacturer external ID
   */
  public void setManufacturers(String[] externalIDs) {
    this.manufacturers = new ArrayList<String>();
    if (externalIDs == null || externalIDs.length == 0) {
       return;
    }
    this.manufacturers.addAll(Arrays.asList(externalIDs));
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.security.DMWebPrincipal#hasRole(java.lang.String)
   */
  public boolean hasRole(String role) {
    if (StringUtils.isEmpty(role)) {
       return false;
    }
    role = role.trim();
    for (String realRole: this.roles) {
        if (realRole.equals(SecurityService.ADMINISTRATOR_ROLE)) {
           return true;
        }
        if (role.equals(realRole)) {
           return true;
        }
    }
    return false;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.security.DMWebPrincipal#getName()
   */
  public String getName() {
    return this.username;
  }

  /**
   * @param manufacturers the manufacturers to set
   */
  public void setOwnedManufacturerExternalIDs(List<String> manufacturers) {
    this.manufacturers = manufacturers;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.security.DMWebPrincipal#getOwnedManufacturerExternalIDs()
   */
  public List<String> getOwnedManufacturerExternalIDs() {
    if (this.manufacturers == null) {
       this.manufacturers = new ArrayList<String>();
    }
    return this.manufacturers;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.security.DMWebPrincipal#isOwnManufacturerExternalID(java.lang.String)
   */
  public boolean isOwnManufacturerExternalID(String externalID) {
    if (this.hasRole(SecurityService.ADMINISTRATOR_ROLE)) {
       return true;
    }
    List<String> externalIDs = this.getOwnedManufacturerExternalIDs();
    if (this.hasRole(SecurityService.MANUFACTURER_OPERATOR_ROLE)) {
       return externalIDs.contains(externalID);
    }
    return true;
  }

  /**
   * Set CarrierIDs for current user.
   */
  public void setOwnedCarrierExternalIDs(List<String> carrierIDs) {
    this.carriers = carrierIDs;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.security.DMWebPrincipal#getOwnedCarrierExternalIDs()
   */
  public List<String> getOwnedCarrierExternalIDs() {
    if (this.carriers == null) {
      this.carriers = new ArrayList<String>();
   }
   return this.carriers;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.security.DMWebPrincipal#isOwnCarrierExternalID(java.lang.String)
   */
  public boolean isOwnCarrierExternalID(String externalID) {
    List<String> externalIDs = this.getOwnedCarrierExternalIDs();
    if (externalIDs.size() > 0) {
       return externalIDs.contains(externalID);
    }
    return true;
  }

  /* (non-Javadoc)
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo(DMWebPrincipal o) {
    if (o == null) {
       return 1;
    }
    return this.getUsername().compareTo(o.getUsername());
  }

  /**
   * @return the active
   */
  public boolean isActive() {
    return active;
  }

  /**
   * @param active the active to set
   */
  public void setActive(boolean active) {
    this.active = active;
  }


}
