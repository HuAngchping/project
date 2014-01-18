<%@ taglib uri="http://struts-menu.sf.net/tag" prefix="menu" %>
<%@ taglib uri="http://struts-menu.sf.net/tag-el" prefix="menu-el" %>

<script type="text/javascript">
<!--
// HiLight current Top Menu.
hilightTopMenu("SecurityMenu");
//-->
</script>

<menu:useMenuDisplayer 
      name="Velocity" 
      config="/common/templates/vertical_tabs.template"
      bundle="org.apache.struts.action.MESSAGE">
  <menu:displayMenu name="SubMenuErrors"/>
</menu:useMenuDisplayer>

