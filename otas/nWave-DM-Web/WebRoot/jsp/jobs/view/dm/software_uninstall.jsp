<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://otas.cn/otasdm-taglib" prefix="otasdm"%>

<!-- Job Basic Information -->
<jsp:include flush="true" page="/jsp/jobs/view/basic_info.jsp"></jsp:include>

<%-- Show Target Software   --%>
<jsp:include flush="true" page="/jsp/jobs/view/dm/show_software_uninstall.jsp"></jsp:include>

<!-- Target devices -->
<jsp:include flush="true" page="/jsp/jobs/view/dm/target_devices.jsp"></jsp:include>

<!-- Button Panel -->
<jsp:include flush="true" page="/jsp/jobs/view/dm/button_panel.jsp"></jsp:include>
