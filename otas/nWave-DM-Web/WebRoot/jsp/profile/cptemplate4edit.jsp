<%@ page language="java"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

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
	<bean:message key="page.CPTemplates.message" /><br><br>
</div>

<html:form action="/profile/SaveCPTemplate">
<logic:notEmpty name="CPTemplateForm" property="templateID">
	<html:hidden property="templateID" />
	<html:hidden property="modelID" />
</logic:notEmpty>
	<table class="entityview">
		<thead>
			<tr>
				<th colspan="2">
					<bean:message key="page.profiletemplate.title.form.label" />
				</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<th width="160px">
		      <logic:empty name="CPTemplateForm" property="templateID">
		      *
		      </logic:empty>
					<bean:message key="CPTemplate.title.manufacturer" />
				</th>
				<td>
		      <logic:notEmpty name="CPTemplateForm" property="templateID">
				    <bean:write name="model" property="manufacturer.externalId"/>
				  </logic:notEmpty>
		      <logic:empty name="CPTemplateForm" property="templateID">
		        <html:select property="manufacturerID" onchange="getModels(this.value)">
		          <html:option value=""><bean:message key="page.device.choice.manufacturer"/></html:option>
		          <html:optionsCollection name="manufacturerOptions"/>
		        </html:select>
		      </logic:empty>
				</td>
			</tr>
			<tr>
				<th width="160px">
		      <logic:empty name="CPTemplateForm" property="templateID">
		      *
		      </logic:empty>
					<bean:message key="CPTemplate.title.model" />
				</th>
				<td>
		      <logic:notEmpty name="CPTemplateForm" property="templateID">
				    <bean:write name="model" property="manufacturerModelId"/>
				  </logic:notEmpty>
		      <logic:empty name="CPTemplateForm" property="templateID">
		        <html:select property="modelID" styleId="modelID">
		          <html:option value=""><bean:message key="page.device.choice.model"/></html:option>
		        </html:select>
            <div class="validateErrorMsg"><html:errors property="modelID"/></div>
		      </logic:empty>
				</td>
			</tr>
			<tr>
				<th width="160px">
					* <bean:message key="CPTemplate.title.name" />
				</th>
				<td>
				  <html:text property="name" size="48"/>
					<div class="validateErrorMsg">
						<html:errors property="name" />
				  </div>
				</td>
			</tr>
			<tr>
				<th width="160px">
					* <bean:message key="CPTemplate.title.category" />
				</th>
				<td>
					<html:select property="categoryID">
						<html:option value="">
							<bean:message key="page.select.option.default.label" />
						</html:option>
						<html:optionsCollection name="categories" label="name" value="ID" />
					</html:select>
					<div class="validateErrorMsg"><html:errors property="categoryID" /></div>
				</td>
			</tr>
			<tr>
				<th width="160px">
					<bean:message key="CPTemplate.title.description" />
				</th>
				<td>
		       <html:textarea property="description"></html:textarea>
		       <div class="validateErrorMsg"><html:errors property="description"/></div>
				</td>
			</tr>
			<tr>
				<th width="160px">
					* <bean:message key="CPTemplate.title.encoder" />
				</th>
				<td>
	        <html:select property="encoder">
	          <html:optionsCollection name="encoderOptions"/>
	        </html:select>
          <div class="validateErrorMsg"><html:errors property="encoder"/></div>
				</td>
			</tr>
			<tr>
				<th width="160px">
					* <bean:message key="CPTemplate.title.content" />
				</th>
				<td>
          <div class="validateErrorMsg"><html:errors property="content"/></div>
	        <html:textarea property="content" styleClass="script"></html:textarea>
				</td>
			</tr>
		</tbody>
	</table>
	<div class="buttonArea">
		<logic:empty name="CPTemplateForm" property="templateID">
			<html:submit property="method" styleClass="NormalWidthButton">
				<bean:message key="page.button.create.label" />
			</html:submit>
		</logic:empty>
		<logic:notEmpty name="CPTemplateForm" property="templateID">
			<html:submit property="method" styleClass="NormalWidthButton">
				<bean:message key="page.button.update.label" />
			</html:submit>
		</logic:notEmpty>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	  <logic:notEmpty name="CPTemplateForm" property="templateID">
		    <html:button property="method" onclick="document.forms['CPTemplateForm'][1].submit();" styleClass="NormalWidthButton">
		      <bean:message key="page.button.view.label"/>
		    </html:button>
	  </logic:notEmpty>
		<html:cancel styleClass="NormalWidthButton">
			<bean:message key="page.button.cancel.label" />
		</html:cancel>
	</div>
</html:form>

<logic:notEmpty name="CPTemplateForm" property="templateID">
	<html:form action="/profile/ViewCPTemplate" method="post">
	  <logic:notEmpty name="template" property="ID">
	    <html:hidden property="templateID"/>
	    <html:hidden property="modelID"/>
	  </logic:notEmpty>
	</html:form>
</logic:notEmpty>

<p>&nbsp;</p>