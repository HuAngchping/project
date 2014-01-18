<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

<div class="messageArea">
  <bean:message key="page.notification.device.message" /><br><br>
  <bean:message key="page.message.required"/><bean:message key="page.message.input"/>
  <div class="validateErrorMsg"><html:errors header="common.errors.header" footer="common.errors.footer" prefix="common.errors.prefix" suffix="common.errors.suffix"/></div>
</div>

<bean:parameter id="deviceID" name="deviceID" />
<html:form action="/device/notification/send" method="post" focus="uiMode">
	<input type="hidden" name="deviceID" value="<bean:write name="deviceID"/>" />
	<table class="entityview">
		<tbody>
			<tr>
				<th width="150">
					<bean:message key="device.title.externalId" />
				</th>
				<td>
					<bean:write name="device" property="externalId" />
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="device.title.phonenumber" />
				</th>
				<td>
					<bean:write name="device" property="subscriberPhoneNumber" />
				</td>
			</tr>
	    <tr>
	      <th><bean:message key="device.title.manufacturer"/></th>
	      <td><bean:write name="device" property="model.manufacturer.name" /></td>
	    </tr>
	    <tr>
	      <th><bean:message key="device.title.model"/></th>
	      <td><bean:write name="device" property="model.name" /></td>
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
        <input type="button" value="<bean:message key="page.device.job.success.button.notification.label" />" onClick="return document.DeviceNotificationForm.submit();">
	  </form>
	  <html:form action="/device/Jobs" method="post">
		  <INPUT type="hidden" name="deviceID" value="<bean:write name="device" property="ID"/>">
		  <html:submit><bean:message key="page.device.job.success.button.jobs.label"/></html:submit>
	  </html:form>
	  <html:form action="/ViewDevice" method="post">
		  <INPUT type="hidden" name="method" value="view">
		  <INPUT type="hidden" name="ID" value="<bean:write name="device" property="ID"/>">
		  <html:submit><bean:message key="page.device.button.backToDeviceView.label"/></html:submit>
	  </html:form>
	</div>
