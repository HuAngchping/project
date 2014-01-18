<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

			<tr>
				<th colspan="2" style="text-align: left;"><bean:message key="ota.omacp.title.syncmlds" /></th>
			</tr>
			<tr>
				<th style="font-weight: normal">* <bean:message key="ota.omacp.label.OTAOMA_SYNCML_HOSTADDR" /></th>
				<td>
					<html:text property="OTAOMA_SYNCML_HOSTADDR" size="48"/>
					<div class="validateErrorMsg"><html:errors property="OTAOMA_SYNCML_HOSTADDR" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal">* <bean:message key="ota.omacp.label.OTAOMA_SYNCML_AAUTHNAME" /></th>
				<td>
					<html:text property="OTAOMA_SYNCML_AAUTHNAME" />
					<div class="validateErrorMsg"><html:errors property="OTAOMA_SYNCML_AAUTHNAME" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal">* <bean:message key="ota.omacp.label.OTAOMA_SYNCML_AAUTHSECRET" /></th>
				<td>
					<html:text property="OTAOMA_SYNCML_AAUTHSECRET" />
					<div class="validateErrorMsg"><html:errors property="OTAOMA_SYNCML_AAUTHSECRET" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal"><bean:message key="ota.omacp.label.OTAOMA_SYNCML_CONTACTS_URI" /></th>
				<td>
					<html:text property="OTAOMA_SYNCML_CONTACTS_URI" />
					<div class="validateErrorMsg"><html:errors property="OTAOMA_SYNCML_CONTACTS_URI" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal"><bean:message key="ota.omacp.label.OTAOMA_SYNCML_CONTACTS_NAME" /></th>
				<td>
					<html:text property="OTAOMA_SYNCML_CONTACTS_NAME" />
					<div class="validateErrorMsg"><html:errors property="OTAOMA_SYNCML_CONTACTS_NAME" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal"><bean:message key="ota.omacp.label.OTAOMA_SYNCML_CALENDAR_URI" /></th>
				<td>
					<html:text property="OTAOMA_SYNCML_CALENDAR_URI" />
					<div class="validateErrorMsg"><html:errors property="OTAOMA_SYNCML_CALENDAR_URI" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal"><bean:message key="ota.omacp.label.OTAOMA_SYNCML_CALENDAR_NAME" /></th>
				<td>
					<html:text property="OTAOMA_SYNCML_CALENDAR_NAME" />
					<div class="validateErrorMsg"><html:errors property="OTAOMA_SYNCML_CALENDAR_NAME" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal"><bean:message key="ota.omacp.label.OTAOMA_SYNCML_NOTES_URI" /></th>
				<td>
					<html:text property="OTAOMA_SYNCML_NOTES_URI" />
					<div class="validateErrorMsg"><html:errors property="OTAOMA_SYNCML_NOTES_URI" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal"><bean:message key="ota.omacp.label.OTAOMA_SYNCML_NOTES_NAME" /></th>
				<td>
					<html:text property="OTAOMA_SYNCML_NOTES_NAME" />
					<div class="validateErrorMsg"><html:errors property="OTAOMA_SYNCML_NOTES_NAME" /></div>
				</td>
			</tr>
