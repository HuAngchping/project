<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/wall.tld" prefix="wall" %>

<html:form action="/wap/wizard/dm/softwares" method="POST" focus="value(searchText)">
<wall:input type="hidden" name="countryID" value="${country.ID}"></wall:input>
<wall:input type="hidden" name="carrierID" value="${carrier.externalID}"></wall:input>
<wall:block><bean:message key="page.wml.dm.wizard.softwares.page.msg4find"/></wall:block>
<!--  Search software Panel -->
<wall:block>
<bean:message key="page.wml.dm.wizard.softwares.page.search.label"/>: <html:text property="value(searchText)" size="6"></html:text>
&nbsp;&nbsp;
<html:submit onclick="this.form.submit();">
  <bean:message key="page.dm.wizard.software.button.find.label" />
</html:submit>
</wall:block>

<display:table name="softwares" id="software" pagesize="10"
  requestURI="<%=request.getContextPath() + "/wap/wizard/dm/softwares.do?navigation="%>" class="simple">
  <!--  Customer DisplayTag default properties  -->
  <display:setProperty name="paging.banner.placement">both</display:setProperty>
  <display:setProperty name="paging.banner.items_name">
      <bean:message key="page.dm.wizard.select.software.displayTag.paging.banner.items_name" arg0="{0}" arg1="{1}" arg2="{2}" arg3="{3}" arg4="{4}" />
  </display:setProperty>
  <display:setProperty name="paging.banner.item_name">
    <bean:message key="page.dm.wizard.select.software.displayTag.paging.banner.items_name" arg0="{0}" arg1="{1}" arg2="{2}" arg3="{3}" arg4="{4}" />
  </display:setProperty>
  <display:setProperty name="basic.msg.empty_list">
    <bean:message key="page.dm.wizard.select.software.displayTag.basic.msg.empty_list" arg0="{0}" arg1="{1}" arg2="{2}" arg3="{3}" arg4="{4}" />
  </display:setProperty>
  <display:setProperty name="basic.msg.empty_list_row">
    <bean:message key="page.dm.wizard.select.software.displayTag.basic.msg.empty_list_row" arg0="{0}" arg1="{1}" arg2="{2}" arg3="{3}" arg4="{4}" />
  </display:setProperty>
  <display:setProperty name="paging.banner.no_items_found">
      <bean:message key="page.dm.wizard.select.software.displayTag.paging.banner.no_items_found" arg0="{0}" arg1="{1}" arg2="{2}" arg3="{3}" arg4="{4}" />
  </display:setProperty>           
  <display:setProperty name="paging.banner.all_items_found">
    <bean:message key="page.dm.wizard.select.software.displayTag.paging.banner.all_items_found" arg0="{0}" arg1="{1}" arg2="{2}" arg3="{3}" arg4="{4}" />
  </display:setProperty>
  <display:setProperty name="paging.banner.some_items_found">
    <bean:message key="page.dm.wizard.select.software.displayTag.paging.banner.some_items_found" arg0="{0}" arg1="{1}" arg2="{2}" arg3="{3}" arg4="{4}" />
  </display:setProperty>
  <display:setProperty name="paging.banner.one_item_found">
    <bean:message key="page.dm.wizard.select.software.displayTag.paging.banner.one_item_found" arg0="{0}" arg1="{1}" arg2="{2}" arg3="{3}" arg4="{4}" />
  </display:setProperty>
  <display:setProperty name="paging.banner.full">
    <bean:message key="page.dm.wizard.select.software.displayTag.paging.banner.full" arg0="{0}" arg1="{1}" arg2="{2}" arg3="{3}" arg4="{4}" />
  </display:setProperty>
  <display:setProperty name="paging.banner.first">
    <bean:message key="page.dm.wizard.select.software.displayTag.paging.banner.first" arg0="{0}" arg1="{1}" arg2="{2}" arg3="{3}" arg4="{4}" />
  </display:setProperty>
  <display:setProperty name="paging.banner.last">
    <bean:message key="page.dm.wizard.select.software.displayTag.paging.banner.last" arg0="{0}" arg1="{1}" arg2="{2}" arg3="{3}" arg4="{4}" />
  </display:setProperty>
  <display:setProperty name="paging.banner.onepage">
    <bean:message key="page.dm.wizard.select.software.displayTag.paging.banner.onepage" arg0="{0}" arg1="{1}" arg2="{2}" arg3="{3}" arg4="{4}" />
  </display:setProperty>

  <display:column class="rowNum"> * </display:column>
  <display:column>
    <html:link action="/wap/wizard/dm/software/view.do?countryID=${country.ID}&carrierID=${carrier.externalID}&softwareID=${software.externalId}"><bean:write name="software" property="name"/>　<bean:write name="software" property="version"/></html:link>
  </display:column>
</display:table>
</html:form>