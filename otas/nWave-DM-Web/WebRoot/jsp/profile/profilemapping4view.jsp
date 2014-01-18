
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<div class="messageArea">
  <bean:message key="page.profileMapping.message" /><br><br>
</div>

<html:form action="/SaveProfileMapping">
	<html:hidden property="templateID" />
	<html:hidden property="modelID" />
	<table class="entityview">
  <thead>
    <tr>
      <th colspan="2"><bean:message key="page.profileMapping.title.form.label"/></th>
    </tr>
  </thead>
		<tbody>
			<tr>
				<th style="white-space: nowrap; width: 30%;">
					<bean:message key="profilemapping.title.template" />
				</th>
				<td>
					<html:link action="/ViewProfileTemplate.do" styleClass="reference_link" target="_blank" paramId="ID" paramName="template" paramProperty="ID">
					  <bean:write name="template" property="name" />
					</html:link>
				</td>
			</tr>
			<tr>
				<th style="white-space: nowrap">
					<bean:message key="profilemapping.title.manufacturer" />
				</th>
				<td>
					<bean:write name="model" property="manufacturer.name" />
				</td>
			</tr>
			<tr>
				<th style="white-space: nowrap">
					<bean:message key="profilemapping.title.model" />
				</th>
				<td>
					<html:link action="/ViewModel.do?action=update" styleClass="reference_link" target="_blank" paramId="ID" paramName="model" paramProperty="ID"><bean:write name="model" property="name" /></html:link>
				</td>
			</tr>
			<tr>
				<th style="white-space: nowrap">
					<bean:message key="profilemapping.title.rootDDFNodePath" />
				</th>
				<td><bean:write name="profileMapping" property="rootDDFNode" /></td>
			</tr>
			<tr>
				<th style="white-space: nowrap">
					<bean:message key="profilemapping.title.linkedProfileType" />
				</th>
				<td><bean:write name="profileMapping" property="linkedProfileType" /></td>
			</tr>
			<tr>
				<th style="white-space: nowrap">
					<bean:message key="profilemapping.title.shareRootNode" />
				</th>
				<td>
		      <html:img page="/common/images/icons/${profileMapping.shareRootNode}.gif" alt="${profileMapping.shareRootNode}"></html:img>
				</td>
			</tr>
      <tr>
        <th style="white-space: nowrap">
          <bean:message key="profilemapping.title.needToDiscovery" />
        </th>
        <td>
          <html:img page="/common/images/icons/${profileMapping.needToDiscovery}.gif" alt="${profileMapping.needToDiscovery}"></html:img>
        </td>
      </tr>
      <tr>
        <th style="white-space: nowrap">
          <bean:message key="profilemapping.title.discoveryNodePaths" />
        </th>
        <td><bean:write name="profileMapping" property="discoveryNodePaths" /></td>
      </tr>
<%--
			<tr>
				<th>
					<bean:message key="profilemapping.title.assignToDevice" />
				</th>
				<td>
					<html:checkbox property="assignToDevice" />
					<div class="validateErrorMsg">
						<html:errors property="assignToDevice" />
				</td>
			</tr>
--%>
		</tbody>
	</table>
	<div class="buttonArea">
    <html:button property="action" onclick="document.forms['ProfileMappingForm'][1].submit();" styleClass="NormalWidthButton">
      <bean:message key="page.button.edit.label"/>
    </html:button>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<html:cancel styleClass="NormalWidthButton">
			<bean:message key="page.button.cancel.label" />
		</html:cancel>
	</div>
</html:form>

<html:form action="/EditProfileMapping" method="post">
	<html:hidden property="templateID" />
	<html:hidden property="modelID" />
</html:form>

<!--  NodeMappings management  -->
<logic:notEmpty name="ProfileMappingForm" property="templateID">

	<!-- Space: height 20 -->
	<table cellpadding=0 cellspacing=0 border=0>
		<tr>
			<td height=20>
				<spacer type=block width=1 height=20>
			</td>
		</tr>
	</table>

	<div class="messageArea">
		<bean:message key="page.profileMapping.message.nodeMappings" />
	</div>

		<bean:define id="templateID" name="ProfileMappingForm" property="templateID"></bean:define>
		<bean:define id="modelID" name="ProfileMappingForm" property="modelID"></bean:define>
		<display:table name="nodeMappings" id="nodeMapping" class="simple">
			<display:column class="rowNum">
				<c:out value="${nodeMapping_rowNum}" />
			</display:column>
			<display:column titleKey="profileNodeMapping.title.attributeName" style="white-space: nowrap">
				<c:out value="${nodeMapping.profileAttribute.name}"></c:out>
			</display:column>
			<display:column titleKey="profileNodeMapping.title.mappingType">
			  <bean:message key="meta.profile.node.mapping.type.${nodeMapping.mappingType}.label"/>
			</display:column>
			<display:column titleKey="profileNodeMapping.title.ddfNodePath" style="white-space: nowrap">
			    <c:if test="${nodeMapping.nodeRelativePath != null}"><c:out value="${nodeMapping.nodeRelativePath}"/></c:if>
			    <c:if test="${nodeMapping.nodeRelativePath == null}"><c:out value="${nodeMapping.ddfNode.nodePath}"/></c:if>
			</display:column>
			<display:column property="categoryName" titleKey="profileNodeMapping.title.categoryName" />
			<display:column property="command" titleKey="profileNodeMapping.title.command" />
			<display:column property="valueFormat" titleKey="profileNodeMapping.title.valueFormat" />
			<display:column property="value" titleKey="profileNodeMapping.title.value" style="white-space: nowrap"/>
			<%--
			<display:column property="paramIndex" titleKey="profileNodeMapping.title.paramIndex" />
			<display:column property="displayName" titleKey="profileNodeMapping.title.displayName" />
			 --%>
		</display:table>
</logic:notEmpty>


