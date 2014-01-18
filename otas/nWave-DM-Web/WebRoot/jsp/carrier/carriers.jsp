<%@ page import="com.npower.dm.action.BaseAction" %>

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
<display:table name="carriers" id="carrier" class="simple" pagesize="<%=BaseAction.getRecordsPerPage(request)%>" requestURI="<%=request.getContextPath() + "/Carriers.do"%>">
	<display:column class="rowNum" href="<%=request.getContextPath() + "/ViewCarrier.do?action=update"%>" paramId="ID" paramProperty="ID">
		<c:out value="${carrier_rowNum}" />
	</display:column>
	<display:column property="name" sortable="true" sortName="name" titleKey="carrier.title.name" href="<%=request.getContextPath() + "/ViewCarrier.do?action=update"%>" paramId="ID" paramProperty="ID" />
	<display:column property="country.name" sortable="true" sortName="country.name" titleKey="carrier.title.country" href="<%=request.getContextPath() + "/ViewCarrier.do?action=update"%>" paramId="ID" paramProperty="ID" />
	<display:column property="serverAuthType" sortable="true" sortName="serverAuthType" titleKey="carrier.title.serverAuthType" href="<%=request.getContextPath() + "/ViewCarrier.do?action=update"%>" paramId="ID" paramProperty="ID" />
	<display:column property="notificationType" sortable="true" sortName="notificationType" titleKey="carrier.title.notificationType" href="<%=request.getContextPath() + "/ViewCarrier.do?action=update"%>" paramId="ID" paramProperty="ID" />
	<display:column>
		<a href="<html:rewrite page='/ViewCarrier.do'/>?action=update&ID=<c:out value='${carrier.ID}'/>"><bean:message key="page.button.view.label" /></a> | 
        <a href="<html:rewrite page='/EditCarrier.do'/>?action=update&ID=<c:out value='${carrier.ID}'/>"><bean:message key="page.button.edit.label" /></a> | 
        <a href="<html:rewrite page='/RemoveCarrier.do'/>?action=remove&ID=<c:out value='${carrier.ID}'/>" onclick='javascript: return confirm("<bean:message key="message.delete.areYourSureDelete"/>");'><bean:message key="page.button.remove.label" /></a>
	</display:column>
</display:table>
<html:form action="/EditCarrier">
	<html:hidden property="action" value="create" />
	<html:submit>
		<bean:message key="page.carriers.button.add" />
	</html:submit>
</html:form>
