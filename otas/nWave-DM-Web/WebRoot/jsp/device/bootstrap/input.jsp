<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<html:form action="/device/bootstrap" method="post" focus="pin">
  <INPUT type="hidden" name="method" value="send">
  <html:hidden property="deviceID" />
  <INPUT type="hidden" name="subscriberIMSI" value="${device.subscriber.IMSI}">
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
			<th>
				<bean:message key="device.title.bootstrap.pinType" />
			</th>
			<td>
				<html:select property="pinType" onchange="if (this.form.subscriberIMSI != '' && this.form.pinType.options[this.form.pinType.selectedIndex].value == '0') {this.form.pin.value=this.form.subscriberIMSI.value; } else {this.form.pin.value='';}">
					<html:option value="0"><bean:message key="meta.bootstrap.pintype.NETWPIN.label"/></html:option>
					<html:option value="1"><bean:message key="meta.bootstrap.pintype.USERPIN.label"/></html:option>
					<html:option value="2"><bean:message key="meta.bootstrap.pintype.USERNETWPIN.label"/></html:option>
					<html:option value="3"><bean:message key="meta.bootstrap.pintype.USERPINMAC.label"/></html:option>
				</html:select>
			</td>
		</tr>
	    <tr>
	      <th><bean:message key="device.title.bootstrap.pin"/></th>
	      <td>
	        <html:text property="pin" />
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
      <html:submit styleClass="NormalWidthButton"><bean:message key="page.device.button.send"/></html:submit>
	  <html:reset styleClass="NormalWidthButton"><bean:message key="page.button.reset.label"/></html:reset>
	  <html:cancel styleClass="NormalWidthButton"><bean:message key="page.button.cancel.label"/></html:cancel>
	</div>
</html:form>
<script language="JavaScript">
<!-- // create calendar object(s) just after form tag closed
	 // specify form element as the only parameter (document.forms['formname'].elements['inputname']);
	 // note: you can have as many calendar objects as you need for your application
	var scheduleCalendar = new calendar2(document.forms['DeviceBootstrapForm'].elements['jobSchedule']);
	scheduleCalendar.year_scroll = true;
	scheduleCalendar.time_comp = true;
	
	function popupCalendar() {
	  document.forms['DeviceBootstrapForm'].elements['jobScheduleOption'][1].checked = true;
	  scheduleCalendar.popup();
	}
//-->
</script>
