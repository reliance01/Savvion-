<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/bpmportal/tld/bpmportal.tld" prefix="sbm" %>

<script>
    var customPanelItems = new Array();
    var columnArray ;
    var tools = new Array();
    var viewMode = <c:out value="${viewmode}"/>;
    var layoutId = <c:out value="${layoutId}"/>;
    var layout = <c:out value="${layout}"/>;

    <c:set var="customColumns" value="${customColumns}"/>
    <c:set var="isColumnLayout" value="${isColumnLayout}"/>

    function addPanels() {
        <c:if test="${isColumnLayout}">
          <c:forEach var="widgetColumn" items="${customColumns}" varStatus="status">
              columnArray = new Array();
              <c:forEach var="widget" items="${widgetColumn.value}" varStatus="status">
                    dwDesc = "<c:out value = '${widget.dashboardWidgetDescWithHtml}' escapeXml = 'false'/>";
                    toolTips = getFormattedToolTip('<c:out value="${widget.dashboardWidgetName}"/>','<c:out value="${widget.dashboardWidgetAppName}"/>',dwDesc,'<c:out value="${widget.dashboardWidgetCategory}"/>');
                    tools = getTools(<c:out value="${widget.widget.type}"/>,<c:out value="${widget.dashboardWidgetId}"/>,<c:out value="${widget.dashboardId}"/>,<c:out value="${widget.widgetId}"/>,<c:out value="${widget.dashboardWidgetRow}"/>,<c:out value="${widget.dashboardWidgetColumn}"/>,toolTips,'<sbm:message key="BPM_Portal_Dashboard.Refresh"/>',viewMode,layout,{autoRefresh:<c:out value="${widget.widget.autoRefresh}" />,isPct:<c:out value="${isRequestFromBcp}"/>,target:'<%=PortalUtil.self().getConfig().getWidgetAutoRefreshTarget()%>',showCounter : <%=PortalUtil.self().getConfig().isWidgetAutoRefShowCounter()%>, category : '<c:out value="${widget.widget.category}"/>'});
                    columnArray.push({title: '<c:out value="${widget.dashboardWidgetTitle}" />',tools:tools, contentEl:'column_<c:out value="${widget.dashboardWidgetRow}" />_<c:out value="${widget.dashboardWidgetColumn}" />_<c:out value="${widget.dashboardWidgetId}" />', id:'<c:out value="${widget.dashboardWidgetId}" />'});
              </c:forEach>
              customPanelItems[<c:out value = "${widgetColumn.key-1}"/>] = columnArray;              
            </c:forEach>
        </c:if>
        if(customPanelItems.length == 0) {
            customPanelItems = null;
        } else {
            for(var ix =0; ix < customPanelItems.length; ix++) {
                if(!Ext.isEmpty(customPanelItems[ix]) && customPanelItems[ix].length == 0) {
                    customPanelItems[ix] = null;
                }
            }
        }
    }

    Ext.onReady(function() {
        try {

            Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

            Ext.QuickTips.init();

            Ext.apply( Ext.QuickTips.getQuickTip(), {
                dismissDelay:15000
            });
           
           addPanels();      
            
	    var config = {
                	  panelItems : customPanelItems,
                	  colData : [{col:1, width:.25}, {col:2, width:.25}, {col:3, width:.25}, {col:4, width:.25}]
            		 };
            		 
            var dashboardPanel = new Ext.Panel({
                layout:'border',
                items:[{
                    xtype: 'portal',
                    renderTo:'ListTblBgPadId',
                    border:false,                    
                    items : getDashboardWidgets(config),
                    listeners:{
                        'drop' : dropListener
                    }
                }]
            });
			dashboardPanel.render('ListTblBgPadId');
        } catch(error) {
          //alert(error);
        }
    });

    function dropListener(ev, target) {
        if(!viewMode){
            updateLayout(viewMode,''+layoutId,'reorder');
        }
    }
    </script>
    <c:if test="${isColumnLayout}">
      <c:if test="${not empty customColumns}">
          <c:forEach var="widgetColumn" items="${customColumns}" varStatus="status">
            <c:forEach var="widget" items="${widgetColumn.value}" varStatus="stat">
              <%@ include file="/bpmportal/common/widget.jsp" %>
            </c:forEach>
          </c:forEach>
      </c:if>
    </c:if>