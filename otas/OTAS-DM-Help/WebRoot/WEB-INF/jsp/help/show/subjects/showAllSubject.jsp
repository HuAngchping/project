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
  <html:link action="/help/show/subjects/showAllSubject">
    <bean:message key="page.help.common.page.message.location.list.label" />
  </html:link>
  <logic:iterate id="subject" name="subjects4Location">
    >> <html:link action="/help/show/subjects/showAllSubject" paramId="subjectId" paramName="subject" paramProperty="subjectId">
      <bean:write name="subject" property="externalId" />
    </html:link>
  </logic:iterate>
</div>

<%-- Search panel --%>
<html:form action="help/show/subjects/showAllSubject" method="post">
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
  <display:table name="subjects" id="subject" class="simple" pagesize="10" requestURI="<%=request.getContextPath() + "/help/show/subjects/showAllSubject.do"%>">
      <display:column class="rowNum">
        <c:out value="${subject_rowNum}" />
      </display:column>
      <display:column property="externalId" titleKey="page.help.show.showAllSubject.page.message.subject.name.lable"
        href="<%=request.getContextPath() + "/help/show/subjects/showSubjectContent.do?action=view"%>" paramId="subjectId" paramProperty="subjectId">
      </display:column>
  </display:table>
