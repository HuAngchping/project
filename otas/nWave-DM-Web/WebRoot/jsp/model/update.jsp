<%@ page language="java"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<div class="messageArea">
	<bean:message key="page.message.required" />
	<bean:message key="page.message.input" />
</div>

<html:form action="/model/saveUpdate" enctype='multipart/form-data'>
	<html:hidden property="updateID" />
	<html:hidden property="modelID" />
	<table class="entityview">
		<tbody>
			<tr>
				<th width="150"><bean:message key="model.title.manufacturer" /></th>
				<td><bean:write name="model" property="manufacturer.name"/></td>
			</tr>
			<tr>
				<th><bean:message key="device.title.model" /></th>
				<td><bean:write name="model" property="name"/></td>
			</tr>
			<tr>
				<th width="160">* <bean:message key="page.firmware.fromVersionID.label"/></th>
				<td>
					<html:text property="fromVersion" size="48"/>
					<div class="validateErrorMsg">
						<html:errors property="fromVersion" />
					</div>
				</td>
			</tr>
			<tr>
				<th>* <bean:message key="page.firmware.toVersionID.label"/></th>
				<td>
					<html:text property="toVersion" size="48"/>
					<div class="validateErrorMsg">
						<html:errors property="toVersion" />
					</div>
				</td>
			</tr>
			<tr>
				<th>* <bean:message key="page.firmware.status.label"/></th>
				<td>
					<html:select property="status">
						<html:optionsCollection name="updateStatusOptions" />
					</html:select>
					<div class="validateErrorMsg">
						<html:errors property="status" />
					</div>
				</td>
			</tr>
            <logic:empty name="ModelUpdateForm" property="updateID">
			<tr>
				<th>
					* <bean:message key="page.firmware.image.label"/>
				</th>
				<td>
					<html:file property="imageFile" />
					<div class="validateErrorMsg">
						<html:errors property="status" />
					</div>
				</td>
			</tr>
			</logic:empty>
			<tr>
				<th><bean:message key="page.firmware.description.label"/></th>
				<td>
					<html:textarea property="description"/>
					<div class="validateErrorMsg">
						<html:errors property="description" />
					</div>
				</td>
			</tr>
		</tbody>
	</table>
	<div class="buttonArea">
		<logic:empty name="ModelUpdateForm" property="updateID">
			<html:submit styleClass="NormalWidthButton">
				<bean:message key="page.button.create.label" />
			</html:submit>
		</logic:empty>
		<logic:notEmpty name="ModelUpdateForm" property="updateID">
			<html:submit styleClass="NormalWidthButton">
				<bean:message key="page.button.update.label" />
			</html:submit>
		</logic:notEmpty>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<html:reset styleClass="NormalWidthButton">
			<bean:message key="page.button.reset.label" />
		</html:reset>
		<html:cancel styleClass="NormalWidthButton">
			<bean:message key="page.button.cancel.label" />
		</html:cancel>
	  <logic:notEmpty name="ModelUpdateForm" property="updateID">
		    <html:button property="action" onclick="document.forms['ModelUpdateForm'][1].submit();" styleClass="NormalWidthButton">
		      <bean:message key="page.button.view.label"/>
		    </html:button>
	  </logic:notEmpty>
	</div>
</html:form>

<html:form action="/model/viewUpdate" method="post">
  <logic:notEmpty name="ModelUpdateForm" property="updateID">
    <html:hidden property="updateID"/>
  </logic:notEmpty>
</html:form>
