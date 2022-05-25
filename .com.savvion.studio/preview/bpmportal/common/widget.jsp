<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/bpmportal/tld/bpmportal.tld" prefix="sbm" %>
<%@ page import="com.savvion.sbm.bpmportal.util.PortalUtil" %>
<%@ page import="com.savvion.sbm.bpmportal.util.PortalConstants" %>
<%@ page import="com.savvion.sbm.bpmportal.util.PortalConfig" %>
<%@ page import="com.savvion.sbm.bpmportal.util.DashboardConstants" %>

<div id="column_<c:out value='${widget.dashboardWidgetRow}'/>_<c:out value='${widget.dashboardWidgetColumn}'/>_<c:out value='${widget.dashboardWidgetId}'/>" style="border:none;">
    <div style="left:5;top:5;right:5;bottom:5" id='content_<c:out value="${widget.dashboardWidgetId}" />'>
    </div>
    <c:choose>
     <c:when test="${not empty widget.widget.drillDownUrl}">  
        <div id="footNoteDiv" style="display:none;class='SmlTxt'">
            <c:if test = "${widget.widget.chartType == 'verticalBar3D' }">
                    <center>(<sbm:message key="BPM_Portal_Dashboard.Bar.FootNote"/>)<center>
            </c:if>
        </div>               
    </c:when>
    <c:otherwise>
    <div>&nbsp;</div>
    </c:otherwise>  
    </c:choose>
    <script>
   
    	<%
    	boolean reqFromBcp = (Boolean)request.getAttribute("isRequestFromBcp");    	
    	if(PortalUtil.self().getConfig().isWidgetAutoRefresh()) {     	
            String target = PortalUtil.self().getConfig().getWidgetAutoRefreshTarget();            
            if((target.equalsIgnoreCase(String.valueOf(PortalConfig.TARGET.PCT)) && reqFromBcp) || (target.equalsIgnoreCase(String.valueOf(PortalConfig.TARGET.SBM)) && !reqFromBcp) || target.equalsIgnoreCase(String.valueOf(PortalConfig.TARGET.ALL))){         
    	%>
        <c:if test="${viewmode}">
    	var timerId = 'timer_'+'<c:out value="${widget.dashboardWidgetId}"/>';
            var widgetType = <c:out value="${widget.widget.type}"/>;
            if(!Ext.isEmpty(widgetType) && widgetType != <%=DashboardConstants.COMPOSITEUI_WIDGET%>) {
			timerColln.add({
				id     		: timerId,
		        	panelId		: '<c:out value="${widget.dashboardWidgetId}"/>',
		        	msg    		    : '<sbm:message key="AutoRefreshMsg" />',
		        	msgStyle	    : 'color:green;font-size:x-small',
		        	widgetType	: '<c:out value="${widget.widget.type}"/>',
		        	dashWidgetId	: '<c:out value="${widget.dashboardWidgetId}"/>',
		        	dashId		: '<c:out value="${widget.dashboardId}"/>',
		        	widgetId    	: '<c:out value="${widget.widgetId}"/>',
		        	row			: '<c:out value="${widget.dashboardWidgetRow}"/>',
		        	column		: '<c:out value="${widget.dashboardWidgetColumn}"/>',
		        	refreshTime 	: <c:out value="${widget.widget.refreshInterval}"/>,
		        	autoRefresh 	: <c:out value="${widget.widget.autoRefresh}"/>,
		        	showCounter 	: <%=PortalUtil.self().getConfig().isWidgetAutoRefShowCounter()%>,
                    timerInterval   : 1,
		        	editMode		: false
			});				
		}
       </c:if> 
	<% } 
	
	}%>	
        var mode = '<c:out value="${mode}"/>';
        var renderUrl = '';
        if(mode == 'user') {
            renderUrl = '../myhome/widgetrenderer.portal';
        } else {
            renderUrl = '../administration/widgetrenderer.portal';
        }
        Ext.get('content_<c:out value="${widget.dashboardWidgetId}" />').load({
            url: renderUrl,
            scripts: true,
            params: 'viewmode=<c:out value="${viewmode}"/>&dwid=<c:out value="${widget.dashboardWidgetId}"/>&did=<c:out value="${widget.dashboardId}"/>&wid=<c:out value="${widget.widgetId}"/>',
            text: "Loading dashboard widget ...",
            callback: successHandler
        });
        
        function successHandler() {
            var el = document.getElementById("footNoteDiv");
            if(el != null && el != undefined) {
                el.style.display = 'block';
                el.style.fontSize = 10;
            }            
            <c:if test="${viewmode}">
            if(!Ext.isEmpty(timerColln)){
            var timerObj = timerColln.getTimer('timer_'+'<c:out value="${widget.dashboardWidgetId}"/>');            
            if(!Ext.isEmpty(timerObj) && timerObj.isAutoRefreshEnabled()){            	
            	timerObj.enableAutoRefresh();
                }
            }
            </c:if>
        }
    </script>
</div>

