
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

<logic:messagesPresent message="false">
<div class="validateErrorMessagePanel">
  <html:errors property="softwareID" />
</div>
</logic:messagesPresent>

<html:form action="/device/job/software/uninstallation/save">
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
	  </tbody>
	</table>
    <TABLE class=table_box cellSpacing=0 cellPadding=0 width="100%" border="0">
      <TBODY>
      <TR>
        <TD class=table_box_heading width="100%" colSpan=5><font color="red"><b>.:</b></font> <bean:message key="page.device.job.software.uninstall.softwares.label"/></TD>
      </TR>
      <TR>
        <TD>
          <logic:empty name="softwares">
              <font color="red"><bean:message key="page.device.job.software4uninstall.message.noAvailiable"/></font>
          </logic:empty>
          <logic:notEmpty name="softwares">
          <display:table name="softwares" id="record" class="simple_inline">
            <display:column class="rowNum"><c:out value="${record_rowNum}" /></display:column>
            <display:column style="width: 30px;"><input type="radio" name="softwareID" value="<c:out value="${record.id}"/>" checked="checked"/></display:column>
            <display:column property="vendor.name" titleKey="entity.software.vendor.label"/>
            <display:column property="name" titleKey="entity.software.software.name.label"/>
            <display:column property="version" titleKey="entity.software.software.version.label"/>
            <display:column property="category.name" titleKey="entity.software.category.label"/>
          </display:table>
          </logic:notEmpty>
        </TD>
      </TR>
      </TBODY>
    </TABLE>
	<div class="buttonArea">
	  <logic:notEmpty name="softwares">
      <html:submit property="action" styleClass="NormalWidthButton"><bean:message key="page.button.next.label"/></html:submit>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	  </logic:notEmpty>
	  <html:cancel styleClass="NormalWidthButton"><bean:message key="page.button.cancel.label"/></html:cancel>
	</div>
</html:form>
