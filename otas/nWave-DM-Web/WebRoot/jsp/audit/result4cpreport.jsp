<%@ page import="com.npower.dm.action.BaseAction" language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<display:table name="result" id="record" class="simple" pagesize="<%=BaseAction.getRecordsPerPage(request) %>" requestURI="<%=request.getContextPath() + "/createReport.do"%>">
  <display:column class="rowNum">
    <c:out value="${record_rowNum}" />
  </display:column>
  <display:column property="phonenumber" titleKey="page.cp.log.result.userphoneumber.label"/>
  <display:column property="web"  titleKey="page.cp.log.result.web.label"/>
  <display:column property="manufacturer" titleKey="page.cp.log.result.manufacturer.label"/>
  <display:column property="model" titleKey="page.cp.log.result.model.label"/>
  <display:column property="imei"  titleKey="page.cp.log.result.imei.label"/>
  <display:column property="workType" titleKey="page.cp.log.result.worktype.label"/>
  <display:column property="status" titleKey="page.cp.log.result.status.label"/>
  <display:column property="province" titleKey="page.cp.log.result.province.label"/>
  <display:column property="costType" titleKey="page.cp.log.result.costtype.label"/>
  <display:column property="functionCost" titleKey="page.cp.log.result.function.label"/>
  <display:column property="startTime" titleKey="page.cp.log.result.begintime.label"/>
  <display:column property="endTime" titleKey="page.cp.log.result.endtime.label"/>
</display:table>



