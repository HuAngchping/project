/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/chart/TimerRunnable.java,v 1.2 2007/03/04 09:56:24 zhao Exp $
  * $Revision: 1.2 $
  * $Date: 2007/03/04 09:56:24 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2007 NPower Network Software Ltd.  All rights reserved.
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
package com.npower.dm.chart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.dm.chart.stats.collectors.Collector;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2007/03/04 09:56:24 $
 */
public class TimerRunnable implements Runnable {
  
  private static Log log = LogFactory.getLog(TimerRunnable.class);

  private Collector collector;
  
  private boolean stop = true;

  /**
   * Default constractuor
   */
  public TimerRunnable(Collector collector) {
    this.collector = collector;
  }
  
  public void shutdown() {
    this.stop = true;
  }

  /* (non-Javadoc)
   * @see java.lang.Runnable#run()
   */
  public void run() {
    this.stop = false;
    try {
        if (this.collector == null) {
           return;
        }
        if (this.collector.getInterval() == 0) {
           throw new Exception("The interval is 0, the timer had been stopped!");
        }
        while (!this.stop) {
              this.collector.collect();
              Thread.sleep(this.collector.getInterval());
        }
    } catch (InterruptedException e) {
      log.error(e.getMessage(), e);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

}
