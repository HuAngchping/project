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
				<bean:message key="profile.attributetype.AbsoluteFile" />
			</th>
			<td>
				<%=request.getSession().getAttribute("AttributeTypeName")%>
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
	<bean:message key="import.attributetype.name" />
</div>


<display:table name="typelist" id="record" pagesize="10" class="simple">
</display:table>


<div class="buttonArea" style="height: 40px;">
	<html:form action="/attributetypeuploadsAction.do" enctype="multipart/form-data">
		<html:cancel>
			<bean:message key="page.button.back.attributetype" />
		</html:cancel>
	</html:form>
</div>
</html:html>
