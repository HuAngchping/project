<%@ page language="java" import="java.util.*" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%-- Push tiles attributes in page context --%>
<tiles:importAttribute/>

<!-- Navigator Pannel                                  -->
<div class="locationArea">
  <bean:message key="page.message.location.label"/>
  <tiles:useAttribute id="locationLabels" name="LocationLabels" classname="java.util.List"/>
  <%
  for (int i = 0; locationLabels != null && i < locationLabels.size(); i++) {
      String locationKey = (String)locationLabels.get(i);
      //out.println(locationKey);
  %>
  <bean:message key="<%=locationKey %>"/>
  <%
      if (i < locationLabels.size() - 1) {
         out.println("»");
  %>
  <%
      }
  %>
  <%
  }
  %>
  <%
  List<String> locationList = (List)request.getAttribute("AddLocationLabels");
  if (locationList != null) {
    for (int i = 0; i < locationList.size(); i++){
        out.println("»");
        out.println(locationList.get(i));
    }
  }
  %>
</div>
<hr class="locationArea">


