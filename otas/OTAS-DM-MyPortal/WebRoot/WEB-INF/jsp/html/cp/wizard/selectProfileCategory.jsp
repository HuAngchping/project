<?xml version="1.0" encoding="UTF-8" ?>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested"	prefix="nested"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
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
<html:form action="/wizard/cp/SelectProfileCategory" method="POST">
<div class="WizardForm">
	<p><bean:message key="page.cp.wizard.select.category.help.1"/></p>
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
		<th><bean:message key="page.cp.wizard.select.model.model.label"/>: </th>
		<td>
		  <bean:write name="manufacturer" property="name"/> <bean:write name="model" property="name"/>
	  </td>
	</tr>
	<tr>
		<th></th>
		<td>
	    <br/><html:img action="/download.do?method=getModelIcon" paramId="modelID" paramName="model" paramProperty="ID"/>
	  </td>
	</tr>
	</table>
  <div style="margin: 0px;padding-bottom: 0px; padding-left: 10px; padding-top: 10px;"><b><bean:message key="page.cp.wizard.select.category.help.2"/></b></div>
	<display:table name="categories" id="category" partialList="false" class="simple">
	  <display:column class="rowNum">
	    <html:radio property="value(profileCategoryID)" value="${category.decoratee.name}"></html:radio>
	  </display:column>
	  <display:column property="name" titleKey="page.cp.wizard.select.category.category.name.label"/>
	  <display:column property="description" titleKey="page.cp.wizard.select.category.category.description.label"/>
	</display:table>
  <div style="margin: 0px;padding-bottom: 0px; padding-left: 15px; padding-top: 0px;">
    <html:checkbox property="value(showAllOfCategory)" onclick="this.form.submit();"> <bean:message key="page.cp.wizard.select.category.checkbox.showAll.label"/></html:checkbox>
  </div>
</div>
<hr class="WizardHR"/>
<div class="WizardNavigation">
  <html:submit property="navigation" styleId="PrevButton" onclick="this.form.submit();"><bean:message key="page.button.prev.label"/></html:submit>
  &nbsp;&nbsp;&nbsp;
  <html:submit property="navigation" styleId="NextButton"><bean:message key="page.button.next.label"/></html:submit>
</div>
</html:form>