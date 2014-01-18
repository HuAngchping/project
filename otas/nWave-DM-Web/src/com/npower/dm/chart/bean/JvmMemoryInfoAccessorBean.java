/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/chart/bean/JvmMemoryInfoAccessorBean.java,v 1.1 2007/02/13 04:10:40 zhao Exp $
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
package com.npower.dm.chart.bean;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeDataSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.modeler.Registry;

import com.npower.dm.chart.jmx.JmxTools;
import com.npower.dm.chart.jmx.MemoryPool;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/02/13 04:10:40 $
 */
public class JvmMemoryInfoAccessorBean {

    private Log logger = LogFactory.getLog(this.getClass());

    public List<MemoryPool> getPools() throws Exception {

        List<MemoryPool> memoryPools = new LinkedList<MemoryPool>();
        MBeanServer mBeanServer = new Registry().getMBeanServer();
        Set memoryOPools = mBeanServer.queryMBeans(new ObjectName("java.lang:type=MemoryPool,*"), null);

        //
        // totals
        //
        long totalInit = 0;
        long totalMax = 0;
        long totalUsed = 0;
        long totalCommitted = 0;

        for (Iterator it = memoryOPools.iterator(); it.hasNext();) {
            ObjectInstance oi = (ObjectInstance) it.next();
            ObjectName oName = oi.getObjectName();
            MemoryPool memoryPool = new MemoryPool();
            memoryPool.setName(JmxTools.getStringAttr(mBeanServer, oName, "Name"));
            memoryPool.setType(JmxTools.getStringAttr(mBeanServer, oName, "Type"));

            CompositeDataSupport cd = (CompositeDataSupport) mBeanServer.getAttribute(oName, "Usage");
            //
            // It seems that "Usage" attribute of one of the pools may turn into null intermittently. We better have a
            // deep in the graph then an NPE though.
            //
            if (cd != null) {
                memoryPool.setMax(JmxTools.getLongAttr(cd, "max"));
                memoryPool.setUsed(JmxTools.getLongAttr(cd, "used"));
                memoryPool.setInit(JmxTools.getLongAttr(cd, "init"));
                memoryPool.setCommitted(JmxTools.getLongAttr(cd, "committed"));
            } else {
                logger.error("Oops, JVM problem? "+oName.toString()+" \"Usage\" attribute is NULL!");
            }

            totalInit += memoryPool.getInit();
            totalMax += memoryPool.getMax();
            totalUsed += memoryPool.getUsed();
            totalCommitted += memoryPool.getCommitted();

            memoryPools.add(memoryPool);
        }

        if (!memoryPools.isEmpty()) {
            MemoryPool pool = new MemoryPool();
            pool.setName("Total");
            pool.setType("TOTAL");
            pool.setInit(totalInit);
            pool.setUsed(totalUsed);
            pool.setMax(totalMax);
            pool.setCommitted(totalCommitted);
            memoryPools.add(pool);
        }

        return memoryPools;

    }
}
