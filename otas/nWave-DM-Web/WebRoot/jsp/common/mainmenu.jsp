
<%@ page language="java" pageEncoding="UTF-8"%>

<%@ page import="com.npower.dm.action.Constants"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts-menu.sf.net/tag" prefix="menu" %>
<%@ taglib uri="http://struts-menu.sf.net/tag-el" prefix="menu-el" %>


<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td width="15%" height="65" rowspan="2">
			<img src="<html:rewrite page="/common/images/spacer.gif"/>" width="20" height="1">
			<!-- <a href=""><img src="<html:rewrite page="/common/images/logo.gif"/>" border="0"></a>  -->
		</td>
		<td align="left"><!-- <img src="<html:rewrite page="/common/images/title.png"/>" border="0">  --></td>
		<td height="25" align="right" valign="top">
			<table border="0" cellspacing="0" cellpadding="0">
  		  <!-- Top menus begin -->
				<tr>
					<td width="120" height="20" align="right"
						class="top_menu_black_link"></td>
					<td width="50" height="20" align="right" valign="middle" class="top_menu_black_link">
					    <a href="#" onclick="invokeAbout()" target="_blank" class="top_menu_black_link"><html:img page="/common/images/icons/info.gif"/><bean:message key="page.button.about.label"/></a>
					</td>
					<td width="50" height="20" align="right" valign="middle" class="top_menu_black_link">
						<a href="#" onclick="invokeAbout()" target="_blank" class="top_menu_black_link"><html:img page="/common/images/icons/help.gif"/><bean:message key="page.button.help.label"/></a>
					</td>
					<td width="120" height="20" align="right" valign="middle" class="top_menu_black_link" nowrap title="admin">
					    <html:link action="/Logout" styleClass="top_menu_black_link"><html:img page="/common/images/icons/logout.gif"/><bean:message key="page.button.logout.label"/> [<bean:write name="<%=Constants.ADMIN_USER_KEY %>" property="username"/>]</html:link>
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
		    <!-- Top menus end -->
				<tr>
					<td background="<html:rewrite page="/common/images/bg_horizontalstrip_top1.gif"/>"></td>
					<td colspan="4"
					    background="<html:rewrite page="/common/images/bg_horizontalstrip_top2.gif"/>"><img src="<html:rewrite page="/common/images/spacer.gif"/>" width="5" height="4"></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td width="85%" height="30" colspan="2" align="right" valign="bottom">
		    <!-- defined applicationMenuPermission in com.npower.servlet.StrutsMenuFilter -->
			<menu:useMenuDisplayer name="Velocity" config="/common/css/templates/${CSS_TEMPLATE_NAME}/horizon_tabs.template"
			    permissions="applicationMenuPermission"
			    bundle="org.apache.struts.action.MESSAGE">
			    <menu:displayMenu name="MainMenu"/>
			</menu:useMenuDisplayer>
		</td>
	</tr>
</table>
<!-- Header table ends //-->

<!-- blue strip for links //-->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="toplinksbar">
      <b>[<bean:write name="<%=Constants.ADMIN_USER_KEY %>" property="firstName"/> <bean:write name="<%=Constants.ADMIN_USER_KEY %>" property="lastName"/>] </b> - 
      <%
        // Load locale from Session
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("EEEEEEEE yyyy-MM-dd HH:mm", org.apache.struts.util.RequestUtils.getUserLocale(request, null));
        out.print(formatter.format(new java.util.Date()));
      %>
    </td>
  </tr>
</table>
