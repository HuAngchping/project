<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested"	prefix="nested"%>

<html:form action="/software/categorySave">
	<html:hidden property="id" />
	<table class="entityview">
		<thead>
        <tr>
          <th colspan="2">
            <logic:present name="category">
            &nbsp;<bean:write name="category" property="pathAsString" />
            </logic:present>
          </th>
        </tr>
		</thead>
		<tbody>
			<tr>
				<th width="150">
					*
					<bean:message key="entity.software.category.name.label" />
				</th>
				<td>
					<html:text property="name" />
					<div class="validateErrorMsg">
						<html:errors property="name" />
					</div>
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="entity.software.category.parent.label" />
				</th>
				<td>
					<html:select property="parentId" >
					   <html:option value=""><bean:message key="page.software.category.choice.parent"/></html:option>
               <html:optionsCollection name="parents" label="pathAsString" value="id"/>
             </html:select>
				</td>
			</tr>			
			<tr>
				<th>
					<bean:message key="entity.software.category.description.label" />
				</th>
				<td>
					<html:textarea property="description" />
					<div class="validateErrorMsg">
						<html:errors property="description" />
					</div>
				</td>
			</tr>
		</tbody>
	</table>
	<div class="buttonArea">
		<logic:empty name="CategoryForm" property="id">
			<html:submit styleClass="NormalWidthButton">
				<bean:message key="page.button.create.label" />
			</html:submit>
		</logic:empty>
		<logic:notEmpty name="CategoryForm" property="id">
			<html:submit styleClass="NormalWidthButton">
				<bean:message key="page.button.update.label" />
			</html:submit>
		</logic:notEmpty>
	</div>
</html:form>