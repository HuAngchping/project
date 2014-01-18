<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://otas.cn/otasdm-taglib" prefix="otasdm"%>

<!-- Job Basic Information -->
<jsp:include flush="true" page="/jsp/device/job/view/basic_info.jsp"></jsp:include>

<div class="buttonArea" style="height: 40px;">
  <html:form action="/device/Jobs" method="post">
   <INPUT type="hidden" name="deviceID" value="<bean:write name="device" property="ID"/>">
   <html:submit><bean:message key="page.device.job.success.button.jobs.label"/></html:submit>
  </html:form>
  <html:form action="/ViewDevice" method="post">
   <INPUT type="hidden" name="method" value="view">
   <INPUT type="hidden" name="ID" value="<bean:write name="device" property="ID"/>">
   <html:submit><bean:message key="page.device.button.backToDeviceView.label"/></html:submit>
  </html:form>
</div>
