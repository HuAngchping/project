<%@ page language="java"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<div class="messageArea">
	<bean:message key="page.message.required" />
	<bean:message key="page.message.input" />
</div>

<logic:notEmpty name="ModelForm" property="ID">
	<div class="buttonArea" style="height: 40px;">
		<bean:define id="modelID" name="ModelForm" property="ID"></bean:define>
		<html:form action="/DDFTree">
			<input type="hidden" name="modelID" value="<c:out value="${modelID}"/>">
			<html:submit styleClass="MiddleWidthButton"><bean:message key="model.view.ddf.tree" /></html:submit>
		</html:form>
		
		<bean:define id="modelID" name="ModelForm" property="ID"></bean:define>
		<html:form action="/ADDTree">
			<input type="hidden" name="modelID" value="<c:out value="${modelID}"/>">
			<html:submit styleClass="MiddleWidthButton"><bean:message key="model.import.ddf" /></html:submit>
		</html:form>

		<bean:define id="modelID" name="ModelForm" property="ID"></bean:define>
		<html:form action="/model/updateimages">
			<input type="hidden" name="modelID" value="<c:out value="${modelID}"/>">
			<html:submit styleClass="MiddleWidthButton"><bean:message key="model.firmware.button.label"/></html:submit>
		</html:form>
		
		<bean:define id="modelID" name="ModelForm" property="ID"></bean:define>
		<form action="<html:rewrite action="/ViewModelSpec"/>" target="_blank">
			<input type="hidden" name="modelID" value="<c:out value="${modelID}"/>">
			<input type="hidden" name="method" value="getModelCapabilityMatrix">
			<html:submit styleClass="MiddleWidthButton"><bean:message key="model.view.capabilities.button.label"/></html:submit>
		</form>
	</div>
</logic:notEmpty>

<html:form action="/SaveModel">
	<html:hidden property="action" />
	<html:hidden property="ID" />
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
					* <bean:message key="model.title.manufacturer" />
				</th>
				<td>
					<html:select property="manufacturerID">
						<html:option value="">
							<bean:message key="model.page.choice.manufacturer" />
						</html:option>
						<html:optionsCollection name="manufacturerOptions" />
					</html:select>
					<div class="validateErrorMsg">
						<html:errors property="manufacturerID" />
					</div>
				</td>
			</tr>
			<tr>
				<th>
					* <bean:message key="model.title.manufacturerModelId" />
				</th>
				<td>
					<html:text property="manufacturerModelId" />
					<div class="validateErrorMsg">
						<html:errors property="manufacturerModelId" />
					</div>
				</td>
			</tr>
			<tr>
				<th width="250">
					* <bean:message key="model.title.name" />
				</th>
				<td>
					<html:text property="name" />
					<div class="validateErrorMsg">
						<html:errors property="name" />
					</div>
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.title.taclist"/><br>
					
				</th>
				<td>
					<html:textarea property="taclist" />
					<div class="validateErrorMsg">
						<html:errors property="taclist" />
					</div>
					eg: 12345678,45678900
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.title.description" />
				</th>
				<td>
					<html:textarea property="description"/>
					<div class="validateErrorMsg">
						<html:errors property="description" />
					</div>
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.title.supportedDownloadMethods" />
				</th>
				<td>
					<html:checkbox property="supportedDownloadMethods" />
					<div class="validateErrorMsg">
						<html:errors property="supportedDownloadMethods" />
					</div>
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.title.isOmaDmEnabled" />
				</th>
				<td>
					<html:checkbox property="isOmaDmEnabled" />
					<div class="validateErrorMsg">
						<html:errors property="isOmaDmEnabled" />
					</div>
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.title.OmaDmVersion" />
				</th>
				<td>
					<html:text property="omaDmVersion" size="6"/>
					<div class="validateErrorMsg">
						<html:errors property="omaDmVersion" />
					</div>
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.title.isOmaCpEnabled" />
				</th>
				<td>
					<html:checkbox property="isOmaCpEnabled" />
					<div class="validateErrorMsg">
						<html:errors property="isOmaCpEnabled" />
					</div>
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.title.OmaCpVersion"/>
				</th>
				<td>
					<html:text property="omaCpVersion" size="6"/>
					<div class="validateErrorMsg">
						<html:errors property="omaCpVersion" />
					</div>
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.title.isNokiaOtaEnabled" />
				</th>
				<td>
					<html:checkbox property="isNokiaOtaEnabled" />
					<div class="validateErrorMsg">
						<html:errors property="isNokiaOtaEnabled" />
					</div>
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.title.NokiaOtaVersion" />
				</th>
				<td>
					<html:text property="nokiaOtaVersion" size="6"/>
					<div class="validateErrorMsg">
						<html:errors property="nokiaOtaVersion" />
					</div>
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.title.serverAuthType" />
				</th>
				<td>
			        <html:select property="serverAuthType">
			          <html:option value=""><bean:message key="page.select.option.default.label"/></html:option>
			          <html:optionsCollection name="authSchemas"/>
			        </html:select>
					<div class="validateErrorMsg">
						<html:errors property="serverAuthType" />
					</div>
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.title.firmwareVersionNode" />
				</th>
				<td>
					<html:text property="firmwareVersionNode" size="48"/>
					<div class="validateErrorMsg">
						<html:errors property="firmwareVersionNode" />
					</div>
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.title.firmwareUpdateNode" />
				</th>
				<td>
					<html:text property="firmwareUpdateNode" size="48"/>
					<div class="validateErrorMsg">
						<html:errors property="firmwareUpdateNode" />
					</div>
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.title.firmwareDownloadNode" />
				</th>
				<td>
					<html:text property="firmwareDownloadNode" size="48"/>
					<div class="validateErrorMsg">
						<html:errors property="firmwareDownloadNode" />
					</div>
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.title.firmwareDownloadAndUpdateNode" />
				</th>
				<td>
					<html:text property="firmwareDownloadAndUpdateNode" size="48"/>
					<div class="validateErrorMsg">
						<html:errors property="firmwareDownloadAndUpdateNode" />
					</div>
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.title.firmwareStatusNode" />
				</th>
				<td>
					<html:text property="firmwareStatusNode" size="48"/>
					<div class="validateErrorMsg">
						<html:errors property="firmwareStatusNode" />
					</div>
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.title.isPlainProfile" />
				</th>
				<td>
					<html:checkbox property="isPlainProfile" />
					<div class="validateErrorMsg">
						<html:errors property="isPlainProfile" />
					</div>
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.title.isUseEncForOmaMsg" />
				</th>
				<td>
					<html:checkbox property="isUseEncForOmaMsg" />
					<div class="validateErrorMsg">
						<html:errors property="isUseEncForOmaMsg" />
					</div>
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.title.isUseNextNoncePerPkg" />
				</th>
				<td>
					<html:checkbox property="isUseNextNoncePerPkg" />
					<div class="validateErrorMsg">
						<html:errors property="isUseNextNoncePerPkg" />
					</div>
				</td>
			</tr>
		</tbody>
	</table>
	<div class="buttonArea">
		<logic:empty name="ModelForm" property="ID">
			<html:submit styleClass="NormalWidthButton">
				<bean:message key="page.button.create.label" />
			</html:submit>
		</logic:empty>
		<logic:notEmpty name="ModelForm" property="ID">
			<html:submit styleClass="NormalWidthButton">
				<bean:message key="page.button.update.label" />
			</html:submit>
		</logic:notEmpty>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<html:reset styleClass="NormalWidthButton">
			<bean:message key="page.button.reset.label" />
		</html:reset>
		<html:cancel styleClass="NormalWidthButton">
			<bean:message key="page.button.cancel.label" />
		</html:cancel>
	  <logic:notEmpty name="ModelForm" property="ID">
		    <html:button property="action" onclick="document.forms['ModelForm'][1].submit();" styleClass="NormalWidthButton">
		      <bean:message key="page.button.view.label"/>
		    </html:button>
	  </logic:notEmpty>
	</div>
</html:form>

<html:form action="/ViewModel" method="post">
  <logic:notEmpty name="ModelForm" property="ID">
    <html:hidden property="ID"/>
  </logic:notEmpty>
</html:form>
