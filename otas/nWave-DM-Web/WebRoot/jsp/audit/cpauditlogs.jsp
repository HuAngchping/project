<%@ page import="com.npower.dm.action.BaseAction"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<display:table name="cpAuditLogs" id="record" class="simple" pagesize="<%=BaseAction.getRecordsPerPage(request) %>" requestURI="<%=request.getContextPath() + "/audit/CPAuditlogs.do"%>">
	<display:column class="rowNum">
		<c:out value="${record_rowNum}" />
	</display:column>
  <display:column property="timeStamp" sortable="true" titleKey="page.cp.audit.logs.timeStamp.label" href="<%=request.getContextPath() + "/audit/ViewCPAuditlog.do"%>"  paramId="ID" paramProperty="cpAuditLogId"/>
  <display:column property="devicePhoneNumber" sortable="true" titleKey="page.cp.audit.searchPannel.phoneNumber.label" href="<%=request.getContextPath() + "/audit/ViewCPAuditlog.do"%>"  paramId="ID" paramProperty="cpAuditLogId"/>
  <display:column property="profileName" sortable="true" titleKey="page.cp.audit.logs.profileName.label" href="<%=request.getContextPath() + "/audit/ViewCPAuditlog.do"%>"  paramId="ID" paramProperty="cpAuditLogId"/>
  <display:column sortable="true" titleKey="page.cp.audit.logs.model.label" href="<%=request.getContextPath() + "/audit/ViewCPAuditlog.do"%>"  paramId="ID" paramProperty="cpAuditLogId">
    <c:out value="${record.manufacturerExtID}"/>&nbsp;<c:out value="${record.modelExtID}"/>
  </display:column>

  <display:column sortable="true" titleKey="page.cp.audit.logs.status.label" href="<%=request.getContextPath() + "/audit/ViewCPAuditlog.do"%>"  paramId="ID" paramProperty="cpAuditLogId" class="rowNum">
      <logic:equal name="record" property="status" value="success">
		<html:img page="/common/images/icons/success.gif" alt="${record.status}"></html:img>		
      </logic:equal>
      <logic:equal name="record" property="status" value="sent">
        <html:img page="/common/images/icons/success.gif" alt="${record.status}"></html:img>  
      </logic:equal>
      <logic:equal name="record" property="status" value="received">
        <html:img page="/common/images/icons/success.gif" alt="${record.status}"></html:img>  
      </logic:equal>
      <logic:equal name="record" property="status" value="wait_to_enqueue">
		<html:img page="/common/images/icons/failure.gif" alt="${record.status}"></html:img>
      </logic:equal>      
      <logic:equal name="record" property="status" value="queued">
        <html:img page="/common/images/icons/failure.gif" alt="${record.status}"></html:img>
      </logic:equal>      
      <logic:equal name="record" property="status" value="unkown">
        <html:img page="/common/images/icons/failure.gif" alt="${record.status}"></html:img>
      </logic:equal>      
      <logic:equal name="record" property="status" value="failure">
        <html:img page="/common/images/icons/failure.gif" alt="${record.status}"></html:img>
      </logic:equal>      
  </display:column>
  
  <display:column property="profileCategoryName" sortable="true" titleKey="page.cp.audit.logs.profileCategory.label" href="<%=request.getContextPath() + "/audit/ViewCPAuditlog.do"%>"  paramId="ID" paramProperty="cpAuditLogId"/>
  <display:column>
    [<a href="<html:rewrite page='/audit/ViewCPAuditlog.do'/>?ID=${record.cpAuditLogId}"><bean:message key="page.button.view.label"/></a>]
  </display:column>
</display:table>

<form action="createReport.do">
    <input type="hidden" name="method" value="sendAQuery"/>
    <html:submit> <bean:message key="page.cp.audit.logs.button.label"/> </html:submit>
</form>




