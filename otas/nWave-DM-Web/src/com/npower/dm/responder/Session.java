/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/responder/Session.java,v 1.2 2008/12/25 10:29:25 chenlei Exp $
 * $Revision: 1.2 $
 * $Date: 2008/12/25 10:29:25 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2008 NPower Network Software Ltd.  All rights reserved.
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

package com.npower.dm.responder;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

/**
 * ��ĳ������������һ�������л���Session
 *    ���д�����Թ���һ��Session
 *        Session session = new Session("XXX");
 *    ���д���������úͻ�ȡ��ɾ��session�����ԣ�
 *        session.setAttribute("XXX", Object);
 *        session.getAttribute("XXX");
 *        session.removeAttribute("XXX");
 * @author chenlei
 * @version $Revision: 1.2 $ $Date: 2008/12/25 10:29:25 $
 */

public class Session implements Serializable{
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Date createdTime = new Date();
  
  private Date lastAccessTime = null;
  
  private String sessionId = null;
  
  private HashMap<String, Object> map = new HashMap<String, Object>();
  
  /**
   * 
   */
  protected Session() {
   super();
  }
  
  public Session(String sessionId) {
    super();
    this.sessionId = sessionId;
    this.lastAccessTime = this.createdTime;
  }

  public String getSessionId() {
    return sessionId;
  }
  
  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public Date getCreatedTime() {
    return createdTime;
  }

  public Date getLastAccessTime() {
    return lastAccessTime;
  }

  /**
   * ����session����
   * @param name
   * @param obj
   */
  public void setAttribute(String name, Object obj) {
    if (name != null && obj != null) {
      map.put(name, obj);
    }
  }
  
  /**
   * ��ȡָ����session����
   * @param name
   * @return
   */
  public Object getAttribute(String name) {
    if (name != null) {
      return map.get(name);
    } else {
      return null;
    } 
  }
  
  /**
   * ɾ��ָ����session����
   * @param name
   */
  public void removeAttribute(String name) {
    map.remove(name);
  }
  
  /**
   * ���Ĵ���StatefulResponder.response�������ʱ��
   */
  public void touch() {
    this.lastAccessTime = new Date();
  }

}
