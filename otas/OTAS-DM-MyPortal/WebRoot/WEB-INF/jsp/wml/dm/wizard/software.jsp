<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/wall.tld" prefix="wall" %>
<wall:block>
  <bean:message key="page.wml.dm.wizard.software.name.label"/>: <bean:write name="software" property="name"/> <bean:write name="software" property="version"/>
</wall:block>
<wall:block>
  <bean:message key="page.wml.dm.wizard.software.category.label"/>: <bean:write name="software" property="category.name"/>
  <logic:notEmpty name="software" property="description">
  <wall:br/><bean:message key="page.wml.dm.wizard.software.memo.label"/>: <bean:write name="software" property="description"/>
  </logic:notEmpty>
</wall:block>
<wall:block>
  * <html:link action="/wap/wizard/dm/software/msm/input.do?countryID=${country.ID}&carrierID=${carrier.externalID}" paramId="softwareID" paramName="software" paramProperty="externalId"><wall:b><bean:message key="page.wml.dm.wizard.software.page.link4DM_Install.label"/></wall:b></html:link>
</wall:block>
<wall:block>
  * <html:link action="/wap/wizard/dm/softwares.do?countryID=${country.ID}&carrierID=${carrier.externalID}"><bean:message key="page.wml.dm.wizard.software.page.link4find_other_software.label"/></html:link>
</wall:block>
