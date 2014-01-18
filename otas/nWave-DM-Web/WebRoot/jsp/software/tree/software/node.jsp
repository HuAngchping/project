<%@ page language="java"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.npower.dm.action.BaseAction" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn"%>

<fmt:bundle basename="com.npower.dm.struts.ApplicationResources">
</fmt:bundle>

<logic:present name="categoryTreeNode">
<table class="entityview" width="100%">
  <thead>
    <tr>
      <th colspan="2"><bean:message key="page.software.software.tree.title"/></th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th width="150"><bean:message key="entity.software.category.name.label" /></th>
      <td><bean:write name="categoryTreeNode" property="name" /></td>
    </tr>
	<logic:present name="categoryTreeNode" property="parent">
	 <tr>
		<th><bean:message key="entity.software.category.parent.label" /></th>
		<td><c:out value='${categoryTreeNode.parent.name}'/></td>
	 </tr>
	</logic:present>
	<tr>
	   <th><bean:message key="entity.software.category.description.label" /></th>
	   <td><bean:write name="categoryTreeNode" property="description" /></td>
	</tr>
  </tbody>
</table>
</logic:present>
	
<logic:notEmpty name="children">
<div class="messageArea">
	<bean:message key="page.software.category.tree.children.message" />
</div>
<display:table name="children" id="child" class="simple" requestURI="<%=request.getContextPath() + "/software/softwareTree.do"%>">
	<display:column class="rowNum" href="<%=request.getContextPath() + "/software/category.do?action=view"%>" paramId="id" paramProperty="id">
		<c:out value="${child_rowNum}" />
	</display:column>
	<display:column property="name" sortable="true" sortName="name"  titleKey="entity.software.category.name.label" href="<%=request.getContextPath() + "/software/category.do?action=view"%>" paramId="id" paramProperty="id" />
	<display:column property="description" sortable="true" sortName="description"  titleKey="entity.software.category.description.label" href="<%=request.getContextPath() + "/software/category.do?action=view"%>" paramId="id" paramProperty="id" />
	<display:column>
		<a href="<html:rewrite page='/software/category.do'/>?action=view&id=<c:out value='${child.id}'/>"><bean:message key="page.button.view.label" /></a> | 
        <a href="<html:rewrite page='/software/category.do'/>?action=edit&id=<c:out value='${child.id}'/>"><bean:message key="page.button.edit.label" /></a> | 
        <a href="<html:rewrite page='/software/category.do'/>?action=remove&id=<c:out value='${child.id}'/>" onclick='javascript: return confirm("<bean:message key="message.delete.areYourSureDelete"/>");'><html:img page="/common/images/delete.gif" altKey="page.button.remove.label"/></a>
	</display:column>
</display:table>
</logic:notEmpty>

<logic:notEmpty name="softwares">
<div class="messageArea">
	<bean:message key="page.software.software.tree.softwares.message" />
</div>
<display:table name="softwares" id="software" class="simple" pagesize="<%=BaseAction.getRecordsPerPage(request)%>" requestURI="<%=request.getContextPath() + "/software/vendors.do"%>">
	<display:column class="rowNum" href="<%=request.getContextPath() + "/software/software.do?action=view"%>" paramId="id" paramProperty="id">
		<c:out value="${software_rowNum}" />
	</display:column>
	<display:column property="externalId" sortable="true" sortName="name"  titleKey="entity.software.software.externalId.label" href="<%=request.getContextPath() + "/software/software.do?action=view"%>" paramId="id" paramProperty="id" />
	<display:column property="name" sortable="true" sortName="name"  titleKey="entity.software.software.name.label" href="<%=request.getContextPath() + "/software/software.do?action=view"%>" paramId="id" paramProperty="id" />
	<display:column titleKey="entity.software.vendor.label" href="<%=request.getContextPath() + "/software/software.do?action=view"%>" paramId="id" paramProperty="id">
	   <c:out value='${software.vendor.name}'/>
	</display:column>
	<display:column property="version" sortable="true" sortName="name"  titleKey="entity.software.software.version.label" href="<%=request.getContextPath() + "/software/software.do?action=view"%>" paramId="id" paramProperty="id" />
	<display:column property="licenseType" sortable="true" sortName="licenseType"  titleKey="entity.software.software.licenseType.label" href="<%=request.getContextPath() + "/software/software.do?action=view"%>" paramId="id" paramProperty="id" />
	<display:column>
		<a href="<html:rewrite page='/software/software.do'/>?action=view&id=<c:out value='${software.id}'/>"><bean:message key="page.button.view.label" /></a> | 
        <a href="<html:rewrite page='/software/editSoftware.do'/>?id=<c:out value='${software.id}'/>"><bean:message key="page.button.edit.label" /></a> | 
        <a href="<html:rewrite page='/software/software.do'/>?action=remove&id=<c:out value='${software.id}'/>" onclick='javascript: return confirm("<bean:message key="message.delete.areYourSureDelete"/>");'><bean:message key="page.button.remove.label" /></a>
	</display:column>
</display:table>
</logic:notEmpty>