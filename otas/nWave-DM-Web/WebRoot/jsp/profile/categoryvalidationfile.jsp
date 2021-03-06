<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>


<html:form action="/categoryvalidationAction">
	<div class="messageArea">
		<bean:message key="profile.explain2" />
		<bean:message key="profile.category.validation.explain.content" />
	</div>
	<table class="entityview">
		<tbody>
			<tr>
				<th>
					<bean:message key="profile.category.AbsoluteFile" />
				</th>
				<td>
					<%=request.getSession().getAttribute("CategoryName")%>
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.ddf.upload.success.tr" />
				</th>
				<td>
					<bean:message key="model.ddf.upload.success.td" />
				</td>
			</tr>
		</tbody>
	</table>


	<div class="buttonArea">
		<html:submit>
			<bean:message key="button.validation" />
		</html:submit>
		<html:cancel>
			<bean:message key="page.button.back.category" />
		</html:cancel>
	</div>
</html:form>

