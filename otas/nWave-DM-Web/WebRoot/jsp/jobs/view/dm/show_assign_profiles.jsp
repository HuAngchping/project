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
    <TD class=table_box_heading width="100%"><font color="red"><b>.:</b></font> <bean:message key="page.jobs.jobview.profiles.header.message"/></TD>
  </TR>
  <TR>
    <TD>
      <table class="simple_inline" width="100%">
        <thead>
          <tr>
            <th width="30" style="text-align: center;">#</th>
            <th width="100" style="text-align: left;"><bean:message key="profile.config.name.label"/></th>
            <th ></th>
          </tr>
        </thead>
        <tbody>
        <logic:iterate id="profileAssignment" name="provisionJob" property="profileAssignments" indexId="profileAssignment_rowNum">
          <tr>
            <th nowrap="nowrap" style="text-align: center;"><c:out value="${profileAssignment_rowNum + 1}"/></th>
            <th nowrap="nowrap" style="text-align: left;"><bean:write name="profileAssignment" property="profileConfig.name"/></th>
            <td>
              <c:set var="nestedName" value="provisionJob.profileAssignments[${profileAssignment_rowNum}].allOfNameValuePairs" />
              <c:set var="profileConfig" value="provisionJob.profileAssignments[${profileAssignment_rowNum}].profileConfig" />
              <c:set var="profileTemplate" value="provisionJob.profileAssignments[${profileAssignment_rowNum}].profileConfig.profileTemplate" />
              <display:table name="${nestedName}" id="attribute${profileAssignment_rowNum}" class="simple_inline">
                <display:column property="name" titleKey="page.profileConfig.attribute.name.lable" />
                <display:column titleKey="page.profileConfig.attribute.value.label">
                  <bean:write name="attribute${profileAssignment_rowNum}" property="value"/>&nbsp;
                </display:column>
              </display:table>
            </td>
          </tr>
        </logic:iterate>
        </tbody>
      </table>
    </TD>
  </TR>
  </TBODY>
</TABLE>

