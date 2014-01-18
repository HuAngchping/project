<%@ page import="com.npower.dm.action.BaseAction" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>


<fmt:bundle basename="com.npower.dm.struts.ApplicationResources">
</fmt:bundle>
<%-- BaseAction.getRecordsPerPage(request) --%>
<display:table name="auditLogs" id="record" class="simple" pagesize="<%=BaseAction.getRecordsPerPage(request) %>" requestURI="<%=request.getContextPath() + "/audit/Auditlogs.do"%>">
	<display:column class="rowNum">
		<c:out value="${record_rowNum}" />
	</display:column>
	<display:column property="action.description" sortable="true" sortName="action.description" titleKey="page.audit.logs.actionType.description.label" style="white-space: nowrap;"/>
	<display:column property="userName" sortable="true" sortName="userName"  titleKey="page.audit.logs.userName.label" style="white-space: nowrap;"/>
	<display:column property="ipAddress" sortable="true" sortName="ipAddress"  titleKey="page.audit.logs.ipAddress.label" style="white-space: nowrap;"/>
	<display:column property="creationDate" sortable="true" sortName="creationDate"  titleKey="page.audit.logs.time.label" style="white-space: nowrap;"/>
	<display:column titleKey="page.audit.logs.targets.label">
	
	   <!-- Audit Type: Device -->
	   <logic:equal name="record" property="action.type" value="Device">
	     <!-- Targets: Device -->
	     <logic:iterate id="target" name="record" property="targets">
	        <bean:message key="page.audit.logs.target.deviceID.label"/>: <bean:write name="target" property="deviceId"/>, 
	        <bean:message key="page.audit.logs.target.deviceExtID.label"/>: <bean:write name="target" property="deviceExternalId"/><br>
	     </logic:iterate>
       </logic:equal>
       
	   <!-- Audit Type: Manufacturer -->
	   <logic:equal name="record" property="action.type" value="Manufacturer">
	     <!-- Targets: Manufacturer -->
	     <logic:iterate id="target" name="record" property="targets">
	        <bean:message key="page.audit.logs.target.manufacturerID.label"/>: <bean:write name="target" property="manufacturerId"/>, 
	        <bean:message key="page.audit.logs.target.manufacturerExtID.label"/>: <bean:write name="target" property="manufacturerExternalId"/><br>
	     </logic:iterate>
       </logic:equal>

	   <!-- Audit Type: Model -->
	   <logic:equal name="record" property="action.type" value="Model">
	     <!-- Targets: Model -->
	     <logic:iterate id="target" name="record" property="targets">
	        <bean:message key="page.audit.logs.target.modelID.label"/>: <bean:write name="target" property="modelId"/>, 
	        <bean:message key="page.audit.logs.target.modelExtID.label"/>: <bean:write name="target" property="modelName"/><br>
	     </logic:iterate>
       </logic:equal>

	   <!-- Audit Type: Update -->
	   <logic:equal name="record" property="action.type" value="Update">
	     <!-- Targets: Update -->
	     <logic:iterate id="target" name="record" property="targets">
	        <bean:message key="page.audit.logs.target.modelID.label"/>: <bean:write name="target" property="modelId"/>, 
	        <bean:message key="page.audit.logs.target.modelExtID.label"/>: <bean:write name="target" property="modelName"/>,
	        <bean:message key="page.audit.logs.target.updateID.label"/>: <bean:write name="target" property="updateId"/><br>
	     </logic:iterate>
       </logic:equal>

	   <!-- Audit Type: Country -->
	   <logic:equal name="record" property="action.type" value="Country">
	     <!-- Targets: Model -->
	     <logic:iterate id="target" name="record" property="targets">
	        <bean:message key="page.audit.logs.target.countryID.label"/>: <bean:write name="target" property="countryId"/>, 
	        <bean:message key="page.audit.logs.target.countryExtID.label"/>: <bean:write name="target" property="countryExternalId"/><br>
	     </logic:iterate>
       </logic:equal>

	   <!-- Audit Type: Carrier -->
	   <logic:equal name="record" property="action.type" value="Carrier">
	     <!-- Targets: Model -->
	     <logic:iterate id="target" name="record" property="targets">
	        <bean:message key="page.audit.logs.target.carrierID.label"/>: <bean:write name="target" property="carrierId"/>, 
	        <bean:message key="page.audit.logs.target.carrierExtID.label"/>: <bean:write name="target" property="carrierExternalId"/><br>
	     </logic:iterate>
       </logic:equal>

	   <!-- Audit Type: DDF -->
	   <logic:equal name="record" property="action.type" value="DDF">
	     <!-- Targets: Model -->
	     <logic:iterate id="target" name="record" property="targets">
	        <bean:message key="page.audit.logs.target.modelID.label"/>: <bean:write name="target" property="modelId"/>, 
	        <bean:message key="page.audit.logs.target.modelExtID.label"/>: <bean:write name="target" property="modelName"/><br>
	     </logic:iterate>
       </logic:equal>

	   <!-- Audit Type: Profile -->
	   <logic:equal name="record" property="action.type" value="Profile">
	     <!-- Targets: Profile -->
	     <logic:iterate id="target" name="record" property="targets">
	        <bean:message key="page.audit.logs.target.profileID.label"/>: <bean:write name="target" property="profileId"/>, 
	        <bean:message key="page.audit.logs.target.profileName.label"/>: <bean:write name="target" property="profileName"/><br>
	     </logic:iterate>
       </logic:equal>

	   <!-- Audit Type: ProfileMapping -->
	   <logic:equal name="record" property="action.type" value="ProfileMapping">
	     <!-- Targets: ProfileMapping -->
	     <logic:iterate id="target" name="record" property="targets">
	        <bean:message key="page.audit.logs.target.modelID.label"/>: <bean:write name="target" property="modelId"/>, 
	        <bean:message key="page.audit.logs.target.modelExtID.label"/>: <bean:write name="target" property="modelName"/>,
	        <bean:message key="page.audit.logs.target.profileTemplateID.label"/>: <bean:write name="target" property="profileId"/>, 
	        <bean:message key="page.audit.logs.target.profileTemplateName.label"/>: <bean:write name="target" property="profileName"/><br>
	     </logic:iterate>
       </logic:equal>

	   <!-- Audit Type: ProfileTemplate -->
	   <logic:equal name="record" property="action.type" value="ProfileTemplate">
	     <!-- Targets: ProfileTemplate -->
	     <logic:iterate id="target" name="record" property="targets">
	        <bean:message key="page.audit.logs.target.profileTemplateID.label"/>: <bean:write name="target" property="profileTemplateId"/>, 
	        <bean:message key="page.audit.logs.target.profileTemplateName.label"/>: <bean:write name="target" property="profileTemplateName"/><br>
	     </logic:iterate>
       </logic:equal>

	   <!-- Audit Type: ProfileAssignment -->
	   <logic:equal name="record" property="action.type" value="ProfileAssignment">
	     <!-- Targets: ProfileAssignment -->
	     <logic:iterate id="target" name="record" property="targets">
	        <bean:message key="page.audit.logs.target.deviceID.label"/>: <bean:write name="target" property="deviceId"/>, 
	        <bean:message key="page.audit.logs.target.deviceExtID.label"/>: <bean:write name="target" property="deviceExternalId"/>,
	        <bean:message key="page.audit.logs.target.profileTemplateID.label"/>: <bean:write name="target" property="profileAssignmentId"/>, 
	        <bean:message key="page.audit.logs.target.profileTemplateName.label"/>: <bean:write name="target" property="profileName"/><br>
	     </logic:iterate>
       </logic:equal>

	   <!-- Audit Type: User -->
	   <logic:equal name="record" property="action.type" value="User">
	     <!-- Targets: Model -->
	     <logic:iterate id="target" name="record" property="targets">
	        <bean:message key="page.audit.logs.target.username.label"/>: <bean:write name="target" property="userName"/><br>
	     </logic:iterate>
       </logic:equal>

	   <!-- Audit Type: ProvisionJob -->
	   <logic:equal name="record" property="action.type" value="Provisioning">
	     <!-- Targets: Profile -->
	     <logic:iterate id="target" name="record" property="targets">
	        <bean:message key="page.audit.logs.target.jobID.label"/>: <bean:write name="target" property="provReqId"/><br>
	     </logic:iterate>
       </logic:equal>
       
     <!-- Audit Type: Software -->
     <logic:equal name="record" property="action.type" value="Software">
       <!-- Targets: Device -->
       <logic:iterate id="target" name="record" property="targets">
          <bean:message key="page.audit.logs.target.softwareName.label"/>: <bean:write name="target" property="softwareName"/>, 
          <bean:message key="page.audit.logs.target.softwareVendor.label"/>: <bean:write name="target" property="softwareVendor"/>,
          <bean:message key="page.audit.logs.target.softwareCategory.label"/>: <bean:write name="target" property="softwareCategory"/><br>
       </logic:iterate>
     </logic:equal>       
       
     <!-- Audit Type: SoftwareVendor -->
     <logic:equal name="record" property="action.type" value="SoftwareVendor">
       <!-- Targets: SoftwareVendor -->
       <logic:iterate id="target" name="record" property="targets">
          <bean:message key="page.audit.logs.target.softwareVendorID.label"/>: <bean:write name="target" property="vendorId"/>, 
          <bean:message key="page.audit.logs.target.softwareVendorExtID.label"/>: <bean:write name="target" property="vendorExtId"/>
       </logic:iterate>
     </logic:equal>       

     <!-- Audit Type: CP Template -->
     <logic:equal name="record" property="action.type" value="ClientProvTemplate">
       <!-- Targets: CP Template -->
       <logic:iterate id="target" name="record" property="targets">
          <bean:message key="page.audit.logs.target.clientProvTemplateId.label"/>: <bean:write name="target" property="clientProvTemplateId"/>, 
          <bean:message key="page.audit.logs.target.clientProvTemplateName.label"/>: <bean:write name="target" property="clientProvTemplateName"/>,
       </logic:iterate>
     </logic:equal>       


     <!-- Audit Type: SoftwareCategory -->
     <logic:equal name="record" property="action.type" value="SoftwareCategory">
       <!-- Targets: SoftwareCategory -->
       <logic:iterate id="target" name="record" property="targets">
          <bean:message key="page.audit.logs.target.softwareCategoryId.label"/>: <bean:write name="target" property="softwareCategoryId"/>,
          <logic:notEmpty name="target" property="softwareCategory">           
            <bean:message key="page.audit.logs.target.softwareCategoryName.label"/>: <bean:write name="target" property="softwareCategory"/>, 
          </logic:notEmpty>
          <logic:notEmpty name="target" property="softwareCategoryParent"> 
            <bean:message key="page.audit.logs.target.softwareCategoryParent.label"/>: <bean:write name="target" property="softwareCategoryParent"/><br>          
          </logic:notEmpty>
       </logic:iterate>
     </logic:equal>       

	</display:column>
	<%-- <display:column property="description" sortable="true" sortName="description"  titleKey="page.audit.logs.description.label"/>--%>
</display:table>
