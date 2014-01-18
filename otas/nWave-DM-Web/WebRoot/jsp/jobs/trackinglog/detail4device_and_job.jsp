<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.npower.dm.action.BaseAction" %>
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
      <th><bean:message key="device.title.model"/></th>
      <td><bean:write name="device" property="model.manufacturer.name" /> <bean:write name="device" property="model.name" /></td>
    </tr>
  </tbody>
</table>

<%-- DM Tracking Log List    ++++++++++++++++++++++++++++++++++++++   --%>
<table class=table_box cellSpacing=0 cellPadding=0 width="100%" border="0">
  <tbody>
  <tr >
    <td class=table_box_heading width="100%" colSpan=5><font color="red"><b>.:</b></font> <bean:message key="page.jobs.job.device.dmSessionTracking.detail.header.message"/></td>
  </tr>
  <tr>
    <td>
      <display:table name="results" id="record" pagesize="<%=BaseAction.getRecordsPerPage(request)%>" class="simple_inline" requestURIcontext="true" requestURI="/jobs/job/device/dmSessionTracking/detail.do">
        <display:column class="rowNum">
          <c:out value="${record_rowNum}"/>
        </display:column>
       <display:column titleKey="entity.DmTrackingLogSum.label.begintime">
         <c:out value="${record.beginTimeStamp}"/>
       </display:column>
       <display:column property="request" titleKey="entity.DmTrackingLogSum.label.request" format="{0,number,#,000}">
       </display:column>
       <display:column  property="response" titleKey="entity.DmTrackingLogSum.label.response" format="{0,number,#,000}">         
       </display:column>
       <%--
       <display:column titleKey="entity.DmTrackingLogSum.label.duration">
         <c:out value="${record.duration}"/> 
       </display:column>  
       --%>
       <display:column titleKey="entity.DmTrackingLogSum.label.clientIp">
         <c:out value="${record.clientIp}"/> 
       </display:column> 
       <display:column titleKey="entity.DmTrackingLogSum.label.userAgent">
         <c:out value="${record.userAgent}"/> 
       </display:column>    
      </display:table>
    </td>
  </tr>
  </tbody>
</table>
