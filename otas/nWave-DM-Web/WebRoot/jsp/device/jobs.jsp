<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.npower.dm.core.Device" %>
<%@ page import="com.npower.dm.core.ProvisionJob" %>
<%@ page import="com.npower.dm.core.ProvisionJobStatus" %>
<%@ page import="com.npower.dm.management.ProvisionJobBean" %>
<%@ page import="com.npower.dm.management.ManagementBeanFactory" %>
<%@ page import="com.npower.dm.action.PersistentManager" %>

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
      <th><bean:message key="device.title.model"/></th>
      <td><bean:write name="device" property="model.manufacturer.name" /> <bean:write name="device" property="model.name" /></td>
    </tr>
  </tbody>
</table>

<display:table name="jobs" id="job" pagesize="10" requestURI="<%=request.getContextPath() + "/device/Jobs.do"%>" class="simple">
  <display:column class="rowNum" 
                  href="<%=request.getContextPath() + "/EditDevice.do?action=update"%>" paramId="ID" paramProperty="ID">
    <c:out value="${job_rowNum}"/>
  </display:column>
  <display:column property="ID" titleKey="page.device.jobs.job.id.label"/>
  <display:column titleKey="page.device.jobs.job.type.label">
    <bean:message key="meta.job.type.${job.jobType}.label"/>
  </display:column>
  <display:column property="name" sortable="true" titleKey="page.device.jobs.job.name.label" maxLength="16"/>
  <display:column titleKey="page.device.jobs.job.mode.label">
    <bean:message key="meta.job.mode.${job.jobMode}.label"/>
  </display:column>
<%--
  <display:column sortable="true" titleKey="page.device.jobs.job.status.label">
    <bean:message key="meta.job.state.${job.state}.label"/>
  </display:column>
  <display:column property="jobTypeForDisplay" titleKey="page.device.jobs.job.type.label"/>
  <display:column property="targetType" titleKey="page.device.jobs.job.targetType.label"/>
  <display:column property="description" titleKey="page.device.jobs.job.description.label"/>
--%>  
<%
// Load ProvisionJobStatus
ManagementBeanFactory mFactory = PersistentManager.getManagementBeanFactory(request);
ProvisionJobBean jobBean = mFactory.createProvisionJobBean();
Device device = (Device)request.getAttribute("device");
ProvisionJob pJob = (ProvisionJob)pageContext.getAttribute("job");
if (pJob != null) {
   ProvisionJobStatus jobStatus = jobBean.getProvisionJobStatus(pJob, device);
   pageContext.setAttribute("jobStatus", jobStatus);
}
%>
  <display:column titleKey="page.device.jobs.job.status.label">
	<bean:message key="meta.job.device.state.${jobStatus.state}.label"/>
  </display:column>
  <display:column property="scheduledTime" titleKey="page.device.jobs.job.scheduledTime.label"/>
  <display:column>
    <a href="<html:rewrite page='/device/JobView.do'/>?action=update&jobID=<c:out value='${job.ID}'/>&deviceID=<c:out value='${device.ID}'/>"><bean:message key="page.button.view.label"/></a> | 
    <a href="<html:rewrite page='/device/RemoveJob.do'/>?action=remove&jobID=<c:out value='${job.ID}'/>&deviceID=<c:out value='${device.ID}'/>" onclick='javascript: return confirm("<bean:message key="message.delete.areYourSureDelete"/>");'><bean:message key="page.button.remove.label"/></a>
  </display:column>
</display:table>
<div class="buttonArea" style="height: 40px;">
	<html:form action="/device/EditJob">
	  <bean:define id="deviceID" name="device" property="ID"></bean:define>
	  <INPUT type="hidden" name="deviceID" value="<bean:write name="deviceID"/>">
	  <html:submit><bean:message key="page.device.button.createJob"/></html:submit>
	</html:form>
    <html:form action="/ViewDevice" method="post">
	  <INPUT type="hidden" name="ID" value="<bean:write name="device" property="ID"/>">
	  <html:submit><bean:message key="page.device.button.backToDeviceView.label"/></html:submit>
    </html:form>
	<html:form action="/device/Jobs" method="post">
	  <INPUT type="hidden" name="deviceID" value="<bean:write name="deviceID"/>">
	  <html:submit styleClass="NormalWidthButton"><bean:message key="page.device.button.refreshJobs"/></html:submit>
	</html:form>
</div>
