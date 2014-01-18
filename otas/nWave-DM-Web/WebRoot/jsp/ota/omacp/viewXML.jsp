<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<html:form action="/ota/omacp/generalInput" method="post">
  <input type="hidden" name="cp_type" value="document"/>
<table class="entityview">
  <THEAD>
    <TR>
      <TH ><bean:message key="page.ota.omacp.viewXML.message1.title"/></TH>
    </TR>
    <TR>
      <TD>
        <textarea class="view_script" readonly="readonly"><bean:write name="settingsText"/></textarea>
      </TD>
    </TR>
  </THEAD>
</table>
</html:form>
