<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

<logic:present name="carrierOptions">
      <tr>
        <th width="250"><bean:message key="device.title.carrier"/></th>
        <td>
          <html:select property="carrierID">
            <html:option value=""><bean:message key="page.device.choice.carrier"/></html:option>
            <html:optionsCollection name="carrierOptions"/>
          </html:select>
            <div class="validateErrorMsg"><html:errors property="carrierID"/></div>
        </td>
      </tr>
</logic:present>
			<tr>
				<th width="250">* <bean:message key="ota.omacp.label.targets.msisdn" /></th>
				<td>
					<html:textarea property="msisdn" />
					<div class="validateErrorMsg"><html:errors property="msisdn" /></div>
					<bean:message key="page.Jobs.jobtypes.targetDevice.phoneNumbers.message"/>
				</td>
			</tr>
      