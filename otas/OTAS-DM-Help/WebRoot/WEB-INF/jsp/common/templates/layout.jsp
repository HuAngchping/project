<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<html>
  <head>
    <jsp:include page="/WEB-INF/jsp/common/templates/header.jsp"></jsp:include>
    <tiles:importAttribute name="title" scope="page"/>
    <title><bean:message key="${title}"/> - <bean:message key="otas.help.admin.page.title.prefix"/></title>
  </head>
  <body marginwidth="0" marginheight="0" style="margin: 0;">

    <table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin: 0;">
      <tr class="main_menu_header">
        <td colspan="2" height="95">
          <!-- Main Menu Panel -->
          <tiles:insert attribute="banner" />
        </td>
      </tr>
    </table>
    <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" style="margin: 0;">
      <tr>
        <td width="13%" valign="top" class="righttdbg">
          <div style="width: 245px;">
            <br />
            <!-- Menu: left pane begins -->
            <tiles:insert attribute="menu" />
            <!-- Menu: left pane ends //-->
          </div>
        </td>
        <td width="81%" valign="top" class="indent20px">
          <tiles:insert attribute="content" />
        </td>
      </tr>
      <tr>
        <td colspan="2" height="20" bgcolor="#EFF3F7">
          <!-- Include Copyright information -->
          <tiles:insert attribute="footer" />
        </td>
      </tr>
    </table>
  </body>
</html>
