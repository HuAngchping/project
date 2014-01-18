<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

<div class="messageArea">
  <bean:message key="page.message.required"/><bean:message key="page.message.input"/>
  <div class="validateErrorMsg"><html:errors header="common.errors.header" footer="common.errors.footer" prefix="common.errors.prefix" suffix="common.errors.suffix"/></div>
</div>


<html:form action="/ota/omacp/send4cpdocument" method="post" focus="msisdn">
  <input type="hidden" name="cp_type" value="document"/>
	<table class="entityview">
		<tbody>
		  <!-- Form Element: Targets -->
		  <jsp:include flush="true" page="/jsp/ota/omacp/form/targets.jsp"></jsp:include>

			<tr>
				<th style="font-weight: normal">* <bean:message key="ota.omacp.label.OTAS_CP_DOCUMENT" /></th>
				<td>
					<html:textarea property="document" style="width: 480px; height: 400;" value="${request.msisdn}"/>
					<div class="validateErrorMsg"><html:errors property="document" /></div>
				</td>
			</tr>

		  <!-- Form Element: PIN -->
		  <jsp:include flush="true" page="/jsp/ota/omacp/form/pin.jsp"></jsp:include>
		</tbody>
	</table>
	<div class="buttonArea" style="height: 40px; weight: 100%;">
	    <input type="submit" name="method" value="<bean:message key="ota.omacp.button.send" />">
	</div>
</html:form>
