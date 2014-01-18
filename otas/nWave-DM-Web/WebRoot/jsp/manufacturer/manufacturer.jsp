<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
 
<div class="messageArea">
  <bean:message key="page.message.required"/><bean:message key="page.message.input"/>
</div>

<html:form action="/SaveManufacturer">
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
      <th width="150">* <bean:message key="manufacturer.title.name"/></th>
      <td><html:text property="name" size="48"/><div class="validateErrorMsg"><html:errors property="name"/></div></td>
    </tr>
    <tr>
      <th><bean:message key="manufacturer.title.externalId"/></th>
      <td><html:text property="externalId" size="48"/><div class="validateErrorMsg"><html:errors property="externalId"/></div></td>
    </tr>
    <tr>
      <th><bean:message key="manufacturer.title.description"/></th>
      <td><html:textarea property="description"/><div class="validateErrorMsg"><html:errors property="description"/></div></td>
    </tr>
  </tbody>
</table>
<div class="buttonArea">
  <logic:empty name="ManufacturerForm" property="ID">
    <html:submit styleClass="NormalWidthButton"><bean:message key="page.button.create.label"/></html:submit>
  </logic:empty>
  <logic:notEmpty name="ManufacturerForm" property="ID">
    <html:submit styleClass="NormalWidthButton"><bean:message key="page.button.update.label"/></html:submit>
  </logic:notEmpty>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <html:reset styleClass="NormalWidthButton"><bean:message key="page.button.reset.label"/></html:reset>
  <html:cancel styleClass="NormalWidthButton"><bean:message key="page.button.cancel.label"/></html:cancel>
  <logic:notEmpty name="ManufacturerForm" property="ID">
	    <html:button property="action" onclick="document.forms['ManufacturerForm'][1].submit();" styleClass="NormalWidthButton">
	      <bean:message key="page.button.view.label"/>
	    </html:button>
  </logic:notEmpty>
</div>
</html:form>
<html:form action="/ViewManufacturer" method="post">
  <logic:notEmpty name="ManufacturerForm" property="ID">
    <html:hidden property="ID"/>
  </logic:notEmpty>
</html:form>
