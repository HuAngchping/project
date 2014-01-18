/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/chart/ChartCollectorsPlugIn.java,v 1.3 2007/03/04 09:56:24 zhao Exp $
  * $Revision: 1.3 $
  * $Date: 2007/03/04 09:56:24 $
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
package com.npower.dm.chart;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import com.npower.dm.chart.bean.JvmMemoryInfoAccessorBean;
import com.npower.dm.chart.bean.RuntimeInfoAccessorBean;
import com.npower.dm.chart.stats.collectors.JvmMemoryStatsCollectorBean;
import com.npower.dm.chart.stats.collectors.RuntimeStatsCollectorBean;
import com.npower.dm.chart.stats.providers.StandardSeriesProvider;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2007/03/04 09:56:24 $
 */
public class ChartCollectorsPlugIn implements PlugIn {
  
  private static Log log = LogFactory.getLog(ChartCollectorsPlugIn.class);

  /**
   * Attribtue Name of States Collection in ServletContext
   */
  public static final String NAME_STATS_COLLECTION = "statsCollection";
  
  private List<TimerRunnable> processors = new ArrayList<TimerRunnable>();

  /**
   * 
   */
  public ChartCollectorsPlugIn() {
    super();
  }

  /**
   * Create Timer thread and start-up
   * @param statsCollection
   */
  private void createProcessor(StatsCollection statsCollection) {
    // Create RuntimeInfo Collector.
    try {
        RuntimeInfoAccessorBean accessor = new RuntimeInfoAccessorBean();
        RuntimeStatsCollectorBean collector = new RuntimeStatsCollectorBean();
        collector.setMaxSeries(240);
        collector.setRuntimeInfoAccessorBean(accessor);
        collector.setStatsCollection(statsCollection);
        // Interval: 5 seconds
        collector.setInterval(5000);
        // Keep 4 hour data
        collector.setMaxSeries(12 * 60 * 4);
      
        // Start-up Collector.
        TimerRunnable timer = new TimerRunnable(collector);
        this.processors.add(timer);
        
        Thread thread = new Thread(timer);
        thread.start();
    } catch (Exception ex) {
      log.error("Could not initialize RuntimeInfo collector", ex);
    }
    
    // Create JVM Memory Information Collector.
    try {
        JvmMemoryInfoAccessorBean accessor = new JvmMemoryInfoAccessorBean();
        JvmMemoryStatsCollectorBean collector = new JvmMemoryStatsCollectorBean();
        collector.setMaxSeries(240);
        collector.setJvmMemoryInfoAccessor(accessor);
        collector.setStatsCollection(statsCollection);
        // Interval: 5 seconds
        collector.setInterval(5000);
        // Keep 4 hour data
        collector.setMaxSeries(12 * 60 * 4);
      
        // Start-up Collector.
        TimerRunnable timer = new TimerRunnable(collector);
        this.processors.add(timer);
        
        Thread thread = new Thread(timer);
        thread.start();
    } catch (Exception ex) {
      log.error("Could not initialize RuntimeInfo collector", ex);
    }
  }

  /**
   * Create chart providers.
   * @param context
   */
  private void createChartProviders(ServletContext context) {
    // Create a chart provider for OS Memory 
    {
      StandardSeriesProvider mem = new StandardSeriesProvider();
      List<String> statNames = new ArrayList<String>();
      statNames.add("os.memory.committed");
      statNames.add("os.memory.physical");
      mem.setStatNames(statNames);
    
      context.setAttribute("chart_os_memory", mem);
    }
    
    // Create a chart provider for OS Swap 
    {
      StandardSeriesProvider mem = new StandardSeriesProvider();
      List<String> statNames = new ArrayList<String>();
      statNames.add("os.memory.swap");
      mem.setStatNames(statNames);
    
      context.setAttribute("chart_swap_usage", mem);
    }
    
    // Create a chart provider for cpu 
    {
      StandardSeriesProvider mem = new StandardSeriesProvider();
      List<String> statNames = new ArrayList<String>();
      statNames.add("os.cpu");
      mem.setStatNames(statNames);
    
      context.setAttribute("chart_cpu_usage", mem);
    }
    
    // Create a chart provider for JVM Memory Usage: Survivor Space, Perm Gen, Tenured Gen, Code Cache, Total
    {
      StandardSeriesProvider mem = new StandardSeriesProvider();
      List<String> statNames = new ArrayList<String>();
      statNames.add("memory.pool.{0}");
      mem.setStatNames(statNames);
    
      context.setAttribute("chart_memory_usage", mem);
    }
  }
  
  // Implements interface PlugIn methods -----------------------------------------------------------------

  /* (non-Javadoc)
   * @see org.apache.struts.action.PlugIn#destroy()
   */
  public void destroy() {
    for (TimerRunnable timer: this.processors) {
        timer.shutdown();
    }
  }

  /* (non-Javadoc)
   * @see org.apache.struts.action.PlugIn#init(org.apache.struts.action.ActionServlet, org.apache.struts.config.ModuleConfig)
   */
  public void init(ActionServlet arg0, ModuleConfig arg1) throws ServletException {
    try {
        ServletContext context = arg0.getServletContext();
        
        // Create a StatsCollection
        StatsCollection statsCollection = new StatsCollection();
        
        this.createProcessor(statsCollection);
        
        this.createChartProviders(context);
        
        // Save stateCollection into ServletContext.
        context.setAttribute(ChartCollectorsPlugIn.NAME_STATS_COLLECTION, statsCollection);
        
    } catch (Exception e) {
      throw new ServletException("Failure initialize the PersistentManager", e);
    }
  }

}
