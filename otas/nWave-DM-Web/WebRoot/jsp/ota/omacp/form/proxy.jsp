<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

			<tr>
				<th colspan="2" style="text-align: left;"><bean:message key="ota.omacp.title.proxy" /></th>
			</tr>
			<tr>
				<th style="font-weight: normal"><bean:message key="ota.omacp.label.OTAOMA_PXLOGICAL_PXPHYSICAL_PXADDR" /></th>
				<td>
					<html:text property="OTAOMA_PXLOGICAL_PXPHYSICAL_PXADDR" />
					<div class="validateErrorMsg"><html:errors property="OTAOMA_PXLOGICAL_PXPHYSICAL_PXADDR" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal"><bean:message key="ota.omacp.label.OTAOMA_PXLOGICAL_PXAUTHINFO_PXAUTH" /></th>
				<td>
					<html:text property="OTAOMA_PXLOGICAL_PXAUTHINFO_PXAUTH" />
					<div class="validateErrorMsg"><html:errors property="OTAOMA_PXLOGICAL_PXAUTHINFO_PXAUTH" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal"><bean:message key="ota.omacp.label.OTAOMA_PXLOGICAL_PXAUTHINFO_PXAUTH-PW" /></th>
				<td>
					<html:text property="OTAOMA_PXLOGICAL_PXAUTHINFO_PXAUTH-PW" />
					<div class="validateErrorMsg"><html:errors property="OTAOMA_PXLOGICAL_PXAUTHINFO_PXAUTH-PW" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal"><bean:message key="ota.omacp.label.OTAOMA_PXLOGICAL_PXPHYSICAL_PORT_PORTNBR" /></th>
				<td>
				  <INPUT TYPE="RADIO" CHECKED NAME="PORT_BUTTON" onclick="setPort9201()">Connection-oriented<br>
				  <INPUT TYPE="RADIO" NAME="PORT_BUTTON" onclick="setPort9200()">Connection-less<br>
				  <INPUT TYPE="RADIO" NAME="PORT_BUTTON" onclick="setPort9203()">Connection-oriented, Secure (WTLS)<br>
				  <INPUT TYPE="RADIO" NAME="PORT_BUTTON" onclick="setPort9202()">Connection-less, Secure (WTLS)<br>
				  <INPUT TYPE="RADIO" NAME="PORT_BUTTON">Other&nbsp;&nbsp;<html:text property="OTAOMA_PXLOGICAL_PXPHYSICAL_PORT_PORTNBR" size="6" maxlength="6"/><br>
				  <div class="validateErrorMsg"><html:errors property="OTAOMA_PXLOGICAL_PXPHYSICAL_PORT_PORTNBR" /></div>
				</td>
			</tr>
