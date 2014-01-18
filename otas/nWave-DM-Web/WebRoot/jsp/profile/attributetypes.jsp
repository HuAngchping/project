<%@ page language="java"%>
<%@ page import="com.npower.dm.core.ProfileAttributeType" %>
<%@ page import="com.npower.dm.action.BaseAction" %>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<html:html>
<div class="messageArea">
  <bean:message key="page.attributetypes.message"/>
</div>

<display:table name="attributeTypes" id="attributeType" requestURI="<%=request.getContextPath() + "/ProfileAttributeTypes.do"%>" class="simple" pagesize="<%=BaseAction.getRecordsPerPage(request) %>">
    <display:column class="rowNum" href="<%=request.getContextPath() + "/EditProfileAttributeType.do"%>" paramId="ID" paramProperty="ID" >
      <c:out value="${attributeType_rowNum}"/>
    </display:column>
	<display:column property="name" sortable="true" titleKey="attributetype.title.name" href="<%=request.getContextPath() + "/EditProfileAttributeType.do"%>" paramId="ID" paramProperty="ID" style="white-space: nowrap;"/>
	<display:column property="description" sortable="true" titleKey="attributetype.title.description" href="<%=request.getContextPath() + "/EditProfileAttributeType.do?action=update"%>" paramId="ID" paramProperty="ID" />
	<display:column titleKey="attributetype.title.values" href="<%=request.getContextPath() + "/EditProfileAttributeType.do?action=update"%>" paramId="ID" paramProperty="ID" >
	  <%=((ProfileAttributeType)attributeType).getAttribTypeValues().size()%>
	</display:column>
  <display:column style="white-space: nowrap;">
    <a href="<html:rewrite page='/EditProfileAttributeType.do'/>?action=update&ID=<c:out value='${attributeType.ID}'/>"><bean:message key="page.button.edit.label"/></a> | 
    <a href="<html:rewrite page='/RemoveProfileAttributeType.do'/>?action=remove&ID=<c:out value='${attributeType.ID}'/>" onclick='javascript: return confirm("<bean:message key="message.delete.areYourSureDelete"/>");'><bean:message key="page.button.remove.label"/></a>
  </display:column>
</display:table>

<div class="buttonArea" style="height:40px">
	<html:form action="/EditProfileAttributeType">
	  <html:hidden property="action" value="create"/>
	  <html:submit><bean:message key="page.profileTypes.button.add"/></html:submit>
	</html:form>
		<html:form action="/ADDAttributeType">
		<html:submit><bean:message key="import.attributeType.button"/></html:submit>
	</html:form>
</div>
</html:html>