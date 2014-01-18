<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

<div class="messageArea">
  <bean:message key="page.notification.device.message" /><br><br>
  <bean:message key="page.message.required"/><bean:message key="page.message.input"/>
  <div class="validateErrorMsg"><html:errors/></div>
</div>


<html:form action="/notification/send" method="post" focus="uiMode">
	<table class="entityview">
		<tbody>
			<tr>
				<th width="150">
					<bean:message key="page.notification.label.phoneNumbers" />
				</th>
				<td>
					<html:textarea property="phoneNumbers" />
					<div class="validateErrorMsg">
						<html:errors property="phoneNumbers" />
					</div>
					<bean:message key="page.notification.message.numberFormat" />
				</td>
			</tr>
			<tr>
				<th width="150">
					<bean:message key="notification.uimode.label" />
				</th>
				<td>
					<html:select property="uiMode">
						<html:option value="0"><bean:message key="meta.notification.uiMode.NOT_SPECIFIE.label"/></html:option>
						<html:option value="1"><bean:message key="meta.notification.uiMode.BACKGROUND.label"/></html:option>
						<html:option value="2"><bean:message key="meta.notification.uiMode.INFORMATIVE.label"/></html:option>
						<html:option value="3"><bean:message key="meta.notification.uiMode.USER_INTERACTION.label"/></html:option>
					</html:select>
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="notification.initiator.label" />
				</th>
				<td>
					<html:select property="initiator">
						<html:option value="0"><bean:message key="meta.notification.initiator.Client.label"/></html:option>
						<html:option value="1"><bean:message key="meta.notification.initiator.Server.label"/></html:option>
					</html:select>
				</td>
			</tr>
		</tbody>
	</table>
</html:form>
    <div class="buttonArea" style="height: 40px; weight: 100%;">
      <form>
        <input type="button" value="<bean:message key="page.device.job.success.button.notification.label" />" onClick="return document.NotificationForm.submit();">
	  </form>
	</div>
