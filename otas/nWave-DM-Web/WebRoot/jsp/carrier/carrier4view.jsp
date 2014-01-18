<%@ page language="java"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"
  prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
  prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"
  prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/otas-dm.tld" prefix="dm"%>

<SCRIPT type="text/javascript">
</SCRIPT>

<html:form action="/SaveCarrier">
  <input type="hidden" name="action" value="update" />
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
          *
          <bean:message key="carrier.title.name" />
        </th>
        <td>
          <bean:write name="carrier" property="name" />
        </td>
      </tr>
      <tr>
        <th style="white-space: nowrap">
          *
          <bean:message key="carrier.title.externalID" />
        </th>
        <td>
          <bean:write name="carrier" property="externalID" />
        </td>
      </tr>
      <tr>
        <th style="white-space: nowrap">
          *
          <bean:message key="carrier.title.country" />
        </th>
        <td>
          <bean:write name="carrier" property="country.name" />
        </td>
      </tr>
      <tr>
        <th style="white-space: nowrap">
          <bean:message key="carrier.title.bootstrapNapProfile" />
        </th>
        <td>
          <logic:notEmpty name="carrier" property="bootstrapNapProfile">
            <bean:write name="carrier"
              property="bootstrapNapProfile.name" />
          </logic:notEmpty>
        </td>
      </tr>
      <tr>
        <th style="white-space: nowrap">
          <bean:message key="carrier.title.bootstrapDmProfile" />
        </th>
        <td>
          <logic:notEmpty name="carrier" property="bootstrapDmProfile">
            <bean:write name="carrier"
              property="bootstrapDmProfile.name" />
          </logic:notEmpty>
        </td>
      </tr>
      <tr>
        <th style="white-space: nowrap">
          <bean:message key="carrier.title.phoneNumberPolicy" />
        </th>
        <td>
          <bean:write name="carrier" property="phoneNumberPolicy" />
        </td>
      </tr>
      <tr>
        <th style="white-space: nowrap">
          <bean:message key="carrier.title.notificationProperties" />
        </th>
        <td>
          <bean:write name="carrier" property="notificationProperties" />
        </td>
      </tr>
      <tr>
        <th style="white-space: nowrap">
          <bean:message key="carrier.title.serverAuthType" />
        </th>
        <td>
          <bean:write name="carrier" property="serverAuthType" />
        </td>
      </TR>
      <tr>
        <th style="white-space: nowrap">
          <bean:message key="carrier.title.defaultBootstrapPinType" />
        </th>
        <td>
          <logic:equal name="carrier" property="defaultBootstrapPinType" value="0"><bean:message key="meta.bootstrap.pintype.NETWPIN.label"/></logic:equal>
          <logic:equal name="carrier" property="defaultBootstrapPinType" value="1"><bean:message key="meta.bootstrap.pintype.USERPIN.label"/></logic:equal>
          <logic:equal name="carrier" property="defaultBootstrapPinType" value="2"><bean:message key="meta.bootstrap.pintype.USERNETWPIN.label"/></logic:equal>
          <logic:equal name="carrier" property="defaultBootstrapPinType" value="3"><bean:message key="meta.bootstrap.pintype.USERPINMAC.label"/></logic:equal>
        </td>
      </tr>
      <tr>
        <th style="white-space: nowrap">
          <bean:message key="carrier.title.defaultBootstrapUserPin" />
        </th>
        <td>
          <bean:write name="carrier" property="defaultBootstrapUserPin" />
        </td>
      </tr>
      <tr>
        <th style="white-space: nowrap">
          <bean:message key="carrier.title.bootstrapMaxRetries" />
        </th>
        <td>
          <bean:write name="carrier" property="bootstrapMaxRetries" />
        </td>
      </tr>
      <tr>
        <th style="white-space: nowrap">
          <bean:message key="carrier.title.bootstrapTimeout" />
        </th>
        <td>
          <dm:time value="${carrier.bootstrapTimeout}" milliSecond="false"></dm:time>
        </td>
      </tr>
      <TR>
        <th style="white-space: nowrap">
          <bean:message key="carrier.title.notificationType" />
        </th>
        <td>
          <bean:write name="carrier" property="notificationType" />
        </td>
      </tr>
      <tr>
        <th style="white-space: nowrap">
          <bean:message key="carrier.title.notificationMaxNumRetries" />
        </th>
        <td>
          <bean:write name="carrier"
            property="notificationMaxNumRetries" />
        </td>
      </tr>
      <tr>
        <th style="white-space: nowrap">
          <bean:message key="carrier.title.notificationStateTimeout" />
        </th>
        <td>
          <dm:time value="${carrier.notificationStateTimeout}" milliSecond="false"></dm:time>
        </td>
      </tr>
      <tr>
        <th style="white-space: nowrap">
          <bean:message key="carrier.title.defaultJobTimeout" />
        </th>
        <td>
          <dm:time value="${carrier.defaultJobExpiredTimeInSeconds}" milliSecond="false"></dm:time>
        </td>
      </tr>
    </tbody>
  </table>
  <div class="buttonArea">
    <logic:notEmpty name="CarrierForm" property="ID">
      <html:button property="action"
        onclick="document.forms['CarrierForm'][1].submit();"
        styleClass="NormalWidthButton">
        <bean:message key="page.button.edit.label" />
      </html:button>
    </logic:notEmpty>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <html:cancel styleClass="NormalWidthButton">
      <bean:message key="page.button.cancel.label" />
    </html:cancel>
  </div>
</html:form>

<html:form action="/EditCarrier" method="post">
  <logic:notEmpty name="CarrierForm" property="ID">
    <input type="hidden" name="action" value="update" />
    <html:hidden property="ID" />
  </logic:notEmpty>
</html:form>
