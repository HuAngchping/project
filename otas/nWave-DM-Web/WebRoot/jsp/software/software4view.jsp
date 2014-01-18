<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<html:form action="/software/editSoftware">
	<html:hidden property="id" />

	<logic:present name="software">
		<table class="entityview">
			<thead>
				<tr>
					<th colspan="2">
						&nbsp;<bean:write name="software" property="name"/>
					</th>
				</tr>
			</thead>
			<tbody>
			<tr>
				<th width="150">
					<bean:message key="entity.software.software.name.label" />
				</th>
				<td>
					<bean:write name="software" property="name"/>
				</td>
			</tr>
      <tr>
        <th>
          <bean:message key="entity.software.software.version.label" />
        </th>
        <td>
          <bean:write name="software" property="version"/>
        </td>
      </tr>
			<tr>
				<th>
					<bean:message key="entity.software.category.label" />
				</th>
				<td>
				    <c:out value='${software.category.pathAsString}'/>
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="entity.software.vendor.label" />
				</th>
				<td>
					<c:out value='${software.vendor.name}'/>
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="entity.software.software.externalId.label" />
				</th>
				<td>
					<bean:write name="software" property="externalId"/>
				</td>
			</tr>
      <tr>
        <th>
          <bean:message key="entity.software.software.status.label" />
        </th>
        <td>
          <bean:message key="meta.software.status.${software.status}.label"/>
        </td>
      </tr>
			<tr>
				<th>
					<bean:message key="entity.software.software.licenseType.label" />
				</th>
				<td>
          <logic:notEmpty name="software" property="licenseType">
					  <bean:message key="meta.software.license_type.${software.licenseType}.label"/>
          </logic:notEmpty>
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="entity.software.software.description.label" />
				</th>
				<td>
					<bean:write name="software" property="description"/>
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

<html:form action="/software/package/edit">
<TABLE class=table_box cellSpacing=0 cellPadding=0 width="100%" border="0">
  <TBODY>
  <TR>
    <TD class=table_box_heading width="100%" colSpan=5><font color="red"><b>.:</b></font> <bean:message key="page.software.view.package.list.title"/></TD>
  </TR>
  <TR>
    <TD>
      <display:table name="software.packages" id="record" class="simple_inline" pagesize="20" requestURI="<%=request.getContextPath() + "/software/software.do"%>">
        <display:column class="rowNum" style="width: 25px;">
          <c:out value="${record_rowNum}"/>
        </display:column>
        <display:column property="name" titleKey="entity.software.software.name.label" maxLength="16"/>
        <display:column titleKey="entity.software.package.store.mode.label">
          <logic:notEmpty name="record" property="url">
            <bean:message key="entity.software.package.store.mode.remote.label" />
          </logic:notEmpty>
          <logic:empty name="record" property="url">
            <bean:message key="entity.software.package.store.mode.local.label" />
          </logic:empty>
        </display:column>
        <display:column property="mimeType" titleKey="entity.software.package.mimeType.label"/>
        <display:column property="language" titleKey="entity.software.package.language.label"/>
        <display:column property="publishedTime" titleKey="entity.software.package.published.time.label"/>
        <display:column>
          <a href="<html:rewrite page='/software/package/view.do'/>?softwareID=<c:out value='${record.software.id}'/>&packageID=<c:out value='${record.id}'/>"><bean:message key="page.button.view.label" /></a> | 
          <a href="<html:rewrite page='/software/package/edit.do'/>?softwareID=<c:out value='${record.software.id}'/>&packageID=<c:out value='${record.id}'/>"><bean:message key="page.button.edit.label" /></a> | 
          <a href="<html:rewrite page='/software/package/remove.do'/>?softwareID=<c:out value='${record.software.id}'/>&packageID=<c:out value='${record.id}'/>" onclick='javascript: return confirm("<bean:message key="message.delete.areYourSureDelete"/>");'><bean:message key="page.button.remove.label" /></a>
        </display:column>
      </display:table>
    </TD>
  </TR>
  </TBODY>
</TABLE>

<div class="buttonArea">
  <input type="hidden" name="softwareID" value="<c:out value="${software.id}"/>"/>
   <html:submit styleClass="NormalWidthButton">
    <bean:message key="page.software.software.button.add.package.label" />
   </html:submit>
</div>
</html:form>

<html:form action="/software/editScreenShot">
<TABLE class=table_box cellSpacing=0 cellPadding=0 width="100%" border="0">
  <TBODY>
  <TR>
    <TD class=table_box_heading width="100%" colSpan=5><font color="red"><b>.:</b></font> <bean:message key="page.software.view.screenshot.list.title"/></TD>
  </TR>
  <TR>
    <TD>
    <display:table name="software.screenShoots" id="screenshot" class="simple_inline">
	  <display:column class="rowNum" href="<%=request.getContextPath() + "/software/screenshot.do?action=view"%>" paramId="screenShotID" paramProperty="id">
		<c:out value="${screenshot_rowNum}" />
	  </display:column>
	  <display:column property="description" sortable="true" sortName="description" maxLength="24" titleKey="entity.software.screenshot.description.label" href="<%=request.getContextPath() + "/software/screenShot.do?action=view"%>" paramId="screenShotID" paramProperty="id" />
	  <display:column>
		<a href="<html:rewrite page='/software/screenShot.do'/>?action=view&screenShotID=<c:out value='${screenshot.id}'/>"><bean:message key="page.button.view.label" /></a> | 
        <a href="<html:rewrite page='/software/editScreenShot.do'/>?screenShotID=<c:out value='${screenshot.id}'/>&softwareID=<c:out value='${screenshot.software.id}'/>"><bean:message key="page.button.edit.label" /></a> | 
        <a href="<html:rewrite page='/software/screenShot.do'/>?action=remove&screenShotID=<c:out value='${screenshot.id}'/>" onclick='javascript: return confirm("<bean:message key="message.delete.areYourSureDelete"/>");'><bean:message key="page.button.remove.label" /></a>
	  </display:column>
   </display:table>
    </TD>
  </TR>
  </TBODY>
</TABLE>
<div class="buttonArea" style="height: 40px;">
     <input type="hidden" name="softwareID" value="<c:out value="${software.id}"/>"/>
	  <html:submit styleClass="NormalWidthButton">
		<bean:message key="page.software.software.button.add.screenshot.label" />
	  </html:submit>
 </div>
 </html:form>
 <br>