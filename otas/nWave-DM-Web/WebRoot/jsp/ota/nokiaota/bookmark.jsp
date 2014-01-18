<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

<div class="messageArea">
  <bean:message key="page.message.required"/><bean:message key="page.message.input"/>
  <div class="validateErrorMsg"><html:errors/></div>
</div>

<html:form action="/ota/nokia/sendBookmark" method="post" focus="msisdn">
	<table class="entityview">
		<tbody>
      <!-- Form Element: Targets -->
      <jsp:include flush="true" page="/jsp/ota/omacp/form/targets.jsp"></jsp:include>
			<tr>
				<th style="font-weight: normal">* <bean:message key="ota.nokia.bookmark.label.Text" /></th>
				<td>
					<html:text property="Text" size="48"/>
					<div class="validateErrorMsg"><html:errors property="Text" /></div>
				</td>
			</tr>
			<tr>
				<th style="font-weight: normal">* <bean:message key="ota.nokia.bookmark.label.WAPBookmark" /></th>
				<td>
					<html:text property="WAPBookmark" size="48"/>
					<div class="validateErrorMsg"><html:errors property="WAPBookmark" /></div>
				</td>
			</tr>
		</tbody>
	</table>
	<div class="buttonArea" style="height: 40px; weight: 100%;">
	    <input type="submit" value="<bean:message key="ota.omacp.button.send" />">
	</div>
</html:form>
