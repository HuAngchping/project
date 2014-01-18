<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<div class="messageArea">
  <bean:message key="page.message.required"/><bean:message key="page.message.input"/>
</div>

<html:form action="/SaveProfileAttribute">
<!--  Template ID -->
<html:hidden property="templateID" />
<!--  Attribute ID -->
<html:hidden property="attributeID"/>
<table class="entityview">
  <thead>
    <tr>
      <th colspan="2"><bean:message key="page.profile.attribute.title.form.label"/></th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th style="width: 200px;">* <bean:message key="profile.attribute.name.label"/></th>
      <td><html:text property="name" size="48"/><div class="validateErrorMsg"><html:errors property="name"/></div></td>
    </tr>
    <tr>
      <th><bean:message key="profile.attribute.displayName.label"/></th>
      <td><html:text property="displayName" size="48"/><div class="validateErrorMsg"><html:errors property="displayName"/></div></td>
    </tr>
    <tr>
      <th>* <bean:message key="profile.attribute.attributeType.label"/></th>
      <td>
        <html:select property="attributeTypeID">
          <html:option value=""><bean:message key="page.select.option.default.label"/></html:option>
          <html:optionsCollection name="profileAttributeTypes" label="name" value="ID"/>
        </html:select>
        <div class="validateErrorMsg"><html:errors property="attributeTypeID"/></div></td>
    </tr>
    <tr>
      <th><bean:message key="profile.attribute.defaultValue.label"/></th>
      <td><html:text property="defaultValue" size="48"/><div class="validateErrorMsg"><html:errors property="defaultValue"/></div></td>
    </tr>
<%--
    <tr>
      <th><bean:message key="profile.attribute.isMultivalued.label"/></th>
      <td><html:checkbox property="isMultivalued"/><div class="validateErrorMsg"><html:errors property="isMultivalued"/></td>
    </tr>
--%>
    <tr>
      <th><bean:message key="profile.attribute.isRequired.label"/></th>
      <td><html:checkbox property="isRequired"/><div class="validateErrorMsg"><html:errors property="isUserAttribute"/></div></td>
    </tr>
    <tr>
      <th><bean:message key="profile.attribute.isUserAttribute.label"/></th>
      <td><html:checkbox property="isUserAttribute"/><div class="validateErrorMsg"><html:errors property="isUserAttribute"/></div></td>
    </tr>
    <tr>
      <th><bean:message key="profile.attribute.isValueUnique.label"/></th>
      <td><html:checkbox property="isValueUnique"/><div class="validateErrorMsg"><html:errors property="isValueUnique"/></div></td>
    </tr>
    <tr>
      <th><bean:message key="profile.attribute.displayValue.label"/></th>
      <td><html:checkbox property="displayValue"/><div class="validateErrorMsg"><html:errors property="displayValue"/></div></td>
    </tr>
    <tr>
      <th><bean:message key="profile.attribute.attributeIndex.label"/></th>
      <td><html:text property="attributeIndex" size="48"/><div class="validateErrorMsg"><html:errors property="attributeIndex"/></div></td>
    </tr>
    <tr>
      <th><bean:message key="profile.attribute.description.label"/></th>
      <td><html:textarea property="description" style="width: 400px;"/><div class="validateErrorMsg"><html:errors property="description"/></div></td>
    </tr>
  </tbody>
</table>
<div class="buttonArea">
  <logic:empty name="ProfileAttributeForm" property="attributeID">
    <html:submit property="action" styleClass="NormalWidthButton"><bean:message key="page.button.create.label"/></html:submit>
  </logic:empty>
  <logic:notEmpty name="ProfileAttributeForm" property="attributeID">
    <html:submit property="action" styleClass="NormalWidthButton"><bean:message key="page.button.update.label"/></html:submit>
  </logic:notEmpty>
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <html:reset styleClass="NormalWidthButton"><bean:message key="page.button.reset.label"/></html:reset>
  <html:cancel styleClass="NormalWidthButton"><bean:message key="page.button.cancel.label"/></html:cancel>
</div>
</html:form>

<!--  Values management  -->
<logic:notEmpty name="values">
    <!-- Space: height 20 -->
    <table cellpadding=0 cellspacing=0 border=0><tr><td height=20><spacer type=block width=1 height=20></td></tr></table>

	<div class="messageArea">
	  <bean:message key="page.profile.attribute.message.attributes"/>
	</div>

	<html:form action="/SaveProfileAttribute">
	<html:hidden name="ProfileAttributeForm" property="attributeID"/>
	<bean:define id="attributeID" name="ProfileAttributeForm" property="attributeID"></bean:define>
	<display:table name="values" id="value" class="simple">
	    <display:column class="rowNum">
	      <c:out value="${value_rowNum}"/>
	    </display:column>
		<display:column property="stringValue" titleKey="profile.attributeValue.value.label" />
	</display:table>
	<div class="buttonArea">
	  <INPUT type="text" name="value"> <html:submit property="action"><bean:message key="page.profile.attributebutton.createAttributeValue"/></html:submit>
	</div>
	</html:form>
</logic:notEmpty>


