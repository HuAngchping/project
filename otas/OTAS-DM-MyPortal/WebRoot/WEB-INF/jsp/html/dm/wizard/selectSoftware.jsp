<?xml version="1.0" encoding="UTF-8" ?>
<%@ page import="com.npower.dm.myportal.BaseWizardAction"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script language="javascript">     
function document.onkeydown() {     
var e = event.srcElement;     
  if (event.keyCode == 13) {     
     document.getElementById("NextButton").click();     
     return false;     
  }     
}     
</script>

<div class="WizardTitle">
  <bean:message key="page.dm.msm.wizard.title" />
</div>
<hr class="WizardHR" />
<html:form action="/wizard/dm/SelectSoftware" method="POST" focus="value(searchText)">
  <div class="WizardForm">
    <p>
      <bean:message key="page.dm.wizard.select.software.help.1" />
    </p>
    <logic:messagesPresent message="false">
      <div class="WizardMessage">
        <html:errors />
      </div>
    </logic:messagesPresent>
    <table border="0">
      <tr>
        <th>
          <bean:message key="page.cp.wizard.select.country.country.label" />
          :
        </th>
        <td>
          <bean:write name="country" property="name" />
        </td>
      </tr>
      <tr>
        <th>
          <bean:message key="page.cp.wizard.select.carrier.carrier.label" />
          :
        </th>
        <td>
          <bean:write name="carrier" property="name" />
        </td>
      </tr>
      <logic:notEmpty name="model">
      <tr>
        <th>
          <bean:message key="page.cp.wizard.select.model.model.label" />
          :
        </th>
        <td>
          <bean:write name="manufacturer" property="name" />
          <bean:write name="model" property="name" />
        </td>
      </tr>
      </logic:notEmpty>
      <%-- 
  	  <tr>
  		<th></th>
  		<td>
  	    <br/><html:img action="/download.do?method=getModelIcon" paramId="modelID" paramName="model" paramProperty="ID"/>
  	    </td>
  	  </tr>
	  --%>
    </table>
    <div style="margin-left: 12px; padding-bottom: 0px; padding-left: 10px; padding-top: 10px;">
      <b><bean:message key="page.dm.wizard.select.software.help.2" /> </b>
    </div>
    <table border="0">
      <tr>
        <td>

<!--  Search software Panel -->
<table border="0">
  <tr>
    <td align="center" valign="top">
      <table border="0">
        <tr>
          <td>
            <bean:message key="page.dm.wizard.software.vendor.label" />
            &nbsp;&nbsp;
            <html:select property="value(vendorID)">
              <html:option value="">
                <bean:message key="page.dm.wizard.software.vendor.EmptyOptions" />
              </html:option>
              <html:optionsCollection name="vendorOptions" value="id" label="name" />
            </html:select>
            &nbsp;
            <html:text property="value(searchText)"></html:text>
            &nbsp;
            <html:select property="value(recordsPerPage)">
              <html:optionsCollection name="recordsPerPageOptions" />
            </html:select>
            <bean:message key="page.dm.wizard.software.select.search.recordsPerPage.label" />
            &nbsp;&nbsp;
            <html:submit onclick="this.form.submit();" styleClass="Button" style="width: 100px;">
              <bean:message key="page.dm.wizard.software.button.find.label" />
            </html:submit>

          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>

<display:table name="softwares" id="software" pagesize="<%=BaseWizardAction.getRecordsPerPage(request)%>"
  requestURI="<%=request.getContextPath() + "/wizard/dm/SelectSoftware.do?navigation="%>" class="simple">
  <!--  Customer DisplayTag default properties  -->
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

  <display:column class="rowNum">
    <html:radio property="value(softwareID)" value="${software.externalId}"></html:radio>
  </display:column>
  <display:column titleKey="page.dm.wizard.software.name.label">
    <c:out value="${software.name}"/> <c:out value="${software.version}"/>
  </display:column>
  <display:column property="vendor.name" titleKey="page.dm.wizard.software.vendor.label" />
  <display:column property="description" titleKey="page.cp.wizard.select.category.category.description.label" maxLength="32"/>
</display:table>
        </td>
      </tr>
    </table>
  </div>
  <hr class="WizardHR" />
  <div class="WizardNavigation">
    <html:submit property="navigation" styleId="PrevButton" onclick="this.form.submit();">
      <bean:message key="page.button.prev.label" />
    </html:submit>
    &nbsp;&nbsp;&nbsp;
    <html:submit property="navigation" styleId="NextButton">
      <bean:message key="page.button.next.label" />
    </html:submit>
  </div>
</html:form>