/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/unicom/CDRsCollectDaemonPlugIn.java,v 1.2 2008/11/09 09:29:25 zhao Exp $
 * $Revision: 1.2 $
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

package com.npower.unicom;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;
import com.npower.add.processor.Processor4CDR;
import com.npower.add.sgsn.dispatcher.CDRDispatcherImpl;
import com.npower.add.sgsn.dispatcher.JobDispatcher;
import com.npower.add.sgsn.parser.DataItemParser;
import com.npower.add.sgsn.parser.DataItemParser4CDR;
import com.npower.common.plugins.AbstractDisablePlugIn;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.server.engine.EngineConfig;

/**
 * @author Zhao wanxiang
 * @version $Revision: 1.2 $ $Date: 2008/11/09 09:29:25 $
 */

public class CDRsCollectDaemonPlugIn extends AbstractDisablePlugIn implements PlugIn {

  private static Log log = LogFactory.getLog(CDRsCollectDaemonPlugIn.class);

  private String directory4req = "./crd";
  private String directory4ResRight = "./cdr/response/right";
  private String directory4ResBad = "./cdr/response/bad";
  private String directory4ResSemiwrong = "./cdr/response/semiwrong";  
  private int intervalInSeconds = 2;
  private boolean enable = true;
  private String carrierExternalID = null;
  private ManagementBeanFactory factory;
  private Thread thread;

  /**
   * 
   */
  public CDRsCollectDaemonPlugIn() {
    super();
  }

  
  
  public String getDirectory4req() {
    return directory4req;
  }



  public void setDirectory4req(String directory4req) {
    this.directory4req = directory4req;
  }

  

  public String getDirectory4ResRight() {
    return directory4ResRight;
  }



  public void setDirectory4ResRight(String directory4ResRight) {
    this.directory4ResRight = directory4ResRight;
  }



  public String getDirectory4ResBad() {
    return directory4ResBad;
  }



  public void setDirectory4ResBad(String directory4ResBad) {
    this.directory4ResBad = directory4ResBad;
  }



  public String getDirectory4ResSemiwrong() {
    return directory4ResSemiwrong;
  }



  public void setDirectory4ResSemiwrong(String directory4ResSemiwrong) {
    this.directory4ResSemiwrong = directory4ResSemiwrong;
  }



  public int getIntervalInSeconds() {
    return intervalInSeconds;
  }



  public void setIntervalInSeconds(int intervalInSeconds) {
    this.intervalInSeconds = intervalInSeconds;
  }



  public boolean isEnable() {
    return enable;
  }



  public void setEnable(boolean enable) {
    this.enable = enable;
  }



  public String getCarrierExternalID() {
    return carrierExternalID;
  }



  public void setCarrierExternalID(String carrierExternalID) {
    this.carrierExternalID = carrierExternalID;
  }



  public ManagementBeanFactory getFactory() {
    return factory;
  }



  public void setFactory(ManagementBeanFactory factory) {
    this.factory = factory;
  }



  public Thread getThread() {
    return thread;
  }



  public void setThread(Thread thread) {
    this.thread = thread;
  }



  /* (non-Javadoc)
   * @see org.apache.struts.action.PlugIn#destroy()
   */
  public void destroy() {
    if (!this.enable) {
      return;
   }
   try {
       if (this.thread != null) {
          this.thread.interrupt();
       }
   } catch (Exception ex) {
     log.info("failure to destroy CRD File Monitor Daemon", ex);
   } finally {
     if (this.factory != null) {
        this.factory.release();
     }
   }
  }

  /* (non-Javadoc)
   * @see org.apache.struts.action.PlugIn#init(org.apache.struts.action.ActionServlet, org.apache.struts.config.ModuleConfig)
   */
  public void init(ActionServlet servlet, ModuleConfig config) throws ServletException {
    if (this.isEnable()) {
      log.info("Starting CRD File Monitor Daemon ...");
   } else {
     log.info("CRD File Monitor Daemon Disabled!");
     return;
   }
   factory = null;
   try {
     factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());

     DataItemParser parser = new DataItemParser4CDR();     
     JobDispatcher cdrDispatcher = new CDRDispatcherImpl(factory, carrierExternalID);
     Processor4CDR processor4CDR = new Processor4CDR(cdrDispatcher,parser,directory4ResRight,directory4ResBad,directory4ResSemiwrong);

     FileMonitorDaemon4CDR daemon = new FileMonitorDaemon4CDR(directory4req, processor4CDR, this.getIntervalInSeconds());
     
     thread = new Thread(daemon);
     thread.start();

     log.info("CRD File Monitor Daemon has been started.");
   } catch (Exception e) {
     log.error("failure to initialize " + this.getClass().getCanonicalName(), e);
   } finally {
   }

  }

}
