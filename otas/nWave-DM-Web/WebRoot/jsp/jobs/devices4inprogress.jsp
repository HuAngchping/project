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

<display:table name="devices" id="device" pagesize="<%=BaseAction.getRecordsPerPage(request)%>" requestURI="<%=request.getContextPath() + "/jobs/DevicesInProgress"%>" class="simple">
  <display:column class="rowNum">
    <c:out value="${device_rowNum}"/>
  </display:column>
  <display:column titleKey="device.title.externalId">
    <html:link action="/ViewDevice.do" styleClass="reference_link" target="_blank" paramId="ID" paramName="device" paramProperty="ID">
      <c:out value="${device.externalId}"/>
    </html:link>
  </display:column>
  <display:column titleKey="device.title.phonenumber">
    <html:link action="/ViewDevice.do" styleClass="reference_link" target="_blank" paramId="ID" paramName="device" paramProperty="ID">
      <c:out value="${device.subscriber.phoneNumber}"/>
    </html:link>
  </display:column>
  <%-- 
  <display:column sortable="true" titleKey="device.title.model">
    <html:link action="/ViewModel.do?action=update" styleClass="reference_link" target="_blank" paramId="ID" paramName="device" paramProperty="model.ID"><c:out value="${device.model.manufacturer.name}"/> <c:out value="${device.model.name}"/></html:link>
  </display:column>
  --%>
  <display:column titleKey="page.device.jobs.job.id.label">
    <html:link action="/jobs/JobView.do?action=view" styleClass="reference_link" target="_blank" paramId="jobID" paramName="device" paramProperty="inProgressDeviceProvReq.provisionElement.provisionRequest.ID">
      <c:out value="${device.inProgressDeviceProvReq.provisionElement.provisionRequest.ID}"/>
    </html:link>
  </display:column>
  <display:column titleKey="page.device.jobs.job.name.label" maxLength="10">
    <html:link action="/jobs/JobView.do?action=view" styleClass="reference_link" target="_blank" paramId="jobID" paramName="device" paramProperty="inProgressDeviceProvReq.provisionElement.provisionRequest.ID">
      <c:out value="${device.inProgressDeviceProvReq.provisionElement.provisionRequest.name}"/>
    </html:link>
  </display:column>
  <display:column titleKey="page.device.jobs.job.type.label">
    <bean:message key="meta.job.type.${device.inProgressDeviceProvReq.provisionElement.provisionRequest.jobType}.label"/>
  </display:column>
</display:table>
