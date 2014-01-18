/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/daemon/TestDeviceEnrollMessageDecoder.java,v 1.2 2008/10/31 08:12:13 chenlei Exp $
 * $Revision: 1.2 $
 * $Date: 2008/10/31 08:12:13 $
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
package com.npower.dm.daemon;

import javax.jms.Message;

import com.npower.util.HelperUtil;

import junit.framework.TestCase;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/10/31 08:12:13 $
 */
public class TestDeviceEnrollMessageDecoder extends TestCase {

  /**
   * @param name
   */
  public TestDeviceEnrollMessageDecoder(String name) {
    super(name);
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  /**
   * 中国网通规范测试
   * @throws Exception
   */
  public void testDecode4CNC() throws Exception {
    Message message = new SimpleMessage4Test(DeviceEnrollMessageDecoder4CNC.class.getName(), 
                            "13801356729", 
                            "494D45490A40047014676701FFFFFFFFFFFFFFFFFFFFFFFF00");
    DeviceEnrollMessageDecoder decoder = DeviceEnrollMessageDecoder.getDecoder(message);
    DeviceEnrollMessage enrollMsg = decoder.decode(message);
    assertNotNull(enrollMsg);
    assertEquals("004400741767618", enrollMsg.getImei());
    assertEquals("13801356729", enrollMsg.getMsisdn());
    assertNull(enrollMsg.getBrandName());
    assertNull(enrollMsg.getModelName());
    assertNull(enrollMsg.getImsi());
    assertNull(enrollMsg.getSoftwareVersion());
  }

  /**
   * 中国移动规范测试
   * @throws Exception
   */
  public void testDecode4CMCC() throws Exception {
    Message message = new SimpleMessage4Test(DeviceEnrollMessageDecoder4CMCC.class.getName(), 
                           "13801356729", 
                           "06050442664266494D45493A3030343430313032303835383035322F4D6F746F726F6C612F4D4F544F524F4B522045362F523533335F475F31312E31302E323852");
    DeviceEnrollMessageDecoder decoder = DeviceEnrollMessageDecoder.getDecoder(message);
    DeviceEnrollMessage enrollMsg = decoder.decode(message);
    assertNotNull(enrollMsg);
    assertEquals("IMEI:004401020858052", enrollMsg.getImei());
    assertEquals("13801356729", enrollMsg.getMsisdn());
    assertEquals("Motorola", enrollMsg.getBrandName());
    assertEquals("MOTOROKR E6", enrollMsg.getModelName());
    assertNull(enrollMsg.getImsi());
    assertEquals("R533_G_11.10.28R", enrollMsg.getSoftwareVersion());
  }

  /**
   * 中国联通规范测试
   * @throws Exception
   */
  public void testDecode4UniCom() throws Exception {
    String text = "460010010989258 3555740130106724 NOKIA 6120c RM.1.1.1.2";
    byte[] raw = text.getBytes("iso8859-1");
    String encoded = HelperUtil.bytesToHexString(raw);
    Message message = new SimpleMessage4Test(DeviceEnrollMessageDecoder4UniCom.class.getName(), 
                           "13801356729", 
                           encoded);
    DeviceEnrollMessageDecoder decoder = DeviceEnrollMessageDecoder.getDecoder(message);
    DeviceEnrollMessage enrollMsg = decoder.decode(message);
    assertNotNull(enrollMsg);
    assertEquals("3555740130106724", enrollMsg.getImei());
    assertEquals("13801356729", enrollMsg.getMsisdn());
    assertEquals("NOKIA", enrollMsg.getBrandName());
    assertEquals("6120c", enrollMsg.getModelName());
    assertEquals("460010010989258", enrollMsg.getImsi());
    assertEquals("RM.1.1.1.2", enrollMsg.getSoftwareVersion());
  }

}
