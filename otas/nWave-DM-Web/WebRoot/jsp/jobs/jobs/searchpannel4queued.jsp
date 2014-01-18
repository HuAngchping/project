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
        <html:form action="/jobs/list/queued">
          <table width="100%" border="0" align="left" cellpadding="2" cellspacing="0">
	        <tr>
	          <td align="right">
	            <bean:message key="page.device.jobs.job.type.label"/>:
	          </td>
	          <td>
				<html:select property="jobType" styleClass="Select4SearchPannel">
				  <html:option value=""><bean:message key="page.jobs.jobs.allTypes.option"/></html:option>
				  <html:optionsCollection name="jobTypes"/>
				</html:select>
	          </td>
	        </tr>
            <tr>
              <td align="right">
                <bean:message key="page.device.jobs.job.mode.label"/>:
              </td>
              <td>
                <html:select property="jobMode">
                  <html:option value=""><bean:message key="page.jobs.jobs.allModes.option"/></html:option>
                  <html:optionsCollection name="jobModes"/>
                </html:select>
              </td>
            </tr>
	        <tr>
	          <td align="right" style="white-space: nowrap;">
	            <bean:message key="page.device.jobs.job.device.status.label"/>:
	          </td>
	          <td>
				<html:select property="deviceJobStatus">
				  <html:option value=""><bean:message key="page.jobs.jobs.allStates.option"/></html:option>
				  <html:optionsCollection name="deviceJobStates"/>
				</html:select>
              </td>
            </tr>
	        <tr>
	          <td align="right">
	            <bean:message key="page.devices.search.title.searchExternalID" />:
	          </td>
	          <td>
	          <html:text property="deviceID" size="16"/>
	          </td>
	        </tr>
	        <tr>
	          <td align="right">
	            <bean:message key="page.devices.search.title.searchPhoneNumber" />:
	          </td>
	          <td>
	          <html:text property="phoneNumber" size="16"/>
	          </td>
	        </tr>
	        <tr>
	          <td align="right">
	            <bean:message key="page.devices.search.title.searchText" />:
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
