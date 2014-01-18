
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

<div class="messageArea">
  <bean:message key="page.createProfileMapping.message" /><br><br>
  <bean:message key="page.message.required"/><bean:message key="page.message.input"/>
  <div class="validateErrorMsg"><html:errors header="common.errors.header" footer="common.errors.footer" prefix="common.errors.prefix" suffix="common.errors.suffix"/></div>
</div>

<html:form action="/AddProfileMapping" method="post">
	<input type="hidden" name="rootDDFNodeID" value="0">
	<table class="entityview">
	  <tbody>
	    <tr>
	      <th>* <bean:message key="profilemapping.title.template"/></th>
	      <td>
	        <html:select property="templateID">
	          <html:option value=""><bean:message key="page.select.option.default.label"/></html:option>
	          <html:optionsCollection name="profileTemplateOptions" label="name" value="ID"/>
	        </html:select>
          <div class="validateErrorMsg"><html:errors property="templateID"/></div></td>
	    </tr>
	    <tr>
	      <th>* <bean:message key="device.title.manufacturer"/></th>
	      <td>
	        <select name="manufacturerID" onchange="getModels(this.value)">
	          <option value=""><bean:message key="page.device.choice.manufacturer"/></option>
	          <logic:iterate id="manufacturer" name="manufacturerOptions">
	            <option value="<bean:write name="manufacturer" property="value" />"><bean:write name="manufacturer" property="label"/></option>
	          </logic:iterate>
	        </select>
	      <div class="validateErrorMsg"><html:errors property="manufacturerID"/></div></td>
	    </tr>
	    <tr>
	      <th>* <bean:message key="device.title.model"/></th>
	      <td>
	        <html:select property="modelID" styleId="modelID">
	          <html:option value=""><bean:message key="page.device.choice.model"/></html:option>
	        </html:select>
          <div class="validateErrorMsg"><html:errors property="modelID"/></div></td>
	    </tr>
	  </tbody>
	</table>
	<div class="buttonArea">
	  <html:submit property="action" styleClass="NormalWidthButton"><bean:message key="page.button.create.label"/></html:submit>
	  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	  <html:reset styleClass="NormalWidthButton"><bean:message key="page.button.reset.label"/></html:reset>
	  <html:cancel styleClass="NormalWidthButton"><bean:message key="page.button.cancel.label"/></html:cancel>
	</div>
</html:form>
