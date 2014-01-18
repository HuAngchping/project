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

<div class="locationArea">
  <bean:message key="page.help.common.page.message.location.label" />
  <html:link action="/help/admin/subjects/subjects">
    <bean:message key="page.help.common.page.message.location.list.label" />
  </html:link>
  <bean:message key="page.help.admin.select4locale.page.message.location.utdate.lable" />
</div>

<html:form action="/help/admin/subjects/editContent" method="post">
  <table class="entityview">
    <html:hidden property="subjectId" value="${subject.subjectId}" />
    <th>
      <bean:message key="page.help.admin.select4locale.form.message.choose.locale.lable" />
    </th>
    <td>
      <html:select property="localeId">
        <html:optionsCollection name="localeList" />
      </html:select>
    </td>
  </table>
  <div class="buttonArea">
    <html:submit>
      <bean:message key="page.help.admin.select4locale.form.message.submit.lable" />
    </html:submit>
    <html:button property="action" onclick="document.forms[1].submit();">
      <bean:message key="page.help.admin.select4locale.form.message.cancel.lable" />
    </html:button>
  </div>
</html:form>

<html:form action="/help/admin/subjects/editContent" method="post">
  <html:hidden property="subjectId" value="${subject.subjectId}" />
  <html:hidden property="localeId" value="${currentLocale.id}" />
</html:form>

