
<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<SCRIPT type="text/javascript">
var xmlHttp;
    
function getModels(manufacturer) {
  createXMLHttpRequest()
  var url = "<html:rewrite page='/ajax.do'/>?manufacturerID=" + manufacturer + "&method=getModels";
  xmlHttp.open("GET", uncache(url), true);
  xmlHttp.onreadystatechange = parseModels;
  xmlHttp.send(null);
}

function parseModels() {
  if (xmlHttp.readyState == 4) {
     if (xmlHttp.status == 200) {
        resText = xmlHttp.responseText
        //alert(resText);
        each = resText.split("|")
        buildSelect(each, document.getElementById("modelID"), "<bean:message key="page.device.choice.model"/>");
     }
  }
}

function buildSelect(str, sel, label) {
  sel.options.length = 0;
  sel.options[sel.options.length] = new Option(label, "")
  for (var i = 0; i < str.length; i++) {
      if (str[i].length == 0) {
         continue;
      }
      each = str[i].split(",")
      sel.options[sel.options.length] = new Option(each[0],each[1])
  }
}

</SCRIPT>

<logic:notEmpty name="DeviceForm" property="ID">
  	<div class="buttonArea" style="height: 40px;">
	  <bean:define id="deviceID" name="DeviceForm" property="ID"></bean:define>
	  <html:form action="/device/DeviceTree" method="post">
		  <INPUT type="hidden" name="deviceID" value="<bean:write name="deviceID"/>">
	      <html:submit><bean:message key="page.device.button.viewDeviceTree"/></html:submit>
	  </html:form>
	  <html:form action="/device/EditJob" method="post">
		  <INPUT type="hidden" name="deviceID" value="<bean:write name="deviceID"/>">
		  <html:submit><bean:message key="page.device.button.createJob"/></html:submit>
	  </html:form>
	  <html:form action="/device/Jobs" method="post">
		  <INPUT type="hidden" name="deviceID" value="<bean:write name="deviceID"/>">
		  <html:submit><bean:message key="page.device.button.viewJobs"/></html:submit>
	  </html:form>
	  <html:form action="/device/ProfileAssignments" method="post">
		  <INPUT type="hidden" name="deviceID" value="<bean:write name="deviceID"/>">
		  <html:submit><bean:message key="page.device.button.viewProfiles"/></html:submit>
	  </html:form>
	  <html:form action="/EditSubscriber" method="post">
		  <INPUT type="hidden" name="ID" value="<bean:write name="device" property="subscriber.ID"/>">
		  <INPUT type="hidden" name="method" value="view">
		  <html:submit><bean:message key="page.device.button.viewSubscriber"/></html:submit>
	  </html:form>
	  <html:form action="/device/bootstrap" method="post">
		  <INPUT type="hidden" name="deviceID" value="<bean:write name="deviceID"/>">
		  <INPUT type="hidden" name="method" value="input">
		  <html:submit><bean:message key="page.device.button.bootstrap"/></html:submit>
	  </html:form>
	  <html:form action="/device/notification/input" method="post">
		  <INPUT type="hidden" name="deviceID" value="<bean:write name="device" property="ID"/>">
		  <html:submit><bean:message key="page.device.job.success.button.notification.label"/></html:submit>
	  </html:form>
	  <html:form action="/device/history" method="post">
		  <INPUT type="hidden" name="deviceID" value="<bean:write name="deviceID"/>">
		  <html:submit><bean:message key="page.device.button.history"/></html:submit>
	  </html:form>
	</div>
</logic:notEmpty>

<div class="messageArea">
  <bean:message key="page.message.required"/><bean:message key="page.message.input"/>
  <logic:messagesPresent message="false">
    <div class="validateErrorMsg"><html:errors suffix="device.error"/></div>
  </logic:messagesPresent>
</div>

<html:form action="/SaveDevice" method="post">
	<html:hidden property="ID"/>
	<table class="entityview">
	  <tbody>
	    <tr>
	      <th width="150">* <bean:message key="device.title.externalId"/></th>
	      <td><html:text property="externalId" size="24"/><div class="validateErrorMsg"><html:errors property="externalId"/></div></td>
	    </tr>
	    <tr>
	      <th>* <bean:message key="device.title.phonenumber"/></th>
	      <td><html:text property="subscriberPhoneNumber" size="24"/><div class="validateErrorMsg"><html:errors property="subscriberPhoneNumber"/></div></td>
	    </tr>
	    <tr>
	      <th>* <bean:message key="device.title.carrier"/></th>
	      <td>
	        <html:select property="carrierID">
	          <html:option value=""><bean:message key="page.device.choice.carrier"/></html:option>
	          <html:optionsCollection name="carrierOptions"/>
	        </html:select>
            <div class="validateErrorMsg"><html:errors property="carrierID"/></div>
          </td>
	    </tr>
	    <tr>
	      <th><bean:message key="device.title.manufacturer"/></th>
	      <td>
	        <html:select property="manufacturerID" onchange="getModels(this.value)">
	          <html:option value=""><bean:message key="page.device.choice.manufacturer"/></html:option>
	          <html:optionsCollection name="manufacturerOptions"/>
	        </html:select>
	      <div class="validateErrorMsg"><html:errors property="manufacturerID"/></div></td>
	    </tr>
	    <tr>
	      <th><bean:message key="device.title.model"/></th>
	      <td>
	        <html:select property="modelID" styleId="modelID">
	          <html:option value=""><bean:message key="page.device.choice.model"/></html:option>
	          <html:optionsCollection name="modelOptions"/>
	        </html:select>
          <div class="validateErrorMsg"><html:errors property="modelID"/></div></td>
	    </tr>
	    <tr>
	      <th><bean:message key="device.title.isActivated"/></th>
	      <td><html:checkbox property="isActivated" value="true"/><div class="validateErrorMsg"><html:errors property="isActivated"/></div></td>
	    </tr>
	    <tr>
	      <th>* <bean:message key="device.title.OMAClientAuthScheme"/></th>
	      <td>
	        <html:select property="OMAClientAuthScheme">
	          <html:option value=""><bean:message key="page.select.option.default.label"/></html:option>
	          <html:optionsCollection name="authSchemas"/>
	        </html:select>
	        <div class="validateErrorMsg"><html:errors property="OMAClientAuthScheme"/></div>
	      </td>
	    </tr>
	    <tr>
	      <th>* <bean:message key="device.title.OMAServerPassword"/></th>
	      <td><html:text property="OMAServerPassword"/><div class="validateErrorMsg"><html:errors property="OMAServerPassword"/></div></td>
	    </tr>
	    <tr>
	      <th>* <bean:message key="device.title.OMAClientUsername"/></th>
	      <td><html:text property="OMAClientUsername"/><div class="validateErrorMsg"><html:errors property="OMAClientUsername"/></div></td>
	    </tr>
	    <tr>
	      <th>* <bean:message key="device.title.OMAClientPassword"/></th>
	      <td><html:text property="OMAClientPassword"/><div class="validateErrorMsg"><html:errors property="OMAClientPassword"/></div></td>
	    </tr>
	    <tr>
	      <th><bean:message key="device.title.OMAServerNonce"/></th>
	      <td><html:text property="OMAServerNonce"/><div class="validateErrorMsg"><html:errors property="OMAServerNonce"/></div></td>
	    </tr>
	    <tr>
	      <th><bean:message key="device.title.OMAClientNonce"/></th>
	      <td><html:text property="OMAClientNonce"/><div class="validateErrorMsg"><html:errors property="OMAClientNonce"/></div></td>
	    </tr>
      <tr>
        <th style="white-space: nowrap">
          <bean:message key="device.title.bootstrapPinType" />
        </th>
        <td>
          <html:select property="bootstrapPinType">
            <html:option value=""><bean:message key="page.select.option.default.label"/></html:option>
            <html:option value="0"><bean:message key="meta.bootstrap.pintype.NETWPIN.label"/></html:option>
            <html:option value="1"><bean:message key="meta.bootstrap.pintype.USERPIN.label"/></html:option>
            <html:option value="2"><bean:message key="meta.bootstrap.pintype.USERNETWPIN.label"/></html:option>
            <html:option value="3"><bean:message key="meta.bootstrap.pintype.USERPINMAC.label"/></html:option>
          </html:select>
          <div class="validateErrorMsg">
            <html:errors property="bootstrapPinType" />
          </div>
        </td>
      </tr>
      <tr>
        <th style="white-space: nowrap">
          <bean:message key="device.title.bootstrapUserPin" />
        </th>
        <td>
          <html:text property="bootstrapUserPin" />
          <div class="validateErrorMsg">
            <html:errors property="bootstrapUserPin" />
          </div>
        </td>
      </tr>
	    <tr>
	      <th><bean:message key="device.title.description"/></th>
	      <td><html:textarea property="description"/><div class="validateErrorMsg"><html:errors property="description"/></div></td>
	    </tr>
	  </tbody>
	</table>
	<div class="buttonArea">
	  <logic:empty name="DeviceForm" property="ID">
	    <html:submit property="action" styleClass="NormalWidthButton"><bean:message key="page.button.create.label"/></html:submit>
	  </logic:empty>
	  <logic:notEmpty name="DeviceForm" property="ID">
	    <html:submit property="action" styleClass="NormalWidthButton"><bean:message key="page.button.update.label"/></html:submit>
	  </logic:notEmpty>
	  <html:reset styleClass="NormalWidthButton"><bean:message key="page.button.reset.label"/></html:reset>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	  <logic:empty name="DeviceForm" property="ID">
	    <html:cancel><bean:message key="page.device.button.backToDeviceList.label"/></html:cancel>
	  </logic:empty>
	  <logic:notEmpty name="DeviceForm" property="ID">
	    <html:button property="action" onclick="document.forms['DeviceForm'][1].submit();"><bean:message key="page.device.button.backToDeviceView.label"/></html:button>
	  </logic:notEmpty>
	</div>
</html:form>
<html:form action="/ViewDevice" method="post">
  <logic:notEmpty name="DeviceForm" property="ID">
    <INPUT type="hidden" name="ID" value="<bean:write name="device" property="ID"/>">
  </logic:notEmpty>
</html:form>
