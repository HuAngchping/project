
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<table class="model_matrix">
  <thead>
  <tr>
    <th rowspan="2">#</th>
    <th rowspan="2"><bean:message key="model.title.manufacturer" /></th>
    <th rowspan="2"><bean:message key="device.title.model" /></th>
    <th colspan="${fn:length(categories)}">OMA CP</th>
    <th colspan="${fn:length(categories)}">OMA DM</th>
    <th colspan="${fn:length(categories)}">Nokia OTA</th>
  </tr>
  <tr>
    <!-- CP -->
    <logic:iterate id="category" name="categories">
      <th class="flag"><bean:write name="category" property="name"/></th>
    </logic:iterate>
    <!-- DM -->
    <logic:iterate id="category" name="categories">
      <th class="flag"><bean:write name="category" property="name"/></th>
    </logic:iterate>
    <!-- Nokia OTA -->
    <logic:iterate id="category" name="categories">
      <th class="flag"><bean:write name="category" property="name"/></th>
    </logic:iterate>
  </tr>
  </thead>
<logic:iterate id="item" name="model_matrix_items" indexId="index" offset="1">
  <tr>
    <td class="index"><bean:write name="index"/></td>
    <td><bean:write name="item" property="model.manufacturer.name"/></td>
    <td nowrap="nowrap"><bean:write name="item" property="model.name"/></td>
    <logic:iterate id="category" name="categories">
      <td class="flag4cp" title="OMA CP <bean:write name="category" property="name"/>">
      <c:set var="flag" value="${item.omaCPCapabilities[category.name]}" />
      <c:if test="${flag}">Y</c:if>
      </td>
    </logic:iterate>
    <logic:iterate id="category" name="categories">
      <td class="flag4dm" title="OMA DM <bean:write name="category" property="name"/>">
      <c:set var="flag" value="${item.omaDMCapabilities[category.name]}" />
      <c:if test="${flag}">Y</c:if>
      </td>
    </logic:iterate>
    <logic:iterate id="category" name="categories">
      <td class="flag4nokia_ota" title="Nokia OTA <bean:write name="category" property="name"/>">
      <c:set var="flag" value="${item.nokiaOtaCapabilities[category.name]}" />
      <c:if test="${flag}">Y</c:if>
      </td>
    </logic:iterate>
  </tr>
</logic:iterate>
</table>
