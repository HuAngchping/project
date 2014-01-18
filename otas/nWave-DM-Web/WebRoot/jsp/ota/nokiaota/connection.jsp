<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

<div class="messageArea">
  <bean:message key="page.message.required"/><bean:message key="page.message.input"/>
  <div class="validateErrorMsg"><html:errors header="common.errors.header" footer="common.errors.footer" prefix="common.errors.prefix" suffix="common.errors.suffix"/></div>
</div>

<html:form action="/ota/nokia/sendConnection" method="post" focus="msisdn">
	<table class="entityview">
		<tbody>
      <!-- Form Element: Targets -->
      <jsp:include flush="true" page="/jsp/ota/omacp/form/targets.jsp"></jsp:include>

			<tr>
				<th style="font-weight: normal">* <bean:message key="ota.nokia.connection.label.OTA_NAME" /></th>
				<td>
					<html:text property="OTA_NAME" />
					<div class="validateErrorMsg"><html:errors property="OTA_NAME" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal"><bean:message key="ota.nokia.connection.label.OTA_BEARER" /></th>
				<td>
					<html:radio property="OTA_BEARER" value="GSM-GPRS">GPRS</html:radio>
					<html:radio property="OTA_BEARER" value="GSM-CSD">GSM/CSD</html:radio>
					<div class="validateErrorMsg"><html:errors property="OTA_BEARER" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal">* <bean:message key="ota.nokia.connection.label.OTA_GPRS_ACCESSPOINTNAME" /></th>
				<td>
					<html:text property="OTA_GPRS_ACCESSPOINTNAME" />
					<div class="validateErrorMsg"><html:errors property="OTA_GPRS_ACCESSPOINTNAME" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal"><bean:message key="ota.nokia.connection.label.OTA_PPP_AUTHNAME" /></th>
				<td>
					<html:text property="OTA_PPP_AUTHNAME" />
					<div class="validateErrorMsg"><html:errors property="OTA_PPP_AUTHNAME" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal"><bean:message key="ota.nokia.connection.label.OTA_PPP_AUTHSECRET" /></th>
				<td>
					<html:text property="OTA_PPP_AUTHSECRET" />
					<div class="validateErrorMsg"><html:errors property="OTA_PPP_AUTHSECRET" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal"><bean:message key="ota.nokia.connection.label.OTA_PPP_AUTHTYPE" /></th>
				<td>
					<html:radio property="OTA_PPP_AUTHTYPE" value="PAP">Standard (PAP)</html:radio>
					<html:radio property="OTA_PPP_AUTHTYPE" value="CHAP">Secure (CHAP)</html:radio>
					<html:radio property="OTA_PPP_AUTHTYPE" value="MS_CHAP">MS-CHAP</html:radio>
					<div class="validateErrorMsg"><html:errors property="OTA_PPP_AUTHTYPE" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal"><bean:message key="ota.nokia.connection.label.OTA_PROXY" /></th>
				<td>
					<html:text property="OTA_PROXY" />
					<div class="validateErrorMsg"><html:errors property="OTA_PROXY" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal"><bean:message key="ota.nokia.connection.label.OTA_PROXY_AUTHNAME" /></th>
				<td>
					<html:text property="OTA_PROXY_AUTHNAME" />
					<div class="validateErrorMsg"><html:errors property="OTA_PROXY_AUTHNAME" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal"><bean:message key="ota.nokia.connection.label.OTA_PROXY_AUTHSECRET" /></th>
				<td>
					<html:text property="OTA_PROXY_AUTHSECRET" />
					<div class="validateErrorMsg"><html:errors property="OTA_PROXY_AUTHSECRET" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal"><bean:message key="ota.nokia.connection.label.OTA_PORT" /></th>
				<td>
				  <INPUT TYPE="RADIO" CHECKED NAME="OTA_PORT" value="9201">Connection-oriented<br>
				  <INPUT TYPE="RADIO" NAME="OTA_PORT" value="9200">Connection-less<br>
				  <INPUT TYPE="RADIO" NAME="OTA_PORT" value="9203">Connection-oriented, Secure (WTLS)<br>
				  <INPUT TYPE="RADIO" NAME="OTA_PORT" value="9202">Connection-less, Secure (WTLS)<br>
				  <div class="validateErrorMsg"><html:errors property="OTA_PORT" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal"><bean:message key="ota.nokia.connection.label.OTA_URL" /></th>
				<td>
					<html:text property="OTA_URL" size="48"/>
					<div class="validateErrorMsg"><html:errors property="OTA_URL" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal"><bean:message key="ota.nokia.connection.label.OTA_MMSURL" /></th>
				<td>
					<html:text property="OTA_MMSURL" size="48"/>
					<div class="validateErrorMsg"><html:errors property="OTA_MMSURL" /></div>
				</td>
			</tr>
		</tbody>
	</table>
	<div class="buttonArea" style="height: 40px; weight: 100%;">
	    <input type="submit" value="<bean:message key="ota.omacp.button.send" />">
	</div>
</html:form>
