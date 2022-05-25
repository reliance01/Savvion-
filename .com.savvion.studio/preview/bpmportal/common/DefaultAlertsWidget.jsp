<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/bpmportal/tld/bpmportal.tld" prefix="sbm" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.savvion.sbm.bpmportal.mvc2.alerts.util.MyAlerts" %>
<%@ page import="com.savvion.sbm.bizmanage.util.BizManageConfiguration" %>
<%@ page import="com.savvion.sbm.bpmportal.util.PortalUtil" %>
<jsp:useBean id="bizManage" class="com.savvion.sbm.bizmanage.api.BizManageBean" scope="session" />
<sbm:setLocale value="<%= bizManage.getLocale() %>" />
<sbm:setBundle basename="properties/bpmportal" scope="page" />

<%
    Integer pgSize = (Integer)request.getAttribute("pageSize");
    String height = (String)request.getAttribute("height");
    String width = (String)request.getAttribute("width");
    String user = bizManage.getName();
    String selectedApp = request.getParameter("app");
    String selectedSeverity = request.getParameter("severity");
    String selectedFromDate = request.getParameter("fromDate");
    String selectedToDate   = request.getParameter("toDate");
    String sortColumn = request.getParameter("sortColumn");
    String sortOrder = request.getParameter("sortOrder");

    MyAlerts ma = new MyAlerts();
    List<String>  priorities = PortalUtil.self().getPriorities();

    String fieldDefs = "{name: \"appName\"}, {name: \"alertName\"}, {name: \"message\"}, {name: \"severity\"}, {name: \"alertDate\"}, {name: \"recipient\"}, {name: \"alertTitle\"}, {name: \"subject\"}, {name: \"body\"},{name: \"alertId\"}, {name: \"bm_flag\"}, {name: \"bm_options\"}";

    String columnDefs = "{dataIndex: \"appName\", header: i18n_application_name, renderer: Bm.Alerts.alertAppNameRenderer}, {dataIndex: \"alertName\", header: i18n_alert_name, renderer: Bm.Alerts.alertAlertNameRenderer},{dataIndex: \"message\", header: i18n_alert_message, width:120, sortable: false, menuDisabled: true, renderer: Bm.Alerts.alertMessageRenderer}, {dataIndex: \"severity\",header: i18n_alert_severity, width:60}, {dataIndex: \"alertDate\", header: i18n_alert_date, width:120,  renderer: Bm.Alerts.alertAlertDateRenderer }, {dataIndex: \"action\",header: i18n_alert_action, width:50, sortable: false,  menuDisabled: true, renderer: Bm.Alerts.alertActionRenderer}";
%>

<script type="text/javascript">
	var i18n_No = "<sbm:message key="srno" />";
    var i18n_application_name = "<sbm:message key="Application"/>";
    var i18n_alert_name = "<sbm:message key="Alert"/>";
    var i18n_alert_message = "<sbm:message key="messageLabel"/>";
    var i18n_alert_severity = "<sbm:message key="Severity"/>";
    var i18n_alert_date = "<sbm:message key="DATETIME"/>";
    var i18n_alert_action = "<sbm:message key="Action"/>";

    Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

    Ext.namespace("Bm.Alerts");

    Bm.Alerts.fieldDefs = [<%= fieldDefs %>];

    Bm.Alerts.ds = new Ext.data.JsonStore({
        url:'widgetrenderer.portal',
        baseParams: {
            did:'<c:out value="${dashboardWidget.dashboardId}"/>',
            wid:'<c:out value="${dashboardWidget.widgetId}"/>',
            dwid:'<c:out value="${dashboardWidget.dashboardWidgetId}"/>',
            viewmode:'<c:out value="${viewmode}"/>',
            action:'jsonData'
        },
        totalProperty: 'total',
        root: 'dataList',
        fields: Bm.Alerts.fieldDefs,
        remoteSort: false
        ,listeners: {
            loadexception: function (proxy, options, response, e) {
                if(isSessionTimeOut(response)){
                    showErrorMsg('<sbm:message key="errorTitle"/>','<sbm:message key="sesstimeouterror"/>',redirectToLogin.createCallback('<c:out value="${isRequestFromBcp}"/>','<%=request.getContextPath()%>'));
                }else{
                    showErrorMsg('<sbm:message key="errorTitle"/>','<sbm:message key="errormsg"/>',Ext.EmptyFn);
                }
            }
        }
    });

    Bm.Alerts.storeListener = function(store, options) {
        if(!Ext.isEmpty(Ext.get('alert_form'))) {
            var fs = Ext.lib.Ajax.serializeForm("alert_form");
            var formValues = Ext.urlDecode(fs);
            Ext.apply(options.params, formValues);

            //set the page value..
            options.params['page'] = ((options.params['start'] / options.params['limit']) + 1) + "";

            //From Date fix
            if(options.params['fromDate'] == undefined || options.params['fromDate'].length <= 0) {
                options.params['fromDate'] = '-1';
            }

            //To Date fix
            if(options.params['toDate'] == undefined || options.params['toDate'].length <= 0) {
                options.params['toDate'] = '-1';
            }

            if(store.getSortState()) {
                options.params['sortColumn'] = store.getSortState().field;
                options.params['sortOrder'] = store.getSortState().direction.toLowerCase();
            } else {
                store.setDefaultSort('alertDate', 'ASC');
            }

        }
        return true;
    };

    Bm.Alerts.ds.on('beforeload', Bm.Alerts.storeListener);
    <%if(PortalUtil.self().getConfig().isWidgetAutoRefresh()){%>
	    if(!Ext.isEmpty(timerColln)){
		    var timerObj = timerColln.getTimer('timer_'+'<c:out value="${dashboardWidget.dashboardWidgetId}"/>'); 
		    if(!Ext.isEmpty(timerObj)){
			    Bm.Alerts.ds.on('load', timerObj.reset);
	        }
    	}
    <% } %>

    Bm.Alerts.alertAppNameRenderer = function(value, metadata, record, rowIndex, colIndex, store){
        return  "<div style='white-space:noWrap' >" + value+"</div>";
    };
    Bm.Alerts.alertAlertNameRenderer = function(value, metadata, record, rowIndex, colIndex, store){
        return  "<div style='white-space:noWrap' >" + value+"</div>";
    };

    Bm.Alerts.alertMessageRenderer = function(value, metadata, record, rowIndex, colIndex, store){
        return  "<div style='white-space:noWrap' >" + value+"</div>";
    };

    Bm.Alerts.alertAlertDateRenderer= function(value, metadata, record, rowIndex, colIndex, store){
        return  "<div style='white-space:noWrap' >" + value+"</div>";
    };

    Bm.Alerts.alertActionRenderer = function(value, metadata, record, rowIndex, colIndex, store){
        var returnValue = "&nbsp;";

        //add forward icon
       // returnValue += "<a href='javascript://' onclick='MM_openBrWindow(\"../common/pop_forward_user_grp_search.jsp?alert_id=" + record.get("alertId") + "\",\"usergroupsearch\",\"scrollbars=yes,resizable=yes,width=900,height=600\")'><img src='../css/<c:out value="${bizManage.theme}" />/images/icon_forward_alert.gif' width='16' height='16' border='0' align='absmiddle' title='<sbm:message key="forward_alerts_title" />'></a>";

        //add email icon
        returnValue += "&nbsp; &nbsp;<a href='mailto:" + record.get("recipient") + "?subject=" + record.get("subject") + "&body=" + record.get("body") + "'><img src='../css/<c:out value="${bizManage.theme}" />/images/icon_email.gif' width='16' height='16' border='0' align='absmiddle' title='<sbm:message key="sendEmailButton" />'></a>";

        /*if((record.get("bm_flag") & 1) == 1 ) {
            var options = record.get("bm_options");
            returnValue += "&nbsp;&nbsp;<a href='psv_chart_view.jsp?PTID=" + options.PTID + "&PIID=" + options.PIID + "&link=status'><img src='../css/<c:out value="${bizManage.theme}" />/images/icon_chart.gif' width='16' height='16' border='0' align='absmiddle' title='<sbm:message key="processStatusViewerTitle" />'></a>";
        } else if ((record.get("bm_flag") & 2)== 2 ) {
            var options = record.get("bm_options");
            returnValue += "&nbsp;&nbsp;<a href='../management/infopad_mgr.jsp?appName=" + options.appName + "&modulename=" + options.moduleName + "&encInfopad=" + options.encInfopad + "'><img src='../css/<c:out value="${bizManage.theme}" />/images/icon_infopad.gif' width='16' height='16' border='0' align='absmiddle' title='<sbm:message key="BPM_Portal_Menu.im" />'></a>";
        } else {
            returnValue += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
        }*/

        return returnValue;
    };

    var alertColumnModel =new Bm.Column.ColumnModel({
        columns : [
            new Ext.grid.RowNumberer({header: i18n_No, menuDisabled: false, hideable: false}),
            <%= columnDefs %>
        ]
    });
    

    var alertListGrid = new Ext.grid.GridPanel({
        id: 'alertList_<c:out value="${dashboardWidget.dashboardWidgetId}"/>',
        store: Bm.Alerts.ds,
        cm: alertColumnModel,
        loadMask: true,
        autowidth: true,
        height: <%=height%>,
        autoScroll: true,
        stripeRows: true,
        viewConfig: {
            autoFill: true,
            emptyText: '<div style="{text-align: center}"><span class="Info infoIcon"><span style="font-size: 9pt; font-weight: bold"><sbm:message key="dashboardWidget.noData" /></span></span><div>'
        }
    });

    alertListGrid.on("mouseover", function(e, t){
        var row_index = alertListGrid.getView().findRowIndex(t);
        var rec = Bm.Alerts.ds.getAt(row_index);
        if(!Ext.isEmpty(rec)) {
           var info_from_record = getAlertsToolTip(rec.get('appName'),
                                        rec.get('alertTitle'),
                                        rec.get('message'));
           Ext.QuickTips.register({
               text: info_from_record,
               trackMouse: true,
               target: e.target
           });
        }
    });

    alertListGrid.on("mouseout", function(e, t){
       Ext.QuickTips.unregister(e.target);
    });


    Ext.onReady(function(){
        <% long currTime = new Date().getTime();%>
        Ext.QuickTips.init();
        alertListGrid.render('alertListDiv_<%=currTime%>');
        Bm.Alerts.ds.load({params:{start: '0', limit: '<%=pgSize %>'}});
    });

    function LoadAlertsList(theForm,divId, dId, wId, dwId,viewmode) {
        if(checkSubmit()) {
            Bm.Alerts.ds.load({
            params:  {
            start: '0',
            limit: '<%=pgSize %>',
            did:dId,
            wid:wId,
            dwid: dwId,
            viewmode: viewmode,
            app:theForm.app.value,
            severity:theForm.severity.value,
            fromDate:theForm.fromDate.value,
            toDate:theForm.toDate.value
            }, add:false
            }) ;
        }
    }

    function setValue(name, value) {
        pwr.util.setValue(name, value);
    }

    function getValue(name) {
        return pwr.util.getValue(name);
    }

    function setAlertID(alert_id){
        if("" == getValue("alert_id")) setValue('alert_id', alert_id);
    }



    function checkSubmit() {
        if(document.alertForm.fromDate.value != '' && !Date.isSBMDateValid(document.alertForm.fromDate.value,'<%=bizManage.getDateFormat()%>'))
        {
            alert('<sbm:message key="StartDateError" params="<%=bizManage.getDateFormat()%>" paramsDelimiter="|"/>');
            document.alertForm.fromDate.value = '';
            document.alertForm.fromDate.focus();
            return false;
        }

        if(document.alertForm.toDate.value != '' && !Date.isSBMDateValid(document.alertForm.toDate.value,'<%=bizManage.getDateFormat()%>'))
        {
            alert('<sbm:message key="EndDateError" params="<%=bizManage.getDateFormat()%>" paramsDelimiter="|"/>');
            document.alertForm.toDate.value = '';
            document.alertForm.toDate.focus();
            return false;
        }

        var longFromDate = sbm.date2Long(document.alertForm.fromDate.value,'<%=bizManage.getJSCalendarDateFormat()%>');
        var longToDate = sbm.date2Long(document.alertForm.toDate.value,'<%=bizManage.getJSCalendarDateFormat()%>');
        if(longFromDate > longToDate) {
            alert('<sbm:message key="StartEndError" />');
            return false;
        }
        return true;
    }

    function getAlertsToolTip(appName, alertTitle, alertMsg){
        if(appName == ''){
            appName = 'ALL';
        }
        var values = {appName:appName,alertTitle:alertTitle, alertMsg:alertMsg};
        var toolTipTemplate = new Ext.Template(
            '<table cellSpacing=0 cellPadding=0 width="100%" border=0>',
              '<tbody>',
                  '<tr>',
                      '<td class=PsvTblInBg><table class="SegDataTbl" cellSpacing="1" cellPadding="4" width="100%" border="0">',
                          '<tbody>',
                              '<tr>',
                                  '<td class="SegFieldRt" nowrap="nowrap" valign="top" width="20%"><sbm:message key="Alert" /></td>',
                                  '<td class=SegValLft>{alertTitle}&nbsp;</td>',
                              '</tr>',
                              '<tr>',
                                  '<td class="SegFieldRt" nowrap="nowrap" valign="top" width="20%"><sbm:message key="Application" /></td>',
                                  '<td class=SegValLft>{appName}&nbsp;</td>',
                              '</tr>',
                              '<tr>',
                                  '<td class="SegFieldRt" nowrap="nowrap" valign="top" width="20%"><sbm:message key="messageLabel" /></td>',
                                  '<td class=SegValLft>{alertMsg}&nbsp;</td>',
                              '</tr>',
                    '</tbody>',
                    '</td></table>',
                '</tr>',
            '</tbody>',
              '</table>');

        return toolTipTemplate.applyTemplate(values);
    }


</script>
<style>
.x-border-layout-ct{ background: #F7FBFF }
.x-grid3-hd-row td{ font-weight: bold; color: #1C417C; text-align: center }
/* This is required to avoid width overflow to 10000px */
/*.x-panel-body {width: 100%;}*/
.x-grid3-header-offset{ width: 100% }
</style>
<form id="alert_form" name="alertForm" method="post" >
<%@ include file="../common/include_form_body.jsp" %>
<input name="userGroup" id="userGroup" type="hidden" value="">
<input name="runtimeAlert" id="runtimeAlert" type="hidden" value="">
<input name="alert_id" id="alert_id" type="hidden" value="">
<input name="user" id="user" type="hidden" value="">
<table border="0" cellpadding="0" cellspacing="0" class="FilterTblEmboss FilterBarTbl">
            <tr>
                <td class="DataLabel" align="left" nowrap="nowrap"><sbm:message key="Application"/>:</td>
                <td class="DataValue">
                    <select class="InptSelect" name="app">
                        <c:choose>
                            <c:when test="${selectedApp == '-1'}">
                                <option value="All" selected="selected"><sbm:message key="TD_AllTask_Key" /></option>
                            </c:when>
                            <c:otherwise>
                                <option value="All"><sbm:message key="TD_AllTask_Key" /></option>
                            </c:otherwise>
                        </c:choose>
                        <c:forEach var="appln" items="${applications}" varStatus="appStatus">
                            <c:choose>
                                <c:when test="${appln == app}">
                                    <option value='<c:out value="${appln}"/>' selected="selected"><c:out value="${appln}"/></option>
                                </c:when>
                                <c:otherwise>
                                    <option value='<c:out value="${appln}"/>'><c:out value="${appln}"/></option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </td>
                <td class="DataLabel" align="left" nowrap="nowrap"><sbm:message key="Severity"/>:</td>
                <td class="DataValue">
                    <select class="InptSelect" name="severity">
                    <option value="All" <%=(selectedSeverity == null) || ("All".equals(selectedSeverity)) ? "selected=selected" : ""%>><sbm:message key="TD_AllTask_Key" /></option>

               <%
                   String priorityLabel;
                   String priority;
                   for(int i=0;i<priorities.size();i++){
                      priorityLabel = bizManage.getResourceString(priorities.get(i));
                      priority = priorities.get(i);
                   %>
                    <option value="<%=priority%>" <%=(selectedSeverity != null) && (priority.equalsIgnoreCase(selectedSeverity)) ? "selected=selected" : ""%>><%=priorityLabel%></option>


               <%    }

                %>
                </select>
                </td>
                </tr><tr>
                <td class="DataLabel" align="left" nowrap="nowrap"><sbm:message key="from"/></td>
                <td class="DataValue">
                    <table border="0" cellspacing="0">
                    <tr>
                        <td><input type="text" class="InptTxt" id="selectedFromDate" name="fromDate" size="18" value="<%=(selectedFromDate != null) && (!"null".equals(selectedFromDate)) ? selectedFromDate : ""%>" /> </td>
                        <td><a href="javascript://"  onclick="sbm.showCalendar('selectedFromDate','<%=bizManage.getJSCalendarDateFormat()%>');" title='<sbm:message key="selectDate"/>'><img src="../css/<c:out value="${bizManage.theme}" />/images/calender.gif" width="16" height="16" border="0" vspace="2" hspace="2"></a></td>
                    </tr>
                    </table>
                </td>
                <td class="DataLabel" align="left" nowrap="nowrap"><sbm:message key="to" /></td>
                <td class="DataValue">
                    <table border="0" cellspacing="0">
                    <tr>
                        <td><input type="text" class="InptTxt" id="selectedToDate" name="toDate" size="18" value="<%=(selectedToDate != null) && (!"null".equals(selectedToDate)) ? selectedToDate : ""%>" /></td>
                        <td><a href="javascript://"  onclick="sbm.showCalendar('selectedToDate','<%=bizManage.getJSCalendarDateFormat()%>');" title='<sbm:message key="selectDate"/>'><img src="../css/<c:out value="${bizManage.theme}" />/images/calender.gif" width="16" height="16" border="0" vspace="2" hspace="2"></a></td>
                    </tr>
                    </table>
                </td>
                <td class="DataValue">
                    <input type="button" name="alertGoButton" value=" <sbm:message key="Search" /> "  class="ResultBtn" onclick="LoadAlertsList(document.alertForm, 'alertsListDiv','<c:out value="${dashboardWidget.dashboardId}"/>','<c:out value="${dashboardWidget.widgetId}"/>','<c:out value="${dashboardWidget.dashboardWidgetId}"/>','<c:out value="${viewmode}"/>')" onmouseover="this.className='ResultBtnHover';" onmouseout="this.className='ResultBtn';" />
                </td>
                <td width="100%"></td>
            </tr>
            </table>

<div id="alertListDiv_<%=currTime%>"></div>
</form>