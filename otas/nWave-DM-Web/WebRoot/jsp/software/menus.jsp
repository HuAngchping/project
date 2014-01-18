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

<script type="text/javascript">
<!--
// HiLight current Top Menu.
hilightTopMenu("SoftwareMenu");
//-->
</script>

<menu:useMenuDisplayer 
      name="Velocity" 
      config="/common/templates/vertical_tabs.template"
      permissions="applicationMenuPermission"
      bundle="org.apache.struts.action.MESSAGE">
  <menu:displayMenu name="SubMenuSoftwares"/>
</menu:useMenuDisplayer>

