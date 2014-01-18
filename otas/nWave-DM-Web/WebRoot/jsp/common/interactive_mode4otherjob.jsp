<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<table width="400">
  <tr>
    <td colspan="2">
      <html:checkbox property="startjob" value="true">
         <bean:message key="page.job.interactive.mode.prompt.begin"/>
      </html:checkbox>
    </td>
  </tr>
  <tr>
    <td>
      <bean:message key="page.job.interactive.mode.prompt.type"/>
    </td>
    <td>
      <html:select property="startprompttype">
        <html:option value="0"> <bean:message key="page.job.interactive.mode.prompt.type.display"/> </html:option>
        <html:option value="1"> <bean:message key="page.job.interactive.mode.prompt.type.confirm"/> </html:option>
      </html:select>
    </td>
  </tr>
  <tr>
    <td>
      <bean:message key="page.job.interactive.mode.prompt.text"/>
    </td>
    <td>
      <html:textarea property="startprompttext"></html:textarea>
    </td>
  </tr>
  <tr> <td>  </td> </tr>
  <tr>
    <td colspan="2">
      <html:checkbox property="endjob" value="true">
         <bean:message key="page.job.interactive.mode.prompt.end"/>
      </html:checkbox>
    </td>
  </tr>
  <tr>
    <td>
      <bean:message key="page.job.interactive.mode.prompt.type"/>
    </td>
    <td>
      <html:select property="endprompttype">
        <html:option value="0"> <bean:message key="page.job.interactive.mode.prompt.type.display"/> </html:option>
        <html:option value="1"> <bean:message key="page.job.interactive.mode.prompt.type.confirm"/> </html:option>
      </html:select>
    </td>
  </tr>
  <tr>
    <td>
      <bean:message key="page.job.interactive.mode.prompt.text"/>
    </td>
    <td>
      <html:textarea property="endprompttext"></html:textarea>
    </td>
  </tr>  
</table>


