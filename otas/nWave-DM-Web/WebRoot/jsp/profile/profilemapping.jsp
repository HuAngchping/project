
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<script type="text/javascript">
<!--
function doChangeDDFNode(selectList) {
  //alert(selectList.selectedIndex);
  option = selectList.options[selectList.selectedIndex];
  if (option.value != "") {
     selectList.form.elements['rootNodePath'].value = option.text;
  } else {
    selectList.form.elements['rootNodePath'].value = "";
  }
}
//-->
</script>

<div class="messageArea">
  <bean:message key="page.profileMapping.message" /><br><br>
  <bean:message key="page.message.required" />
  <bean:message key="page.message.input" />
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
				<th style="white-space: nowrap">
					<bean:message key="profilemapping.title.template" />
				</th>
				<td>
					<bean:write name="template" property="name" />
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
					<bean:write name="model" property="name" />
				</td>
			</tr>
			<tr>
				<th style="white-space: nowrap">
					<bean:message key="profilemapping.title.rootDDFNodePath" />
				</th>
				<td>
					<html:select property="rootDDFNodeID" onchange="doChangeDDFNode(this);">
						<html:option value="">
							<bean:message key="page.select.option.default.label" />
						</html:option>
						<html:optionsCollection name="ddfNodes" label="nodePath" value="ID" />
					</html:select>
					<div class="validateErrorMsg"><html:errors property="rootDDFNodeID" /></div>
				</td>
			</tr>
			<tr>
				<th style="white-space: nowrap">
					<bean:message key="profilemapping.title.rootDDFNodePath" />
				</th>
				<td><html:text property="rootNodePath" size="72"/></td>
			</tr>
			<tr>
				<th style="white-space: nowrap">
					<bean:message key="profilemapping.title.linkedProfileType" />
				</th>
				<td>
					<html:select property="linkedProfileType">
						<html:option value="">
							<bean:message key="page.select.option.default.label" />
						</html:option>
						<html:optionsCollection name="categories" label="name" value="name" />
					</html:select>
					<div class="validateErrorMsg"><html:errors property="linkedProfileType" /></div>
				</td>
			</tr>
			<tr>
				<th style="white-space: nowrap">
					<bean:message key="profilemapping.title.shareRootNode" />
				</th>
				<td>
					<html:checkbox property="shareRootNode" />
					<div class="validateErrorMsg"><html:errors property="shareRootNode" /></div>
				</td>
			</tr>
      <tr>
        <th style="white-space: nowrap">
          <bean:message key="profilemapping.title.needToDiscovery" />
        </th>
        <td>
          <html:checkbox property="needToDiscovery" />
          <div class="validateErrorMsg"><html:errors property="needToDiscovery" /></div>
        </td>
      </tr>
      <tr>
        <th style="white-space: nowrap">
          <bean:message key="profilemapping.title.discoveryNodePaths" />
        </th>
        <td>
        <html:textarea property="discoveryNodePaths"/>
        <br/><bean:message key="profilemapping.title.discoveryNodePaths.tips" />
        </td>
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
		<html:submit property="action" styleClass="NormalWidthButton">
			<bean:message key="page.button.update.label" />
		</html:submit>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<html:reset styleClass="NormalWidthButton">
			<bean:message key="page.button.reset.label" />
		</html:reset>
		<html:cancel styleClass="NormalWidthButton">
			<bean:message key="page.button.cancel.label" />
		</html:cancel>
    <html:button property="action" onclick="document.forms['ProfileMappingForm'][1].submit();" styleClass="NormalWidthButton">
      <bean:message key="page.button.view.label"/>
    </html:button>
	</div>
</html:form>

<html:form action="/ViewProfileMapping" method="post">
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

	<html:form action="/SaveProfileMapping">
		<html:hidden name="ProfileMappingForm" property="templateID" />
		<html:hidden name="ProfileMappingForm" property="modelID" />
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
			<display:column style="white-space: nowrap">
				<a href="<html:rewrite page='/EditProfileNodeMapping.do'/>?ID=<c:out value="${nodeMapping.ID}"/>&templateID=<c:out value="${templateID}"/>&modelID=<c:out value="${modelID}"/>"><bean:message key="page.button.edit.label" /></a>
	      |
	      <a href="<html:rewrite page='/RemoveProfileNodeMapping.do'/>?ID=<c:out value="${nodeMapping.ID}"/>&templateID=<c:out value="${templateID}"/>&modelID=<c:out value="${modelID}"/>" onclick='javascript: return confirm("<bean:message key="message.delete.areYourSureDelete"/>");'><bean:message key="page.button.remove.label" /></a>
			</display:column>
		</display:table>
	</html:form>
	<div class="buttonArea" style="height: 40px;">
		<html:form action="/EditProfileNodeMapping">
			<html:hidden name="ProfileMappingForm" property="templateID" />
			<html:hidden name="ProfileMappingForm" property="modelID" />
			<html:submit property="action">
				<bean:message key="page.profileMapping.button.createNodeMapping.label" />
			</html:submit>
		</html:form>
	</div>
</logic:notEmpty>


