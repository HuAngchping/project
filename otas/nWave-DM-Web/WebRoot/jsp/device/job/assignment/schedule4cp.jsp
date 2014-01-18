<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

<div class="messageArea">
  <bean:message key="page.device.job.assignment.message" /><br><br>
  <bean:message key="page.message.required"/><bean:message key="page.message.input"/>
  <div class="validateErrorMsg"><html:errors header="common.errors.header" footer="common.errors.footer" prefix="common.errors.prefix" suffix="common.errors.suffix"/></div>
</div>

<html:form action="/device/job/cp/profile_assignment/submit" method="post">
<html:hidden property="value(ID)"/>
<html:hidden property="value(profileID)"/>
<html:hidden property="value(deviceID)"/>
<input type="hidden" name="deviceID" value="<bean:write name="device" property="ID"/>"/>
<input type="hidden" name="subscriberIMSI" value="${device.subscriber.IMSI}">
<logic:iterate id="parameter" name="parameters">
  <input type="hidden" name="<bean:write name="parameter" property="name"/>" value="<bean:write name="parameter" property="value"/>">
</logic:iterate>
	<table class="entityview">
		<tbody>
			<tr>
				<th width="150">
					<bean:message key="device.title.externalId" />
				</th>
				<td>
					<bean:write name="device" property="externalId" />
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="device.title.phonenumber" />
				</th>
				<td>
					<bean:write name="device" property="subscriberPhoneNumber" />
				</td>
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
          <bean:message key="device.job.jobType" />
        </th>
        <td>
          <bean:write name="JOB_TYPE_FOR_ASSIGN_PROFILE" property="name" /> [ <bean:write name="JOB_TYPE_FOR_ASSIGN_PROFILE" property="mode" /> ]
        </td>
      </tr>
	    <tr>
	      <th><bean:message key="page.device.job.assignment.label.profile"/></th>
	      <td><bean:write name="profile" property="name"/></td>
	    </tr>
			<!-- Include schedule form -->
			<jsp:include page="/jsp/device/job/schedule_calendar.jsp"></jsp:include>
			
    <tr>
      <th>
        <bean:message key="device.title.bootstrap.pinType" />
      </th>
      <td>
        <html:select property="value(pinType)" onchange="if (this.form.subscriberIMSI != '' && this.form.elements['value(pinType)'].options[this.form.elements['value(pinType)'].selectedIndex].value == '0') {this.form.elements['value(pin)'].value=this.form.subscriberIMSI.value; } else {this.form.elements['value(pin)'].value='';}">
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
        <html:text property="value(pin)" />
        <div class="validateErrorMsg"><html:errors property="pin"/></div>
        <bean:message key="page.bootstrap.message.netwpin.hint" />
      </td>
    </tr>
    <tr>
      <th>
          <bean:message key="page.job.interactive.mode.label"/>
      </th>
      <td>
          <jsp:include page="/jsp/common/interactive_mode4configjob.jsp"></jsp:include>             
      </td>
    </tr>
		</tbody>
	</table>
	<div class="buttonArea">
      <html:submit property="action" styleClass="NormalWidthButton"><bean:message key="page.button.submit.label"/></html:submit>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <html:submit property="action" styleClass="NormalWidthButton"><bean:message key="page.button.previous.label"/></html:submit>
	  <html:cancel styleClass="NormalWidthButton"><bean:message key="page.button.cancel.label"/></html:cancel>
	</div>
</html:form>
<script language="JavaScript">
<!-- // create calendar object(s) just after form tag closed
	 // specify form element as the only parameter (document.forms['formname'].elements['inputname']);
	 // note: you can have as many calendar objects as you need for your application
	var scheduleCalendar = new calendar2(document.forms['DeviceJobProfileAssignmentForm'].elements['value(jobSchedule)']);
	scheduleCalendar.year_scroll = true;
	scheduleCalendar.time_comp = true;
	
	function popupCalendar() {
	  document.forms['DeviceJobProfileAssignmentForm'].elements['value(jobScheduleOption)'][1].checked = true;
	  scheduleCalendar.popup();
	}
//-->
</script>
