<%@ page language="java" pageEncoding="UTF-8"%>

<%@ page import="com.npower.dm.action.BaseAction" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

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
      <th><bean:message key="device.title.manufacturer"/></th>
      <td><bean:write name="device" property="model.manufacturer.name" /></td>
    </tr>
    <tr>
      <th><bean:message key="device.title.model"/></th>
      <td><bean:write name="device" property="model.name" /></td>
    </tr>
  </tbody>
</table>

<div class="messageArea">
  <bean:message key="page.device.softwares.deployed.caution.message"/>
</div>

<TABLE class=table_box cellSpacing=0 cellPadding=0 width="100%" border="0">
  <TBODY>
  <TR>
    <TD class=table_box_heading width="100%" colSpan=5><font color="red"><b>.:</b></font> <bean:message key="page.device.softwares.deployed.message"/></TD>
  </TR>
  <TR>
    <TD>
  <logic:notEqual name="device" property="model.manufacturer.name" value="SonyEricsson">
	<display:table name="deployedSoftwares" id="record" pagesize="<%=BaseAction.getRecordsPerPage(request)%>" requestURI="<%=request.getContextPath() + "/device/ProfileAssignments.do"%>" class="simple_inline">
	  <display:column class="rowNum">
	    <c:out value="${record_rowNum}"/>
	  </display:column>
	  <display:column titleKey="entity.software.vendor.label">
	    <bean:write name="record" property="software.vendor.name"/>
	  </display:column>
      <display:column titleKey="entity.software.software.name.label">
        <bean:write name="record" property="software.name"/>
      </display:column>
      <display:column titleKey="entity.software.software.version.label">
        <bean:write name="record" property="software.version"/>
      </display:column>
      <display:column titleKey="page.device.softwares.deployed.software.state.label">
        <logic:notEmpty name="record" property="state">
          <bean:message key="page.device.softwares.deployed.software.state.${record.state}.label"/>
        </logic:notEmpty>
        <logic:empty name="record" property="state">
          <bean:message key="page.device.softwares.deployed.software.state.Unkown.label"/>
        </logic:empty>
      </display:column>
	</display:table>
  </logic:notEqual>
  <logic:equal name="device" property="model.manufacturer.name" value="SonyEricsson">
  <display:table name="softwareInfos" id="record" pagesize="<%=BaseAction.getRecordsPerPage(request)%>" requestURI="<%=request.getContextPath() + "/device/ProfileAssignments.do"%>" class="simple_inline">
    <display:column class="rowNum">
      <c:out value="${record_rowNum}"/>
    </display:column>
    <display:column titleKey="entity.software.vendor.label">
      <bean:write name="record" property="vendorName"/>
    </display:column>
      <display:column titleKey="entity.software.software.name.label">
        <bean:write name="record" property="softwareName"/>
      </display:column>
      <display:column titleKey="entity.software.software.version.label">
        <bean:write name="record" property="softwareVersion"/>
      </display:column>
  </display:table>
  </logic:equal>
    </TD>
  </TR>
  </TBODY>
</TABLE>

<div class="buttonArea" style="height: 40px;">
  <html:form action="/ViewDevice" method="post">
	  <INPUT type="hidden" name="ID" value="<bean:write name="device" property="ID"/>">
	  <html:submit><bean:message key="page.device.button.backToDeviceView.label"/></html:submit>
  </html:form>
</div>
