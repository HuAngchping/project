<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

    <html:form action="/uploadFile" enctype="multipart/form-data">
      <table>
        <tr>
          <th>
            <bean:message key="page.software.upload.label"/>
          </th>
        </tr>
        <tr></tr>
        <tr>
          <td>            
              <html:file property="binaryFile"/>
              <div class="validateErrorMsg">
                <html:errors property="binaryFile" />
              </div>                     
          </td>
        </tr>
        <tr></tr>
        <tr>
          <td>
              <html:submit> <bean:message key="page.software.upload.next.button.label"/> </html:submit>  
          </td>
        </tr>
      </table>   
    </html:form>
