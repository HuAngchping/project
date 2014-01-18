<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<script type="text/javascript">
<!--
var xmlHttp;
    
function getAttributeList(templateID, categoryName) {
  createXMLHttpRequest()
  var url = "<html:rewrite page='/ajax.do'/>?templateID=" + templateID + "&categoryName=" + categoryName + "&method=getAttributes4NodeMapping";
  xmlHttp.open("GET", uncache(url), true);
  xmlHttp.onreadystatechange = parseAttributeList;
  xmlHttp.send(null);
}

function parseAttributeList() {
  if (xmlHttp.readyState == 4) {
     if (xmlHttp.status == 200) {
        resText = xmlHttp.responseText
        //alert(resText);
        each = resText.split("|")
        buildAttributeSelect(each, document.getElementById("profileAttributeID"), "<bean:message key="profileNodeMapping.title.categoryName.none.label"/>");
     }
  }
}

function buildAttributeSelect(str, sel, label) {
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

function doChangeDDFNode(selectList) {
  //alert(selectList.selectedIndex);
  option = selectList.options[selectList.selectedIndex];
  if (option.value != "") {
     selectList.form.elements['nodeRelativePath'].value = option.text;
  } else {
    selectList.form.elements['nodeRelativePath'].value = "";
  }
}

function doChangeValueFormat(selectList) {
  option = selectList.options[selectList.selectedIndex];
  if (option.value != "") {
     selectList.form.elements['valueFormatText'].value = option.text;
  } else {
    selectList.form.elements['valueFormatText'].value = "";
  }
  
}

function doChangeMappingType(selectList) {
  option = selectList.options[selectList.selectedIndex];
  if (option.value == "") {
     // Hidden all
     //alert("hiden all");
     changeStyle("attributeMapping", "none");
     changeStyle("valueMapping", "none");
  } else if (option.value == "attribute") {
    // Hidden value mapping and show attribute mapping
    //alert("hidden value mapping");
    changeStyle("attributeMapping", "");
    changeStyle("valueMapping", "none");
    
  } else if (option.value == "value") {
    // Hidden attribute mapping and show value mapping
    //alert("hidden attribute mapping");
    changeStyle("attributeMapping", "none");
    changeStyle("valueMapping", "");
  }
}

function changeStyle(id, display) {
    var elements = document.getElementsByTagName("tr");
    for (var i = 0; i < elements.length; i++) {
        if (elements[i].id == id) {
           //alert(elements[i].style);
           elements[i].style.display = display;
        }
    }
}

//-->
</script>

<div class="messageArea">
  <bean:message key="page.message.required"/><bean:message key="page.message.input"/>
</div>

<html:form action="/SaveProfileNodeMapping">
<!--  NodeMapping ID -->
<html:hidden property="ID" />
<!--  Template ID -->
<html:hidden property="templateID" />
<!--  Attribute ID -->
<html:hidden property="modelID"/>
<table class="entityview">
  <thead>
    <tr>
      <th colspan="2"><bean:message key="page.profileNodeMapping.title.form.label"/></th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>* <bean:message key="profileNodeMapping.title.mappingType"/></th>
      <td>
        <html:select property="mappingType" onchange="doChangeMappingType(this);">
          <html:option value=""><bean:message key="page.select.option.default.label"/></html:option>
          <html:optionsCollection name="mappingTypes" label="label" value="value"/>
        </html:select>
        <div class="validateErrorMsg"><html:errors property="mappingType"/></div>
      </td>
    </tr>
    <tr id="attributeMapping">
      <th style="white-space: nowrap"><bean:message key="profileNodeMapping.title.categoryName"/></th>
      <td>
        <html:select property="categoryName" onchange="getAttributeList(this.form.templateID.value, this.value)">
          <html:option value=""><bean:message key="profileNodeMapping.title.categoryName.none.label"/></html:option>
          <html:option value="NAP">NAP</html:option>
          <html:option value="Proxy">Proxy</html:option>
        </html:select>
        <div class="validateErrorMsg"><html:errors property="categoryName"/></div></td>
    </tr>
    <tr id="attributeMapping">
      <th><bean:message key="profileNodeMapping.title.attributeName"/></th>
      <td>
        <html:select property="profileAttributeID" styleId="profileAttributeID">
          <html:option value=""><bean:message key="page.select.option.default.label"/></html:option>
          <html:optionsCollection name="attributes" label="name" value="ID"/>
        </html:select>
        <div class="validateErrorMsg"><html:errors property="errors.profileAttributeID"/></div></td>
    </tr>
    <tr>
      <th><bean:message key="profileNodeMapping.title.ddfNodePath"/></th>
      <td style="white-space: nowrap">
        <bean:write name="profileMapping" property="rootNodePath"/>/
        <html:select property="ddfNodeID" onchange="doChangeDDFNode(this);">
          <html:option value=""><bean:message key="page.select.option.default.label"/></html:option>
          <html:optionsCollection name="ddfNodes" label="label" value="value"/>
        </html:select>
        <div class="validateErrorMsg"><html:errors property="ddfNodeID"/></div></td>
    </tr>
    <tr>
      <th>* <bean:message key="profileNodeMapping.title.ddfNodePath"/></th>
      <td style="white-space: nowrap">
        <bean:write name="profileMapping" property="rootNodePath"/>/
        <html:text property="nodeRelativePath" size="32"/>
        <div class="validateErrorMsg"><html:errors property="nodeRelativePath"/></div>
      </td>
    </tr>
         
    <tr id="valueMapping">
      <th><bean:message key="profileNodeMapping.title.value"/></th>
      <td><html:text property="value" size="48"/><div class="validateErrorMsg"><html:errors property="value"/></div></td>
    </tr>
    <tr id="valueMapping">
      <th><bean:message key="profileNodeMapping.title.displayName"/></th>
      <td><html:text property="displayName"/><div class="validateErrorMsg"><html:errors property="displayName"/></div></td>
    </tr>
    
    <tr>
      <th><bean:message key="profileNodeMapping.title.command"/></th>
      <td>
        <html:select property="command" >
          <html:option value=""><bean:message key="page.select.option.default.label1"/></html:option>
          <html:option value="Add">Add</html:option>
          <html:option value="Replace">Replace</html:option>
          <html:option value="Exec">Exec</html:option>          
        </html:select>
      </td>
    </tr>

    <tr>
      <th><bean:message key="profileNodeMapping.title.valueFormat"/></th>
      <td>
        <html:text property="valueFormatText" size="15"/>
        <html:select property="valueFormat" onchange="doChangeValueFormat(this);">
          <html:option value=""><bean:message key="page.select.option.default.label1"/></html:option>
          <html:option value="chr">chr</html:option>
          <html:option value="int">int</html:option>
          <html:option value="bool">bool</html:option> 
          <html:option value="xml">xml</html:option>         
        </html:select>
      </td>
    </tr>
                  
    <logic:present name="profileNodeMapping">
	    <logic:notEmpty name="profileNodeMapping" property="attribTranslationses">
	    <tr>
	      <th><bean:message key="profileNodeMapping.title.translations.label"/></th>
	      <td>
					<display:table name="profileNodeMapping.attribTranslationses" id="translation" class="simple">
						<display:column property="ID.originalValue" titleKey="profileNodeMapping.title.translation.original.label"/>
						<display:column property="ID.value" titleKey="profileNodeMapping.title.translation.value.label"/>
					</display:table>
	      </td>
	    </tr>
	    </logic:notEmpty>
    </logic:present>
    <!-- 
    <tr>
      <th><bean:message key="profileNodeMapping.title.paramIndex"/></th>
      <td><html:text property="paramIndex"/><div class="validateErrorMsg"><html:errors property="paramIndex"/></div></td>
    </tr>
     -->
  </tbody>
</table>
<div class="buttonArea">
  <logic:empty name="ProfileNodeMappingForm" property="ID">
    <html:submit property="action" styleClass="NormalWidthButton"><bean:message key="page.button.create.label"/></html:submit>
  </logic:empty>
  <logic:notEmpty name="ProfileNodeMappingForm" property="ID">
    <html:submit property="action" styleClass="NormalWidthButton"><bean:message key="page.button.update.label"/></html:submit>
  </logic:notEmpty>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <html:reset styleClass="NormalWidthButton"><bean:message key="page.button.reset.label"/></html:reset>
  <html:cancel styleClass="NormalWidthButton"><bean:message key="page.button.cancel.label"/></html:cancel>
</div>
</html:form>

<script type="text/javascript">
<!--
// Initialize UI
doChangeMappingType(document.ProfileNodeMappingForm.mappingType);

//-->
</script>
