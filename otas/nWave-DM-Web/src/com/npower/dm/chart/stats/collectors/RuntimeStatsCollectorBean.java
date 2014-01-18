/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/chart/stats/collectors/RuntimeStatsCollectorBean.java,v 1.1 2007/02/13 04:10:40 zhao Exp $
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

package com.npower.dm.chart.stats.collectors;

import com.npower.dm.chart.bean.RuntimeInfoAccessorBean;
import com.npower.dm.chart.jmx.RuntimeInformation;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/02/13 04:10:40 $
 */
public class RuntimeStatsCollectorBean extends BaseStatsCollectorBean {
    private RuntimeInfoAccessorBean runtimeInfoAccessorBean;

    public RuntimeInfoAccessorBean getRuntimeInfoAccessorBean() {
        return runtimeInfoAccessorBean;
    }

    public void setRuntimeInfoAccessorBean(RuntimeInfoAccessorBean runtimeInfoAccessorBean) {
        this.runtimeInfoAccessorBean = runtimeInfoAccessorBean;
    }

    public void collect() throws Exception {
        RuntimeInformation ri = runtimeInfoAccessorBean.getRuntimeInformation();
        if (ri != null) {
            long time = System.currentTimeMillis();
            buildAbsoluteStats("os.memory.committed", ri.getCommittedVirtualMemorySize()/1024, time);
            buildAbsoluteStats("os.memory.physical", (ri.getTotalPhysicalMemorySize() - ri.getFreePhysicalMemorySize())/1024, time);
            buildAbsoluteStats("os.memory.swap", (ri.getTotalSwapSpaceSize() - ri.getFreeSwapSpaceSize())/1024, time);
            //
            // processCpuTime is in nano-seconds, to build timePercentageStats both time parameters have to use
            // in the same units.
            //
            buildTimePercentageStats("os.cpu", ri.getProcessCpuTime() / 1000000, time);
        }
    }
}
