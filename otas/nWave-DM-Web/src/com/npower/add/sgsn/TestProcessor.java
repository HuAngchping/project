/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/add/sgsn/TestProcessor.java,v 1.9 2008/11/12 04:44:57 zhao Exp $
 * $Revision: 1.9 $
 * $Date: 2008/11/12 04:44:57 $
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
package com.npower.add.sgsn;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;

import junit.framework.TestCase;

import com.npower.add.processor.Processor4Sgsn;
import com.npower.add.sgsn.dispatcher.JobDispatcher;
import com.npower.add.sgsn.dispatcher.JobDispatcherImpl;
import com.npower.add.sgsn.parser.DataItemParser;
import com.npower.add.sgsn.parser.DataItemParser4Sgsn;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.server.engine.EngineConfig;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.9 $ $Date: 2008/11/12 04:44:57 $
 */
public class TestProcessor extends TestCase {
  ManagementBeanFactory factory = null;
  DataItemParser parser = null;
  JobDispatcher dispatcher = null;
  Processor4Sgsn processor4Sgsn = null;
  public TestProcessor(String name) {
    super(name);
  }

  /**
   * @throws java.lang.Exception
   */
  protected void setUp() throws Exception {
    System.setProperty("otas.dm.home", "D:/OTASDM");
    String[] successFlags = {"queued","sent","received","success"};    
    factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
    parser = new DataItemParser4Sgsn("86");
    dispatcher = new JobDispatcherImpl(factory,"ChinaMobile","ChinaMobile.Proxy.CMWAP", 1, successFlags);
    processor4Sgsn = new Processor4Sgsn(parser, dispatcher);
  }

  /**
   * @throws java.lang.Exception
   */
  protected void tearDown() throws Exception {
  }
 
  public void testCase1() throws Exception {
    DataItemParser parser = new DataItemParser4Sgsn();
    JobDispatcherImpl dispatcher = new JobDispatcherImpl();
    dispatcher.setSendIntervalInMinutes(1);
    
    Reader reader = new FileReader(new File("D:/Zhao/MyWorkspace/Lab4SGSN/data", "t-huawei-sgsn-1-20080505140815.txt"));
    
    Processor4Sgsn processor4Sgsn = new Processor4Sgsn();
    processor4Sgsn.setJobDispatcher(dispatcher);
    processor4Sgsn.setParser(parser);
    boolean result = processor4Sgsn.process(reader);
    assertTrue(result);
  }
  
  public void testCase2() throws Exception {
    FileMonitorDaemon4SGSN daemon = new FileMonitorDaemon4SGSN("D:/OTASDM/SGSN", processor4Sgsn, 5);
    
    Thread thread = new Thread(daemon);
    thread.start();

    Thread.sleep(1000);
  }

  /*
  public void testCase4() throws Exception {
    String[] successFlags = {"queued","sent","received","success"};
    Processor4Sgsn processor4Sgsn = new Processor4Sgsn(parser, dispatcher, 1,successFlags){
      protected long getLastSentTime(DataItem4sgsn item) throws DMException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2008, 02, 10, 1, 21, 11);
        return calendar.getTimeInMillis();
      }
    };
    
    Reader reader = new FileReader(new File("D:/OTASDM/SGSN", "t-huawei-sgsn-1-20080505140815.txt"));
    processor4Sgsn.process(reader);
    boolean result = processor4Sgsn.process(reader);
    assertTrue(result);    
  }

  */
}
