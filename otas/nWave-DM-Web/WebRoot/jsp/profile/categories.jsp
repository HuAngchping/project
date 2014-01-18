<%@ page language="java"%>
<%@ page import="com.npower.dm.core.ProfileCategory" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<html:html>
<div class="messageArea">
  <bean:message key="page.categories.message"/>
</div>

<display:table name="categories" id="category" requestURI="<%=request.getContextPath() + "/Categories.do"%>"  pagesize="10" class="simple">
    <display:column class="rowNum" href="<%=request.getContextPath() + "/EditProfileCategory.do?action=update"%>" paramId="ID" paramProperty="ID" >
      <c:out value="${category_rowNum}"/>
    </display:column>
	<display:column property="name" sortable="true" titleKey="profilecategory.title.name" href="<%=request.getContextPath() + "/EditProfileCategory.do?action=update"%>" paramId="ID" paramProperty="ID" style="white-space: nowrap;" />
	<display:column property="description" sortable="true" titleKey="profilecategory.title.description" href="<%=request.getContextPath() + "/EditProfileCategory.do?action=update"%>" paramId="ID" paramProperty="ID" />
	<display:column titleKey="profilecategory.title.templates" sortable="true" href="<%=request.getContextPath() + "/EditProfileCategory.do?action=update"%>" paramId="ID" paramProperty="ID" >
	  <%=((ProfileCategory)category).getProfileTemplates().size()%>
	</display:column>
    <display:column style="white-space: nowrap;">
      <a href="<html:rewrite page='/EditProfileCategory.do'/>?action=update&ID=<c:out value='${category.ID}'/>"><bean:message key="page.button.edit.label"/></a> | 
      <a href="<html:rewrite page='/RemoveProfileCategory.do'/>?action=remove&ID=<c:out value='${category.ID}'/>" onclick='javascript: return confirm("<bean:message key="message.delete.areYourSureDelete"/>");'><bean:message key="page.button.remove.label"/></a>
    </display:column>
</display:table>
<div class="buttonArea" style="height:40px">
	<html:form action="/EditProfileCategory">
	  <html:hidden property="action" value="create"/>
	  <html:submit><bean:message key="page.categories.button.add"/></html:submit>
	</html:form>
		<html:form action="/ADDCategory">
		<html:submit><bean:message key="import.category.button"/></html:submit>
	</html:form>
</div>
</html:html>