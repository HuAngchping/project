<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>

<html:form action="/SaveProfileCategory">
<html:hidden property="action"/>
<html:hidden property="ID"/>
<table class="entityview">
  <thead>
    <tr>
      <th colspan="2"><bean:message key="page.attributetype.title.form.label"/></th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th width="150">* <bean:message key="profilecategory.title.name"/></th>
      <td><html:text property="name" size="48"/><div class="validateErrorMsg"><html:errors property="name"/></div></td>
    </tr>
    <tr>
      <th><bean:message key="profilecategory.title.description"/></th>
      <td><html:textarea property="description"/><div class="validateErrorMsg"><html:errors property="description"/></div></td>
    </tr>
  </tbody>
</table>
<div class="buttonArea">
  <logic:empty name="ProfileCategoryForm" property="ID">
    <html:submit styleClass="NormalWidthButton"><bean:message key="page.button.create.label"/></html:submit>
  </logic:empty>
  <logic:notEmpty name="ProfileCategoryForm" property="ID">
    <html:submit styleClass="NormalWidthButton"><bean:message key="page.button.update.label"/></html:submit>
  </logic:notEmpty>
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <html:reset styleClass="NormalWidthButton"><bean:message key="page.button.reset.label"/></html:reset>
  <html:cancel styleClass="NormalWidthButton"><bean:message key="page.button.cancel.label"/></html:cancel>
</div>
</html:form>
