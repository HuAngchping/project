/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/responder/MemorySessionManager.java,v 1.1 2008/12/25 10:29:25 chenlei Exp $
 * $Revision: 1.1 $
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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 内存方式的SessionManager，启动线程，监听每个超期的Session并删除
 *    下列代码可以构造一个MemorySessionManager:
 *       MemorySessionManager sessionManager = new MemorySessionManager();
 *    下列代码可以开启一个线程：
 *       sessionManager.start();
 *    下列代码可以停止一个线程：
 *       sessionManager.stop();  
 * @author chenlei
 * @version $Revision: 1.1 $ $Date: 2008/12/25 10:29:25 $
 */
public class MemorySessionManager implements Runnable, BaseSessionManager {

  private static Log log = LogFactory.getLog(MemorySessionManager.class);
  
  private Map<String, Session> sessionMap = Collections.synchronizedMap(new HashMap<String, Session>());
  
  private Thread thread = null;

  private long expireTimeInSeconds = 0;


  /**
   * 
   */
  public MemorySessionManager() {
    super();
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.responder.BaseSessionManager#getExpireTimeInSeconds()
   */
  public long getExpireTimeInSeconds() {
    return expireTimeInSeconds;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.responder.BaseSessionManager#setExpireTimeInSeconds(long)
   */
  public void setExpireTimeInSeconds(long expireTimeInSeconds) {
    this.expireTimeInSeconds = expireTimeInSeconds;
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.responder.BaseSessionManager#createSession(com.npower.dm.responder.SMSRequest)
   */
  public synchronized Session createSession(SMSRequest req) {
    Session session = new Session(req.getFrom().getAddress());
    sessionMap.put(req.getFrom().getAddress(), session);
    return session;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.responder.BaseSessionManager#loadSession(com.npower.dm.responder.SMSRequest)
   */
  public synchronized Session loadSession(SMSRequest req) {
    Session session = (Session)sessionMap.get(req.getFrom().getAddress());
    return session;
  }
 
  /* (non-Javadoc)
   * @see com.npower.dm.responder.BaseSessionManager#removeSession(com.npower.dm.responder.Session)
   */
  public synchronized void removeSession (Session session) {
    sessionMap.remove(session.getSessionId());
  }
  
  /**
   * 开启线程
   */
  public void start() {
    if (thread == null) {
       this.thread = new Thread(this);
       thread.start();
    }
  }
  
  /**
   * 结束线程
   */
  public void stop() {
    if (thread == null)
      return;
    thread.interrupt();
  }
  
  public void run() {
    try {
      while (true) {
        processExpires();
        Thread.sleep(1000);
      }
    } catch (InterruptedException e) {
      log.info(this.getClass().getName() + " thread has been interrupted!");
    }
  }
 
  /**
   * 便利所有Session，并找出超期的删除。
   */
  private synchronized void processExpires() {
    for (Session session: this.sessionMap.values()) {
      long now = System.currentTimeMillis();
      if (now - session.getLastAccessTime().getTime() >= 1000 * this.getExpireTimeInSeconds()) {
        this.removeSession(session);
      } else {
        continue;
      } 
    }
  } 
}
