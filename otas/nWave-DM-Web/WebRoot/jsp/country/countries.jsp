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

<display:table name="countries" id="country" class="simple" pagesize="<%=BaseAction.getRecordsPerPage(request)%>" requestURI="<%=request.getContextPath() + "/Countries.do"%>">
	<display:column class="rowNum" href="<%=request.getContextPath() + "/ViewCountry.do?action=update"%>" paramId="ID" paramProperty="ID">
		<c:out value="${country_rowNum}" />
	</display:column>
	<display:column property="name" sortable="true" sortName="name"  titleKey="country.title.name" href="<%=request.getContextPath() + "/ViewCountry.do?action=update"%>" paramId="ID" paramProperty="ID" />
	<display:column property="ISOCode" sortable="true" sortName="ISOCode"  titleKey="country.title.ISOCode" href="<%=request.getContextPath() + "/ViewCountry.do?action=update"%>" paramId="ID" paramProperty="ID" />
	<display:column property="countryCode" sortable="true" sortName="countryCode"  titleKey="country.title.countryCode" href="<%=request.getContextPath() + "/ViewCountry.do?action=update"%>" paramId="ID" paramProperty="ID" />
	<display:column sortable="true" sortName="displayTrunkCode"  titleKey="country.title.displayTrunkCode">
	  <html:img page="/common/images/icons/${country.displayTrunkCode}.gif" alt="${country.displayTrunkCode}"></html:img>
	</display:column>
	<display:column>
		<a href="<html:rewrite page='/ViewCountry.do'/>?action=update&ID=<c:out value='${country.ID}'/>"><bean:message key="page.button.view.label" /></a> | 
        <a href="<html:rewrite page='/EditCountry.do'/>?action=update&ID=<c:out value='${country.ID}'/>"><bean:message key="page.button.edit.label" /></a> | 
        <a href="<html:rewrite page='/RemoveCountry.do'/>?action=remove&ID=<c:out value='${country.ID}'/>" onclick='javascript: return confirm("<bean:message key="message.delete.areYourSureDelete"/>");'><bean:message key="page.button.remove.label" /></a>
	</display:column>
</display:table>
<html:form action="/EditCountry">
	<html:hidden property="action" value="create" />
	<html:submit>
		<bean:message key="page.countries.button.add" />
	</html:submit>
</html:form>
