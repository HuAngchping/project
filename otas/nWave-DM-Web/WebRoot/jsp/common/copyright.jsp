<%@ page language="java" pageEncoding="UTF-8"%>

<%@ page import="com.npower.dm.util.SystemInfo"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>


<div id="divPoweredBy" align="center">
    <br />
     <bean:message key="page.copyright"/><BR>
     <bean:message key="DM.product.name"/> <%=SystemInfo.getVersionInfo()%> 
     <%-- <bean:message key="page.server.responsed.time.message" arg0="${expendTime}"/> --%>
</div>
