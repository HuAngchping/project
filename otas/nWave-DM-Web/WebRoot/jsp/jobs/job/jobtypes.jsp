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
  <bean:message key="page.Jobs.jobtypes.message" /><br><br>
  <bean:message key="page.message.required"/><bean:message key="page.message.input"/>
  <div class="validateErrorMsg"><html:errors header="common.errors.header" footer="common.errors.footer" prefix="common.errors.prefix" suffix="common.errors.suffix"/></div>
</div>

<html:form action="/jobs/SelectJobType">
  <!-- Job Type List -->
  <table class=table_box cellSpacing=0 cellPadding=0 width="100%" border="0">
    <tbody>
    <tr>
      <td class=table_box_heading width="100%" colSpan=5><font color="red"><b>.:</b></font> <bean:message key="page.device.job.selectType.message"/></TD>
    </tr>
    <tr>
      <td>
        <display:table name="jobTypes" id="jobType" class="simple_inline">
          <display:column class="rowNum" 
                          href="./jobs/EditJobType.do" paramId="jobType" paramProperty="id">
            <c:out value="${jobType_rowNum}"/>
          </display:column>
          <display:column>
            <html:radio property="jobType" value="${jobType.id}"></html:radio>
          </display:column>
          <display:column property="name" titleKey="device.job.jobType"
                          href="./jobs/EditJobType.do" paramId="jobType" paramProperty="id"/>
          <display:column property="mode" titleKey="device.job.jobType.mode.label"
                          href="./jobs/EditJobType.do" paramId="jobType" paramProperty="id"/>
          <display:column property="description" titleKey="device.job.jobType.description"
                          href="./jobs/EditJobType.do" paramId="jobType" paramProperty="id"/>
        </display:table>  
      </td>
    </tr>
    </tbody>
  </table>

	<div class="buttonArea">
    <html:submit property="method" styleClass="NormalWidthButton"><bean:message key="page.button.next.label"/></html:submit>
	  <html:reset styleClass="NormalWidthButton"><bean:message key="page.button.reset.label"/></html:reset>
	  <html:cancel styleClass="NormalWidthButton"><bean:message key="page.button.cancel.label"/></html:cancel>
	</div>
</html:form>
