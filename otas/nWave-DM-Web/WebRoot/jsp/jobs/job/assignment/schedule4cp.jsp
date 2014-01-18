<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %>

<div class="messageArea">
  <bean:message key="page.device.job.discovery.message" /><br><br>
  <bean:message key="page.message.required"/><bean:message key="page.message.input"/>
  <div class="validateErrorMsg"><html:errors header="common.errors.header" footer="common.errors.footer" prefix="common.errors.prefix" suffix="common.errors.suffix"/></div>
</div>

<html:form action="/jobs/cp/SaveProfileAssignment" method="post">
<html:hidden property="value(ID)"/>
<html:hidden property="value(profileID)"/>
<logic:iterate id="parameter" name="parameters">
  <input type="hidden" name="<bean:write name="parameter" property="name"/>" value="<bean:write name="parameter" property="value"/>">
</logic:iterate>
	<table class="entityview">
		<tbody>
		    <tr>
		      <th><bean:message key="page.Jobs.jobtypes.totalTargetDevices.label"/></th>
		      <td>
            <c:out value="${targetDevices.fullListSize}"/>
	          </td>
		    </tr>
        <tr>
          <th>
            <bean:message key="device.job.jobType" />
          </th>
          <td>
            <bean:write name="JOB_TYPE_FOR_ASSIGN_PROFILE" property="name" /> [ <bean:write name="JOB_TYPE_FOR_ASSIGN_PROFILE" property="mode" /> ]
          </td>
        </tr>
        <!-- Include job parameters form -->
        <jsp:include flush="true" page="/jsp/jobs/job/common/job_parameters_in_backed_map_form.jsp"></jsp:include>
        <tr>
          <th>
            <bean:message key="device.title.bootstrap.pinType" />
          </th>
          <td>
            <html:select property="value(pinType)" >
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
            <bean:message key="page.jobs.edit.profileAssignment.schedule.pin.hint" />
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
	var scheduleCalendar = new calendar2(document.forms['JobProfileAssignmentForm'].elements['value(jobSchedule)']);
	scheduleCalendar.year_scroll = true;
	scheduleCalendar.time_comp = true;
	
	function popupCalendar() {
	  document.forms['JobProfileAssignmentForm'].elements['value(jobScheduleOption)'][1].checked = true;
	  scheduleCalendar.popup();
	}
//-->
</script>
