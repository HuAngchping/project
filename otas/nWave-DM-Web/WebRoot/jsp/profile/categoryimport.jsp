
<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<html:html>

<div class="messageArea">
	<bean:message key="profile.explain3" />
	<bean:message key="profile.category.import.explain.content" />
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
				<bean:message key="model.ddf.validation.passed.tr" />
			</th>
			<td>
				<bean:message key="model.ddf.validation.passed.td" />
			</td>
		</tr>
	</tbody>
</table>
<div class="buttonArea" style="height: 40px;">
	<html:form action="/categoryimportsAction.do" enctype="multipart/form-data">

		<html:submit>
			<bean:message key="button.import.label" />
		</html:submit>

		<html:cancel>
			<bean:message key="page.button.back.category" />
		</html:cancel>

	</html:form>
</div>
</html:html>