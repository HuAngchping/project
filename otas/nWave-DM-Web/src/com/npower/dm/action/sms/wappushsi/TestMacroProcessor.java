/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/sms/wappushsi/TestMacroProcessor.java,v 1.2 2008/06/21 02:27:33 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2008/06/21 02:27:33 $
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
package com.npower.dm.action.sms.wappushsi;

import junit.framework.TestCase;

import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.server.engine.EngineConfig;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/06/21 02:27:33 $
 */
public class TestMacroProcessor extends TestCase {
  
  private ManagementBeanFactory factory = null;

  /**
   * @param name
   */
  public TestMacroProcessor(String name) {
    super(name);
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    
    factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
    
    if (factory != null) {
       factory.release();
    }
  }
  
  public void testCase1() throws Exception {
    MacroProcessor processor = new MacroProcessor();
    String content = "http://127.0.0.1:8080/OTAS-DM-MyPortal/wap/software/task.do?softwareID=MSN&msisdn=$(msisdn)&imei=IMEI:357674014003983";

    assertEquals("http://127.0.0.1:8080/OTAS-DM-MyPortal/wap/software/task.do?softwareID=MSN&msisdn=&imei=IMEI:357674014003983", 
                 processor.process(content, null));
    assertEquals(null, processor.process(null, null));
    
    assertEquals("http://127.0.0.1:8080/OTAS-DM-MyPortal/wap/software/task.do?softwareID=MSN&msisdn=13801356729&imei=IMEI:357674014003983",
                 processor.process(content, "13801356729"));
  }

  public void testCase2() throws Exception {
    MacroProcessor processor = new MacroProcessor();
    processor.setFactory(this.factory);
    
    String content = "http://127.0.0.1:8080/OTAS-DM-MyPortal/wap/software/task.do?softwareID=MSN&msisdn=$(msisdn)&imei=$(imei)";

    assertEquals("http://127.0.0.1:8080/OTAS-DM-MyPortal/wap/software/task.do?softwareID=MSN&msisdn=&imei=", 
                 processor.process(content, null));
    assertEquals(null, processor.process(null, null));
    // Exists
    assertEquals("http://127.0.0.1:8080/OTAS-DM-MyPortal/wap/software/task.do?softwareID=MSN&msisdn=13801356729&imei=356255019067370",
                 processor.process(content, "13801356729"));
    // None exists
    assertEquals("http://127.0.0.1:8080/OTAS-DM-MyPortal/wap/software/task.do?softwareID=MSN&msisdn=12345678900&imei=",
        processor.process(content, "12345678900"));
  }

  public void testCase3() throws Exception {
    MacroProcessor processor = new MacroProcessor();
    processor.setFactory(this.factory);
    
    String content = "http://127.0.0.1:8080/OTAS-DM-MyPortal/wap/software/task.do?softwareID=MSN&msisdn=$(msisdn)&imei=$(IMEI)";

    assertEquals("http://127.0.0.1:8080/OTAS-DM-MyPortal/wap/software/task.do?softwareID=MSN&msisdn=&imei=", 
                 processor.process(content, null));
    assertEquals(null, processor.process(null, null));
    // Exists
    assertEquals("http://127.0.0.1:8080/OTAS-DM-MyPortal/wap/software/task.do?softwareID=MSN&msisdn=13801356729&imei=IMEI:356255019067370",
                 processor.process(content, "13801356729"));
    // None exists
    assertEquals("http://127.0.0.1:8080/OTAS-DM-MyPortal/wap/software/task.do?softwareID=MSN&msisdn=12345678900&imei=",
        processor.process(content, "12345678900"));
  }

}
