/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/chart/jmx/JmxTools.java,v 1.1 2007/02/13 04:10:40 zhao Exp $
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

import javax.management.AttributeNotFoundException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/02/13 04:10:40 $
 */
public class JmxTools {

    private static Log logger = LogFactory.getLog(JmxTools.class);

    public static Object getAttribute(MBeanServer mBeanServer, ObjectName oName, String attrName) throws Exception {
        try {
            return mBeanServer.getAttribute(oName, attrName);
        } catch (AttributeNotFoundException e) {
            logger.error(oName + " does not have \""+attrName+"\" attribute");
            return null;
        }
    }

    public static long getLongAttr(MBeanServer mBeanServer, ObjectName oName, String attrName, long defaultValue) {
        try {
            Object o = mBeanServer.getAttribute(oName, attrName);
            return o == null ? defaultValue : ((Long) o).longValue();
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static long getLongAttr(MBeanServer mBeanServer, ObjectName oName, String attrName) throws Exception {
        return ((Long) mBeanServer.getAttribute(oName, attrName)).longValue();
    }

    public static int getIntAttr(MBeanServer mBeanServer, ObjectName oName, String attrName) throws Exception {
        return ((Integer) mBeanServer.getAttribute(oName, attrName)).intValue();
    }

    public static String getStringAttr(MBeanServer mBeanServer, ObjectName oName, String attrName) throws Exception {
        Object o = getAttribute(mBeanServer, oName, attrName);
        return o == null ? null : o.toString();
    }

    public static long getLongAttr(CompositeData cds, String name) {
        Object o = cds.get(name);
        if (o != null && o instanceof Long) {
            return ((Long)o).longValue();
        } else {
            return 0;
        }
    }

    public static String getStringAttr(CompositeData cds, String name) {
        Object o = cds.get(name);
        return o != null ? o.toString() : null;
    }

    public static boolean getBooleanAttr(CompositeData cds, String name) {
        Object o = cds.get(name);

        if (o != null && o instanceof Boolean) {
            return ((Boolean)o).booleanValue();
        } else {
            return false;
        }
    }

    public static int getIntAttr(CompositeData cds, String name, int defaultValue) {
        Object o = cds.get(name);

        if (o != null && o instanceof Integer) {
            return ((Integer)o).intValue();
        } else {
            return defaultValue;
        }
    }

    public static boolean hasAttribute(MBeanServer server, ObjectName mbean, String attrName) throws Exception {
        MBeanInfo info = server.getMBeanInfo(mbean);
        MBeanAttributeInfo[] ai = info.getAttributes();
        for (int i = 0; i < ai.length; i++) {
            if (ai[i].getName().equals(attrName)) {
                return true;
            }
        }
        return false;
    }
}
