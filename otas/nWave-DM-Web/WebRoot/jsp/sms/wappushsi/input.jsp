<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

<script type="text/javascript" language="javascript">
</script>

<div class="messageArea">
  <bean:message key="page.message.required"/><bean:message key="page.message.input"/>
  <div class="validateErrorMsg"><html:errors header="common.errors.header" footer="common.errors.footer" prefix="common.errors.prefix" suffix="common.errors.suffix"/></div>
</div>


<html:form action="/sms/wappushsi/send" method="post" focus="msisdn">
	<table class="entityview">
		<tbody>
		  <!-- Form Element: Targets -->
		  <jsp:include flush="true" page="/jsp/ota/omacp/form/targets.jsp"></jsp:include>

			<tr>
				<th style="font-weight: normal">* <bean:message key="sms.wappushsi.url.label" /></th>
				<td>
					<html:textarea property="url" />
					<div class="validateErrorMsg"><html:errors property="url" /></div>
          <bean:message key="sms.wappushsi.content.hint.message" />
				</td>
			</tr>

			<tr>
				<th style="font-weight: normal">* <bean:message key="sms.wappushsi.content.label" /></th>
				<td>
					<html:textarea property="content" />
					<div class="validateErrorMsg"><html:errors property="content" /></div>
          <bean:message key="sms.wappushsi.content.hint.message"/>
				</td>
			</tr>

      <tr>
        <th><bean:message key="sms.wappushsi.schedule.label"/></th>
        <td>
          <table border="0" cellspacing="2" style="border: 0px;">
            <tr>
              <td style="border: 0px;"><input type="radio" name="scheduleOption" checked="checked" onclick="this.form.elements['jobSchedule'].value='';"> <bean:message key="page.job.schedule.now.label"/></td>
            </tr>
            <tr>
              <td style="border: 0px; vertical-align: middle;">
                <input type="radio" name="scheduleOption"> <bean:message key="page.job.schedule.later.label"/> &nbsp;
                <html:text property="scheduleTime" value=""/>
                <a href="javascript:popupCalendar();"><img src="common/calendar/img/cal.gif" width="16" height="16" border="0" alt="<bean:message key="page.job.schedule.calendar.label"/>"></a>
                &nbsp;(mm/dd/yyyy HH:MM:SS)
              </td>
            </tr>
          </table>
        </td>
      </tr>

      <tr>
        <th style="font-weight: normal"><bean:message key="sms.wappushsi.minDuration.label" /></th>
        <td>
          <html:text property="minDuration" size="6"/>
          <div class="validateErrorMsg"><html:errors property="minDuration" /></div>
        </td>
      </tr>

      <tr>
        <th style="font-weight: normal"><bean:message key="sms.wappushsi.maxDuration.label" /></th>
        <td>
          <html:text property="maxDuration" size="6"/>
          <div class="validateErrorMsg"><html:errors property="maxDuration" /></div>
        </td>
      </tr>

		</tbody>
	</table>
	<div class="buttonArea" style="height: 40px; weight: 100%;">
	    <input type="submit" name="method" value="<bean:message key="sms.wappushsi.button.send" />" class="NormalWidthButton">
	</div>
</html:form>

<script language="JavaScript">
<!-- // create calendar object(s) just after form tag closed
   // specify form element as the only parameter (document.forms['formname'].elements['inputname']);
   // note: you can have as many calendar objects as you need for your application
  var scheduleCalendar = new calendar2(document.forms['WapPushSmsSiForm'].elements['scheduleTime']);
  scheduleCalendar.year_scroll = true;
  scheduleCalendar.time_comp = true;
  
  function popupCalendar() {
    document.forms['WapPushSmsSiForm'].elements['scheduleOption'][1].checked = true;
    scheduleCalendar.popup();
  }
//-->
</script>
