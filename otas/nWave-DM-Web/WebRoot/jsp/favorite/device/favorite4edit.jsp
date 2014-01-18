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

<bean:message key="page.tips.message.label" />
<html:form action="/favorite/saveFavorite.do?action=save" method="post">
  <logic:present name="favorite">
    <html:hidden property="favoriteId" value="${favorite.favoriteId}" />
  </logic:present>
  <table class="entityview">
    <tr>
      <th>
       * <bean:message key="page.message.favorite.name.label" />
      </th>
      <td>
        <html:text property="name" />
        <div class="validateErrorMsg">
          <html:errors property="name"/>
        </div>
      </td>
    </tr>
    <tr>
      <th>
        <bean:message key="page.message.favorite.description.label" />
      </th>
      <td>
        <html:textarea property="description" rows="5" cols="30"/>
      </td>
    </tr>
    <tr>
      <th>
        <bean:message key="page.message.favorite.shared.label" />
      </th>
      <td>
        <html:checkbox property="shared" />
      </td>
    </tr>
  </table>
  <div class="buttonArea">
	  <logic:present name="favorite">
	    <html:submit property="action">
	      <bean:message key="page.message.favorite.button.save.label" />
	    </html:submit>
	  </logic:present>
	  <logic:notPresent name="favorite">
	    <html:submit property="action">
	      <bean:message key="page.message.favorite.button.create.label" />
	    </html:submit>
	  </logic:notPresent>
    <html:button property="return" onclick="document.forms[2].submit();">
      <bean:message key="page.message.favorite.button.return.label"/>
    </html:button>
  </div>
</html:form>
<html:form action="/favorite/favorites" method="post">
</html:form>
