<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%@ taglib uri="http://struts-menu.sf.net/tag" prefix="menu"%>
<%@ taglib uri="http://struts-menu.sf.net/tag-el" prefix="menu-el"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<fmt:bundle basename="com.npower.help.struts.ApplicationResources">
</fmt:bundle>
<%-- location lable --%>
<div class="locationArea">
  <bean:message key="page.help.common.page.message.location.label" />
  <html:link action="/help/admin/subjects/subjects">
    <bean:message key="page.help.common.page.message.location.list.label" />
  </html:link>
  <bean:message key="page.help.admin.edit4subject.page.message.location.lable" />
</div>

<bean:message key="page.help.admin.edit4subject.page.message.note.lable" />

<html:form action="/help/admin/subjects/saveSubject" method="post">
  <logic:present name="subject">
    <html:hidden property="subjectId" value="${subject.subjectId}" />
  </logic:present>
  <table class="entityview">
    <tr>
      <th>
        <bean:message key="page.help.admin.edit4subject.form.message.subject.name.lable" />
      </th>
      <td>
        <html:text property="externalId" />
        <b><font color="red"><html:errors property="externalId"/></font></b>
      </td>
    </tr>
  </table>
  <div class="buttonArea">
    <html:submit property="method">
      <bean:message key="page.help.admin.edit4subject.page.message.save.button.lable" />
    </html:submit>
    <html:button property="return" onclick="document.forms[1].submit();">
      <bean:message key="page.help.admin.return.message.button.lable" />
    </html:button>
  </div>
</html:form>

<html:form action="/help/admin/subjects/subjects" method="post">
</html:form>

