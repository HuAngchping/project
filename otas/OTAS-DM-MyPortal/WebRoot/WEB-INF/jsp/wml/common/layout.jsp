<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%><%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%><%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%><%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%><%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%><%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%><%@ taglib uri="/WEB-INF/c.tld" prefix="c"%><%@ taglib uri="/WEB-INF/wall.tld" prefix="wall" %><wall:document><wall:xmlpidtd />
  <wall:load_capabilities />
  <tiles:importAttribute name="title" scope="page"/>
  <wall:head>
     <meta http-equiv="Expires" content="-1" />
     <meta http-equiv="Pragma" content="no-cache" />
     <meta http-equiv="Cache-Control" content="no-cache" />
     <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
     <meta name="author" content="<bean:message key="myportal.html.meta.author"/>" />
     <meta name="description" content="<bean:message key="myportal.html.meta.description"/>" />
     <meta name="keywords" content="<bean:message key="myportal.html.meta.keywords"/>" />
     <wall:title><bean:message key="page.title.prefix" /> - <bean:message key="${title}"/></wall:title>
  </wall:head>
  <wall:body>
    <wall:block>
      <bean:message key="${title}"/>
    </wall:block>
    <wall:hr/>
    
    <!-- Work panel -->
    <tiles:insert attribute="MainContent" />
    
    <wall:hr/>
    <!-- Copyright -->
    <fmt:setBundle basename="otas-dm-myportal-release"/>
    <fmt:message var="version" key="Version"/>
    <fmt:message var="build_number" key="BuildNumber"/>
    <fmt:message var="build_date" key="BuildDate"/>
    <c:set var="myportal_version" value="${version}.${build_number} ${build_date}"></c:set>
    <wall:block>
      <wall:font size="-2">
      <bean:message key="myportal.product.powered_by" arg0="${myportal_version}"/><br/>
      <bean:message key="myportal.copyright.info"/>
      </wall:font>
    </wall:block>
 
  </wall:body>
</wall:document>
