<%@ page import="com.npower.dm.core.ProfileAssignment" %>
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
  // Checking attribute
  total = form.elements.length;
  for (i = 0; i < total; i++) {
      e = form.elements[i];
      //alert(e.name);
      if (e.name.match("value\\(attribute.*__value[0-9]{0,}")) {
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
         if (type == "text" || type == "textarea" || type == "hidden") {
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
  <bean:message key="page.message.required"/><bean:message key="page.message.input"/>
</div>

<html:form action="/device/job/dm/profile_assignment/submit" onsubmit="return doSubmit(this);" enctype='multipart/form-data'>
<html:hidden property="value(ID)"/>
<html:hidden property="value(profileID)"/>
<html:hidden property="value(deviceID)"/>

<input type="hidden" name="deviceID" value="<bean:write name="device" property="ID"/>"/>
<table class="entityview">
  <thead>
    <tr>
      <th colspan="2"><bean:message key="page.device.job.assignment.title.form.label"/></th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th width="150"><bean:message key="page.device.job.assignment.label.deviceID"/></th>
      <td><bean:write name="device" property="externalId"/></td>
    </tr>
    <tr>
      <th><bean:message key="device.title.phonenumber"/></th>
      <td><bean:write name="device" property="subscriberPhoneNumber"/></td>
    </tr>
    <tr>
      <th><bean:message key="device.title.manufacturer"/></th>
      <td><bean:write name="device" property="model.manufacturer.name" /></td>
    </tr>
    <tr>
      <th><bean:message key="device.title.model"/></th>
      <td><bean:write name="device" property="model.name" /></td>
    </tr>
    <tr>
      <th>
        <bean:message key="device.job.jobType" />
      </th>
      <td>
        <bean:write name="JOB_TYPE_FOR_ASSIGN_PROFILE" property="name" /> [ <bean:write name="JOB_TYPE_FOR_ASSIGN_PROFILE" property="mode" /> ]
      </td>
    </tr>
    <tr>
      <th><bean:message key="page.device.job.assignment.label.profile"/></th>
      <td><bean:write name="profile" property="name"/></td>
    </tr>
  </tbody>
</table>

<logic:notEmpty name="attributes">
<!--  Attributes management  -->
	<div class="messageArea">
	  <bean:message key="page.profileConfig.message.attributes"/>
	</div>

	<display:table name="attributes" id="attribute" class="simple">
    <c:set var="originalValue" value="${attribute.defaultValue}" />
    <c:set var="attributeName" value="${attribute.name}" />
    <c:if test="${not empty profile}">
      <%
        // Caculate value of attribute__
        String attributeName = ((ProfileAttribute)attribute).getName();
        ProfileAssignment currentAssingment = (ProfileAssignment)request.getAttribute("profileAssignment");
        ProfileConfig currentProfile = (ProfileConfig)request.getAttribute("profile");
        ProfileAttribute pAttribute = (ProfileAttribute)attribute;
        if (currentAssingment != null && currentAssingment.getProfileAttributeValue((String)attributeName) != null) {
           // Load data from assignment
	         if (pAttribute.getProfileAttribType().getName() != null
	            && ProfileAttributeType.TYPE_BINARY.equalsIgnoreCase(pAttribute.getProfileAttribType().getName())) {
	            // In Binary Mode
	            long value = 0;
	            if (currentAssingment.getProfileAttributeValue((String)attributeName) != null
	                && currentAssingment.getProfileAttributeValue((String)attributeName).getBinaryData() != null) {
	               value = currentAssingment.getProfileAttributeValue((String)attributeName).getBinaryData().length();
			        }
	            pageContext.setAttribute("originalValue", "" + value);
           } else {           
	           String value = currentAssingment.getProfileAttributeValue((String)attributeName).getStringValue();
	           if (value != null && value.trim().length() > 0) {
	              pageContext.setAttribute("originalValue", value);
	           }
	         }
        } else if ( currentProfile.getProfileAttributeValue((String)attributeName) != null ) {
          // Load data from profile
	         if (pAttribute.getProfileAttribType().getName() != null
	            && ProfileAttributeType.TYPE_BINARY.equalsIgnoreCase(pAttribute.getProfileAttribType().getName())) {
	            // In Binary Mode
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
    <display:column class="rowNum">
      <c:out value="${attribute_rowNum}"/>
    </display:column>
		<display:column titleKey="page.profileConfig.attribute.name.lable" style="text-align: right;white-space: nowrap;">
		  <c:if test="${attribute.isUserAttribute && attribute.isRequired}">*</c:if>
		  <c:out value="${attribute.displayName}"/>&nbsp;&nbsp;&nbsp;
		</display:column>
		<display:column titleKey="page.profileConfig.attribute.value.label"  style="text-align: left;white-space: nowrap;">
 		  <input type="hidden" name="value(attribute__<c:out value="${attribute.ID}"/>__name)" value="<c:out value="${attribute.name}"/>"/>
 		  <input type="hidden" name="value(attribute__<c:out value="${attribute.ID}"/>__required)" value="<c:out value="${attribute.isRequired}"/>"/>
 		  <input type="hidden" name="value(attribute__<c:out value="${attribute.ID}"/>__typeName)" value="<c:out value="${attribute.profileAttribType.name}"/>"/>

		  <c:if test="${not attribute.isUserAttribute}">
			  <%-- View Mode       --%>
			  <c:if test="${attribute.profileAttribType.name != 'Binary'}">
	 	      <input type="hidden" name="value(attribute__<c:out value="${attribute.ID}"/>__value)" value="<c:out value="${originalValue}"/>" size="36"/>
			    <c:out value="${originalValue}"/>
			  </c:if>
			  <c:if test="${attribute.profileAttribType.name eq 'Binary'}">
			      [<bean:message key="meta.attribute.type.Binary.label"/>] <bean:message key="binary.size.label"/>: <fmt:formatNumber pattern="#,###" value="${originalValue}"></fmt:formatNumber><bean:message key="size.bytes.label"/>
						<html:link action="/profile/downloadAttributeValue?method=getBinary&profileID=${profile.ID}" target="_blank" paramId="attributeName" paramName="attribute" paramProperty="name">
						[<bean:message key="page.button.download.label" />]<html:img page="/common/images/icons/icon_down.gif"/>
						</html:link>
			  </c:if>
		  </c:if>
		  
		  <c:if test="${attribute.isUserAttribute}">
			  <%-- Edit Mode       --%>
			  <%-- Input       --%>
			  <c:if test="${empty attribute.profileAttribType.attribTypeValues}">
  		      <c:if test="${attribute.profileAttribType.name != 'Binary'}">
	 		      <input type="text" name="value(attribute__<c:out value="${attribute.ID}"/>__value)" value="<c:out value="${originalValue}"/>" size="36"/>
			    </c:if>
			  </c:if>
			  
			  <%-- Select box       --%>
			  <c:if test="${not empty attribute.profileAttribType.attribTypeValues}">
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
			  
	 		  <!-- Binary -->
			  <c:if test="${attribute.profileAttribType.name eq 'Binary'}">
	 		    <input type="file" name="value(attribute__<c:out value="${attribute.ID}"/>__value)" size="36"/>
		      <c:if test="${originalValue > 0}">
	  		    <input type="hidden" name="value(attribute__<c:out value="${attribute.ID}"/>__oldValueFlag)" value="exists"/>
            <br>
			      <c:if test="${attribute.isUserAttribute && !attribute.isRequired}">
		          <input type="checkbox" name="value(attribute__<c:out value="${attribute.ID}"/>__emptyIt)" value="true"/> <bean:message key="page.profileConfig.checkbox.emptyIt.label"/>&nbsp;&nbsp;&nbsp;
	          </c:if>
			      [<bean:message key="meta.attribute.type.Binary.label"/>] <bean:message key="binary.size.label"/>: <fmt:formatNumber pattern="#,###" value="${originalValue}"></fmt:formatNumber><bean:message key="size.bytes.label"/>
						<html:link action="/profile/downloadAttributeValue?method=getBinary&profileID=${profile.ID}" target="_blank" paramId="attributeName" paramName="attribute" paramProperty="name">
						[<bean:message key="page.button.download.label" />]<html:img page="/common/images/icons/icon_down.gif"/>
						</html:link>
	 		    </c:if>
			  </c:if>
		  
			  <!-- Help: Hints -->
			  <c:if test="${not empty attribute.description}">
	        <html:img page="/common/images/icons/icon_help.gif" alt="${attribute.description}"></html:img>
			  </c:if>
		  </c:if>
		</display:column>

	</display:table>
</logic:notEmpty>

<div class="buttonArea">
  <logic:empty name="DeviceJobProfileAssignmentForm" property="value(ID)">
    <html:submit property="action" styleClass="NormalWidthButton"><bean:message key="page.button.create.label"/></html:submit>
  </logic:empty>
  <logic:notEmpty name="DeviceJobProfileAssignmentForm" property="value(ID)">
    <html:submit property="action" styleClass="NormalWidthButton"><bean:message key="page.button.update.label"/></html:submit>
  </logic:notEmpty>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <html:reset styleClass="NormalWidthButton"><bean:message key="page.button.reset.label"/></html:reset>
  <logic:notEmpty name="DeviceJobProfileAssignmentForm" property="value(ID)">
    <html:button property="action" onclick="document.forms['DeviceSearchAssignmentsForm'].submit();"><bean:message key="page.device.button.viewProfiles"/></html:button>
  </logic:notEmpty>
  <html:cancel><bean:message key="page.device.button.backToDeviceView.label"/></html:cancel>
</div>
</html:form>

<html:form action="/device/ProfileAssignments" method="post">
  <INPUT type="hidden" name="deviceID" value="<bean:write name="device" property="ID"/>">
</html:form>
