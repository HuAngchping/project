<?xml version="1.0" encoding="UTF-8" ?>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"
	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"
	prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested"
	prefix="nested"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script language="javascript">     
function document.onkeydown() {     
var e = event.srcElement;     
  if (event.keyCode == 13) {     
     document.getElementById("NextButton").click();     
     return false;     
  }     
}     
</script>

<div class="WizardTitle">
<bean:message key="page.cp.wizard.title"/>
</div>
<hr class="WizardHR"/>
<html:form action="/wizard/cp/InputPhone" method="POST" focus="value(phoneNumber)">
<div class="WizardForm">
<p><bean:message key="page.cp.wizard.input.phone.help"/></p>
<logic:messagesPresent message="false">
<div class="WizardMessage">
  <html:errors/>
</div>
</logic:messagesPresent>
<table border="0">
<tr>
	<th><bean:message key="page.cp.wizard.select.country.country.label"/>: </th>
	<td>
	  <bean:write name="country" property="name"/>
  </td>
</tr>
<tr>
	<th><bean:message key="page.cp.wizard.select.carrier.carrier.label"/>: </th>
	<td>
	  <bean:write name="carrier" property="name"/>
  </td>
</tr>
<tr>
  <th><bean:message key="page.cp.wizard.select.category.category.name.label"/>: </th>
  <td>
    <bean:write name="category" property="name"/>
  </td>
</tr>
<tr>
  <th><bean:message key="page.cp.wizard.select.profile.profile.label"/>: </th>
  <td>
    <bean:write name="profile" property="name"/>
  </td>
</tr>
<logic:notEmpty name="model">
  <tr>
    <th><bean:message key="page.cp.wizard.select.model.model.label"/>: </th>
    <td>
      <bean:write name="manufacturer" property="name"/> <bean:write name="model" property="name"/>
    </td>
  </tr>
  <tr>
    <th></th>
    <td>
      <div id="ModelIcon">
        <logic:notEmpty name="ClientProvForm4Wizard" property="value(modelID)" scope="session">
          <html:img action="/download.do?method=getModelIcon" paramId="modelID" paramName="ClientProvForm4Wizard" paramProperty="value(modelID)"/>
        </logic:notEmpty>
      </div>
    </td>
  </tr>
</logic:notEmpty>
<logic:empty name="model">
  <jsp:include page="/WEB-INF/jsp/html/common/model_ui_element.jsp"/>
</logic:empty>
<tr bgcolor="<logic:messagesPresent message="false" property="phoneNumber">#fffbb8</logic:messagesPresent>">
	<th nowrap="nowrap"><bean:message key="page.cp.wizard.input.phone.phone.label"/>: </th>
	<td>
	  <html:text property="value(phoneNumber)"></html:text>
  </td>
</tr>
<tr>
	<th></th>
	<td>
	  <bean:message key="page.cp.wizard.input.phone.phone.hint"/>
  </td>
</tr>
<tr bgcolor="<logic:messagesPresent message="false" property="checkcode">#fffbb8</logic:messagesPresent>">
	<th><bean:message key="page.cp.wizard.input.phone.checkcode.label"/>: </th>
	<td>
	  <input type="text" name="checkcode" value=""/> <html:img action="/common/checkcode.do" style="vertical-align: bottom" styleClass="check_code_image"/>
  </td>
</tr>
<tr>
	<th></th>
	<td>
	  <bean:message key="page.cp.wizard.input.phone.checkcode.hint"/><br/><br/>
  </td>
</tr>
<tr>
  <th><bean:message key="page.cp.wizard.input.cpmode.label"/></th>
  <td>
    <html:checkbox property="value(cpMode)"></html:checkbox>
  </td>
</tr>
</table>
</div>
<hr class="WizardHR"/>
<div class="WizardNavigation">
  <html:submit property="navigation" styleId="PrevButton" onclick="this.form.submit();"><bean:message key="page.button.prev.label"/></html:submit>
  &nbsp;&nbsp;&nbsp;
  <html:submit property="navigation" styleId="NextButton"><bean:message key="page.button.next.label"/></html:submit>
</div>
</html:form>