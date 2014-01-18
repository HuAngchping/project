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

<display:table name="softwares" id="software" class="simple" pagesize="<%=BaseAction.getRecordsPerPage(request)%>" requestURI="./software/softwares.do">
  <display:column class="rowNum" href="./software/software.do?action=view" paramId="id" paramProperty="id">
    <c:out value="${software_rowNum}" />
  </display:column>
  <display:column property="externalId" sortable="true" sortName="externalId"  titleKey="entity.software.software.externalId.label" href="./software/software.do?action=view" paramId="id" paramProperty="id" />
  <display:column property="name" sortable="true" sortName="name"  titleKey="entity.software.software.name.label" href="./software/software.do?action=view" paramId="id" paramProperty="id" />
  <display:column property="version" sortable="true" sortName="version"  titleKey="entity.software.software.version.label" href="./software/software.do?action=view" paramId="id" paramProperty="id" />
  <display:column sortable="true" sortName="name" titleKey="entity.software.category.label" href="./software/software.do?action=view" paramId="id" paramProperty="id" maxLength="12">
    <bean:write name="software" property="category.pathAsString"/>
  </display:column>
  <display:column sortable="true" sortName="name" titleKey="entity.software.vendor.label" href="./software/software.do?action=view" paramId="id" paramProperty="id" maxLength="12">
    <bean:write name="software" property="vendor.name"/>
  </display:column>
  <display:column titleKey="entity.software.software.status.label" href="./software/software.do?action=view" paramId="id" paramProperty="id">
    <bean:message key="meta.software.status.${software.status}.label"/>
  </display:column>
  <display:column>
    <a href="<html:rewrite page='/software/software.do'/>?action=view&id=<c:out value='${software.id}'/>"><bean:message key="page.button.view.label" /></a> | 
    <a href="<html:rewrite page='/software/editSoftware.do'/>?id=<c:out value='${software.id}'/>"><bean:message key="page.button.edit.label" /></a> | 
    <a href="<html:rewrite page='/software/software.do'/>?action=remove&id=<c:out value='${software.id}'/>" onclick='javascript: return confirm("<bean:message key="message.delete.areYourSureDelete"/>");'><bean:message key="page.button.remove.label" /></a>
  </display:column>
</display:table>
<div class="buttonArea" style="height: 40px;">
 <html:form action="/software/editSoftware">
    <html:submit>
      <bean:message key="page.software.softwares.button.add" />
    </html:submit>
 </html:form>
 <!--  
 <html:form action="/software/softwareTree.do?action=display">
    <html:submit>
      <bean:message key="page.software.softwares.button.tree" />
    </html:submit>
 </html:form> 
 -->
 <br>
</div>
