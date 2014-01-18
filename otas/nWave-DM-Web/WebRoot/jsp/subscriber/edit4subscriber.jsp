<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
 
<div class="messageArea">
  <bean:message key="page.message.required"/><bean:message key="page.message.input"/>
</div>

		<html:form action="/SaveSubscriber">
		<html:hidden property="method"/>
		<html:hidden property="ID"/>
		<table class="entityview">
	      <thead>
	        <tr>
	          <th colspan="2">&nbsp;</th>
	        </tr>
	      </thead>
		  <tbody>
		    <tr>
		      <th>* <bean:message key="page.subscriber.phoneNumber.title"/></th>
		      <td><html:text property="phoneNumber"/><div class="validateErrorMsg"><html:errors property="phoneNumber"/></div></td>
		    </tr>
		    <tr>
		      <th>* <bean:message key="page.subscriber.carrier.title"/></th>
		      <td>
		        <html:select property="carrierID">
		          <html:option value=""><bean:message key="page.device.choice.carrier"/></html:option>
		          <html:optionsCollection name="carrierOptions"/>
		        </html:select>
	            <div class="validateErrorMsg"><html:errors property="carrierID"/></div>
		      </td>
		    </tr>
        <tr>
          <th><bean:message key="page.subscriber.service_provider.title"/></th>
          <td>
            <html:select property="serviceProviderID">
              <html:option value=""><bean:message key="page.device.choice.serviceProvider"/></html:option>
              <html:optionsCollection name="serviceProviderOptions"/>
            </html:select>
              <div class="validateErrorMsg"><html:errors property="serviceProviderID"/></div>
          </td>
        </tr>
		    <tr>
		      <th><bean:message key="device.title.isActivated"/></th>
		      <td><html:checkbox property="isActivated" value="true"/><div class="validateErrorMsg"><html:errors property="isActivated"/></div></td>
		    </tr>
		    <tr>
		      <th width="150">* <bean:message key="page.subscriber.externalId.title"/></th>
		      <td><html:text property="externalId"/><div class="validateErrorMsg"><html:errors property="externalId"/></div></td>
		    </tr>
		    <tr>
		      <th width="150">* <bean:message key="page.subscriber.password.title"/></th>
		      <td><html:text property="password"/><div class="validateErrorMsg"><html:errors property="password"/></div></td>
		    </tr>
		    <tr>
		      <th width="150"><bean:message key="page.subscriber.imsi.title"/></th>
		      <td><html:text property="IMSI"/><div class="validateErrorMsg"><html:errors property="IMSI"/></div><bean:message key="page.subscriber.imsi.hint"/></td>
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
            <html:text property="pin" />
            <div class="validateErrorMsg">
              <html:errors property="pin" />
            </div>
          </td>
        </tr>
        <tr>
          <th><bean:message key="page.subscriber.firstname.title"/></th>
          <td><html:text property="firstname"/><div class="validateErrorMsg"><html:errors property="firstname"/></div></td>
        </tr>
        <tr>
          <th><bean:message key="page.subscriber.lastname.title"/></th>
          <td><html:text property="lastname"/><div class="validateErrorMsg"><html:errors property="lastname"/></div></td>
        </tr>
        <tr>
          <th><bean:message key="page.subscriber.email.title"/></th>
          <td><html:text property="email"/><div class="validateErrorMsg"><html:errors property="email"/></div></td>
        </tr>
        <%--
		    <tr>
		      <th width="150"><bean:message key="page.subscriber.state.title"/></th>
		      <td><html:text property="state"/><div class="validateErrorMsg"><html:errors property="state"/></div></td>
		    </tr>
        --%>
		  </tbody>
		</table>
  <div class="buttonArea">
	  <logic:empty name="SubscriberForm" property="ID">
	    <html:submit styleClass="NormalWidthButton"><bean:message key="page.button.create.label"/></html:submit>
	  </logic:empty>
	  <logic:notEmpty name="SubscriberForm" property="ID">
	    <html:submit styleClass="NormalWidthButton"><bean:message key="page.button.update.label"/></html:submit>
	  </logic:notEmpty>
	  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	  <html:reset styleClass="NormalWidthButton"><bean:message key="page.button.reset.label"/></html:reset>
	  <html:cancel styleClass="NormalWidthButton"><bean:message key="page.button.cancel.label"/></html:cancel>
	  <logic:notEmpty name="SubscriberForm" property="ID">
		    <html:button property="action" onclick="document.forms['SubscriberForm'][1].submit();" styleClass="NormalWidthButton">
		      <bean:message key="page.button.view.label"/>
		    </html:button>
	  </logic:notEmpty>
  </div>
</html:form>

<html:form action="/EditSubscriber" method="post">
  <logic:notEmpty name="SubscriberForm" property="ID">
    <input type="hidden" name="method" value="view"/>
    <html:hidden property="ID"/>
  </logic:notEmpty>
</html:form>
