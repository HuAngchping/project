
<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>


<html:form action="/mappinguploadsAction.do" enctype="multipart/form-data">
	<div class="messageArea">
		<bean:message key="profile.explain1" />
		<bean:message key="profile.mapping.upload.explain.content" />
	</div>
	<table class="entityview">
		<tr>
			<th>
				<bean:message key="profile.template.importfiletype" />
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
			<bean:message key="button.upload" />
		</html:submit>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<html:reset styleClass="NormalWidthButton">
			<bean:message key="page.button.reset.label" />
		</html:reset>
		<html:cancel>
			<bean:message key="page.button.back.mappings" />
		</html:cancel>
	</div>

</html:form>
