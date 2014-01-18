/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/chart/jmx/MemoryPool.java,v 1.1 2007/02/13 04:10:40 zhao Exp $
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
public class MemoryPool {
    private String name;
    private long init = 0;
    private long max = 0;
    private long used = 0;
    private long committed = 0;
    private String type;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.id = name != null ? name.replaceAll(" ", "_").toLowerCase() : null;
    }

    public long getInit() {
        return init;
    }

    public void setInit(long init) {
        this.init = init;
    }

    public long getMax() {
        return max;
    }

    public void setMax(long max) {
        this.max = max;
    }

    public long getUsed() {
        return used;
    }

    public void setUsed(long used) {
        this.used = used;
    }

    public long getCommitted() {
        return committed;
    }

    public void setCommitted(long committed) {
        this.committed = committed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUsageScore() {
        return max == 0 ? 0 : (int) (used * 100 / max);
    }

    public String getId() {
        return id;
    }
}
