<%@ page language="java"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<display:table name="targetDevices" id="targetDevice" class="simple_inline">
  <display:column class="rowNum"> 
    <c:out value="${targetDevice_rowNum}"/>
  </display:column>
  <display:column property="externalId" titleKey="device.title.externalId"/>
  <display:column property="subscriber.phoneNumber" titleKey="device.title.phonenumber"/>
  <display:column property="model.name" titleKey="device.title.model"/>
  <display:column property="model.manufacturer.name" titleKey="device.title.manufacturer"/>
</display:table>

