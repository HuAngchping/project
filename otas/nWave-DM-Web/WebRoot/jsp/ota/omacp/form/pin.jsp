<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

			<tr>
				<th colspan="2" style="text-align: left;"><bean:message key="ota.omacp.title.otapin" /></th>
			</tr>
			<tr>
				<th style="font-weight: normal"><bean:message key="ota.omacp.label.OTAPIN" /></th>
				<td>
					<html:text property="OTAPIN" />
					<div class="validateErrorMsg"><html:errors property="OTAPIN" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal"><bean:message key="ota.omacp.label.OTAPINTYPE" /></th>
				<td>
					<html:radio property="OTAPINTYPE" value="USERPIN">User PIN</html:radio>
					<html:radio property="OTAPINTYPE" value="NETWPIN">Network PIN</html:radio>
					<div class="validateErrorMsg"><html:errors property="OTAPINTYPE" /></div>
				</td>
			</tr>
