<%@ page import="com.npower.dm.action.BaseAction" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/otas-dm.tld" prefix="dm" %>

<fmt:bundle basename="com.npower.dm.struts.ApplicationResources">
</fmt:bundle>
<display:table name="models" id="model" class="simple" pagesize="<%=BaseAction.getRecordsPerPage(request)%>" requestURI="<%=request.getContextPath() + "/Models.do"%>">
	<display:column class="rowNum" href="<%=request.getContextPath() + "/ViewModel.do?action=update"%>" paramId="ID" paramProperty="ID">
		<c:out value="${model_rowNum}" />
	</display:column>
	<display:column property="name" sortable="true" sortName="name" titleKey="model.title.name" href="<%=request.getContextPath() + "/ViewModel.do?action=update"%>" paramId="ID" paramProperty="ID" />
	<display:column property="manufacturer.name" sortable="true" sortName="manufacturer.name" titleKey="model.title.manufacturer" href="<%=request.getContextPath() + "/ViewModel.do?action=update"%>" paramId="ID" paramProperty="ID" />
	<display:column property="manufacturerModelId" sortable="true" sortName="manufacturerModelId" titleKey="model.title.manufacturerModelId" href="<%=request.getContextPath() + "/ViewModel.do?action=update"%>" paramId="ID" paramProperty="ID" />
	<display:column property="description" sortable="true" sortName="id" titleKey="model.title.description" maxLength="36" href="<%=request.getContextPath() + "/ViewModel.do?action=update"%>" paramId="ID" paramProperty="ID" />
	<display:column>
		<a href="<html:rewrite page='/ViewModel.do'/>?ID=<c:out value='${model.ID}'/>"><bean:message key="page.button.view.label"/></a>
         <dm:permission roles="dm.admin.models,dm.operator.manufacturer">
         | <a href="<html:rewrite page='/EditModel.do'/>?action=update&ID=<c:out value='${model.ID}'/>"><bean:message key="page.button.edit.label"/></a>
         | <a href="<html:rewrite page='/RemoveModel.do'/>?action=remove&ID=<c:out value='${model.ID}'/>" onclick='javascript: return confirm("<bean:message key="message.delete.areYourSureDelete"/>");'><bean:message key="page.button.remove.label"/></a>
         | <html:link action="/DDFTree" paramId="modelID" paramName="model" paramProperty="ID"><html:img page="/common/images/icons/icon_tree.gif" altKey="page.models.button.image.alt.viewDDF"/></html:link>
         </dm:permission>
	</display:column>
</display:table>
<dm:permission roles="dm.admin.models,dm.operator.manufacturer">
<html:form action="/EditModel">
	<html:hidden property="action" value="create" />
	<html:submit>
		<bean:message key="page.models.button.add" />
	</html:submit>
</html:form>
</dm:permission>