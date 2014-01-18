<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
 
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
		      <th width="200"><bean:message key="page.user.username.title"/></th>
		      <td><bean:write name="user" property="username"/></td>
		    </tr>
		    <tr>
		      <th><bean:message key="page.user.firstname.title"/></th>
		      <td><bean:write name="user" property="firstName"/></td>
		    </tr>
		    <tr>
		      <th><bean:message key="page.user.lastname.title"/></th>
		      <td><bean:write name="user" property="lastName"/></td>
		    </tr>
		    <tr>
		      <th><bean:message key="page.user.roles.title"/></th>
		      <td>
		        <logic:iterate id="role" name="user" property="roles">
  		        [ <bean:write name="role"/> ]<br>
		        </logic:iterate>
		      </td>
		    </tr>
		    <tr>
		      <th><bean:message key="page.user.manufacturers.title"/></th>
		      <td>
		        <logic:iterate id="manufacturer" name="user" property="ownedManufacturerExternalIDs">
		        [ <bean:write name="manufacturer"/> ]<br>
		        </logic:iterate>
		      </td>
		    </tr>
		    <tr>
		      <th><bean:message key="page.user.carriers.title"/></th>
		      <td>
		        <logic:iterate id="carrier" name="user" property="ownedCarrierExternalIDs">
		          [ <bean:write name="carrier"/> ]<br>
		        </logic:iterate>
		      </td>
		    </tr>
		    <tr>
		      <th><bean:message key="device.title.isActivated"/></th>
		      <td>
	          <html:img page="/common/images/icons/${user.active}.gif" alt="${user.active}"></html:img>
		      </td>
		    </tr>
		  </tbody>
		</table>
  <div class="buttonArea">
	  <logic:notEmpty name="UserForm" property="username">
		    <html:button property="action" onclick="document.forms['UserForm'][1].submit();" styleClass="NormalWidthButton">
		      <bean:message key="page.button.edit.label"/>
		    </html:button>
	  </logic:notEmpty>
	  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	  <html:cancel styleClass="NormalWidthButton"><bean:message key="page.button.cancel.label"/></html:cancel>
  </div>
</html:form>

<html:form action="/EditUser" method="post">
  <logic:notEmpty name="UserForm" property="username">
    <input type="hidden" name="method" value="update"/>
    <html:hidden property="username"/>
  </logic:notEmpty>
</html:form>
