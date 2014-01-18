
<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<fmt:bundle basename="com.npower.dm.struts.ApplicationResources">
</fmt:bundle>

<div class="messageArea">
  <bean:message key="page.message.required"/><bean:message key="page.message.input"/>
</div>

<html:form action="/device/SaveFirmwareJob">
    <input type="hidden" name="deviceID" value="<bean:write name="device" property="ID"/>"/>
	<table class="entityview">
	  <tbody>
	    <tr>
	      <th width="150"><bean:message key="device.title.externalId"/></th>
	      <td><bean:write name="device" property="externalId"/></td>
	    </tr>
	    <tr>
	      <th><bean:message key="device.title.phonenumber"/></th>
	      <td><bean:write name="device" property="subscriberPhoneNumber"/></td>
	    </tr>
	    <tr>
	      <th><bean:message key="device.title.manufacturer"/></th>
	      <td><bean:write name="device" property="model.manufacturer.name" /></td>
	    </tr>
	    <tr>
	      <th><bean:message key="device.title.model"/></th>
	      <td><bean:write name="device" property="model.name" /></td>
	    </tr>
	    <tr>
	      <th><bean:message key="device.title.firmwareVersion"/></th>
	      <td><bean:write name="currentFirmwareVersionID"/></td>
	    </tr>
	  </tbody>
	</table>
    <TABLE class=table_box cellSpacing=0 cellPadding=0 width="100%" border="0">
      <TBODY>
      <TR>
        <TD class=table_box_heading width="100%" colSpan=5><font color="red"><b>.:</b></font> <bean:message key="page.device.job.firmware.firmwares.label"/></TD>
      </TR>
      <TR>
        <TD>
          <logic:empty name="allUpdates">
              <font color="red"><bean:message key="page.device.job.firmware.message.noAvailiable"/></font>
          </logic:empty>
          <logic:notEmpty name="allUpdates">
          <display:table name="allUpdates" id="update" class="simple_inline">
            <display:column class="rowNum">
              <c:out value="${update_rowNum}" />
            </display:column>
            <display:column style="width: 30px;"><input type="radio" name="updateID" value="<c:out value="${update.ID}"/>" checked="checked"/></display:column>
            <display:column property="fromImage.versionId" titleKey="page.device.job.firmware.firmwares.fromVersion.label"/>
            <display:column property="toImage.versionId" titleKey="page.device.job.firmware.firmwares.toVersion.label"/>
            <display:column property="size" titleKey="page.device.job.firmware.firmwares.size.label"/>
          </display:table>
          </logic:notEmpty>
        </TD>
      </TR>
      </TBODY>
    </TABLE>
	<div class="buttonArea">
	  <logic:notEmpty name="allUpdates">
      <html:submit property="action" styleClass="NormalWidthButton"><bean:message key="page.button.next.label"/></html:submit>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	  </logic:notEmpty>
	  <html:cancel styleClass="NormalWidthButton"><bean:message key="page.button.cancel.label"/></html:cancel>
	</div>
</html:form>
