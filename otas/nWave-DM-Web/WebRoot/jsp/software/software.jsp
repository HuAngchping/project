<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested"	prefix="nested"%>

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
</script>

<html:form action="/software/savaSoftware" onsubmit="return doSelectAll(this);">
	<html:hidden property="id" />
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
				<th width="150">
					*
					<bean:message key="entity.software.software.name.label" />
				</th>
				<td>
					<html:text property="name" size="32"/>
					<div class="validateErrorMsg">
						<html:errors property="name" />
					</div>
				</td>
			</tr>
      <tr>
        <th>
          <bean:message key="entity.software.software.version.label" />
        </th>
        <td>
          <html:text property="version" />
        </td>
      </tr>
			<tr>
				<th>
					*
					<bean:message key="entity.software.category.label" />
				</th>
			  <td>
	        <table>
		        <tr>
			        <td style="border: 0px;">
			          <select name="categories4Select" multiple="multiple" size="10" style="width: 240px;">
			          <logic:iterate id="item" name="categories">
			            <option value='${item.id }'><bean:write name="item" property="pathAsString"/></option>
			          </logic:iterate>
			          </select>
			          <div class="validateErrorMsg">
			            <html:errors property="categoryID" />
			          </div>
			        </td>
			        <td style="border: 0px;">
			          <input type="button" value="<bean:message key="page.software.package.edit.button.add.label"/>" onclick="return doSelectCategories(this);" style="width: 80px;">
			          <br/>
			          <input type="button" value="<bean:message key="page.software.package.edit.button.remove.label"/>" onclick="return doDeSelectCategories(this)" style="width: 80px;">
			          <br/>
			          <input type="button" value="<bean:message key="page.software.package.edit.button.cleanAll.label"/>" onclick="return doCleanAllCategories(this.form)" style="width: 80px;"> 
			        </td>
			        <td style="border: 0px;">
			          <html:select property="categoryID" multiple="true" size="10" style="width: 240px;">
                  <logic:present name="selectCategories">
			            <html:optionsCollection name="selectCategories" label="pathAsString" value="id"/>
                  </logic:present>
			          </html:select>
			        </td>
		        </tr>
	        </table>
			  </td>
			</tr>
			<tr>
				<th>
					*
					<bean:message key="entity.software.vendor.label" />
				</th>
				<td>
					<html:select property="vendorID" >
					   <html:option value=""><bean:message key="page.software.software.choice.vendor"/></html:option>
                       <html:optionsCollection name="vendors" label="name" value="id"/>
                    </html:select>
					<div class="validateErrorMsg">
						<html:errors property="vendorID" />
					</div>                    
				</td>
			</tr>
			<tr>
				<th>
					*
					<bean:message key="entity.software.software.externalId.label" />
				</th>
				<td>
					<html:text property="externalId" />
					<div class="validateErrorMsg">
						<html:errors property="externalId" />
					</div>					
				</td>
			</tr>
      <tr>
        <th>
          * <bean:message key="entity.software.software.status.label" />
        </th>
        <td>
          <html:select property="status">
            <html:option value="test"><bean:message key="meta.software.status.test.label" /></html:option>
            <html:option value="release"><bean:message key="meta.software.status.release.label" /></html:option>
          </html:select>
        </td>
      </tr>
			<tr>
				<th>
					<bean:message key="entity.software.software.licenseType.label" />
				</th>
				<td>
          <html:select property="licenseType">
            <html:option value=""><bean:message key="meta.software.license_type.unkown.label" /></html:option>
            <html:option value="free"><bean:message key="meta.software.license_type.free.label" /></html:option>
            <html:option value="trial"><bean:message key="meta.software.license_type.trial.label" /></html:option>
          </html:select>
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="entity.software.software.description.label" />
				</th>
				<td>
					<html:textarea property="description" style="width: 100%; height: 260px;"/>
				</td>
			</tr>												
		</tbody>
	</table>
	<div class="buttonArea">
		<logic:empty name="SoftwareForm" property="id">
			<html:submit styleClass="NormalWidthButton">
				<bean:message key="page.button.create.label" />
			</html:submit>
		</logic:empty>
		<logic:notEmpty name="SoftwareForm" property="id">
			<html:submit styleClass="NormalWidthButton">
				<bean:message key="page.button.update.label" />
			</html:submit>
		</logic:notEmpty>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<html:cancel styleClass="NormalWidthButton">
			<bean:message key="page.button.cancel.label" />
		</html:cancel>
	</div>
</html:form>