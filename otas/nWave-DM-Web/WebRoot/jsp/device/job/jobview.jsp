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
      <th><bean:message key="device.title.model"/></th>
      <td><bean:write name="device" property="model.manufacturer.name" /> <bean:write name="device" property="model.name" /></td>
    </tr>
  </tbody>
</table>

<table class="entityview">
  <thead>
    <tr>
      <th colspan="2"><bean:message key="page.jobs.jobview.form.label"/></th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th width="150"><bean:message key="page.device.jobs.job.id.label"/></th>
      <td><bean:write name="provisionJob" property="ID"/></td>
    </tr>
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
      <th width="150"><bean:message key="page.device.jobs.job.description.label"/></th>
      <td><bean:write name="provisionJob" property="description"/></td>
    </tr>

<%-- DM mode Job    ++++++++++++++++++++++++++++++++++++++   --%>
<logic:equal name="provisionJob" property="jobMode" value="dm">
  <%-- ProfileAssignment Job    ++++++++++++++++++++++++++++++++++++++   --%>
  <logic:equal name="provisionJob" property="jobType" value="Assign">
    <tr>
      <th width="150"><bean:message key="page.device.jobs.job.profiles.label"/></th>
      <td>
		<display:table name="provisionJob.profileAssignments" id="profileAssignment" class="simple">
	      <display:column property="profileConfig.name" titleKey="profile.config.name.label" />
	      <c:set var="nestedName" value="provisionJob.profileAssignments[${profileAssignment_rowNum -1}].allOfNameValuePairs" />
	      <c:set var="profileConfig" value="provisionJob.profileAssignments[${profileAssignment_rowNum -1}].profileConfig" />
	      <c:set var="profileTemplate" value="provisionJob.profileAssignments[${profileAssignment_rowNum -1}].profileConfig.profileTemplate" />
	      <display:column>
	          <display:table name="${nestedName}" id="attribute${profileAssignment_rowNum}" class="simple">
		        <display:column property="name" titleKey="page.profileConfig.attribute.name.lable" />
		        <display:column property="value" titleKey="page.profileConfig.attribute.value.label" />
	          </display:table>
          </display:column>
        </display:table>
      </td>
    </tr>
  </logic:equal>
  
  <%-- Re-Send ProfileAssignment Job   ++++++++++++++++++++++++++++++++++++++    --%>
  <logic:equal name="provisionJob" property="jobType" value="Re-Send">
    <tr>
      <th width="150"><bean:message key="page.device.jobs.job.profiles.label"/></th>
      <td>
		<display:table name="provisionJob.profileAssignments" id="profileAssignment" class="simple">
	      <display:column property="profileConfig.name" titleKey="profile.config.name.label" />
	      <c:set var="nestedName" value="provisionJob.profileAssignments[${profileAssignment_rowNum -1}].allOfNameValuePairs" />
	      <c:set var="profileConfig" value="provisionJob.profileAssignments[${profileAssignment_rowNum -1}].profileConfig" />
	      <c:set var="profileTemplate" value="provisionJob.profileAssignments[${profileAssignment_rowNum -1}].profileConfig.profileTemplate" />
	      <display:column>
	          <display:table name="${nestedName}" id="attribute${profileAssignment_rowNum}" class="simple">
		        <display:column property="name" titleKey="page.profileConfig.attribute.name.lable" />
		        <display:column property="value" titleKey="page.profileConfig.attribute.value.label" />
	          </display:table>
          </display:column>
        </display:table>
      </td>
    </tr>
  </logic:equal>
  
  <%-- Discovery Job   ++++++++++++++++++++++++++++++++++++++     --%>
  <logic:equal name="provisionJob" property="jobType" value="Discovery">
    <tr>
      <th width="150"><bean:message key="page.device.jobs.job.discoveryNodes.label"/></th>
      <td>
		<logic:iterate name="provisionJob" property="nodes4Discovery" id="nodePath">
			<bean:write name="nodePath"/><br>
        </logic:iterate>
      </td>
    </tr>
  </logic:equal>
  
  <%-- Command Script Job     --%>
  <logic:equal name="provisionJob" property="jobType" value="Script">
    <tr>
      <th width="150"><bean:message key="page.device.jobs.job.commandScript.label"/></th>
      <td>
        <pre><code><otasdm:xmlPretty content="${provisionJob.scriptString}"/></code></pre>
      </td>
    </tr>
  </logic:equal>
  
  <%-- Firmware update Job     --%>
  <logic:equal name="provisionJob" property="jobType" value="Firmware">
    <tr>
      <th width="150"><bean:message key="page.device.jobs.job.firmware.targetVersion.label"/></th>
      <td>
        <bean:write name="provisionJob" property="targetImage.versionId"/>
      </td>
    </tr>
  </logic:equal>
</logic:equal>

  </tbody>
</table>

<%--
<!--  Device Job Status  -->
<div class="messageArea">
  <bean:message key="page.jobs.jobview.message1"/>
</div>

<display:table name="deviceJobStatus" id="record" pagesize="20" class="simple">
  <display:column class="rowNum">
	<c:out value="${record_rowNum}"/>
  </display:column>
  <display:column property="device.externalId" titleKey="page.device.job.assignment.label.deviceID" />
  <display:column property="state" titleKey="page.device.jobs.job.device.status.label" />
</display:table>
--%>

<div class="buttonArea" style="height: 40px;">
  <html:form action="/device/Jobs" method="post">
	  <INPUT type="hidden" name="deviceID" value="<bean:write name="device" property="ID"/>">
	  <html:submit><bean:message key="page.device.job.success.button.jobs.label"/></html:submit>
  </html:form>
  <html:form action="/ViewDevice" method="post">
	  <INPUT type="hidden" name="method" value="view">
	  <INPUT type="hidden" name="ID" value="<bean:write name="device" property="ID"/>">
	  <html:submit><bean:message key="page.device.button.backToDeviceView.label"/></html:submit>
  </html:form>
</div>
