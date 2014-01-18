<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="http://struts-menu.sf.net/tag" prefix="menu" %>
<%@ taglib uri="http://struts-menu.sf.net/tag-el" prefix="menu-el" %>

<script type="text/javascript">
<!--
// HiLight current Top Menu.
hilightTopMenu("DeviceMenu");
//-->
</script>

<menu:useMenuDisplayer 
      name="Velocity" 
      config="/common/templates/vertical_tabs.template"
      permissions="applicationMenuPermission"
      bundle="org.apache.struts.action.MESSAGE">
  <menu:displayMenu name="SubMenuDevices"/>
</menu:useMenuDisplayer>

<!-- Device Context Menu -->
<logic:present name="device">
  <!-- Space -->
  <br/>
  
  <!-- Menu: View Device -->
  <form action="<html:rewrite action="/ViewDevice"/>" method="post" name="ContextMenuForm_ViewDevice">
	  <INPUT type="hidden" name="ID" value="<bean:write name="device" property="ID"/>">
  </form>
  <!-- Menu: Edit Device -->
  <form action="<html:rewrite action="/EditDevice"/>" method="post" name="ContextMenuForm_EditDevice">
	  <INPUT type="hidden" name="ID" value="<bean:write name="device" property="ID"/>">
  </form>
  <!-- Menu: View Device Tree -->
  <form action="<html:rewrite action="/device/DeviceTree"/>" method="post" name="ContextMenuForm_ViewDeviceTree">
	  <INPUT type="hidden" name="deviceID" value="<bean:write name="device" property="ID"/>">
  </form>
  <!-- Menu: Create Job -->
  <form action="<html:rewrite action="/device/EditJob.do"/>" method="post" name="ContextMenuForm_Create_Job">
	  <INPUT type="hidden" name="deviceID" value="<bean:write name="device" property="ID"/>">
  </form>
  <!-- Menu: Job Queue -->
  <form action="<html:rewrite action="/device/Jobs"/>" method="post" name="ContextMenuForm_Job_Queue">
	  <INPUT type="hidden" name="deviceID" value="<bean:write name="device" property="ID"/>">
  </form>
  <!-- Menu: Profiles -->
  <form action="<html:rewrite action="/device/ProfileAssignments"/>" method="post" name="ContextMenuForm_View_Profiles">
	  <INPUT type="hidden" name="deviceID" value="<bean:write name="device" property="ID"/>">
  </form>
  <!-- Menu: Deployed Softwares -->
  <form action="<html:rewrite action="/device/softwares"/>" method="post" name="ContextMenuForm_Deployed_Softwares">
      <INPUT type="hidden" name="deviceID" value="<bean:write name="device" property="ID"/>">
  </form>
  <!-- Menu: Subscriber -->
  <form action="<html:rewrite action="/EditSubscriber"/>" method="post" name="ContextMenuForm_View_Subscriber">
	  <INPUT type="hidden" name="ID" value="<bean:write name="device" property="subscriber.ID"/>">
	  <INPUT type="hidden" name="method" value="view">
  </form>
  <!-- Menu: Bootstrap -->
  <form action="<html:rewrite action="/device/bootstrap"/>" method="post" name="ContextMenuForm_Bootstrap">
	  <INPUT type="hidden" name="deviceID" value="<bean:write name="device" property="ID"/>">
	  <INPUT type="hidden" name="method" value="input">
  </form>
  <!-- Menu: Notification -->
  <form action="<html:rewrite action="/device/notification/input"/>" method="post" name="ContextMenuForm_Notification">
	  <INPUT type="hidden" name="deviceID" value="<bean:write name="device" property="ID"/>">
  </form>
  <!-- Menu: Sync History -->
  <form action="<html:rewrite action="/device/history"/>" method="post" name="ContextMenuForm_History">
	  <INPUT type="hidden" name="deviceID" value="<bean:write name="device" property="ID"/>">
  </form>
  
	<menu:useMenuDisplayer 
	      name="Velocity" 
	      config="/common/templates/vertical_tabs.template"
	      permissions="applicationMenuPermission"
	      bundle="org.apache.struts.action.MESSAGE">
	  <menu:displayMenu name="SubMenuDeviceContext"/>
	</menu:useMenuDisplayer>

  <!-- Menu: Create Job for XXXXX -->
  <!-- Space -->
  <br/>
  <form action="<html:rewrite action="/device/EditJob.do"/>" method="post" name="ContextMenu_Form_Create_Job_By_Type">
	  <INPUT type="hidden" name="deviceID" value="<bean:write name="device" property="ID"/>">
	  <INPUT type="hidden" name="value(deviceID)" value="<bean:write name="device" property="ID"/>">
	  <INPUT type="hidden" name="jobType" value="">
  </form>
	<script type="text/javascript">
	<!--
	function doSubmitAJob(jobType) {
	  document.ContextMenu_Form_Create_Job_By_Type.jobType.value = jobType;
	  document.ContextMenu_Form_Create_Job_By_Type.submit();
	}
	//-->
	</script>
	<menu:useMenuDisplayer 
	      name="Velocity" 
	      config="/common/templates/vertical_tabs.template"
	      permissions="applicationMenuPermission"
	      bundle="org.apache.struts.action.MESSAGE">
	  <menu:displayMenu name="SubMenuDeviceContext4SubmitJobByType"/>
	</menu:useMenuDisplayer>

  
</logic:present>

