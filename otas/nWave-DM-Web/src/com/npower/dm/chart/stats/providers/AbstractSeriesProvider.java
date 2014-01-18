/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/chart/stats/providers/AbstractSeriesProvider.java,v 1.2 2007/06/21 11:12:47 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2007/06/21 11:12:47 $
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
package com.npower.dm.chart.stats.providers;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2007/06/21 11:12:47 $
 */
public abstract class AbstractSeriesProvider implements SeriesProvider {

  protected Log logger = LogFactory.getLog(getClass());

  protected XYSeries toSeries(String legend, List<XYDataItem> stats) {
    XYSeries xySeries = new XYSeries(legend, true, false);
    for (int i = 0; i < stats.size(); i++) {
      xySeries.add((XYDataItem) stats.get(i));
    }
    return xySeries;
  }

}
