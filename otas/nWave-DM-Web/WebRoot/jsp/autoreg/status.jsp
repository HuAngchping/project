<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    
    <title>status.jsp</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>

<html:form action="/autoreg">
	<table class="entityview">
		<thead>
			<tr>
				<th colspan="2" align="left">
					<logic:notEmpty name="status"><bean:write name="status"/></logic:notEmpty>
				</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<th width="100">
					* MSISDN
				</th>
				<td>
					<html:text property="msisdn" />
				</td>
			</tr>
			<tr>
				<th>
					* IMEI
				</th>
				<td>
					<html:text property="imei" maxlength="15" />
				</td>
			</tr>
		</tbody>
	</table>
	<div class="buttonArea">
			<html:submit>
				<bean:message key="page.button.submit.label" />
			</html:submit>
	</div>
</html:form>
  </body>
</html:html>
