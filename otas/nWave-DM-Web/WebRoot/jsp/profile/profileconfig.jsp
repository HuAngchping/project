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
function doSubmit(form) {
  if (bCancel) {
     return true;
  }
  //alert(form);
  // Checking NAP Profile
  {
	  element = form.elements["value(napProfileID)"];
	  if (element) {
	     value = element.options[element.selectedIndex].value
	     if (value == "") {
	        // NAP Profile is required
	        alert("<bean:message key="page.profileconfigs.javascript.validate.napProfileID" />");
	        element.focus();
	        return false;
	     }
	     
	  }
  }
  
  // Checking Proxy Profile
  {
	  element = form.elements["value(proxyProfileID)"];
	  if (element) {
	     value = element.options[element.selectedIndex].value
	     if (value == "") {
	        // Proxy Profile is required
	        alert("<bean:message key="page.profileconfigs.javascript.validate.proxyProfileID" />");
	        element.focus();
	        return false;
	     }
	     
	  }
  }
  
  // Checking attribute
  total = form.elements.length;
  for (i = 0; i < total; i++) {
      e = form.elements[i];
      //alert(e.name);
      if (e.name.match("value\\(attribute__value[0-9]{0,}")) {
         //alert("matched:" + e.name);
         fullName = e.name;
         attributeID = fullName.substring("value(attribute__".length, fullName.indexOf("__value)"));
         //alert("attribute ID:" + attributeID);
         
         attributeName = form.elements["value(attribute__" + attributeID + "__name)"].value;
         isRequired = form.elements["value(attribute__" + attributeID + "__required)"].value;
         //alert("Is required:" + isRequired);
         
         if (isRequired != "true") {
            continue;
         }
         
         //alert("Is required:" + e.name);
         type = form.elements["value(attribute__" + attributeID + "__value)"].type;
         //alert("Type: " + type);
         value = "";
         if (type == "text" || type == "textarea") {
            value = form.elements["value(attribute__" + attributeID + "__value)"].value;
         } else if (type == "file") {
            value = form.elements["value(attribute__" + attributeID + "__value)"].value;
            emptyItCheckbox = form.elements["value(attribute__" + attributeID + "__oldValueFlag)"];
            if (emptyItCheckbox) {
               value = emptyItCheckbox.value;
            }
         } else if (type == "select-one") {
           element = form.elements["value(attribute__" + attributeID + "__value)"];
           value = element.options[element.selectedIndex].value
         }
         if (value == "") {
            // is required
            alert(attributeName + " <bean:message key="page.profileconfigs.javascript.validate.attribute" />");
            form.elements["value(attribute__" + attributeID + "__value)"].focus();
            return false;
         }
      }
  }
  return true;
}
//-->
</script>

<div class="messageArea">
  <bean:message key="page.profileConfig.message" /><br><br>
  <bean:message key="page.message.required"/><bean:message key="page.message.input"/>
  <div class="validateErrorMsg"><html:errors header="common.errors.header" footer="common.errors.footer" prefix="common.errors.prefix" suffix="common.errors.suffix"/></div>
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
      <th>* <bean:message key="profile.config.name.label"/></th>
      <td><html:text property="value(name)" size="48"/><div class="validateErrorMsg"><html:errors property="name"/></div></td>
    </tr>
    <tr>
      <th>* <bean:message key="profile.config.externalID.label"/></th>
      <td><html:text property="value(externalID)" size="48"/><div class="validateErrorMsg"><html:errors property="externalID"/></div></td>
    </tr>
    <tr>
      <th>* <bean:message key="profile.config.template.label"/></th>
      <td>
      <logic:notEmpty name="ProfileConfigForm" property="value(ID)">
        <html:hidden property="value(templateID)"/>
        <bean:write name="profileTemplate" property="name"/>
      </logic:notEmpty>
      <logic:empty name="ProfileConfigForm" property="value(ID)">
        <html:select property="value(templateID)">
          <html:option value=""><bean:message key="page.select.option.default.label"/></html:option>
          <html:optionsCollection name="profileTemplates" label="name" value="ID"/>
        </html:select>
        <div class="validateErrorMsg"><html:errors property="templateID"/></div>
      </logic:empty>
      </td>
    </tr>
    <tr>
      <th><bean:message key="profile.config.carrier.label"/></th>
      <td>
        <html:select property="value(carrierID)">
          <html:option value=""><bean:message key="page.select.option.default.label"/></html:option>
          <html:optionsCollection name="carriers" label="name" value="ID"/>
        </html:select>
        <div class="validateErrorMsg"><html:errors property="carrierID"/></div>
      </td>
    </tr>
    <c:if test="${profileTemplate.needsNap == true}">
    <tr>
      <th>* <bean:message key="profile.config.NAPProfileConfigID.label"/></th>
      <td>
        <html:select property="value(napProfileID)">
          <html:option value=""><bean:message key="page.select.option.default.label"/></html:option>
          <html:optionsCollection name="napProfiles" label="name" value="ID"/>
        </html:select>
        <div class="validateErrorMsg"><html:errors property="napProfileID"/></div>
      </td>
    </tr>
    </c:if>    
    <c:if test="${profileTemplate.needsProxy == true}">
    <tr>
      <th>* <bean:message key="profile.config.proxyProfileConfigID.label"/></th>
      <td>
        <html:select property="value(proxyProfileID)">
          <html:option value=""><bean:message key="page.select.option.default.label"/></html:option>
          <html:optionsCollection name="proxyProfiles" label="name" value="ID"/>
        </html:select>
        <div class="validateErrorMsg"><html:errors property="proxyProfileID"/></div>
      </td>
    </tr>
    </c:if>    
    <tr>
      <th><bean:message key="profile.config.isUserProfile.label"/></th>
      <td><html:checkbox property="value(isUserProfile)"/><div class="validateErrorMsg"><html:errors property="isUserProfile"/></div></td>
    </tr>
    <tr>
      <th><bean:message key="profile.config.description.label"/></th>
      <td><html:textarea property="value(description)"/><div class="validateErrorMsg"><html:errors property="description"/></div></td>
    </tr>
  </tbody>
</table>

<!--  Attributes management  -->
<logic:notEmpty name="ProfileConfigForm" property="value(ID)">
<logic:notEmpty name="attributes">
	<div class="messageArea">
	  <bean:message key="page.profileConfig.message.attributes"/><br/><br/>
      <bean:message key="page.message.required"/><bean:message key="page.message.input"/><br/>
	</div>
	<div class="validateErrorMsg">
      <html:errors property="attribute.validate.message"/>
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
		</display:column>
    <%-- Value --%>
		<display:column titleKey="page.profileConfig.attribute.value.label" style="text-align: left;white-space: nowrap;">
 		  <input type="hidden" name="value(attribute__<c:out value="${attribute.ID}"/>__name)" value="<c:out value="${attribute.name}"/>"/>
 		  <input type="hidden" name="value(attribute__<c:out value="${attribute.ID}"/>__required)" value="<c:out value="${!attribute.isUserAttribute && attribute.isRequired}"/>"/>
 		  <input type="hidden" name="value(attribute__<c:out value="${attribute.ID}"/>__typeName)" value="<c:out value="${attribute.profileAttribType.name}"/>"/>
 		  
 		  <!-- Input -->
		  <c:if test="${empty attribute.profileAttribType.attribTypeValues}">
		  <c:if test="${attribute.profileAttribType.name != 'Binary'}">
 		    <input type="text" name="value(attribute__<c:out value="${attribute.ID}"/>__value)" value="<c:out value="${originalValue}"/>" size="36"/>
		  </c:if>
		  </c:if>
		  
 		  <!-- Binary -->
		  <c:if test="${attribute.profileAttribType.name eq 'Binary'}">
 		    <input type="file" name="value(attribute__<c:out value="${attribute.ID}"/>__value)" size="36"/>
	      <c:if test="${originalValue > 0}">
  		    <input type="hidden" name="value(attribute__<c:out value="${attribute.ID}"/>__oldValueFlag)" value="exists"/>
          <br>
		      <c:if test="${!attribute.isUserAttribute && !attribute.isRequired || attribute.isUserAttribute}">
	          <input type="checkbox" name="value(attribute__<c:out value="${attribute.ID}"/>__emptyIt)" value="true"/> <bean:message key="page.profileConfig.checkbox.emptyIt.label"/>&nbsp;&nbsp;&nbsp;
          </c:if>
          
		      [<bean:message key="meta.attribute.type.Binary.label"/>] <bean:message key="binary.size.label"/>: <fmt:formatNumber pattern="#,###" value="${originalValue}"></fmt:formatNumber><bean:message key="size.bytes.label"/>
					<html:link action="/profile/downloadAttributeValue?method=getBinary&profileID=${profileConfig.ID}" target="_blank" paramId="attributeName" paramName="attribute" paramProperty="name">
					[<bean:message key="page.button.download.label" />]<html:img page="/common/images/icons/icon_down.gif"/>
					</html:link>
 		    </c:if>
		  </c:if>
		  
 		  <!-- Select List -->
		  <c:if test="${not empty attribute.profileAttribType.attribTypeValues}">
		    <%-- Attribute Type: Multiple values --%>
		    <select name="value(attribute__<c:out value="${attribute.ID}"/>__value)">
              <option value=""><bean:message key="page.select.option.default.label"/></option>
				      <c:forEach items="${attribute.profileAttribType.attribTypeValues}" var="attribTypeValue">
				          <c:if test="${attribTypeValue.value eq originalValue}">
			  		        <option value="<c:out value="${attribTypeValue.value}"/>" selected="selected">
			  		          <c:out value="${attribTypeValue.value}"/>
			  		        </option>
				          </c:if>
				          <c:if test="${attribTypeValue.value ne originalValue}">
			  		        <option value="<c:out value="${attribTypeValue.value}"/>">
			  		          <c:out value="${attribTypeValue.value}"/>
			  		        </option>
				          </c:if>
				      </c:forEach>
		    </select>
		  </c:if>
		  
		  <!-- Help: Hints -->
		  <c:if test="${not empty attribute.description}">
        <html:img page="/common/images/icons/icon_help.gif" alt="${attribute.description}"></html:img>
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
  <logic:empty name="ProfileConfigForm" property="value(ID)">
    <html:submit property="action" styleClass="NormalWidthButton"><bean:message key="page.button.create.label"/></html:submit>
  </logic:empty>
  <logic:notEmpty name="ProfileConfigForm" property="value(ID)">
    <html:submit property="action" styleClass="NormalWidthButton"><bean:message key="page.button.update.label"/></html:submit>
  </logic:notEmpty>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <html:reset styleClass="NormalWidthButton"><bean:message key="page.button.reset.label"/></html:reset>
  <html:cancel styleClass="NormalWidthButton"><bean:message key="page.button.cancel.label"/></html:cancel>
  <logic:notEmpty name="ProfileConfigForm" property="value(ID)">
	    <html:button property="action" onclick="document.forms['ProfileConfigForm'][1].submit();"><bean:message key="page.profileConfig.button.backToView.label"/></html:button>
  </logic:notEmpty>
</div>
</html:form>

<html:form action="/ViewProfileConfig" method="post">
  <logic:notEmpty name="ProfileConfigForm" property="value(ID)">
    <html:hidden property="value(ID)"/>
  </logic:notEmpty>
</html:form>
