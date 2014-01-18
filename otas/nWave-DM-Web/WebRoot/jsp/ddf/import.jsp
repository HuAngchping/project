
<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<html:errors header="common.errors.header" footer="common.errors.footer" prefix="common.errors.prefix" suffix="common.errors.suffix"/>
<html:form action="/importsAction.do" enctype="multipart/form-data">
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
					<bean:message key="model.ddf.validation.passed.tr" />
				</th>
				<td>
					<bean:message key="model.ddf.validation.passed.td" />
				</td>
			</tr>
		</tbody>
	</table>
	<div class="buttonArea">
		<html:submit styleClass="NormalWidthButton">
			<bean:message key="button.import.label"/>
		</html:submit>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<html:cancel>
			<bean:message key="model.page.button.backToModelList" />
		</html:cancel>
	</div>
</html:form>
