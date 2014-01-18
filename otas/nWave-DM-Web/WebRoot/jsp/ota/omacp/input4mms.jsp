<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

<script type="text/javascript" language="javascript">
function setPort9200() {
   document.OMAClientProvForm.OTAOMA_PXLOGICAL_PXPHYSICAL_PORT_PORTNBR.value = "9200";
}
function setPort9201() {
   document.OMAClientProvForm.OTAOMA_PXLOGICAL_PXPHYSICAL_PORT_PORTNBR.value = "9201";
}
function setPort9202() {
   document.OMAClientProvForm.OTAOMA_PXLOGICAL_PXPHYSICAL_PORT_PORTNBR.value = "9202";
}
function setPort9203() {
   document.OMAClientProvForm.OTAOMA_PXLOGICAL_PXPHYSICAL_PORT_PORTNBR.value = "9203";
}
</script>

<div class="messageArea">
  <bean:message key="page.message.required"/><bean:message key="page.message.input"/>
  <div class="validateErrorMsg"><html:errors header="common.errors.header" footer="common.errors.footer" prefix="common.errors.prefix" suffix="common.errors.suffix"/></div>
</div>


<html:form action="/ota/omacp/send4mms" method="post" focus="msisdn">
<html:hidden property="cp_type"/>

	<table class="entityview">
		<tbody>
		  <!-- Form Element: Targets -->
		  <jsp:include flush="true" page="/jsp/ota/omacp/form/targets.jsp"></jsp:include>

		  <!-- Form Element: Setting name -->
		  <jsp:include flush="true" page="/jsp/ota/omacp/form/name.jsp"></jsp:include>

		  <!-- Form Element: NAP -->
		  <jsp:include flush="true" page="/jsp/ota/omacp/form/nap.jsp"></jsp:include>

		  <!-- Form Element: Proxy -->
		  <jsp:include flush="true" page="/jsp/ota/omacp/form/proxy.jsp"></jsp:include>

		  <!-- Form Element: MMS -->
		  <jsp:include flush="true" page="/jsp/ota/omacp/form/mms.jsp"></jsp:include>

		  <!-- Form Element: OTAS PIN -->
		  <jsp:include flush="true" page="/jsp/ota/omacp/form/pin.jsp"></jsp:include>

		</tbody>
	</table>
	<div class="buttonArea" style="height: 40px; weight: 100%;">
	    <input type="submit" name="method" value="<bean:message key="ota.omacp.button.send" />">
	    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    <input type="submit" name="method" value="<bean:message key="ota.omacp.button.viewXML" />">
	</div>
</html:form>
