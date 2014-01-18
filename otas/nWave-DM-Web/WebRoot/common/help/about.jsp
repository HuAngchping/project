<%@ page language="java" pageEncoding="UTF-8"%>

<%@ page import="com.npower.dm.util.SystemInfo"%>
<%@ page import="java.util.*"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<jsp:include page="/jsp/common/header.jsp"></jsp:include>
	<TITLE>About ... </TITLE>
	
<script>
function cancelKeyPressed(e)
{
	var keycode = e.keyCode;
	if (keycode == 27) { // escape
		window.close();
	}
}
</script>
</head>
<%
SystemInfo sysInfo = new SystemInfo();
Map properties = sysInfo.getDMWebAdminBuildInfo();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onKeyPress="cancelKeyPressed(event)">
<table width="100%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="tableborder">
  <tr>
    <td height="30" align="left" valign="middle" ><img src="<html:rewrite page="/common/images/logo.gif"/>" height="46" border="0"></td>
  </tr>
  <tr>
    <td height="90%" align="left" valign="top" >
	<table width="100%" height="100%" border="0" cellpadding="3" cellspacing="1">
        <tr align="left" valign="top">
          <td width="4%" rowspan="4">&nbsp;</td>
          <td width="30%">&nbsp;</td>
          <td colspan="2">&nbsp;</td>
        </tr>
        <tr align="left" valign="top">
          <td height="12" align="left" valign="middle" class="tabboldtext">Version:</td>
          <td colspan="2" valign="middle" class="tabtext"><%=properties.get("Version")%></td>
        </tr>
	    <tr align="left" valign="top">
          <td height="12" align="left" valign="middle" class="tabboldtext">Build Number:</td>
          <td colspan="2" valign="middle" class="tabtext"><%=properties.get("Build Revision")%></td>
        </tr>
	    <tr align="left" valign="top">
          <td height="12" align="left" valign="middle" class="tabboldtext">Build Date:</td>
          <td colspan="2" valign="middle" class="tabtext"><%=properties.get("Build Date")%> <%=properties.get("Build Time")%></td>
        </tr>
        <tr align="left" valign="top">
          <td height="8" colspan="4"><img src="images/spacer.gif" width="34" height="8"></td>
        </tr>
        <tr align="left" valign="top">
          <td>&nbsp;</td>
          <td height="12" align="left" valign="bottom" class="tabboldtext">Credits:</td>
          <td width="61%" height="34" align="left" valign="middle">&nbsp;</td>
          <td width="2%" align="right">&nbsp;</td>
        </tr>
        <tr align="left" valign="top">
          <td>&nbsp;</td>
          <td height="100%" colspan="3" align="left">
			<table width="97%" height="100" border="0" cellpadding="4" cellspacing="2" class="about-window-bg">
              <tr>
                <td height="100%" align="left" valign="top" >
				<div style="height:100;overflow:hidden" class="tableborder">					
					<div id="iemarquee" style="position:relative;left:0px;top:0px;width:100%;" onMouseover="copyspeed=pausespeed" onMouseout="copyspeed=marqueespeed"> 
                    </div>
	            		</div>
				<script language="JavaScript1.2">
							var marqueewidth="250px"
							var marqueeheight="40px"
							var marqueespeed=1
							var pauseit=5
							var marqueecontent='<p align="center" class="scroll-text"><font color="#0066CC"><br>&nbsp;<br>&nbsp;<br>&nbsp;</font><br><br></p>'

							marqueespeed=(document.all)? marqueespeed : Math.max(1, marqueespeed-1) //slow speed down by 1 for NS
							var copyspeed=marqueespeed
							var pausespeed=(pauseit==0)? copyspeed: 0
							var iedom=document.all||document.getElementById
							var actualheight=''
							var cross_marquee, ns_marquee

							function populate(){
								cross_marquee=document.getElementById? document.getElementById("iemarquee") : document.all.iemarquee
								cross_marquee.style.top=parseInt(marqueeheight)+8+"px"
								cross_marquee.innerHTML=marqueecontent
								actualheight=cross_marquee.offsetHeight
								lefttime=setInterval("scrollmarquee()",30)
							}
							//window.onload=populate
							window.onload=new Function("timer = setTimeout('populate()', 1000);")

							function scrollmarquee() {
								if (parseInt(cross_marquee.style.top)>(actualheight*(-1)+8))
								{
									cross_marquee.style.top=parseInt(cross_marquee.style.top)-copyspeed+"px"
									}
								else{
									cross_marquee.style.top=parseInt(marqueeheight)+8+"px"
									}
							}


					</script>

                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr align="left" valign="top">
          <td>&nbsp;</td>
          <td height="24" colspan="3" align="left" valign="middle">
            <bean:message key="page.copyright"/>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td height="2" align="left" valign="top" class="footertext"><img src="images/spacer.gif" width="34" height="2"></td>
  </tr>
  <tr>
    <td height="30" align="right" valign="middle" bgcolor="#347CD5">
      <input name="Submit2" type="button" class="buttons" onclick="window.close();" value="  Close  "> &nbsp;&nbsp;
    </td>
  </tr>
</table>
</body>
</html:html>
