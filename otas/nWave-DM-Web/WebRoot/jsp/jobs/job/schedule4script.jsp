<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %>
<%@ taglib uri="http://otas.cn/otasdm-taglib" prefix="otasdm"%>

<div class="messageArea">
  <bean:message key="page.device.job.script.message" /><br><br>
  <bean:message key="page.message.required"/><bean:message key="page.message.input"/>
  <div class="validateErrorMsg"><html:errors header="common.errors.header" footer="common.errors.footer" prefix="common.errors.prefix" suffix="common.errors.suffix"/></div>
</div>

<html:form action="/jobs/SaveScirpt" method="post">
    <html:hidden property="commandScript"/>
	<table class="entityview">
		<tbody>
		    <tr>
		      <th><bean:message key="page.Jobs.jobtypes.totalTargetDevices.label"/></th>
		      <td>
            <c:out value="${targetDevices.fullListSize}"/>
	          </td>
		    </tr>
  	        <tr>
	          <th><bean:message key="page.device.job.script.label"/></th>
	          <td>
              <bean:define id="scriptText" name="JobsJobScriptForm" property="commandScript"></bean:define>
	          <textarea class="view_script" readonly="readonly"><otasdm:xmlPretty content="${scriptText}"/></textarea>
              </td>
	        </tr>
      <!-- Include job parameters form -->
      <jsp:include flush="true" page="/jsp/jobs/job/common/job_parameters.jsp"></jsp:include>
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
	var scheduleCalendar = new calendar2(document.forms['JobsJobScriptForm'].elements['jobSchedule']);
	scheduleCalendar.year_scroll = true;
	scheduleCalendar.time_comp = true;
	
	function popupCalendar() {
	  document.forms['JobsJobScriptForm'].elements['jobScheduleOption'][1].checked = true;
	  scheduleCalendar.popup();
	}
//-->
</script>
