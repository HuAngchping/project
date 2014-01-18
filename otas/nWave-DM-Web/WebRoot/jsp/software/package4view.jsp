<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/otas-dm.tld" prefix="dm"%>

<html:form action="/software/package/save.do">
  <html:hidden property="softwareID" />
  <html:hidden property="packageID" />
  <table class="entityview">
    <thead>
      <tr>
        <th colspan="2">
          <font color="red"><b>.:</b> </font>
          <bean:write name="software" property="vendor.name" />
          <bean:write name="software" property="name" />
          <bean:write name="software" property="version" />
        </th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <th width="150px;">
          <bean:message key="entity.software.software.name.label" />
        </th>
        <td>
          <bean:write name="softwarePackage" property="name" />
        </td>
      </tr>
      <tr>
        <th>
          <bean:message key="entity.software.package.language.label" />
        </th>
        <td>
          <bean:write name="softwarePackage" property="language" />
        </td>
      </tr>
      <tr>
        <th >
          <bean:message key="entity.software.package.published.time.label" />
        </th>
        <td>
          <bean:write name="softwarePackage" property="publishedTime" />
        </td>
      </tr>
      <logic:notEmpty name="softwarePackage" property="url">
        <!-- Remote mode -->
        <tr>
          <th>
            <bean:message key="entity.software.package.store.mode.label" />
          </th>
          <td>
            <bean:message key="entity.software.package.store.mode.remote.label" />
          </td>
        </tr>
        <tr>
          <th>
            <bean:message key="entity.software.package.url.label" />
          </th>
          <td>
            <html:link href="${softwarePackage.url}" styleClass="reference_link">
            <bean:write name="softwarePackage" property="url" />
            </html:link>
          </td>
        </tr>
      </logic:notEmpty>
      <logic:empty name="softwarePackage" property="url">
        <!-- Local mode -->
        <tr>
          <th>
            <bean:message key="entity.software.package.store.mode.label" />
          </th>
          <td>
            <bean:message key="entity.software.package.store.mode.local.label" />
          </td>
        </tr>
        <tr>
          <th>
            <bean:message
              key="entity.software.package.upload.file.label" />
          </th>
          <td>
            <html:link page="/download/software/${softwarePackage.id}/${softwarePackage.blobFilename}" styleClass="reference_link">
            <bean:write name="softwarePackage" property="blobFilename" />
            </html:link>
          </td>
        </tr>
        <tr>
          <th>
            <bean:message key="entity.software.package.mimeType.label" />
          </th>
          <td>
            <bean:write name="softwarePackage" property="mimeType" />
          </td>
        </tr>
        <tr>
          <th>
            <bean:message key="entity.software.package.size.label" />
          </th>
          <td>
            <dm:volume value="${softwarePackage.size}" unit="B" fractions="2"></dm:volume>
            [<fmt:formatNumber value="${softwarePackage.size}"/>B]
          </td>
        </tr>
      </logic:empty>
      <tr>
        <th>
          <bean:message
            key="entity.software.package.installation.options.label" />
        </th>
        <td>
          <textarea class="view_script" readonly="readonly" style="height: 200px;"><bean:write name="softwarePackage" property="installationOptions" /></textarea>
        </td>
      </tr>
      <tr>
        <th>
          <bean:message key="entity.software.package.description.label" />
        </th>
        <td>
          <bean:write name="softwarePackage" property="description"/>
        </td>
      </tr>     
    </tbody>
  </table>
  <div class="buttonArea">
    <html:button property="action"
      onclick="document.forms['PackageEditForm'].submit();"
      styleClass="NormalWidthButton">
      <bean:message key="page.button.edit.label" />
    </html:button>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <html:cancel styleClass="NormalWidthButton">
      <bean:message key="page.button.cancel.label" />
    </html:cancel>
  </div>
</html:form>

<TABLE class=table_box cellSpacing=0 cellPadding=0 width="100%" border="0" style="clear: both;">
  <TBODY>
    <TR>
      <TD class=table_box_heading width="100%" colSpan=5>
        <font color="red"><b>.:</b>
        </font>
        <bean:message key="page.package.view.target.model.list.title" />
      </TD>
    </TR>
    <TR>
      <TD>
        <display:table name="targetModels" id="record" class="simple_inline" pagesize="20" requestURI="<%=request.getContextPath() + "/software/package/view.do"%>">
          <display:column class="rowNum" style="width: 25px;">
            <c:out value="${record_rowNum}" />
          </display:column>
          <display:column titleKey="entity.software.package.model.label">
            <c:out value="${record.manufacturer.externalId}"/> <c:out value="${record.manufacturerModelId}"/>
          </display:column>
          <display:column titleKey="model.title.modelFamily">
            <c:out value="${record.familyExternalID}"/>
          </display:column>
        </display:table>
      </TD>
    </TR>
  </TBODY>
</TABLE>

<html:form action="/software/package/edit.do" method="post" styleId="PackageEditForm">
  <input type="hidden" name="packageID"
    value="<c:out value="${softwarePackage.id}"/>" />
  <input type="hidden" name="softwareID"
    value="<c:out value="${softwarePackage.software.id}"/>" />
</html:form>

