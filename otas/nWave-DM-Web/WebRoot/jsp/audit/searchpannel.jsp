<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://struts-menu.sf.net/tag" prefix="menu" %>
<%@ taglib uri="http://struts-menu.sf.net/tag-el" prefix="menu-el" %>

<SCRIPT type="text/javascript">
var xmlHttp;
    
function getAuditLogActions(actionType) {
  createXMLHttpRequest()
  var url = "<html:rewrite page='/ajax.do'/>?auditActionType=" + actionType + "&method=getAuditLogActions";
  xmlHttp.open("GET", uncache(url), true);
  xmlHttp.onreadystatechange = parseAuditLogActions;
  xmlHttp.send(null);
}

function parseAuditLogActions() {
  if (xmlHttp.readyState == 4) {
     if (xmlHttp.status == 200) {
        resText = xmlHttp.responseText
        //alert(resText);
        each = resText.split("|")
        buildAuditLogActionSelect(each, document.getElementById("searchAuditActionValue"), "<bean:message key="page.select.search.option.default.label"/>");
     }
  }
}

function buildAuditLogActionSelect(str, sel, label) {
  sel.options.length = 0;
  sel.options[sel.options.length] = new Option(label, "")
  for (var i = 0; i < str.length; i++) {
      if (str[i].length == 0) {
         continue;
      }
      each = str[i].split(",")
      sel.options[sel.options.length] = new Option(each[0],each[1])
  }
}

</SCRIPT>

<!--  Insert space -->
<br>

<!--  Search Panel -->
<table align="CENTER" width="90%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="1%"><img src="common/images/table_curve_topleft_trans.gif" width="9" height="28"></td>
		<td width="10%" class="columnheading" valign="middle"><img src="common/images/menu_search_icon.png"></td>
		<td width="88%" class="columnheading" valign="middle">&nbsp;&nbsp;<bean:message key="page.search.title.label" /></td>
		<td width="1%" align="right"><img src="common/images/table_curve_topright_trans.gif" width="10" height="28"></td>
	</tr>
</table>
<table width="90%" border="0" align="center" cellpadding="0" cellspacing="5" class="mnutableborder">
	<tr>
		<td>
        <html:form action="/audit/Auditlogs">
          <table width="100%" border="0" align="left" cellpadding="2" cellspacing="0">
	        <tr>
	          <td align="right">
	            <bean:message key="page.audit.searchPannel.actionType.label" />:
	          </td>
	          <td>
	          <html:select property="searchAuditActionType" styleClass="Select4SearchPannel" onchange="getAuditLogActions(this.value)">
	            <html:option value=""><bean:message key="page.select.search.option.default.label"/></html:option>
	            <html:optionsCollection name="actionTypes"/>
	          </html:select>
	          </td>
	        </tr>
	        <tr>
	          <td align="right">
	            <bean:message key="page.audit.searchPannel.action.label" />:
	          </td>
	          <td>
	          <html:select property="searchAuditActionValue" styleId="searchAuditActionValue" styleClass="Select4SearchPannel">
	            <html:option value=""><bean:message key="page.select.search.option.default.label"/></html:option>
	            <html:optionsCollection name="actionValues"/>
	          </html:select>
	          </td>
	        </tr>
	        <tr>
	          <td align="right">
	            <bean:message key="page.audit.searchPannel.username.label" />:
	          </td>
	          <td>
	          <html:text property="searchUserName" size="16"/>
	          </td>
	        </tr>
	        <tr>
	          <td align="right">
	            <bean:message key="page.audit.searchPannel.ipAddress.label" />:
	          </td>
	          <td>
	          <html:text property="searchIPAddress" size="16"/>
	          </td>
	        </tr>
	        <tr>
	          <td align="right" nowrap="nowrap">
	            <bean:message key="page.audit.searchPannel.beginTime.label" />:
	          </td>
	          <td nowrap="nowrap">
			      <html:text property="searchBeginTime" size="19" maxlength="19" style="font-size: 9px;"/>
			      <a href="javascript:popupCalendar4BeginTime();"><img src="common/calendar/img/cal.gif" width="16" height="16" border="0" alt="<bean:message key="page.job.schedule.calendar.label"/>"></a>
	          </td>
	        </tr>
	        <tr>
	          <td align="right"></td>
	          <td nowrap="nowrap">
			      <font style="font-size: 9px;">(mm/dd/yyyy HH:MM)</font>
	          </td>
	        </tr>
	        <tr>
	          <td align="right" nowrap="nowrap">
	            <bean:message key="page.audit.searchPannel.endTime.label" />:
	          </td>
	          <td nowrap="nowrap">
			      <html:text property="searchEndTime" size="19" maxlength="19" style="font-size: 9px;"/>
			      <a href="javascript:popupCalendar4EndTime();"><img src="common/calendar/img/cal.gif" width="16" height="16" border="0" alt="<bean:message key="page.job.schedule.calendar.label"/>"></a>
	          </td>
	        </tr>
	        <tr>
	          <td align="right"></td>
	          <td nowrap="nowrap">
			      <font style="font-size: 9px;">(mm/dd/yyyy HH:MM)</font>
	          </td>
	        </tr>
	        <tr>
	          <td align="right" nowrap="nowrap">
	            <bean:message key="page.countries.search.title.searchText" />:
	          </td>
	          <td>
	          <html:text property="searchText" size="16"/>
	          </td>
	        </tr>
	        <tr>
	          <td align="right">
	            <bean:message key="page.select.search.recordsPerPage.label" />:
	          </td>
	          <td>
	            <html:select property="recordsPerPage">
	              <html:optionsCollection name="recordsPerPageOptions"/>
	            </html:select>
	          </td>
	        </tr>
	        <tr>
	          <td align="right"></td>
	          <td>
	            <html:submit><bean:message key="page.button.search.label" /></html:submit>
	          </td>
	        </tr>
          </table>
        </html:form>
		</td>
	</tr>
</table>
<script language="JavaScript">
<!-- // create calendar object(s) just after form tag closed
	 // specify form element as the only parameter (document.forms['formname'].elements['inputname']);
	 // note: you can have as many calendar objects as you need for your application
	var scheduleCalendar = new calendar2(document.forms['SearchAuditLogsForm'].elements['searchBeginTime']);
	scheduleCalendar.year_scroll = true;
	scheduleCalendar.time_comp = true;
	
	function popupCalendar4BeginTime() {
	  scheduleCalendar = new calendar2(document.forms['SearchAuditLogsForm'].elements['searchBeginTime']);
	  scheduleCalendar.year_scroll = true;
	  scheduleCalendar.time_comp = true;
	  scheduleCalendar.popup();
	}
	function popupCalendar4EndTime() {
	  scheduleCalendar = new calendar2(document.forms['SearchAuditLogsForm'].elements['searchEndTime']);
	  scheduleCalendar.year_scroll = true;
	  scheduleCalendar.time_comp = true;
	  scheduleCalendar.popup();
	}
//-->
</script>
