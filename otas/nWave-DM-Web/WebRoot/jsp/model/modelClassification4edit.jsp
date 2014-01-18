<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn"%>

<fmt:bundle basename="com.npower.dm.struts.ApplicationResources">
</fmt:bundle>

<html:form action="/model/modelClassification4Save" method="post">
	<logic:present name="modelClassification">
	  <html:hidden property="id" value="${modelClassification.id}"/>
	</logic:present>
  <table class="entityview">
    <tbody>
		  <tr>
		    <td class=table_box_heading width="100%" colSpan=5><font color="red"><b>.:</b></font> <bean:message key="page.model.classification.save.label"/></td>
		  </tr>
      <tr>
        <th>
          * <bean:message key="entity.model.classification.save.externalID.label"/>
        </th>
        <td>
          <html:text property="externalID" size="48"/>
          <div class="validateErrorMsg">
            <html:errors property="externalID"/>
          </div>
        </td>
      </tr>
      <tr>
        <th>
          * <bean:message key="entity.model.classification.save.name.label"/>
        </th>
        <td>
          <html:text property="name" size="48"/>
          <div class="validateErrorMsg">
            <html:errors property="name"/>
          </div>
        </td>
      </tr>
      <tr>
        <th>
          <bean:message key="entity.model.classification.save.description.label"/>
        </th>
        <td>
          <html:textarea property="description" style="width: 100%;"/>
        </td>
      </tr>
    </tbody>
  </table>
  <div class="buttonArea">
	  <logic:notPresent name="modelClassification">
		  <html:submit>
		    <bean:message key="page.model.classification.save.button.label"/>
		  </html:submit>
		</logic:notPresent>
		<logic:present name="modelClassification">
		  <html:submit>
		    <bean:message key="page.model.classification.save.button2.label"/>
		  </html:submit>
		</logic:present>
  </div>
</html:form>