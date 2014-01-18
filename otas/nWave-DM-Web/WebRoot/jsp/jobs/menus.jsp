<%@ taglib uri="http://struts-menu.sf.net/tag" prefix="menu" %>
<%@ taglib uri="http://struts-menu.sf.net/tag-el" prefix="menu-el" %>
<script type="text/javascript">
<!--
// HiLight current Top Menu.
hilightTopMenu("JobMenu");
//-->
</script>

<menu:useMenuDisplayer 
      name="Velocity" 
      config="/common/templates/vertical_tabs.template"
      permissions="applicationMenuPermission"
      bundle="org.apache.struts.action.MESSAGE">
  <menu:displayMenu name="SubMenuJobs"/>
</menu:useMenuDisplayer>

<br>

<menu:useMenuDisplayer 
      name="Velocity" 
      config="/common/templates/vertical_tabs.template"
      bundle="org.apache.struts.action.MESSAGE">
  <menu:displayMenu name="SubMenuOMA_CP_OTAProvision"/>
</menu:useMenuDisplayer>

<br>

<menu:useMenuDisplayer 
      name="Velocity" 
      config="/common/templates/vertical_tabs.template"
      bundle="org.apache.struts.action.MESSAGE">
  <menu:displayMenu name="SubMenuNokiaOTA_Provision"/>
</menu:useMenuDisplayer>
