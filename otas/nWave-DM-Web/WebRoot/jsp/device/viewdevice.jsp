<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/otas-dm.tld" prefix="dm" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<logic:notEmpty name="device">
  	<div class="buttonArea" style="height: 40px;">
	  <bean:define id="deviceID" name="device" property="ID"></bean:define>
	  <html:form action="/device/DeviceTree" method="post">
		  <INPUT type="hidden" name="deviceID" value="<bean:write name="deviceID"/>">
	      <html:submit><bean:message key="page.device.button.viewDeviceTree"/></html:submit>
	  </html:form>
	  <html:form action="/device/EditJob" method="post">
		  <INPUT type="hidden" name="deviceID" value="<bean:write name="deviceID"/>">
		  <html:submit><bean:message key="page.device.button.createJob"/></html:submit>
	  </html:form>
	  <html:form action="/device/Jobs" method="post">
		  <INPUT type="hidden" name="deviceID" value="<bean:write name="deviceID"/>">
		  <html:submit><bean:message key="page.device.button.viewJobs"/></html:submit>
	  </html:form>
	  <html:form action="/device/ProfileAssignments" method="post">
		  <INPUT type="hidden" name="deviceID" value="<bean:write name="deviceID"/>">
		  <html:submit><bean:message key="page.device.button.viewProfiles"/></html:submit>
	  </html:form>
      <html:form action="/device/softwares" method="post">
          <INPUT type="hidden" name="deviceID" value="<bean:write name="deviceID"/>">
          <html:submit><bean:message key="page.device.button.view.deployed.softwares"/></html:submit>
      </html:form>
      <html:form action="/device/history" method="post">
          <INPUT type="hidden" name="deviceID" value="<bean:write name="deviceID"/>">
          <html:submit><bean:message key="page.device.button.history"/></html:submit>
      </html:form>
      <br/><br/>
	  <html:form action="/EditSubscriber" method="post">
		  <INPUT type="hidden" name="ID" value="<bean:write name="device" property="subscriber.ID"/>">
		  <INPUT type="hidden" name="method" value="view">
		  <html:submit><bean:message key="page.device.button.viewSubscriber"/></html:submit>
	  </html:form>
	  <html:form action="/device/bootstrap" method="post">
		  <INPUT type="hidden" name="deviceID" value="<bean:write name="deviceID"/>">
		  <INPUT type="hidden" name="method" value="input">
		  <html:submit><bean:message key="page.device.button.bootstrap"/></html:submit>
	  </html:form>
	  <html:form action="/device/notification/input" method="post">
		  <INPUT type="hidden" name="deviceID" value="<bean:write name="device" property="ID"/>">
		  <html:submit><bean:message key="page.device.job.success.button.notification.label"/></html:submit>
	  </html:form>
    <input type="button" id="add_device_favorite_button" value="<bean:message key='page.device.favorite.add.button.message' />" onclick="popAddDeviceFavoriteWin('<bean:write name="deviceID"/>')"/>
		<div id="add-device-favorite-win" class="x-hidden">
		    <div class="x-window-header"><bean:message key="page.device.favorite.win.header.message" /></div>    
		</div>
	</div>
</logic:notEmpty>

<table class="entityview">
  <tbody>
    <tr>
      <th width="150"><bean:message key="device.title.externalId"/></th>
      <td><bean:write name="device" property="externalId" /></td>
    </tr>
    <tr>
      <th><bean:message key="device.title.phonenumber"/></th>
      <td><bean:write name="device" property="subscriber.phoneNumber" /></td>
    </tr>
    <tr>
      <th><bean:message key="page.subscriber.imsi.title"/></th>
      <td><bean:write name="device" property="subscriber.IMSI" /></td>
    </tr>
    <tr>
      <th><bean:message key="device.title.carrier"/></th>
      <td><bean:write name="device" property="subscriber.carrier.name" /></td>
    </tr>
    <tr>
      <th><bean:message key="device.title.manufacturer"/></th>
      <td><bean:write name="device" property="model.manufacturer.name" /></td>
    </tr>
    <tr>
      <th><bean:message key="device.title.model"/></th>
      <td><html:link action="/ViewModel.do?action=update" styleClass="reference_link" target="_blank" paramId="ID" paramName="device" paramProperty="model.ID"><bean:write name="device" property="model.name" /></html:link></td>
    </tr>
    <tr>
      <th><bean:message key="device.title.firmwareVersion"/></th>
      <td><bean:write name="currentFirmwareVersionID"/></td>
    </tr>
    <tr>
      <th><bean:message key="device.title.isActivated"/></th>
      <td>
      <logic:equal name="device" property="isActivated" value="false">
		<html:img page="/common/images/icons/deactive.gif" alt="${device.isActivated}"></html:img>
      </logic:equal>
      <logic:equal name="device" property="isActivated" value="true">
        <html:img page="/common/images/icons/active.gif" alt="${device.isActivated}"></html:img>
      </logic:equal>
      </td>
    </tr>
    <tr>
      <th><bean:message key="device.title.booted"/></th>
      <td>
      <logic:equal name="device" property="booted" value="false">
        <html:img page="/common/images/icons/deactive.gif" alt="${device.booted}"></html:img>
      </logic:equal>
      <logic:equal name="device" property="booted" value="true">
        <html:img page="/common/images/icons/active.gif" alt="${device.booted}"></html:img>
      </logic:equal>
      </td>
    </tr>
    <tr>
      <th><bean:message key="page.subscriber.service_provider.title"/></th>
      <td>
        <logic:notEmpty name="device" property="subscriber.serviceProvider">
          <bean:write name="device" property="subscriber.serviceProvider.name"/>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th><bean:message key="device.title.OMAClientAuthScheme"/></th>
      <td><bean:write name="device" property="OMAClientAuthScheme" /></td>
    </tr>
    <tr>
      <th><bean:message key="device.title.OMAServerPassword"/></th>
      <td><bean:write name="device" property="OMAServerPassword" /></td>
    </tr>
    <tr>
      <th><bean:message key="device.title.OMAClientUsername"/></th>
      <td><bean:write name="device" property="OMAClientUsername" /></td>
    </tr>
    <tr>
      <th><bean:message key="device.title.OMAClientPassword"/></th>
      <td><bean:write name="device" property="OMAClientPassword" /></td>
    </tr>
    <tr>
      <th><bean:message key="device.title.queuedJobs"/></th>
      <td>
        <html:link action="/device/Jobs" paramId="deviceID" paramName="device" paramProperty="ID"><bean:write name="numberUnfinishedJobs"/></html:link>
      </td>
    </tr>
    <tr>
      <th><bean:message key="device.title.description"/></th>
      <td><bean:write name="device" property="description" /></td>
    </tr>
    <tr>
      <th><bean:message key="device.title.bootstrapPinType"/></th>
      <td>
        <logic:equal name="device" property="bootstrapPinType" value="0"><bean:message key="meta.bootstrap.pintype.NETWPIN.label"/></logic:equal>
        <logic:equal name="device" property="bootstrapPinType" value="1"><bean:message key="meta.bootstrap.pintype.USERPIN.label"/></logic:equal>
        <logic:equal name="device" property="bootstrapPinType" value="2"><bean:message key="meta.bootstrap.pintype.USERNETWPIN.label"/></logic:equal>
        <logic:equal name="device" property="bootstrapPinType" value="3"><bean:message key="meta.bootstrap.pintype.USERPINMAC.label"/></logic:equal>
      </td>
    </tr>
    <tr>
      <th><bean:message key="device.title.bootstrapUserPin"/></th>
      <td><bean:write name="device" property="bootstrapUserPin" /></td>
    </tr>
    <tr>
      <th><bean:message key="device.title.lastBootstrapTime"/></th>
      <td><bean:write name="device" property="lastBootstrapTime" /></td>
    </tr>
    <tr>
      <th><bean:message key="device.title.bootstrapAskCounter"/></th>
      <td><bean:write name="device" property="bootstrapAskCounter" /></td>
    </tr>
    <tr>
      <th><bean:message key="device.title.lastNotificationTime"/></th>
      <td><bean:write name="device" property="subscriber.notificationTime" /></td>
    </tr>
    <tr>
      <th><bean:message key="device.title.createdTime"/></th>
      <td><bean:write name="device" property="createdTime" format="yyyy-MM-dd HH:mm:ss"/></td>
    </tr>
    <tr>
      <th><bean:message key="device.title.firstSync"/></th>
      <td>
        <logic:present name="firstSyncTimestamp"><bean:write name="firstSyncTimestamp"/></logic:present>
      </td>
    </tr>
    <tr>
      <th><bean:message key="device.title.lastSync"/></th>
      <td>
        <logic:present name="lastSyncTimestamp"><bean:write name="lastSyncTimestamp"/></logic:present>
      </td>
    </tr>
  </tbody>
</table>

<div class="buttonArea" style="height:40px">
  <dm:permission roles="dm.admin.devices">
  <html:form action="/EditDevice" method="post">
	<INPUT type="hidden" name="ID" value="<bean:write name="device" property="ID"/>">
    <html:submit><bean:message key="page.device.button.modify"/></html:submit>
  </html:form>
  </dm:permission>
  <html:form action="/Devices" method="post">
    <html:cancel><bean:message key="page.device.button.backToDeviceList.label"/></html:cancel>
  </html:form>
</div>

<table class=table_box cellSpacing=0 cellPadding=0 width="100%" border="0">
  <tbody>
  <tr>
    <td class=table_box_heading width="100%" colSpan=5><font color="red"><b>.:</b></font> <bean:message key="page.device.changeLogs.message"/></TD>
  </tr>
  <tr>
    <td>
    <display:table name="changeLogs" id="deviceChangeLog" class="simple_inline">
      <display:column class="rowNum">
        <c:out value="${deviceChangeLog_rowNum}"/>
      </display:column>
      <display:column property="lastUpdate" sortable="true" titleKey="device.title.createdTime"/>
      <display:column property="phoneNumber" sortable="true" titleKey="device.title.phonenumber"/>
      <display:column property="imsi" sortable="true" title="IMSI"/>
      <display:column property="imei" sortable="true" titleKey="device.title.externalId"/>
      <display:column sortable="true" titleKey="device.title.model">
        <c:out value="${deviceChangeLog.brand}"/> <c:out value="${deviceChangeLog.model}"/>
      </display:column>
      <display:column property="softwareVersion" sortable="true" titleKey="device.title.firmwareVersion"/>
    </display:table>
    </td>
  </tr>
  </tbody>
</table>