<%@ page language="java" pageEncoding="UTF-8"%>

<%@ page import="com.npower.dm.util.SystemInfo"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<html>
<head>
<head>
	<html:base />
	<TITLE><bean:message key="page.title.prefix" /> - <bean:message key="pageTitle.language.title" /></TITLE>
	<jsp:include page="/jsp/common/header.jsp"></jsp:include>
	
<script language="JavaScript" type="text/javascript">
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
          <TD width=418><IMG alt="" src="<html:rewrite page='/images/top_left.gif'/>"></TD>
          <TD background="<html:rewrite page='/images/bg001.gif'/>">&nbsp;</TD>
          <TD width=413><IMG alt="" src="<html:rewrite page='/images/top_right.gif'/>"></TD>
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
    <TD vAlign=middle align="center" height=60%>
      <table border="0" cellspacing="0" cellpadding="0" class="languagetitle">
        <tr>
          <td align="left" class="login" height=30><bean:message key="page.language.message"/></td>
        </tr>
        <tr>
          <td align="left" class="login" height=30><img src="<html:rewrite page='/images/icon1.gif'/>"/> <a href="<html:rewrite page='/ChangeLocale.do'/>"><bean:message key="page.language.default.label"/></a></td>
        </tr>
        <tr>
          <td align="left" class="login" height=30><img src="<html:rewrite page='/images/icon1.gif'/>"/> <a href="<html:rewrite page='/ChangeLocale.do?language=en'/>"><bean:message key="page.language.english.label"/></a></td>
        </tr>
        <tr>
          <td align="left" class="login" height=30><img src="<html:rewrite page='/images/icon1.gif'/>"/> <a href="<html:rewrite page='/ChangeLocale.do?language=zh&country=CN'/>"><bean:message key="page.language.simChinese.label"/></a></td>
        </tr>
        <tr>
          <td align="left" class="login" height=30><img src="<html:rewrite page='/images/icon1.gif'/>"/> <a href="<html:rewrite page='/ChangeLocale.do?language=zh'/>"><bean:message key="page.language.traChinese.label"/></a></td>
        </tr>
      </table>
    </TD>
  </TR>
  </TBODY>
</TABLE>
<TABLE height=24 cellSpacing=0 cellPadding=0 width="100%" border=0 background="<html:rewrite page='/images/bg001.gif'/>">
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
