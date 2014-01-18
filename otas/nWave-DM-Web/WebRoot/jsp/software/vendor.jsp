<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"
	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"
	prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested"
	prefix="nested"%>

<html:form action="/software/SaveVendor.do">
	<html:hidden property="action" />
	<html:hidden property="id" />
	<table class="entityview">
		<thead>
			<tr>
				<th colspan="2">
					&nbsp;
				</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<th width="150">
					*
					<bean:message key="entity.software.vendor.name.label" />
				</th>
				<td>
					<html:text property="name" size="64"/>
					<div class="validateErrorMsg">
						<html:errors property="name" />
					</div>
				</td>
			</tr>
            <tr>
                <th>
                    <bean:message key="entity.software.vendor.webSite.label" />
                </th>
                <td>
                    <html:text property="webSite" size="64"/>
                    <div class="validateErrorMsg">
                        <html:errors property="webSite" />
                    </div>
                </td>
            </tr>
			<tr>
				<th>
					<bean:message key="entity.software.vendor.description.label" />
				</th>
				<td>
					<html:textarea property="description" style="width: 100%; height: 260px;"/>
					<div class="validateErrorMsg">
						<html:errors property="description" />
					</div>
				</td>
			</tr>
		</tbody>
	</table>
	<div class="buttonArea">
		<logic:empty name="VendorForm" property="id">
			<html:submit styleClass="NormalWidthButton">
				<bean:message key="page.button.create.label" />
			</html:submit>
		</logic:empty>
		<logic:notEmpty name="VendorForm" property="id">
			<html:submit styleClass="NormalWidthButton">
				<bean:message key="page.button.update.label" />
			</html:submit>
		</logic:notEmpty>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<html:reset styleClass="NormalWidthButton">
			<bean:message key="page.button.reset.label" />
		</html:reset>
		<html:cancel styleClass="NormalWidthButton">
			<bean:message key="page.button.cancel.label" />
		</html:cancel>
		<logic:notEmpty name="VendorForm" property="id">
			<html:button property="action"
				onclick="document.forms['VendorForm'][1].submit();"
				styleClass="NormalWidthButton">
				<bean:message key="page.button.view.label" />
			</html:button>
		</logic:notEmpty>
	</div>
</html:form>

<html:form action="/software/ViewVendor.do" method="post">
	<logic:notEmpty name="VendorForm" property="id">
		<html:hidden property="id" />
	</logic:notEmpty>
</html:form>
