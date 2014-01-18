/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/ChartAction.java,v 1.1 2007/02/13 04:10:40 zhao Exp $
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

package com.npower.dm.action;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DownloadAction;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.ui.RectangleInsets;

import com.npower.dm.chart.ChartCollectorsPlugIn;
import com.npower.dm.chart.StatsCollection;
import com.npower.dm.chart.Utils;
import com.npower.dm.chart.stats.providers.SeriesProvider;
import com.npower.dm.util.ServletRequestUtils;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/02/13 04:10:40 $
 */
public class ChartAction extends DownloadAction {

  // --------------------------------------------------------- Instance
  // Variables

  // --------------------------------------------------------- Methods

  /* (non-Javadoc)
   * @see org.apache.struts.actions.DownloadAction#getStreamInfo(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    // Download a "pdf" file
    String contentType = "image/png";

    //
    // get Series1 Color from the request
    //
    int series1Color = Utils.toIntHex(request.getParameter("s1c"), 0x9bd2fb);
    //
    // get Series1 Outline Color from the request
    //
    int series1OutlineColor = Utils.toIntHex(request.getParameter("s1o"), 0x0665aa);
    //
    // get Series2 Color
    //
    int series2Color = Utils.toIntHex(request.getParameter("s2c"), 0xFF0606);
    //
    // get Series2 Outline Color
    //
    int series2OutlineColor = Utils.toIntHex(request.getParameter("s2o"), 0x9d0000);
    //
    // background color
    //
    int backgroundColor = Utils.toIntHex(request.getParameter("bc"), 0xFFFFFF);
    //
    // grid color
    //
    int gridColor = Utils.toIntHex(request.getParameter("gc"), 0);
    //
    // X axis title
    //
    String xLabel = ServletRequestUtils.getStringParameter(request, "xl", "");
    //
    // Y axis title
    //
    String yLabel = ServletRequestUtils.getStringParameter(request, "yl", "");
    //
    // image width
    //
    int width = ServletRequestUtils.getIntParameter(request, "xz", 800);
    //
    // image height
    //
    int height = ServletRequestUtils.getIntParameter(request, "yz", 400);
    //
    // show legend?
    //
    boolean showLegend = ServletRequestUtils.getBooleanParameter(request, "l", true);
    //
    // Series provider
    //
    String providerName = ServletRequestUtils.getStringParameter(request, "p", null);
    //
    // Chart type
    //
    String chartType = ServletRequestUtils.getStringParameter(request, "ct", "area");


    ServletContext context = this.getServlet().getServletContext();
    StatsCollection statsCollection = (StatsCollection) context.getAttribute(ChartCollectorsPlugIn.NAME_STATS_COLLECTION);
    
    SeriesProvider provider = (SeriesProvider)context.getAttribute(providerName);
    DefaultTableXYDataset ds = new DefaultTableXYDataset();
    provider.populate(ds, statsCollection, request);
    
    //
    // Build series data from the give statistic
    //

    JFreeChart chart = null;
    if ("area".equals(chartType)) {
        chart = ChartFactory.createXYAreaChart("", xLabel, yLabel, ds, PlotOrientation.VERTICAL,
                showLegend, false, false);
    } else if ("stacked".equals(chartType)) {
        chart = ChartFactory.createStackedXYAreaChart("", xLabel, yLabel, ds, PlotOrientation.VERTICAL, showLegend,
                false, false);
    }

    if (chart != null) {
        chart.setAntiAlias(true);
        chart.setBackgroundPaint(new Color(backgroundColor));
        ((XYAreaRenderer) chart.getXYPlot().getRenderer()).setOutline(true);
        chart.getXYPlot().getRenderer().setSeriesPaint(0, new Color(series1Color));
        chart.getXYPlot().getRenderer().setSeriesOutlinePaint(0, new Color(series1OutlineColor));
        chart.getXYPlot().getRenderer().setSeriesPaint(1, new Color(series2Color));
        chart.getXYPlot().getRenderer().setSeriesOutlinePaint(1, new Color(series2OutlineColor));
        chart.getXYPlot().setDomainGridlinePaint(new Color(gridColor));
        chart.getXYPlot().setRangeGridlinePaint(new Color(gridColor));
        chart.getXYPlot().setDomainAxis(0, new DateAxis());
        chart.getXYPlot().setDomainAxis(1, new DateAxis());
        chart.getXYPlot().setInsets(new RectangleInsets(-15, 0, 0, 10));

        response.setHeader("Content-type", "image/png");
        ByteArrayStreamInfo osInfo = new ByteArrayStreamInfo(contentType, ChartUtilities.encodeAsPNG(chart.createBufferedImage(width, height)));
        return osInfo;
    }
    return null;
  }

  protected class ByteArrayStreamInfo implements StreamInfo {

    protected String contentType;

    protected byte[] bytes;

    public ByteArrayStreamInfo(String contentType, byte[] bytes) {
      this.contentType = contentType;
      this.bytes = bytes;
    }

    public String getContentType() {
      return contentType;
    }

    public InputStream getInputStream() throws IOException {
      return new ByteArrayInputStream(bytes);
    }
  }

}
