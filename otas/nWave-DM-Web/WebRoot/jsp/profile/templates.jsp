<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.npower.dm.action.BaseAction" %>
<%@ page import="com.npower.dm.core.ProfileTemplate"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<html:html>
<div class="messageArea">
	<bean:message key="page.profiletemplates.message" />
</div>

<display:table name="templates" id="record" requestURI="<%=request.getContextPath() + "/ProfileTemplates.do"%>" pagesize="<%=BaseAction.getRecordsPerPage(request)%>" class="simple">
	<display:column class="rowNum" href="<%=request.getContextPath() + "/ViewProfileTemplate.do"%>" paramId="ID" paramProperty="ID">
		<c:out value="${record_rowNum}" />
	</display:column>
	<display:column property="name" sortable="true" titleKey="profiletemplate.title.name" href="<%=request.getContextPath() + "/ViewProfileTemplate.do"%>" paramId="ID" paramProperty="ID" style="white-space: nowrap;" />
	<display:column sortable="true" titleKey="profiletemplate.title.category" href="<%=request.getContextPath() + "/ViewProfileTemplate.do"%>" paramId="ID" paramProperty="ID">
		<%=((ProfileTemplate) record).getProfileCategory().getName()%>
	</display:column>
	<display:column sortable="true" titleKey="profiletemplate.title.needsNap" href="<%=request.getContextPath() + "/ViewProfileTemplate.do"%>" paramId="ID" paramProperty="ID" class="rowNum">
	  <html:img page="/common/images/icons/${record.needsNap}.gif" alt="${record.needsNap}"></html:img>
	</display:column>
	<display:column sortable="true" titleKey="profiletemplate.title.needsProxy" href="<%=request.getContextPath() + "/ViewProfileTemplate.do"%>" paramId="ID" paramProperty="ID" class="rowNum">
	  <html:img page="/common/images/icons/${record.needsProxy}.gif" alt="${record.needsProxy}"></html:img>
	</display:column>
	<display:column style="white-space: nowrap;">
		<a href="<html:rewrite page='/ViewProfileTemplate.do'/>?ID=<c:out value='${record.ID}'/>"><bean:message key="page.button.view.label" /></a> | 
      <a href="<html:rewrite page='/EditProfileTemplate.do'/>?ID=<c:out value='${record.ID}'/>"><bean:message key="page.button.edit.label" /></a> | 
      <a href="<html:rewrite page='/RemoveProfileTemplate.do'/>?ID=<c:out value='${record.ID}'/>" onclick='javascript: return confirm("<bean:message key="message.delete.areYourSureDelete"/>");'><bean:message key="page.button.remove.label" /></a>
	</display:column>
</display:table>


<div class="buttonArea" style="height: 40px;">
	<html:form action="/EditProfileTemplate">
		<html:submit>
			<bean:message key="page.profiletemplate.button.addTemplate" />
		</html:submit>
	</html:form>
	<html:form action="/ADDTemplate">
		<html:submit>
			<bean:message key="import.template.button" />
		</html:submit>
	</html:form>
</div>
</html:html>
