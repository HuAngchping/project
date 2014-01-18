<%@ page language="java"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn"%>
<%@ taglib uri="/WEB-INF/otas-dm.tld" prefix="dm" %>

<script type="text/javascript">
<!--
function doCleanupTree() {
  if (confirm("<bean:message key="message.delete.areYourSureDelete"/>")) {
     return true;
  }
  return false;
}
//-->
</script>

<fmt:bundle basename="com.npower.dm.struts.ApplicationResources">
</fmt:bundle>

<div class="buttonArea" style="height: 40px;">
  <dm:permission roles="dm.admin.devices">
  <html:form action="/device/DeviceTree.do" method="post">
	  <INPUT type="hidden" name="method" value="cleanup">
	  <html:hidden property="deviceID"/>
	  <html:submit onclick="return doCleanupTree();"><bean:message key="page.device.tree.button.cleanup"/></html:submit>
  </html:form>
  </dm:permission>
  <html:form action="/ViewDevice" method="post">
	  <INPUT type="hidden" name="ID" value="<bean:write name="device" property="ID"/>">
	  <html:submit><bean:message key="page.device.button.backToDeviceView.label"/></html:submit>
  </html:form>
</div>

<table class="entityview">
  <thead>
    <tr>
      <th colspan="2"><bean:message key="page.device.tree.device.title"/></th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th width="150"><bean:message key="device.title.externalId"/></th>
      <td><bean:write name="device" property="externalId" /></td>
    </tr>
    <tr>
      <th><bean:message key="device.title.phonenumber"/></th>
      <td><bean:write name="device" property="subscriberPhoneNumber" /></td>
    </tr>
    <tr>
      <th><bean:message key="device.title.manufacturer"/></th>
      <td><bean:write name="device" property="model.manufacturer.name" /></td>
    </tr>
    <tr>
      <th><bean:message key="device.title.model"/></th>
      <td><bean:write name="device" property="model.name" /></td>
    </tr>
  </tbody>
</table>

<div class="messageArea">
	<bean:message key="page.device.tree.message1" />
</div>
<logic:present name="deviceTreeNode">
	<table class="entityview" width="100%">
	    <thead>
	      <tr>
	        <th colspan="2"><bean:message key="page.device.tree.node.title"/></th>
	      </tr>
	    </thead>
		<tbody>
			<tr>
				<th width="150">
					<bean:message key="deviceTree.node.title.name" />
				</th>
				<td>
					<bean:write name="deviceTreeNode" property="name" />
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="deviceTree.node.title.path" />
				</th>
				<td>
					<bean:define id="node" name="deviceTreeNode" />
					<bean:write name="deviceTreeNode" property="nodePath" />
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="deviceTree.format.title.path" />
				</th>
				<td>
					<bean:write name="deviceTreeNode" property="format" />
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="deviceTree.type.title.path" />
				</th>
				<td>
					<bean:write name="deviceTreeNode" property="type" />
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="deviceTree.size.title.path" />
				</th>
				<td>
					<bean:write name="deviceTreeNode" property="size" format="#,###"/>
				</td>
			</tr>
<logic:notEqual name="deviceTreeNode" property="format" value="node">
<logic:greaterThan name="deviceTreeNode" property="size" value="0">
			<tr>
				<th></th>
				<td>
					<html:link action="/device/DownloadDeviceTreeNode?method=getContent" target="_blank" paramId="deviceTreeNodeID" paramName="deviceTreeNode" paramProperty="ID">
					[<bean:message key="page.device.tree.node.download.label" />]<html:img page="/common/images/icons/icon_down.gif"/>
					</html:link>
					&nbsp;&nbsp;&nbsp;
					<html:link action="/device/DownloadDeviceTreeNode?method=getContentAsXML" target="_blank" paramId="deviceTreeNodeID" paramName="deviceTreeNode" paramProperty="ID">
					[<bean:message key="page.device.tree.node.downloadAsXML.label" />]<html:img page="/common/images/icons/icon_down.gif"/>
					</html:link>
				</td>
			</tr>
</logic:greaterThan>
</logic:notEqual>
<logic:notEqual name="deviceTreeNode" property="format" value="node">
<logic:notEqual name="deviceTreeNode" property="format" value="bin">
			<tr>
				<th>
					<bean:message key="deviceTree.node.title.value" />
				</th>
				<td>
				<logic:greaterThan name="deviceTreeNode" property="size" value="128">
				  <c:out value="${fn:substring(deviceTreeNode.stringValue, 0, 128)}" escapeXml="true"></c:out> ...
				  <br><br>
					<html:link action="/device/DownloadDeviceTreeNode?method=getContent" target="_blank" paramId="deviceTreeNodeID" paramName="deviceTreeNode" paramProperty="ID">
				  [ <bean:message key="page.device.tree.node.value.moreContent.label" /> ]<html:img page="/common/images/icons/icon_down.gif"/>
					</html:link>
				</logic:greaterThan>
				<logic:lessEqual name="deviceTreeNode" property="size" value="128">
					<bean:write name="deviceTreeNode" property="stringValue" />
				</logic:lessEqual>
				</td>
			</tr>
</logic:notEqual>
</logic:notEqual>
		</tbody>
	</table>

	<div class="messageArea">
		<bean:message key="page.device.tree.message2" />
	</div>

	<display:table name="children" id="child" requestURI="<%=request.getContextPath() + "/device/DeviceTree.do?deviceID=" + request.getParameter("deviceID")%>" class="simple">
		<display:column href="<%=request.getContextPath() + "/device/DeviceTree.do?deviceID=" + request.getParameter("deviceID")%>" paramId="nodeID" paramProperty="ID" class="rowNum">
		  <c:out value="${child_rowNum}"/>
		</display:column>
		<display:column property="name" sortable="true" titleKey="deviceTree.node.title.name" href="<%=request.getContextPath() + "/device/DeviceTree.do?deviceID=" + request.getParameter("deviceID")%>" paramId="nodeID" paramProperty="ID" />
		<display:column property="format" sortable="true" titleKey="deviceTree.format.title.path" href="<%=request.getContextPath() + "/device/DeviceTree.do?deviceID=" + request.getParameter("deviceID")%>" paramId="nodeID" paramProperty="ID" />
		<display:column property="type" sortable="true" titleKey="deviceTree.type.title.path" href="<%=request.getContextPath() + "/device/DeviceTree.do?deviceID=" + request.getParameter("deviceID")%>" paramId="nodeID" paramProperty="ID" />
		<display:column property="size" sortable="true" titleKey="deviceTree.size.title.path" href="<%=request.getContextPath() + "/device/DeviceTree.do?deviceID=" + request.getParameter("deviceID")%>" paramId="nodeID" paramProperty="ID" />
		<display:column property="stringValue" maxLength="128" escapeXml="true" sortable="true" titleKey="deviceTree.node.title.value" href="<%=request.getContextPath() + "/device/DeviceTree.do?deviceID=" + request.getParameter("deviceID")%>" paramId="nodeID" paramProperty="ID" />
		<display:column>
		  <dm:permission roles="dm.admin.devices">
	      <a href="<%=request.getContextPath() + "/device/DeviceTree.do?method=delete&deviceID=" + request.getParameter("deviceID") + "&nodeID=" + ((request.getParameter("nodeID")==null)?"":request.getParameter("nodeID"))%>&deleteNodeID=<c:out value="${child.ID}"/>" onclick='javascript: return confirm("<bean:message key="message.delete.areYourSureDelete"/>");'><html:img page="/common/images/delete.gif" altKey="page.button.remove.label"/></a>
	      </dm:permission> 
		</display:column>
	</display:table>
</logic:present>
