<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

			<tr>
				<th style="font-weight: normal">* <bean:message key="ota.omacp.label.OTAOMA_NAME" /></th>
				<td>
					<html:text property="OTAOMA_NAME" />
					<div class="validateErrorMsg"><html:errors property="ota.omacp.label.OTAOMA_NAME" /></div>
				</td>
			</tr>
