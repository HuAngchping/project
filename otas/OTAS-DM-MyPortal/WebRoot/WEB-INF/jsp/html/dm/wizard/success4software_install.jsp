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

<style>
<!--
.help_screen_shot {
  margin-right: 10px; 
  padding: 5px; 
  background: #aaa; 
  float: left;
}
-->
</style>

<div class="WizardTitle">
<bean:message key="page.dm.msm.wizard.title"/>
</div>
<hr class="WizardHR"/>
<div class="WizardForm" style="padding-left: 10px;">
  <c:set var="softwareName" value="${software.vendor.name} ${software.name}"></c:set>
  <div style="font-size: 120%; font-weight: bold;"><html:img page="/common/images/help/icon_light.gif"/> <bean:message key="page.dm.wizard.success.help.1" arg0="${softwareName}"/></div>
  <div>
    <ul>
      <li><bean:message key="page.dm.wizard.success.help.5"/></li>
      <li><bean:message key="page.dm.wizard.success.help.6"/></li>
      <li><bean:message key="page.dm.wizard.success.help.9"/></li>
    </ul>
  </div>
  <div style="margin-bottom: 10px; font-size: 120%; font-weight: bold;">
     <html:img page="/common/images/help/icon_light.gif"/> <bean:message key="page.dm.wizard.success.help.2"/>
  </div>
  <logic:equal name="device" property="booted" value="false">
  <div style="margin-left: 20px; margin-bottom: 20px; ">
    <div style="margin-bottom: 10px; font-size: 120%;">
      <html:img page="/common/images/help/icon_square_gold.gif"/> <bean:message key="page.dm.wizard.success.help.bootstrap.1" arg0="${userPin}"/>
    </div>
    <div style="margin: 15px;">
      <div class="help_screen_shot">
        <html:img pageKey="page.dm.wizard.success.help.bootstrap.image.new_msg.src" altKey="page.dm.wizard.success.help.bootstrap.image.new_msg.alt"/>
      </div>
      <div class="help_screen_shot">
        <html:img pageKey="page.dm.wizard.success.help.bootstrap.image.input_pin.src" altKey="page.dm.wizard.success.help.bootstrap.image.input_pin.alt"/>
      </div>
      <div class="help_screen_shot">
        <html:img pageKey="page.dm.wizard.success.help.bootstrap.image.view_cp_msg.src" altKey="page.dm.wizard.success.help.bootstrap.image.view_cp_msg.alt"/>
      </div>
      <div class="help_screen_shot">
        <html:img pageKey="page.dm.wizard.success.help.bootstrap.image.save_msg.src" altKey="page.dm.wizard.success.help.bootstrap.image.save_msg.alt"/>
      </div>
    </div>
  </div>
  </logic:equal>
  <div style="margin-left: 20px; margin-bottom: 20px; clear: left; ">
    <div style="margin-bottom: 10px; font-size: 120%;">
      <html:img page="/common/images/help/icon_square_gold.gif"/> <bean:message key="page.dm.wizard.success.help.notification.1"/>
    </div>
    <div style="margin: 15px;">
      <div class="help_screen_shot">
        <html:img pageKey="page.dm.wizard.success.help.notification.image.notification_msg.src" altKey="page.dm.wizard.success.help.notification.image.notification_msg.alt"/>
      </div>
    </div>
  </div>
  
  <div style="margin-bottom: 20px; clear: both; font-size: 120%;">
    <html:img page="/common/images/help/icon_light.gif"/> <bean:define id="RetryURL"><html:rewrite action="/wizard/dm/InputPhone"/></bean:define><bean:message key="page.dm.wizard.success.help.10" arg0="${RetryURL}"/>
  </div>
</div>
<hr class="WizardHR"/>
<div class="WizardNavigation">
</div>
