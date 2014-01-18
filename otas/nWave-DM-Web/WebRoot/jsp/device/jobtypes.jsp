<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<div class="messageArea">
  <bean:message key="page.message.required"/><bean:message key="page.message.input"/>
  <logic:messagesPresent message="false">
    <div class="validateErrorMsg"><html:errors suffix="device.error"/></div>
  </logic:messagesPresent>
</div>

<html:form action="/device/EditJob">
	<html:hidden property="deviceID"/>
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
  
  <table class=table_box cellSpacing=0 cellPadding=0 width="100%" border="0">
    <tbody>
    <tr>
      <td class=table_box_heading width="100%" colSpan=5><font color="red"><b>.:</b></font> <bean:message key="page.device.job.selectType.message"/></TD>
    </tr>
    <tr>
      <td>
        <display:table name="jobTypes" id="jobType" class="simple_inline">
          <display:column class="rowNum" 
                          href="./device/EditJob.do?deviceID=${device.ID}" paramId="jobType" paramProperty="id">
            <c:out value="${jobType_rowNum}"/>
          </display:column>
          <display:column>
            <html:radio property="jobType" value="${jobType.id}"></html:radio>
          </display:column>
          <display:column property="name" titleKey="device.job.jobType"
                          href="./device/EditJob.do?deviceID=${device.ID}" paramId="jobType" paramProperty="id"/>
          <display:column property="mode" titleKey="device.job.jobType.mode.label"
                          href="./device/EditJob.do?deviceID=${device.ID}" paramId="jobType" paramProperty="id"/>
          <display:column property="description" titleKey="device.job.jobType.description"
                          href="./device/EditJob.do?deviceID=${device.ID}" paramId="jobType" paramProperty="id"/>
        </display:table>  
      </td>
    </tr>
    </tbody>
  </table>

	<div class="buttonArea">
      <html:submit styleClass="NormalWidthButton"><bean:message key="page.button.next.label"/></html:submit>
	  <html:reset styleClass="NormalWidthButton"><bean:message key="page.button.reset.label"/></html:reset>
	  <html:cancel styleClass="NormalWidthButton"><bean:message key="page.button.cancel.label"/></html:cancel>
	</div>
</html:form>
