<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested"	prefix="nested"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<fmt:bundle basename="com.npower.help.struts.ApplicationResources">
</fmt:bundle>

<table class="entityview" align="center">
	<tr>
		<td>
			<bean:message
				key="page.help.admin.success4move_subject.page.message.lable" />
		</td>
	</tr>
	<tr>
		<th>
			<html:link action="/help/admin/subjects/subjects">
				<bean:message key="page.help.admin.return.message.lable" />
			</html:link>
		</th>
	</tr>
</table>
