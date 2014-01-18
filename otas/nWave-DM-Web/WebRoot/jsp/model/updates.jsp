<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<fmt:bundle basename="com.npower.dm.struts.ApplicationResources">
</fmt:bundle>
<table class="entityview">
	<tbody>
		<tr>
			<th width="150"><bean:message key="model.title.manufacturer" /></th>
			<td><bean:write name="model" property="manufacturer.name"/></td>
		</tr>
		<tr>
			<th><bean:message key="device.title.model" /></th>
			<td><bean:write name="model" property="name"/></td>
		</tr>
	</tbody>
</table>

<display:table name="updates" id="update" class="simple" pagesize="10" requestURI="<%=request.getContextPath() + "/model/updateimages.do"%>">
	<display:column class="rowNum" href="<%=request.getContextPath() + "/model/viewUpdate.do?action=update"%>" paramId="updateID" paramProperty="ID">
		<c:out value="${update_rowNum}" />
	</display:column>
	<display:column property="fromImage.versionId" sortable="true" maxLength="36" sortName="fromImage.versionId" titleKey="page.firmware.fromVersionID.label" href="<%=request.getContextPath() + "/model/viewUpdate.do?action=update"%>" paramId="updateID" paramProperty="ID" style="white-space: nowrap;"/>
	<display:column property="toImage.versionId" sortable="true" maxLength="36" sortName="toImage.versionId" titleKey="page.firmware.toVersionID.label" href="<%=request.getContextPath() + "/model/viewUpdate.do?action=update"%>" paramId="updateID" paramProperty="ID" style="white-space: nowrap;"/>
	<display:column property="status.name" sortable="true" sortName="status.name" titleKey="page.firmware.status.label" href="<%=request.getContextPath() + "/model/viewUpdate.do?action=update"%>" paramId="updateID" paramProperty="ID" style="white-space: nowrap;"/>
	<display:column property="size" sortable="true" sortName="size" titleKey="page.firmware.imageSize.label" href="<%=request.getContextPath() + "/model/viewUpdate.do?action=update"%>" paramId="updateID" paramProperty="ID" />
	<display:column style="white-space: nowrap;">
        &nbsp;&nbsp;
        <a href="<html:rewrite page='/model/viewUpdate.do'/>?updateID=<c:out value='${update.ID}'/>"><bean:message key="page.button.view.label"/></a> | 
        <a href="<html:rewrite page='/model/editUpdate.do'/>?updateID=<c:out value='${update.ID}'/>"><bean:message key="page.button.edit.label"/></a> | 
        <a href="<html:rewrite page='/model/removeUpdate.do'/>?updateID=<c:out value='${update.ID}'/>" onclick='javascript: return confirm("<bean:message key="message.delete.areYourSureDelete"/>");'><bean:message key="page.button.remove.label"/></a>
	</display:column>
</display:table>
<html:form action="/model/editUpdate">
	<input type="hidden" name="modelID" value="<bean:write name="modelID"/>"/>
	<html:submit><bean:message key="page.firmwares.button.uploadFirmware"/></html:submit>
</html:form>

<html:form action="/ViewModel">
	<input type="hidden" name="ID" value="<bean:write name="modelID"/>"/>
	<input type="hidden" name="action" value="update"/>
	<html:submit><bean:message key="model.page.button.backToModelView"/></html:submit>
</html:form>
