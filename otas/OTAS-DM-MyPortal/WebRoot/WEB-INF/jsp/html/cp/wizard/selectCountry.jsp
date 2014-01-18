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
<html:form method="POST" action="/wizard/cp/SelectCountry">
<div class="WizardForm">
<p><bean:message key="page.cp.wizard.select.country.help"/></p>
<logic:messagesPresent message="false">
<div class="WizardMessage">
  <html:errors/>
</div>
</logic:messagesPresent>
<table border="0">
<tr bgcolor="<logic:messagesPresent message="false" property="countryID">#fffbb8</logic:messagesPresent>">
	<th><bean:message key="page.cp.wizard.select.country.country.label"/>: </th>
	<td>
	  <html:select property="value(countryID)">
	    <html:option value=""><bean:message key="page.cp.wizard.select.country.country.EmptyOptions"/></html:option>
	    <html:optionsCollection name="countryOptions"/>
	  </html:select>
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