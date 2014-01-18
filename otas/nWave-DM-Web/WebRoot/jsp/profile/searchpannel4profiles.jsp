<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://struts-menu.sf.net/tag" prefix="menu" %>
<%@ taglib uri="http://struts-menu.sf.net/tag-el" prefix="menu-el" %>

<!--  Insert space -->
<br>

<!--  Search Panel -->
<table align="CENTER" width="90%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="1%"><img src="common/images/table_curve_topleft_trans.gif" width="9" height="28"></td>
		<td width="10%" class="columnheading" valign="middle"><img src="common/images/menu_search_icon.png"></td>
		<td width="88%" class="columnheading" valign="middle">&nbsp;&nbsp;<bean:message key="page.search.title.label" /></td>
		<td width="1%" align="right"><img src="common/images/table_curve_topright_trans.gif" width="10" height="28"></td>
	</tr>
</table>
<table width="90%" border="0" align="center" cellpadding="0" cellspacing="5" class="mnutableborder">
	<tr>
		<td>
        <html:form action="/ProfileConfigs">
          <table width="100%" border="0" align="left" cellpadding="2" cellspacing="0">
	        <tr>
	          <td align="right">
	            <bean:message key="page.profileconfigs.search.title.category" />:
	          </td>
	          <td>
	          <html:select property="searchCategoryID" styleClass="Select4SearchPannel">
	            <html:option value=""><bean:message key="page.select.search.option.default.label"/></html:option>
	            <html:optionsCollection name="categoryOptions"/>
	          </html:select>
	          </td>
	        </tr>
	        <tr>
	          <td align="right">
	            <bean:message key="page.profileconfigs.search.title.carrier" />:
	          </td>
	          <td>
	          <html:select property="searchCarrierID" styleClass="Select4SearchPannel">
	            <html:option value=""><bean:message key="page.select.search.option.default.label"/></html:option>
	            <html:optionsCollection name="carrierOptions"/>
	          </html:select>
	          </td>
	        </tr>
	        <tr>
	          <td align="right">
	            <bean:message key="profile.config.isUserProfile.label" />:
	          </td>
	          <td>
	          <html:select property="searchIsUserProfile" styleClass="Select4SearchPannel">
	            <html:option value=""><bean:message key="page.select.search.option.default.label"/></html:option>
	            <html:option value="true"><bean:message key="page.select.search.option.true.label"/></html:option>
	            <html:option value="false"><bean:message key="page.select.search.option.false.label"/></html:option>
	          </html:select>
	          </td>
	        </tr>
	        <tr>
	          <td align="right">
	            <bean:message key="page.profileconfigs.search.title.searchText" />:
	          </td>
	          <td>
	          <html:text property="searchText" size="16"/>
	          </td>
	        </tr>
	        <tr>
	          <td align="right">
	            <bean:message key="page.select.search.recordsPerPage.label" />:
	          </td>
	          <td>
	            <html:select property="recordsPerPage">
	              <html:optionsCollection name="recordsPerPageOptions"/>
	            </html:select>
	          </td>
	        </tr>
	        <tr>
	          <td align="right"></td>
	          <td>
	            <html:submit><bean:message key="page.button.search.label" /></html:submit>
	          </td>
	        </tr>
          </table>
        </html:form>
		</td>
	</tr>
</table>
