<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
 
<div class="messageArea">
  <bean:message key="page.message.required"/><bean:message key="page.message.input"/>
</div>

		<html:form action="/SaveCountry">
		<html:hidden property="action"/>
		<html:hidden property="ID"/>
		<table class="entityview">
  <thead>
      <tr>
        <th colspan="2">&nbsp;</th>
      </tr>
  </thead>
		  <tbody>
		    <tr>
		      <th width="150">* <bean:message key="country.title.ISOCode"/></th>
		      <td><html:text property="ISOCode" size="2" maxlength="2"/><div class="validateErrorMsg"><html:errors property="ISOCode"/></div></td>
		    </tr>
		    <tr>
		      <th>* <bean:message key="country.title.name"/></th>
		      <td><html:text property="name"/><div class="validateErrorMsg"><html:errors property="name"/></div></td>
		    </tr>
		    <tr>
		      <th>* <bean:message key="country.title.countryCode"/></th>
		      <td><html:text property="countryCode"/><div class="validateErrorMsg"><html:errors property="countryCode"/></div></td>
		    </TR>
		    <TR>
		      <th><bean:message key="country.title.displayCountryCode"/></th>
		      <td><html:checkbox property="displayCountryCode"/><div class="validateErrorMsg"><html:errors property="displayCountryCode"/></div></td>
		    </tr>
		    <tr>
		      <th><bean:message key="country.title.trunkCode"/></th>
		      <td><html:text property="trunkCode"/><div class="validateErrorMsg"><html:errors property="trunkCode"/></div></td>
		    </tr>
		    <tr>
		      <th><bean:message key="country.title.displayTrunkCode"/></th>
		      <td><html:checkbox property="displayTrunkCode"/><div class="validateErrorMsg"><html:errors property="displayTrunkCode"/></div></td>
		    </tr>
		    <tr>
		      <th><bean:message key="country.title.displayPrefix"/></th>
		      <td><html:checkbox property="displayPrefix"/><div class="validateErrorMsg"><html:errors property="displayPrefix"/></div></td>
		    </tr>
		  </tbody>
		</table>
  <div class="buttonArea">
	  <logic:empty name="CountryForm" property="ID">
	    <html:submit styleClass="NormalWidthButton"><bean:message key="page.button.create.label"/></html:submit>
	  </logic:empty>
	  <logic:notEmpty name="CountryForm" property="ID">
	    <html:submit styleClass="NormalWidthButton"><bean:message key="page.button.update.label"/></html:submit>
	  </logic:notEmpty>
	  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	  <html:reset styleClass="NormalWidthButton"><bean:message key="page.button.reset.label"/></html:reset>
	  <html:cancel styleClass="NormalWidthButton"><bean:message key="page.button.cancel.label"/></html:cancel>
	  <logic:notEmpty name="CountryForm" property="ID">
		    <html:button property="action" onclick="document.forms['CountryForm'][1].submit();" styleClass="NormalWidthButton">
		      <bean:message key="page.button.view.label"/>
		    </html:button>
	  </logic:notEmpty>
  </div>
</html:form>

<html:form action="/ViewCountry" method="post">
  <logic:notEmpty name="CountryForm" property="ID">
    <html:hidden property="ID"/>
  </logic:notEmpty>
</html:form>
