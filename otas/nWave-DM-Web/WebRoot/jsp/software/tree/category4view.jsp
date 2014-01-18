<%@ page language="java" pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<html:form action="/software/editCategory">
	<html:hidden property="id" />

	<logic:present name="category">
		<table class="entityview">
			<thead>
				<tr>
					<th colspan="2">
						&nbsp;<bean:write name="category" property="name" />
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
							<c:out value='${category.parent.name}'/>
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
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<html:cancel styleClass="NormalWidthButton">
				<bean:message key="page.button.cancel.label" />
			</html:cancel>
		</div>
	</logic:present>
</html:form>

