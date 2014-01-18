<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

  <tr>
    <th><bean:message key="page.device.jobs.job.name.label"/></th>
    <td>
       <html:text property="name" size="48"></html:text>
       <div class="validateErrorMsg"><html:errors property="name"/></div></td>
  </tr>
  <tr>
    <th><bean:message key="page.device.jobs.job.description.label"/></th>
    <td>
       <html:textarea property="description"></html:textarea>
       <div class="validateErrorMsg"><html:errors property="description"/></div>
    </td>
  </tr>
  <tr>
    <th><bean:message key="page.device.jobs.job.priority.label"/></th>
    <td>
       <html:select property="priority" value="5">
         <html:option value="9">9 (<bean:message key="page.device.jobs.job.priority.highest.label"/>)</html:option>
         <html:option value="8">8</html:option>
         <html:option value="7">7</html:option>
         <html:option value="6">6</html:option>
         <html:option value="5">5 (<bean:message key="page.device.jobs.job.priority.normal.label"/>)</html:option>
         <html:option value="4">4</html:option>
         <html:option value="3">3</html:option>
         <html:option value="2">2</html:option>
         <html:option value="1">1 (<bean:message key="page.device.jobs.job.priority.lowest.label"/>)</html:option>
       </html:select>
       <div class="validateErrorMsg"><html:errors property="priority"/></div>
    </td>
  </tr>
	<tr>
		<th><bean:message key="page.job.schedule.label"/></th>
		<td>
			<table border="0" cellspacing="2" style="border: 0px;">
			  <tr>
			    <td style="border: 0px;"><input type="radio" name="jobScheduleOption" checked="checked" onclick="this.form.elements['jobSchedule'].value='';"> <bean:message key="page.job.schedule.now.label"/></td>
			  </tr>
			  <tr>
			    <td style="border: 0px; vertical-align: middle;">
			      <input type="radio" name="jobScheduleOption"> <bean:message key="page.job.schedule.later.label"/> &nbsp;
			      <html:text property="jobSchedule" value=""/>
			      <a href="javascript:popupCalendar();"><img src="common/calendar/img/cal.gif" width="16" height="16" border="0" alt="<bean:message key="page.job.schedule.calendar.label"/>"></a>
			      &nbsp;(mm/dd/yyyy HH:MM:SS)
			    </td>
			  </tr>
			</table>
		</td>
	</tr>
	<tr>
		<th><bean:message key="page.job.notification.label"/></th>
		<td>
			<table border="0" cellspacing="2" style="border: 0px;">
			  <tr>
			    <th style="border: 0px;background-color: #fff;" nowrap="nowrap"><bean:message key="page.job.notification.send.label"/></th>
			    <td style="border: 0px;"><html:checkbox property="sendNotification" ></html:checkbox></td>
			  </tr>
			  <tr>
				<th style="border: 0px;background-color: #fff;">
					<bean:message key="notification.uimode.label" />
				</th>
				<td style="border: 0px;">
					<html:select property="uiMode" value="3">
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
					<html:select property="initiator" value="1">
						<html:option value="0"><bean:message key="notification.initiator.client.label"/></html:option>
						<html:option value="1"><bean:message key="notification.initiator.server.label"/></html:option>
					</html:select>
				</td>
			  </tr>
			</table>
		</td>
	</tr>
  <tr>
    <th>* <bean:message key="page.device.jobs.job.concurrentSize.label"/></th>
    <td>
       <html:text property="concurrentSize" size="8"></html:text>
       <div class="validateErrorMsg"><html:errors property="concurrentSize"/></div>
    </td>
  </tr>
  <tr>
    <th>* <bean:message key="page.device.jobs.job.concurrentInterval.label"/></th>
    <td>
       <html:text property="concurrentInterval" size="8"></html:text> <bean:message key="page.device.jobs.job.concurrentInterval.seconds.label"/>
       <div class="validateErrorMsg"><html:errors property="concurrentInterval"/></div>
    </td>
  </tr>
  