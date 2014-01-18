<%@ page language="java"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

	<table class="entityview">
		<thead>
			<tr>
				<th colspan="2">
					&nbsp;<bean:message key="page.model.capability.title" arg0="${model.manufacturer.name}" arg1="${model.manufacturerModelId}"/>
				</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<th width="350"></th>
				<td><html:img action="/ViewModelSpec?method=getModelIcon" paramId="modelID" paramName="model" paramProperty="ID" height="110"/></td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.title.manufacturer" />
				</th>
				<td><bean:write name="model" property="manufacturer.name"/></td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.title.manufacturerModelId" />
				</th>
				<td><bean:write name="model" property="manufacturerModelId"/></td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.title.name" />
				</th>
				<td><bean:write name="model" property="name"/></td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.spec.announced.time.label" />
				</th>
				<td><logic:present name="announced"><bean:write name="announced"/></logic:present></td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.spec.os.label" />
				</th>
				<td><logic:present name="os"><bean:write name="os"/></logic:present></td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.spec.developer.platform.label" />
				</th>
				<td><logic:present name="platform"><bean:write name="platform"/></logic:present></td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.title.taclist"/><br>
				</th>
				<td>
				<logic:iterate id="tac" name="model" property="modelTAC"><bean:write name="tac"/>, </logic:iterate>
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.title.description" />
				</th>
				<td><bean:write name="model" property="description"/></td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.view.capabilities.oma_cp_capabilities.label" />
				</th>
				<td>
					<!-- Model Capabilities Matrix -->
					<table class="model_matrix">
					  <thead>
					  <tr>
					    <!-- CP -->
					    <logic:iterate id="category" name="categories">
					      <th class="flag"><bean:write name="category" property="name"/></th>
					    </logic:iterate>
					  </tr>
					  </thead>
					  <tr>
					    <logic:iterate id="category" name="categories">
					      <td class="flag4cp" title="OMA CP <bean:write name="category" property="name"/>">
					      <c:set var="flag" value="${matrix_item.omaCPCapabilities[category.name]}" />
					      <c:if test="${flag}">Y</c:if>
					      </td>
					    </logic:iterate>
					  </tr>
					</table>
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.view.capabilities.oma_dm_capabilities.label" />
				</th>
				<td>
					<!-- Model Capabilities Matrix -->
					<table class="model_matrix">
					  <thead>
					  <tr>
					    <!-- DM -->
					    <logic:iterate id="category" name="categories">
					      <th class="flag"><bean:write name="category" property="name"/></th>
					    </logic:iterate>
					  </tr>
					  </thead>
					  <tr>
					    <logic:iterate id="category" name="categories">
					      <td class="flag4cp" title="OMA DM <bean:write name="category" property="name"/>">
					      <c:set var="flag" value="${matrix_item.omaDMCapabilities[category.name]}" />
					      <c:if test="${flag}">Y</c:if>
					      </td>
					    </logic:iterate>
					  </tr>
					</table>
				</td>
			</tr>
			<tr>
				<th>
					<bean:message key="model.view.capabilities.nokia_ota_capabilities.label" />
				</th>
				<td>
					<!-- Model Capabilities Matrix -->
					<table class="model_matrix">
					  <thead>
					  <tr>
					    <!-- Nokia OTA -->
					    <logic:iterate id="category" name="categories">
					      <th class="flag"><bean:write name="category" property="name"/></th>
					    </logic:iterate>
					  </tr>
					  </thead>
					  <tr>
					    <logic:iterate id="category" name="categories">
					      <td class="flag4nokia_ota" title="Nokia OTA <bean:write name="category" property="name"/>">
					      <c:set var="flag" value="${matrix_item.nokiaOtaCapabilities[category.name]}" />
					      <c:if test="${flag}">Y</c:if>
					      </td>
					    </logic:iterate>
					  </tr>
					</table>
				</td>
			</tr>
		</tbody>
	</table>

	<!-- start content -->
  <!-- 
	<table id="toc" class="toc" summary="目录" width="100%">
		<tr>
		<td>
			<div id="toctitle"><h2>目录</h2></div>
			<ul>
		    <logic:iterate id="category" name="modelCapabilities" indexId="index">
				<li class='toclevel-1'><a href='#<bean:write name="category" property="key"/>'><span class="tocnumber">${index+1}.</span> <span class="toctext"><bean:write name="category" property="key"/></span></a>
			  </li>
		    </logic:iterate>
			</ul>
		</td>
		</tr>
	</table>

	<table class="entityview">
		<tbody>
  		<logic:empty name="modelCapabilities">
			  <tr>
				  <td><bean:message key="page.model.capability.msg.NotAvailable"/></td>
			  </tr>
 		  </logic:empty>
  		<logic:notEmpty name="modelCapabilities">
			  <tr>
				  <td>
				  <ul>
		      <logic:iterate id="category" name="modelCapabilities" indexId="index">
						<a href='#<bean:write name="category" property="key"/>'>${index+1}. <bean:write name="category" property="key"/></a><br/>
		      </logic:iterate>
		      </ul>
				  </td>
			  </tr>
 		  </logic:notEmpty>
		</tbody>
	</table>
	-->
	<br>
	<table class="entityview">
		<thead>
			<tr>
				<th colspan="3">
					<bean:message key="page.model.capability.character"/>
				</th>
			</tr>
		</thead>
		<tbody>
		 <%int top = 0;%>
		  <logic:present name="generalCharacters">
			<tr>
				<th width="10">
					<%=++top%>
				</th>
				<th colspan="2">
					<p align="left"><bean:message key="page.model.capability.character.general"/></p>
				</th>
			</tr>		  
			<logic:iterate id="character" name="generalCharacters" indexId="subIndex">			
			<tr>
				<th width="10">
					<%=top%>.${subIndex+1}
				</th>
				<th width="350">
					<bean:write name="character" property="name"/>
				</th>
				<td>
				  <bean:write name="character" property="value"/>
				</td>
			</tr>
			</logic:iterate>
		  </logic:present>
		  <logic:present name="sizeCharacters">
			<tr>
				<th width="10">
					<%=++top%>
				</th>
				<th colspan="2">
					<p align="left"><bean:message key="page.model.capability.character.size"/></p>
				</th>
			</tr>		  
			<logic:iterate id="character" name="sizeCharacters" indexId="subIndex">			
			<tr>
				<th width="10">
					<%=top%>.${subIndex+1}
				</th>
				<th width="350">
					<bean:write name="character" property="name"/>
				</th>
				<td>
				  <bean:write name="character" property="value"/>
				</td>
			</tr>
			</logic:iterate>
		  </logic:present>
		  <logic:present name="displayCharacters">
			<tr>
				<th width="10">
					<%=++top%>
				</th>
				<th colspan="2">
					<p align="left"><bean:message key="page.model.capability.character.display"/></p>
				</th>
			</tr>		  
			<logic:iterate id="character" name="displayCharacters" indexId="subIndex">			
			<tr>
				<th width="10">
					<%=top%>.${subIndex+1}
				</th>
				<th width="350">
					<bean:write name="character" property="name"/>
				</th>
				<td>
				  <bean:write name="character" property="value"/>
				</td>
			</tr>
			</logic:iterate>
		  </logic:present>
		  <logic:present name="ringtonesCharacters">
			<tr>
				<th width="10">
					<%=++top%>
				</th>
				<th colspan="2">
					<p align="left"><bean:message key="page.model.capability.character.ringtone"/></p>
				</th>
			</tr>		  
			<logic:iterate id="character" name="ringtonesCharacters" indexId="subIndex">			
			<tr>
				<th width="10">
					<%=top%>.${subIndex+1}
				</th>
				<th width="350">
					<bean:write name="character" property="name"/>
				</th>
				<td>
				  <bean:write name="character" property="value"/>
				</td>
			</tr>
			</logic:iterate>
		  </logic:present>
		  <logic:present name="memoryCharacters">
			<tr>
				<th width="10">
					<%=++top%>.
				</th>
				<th colspan="2">
					<p align="left"><bean:message key="page.model.capability.character.memory"/></p>
				</th>
			</tr>		  
			<logic:iterate id="character" name="memoryCharacters" indexId="subIndex">			
			<tr>
				<th width="10">
					<%=top%>.${subIndex+1}
				</th>
				<th width="350">
					<bean:write name="character" property="name"/>
				</th>
				<td>
				  <bean:write name="character" property="value"/>
				</td>
			</tr>
			</logic:iterate>
		  </logic:present>
		  <logic:present name="dataCharacters">
			<tr>
				<th width="10">
					<%=++top%>
				</th>
				<th colspan="2">
					<p align="left"><bean:message key="page.model.capability.character.data"/></p>
				</th>
			</tr>		  
			<logic:iterate id="character" name="dataCharacters" indexId="subIndex">			
			<tr>
				<th width="10">
					<%=top%>.${subIndex+1}
				</th>
				<th width="350">
					<bean:write name="character" property="name"/>
				</th>
				<td>
				  <bean:write name="character" property="value"/>
				</td>
			</tr>
			</logic:iterate>
		  </logic:present>
		  <logic:present name="featuresCharacters">
			<tr>
				<th width="10">
					<%=++top%>
				</th>
				<th colspan="2">
					<p align="left"><bean:message key="page.model.capability.character.feature"/></p>
				</th>
			</tr>		  
			<logic:iterate id="character" name="featuresCharacters" indexId="subIndex">			
			<tr>
				<th width="10">
					<%=top%>.${subIndex+1}
				</th>
				<th width="350">
					<bean:write name="character" property="name"/>
				</th>
				<td>
				  <bean:write name="character" property="value"/>
				</td>
			</tr>
			</logic:iterate>
		  </logic:present>
		  <logic:present name="batteryCharacters">
			<tr>
				<th width="10">
					<%=++top%>
				</th>
				<th colspan="2">
					<p align="left"><bean:message key="page.model.capability.character.battery"/></p>
				</th>
			</tr>		  
			<logic:iterate id="character" name="batteryCharacters" indexId="subIndex">			
			<tr>
				<th width="10">
					<%=top%>.${subIndex+1}
				</th>
				<th width="350">
					<bean:write name="character" property="name"/>
				</th>
				<td>
				  <bean:write name="character" property="value"/>
				</td>
			</tr>
			</logic:iterate>
		  </logic:present>
	   </tbody>
	</table>
<br>
<logic:iterate id="category" name="modelCapabilities" indexId="index">
	<table class="entityview">
		<thead>
			<tr>
				<th colspan="3">
					<a name='<bean:write name="category" property="key"/>'>${index+1}. <bean:write name="category" property="key"/></a>
				</th>
			</tr>
		</thead>
		<tbody>
		  <bean:define id="capabilities" name="category" property="value"></bean:define>
			<logic:iterate id="capability" name="capabilities" indexId="subIndex">
			<tr>
				<th width="10">
					${index+1}.${subIndex+1}
				</th>
				<th width="350">
					<bean:write name="capability" property="name"/>
				</th>
				<td>
				  <logic:equal value="true" name="capability" property="value">
					  <html:img page="/common/images/icons/true.gif" alt="true"/>
				  </logic:equal>
				  <logic:equal value="false" name="capability" property="value">
					  <html:img page="/common/images/icons/false.gif" alt="false"/>
				  </logic:equal>
				  <logic:notEqual value="true" name="capability" property="value">
					  <logic:notEqual value="false" name="capability" property="value">
					    <bean:write name="capability" property="value"/>
					  </logic:notEqual>
				  </logic:notEqual>
				</td>
			</tr>
			</logic:iterate>
		</tbody>
	</table>
</logic:iterate>
	
	<div class="buttonArea">
	  <input type="button" name="CloseButton" onclick="return window.close();" value="<bean:message key="page.button.close.label"/>" class="NormalWidthButton"/>
	</div>
	<br/>
	