<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.npower.dm.action.BaseAction" %>
<%@ page import="com.npower.dm.core.ProvisionJob" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<div class="messageArea">
  <bean:message key="page.jobs.list.queued.message" /><br><br>
</div>

<!-- List of jobs -->
<html:form action="/jobs/Job">
<input type="hidden" name="action" value=""/>
<display:table name="jobs" id="job" pagesize="<%=BaseAction.getRecordsPerPage(request)%>" requestURI="<%=request.getContextPath() + "/jobs/list/queued.do"%>" class="simple">
  <display:column class="rowNum" 
                  href="<%=request.getContextPath() + "/jobs/JobView.do?action=view"%>" paramId="jobID" paramProperty="ID">
    <c:out value="${job_rowNum}"/>
  </display:column>
  <display:column class="rowNum">
    <html:checkbox property="jobIDs" value="${job.ID}"></html:checkbox>
  </display:column>
  <display:column property="ID" sortable="true" titleKey="page.device.jobs.job.id.label" href="<%=request.getContextPath() + "/jobs/JobView.do?action=view"%>" paramId="jobID" paramProperty="ID"/>
  <display:column sortable="true" titleKey="page.device.jobs.job.type.label" href="<%=request.getContextPath() + "/jobs/JobView.do?action=view"%>" paramId="jobID" paramProperty="ID">
    <bean:message key="meta.job.type.${job.jobType}.label"/>
  </display:column>
  <display:column sortable="true" titleKey="page.device.jobs.job.mode.label" href="<%=request.getContextPath() + "/jobs/JobView.do?action=view"%>" paramId="jobID" paramProperty="ID">
    <bean:message key="meta.job.mode.${job.jobMode}.label"/>
  </display:column>
  <display:column property="name" sortable="true" titleKey="page.device.jobs.job.name.label" maxLength="36" href="<%=request.getContextPath() + "/jobs/JobView.do?action=view"%>" paramId="jobID" paramProperty="ID"/>
  <display:column sortable="true" titleKey="page.Jobs.jobtypes.targetDevices.label" style="text-align: center;" href="<%=request.getContextPath() + "/jobs/JobView.do?action=view"%>" paramId="jobID" paramProperty="ID">
    <% ProvisionJob jobItem = (ProvisionJob)job; %>
    <%=jobItem.getAllOfProvisionJobStatus().size() + jobItem.getOtaTargetDevices().size()%>
  </display:column>
  <%-- <display:column property="jobTypeForDisplay" sortable="true" titleKey="page.device.jobs.job.type.label"/> --%>
  <%-- <display:column property="targetType" sortable="true" titleKey="page.device.jobs.job.targetType.label"/> --%>
  <%-- <display:column property="description" sortable="true" titleKey="page.device.jobs.job.description.label"/> --%>
  <display:column property="scheduledTime" format="{0,date,yyyy-MM-dd HH:mm:ss}" sortable="true" titleKey="page.device.jobs.job.scheduledTime.label" href="<%=request.getContextPath() + "/jobs/JobView.do?action=view"%>" paramId="jobID" paramProperty="ID"/>
  <display:column>
    <a href="<html:rewrite page='/jobs/JobView.do'/>?action=view&jobID=<c:out value='${job.ID}'/>"><bean:message key="page.button.view.label"/></a>
    | <a href="<html:rewrite page='/jobs/Job.do'/>?action=removeJob&jobID=<c:out value='${job.ID}'/>" onclick='javascript: return confirm("<bean:message key="message.delete.areYourSureDelete"/>");'><bean:message key="page.button.remove.label"/></a> | 
    <c:if test="${job.state=='Disable' || job.state=='Cancelled'}">
      <a href="<html:rewrite page='/jobs/Job.do'/>?action=enableJob&jobID=<c:out value='${job.ID}'/>" onclick='javascript: return confirm("<bean:message key="message.jobs.areYourSureEnable"/>");'><bean:message key="page.button.enable.label"/></a> 
    </c:if>
    <c:if test="${job.state=='Applied'}">
      <a href="<html:rewrite page='/jobs/Job.do'/>?action=disableJob&jobID=<c:out value='${job.ID}'/>" onclick='javascript: return confirm("<bean:message key="message.jobs.areYoutSureDisable"/>");'><bean:message key="page.button.disable.label"/></a>
    </c:if>
  </display:column>
</display:table>
</html:form>

<!-- Panel of buttons -->
<div class="buttonArea" style="height: 40px;">
  <html:form action="/jobs/SelectJobType">
    <html:submit><bean:message key="page.device.button.createJob"/></html:submit>
  </html:form>
  <logic:notEmpty name="jobs">
  <form>
    <input type="button" name="DisableButton" onclick="return doDisableSelected();" value="<bean:message key="page.button.disable.label"/>" class="NormalWidthButton"/>
    <input type="button" name="RemoveButton" onclick="return doRemoveSelected();" value="<bean:message key="page.button.remove.label"/>" class="NormalWidthButton"/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <input type="button" name="DisableButton" onclick="return doDisableeAll();" value="<bean:message key="page.button.disableAll.label"/>" class="NormalWidthButton"/>
    <input type="button" name="RemoveButton" onclick="return doRemoveAll();" value="<bean:message key="page.button.removeAll.label"/>" class="NormalWidthButton"/>
  </form>
  </logic:notEmpty>
</div>

<!-- Include common javascript -->
<jsp:include page="/jsp/jobs/jobs/javascript.jsp"/>
