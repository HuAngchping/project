<%@ page import="com.npower.dm.action.BaseAction"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn"%>
<%@ taglib uri="http://struts-menu.sf.net/tag" prefix="menu"%>
<%@ taglib uri="http://struts-menu.sf.net/tag-el" prefix="menu-el"%>

<fmt:bundle basename="com.npower.dm.struts.ApplicationResources">
</fmt:bundle>

<logic:notPresent name="category">
  <logic:empty name="category">
    <table class="entityview">
      <thead>
        <tr>
          <th colspan="2">
            &nbsp;<bean:message key="page.software.recommend.root.category.name.label"/>
          </th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <th width="150">
            <bean:message key="entity.software.recommend.root.category.name.label" />
          </th>
          <td>
            <bean:message key="page.software.recommend.root.category.name.label"/>
          </td>
        </tr>
        <tr>
          <th>
            <bean:message key="entity.software.recommend.root.category.parent.label" />
          </th>
          <td>
            
          </td>
        </tr>
        <tr>
          <th>
            <bean:message key="entity.software.recommend.root.category.description.label" />
          </th>
          <td>
            <bean:message key="page.software.recommend.root.category.description.label"/>
          </td>
        </tr>
      </tbody>
    </table>
  </logic:empty>
</logic:notPresent>
<table class=table_box cellSpacing=0 cellPadding=0 width="100%" border="0">
  <tbody>
  <tr>
    <td class=table_box_heading width="100%" colSpan=5><font color="red"><b>.:</b></font> <bean:message key="page.software.recommend.view.software.list.title"/></td>
  </tr>
  <tr>
    <td>
      <display:table name="recommendSoftwares" id="software" class="simple_inline" pagesize="<%=BaseAction.getRecordsPerPage(request)%>" requestURI="./software/recommends.do${id}">
        <display:column class="rowNum">
          <c:out value="${software_rowNum}" />
        </display:column>
        <display:column property="name" sortName="name" maxLength="24" titleKey="entity.software.recommend.name.label" href="./software/software.do?action=view" paramId="id" paramProperty="id"/>
        <display:column property="vendor.name" sortName="vendor" maxLength="24" titleKey="entity.software.recommend.vendor.label" href="./software/ViewVendor.do?action=update" paramId="id" paramProperty="vendor.id"/>
        <display:column property="version" sortName="version" maxLength="24" titleKey="entity.software.recommend.version.label" href="./software/software.do?action=view" paramId="id" paramProperty="id"/>
        <display:column property="description" sortName="description" maxLength="24" titleKey="entity.software.recommend.description.label" href="./software/software.do?action=view" paramId="id" paramProperty="id"/>
      </display:table>
    </td>
  </tr>
  </tbody>
</table>
<html:form action="/software/recommendEdit" method="post">
	<div class="buttonArea" style="width: 100%">
	  <html:submit styleClass="NormalWidthButton">
	    <bean:message key="page.software.recommend.edit.button.label"/>
	  </html:submit>
	</div>
</html:form>

