<%@ taglib uri="http://struts-menu.sf.net/tag" prefix="menu" %>
<%@ taglib uri="http://struts-menu.sf.net/tag-el" prefix="menu-el" %>

<script type="text/javascript">
<!--
// HiLight current Top Menu.
hilightTopMenu("AuditMenu");
//-->
</script>

<menu:useMenuDisplayer 
      name="Velocity" 
      config="/common/templates/vertical_tabs.template"
      permissions="applicationMenuPermission"
      bundle="org.apache.struts.action.MESSAGE">
  <menu:displayMenu name="SubMenuAudit"/>
</menu:useMenuDisplayer>
