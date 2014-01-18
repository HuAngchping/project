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
  <bean:message key="page.ProfileMappings.message" /><br><br>
</div>

<!-- ProfileMapping list -->
<display:table name="results" id="item" class="simple" pagesize="<%=BaseAction.getRecordsPerPage(request)%>" requestURI="<%=request.getContextPath() + "/ViewProfileMapping.do"%>">
	<display:column class="rowNum">
		<c:out value="${item_rowNum}" />
	</display:column>
	<display:column sortable="true" sortName="model.manufacturer.name" property="model.manufacturer.name" titleKey="profilemapping.title.manufacturer" />
	<display:column sortable="true" sortName="model.name" property="model.name" titleKey="profilemapping.title.model" />
	<display:column sortable="true" sortName="template.name" property="template.name" titleKey="profilemapping.title.template" style="white-space: nowrap;" />
	<display:column style="white-space: nowrap;">
		<a href="<html:rewrite page='/ViewProfileMapping.do'/>?action=view&templateID=<c:out value='${item.template.ID}'/>&modelID=<c:out value='${item.model.ID}'/>"> <bean:message key="page.button.view.label" /> </a> |
		<a href="<html:rewrite page='/EditProfileMapping.do'/>?action=edit&templateID=<c:out value='${item.template.ID}'/>&modelID=<c:out value='${item.model.ID}'/>"> <bean:message key="page.button.edit.label" /> </a> |
		<a href="<html:rewrite page='/RemoveProfileMapping.do'/>?action=remove&templateID=<c:out value='${item.template.ID}'/>&modelID=<c:out value='${item.model.ID}'/>" onclick='javascript: return confirm("<bean:message key="message.delete.areYourSureDelete"/>");'> <bean:message key="page.button.remove.label" /> </a>
	</display:column>
</display:table>

<div class="buttonArea" style="height: 40px;">
	<html:form action="/CreateProfileMapping">
		<html:submit><bean:message key="create.mapping.template.button" /></html:submit>
	</html:form>
	<html:form action="/ADDMapping">
		<html:submit><bean:message key="import.mapping.button" /></html:submit>
	</html:form>
</div>
