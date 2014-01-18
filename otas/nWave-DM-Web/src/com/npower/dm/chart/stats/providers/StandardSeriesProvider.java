/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/chart/stats/providers/StandardSeriesProvider.java,v 1.2 2007/02/13 06:08:37 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2007/02/13 06:08:37 $
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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.XYDataItem;
import org.springframework.web.bind.RequestUtils;

import com.npower.dm.chart.StatsCollection;
import com.npower.dm.util.ServletRequestUtils;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2007/02/13 06:08:37 $
 */
public class StandardSeriesProvider extends AbstractSeriesProvider {

  private List<String> statNames = new ArrayList<String>(2);

  public List getStatNames() {
    return statNames;
  }

  public void setStatNames(List<String> statNames) {
    this.statNames = statNames;
  }

  public void populate(DefaultTableXYDataset dataset, StatsCollection statsCollection, HttpServletRequest request) {
    String seriesParam = RequestUtils.getStringParameter(request, "sp", null);
    for (int i = 0; i < statNames.size(); i++) {
      String statName = (String) statNames.get(i);
      if (seriesParam != null) {
        statName = MessageFormat.format(statName, new Object[] { seriesParam });
      }
      List<XYDataItem> l = statsCollection.getStats(statName);
      if (l != null) {
        String legend = ServletRequestUtils.getStringParameter(request, "s" + (i + 1) + "l", "series" + i);
        dataset.addSeries(toSeries(legend, l));
      }
    }
  }
}
