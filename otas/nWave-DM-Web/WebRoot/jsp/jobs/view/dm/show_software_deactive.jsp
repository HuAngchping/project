<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://otas.cn/otasdm-taglib" prefix="otasdm"%>

<%-- Profile List   --%>
<TABLE class=table_box cellSpacing=0 cellPadding=0 width="100%" border=0>
  <TBODY>
  <TR>
    <TD class=table_box_heading width="100%"><font color="red"><b>.:</b></font> <bean:message key="page.jobs.jobview.software.deactive.header.message"/></TD>
  </TR>
  <TR>
    <TD>
      <table class="simple_inline" width="100%">
        <thead>
          <tr>
            <th style="text-align: left; width: 10px;"></th>
            <th style="text-align: left;" nowrap="nowrap">
              <bean:message key="page.device.job.software.deactive.schedule.target.software.label"/>
            </th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td></td>
            <td nowrap="nowrap" style="text-align: left;">
              <bean:write name="provisionJob" property="targetSoftware.vendor.name"/> 
              <bean:write name="provisionJob" property="targetSoftware.name"/>
              <bean:write name="provisionJob" property="targetSoftware.version"/>
            </td>
          </tr>
        </tbody>
      </table>
    </TD>
  </TR>
  </TBODY>
</TABLE>
