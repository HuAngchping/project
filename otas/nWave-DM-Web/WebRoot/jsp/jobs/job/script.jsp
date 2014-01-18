
<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<script type="text/javascript">
<!--
var alert_msg_cmd_toolbar_syntax_error = '<bean:message key="page.dm.cmd.toolbar.msg.alert.syntax_error"/>';
//-->
</script>

<!-- Javascript: command script toolbar -->
<script type="text/javascript" language="javascript" src="<html:rewrite page='/common/scripts/sctipt-job-tbr/script-job-tbr.js'/>"></script>

<!-- Styles: command script toolbar -->
<link rel="stylesheet" type="text/css" href="<html:rewrite page='/common/scripts/sctipt-job-tbr/tbr-menus.css'/>" />

<script type="text/javascript" src="<html:rewrite page='/common/scripts/dm_cmd_helper.js'/>"></script>

<div class="messageArea">
  <bean:message key="page.message.required"/><bean:message key="page.message.input"/>
</div>

<html:form action="/jobs/SaveScirpt">
  <table class="toolbarview">
    <tr>
      <td>
        <div id="script-job-tbr"></div>
      </td>
    </tr>
    <tr>
      <td>
        <html:textarea styleId="commandScriptEditor" property="commandScript" styleClass="script" value="<script>
</script>" style="clear: both;" />
         <div class="validateErrorMsg"><html:errors property="commandScript"/></div>
       </td>
    </tr>
  </table>
	<div class="buttonArea">
      <html:submit property="action"><bean:message key="page.device.job.script.button.compile"/></html:submit>
	  <html:cancel styleClass="NormalWidthButton"><bean:message key="page.button.cancel.label"/></html:cancel>
	</div>
</html:form>

<!-- Panel for Target Devices -->
<table class=table_box cellSpacing=0 cellPadding=0 width="100%" border="0">
  <tbody>
  <tr>
    <td class=table_box_heading width="100%" colSpan=5><font color="red"><b>.:</b></font> <bean:message key="page.jobs.submit.job.success.target.devices.message"/></td>
  </tr>
  <tr>
    <td>
      <jsp:include page="/jsp/jobs/job/common/target_devices_view.jsp"></jsp:include>
    </td>
  </tr>
  </tbody>
</table>
