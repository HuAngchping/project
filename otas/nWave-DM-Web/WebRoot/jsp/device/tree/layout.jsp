<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<table border="0">
  <tr>
    <td valign="top">
	<div class="deviceTreeContainer">
		<!-- Include the Tree Pannel -->
		<tiles:insert attribute="deviceTree" />
	</div>
    </td>
    <td valign="top">
	<div class="deviceNodeContainer">
		<!-- Work pannel -->
		<tiles:insert attribute="deviceTreeNodes" />
	</div>
    </td>
  </tr>
</table>

