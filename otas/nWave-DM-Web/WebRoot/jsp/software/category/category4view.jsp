<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn"%>

<html:form action="/software/categoryEdit">
	<html:hidden property="id" />

	<logic:present name="category">
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
							<c:out value='${category.parent.pathAsString}'/>
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
    <div class="buttonArea">
      <html:submit styleClass="NormalWidthButton">
        <bean:message key="page.button.edit.label" />
      </html:submit>
    </div>
    <logic:notEmpty name="children">
      <div class="messageArea">
        <bean:message key="page.software.category.tree.children.message" />
      </div>
      <display:table name="children" id="child" class="simple" requestURI="./software/category.do?action=view">
        <display:column class="rowNum" href="./software/category.do?action=view" paramId="id" paramProperty="id">
          <c:out value="${child_rowNum}" />
        </display:column>
        <display:column property="name" sortable="true" sortName="name" titleKey="entity.software.category.name.label" href="./software/category.do?action=view" paramId="id" paramProperty="id" />
        <display:column property="description" sortable="true" sortName="description" titleKey="entity.software.category.description.label" href="./software/category.do?action=view" paramId="id" paramProperty="id" />
        <display:column>
          <a href="<html:rewrite page='/software/category.do'/>?action=view&id=<c:out value='${child.id}'/>"><bean:message key="page.button.view.label" /> </a> | 
          <a href="<html:rewrite page='/software/category.do'/>?action=edit&id=<c:out value='${child.id}'/>"><bean:message key="page.button.edit.label" /> </a> | 
          <a href="<html:rewrite page='/software/category.do'/>?action=remove&id=<c:out value='${child.id}'/>" onclick='javascript: return confirm("<bean:message key="message.delete.areYourSureDelete"/>");'>
            <html:img page="/common/images/delete.gif" altKey="page.button.remove.label" />
          </a>
        </display:column>
      </display:table>
    </logic:notEmpty>
	</logic:present>
</html:form>

