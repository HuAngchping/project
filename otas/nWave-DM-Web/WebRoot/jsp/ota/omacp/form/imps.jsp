<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

			<tr>
				<th colspan="2" style="text-align: left;"><bean:message key="ota.omacp.title.imps" /></th>
			</tr>
			<tr>
				<th style="font-weight: normal">* <bean:message key="ota.omacp.label.OTAOMA_IMPS_HOSTADDR" /></th>
				<td>
					<html:text property="OTAOMA_IMPS_HOSTADDR" size="48"/>
					<div class="validateErrorMsg"><html:errors property="OTAOMA_IMPS_HOSTADDR" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal">* <bean:message key="ota.omacp.label.OTAOMA_IMPS_USERNAME" /></th>
				<td>
					<html:text property="OTAOMA_IMPS_USERNAME" />
					<div class="validateErrorMsg"><html:errors property="OTAOMA_IMPS_USERNAME" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal">* <bean:message key="ota.omacp.label.OTAOMA_IMPS_PASSWORD" /></th>
				<td>
					<html:text property="OTAOMA_IMPS_PASSWORD" />
					<div class="validateErrorMsg"><html:errors property="OTAOMA_IMPS_PASSWORD" /></div>
				</td>
			</tr>
