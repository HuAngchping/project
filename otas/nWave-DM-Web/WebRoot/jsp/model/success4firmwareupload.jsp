
<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<table class="entityview">
  <THEAD>
    <TR>
      <TH ><bean:message key="page.firmware.success.message"/></TH>
    </TR>
  </THEAD>
</table>
<table class="entityview">
  <tbody>
    <tr>
      <th width="150"><bean:message key="device.title.externalId"/></th>
      <td><bean:write name="update" property="fromImage.model.manufacturer.name"/> <bean:write name="update" property="fromImage.model.name"/></td>
    </tr>
    <tr>
      <th width="150"><bean:message key="page.firmware.fromVersionID.label"/></th>
      <td><bean:write name="update" property="fromImage.versionId"/></td>
    </tr>
    <tr>
      <th width="150"><bean:message key="page.firmware.toVersionID.label"/></th>
      <td><bean:write name="update" property="toImage.versionId"/></td>
    </tr>
  </tbody>
</table>
	
<div class="buttonArea" style="height: 40px;">
	<html:form action="/model/updateimages">
		<input type="hidden" name="modelID" value="<bean:write name="update" property="fromImage.model.ID"/>">
		<html:submit><bean:message key="page.firmware.button.backToFirmwareList.label"/></html:submit>
	</html:form>
	<html:form action="/ViewModel">
		<input type="hidden" name="ID" value="<bean:write name="update" property="fromImage.model.ID"/>"/>
		<input type="hidden" name="action" value="update"/>
		<html:submit>
			<bean:message key="page.firmware.button.backToModelView.label"/>
		</html:submit>
	</html:form>
</div>
