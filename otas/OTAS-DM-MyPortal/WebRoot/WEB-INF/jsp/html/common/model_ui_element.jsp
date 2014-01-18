<?xml version="1.0" encoding="UTF-8" ?>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"
	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"
	prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested"
	prefix="nested"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
var xmlHttp;
    
function getModels(manufacturer) {
  createXMLHttpRequest()
  var url = "<html:rewrite page='/ajax.do'/>?manufacturerID=" + manufacturer + "&method=getModels";
  document.getElementById("value(modelID)").disabled = true;
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
        buildSelect(each, document.getElementById("value(modelID)"), "<bean:message key="page.cp.wizard.select.model.model.EmptyOptions"/>");
     }
     document.getElementById("value(modelID)").disabled = false;
  }
}

function buildSelect(str, sel, label) {
  //alert(sel);
  sel.options.length = 0;
  sel.options[sel.options.length] = new Option(label, "")
  for (var i = 0; i < str.length; i++) {
      if (str[i].length == 0) {
         continue;
      }
      each = str[i].split(",")
      sel.options[sel.options.length] = new Option(each[0],each[1])
  }
  modelChanged();
}

function modelChanged() {
  modelSelectList = document.getElementById("value(modelID)");
  manufacturerSelectList = document.getElementById("manufacturerOptions");
  modelID = modelSelectList.options[modelSelectList.selectedIndex].value;
  //alert(modelID);
  url = WEB_HTTP_BASE_PATH + '/download.do?method=getModelIconHTML&modelID=' + modelID;
  new Ajax.Updater('ModelIcon', uncache(url), { method: 'get', onComplete:function(){ resizePage();} });
}
</script>
<tr bgcolor="<logic:messagesPresent message="false" property="manufacturerID">#fffbb8</logic:messagesPresent>">
	<th nowrap="nowrap"><bean:message key="page.cp.wizard.select.manufacturer.manufacturer.label"/>: </th>
	<td>
	  <html:select property="value(manufacturerID)" styleId="manufacturerOptions" onchange="getModels(this.value)">
	    <html:option value=""><bean:message key="page.cp.wizard.select.manufacturer.manufacturer.EmptyOptions"/></html:option>
        <logic:iterate id="option" name="specialManufacturerOptions">
          <html:option value="${option.value}">${option.label}</html:option>
        </logic:iterate>
        <html:option value="">--------------------</html:option>
	    <html:optionsCollection name="manufacturerOptions"/>
	  </html:select>
  </td>
</tr>
<tr bgcolor="<logic:messagesPresent message="false" property="modelID">#fffbb8</logic:messagesPresent>">
	<th><bean:message key="page.cp.wizard.select.model.model.label"/>: </th>
	<td>
	  <html:select property="value(modelID)" styleId="value(modelID)" onchange="modelChanged();">
	    <html:option value=""><bean:message key="page.cp.wizard.select.model.model.EmptyOptions"/></html:option>
	    <html:optionsCollection name="modelOptions"/>
	  </html:select>
  </td>
</tr>
<tr>
	<th height="1px;"></th>
	<td height="1px;">
	  <div id="ModelIcon">
      <logic:empty name="ClientProvForm4Wizard" property="value(modelID)" scope="session">
        <bean:message key="page.cp.wizard.select.model.help.2"/>
      </logic:empty>
      <logic:notEmpty name="ClientProvForm4Wizard" property="value(modelID)" scope="session">
        <div id="specs-model-pic">
    	     <html:link target="_blank" action="/download.do?method=getModelIcon" paramId="modelID" paramName="ClientProvForm4Wizard" paramProperty="value(modelID)"><html:img action="/download.do?method=getModelIcon" paramId="modelID" paramName="ClientProvForm4Wizard" paramProperty="value(modelID)"/></html:link>
    	  </div>
      </logic:notEmpty>
	  </div>
  </td>
</tr>
