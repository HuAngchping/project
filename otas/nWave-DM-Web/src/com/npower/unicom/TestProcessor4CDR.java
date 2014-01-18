/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/unicom/TestProcessor4CDR.java,v 1.2 2008/11/05 08:46:54 zhaowx Exp $
 * $Revision: 1.2 $
 * $Date: 2008/11/05 08:46:54 $
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
package com.npower.unicom;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import junit.framework.TestCase;

import com.npower.add.processor.Processor4CDR;
import com.npower.add.sgsn.dispatcher.CDRDispatcherImpl;
import com.npower.add.sgsn.dispatcher.JobDispatcher;
import com.npower.add.sgsn.parser.DataItemParser;
import com.npower.add.sgsn.parser.DataItemParser4CDR;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.server.engine.EngineConfig;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/11/05 08:46:54 $
 */
public class TestProcessor4CDR extends TestCase {
  ManagementBeanFactory factory = null;
  DataItemParser parser = null;
  JobDispatcher dispatcher = null;
  Processor4CDR processor = null;
  public TestProcessor4CDR(String name) {
    super(name);
  }

  /**
   * @throws java.lang.Exception
   */
  protected void setUp() throws Exception {
    System.setProperty("otas.dm.home", "D:/OTASDM");
    factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
    parser = new DataItemParser4CDR();
    dispatcher = new CDRDispatcherImpl(factory,"ChinaMobile");
    processor = new Processor4CDR(dispatcher,parser,"./cdr/response/right","./cdr/response/bad","./cdr/response/semiwrong");
  }

  /**
   * @throws java.lang.Exception
   */
  protected void tearDown() throws Exception {
  }
   
  public void testCase1() throws Exception {
    FileMonitorDaemon4CDR daemon = new FileMonitorDaemon4CDR("D:/OTASDM/cdr/request", processor, 5);
    
    Thread thread = new Thread(daemon);
    thread.start();
    Thread.sleep(1000);
  }

  public void testCase2() throws Exception {
    Reader reader = new FileReader(new File("D:/OTASDM/cdr/request", "200811021415xxx0001.req"));
    processor.setFileName("200811021415xxx0001.req");
    boolean result = processor.process(reader);
    assertTrue(result);    
  }

}
