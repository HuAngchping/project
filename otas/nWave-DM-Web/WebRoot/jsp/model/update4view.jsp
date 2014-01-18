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
				<th><bean:message key="page.firmware.fromVersionID.label"/></th>
				<td><bean:write name="update" property="fromImage.versionId"/></td>
			</tr>
			<tr>
				<th><bean:message key="page.firmware.toVersionID.label"/></th>
				<td><bean:write name="update" property="toImage.versionId"/></td>
			</tr>
			<tr>
				<th><bean:message key="page.firmware.status.label"/></th>
				<td><bean:write name="update" property="status.name"/></td>
			</tr>
			<tr>
				<th><bean:message key="page.firmware.imageSize.label"/></th>
				<td><bean:write name="update" property="size" format="#,###"/></td>
			</tr>
			<tr>
				<th><bean:message key="page.firmware.image.label"/></th>
				<td>
					<html:link action="/updates/downloadfirmware?method=getBinary" target="_blank" paramId="updateID" paramName="update" paramProperty="ID">
					[<bean:message key="page.button.download.label" />]<html:img page="/common/images/icons/icon_down.gif"/>
					</html:link>
				</td>
			</tr>
			<tr>
				<th><bean:message key="page.firmware.description.label"/></th>
				<td><bean:write name="update" property="description"/></td>
			</tr>
		</tbody>
	</table>
	<div class="buttonArea">
	  <logic:notEmpty name="ModelUpdateForm" property="updateID">
		    <html:button property="action" onclick="document.forms['ModelUpdateForm'][1].submit();" styleClass="NormalWidthButton">
		      <bean:message key="page.button.edit.label"/>
		    </html:button>
	  </logic:notEmpty>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<html:cancel styleClass="NormalWidthButton">
			<bean:message key="page.button.cancel.label" />
		</html:cancel>
	</div>
</html:form>

<html:form action="/model/editUpdate" method="post">
  <logic:notEmpty name="ModelUpdateForm" property="updateID">
    <html:hidden property="updateID"/>
  </logic:notEmpty>
</html:form>
