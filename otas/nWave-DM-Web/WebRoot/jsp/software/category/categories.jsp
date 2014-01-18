<%@ page import="com.npower.dm.action.BaseAction"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn"%>
<%@ taglib uri="http://struts-menu.sf.net/tag" prefix="menu"%>
<%@ taglib uri="http://struts-menu.sf.net/tag-el" prefix="menu-el"%>

<fmt:bundle basename="com.npower.dm.struts.ApplicationResources">
</fmt:bundle>
<table class=table_box cellSpacing=0 cellPadding=0 width="100%" border="0">
<tbody>
  <tr>
    <td class=table_box_heading width="100%" colSpan=5><font color="red"><b>.:</b></font> <bean:message key="entity.software.category.list.label"/></td>
  </tr>
  <tr>
	  <td>
			<display:table name="categories" id="category" class="simple_inline"	pagesize="<%=BaseAction.getRecordsPerPage(request)%>"	requestURI="./software/categories.do">
				<display:column class="rowNum" href="./software/category.do?action=view"	paramId="id" paramProperty="id">
					<c:out value="${category_rowNum}" />
				</display:column>
				<display:column property="pathAsString" sortable="true"	sortName="name" titleKey="entity.software.category.name.label" href="./software/category.do?action=view"	paramId="id" paramProperty="id" />
				<display:column property="description" sortable="true" sortName="description" maxLength="24" titleKey="entity.software.category.description.label" href="./software/category.do?action=view"	paramId="id" paramProperty="id" />
				<display:column	titleKey="page.software.categories.numberOfSoftwares.label" sortable="true" style="text-align: right;width: 25px;" href="./software/category.do?action=view" paramId="id" paramProperty="id">
					<c:out value="${fn:length(category.softwares)}" />
				</display:column>
				<display:column style="text-align: center;width: 300px;">
					<a href="<html:rewrite page='/software/category.do'/>?action=view&id=<c:out value='${category.id}'/>"><bean:message key="page.button.view.label" /> </a> | 
			     <a href="<html:rewrite page='/software/categoryEdit.do'/>?id=<c:out value='${category.id}'/>"><bean:message key="page.button.edit.label" /> </a> | 
			     <a href="<html:rewrite page='/software/category.do'/>?action=remove&id=<c:out value='${category.id}'/>" onclick='javascript: return confirm("<bean:message key="message.delete.areYourSureDelete"/>");'><bean:message	key="page.button.remove.label" /> </a>
				</display:column>
			</display:table>
		</td>
	</tr>	
</tbody>
</table>
	<div class="buttonArea" style="height: 40px;">
		<html:form action="/software/categoryEdit">
			<html:submit>
				<bean:message key="page.software.categories.button.add" />
			</html:submit>
		</html:form>
		<br>
	</div>

