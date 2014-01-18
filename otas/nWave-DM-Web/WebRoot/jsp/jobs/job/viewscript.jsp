
<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %>
<%@ taglib uri="http://otas.cn/otasdm-taglib" prefix="otasdm"%>

<html:form action="/jobs/SaveScirpt">
    <html:hidden property="commandScript"/>
	<table class="entityview">
	  <tbody>
	    <tr>
	      <th><bean:message key="page.Jobs.jobtypes.totalTargetDevices.label"/></th>
	      <td>
            <c:out value="${targetDevices.fullListSize}"/>
          </td>
	    </tr>
	    <tr>
	      <th><bean:message key="page.device.job.script.label.checkStatus"/></th>
	      <td>
	      <c:if test="${compileVerify}">
	        <bean:message key="page.device.job.script.label.passed"/>
	      </c:if>
	      <c:if test="${not compileVerify}">
	        <bean:message key="page.device.job.script.label.failure"/>
	      </c:if>
	      </td>
	    </tr>
	    <html:messages id="checkErrorMessage" message="true">
	    <tr>
	      <th style="color: red"><bean:message key="page.device.job.script.label.checkErrorMessage"/></th>
	      <td>
	        <div class="validateErrorMsg" style="color: red">
	        <bean:write name="checkErrorMessage"/>
	        </div>
	      </td>
	    </tr>
	    </html:messages>
	    <tr>
	      <th><bean:message key="page.device.job.script.label"/></th>
	      <td>
            <bean:define id="scriptText" name="JobsJobScriptForm" property="commandScript"></bean:define>
	        <textarea class="view_script" readonly="readonly"><otasdm:xmlPretty content="${scriptText}"/></textarea>
          </td>
	    </tr>
	  </tbody>
	</table>
	<div class="buttonArea">
	  <c:if test="${compileVerify}">
        <html:submit property="action" styleClass="NormalWidthButton"><bean:message key="page.button.next.label"/></html:submit>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	  </c:if>
      <html:submit property="action" styleClass="NormalWidthButton"><bean:message key="page.button.modify.label"/></html:submit>
	  <html:cancel styleClass="NormalWidthButton"><bean:message key="page.button.cancel.label"/></html:cancel>
	</div>
</html:form>
