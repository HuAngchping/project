<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
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
  //sel.options[sel.options.length] = new Option(label, "")
  for (var i = 0; i < str.length; i++) {
      if (str[i].length == 0) {
         continue;
      }
      each = str[i].split(",")
      sel.options[sel.options.length] = new Option(each[0],each[1])
  }
}

function doChangeStoreMode(selectList) {
  option = selectList.options[selectList.selectedIndex];
  if (option.value == "") {
     // Hidden all
     //alert("hiden all");
     changeStyle("remoteMode", "none");
     changeStyle("localMode", "none");
  } else if (option.value == "remote") {
    changeStyle("remoteMode", "");
    changeStyle("localMode", "none");
    
  } else if (option.value == "local") {
    changeStyle("remoteMode", "none");
    changeStyle("localMode", "");
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

/*
 Exchange option from sourceSelect to targetSelect,
    The selected ptions will be remove from sourceSelect list, and move to targetSelect list
*/
function doSelectModel(button) {
  //alert(button.form.name);
  
  sourceModelSelect = button.form.modelID;
  sourceManufacturerSelect = button.form.manufacturerID;
  targetSelect = button.form.targetModelIDs;
  
  manufacturerNameSelected = sourceManufacturerSelect.options[sourceManufacturerSelect.selectedIndex].text;
  manufacturerIDSelected = sourceManufacturerSelect.options[sourceManufacturerSelect.selectedIndex].value;
  if (manufacturerIDSelected == "") {
     // Please select manufacturer first.
     return;
  }
  for (i = 0; i < sourceModelSelect.options.length; i++) {
      option = sourceModelSelect.options[i];
      if (option.selected) {
         //alert(option);
         exists = false;
         modelID = option.value;
         if (modelID == "") {
            continue;
         }
         for (j = 0; j < targetSelect.options.length; j++) {
             if (modelID == targetSelect.options[j].value) {
                exists = true;
             }
         }
         if (!exists) {
            targetSelect[targetSelect.options.length] = new Option(manufacturerNameSelected + " " + option.text, option.value, false);
         }
      }
  }
}

function doDeSelectModel(button) {
  targetSelect = button.form.targetModelIDs;
  
  for (j = targetSelect.options.length - 1; j >= 0; j--) {
      if (targetSelect.options[j].selected) {
         targetSelect.options[j] = null;
      }
  }
}

/*
 Exchange option from sourceSelect to targetSelect,
    The selected ptions will be remove from sourceSelect list, and move to targetSelect list
*/
function doSelectModelFamily(button) {
  //alert(button.form.name);
  
  sourceSelect = button.form.modelFamilyID;
  targetSelect = button.form.targetModelFamilyIDs;
  
  for (i = 0; i < sourceSelect.options.length; i++) {
      option = sourceSelect.options[i];
      if (option.selected) {
         //alert(option);
         exists = false;
         modelID = option.value;
         if (modelID == "") {
            continue;
         }
         for (j = 0; j < targetSelect.options.length; j++) {
             if (modelID == targetSelect.options[j].value) {
                exists = true;
             }
         }
         if (!exists) {
            targetSelect[targetSelect.options.length] = new Option(option.text, option.value, false);
         }
      }
  }
}

function doSelectModelClassification(button) {
  //alert(button.form.name);
  
  sourceSelect = button.form.modelClassificationID;
  targetSelect = button.form.targetModelClassificationIDs;
  
  for (i = 0; i < sourceSelect.options.length; i++) {
      option = sourceSelect.options[i];
      if (option.selected) {
         //alert(option);
         exists = false;
         modelID = option.value;
         if (modelID == "") {
            continue;
         }
         for (j = 0; j < targetSelect.options.length; j++) {
             if (modelID == targetSelect.options[j].value) {
                exists = true;
             }
         }
         if (!exists) {
            targetSelect[targetSelect.options.length] = new Option(option.text, option.value, false);
         }
      }
  }
}

function doDeSelectModelFamily(button) {
  targetSelect = button.form.targetModelFamilyIDs;
  
  for (j = targetSelect.options.length - 1; j >= 0; j--) {
      if (targetSelect.options[j].selected) {
         targetSelect.options[j] = null;
      }
  }
}

function doDeSelectModelClassification(button) {
  targetSelect = button.form.targetModelClassificationIDs;
  
  for (j = targetSelect.options.length - 1; j >= 0; j--) {
      if (targetSelect.options[j].selected) {
         targetSelect.options[j] = null;
      }
  }
}

function doSelectAll(form) {
  targetSelect = form.targetModelIDs;
  for (j = targetSelect.options.length - 1; j >= 0; j--) {
      targetSelect.options[j].selected = true;
  }

  targetSelect = form.targetModelFamilyIDs;
  for (j = targetSelect.options.length - 1; j >= 0; j--) {
      targetSelect.options[j].selected = true;
  }

  targetSelect = form.targetModelClassificationIDs;
  for (j = targetSelect.options.length - 1; j >= 0; j--) {
      targetSelect.options[j].selected = true;
  }
  return true;
}

function doCleanAllModel(form) {
  if (confirm('<bean:message key="page.software.package.edit.prompt.clean.all.message" />')) {
     form.targetModelIDs.length = 0;
  }
}

function doCleanAllModelFamily(form) {
  if (confirm('<bean:message key="page.software.package.edit.prompt.clean.all.message" />')) {
     form.targetModelFamilyIDs.length = 0;
  }
}

function doCleanAllModelClassification(form) {
  if (confirm('<bean:message key="page.software.package.edit.prompt.clean.all.message" />')) {
     form.targetModelClassificationIDs.length = 0;
  }
}

</SCRIPT>

<div class="messageArea">
  <bean:message key="page.software.package.edit.message"/><br><br>
  <bean:message key="page.message.required" />
  <bean:message key="page.message.input" />
</div>

<logic:messagesPresent message="false">
<div class="validateErrorMessagePanel">
  <html:errors />
</div>
</logic:messagesPresent>
          
<html:form action="/software/package/save.do" enctype='multipart/form-data' onsubmit="return doSelectAll(this);">
  <html:hidden property="softwareID" />
  <html:hidden property="packageID" />
  <table class="entityview">
    <thead>
      <tr>
        <th colspan="2">
          <font color="red"><b>.:</b></font>  <bean:write name="software" property="vendor.name"/> <bean:write name="software" property="name"/> <bean:write name="software" property="version"/>
        </th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <th width="150">
          * <bean:message key="entity.software.software.name.label" />
        </th>
        <td>
          <html:text property="name" size="64"/>
          <div class="validateErrorMsg">
            <html:errors property="name" />
          </div>
        </td>
      </tr>                       
      <tr>
        <th>
          * <bean:message key="entity.software.package.language.label" />
        </th>
        <td>
          <html:select property="language">
            <html:option value=""><bean:message key="page.software.package.edit.choice.language"/></html:option>
            <html:optionsCollection name="languageOptions"/>
          </html:select>
          <div class="validateErrorMsg">
            <html:errors property="language" />
          </div>
        </td>
      </tr>
      <tr>
        <th>
          *
          <bean:message key="entity.software.package.store.mode.label" />
        </th>
        <td>
          <html:select property="storeMode" onchange="doChangeStoreMode(this);">
            <html:option value="local"><bean:message key="entity.software.package.store.mode.local.label" /></html:option>
            <html:option value="remote"><bean:message key="entity.software.package.store.mode.remote.label" /></html:option>
          </html:select>
        </td>
      </tr>
      <tr id="remoteMode">
        <th>
          *
          <bean:message key="entity.software.package.url.label" />
        </th>
        <td>
          <html:text property="url" size="80"/>
          <div class="validateErrorMsg">
            <html:errors property="url" />
          </div>
        </td>
      </tr>
      <tr id="localMode">
        <th>
          *
          <bean:message key="entity.software.package.mimeType.label" />
        </th>
        <td>
          <html:select property="mimeType">
            <html:option value=""><bean:message key="page.software.package.edit.choice.mimeType"/></html:option>
            <html:optionsCollection name="mimeTypeOptions"/>
          </html:select>
          <div class="validateErrorMsg">
            <html:errors property="mimeType" />
          </div>
        </td>
      </tr>
      <tr id="localMode">
        <th>
          <logic:empty name="SoftwarePackageForm" property="packageID">*</logic:empty>
          <bean:message key="entity.software.package.upload.file.label" />
        </th>
        <td>
          <html:file property="binaryFile"/>
          <div class="validateErrorMsg">
            <html:errors property="binaryFile" />
          </div>
        </td>
      </tr>
      <tr>
        <th>
          <bean:message key="entity.software.package.installation.options.label" />
        </th>
        <td>
          <html:textarea property="installationOptions" style="width: 100%; height: 150px;"/>
          <div class="validateErrorMsg">
            <html:errors property="installationOptions" />
          </div>
        </td>
      </tr>
      <tr>
        <th>
          <bean:message key="entity.software.package.description.label" />
        </th>
        <td>
          <html:textarea property="description" style="width: 100%; height: 150px;"/>
          <div class="validateErrorMsg">
            <html:errors property="description" />
          </div>
        </td>
      </tr>                       
    </tbody>
  </table>

  <!-- Edit panel for Target model classifications -->
  <table class="entityview">
    <thead>
      <tr>
        <th colspan="2">
          <font color="red"><b>.:</b></font> <bean:message key="page.software.package.edit.choice.model.classification.message"/>
        </th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <th width="150">
          *
          <bean:message key="entity.software.package.model.classification.label" />
        </th>
        <td>
          <table cellpadding="0" cellspacing="0" border="0" style="border: 0px;">
            <tr>
              <td style="width: 200px; border: 0px; vertical-align: top;">
              </td>
              <td style="width: 30px; border: 0px;">
              </td>
              <td style="border: 0px;">
                <B><bean:message key="page.software.package.edit.targetModelClassification.list.label"/></B>
              </td>
            </tr>
            <tr>
              <td style="width: 200px; border: 0px; vertical-align: top;">
                <html:select property="modelClassificationID" multiple="true" size="10" style="width: 240px;">
                  <html:optionsCollection name="modelClassificationOptions"/>
                </html:select>
              </td>
              <td style="width: 30px; border: 0px;">
                <input type="button" value="<bean:message key="page.software.package.edit.button.add.label"/>" onclick="return doSelectModelClassification(this);" style="width: 80px;">
                <br/>
                <input type="button" value="<bean:message key="page.software.package.edit.button.remove.label"/>" onclick="return doDeSelectModelClassification(this);" style="width: 80px;">
                <br/>
                <input type="button" value="<bean:message key="page.software.package.edit.button.cleanAll.label"/>" onclick="return doCleanAllModelClassification(this.form);" style="width: 80px;">
              </td>
              <td style="border: 0px;">
                <html:select property="targetModelClassificationIDs" multiple="true" size="10" style="width: 240px;">
                  <html:optionsCollection name="targetModelClassificationOptions"/>
                </html:select>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </tbody>
  </table>
  
  <!-- Edit panel for Target model -->
  <table class="entityview">
    <thead>
      <tr>
        <th colspan="2">
          <font color="red"><b>.:</b></font> <bean:message key="page.software.package.edit.choice.model.family.message"/>
        </th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <th width="150">
          *
          <bean:message key="entity.software.package.model.label" />
        </th>
        <td>
          <table cellpadding="0" cellspacing="0" border="0" style="border: 0px;">
            <tr>
              <td style="width: 200px; border: 0px; vertical-align: top;">
              </td>
              <td style="width: 30px; border: 0px;">
              </td>
              <td style="border: 0px;">
                <B><bean:message key="page.software.package.edit.targetModel.list.label"/></B>
              </td>
            </tr>
            <tr>
              <td style="width: 200px; border: 0px; vertical-align: top;">
                <html:select property="manufacturerID" onchange="getModels(this.value)" style="width: 240px;">
                  <html:option value=""><bean:message key="page.device.choice.manufacturer"/></html:option>
                  <html:optionsCollection name="manufacturerOptions"/>
                </html:select>
                <html:select property="modelID" styleId="modelID" multiple="true" size="10" style="width: 240px;">
                  <html:optionsCollection name="modelOptions"/>
                </html:select>
              </td>
              <td style="width: 30px; border: 0px;">
                <input type="button" value="<bean:message key="page.software.package.edit.button.add.label"/>" onclick="return doSelectModel(this);" style="width: 80px;">
                <br/>
                <input type="button" value="<bean:message key="page.software.package.edit.button.remove.label"/>" onclick="return doDeSelectModel(this);" style="width: 80px;">
                <br/>
                <input type="button" value="<bean:message key="page.software.package.edit.button.cleanAll.label"/>" onclick="return doCleanAllModel(this.form);" style="width: 80px;">
              </td>
              <td style="border: 0px;">
                <html:select property="targetModelIDs" styleId="targetModelIDs" multiple="true" size="12" style="width: 240px;">
                  <html:optionsCollection name="targetModelOptions"/>
                </html:select>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </tbody>
  </table>
  
  <!-- Edit panel for Target model family -->
  <table class="entityview">
    <thead>
      <tr>
        <th colspan="2">
          <font color="red"><b>.:</b></font> <bean:message key="page.software.package.edit.choice.model.family.message"/>
        </th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <th width="150">
          *
          <bean:message key="entity.software.package.model.family.label" />
        </th>
        <td>
          <table cellpadding="0" cellspacing="0" border="0" style="border: 0px;">
            <tr>
              <td style="width: 200px; border: 0px; vertical-align: top;">
              </td>
              <td style="width: 30px; border: 0px;">
              </td>
              <td style="border: 0px;">
                <B><bean:message key="page.software.package.edit.targetModelFamily.list.label"/></B>
              </td>
            </tr>
            <tr>
              <td style="width: 200px; border: 0px; vertical-align: top;">
                <html:select property="modelFamilyID" multiple="true" size="10" style="width: 240px;">
                  <html:optionsCollection name="modelFamilyOptions"/>
                </html:select>
              </td>
              <td style="width: 30px; border: 0px;">
                <input type="button" value="<bean:message key="page.software.package.edit.button.add.label"/>" onclick="return doSelectModelFamily(this);" style="width: 80px;">
                <br/>
                <input type="button" value="<bean:message key="page.software.package.edit.button.remove.label"/>" onclick="return doDeSelectModelFamily(this);" style="width: 80px;">
                <br/>
                <input type="button" value="<bean:message key="page.software.package.edit.button.cleanAll.label"/>" onclick="return doCleanAllModelFamily(this.form);" style="width: 80px;">
              </td>
              <td style="border: 0px;">
                <html:select property="targetModelFamilyIDs" multiple="true" size="10" style="width: 240px;">
                  <html:optionsCollection name="targetModelFamilyOptions"/>
                </html:select>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </tbody>
  </table>
  
  <div class="buttonArea">
    <logic:empty name="SoftwarePackageForm" property="packageID">
      <html:submit property="action" styleClass="NormalWidthButton" >
        <bean:message key="page.button.create.label" />
      </html:submit>
    </logic:empty>
    <logic:notEmpty name="SoftwarePackageForm" property="packageID">
      <html:submit property="action" styleClass="NormalWidthButton">
        <bean:message key="page.button.update.label" />
      </html:submit>
    </logic:notEmpty>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <html:reset styleClass="NormalWidthButton">
      <bean:message key="page.button.reset.label" />
    </html:reset>
    <html:cancel styleClass="NormalWidthButton">
      <bean:message key="page.button.cancel.label" property="packageID"/>
    </html:cancel>
    <logic:notEmpty name="SoftwarePackageForm" property="packageID">
      <html:button property="action"
        onclick="document.forms['SoftwarePackageForm'][1].submit();"
        styleClass="NormalWidthButton">
        <bean:message key="page.button.view.label" />
      </html:button>
    </logic:notEmpty>
  </div>
</html:form>

<html:form action="/software/package/view.do" method="post">
  <logic:notEmpty name="SoftwarePackageForm" property="packageID">
    <html:hidden property="softwareID" />
    <html:hidden property="packageID" />
  </logic:notEmpty>
</html:form>

<script type="text/javascript">
<!--
// Initialize UI
doChangeStoreMode(document.SoftwarePackageForm[0].storeMode);

//-->
</script>
