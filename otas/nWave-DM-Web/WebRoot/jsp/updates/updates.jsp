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

<display:table name="updates" id="update" class="simple" pagesize="10" requestURI="<%=request.getContextPath() + "/updates/updateimages.do"%>">
	<display:column class="rowNum" href="<%=request.getContextPath() + "/model/viewUpdate.do?action=update"%>" paramId="updateID" paramProperty="ID">
		<c:out value="${update_rowNum}" />
	</display:column>
	<display:column property="fromImage.model.manufacturer.name" sortable="true" sortName="fromImage.model.manufacturer.name" titleKey="model.title.manufacturer" href="<%=request.getContextPath() + "/updates/viewUpdate.do?action=update"%>" paramId="updateID" paramProperty="ID" />
	<display:column sortable="true" sortName="fromImage.model.name" titleKey="page.firmware.model.label">
	    <html:link action="/ViewModel.do" styleClass="reference_link" target="_blank" paramId="ID" paramName="update" paramProperty="fromImage.model.ID"><c:out value="${update.fromImage.model.name}"/></html:link>
	</display:column>
	<display:column property="fromImage.versionId" sortable="true" maxLength="24" sortName="fromImage.versionId" titleKey="page.firmware.fromVersionID.label" href="<%=request.getContextPath() + "/updates/viewUpdate.do?action=update"%>" paramId="updateID" paramProperty="ID" style="white-space: nowrap;"/>
	<display:column property="toImage.versionId" sortable="true" maxLength="24" sortName="toImage.versionId" titleKey="page.firmware.toVersionID.label" href="<%=request.getContextPath() + "/updates/viewUpdate.do?action=update"%>" paramId="updateID" paramProperty="ID" style="white-space: nowrap;"/>
	<display:column property="status.name" sortable="true" sortName="status.name" titleKey="page.firmware.status.label" href="<%=request.getContextPath() + "/updates/viewUpdate.do?action=update"%>" paramId="updateID" paramProperty="ID" style="white-space: nowrap;"/>
	<display:column sortable="true" sortName="size" titleKey="page.firmware.imageSize.label" href="<%=request.getContextPath() + "/updates/viewUpdate.do?action=update"%>" paramId="updateID" paramProperty="ID" style="text-align: right;">
    <fmt:formatNumber value="${update.size}"></fmt:formatNumber>
  </display:column>
	<display:column style="white-space: nowrap;">
        &nbsp;&nbsp;
        <a href="<html:rewrite page='/updates/viewUpdate.do'/>?updateID=<c:out value='${update.ID}'/>"><bean:message key="page.button.view.label"/></a> | 
        <a href="<html:rewrite page='/updates/editUpdate.do'/>?updateID=<c:out value='${update.ID}'/>"><bean:message key="page.button.edit.label"/></a> | 
        <a href="<html:rewrite page='/updates/removeUpdate.do'/>?updateID=<c:out value='${update.ID}'/>" onclick='javascript: return confirm("<bean:message key="message.delete.areYourSureDelete"/>");'><bean:message key="page.button.remove.label"/></a>
	</display:column>
</display:table>

<html:form action="/updates/editUpdate">
	<html:submit><bean:message key="page.firmwares.button.uploadFirmware"/></html:submit>
</html:form>

