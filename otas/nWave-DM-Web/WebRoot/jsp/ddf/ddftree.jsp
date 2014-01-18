<%@ taglib uri="http://struts-menu.sf.net/tag" prefix="menu" %>
<%@ taglib uri="http://struts-menu.sf.net/tag-el" prefix="menu-el" %>

<script type="text/javascript">
<menu:useMenuDisplayer name="Velocity" repository="repository" config="/common/templates/xtree.template"
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

