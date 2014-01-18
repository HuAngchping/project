
<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<script language="JavaScript1.2">
function onChangeRootNode(targetElementID, value) {
  targetElement = document.forms.DeviceJobDiscoveryForm.elements[targetElementID]
  if (value == 'other') {
      targetElement.value = "";
  } else {
      targetElement.value = value;
  }
}
</script>

<div class="messageArea">
  <bean:message key="page.message.required"/><bean:message key="page.message.input"/>
  <div class="validateErrorMsg"><html:errors header="common.errors.header" footer="common.errors.footer" prefix="common.errors.prefix" suffix="common.errors.suffix"/></div>
</div>

<html:form action="/device/SaveDiscoveryJob" focus="action">
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
	    <tr>
	      <th>* <bean:message key="page.device.job.discovery.nodeURI.label"/></th>
	      <td>
          <div class="validateErrorMsg"><html:errors property="nodeURI"/></div>
          <% 
          for (int i = 0; i < 10; i++) {
              pageContext.setAttribute("index4Node", new Integer(i + 1));
          %>
                <input type="text" name="nodeURIs" size="48" id="nodeURI_${index4Node}"/>
                <select name="rootNode" ONCHANGE="onChangeRootNode('nodeURI_${index4Node}', this.value)">
                    <option value=".">Entire Tree</option>
                    <option value="./DevDetail">Device Detail Info</option>
                    <option value="./SyncML/DSAcc">Data Synchronization Info</option>
                    <option value="./SyncML/DMAcc">Device Management Info</option>
                    <option value="./MMS">MMS Info</option>
                    <option value="other" selected="selected">Other</option>
                </select>
                <br/>
          <% } %>
        </td>
	    </tr>
	  </tbody>
	</table>
	<div class="buttonArea">
      <html:submit property="action" styleClass="NormalWidthButton"><bean:message key="page.button.next.label"/></html:submit>
	  <html:cancel styleClass="NormalWidthButton"><bean:message key="page.button.cancel.label"/></html:cancel>
	</div>
</html:form>
