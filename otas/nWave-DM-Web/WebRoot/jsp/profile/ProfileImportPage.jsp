
<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<html:html>
<div class="messageArea">
	<bean:message key="profile.import.page.explain" />
</div>
<div class="buttonArea" style="height:40px">
	<html:form action="/ADDCategory">
		<html:submit>
			<bean:message key="import.category.button" />
		</html:submit>
	</html:form>
	<html:form action="/ADDAttributeType">
		<html:submit>
			<bean:message key="import.attributeType.button" />
		</html:submit>
	</html:form>
	<html:form action="/ADDTemplate">
		<html:submit>
			<bean:message key="import.template.button" />
		</html:submit>
	</html:form>
	<html:form action="/ADDMapping">
		<html:submit>
			<bean:message key="import.mapping.button" />
		</html:submit>
	</html:form>
</div>
</html:html>
