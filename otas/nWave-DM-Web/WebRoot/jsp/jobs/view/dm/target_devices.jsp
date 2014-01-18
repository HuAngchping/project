<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://otas.cn/otasdm-taglib" prefix="otasdm"%>

<%-- DM Target devices    ++++++++++++++++++++++++++++++++++++++   --%>
<!--  Device status  -->
<TABLE class=table_box cellSpacing=0 cellPadding=0 width="100%" border="0">
  <TBODY>
  <TR>
    <TD class=table_box_heading width="100%" colSpan=5><font color="red"><b>.:</b></font> <bean:message key="page.jobs.jobview.target_devices.header.message"/></TD>
  </TR>
  <TR>
    <TD>
      <display:table name="deviceJobStatus" id="record" class="simple_inline">
        <display:column class="rowNum">
          <c:out value="${record_rowNum}"/>
        </display:column>
       <display:column titleKey="page.device.job.assignment.label.deviceID">
         <html:link action="/ViewDevice.do" styleClass="reference_link" target="_blank" paramId="ID" paramName="record" paramProperty="device.ID"><c:out value="${record.device.externalId}"/></html:link>
       </display:column>
       <display:column property="device.subscriber.phoneNumber" titleKey="device.title.phonenumber"/>
       <display:column titleKey="page.device.jobs.job.device.status.label">
         <bean:message key="meta.job.device.state.${record.state}.label"/>
       </display:column>
       <display:column>
         <html:link action="jobs/job/device/dmSessionTracking/sumary?jobID=${provisionJob.ID}" paramId="deviceID" paramName="record" paramProperty="device.externalId"><bean:message key="page.jobs.jobview.target_devices.button.session.sumary.label"/></html:link>   
       </display:column>
       <display:column>
         <html:link action="jobs/job/device/dmSessionTracking/detail?jobID=${provisionJob.ID}" paramId="deviceID" paramName="record" paramProperty="device.externalId"><bean:message key="page.jobs.jobview.target_devices.button.session.detail.label"/></html:link>   
       </display:column>
      </display:table>
    </TD>
  </TR>
  </TBODY>
</TABLE>

