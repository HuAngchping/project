<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://otas.cn/otasdm-taglib" prefix="otasdm"%>

<TABLE class=table_box cellSpacing=0 cellPadding=0 width="100%" border=0>
  <TBODY>
  <TR>
    <TD class=table_box_heading width="100%"><font color="red"><b>.:</b></font> <bean:message key="page.jobs.jobview.discovery.header.message"/></TD>
  </TR>
  <TR>
    <TD>
      <table class="simple_inline" width="100%">
        <thead>
          <tr>
            <th width="30" style="text-align: center;">#</th>
            <th style="text-align: left;" nowrap="nowrap"><bean:message key="page.device.jobs.job.discoveryNodes.label"/></th>
          </tr>
        </thead>
        <tbody>
        <logic:iterate name="provisionJob" property="nodes4Discovery" id="nodePath" indexId="rowNum">
          <tr>
            <td nowrap="nowrap" style="text-align: center;"><c:out value="${rowNum + 1}"/></td>
            <td nowrap="nowrap" style="text-align: left;"><bean:write name="nodePath"/></td>
          </tr>
        </logic:iterate>
        </tbody>
      </table>
    </TD>
  </TR>
  </TBODY>
</TABLE>
