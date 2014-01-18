<%@ page import="com.npower.dm.action.BaseAction" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %>


<fmt:bundle basename="com.npower.dm.struts.ApplicationResources">
</fmt:bundle>

<display:table name="subscribers" id="subscriber" class="simple" pagesize="<%=BaseAction.getRecordsPerPage(request)%>" requestURI="<%=request.getContextPath() + "/Subscribers.do"%>">
	<display:column class="rowNum" href="<%=request.getContextPath() + "/EditSubscriber.do?method=view"%>" paramId="ID" paramProperty="ID">
		<c:out value="${subscriber_rowNum}" />
	</display:column>
	<display:column property="externalId" sortable="true" sortName="phoneNumber"  titleKey="page.subscriber.externalId.title" href="<%=request.getContextPath() + "/EditSubscriber.do?method=view"%>" paramId="ID" paramProperty="ID" />
	<display:column property="phoneNumber" sortable="true" sortName="phoneNumber"  titleKey="page.subscriber.phoneNumber.title" href="<%=request.getContextPath() + "/EditSubscriber.do?method=view"%>" paramId="ID" paramProperty="ID" />
  <display:column property="IMSI" sortable="true" sortName="IMSI"  titleKey="page.subscriber.imsi.title" href="<%=request.getContextPath() + "/EditSubscriber.do?method=view"%>" paramId="ID" paramProperty="ID" />
	<display:column property="carrier.name" sortable="true" sortName="phoneNumber"  titleKey="page.subscriber.carrier.title" href="<%=request.getContextPath() + "/EditSubscriber.do?method=view"%>" paramId="ID" paramProperty="ID" />
	<display:column sortable="true" titleKey="page.subscriber.NumberOfDevices.title" href="<%=request.getContextPath() + "/EditSubscriber.do?method=view"%>" paramId="ID" paramProperty="ID">
	    <c:out value="${fn:length(subscriber.devices)}"/>
	</display:column>
	<display:column>
		<a href="<html:rewrite page='/EditSubscriber.do'/>?method=view&ID=<c:out value='${subscriber.ID}'/>"><bean:message key="page.button.view.label" /></a> | 
        <a href="<html:rewrite page='/EditSubscriber.do'/>?method=update&ID=<c:out value='${subscriber.ID}'/>"><bean:message key="page.button.edit.label" /></a> | 
        <a href="<html:rewrite page='/RemoveSubscriber.do'/>?method=remove&ID=<c:out value='${subscriber.ID}'/>" onclick='javascript: return confirm("<bean:message key="message.delete.areYourSureDelete"/>");'><bean:message key="page.button.remove.label" /></a>
	</display:column>
</display:table>
<html:form action="/EditSubscriber">
	<html:hidden property="method" value="create" />
	<html:submit>
		<bean:message key="page.subscribers.button.add" />
	</html:submit>
</html:form>
