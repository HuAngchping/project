<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://otas.cn/otasdm-taglib" prefix="otasdm"%>

<!-- Button Panel -->
<div class="buttonArea" style="height: 40px;">
  <html:form action="/jobs/Job" method="post">
    <html:cancel><bean:message key="page.jobs.button.backJobsList.label"/></html:cancel>
  </html:form>
  <html:form action="/jobs/Job" method="post">
    <input type="hidden" name="action" value="removeJob"/>
    <INPUT type="hidden" name="jobID" value="<bean:write name="provisionJob" property="ID"/>">
    <html:submit styleClass="NormalWidthButton"><bean:message key="page.button.remove.label"/></html:submit>
  </html:form>
  <c:if test="${provisionJob.state=='Disable' || provisionJob.state=='Cancelled'}">
  <html:form action="/jobs/Job" method="post">
    <input type="hidden" name="action" value="enableJob"/>
    <INPUT type="hidden" name="jobID" value="<bean:write name="provisionJob" property="ID"/>">
    <html:submit styleClass="NormalWidthButton"><bean:message key="page.button.enable.label"/></html:submit>
  </html:form>
  </c:if>
  <c:if test="${provisionJob.state=='Applied'}">
  <html:form action="/jobs/Job" method="post">
    <input type="hidden" name="action" value="disableJob"/>
    <INPUT type="hidden" name="jobID" value="<bean:write name="provisionJob" property="ID"/>">
    <html:submit styleClass="NormalWidthButton"><bean:message key="page.button.disable.label"/></html:submit>
  </html:form>
  </c:if>
</div>
