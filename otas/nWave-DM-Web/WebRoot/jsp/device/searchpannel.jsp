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

<SCRIPT type="text/javascript">
var xmlHttp;
    
function getModels4SearchPanel(manufacturer) {
  createXMLHttpRequest()
  var url = "<html:rewrite page='/ajax.do'/>?manufacturerID=" + manufacturer + "&method=getModels";
  xmlHttp.open("GET", uncache(url), true);
  xmlHttp.onreadystatechange = parseModels4SearchPanel;
  xmlHttp.send(null);
}

function parseModels4SearchPanel() {
  if (xmlHttp.readyState == 4) {
     if (xmlHttp.status == 200) {
        resText = xmlHttp.responseText
        //alert(resText);
        each = resText.split("|")
        buildModelSelect4SearchPanel(each, document.getElementById("searchModelID"), "<bean:message key="page.select.search.option.default.label"/>");
     }
  }
}

function buildModelSelect4SearchPanel(str, sel, label) {
  sel.options.length = 0;
  sel.options[sel.options.length] = new Option(label, "")
  for (var i = 0; i < str.length; i++) {
      if (str[i].length == 0) {
         continue;
      }
      each = str[i].split(",")
      sel.options[sel.options.length] = new Option(each[0],each[1])
  }
}

</SCRIPT>

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
        <html:form action="/Devices">
          <table width="100%" border="0" align="left" cellpadding="2" cellspacing="0">
	        <tr>
	          <td align="right">
	            <bean:message key="page.devices.search.title.carrier" />:
	          </td>
	          <td>
	          <html:select property="searchCarrierID" styleClass="Select4SearchPannel">
	            <html:option value=""><bean:message key="page.select.search.option.default.label"/></html:option>
	            <html:optionsCollection name="carrierOptions"/>
	          </html:select>
	          </td>
	        </tr>
	        <tr>
	          <td align="right">
	            <bean:message key="page.devices.search.title.manufacturer" />:
	          </td>
	          <td>
	          <html:select property="searchManufacturerID" styleClass="Select4SearchPannel" onchange="getModels4SearchPanel(this.value)">
	            <html:option value=""><bean:message key="page.select.search.option.default.label"/></html:option>
	            <html:optionsCollection name="manufacturerOptions"/>
	          </html:select>
	          </td>
	        </tr>
	        <tr>
	          <td align="right">
	            <bean:message key="page.devices.search.title.model" />:
	          </td>
	          <td>
	          <html:select property="searchModelID" styleId="searchModelID" styleClass="Select4SearchPannel">
	            <html:option value=""><bean:message key="page.select.search.option.default.label"/></html:option>
	            <html:optionsCollection name="modelOptions"/>
	          </html:select>
              </td>
            </tr>
	        <tr>
	          <td align="right">
	            <bean:message key="page.devices.search.title.searchExternalID" />:
	          </td>
	          <td>
	          <html:text property="searchExternalID" size="16"/>
	          </td>
	        </tr>
          <tr>
            <td align="right">
              <bean:message key="page.devices.search.title.searchIMSI" />:
            </td>
            <td>
            <html:text property="searchIMSI" size="16"/>
            </td>
          </tr>
	        <tr>
	          <td align="right">
	            <bean:message key="page.devices.search.title.searchPhoneNumber" />:
	          </td>
	          <td>
	          <html:text property="searchPhoneNumber" size="16"/>
	          </td>
	        </tr>
	        <tr>
	          <td align="right">
	            <bean:message key="page.devices.search.title.searchActivity" />:
	          </td>
	          <td>
	          <html:select property="searchActivity">
	            <html:option value=""><bean:message key="page.select.search.option.default.label"/></html:option>
                <html:option key="page.select.search.option.true.label" value="true"/>
                <html:option key="page.select.search.option.false.label" value="false"/>
	          </html:select>
	          </td>
	        </tr>
          <tr>
            <td align="right">
              <bean:message key="page.devices.search.title.searchBooted" />:
            </td>
            <td>
            <html:select property="searchBooted">
              <html:option value=""><bean:message key="page.select.search.option.default.label"/></html:option>
              <html:option key="page.select.search.option.true.label" value="true"/>
              <html:option key="page.select.search.option.false.label" value="false"/>
            </html:select>
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="page.subscribers.search.title.service_provider" />:
            </td>
            <td>
            <html:select property="searchServiceProviderID" styleClass="Select4SearchPannel">
              <html:option value=""><bean:message key="page.select.search.option.default.label"/></html:option>
              <html:optionsCollection name="serviceProviderOptions"/>
            </html:select>
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
