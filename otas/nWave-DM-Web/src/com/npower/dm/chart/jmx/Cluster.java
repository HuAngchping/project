/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/chart/jmx/Cluster.java,v 1.1 2007/02/13 04:10:40 zhao Exp $
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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/02/13 04:10:40 $
 */
public class Cluster {
    private String name;
    private String info;
    private String managerClassName;
    private String mcastAddress;
    private String mcastBindAddress;
    private String mcastClusterDomain;
    private long mcastDropTime;
    private long mcastFrequency;
    private int mcastPort;
    private int mcastSoTimeout;
    private int mcastTTL;
    private List members = new ArrayList();
    private int tcpThreadCount;
    private String tcpListenAddress;
    private int tcpListenPort;
    private long tcpSelectorTimeout;
    private long nrOfMsgsReceived;
    private long totalReceivedBytes;
    private long senderAckTimeout;
    private boolean senderAutoConnect;
    private long senderFailureCounter;
    private long senderNrOfRequests;
    private String senderReplicationMode;
    private long senderTotalBytes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getManagerClassName() {
        return managerClassName;
    }

    public void setManagerClassName(String managerClassName) {
        this.managerClassName = managerClassName;
    }

    public String getMcastAddress() {
        return mcastAddress;
    }

    public void setMcastAddress(String mcastAddress) {
        this.mcastAddress = mcastAddress;
    }

    public String getMcastBindAddress() {
        return mcastBindAddress;
    }

    public void setMcastBindAddress(String mcastBindAddress) {
        this.mcastBindAddress = mcastBindAddress;
    }

    public String getMcastClusterDomain() {
        return mcastClusterDomain;
    }

    public void setMcastClusterDomain(String mcastClusterDomain) {
        this.mcastClusterDomain = mcastClusterDomain;
    }


    public List getMembers() {
        return members;
    }

    public void setMembers(List members) {
        this.members = members;
    }

    public String getTcpListenAddress() {
        return tcpListenAddress;
    }

    public void setTcpListenAddress(String tcpListenAddress) {
        this.tcpListenAddress = tcpListenAddress;
    }

    public int getTcpListenPort() {
        return tcpListenPort;
    }

    public void setTcpListenPort(int tcpListenPort) {
        this.tcpListenPort = tcpListenPort;
    }

    public long getTcpSelectorTimeout() {
        return tcpSelectorTimeout;
    }

    public void setTcpSelectorTimeout(long tcpSelectorTimeout) {
        this.tcpSelectorTimeout = tcpSelectorTimeout;
    }

    public long getNrOfMsgsReceived() {
        return nrOfMsgsReceived;
    }

    public void setNrOfMsgsReceived(long nrOfMsgsReceived) {
        this.nrOfMsgsReceived = nrOfMsgsReceived;
    }

    public long getSenderAckTimeout() {
        return senderAckTimeout;
    }

    public void setSenderAckTimeout(long senderAckTimeout) {
        this.senderAckTimeout = senderAckTimeout;
    }

    public boolean isSenderAutoConnect() {
        return senderAutoConnect;
    }

    public void setSenderAutoConnect(boolean senderAutoConnect) {
        this.senderAutoConnect = senderAutoConnect;
    }

    public long getSenderFailureCounter() {
        return senderFailureCounter;
    }

    public void setSenderFailureCounter(long senderFailureCounter) {
        this.senderFailureCounter = senderFailureCounter;
    }

    public long getSenderNrOfRequests() {
        return senderNrOfRequests;
    }

    public void setSenderNrOfRequests(long senderNrOfRequests) {
        this.senderNrOfRequests = senderNrOfRequests;
    }

    public String getSenderReplicationMode() {
        return senderReplicationMode;
    }

    public void setSenderReplicationMode(String senderReplicationMode) {
        this.senderReplicationMode = senderReplicationMode;
    }

    public long getSenderTotalBytes() {
        return senderTotalBytes;
    }

    public void setSenderTotalBytes(long senderTotalBytes) {
        this.senderTotalBytes = senderTotalBytes;
    }

    public long getMcastDropTime() {
        return mcastDropTime;
    }

    public void setMcastDropTime(long mcastDropTime) {
        this.mcastDropTime = mcastDropTime;
    }

    public long getMcastFrequency() {
        return mcastFrequency;
    }

    public void setMcastFrequency(long mcastFrequency) {
        this.mcastFrequency = mcastFrequency;
    }

    public int getMcastPort() {
        return mcastPort;
    }

    public void setMcastPort(int mcastPort) {
        this.mcastPort = mcastPort;
    }

    public int getMcastSoTimeout() {
        return mcastSoTimeout;
    }

    public void setMcastSoTimeout(int mcastSoTimeout) {
        this.mcastSoTimeout = mcastSoTimeout;
    }

    public int getMcastTTL() {
        return mcastTTL;
    }

    public void setMcastTTL(int mcastTTL) {
        this.mcastTTL = mcastTTL;
    }

    public int getTcpThreadCount() {
        return tcpThreadCount;
    }

    public void setTcpThreadCount(int tcpThreadCount) {
        this.tcpThreadCount = tcpThreadCount;
    }

    public long getTotalReceivedBytes() {
        return totalReceivedBytes;
    }

    public void setTotalReceivedBytes(long totalReceivedBytes) {
        this.totalReceivedBytes = totalReceivedBytes;
    }
}
