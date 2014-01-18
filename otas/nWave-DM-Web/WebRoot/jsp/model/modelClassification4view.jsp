<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.npower.dm.action.BaseAction"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn"%>

<fmt:bundle basename="com.npower.dm.struts.ApplicationResources">
</fmt:bundle>
<table class="entityview">
  <thead>
    <tr>
      <th colspan="2">
        &nbsp;<bean:write name="classification" property="name" />
      </th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th width="150">
        <bean:message key="entity.model.classification.externalID.label" />
      </th>
      <td>
        <bean:write name="classification" property="externalID" />
      </td>
    </tr>
    <tr>
      <th>
        <bean:message key="entity.model.classification.name.label" />
      </th>
      <td>
        <bean:write name="classification" property="name" />
      </td>
    </tr>
    <tr>
      <th>
        <bean:message key="entity.model.classification.description.label" />
      </th>
      <td>
        <bean:write name="classification" property="description" />
      </td>
    </tr>
  </tbody>
</table>

<table class=table_box cellSpacing=0 cellPadding=0 width="100%" border="0">
  <tbody>
  <tr>
    <td class=table_box_heading width="100%" colSpan=5><font color="red"><b>.:</b></font> <bean:message key="page.model.list.label"/></td>
  </tr>
  <tr>
    <td>
      <display:table name="models" id="model" class="simple_inline" pagesize="<%=BaseAction.getRecordsPerPage(request)%>" requestURI="./model/modelClassification4view.do">
        <display:column class="rowNum">
          <c:out value="${model_rowNum}" />
        </display:column>
        <display:column property="manufacturer.name" sortable="true" sortName="manufacturer.name" titleKey="model.title.manufacturer" href="<%=request.getContextPath() + "/ViewModel.do?action=update"%>" paramId="ID" paramProperty="ID" />
        <display:column sortable="true" titleKey="model.title.manufacturerModelId">
        <html:link action="/ViewModel.do?action=update" styleClass="reference_link" target="_blank" paramId="ID" paramName="model" paramProperty="ID"><c:out value="${model.manufacturerModelId}"></c:out></html:link>
        </display:column>
			  <display:column property="name" sortable="true" sortName="name" titleKey="model.title.name" href="<%=request.getContextPath() + "/ViewModel.do?action=update"%>" paramId="ID" paramProperty="ID" />
			  <display:column property="description" sortable="true" sortName="id" titleKey="model.title.description" maxLength="36" href="<%=request.getContextPath() + "/ViewModel.do?action=update"%>" paramId="ID" paramProperty="ID" />
      </display:table>
    </td>
  </tr>
  </tbody>
</table>
