<%@ page language="java"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<div class="messageArea">
	<bean:message key="page.profiletemplate.message" /><br><br>
</div>

<html:form action="/SaveProfileTemplate">
	<html:hidden property="ID" />
	<table class="entityview">
		<thead>
			<tr>
				<th colspan="2">
					<bean:message key="page.profiletemplate.title.form.label" />
				</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<th>
					* <bean:message key="profiletemplate.title.name" />
				</th>
				<td><bean:write name="profileTemplate" property="name" /></td>
			</tr>
			<tr>
				<th>
					* <bean:message key="profiletemplate.title.category" />
				</th>
				<td><bean:write name="profileTemplate" property="profileCategory.name"/></td>
			</tr>
			<tr>
				<th>
					<bean:message key="profiletemplate.title.needsNap" />
				</th>
				<td>
		      <html:img page="/common/images/icons/${profileTemplate.needsNap}.gif" alt="${profileTemplate.needsNap}"></html:img>
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="profiletemplate.title.needsProxy" />
				</th>
				<td>
		      <html:img page="/common/images/icons/${profileTemplate.needsProxy}.gif" alt="${profileTemplate.needsProxy}"></html:img>
				</td>
			</tr>
		</tbody>
	</table>
	<div class="buttonArea">
	  <logic:notEmpty name="ProfileTemplateForm" property="ID">
		    <html:button property="action" onclick="document.forms['ProfileTemplateForm'][1].submit();" styleClass="NormalWidthButton">
		      <bean:message key="page.button.edit.label"/>
		    </html:button>
	  </logic:notEmpty>
		<html:cancel styleClass="NormalWidthButton">
			<bean:message key="page.button.cancel.label" />
		</html:cancel>
	</div>
</html:form>

<html:form action="/EditProfileTemplate" method="post">
  <logic:notEmpty name="ProfileTemplateForm" property="ID">
    <html:hidden property="ID"/>
  </logic:notEmpty>
</html:form>

<!--  Attributes management  -->
<logic:notEmpty name="ProfileTemplateForm" property="ID">

	<!-- Space: height 20 -->
	<table cellpadding=0 cellspacing=0 border=0>
		<tr>
			<td height=20>
				<spacer type=block width=1 height=20>
			</td>
		</tr>
	</table>

	<div class="messageArea">
		<bean:message key="page.profiletemplate.message.attributes" />
	</div>

		<bean:define id="templateID" name="ProfileTemplateForm" property="ID"></bean:define>
		<display:table name="attributes" id="attribute" class="simple">
			<display:column class="rowNum">
				<c:out value="${attribute_rowNum}" />
			</display:column>
			<display:column property="name" titleKey="profile.attribute.name.label" style="white-space: nowrap;" />
			<display:column property="displayName" titleKey="profile.attribute.displayName.label" style="white-space: nowrap;" />
			<display:column titleKey="profile.attribute.attributeType.label" style="white-space: nowrap;">
			  <html:link action="/EditProfileAttributeType.do?action=update" styleClass="reference_link" target="_blank" paramId="ID" paramName="attribute" paramProperty="profileAttribType.ID" title="${attribute.profileAttribType.description}"><c:out value="${attribute.profileAttribType.name}"/></html:link>
			</display:column>
			<display:column titleKey="profile.attribute.isRequired.label" class="rowNum">
		      <html:img page="/common/images/icons/${attribute.isRequired}.gif" alt="${attribute.isRequired}"></html:img>
			</display:column>
			<%--<display:column property="isMultivalued" titleKey="profile.attribute.isMultivalued.label" />--%>
			<display:column titleKey="profile.attribute.isUserAttribute.label" class="rowNum">
		      <html:img page="/common/images/icons/${attribute.isUserAttribute}.gif" alt="${attribute.isUserAttribute}"></html:img>
			</display:column>
			<display:column property="attributeIndex" titleKey="profile.attribute.attributeIndex.label" />
		</display:table>
</logic:notEmpty>

<!-- ProfileMapping list -->
<logic:notEmpty name="ProfileTemplateForm" property="ID">
	<!-- Space: height 20 -->
	<table cellpadding=0 cellspacing=0 border=0>
		<tr>
			<td height=20>
				<spacer type=block width=1 height=20>
			</td>
		</tr>
	</table>

	<div class="messageArea">
		<bean:message key="page.profiletemplate.message.mappings" />
	</div>

	<bean:define id="templateID" name="ProfileTemplateForm" property="ID"></bean:define>
	<display:table name="models" id="model" class="simple">
		<display:column class="rowNum">
			<c:out value="${model_rowNum}" />
		</display:column>
		<display:column property="manufacturer.name" titleKey="profilemapping.title.manufacturer" />
		<display:column property="manufacturerModelId" titleKey="profilemapping.title.model" />
		<display:column>
			<a href="<html:rewrite page='/ViewProfileMapping.do'/>?templateID=<c:out value='${templateID}'/>&modelID=<c:out value='${model.ID}'/>"> <bean:message key="page.button.view.label" /> </a>
		</display:column>
	</display:table>
</logic:notEmpty>
