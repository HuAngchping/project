<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.npower.dm.action.BaseAction" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<div class="messageArea">
  <bean:message key="page.profileConfigs.message" /><br><br>
</div>

<display:table name="results" id="record" requestURI="<%=request.getContextPath() + "/ProfileConfigs.do"%>"  pagesize="<%=BaseAction.getRecordsPerPage(request)%>" class="simple">
    <display:column class="rowNum" 
                    href="<%=request.getContextPath() + "/ViewProfileConfig.do"%>" paramId="value(ID)" paramProperty="ID" >
      <c:out value="${record_rowNum}"/>
    </display:column>
	<display:column property="name" sortable="true" titleKey="profile.config.name.label" 
	                href="<%=request.getContextPath() + "/ViewProfileConfig.do"%>" paramId="value(ID)" paramProperty="ID" />
	<display:column property="profileType" sortable="true" titleKey="profile.config.profileType.lable" 
	                href="<%=request.getContextPath() + "/ViewProfileConfig.do"%>" paramId="value(ID)" paramProperty="ID" />
	<display:column sortable="true" titleKey="profile.config.template.label">
	                <html:link action="/ViewProfileTemplate.do" styleClass="reference_link" target="_blank" paramId="ID" paramName="record" paramProperty="profileTemplate.ID"><c:out value="${record.profileTemplate.name}"/></html:link>
	</display:column>
	<display:column property="carrier.name" sortable="true" titleKey="profile.config.carrier.label" 
	                href="<%=request.getContextPath() + "/ViewProfileConfig.do"%>" paramId="value(ID)" paramProperty="ID" />
    <display:column>
      <a href="<html:rewrite page='/ViewProfileConfig.do'/>?value(ID)=<c:out value='${record.ID}'/>"><bean:message key="page.button.view.label"/></a> | 
      <a href="<html:rewrite page='/EditProfileConfig.do'/>?value(ID)=<c:out value='${record.ID}'/>"><bean:message key="page.button.edit.label"/></a> | 
      <a href="<html:rewrite page='/RemoveProfileConfig.do'/>?ID=<c:out value='${record.ID}'/>" onclick='javascript: return confirm("<bean:message key="message.delete.areYourSureDelete"/>");'><bean:message key="page.button.remove.label"/></a>
    </display:column>
</display:table>
<div class="buttonArea" style="height: 40px;">
	<html:form action="EditProfileConfig">
	  <html:submit><bean:message key="page.profileconfigs.button.addConfig"/></html:submit>
	</html:form>
</div>