/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/responder/TestModelDetectorImpl.java,v 1.1 2008/10/22 00:15:05 zhao Exp $
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

import com.npower.dm.AllTests;
import com.npower.dm.core.Model;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.sms.SmsAddress;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/10/22 00:15:05 $
 */
public class TestModelDetectorImpl extends TestCase {

  private ManagementBeanFactory factory;
  private ModelDetectorImpl detector;
  private MessageParserImpl parser;

  /**
   * @param name
   */
  public TestModelDetectorImpl(String name) {
    super(name);
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    this.factory = AllTests.getManagementBeanFactory();
    this.detector = new ModelDetectorImpl();
    this.detector.setManagementBeanFactory(this.factory);
    this.parser = new MessageParserImpl();
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
    this.factory.release();
  }
  
  /**
   * @throws Exception
   */
  public void testCase1() throws Exception {
    String text = "nokia n80 mms";
    SmsAddress from = new SmsAddress("13801356729");
    SmsAddress serviceId = new SmsAddress("888");
    
    SimpleMessageData data = parser.parse(from, text.getBytes("UTF-8"), serviceId);
    long modelId = this.detector.detect(from, data, serviceId);
    assertTrue(modelId > 0);
    
    ModelBean bean = this.factory.createModelBean();
    Model model = bean.getModelByID(Long.toString(modelId));
    assertNotNull(model);
    assertEquals("NOKIA", model.getManufacturer().getExternalId());
    assertEquals("N80", model.getManufacturerModelId());
  }

  /**
   * @throws Exception
   */
  public void testCase2() throws Exception {
    String text = "Nokia N80 mms";
    SmsAddress from = new SmsAddress("13801356729");
    SmsAddress serviceId = new SmsAddress("888");
    
    SimpleMessageData data = parser.parse(from, text.getBytes("UTF-8"), serviceId);
    long modelId = this.detector.detect(from, data, serviceId);
    assertTrue(modelId > 0);
    
    ModelBean bean = this.factory.createModelBean();
    Model model = bean.getModelByID(Long.toString(modelId));
    assertNotNull(model);
    assertEquals("NOKIA", model.getManufacturer().getExternalId());
    assertEquals("N80", model.getManufacturerModelId());
  }

  /**
   * @throws Exception
   */
  public void testCase3() throws Exception {
    String text = "NOKIA N80 mms";
    SmsAddress from = new SmsAddress("13801356729");
    SmsAddress serviceId = new SmsAddress("888");
    
    SimpleMessageData data = parser.parse(from, text.getBytes("UTF-8"), serviceId);
    long modelId = this.detector.detect(from, data, serviceId);
    assertTrue(modelId > 0);
    
    ModelBean bean = this.factory.createModelBean();
    Model model = bean.getModelByID(Long.toString(modelId));
    assertNotNull(model);
    assertEquals("NOKIA", model.getManufacturer().getExternalId());
    assertEquals("N80", model.getManufacturerModelId());
  }

  /**
   * @throws Exception
   */
  public void testCase4() throws Exception {
    String text = "N80 mms";
    SmsAddress from = new SmsAddress("13801356729");
    SmsAddress serviceId = new SmsAddress("888");
    
    SimpleMessageData data = parser.parse(from, text.getBytes("UTF-8"), serviceId);
    long modelId = this.detector.detect(from, data, serviceId);
    assertTrue(modelId > 0);
    
    ModelBean bean = this.factory.createModelBean();
    Model model = bean.getModelByID(Long.toString(modelId));
    assertNotNull(model);
    assertEquals("NOKIA", model.getManufacturer().getExternalId());
    assertEquals("N80", model.getManufacturerModelId());
  }

  /**
   * @throws Exception
   */
  public void testCase5() throws Exception {
    String text = "N90 mms";
    SmsAddress from = new SmsAddress("13801356729");
    SmsAddress serviceId = new SmsAddress("888");
    
    SimpleMessageData data = parser.parse(from, text.getBytes("UTF-8"), serviceId);
    long modelId = this.detector.detect(from, data, serviceId);
    assertTrue(modelId > 0);
    
    ModelBean bean = this.factory.createModelBean();
    Model model = bean.getModelByID(Long.toString(modelId));
    assertNotNull(model);
    assertEquals("NOKIA", model.getManufacturer().getExternalId());
    assertEquals("N90", model.getManufacturerModelId());
  }

  /**
   * @throws Exception
   */
  public void testCase6() throws Exception {
    String text = "6120 mms";
    SmsAddress from = new SmsAddress("13801356729");
    SmsAddress serviceId = new SmsAddress("888");
    
    SimpleMessageData data = parser.parse(from, text.getBytes("UTF-8"), serviceId);
    long modelId = this.detector.detect(from, data, serviceId);
    assertTrue(modelId > 0);
    
    ModelBean bean = this.factory.createModelBean();
    Model model = bean.getModelByID(Long.toString(modelId));
    assertNotNull(model);
    assertEquals("NOKIA", model.getManufacturer().getExternalId());
    assertEquals("6120c", model.getManufacturerModelId());
  }

  /**
   * @throws Exception
   */
  public void testCase7() throws Exception {
    String text = "61201111 mms";
    SmsAddress from = new SmsAddress("13801356729");
    SmsAddress serviceId = new SmsAddress("888");
    
    SimpleMessageData data = parser.parse(from, text.getBytes("UTF-8"), serviceId);
    long modelId = this.detector.detect(from, data, serviceId);
    assertTrue(modelId <= 0);
  }

}
