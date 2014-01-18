<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
 
<html:form action="/SaveCountry">
	<input type="hidden" name="action" value="update"/>
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
		      <td><bean:write name="country" property="ISOCode" /></td>
		    </tr>
		    <tr>
		      <th><bean:message key="country.title.name"/></th>
		      <td><bean:write name="country" property="name" /></td>
		    </tr>
		    <tr>
		      <th><bean:message key="country.title.countryCode"/></th>
		      <td><bean:write name="country" property="countryCode" /></td>
		    </TR>
		    <TR>
		      <th><bean:message key="country.title.displayCountryCode"/></th>
		      <td>
					<html:img page="/common/images/icons/${country.displayCountryCode}.gif" alt="${country.displayCountryCode}"></html:img>
		      </td>
		    </tr>
		    <tr>
		      <th><bean:message key="country.title.trunkCode"/></th>
		      <td><bean:write name="country" property="trunkCode" /></td>
		    </tr>
		    <tr>
		      <th><bean:message key="country.title.displayTrunkCode"/></th>
		      <td>
					  <html:img page="/common/images/icons/${country.displayTrunkCode}.gif" alt="${country.displayTrunkCode}"></html:img>
		      </td>
		    </tr>
		    <tr>
		      <th><bean:message key="country.title.displayPrefix"/></th>
		      <td>
					  <html:img page="/common/images/icons/${country.displayPrefix}.gif" alt="${country.displayPrefix}"></html:img>
		      </td>
		    </tr>
		  </tbody>
		</table>
  <div class="buttonArea">
	  <logic:notEmpty name="CountryForm" property="ID">
		    <html:button property="action" onclick="document.forms['CountryForm'][1].submit();" styleClass="NormalWidthButton">
		      <bean:message key="page.button.edit.label"/>
		    </html:button>
	  </logic:notEmpty>
	  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	  <html:cancel styleClass="NormalWidthButton"><bean:message key="page.button.cancel.label"/></html:cancel>
  </div>
</html:form>

<html:form action="/EditCountry" method="post">
  <logic:notEmpty name="CountryForm" property="ID">
	  <input type="hidden" name="action" value="update"/>
    <html:hidden property="ID"/>
  </logic:notEmpty>
</html:form>
