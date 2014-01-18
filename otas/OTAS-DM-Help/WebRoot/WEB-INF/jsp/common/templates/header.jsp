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
WEB_HTTP_BASE_PATH = "<%=path%>";
</script>
<meta http-equiv="Expires" content="-1" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />

<link rel="bookmark" href="<html:rewrite page='/common/images/favicon.ico'/>" />
<link rel="shortcut icon" href="<html:rewrite page='/common/images/favicon.ico'/>" />	

<!-- Styles: Common and global stylesheet  -->
<style type="text/css" media="all">
    @import url("<html:rewrite page='/common/css/common.css'/>");
</style>

<!-- Styles: Xtree   -->
<link rel="stylesheet" type="text/css" media="screen"
    href="<html:rewrite page='/common/css/xtree.css'/>" />
    
<!-- Styles: Templates  -->
<style type="text/css" media="all">
    @import url("<html:rewrite page='/common/css/templates/blue/common.css'/>");
</style>

<!-- Javascript: Xtree  -->
<script type="text/javascript" src="<html:rewrite page='/common/scripts/xtree.js'/>"></script>

<!-- Javascript: Common functions  -->
<script type="text/javascript" src="<html:rewrite page='/common/scripts/functions.js'/>"></script>
