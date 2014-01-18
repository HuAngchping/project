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
        <display:column property="phoneNumber" titleKey="device.title.phonenumber"/>
        <display:column titleKey="device.title.model">
          <c:out value="${record.manufacturerExternalId}"/> <c:out value="${record.modelExternalId}"/>
        </display:column>
        <display:column titleKey="page.cp.audit.logs.status.label">
          <bean:message key="meta.job.cp.state.${record.status}.label"/>
        </display:column>
        <display:column titleKey="page.device.jobs.job.numberOfEnqueueRetries.label">
          <c:out value="${record.numberOfEnqueueRetries}"/>
        </display:column>
        <display:column titleKey="page.device.jobs.job.lastEnqueueRetriesTime.label">
          <c:out value="${record.lastEnqueueRetriesTime}"/>
        </display:column>
        <display:column property="profileExternalId" titleKey="profile.config.name.label"/>
      </display:table>
    </TD>
  </TR>
  </TBODY>
</TABLE>

