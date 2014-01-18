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
        

        <div>
            <div id="osinfo">
                <div class="ajax_activity">
                </div>
            </div>

            <div id="chart_group" style="width: 99%;">
                <!-- Chart: Survivor Space -->
                <c:url value="/monitor/chart.do" var="chartUrl" scope="page">
                    <c:param name="p" value="chart_memory_usage"/>
                    <c:param name="sp" value="Survivor Space"/>
                    <c:param name="xz" value="228"/>
                    <c:param name="yz" value="120"/>
                    <c:param name="l" value="false"/>
                </c:url>
                <div class="chartContainer">
                    <dl>
                        <dt><bean:message key="page.JvmMemoryInfo.survivor_space.title"/></dt>
                        <dd class="image">
                            <img id="img_survivor_space"
                                 src="<c:out value="${chartUrl}" escapeXml="false"/>" alt=""
                                 onclick="zoomIn('Survivor Space')"/>
                        </dd>
                    </dl>
                </div>
                <script type="text/javascript">
                    new Ajax.ImgUpdater("img_survivor_space", 30);
                </script>

                <!-- Chart: Perm Gen -->
                <c:url value="/monitor/chart.do" var="chartUrl" scope="page">
                    <c:param name="p" value="chart_memory_usage"/>
                    <c:param name="sp" value="Perm Gen"/>
                    <c:param name="xz" value="228"/>
                    <c:param name="yz" value="120"/>
                    <c:param name="l" value="false"/>
                </c:url>
                <div class="chartContainer">
                    <dl>
                        <dt><bean:message key="page.JvmMemoryInfo.perm_gen.title"/></dt>
                        <dd class="image">
                            <img id="img_perm_gen"
                                 src="<c:out value="${chartUrl}" escapeXml="false"/>" alt=""
                                 onclick="zoomIn('Perm Gen')"/>
                        </dd>
                    </dl>
                </div>
                <script type="text/javascript">
                    new Ajax.ImgUpdater("img_perm_gen", 30);
                </script>

                <!-- Chart: Tenured Gen -->
                <c:url value="/monitor/chart.do" var="chartUrl" scope="page">
                    <c:param name="p" value="chart_memory_usage"/>
                    <c:param name="sp" value="Tenured Gen"/>
                    <c:param name="xz" value="228"/>
                    <c:param name="yz" value="120"/>
                    <c:param name="l" value="false"/>
                </c:url>
                <div class="chartContainer">
                    <dl>
                        <dt><bean:message key="page.JvmMemoryInfo.tenured_gen.title"/></dt>
                        <dd class="image">
                            <img id="img_tenured_gen"
                                 src="<c:out value="${chartUrl}" escapeXml="false"/>" alt=""
                                 onclick="zoomIn('Tenured Gen')"/>
                        </dd>
                    </dl>
                </div>
                <script type="text/javascript">
                    new Ajax.ImgUpdater("img_tenured_gen", 30);
                </script>

                <!-- Chart: Eden Space -->
                <c:url value="/monitor/chart.do" var="chartUrl" scope="page">
                    <c:param name="p" value="chart_memory_usage"/>
                    <c:param name="sp" value="Eden Space"/>
                    <c:param name="xz" value="228"/>
                    <c:param name="yz" value="120"/>
                    <c:param name="l" value="false"/>
                </c:url>
                <div class="chartContainer">
                    <dl>
                        <dt><bean:message key="page.JvmMemoryInfo.eden_space.title"/></dt>
                        <dd class="image">
                            <img id="img_eden_space"
                                 src="<c:out value="${chartUrl}" escapeXml="false"/>" alt=""
                                 onclick="zoomIn('Eden Space')"/>
                        </dd>
                    </dl>
                </div>
                <script type="text/javascript">
                    new Ajax.ImgUpdater("img_eden_space", 30);
                </script>

                <!-- Chart: Code Cache -->
                <c:url value="/monitor/chart.do" var="chartUrl" scope="page">
                    <c:param name="p" value="chart_memory_usage"/>
                    <c:param name="sp" value="Code Cache"/>
                    <c:param name="xz" value="228"/>
                    <c:param name="yz" value="120"/>
                    <c:param name="l" value="false"/>
                </c:url>
                <div class="chartContainer">
                    <dl>
                        <dt><bean:message key="page.JvmMemoryInfo.code_cache.title"/></dt>
                        <dd class="image">
                            <img id="img_code_cache"
                                 src="<c:out value="${chartUrl}" escapeXml="false"/>" alt=""
                                 onclick="zoomIn('Code Cache')"/>
                        </dd>
                    </dl>
                </div>
                <script type="text/javascript">
                    new Ajax.ImgUpdater("img_code_cache", 30);
                </script>
                
                <!-- Chart: Total -->
                <c:url value="/monitor/chart.do" var="chartUrl" scope="page">
                    <c:param name="p" value="chart_memory_usage"/>
                    <c:param name="sp" value="Total"/>
                    <c:param name="xz" value="228"/>
                    <c:param name="yz" value="120"/>
                    <c:param name="l" value="false"/>
                </c:url>
                <div class="chartContainer">
                    <dl>
                        <dt><bean:message key="page.JvmMemoryInfo.total.title"/></dt>
                        <dd class="image">
                            <img id="img_total"
                                 src="<c:out value="${chartUrl}" escapeXml="false"/>" alt=""
                                 onclick="zoomIn('Total')"/>
                        </dd>
                    </dl>
                </div>
                <script type="text/javascript">
                    new Ajax.ImgUpdater("img_total", 30);
                </script>

           </div>
           
           <c:url value="/monitor/chart.do" var="fullChartBase">
             <c:param name="p" value="chart_memory_usage"/>
             <c:param name="xz" value="750"/>
             <c:param name="yz" value="350"/>
           </c:url>
           <div id="fullMemoryChart" style="display: none;">
             <img id="fullImg" class="clickable" src="${fullChartBase}&sp=Total" alt="" onclick="zoomOut();"/>
           </div>
           <script type="text/javascript">

            var fullImageUpdater;

            function zoomIn(newPool) {
                if (fullImageUpdater) {
                    fullImageUpdater.stop();
                }
                var img = document.getElementById('fullImg');
                Effect.DropOut('chart_group');
                Effect.Appear('fullMemoryChart');
                fullImageUpdater = new Ajax.ImgUpdater("fullImg", 30, '<c:out value="${fullChartBase}" escapeXml="false"/>&sp=' + newPool + "&s1l=" + newPool);
            }

            function zoomOut() {
                Effect.DropOut('fullMemoryChart');
                Effect.Appear('chart_group');
                if (fullImageUpdater) {
                    fullImageUpdater.stop();
                    fullImageUpdater=null;
                }
            }

           </script>
    </c:otherwise>
</c:choose>
        