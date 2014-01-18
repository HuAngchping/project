<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.npower.dm.action.BaseAction" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/otas-dm.tld" prefix="dm" %>

<display:table name="devices" id="device" pagesize="<%=BaseAction.getRecordsPerPage(request)%>" requestURI="<%=request.getContextPath() + "/Devices.do"%>" class="simple">
  <display:column class="rowNum" 
                  href="<%=request.getContextPath() + "/ViewDevice.do"%>" paramId="ID" paramProperty="ID">
    <c:out value="${device_rowNum}"/>
  </display:column>
  <display:column property="externalId" sortable="true" titleKey="device.title.externalId" 
                  href="<%=request.getContextPath() + "/ViewDevice.do"%>" paramId="ID" paramProperty="ID"/>
  <display:column property="subscriber.phoneNumber" sortable="true" titleKey="device.title.phonenumber" 
                  href="<%=request.getContextPath() + "/ViewDevice.do"%>" paramId="ID" paramProperty="ID"/>
  <display:column property="model.manufacturer.name" sortable="true" titleKey="device.title.manufacturer" 
                  href="<%=request.getContextPath() + "/ViewDevice.do"%>" paramId="ID" paramProperty="ID"/>
  <display:column sortable="true" titleKey="device.title.model">
                  <html:link action="/ViewModel.do?action=update" styleClass="reference_link" target="_blank" paramId="ID" paramName="device" paramProperty="model.ID"><c:out value="${device.model.name}"></c:out></html:link>
  </display:column>
  <display:column>
    <a href="<html:rewrite page='/ViewDevice.do'/>?ID=<c:out value='${device.ID}'/>"><bean:message key="page.button.view.label"/></a>
    <dm:permission roles="dm.admin.devices">
     | <a href="<html:rewrite page='/EditDevice.do'/>?ID=<c:out value='${device.ID}'/>"><bean:message key="page.button.edit.label"/></a>
     | <a href="<html:rewrite page='/RemoveDevice.do'/>?method=remove&ID=<c:out value='${device.ID}'/>" onclick='javascript: return confirm("<bean:message key="message.delete.areYourSureDelete"/>");'><bean:message key="page.button.remove.label"/></a>
    </dm:permission>
     | <html:link action="/device/DeviceTree" paramId="deviceID" paramName="device" paramProperty="ID"><html:img page="/common/images/icons/icon_tree.gif" altKey="page.device.button.viewDeviceTree"/></html:link>
    </display:column>
</display:table>
<dm:permission roles="dm.admin.devices">
<div class="buttonArea">
<html:form action="/EditDevice">
  <html:hidden property="method" value="edit"/>
  <html:submit><bean:message key="page.devices.button.add"/></html:submit>
</html:form>
</div>
</dm:permission>
