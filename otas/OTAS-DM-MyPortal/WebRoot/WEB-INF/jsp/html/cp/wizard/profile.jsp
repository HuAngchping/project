<?xml version="1.0" encoding="UTF-8" ?>
<%@ page import="com.npower.dm.core.ProfileConfig" %>
<%@ page import="com.npower.dm.core.ProfileAttribute" %>
<%@ page import="com.npower.dm.core.ProfileAttributeType" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

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
<html:form action="/wizard/cp/InputProfileAttribute" method="POST">
<div class="WizardForm">
<p><bean:message key="page.cp.wizard.input.profile.help.1"/></p>
<p><bean:message key="page.cp.wizard.input.profile.help.2"/></p>
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
<tr>
	<th><bean:message key="page.cp.wizard.select.profile.profile.label"/>: </th>
	<td>
	  <bean:write name="profile" property="name"/>
  </td>
</tr>
<tr>
	<th><bean:message key="page.cp.wizard.input.profile.help.3"/></th>
	<td></td>
</tr>
<logic:iterate id="attribute" name="attributes">
    <c:set var="originalValue" value="${attribute.defaultValue}" />
    <c:set var="attributeName" value="${attribute.name}" />
    <c:if test="${not empty profile}">
    <%
      String attributeName = ((ProfileAttribute)attribute).getName();
      ProfileConfig currentProfile = (ProfileConfig)request.getAttribute("profile");
      String valueFromRequest = request.getParameter("value(attribute__" + ((ProfileAttribute)attribute).getID() + "__value)");
      if (valueFromRequest != null && valueFromRequest.trim().length() > 0) {
         pageContext.setAttribute("originalValue", "" + valueFromRequest);
      }
      if ( currentProfile.getProfileAttributeValue((String)attributeName) != null ) {
         ProfileAttribute pAttribute = (ProfileAttribute)attribute;
         if (pAttribute.getProfileAttribType().getName() != null
           && ProfileAttributeType.TYPE_BINARY.equalsIgnoreCase(pAttribute.getProfileAttribType().getName())) {
           long value = 0;
           if (currentProfile.getProfileAttributeValue((String)attributeName) != null
               && currentProfile.getProfileAttributeValue((String)attributeName).getBinaryData() != null) {
              value = currentProfile.getProfileAttributeValue((String)attributeName).getBinaryData().length();
	         }
           pageContext.setAttribute("originalValue", "" + value);
         } else {
	         String value = currentProfile.getProfileAttributeValue((String)attributeName).getStringValue();
	         if (value != null && value.trim().length() > 0) {
	            pageContext.setAttribute("originalValue", value);
	         }
         }
      }
    %>
    </c:if>
	<tr bgcolor="<logic:messagesPresent message="false" property="attribute__.${attribute.ID}__value">#fffbb8</logic:messagesPresent>">
		<th>
		  <logic:equal value="true" name="attribute" property="isRequired">*</logic:equal>
		  <bean:write name="attribute" property="displayName"/>: 
		</th>
		<td>
		  <html:hidden property="value(attribute__${attribute.ID}__name)" value="${attribute.name}"></html:hidden>
		  <html:hidden property="value(attribute__${attribute.ID}__required)" value="${attribute.isRequired}"></html:hidden>
		  <html:hidden property="value(attribute__${attribute.ID}__typeName)" value="${attribute.profileAttribType.name}"></html:hidden>
 		  <!-- Input -->
		  <c:if test="${empty attribute.profileAttribType.attribTypeValues}">
			  <c:if test="${attribute.profileAttribType.name != 'Binary'}">
				  <c:if test="${attribute.profileAttribType.name == 'Password'}">
		 		    <html:password property="value(attribute__${attribute.ID}__value)" size="12" redisplay="false"/>
				  </c:if>
				  <c:if test="${attribute.profileAttribType.name != 'Password'}">
		 		    <html:text property="value(attribute__${attribute.ID}__value)" value="${originalValue}" size="36"/>
				  </c:if>
			  </c:if>
		  </c:if>

 		  <!-- Select List -->
		  <c:if test="${not empty attribute.profileAttribType.attribTypeValues}">
		    <%-- Attribute Type: Multiple values --%>
		    <html:select property="value(attribute__${attribute.ID}__value)" value="${originalValue}">
              <html:option value=""><bean:message key="page.select.option.default.label"/></html:option>
				      <c:forEach items="${attribute.profileAttribType.attribTypeValues}" var="attribTypeValue">
	  		        <html:option value="${attribTypeValue.value}">
	  		          <c:out value="${attribTypeValue.value}"/>
	  		        </html:option>
				      </c:forEach>
		    </html:select>
		  </c:if>
		  
		  <!-- Help: Hints -->
		  <c:if test="${not empty attribute.description}">
        <html:img page="/common/images/icons/icon_help.gif" alt="${attribute.description}"></html:img>
		  </c:if>

	</td>
</tr>

<c:if test="${empty attribute.profileAttribType.attribTypeValues}">
 <c:if test="${attribute.profileAttribType.name != 'Binary'}">
  <c:if test="${attribute.profileAttribType.name == 'Password'}">
<tr bgcolor="<logic:messagesPresent message="false" property="attribute__${attribute.ID}__value">#fffbb8</logic:messagesPresent>">
	<th>
	  <logic:equal value="true" name="attribute" property="isRequired">*</logic:equal>
	  <bean:message key="tile.cp.wizard.input.profile.password.again.label"/>:</th>
	<td>
		<html:password property="value(attribute__${attribute.ID}__value__confirm)" size="12" redisplay="false"/>
  </td>
</tr>
<tr>
	<th></th>
	<td><bean:message key="tile.cp.wizard.input.profile.password.again.msg"/></td>
</tr>
  </c:if>
 </c:if>
</c:if>

</logic:iterate>
</table>

</div>
<hr class="WizardHR"/>
<div class="WizardNavigation">
  <html:submit property="navigation" styleId="PrevButton" onclick="this.form.submit();"><bean:message key="page.button.prev.label"/></html:submit>
  &nbsp;&nbsp;&nbsp;
  <html:submit property="navigation" styleId="NextButton"><bean:message key="page.button.next.label"/></html:submit>
</div>
</html:form>