<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

			<tr>
				<th colspan="2" style="text-align: left;"><bean:message key="ota.omacp.title.browser" /></th>
			</tr>
			<tr>
				<th style="font-weight: normal">* <bean:message key="ota.omacp.label.OTAOMA_PXLOGICAL_STARTPAGE" /></th>
				<td>
					<html:text property="OTAOMA_PXLOGICAL_STARTPAGE" size="48"/>
					<div class="validateErrorMsg"><html:errors property="OTAOMA_PXLOGICAL_STARTPAGE" /></div>
				</td>
			</tr>
