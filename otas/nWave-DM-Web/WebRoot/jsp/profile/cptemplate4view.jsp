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
	<bean:message key="page.CPTemplates.message" /><br><br>
</div>

<html:form action="/profile/ViewCPTemplate">
	<html:hidden property="templateID" />
	<html:hidden property="modelID" />
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
				<th width="160px">
					<bean:message key="CPTemplate.title.name" />
				</th>
				<td><bean:write name="template" property="name" /></td>
			</tr>
			<tr>
				<th width="160px">
					<bean:message key="CPTemplate.title.category" />
				</th>
				<td><bean:write name="template" property="profileCategory.name"/></td>
			</tr>
			<tr>
				<th width="160px">
					<bean:message key="CPTemplate.title.manufacturer" />
				</th>
				<td><bean:write name="model" property="manufacturer.externalId"/></td>
			</tr>
			<tr>
				<th width="160px">
					<bean:message key="CPTemplate.title.model" />
				</th>
				<td><html:link action="/ViewModel.do" styleClass="reference_link" target="_blank" paramId="ID" paramName="model" paramProperty="ID"><bean:write name="model" property="manufacturerModelId"/></html:link></td>
			</tr>
			<tr>
				<th width="160px">
					<bean:message key="CPTemplate.title.description" />
				</th>
				<td><bean:write name="template" property="description"/></td>
			</tr>
			<tr>
				<th width="160px">
					<bean:message key="CPTemplate.title.encoder" />
				</th>
				<td><bean:message key="meta.cptemplate.encoder.${template.encoder}.label"/></td>
			</tr>
			<tr>
				<th width="160px" valign="top">
					<bean:message key="CPTemplate.title.content" />
				</th>
				<td>
	        <textarea class="view_script" readonly="readonly"><bean:write name="template" property="content"/></textarea>
				</td>
			</tr>
		</tbody>
	</table>
	<div class="buttonArea">
	  <logic:notEmpty name="CPTemplateForm" property="templateID">
		    <html:button property="action" onclick="document.forms['CPTemplateForm'][1].submit();" styleClass="NormalWidthButton">
		      <bean:message key="page.button.edit.label"/>
		    </html:button>
	  </logic:notEmpty>
		<html:cancel styleClass="NormalWidthButton">
			<bean:message key="page.button.cancel.label" />
		</html:cancel>
	</div>
</html:form>

<html:form action="/profile/EditCPTemplate" method="post">
  <logic:notEmpty name="template" property="ID">
    <html:hidden property="templateID"/>
    <html:hidden property="modelID"/>
  </logic:notEmpty>
</html:form>
