<%@ page import="com.npower.dm.core.ProfileConfig" %>
<%@ page import="com.npower.dm.core.ProfileAttribute" %>
<%@ page import="com.npower.dm.core.ProfileAttributeType" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<script type="text/javascript" language="javascript">
<!--
var bCancel = false;
//-->
</script>

<div class="messageArea">
  <bean:message key="page.profileConfig.message" /><br><br>
</div>

<html:form action="/SaveProfileConfig" onsubmit="return doSubmit(this);" enctype='multipart/form-data'>
<html:hidden property="value(ID)"/>
<table class="entityview">
  <thead>
    <tr>
      <th colspan="2"><bean:message key="page.profileConfig.title.form.label"/></th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th><bean:message key="profile.config.name.label"/></th>
      <td><bean:write name="profileConfig" property="name" /></td>
    </tr>
    <tr>
      <th><bean:message key="profile.config.externalID.label"/></th>
      <td><bean:write name="profileConfig" property="externalID" /></td>
    </tr>
    <tr>
      <th>* <bean:message key="profile.config.template.label"/></th>
      <td>
        <html:link action="/ViewProfileTemplate.do" styleClass="reference_link" target="_blank" paramId="ID" paramName="profileConfig" paramProperty="profileTemplate.ID">
          <bean:write name="profileConfig" property="profileTemplate.name" />
        </html:link>
      </td>
    </tr>
    <tr>
      <th><bean:message key="profile.config.carrier.label"/></th>
      <td>
      <logic:notEmpty name="profileConfig" property="carrier">
        <bean:write name="profileConfig" property="carrier.name" />
      </logic:notEmpty>
      </td>
    </tr>
    <c:if test="${profileTemplate.needsNap == true}">
    <tr>
      <th><bean:message key="profile.config.NAPProfileConfigID.label"/></th>
      <td>
        <logic:notEmpty name="profileConfig" property="NAPProfile">
          <bean:write name="profileConfig" property="NAPProfile.name" />
        </logic:notEmpty>
      </td>
    </tr>
    </c:if>    
    <c:if test="${profileTemplate.needsProxy == true}">
    <tr>
      <th>* <bean:message key="profile.config.proxyProfileConfigID.label"/></th>
      <td>
        <logic:notEmpty name="profileConfig" property="proxyProfile">
          <bean:write name="profileConfig" property="proxyProfile.name" />
        </logic:notEmpty>
      </td>
    </tr>
    </c:if>    
    <tr>
      <th><bean:message key="profile.config.isUserProfile.label"/></th>
      <td><html:img page="/common/images/icons/${profileConfig.isUserProfile}.gif" alt="${profileConfig.isUserProfile}"/></td>
    </tr>
    <tr>
      <th><bean:message key="profile.config.description.label"/></th>
      <td><bean:write name="profileConfig" property="description" /></td>
    </tr>
  </tbody>
</table>

<!--  Attributes management  -->
<logic:notEmpty name="ProfileConfigForm" property="value(ID)">
<logic:notEmpty name="attributes">
	<div class="messageArea">
	  <bean:message key="page.profileConfig.message.attributes"/><br/><br/>
	</div>

	<display:table name="attributes" id="attribute" class="simple">
    <c:set var="originalValue" value="${attribute.defaultValue}" />
    <c:set var="attributeName" value="${attribute.name}" />
    <c:if test="${not empty profileConfig}">
      <%
        String attributeName = ((ProfileAttribute)attribute).getName();
        ProfileConfig currentProfile = (ProfileConfig)request.getAttribute("profileConfig");
        if ( currentProfile.getProfileAttributeValue((String)attributeName) != null ) {
           ProfileAttribute pAttribute = (ProfileAttribute)attribute;
	         if (pAttribute.getProfileAttribType().getName() != null
	            && ProfileAttributeType.TYPE_BINARY.equalsIgnoreCase(pAttribute.getProfileAttribType().getName())) {
	            long value = 0;
	            if (currentProfile.getProfileAttributeValue((String)attributeName) != null
	                && currentProfile.getProfileAttributeValue((String)attributeName).getBinaryData() != null) {
	               value = currentProfile.getProfileAttributeValue((String)attributeName).getBinaryData().length();
			        }
	            pageContext.setAttribute("originalValue", "" + value);
	         } else {
			       String value = currentProfile.getProfileAttributeValue((String)attributeName).getStringValue();
			       if (value != null && value.trim().length() > 0) {
			          pageContext.setAttribute("originalValue", value);
			       }
		       }
        }
      %>
    </c:if>
    <%-- Row Number --%>
    <display:column class="rowNum">
      <c:out value="${attribute_rowNum}"/>
    </display:column>
    <%-- Attribute Name --%>
		<display:column titleKey="page.profileConfig.attribute.name.lable" style="text-align: right;white-space: nowrap;">
		  <c:if test="${!attribute.isUserAttribute && attribute.isRequired}">*</c:if>
		  <c:out value="${attribute.displayName}"/>
		  <!-- Help: Hints -->
		  <c:if test="${not empty attribute.description}">
            <html:img page="/common/images/icons/icon_help.gif" alt="${attribute.description}"></html:img>
		  </c:if>
          <c:if test="${empty attribute.name}">
            <html:img page="/common/images/icons/icon_help.gif" alt="${attribute.description}"></html:img>
          </c:if>
          &nbsp;&nbsp;
		</display:column>
    <%-- Value --%>
		<display:column titleKey="page.profileConfig.attribute.value.label" style="text-align: left;white-space: nowrap;">
		  <c:if test="${attribute.profileAttribType.name eq 'Binary'}">
		    [<bean:message key="meta.attribute.type.Binary.label"/>] 
		    <c:if test="${originalValue > 0}">
		      <bean:message key="binary.size.label"/>: <fmt:formatNumber pattern="#,###" value="${originalValue}"></fmt:formatNumber><bean:message key="size.bytes.label"/>
		      &nbsp;&nbsp;
					<html:link action="/profile/downloadAttributeValue?method=getBinary&profileID=${profileConfig.ID}" target="_blank" paramId="attributeName" paramName="attribute" paramProperty="name">
					[<bean:message key="page.button.download.label" />]<html:img page="/common/images/icons/icon_down.gif"/>
					</html:link>
		    </c:if>
      </c:if>
		  <c:if test="${attribute.profileAttribType.name != 'Binary'}">
 		    <c:out value="${originalValue}"/>
      </c:if>
		</display:column>
<%--
		<display:column titleKey="profile.attribute.attributeIndex.label">
		  <c:out value="${attribute.defaultValue}"></c:out>
		</display:column>
		<display:column titleKey="profile.attribute.attributeType.label" >
		  <c:out value="${attribute.profileAttribType.name}"></c:out>
		</display:column>
--%>
		<%--<display:column property="isRequired" titleKey="profile.attribute.isRequired.label" />--%>
		<display:column titleKey="profile.attribute.isUserAttribute.label">
		  <html:img page="/common/images/icons/${attribute.isUserAttribute}.gif" alt="${attribute.isUserAttribute}"></html:img>
		</display:column>
		<%--<display:column property="isMultivalued" titleKey="profile.attribute.isMultivalued.label" />--%>
		<%--<display:column property="attributeIndex" titleKey="profile.attribute.attributeIndex.label" />--%>
		<%--<display:column property="description" titleKey="profile.attribute.attributeIndex.label"/>--%>
	</display:table>
</logic:notEmpty>
</logic:notEmpty>

<div class="buttonArea">
  <logic:notEmpty name="ProfileConfigForm" property="value(ID)">
	    <html:button property="action" onclick="document.forms['ProfileConfigForm'][1].submit();"><bean:message key="page.profileConfig.button.backToEdit.label"/></html:button>
  </logic:notEmpty>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <html:cancel styleClass="NormalWidthButton"><bean:message key="page.button.cancel.label"/></html:cancel>
</div>
</html:form>

<html:form action="/EditProfileConfig" method="post">
  <logic:notEmpty name="ProfileConfigForm" property="value(ID)">
    <html:hidden property="value(ID)"/>
  </logic:notEmpty>
</html:form>
