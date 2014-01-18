<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<html:form action="/software/SaveVendor.do">
	<input type="hidden" name="action" value="update" />
	<html:hidden property="id" />
	<table class="entityview">
		<thead>
			<tr>
				<th colspan="2">
					&nbsp;<bean:write name="vendor" property="name" />
				</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<th width="150">
					<bean:message key="entity.software.vendor.name.label" />
				</th>
				<td>
					<bean:write name="vendor" property="name" />
				</td>
			</tr>
            <tr>
                <th width="150">
                    <bean:message key="entity.software.vendor.webSite.label" />
                </th>
                <td>
                    <bean:write name="vendor" property="webSite" />
                </td>
            </tr>
			<tr>
				<th width="150">
					<bean:message key="entity.software.vendor.description.label" />
				</th>
				<td>
					<bean:write name="vendor" property="description" />
				</td>
			</tr>
		</tbody>
	</table>
	<div class="buttonArea">
		<logic:notEmpty name="VendorForm" property="id">
			<html:button property="action"
				onclick="document.forms['VendorForm'][1].submit();"
				styleClass="NormalWidthButton">
				<bean:message key="page.button.edit.label" />
			</html:button>
		</logic:notEmpty>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<html:cancel styleClass="NormalWidthButton">
			<bean:message key="page.button.cancel.label" />
		</html:cancel>
	</div>
</html:form>

<html:form action="/software/EditVendor.do" method="post">
	<logic:notEmpty name="VendorForm" property="id">
		<input type="hidden" name="action" value="update" />
		<html:hidden property="id" />
	</logic:notEmpty>
</html:form>
