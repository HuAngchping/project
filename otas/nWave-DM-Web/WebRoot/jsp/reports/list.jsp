<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/otas-dm.tld" prefix="dm"%>
<style>
<!--
.dataTable {
	border-bottom: 1px solid #CCCCCC;
  width: 100%;
}

.dataTable td {
	padding: 5px;
}

.record {
	background: #E9E9E6 none repeat scroll 0 0;
}

.record td {
	border-left: 1px solid #CCCCCC;
	border-top: 1px solid #CCCCCC;
}

.record td td {
	border-width: 0;
}

.evenRecord {
	
}

.evenRecord td {
	border-left: 1px solid #CCCCCC;
	border-top: 1px solid #CCCCCC;
}

.evenRecord td td {
	border-width: 0;
}
-->
</style>
<table cellspacing="0" cellpadding="0" class="dataTable">
  <thead />
  <tbody>
    <logic:iterate id="report" name="reports" indexId="reportIndex">
      <tr valign="top" class='${(reportIndex % 2 == 0)?"record":"evenRecord"}'>
        <td style="border-right: 1px solid rgb(204, 204, 204);">
          <p style="margin-bottom: 5px; padding-bottom: 5px; border-bottom: 1px solid rgb(204, 204, 204);">
            <b>» <bean:message key="${report.title}"/></b>
          </p>
          <logic:iterate id="reportItem" name="report" property="items">
            <p style="padding-bottom: 5px;">
              <span style="width: 20px;"><html:img page="${reportItem.icon}"/></span>
              <span style="padding-right: 50px; padding-left: 3; width: 260px;"> 
                  <a href="${reportItem.url}" style="padding-left: 3;" target="_blank"><bean:message key="${reportItem.label}"/></a>
              </span>
              <logic:notEmpty name="reportItem" property="generatedTime">
                <span style="padding-right: 50px;"><bean:message key="page.report.list.file.generatedTime.label"/>:[<bean:write name="reportItem" property="generatedTime"
                    format="yyyy-MM-dd HH-mm-ss" />]</span>
                <span style="padding-right: 50px;"><bean:message key="page.report.list.file.size.label"/>: [<dm:volume value="${reportItem.size}" fractions="2"
                    unit="B" />]</span>
              </logic:notEmpty>
            </p>
          </logic:iterate>
          <p />
        </td>
      </tr>
    </logic:iterate>
  </tbody>
</table>