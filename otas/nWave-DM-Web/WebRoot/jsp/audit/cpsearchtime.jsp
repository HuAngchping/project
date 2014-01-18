<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>


<html:form action="/createReport">
  <table>
  <tr align="center">
    <th colspan="2"> <bean:message key="page.search.cp.log.title.label"/> </th>
  </tr>
  <tr>
    <th> <bean:message key="page.search.cp.log.begintime.label"/> </th>
    <th>
       <html:text property="startTime" value="${nowdate1}"/>
       <a href="javascript:popupCalendar4BeginTime();"><img src="common/calendar/img/cal.gif" width="16" height="16" border="0"></a>        
    </th>
  </tr>
  <tr>
    <th> <bean:message key="page.search.cp.log.endtime.label"/> </th>
    <th>
	     <html:text property="endTime" value="${nowDate}"/>
	     <a href="javascript:popupCalendar4EndTime();"><img src="common/calendar/img/cal.gif" width="16" height="16" border="0"></a>    
    </th>
  </tr>
  <tr align="center">
    <th colspan="2">    
       <input type="hidden" name="method" value="query4Time"/>
       <html:submit> <bean:message key="page.search.cp.log.button.label"/> </html:submit> 
    </th>
  </tr>  
</table>
</html:form>

<script language="JavaScript">
<!-- // create calendar object(s) just after form tag closed
   // specify form element as the only parameter (document.forms['formname'].elements['inputname']);
   // note: you can have as many calendar objects as you need for your application
  var scheduleCalendar = new calendar2(document.forms['CreateCPReportForm'].elements['startTime']);
  scheduleCalendar.year_scroll = true;
  scheduleCalendar.time_comp = true;
  
  function popupCalendar4BeginTime() {
    scheduleCalendar = new calendar2(document.forms['CreateCPReportForm'].elements['startTime']);
    scheduleCalendar.year_scroll = true;
    scheduleCalendar.time_comp = true;
    scheduleCalendar.popup();
  }
  function popupCalendar4EndTime() {
    scheduleCalendar = new calendar2(document.forms['CreateCPReportForm'].elements['endTime']);
    scheduleCalendar.year_scroll = true;
    scheduleCalendar.time_comp = true;
    scheduleCalendar.popup();
  }
//-->
</script>





