<%@ page language="java" pageEncoding="UTF-8"%>

<%@ page import="java.util.Locale"%>
<%@ page import="com.npower.dm.util.SystemInfo"%>
<%@ page import="com.npower.dm.action.LocaleAction"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%
    // Restore Locale from cookie
    LocaleAction.restoreLocaleFromCookies(request);
%>
<html>
	<head>
	<head>
		<html:base />
		<TITLE><bean:message key="page.title.prefix" /> - <bean:message key="pageTitle.login.title" /></TITLE>
		<jsp:include page="/jsp/common/header.jsp"></jsp:include>

		<script language="JavaScript" type="text/javascript">


function submitLogon() {
  document.LoginForm.submit();
}
</script>
	</head>
	<body bgcolor="#ffffff" MARGINHEIGHT=0 MARGINWIDTH=0 LEFTMARGIN=0 TOPMARGIN=0>
		<TABLE height=80 cellSpacing=0 cellPadding=0 width="100%" border=0 background="<html:rewrite page='/images/bg001.gif'/>">
			<TBODY>
				<TR>
					<TD vAlign=top height=67>
						<TABLE height=77 cellSpacing=0 cellPadding=0 width="100%" border=0>
							<TBODY>
								<TR>
									<TD width=418>
										<IMG alt="" src="<html:rewrite page='/images/top_left_template.gif'/>">
									</TD>
									<TD background="<html:rewrite page='/images/bg001.gif'/>">
										&nbsp;
									</TD>
									<TD width=413>
										<IMG alt="" src="<html:rewrite page='/images/top_right.gif'/>">
									</TD>
								</TR>
							</TBODY>
						</TABLE>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		<TABLE bgcolor="#ffffff" height=70% cellSpacing=0 cellPadding=0 width="100%" border=0>
			<TBODY>
				<TR>
					<TD vAlign=middle align="center" height=280>

						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td align="left" colspan="2">
									<img alt="Welcome to Mobile care center" src="<html:rewrite page='/images/welcome.gif'/>" border="0">
								</td>
							</tr>
							<tr>
								<td colspan="2" align="right">
									<br>
									<b><font color="red"><html:errors header="common.errors.header" footer="common.errors.footer" prefix="common.errors.prefix" suffix="common.errors.suffix"/></font></b>
								</td>
							</tr>
							<tr>
								<td valign="bottom" width="275">
									<a href="<html:rewrite page='/jsp/common/language.jsp'/>" style="text-decoration:underline"><bean:message key="page.login.selectLanguage.label" /></a>
								</td>
								<td nowrap="nowrap">
									<br>
									<html:form action="/Login" acceptCharset="UTF-8" target="_top" focus="username">
										<input name="language" type="hidden" value="en"/>
										<input name="country" type="hidden" value="US"/>
                    <input name="returnRequestURL" type="hidden" value="<c:out value="${returnRequestURL}"/>"/>
										<table border="0" cellspacing="0" cellpadding="3">
											<tr>
												<td valign="middle" nowrap="nowrap">
													<b><bean:message key="page.login.username.label" />:&nbsp;</b>
												</td>
												<td valign="bottom" align="left">
													<html:text property="username" size="32"></html:text>
												</td>
											</tr>
											<tr>
												<td valign="middle" nowrap="nowrap">
													<b><bean:message key="page.login.password.label" />:&nbsp;</b>
												</td>
												<td valign="bottom" align="left">
													<html:password property="password" size="32" onkeydown="if (event.keyCode==13) submitLogon();"></html:password>
												</td>
											</tr>
											<tr>
												<td colspan="2" align="right">
													<a id="loginClick" href="Javascript:submitLogon();" onmouseout="document.images['logon'].src='<html:rewrite page='/images/login.gif'/>'; window.status=location; return true"
														onmouseover="document.images['logon'].src='<html:rewrite page='/images/login_f2.gif'/>'; return true"> <img vspace="4" border="0" name="logon" src="<html:rewrite page='/images/login.gif'/>" alt="Login to DM Manager"> </a>
												</td>
											</tr>
											<tr>
												<td colspan="2">
												</td>
											</tr>
											<tr>
												<td colspan="2">
												</td>
											</tr>
										</table>
									</html:form>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<br>
								</td>
							</tr>
						</table>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		<TABLE height=36 cellSpacing=0 cellPadding=0 width="100%" border=0 background="<html:rewrite page='/images/bg001.gif'/>">
			<TBODY>
				<TR>
					<TD vAlign=middle>
					  <DIV class="copyright" align="center">
					     <bean:message key="page.copyright"/><BR>
					     <bean:message key="DM.product.name"/> <%=SystemInfo.getVersionInfo()%>
					  </DIV>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
	</BODY>
</html>
