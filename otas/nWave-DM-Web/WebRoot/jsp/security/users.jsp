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

<display:table name="users" id="user" class="simple" pagesize="<%=BaseAction.getRecordsPerPage(request)%>" requestURI="<%=request.getContextPath() + "/Users.do"%>">
	<display:column class="rowNum" href="<%=request.getContextPath() + "/EditUser.do?method=view"%>" paramId="username" paramProperty="username">
		<c:out value="${user_rowNum}" />
	</display:column>
	<display:column property="username" sortable="true" sortName="username"  titleKey="page.user.username.title" href="<%=request.getContextPath() + "/EditUser.do?method=view"%>" paramId="username" paramProperty="username" />
	<display:column property="firstName" sortable="true" sortName="firstName"  titleKey="page.user.firstname.title" href="<%=request.getContextPath() + "/EditUser.do?method=view"%>" paramId="username" paramProperty="username" />
	<display:column property="lastName" sortable="true" sortName="lastName"  titleKey="page.user.lastname.title" href="<%=request.getContextPath() + "/EditUser.do?method=view"%>" paramId="username" paramProperty="username" />
	<display:column sortable="true" sortName="active"  titleKey="device.title.isActivated" href="<%=request.getContextPath() + "/EditUser.do?method=view"%>" paramId="username" paramProperty="username" >
	  <html:img page="/common/images/icons/${user.active}.gif" alt="${user.active}"></html:img>
	</display:column>
	<display:column>
        <a href="<html:rewrite page='/EditUser.do'/>?method=view&username=<c:out value='${user.username}'/>"><bean:message key="page.button.view.label" /></a> | 
        <a href="<html:rewrite page='/EditUser.do'/>?method=update&username=<c:out value='${user.username}'/>"><bean:message key="page.button.edit.label" /></a> | 
        <a href="<html:rewrite page='/RemoveUser.do'/>?method=remove&username=<c:out value='${user.username}'/>" onclick='javascript: return confirm("<bean:message key="message.delete.areYourSureDelete"/>");'><bean:message key="page.button.remove.label" /></a>
	</display:column>
</display:table>
<html:form action="/EditUser">
	<html:hidden property="method" value="create" />
	<html:submit>
		<bean:message key="page.users.button.add" />
	</html:submit>
</html:form>
