<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/bpmportal/tld/bpmportal.tld" prefix="sbm" %>

<script>
var panelItemsOne = new Array();
var panelItemsTwo = new Array();
var tools = new Array();
var viewMode = <c:out value="${viewmode}"/>;
var layout = <c:out value="${layout}"/>;

function addPanels(){
	<c:forEach var="widget" items="${columnOne}" varStatus="status">
		dwDesc = "<c:out value = '${widget.dashboardWidgetDescWithHtml}' escapeXml = 'false'/>"; 
		toolTips = getFormattedToolTip('<c:out value="${widget.dashboardWidgetName}"/>','<c:out value="${widget.dashboardWidgetAppName}"/>',dwDesc, '<c:out value="${widget.dashboardWidgetCategory}"/>');
		tools = getTools(<c:out value="${widget.widget.type}"/>,<c:out value="${widget.dashboardWidgetId}"/>,<c:out value="${widget.dashboardId}"/>,<c:out value="${widget.widgetId}"/>,<c:out value="${widget.dashboardWidgetRow}"/>,<c:out value="${widget.dashboardWidgetColumn}"/>,toolTips,'<sbm:message key="BPM_Portal_Dashboard.Refresh"/>',viewMode,layout,{autoRefresh:<c:out value="${widget.widget.autoRefresh}" />,isPct:<c:out value="${isRequestFromBcp}"/>,target:'<%=PortalUtil.self().getConfig().getWidgetAutoRefreshTarget()%>',showCounter : <%=PortalUtil.self().getConfig().isWidgetAutoRefShowCounter()%>, category : '<c:out value="${widget.widget.category}"/>'});
		panelItemsOne.push({title: '<c:out value="${widget.dashboardWidgetTitle}" />',tools:tools, contentEl:'column_<c:out value="${widget.dashboardWidgetRow}" />_<c:out value="${widget.dashboardWidgetColumn}" />_<c:out value="${widget.dashboardWidgetId}" />', id:'<c:out value="${widget.dashboardWidgetId}" />'});
	</c:forEach>
	<c:forEach var="widget" items="${columnTwo}" varStatus="status">
		dwDesc = "<c:out value = '${widget.dashboardWidgetDescWithHtml}' escapeXml = 'false'/>"; 
		toolTips = getFormattedToolTip('<c:out value="${widget.dashboardWidgetName}"/>','<c:out value="${widget.dashboardWidgetAppName}"/>',dwDesc, '<c:out value="${widget.dashboardWidgetCategory}"/>');
		tools = getTools(<c:out value="${widget.widget.type}"/>,<c:out value="${widget.dashboardWidgetId}"/>,<c:out value="${widget.dashboardId}"/>,<c:out value="${widget.widgetId}"/>,<c:out value="${widget.dashboardWidgetRow}"/>,<c:out value="${widget.dashboardWidgetColumn}"/>,toolTips,'<sbm:message key="BPM_Portal_Dashboard.Refresh"/>',viewMode,layout,{autoRefresh:<c:out value="${widget.widget.autoRefresh}" />,isPct:<c:out value="${isRequestFromBcp}"/>,target:'<%=PortalUtil.self().getConfig().getWidgetAutoRefreshTarget()%>',showCounter : <%=PortalUtil.self().getConfig().isWidgetAutoRefShowCounter()%>, category : '<c:out value="${widget.widget.category}"/>'});
		panelItemsTwo.push({title: '<c:out value="${widget.dashboardWidgetTitle}" />',tools:tools, contentEl:'column_<c:out value="${widget.dashboardWidgetRow}" />_<c:out value="${widget.dashboardWidgetColumn}" />_<c:out value="${widget.dashboardWidgetId}" />', id:'<c:out value="${widget.dashboardWidgetId}" />'});
	</c:forEach>
	if(panelItemsOne.length==0){
		panelItemsOne = null;
	}
	if(panelItemsTwo.length==0){
		panelItemsTwo = null;	
	}
}

Ext.onReady(function(){

    Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

    Ext.QuickTips.init();
    
    Ext.apply(Ext.QuickTips.getQuickTip(), {
        	dismissDelay:15000
    });
    
    addPanels();
    
    var dashboardPanel = new Ext.Panel({
        layout:'border',
        items:[{
            xtype:'portal',
            renderTo:'ListTblBgPadId',
            border:false,
            items:[{
                columnWidth:.7,
                style:'padding:10px 6px 6px 0px',
                items: panelItemsOne
            },{
                columnWidth:.3,
                style:'padding:10px 20px 6px 0px',
                items:panelItemsTwo
            }],
            listeners:{
                'drop' : dropListener
            }
        }]
    });
    dashboardPanel.render('ListTblBgPadId');
});

function dropListener(ev, target) {
    if(!viewMode){
        updateLayout(viewMode,'3','reorder');
    }
}

</script>

<c:if test="${not empty columnOne}">
    <c:forEach var="widget" items="${columnOne}" varStatus="status">
        <%@ include file="../common/widget.jsp" %>
    </c:forEach>
</c:if>
<c:if test="${not empty columnTwo}">
    <c:forEach var="widget" items="${columnTwo}" varStatus="status">
        <%@ include file="../common/widget.jsp" %>
    </c:forEach>
</c:if>
