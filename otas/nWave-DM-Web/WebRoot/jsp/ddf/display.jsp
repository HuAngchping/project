<%@ page language="java"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<table class="entityview">
	<tbody>
		<tr>
			<th width="150">
				<bean:message key="model.ddf.manufacturerName" />
			</th>
			<td>
				<%=request.getSession().getAttribute("manufacturerName")%>
			</td>
		</tr>
		<tr>
			<th>
				<bean:message key="model.ddf.modelName" />
			</th>
			<td>
				<%=request.getSession().getAttribute("modelName")%>
			</td>
		</tr>
		<tr>
			<th>
				<bean:message key="model.ddf.AbsoluteFile" />
			</th>
			<td>
				<%=request.getSession().getAttribute("ddfName")%>
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
		<tr>
			<th>
				<bean:message key="model.ddf.import.tree.rootnode" />
			</th>
			<td>
				<%=request.getAttribute("nodename")%>
			</td>
		</tr>
	</tbody>
</table>
<div class="buttonArea" style="height: 40px;">

	<html:form action="/DDFTree">
		<input type="hidden" name="modelID" value="<c:out value="${modelID}"/>">
		<html:submit><bean:message key="view.ddf.tree.button" /></html:submit>
	</html:form>
	<html:form action="/uploadsAction.do" enctype="multipart/form-data">
		<html:cancel>
			<bean:message key="model.page.button.backToModelList" />
		</html:cancel>
	</html:form>
</div>

