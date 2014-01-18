/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/responder/TestOtaResponder.java,v 1.1 2008/10/22 10:39:36 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/10/22 10:39:36 $
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

import com.npower.dm.AllTests;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.sms.SmsAddress;
import com.npower.sms.SmsMessage;
import com.npower.wap.omacp.OMACPSecurityMethod;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/10/22 10:39:36 $
 */
public class TestOtaResponder extends TestCase {

  private static final String DEFAULT_TARGET_CARRIER = "ChinaMobile";
  private ManagementBeanFactory factory;
  private ModelDetectorImpl modelDetector;
  private MessageParserImpl parser;
  private ProfileDetectorImpl profileDetector;

  /**
   * @param name
   */
  public TestOtaResponder(String name) {
    super(name);
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    this.factory = AllTests.getManagementBeanFactory();
    
    this.modelDetector = new ModelDetectorImpl();
    this.modelDetector.setManagementBeanFactory(this.factory);
    this.profileDetector = new ProfileDetectorImpl();
    this.profileDetector.setManagementBeanFactory(this.factory);
    this.profileDetector.setTargetCarrierExtId(TestOtaResponder.DEFAULT_TARGET_CARRIER);
    
    this.parser = new MessageParserImpl();
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
    this.factory.release();
  }
  
  public void testCase1() throws Exception {
    OtaResponder responder = new OtaResponder();
    responder.setManagementBeanFactory(this.factory);
    responder.setModelDetector(this.modelDetector);
    responder.setParser(parser);
    responder.setProfileDetector(profileDetector);
    responder.setPinType(OMACPSecurityMethod.USERPIN);
    responder.setUserPin("1234");
    
    responder.setTargetCarrier(TestOtaResponder.DEFAULT_TARGET_CARRIER);
    
    String text = "nokia 6120c mms";
    List<SmsMessage> messages = responder.response(new SmsAddress("13801356729"), text.getBytes("UTF-8"), new SmsAddress("888"));
    assertNotNull(messages);
    assertFalse(messages.isEmpty());
  }

}
