/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/chart/stats/collectors/BaseStatsCollectorBean.java,v 1.1 2007/02/13 04:10:40 zhao Exp $
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

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jfree.data.xy.XYDataItem;

import com.npower.dm.chart.StatsCollection;
import com.npower.dm.chart.Utils;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/02/13 04:10:40 $
 */
public abstract class BaseStatsCollectorBean implements Collector {

  private StatsCollection     statsCollection;

  /**
   * Maxium data in cache, default is 240 data entries.
   */
  private int                 maxSeries    = 240;
  
  /**
   * Default: 30 seconds.
   */
  private long                interval     = 30000;

  private Map<String, Object> previousData = new TreeMap<String, Object>();

  public StatsCollection getStatsCollection() {
    return statsCollection;
  }

  public void setStatsCollection(StatsCollection statsCollection) {
    this.statsCollection = statsCollection;
  }

  public int getMaxSeries() {
    return maxSeries;
  }

  public void setMaxSeries(int maxSeries) {
    this.maxSeries = maxSeries;
  }

  /**
   * @return the interval
   */
  public long getInterval() {
    return interval;
  }

  /**
   * @param interval the interval to set
   */
  public void setInterval(long interval) {
    this.interval = interval;
  }

  protected void buildDeltaStats(String name, long value) {
    buildDeltaStats(name, value, System.currentTimeMillis());
  }

  protected void buildDeltaStats(String name, long value, long time) {
    if (statsCollection != null) {
      buildAbsoluteStats(name, value - Utils.toLong((Long) previousData.get(name), -1), time);
      previousData.put(name, new Long(value));
    }
  }

  protected void buildAbsoluteStats(String name, long value) {
    buildAbsoluteStats(name, value, System.currentTimeMillis());
  }

  protected void buildAbsoluteStats(String name, long value, long time) {
    List<XYDataItem> stats = statsCollection.getStats(name);
    if (stats == null) {
      statsCollection.newStats(name, maxSeries);
    } else {
      stats.add(new XYDataItem(time, value));
      houseKeepStats(stats);
    }

  }

  private class Entry {
    long time;

    long value;
  }

  /**
   * If there is a value indicating the accumulated amount of time spent on
   * something it is possible to build a series of values representing the
   * percentage of time spent on doing something. For example:
   * 
   * at point T1 the system has spent A milliseconds performing tasks at point
   * T2 the system has spent B milliseconds performing tasks
   * 
   * so between in a timeframe T2-T1 the system spent B-A milliseconds being
   * busy. Thus (B - A)/(T2 - T1) * 100 is the percentage of all time the system
   * spent being busy.
   * 
   * @param name
   * @param value
   *          time in milliseconds
   * @param time
   */
  protected void buildTimePercentageStats(String name, long value, long time) {
    Entry entry = (Entry) previousData.get(name);
    if (entry == null) {
      entry = new Entry();
      entry.value = value;
      entry.time = time;
      previousData.put(name, entry);
    } else {
      double valueDelta = value - entry.value;
      double timeDelta = time - entry.time;
      double statValue = valueDelta * 100 / timeDelta;
      List<XYDataItem> stats = statsCollection.getStats(name);
      if (stats == null)
        stats = statsCollection.newStats(name, maxSeries);
      stats.add(stats.size(), new XYDataItem(time, statValue));
      houseKeepStats(stats);
    }
  }

  private void houseKeepStats(List stats) {
    while (stats.size() > maxSeries)
      stats.remove(0);
  }

}
