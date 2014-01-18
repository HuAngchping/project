<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<jsp:include page="/jsp/common/header.jsp"></jsp:include>
	<tiles:importAttribute name="title" scope="page"/>
	<title><bean:message key="page.title.prefix" /> - <bean:message key="${title}"/></title>
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
        <br/>
        <!-- Menu: left pane begins -->
		    <tiles:insert attribute="SubMenus" />
		
		    <!-- Search pannel -->
		    <tiles:insert attribute="SearchPannel" ignore="true"/>
		
        <!-- Menu: left pane ends //-->
        </div>
      </td>
      <td width="81%" valign="top" class="indent20px"> 
		    <!-- Include the Location Pannel -->
		    <jsp:include page="/jsp/common/location.jsp"></jsp:include>

		    <!-- Work pannel -->
		    <tiles:insert attribute="MainContent" />
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
</html:html>
