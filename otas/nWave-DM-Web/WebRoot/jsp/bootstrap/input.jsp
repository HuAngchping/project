<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<div class="messageArea">
  <bean:message key="page.notification.device.message" /><br><br>
  <bean:message key="page.message.required"/><bean:message key="page.message.input"/>
</div>

<html:form action="/bootstrap/send" method="post" focus="msisdn">
  <INPUT type="hidden" name="method" value="send">
	<table class="entityview">
		<tbody>
			<tr>
				<th width="150">
					* <bean:message key="page.bootstrap.label.phoneNumbers" />
				</th>
				<td>
					<html:textarea property="msisdn" />
					<div class="validateErrorMsg">
						<html:errors property="msisdn" />
					</div>
					<bean:message key="page.bootstrap.message.numberFormat" />
				</td>
			</tr>
		    <tr>
		      <th><bean:message key="bootstrap.label.serverURL"/></th>
		      <td>
		        <html:text property="serverURL" size="48" readonly="true"/>
	          <div class="validateErrorMsg"><html:errors property="serverURL"/></div></td>
		    </tr>
		    <tr>
		      <th><bean:message key="bootstrap.label.serverID"/></th>
		      <td>
		        <html:text property="serverID" size="24" readonly="true"/>
	          <div class="validateErrorMsg"><html:errors property="serverID"/></div></td>
		    </tr>
		    <tr>
		      <th>* <bean:message key="bootstrap.label.serverPassword"/></th>
		      <td>
		        <html:text property="serverPassword" size="24"/>
	          <div class="validateErrorMsg"><html:errors property="serverPassword"/></div></td>
		    </tr>
		    <tr>
		      <th>* <bean:message key="bootstrap.label.clientUsername"/></th>
		      <td>
		        <html:text property="clientUsername" size="24"/>
	          <div class="validateErrorMsg"><html:errors property="clientUsername"/></div></td>
		    </tr>
		    <tr>
		      <th>* <bean:message key="bootstrap.label.clientPassword"/></th>
		      <td>
		        <html:text property="clientPassword" size="24"/>
	          <div class="validateErrorMsg"><html:errors property="clientPassword"/></div></td>
		    </tr>
			<tr>
				<th>
					<bean:message key="device.title.bootstrap.pinType" />
				</th>
				<td>
					<html:select property="pinType">
						<html:option value="0"><bean:message key="meta.bootstrap.pintype.NETWPIN.label"/></html:option>
						<html:option value="1"><bean:message key="meta.bootstrap.pintype.USERPIN.label"/></html:option>
						<html:option value="2"><bean:message key="meta.bootstrap.pintype.USERNETWPIN.label"/></html:option>
						<html:option value="3"><bean:message key="meta.bootstrap.pintype.USERPINMAC.label"/></html:option>
					</html:select>
				</td>
			</tr>
		    <tr>
		      <th>* <bean:message key="device.title.bootstrap.pin"/></th>
		      <td>
		        <html:text property="pin" size="24"/>
	          <div class="validateErrorMsg"><html:errors property="pin"/></div>
            <bean:message key="page.bootstrap.message.netwpin.hint" />
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
		</tbody>
	</table>
	<div class="buttonArea">
      <html:submit><bean:message key="bootstrap.button.SendBootstrap"/></html:submit>
	</div>
</html:form>
<script language="JavaScript">
<!-- // create calendar object(s) just after form tag closed
	 // specify form element as the only parameter (document.forms['formname'].elements['inputname']);
	 // note: you can have as many calendar objects as you need for your application
	var scheduleCalendar = new calendar2(document.forms['BootstrapForm'].elements['jobSchedule']);
	scheduleCalendar.year_scroll = true;
	scheduleCalendar.time_comp = true;
	
	function popupCalendar() {
	  document.forms['BootstrapForm'].elements['jobScheduleOption'][1].checked = true;
	  scheduleCalendar.popup();
	}
//-->
</script>
