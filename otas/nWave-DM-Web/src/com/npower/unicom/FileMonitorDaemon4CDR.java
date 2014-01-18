/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/unicom/FileMonitorDaemon4CDR.java,v 1.2 2008/11/05 10:01:21 zhaowx Exp $
 * $Revision: 1.2 $
 * $Date: 2008/11/05 10:01:21 $
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
import java.io.FilenameFilter;
import java.io.Reader;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.npower.add.processor.Processor4CDR;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/11/05 10:01:21 $
 */
public class FileMonitorDaemon4CDR implements Runnable {
  
  private static Log log = LogFactory.getLog(FileMonitorDaemon4CDR.class);
  
  private String directory4req = "/home/otasdm/cdr/request";
  
  private Processor4CDR processor = null;

  private long intervalInSeconds = 2;

  
  /**
   * 
   */
  public FileMonitorDaemon4CDR() {
    super();
  }
  
  /**
   * @param directory
   * @param processor4Sgsn
   * @param intervalInSeconds
   */
  public FileMonitorDaemon4CDR(String directory4req, Processor4CDR processor, long intervalInSeconds) {
    super();
    this.directory4req = directory4req;
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
  public String getDirectory4req() {
    return directory4req;
  }

  /**
   * @param directory the directory to set
   */
  public void setDirectory4req(String directory4req) {
    this.directory4req = directory4req;
  }

  /**
   * @return the processor4Sgsn
   */
  public Processor4CDR getProcessor() {
    return processor;
  }

  /**
   * @param processor4Sgsn the processor4Sgsn to set
   */
  public void setProcessor(Processor4CDR processor) {
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
      File dir = new File(this.getDirectory4req());
      if (!dir.isAbsolute()) {
         dir = new File(System.getProperty("otas.dm.home"), this.getDirectory4req());
      }
      while (true) {
        try {
            File[] files = dir.listFiles(new FilenameFilter(){

              public boolean accept(File dir, String name) {
                if (StringUtils.isNotEmpty(name)) {
                   if (name.toLowerCase().endsWith(".req")) {
                      return true;
                   }
                }
                return false;
              }});
            if (files != null && files.length > 0) {
               for (File file: files) {
                   log.info("start processing new file: " + file.getAbsolutePath());
                   this.processor.setFileName(file.getName());
                   Reader reader = new FileReader(file);                   
                   boolean success = this.processor.process(reader);
                   reader.close();
                   boolean renameSuccess = true;
                   log.info("end of processing new file: " + file.getAbsolutePath());
                   if (success) {
                      renameSuccess = file.renameTo(new File(file.getParent(), file.getName() + ".finished"));
                   } else {
                     renameSuccess = file.renameTo(new File(file.getParent(), file.getName() + ".error"));
                   }
                   log.info("rename file:" + renameSuccess);
               }
            }
        } catch(Exception ex) {
          log.error("Error to monitor directory: " + this.getDirectory4req(), ex);
        } finally {
          try {
            Thread.sleep(this.getIntervalInSeconds() * 1000);
          } catch (InterruptedException e) {
            log.warn("File monitor thread has been interrupted: " + this.getDirectory4req());
            if (log.isDebugEnabled()) {
              log.debug("File monitor thread has been interrupted: " + this.getDirectory4req(), e);
            }
            break;
          }
        }
      }
    } catch (Exception e) {
      log.error("Error to monitor directory: " + this.getDirectory4req(), e);
    } finally {
      this.isRunning = false;
    }
  }

}
