<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

			<tr>
				<th colspan="2" style="text-align: left;"><bean:message key="ota.omacp.title.poc" /></th>
			</tr>
			<tr>
				<th style="font-weight: normal">* <bean:message key="ota.omacp.label.OTAOMA_POC_ADDR" /></th>
				<td>
					<html:text property="OTAOMA_POC_ADDR" size="48"/>
					<div class="validateErrorMsg"><html:errors property="OTAOMA_POC_ADDR" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal">* <bean:message key="ota.omacp.label.OTAOMA_POC_ADDR_TYPE" /></th>
				<td>
					<html:radio property="OTAOMA_POC_ADDR_TYPE" value="IPv4">IPv4</html:radio>
					<html:radio property="OTAOMA_POC_ADDR_TYPE" value="IPV6">IPv6</html:radio>
					<html:radio property="OTAOMA_POC_ADDR_TYPE" value="E164">E164</html:radio>
					<html:radio property="OTAOMA_POC_ADDR_TYPE" value="ALPHA">ALPHA</html:radio>
					<div class="validateErrorMsg"><html:errors property="OTAOMA_POC_ADDR_TYPE" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal">* <bean:message key="ota.omacp.label.OTAOMA_POC_PORT" /></th>
				<td>
					<html:text property="OTAOMA_POC_PORT" size="48"/>
					<div class="validateErrorMsg"><html:errors property="OTAOMA_POC_PORT" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal">* <bean:message key="ota.omacp.label.OTAOMA_POC_AUTH_DATA" /></th>
				<td>
					<html:text property="OTAOMA_POC_AUTH_DATA" size="48"/>
					<div class="validateErrorMsg"><html:errors property="OTAOMA_POC_AUTH_DATA" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal">* <bean:message key="ota.omacp.label.OTAOMA_POC_AUTH_NAME" /></th>
				<td>
					<html:text property="OTAOMA_POC_AUTH_NAME" />
					<div class="validateErrorMsg"><html:errors property="OTAOMA_POC_AUTH_NAME" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal">* <bean:message key="ota.omacp.label.OTAOMA_POC_AUTH_SECRET" /></th>
				<td>
					<html:text property="OTAOMA_POC_AUTH_SECRET" />
					<div class="validateErrorMsg"><html:errors property="OTAOMA_POC_AUTH_SECRET" /></div>
				</td>
			</tr>
