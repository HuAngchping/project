<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

			<tr>
				<th colspan="2" style="text-align: left;"><bean:message key="ota.omacp.title.nap" /></th>
			</tr>
			<tr>
				<th style="font-weight: normal"><bean:message key="ota.omacp.label.OTAOMA_NAPDEF_BEARER" /></th>
				<td>
					<html:radio property="OTAOMA_NAPDEF_BEARER" value="GSM-GPRS">GPRS</html:radio>
					<html:radio property="OTAOMA_NAPDEF_BEARER" value="GSM-CSD">GSM/CSD</html:radio>
					<div class="validateErrorMsg"><html:errors property="OTAOMA_NAPDEF_BEARER" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal">* <bean:message key="ota.omacp.label.OTAOMA_NAPDEF_NAP-ADDRESS" /></th>
				<td>
					<html:text property="OTAOMA_NAPDEF_NAP-ADDRESS" />
					<div class="validateErrorMsg"><html:errors property="OTAOMA_NAPDEF_NAP-ADDRESS" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal"><bean:message key="ota.omacp.label.OTAOMA_NAPDEF_NAPAUTHINFO_AUTHNAME" /></th>
				<td>
					<html:text property="OTAOMA_NAPDEF_NAPAUTHINFO_AUTHNAME" />
					<div class="validateErrorMsg"><html:errors property="OTAOMA_NAPDEF_NAPAUTHINFO_AUTHNAME" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal"><bean:message key="ota.omacp.label.OTAOMA_NAPDEF_NAPAUTHINFO_AUTHSECRET" /></th>
				<td>
					<html:text property="OTAOMA_NAPDEF_NAPAUTHINFO_AUTHSECRET" />
					<div class="validateErrorMsg"><html:errors property="OTAOMA_NAPDEF_NAPAUTHINFO_AUTHSECRET" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal"><bean:message key="ota.omacp.label.OTAOMA_NAPDEF_NAPAUTHINFO_AUTHTYPE" /></th>
				<td>
					<html:radio property="OTAOMA_NAPDEF_NAPAUTHINFO_AUTHTYPE" value="PAP">Standard (PAP)</html:radio>
					<html:radio property="OTAOMA_NAPDEF_NAPAUTHINFO_AUTHTYPE" value="CHAP">Secure (CHAP)</html:radio>
					<html:radio property="OTAOMA_NAPDEF_NAPAUTHINFO_AUTHTYPE" value="MS_CHAP">MS-CHAP</html:radio>
					<div class="validateErrorMsg"><html:errors property="OTAOMA_NAPDEF_NAPAUTHINFO_AUTHTYPE" /></div>
				</td>
			</tr>
