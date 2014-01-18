<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
 
<div class="messageArea">
  <bean:message key="page.message.required"/><bean:message key="page.message.input"/>
</div>

		<html:form action="/SaveUser">
		<html:hidden property="method"/>
        <logic:notEmpty name="UserForm" property="username">
		  <html:hidden property="username"/>
        </logic:notEmpty>
		<table class="entityview">
	      <thead>
	        <tr>
	          <th colspan="2">&nbsp;</th>
	        </tr>
	      </thead>
		  <tbody>
		    <tr>
		      <th width="200">* <bean:message key="page.user.username.title"/></th>
		      <td>
              <logic:empty name="UserForm" property="username">
		        <html:text property="username"/><div class="validateErrorMsg"><html:errors property="username"/></div>
              </logic:empty>
	          <logic:notEmpty name="UserForm" property="username">
			    <bean:write name="UserForm" property="username"/>
	          </logic:notEmpty>
		      </td>
		    </tr>
		    <tr>
		      <th>* <bean:message key="page.user.firstname.title"/></th>
		      <td><html:text property="firstName"/><div class="validateErrorMsg"><html:errors property="firstName"/></div></td>
		    </tr>
		    <tr>
		      <th>* <bean:message key="page.user.lastname.title"/></th>
		      <td><html:text property="lastName"/><div class="validateErrorMsg"><html:errors property="lastName"/></div></td>
		    </tr>
		    <tr>
		      <th>* <bean:message key="page.user.password.title"/></th>
		      <td><html:password property="password"/><div class="validateErrorMsg"><html:errors property="password"/></div></td>
		    </tr>
		    <tr>
		      <th>* <bean:message key="page.user.confirmPassword.title"/></th>
		      <td><html:password property="confirmPassword"/><div class="validateErrorMsg"><html:errors property="confirmPassword"/></div></td>
		    </tr>
		    <tr>
		      <th>* <bean:message key="page.user.roles.title"/></th>
		      <td>
		        <html:select property="formRoles" multiple="true" size="8" style="width: 300px">
		          <html:option value="dm.admin"><bean:message key="meta.role.dm.admin.label"/></html:option>
		          <html:option value="dm.admin.jobs">&nbsp;&nbsp;<bean:message key="meta.role.dm.admin.jobs.label"/></html:option>
		          <html:option value="dm.admin.devices">&nbsp;&nbsp;<bean:message key="meta.role.dm.admin.devices.label"/></html:option>
                  <html:option value="dm.operator.devices">&nbsp;&nbsp;<bean:message key="meta.role.dm.operator.devices.label"/></html:option>
		          <html:option value="dm.admin.profiles">&nbsp;&nbsp;<bean:message key="meta.role.dm.admin.profiles.label"/></html:option>
		          <html:option value="dm.admin.manufacturers">&nbsp;&nbsp;<bean:message key="meta.role.dm.admin.manufacturers.label"/></html:option>
		          <html:option value="dm.operator.manufacturer">&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="meta.role.dm.operator.manufacturer.label"/></html:option>
		          <html:option value="dm.admin.models">&nbsp;&nbsp;<bean:message key="meta.role.dm.admin.models.label"/></html:option>
		          <html:option value="dm.admin.carriers">&nbsp;&nbsp;<bean:message key="meta.role.dm.admin.carriers.label"/></html:option>
                  <html:option value="dm.admin.softwares">&nbsp;&nbsp;<bean:message key="meta.role.dm.admin.softwares.label"/></html:option>
		          <html:option value="dm.admin.security">&nbsp;&nbsp;<bean:message key="meta.role.dm.admin.security.label"/></html:option>
		          <html:option value="dm.admin.audit">&nbsp;&nbsp;<bean:message key="meta.role.dm.admin.audit.label"/></html:option>
		        </html:select>
                <div class="validateErrorMsg"><html:errors property="formRoles"/></div>
              </td>
		    </tr>
		    <tr>
		      <th><bean:message key="page.user.manufacturers.title"/></th>
		      <td>
		        <html:select property="formManufacturerExternalIDs" multiple="true" size="8" style="width: 300px">
		          <html:optionsCollection name="manufacturers"/>
		        </html:select>
                <div class="validateErrorMsg"><html:errors property="formManufacturerExternalIDs"/></div>
              </td>
		    </tr>
		    <tr>
		      <th><bean:message key="page.user.carriers.title"/></th>
		      <td>
		        <html:select property="formCarrierExternalIDs" multiple="true" size="8" style="width: 300px">
		          <html:optionsCollection name="carriers"/>
		        </html:select>
                <div class="validateErrorMsg"><html:errors property="formCarrierExternalIDs"/></div>
              </td>
		    </tr>
		    <tr>
		      <th><bean:message key="device.title.isActivated"/></th>
		      <td><html:checkbox property="active" value="true"/><div class="validateErrorMsg"><html:errors property="active"/></div></td>
		    </tr>
		  </tbody>
		</table>
  <div class="buttonArea">
	  <logic:empty name="UserForm" property="username">
	    <html:submit styleClass="NormalWidthButton"><bean:message key="page.button.create.label"/></html:submit>
	  </logic:empty>
	  <logic:notEmpty name="UserForm" property="username">
	    <html:submit styleClass="NormalWidthButton"><bean:message key="page.button.update.label"/></html:submit>
	  </logic:notEmpty>
	  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	  <html:reset styleClass="NormalWidthButton"><bean:message key="page.button.reset.label"/></html:reset>
	  <html:cancel styleClass="NormalWidthButton"><bean:message key="page.button.cancel.label"/></html:cancel>
	  <logic:notEmpty name="UserForm" property="username">
		    <html:button property="action" onclick="document.forms['UserForm'][1].submit();" styleClass="NormalWidthButton">
		      <bean:message key="page.button.view.label"/>
		    </html:button>
	  </logic:notEmpty>
  </div>
</html:form>

<html:form action="/EditUser" method="post">
  <logic:notEmpty name="UserForm" property="username">
    <input type="hidden" name="method" value="view"/>
    <html:hidden property="username"/>
  </logic:notEmpty>
</html:form>
