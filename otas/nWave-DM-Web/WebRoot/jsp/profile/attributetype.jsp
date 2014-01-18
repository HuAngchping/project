<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<div class="messageArea">
  <bean:message key="page.message.required"/><bean:message key="page.message.input"/>
</div>

<html:form action="/SaveProfileAttributeType">
<html:hidden property="ID"/>
<table class="entityview">
  <thead>
    <tr>
      <th colspan="2"><bean:message key="page.attributetype.title.form.label"/></th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>* <bean:message key="attributetype.title.name"/></th>
      <td><html:text property="name" size="48"/><div class="validateErrorMsg"><html:errors property="name"/></div></td>
    </tr>
    <tr>
      <th><bean:message key="attributetype.title.description"/></th>
      <td><html:textarea property="description"/><div class="validateErrorMsg"><html:errors property="description"/></div></td>
    </tr>
  </tbody>
</table>
<div class="buttonArea">
  <logic:empty name="ProfileAttributeTypeForm" property="ID">
    <html:submit property="action" styleClass="NormalWidthButton"><bean:message key="page.attributetype.button.createAttributeType"/></html:submit>
  </logic:empty>
  <logic:notEmpty name="ProfileAttributeTypeForm" property="ID">
    <html:submit property="action" styleClass="NormalWidthButton"><bean:message key="page.attributetype.button.updateAttributeType"/></html:submit>
  </logic:notEmpty>
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <html:reset styleClass="NormalWidthButton"><bean:message key="page.attributetype.button.reset"/></html:reset>
  <html:cancel styleClass="NormalWidthButton"><bean:message key="page.attributetype.button.cancel"/></html:cancel>
</div>
</html:form>

<!--  Values management  -->
<logic:notEmpty name="ProfileAttributeTypeForm" property="ID">

<!-- JS: Local functions  -->
<script type="text/javascript" language="javascript">
ValueFormName = "ProfileAttributeTypeValueForm";
function doAddValue() {
  form = document.forms[ValueFormName];
  nv = form.elements["value"].value;
  if (nv == null || nv.length == 0) {
     alert("<bean:message key="page.attributetype.alert.inputvalue"/>");
     form.elements["value"].focus();
     return false;
  }
  return true;
}
</script>
    <!-- Space: height 20 -->
    <table cellpadding=0 cellspacing=0 border=0><tr><td height=20><spacer type=block width=1 height=20></td></tr></table>

	<div class="messageArea">
	  <bean:message key="page.attributetype.message.valuelist"/>
	</div>

	<html:form action="/SaveProfileAttributeTypeValue" onsubmit="return doAddValue();">
	<html:hidden name="ProfileAttributeTypeForm" property="ID"/>
	<bean:define id="attributeTypeID" name="ProfileAttributeTypeForm" property="ID"></bean:define>
	<display:table name="attribTypeValues" id="value" class="simple">
	    <display:column class="rowNum">
	      <c:out value="${value_rowNum}"/>
	    </display:column>
		<display:column property="value" titleKey="attributetype.title.value" />
	    <display:column>
	      <a href="<html:rewrite page='/SaveProfileAttributeTypeValue.do'/>?action=<bean:message key="page.attributetype.button.removeAttributeTypeValue"/>&ID=<c:out value='${attributeTypeID}'/>&valueID=<c:out value='${value.ID}'/>" onclick='javascript: return confirm("<bean:message key="message.delete.areYourSureDelete"/>");'><bean:message key="page.attributetype.button.removeAttributeTypeValue"/></a>
	    </display:column>
	</display:table>
	<div class="buttonArea">
	  <INPUT type="text" name="value"> <html:submit property="action"><bean:message key="page.attributetype.button.createAttributeTypeValue"/></html:submit>
	</div>
	</html:form>
</logic:notEmpty>


