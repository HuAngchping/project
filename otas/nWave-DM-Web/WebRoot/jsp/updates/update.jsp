<%@ page language="java"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
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
	<bean:message key="page.message.required" />
	<bean:message key="page.message.input" />
</div>

<html:form action="/updates/saveUpdate" enctype='multipart/form-data'>
	<html:hidden property="updateID" />
	<table class="entityview">
		<tbody>
	    <tr>
	      <th width="160">* <bean:message key="device.title.manufacturer"/></th>
	      <td>
	        <html:select property="manufacturerID" onchange="getModels(this.value)">
	          <html:option value=""><bean:message key="page.device.choice.manufacturer"/></html:option>
	          <html:optionsCollection name="manufacturerOptions"/>
	        </html:select>
	      <div class="validateErrorMsg"><html:errors property="manufacturerID"/></div></td>
	    </tr>
	    <tr>
	      <th>* <bean:message key="device.title.model"/></th>
	      <td>
	        <html:select property="modelID" styleId="modelID">
	          <html:option value=""><bean:message key="page.device.choice.model"/></html:option>
	          <html:optionsCollection name="modelOptions"/>
	        </html:select>
          <div class="validateErrorMsg"><html:errors property="modelID"/></div></td>
	    </tr>
			<tr>
				<th>* <bean:message key="page.firmware.fromVersionID.label"/></th>
				<td>
					<html:text property="fromVersion" size="48"/>
					<div class="validateErrorMsg">
						<html:errors property="fromVersion" />
					</div>
				</td>
			</tr>
			<tr>
				<th>* <bean:message key="page.firmware.toVersionID.label"/></th>
				<td>
					<html:text property="toVersion" size="48"/>
					<div class="validateErrorMsg">
						<html:errors property="toVersion" />
					</div>
				</td>
			</tr>
			<tr>
				<th>* <bean:message key="page.firmware.status.label"/></th>
				<td>
					<html:select property="status">
						<html:optionsCollection name="updateStatusOptions" />
					</html:select>
					<div class="validateErrorMsg">
						<html:errors property="status" />
					</div>
				</td>
			</tr>
            <logic:empty name="FotaUpdateForm" property="updateID">
			  <tr>
				<th>
					* <bean:message key="page.firmware.image.label"/>
				</th>
				<td>
					<html:file property="imageFile" />
					<div class="validateErrorMsg">
						<html:errors property="FotaUpdateForm" />
					</div>
				</td>
			  </tr>
			</logic:empty>
            <logic:notEmpty name="FotaUpdateForm" property="updateID">
			  <tr>
				<th><bean:message key="page.firmware.imageSize.label"/></th>
				<td><bean:write name="update" property="size" format="#,###"/></td>
			  </tr>
			  <tr>
				<th>
					* <bean:message key="page.firmware.image.upload.label"/>
				</th>
				<td>
					<html:file property="imageFile" />
					<div class="validateErrorMsg">
						<html:errors property="FotaUpdateForm" />
					</div>
				</td>
			  </tr>
			  <tr>
				<th><bean:message key="page.firmware.image.label"/></th>
				<td>
					<html:link action="/updates/downloadfirmware?method=getBinary" target="_blank" paramId="updateID" paramName="update" paramProperty="ID">
					[<bean:message key="page.button.download.label" />]<html:img page="/common/images/icons/icon_down.gif"/>
					</html:link>
				</td>
			  </tr>			  
			</logic:notEmpty>

			<tr>
				<th><bean:message key="page.firmware.description.label"/></th>
				<td>
					<html:textarea property="description"/>
					<div class="validateErrorMsg">
						<html:errors property="description" />
					</div>
				</td>
			</tr>
		</tbody>
	</table>
	<div class="buttonArea">
		<logic:empty name="FotaUpdateForm" property="updateID">
			<html:submit styleClass="NormalWidthButton">
				<bean:message key="page.button.create.label" />
			</html:submit>
		</logic:empty>
		<logic:notEmpty name="FotaUpdateForm" property="updateID">
			<html:submit styleClass="NormalWidthButton">
				<bean:message key="page.button.update.label" />
			</html:submit>
		</logic:notEmpty>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<!--<html:reset styleClass="NormalWidthButton">
			<bean:message key="page.button.reset.label" />
		</html:reset>-->
		<html:cancel styleClass="NormalWidthButton">
			<bean:message key="page.button.cancel.label" />
		</html:cancel>
	  <logic:notEmpty name="FotaUpdateForm" property="updateID">
		    <html:button property="action" onclick="document.forms['FotaUpdateForm'][1].submit();" styleClass="NormalWidthButton">
		      <bean:message key="page.button.view.label"/>
		    </html:button>
	  </logic:notEmpty>
	</div>
</html:form>

<html:form action="/updates/viewUpdate" method="post">
  <logic:notEmpty name="FotaUpdateForm" property="updateID">
    <html:hidden property="updateID"/>
  </logic:notEmpty>
</html:form>
