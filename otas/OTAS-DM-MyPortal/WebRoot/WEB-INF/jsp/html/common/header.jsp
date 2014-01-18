<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>">
<script type="text/javascript">
WEB_HTTP_BASE_PATH = "<%=basePath%>";
</script>
<meta http-equiv="Expires" content="-1" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta name="author" content="<bean:message key="myportal.html.meta.author"/>" />
<meta name="description" content="<bean:message key="myportal.html.meta.description"/>" />
<meta name="keywords" content="<bean:message key="myportal.html.meta.keywords"/>" />

<link rel="bookmark" href="<html:rewrite page='/common/images/favicon.ico'/>" />
<link rel="shortcut icon" href="<html:rewrite page='/common/images/favicon.ico'/>" />	

<!-- Styles: Common and global stylesheet  -->
<style type="text/css" media="all">
    @import url("<html:rewrite page='/common/html/css/common.css'/>");
</style>

<!-- Styles: Basic wizard template stylesheet  -->
<style type="text/css" media="all">
    @import url("<html:rewrite page='/common/html/css/basic.css'/>");
</style>

<!-- Styles: DisplayTag stylesheet  -->
<style type="text/css" media="all">
    @import url("<html:rewrite page='/common/html/css/displaytag/displaytag.css'/>");
</style>

<!-- Styles: Xtree   -->
<link rel="stylesheet" type="text/css" media="screen" href="<html:rewrite page='/common/html/css/xtree.css'/>" />

<!-- Javascript: Common functions  -->
<script type="text/javascript" src="<html:rewrite page='/common/html/scripts/functions.js'/>"></script>

<!-- Javascript: Prototype functions  -->
<script type="text/javascript" src="<html:rewrite page='/common/html/scripts/prototype.js'/>"></script>

<!-- Javascript: Xtree  -->
<script type="text/javascript" src="<html:rewrite page='/common/html/scripts/xtree.js'/>"></script>

