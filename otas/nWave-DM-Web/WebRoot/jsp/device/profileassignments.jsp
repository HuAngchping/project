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

<TABLE class=table_box cellSpacing=0 cellPadding=0 width="100%" border="0">
  <TBODY>
  <TR>
    <TD class=table_box_heading width="100%" colSpan=5><font color="red"><b>.:</b></font> <bean:message key="page.device.job.reAssignment.message"/></TD>
  </TR>
  <TR>
    <TD>
	<display:table name="profileAssignments" id="record" pagesize="<%=BaseAction.getRecordsPerPage(request)%>" requestURI="<%=request.getContextPath() + "/device/ProfileAssignments.do"%>" class="simple_inline">
	  <display:column class="rowNum">
	    <c:out value="${record_rowNum}"/>
	  </display:column>
	  <display:column property="profileConfig.name" titleKey="profile.config.name.label" />
	  <display:column property="profileRootNodePath" titleKey="page.device.job.reAssignment.profileRootNodePath.label" />
	  <display:column property="lastSentToDevice" titleKey="page.device.job.reAssignment.lastSentToDevice.label" />
	  <display:column>
	    <a href="<html:rewrite page='/device/job/dm/EditProfileAssignment.do'/>?action=update&value(ID)=<c:out value='${record.ID}'/>&value(profileID)=<c:out value='${record.profileConfig.ID}'/>&value(deviceID)=<c:out value='${device.ID}'/>"><bean:message key="page.device.job.reAssignment.label"/></a>
	    <c:if test="${not empty record.profileRootNodePath}" >
	    | 
	    <a href="<html:rewrite page='/device/job/RemoveProfileAssignment.do'/>?ID=<c:out value='${record.ID}'/>&profileID=<c:out value='${record.profileConfig.ID}'/>&deviceID=<c:out value='${device.ID}'/>" onclick='javascript: return confirm("<bean:message key="message.delete.areYourSureDelete"/>");'><bean:message key="page.button.remove.label"/></a> 
	    </c:if>
	  </display:column>
	</display:table>
    </TD>
  </TR>
  </TBODY>
</TABLE>

<div class="buttonArea" style="height: 40px;">
  <html:form action="/ViewDevice" method="post">
	  <INPUT type="hidden" name="ID" value="<bean:write name="device" property="ID"/>">
	  <html:submit><bean:message key="page.device.button.backToDeviceView.label"/></html:submit>
  </html:form>
  <html:form action="/device/Jobs" method="post">
	  <INPUT type="hidden" name="deviceID" value="<bean:write name="device" property="ID"/>">
	  <html:submit><bean:message key="page.device.job.success.button.jobs.label"/></html:submit>
  </html:form>
</div>
