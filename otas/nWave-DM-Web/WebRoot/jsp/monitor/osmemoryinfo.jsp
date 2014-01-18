<%@ page language="java"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<c:choose>
    <c:when test="${false}">
        <div class="errorMessage">
            <p>
               <bean:message key="page.osMemoryInfo.missingJMX.message"/>
            </p>
        </div>
    </c:when>
    <c:otherwise>
        
        <c:url value="/monitor/chart.do" var="os_memory_url">
            <c:param name="p" value="chart_os_memory"/>
            <c:param name="xz" value="260"/>
            <c:param name="yz" value="140"/>
            <c:param name="s1c" value="#FF0606"/>
            <c:param name="s1o" value="#9d0000"/>
            <c:param name="s2c" value="#9bd2fb"/>
            <c:param name="s2o" value="#0665aa"/>
            <c:param name="l" value="false"/>
        </c:url>

        <c:url value="/monitor/chart.do" var="os_memory_url_full">
            <c:param name="p" value="chart_os_memory"/>
            <c:param name="xz" value="650"/>
            <c:param name="yz" value="320"/>
            <c:param name="l" value="true"/>
            <c:param name="s1c" value="#FF0606"/>
            <c:param name="s1o" value="#9d0000"/>
            <c:param name="s2c" value="#9bd2fb"/>
            <c:param name="s2o" value="#0665aa"/>
            <c:param name="s1l"><bean:message key="page.osMemoryInfo.committed.legend"/></c:param>
            <c:param name="s2l"><bean:message key="page.osMemoryInfo.physical.legend"/></c:param>
        </c:url>

        <c:url value="/monitor/chart.do" var="swap_usage_url">
            <c:param name="p" value="chart_swap_usage"/>
            <c:param name="xz" value="260"/>
            <c:param name="yz" value="140"/>
            <c:param name="s1c" value="#FFCD9B"/>
            <c:param name="s1o" value="#D26900"/>
            <c:param name="l" value="false"/>
        </c:url>

        <c:url value="/monitor/chart.do" var="swap_usage_url_full">
            <c:param name="p" value="chart_swap_usage"/>
            <c:param name="xz" value="650"/>
            <c:param name="yz" value="320"/>
            <c:param name="s1c" value="#FFCD9B"/>
            <c:param name="s1o" value="#D26900"/>
            <c:param name="s1l"><bean:message key="page.osMemoryInfo.swapUsage.title"/></c:param>
        </c:url>

        <c:url value="/monitor/chart.do" var="cpu_usage_url">
            <c:param name="p" value="chart_cpu_usage"/>
            <c:param name="xz" value="260"/>
            <c:param name="yz" value="140"/>
            <c:param name="s1c" value="#FFCCCC"/>
            <c:param name="s1o" value="#FF8484"/>
            <c:param name="l" value="false"/>
        </c:url>

        <c:url value="/monitor/chart.do" var="cpu_usage_url_full">
            <c:param name="p" value="chart_cpu_usage"/>
            <c:param name="xz" value="650"/>
            <c:param name="yz" value="320"/>
            <c:param name="s1c" value="#FFCCCC"/>
            <c:param name="s1o" value="#FF8484"/>
            <c:param name="s1l"><bean:message key="page.osMemoryInfo.jvmCPUUtilization.title"/></c:param>
        </c:url>

        <div>
            <div id="osinfo">
                <div class="ajax_activity">
                </div>
            </div>

            <div id="chart_group" style="width: 99%;">
                <div class="chartContainer">
                    <dl>
                        <dt><bean:message key="page.osMemoryInfo.jvmCPUUtilization.title"/></dt>
                        <dd class="image">
                            <img id="cpu_chart" border="0" src="<c:out value="${cpu_usage_url}" escapeXml="false"/>"
                                 alt="<bean:message key="page.osMemoryInfo.jvmCPUUtilization.title"/>"/>
                        </dd>
                    </dl>
                </div>

                <div class="chartContainer">
                    <dl>
                        <dt><bean:message key="page.osMemoryInfo.jvmCPUUtilization.title"/></dt>
                        <dd class="image">
                            <img id="mem_chart" border="0" src="<c:out value="${os_memory_url}" escapeXml="false"/>"
                                 alt="<bean:message key="page.osMemoryInfo.jvmCPUUtilization.title"/>"/>
                        </dd>
                    </dl>
                </div>

                <div class="chartContainer">
                    <dl>
                        <dt><bean:message key="page.osMemoryInfo.swapUsage.title"/></dt>
                        <dd class="image">
                            <img id="swap_chart" border="0" src="<c:out value="${swap_usage_url}" escapeXml="false"/>"
                                 alt="<bean:message key="page.osMemoryInfo.swapUsage.title"/>"/>
                        </dd>
                    </dl>
                </div>
            </div>

            <div id="full_chart" style="display: none;">
                <img id="fullImg" class="clickable" src="" alt=""/>
            </div>
        </div>

        <script type="text/javascript">
            new Ajax.ImgUpdater("cpu_chart", 5);
            new Ajax.ImgUpdater("mem_chart", 5);
            new Ajax.ImgUpdater("swap_chart", 5);
        </script>


        <script type="text/javascript">
            var fullImageUpdater;

            function zoomIn(url) {
                if (fullImageUpdater) {
                    fullImageUpdater.stop();
                }
                var img = document.getElementById('fullImg');
                Effect.DropOut('chart_group');
                Effect.Appear('full_chart');
                fullImageUpdater = new Ajax.ImgUpdater("fullImg", 30, url);
            }

            function zoomOut() {
                Effect.DropOut('full_chart');
                Effect.Appear('chart_group');
                if (fullImageUpdater) {
                    fullImageUpdater.stop();
                    fullImageUpdater = null;
                }
            }

            var rules = {
                '#mem_chart': function(element) {
                    element.onclick = function() {
                        zoomIn('<c:out value="${os_memory_url_full}" escapeXml="false"/>');
                    }
                },
                '#swap_chart': function(element) {
                    element.onclick = function() {
                        zoomIn('<c:out value="${swap_usage_url_full}" escapeXml="false"/>');
                    }
                },
                '#cpu_chart': function(element) {
                    element.onclick = function() {
                        zoomIn('<c:out value="${cpu_usage_url_full}" escapeXml="false"/>');
                    }
                },
                '#full_chart': function(element) {
                    element.onclick = function() {
                        zoomOut();
                    }
                }
            }

            Behaviour.register(rules);

            new Ajax.PeriodicalUpdater("osinfo", "<c:url value="/ajax.do?method=getOSMemoInfoAction"/>", {frequency: 5});

        </script>
        
    </c:otherwise>
</c:choose>
        