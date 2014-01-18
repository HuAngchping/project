<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page isErrorPage="true"%>
<%@ page import="java.io.PrintWriter,org.apache.struts.Globals"%>
<%@ page import="java.util.TreeMap"%>
<%@ page import="com.npower.dm.util.SystemInfo"%>
<%@ page import="com.npower.dm.tags.BoxedListTag"%>
<%@ page import="com.npower.dm.action.help.SystemInfoAction"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://otas.cn/otasdm-taglib" prefix="otasdm"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
  <html:base />
  <TITLE><bean:message key="page.title.prefix" /></TITLE>
  <jsp:include page="/jsp/common/header.jsp"></jsp:include>
  <script type="text/javascript">
	 function toggle(id) {
	  var element = document.getElementById(id);
	  with (element.style) {
	      if ( display == "none" ){
	          display = ""
	          //setCookie(id, "show");
	      } else{
	          display = "none"
	          //setCookie(id, "hide");
	      }
	  }
	  var text = document.getElementById(id + "-switch").firstChild;
	  if (text.nodeValue == "[show]") {
	      text.nodeValue = "[hide]";
	  } else {
	      text.nodeValue = "[show]";
	  }
	 }
  </script>
  <style>
	.sectionTitle {
	  font-size: 12px;
	  font-weight: bold;
	  margin: 0 0 10px 10px;
	}
	
	.warning {
	  background-color: lightcoral;
	  border: 1 #bbb solid;
	  font-weight: bold;
	  text-align:center;
	}
	
	/*
	 * box styles used by the boxXXX custom tags
	 */
	.propertyName {
	  font-weight:bold;
	  font-size: 11px;
	}
	
	.propertyValue {
	  color:dimgray;
	  font-size: 11px;
	}
	
	.box, .box h3{
	  border: 1px #ccc solid
	  font-size: 11px;
	}
	
	.box {
	   font-size:0.8em;
	   background-color:#fff;
	   padding: 0px;
	   margin: 0 0 7px 0;
	}
	.box h3 {
	  font-size: 12px;
	  font-weight:bold;
	  border-width: 0 0 1px 0;
	  color: #0a0a0a;
	  background-color: #e9e9e9;
	  padding: 2px 0 2px 10px;
	  margin: 0px;
	}
	
	.boxIcon {
	   float: right;
	   font-weight: normal;
	   font-size: 0.9em;
	   margin: 0;
	   padding: 0;
	   vertical-align:top;
	   margin-right:5px;
	}
	
	.boxIcon img {
	   border-width: 0px;
	   margin-right: 5px;
	}
	
	.boxIconText {
	   vertical-align: top;
	   margin-right: 5px;
	   cursor:pointer;
	}
	
	.boxBody {
	   font-weight: normal;
	   padding: 0 15px 0 30px;
	/*padding: 4px 10px 4px 4px;*/
	   margin: 4px;
	}
	
	.boxBody ul {
	   padding: 0px 0 0px 10px;
	}
	
	.boxBody pre {
	   padding: 0;
	   font-size: 1.2em;
	}
  </style>
</head>
  
<body style="margin: 0px 0 0px 0;">

<%
      //boolean isSystemInfo = request.getAttribute(SystemInfoAction.IS_SYSTEM_INFO_KEY) != null;
      //String titleKey = isSystemInfo ? "system.info.title" : "system.info.title";
      SystemInfo systemInfo = (SystemInfo) application.getAttribute("systemInfo");
%>
<table border="0" cellpadding="5" cellspacing="0" width="100%">
<!-- 
	<tr>
		<td valign="middle">
			<div class="locationArea"><bean:message key="DM.product.name"/> <bean:message key="system.info.title"/></div>
			<hr class="locationArea">
        </td>
    </tr>
-->
	<tr>
		<td valign="top">
			<div class="sectionTitle">
				<bean:message key="system.info.system.env.title"/>
			</div>
			<otasdm:boxedList title="system.info.system.env.version.title" keyValues="<%=systemInfo.getDMWebAdminBuildInfo()%>" />
			<!-- <otasdm:boxedList title="Database" keyValues="<%=systemInfo.getDatabaseInfo()%>" />-->
			<otasdm:boxedList title="system.info.system.env.appserver.title" keyValues="<%=systemInfo.getAppServerInfo()%>" />
			<otasdm:boxedList title="system.info.system.env.memory.title" keyValues="<%=systemInfo.getJVMStatistics()%>" />
			<otasdm:boxedList title="system.info.system.env.system.title" keyValues="<%=SystemInfo.getSystemProperties()%>" />

			<div class="sectionTitle">
				Request Information
			</div>
			<%try {

        %>
			<otasdm:boxedList title="General">
				<li>
					<%=BoxedListTag.renderProperty("Referer URL", request.getHeader("Referer") != null ? request
                .getHeader("Referer") : "Unknown")%>
				</li>
				<li>
					<%=BoxedListTag.renderProperty("Locale", request.getSession(false).getAttribute(Globals.LOCALE_KEY))%>
				</li>
				<li>
					<%=BoxedListTag.renderProperty("URL", request.getRequestURL())%>
				</li>
				<ul>
					<li>
						<%=BoxedListTag.renderProperty("Scheme", request.getScheme())%>
					</li>
					<li>
						<%=BoxedListTag.renderProperty("Server", request.getServerName())%>
					</li>
					<li>
						<%=BoxedListTag.renderProperty("Port", "" + request.getServerPort())%>
					</li>
					<li>
						<%=BoxedListTag.renderProperty("URI", request.getRequestURI())%>
					</li>
					<ul>
						<li>
							<%=BoxedListTag.renderProperty("Context Path", request.getContextPath())%>
						</li>
						<li>
							<%=BoxedListTag.renderProperty("Servlet Path", request.getServletPath())%>
						</li>
						<li>
							<%=BoxedListTag.renderProperty("Path Info", request.getPathInfo())%>
						</li>
						<li>
							<%=BoxedListTag.renderProperty("Query String", request.getQueryString())%>
						</li>
					</ul>
				</ul>
			</otasdm:boxedList>
			<otasdm:boxedList title="Parameters" keyValues="<%=new TreeMap(request.getParameterMap())%>" />
			<otasdm:boxedList title="Attributes" keyValues="<%=BoxedListTag.getRequestAttributeMap(request)%>" />
			<%} catch (Throwable t) {
        out.println("Error rendering logging information");
        t.printStackTrace(new PrintWriter(out));
      }

    %>
		</td>
	</tr>
</table>
</body>
</html:html>

