
<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<table class="entityview">
  <THEAD>
    <TR>
      <TH ><bean:message key="page.device.job.success.message1"/></TH>
    </TR>
  </THEAD>
</table>
<table class="entityview">
  <tbody>
    <tr>
      <th width="30%" nowrap="nowrap"><bean:message key="page.device.job.success.jobID"/></th>
      <td><bean:write name="provisionJob" property="ID"/></td>
    </tr>
    <tr>
      <th>
        <bean:message key="page.Jobs.jobtypes.job.type.label" />
      </th>
      <td>
        <bean:write name="JOB_TYPE_FOR_ASSIGN_PROFILE" property="name" /> [ <bean:write name="JOB_TYPE_FOR_ASSIGN_PROFILE" property="mode" /> ]
      </td>
    </tr>
  </tbody>
</table>
	
<div class="buttonArea" style="height: 40px;">
  <html:form action="/jobs/list/queued" method="post">
	  <html:submit><bean:message key="page.jobs.button.backJobsList.label"/></html:submit>
  </html:form>
  <html:form action="/jobs/JobView" method="post">
      <input type="hidden" name="jobID" value="<bean:write name="provisionJob" property="ID"/>">
	  <html:submit><bean:message key="page.jobs.button.viewJobDetail.label"/></html:submit>
  </html:form>
</div>
