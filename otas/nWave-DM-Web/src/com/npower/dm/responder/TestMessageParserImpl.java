/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/responder/TestMessageParserImpl.java,v 1.1 2008/10/22 00:15:05 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2008/10/22 00:15:05 $
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
package com.npower.dm.responder;

import junit.framework.TestCase;

import com.npower.sms.SmsAddress;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/10/22 00:15:05 $
 */
public class TestMessageParserImpl extends TestCase {

  private MessageParser<SimpleMessageData> parser;

  /**
   * @param name
   */
  public TestMessageParserImpl(String name) {
    super(name);
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void setUp() throws Exception {
    super.tearDown();
    parser = new MessageParserImpl();
  }
  
  public void testCase1() throws Exception {

    String text = "nokia n80 mms";
    SmsAddress from = new SmsAddress("13801356729");
    SmsAddress serviceId = new SmsAddress("888");
    
    SimpleMessageData data = parser.parse(from, text.getBytes("UTF-8"), serviceId);
    assertNotNull(data);
    assertEquals("nokia", data.getBrand());
    assertEquals("n80", data.getModel());
    assertEquals("mms", data.getProfileType());
    assertEquals(text, new String(data.getRawMessage()));
  }

  public void testCase2() throws Exception {
    String text = "  nokia   n80  mms  ";
    SmsAddress from = new SmsAddress("13801356729");
    SmsAddress serviceId = new SmsAddress("888");
    
    SimpleMessageData data = parser.parse(from, text.getBytes("UTF-8"), serviceId);
    assertNotNull(data);
    assertEquals("nokia", data.getBrand());
    assertEquals("n80", data.getModel());
    assertEquals("mms", data.getProfileType());
    assertEquals(text, new String(data.getRawMessage(), "UTF-8"));
  }

  public void testCase3() throws Exception {
    // 中文全角空格
    String text = "　　　nokia　　n80　　mms　　";
    SmsAddress from = new SmsAddress("13801356729");
    SmsAddress serviceId = new SmsAddress("888");
    
    SimpleMessageData data = parser.parse(from, text.getBytes("UTF-8"), serviceId);
    assertNotNull(data);
    assertEquals("nokia", data.getBrand());
    assertEquals("n80", data.getModel());
    assertEquals("mms", data.getProfileType());
    assertEquals(text, new String(data.getRawMessage(), "UTF-8"));
  }

  public void testCase4() throws Exception {
    String text = "n80  mms  ";
    SmsAddress from = new SmsAddress("13801356729");
    SmsAddress serviceId = new SmsAddress("888");
    
    SimpleMessageData data = parser.parse(from, text.getBytes("UTF-8"), serviceId);
    assertNotNull(data);
    assertEquals(null, data.getBrand());
    assertEquals("n80", data.getModel());
    assertEquals("mms", data.getProfileType());
    assertEquals(text, new String(data.getRawMessage(), "UTF-8"));
  }


  public void testCase5() throws Exception {
    String text = "mms  ";
    SmsAddress from = new SmsAddress("13801356729");
    SmsAddress serviceId = new SmsAddress("888");
    
    try {
      parser.parse(from, text.getBytes("UTF-8"), serviceId);
      fail("Exptected a exception");
    } catch (InvalidateMessageException e) {
      assertTrue(true);
    }
  }

}
