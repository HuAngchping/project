
<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<html:form action="/uploadsAction.do" enctype="multipart/form-data">

	<table class="entityview">
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
				<bean:message key="model.ddf.importfiletype" />
			</th>
			<td>
				<html:file property="theFile" />
				<div class="validateErrorMsg">
					<html:errors property="fileerr" />
				</div>
			</td>
		</tr>
	</table>

	<div class="buttonArea">
		<html:submit styleClass="NormalWidthButton">
			<bean:message key="button.upload"/>
		</html:submit>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<html:reset styleClass="NormalWidthButton">
			<bean:message key="page.button.reset.label" />
		</html:reset>
		<html:cancel>
			<bean:message key="model.page.button.backToModelList" />
		</html:cancel>
	</div>

</html:form>

