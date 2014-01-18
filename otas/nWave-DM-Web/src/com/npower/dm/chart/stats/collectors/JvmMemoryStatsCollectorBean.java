/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/chart/stats/collectors/JvmMemoryStatsCollectorBean.java,v 1.1 2007/02/13 04:10:40 zhao Exp $
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

import java.util.Iterator;
import java.util.List;

import com.npower.dm.chart.bean.JvmMemoryInfoAccessorBean;
import com.npower.dm.chart.jmx.MemoryPool;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/02/13 04:10:40 $
 */
public class JvmMemoryStatsCollectorBean extends BaseStatsCollectorBean {
  private JvmMemoryInfoAccessorBean jvmMemoryInfoAccessor;

  public JvmMemoryInfoAccessorBean getJvmMemoryInfoAccessor() {
    return jvmMemoryInfoAccessor;
  }

  public void setJvmMemoryInfoAccessor(JvmMemoryInfoAccessorBean jvmMemoryInfoAccessor) {
    this.jvmMemoryInfoAccessor = jvmMemoryInfoAccessor;
  }

  public void collect() throws Exception {
    List pools = jvmMemoryInfoAccessor.getPools();
    long time = System.currentTimeMillis();
    for (Iterator it = pools.iterator(); it.hasNext();) {
      MemoryPool pool = (MemoryPool) it.next();
      buildAbsoluteStats("memory.pool." + pool.getName(), pool.getUsed(), time);
    }
  }

}
