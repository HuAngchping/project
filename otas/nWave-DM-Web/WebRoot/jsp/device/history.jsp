<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<table class="entityview">
  <thead>
    <tr>
      <th colspan="2"><bean:message key="page.device.tree.device.title"/></th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th width="150"><bean:message key="device.title.externalId"/></th>
      <td><bean:write name="device" property="externalId" /></td>
    </tr>
    <tr>
      <th><bean:message key="device.title.phonenumber"/></th>
      <td><bean:write name="device" property="subscriberPhoneNumber" /></td>
    </tr>
    <tr>
      <th><bean:message key="device.title.manufacturer"/></th>
      <td><bean:write name="device" property="model.manufacturer.name" /></td>
    </tr>
    <tr>
      <th><bean:message key="device.title.model"/></th>
      <td><bean:write name="device" property="model.name" /></td>
    </tr>
  </tbody>
</table>

<display:table name="histories" id="history" pagesize="10" requestURI="<%=request.getContextPath() + "/device/history.do"%>" class="simple">
  <display:column class="rowNum">
    <c:out value="${history_rowNum}"/>
  </display:column>
  <display:column sortable="true" titleKey="page.device.history.jobType.label" style="white-space: nowrap">
    <bean:message key="meta.job.type.${history.provisionElement.provisionRequest.jobType}.label"/>
  </display:column>
  <display:column sortable="true" titleKey="page.device.history.jobName.label" maxLength="36" style="white-space: nowrap">
    <c:out value="${history.provisionElement.provisionRequest.name}"/>
  </display:column>
  <display:column sortable="true" titleKey="page.device.history.jobStatus.label">
    <bean:message key="meta.job.device.state.${history.state}.label"/>
  </display:column>
  <display:column property="scheduledTime" sortable="true" titleKey="page.device.history.scheduledTime.label" style="white-space: nowrap"/>
  <display:column property="lastUpdatedTime" sortable="true" titleKey="page.device.history.lastUpdatedTime.label" style="white-space: nowrap"/>
  <display:column property="cause" sortable="true" titleKey="page.device.history.cause.label" style="white-space: nowrap"/>
</display:table>
<div class="buttonArea" style="height: 40px;">
    <html:form action="/ViewDevice" method="post">
	  <INPUT type="hidden" name="ID" value="<bean:write name="device" property="ID"/>">
	  <html:submit><bean:message key="page.device.button.backToDeviceView.label"/></html:submit>
    </html:form>
	<html:form action="/device/history" method="post">
	  <INPUT type="hidden" name="deviceID" value="<bean:write name="device" property="ID"/>">
	  <html:submit styleClass="NormalWidthButton"><bean:message key="page.device.button.refresh"/></html:submit>
	</html:form>
</div>
