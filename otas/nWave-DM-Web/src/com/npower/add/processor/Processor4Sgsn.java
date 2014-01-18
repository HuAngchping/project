/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/add/processor/Processor4Sgsn.java,v 1.3 2008/11/12 04:44:57 zhao Exp $
 * $Revision: 1.3 $
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
package com.npower.add.processor;

import java.io.BufferedReader;
import java.io.Reader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.add.sgsn.core.DataItem4sgsn;
import com.npower.add.sgsn.dispatcher.JobDispatcher;
import com.npower.add.sgsn.parser.DataItemParser;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/11/12 04:44:57 $
 */
public class Processor4Sgsn implements Processor {
  private static Log log = LogFactory.getLog(Processor4Sgsn.class);
  
  private DataItemParser parser = null;
  
  private JobDispatcher jobDispatcher = null;


  /**
   * 
   */
  public Processor4Sgsn() {
    super();
  }

  /**
   * @param parser
   * @param jobDispatcher
   */
  public Processor4Sgsn(DataItemParser parser, JobDispatcher jobDispatcher) {
    super();
    this.parser = parser;
    this.jobDispatcher = jobDispatcher;
  }

  /**
   * @return the parser
   */
  public DataItemParser getParser() {
    return parser;
  }

  
  
  /**
   * @param parser the parser to set
   */
  public void setParser(DataItemParser parser) {
    this.parser = parser;
  }

  /**
   * @return the jobDispatcher
   */
  public JobDispatcher getJobDispatcher() {
    return jobDispatcher;
  }

  /**
   * @param jobDispatcher the jobDispatcher to set
   */
  public void setJobDispatcher(JobDispatcher jobDispatcher) {
    this.jobDispatcher = jobDispatcher;
  }

  /**
   * 处理一个数据包, 所有都成功后，返回true
   * @param reader
   * @return
   * @throws Exception
   */
  public boolean process(Reader reader) throws Exception {
    boolean success = true;
    BufferedReader in = new BufferedReader(reader);
    String line = in.readLine();
    int lineNumber = 1;
    while (line != null) {
          DataItem4sgsn item = this.getParser().parse(lineNumber, line);  
          if (item != null ) {
             this.getJobDispatcher().dispatch(item);
          }
          line = in.readLine();
          lineNumber++;
          
    }
    return success;
  }


}
