<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://otas.cn/otasdm-taglib" prefix="otasdm"%>

	<table class="entityview">
	      <thead>
	        <tr>
	          <th colspan="2">&nbsp;</th>
	        </tr>
	      </thead>
		  <tbody>
		    <tr>
		      <th width="200"><bean:message key="page.cp.audit.logs.timeStamp.label"/></th>
		      <td><bean:write name="cpAuditLog" property="timeStamp"/></td>
		    </tr>
		    <tr>
		      <th><bean:message key="page.cp.audit.searchPannel.phoneNumber.label"/></th>
		      <td><bean:write name="cpAuditLog" property="devicePhoneNumber"/></td>
		    </tr>
		    <tr>
		      <th><bean:message key="page.cp.audit.searchPannel.carrier.label"/></th>
		      <td><bean:write name="cpAuditLog" property="carrierExtID"/></td>
		    </tr>
		    <tr>
		      <th><bean:message key="page.cp.audit.searchPannel.manufacturer.label"/></th>
		      <td><bean:write name="cpAuditLog" property="manufacturerExtID"/></td>
		    </tr>
		    <tr>
		      <th><bean:message key="page.cp.audit.logs.model.label"/></th>
		      <td><bean:write name="cpAuditLog" property="modelExtID"/></td>
		    </tr>
		    <tr>
		      <th><bean:message key="page.cp.audit.searchPannel.profileName.label"/></th>
		      <td><bean:write name="cpAuditLog" property="profileName"/></td>
		    </tr>
		    <tr>
		      <th><bean:message key="page.cp.audit.logs.profileCategory.label"/></th>
		      <td><bean:write name="cpAuditLog" property="profileCategoryName"/></td>
		    </tr>
		    <tr>
		      <th><bean:message key="page.cp.audit.logs.status.label"/></th>
		      <td><bean:message key="meta.job.cp.state.${cpAuditLog.status}.label"/></td>
		    </tr>
		    <tr>
		      <th><bean:message key="page.cp.audit.logs.profileContent.label"/></th>
		      <td><textarea class="view_script" readonly="readonly"><otasdm:xmlPretty content="${cpAuditLog.profileContent}"/></textarea></td>
		    </tr>	
		    <tr>
		      <th><bean:message key="page.cp.audit.logs.memo.label"/></th>
		      <td><bean:write name="cpAuditLog" property="memo"/></td>
		    </tr>		    	    		    
		  </tbody>
		</table>
				
  <div class="buttonArea" style="height: 40px;">
	 <html:form action="/audit/ViewCPAuditlog" method="post">
	  <html:cancel styleClass="NormalWidthButton"><bean:message key="page.button.return.label"/></html:cancel>
	  <logic:present name="cpAuditLogNextID">
	    <INPUT type="hidden" name="ID" value="<bean:write name="cpAuditLogNextID"/>">
	    <html:submit styleClass="NormalWidthButton"><bean:message key="page.button.previousRecord.label"/></html:submit>	  
	  </logic:present>
	</html:form>	
	<html:form action="/audit/ViewCPAuditlog" method="post"> 
	 <logic:present name="cpAuditLogPrevID">
	   &nbsp;&nbsp;<INPUT type="hidden" name="ID" value="<bean:write name="cpAuditLogPrevID"/>">
	   <html:submit styleClass="NormalWidthButton"><bean:message key="page.button.nextRecord.label"/></html:submit>
	  </logic:present> 
	</html:form>	
  </div>
