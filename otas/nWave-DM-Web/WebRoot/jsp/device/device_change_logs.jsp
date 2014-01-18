<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.npower.dm.action.BaseAction" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/otas-dm.tld" prefix="dm" %>

<display:table name="deviceChangeLogs" id="deviceChangeLog" pagesize="<%=BaseAction.getRecordsPerPage(request)%>" requestURI="<%=request.getContextPath() + "/devices/changeLogs.do"%>" class="simple">
  <display:column class="rowNum">
    <c:out value="${deviceChangeLog_rowNum}"/>
  </display:column>
  <display:column property="lastUpdate" sortable="true" titleKey="device.title.createdTime"/>
  <display:column property="phoneNumber" sortable="true" titleKey="device.title.phonenumber"/>
  <display:column property="imsi" sortable="true" title="IMSI"/>
  <display:column property="imei" sortable="true" titleKey="device.title.externalId"/>
  <display:column sortable="true" titleKey="device.title.model">
    <c:out value="${deviceChangeLog.brand}"/> <c:out value="${deviceChangeLog.model}"/>
  </display:column>
  <display:column property="softwareVersion" sortable="true" titleKey="device.title.firmwareVersion"/>
</display:table>
