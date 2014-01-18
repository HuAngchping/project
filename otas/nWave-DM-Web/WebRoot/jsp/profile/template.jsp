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
	<bean:message key="page.message.required" />
	<bean:message key="page.message.input" />
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
				<td>
					<html:text property="name" size="48" />
					<div class="validateErrorMsg">
						<html:errors property="name" />
				  </div>
				</td>
			</tr>
			<tr>
				<th>
					* <bean:message key="profiletemplate.title.category" />
				</th>
				<td>
                  <logic:notEmpty name="ProfileTemplateForm" property="ID">
                    <html:hidden property="profileCategoryID"/>
                    <bean:write name="profileCategory" property="name"/>
                  </logic:notEmpty>
                  <logic:empty name="ProfileTemplateForm" property="ID">
					<html:select property="profileCategoryID">
						<html:option value="">
							<bean:message key="page.select.option.default.label" />
						</html:option>
						<html:optionsCollection name="categories" label="name" value="ID" />
					</html:select>
					<div class="validateErrorMsg"><html:errors property="profileCategoryID" /></div>
                  </logic:empty>
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="profiletemplate.title.needsNap" />
				</th>
				<td>
					<html:checkbox property="needsNap" />
					<div class="validateErrorMsg">
						<html:errors property="needNap" />
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="profiletemplate.title.needsProxy" />
				</th>
				<td>
					<html:checkbox property="needsProxy" />
					<div class="validateErrorMsg">
						<html:errors property="needProxy" />
				</td>
			</tr>
		</tbody>
	</table>
	<div class="buttonArea">
		<logic:empty name="ProfileTemplateForm" property="ID">
			<html:submit property="action" styleClass="NormalWidthButton">
				<bean:message key="page.button.create.label" />
			</html:submit>
		</logic:empty>
		<logic:notEmpty name="ProfileTemplateForm" property="ID">
			<html:submit property="action" styleClass="NormalWidthButton">
				<bean:message key="page.button.update.label" />
			</html:submit>
		</logic:notEmpty>
		<html:reset styleClass="NormalWidthButton">
			<bean:message key="page.button.reset.label" />
		</html:reset>
		<html:cancel styleClass="NormalWidthButton">
			<bean:message key="page.button.cancel.label" />
		</html:cancel>
	  <logic:notEmpty name="ProfileTemplateForm" property="ID">
		    <html:button property="action" onclick="document.forms['ProfileTemplateForm'][1].submit();" styleClass="NormalWidthButton"><bean:message key="page.button.view.label"/></html:button>
	  </logic:notEmpty>
	</div>
</html:form>

<html:form action="/ViewProfileTemplate" method="post">
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

	<html:form action="/SaveProfileAttributeTypeValue">
		<html:hidden name="ProfileTemplateForm" property="ID" />
		<bean:define id="templateID" name="ProfileTemplateForm" property="ID"></bean:define>
		<display:table name="attributes" id="attribute" class="simple">
			<display:column class="rowNum">
				<c:out value="${attribute_rowNum}" />
			</display:column>
			<display:column property="name" titleKey="profile.attribute.name.label" style="white-space: nowrap;" />
			<display:column property="displayName" titleKey="profile.attribute.displayName.label" style="white-space: nowrap;" />
			<display:column titleKey="profile.attribute.attributeType.label" style="white-space: nowrap;">
				<c:out value="${attribute.profileAttribType.name}"></c:out>
			</display:column>
			<display:column titleKey="profile.attribute.isRequired.label" class="rowNum">
		      <html:img page="/common/images/icons/${attribute.isRequired}.gif" alt="${attribute.isRequired}"></html:img>
			</display:column>
			<%--<display:column property="isMultivalued" titleKey="profile.attribute.isMultivalued.label" />--%>
			<display:column titleKey="profile.attribute.isUserAttribute.label" class="rowNum">
		      <html:img page="/common/images/icons/${attribute.isUserAttribute}.gif" alt="${attribute.isUserAttribute}"></html:img>
			</display:column>
			<display:column property="attributeIndex" titleKey="profile.attribute.attributeIndex.label" />
			<display:column style="white-space: nowrap;">
				<a href="<html:rewrite page='/EditProfileAttribute.do'/>?action=<bean:message key="page.button.edit.label"/>&ID=<c:out value='${templateID}'/>&attributeID=<c:out value='${attribute.ID}'/>"><bean:message key="page.button.edit.label" /></a>
	      |
	      <a href="<html:rewrite page='/RemoveProfileAttribute.do'/>?templateID=<c:out value='${templateID}'/>&attributeID=<c:out value='${attribute.ID}'/>" onclick='javascript: return confirm("<bean:message key="message.delete.areYourSureDelete"/>");'><bean:message key="page.button.remove.label" /></a>
			</display:column>
		</display:table>
	</html:form>
	<div class="buttonArea" style="height: 40px;">
		<html:form action="/EditProfileAttribute">
			<input type="hidden" name="ID" value="<c:out value='${templateID}'/>">
			<html:submit property="action">
				<bean:message key="page.profile.attribute.button.addAttribute" />
			</html:submit>
		</html:form>
	</div>
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
			<a href="<html:rewrite page='/EditProfileMapping.do'/>?templateID=<c:out value='${templateID}'/>&modelID=<c:out value='${model.ID}'/>"> <bean:message key="page.button.edit.label" /> </a>
		</display:column>
	</display:table>
</logic:notEmpty>
