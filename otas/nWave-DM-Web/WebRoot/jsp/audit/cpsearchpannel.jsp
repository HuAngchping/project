<%@ page import="com.npower.dm.core.ClientProvAuditLog" %>
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
        <html:form action="/audit/CPAuditlogs">
          <table width="100%" border="0" align="left" cellpadding="2" cellspacing="0">
	        <tr>
	          <td align="right">
	            <bean:message key="page.cp.audit.searchPannel.carrier.label" />:
	          </td>
	          <td>
	          <html:select name="SearchCPAuditLogsForm" property="searchCarrier" styleClass="Select4SearchPannel">	   
	             <html:option value=""><bean:message key="page.select.search.option.default.label"/></html:option>         
	             <html:optionsCollection name="carrierOptions" />
	          </html:select>
	          </td>
	        </tr>
	        <tr>
	          <td align="right">
	            <bean:message key="page.cp.audit.logs.profileCategory.label" />:
	          </td>
	          <td>
	          <html:select name="SearchCPAuditLogsForm" property="searchProfileCategory" styleClass="Select4SearchPannel">
	            <html:option value=""><bean:message key="page.select.search.option.default.label"/></html:option>
	     	    <html:optionsCollection name="categoryOptions" />
	          </html:select>
	          </td>
	        </tr>         
	        <tr>
	          <td align="right">
	            <bean:message key="page.cp.audit.searchPannel.manufacturer.label" />:
	          </td>
	          <td>
	          <html:select name="SearchCPAuditLogsForm" property="searchManufacturer" styleClass="Select4SearchPannel">
	            <html:option value=""><bean:message key="page.select.search.option.default.label"/></html:option>
	             <html:optionsCollection name="manufacturerOptions"/>
	          </html:select>
	          </td>
	        </tr>
	        <tr>
	          <td align="right">
	            <bean:message key="page.cp.audit.searchPannel.model.label" />:
	          </td>
	          <td>
	            <html:text name="SearchCPAuditLogsForm" property="searchModel" size="16"/>
              </td>
            </tr>
	        <tr>
	          <td align="right">
	            <bean:message key="page.cp.audit.searchPannel.phoneNumber.label" />:
	          </td>
	          <td>
	          <html:text name="SearchCPAuditLogsForm" property="searchPhoneNumber" size="16"/>
	          </td>
	        </tr>
	        <tr>
	          <td align="right">
	            <bean:message key="page.cp.audit.searchPannel.profileName.label" />:
	          </td>
	          <td>
	          <html:text name="SearchCPAuditLogsForm" property="searchProfileName" size="16"/>
	          </td>
	        </tr>	        	        
	        <tr>
	          <td align="right">
	            <bean:message key="page.cp.audit.searchPannel.status.label" />:
	          </td>
	          <td>
	          <html:select name="SearchCPAuditLogsForm" property="searchStatus" styleClass="Select4SearchPannel">
	            <html:option value=""><bean:message key="page.select.search.option.default.label"/></html:option>
                <html:option value="<%=ClientProvAuditLog.STATUS_WAIT%>"><bean:message key="meta.job.cp.state.wait_to_enqueue.label"/></html:option>
                <html:option value="<%=ClientProvAuditLog.STATUS_QUEUED%>"><bean:message key="meta.job.cp.state.queued.label"/></html:option>
                <html:option value="<%=ClientProvAuditLog.STATUS_SENT%>"><bean:message key="meta.job.cp.state.sent.label"/></html:option>
                <html:option value="<%=ClientProvAuditLog.STATUS_FAILURE%>"><bean:message key="meta.job.cp.state.failure.label"/></html:option>
                <html:option value="<%=ClientProvAuditLog.STATUS_RECEIVED%>"><bean:message key="meta.job.cp.state.received.label"/></html:option>
	            <html:option value="<%=ClientProvAuditLog.STATUS_SUCCESS%>"><bean:message key="meta.job.cp.state.success.label"/></html:option>
	            <html:option value="<%=ClientProvAuditLog.STATUS_UNKNOW%>"><bean:message key="meta.job.cp.state.unkown.label"/></html:option>
	          </html:select>
	          </td>
	        </tr>	        
	        
	        <tr>
	          <td align="right" nowrap="nowrap">
	            <bean:message key="page.cp.audit.searchPannel.beginTime.label" />:
	          </td>
	          <td nowrap="nowrap">
			      <html:text name="SearchCPAuditLogsForm" property="searchBeginTime" size="18" maxlength="19" style="font-size: 9px;"/>
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
	            <bean:message key="page.cp.audit.searchPannel.endTime.label" />:
	          </td>
	          <td nowrap="nowrap">
			      <html:text property="searchEndTime" size="18" maxlength="19" style="font-size: 9px;"/>
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
	var scheduleCalendar = new calendar2(document.forms['SearchCPAuditLogsForm'].elements['searchBeginTime']);
	scheduleCalendar.year_scroll = true;
	scheduleCalendar.time_comp = true;
	
	function popupCalendar4BeginTime() {
	  scheduleCalendar = new calendar2(document.forms['SearchCPAuditLogsForm'].elements['searchBeginTime']);
	  scheduleCalendar.year_scroll = true;
	  scheduleCalendar.time_comp = true;
	  scheduleCalendar.popup();
	}
	function popupCalendar4EndTime() {
	  scheduleCalendar = new calendar2(document.forms['SearchCPAuditLogsForm'].elements['searchEndTime']);
	  scheduleCalendar.year_scroll = true;
	  scheduleCalendar.time_comp = true;
	  scheduleCalendar.popup();
	}
//-->
</script>
