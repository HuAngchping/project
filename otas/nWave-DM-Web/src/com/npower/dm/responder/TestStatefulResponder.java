/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/responder/TestStatefulResponder.java,v 1.2 2008/12/25 10:29:25 chenlei Exp $
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

import junit.framework.TestCase;

import com.npower.sms.SmsAddress;
import com.npower.sms.SmsException;
import com.npower.sms.SmsMessage;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/12/25 10:29:25 $
 */
public class TestStatefulResponder extends TestCase {

  private class StatefulResponder4Test extends StatefulResponder {
    private Session currentSsession;

    @Override
    public List<SmsMessage> doResponse(SMSRequest request) throws SmsException {
      this.currentSsession = this.getSession(request, false);
      return null;
    }

    public Session getCurrentSsession() {
      return currentSsession;
    }
  }

  private MemorySessionManager MemorySessionManager = null;
  /**
   * @param name
   */
  public TestStatefulResponder(String name) {
    super(name);
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    MemorySessionManager = new MemorySessionManager();
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  public void testGetRequest() throws Exception {
    StatefulResponder responder = new StatefulResponder() {
      @Override
      public List<SmsMessage> doResponse(SMSRequest request) throws SmsException {
        return null;
      }
    };

    String text = "nokia 6120c mms";
    final SmsAddress userMsisdn = new SmsAddress("13801356729");
    
    SMSRequest request = responder.getRequest(userMsisdn, text.getBytes("UTF-8"), new SmsAddress("888"));
    // Test method getRequest()
    assertEquals("13801356729", request.getFrom().getAddress());
    assertEquals(text, new String(request.getMessage(), "UTF-8"));
    assertEquals("888", request.getTo().getAddress());
    
  }

  public void testGetSession4Creation() throws Exception {
    StatefulResponder responder = new StatefulResponder() {
      @Override
      public List<SmsMessage> doResponse(SMSRequest request) throws SmsException {
        return null;
      }
    };

    String text = "nokia 6120c mms";
    final SmsAddress userMsisdn = new SmsAddress("13801356729");
    {
      SMSRequest request = responder.getRequest(userMsisdn, text.getBytes("UTF-8"), new SmsAddress("888"));
      
      // Create a Session
      responder.setSessionManager(this.MemorySessionManager);
      Session session = responder.getSession(request, true);
      session.setAttribute("attr1", "11111");
      // Load attribute immediatelly
      assertEquals("11111", (String)session.getAttribute("attr1"));
    }
    
    {
      SMSRequest request = responder.getRequest(new SmsAddress("13030065786"), text.getBytes("UTF-8"), new SmsAddress("888"));
      
      // Create a Session
      responder.setSessionManager(this.MemorySessionManager);
      Session session = responder.getSession(request, false);
      assertNull(session);
    }
    
    
  }


  public void testRecoverySession() throws Exception {
    StatefulResponder responder = new StatefulResponder() {
      @Override
      public List<SmsMessage> doResponse(SMSRequest request) throws SmsException {
        return null;
      }
    };

    String text = "nokia 6120c mms";
    final SmsAddress userMsisdn = new SmsAddress("13801356729");
    
    SMSRequest request = responder.getRequest(userMsisdn, text.getBytes("UTF-8"), new SmsAddress("888"));
    
    // Create a Session
    responder.setSessionManager(this.MemorySessionManager);
    Session session = responder.getSession(request, true);
    session.setAttribute("attr1", "11111");
    
    // Get next request and recovery session
    SMSRequest request2 = responder.getRequest(userMsisdn, text.getBytes("UTF-8"), new SmsAddress("888"));
    Session session2 = responder.getSession(request2, true);
    assertEquals(session, session2);
    assertEquals("11111", (String)session2.getAttribute("attr1"));
  }

  public void testSessionExpire() throws Exception {
    long expireTimeInSeconds = 10;
    StatefulResponder responder = new StatefulResponder() {
      @Override
      public List<SmsMessage> doResponse(SMSRequest request) throws SmsException {
        return null;
      }
    };
    MemorySessionManager.setExpireTimeInSeconds(expireTimeInSeconds);
    
    String text = "nokia 6120c mms";
    final SmsAddress userMsisdn = new SmsAddress("13801356729");
    
    SMSRequest request = responder.getRequest(userMsisdn, text.getBytes("UTF-8"), new SmsAddress("888"));
    
    // Create a Session A
    responder.setSessionManager(this.MemorySessionManager);
    responder.getSession(request, true);
    //开始线程
    MemorySessionManager.start();
    Thread.sleep((expireTimeInSeconds + 10) * 1000);
    //利用当前request新建的session A已超期，判断用此request loadSession的session2应该为空
    Session session2 = MemorySessionManager.loadSession(request);
    assertNull(session2);
    //手动停止线程
    MemorySessionManager.stop();
  }

  public void testSessionExpire4Touch() throws Exception {
    long expireTimeInSeconds = 10;
    MemorySessionManager.setExpireTimeInSeconds(expireTimeInSeconds);

    StatefulResponder4Test responder = new StatefulResponder4Test();
    responder.setSessionManager(this.MemorySessionManager);

    String text = "nokia 6120c mms";
    final SmsAddress userMsisdn = new SmsAddress("13801356729");
    
    SMSRequest request = responder.getRequest(userMsisdn, text.getBytes("UTF-8"), new SmsAddress("888"));
    
    // Create a Session
    Session session = responder.getSession(request, true);
    session.setAttribute("attr1", "11111");
    MemorySessionManager.start();
    
    Thread.sleep(1000 * (expireTimeInSeconds / 2));
    {    
      // Get next request and recovery session
      responder.response(userMsisdn, text.getBytes("UTF-8"), new SmsAddress("888"));
      Session session2 = responder.getCurrentSsession();
      assertNotNull(session2);
      session2.setAttribute("attr2", "2222");
    }

    Thread.sleep(1000 * (expireTimeInSeconds / 2 + 3));
    {    
      // Get next request and recovery session
      responder.response(userMsisdn, text.getBytes("UTF-8"), new SmsAddress("888"));
      Session session2 = responder.getCurrentSsession();
      assertNotNull(session2);
    }

    //手动停止线程
    MemorySessionManager.stop();
  }

}
