<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ page import="org.apache.struts.Globals"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<base href="<%=basePath%>">
<script type="text/javascript">
WEB_HTTP_BASE_PATH = "<%=basePath%>";
CSS_TEMPLATE_NAME = '<bean:write name="CSS_TEMPLATE_NAME"/>';
</script>
<meta http-equiv="Expires" content="-1" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />

<link rel="bookmark" href="<html:rewrite page='/common/images/favicon.ico'/>" />
<link rel="shortcut icon" href="<html:rewrite page='/common/images/favicon.ico'/>" />	

<!-- EXT Default Language -->
<script language="javascript">
  var Default_Language = "<%=request.getSession().getAttribute(Globals.LOCALE_KEY)%>";
</script>

<!-- EXT CSS -->
<link rel="stylesheet" type="text/css" href="<html:rewrite page='/common/scripts/ext-2.2/resources/css/ext-all.css'/>" />
<link rel="stylesheet" type="text/css" href="<html:rewrite page='/common/css/ext-2.2-patch.css'/>" />

<!-- EXT LIBS -->
<script type="text/javascript" language="javascript" src="<html:rewrite page='/common/scripts/ext-2.2/adapter/ext/ext-base.js'/>"></script>
<script type="text/javascript" language="javascript" src="<html:rewrite page='/common/scripts/ext-2.2/ext-all.js'/>"></script>

<!-- Javascript: show favorite window -->
<script type="text/javascript" language="javascript" src="<html:rewrite page='/common/scripts/favorite/localizing_ext_chinese.js'/>"></script>
<script type="text/javascript" language="javascript" src="<html:rewrite page='/common/scripts/favorite/favorite.js'/>"></script>

<!-- Styles: Common and global stylesheet  -->
<style type="text/css" media="all">
    @import url("<html:rewrite page='/common/css/common.css'/>");
    @import url("<html:rewrite page='/common/css/tabbed_menu.css'/>");
</style>

<!-- Styles: Xtree   -->
<link rel="stylesheet" type="text/css" media="screen"
    href="<html:rewrite page='/common/css/xtree.css'/>" />
    
<!-- Stylesheet: Chart -->
<link rel="stylesheet" href="<html:rewrite page='/common/css/chart/main.css" type="text/css'/>"/>
<link rel="stylesheet" href="<html:rewrite page='/common/css/chart/messages.css" type="text/css'/>"/>

<!-- Styles: Templates  -->
<style type="text/css" media="all">
    @import url("<html:rewrite page='/common/css/templates/${CSS_TEMPLATE_NAME}/common.css'/>");
</style>

<!-- Main Menu Stylesheet -->
<link rel="stylesheet" href="<html:rewrite page='/common/css/templates/${CSS_TEMPLATE_NAME}/main_menu.css'/>" type="text/css" />

<!-- Main Menu Scripts -->
<script type="text/javascript" src="<html:rewrite page='/common/scripts/menu/menu.js'/>"></script>

<!-- Javascript: Xtree  -->
<script type="text/javascript" src="<html:rewrite page='/common/scripts/xtree.js'/>"></script>

<!-- Javascript: Common functions  -->
<script type="text/javascript" src="<html:rewrite page='/common/scripts/functions.js'/>"></script>

<!-- Javascript: Calendar functions  -->
<script type="text/javascript" src="<html:rewrite page='/common/calendar/calendar1.js'/>"></script>
<script type="text/javascript" src="<html:rewrite page='/common/calendar/calendar2.js'/>"></script>

<!-- Javascript: Chart -->
<script type="text/javascript" language="javascript" src="<html:rewrite page='/common/scripts/chart/prototype.js'/>"></script>
<script type="text/javascript" language="javascript" src="<html:rewrite page='/common/scripts/chart/scriptaculous.js'/>"></script>
<script type="text/javascript" language="javascript" src="<html:rewrite page='/common/scripts/chart/func.js'/>"></script>
<script type="text/javascript" language="javascript" src="<html:rewrite page='/common/scripts/chart/behaviour.js'/>"></script>

