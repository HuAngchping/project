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

<table class=table_box cellSpacing=0 cellPadding=0 width="100%" border="0">
  <tbody>
  <tr>
    <td class=table_box_heading width="100%" colSpan=5><font color="red"><b>.:</b></font> <bean:message key="page.model.classification.list.label"/></td>
  </tr>
  <tr>
    <td>
      <display:table name="modelClassifications" id="modelClassification" class="simple_inline"  pagesize="<%=BaseAction.getRecordsPerPage(request)%>" requestURI="./model/modelClassifications.do">
        <display:column class="rowNum">
          <c:out value="${modelClassification_rowNum}" />
        </display:column>
        <display:column property="externalID" sortName="externalID" titleKey="entity.model.classification.externalID.label" href="./model/modelClassification4view.do" paramId="id" paramProperty="id" />
        <display:column property="name" sortName="name" titleKey="entity.model.classification.name.label" href="./model/modelClassification4view.do" paramId="id" paramProperty="id" />
        <display:column titleKey="entity.model.classification.number4model.label" sortable="true" href="./model/modelClassification4view.do" paramId="id" paramProperty="id">
          <c:out value="${fn:length(modelClassification.modelSelector.models)}" />
        </display:column>
        <display:column>
          <html:link action="./model/modelClassification4view" paramId="id" paramName="modelClassification" paramProperty="id">
            <bean:message key="page.model.classification.view.label"/>
          </html:link>
          <html:link action="./model/modelClassificaion4edit" paramId="id" paramName="modelClassification" paramProperty="id">
            <bean:message key="page.model.classification.edit.label" />
          </html:link>
          <html:link action="./model/modelClassification4delete" paramId="id" paramName="modelClassification" paramProperty="id">
            <bean:message key="page.model.classification.delete.label"/>
          </html:link>
        </display:column>
      </display:table>
    </td>
  </tr>
  </tbody>
</table>
<html:form action="/model/modelClassificaion4edit" method="post" style="clear: both;width:100%;">
  <div class="buttonArea">
    <html:submit>
      <bean:message key="page.model.classification.button.label"/>
    </html:submit>
  </div>
</html:form>
