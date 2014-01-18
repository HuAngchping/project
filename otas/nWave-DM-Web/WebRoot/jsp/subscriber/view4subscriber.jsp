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
 
		<html:form action="/EditSubscriber">
		<input type="hidden" name="method" value="update"/>
		<html:hidden property="ID"/>
		<table class="entityview">
	      <thead>
	        <tr>
	          <th colspan="2">&nbsp;</th>
	        </tr>
	      </thead>
		  <tbody>
		    <tr>
		      <th width="150"><bean:message key="page.subscriber.phoneNumber.title"/></th>
		      <td><bean:write name="subscriber" property="phoneNumber"/></td>
		    </tr>
		    <tr>
		      <th><bean:message key="page.subscriber.carrier.title"/></th>
		      <td><bean:write name="subscriber" property="carrier.name"/></td>
		    </tr>
		    <tr>
		      <th><bean:message key="device.title.isActivated"/></th>
		      <td>
            <logic:equal name="subscriber" property="isActivated" value="false">
              <html:img page="/common/images/icons/deactive.gif" alt="${subscriber.isActivated}"></html:img>
            </logic:equal>
            <logic:equal name="subscriber" property="isActivated" value="true">
              <html:img page="/common/images/icons/active.gif" alt="${subscriber.isActivated}"></html:img>
            </logic:equal>
          </td>
		    </tr>
		    <tr>
		      <th><bean:message key="page.subscriber.externalId.title"/></th>
		      <td><bean:write name="subscriber" property="externalId"/></td>
		    </tr>
		    <tr>
		      <th><bean:message key="page.subscriber.imsi.title"/></th>
		      <td><bean:write name="subscriber" property="IMSI"/></td>
		    </tr>
        <tr>
          <th><bean:message key="page.subscriber.service_provider.title"/></th>
          <td>
            <logic:notEmpty name="subscriber" property="serviceProvider">
              <bean:write name="subscriber" property="serviceProvider.name"/>
            </logic:notEmpty>
          </td>
        </tr>
        <tr>
          <th><bean:message key="device.title.bootstrapPinType"/></th>
          <td>
            <logic:equal name="subscriber" property="bootstrapPinType" value="0"><bean:message key="meta.bootstrap.pintype.NETWPIN.label"/></logic:equal>
            <logic:equal name="subscriber" property="bootstrapPinType" value="1"><bean:message key="meta.bootstrap.pintype.USERPIN.label"/></logic:equal>
            <logic:equal name="subscriber" property="bootstrapPinType" value="2"><bean:message key="meta.bootstrap.pintype.USERNETWPIN.label"/></logic:equal>
            <logic:equal name="subscriber" property="bootstrapPinType" value="3"><bean:message key="meta.bootstrap.pintype.USERPINMAC.label"/></logic:equal>
          </td>
        </tr>
        <tr>
          <th><bean:message key="device.title.bootstrapUserPin"/></th>
          <td><bean:write name="subscriber" property="pin" /></td>
        </tr>
        <tr>
          <th><bean:message key="page.subscriber.firstname.title"/></th>
          <td><bean:write name="subscriber" property="firstname"/></td>
        </tr>
        <tr>
          <th><bean:message key="page.subscriber.lastname.title"/></th>
          <td><bean:write name="subscriber" property="lastname"/></td>
        </tr>
        <tr>
          <th><bean:message key="page.subscriber.email.title"/></th>
          <td><bean:write name="subscriber" property="email"/></td>
        </tr>
        <%--
		    <tr>
		      <th width="150"><bean:message key="page.subscriber.state.title"/></th>
		      <td><bean:write name="subscriber" property="state"/></td>
		    </tr>
         --%>
		  </tbody>
		</table>
  <div class="buttonArea">
  <logic:notEmpty name="SubscriberForm" property="ID">
    <html:submit styleClass="NormalWidthButton"><bean:message key="page.button.edit.label"/></html:submit>
  </logic:notEmpty>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <html:cancel styleClass="NormalWidthButton"><bean:message key="page.button.cancel.label"/></html:cancel>
  </div>
  </html:form>


<!-- Devices list -->
<logic:notEmpty name="devices">
	<!-- Space: height 20 -->
	<table cellpadding=0 cellspacing=0 border=0>
		<tr>
			<td height=20>
				<spacer type=block width=1 height=20>
			</td>
		</tr>
	</table>

	<div class="messageArea">
		<bean:message key="page.subscriber.message.devices" />
	</div>
	<display:table name="devices" id="device" pagesize="<%=BaseAction.getRecordsPerPage(request)%>" requestURI="<%=request.getContextPath() + "/Devices.do"%>" class="simple">
	  <display:column class="rowNum" 
	                  href="<%=request.getContextPath() + "/ViewDevice.do"%>" paramId="ID" paramProperty="ID">
	    <c:out value="${device_rowNum}"/>
	  </display:column>
	  <display:column property="externalId" sortable="true" titleKey="device.title.externalId" 
	                  href="<%=request.getContextPath() + "/ViewDevice.do"%>" paramId="ID" paramProperty="ID"/>
	  <display:column property="subscriber.phoneNumber" sortable="true" titleKey="device.title.phonenumber"/>
	  <display:column property="model.manufacturer.name" sortable="true" titleKey="device.title.manufacturer"/>
	  <display:column sortable="true" titleKey="device.title.model">
	    <html:link action="/ViewModel.do" styleClass="reference_link" target="_blank" paramId="ID" paramName="device" paramProperty="model.ID"><c:out value="${device.model.name}"/></html:link>
	  </display:column>
	  <display:column>
	    <a href="<html:rewrite page='/ViewDevice.do'/>?ID=<c:out value='${device.ID}'/>"><bean:message key="page.button.view.label"/></a> | 
	    <a href="<html:rewrite page='/EditDevice.do'/>?ID=<c:out value='${device.ID}'/>"><bean:message key="page.button.edit.label"/></a> | 
	    <a href="<html:rewrite page='/RemoveDevice.do'/>?method=remove&ID=<c:out value='${device.ID}'/>" onclick='javascript: return confirm("<bean:message key="message.delete.areYourSureDelete"/>");'><bean:message key="page.button.remove.label"/></a>
	    </display:column>
	</display:table>
</logic:notEmpty>