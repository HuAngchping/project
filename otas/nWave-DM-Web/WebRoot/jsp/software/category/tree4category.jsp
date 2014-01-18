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
<script type="text/javascript">
					<menu:useMenuDisplayer name="Velocity" repository="categoryTreeRepository" config="/common/templates/xtree.template"
					  bundle="org.apache.struts.action.MESSAGE">
					if (document.getElementById) {
					  <menu:displayMenu name="root"/>
					} else {
					  var msg = "Your browser does not support document.getElementById().\n";
					  msg += "You must use a modern browser for this menu.";
					  alert(msg);
					}
					</menu:useMenuDisplayer>
				</script>

