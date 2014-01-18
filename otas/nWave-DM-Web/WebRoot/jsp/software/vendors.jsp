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

<display:table name="vendors" id="vendor" class="simple" pagesize="<%=BaseAction.getRecordsPerPage(request)%>" requestURI="<%=request.getContextPath() + "/software/vendors.do"%>">
	<display:column class="rowNum" href="<%=request.getContextPath() + "/software/ViewVendor.do?action=update"%>" paramId="id" paramProperty="id">
		<c:out value="${vendor_rowNum}" />
	</display:column>
	<display:column property="name" sortable="true" sortName="name"  titleKey="entity.software.vendor.name.label" href="<%=request.getContextPath() + "/software/ViewVendor.do?action=update"%>" paramId="id" paramProperty="id" />
	<display:column property="description" maxLength="24" sortable="true" sortName="description"  titleKey="entity.software.vendor.description.label" href="<%=request.getContextPath() + "/software/ViewVendor.do?action=update"%>" paramId="id" paramProperty="id" />
	<display:column>
		<a href="<html:rewrite page='/software/ViewVendor.do'/>?action=update&id=<c:out value='${vendor.id}'/>"><bean:message key="page.button.view.label" /></a> | 
        <a href="<html:rewrite page='/software/EditVendor.do'/>?action=update&id=<c:out value='${vendor.id}'/>"><bean:message key="page.button.edit.label" /></a> | 
        <a href="<html:rewrite page='/software/RemoveVendor.do'/>?action=remove&id=<c:out value='${vendor.id}'/>" onclick='javascript: return confirm("<bean:message key="message.delete.areYourSureDelete"/>");'><bean:message key="page.button.remove.label" /></a>
	</display:column>
</display:table>
<html:form action="/software/EditVendor">
	<html:hidden property="action" value="create" />
	<html:submit>
		<bean:message key="page.vendors.button.add" />
	</html:submit>
</html:form>
