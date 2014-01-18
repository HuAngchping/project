<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<jsp:include page="/WEB-INF/jsp/html/common/header.jsp"></jsp:include>
	<tiles:importAttribute name="title" scope="page"/>
	<title><bean:message key="page.title.prefix" /> - <bean:message key="${title}"/></title>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<div style="margin: 0; padding: 0;">
<!-- Work pannel -->
<tiles:insert attribute="MainContent" />
</div>

<!-- Copyright -->
<fmt:setBundle basename="otas-dm-myportal-release"/>
<fmt:message var="version" key="Version"/>
<fmt:message var="build_number" key="BuildNumber"/>
<fmt:message var="build_date" key="BuildDate"/>
<c:set var="myportal_version" value="${version}.${build_number} ${build_date}"></c:set>
<div class="Copyright">
<bean:message key="myportal.product.powered_by" arg0="${myportal_version}"/><br/>
<bean:message key="myportal.copyright.info"/>
</div>
</body>
</html:html>
