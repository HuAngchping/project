/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/security/FileDBSecurityServiceImpl.java,v 1.8 2007/04/12 02:05:20 LAH Exp $
 * $Revision: 1.8 $
 * $Date: 2007/04/12 02:05:20 $
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

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.dm.core.DMException;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.8 $ $Date: 2007/04/12 02:05:20 $
 */
public class FileDBSecurityServiceImpl implements SecurityService {
  
  private static Log log = LogFactory.getLog(FileDBSecurityServiceImpl.class);
  
  //private String userDatabaseDir = null;
  private String userDatabaseDir = "D:\\Zhao\\MyWorkspace\\nWave-DM-Web\\config\\userdb";
  
  private Map<String, DMWebPrincipal>   users = new TreeMap<String, DMWebPrincipal>();
  private Map<String, String>   passwords = new TreeMap<String, String>();

  private Properties properties = new Properties();

  public FileDBSecurityServiceImpl() throws DMException {
    super();
  }
  
  // Setter and Getter -----------------------------------------------------------------
  /**
   * @return the userDatabaseDir
   */
  public String getUserDatabaseDir() {
    return userDatabaseDir;
  }

  /**
   * @param userDatabaseDir the userDatabaseDir to set
   */
  public void setUserDatabaseDir(String userDatabaseDir) {
    this.userDatabaseDir = userDatabaseDir;
  }
  
  
  // Protected methods -----------------------------------------------------------------
  /**
   * Save users and password into user database
   * @throws DMException
   */
  protected void save() throws DMException {
    if (StringUtils.isEmpty(userDatabaseDir)) {
       throw new DMException("Missing UserDB base directory, please set base directory for FileDBSecurityServiceImpl.");
    }
    File userFile = new File(userDatabaseDir, "dm.users.xml");
    try {
        XMLEncoder encoder = null;
        try {
            encoder = new XMLEncoder(new FileOutputStream(userFile));
            encoder.writeObject(this.users);
            encoder.flush();
        } catch (IOException e) {
            String msg = "Bean saving (" + userFile + ") failed: " + e.getMessage();
            throw new DMException(msg, e);
        } finally {
          if (encoder != null) {
             encoder.close();
          }
        }
    } catch (Exception e) {
      throw new DMException(e.getMessage(), e);
    }
    File passwordFile = new File(userDatabaseDir, "dm.users.password.xml");
    try {
        XMLEncoder encoder = null;
        try {
            encoder = new XMLEncoder(new FileOutputStream(passwordFile));
            encoder.writeObject(this.passwords);
        } catch (IOException e) {
            String msg = "Bean saving (" + passwordFile + ") failed: " + e.getMessage();
            throw new DMException(msg, e);
        } finally {
          if (encoder != null) {
             encoder.close();
          }
        }
    } catch (Exception e) {
      throw new DMException(e.getMessage(), e);
    }
  }
  
  /**
   * Load users and password from user database.
   * @throws DMException
   */
  @SuppressWarnings("unchecked")
  protected void load() throws DMException {
    if (StringUtils.isEmpty(userDatabaseDir)) {
       throw new DMException("Missing UserDB base directory, please set base directory for FileDBSecurityServiceImpl.");
    }
    File userFile = new File(userDatabaseDir, "dm.users.xml");
    try {
        XMLDecoder e = new XMLDecoder(new FileInputStream(userFile));
        Object bean = e.readObject();
        e.close();
        if (bean == null) {
           this.users = new TreeMap<String, DMWebPrincipal>();
        } else {
          Map<String, DMWebPrincipal> map = (Map<String, DMWebPrincipal>)bean;
          this.users = map;
        }
    } catch (FileNotFoundException e) {
      throw new DMException(e.getMessage(), e);
    }
    File passwordFile = new File(userDatabaseDir, "dm.users.password.xml");
    try {
        XMLDecoder e = new XMLDecoder(new FileInputStream(passwordFile));
        Object bean = e.readObject();
        e.close();
        if (bean == null) {
           this.passwords = new HashMap<String, String>();
        } else {
          Map<String, String> map = (Map<String, String>)bean;
          this.passwords = map;
        }
    } catch (FileNotFoundException e) {
      throw new DMException(e.getMessage(), e);
    }
  }
  

  // Implements SecurityService Interface ------------------------------------------------------
  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.security.SecurityService#authenticate(java.lang.String,
   *      java.lang.String)
   */
  public DMWebPrincipal authenticate(String username, String password) throws AuthenticationException {
    DMWebPrincipal user = (DMWebPrincipal) users.get(username);
    if (user == null) {
       throw new AuthenticationException("Unknown user");
    }
    String realPassword = passwords.get(username);
    boolean passwordIsValid = false;
    if (realPassword != null && realPassword.equals(password)) {
       passwordIsValid = true;
    }
    if (!passwordIsValid) {
       throw new AuthenticationException("Invalid password");
    }
    return user;
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.security.SecurityService#initilize()
   */
  public void initilize() throws DMException {
    // Load users from File DB.
    this.load();
  }

  // Public methods ----------------------------------------------------------------------------
  
  /* (non-Javadoc)
   * @see com.npower.dm.security.SecurityService#list()
   */
  public List<DMWebPrincipal> list() throws DMException {
    List<DMWebPrincipal> result = new ArrayList<DMWebPrincipal>();
    for (String username: this.users.keySet()) {
        DMWebPrincipal user = this.users.get(username);
        result.add(user);
    }
    return result;
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.security.SecurityService#add(com.npower.dm.security.DMWebPrincipal)
   */
  public void add(DMWebPrincipal user) throws DMException {
    if (user == null) {
       return;
    }
    if (this.users.containsKey(user.getUsername())) {
       throw new DMException("User exists: " + user.getUsername());
    }
    this.users.put(user.getUsername(), user);
    this.save();
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.security.SecurityService#update(com.npower.dm.security.DMWebPrincipal)
   */
  public void update(DMWebPrincipal user) throws DMException {
    if (user == null) {
       return;
    }
    if (!this.users.containsKey(user.getUsername())) {
       throw new DMException("User no exists: " + user.getUsername());
    }
    this.users.put(user.getUsername(), user);
    this.save();
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.security.SecurityService#delete(java.lang.String)
   */
  public void delete(String username) throws DMException {
    if (username == null) {
       return;
    }
    if (!this.users.containsKey(username)) {
       throw new DMException("User no exists: " + username);
    }
    this.users.remove(username);
    this.save();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.security.SecurityService#updatePassword(com.npower.dm.security.DMWebPrincipal, java.lang.String)
   */
  public void updatePassword(DMWebPrincipal user, String password) throws DMException {
    if (user == null) {
       return;
    }
    if (!this.users.containsKey(user.getUsername())) {
       throw new DMException("User no exists: " + user.getUsername());
    }
    this.passwords.put(user.getUsername(), password);
    this.save();
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.security.SecurityService#get(java.lang.String)
   */
  public DMWebPrincipal get(String username) throws DMException {
    if (username == null) {
       return null;
    }
    return this.users.get(username);
  }
  
  public static void main(String[] args) {
    try {
        FileDBSecurityServiceImpl service = new FileDBSecurityServiceImpl();
        service.save();
    } catch (DMException e) {
      log.error("Error in FileDBSecurityServiceImpl", e);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.security.SecurityService#setProperties(java.util.Properties)
   */
  public void setProperties(Properties props) {
    this.properties = props;
  }

}
