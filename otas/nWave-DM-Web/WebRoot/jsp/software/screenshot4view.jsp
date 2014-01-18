<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<html:form action="/software/editScreenShot">
	<html:hidden property="screenShotID" />
    <html:hidden property="softwareID" />
	<logic:present name="screenShot">
		<table class="entityview">
      <thead>
        <tr>
          <th colspan="2">
            <font color="red"><b>.:</b></font>  <bean:write name="software" property="vendor.name"/> <bean:write name="software" property="name"/> <bean:write name="software" property="version"/>
          </th>
        </tr>
      </thead>
			<tbody>
				<tr>
					<th>
						<bean:message key="entity.software.screenshot.description.label" />
					</th>
					<td>
						<bean:write name="screenShot" property="description" />
					</td>
				</tr>
				<tr>
					<th>
						<bean:message key="entity.software.screenshot.imgage.label" />
					</th>
					<td>
             <html:img page="/software/screenShot.do?action=showImg" paramId="screenShotID" paramName="screenShot" paramProperty="id"/>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="buttonArea">
			<html:submit styleClass="NormalWidthButton">
				<bean:message key="page.button.edit.label" />
			</html:submit>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<html:cancel styleClass="NormalWidthButton">
				<bean:message key="page.button.cancel.label" />
			</html:cancel>
		</div>
	</logic:present>
</html:form>

