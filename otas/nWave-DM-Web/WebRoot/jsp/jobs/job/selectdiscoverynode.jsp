
<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<script language="JavaScript1.2">
function onChangeRootNode(targetElementID, value) {
  targetElement = document.forms.JobsJobDiscoveryForm.elements[targetElementID]
  if (value == 'other') {
     targetElement.value = "";
  } else {
    targetElement.value = value;
  }
}
    
var bCancel = false;
function doSubmit(form) {
  if (bCancel) {
     return true;
  }
  /*
  if (form.nodeURI.value == "") {
     alert("<bean:message key="page.Jobs.selectDiscoveryNode.js.validate"/>");
     form.nodeURI.focus();
     return false;
  }
  */
  return true;
}
    
</script>
<div class="messageArea">
  <bean:message key="page.message.required"/><bean:message key="page.message.input"/>
</div>

<div class="messageArea">
  <div class="validateErrorMsg"><html:errors header="common.errors.header" footer="common.errors.footer" prefix="common.errors.prefix" suffix="common.errors.suffix"/></div>
</div>

<html:form action="/jobs/SaveDiscoveryJob" onsubmit="return doSubmit(this);">
	<table class="entityview">
      <thead>
        <tr>
          <th colspan="2"><font color="red"><b>.:</b></font> <bean:message key="page.device.job.discovery.nodeURI.message"/></th>
        </tr>
      </thead>
	  <tbody>
      <% 
      for (int i = 0; i < 10; i++) {
          pageContext.setAttribute("index4Node", new Integer(i + 1));
      %>
	    <tr>
	      <th><bean:message key="page.device.job.discovery.nodeURI.label"/> <bean:write name="index4Node"/></th>
	      <td>
            <input type="text" name="nodeURIs" size="48" id="nodeURI_${index4Node}"/>
            <select name="rootNode" ONCHANGE="onChangeRootNode('nodeURI_${index4Node}', this.value)">
                <option value=".">Entire Tree</option>
                <option value="./DevDetail">Device Detail Info</option>
                <option value="./SyncML/DSAcc">Data Synchronization Info</option>
                <option value="./SyncML/DMAcc">Device Management Info</option>
                <option value="./MMS">MMS Info</option>
                <option value="other" selected="selected">Other</option>
            </select>
        </td>
	    </tr>
      <% } %>
	  </tbody>
	</table>
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
