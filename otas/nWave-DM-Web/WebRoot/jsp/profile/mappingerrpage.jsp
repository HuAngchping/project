<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>


<html:html>
<div class="messageArea">
	<bean:message key="profile.err.explain" />
	<bean:message key="profile.mapping.err.explain.content" />
</div><br>
<div>
	<html:errors property="errParseName" header="profile.errors.header" footer="profile.errors.footer"/>
</div>
<div>
	<html:errors property="errParseLine" header="profile.errors.header" footer="profile.errors.footer"/>
</div>
<div>
	<html:errors property="errParseMessage" header="profile.errors.header" footer="profile.errors.footer"/>
</div>
<div>
	<html:errors property="errSAXExpName" header="profile.errors.header" footer="profile.errors.footer"/>
</div>
<div>
	<html:errors property="errSAXExpStr" header="profile.errors.header" footer="profile.errors.footer"/>
</div>
<div>
	<html:errors property="errSAXExpMessage" header="profile.errors.header" footer="profile.errors.footer"/>
</div>
<div>
	<html:errors property="errIOEName" header="profile.errors.header" footer="profile.errors.footer"/>
</div>
<div>
	<html:errors property="errIOEStr" header="profile.errors.header" footer="profile.errors.footer"/>
</div>
<div>
	<html:errors property="errParserCfgExpName" header="profile.errors.header" footer="profile.errors.footer"/>
</div>
<div>
	<html:errors property="errFactoryCfgName" header="profile.errors.header" footer="profile.errors.footer"/>
</div>
<div>
	<html:errors property="noManufacturerErr" header="profile.errors.header" footer="profile.errors.footer"/>
</div>
<div>
	<html:errors property="noModelErr" header="profile.errors.header" footer="profile.errors.footer"/>
</div>
<div>
	<html:errors property="noTemplateErr" header="profile.errors.header" footer="profile.errors.footer"/>
</div>
<div>
	<html:errors property="otherErr" header="profile.errors.header" footer="profile.errors.footer"/>
</div>



<div class="buttonArea" style="height: 40px;">
	<html:form action="/ADDMapping">

		<html:submit>
			<bean:message key="button.upload.again" />
		</html:submit>

	</html:form>
	<html:form action="/mappinguploadsAction.do" enctype="multipart/form-data">
		<html:cancel>
			<bean:message key="page.button.back.mapping" />
		</html:cancel>
	</html:form>
</div>
</html:html>
