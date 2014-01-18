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

function profileChanged() {
  selectList = document.getElementById("value(profileID)");
  profileID = selectList.options[selectList.selectedIndex].value;
  //alert(profileID);
  url = WEB_HTTP_BASE_PATH + '/ajax.do?method=getProfileDescription&profileID=' + profileID;
  new Ajax.Updater('profile_description', uncache(url), { method: 'get' });
}
</script>

<div class="WizardTitle">
<bean:message key="page.cp.wizard.title"/>
</div>
<hr class="WizardHR"/>
<html:form action="/wizard/cp/SelectProfile" method="POST">
<div class="WizardForm">
<p><bean:message key="page.cp.wizard.select.profile.help.1"/></p>
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
	  <div id="ModelIcon">
	    <logic:notEmpty name="ClientProvForm4Wizard" property="value(modelID)" scope="session">
	      <html:img action="/download.do?method=getModelIcon" paramId="modelID" paramName="ClientProvForm4Wizard" paramProperty="value(modelID)"/>
	    </logic:notEmpty>
	  </div>
  </td>
</tr>
<tr>
	<th><bean:message key="page.cp.wizard.select.category.category.name.label"/>: </th>
	<td>
	  <bean:write name="category" property="name"/>
  </td>
</tr>
<tr bgcolor="<logic:messagesPresent message="false" property="profileID">#fffbb8</logic:messagesPresent>">
	<th><bean:message key="page.cp.wizard.select.profile.profile.label"/>: </th>
	<td>
	  <html:select property="value(profileID)" onchange="profileChanged();">
	    <html:option value=""><bean:message key="page.cp.wizard.select.profile.profile.EmptyOptions"/></html:option>
	    <html:optionsCollection name="profileOptions"/>
	  </html:select>
  </td>
</tr>
<tr>
  <th></th>
  <td id="profile_description">
	<logic:present name="profile">
	<logic:notEmpty name="profile" property="description">
	  <div class="ProfileDescription"><bean:write name="profile" property="description"/></div>
	</logic:notEmpty>
	</logic:present>
  </td>
</tr>
<tr>
  <th></th>
	<td>
	  <bean:message key="page.cp.wizard.select.profile.help.2"/><br/>
	  <html:checkbox property="value(advanceProfileParameterMode)"><bean:message key="page.cp.wizard.select.profile.advanceMode"/></html:checkbox>
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