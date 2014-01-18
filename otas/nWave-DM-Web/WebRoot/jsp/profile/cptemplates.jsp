<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.npower.dm.action.BaseAction" %>

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

<!-- ProfileMapping list -->
<display:table name="results" id="item" class="simple" pagesize="<%=BaseAction.getRecordsPerPage(request)%>" requestURI="<%=request.getContextPath() + "/profile/CPTemplates.do"%>">
	<display:column class="rowNum">
		<c:out value="${item_rowNum}" />
	</display:column>
	<display:column sortable="true" sortName="model.manufacturer.name" property="model.manufacturer.name" titleKey="CPTemplate.title.manufacturer" />
	<display:column sortable="true" sortName="model.name" titleKey="CPTemplate.title.model">
	   <html:link action="/ViewModel.do" styleClass="reference_link" target="_blank" paramId="ID" paramName="item" paramProperty="model.ID"><c:out value="${item.model.name}"/></html:link>
	</display:column>
	<display:column sortable="true" sortName="clientProvTemplate.name" property="clientProvTemplate.name" titleKey="CPTemplate.title.name" style="white-space: nowrap;" />
	<display:column sortable="true" sortName="clientProvTemplate.profileCategory.name" property="clientProvTemplate.profileCategory.name" titleKey="CPTemplate.title.category" style="white-space: nowrap;" />
	<display:column style="white-space: nowrap;">
		<a href="<html:rewrite page='/profile/ViewCPTemplate.do'/>?action=view&templateID=<c:out value='${item.clientProvTemplate.ID}'/>&modelID=<c:out value='${item.model.ID}'/>"> <bean:message key="page.button.view.label" /> </a> |
		<a href="<html:rewrite page='/profile/EditCPTemplate.do'/>?action=edit&templateID=<c:out value='${item.clientProvTemplate.ID}'/>&modelID=<c:out value='${item.model.ID}'/>"> <bean:message key="page.button.edit.label" /> </a> |
		<a href="<html:rewrite page='/profile/RemoveCPTemplate.do'/>?templateID=<c:out value='${item.clientProvTemplate.ID}'/>&modelID=<c:out value='${item.model.ID}'/>" onclick='javascript: return confirm("<bean:message key="message.delete.areYourSureDelete"/>");'> <bean:message key="page.button.remove.label" /> </a>
	</display:column>
</display:table>

<div class="buttonArea" style="height: 40px;">
	<html:form action="/profile/EditCPTemplate">
		<html:submit><bean:message key="page.CPTemplates.create.button" /></html:submit>
	</html:form>
</div>
