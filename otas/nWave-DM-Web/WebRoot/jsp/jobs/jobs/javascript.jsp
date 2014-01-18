<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<script type="text/javascript">
function doDisableSelected() {
  checkBoxes = document.getElementsByName("jobIDs");   
  size = document.getElementsByName("jobIDs").length;
  checked = false;
  for (i = 0; i < size; i++) {
      if (checkBoxes[i].checked) {
         checked = true;
         break;
      }
  }
  if (!checked) {
     alert("<bean:message key="message.jobs.message.selectJobs" />");
     return false;
  }

  if (!confirm("<bean:message key="message.jobs.areYoutSureDisable"/>")) {
     return false;
  }
  document.forms["JobsOperationForm"].elements["action"].value = "disableJobs";
  document.forms["JobsOperationForm"].submit();
  return true;
}

function doRemoveSelected() {
  checkBoxes = document.getElementsByName("jobIDs");   
  size = document.getElementsByName("jobIDs").length;
  checked = false;
  for (i = 0; i < size; i++) {
      if (checkBoxes[i].checked) {
         checked = true;
         break;
      }
  }
  if (!checked) {
     alert("<bean:message key="message.jobs.message.selectJobs" />");
     return false;
  }

  if (!confirm("<bean:message key="message.delete.areYourSureDelete"/>")) {
     return false;
  }
  document.forms["JobsOperationForm"].elements["action"].value = "removeJobs";
  document.forms["JobsOperationForm"].submit();
  return true;
}

function doRemoveAll() {
  if (!confirm("<bean:message key="message.delete.areYourSureDeleteAll"/>")) {
     return false;
  }
  document.forms["JobsOperationForm"].elements["action"].value = "removeQueuedJobs";
  document.forms["JobsOperationForm"].submit();
  return true;
}

function doDisableeAll() {
  if (!confirm("<bean:message key="message.disable.areYourSureDisableAll"/>")) {
     return false;
  }
  document.forms["JobsOperationForm"].elements["action"].value = "disableQueuedJobs";
  document.forms["JobsOperationForm"].submit();
  return true;
}
</script>