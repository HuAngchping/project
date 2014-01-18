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
  <html:form action="/DDFTree" method="post">
	  <INPUT type="hidden" name="method" value="cleanup">
	  <html:hidden property="modelID"/>
	  <html:submit onclick="return doCleanupTree();"><bean:message key="page.device.ddftree.button.cleanup"/></html:submit>
  </html:form>
  <html:form action="/EditModel">
	  <input type="hidden" name="ID" value="<bean:write name="modelID"/>"/>
	  <input type="hidden" name="action" value="update"/>
	  <html:submit><bean:message key="model.page.button.backToModelView"/></html:submit>
  </html:form>
</div>

<div class="messageArea">
	<bean:message key="page.ddfnode.message1" />
</div>
<logic:present name="ddfNode">
	<table class="entityview">
		<tbody>
			<tr>
				<th width="150">
					<bean:message key="ddfnode.title.name" />
				</th>
				<td>
					<bean:write name="ddfNode" property="name" />
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="ddfnode.title.path" />
				</th>
				<td>
					<bean:write name="ddfNode" property="nodePath" />
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="ddfnode.title.description" />
				</th>
				<td>
					<bean:write name="ddfNode" property="description" />
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="ddfnode.title.title" />
				</th>
				<td>
					<bean:write name="ddfNode" property="title" />
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="ddfnode.title.format" />
				</th>
				<td>
					<bean:write name="ddfNode" property="format" />
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="ddfnode.title.occurrence" />
				</th>
				<td>
					<bean:write name="ddfNode" property="occurrence" />
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="ddfnode.title.maxOccurrence" />
				</th>
				<td>
					<bean:write name="ddfNode" property="maxOccurrence" />
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="ddfnode.title.isDynamic" />
				</th>
				<td>
					<bean:write name="ddfNode" property="isDynamic" />
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="ddfnode.title.ddfDocumentName" />
				</th>
				<td>
					<bean:write name="ddfNode" property="ddfDocumentName" />
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="ddfnode.title.value" />
				</th>
				<td>
					<bean:write name="ddfNode" property="value" />
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="ddfnode.title.defaultValue" />
				</th>
				<td>
					<bean:write name="ddfNode" property="defaultValue" />
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="ddfnode.title.ddfNodeMIMETypes" />
				</th>
				<td>
					<logic:iterate name="ddfNode" property="ddfNodeMIMETypes" id="mimeType">
						<bean:write name="mimeType" property="mimeType" />&nbsp;&nbsp;&nbsp;
	                </logic:iterate>
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="ddfnode.title.ddfNodeAccessTypes" />
				</th>
				<td>
					<logic:iterate name="ddfNode" property="ddfNodeAccessTypes" id="accessType">
						<bean:write name="accessType" property="accessType" />&nbsp;&nbsp;&nbsp;
	                </logic:iterate>
				</td>
			</tr>
		</tbody>
	</table>
</logic:present>

	<div class="messageArea">
		<bean:message key="page.ddfnode.message2" />
	</div>

	<display:table name="children" id="child" requestURI="<%=request.getContextPath() + "/DDFTree.do?modelID=" + request.getParameter("modelID")%>" class="simple">
		<display:column href="<%=request.getContextPath() + "/DDFTree.do?modelID=" + request.getParameter("modelID")%>" paramId="nodeID" paramProperty="ID" class="rowNum">
		  <c:out value="${child_rowNum}"></c:out>
		</display:column>
		<display:column property="name" sortable="true" titleKey="ddfnode.title.name" href="<%=request.getContextPath() + "/DDFTree.do?modelID=" + request.getParameter("modelID")%>" paramId="nodeID" paramProperty="ID" />
		<display:column property="format" sortable="true" titleKey="ddfnode.title.format" href="<%=request.getContextPath() + "/DDFTree.do?modelID=" + request.getParameter("modelID")%>" paramId="nodeID" paramProperty="ID" />
		<display:column property="title" sortable="true" titleKey="ddfnode.title.title" href="<%=request.getContextPath() + "/DDFTree.do?modelID=" + request.getParameter("modelID")%>" paramId="nodeID" paramProperty="ID" />
		<display:column>
	      <a href="<%=request.getContextPath() + "/DDFTree.do?method=delete&modelID=" + request.getParameter("modelID") + "&nodeID=" + ((request.getParameter("nodeID")==null)?"":request.getParameter("nodeID"))%>&deleteNodeID=<c:out value="${child.ID}"/>" onclick='javascript: return confirm("<bean:message key="message.delete.areYourSureDelete"/>");'><html:img page="/common/images/delete.gif" altKey="page.button.remove.label"/></a> 
		</display:column>
	</display:table>
