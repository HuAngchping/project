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

<display:table name="moveSubjectList" id="subject" class="simple" pagesize="10">
  <display:column class="rowNum">
    <c:out value="${subject_rowNum}" />
  </display:column>
	<display:column property="externalId" titleKey="page.help.admin.move4subject.page.message.subject.name.label" />
</display:table>

<html:form action="/help/admin/subjects/moveSubject.do?method=save" method="post">
<logic:iterate id="move4subjectId" name="MoveSubjectForm4admin" property="move4subjectId">
  <input type="hidden" name="move4subjectId" value="${move4subjectId }" />
</logic:iterate>
  <table class="entityview">
    <th>
      <bean:message key="page.help.admin.move4subject.form.message.choose.subject.lable"/>
    </th>
    <td>
      <html:select property="subjectId">
        <html:optionsCollection name="subjectList" />
      </html:select>
    </td>
  </table>
  <div class="buttonArea">
    <html:submit>
      <bean:message key="page.help.admin.move4subject.form.message.move.button.lable"/>
    </html:submit>
    <html:button property="return" onclick="document.forms[1].submit();">
      <bean:message key="page.help.admin.move4subject.form.message.return.button.lable"/>
    </html:button>
  </div>
</html:form>
<html:form action="/help/admin/subjects/subjects" method="post">
</html:form>
