<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

<div class="messageArea">
  <bean:message key="page.device.job.schedule.message" /><br><br>
  <bean:message key="page.message.required"/><bean:message key="page.message.input"/>
  <div class="validateErrorMsg"><html:errors header="common.errors.header" footer="common.errors.footer" prefix="common.errors.prefix" suffix="common.errors.suffix"/></div>
</div>

<html:form action="/device/job/software/discovery/save" method="post">
	<html:hidden property="deviceID"/>
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
		<!-- Include schedule form -->
		<jsp:include flush="true" page="/jsp/device/job/schedule.jsp"></jsp:include>
    <tr>
      <th>
          <bean:message key="page.job.interactive.mode.label"/>
      </th>
      <td>
          <jsp:include page="/jsp/common/interactive_mode4otherjob.jsp"></jsp:include>              
      </td>
    </tr>		
	</tbody>
	</table>
	<div class="buttonArea">
      <html:submit property="action" styleClass="NormalWidthButton"><bean:message key="page.button.submit.label"/></html:submit>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	  <html:cancel styleClass="NormalWidthButton"><bean:message key="page.button.cancel.label"/></html:cancel>
	</div>
</html:form>
<script language="JavaScript">
<!-- // create calendar object(s) just after form tag closed
	 // specify form element as the only parameter (document.forms['formname'].elements['inputname']);
	 // note: you can have as many calendar objects as you need for your application
	var scheduleCalendar = new calendar2(document.forms['Form4SoftwareDiscovery'].elements['jobSchedule']);
	scheduleCalendar.year_scroll = true;
	scheduleCalendar.time_comp = true;
	
	function popupCalendar() {
	  document.forms['Form4SoftwareDiscovery'].elements['jobScheduleOption'][1].checked = true;
	  scheduleCalendar.popup();
	}
//-->
</script>
