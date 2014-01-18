<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

  <jsp:include page="/jsp/device/job/schedule_calendar.jsp"></jsp:include>
	<tr>
		<th><bean:message key="page.job.notification.label"/></th>
		<td>
			<table border="0" cellspacing="2" style="border: 0px;">
			  <tr>
			    <th style="border: 0px;background-color: #fff;" nowrap="nowrap"><bean:message key="page.job.notification.send.label"/></th>
			    <td style="border: 0px;"><html:checkbox property="value(sendNotification)" ></html:checkbox></td>
			  </tr>
			  <tr>
				<th style="border: 0px;background-color: #fff;">
					<bean:message key="notification.uimode.label" />
				</th>
				<td style="border: 0px;">
					<html:select property="value(uiMode)" value="3">
						<html:option value="0"><bean:message key="notification.uimode.notSpecifie.label"/></html:option>
						<html:option value="1"><bean:message key="notification.uimode.background.label"/></html:option>
						<html:option value="2"><bean:message key="notification.uimode.informative.label"/></html:option>
						<html:option value="3"><bean:message key="notification.uimode.userInteraction.label"/></html:option>
					</html:select>
				</td>
			  </tr>
			  <tr>
				<th style="border: 0px;background-color: #fff;">
					<bean:message key="notification.initiator.label" />
				</th>
				<td style="border: 0px;">
					<html:select property="value(initiator)" value="1">
						<html:option value="0"><bean:message key="notification.initiator.client.label"/></html:option>
						<html:option value="1"><bean:message key="notification.initiator.server.label"/></html:option>
					</html:select>
				</td>
			  </tr>
			</table>
		</td>
	</tr>
