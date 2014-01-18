<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<script type="text/javascript">
<!--
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

function doDeSelect(button, targetSelect) {
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
  doDeSelect(button, button.form.modelIDs);
}

function doCleanAllModel(form) {
  if (confirm('<bean:message key="page.software.package.edit.prompt.clean.all.message" />')) {
     form.modelIDs.length = 0;
  }
}

function doSelectManufacturer(button) {
  //alert(button.form.name);
  
  sourceModelSelect = button.form.manufacturerID4TargetManufacturer;
  targetSelect = button.form.manufacturerIDs;
  
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
            targetSelect[targetSelect.options.length] = new Option(option.text, option.value, false);
         }
      }
  }
}
function doDeSelectManufacturer(button) {
  doDeSelect(button, button.form.manufacturerIDs);
}

function doCleanAllManufacturer(form) {
  if (confirm('<bean:message key="page.software.package.edit.prompt.clean.all.message" />')) {
     form.manufacturerIDs.length = 0;
  }
}

function doSelectCarrier(button) {
  //alert(button.form.name);
  
  sourceModelSelect = button.form.carrierID;
  targetSelect = button.form.carrierIDs;
  
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
            targetSelect[targetSelect.options.length] = new Option(option.text, option.value, false);
         }
      }
  }
}
function doDeSelectCarrier(button) {
  doDeSelect(button, button.form.carrierIDs);
}

function doCleanAllCarrier(form) {
  if (confirm('<bean:message key="page.software.package.edit.prompt.clean.all.message" />')) {
     form.carrierIDs.length = 0;
  }
}

function doSelectAll(form) {
  targetSelect = form.modelIDs;
  for (j = targetSelect.options.length - 1; j >= 0; j--) {
      targetSelect.options[j].selected = true;
  }

  targetSelect = form.manufacturerIDs;
  for (j = targetSelect.options.length - 1; j >= 0; j--) {
      targetSelect.options[j].selected = true;
  }

  targetSelect = form.carrierIDs;
  for (j = targetSelect.options.length - 1; j >= 0; j--) {
      targetSelect.options[j].selected = true;
  }

  return true;
}

//-->
</script>
<div class="messageArea">
  <div class="validateErrorMsg"><html:errors header="common.errors.header" footer="common.errors.footer" prefix="common.errors.prefix" suffix="common.errors.suffix"/></div>
</div>

<html:form action="/jobs/EditJobType" enctype='multipart/form-data' onsubmit="return doSelectAll(this);">
<html:hidden property="jobType"/>
	<table class="entityview">
	  <tbody>
      <tr>
        <td class=table_box_heading width="100%" colSpan=5><font color="red"><b>.:</b></font> <bean:message key="page.Jobs.jobtypes.message" /></td>
      </tr>
    <tr>
      <th>
        <bean:message key="device.job.jobType" />
      </th>
      <td>
        <bean:write name="JOB_TYPE_FOR_ASSIGN_PROFILE" property="name" /> [ <bean:write name="JOB_TYPE_FOR_ASSIGN_PROFILE" property="mode" /> ]
      </td>
    </tr>
	    <tr>
	      <th>* <bean:message key="page.Jobs.jobtypes.targetDevice.phoneNumbers.label"/></th>
	      <td>
          <html:file property="phoneNumbersFile" size="36"/><br/>
	        <html:textarea property="phoneNumbers" style="width: 450px;"></html:textarea><br/>
	        <bean:message key="page.Jobs.jobtypes.targetDevice.phoneNumbers.message"/>
        </td>
      </tr>
      <tr>
        <th>* <bean:message key="page.Jobs.jobtypes.targetDevice.externalIDs.label"/></th>
        <td>
          <html:file property="deviceExternalIDsFile" size="36"/><br/>
          <html:textarea property="deviceExternalIDs" style="width: 450px;"></html:textarea><br/>
          <bean:message key="page.Jobs.jobtypes.targetDevice.externalIDs.message"/>
        </td>
      </tr>
      <!-- Target Model -->
      <tr>
        <th>* <bean:message key="page.Jobs.jobtypes.targetDevice.modelIDs.label"/></th>
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
                <html:select property="modelIDs" styleId="targetModelIDs" multiple="true" size="12" style="width: 240px;">
                </html:select>
              </td>
            </tr>
          </table>
        </td>
      </tr>
      <!-- Target Manufacturers -->
      <tr>
        <th>* <bean:message key="page.Jobs.jobtypes.targetDevice.manufacturerIDs.label"/></th>
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
                <html:select property="manufacturerID4TargetManufacturer" styleId="targetManufacturerID" multiple="true" size="12" style="width: 240px;">
                  <html:optionsCollection name="manufacturerOptions"/>
                </html:select>
              </td>
              <td style="width: 30px; border: 0px;">
                <input type="button" value="<bean:message key="page.software.package.edit.button.add.label"/>" onclick="return doSelectManufacturer(this);" style="width: 80px;">
                <br/>
                <input type="button" value="<bean:message key="page.software.package.edit.button.remove.label"/>" onclick="return doDeSelectManufacturer(this);" style="width: 80px;">
                <br/>
                <input type="button" value="<bean:message key="page.software.package.edit.button.cleanAll.label"/>" onclick="return doCleanAllManufacturer(this.form);" style="width: 80px;">
              </td>
              <td style="border: 0px;">
                <html:select property="manufacturerIDs" styleId="targetManufacturerIDs" multiple="true" size="12" style="width: 240px;">
                </html:select>
              </td>
            </tr>
          </table>
        </td>
      </tr>
      <!-- Target Carriers -->
      <tr>
        <th>* <bean:message key="page.Jobs.jobtypes.targetDevice.carrierIDs.label"/></th>
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
                <html:select property="carrierID" styleId="targetCarrierID" multiple="true" size="8" style="width: 240px;">
                  <html:optionsCollection name="carrierOptions"/>
                </html:select>
              </td>
              <td style="width: 30px; border: 0px;">
                <input type="button" value="<bean:message key="page.software.package.edit.button.add.label"/>" onclick="return doSelectCarrier(this);" style="width: 80px;">
                <br/>
                <input type="button" value="<bean:message key="page.software.package.edit.button.remove.label"/>" onclick="return doDeSelectCarrier(this);" style="width: 80px;">
                <br/>
                <input type="button" value="<bean:message key="page.software.package.edit.button.cleanAll.label"/>" onclick="return doCleanAllCarrier(this.form);" style="width: 80px;">
              </td>
              <td style="border: 0px;">
                <html:select property="carrierIDs" styleId="targetCarrierIDs" multiple="true" size="8" style="width: 240px;">
                </html:select>
              </td>
            </tr>
          </table>
        </td>
      </tr>
	  </tbody>
	</table>

	<div class="buttonArea">
    <html:submit property="method" styleClass="NormalWidthButton"><bean:message key="page.button.next.label"/></html:submit>
	  <html:reset styleClass="NormalWidthButton"><bean:message key="page.button.reset.label"/></html:reset>
	  <html:cancel styleClass="NormalWidthButton"><bean:message key="page.button.cancel.label"/></html:cancel>
	</div>
</html:form>
