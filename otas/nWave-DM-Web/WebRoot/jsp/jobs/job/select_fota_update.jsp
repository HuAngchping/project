
<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<script language="JavaScript1.2">
</script>
<div class="messageArea">
  <bean:message key="page.message.required"/><bean:message key="page.message.input"/>
</div>

<logic:messagesPresent message="false">
<div class="validateErrorMessagePanel">
  <html:errors property="softwareID" />
</div>
</logic:messagesPresent>

<html:form action="/jobs/job/fota/save">
    <TABLE class=table_box cellSpacing=0 cellPadding=0 width="100%" border="0">
      <TBODY>
      <TR>
        <TD class=table_box_heading width="100%" colSpan=5><font color="red"><b>.:</b></font> <bean:message key="page.device.job.fota.firmwares.label"/></TD>
      </TR>
      <TR>
        <TD>
          <logic:empty name="updates">
              <font color="red"><bean:message key="page.device.job.fota.message.noAvailiable"/></font>
          </logic:empty>
          <logic:notEmpty name="updates">
          <display:table name="updates" id="record" class="simple_inline">
            <display:column class="rowNum"><c:out value="${record_rowNum}" /></display:column>
            <display:column style="width: 30px;"><input type="radio" name="updateID" value="<c:out value="${record.ID}"/>" checked="checked"/></display:column>
            <display:column titleKey="page.firmware.model.label">
              <c:out value="${record.fromImage.model.manufacturer.name}"/> <c:out value="${record.fromImage.model.name}"/>
            </display:column>
            <display:column property="fromImage.versionId" sortable="true" maxLength="24" sortName="fromImage.versionId" titleKey="page.firmware.fromVersionID.label" style="white-space: nowrap;"/>
            <display:column property="toImage.versionId" sortable="true" maxLength="24" sortName="toImage.versionId" titleKey="page.firmware.toVersionID.label" style="white-space: nowrap;"/>
            <display:column property="size" sortable="true" sortName="size" titleKey="page.firmware.imageSize.label" style="text-align: right;"/>
          </display:table>
          </logic:notEmpty>
        </TD>
      </TR>
      </TBODY>
    </TABLE>
	<div class="buttonArea">
      <html:submit property="action" styleClass="NormalWidthButton"><bean:message key="page.button.next.label"/></html:submit>
	  <html:cancel styleClass="NormalWidthButton"><bean:message key="page.button.cancel.label"/></html:cancel>
	</div>
</html:form>

<!-- Panel for Target Devices -->
<table class=table_box cellSpacing=0 cellPadding=0 width="100%" border="0">
  <tbody>
  <tr>
    <td class=table_box_heading width="100%" colSpan=5><font color="red"><b>.:</b></font> <bean:message key="page.jobs.submit.job.success.target.devices.message"/></td>
  </tr>
  <tr>
    <td>
      <jsp:include page="/jsp/jobs/job/common/target_devices_view.jsp"></jsp:include>
    </td>
  </tr>
  </tbody>
</table>
