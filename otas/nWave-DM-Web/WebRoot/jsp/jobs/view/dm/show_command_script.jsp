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
    <TD class=table_box_heading width="100%"><font color="red"><b>.:</b></font> <bean:message key="page.jobs.jobview.command_script.header.message"/></TD>
  </TR>
  <TR>
    <TD>
      <textarea class="view_script" readonly="readonly"><otasdm:xmlPretty content="${provisionJob.scriptString}"/></textarea>
    </TD>
  </TR>
  </TBODY>
</TABLE>
