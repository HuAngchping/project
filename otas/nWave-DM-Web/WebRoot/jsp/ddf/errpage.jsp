
<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

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
	</tbody>
</table>
<div>
	<html:errors property="errParseName" header="profile.errors.header" footer="profile.errors.footer"/>
</div>
<div>
	<html:errors property="errParseLine" header="profile.errors.header" footer="profile.errors.footer"/>
</div>
<div>
	<html:errors property="errParseMessage" header="profile.errors.header" footer="profile.errors.footer"/>
</div>
<div>
	<html:errors property="errSAXExpName" header="profile.errors.header" footer="profile.errors.footer"/>
</div>
<div>
	<html:errors property="errSAXExpStr" header="profile.errors.header" footer="profile.errors.footer"/>
</div>
<div>
	<html:errors property="errSAXExpMessage" header="profile.errors.header" footer="profile.errors.footer"/>
</div>
<div>
	<html:errors property="errIOEName" header="profile.errors.header" footer="profile.errors.footer"/>
</div>
<div>
	<html:errors property="errIOEStr" header="profile.errors.header" footer="profile.errors.footer"/>
</div>
<div>
	<html:errors property="errParserCfgExpName" header="profile.errors.header" footer="profile.errors.footer"/>
</div>
<div>
	<html:errors property="errFactoryCfgName" header="profile.errors.header" footer="profile.errors.footer"/>
</div>

<html:form action="/uploadsAction.do" enctype="multipart/form-data">
	<html:cancel>
		<bean:message key="model.page.button.backToModelList" />
	</html:cancel>
</html:form>

