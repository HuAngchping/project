
<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<table class="entityview">
  <THEAD>
    <TR>
      <TH ><bean:message key="page.device.job.success.message1"/></TH>
    </TR>
  </THEAD>
</table>
<table class="entityview">
  <tbody>
    <tr>
      <th width="150"><bean:message key="device.title.externalId"/></th>
      <td><bean:write name="device" property="externalId"/></td>
    </tr>
    <tr>
      <th><bean:message key="device.title.phonenumber"/></th>
      <td><bean:write name="device" property="subscriberPhoneNumber"/></td>
    </tr>
    <tr>
      <th><bean:message key="device.title.manufacturer"/></th>
      <td><bean:write name="device" property="model.manufacturer.name" /></td>
    </tr>
    <tr>
      <th><bean:message key="device.title.model"/></th>
      <td><bean:write name="device" property="model.name" /></td>
    </tr>
    <tr>
      <th><bean:message key="page.device.job.success.jobID"/></th>
      <td><bean:write name="provisionJob" property="ID"/></td>
    </tr>
  </tbody>
</table>
	
<div class="buttonArea" style="height: 40px;">
  <html:form action="/device/notification/input" method="post">
	  <INPUT type="hidden" name="deviceID" value="<bean:write name="device" property="ID"/>">
	  <html:submit><bean:message key="page.device.job.success.button.notification.label"/></html:submit>
  </html:form>
  <html:form action="/device/Jobs" method="post">
	  <INPUT type="hidden" name="deviceID" value="<bean:write name="device" property="ID"/>">
	  <html:submit><bean:message key="page.device.job.success.button.jobs.label"/></html:submit>
  </html:form>
  <html:form action="/ViewDevice" method="post">
	  <INPUT type="hidden" name="method" value="view">
	  <INPUT type="hidden" name="ID" value="<bean:write name="device" property="ID"/>">
	  <html:submit><bean:message key="page.device.button.backToDeviceView.label"/></html:submit>
  </html:form>
</div>
