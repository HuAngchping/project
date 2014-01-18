<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested"	prefix="nested"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn"%>

<SCRIPT type="text/javascript">

/*
 Exchange option from sourceSelect to targetSelect,
    The selected ptions will be remove from sourceSelect list, and move to targetSelect list
*/
function doSelectModelFamily(button) {
  
  sourceSelect = button.form.software;
  targetSelect = button.form.recommendIds;
  
  for (i = 0; i < sourceSelect.options.length; i++) {
      option = sourceSelect.options[i];
      if (option.selected) {
         exists = false;
         modelID = option.value;
         if (modelID == "") {
            continue;
         }
         for (j = 0; j < targetSelect.options.length; j++) {
             if (modelID == targetSelect.options[j].value) {
                exists = true;
             }
         }
         if (!exists) {
            targetSelect[targetSelect.options.length] = new Option(option.text, option.value, false);
         }
      }
  }
}

function doDeSelectModelFamily(button) {
  targetSelect = button.form.recommendIds;
  
  for (j = targetSelect.options.length - 1; j >= 0; j--) {
      if (targetSelect.options[j].selected) {
         targetSelect.options[j] = null;
      }
  }
}

function doCleanAllModelFamily(form) {
  if (confirm('<bean:message key="page.software.package.edit.prompt.clean.all.message" />')) {
     form.recommendIds.length = 0;
  }
}

function swapNode(node1,node2) {
  var parent = node1.parentNode;//父节点
  var t1 = node1.nextSibling;//两节点的相对位置
  var t2 = node2.nextSibling;
  if (t1) {
    parent.insertBefore(node2,t1);
  } else {
    parent.appendChild(node2);
  }
  if (t2) {
    parent.insertBefore(node1,t2);
  } else {
    parent.appendChild(node1);
  }
}

function moveUp(button,isToTop) {
  targetSelect = button.form.recommendIds;
  
  document.forms(0).up.disabled = true;
  if (isToTop == null) 
    var isToTop = false; 

  var selIndex = targetSelect.selectedIndex; 
  if (selIndex <= 0)
    return; 
  if (isToTop) { 
    while (selIndex > 0) { 
      swapNode(targetSelect.options[selIndex], targetSelect.options[selIndex - 1]); 
      selIndex --; 
    }
  } else {
    swapNode(targetSelect.options[selIndex], targetSelect.options[selIndex - 1]); 
  } 
  document.forms(0).up.disabled = false;
}

function moveDown(button,isToTop) {
  targetSelect = button.form.recommendIds;
  
  document.forms(0).up.disabled = true;
  if (isToTop == null) {
    var isToTop = false; 
  }
  var selLength = targetSelect.options.length - 1; 
  var selIndex = targetSelect.selectedIndex; 
  if (selIndex >= selLength) {
    return;
  }
  if (isToTop) { 
    while (selIndex < selLength - 1) {
      swapNode(targetSelect.options[selIndex], targetSelect.options[selIndex + 1]); 
      selIndex ++; 
    } 
  } else {
    swapNode(targetSelect.options[selIndex], targetSelect.options[selIndex + 1]); 
  } 
  document.forms(0).up.disabled = false;
}

function doSelectAll(form) {
  targetSelect = form.recommendIds;
  for (j = targetSelect.options.length - 1; j >= 0; j--) {
      targetSelect.options[j].selected = true;
  }
  return true;
}
</SCRIPT>

<fmt:bundle basename="com.npower.dm.struts.ApplicationResources">
</fmt:bundle>

<logic:present name="category">
	<logic:notEmpty name="category">
		<table class="entityview">
			<thead>
				<tr>
					<th colspan="2">
						&nbsp;<bean:write name="category" property="pathAsString" />
					</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<th width="150">
						<bean:message key="entity.software.category.name.label" />
					</th>
					<td>
						<bean:write name="category" property="name" />
					</td>
				</tr>
				<logic:present name="category" property="parent">
					<tr>
						<th>
							<bean:message key="entity.software.category.parent.label" />
						</th>
						<td>
							<c:out value='${category.parent.pathAsString}' />
						</td>
					</tr>
				</logic:present>
				<tr>
					<th>
						<bean:message key="entity.software.category.description.label" />
					</th>
					<td>
						<bean:write name="category" property="description" />
					</td>
				</tr>
			</tbody>
		</table>
	</logic:notEmpty>
</logic:present>
<logic:notPresent name="category">
  <logic:empty name="category">
    <table class="entityview">
      <thead>
        <tr>
          <th colspan="2">
            &nbsp;<bean:message key="page.software.recommend.root.category.name.label"/>
          </th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <th width="150">
            <bean:message key="entity.software.recommend.root.category.name.label" />
          </th>
          <td>
            <bean:message key="page.software.recommend.root.category.name.label"/>
          </td>
        </tr>
        <tr>
          <th>
            <bean:message key="entity.software.recommend.root.category.parent.label" />
          </th>
          <td>
            
          </td>
        </tr>
        <tr>
          <th>
            <bean:message key="entity.software.recommend.root.category.description.label" />
          </th>
          <td>
            <bean:message key="page.software.recommend.root.category.description.label"/>
          </td>
        </tr>
      </tbody>
    </table>
  </logic:empty>
</logic:notPresent>
<html:form action="/software/recommendSave" method="post" enctype='multipart/form-data' onsubmit="return doSelectAll(this);">
  <html:hidden property="categoryId" value="${category.id}"/>
	<table class="entityview">
		<thead>
			<logic:present name="category">
				<tr>
					<th colspan="2">
						<font color="red"><b>.:</b></font>
						<bean:message	key="entity.software.category.recommend.label" />
					</th>
				</tr>
			</logic:present>
		  <logic:notPresent name="category">
        <tr>
          <th colspan="2">
            <font color="red"><b>.:</b></font>
            <bean:message key="entity.software.recommend.root.category.list.title" />
          </th>
        </tr>
       </logic:notPresent>
		</thead>
		<tbody>
			<tr>
				<th width="150">
					*
					<bean:message key="entity.software.category.recommend.list.label" />
				</th>
				<td>
					<table cellpadding="0" cellspacing="0" border="0"
						style="border: 0px;">
						<tr>
							<td style="width: 200px; border: 0px; vertical-align: top;">
							</td>
							<td style="width: 30px; border: 0px;">
							</td>
							<td style="border: 0px;">
								<B><bean:message key="entity.software.category.recommended.list.label" />
								</B>
							</td>
						</tr>
						<tr>
							<td style="width: 200px; border: 0px; vertical-align: top;">
								<html:select property="software" multiple="true" size="10" style="width: 240px;">
									<html:optionsCollection name="RecommendSoftwares" />
								</html:select>
							</td>
							<td style="width: 30px; border: 0px;">
								<input type="button" value="<bean:message key="page.software.recommend.edit.button.add.label"/>" onclick="return doSelectModelFamily(this);" style="width: 80px;">
								<br />
								<input type="button" value="<bean:message key="page.software.recommend.edit.button.remove.label"/>"	onclick="return doDeSelectModelFamily(this);"	style="width: 80px;">
								<br />
								<input type="button" value="<bean:message key="page.software.recommend.edit.button.cleanAll.label"/>"	onclick="return doCleanAllModelFamily(this.form);" style="width: 80px;">
							</td>
							<td style="border: 0px;">
								<html:select property="recommendIds" multiple="true" size="10" style="width: 240px;">
									<html:optionsCollection name="RecommendedSoftwares" />
								</html:select>
							</td>
							<td style="width: 30px; border: 0px;">
                <input type="button" name="up" value="<bean:message key="page.software.recommend.edit.button.up.label"/>" onclick="return moveUp(this);" style="width: 80px;">
                <br />
                <input type="button" name="down" value="<bean:message key="page.software.recommend.edit.button.down.label"/>" onclick="return moveDown(this);" style="width: 80px;">
                <br />							  
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</tbody>
	</table>              
  <div class="buttonArea">
    <html:submit styleClass="NormalWidthButton">
      <bean:message key="page.software.recommend.save.button.label"/>
    </html:submit>
  </div>           
</html:form>