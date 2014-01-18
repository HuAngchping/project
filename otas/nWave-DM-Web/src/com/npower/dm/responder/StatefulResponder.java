/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/responder/StatefulResponder.java,v 1.2 2008/12/25 10:29:25 chenlei Exp $
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


import java.util.List;

import com.npower.sms.SmsAddress;
import com.npower.sms.SmsException;
import com.npower.sms.SmsMessage;

/**
 * 有状态的回应器
 * @author chenlei
 * @version $Revision: 1.2 $ $Date: 2008/12/25 10:29:25 $
 */

public abstract class StatefulResponder implements Responder {


  private BaseSessionManager sessionManager  = null;
  
  
  // Public methods ------------------------------------------------------------------
  
  /**
   * 
   */
  public StatefulResponder() {
    super();
  }
  
  public BaseSessionManager getSessionManager() {
    return sessionManager;
  }

  public void setSessionManager(BaseSessionManager sessionManager) {
    this.sessionManager = sessionManager;
  }
  
  
  /**
   * 获取当前request的Session
   * @param req
   * @param create
   * @return
   */
  public Session getSession(SMSRequest req, boolean create){
    Session session = this.sessionManager.loadSession(req);
    if (session == null && create) {
       session = this.sessionManager.createSession(req);
    }
    return session;
  }
  
  /**
   * 删除指定的session
   * @param session
   */
  public void removeSession(Session session) {
    this.sessionManager.removeSession(session);
  }
  
  /**
   * 用一个请求三个必要条件构成一个request.
   * @param from
   *        
   * @param message
   * @param to
   * @return
   * 
   */
  public SMSRequest getRequest(SmsAddress from, byte[] message, SmsAddress to){
    SMSRequest request = new SMSRequest(from, message, to);
    return request;    
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.responder.Responder#response(com.npower.sms.SmsAddress, byte[], com.npower.sms.SmsAddress)
   */
  public List<SmsMessage> response(SmsAddress from, byte[] message, SmsAddress serviceID) throws SmsException {
    SMSRequest request = this.getRequest(from, message, serviceID);
    Session session = this.getSession(request, false);
    if (session != null) {
       // Touch access time
       session.touch();
    }
    return this.doResponse(request);
  }
  
  /**
   * 利用Request做回应
   * @param request
   * @return
   * @throws SmsException
   */
  public abstract List<SmsMessage> doResponse(SMSRequest request) throws SmsException;

}
