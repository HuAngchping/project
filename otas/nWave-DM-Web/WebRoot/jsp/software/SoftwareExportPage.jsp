<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<script language="JavaScript">
  var xmlHttp; 
  
  function doExportData(){
    if(window.ActiveXObject){
      xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
    }else if(window.XMLHttpRequest){
      xmlHttp=new XMLHttpRequest();
    }
    var url= WEB_HTTP_BASE_PATH + "exportSoftware.do?action=exportData";
    xmlHttp.open("GET",url);
    xmlHttp.onreadystatechange=callback;
    xmlHttp.send(null);
  }

  function callback(){
    if(xmlHttp.readyState==4){
      if(xmlHttp.status==200){
        var  result=xmlHttp.responseText;                              
        document.getElementById("result").innerHTML=result;
        document.getElementById("submit").disabled="";  
        document.getElementById("download").style.display=""; 
        document.getElementById("picture").style.display="none";   
      }
    }else {
      document.getElementById("picture").style.display="";                
    }
  }

  function disable(){
    document.getElementById("submit").disabled="true";    
  }
</script>
<html:html>
<div class="messageArea">
	<bean:message key="software.export.page.explain" />
</div>
<div class="buttonArea" style="height:40px">	
  <html:submit property="submit" onclick="doExportData();disable();" >
	<bean:message key="export.software.button" />
  </html:submit>	
</div>
<br>
<div id="result"></div>
<div id="download"  class="buttonArea" style="height:40px;display: none" >
	<html:form action="/exportSoftware.do?action=downLoadData" >
		<html:submit>
		  <bean:message key="button.export.download" />
		</html:submit>
	</html:form>
</div>
<div id="picture" style="display: none"><img src="common/images/export_data.gif" width="100" height="10"> </div>
</html:html>
