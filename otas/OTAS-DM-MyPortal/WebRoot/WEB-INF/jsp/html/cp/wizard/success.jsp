<?xml version="1.0" encoding="UTF-8" ?>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"
	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"
	prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested"
	prefix="nested"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="WizardTitle">
<bean:message key="page.cp.wizard.title"/>
</div>
<hr class="WizardHR"/>
<div class="WizardForm">
  <p><bean:message key="page.cp.wizard.success.help.1"/></p>
  <p><bean:message key="page.cp.wizard.success.help.2"/></p>
  <p><bean:message key="page.cp.wizard.success.help.3" arg0="${pin}"/></p>
  <p><bean:message key="page.cp.wizard.success.help.4"/></p>
  <ul>
    <li><bean:message key="page.cp.wizard.success.help.5"/></li>
    <li><bean:message key="page.cp.wizard.success.help.6" arg0="${pin}"/></li>
    <li><bean:message key="page.cp.wizard.success.help.8"/></li>
    <li><bean:message key="page.cp.wizard.success.help.9"/></li>
  </ul>
  <p><bean:define id="RetryURL"><html:rewrite action="/wizard/cp/InputPhone"/></bean:define><bean:message key="page.cp.wizard.success.help.10" arg0="${RetryURL}"/></p>
</div>
<hr class="WizardHR"/>
<div class="WizardNavigation">
</div>
