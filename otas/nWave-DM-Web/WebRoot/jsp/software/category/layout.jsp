<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested"	prefix="nested"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn"%>
<%@ taglib uri="http://struts-menu.sf.net/tag" prefix="menu"%>
<%@ taglib uri="http://struts-menu.sf.net/tag-el" prefix="menu-el"%>

<fmt:bundle basename="com.npower.dm.struts.ApplicationResources">
</fmt:bundle>

<table border="0">
	<tr>
		<td valign="top">
			<div class="deviceTreeContainer">
				<tiles:insert attribute="categoryTree"></tiles:insert>
			</div>
		</td>
		<td valign="top" width="100%">
			<div class="deviceNodeContainer">
				<tiles:insert attribute="categoryContent"></tiles:insert>
			</div>
		</td>
	</tr>
</table>
