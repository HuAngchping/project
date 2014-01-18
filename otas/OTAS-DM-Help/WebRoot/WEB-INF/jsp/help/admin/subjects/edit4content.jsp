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
<script type="text/javascript" src="FCKeditor/fckeditor.js"></script>
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
	<logic:present name="currentContent">
		<bean:message
			key="page.help.admin.edit4content.page.message.location.utdate.lable" />
	</logic:present>

	<logic:notPresent name="currentContent">
		<bean:message
			key="page.help.admin.edit4content.page.message.location.create.lable" />
	</logic:notPresent>
</div>
<div align="left">
	<bean:message
		key="page.help.admin.edit4content.page.message.note.lable" />
</div>
<!-- Locale Panel -->
<html:form action="/help/admin/subjects/selectLocal"
	style="padding: 0px; margin: 0;">
	<html:hidden property="subjectId" value="${subject.subjectId}" />
	<html:hidden property="localeId" value="${currentLocale.id}" />
	<div id="locale_table">
		<ul>
			<logic:iterate id="subjectLocale" name="localeList">
				<li>
					<logic:equal name="SubjectLocale" property="id"
						value="${subjectLocale.id}">
						<li id="current">
					</logic:equal>
					<html:link
						action="/help/admin/subjects/editContent.do?subjectId=${subject.subjectId}"
						paramId="localeId" paramName="subjectLocale" paramProperty="id">
						<span><bean:write name="subjectLocale" property="language" />
							<bean:write name="subjectLocale" property="country" /> </span>
					</html:link>
				</li>
			</logic:iterate>
		</ul>
		<div style="text-align: right;">
			<html:submit property="addlocale">
				<bean:message
					key="page.help.admin.edit4content.page.message.addlocale.lable" />
			</html:submit>
		</div>
	</div>
</html:form>

<!-- Edit Panel for Subject Content -->
<html:form action="/help/admin/subjects/saveContent" method="post"
	style="padding: 0px; margin: 0;">
	<html:hidden property="subjectId" value="${subject.subjectId}" />
	<html:hidden property="localeId" value="${currentLocale.id}" />
	<table class="entityview">
		<tr>
			<th nowrap="nowrap">
				<bean:message
					key="page.help.admin.edit4content.page.message.subject.name.lable" />
			</th>
			<td>
				<bean:write name="subject" property="externalId" />
			</td>
		</tr>
		<tr>
			<th nowrap="nowrap">
				<bean:message
					key="page.help.admin.edit4content.form.message.content.title.lable" />
			</th>
			<td>
				<html:text property="name" style="width:400px" />
				<b><font color="red"><html:errors property="name" /> </font> </b>
			</td>
		</tr>
		<tr>
			<th nowrap="nowrap">
				<bean:message
					key="page.help.admin.edit4content.form.message.content.keywords.lable" />
			</th>
			<td>
				<html:text property="keywords" style="width:400px" />
			</td>
		</tr>
		<tr>
			<th nowrap="nowrap">
				<bean:message
					key="page.help.admin.edit4content.form.message.content.introduction.lable" />
			</th>
			<td>
				<html:textarea property="description" style="width:400px" />
			</td>
		</tr>
		<logic:present name="currentContent">
			<tr>
				<th nowrap="nowrap">
					<bean:message key="page.help.admin.edit4content.form.message.content.text.lable" />
				</th>
				<td>
					<html:textarea property="content" />
					<script type="text/javascript">
	            var oFCKeditor = new FCKeditor( 'content' ) ;
	            //oFCKeditor.BasePath = 'FCKeditor/' ;
	            oFCKeditor.ToolbarSet = 'Default' ;
	            oFCKeditor.Width = '620px' ;
	            oFCKeditor.Height = '600' ;
	            oFCKeditor.Value = '' ;
	            oFCKeditor.ReplaceTextarea(); 
	            //oFCKeditor.Create() ;
	        </script>
				</td>
			</tr>
		</logic:present>
	</table>
	<div class="buttonArea">
		<logic:present name="currentContent">
			<html:submit property="method">
				<bean:message
					key="page.help.admin.edit4content.page.message.save.button.lable" />
			</html:submit>
		</logic:present>

		<logic:notPresent name="currentContent">
			<html:submit property="method">
				<bean:message
					key="page.help.admin.edit4content.page.message.create.button.lable" />
			</html:submit>
		</logic:notPresent>
		<html:button property="return" onclick="document.forms[2].submit();">
			<bean:message key="page.help.admin.return.message.button.lable" />
		</html:button>
	</div>
</html:form>

<html:form action="/help/admin/subjects/subjects" method="post">
</html:form>

