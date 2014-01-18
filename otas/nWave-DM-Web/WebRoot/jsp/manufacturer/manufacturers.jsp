<%@ page import="com.npower.dm.action.BaseAction" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %>

<fmt:bundle basename="com.npower.dm.struts.ApplicationResources">
</fmt:bundle>
<display:table class="simple" name="manufacturers" id="manufacturer" pagesize="<%=BaseAction.getRecordsPerPage(request)%>" requestURI="<%=request.getContextPath() + "/Manufacturers.do"%>">
	<display:column class="rowNum" href="<%=request.getContextPath() + "/ViewManufacturer.do?action=update"%>" paramId="ID" paramProperty="ID">
		<c:out value="${manufacturer_rowNum}"></c:out>
	</display:column>
	<display:column property="name" titleKey="manufacturer.title.name" href="<%=request.getContextPath() + "/ViewManufacturer.do?action=update"%>" paramId="ID" paramProperty="ID" />
	<display:column property="externalId" titleKey="manufacturer.title.externalId" href="<%=request.getContextPath() + "/ViewManufacturer.do?action=update"%>" paramId="ID" paramProperty="ID" />
	<display:column titleKey="manufacturer.title.models" style="text-align: right;width: 40px;" href="<%=request.getContextPath() + "/Models.do"%>" paramId="searchManufacturerID" paramProperty="ID">
	  <c:out value="${fn:length(manufacturer.models)}"/>
	</display:column>
	<display:column property="description" titleKey="manufacturer.title.description" href="<%=request.getContextPath() + "/ViewManufacturer.do?action=update"%>" paramId="ID" paramProperty="ID" />
	<display:column>
		<a href="<html:rewrite page='/ViewManufacturer.do'/>?action=update&ID=<c:out value='${manufacturer.ID}'/>"><bean:message key="page.button.view.label"/></a> | 
    <a href="<html:rewrite page='/EditManufacturer.do'/>?action=update&ID=<c:out value='${manufacturer.ID}'/>"><bean:message key="page.button.edit.label"/></a> | 
    <a href="<html:rewrite page='/RemoveManufacturer.do'/>?action=remove&ID=<c:out value='${manufacturer.ID}'/>" onclick='javascript: return confirm("<bean:message key="message.delete.areYourSureDelete"/>");'><bean:message key="page.button.remove.label"/></a>
	</display:column>
</display:table>
<html:form action="/EditManufacturer">
	<html:hidden property="action" value="create" />
	<html:submit>
		<bean:message key="page.manufacturers.button.add" />
	</html:submit>
</html:form>
