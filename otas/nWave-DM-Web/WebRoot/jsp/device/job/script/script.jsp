
<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
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

<html:form action="/device/job/SaveScirpt">
    <input type="hidden" name="deviceID" value="<bean:write name="device" property="ID"/>"/>
	<table class="entityview">
	  <tbody>
	    <tr>
	      <th width="150"><bean:message key="device.title.externalId"/></th>
	      <td><bean:write name="device" property="externalId"/></td>
	    </tr>
	    <tr>
	      <th><bean:message key="device.title.phonenumber"/></th>
	      <td><bean:write name="device" property="subscriberPhoneNumber"/></td>
	    </tr>
	    <tr>
	      <th><bean:message key="device.title.manufacturer"/></th>
	      <td><bean:write name="device" property="model.manufacturer.name" /></td>
	    </tr>
	    <tr>
	      <th><bean:message key="device.title.model"/></th>
	      <td><bean:write name="device" property="model.name" /></td>
	    </tr>
    </tbody>
	</table>
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
