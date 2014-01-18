<%@ page language="java"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/otas-dm.tld" prefix="inf" %>

<div class="shadow" style="clear: none;">
	<div class="info">
	    <p>
	        <bean:message key="page.osMemoryInfo.OSName.label"/>:
	        <span class="value">${runtime.osName}</span>
	        <bean:message key="page.osMemoryInfo.OSVersion.label"/>:
	        <span class="value">${runtime.osVersion}</span>
	        <bean:message key="page.osMemoryInfo.totalRAM.label"/>:
	        <span class="value"><inf:volume value="${runtime.totalPhysicalMemorySize}" fractions="2"/></span>
	        <bean:message key="page.osMemoryInfo.freeRAM.label"/>:
	        <span class="value"><inf:volume value="${runtime.freePhysicalMemorySize}" fractions="2"/></span>
	        <bean:message key="page.osMemoryInfo.committedJVMMemory.label"/>:
	        <span class="value"><inf:volume value="${runtime.committedVirtualMemorySize}" fractions="2"/></span>
	        <bean:message key="page.osMemoryInfo.totalSwap.label"/>:
	        <span class="value"><inf:volume value="${runtime.totalSwapSpaceSize}" fractions="2"/></span>
	        <bean:message key="page.osMemoryInfo.freeSwap.label"/>:
	        <span class="value"><inf:volume value="${runtime.freeSwapSpaceSize}" fractions="2"/></span>
	    </p>
	</div>
</div>
