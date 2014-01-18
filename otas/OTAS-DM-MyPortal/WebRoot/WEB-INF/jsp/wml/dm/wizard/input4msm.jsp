<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/wall.tld" prefix="wall" %>
<wall:block>
  <bean:message key="page.wml.dm.wizard.software.name.label"/>: <bean:write name="software" property="name"/> <bean:write name="software" property="version"/>
</wall:block>

<logic:notPresent name="model">
<wall:block>
  <bean:message key="page.wml.dm.wizard.software.page.input4msm.msg.cant_find_model_select_model"/>: 
  <logic:notPresent name="manufacturer">
  <%-- 厂商未知，提供厂商列表供选择 --%>
  <wall:br/>* <bean:message key="page.wml.dm.wizard.software.page.input4msm.select_brand.label"/>:<wall:br/>
  <logic:iterate id="m" name="manufacturers">
    <html:link action="/wap/wizard/dm/software/msm/input.do?value(manullySelectModel)=true&softwareID=${software.externalId}&brand=${m.externalId}&countryID=${country.ID}&carrierID=${carrier.externalID}">
      <bean:write name="m" property="name"/>
    </html:link> |
  </logic:iterate>
  </logic:notPresent>
  
  <logic:present name="manufacturer">
  <%-- 厂商已知，提供型号列表供选择 --%>
  <bean:message key="page.wml.dm.wizard.software.page.input4msm.brand.label"/>: <bean:write name="manufacturer" property="name"/>, <bean:message key="page.wml.dm.wizard.software.page.input4msm.select_model.label"/>: <wall:br/>
  <logic:iterate id="m" name="models">
    <html:link action="/wap/wizard/dm/software/msm/input.do?value(manullySelectModel)=true&softwareID=${software.externalId}&brand=${manufacturer.externalId}&value(modelID)=${m.ID}&countryID=${country.ID}&carrierID=${carrier.externalID}">
    <bean:write name="m" property="name"/> 
    </html:link> |
  </logic:iterate>
  [<html:link action="/wap/wizard/dm/software/msm/input.do?value(manullySelectModel)=true&softwareID=${software.externalId}&countryID=${country.ID}&carrierID=${carrier.externalID}"><bean:message key="page.wml.dm.wizard.software.page.input4msm.link.select_another_brand"/></html:link>]
  </logic:present>
  
</wall:block>
</logic:notPresent>

<logic:present name="model">
  <bean:define id="URL4DMSubmit"><html:rewrite action="/wap/wizard/dm/msm/install/job/submit4dm"/></bean:define>
  <wall:form action="${URL4DMSubmit}">
    <wall:input type="hidden" name="value(softwareID)" value="${software.externalId}"></wall:input>
    <wall:input type="hidden" name="value(modelID)" value="${model.ID}"></wall:input>
    <wall:input type="hidden" name="countryID" value="${country.ID}"></wall:input>
    <wall:input type="hidden" name="carrierID" value="${carrier.externalID}"></wall:input>
    <wall:block>
      <logic:equal name="isAutomaticDetected" value="true">
        <%-- 型号:自动监测 --%>
        <bean:message key="page.wml.dm.wizard.software.page.input4msm.msg.current_model_auto_detected"/>: 
        <wall:b><bean:write name="manufacturer" property="name"/> <bean:write name="model" property="name"/></wall:b>
        [<html:link action="/wap/wizard/dm/software/msm/input.do?value(manullySelectModel)=true&softwareID=${software.externalId}&countryID=${country.ID}&carrierID=${carrier.externalID}"><bean:message key="page.wml.dm.wizard.software.page.input4msm.link.select_another_brand"/></html:link>]
        [<html:link action="/wap/wizard/dm/software/msm/input.do?value(manullySelectModel)=true&softwareID=${software.externalId}&brand=${model.manufacturer.externalId}&countryID=${country.ID}&carrierID=${carrier.externalID}"><bean:message key="page.wml.dm.wizard.software.page.input4msm.link.select_another_model"/></html:link>]
      </logic:equal>
      <logic:equal name="isAutomaticDetected" value="false">
        <%-- 型号:用户手工设置 --%>
        <bean:message key="page.wml.dm.wizard.software.page.input4msm.msg.current_model_manually_selected"/>: 
        <wall:b><bean:write name="manufacturer" property="name"/> <bean:write name="model" property="name"/></wall:b>
        [<html:link action="/wap/wizard/dm/software/msm/input.do?value(manullySelectModel)=true&softwareID=${software.externalId}&brand=${model.manufacturer.externalId}&countryID=${country.ID}&carrierID=${carrier.externalID}"><bean:message key="page.wml.dm.wizard.software.page.input4msm.link.select_another_model"/></html:link>]
        [<html:link action="/wap/wizard/dm/software/msm/input.do?value(manullySelectModel)=true&softwareID=${software.externalId}&countryID=${country.ID}&carrierID=${carrier.externalID}"><bean:message key="page.wml.dm.wizard.software.page.input4msm.link.select_another_brand"/></html:link>]
      </logic:equal>
    </wall:block>
    <logic:equal name="supported_msm" value="true">
      <%-- MSM mode --%>
      <wall:block>
        * <bean:message key="page.wml.dm.wizard.software.page.input4msm.msg.input_phone_number"/>: <wall:br/><wall:input name="value(phoneNumber)" type="text" size="11" maxlength="11" format="NNNNNNNNNNN" value="${phoneNumber}"></wall:input>
        <wall:br/><wall:input type="submit" name="SubmitButton" value=" 确定 "></wall:input>
      </wall:block>
    </logic:equal>
    <logic:equal name="supported_msm" value="false">
      <%-- Download mode --%>
      <wall:block>
      * <bean:message key="page.wml.dm.wizard.software.page.input4msm.msg.not_support_msm"/>
      </wall:block>
      <logic:present name="downloadURL">
      <%-- Software package exists --%>
      <wall:block>
        * <html:link href="${downloadURL}"><bean:message key="page.wml.dm.wizard.software.page.input4msm.link.direct_download"/></html:link>
      </wall:block>
      </logic:present>
      <%-- Software package none exists --%>
      <logic:notPresent name="downloadURL">
       * <wall:font color="red"><wall:b><bean:message key="page.wml.dm.wizard.software.page.input4msm.no_software_package_found"/></wall:b></wall:font>
      </logic:notPresent>
    </logic:equal>
    
  </wall:form>
</logic:present>

