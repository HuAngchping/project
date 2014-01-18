/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/add/sgsn/FileMonitorDaemon4SGSN.java,v 1.3 2008/11/09 09:29:25 zhao Exp $
 * $Revision: 1.3 $
 * $Date: 2008/11/09 09:29:25 $
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
import java.io.FilenameFilter;
import java.io.Reader;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.npower.add.processor.Processor;


/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/11/09 09:29:25 $
 */
public class FileMonitorDaemon4SGSN implements Runnable {
  
  private static Log log = LogFactory.getLog(FileMonitorDaemon4SGSN.class);
  
  private String directory = "/home/otasdm/sgsn";
  
  private Processor processor = null;

  private long intervalInSeconds = 2;

  
  /**
   * 
   */
  public FileMonitorDaemon4SGSN() {
    super();
  }
  
  /**
   * @param directory
   * @param processor4Sgsn
   * @param intervalInSeconds
   */
  public FileMonitorDaemon4SGSN(String directory, Processor processor, long intervalInSeconds) {
    super();
    this.directory = directory;
    this.processor = processor;
    this.intervalInSeconds = intervalInSeconds;
  }

  /**
   * @return the intervalInSeconds
   */
  public long getIntervalInSeconds() {
    return intervalInSeconds;
  }

  /**
   * @param intervalInSeconds the intervalInSeconds to set
   */
  public void setIntervalInSeconds(long intervalInSeconds) {
    this.intervalInSeconds = intervalInSeconds;
  }

  /**
   * @return the directory
   */
  public String getDirectory() {
    return directory;
  }

  /**
   * @param directory the directory to set
   */
  public void setDirectory(String directory) {
    this.directory = directory;
  }

  /**
   * @return the processor4Sgsn
   */
  public Processor getProcessor() {
    return processor;
  }

  /**
   * @param processor4Sgsn the processor4Sgsn to set
   */
  public void setProcessor(Processor processor) {
    this.processor = processor;
  }

  
  
  /* (non-Javadoc)
   * @see java.lang.Runnable#run()
   */
  private boolean isRunning = false;
  public synchronized void run() {
    if (this.isRunning) {
       return;
    }
    this.isRunning = true;
    try {
      File dir = new File(this.getDirectory());
      if (!dir.isAbsolute()) {
         dir = new File(System.getProperty("otas.dm.home"), this.getDirectory());
      }
      while (true) {
        try {
            File[] files = dir.listFiles(new FilenameFilter(){

              public boolean accept(File dir, String name) {
                if (StringUtils.isNotEmpty(name)) {
                   if (name.toLowerCase().endsWith(".txt")) {
                      return true;
                   }
                }
                return false;
              }});
            if (files != null && files.length > 0) {
               for (File file: files) {
                   log.info("start processing new file: " + file.getAbsolutePath());
                   Reader reader = new FileReader(file);                   
                   boolean success = this.processor.process(reader);
                   reader.close();
                   boolean renameSuccess = true;
                   log.info("end of processing new file: " + file.getAbsolutePath());
                   if (success) {
                      File finishedFile = new File(file.getParent(), file.getName() + ".finished." + System.currentTimeMillis());
                      renameSuccess = file.renameTo(finishedFile);
                   } else {
                     File errorFile = new File(file.getParent(), file.getName() + ".error." + System.currentTimeMillis());
                     renameSuccess = file.renameTo(errorFile);
                   }
                   log.info("rename file:" + renameSuccess);
               }
            }
        } catch(Exception ex) {
          log.error("Error to monitor directory: " + this.getDirectory(), ex);
        } finally {
          try {
            Thread.sleep(this.getIntervalInSeconds() * 1000);
          } catch (InterruptedException e) {
            log.warn("File monitor thread has been interrupted: " + this.getDirectory());
            if (log.isDebugEnabled()) {
              log.debug("File monitor thread has been interrupted: " + this.getDirectory(), e);
            }
            break;
          }
        }
      }
    } catch (Exception e) {
      log.error("Error to monitor directory: " + this.getDirectory(), e);
    } finally {
      this.isRunning = false;
    }
  }

}
