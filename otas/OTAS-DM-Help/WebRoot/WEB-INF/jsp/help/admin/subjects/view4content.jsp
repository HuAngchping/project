<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
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

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<style type="text/css">
<!-- /*- Menu Tabs 1--------------------------- */
#locale_table {
	float: left;
	width: 100%;
	background: #F4F7FB;
	font-size: 93%;
	line-height: normal;
	border-bottom: 1px solid #BCD2E6;
}

#locale_table ul {
	margin: 0;
	padding: 10px 10px 0 20px;
	list-style: none;
}

#locale_table li {
	display: inline;
	margin: 0;
	padding: 0;
}

#locale_table a {
	float: left;
	background: url("common/images/tabbedMenu/tableft1.gif") no-repeat left
		top;
	margin: 0;
	padding: 0 0 0 4px;
	text-decoration: none;
}

#locale_table a span {
	float: left;
	display: block;
	background: url("common/images/tabbedMenu/tabright1.gif") no-repeat
		right top;
	padding: 5px 15px 4px 6px;
	color: #627EB7;
}

/* Commented Backslash Hack hides rule from IE5-Mac \*/
#locale_table a span {
	float: none;
}

/* End IE5-Mac hack */
#locale_table a:hover span {
	color: #627EB7;
}

#locale_table a:hover {
	background-position: 0% -42px;
}

#locale_table a:hover span {
	background-position: 100% -42px;
}

#locale_table #current a {
	background-position: 0% -42px;
}

#locale_table #current a span {
	background-position: 100% -42px;
}
-->
</style>
<%-- Locale panel --%>
<div class="locationArea">
	<bean:message
		key="page.help.common.page.message.location.label" />
	<html:link action="/help/admin/subjects/subjects">
		<bean:message
			key="page.help.common.page.message.location.list.label" />
	</html:link>
	<logic:iterate id="subject" name="subjects4Location">
    >> <html:link action="/help/admin/subjects/subjects"
			paramId="subjectId" paramName="subject" paramProperty="subjectId">
			<bean:write name="subject" property="externalId" />
		</html:link>
	</logic:iterate>
</div>

<div id="locale_table">
	<ul>
		<logic:iterate id="subjectLocale" name="localeList">
			<li>
				<logic:equal name="currentLocale" property="id"
					value="${subjectLocale.id}">
					<li id="current">
				</logic:equal>
				<html:link
					action="/help/admin/subjects/viewContent.do?subjectId=${subject.subjectId}"
					paramId="localeId" paramName="subjectLocale" paramProperty="id">
					<span><bean:write name="subjectLocale" property="language" />
						<bean:write name="subjectLocale" property="country" /> </span>
				</html:link>
			</li>
		</logic:iterate>
	</ul>
</div>
<table class="entityview">
	<tr>
		<th nowrap="nowrap">
			<bean:message
				key="page.help.admin.view4content.page.message.subject.name.lable" />
		</th>
		<td>
			<bean:write name="subject" property="externalId" />
		</td>
	</tr>
	<tr>
		<th nowrap="nowrap">
			<bean:message
				key="page.help.admin.view4content.page.message.content.name.label" />
		</th>
		<td>
			<logic:present name="subjectContent">
				<bean:write name="subjectContent" property="name" />
			</logic:present>
		</td>
	</tr>
	<tr>
		<th>
			<bean:message
				key="page.help.admin.view4content.page.message.subject.introduction.lable" />
		</th>
		<td nowrap="nowrap">
			<logic:present name="subjectContent">
				<bean:write name="subjectContent" property="description" />
			</logic:present>
		</td>
	</tr>
	<tr>
		<th nowrap="nowrap">
			<bean:message
				key="page.help.admin.view4content.page.message.subject.keywords.lable" />
		</th>
		<td>
			<logic:present name="subjectContent">
				<bean:write name="subjectContent" property="keywords" />
			</logic:present>
		</td>
	</tr>
	<tr>
		<th>
		</th>
		<td>
			<logic:present name="subjectContent">
				<bean:write name="subjectContent" property="content" filter="false" />
			</logic:present>
		</td>
	</tr>
	<tr>
		<th nowrap="nowrap">
			<bean:message
				key="page.help.admin.view4content.page.message.content.lastupdateTime.label" />
		</th>
		<td>
			<logic:present name="subjectContent">
				<bean:write name="subjectContent" property="lastUpdatedTime" />
			</logic:present>
		</td>
	</tr>
</table>
<html:form action="/help/admin/subjects/editContent">
	<html:hidden property="subjectId" value="${subject.subjectId}" />
	<div class="buttonArea" style="width: 100%;">
		<html:submit property="submit">
			<bean:message
				key="page.help.admin.view4content.form.message.submit.lable" />
		</html:submit>
		<html:button property="button" onclick="document.forms[1].submit();">
			<bean:message
				key="page.help.admin.view4content.form.message.button.lable" />
		</html:button>
	</div>
</html:form>
<html:form action="/help/admin/subjects/subjects">
</html:form>

<logic:present name="childList">
  <display:table name="childList" id="subject" class="simple">
		<display:column class="rowNum">
			<c:out value="${subject_rowNum}" />
		</display:column>
		<display:column property="externalId" titleKey="page.help.admin.subjects.page.message.subject.name.label" href="<%=request.getContextPath() + "/help/admin/subjects/viewContent.do?action=view"%>" paramId="subjectId" paramProperty="subjectId">
		</display:column>	
  </display:table>
</logic:present>
