<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<fmt:bundle basename="com.npower.help.struts.ApplicationResources">
</fmt:bundle>
<%-- Locale panel --%>
<div class="locationArea" style="clear: both;">
  <bean:message key="page.help.common.page.message.location.label" />
  <html:link action="/help/admin/subjects/subjects">
    <bean:message key="page.help.common.page.message.location.list.label" />
  </html:link>
  <logic:iterate id="subject" name="subjects4Location">
    >> <html:link action="/help/admin/subjects/subjects" paramId="subjectId" paramName="subject" paramProperty="subjectId">
      <bean:write name="subject" property="externalId" />
    </html:link>
  </logic:iterate>
</div>

<%-- Search panel --%>
<html:form action="/help/admin/subjects/subjects" method="post">
  <div class="locationArea">
    <logic:iterate id="subject" name="subjects4Location">
      <html:hidden property="subjectId" value="${subject.subjectId}"/>
    </logic:iterate>
    <bean:message key="page.help.common.form.message.search.label" />
    <html:text property="searchText" />
    <html:submit property="button">
      <bean:message key="page.help.common.form.message.search.button.label" />
    </html:submit>
  </div>
</html:form>

<%-- List panel --%>
<html:form action="/help/admin/subjects/moveSubject.do?method=move" style="clear: both;width:100%;">
  <display:table name="subjects" id="subject" class="simple" pagesize="10" requestURI="<%=request.getContextPath() + "/help/admin/subjects/subjects.do"%>">
	    <display:column class="rowNum">
	      <c:out value="${subject_rowNum}" />
	    </display:column>
	    <display:column>
	      <input type="checkbox" name="move4subjectId" value="${subject.subjectId}" />
	    </display:column>
	    <display:column property="externalId" titleKey="page.help.admin.subjects.page.message.subject.name.label"
	      href="<%=request.getContextPath() + "/help/admin/subjects/viewContent.do?action=view"%>" paramId="subjectId" paramProperty="subjectId">
	    </display:column>
	    <display:column>
	      <html:link action="/help/admin/subjects/editSubject.do?method=edit" paramId="subjectId" paramName="subject" paramProperty="subjectId">
	        <bean:message key="page.help.admin.subjects.page.message.laws.label" />
	      </html:link>
	      <html:link action="/help/admin/subjects/deleteSubject" paramId="subjectId" paramName="subject" paramProperty="subjectId">
	        <bean:message key="page.help.admin.subjects.page.message.delete.label" />
	      </html:link>
	      <html:link action="/help/admin/subjects/viewContent" paramId="subjectId" paramName="subject" paramProperty="subjectId">
	        <bean:message key="page.help.admin.subjects.page.message.view.label" />
	      </html:link>
	    </display:column>
  </display:table>
</html:form>
<html:form action="/help/admin/subjects/editSubject.do?method=forword" method="post" style="clear: both;width:100%;">
  <div class="buttonArea">
    <html:submit property="action">
      <bean:message key="page.help.admin.subjects.page.message.create.subject.button.label" />
    </html:submit>
    <html:button property="method" onclick="document.forms[1].submit();">
      <bean:message key="page.help.admin.subjects.page.message.move.button.label" />
    </html:button>
  </div>
</html:form>
