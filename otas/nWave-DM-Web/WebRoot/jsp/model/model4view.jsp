<%@ page language="java"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/otas-dm.tld" prefix="dm" %>

<logic:notEmpty name="ModelForm" property="ID">
	<div class="buttonArea" style="height: 40px;">
	  <dm:permission roles="dm.admin.models,dm.operator.manufacturer">
		
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
      </dm:permission>
      
		<bean:define id="modelID" name="ModelForm" property="ID"></bean:define>
		<form action="<html:rewrite action="/ViewModelSpec"/>" target="_blank">
			<input type="hidden" name="modelID" value="<c:out value="${modelID}"/>">
			<input type="hidden" name="method" value="getModelCapabilityMatrix">
			<html:submit styleClass="MiddleWidthButton"><bean:message key="model.view.capabilities.button.label"/></html:submit>
		</form>
	</div>
</logic:notEmpty>

<html:form action="/SaveModel">
  <input type="hidden" name="action" value="update"/>
	<html:hidden property="ID" />
	<table class="entityview">
		<thead>
			<tr>
				<th colspan="2">
					&nbsp;<bean:write name="model" property="manufacturer.name"/> <bean:write name="model" property="manufacturerModelId"/>
				</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<th></th>
				<td><html:img action="/ViewModelSpec?method=getModelIcon" paramId="modelID" paramName="model" paramProperty="ID" height="110"/></td>
			</tr>
			<tr>
				<th>
					* <bean:message key="model.title.manufacturer" />
				</th>
				<td><bean:write name="model" property="manufacturer.name"/></td>
			</tr>
			<tr>
				<th>
					* <bean:message key="model.title.manufacturerModelId" />
				</th>
				<td><bean:write name="model" property="manufacturerModelId"/></td>
			</tr>
			<tr>
				<th width="250">
					* <bean:message key="model.title.name" />
				</th>
				<td><bean:write name="model" property="name"/></td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.title.taclist"/><br>
				</th>
				<td>
				<logic:iterate id="tac" name="model" property="modelTAC"><bean:write name="tac"/>, </logic:iterate>
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.spec.announced.time.label" />
				</th>
				<td><logic:present name="announced"><bean:write name="announced"/></logic:present></td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.spec.os.label" />
				</th>
				<td><logic:present name="os"><bean:write name="os"/></logic:present></td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.spec.developer.platform.label" />
				</th>
				<td><logic:present name="platform"><bean:write name="platform"/></logic:present></td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.title.description" />
				</th>
				<td><bean:write name="model" property="description"/></td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.title.supportedDownloadMethods" />
				</th>
				<td>
					<html:img page="/common/images/icons/${model.supportedDownloadMethods}.gif" alt="${model.supportedDownloadMethods}"></html:img>
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.title.isOmaDmEnabled" />
				</th>
				<td>
					<html:img page="/common/images/icons/${model.isOmaDmEnabled}.gif" alt="${model.isOmaDmEnabled}"></html:img>
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.title.OmaDmVersion" />
				</th>
				<td><bean:write name="model" property="omaDmVersion"/></td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.title.isOmaCpEnabled" />
				</th>
				<td>
					<html:img page="/common/images/icons/${model.isOmaCpEnabled}.gif" alt="${model.isOmaCpEnabled}"></html:img>
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.title.OmaCpVersion" />
				</th>
				<td><bean:write name="model" property="omaCpVersion"/></td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.title.isNokiaOtaEnabled" />
				</th>
				<td>
					<html:img page="/common/images/icons/${model.isNokiaOtaEnabled}.gif" alt="${model.isNokiaOtaEnabled}"></html:img>
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.title.NokiaOtaVersion" />
				</th>
				<td><bean:write name="model" property="nokiaOtaVersion"/></td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.title.serverAuthType" />
				</th>
				<td><bean:write name="model" property="serverAuthType"/></td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.title.firmwareVersionNode" />
				</th>
				<td><bean:write name="model" property="firmwareVersionNode"/></td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.title.firmwareUpdateNode" />
				</th>
				<td><bean:write name="model" property="firmwareUpdateNode"/></td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.title.firmwareDownloadNode" />
				</th>
				<td><bean:write name="model" property="firmwareDownloadNode"/></td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.title.firmwareDownloadAndUpdateNode" />
				</th>
				<td><bean:write name="model" property="firmwareDownloadAndUpdateNode"/></td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.title.firmwareStatusNode" />
				</th>
				<td><bean:write name="model" property="firmwareStatusNode"/></td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.title.isPlainProfile" />
				</th>
				<td>
					<html:img page="/common/images/icons/${model.isPlainProfile}.gif" alt="${model.isPlainProfile}"></html:img>
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.title.isUseEncForOmaMsg" />
				</th>
				<td>
					<html:img page="/common/images/icons/${model.isUseEncForOmaMsg}.gif" alt="${model.isUseEncForOmaMsg}"></html:img>
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.title.isUseNextNoncePerPkg" />
				</th>
				<td>
					<html:img page="/common/images/icons/${model.isUseNextNoncePerPkg}.gif" alt="${model.isUseNextNoncePerPkg}"></html:img>
				</td>
			</tr>
		</tbody>
	</table>

	<div class="buttonArea">
	  <dm:permission roles="dm.admin.models,dm.operator.manufacturer">
	  <logic:notEmpty name="ModelForm" property="ID">
		    <html:button property="action" onclick="document.forms['ModelForm'][1].submit();" styleClass="NormalWidthButton">
		      <bean:message key="page.button.edit.label"/>
		    </html:button>
	  </logic:notEmpty>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      </dm:permission> 
	  <html:cancel styleClass="NormalWidthButton">
		<bean:message key="page.button.cancel.label" />
	  </html:cancel>
	</div>
</html:form>

<html:form action="/EditModel" method="post">
  <logic:notEmpty name="ModelForm" property="ID">
    <input type="hidden" name="action" value="update"/>
    <html:hidden property="ID"/>
  </logic:notEmpty>
</html:form>
