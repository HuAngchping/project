/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/chart/bean/RuntimeInfoAccessorBean.java,v 1.1 2007/02/13 04:10:40 zhao Exp $
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

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.modeler.Registry;

import com.npower.dm.chart.jmx.JmxTools;
import com.npower.dm.chart.jmx.RuntimeInformation;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/02/13 04:10:40 $
 */
public class RuntimeInfoAccessorBean {

    private Log logger = LogFactory.getLog(RuntimeInfoAccessorBean.class);

    public RuntimeInformation getRuntimeInformation() throws Exception {
        MBeanServer mBeanServer = new Registry().getMBeanServer();
        RuntimeInformation ri = new RuntimeInformation();

        try {
            ObjectName runtimeOName = new ObjectName("java.lang:type=Runtime");
            ri.setStartTime(JmxTools.getLongAttr(mBeanServer, runtimeOName, "StartTime"));
            ri.setUptime(JmxTools.getLongAttr(mBeanServer, runtimeOName, "Uptime"));
            ri.setVmVendor(JmxTools.getStringAttr(mBeanServer,runtimeOName,"VmVendor"));

            ObjectName osOName = new ObjectName("java.lang:type=OperatingSystem");
            ri.setOsName(JmxTools.getStringAttr(mBeanServer, osOName, "Name"));
            ri.setOsVersion(JmxTools.getStringAttr(mBeanServer, osOName, "Version"));

            if(!ri.getVmVendor().startsWith("IBM Corporation")){
                ri.setTotalPhysicalMemorySize(JmxTools.getLongAttr(mBeanServer, osOName, "TotalPhysicalMemorySize"));
                ri.setCommittedVirtualMemorySize(JmxTools.getLongAttr(mBeanServer, osOName, "CommittedVirtualMemorySize"));
                ri.setFreePhysicalMemorySize(JmxTools.getLongAttr(mBeanServer, osOName, "FreePhysicalMemorySize"));
                ri.setFreeSwapSpaceSize(JmxTools.getLongAttr(mBeanServer, osOName, "FreeSwapSpaceSize"));
                ri.setTotalSwapSpaceSize(JmxTools.getLongAttr(mBeanServer, osOName, "TotalSwapSpaceSize"));
                ri.setProcessCpuTime(JmxTools.getLongAttr(mBeanServer, osOName, "ProcessCpuTime"));
            } else {
                ri.setTotalPhysicalMemorySize(JmxTools.getLongAttr(mBeanServer, osOName, "TotalPhysicalMemory"));
            }

            return ri;
        } catch (Exception e) {
            logger.debug("OS information is unavailable");
            return null;
        }
    }
}
