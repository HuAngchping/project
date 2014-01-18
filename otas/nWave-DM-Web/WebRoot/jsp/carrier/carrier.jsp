<%@ page language="java"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<SCRIPT type="text/javascript">
</SCRIPT>

<div class="messageArea">
	<bean:message key="page.message.required" />
	<bean:message key="page.message.input" />
</div>

<html:form action="/SaveCarrier">
	<html:hidden property="action" />
	<html:hidden property="ID" />
	<table class="entityview">
		<thead>
			<tr>
				<th colspan="2">
					&nbsp;
				</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<th width="250" style="white-space: nowrap">
					* <bean:message key="carrier.title.name" />
				</th>
				<td>
					<html:text property="name" />
					<div class="validateErrorMsg">
						<html:errors property="name" />
					</div>
				</td>
			</tr>
			<tr>
				<th style="white-space: nowrap">
					* <bean:message key="carrier.title.externalID" />
				</th>
				<td>
					<html:text property="externalID" />
					<div class="validateErrorMsg">
						<html:errors property="externalID" />
					</div>
				</td>
			</tr>
			<tr>
				<th style="white-space: nowrap">
					* <bean:message key="carrier.title.country" />
				</th>
				<td>
					<html:select property="countryID">
						<html:option value="">
							<bean:message key="carrier.page.choice.country" />
						</html:option>
						<html:optionsCollection name="countryOptions" />
					</html:select>
					<div class="validateErrorMsg">
						<html:errors property="validateErrorMsg" />
					</div>
				</td>
			</tr>
		    <logic:notEmpty name="CarrierForm" property="ID">
			<tr>
				<th style="white-space: nowrap">
					<bean:message key="carrier.title.bootstrapNapProfile" />
				</th>
				<td>
			        <html:select property="profileID4NAP">
			          <html:option value=""><bean:message key="page.select.option.default.label"/></html:option>
			          <html:optionsCollection name="profileOptions4NAP"/>
			        </html:select>
					<div class="validateErrorMsg">
						<html:errors property="profileID4NAP" />
					</div>
				</td>
			</tr>
            <tr>
             <th style="white-space: nowrap">
              <bean:message key="carrier.title.bootstrapDmProfile" />
             </th>
             <td>
                    <html:select property="profileID4DM">
                      <html:option value=""><bean:message key="page.select.option.default.label"/></html:option>
                      <html:optionsCollection name="profileOptions4DM"/>
                    </html:select>
              <div class="validateErrorMsg">
               <html:errors property="profileID4DM" />
              </div>
             </td>
            </tr>
			</logic:notEmpty>
			<tr>
				<th style="white-space: nowrap">
					<bean:message key="carrier.title.phoneNumberPolicy" />
				</th>
				<td>
					<html:textarea property="phoneNumberPolicy"/><br>
					<bean:message key="page.carrier.phoneNumberPolicy.message" arg1="{1}"/>
					<div class="validateErrorMsg">
						<html:errors property="phoneNumberPolicy" />
					</div>
				</td>
			</tr>
			<tr>
				<th style="white-space: nowrap">
					<bean:message key="carrier.title.notificationProperties" />
				</th>
				<td>
					<html:textarea property="notificationProperties" style="height: 200px; width: 640px;"/>
					<div class="validateErrorMsg">
						<html:errors property="notificationProperties" />
					</div>
				</td>
			</tr>
			<tr>
				<th style="white-space: nowrap">
					* <bean:message key="carrier.title.serverAuthType" />
				</th>
				<td>
			        <html:select property="serverAuthType">
			          <html:option value=""><bean:message key="page.select.option.default.label"/></html:option>
			          <html:optionsCollection name="authSchemas"/>
			        </html:select>
					<div class="validateErrorMsg">
						<html:errors property="serverAuthType" />
					</div>
				</td>
			</TR>
            <tr>
              <th style="white-space: nowrap">
                * <bean:message key="carrier.title.defaultBootstrapPinType" />
              </th>
              <td>
                <html:select property="defaultBootstrapPinType">
                  <html:option value="0"><bean:message key="meta.bootstrap.pintype.NETWPIN.label"/></html:option>
                  <html:option value="1"><bean:message key="meta.bootstrap.pintype.USERPIN.label"/></html:option>
                  <html:option value="2"><bean:message key="meta.bootstrap.pintype.USERNETWPIN.label"/></html:option>
                  <html:option value="3"><bean:message key="meta.bootstrap.pintype.USERPINMAC.label"/></html:option>
                </html:select>
                <div class="validateErrorMsg">
                  <html:errors property="defaultBootstrapPinType" />
                </div>
              </td>
            </tr>
            <tr>
              <th style="white-space: nowrap">
                * <bean:message key="carrier.title.defaultBootstrapUserPin" />
              </th>
              <td>
                <html:text property="defaultBootstrapUserPin" />
                <div class="validateErrorMsg">
                  <html:errors property="defaultBootstrapUserPin" />
                </div>
              </td>
            </tr>
            <tr>
              <th style="white-space: nowrap">
                * <bean:message key="carrier.title.bootstrapMaxRetries" />
              </th>
              <td>
                <html:text property="bootstrapMaxRetries" />
                <div class="validateErrorMsg">
                  <html:errors property="bootstrapMaxRetries" />
                </div>
              </td>
            </tr>
            <tr>
              <th style="white-space: nowrap">
                * <bean:message key="carrier.title.bootstrapTimeout" />
              </th>
              <td>
                <html:text property="bootstrapTimeout" /> <bean:message key="time.seconds.label"/>
                <div class="validateErrorMsg">
                  <html:errors property="bootstrapTimeout" />
                </div>
              </td>
            </tr>
			<TR>
				<th style="white-space: nowrap">
					* <bean:message key="carrier.title.notificationType"/>
				</th>
				<td>
			        <html:select property="notificationType">
			          <html:option value="wapPush">WapPush</html:option>
			        </html:select>
					<div class="validateErrorMsg">
						<html:errors property="notificationType" />
					</div>
				</td>
			</tr>
            <tr>
              <th style="white-space: nowrap">
                * <bean:message key="carrier.title.notificationMaxNumRetries" />
              </th>
              <td>
                <html:text property="notificationMaxNumRetries" />
                <div class="validateErrorMsg">
                  <html:errors property="notificationMaxNumRetries" />
                </div>
              </td>
            </tr>
			<tr>
				<th style="white-space: nowrap">
					* <bean:message key="carrier.title.notificationStateTimeout" />
				</th>
				<td>
					<html:text property="notificationStateTimeout" /> <bean:message key="time.seconds.label"/>
					<div class="validateErrorMsg">
						<html:errors property="notificationStateTimeout" />
					</div>
				</td>
			</tr>
      <tr>
        <th style="white-space: nowrap">
          * <bean:message key="carrier.title.defaultJobTimeout" />
        </th>
        <td>
          <html:text property="defaultJobExpiredTimeInSeconds" /> <bean:message key="time.seconds.label"/>
          <div class="validateErrorMsg">
            <html:errors property="defaultJobExpiredTimeInSeconds" />
          </div>
        </td>
      </tr>
		</tbody>
	</table>
	<div class="buttonArea">
		<logic:empty name="CarrierForm" property="ID">
			<html:submit styleClass="NormalWidthButton">
				<bean:message key="page.button.create.label" />
			</html:submit>
		</logic:empty>
		<logic:notEmpty name="CarrierForm" property="ID">
			<html:submit styleClass="NormalWidthButton">
				<bean:message key="page.button.update.label" />
			</html:submit>
		</logic:notEmpty>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<html:reset styleClass="NormalWidthButton">
			<bean:message key="page.button.reset.label" />
		</html:reset>
		<html:cancel styleClass="NormalWidthButton">
			<bean:message key="page.button.cancel.label" />
		</html:cancel>
	  <logic:notEmpty name="CarrierForm" property="ID">
		    <html:button property="action" onclick="document.forms['CarrierForm'][1].submit();" styleClass="NormalWidthButton">
		      <bean:message key="page.button.view.label"/>
		    </html:button>
	  </logic:notEmpty>
	</div>
</html:form>

<html:form action="/ViewCarrier" method="post">
  <logic:notEmpty name="CarrierForm" property="ID">
    <html:hidden property="ID"/>
  </logic:notEmpty>
</html:form>
