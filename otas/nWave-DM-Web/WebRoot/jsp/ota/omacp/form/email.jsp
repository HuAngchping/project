<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

			<tr>
				<th colspan="2" style="text-align: left;"><bean:message key="ota.omacp.title.email" /></th>
			</tr>
			<tr>
				<th style="font-weight: normal"><bean:message key="ota.omacp.label.OTAOMA_EMAIL_APPID" /></th>
				<td>
					<html:radio property="OTAOMA_EMAIL_APPID" value="110">POP3</html:radio>
					<html:radio property="OTAOMA_EMAIL_APPID" value="143">IMAP4</html:radio>
					<div class="validateErrorMsg"><html:errors property="OTAOMA_EMAIL_APPID" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal">* <bean:message key="ota.omacp.label.OTAOMA_EMAIL_AAUTHNAME" /></th>
				<td>
					<html:text property="OTAOMA_EMAIL_AAUTHNAME" />
					<div class="validateErrorMsg"><html:errors property="OTAOMA_EMAIL_AAUTHNAME" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal">* <bean:message key="ota.omacp.label.OTAOMA_EMAIL_AAUTHSECRET" /></th>
				<td>
					<html:text property="OTAOMA_EMAIL_AAUTHSECRET" />
					<div class="validateErrorMsg"><html:errors property="OTAOMA_EMAIL_AAUTHSECRET" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal">* <bean:message key="ota.omacp.label.OTAOMA_EMAIL_FROM" /></th>
				<td>
					<html:text property="OTAOMA_EMAIL_FROM" size="48"/>
					<div class="validateErrorMsg"><html:errors property="OTAOMA_EMAIL_FROM" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal">* <bean:message key="ota.omacp.label.OTAOMA_EMAIL_INADDR" /></th>
				<td>
					<html:text property="OTAOMA_EMAIL_INADDR" />
					<div class="validateErrorMsg"><html:errors property="OTAOMA_EMAIL_INADDR" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal">* <bean:message key="ota.omacp.label.OTAOMA_EMAIL_INADDR_PORTNBR" /></th>
				<td>
					<html:text property="OTAOMA_EMAIL_INADDR_PORTNBR" />
					<div class="validateErrorMsg"><html:errors property="OTAOMA_EMAIL_INADDR_PORTNBR" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal">* <bean:message key="ota.omacp.label.OTAOMA_EMAIL_OUTADDR" /></th>
				<td>
					<html:text property="OTAOMA_EMAIL_OUTADDR" />
					<div class="validateErrorMsg"><html:errors property="OTAOMA_EMAIL_OUTADDR" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal">* <bean:message key="ota.omacp.label.OTAOMA_EMAIL_OUTADDR_PORTNBR" /></th>
				<td>
					<html:text property="OTAOMA_EMAIL_OUTADDR_PORTNBR" />
					<div class="validateErrorMsg"><html:errors property="OTAOMA_EMAIL_OUTADDR_PORTNBR" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal"><bean:message key="ota.omacp.label.OTAOMA_EMAIL_SMTPAUTH" /></th>
				<td>
					<html:radio property="OTAOMA_EMAIL_SMTPAUTH" value="true">Yes</html:radio>
					<html:radio property="OTAOMA_EMAIL_SMTPAUTH" value="false">No</html:radio>
					<div class="validateErrorMsg"><html:errors property="OTAOMA_EMAIL_SMTPAUTH" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal"><bean:message key="ota.omacp.label.OTAOMA_EMAIL_STARTTLS" /></th>
				<td>
					<html:radio property="OTAOMA_EMAIL_STARTTLS" value="true">Yes</html:radio>
					<html:radio property="OTAOMA_EMAIL_STARTTLS" value="false">No</html:radio>
					<div class="validateErrorMsg"><html:errors property="OTAOMA_EMAIL_STARTTLS" /></div>
				</td>
			</tr>
