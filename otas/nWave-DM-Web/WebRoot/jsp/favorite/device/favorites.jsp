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

<display:table name="favorites" id="favorite" class="simple" pagesize="10" requestURI="favorite/favorites.do">
  <display:column class="rowNum">
    <c:out value="${favorite_rowNum}" />
  </display:column>
  <display:column property="name" titleKey="page.message.favorite.name.label" href="favorite/viewFavorite.do?action=view" paramId="favoriteId" paramProperty="favoriteId" />
  <display:column property="description" titleKey="page.message.favorite.description.label" maxLength="24" href="favorite/viewFavorite.do?action=view" paramId="favoriteId" paramProperty="favoriteId" />
  <display:column titleKey="page.message.favorite.devices.label" href="favorite/viewFavorite.do?action=view" paramId="favoriteId" paramProperty="favoriteId">
    <c:out value="${fn:length(favorite.favoriteDevices)}" />
  </display:column>
  <logic:present name="favorite">
	  <logic:equal name="favorite" property="owner" value="${currentOwner}">
	    <display:column titleKey="page.message.favorite.owner.label" href="favorite/viewFavorite.do?action=view" paramId="favoriteId" paramProperty="favoriteId">
	      <c:out value="${favorite.owner}"/> <bean:message key="page.messae.favorite.own.label" />
	    </display:column>
	  </logic:equal>
  </logic:present>
  <logic:present name="favorite">
	  <logic:notEqual name="favorite" property="owner" value="${currentOwner}">
	    <display:column titleKey="page.message.favorite.owner.label" href="favorite/viewFavorite.do?action=view" paramId="favoriteId" paramProperty="favoriteId">
	      <c:out value="${favorite.owner}" /> <bean:message key="page.message.favorite.share.label" />
	    </display:column>
	  </logic:notEqual>
  </logic:present>
  <logic:present name="favorite">
	  <logic:equal name="favorite" property="owner" value="${currentOwner}">
		  <display:column>
		    <html:link action="/favorite/viewFavorite.do?action=view" paramId="favoriteId" paramName="favorite" paramProperty="favoriteId">
		      <bean:message key="page.message.favorite.view.label" />
		    </html:link>|
		    <html:link action="/favorite/editFavorite.do?action=edit" paramId="favoriteId" paramName="favorite" paramProperty="favoriteId">
		      <bean:message key="page.message.favorite.edit.label" />
		    </html:link>| <a href="<html:rewrite page='/favorite/deleteFavorite.do'/>?action=delete&favoriteId=<c:out value='${favorite.favoriteId}'/>" onclick='javascript: return confirm("<bean:message key="message.delete.areYourSureDelete"/>");'><bean:message key="page.message.favorite.delete.label" /></a>
		  </display:column>
		</logic:equal>
	</logic:present>
</display:table>

<html:form action="/favorite/editFavorite.do?action=edit" method="post">
	<div class="buttonArea" style="width: 100%">
		<html:submit property="action">
		  <bean:message key="page.message.favorite.button.create.label" />
		</html:submit>
	</div>
</html:form>
