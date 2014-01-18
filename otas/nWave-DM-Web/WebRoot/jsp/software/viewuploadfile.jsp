<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>



<script type="text/javascript">
function doSelectCategories(button) {
  
  sourceSelect = button.form.categories4Select;
  targetSelect = button.form.categoryID;

  for (i = 0; i < sourceSelect.options.length; i++) {
      option = sourceSelect.options[i];
      if (option.selected) {
         //alert(option);
         exists = false;
         modelID = option.value;
         //alert(modelID);
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
         for (x = 0; x < targetSelect.options.length; x++) {
            targetSelect.options[j].selected = true;
         }
      }
  }
}

function doDeSelectCategories(button) {
  targetSelect = button.form.categoryID;
  
  for (j = targetSelect.options.length - 1; j >= 0; j--) {
      if (targetSelect.options[j].selected) {
         targetSelect.options[j] = null;
      }
  }
}

function doCleanAllCategories(form) {
  if (confirm('<bean:message key="page.software.package.edit.prompt.clean.all.message" />')) {
     form.categoryID.length = 0;
  }
}
function doSelectAll(form) {
  targetSelect = form.categoryID;
  for (j = targetSelect.options.length - 1; j >= 0; j--) {
      targetSelect.options[j].selected = true;
  }
  return true;  
}
function check(button){
  targetSelect = button.form.categoryID;
  if (targetSelect.options.length == 0) {
    alert('<bean:message key="page.software.uploadfile.error.label"/>');
  } else {
    button.form.submit();
  }
}
</script>


<html:form action="/createUploadFile">
  <table class="entityview">
    <thead>
      <tr>
        <th colspan="2">
          &nbsp;
        </th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <th>
          <bean:message key="page.software.uploadfile.vendor.label" />
        </th>
        <td>
          <html:text property="vendor" value="${softwareInfo.vendor }"/>          
        </td>
      </tr>
      <tr>
        <th>
          <bean:message key="page.software.uploadfile.name.label" />
        </th>
        <td>
          <html:text property="name" value="${softwareInfo.name }"/>
          <input type="hidden" name="file" value="${file}"/>
        </td>
      </tr>
      <tr>
        <th>
          <bean:message key="page.software.uploadfile.version.label" />
        </th>
        <td>
          <html:text property="version" value="${softwareInfo.version }"/>
          
        </td>
      </tr>
      <tr>
        <th>
          * <bean:message key="page.software.uploadfile.store.mode.label"/>
        </th>
        <td>
          <input type="text" value="<bean:message key="page.software.uploadfile.store.mode.local.label"/>"/>
          <input type="hidden" name="local" value="<bean:message key="page.software.uploadfile.store.mode.local.label"/>"/>
        </td>
      </tr>
      <tr>
        <th>
          * <bean:message key="page.software.uploadfile.mimeType.label"/>
        </th>
        <td>
          <html:text property="mimeType" value="${mimeType}" style="width: 150px"/>
        </td>
      </tr>
      <tr>
        <th>
          * <bean:message key="page.software.uploadfile.language.label"/>
        </th>
        <td>
          <html:select property="language">
            <html:option value=""> <bean:message key="page.software.uploadfile.language.choice.label"/> </html:option>
            <html:optionsCollection name="languageOptions"/>
          </html:select>
        </td>
      </tr>
      <tr>
        <th>
          * <bean:message key="page.software.uploadfile.category.label"/>
        </th>
        <td>
          <table>
            <tr>
              <td style="border: 0px;">
                 <select name="categories4Select" multiple="multiple" size="10" style="width: 240px;">
                    <logic:iterate id="item" name="categories">
                      <option value="${item.id }">
                        <bean:write name="item" property="pathAsString"/>
                      </option>
                    </logic:iterate>
                 </select>
                 <div class="validateErrorMsg">
                   <html:errors property="categoryID" />
                 </div>
              </td>
              <td style="border: 0px;">
                <input type="button"
                  value="<bean:message key="page.software.package.edit.button.add.label"/>"
                  onclick="return doSelectCategories(this);" style="width: 80px;">
                <br />
                <input type="button"
                  value="<bean:message key="page.software.package.edit.button.remove.label"/>"
                  onclick="return doDeSelectCategories(this)"
                  style="width: 80px;">
                <br />
                <input type="button"
                  value="<bean:message key="page.software.package.edit.button.cleanAll.label"/>"
                  onclick="return doCleanAllCategories(this.form)"
                  style="width: 80px;">
              </td>
              <td style="border: 0px;">
                <html:select property="categoryID" multiple="true" size="10" style="width: 240px;">
	                 <logic:present name="category">
	                  <html:optionsCollection name="category"/>
	                 </logic:present>  
                </html:select>
              </td>
            </tr>
          </table>
        </td>
      </tr>
      <tr>
        <th>
          * <bean:message key="page.software.uploadfile.externalId.label"/>
        </th>
        <td>
          <html:text property="externalId" value="${externalId}"/>
        </td>
      </tr>
      <tr>
        <th>
          * <bean:message key="page.software.uploadfile.status.label"/>
        </th>
        <td>
          <html:select property="status">
            <html:option value="test">
              <bean:message key="page.software.uploadfile.status.test.label"/>
            </html:option>
            <html:option value="release">
              <bean:message key="page.software.uploadfile.status.release.label"/>
            </html:option>
          </html:select>
        </td>
      </tr>
      <tr>
        <th>
          <bean:message key="page.software.uploadfile.licenseType.label"/>
        </th>
        <td>
          <html:select property="licenseType">
            <html:option value=""> <bean:message key="page.software.uploadfile.licenseType.unkown.label"/> </html:option>
            <html:option value="free"> <bean:message key="page.software.uploadfile.licenseType.free.label"/> </html:option>
            <html:option value="trial"> <bean:message key="page.software.uploadfile.licenseType.trial.label"/> </html:option>
          </html:select>
        </td>
      </tr>
      <tr>
        <th>
          * <bean:message key="page.software.uploadfile.targetmodel.label"/>
        </th>
        <td>
          <html:text property="classification" value="${classification}" style="width: 150px"/>
        </td>
      </tr>
      <tr>
        <th>
          <bean:message key="page.software.uploadfile.installInfo.label"/>
        </th>
        <td>
          <html:textarea property="installationOptions" value="${installInfo}" style="width: 100%; height: 150px;"/>
        </td>
      </tr>
      <tr>
        <th>
          <bean:message key="page.software.uploadfile.description.label"/>
        </th>
        <td>
          <html:text property="description" style="width: 100%; height: 260px;"/>
        </td>
      </tr>
    </tbody>
  </table> 
  <div>
 <!--     <html:submit styleClass="NormalWidthButton">
      <bean:message key="page.button.next.label" />
    </html:submit> -->
    <input type="button"
           value="<bean:message key="page.button.next.label"/>"
           onclick="return check(this);" style="width: 80px;">
  </div>
</html:form>