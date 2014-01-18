<%@ page import="com.npower.dm.action.BaseAction"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested"	prefix="nested"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn"%>

<fmt:bundle basename="com.npower.dm.struts.ApplicationResources">
</fmt:bundle>

<logic:present name="category">
  <logic:notEmpty name="category">
		<html:form action="/software/recommendEdit" method="post">
		  <html:hidden property="categoryId" value="${category.id}"/>
			<table class="entityview">
				<thead>
					<tr>
						<th colspan="2">
							&nbsp;<bean:write name="category" property="pathAsString" />
						</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th width="150">
							<bean:message key="entity.software.category.name.label" />
						</th>
						<td>
							<bean:write name="category" property="name" />
						</td>
					</tr>
					<logic:present name="category" property="parent">
						<tr>
							<th>
								<bean:message key="entity.software.category.parent.label" />
							</th>
							<td>
								<c:out value='${category.parent.pathAsString}' />
							</td>
						</tr>
					</logic:present>
					<tr>
						<th>
							<bean:message key="entity.software.category.description.label" />
						</th>
						<td>
							<bean:write name="category" property="description" />
						</td>
					</tr>
				</tbody>
			</table>
		</html:form>
		<c:set var="id" value="?id=${category.id}"/>
	</logic:notEmpty>
</logic:present>
<table class=table_box cellSpacing=0 cellPadding=0 width="100%" border="0">
  <tbody>
  <tr>
    <td class=table_box_heading width="100%" colSpan=5><font color="red"><b>.:</b></font> <bean:message key="page.software.recommend.view.software.list.title"/></td>
  </tr>
  <tr>
    <td>
			<display:table name="recommendSoftwares" id="software" class="simple_inline" pagesize="<%=BaseAction.getRecordsPerPage(request)%>" requestURI="./software/recommends.do${id}">
			  <display:column class="rowNum">
			    <c:out value="${software_rowNum}" />
			  </display:column>
			  <display:column property="name" sortName="name" maxLength="24" titleKey="entity.software.recommend.name.label" href="./software/software.do?action=view" paramId="id" paramProperty="id"/>
			  <display:column property="vendor.name" sortName="vendor" maxLength="24" titleKey="entity.software.recommend.vendor.label" href="./software/ViewVendor.do?action=update" paramId="id" paramProperty="vendor.id"/>
			  <display:column property="version" sortName="version" maxLength="24" titleKey="entity.software.recommend.version.label" href="./software/software.do?action=view" paramId="id" paramProperty="id"/>
			  <display:column property="description" sortName="description" maxLength="24" titleKey="entity.software.recommend.description.label" href="./software/software.do?action=view" paramId="id" paramProperty="id"/>
			</display:table>
    </td>
  </tr>
  </tbody>
</table>
<div class="buttonArea">
  <html:button styleClass="NormalWidthButton" property="edit" onclick="document.forms[0].submit();">
    <bean:message key="page.software.recommend.edit.button.label"/>
  </html:button>
</div>           
