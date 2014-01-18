<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

  <tr>
    <th><bean:message key="page.device.jobs.job.name.label"/></th>
    <td>
       <html:text property="value(name)" size="48"></html:text>
       <div class="validateErrorMsg"><html:errors property="name"/></div></td>
  </tr>
  <tr>
    <th><bean:message key="page.device.jobs.job.description.label"/></th>
    <td>
       <html:textarea property="value(description)"></html:textarea>
       <div class="validateErrorMsg"><html:errors property="description"/></div>
    </td>
  </tr>
  <tr>
    <th><bean:message key="page.device.jobs.job.priority.label"/></th>
    <td>
       <html:select property="value(priority)" value="5">
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
			    <td style="border: 0px;"><input type="radio" name="value(jobScheduleOption)" checked="checked" onclick="this.form.elements['jobSchedule'].value='';"> <bean:message key="page.job.schedule.now.label"/></td>
			  </tr>
			  <tr>
			    <td style="border: 0px; vertical-align: middle;">
			      <input type="radio" name="value(jobScheduleOption)"> <bean:message key="page.job.schedule.later.label"/> &nbsp;
			      <html:text property="value(jobSchedule)" value=""/>
			      <a href="javascript:popupCalendar();"><img src="common/calendar/img/cal.gif" width="16" height="16" border="0" alt="<bean:message key="page.job.schedule.calendar.label"/>"></a>
			      &nbsp;(mm/dd/yyyy HH:MM:SS)
			    </td>
			  </tr>
			</table>
		</td>
	</tr>
