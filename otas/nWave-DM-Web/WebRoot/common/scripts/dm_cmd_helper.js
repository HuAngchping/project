var _command_script_target_form_element_;

function sendAjaxRequest(method, callBackMethodName, targetFormElement) {
	_command_script_target_form_element_ = targetFormElement;
	createXMLHttpRequest();
	url = WEB_HTTP_BASE_PATH + "common/CmdTemplate.do?method=" + method;
	if (targetFormElement && targetFormElement.value != null) {
		url += "&content=" + encodeURI(targetFormElement.value);
		// alert(url);
	}
	xmlHttp.open("GET", uncache(url), true);
	xmlHttp.onreadystatechange = callBackMethodName;
	xmlHttp.send(null);
}

var _last_command_script_content;
function insertIntoTemplate(formElement) {
	if (xmlHttp.readyState == 4) {
		if (xmlHttp.status == 200) {
			commandTemplate = xmlHttp.responseText
			_command_script_target_form_element_.focus();
			_last_command_script_content = _command_script_target_form_element_.value;
			if (window.getSelection) {
				scriptJobTextArea = document
						.getElementById("commandScriptEditor");
				start = 0;
				end = 0;
				if (typeof(scriptJobTextArea.selectionStart) == 'number') {
					start = scriptJobTextArea.selectionStart;
					end = scriptJobTextArea.selectionEnd;
				}
				pre = scriptJobTextArea.value.substr(0, start);
				post = scriptJobTextArea.value.substr(end);
				scriptJobTextArea.value = pre + commandTemplate + post;
			} else if (document.getSelection) {
				scriptJobTextArea = document
						.getElementById("commandScriptEditor");
				start = 0;
				end = 0;
				if (typeof(scriptJobTextArea.selectionStart) == 'number') {
					start = scriptJobTextArea.selectionStart;
					end = scriptJobTextArea.selectionEnd;
				}
				pre = scriptJobTextArea.value.substr(0, start);
				post = scriptJobTextArea.value.substr(end);
				scriptJobTextArea.value = pre + commandTemplate + post;
			} else if (document.selection) {
				document.selection.createRange().text += commandTemplate;
			}
		}
	}
}

function updateIntoTemplate() {
	if (xmlHttp.readyState == 4) {
		if (xmlHttp.status == 200) {
			commandTemplate = xmlHttp.responseText
      if (commandTemplate == 'errorMsg') {
        alert(alert_msg_cmd_toolbar_syntax_error);
        return;
      } else {
  			_last_command_script_content = _command_script_target_form_element_.value;
  			_command_script_target_form_element_.value = commandTemplate;
      } 
		}
	}
}

function doUndo(targetFormElement) {
	if (_last_command_script_content != null) {
		targetFormElement.value = _last_command_script_content;
	}
}

function doAddLeafNodeTemplate(targetFormElement) {
	sendAjaxRequest("getAddLeafNodeTemplate", insertIntoTemplate,
			targetFormElement);
}

function doAddInteriorNodeTemplate(targetFormElement) {
	sendAjaxRequest("getAddInteriorNodeTemplate", insertIntoTemplate,
			targetFormElement);
}

function doDeleteTemplate(targetFormElement) {
	sendAjaxRequest("getDeleteTemplate", insertIntoTemplate, targetFormElement);
}

function doExec(targetFormElement) {
	sendAjaxRequest("getExecItemTemplate", insertIntoTemplate,
			targetFormElement);
}

function doExec1(targetFormElement) {
	sendAjaxRequest("getExecItemDataTemplate", insertIntoTemplate,
			targetFormElement);
}

function doGet(targetFormElement) {
	sendAjaxRequest("getGetTemplate", insertIntoTemplate, targetFormElement);
}

function doReplace(targetFormElement) {
	sendAjaxRequest("getReplaceIntTemplate", insertIntoTemplate,
			targetFormElement);
}

function doReplace1(targetFormElement) {
	sendAjaxRequest("getReplaceCharTemplate", insertIntoTemplate,
			targetFormElement);
}

function doAlertDisplayTemplate(targetFormElement) {
	sendAjaxRequest("getAlertDisplayTemplate", insertIntoTemplate,
			targetFormElement);
}

function doAlertConfirmTemplate(targetFormElement) {
	sendAjaxRequest("getAlertConfirmTemplate", insertIntoTemplate,
			targetFormElement);
}

function doAlertSingleChoiceTemplate(targetFormElement) {
	sendAjaxRequest("getAlertSingleChoiceTemplate", insertIntoTemplate,
			targetFormElement);
}

function doAlertMultipleChoiceTemplate(targetFormElement) {
	sendAjaxRequest("getAlertMultipleChoiceTemplate", insertIntoTemplate,
			targetFormElement);
}

function doSequenceTemplate(targetFormElement) {
	sendAjaxRequest("getSequenceTemplate", insertIntoTemplate,
			targetFormElement);
}

function doAtomicTemplate(targetFormElement) {
	sendAjaxRequest("getAtomicTemplate", insertIntoTemplate, targetFormElement);
}

function doFormat(targetFormElement) {
	sendAjaxRequest("doXMLFormat", updateIntoTemplate, targetFormElement);
}

function changeProvince(targetFormElement) {
	sendAjaxRequest("doUpdateTemplate", insertIntoTemplate, targetFormElement);
}
