<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
 
<html:form action="/SaveManufacturer">
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
      <th width="150">* <bean:message key="manufacturer.title.name"/></th>
      <td><bean:write name="manufacturer" property="name"/></td>
    </tr>
    <tr>
      <th><bean:message key="manufacturer.title.externalId"/></th>
      <td><bean:write name="manufacturer" property="externalId"/></td>
    </tr>
    <tr>
      <th><bean:message key="manufacturer.title.description"/></th>
      <td><bean:write name="manufacturer" property="description"/></td>
    </tr>
  </tbody>
</table>
<div class="buttonArea">
  <logic:notEmpty name="ManufacturerForm" property="ID">
	    <html:button property="action" onclick="document.forms['ManufacturerForm'][1].submit();" styleClass="NormalWidthButton">
	      <bean:message key="page.button.edit.label"/>
	    </html:button>
  </logic:notEmpty>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <html:cancel styleClass="NormalWidthButton"><bean:message key="page.button.cancel.label"/></html:cancel>
</div>
</html:form>
<html:form action="/EditManufacturer" method="post">
  <logic:notEmpty name="ManufacturerForm" property="ID">
    <input type="hidden" name="action" value="update"/>
    <html:hidden property="ID"/>
  </logic:notEmpty>
</html:form>
