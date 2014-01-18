<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"
	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"
	prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested"
	prefix="nested"%>

<html:form action="/software/savaScreenShot" enctype='multipart/form-data'>
	<html:hidden property="screenShotID" />
  <html:hidden property="softwareID" />
	<table class="entityview">
    <thead>
      <tr>
        <th colspan="2">
          <font color="red"><b>.:</b></font>  <bean:write name="software" property="vendor.name"/> <bean:write name="software" property="name"/> <bean:write name="software" property="version"/>
        </th>
      </tr>
    </thead>
		<tbody>
			<tr>
				<th>
					*
					<bean:message key="entity.software.screenshot.file.label" />
				</th>
				<td>
					<html:file property="binaryFile" />
					<div class="validateErrorMsg">
						<html:errors property="binaryFile" />
					</div>
				</td>
			</tr>					
			<tr>
				<th>
				   *
					<bean:message key="entity.software.screenshot.description.label" />
				</th>
				<td>
					<html:textarea property="description" />
					<div class="validateErrorMsg">
						<html:errors property="description" />
					</div>
				</td>
			</tr>
		</tbody>
	</table>
	<div class="buttonArea">
		<logic:empty name="ScreenShotForm" property="screenShotID">
			<html:submit styleClass="NormalWidthButton">
				<bean:message key="page.button.create.label" />
			</html:submit>
		</logic:empty>
		<logic:notEmpty name="ScreenShotForm" property="screenShotID">
			<html:submit styleClass="NormalWidthButton">
				<bean:message key="page.button.update.label" />
			</html:submit>
		</logic:notEmpty>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<html:cancel styleClass="NormalWidthButton">
			<bean:message key="page.button.cancel.label" />
		</html:cancel>
	</div>
</html:form>