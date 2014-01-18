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
<bean:message key="page.dm.msm.wizard.title"/>
</div>
<hr class="WizardHR"/>
<html:form action="/wizard/dm/msm/install/job/choice_download_method" method="POST">
<div class="WizardForm">
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
<tr bgcolor="<logic:messagesPresent message="false" property="phoneNumber">#fffbb8</logic:messagesPresent>">
  <th nowrap="nowrap"><bean:message key="page.cp.wizard.input.phone.phone.label"/>: </th>
  <td>
    <bean:write name="ClientProvForm4Wizard" property="value(phoneNumber)"/>
  </td>
</tr>
<tr>
  <th><bean:message key="page.dm.wizard.select.software.label"/>: </th>
  <td>
    <logic:notEmpty name="software" property="vendor.webSite">
    <a href="<bean:write name="software" property="vendor.webSite"/>" target="_blank" class="external_link">
      <b style="font-size: 120%; color: blue;"><bean:write name="software" property="vendor.name"/> <bean:write name="software" property="name"/></b>
    </a>
    </logic:notEmpty>
    <logic:empty name="software" property="vendor.webSite">
      <b style="font-size: 120%; color: blue;"><bean:write name="software" property="vendor.name"/> <bean:write name="software" property="name"/></b>
    </logic:empty>
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
</table>
<div style="padding-left: 30px; margin-top: 30px; font-size: 110%; ">
  <div>
    <div style="margin-bottom: 10px; font-weight: bold; color: red;">
     <html:img page="/common/images/help/icon_light.gif"/>
      <bean:message key="page.dm.msm.wappush.choice.method.wizard.help.1"/>
    </div>
    <div style="margin-bottom: 20px;">
      <div style="margin-bottom: 5px; font-weight: bold;"><bean:message key="page.dm.msm.wappush.sms.method.wizard.help.1"/></div>
      <div style="margin-bottom: 5px; padding-left: 20px;"><bean:message key="page.dm.msm.wappush.sms.method.wizard.help.2"/></div>
      <div style="margin-bottom: 5px; padding-left: 20px;">
        <html:submit property="navigation" styleId="NextButton"><bean:message key="page.msm.send.wappush.button.label"/></html:submit>
      </div>
    </div>
    <div style="margin-bottom: 20px;">
      <div style="margin-bottom: 5px; font-weight: bold;"><bean:message key="page.dm.msm.wappush.wap.method.wizard.help.1"/></div>
      <div style="margin-bottom: 5px; padding-left: 20px;"><bean:message key="page.dm.msm.wappush.wap.method.wizard.help.2"/></div>
      <div style="margin-bottom: 5px; padding-left: 20px;">
        <logic:notEmpty name="downloadURL">
          <a href="<bean:write name="downloadURL" />"><bean:write name="downloadURL" /></a>
        </logic:notEmpty>
      </div>
    </div>
    <div style="margin-bottom: 20px;">
      <div style="margin-bottom: 5px; font-weight: bold;"><bean:message key="page.dm.msm.wappush.desktop.method.wizard.help.1"/></div>
      <div style="margin-bottom: 5px; padding-left: 20px;"><bean:message key="page.dm.msm.wappush.desktop.method.wizard.help.2"/></div>
      <div style="margin-bottom: 5px; padding-left: 20px;">
        <logic:notEmpty name="downloadURL">
          <a href="<bean:write name="downloadURL" />"><bean:write name="downloadURL" /></a>
        </logic:notEmpty>
      </div>
    </div>
    <div style="margin-bottom: 10px; font-weight: bold; color: red;">
     <html:img page="/common/images/help/icon_light.gif"/>
      <bean:message key="page.dm.msm.wappush.retry.help.1"/>
    </div>
  </div>
</div>
</div>
<hr class="WizardHR"/>
<div class="WizardNavigation">
  <html:submit property="navigation" styleId="PrevButton" onclick="this.form.submit();"><bean:message key="page.button.prev.label"/></html:submit>
  &nbsp;&nbsp;&nbsp;
  
</div>
</html:form>