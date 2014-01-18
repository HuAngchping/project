<%@ page language="java"%>
<%@ page import="com.npower.dm.action.BaseAction"%>
<%@ page import="com.npower.dm.core.ProfileConfig"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<table class="entityview">
  <thead>
    <tr>
      <th colspan="2">
        <bean:message key="page.device.tree.device.title" />
      </th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th width="150">
        <bean:message key="device.title.externalId" />
      </th>
      <td>
        <bean:write name="device" property="externalId" />
      </td>
    </tr>
    <tr>
      <th>
        <bean:message key="device.title.phonenumber" />
      </th>
      <td>
        <bean:write name="device" property="subscriberPhoneNumber" />
      </td>
    </tr>
    <tr>
      <th>
        <bean:message key="device.title.manufacturer" />
      </th>
      <td>
        <bean:write name="device" property="model.manufacturer.name" />
      </td>
    </tr>
    <tr>
      <th>
        <bean:message key="device.title.model" />
      </th>
      <td>
        <bean:write name="device" property="model.name" />
      </td>
    </tr>
    <tr>
      <th>
        <bean:message key="device.job.jobType" />
      </th>
      <td>
        <bean:write name="JOB_TYPE_FOR_ASSIGN_PROFILE" property="name" /> [ <bean:write name="JOB_TYPE_FOR_ASSIGN_PROFILE" property="mode" /> ]
      </td>
    </tr>
  </tbody>
</table>

<html:messages id="inputProfileID" message="true">
  <div class="messageArea">
    <div class="validateErrorMsg" style="color: red">
      <bean:write name="inputProfileID" />
    </div>
  </div>
</html:messages>

<html:form action="/device/job/cp/EditProfileAssignment">
  <bean:define id="deviceID" name="device" property="ID"></bean:define>
  <html:hidden property="value(deviceID)" value="${device.ID}"/>

  <table class=table_box cellSpacing=0 cellPadding=0 width="100%" border="0">
    <tbody>
      <tr>
        <td class=table_box_heading width="100%" colSpan=5>
          <font color="red"><b>.:</b>
          </font>
          <bean:message key="page.jobs.profileAssignment.message1.selectProfileConfig" />
        </TD>
      </tr>
      <tr>
        <td>
          <display:table name="results" id="record"
            requestURI="<%=request.getContextPath() + "/device/job/cp/SelectProfileConfig.do"%>"
            pagesize="<%=BaseAction.getRecordsPerPage(request)%>" class="simple_inline">
            <display:column class="rowNum">
              <c:out value="${record_rowNum}" />
            </display:column>
            <display:column class="rowNum">
              <input type="radio" name="value(profileID)" value="<%="" + ((ProfileConfig) record).getID()%>" />
            </display:column>
            <display:column property="name" sortable="true" titleKey="profile.config.name.label" />
            <display:column property="profileType" sortable="true" titleKey="profile.config.profileType.lable" />
            <display:column property="profileTemplate.name" sortable="true" titleKey="profile.config.template.label" />
            <display:column property="carrier.name" sortable="true" titleKey="profile.config.carrier.label" />
          </display:table>
        </td>
      </tr>
    </tbody>
  </table>

  <html:submit styleClass="NormalWidthButton">
    <bean:message key="page.button.next.label" />
  </html:submit>
  <html:cancel styleClass="NormalWidthButton">
    <bean:message key="page.button.cancel.label" />
  </html:cancel>
</html:form>
