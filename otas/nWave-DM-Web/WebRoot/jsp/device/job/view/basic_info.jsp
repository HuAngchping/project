<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://otas.cn/otasdm-taglib" prefix="otasdm"%>

<table class="entityview">
  <thead>
    <tr>
      <th colspan="2"><font color="red"><b>.:</b></font> <bean:message key="page.device.tree.device.title"/></th>
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
      <th><bean:message key="device.title.model"/></th>
      <td><bean:write name="device" property="model.manufacturer.name" /> <bean:write name="device" property="model.name" /></td>
    </tr>
  </tbody>
</table>

<table class="entityview">
  <thead>
    <tr>
      <th colspan="2"><font color="red"><b>.:</b></font> <bean:message key="page.jobs.jobview.form.label"/></th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th width="150"><bean:message key="page.device.jobs.job.id.label"/></th>
      <td><bean:write name="provisionJob" property="ID"/></td>
    </tr>
    <logic:notEmpty name="provisionJob" property="parent">
    <tr>
      <th width="150"><bean:message key="page.device.jobs.job.parent.label"/></th>
      <td>
        <html:link action="/jobs/JobView.do?action=view" styleClass="reference_link" target="_blank" paramId="jobID" paramName="provisionJob" paramProperty="parent.ID">
          [<bean:write name="provisionJob" property="parent.ID"/>] <bean:write name="provisionJob" property="parent.name"/>
        </html:link>
      </td>
    </tr>
    </logic:notEmpty>
    <tr>
      <th width="150"><bean:message key="page.device.jobs.job.type.label"/></th>
      <td><bean:message key="meta.job.type.${provisionJob.jobType}.label"/></td>
    </tr>
    <tr>
      <th width="150"><bean:message key="page.device.jobs.job.mode.label"/></th>
      <td><bean:message key="meta.job.mode.${provisionJob.jobMode}.label"/></td>
    </tr>
    <tr>
      <th width="150"><bean:message key="page.device.jobs.job.name.label"/></th>
      <td><bean:write name="provisionJob" property="name"/></td>
    </tr>
    <tr>
      <th width="150"><bean:message key="page.device.jobs.job.status.label"/></th>
      <td><bean:message key="meta.job.state.${provisionJob.state}.label"/></td>
    </tr>
    <tr>
      <th width="150"><bean:message key="page.device.jobs.job.targetType.label"/></th>
      <td><bean:message key="meta.job.target.type.${provisionJob.targetType}.label"/></td>
    </tr>
    <tr>
      <th width="150"><bean:message key="page.device.jobs.job.scheduledTime.label"/></th>
      <td><bean:write name="provisionJob" property="scheduledTime"/></td>
    </tr>
    <tr>
      <th width="150"><bean:message key="page.device.jobs.job.concurrentSize.label"/></th>
      <td><bean:write name="provisionJob" property="concurrentSize"/></td>
    </tr>
    <tr>
      <th width="150"><bean:message key="page.device.jobs.job.concurrentInterval.label"/></th>
      <td><bean:write name="provisionJob" property="concurrentInterval"/> <bean:message key="page.device.jobs.job.concurrentInterval.seconds.label"/></td>
    </tr>
    <tr>
      <th width="150"><bean:message key="page.device.jobs.job.priority.label"/></th>
      <td><bean:write name="provisionJob" property="priority"/></td>
    </tr>
    <tr>
      <th width="150"><bean:message key="page.device.jobs.job.description.label"/></th>
      <td><bean:write name="provisionJob" property="description"/></td>
    </tr>
  </tbody>
</table>
    