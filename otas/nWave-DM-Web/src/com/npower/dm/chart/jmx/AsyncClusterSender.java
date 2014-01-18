/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/chart/jmx/AsyncClusterSender.java,v 1.1 2007/02/13 04:10:40 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2007/02/13 04:10:40 $
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

package com.npower.dm.chart.jmx;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/02/13 04:10:40 $
 */
public class AsyncClusterSender extends SyncClusterSender {
    private long inQueueCounter;
    private long outQueueCounter;
    private long queueSize;
    private long queuedNrOfBytes;

    public long getInQueueCounter() {
        return inQueueCounter;
    }

    public void setInQueueCounter(long inQueueCounter) {
        this.inQueueCounter = inQueueCounter;
    }

    public long getOutQueueCounter() {
        return outQueueCounter;
    }

    public void setOutQueueCounter(long outQueueCounter) {
        this.outQueueCounter = outQueueCounter;
    }

    public long getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(long queueSize) {
        this.queueSize = queueSize;
    }

    public long getQueuedNrOfBytes() {
        return queuedNrOfBytes;
    }

    public void setQueuedNrOfBytes(long queuedNrOfBytes) {
        this.queuedNrOfBytes = queuedNrOfBytes;
    }
}
