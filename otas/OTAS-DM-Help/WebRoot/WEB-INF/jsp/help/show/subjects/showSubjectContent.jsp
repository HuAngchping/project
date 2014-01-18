<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested"	prefix="nested"%>
<%@ taglib uri="http://struts-menu.sf.net/tag" prefix="menu"%>
<%@ taglib uri="http://struts-menu.sf.net/tag-el" prefix="menu-el"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<fmt:bundle basename="com.npower.help.struts.ApplicationResources">
</fmt:bundle>

<%-- Locale panel --%>
<div class="locationArea">
	<bean:message key="page.help.common.page.message.location.label" />
	<html:link action="/help/show/subjects/showAllSubject">
		<bean:message key="page.help.common.page.message.location.list.label" />
	</html:link>
	<logic:iterate id="subject" name="subjects4Location">
    >> <html:link action="/help/show/subjects/showAllSubject"
			paramId="subjectId" paramName="subject" paramProperty="subjectId">
			<bean:write name="subject" property="externalId" />
		</html:link>
	</logic:iterate>
</div>
<hr width="80%" size="3" color="#00ffff" style="FILTER: alpha(opacity=100, finishopacity=0, style=3)">
<div align="center">
	<FONT size="5" dir="ltr"><bean:write name="subject"
			property="externalId" /> </FONT>
</div>
<hr width="100%" size="1" color="#00ffff" align="center" noshade>
<div align="right">
	<b> <logic:present name="subjectContent">
			<logic:notEmpty name="${subjectContent.keywords}">
				<bean:message
					key="page.help.show.showSubjectContent.page.message.subject.keywords.lable" />
				<bean:write name="subjectContent" property="keywords" />
			</logic:notEmpty>
			<bean:message
				key="page.help.show.showSubjectContent.page.message.content.lastupdateTime.label" />
			<bean:write name="subjectContent" property="lastUpdatedTime" />
			<br>
		</logic:present> </b>
</div>
<div align="left">
	<logic:present name="subjectContent">
		<logic:notEmpty name="${subjectContent.description}">
			<bean:message
				key="page.help.show.showSubjectContent.page.message.subject.introduction.lable" />
			<bean:write name="subjectContent" property="description" />
		</logic:notEmpty>
	</logic:present>
</div>
<div align="left">
	<logic:present name="subjectContent">
		<bean:write name="subjectContent" property="content" filter="false" />
	</logic:present>
</div>

<logic:present name="childList">
	<logic:notEmpty name="childList">
		<display:table name="childList" id="subject" class="simple">
			<display:column class="rowNum">
				<c:out value="${subject_rowNum}" />
			</display:column>
			<display:column property="externalId"
				titleKey="page.help.show.showSubjectContent.page.message.subject.name.lable"
				href="<%=request.getContextPath() + "/help/show/subjects/showSubjectContent.do?action=view"%>"
				paramId="subjectId" paramProperty="subjectId">
			</display:column>
		</display:table>
	</logic:notEmpty>
</logic:present>

