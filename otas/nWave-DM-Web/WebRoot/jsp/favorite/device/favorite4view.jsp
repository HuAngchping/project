<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %>

<table class="entityview">
  <thead>
    <tr>
      <th colspan="2">
        &nbsp;<bean:write name="favorite" property="name" />
      </th>
    </tr>
  </thead>
  <tbody>
    <logic:present name="favorite">
      <tr>
        <th width="150">
          <bean:message key="page.message.favorite.name.label" />
        </th>
        <td>
          <bean:write name="favorite" property="name"/>
        </td>
      </tr>
    </logic:present>
    <tr>
      <th>
        <bean:message key="page.message.favorite.description.label" />
      </th>
      <td>
        <bean:write name="favorite" property="description" />
      </td>
    </tr>
  </tbody>
</table>
<table class=table_box cellSpacing=0 cellPadding=0 width="100%" border="0">
  <tbody>
  <tr>
    <td class=table_box_heading width="100%" colSpan=5><font color="red"><b>.:</b></font> <bean:message key="page.message.favorite.device.list.title"/></td>
  </tr>
  <tr>
    <td>
<display:table name="devices" id="device" class="simple" pagesize="10" requestURI="favorite/viewFavorite.do?action=view">
  <display:column class="rowNum">
    <c:out value="${Device_rowNum}" />
  </display:column>
  <display:column property="externalId" sortable="true" titleKey="device.title.externalId" 
                  href="<%=request.getContextPath() + "/ViewDevice.do"%>" paramId="ID" paramProperty="ID"/>
  <display:column property="subscriber.phoneNumber" sortable="true" titleKey="device.title.phonenumber" 
                  href="<%=request.getContextPath() + "/ViewDevice.do"%>" paramId="ID" paramProperty="ID"/>
  <display:column property="model.manufacturer.name" sortable="true" titleKey="device.title.manufacturer" 
                  href="<%=request.getContextPath() + "/ViewDevice.do"%>" paramId="ID" paramProperty="ID"/>
  <display:column sortable="true" titleKey="device.title.model">
                  <html:link action="/ViewModel.do?action=update" styleClass="reference_link" target="_blank" paramId="ID" paramName="device" paramProperty="model.ID"><c:out value="${device.model.name}"></c:out></html:link>
  </display:column>
  <display:column>
    <html:link action="/ViewDevice.do" paramName="device" paramId="ID" paramProperty="ID">
      <bean:message key="page.message.favorite.device.view.label" />
    </html:link>| <a href="<html:rewrite page='/favorite/removeDevice.do'/>?action=remove&favoriteId=<c:out value='${favorite.favoriteId}'/>&deviceId=<c:out value='${device.ID}'/>" onclick='javascript: return confirm("<bean:message key="message.delete.areYourSureDelete"/>");'><bean:message key="page.message.favorite.device.move.label" /></a>
  </display:column>
</display:table>
    </td>
  </tr>
  </tbody>
</table>
<html:form action="/favorite/favorites">
  <div class="buttonArea" style="width: 100%">
	  <html:submit property="return">
	    <bean:message key="page.message.favorite.button.return.label"/>
	  </html:submit>
	</div>
</html:form>
