<%@ page language="java"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<html:html>
<table class="entityview">
	<tbody>
		<tr>
			<th>
				<bean:message key="profile.template.AbsoluteFile" />
			</th>
			<td>
				<%=request.getSession().getAttribute("TemplateName")%>
			</td>
		</tr>
		<tr>
			<th>
				<bean:message key="model.ddf.import.success.tr" />
			</th>
			<td>
				<bean:message key="model.ddf.import.success.td" />
			</td>
		</tr>
	</tbody>
</table>

<div class="messageArea">
	<bean:message key="import.mapping.name" />
</div>
<display:table name="templates" id="record" pagesize="10" class="simple">
	<display:column property="name" sortable="true" titleKey="profiletemplate.title.name" paramId="ID" paramProperty="ID">
	</display:column>
</display:table>

<div class="buttonArea" style="height: 40px;">
	<html:form action="/templateuploadsAction.do" enctype="multipart/form-data">
		<html:cancel>
			<bean:message key="page.button.back.templates" />
		</html:cancel>
	</html:form>
</div>
</html:html>