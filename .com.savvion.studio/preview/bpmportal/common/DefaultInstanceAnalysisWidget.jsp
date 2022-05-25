<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> 
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/bpmportal/tld/bpmportal.tld" prefix="sbm" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.savvion.sbm.bpmportal.bean.UserBean" %>
<%@ page import="com.savvion.acl.impl.SBMACLUser" %>
<%@ page import="com.savvion.sbm.bpmportal.bizsite.api.BizSiteStatus" %>
<%@ page import="com.savvion.sbm.bpmportal.bizsite.api.StatusPageSpec" %>
<%@ page import="com.savvion.sbm.bpmportal.bizsite.util.BizSiteConfiguration" %>
<%@ page import="com.savvion.sbm.bpmportal.util.PortalConstants" %>
<%@ page import="com.savvion.sbm.bpmportal.util.DateTimeUtils" %>
<%@ page import="com.savvion.sbm.bpmportal.util.PortalUtil" %>
<jsp:useBean id="bizManage" class="com.savvion.sbm.bizmanage.api.BizManageBean" scope="session" />
<jsp:useBean id="bizSite" class="com.savvion.sbm.bpmportal.bizsite.api.BizSiteBean" scope="session"/>
<sbm:setLocale value="<%= bizManage.getLocale() %>" />
<sbm:setBundle basename="properties/bpmportal" scope="page" />
<%
    Integer pgSize = (Integer)request.getAttribute("pageSize");
    String height = (String)request.getAttribute("height");
    String width = (String)request.getAttribute("width");
    UserBean userBean = (UserBean) request.getAttribute("userBean");
    BizSiteStatus[] status = (BizSiteStatus[]) request.getAttribute("dataList");
    StatusPageSpec spec = (StatusPageSpec) request.getAttribute("pageSpec");
    SBMACLUser user = (SBMACLUser)session.getAttribute(PortalConstants.SBM_ACL_USEROBJ);
    if (user == null){
        user = SBMACLUser.getSBMACLUser(bizSite.getCurrentUser());
    }
    String auditHistoryUrl = "../myhome/audit_history.portal?view=task";
    long currentTime = new Date().getTime();
    boolean isAppNotSelected = ((spec.getAppName() == null || spec.getAppName().equals("")) ? true : false);
    boolean displayCheckBox = false;

    String fieldDefs = ""; 
    String columnDefs = "";

    fieldDefs += "{name: \"bm_flag\"}, {name: \"PTID\"}, {name: \"PIID\"}, {name: \"piName\"}, {name: \"piEncodedName\"}";

    if (isAppNotSelected && userBean.isColumnViewable(UserBean.APPLICATION))
    {
        if(columnDefs.trim().length() > 0)
            columnDefs += ", ";

        fieldDefs += ", {name: \"application\"}";
        columnDefs += "{dataIndex: \"application\", header: i18n_application_name, renderer: Bm.Instances.instanceWrappedDefaultRenderer}";
    }
    
    if(userBean.isColumnViewable(UserBean.INSTANCE))
    {
        if(columnDefs.trim().length() > 0)
            columnDefs += ", ";

        fieldDefs += ", {name: \"instanceid\"}";
        columnDefs += "{dataIndex: \"instanceid\", header: i18n_instance, renderer: Bm.Instances.instanceWrappedDefaultRenderer}";
    }
    /*
    if(userBean.isColumnViewable(UserBean.TASK))
    {
        if(columnDefs.trim().length() > 0)
            columnDefs += ", ";

        fieldDefs += ", {name: \"task\"}";
        columnDefs += "{dataIndex: \"task\", header: i18n_task, renderer: Bm.Instances.instanceWrappedDefaultRenderer, sortable: false, align:\"center\"}";
    }
    
    if(columnDefs.trim().length() > 0)
        columnDefs += ", ";

    fieldDefs += ", {name: \"performer\"}";
    columnDefs += "{dataIndex: \"performer\", header: i18n_performer, renderer: Bm.Instances.instancePerformerRenderer, align:\"center\"}";
    */
    if((status == null || status.length == 0 || status[0].hasPSVLink(user)) && (userBean.isColumnViewable(UserBean.TABULAR) || userBean.isColumnViewable(UserBean.CHART) || userBean.isColumnViewable(UserBean.AUDITHISTORY) ))
    {
        if(columnDefs.trim().length() > 0)
            columnDefs += ", ";

        columnDefs += "{dataIndex: \"\", header: i18n_details, renderer: Bm.Instances.instanceDetailsRenderer, sortable: false, align:\"center\", width:50}";
    }

    if(userBean.isColumnViewable(UserBean.PRIORITY))
    {
        if(columnDefs.trim().length() > 0)
            columnDefs += ", ";

        fieldDefs += ", {name: \"priority\"}";
        columnDefs += "{dataIndex: \"priority\", header: i18n_priority, renderer: Bm.Instances.instanceDefaultRenderer, align:\"center\", width:50}";
    }
    /*
    if(userBean.isColumnViewable(UserBean.ASSIGNED_DATE))
    {
        if(columnDefs.trim().length() > 0)
            columnDefs += ", ";

        fieldDefs += ", {name: \"assignedDate\"}";
        columnDefs += "{dataIndex: \"assignedDate\", header: i18n_assigned_date, renderer: Bm.Instances.instanceDefaultRenderer, sortable: false, align:\"center\"}";
    }
    */
    if(userBean.isColumnViewable(UserBean.END_DATE))
    {
        if(columnDefs.trim().length() > 0)
            columnDefs += ", ";

        fieldDefs += ", {name: \"duedate\"}";
        columnDefs += "{dataIndex: \"duedate\", header: i18n_dueDate, renderer: Bm.Instances.instanceDueDateRenderer, align:\"center\"}";
    }

%>
<script type="text/javascript">
	var i18n_No = "<sbm:message key="srno" />";
    var i18n_application_name = "<sbm:message key="application" />";
    var i18n_instance = "<sbm:message key="AimInstance" />";
    var i18n_task = "<sbm:message key="Task" />";
    var i18n_performer = "<sbm:message key="performer" />";
    var i18n_details = "<sbm:message key="Details" />";
    var i18n_priority = "<sbm:message key="Priority" />";
    var i18n_assigned_date = "<sbm:message key="assignedDate" />";
    var i18n_dueDate = "<sbm:message key="BPM_Portal_Status.DueDate" />";
    
    Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
        
    var currentPage = 0;
        
    Ext.namespace("Bm.Instances");

    Bm.Instances.fieldDefs = [<%= fieldDefs %>];
    
    Bm.Instances.ds = new Ext.data.JsonStore({
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
        fields: Bm.Instances.fieldDefs
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

   Bm.Instances.storeListener = function(store, options) {
        if(!Ext.isEmpty(Ext.get('InstAnalysis_Form'))) {
            var fs = Ext.lib.Ajax.serializeForm("InstAnalysis_Form");
            var formValues = Ext.urlDecode(fs);

            Ext.apply(options.params, formValues);

            currentPage = (options.params['start'] / options.params['limit']) + 1;
            //set the page value..
            options.params['page'] =  currentPage + "";
            options.params['limit'] = '<%=pgSize%>';

            //Due Date fix
            if(options.params['duedate'] != undefined && options.params['duedate'].length > 0) {
                var longDueDate = sbm.date2Long(options.params['duedate'],'<%=bizManage.getJSCalendarDateFormat()%>');
                options.params['duedate'] = ''+longDueDate;
            } else {
                options.params['duedate'] = '';
            }

            //the column name that server uses is for sorting is 'col'
            if(store.getSortState()) {
                store.getSortState().direction = store.getSortState().direction.toLowerCase();
                options.params['col'] = store.getSortState().field;
            }
        }
        return true;
    };
        
    Bm.Instances.ds.on('beforeload', Bm.Instances.storeListener);
    <%if(PortalUtil.self().getConfig().isWidgetAutoRefresh()){%>
        if(!Ext.isEmpty(timerColln)){
            var timerObj = timerColln.getTimer('timer_'+'<c:out value="${dashboardWidget.dashboardWidgetId}"/>'); 
            if(!Ext.isEmpty(timerObj)){
                Bm.Instances.ds.on('load', timerObj.reset);
            }
        }
    <%}%> 	
    Bm.Instances.instanceDefaultRenderer = function(value, metadata, record, rowIndex, colIndex, store){
        if((record.get("bm_flag") & 1) == 1) //if instance is overdue
           metadata.css = 'RedTxt';
        else 
           metadata.css = 'smlTxt'; 

        return "<div style='white-space:noWrap' title='"+value+"'>"+value+"</div>";
    };

    Bm.Instances.instanceWrappedDefaultRenderer = function(value, metadata, record, rowIndex, colIndex, store){
        if((record.get("bm_flag") & 1) == 1) //if instance is overdue
           metadata.css = 'RedTxt';
        else 
           metadata.css = 'smlTxt'; 

        return "<div title='"+value+"'>"+value+"</div>";
    };

    Bm.Instances.instancePerformerRenderer = function(value, metadata, record, rowIndex, colIndex, store){
        if((record.get("bm_flag") & 1) == 1) //if task is overdue
           metadata.css = 'RedTxt';
                else
           metadata.css = 'smlTxt'; 

        return "<div style='white-space:noWrap' title='"+value+"'><a class='TblLnk' href='../management/mail.jsp?user=" + value + "' target='email'>" + value + "</a></div>";
    };

    Bm.Instances.instanceDueDateRenderer = function(value, metadata, record, rowIndex, colIndex, store){
        if((record.get("bm_flag") & 1) == 1) //if task is overdue
           metadata.css = 'RedTxt';
        else 
           metadata.css = 'smlTxt'; 

        if((record.get("bm_flag") & 1) == 1) //if task is overdue
           return "<div style='white-space:noWrap' title='"+value+"'><img src='../css/<c:out value="${bizManage.theme}"/>/images/icon_overdue.gif' width='16' height='12' border='0'>" + value+"</div>";

        return  "<div style='white-space:noWrap' title='"+value+"'>" + value+"</div>";
    };

    Bm.Instances.instanceDetailsRenderer = function(value, metadata, record, rowIndex, colIndex, store){
        var returnValue = "";
        if((record.get("bm_flag") & 2) == 2)
            returnValue += "<a href='../myhome/psv_table_view.jsp?PTID=" + record.get("PTID") + "&PIID=" + record.get('PIID') + "&piName="+ record.get("piEncodedName") + "&link=status'><img alt='<sbm:message key="TabularToolTip" />' src='../css/<c:out value="${bizManage.theme}" />/images/icon_psv_tabular.gif' width='16' height='16' border='0' title='<sbm:message key="TabularToolTip" />'/></a>&nbsp";  
        else
            returnValue += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";

        if((record.get("bm_flag") & 4) == 4)
            returnValue += "<a href='../myhome/psv_chart_view.jsp?PTID=" + record.get("PTID") + "&PIID=" + record.get('PIID') + "&piName=" + record.get("piEncodedName") + "&link=status'><img alt='<sbm:message key="FlowToolTip" />'  src='../css/<c:out value="${bizManage.theme}" />/images/icon_chart.gif' width='16' height='16' border='0' title='<sbm:message key="FlowToolTip" />'/></a>&nbsp";
                else
            returnValue += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";

        if((record.get("bm_flag") & 8) == 8)
            returnValue += "<a href='<%=auditHistoryUrl%>&PTID=" + record.get("PTID") + "&PIID=" + record.get('PIID') + "&piName=" + record.get("piEncodedName") + "&link=status'><img alt='<sbm:message key="AuditHistory" />' src='../css/<c:out value="${bizManage.theme}" />/images/audit_history.gif' width='16' height='16' border='0' title='<sbm:message key="AuditHistory" />'/></a>";
        else
            returnValue += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";

        return "<div style='white-space:noWrap'>"+returnValue+"</div>";
    };

    var checkBoxSelected = function(sm, rowIndex, rowRecord ) {
        if((rowRecord.get("bm_flag") & 16) == 16){ //has root process instance 
            document.IAForm.Remove.disabled = true;
            document.IAForm.Remove.className = 'ScrnButtonDis';
        }
    };

    var checkBoxDeselected = function(sm, rowIndex) {
        document.IAForm.Remove.disabled = false;
        document.IAForm.Remove.className = 'ScrnButton';

        var selectedRows = sm.getSelections();
        for (var i=0; i<selectedRows.length;i++) {
            if((selectedRows[i]['data']["bm_flag"] & 16) == 16) {
                document.IAForm.Remove.disabled = true;
                document.IAForm.Remove.className = 'ScrnButtonDis';
            }
        }
    };

    var beforeCheckboxSelected = function(sm, rowIndex, keepExisting, rowRecord) {
        if(!keepExisting) {
            sm.selectRow(rowIndex, true);
            return false;
        }
        return true;
    }


    var statusListColumnModel = new Bm.Column.ColumnModel({
         columns : [
            new Ext.grid.RowNumberer({header: i18n_No, menuDisabled: false, hideable: false}),
            <%= columnDefs %>
        ]
    });
    

    var statusListGrid = new Ext.grid.GridPanel({
            id: 'instanceList_<c:out value="${dashboardWidget.dashboardWidgetId}"/>',
            store: Bm.Instances.ds,
            cm: statusListColumnModel,
            autowidth:true,
            height : <%=height%>,
            loadMask: true,
            autoScroll: true,
            stripeRows: true,
            viewConfig: {
                autoFill: true,
                emptyText: '<div style="{text-align: center}"><span class="Info infoIcon"><span style="font-size: 9pt; font-weight: bold"><sbm:message key="dashboardWidget.noData" /></span></span><div>'
            }
    });
    Ext.onReady(function(){
	<% long currTime = new Date().getTime();%>
        statusListGrid.render('instanceListDiv_<%=currTime%>');
        Bm.Instances.ds.load({params:{start: 0, limit: <%=pgSize %>}});
    });

    function onGetGridSettingsSuccessForInstance( result, request) {
        var gridSettings = Ext.util.JSON.decode(result.responseText);
        flds = gridSettings[0];
        cols = gridSettings[1];
        Bm.Instances.ds = new Ext.data.JsonStore({
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
            fields: flds,
            listeners: { 
                loadexception: function (proxy, options, response, e) {                     
                    if(isSessionTimeOut(response)){
                        showErrorMsg('<sbm:message key="errorTitle"/>','<sbm:message key="sesstimeouterror"/>',redirectToLogin.createCallback('<c:out value="${isRequestFromBcp}"/>','<%=request.getContextPath()%>'));
                    }else{
                        showErrorMsg('<sbm:message key="errorTitle"/>','<sbm:message key="errormsg"/>',Ext.EmptyFn);
                    }
                }
            }
        });
        statusListColumnModel = new Bm.Column.ColumnModel(cols);
        Bm.Instances.ds.load({
            params:  {
                start: 0,
                limit: <%=pgSize%>,
                app:document.IAForm.app.value,
                prio:document.IAForm.prio.value,
                duedate:document.IAForm.duedate.value
            }, add:false
        }) ;

        statusListGrid.reconfigure(Bm.Instances.ds, statusListColumnModel);
        statusListGrid.render();
    }

    function LoadInstanceList(theForm,divId, dId, wId, dwId,viewmode) {
        var appName = "";
        if("-1" != theForm.app.value){
            appName = theForm.app.value;
        }
        Ext.Ajax.request({
            url: 'taskwidget.portal',
            success: onGetGridSettingsSuccessForInstance,
            failure: onGetGridSettingsFailure,
            params: {app:appName,action:'getFieldsAndColumnsForInstance'}
        });
    }
   
</script>
<style>
.x-border-layout-ct{ background: #F7FBFF }
.x-grid3-hd-row td{ font-weight: bold; color: #1C417C; text-align: center }
/* This is required to avoid width overflow to 10000px */
/*.x-panel-body {width: 100%;}*/
.x-grid3-header-offset{ width: 100% }
</style>
<form name="IAForm" id="InstAnalysis_Form">
<%@ include file="../common/include_form_body.jsp" %>
<table border="0" cellpadding="0" cellspacing="0" class="FilterTblEmboss FilterBarTbl">
<tr>
    <td class="DataLabel" nowrap><sbm:message key="BPM_Portal_Application.Application" />:</td>
    <td class="DataValue">
      <select class="InptSelect" name="app" >
                <c:choose>
                    <c:when test="${pageSpec.appName == '-1'}">
                        <option value="-1" selected="selected"><sbm:message key="TD_AllTask_Key" /></option>
                    </c:when>
                    <c:otherwise>
                        <option value="-1"><sbm:message key="TD_AllTask_Key" /></option>
                    </c:otherwise>
                </c:choose>
                <c:forEach var="appln" items="${applications}" varStatus="appStatus">
                    <c:choose>
                        <c:when test="${appln == pageSpec.appName}">
                            <option value='<c:out value="${appln}"/>' selected="selected"><c:out value="${appln}"/></option>
                        </c:when>
                        <c:otherwise>
                            <option value='<c:out value="${appln}"/>'><c:out value="${appln}"/></option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select>
    <td width="100%" colspan="4">&nbsp;</td>      
    </tr>
    <tr>
    <td class="DataLabel" nowrap><sbm:message key="Priority" />:</td>
    <td class="DataValue">
      <select class="InptSelect" name="prio">
        <c:choose>
            <c:when test="${pageSpec.priority == '-1'}">
                <option value="-1" selected="selected"><sbm:message key="TD_AllTask_Key" /></option>
            </c:when>
            <c:otherwise>
                <option value="-1"><sbm:message key="TD_AllTask_Key" /></option>
            </c:otherwise>
        </c:choose>
        <c:forEach var="priority" items="${priorities}" varStatus="priorityStatus">
            <c:choose>
                <c:when test="${priority.key == pageSpec.priority}">
                    <option value='<c:out value="${priority.key}"/>' selected="selected"><c:out value="${priority.value}"/></option>
                </c:when>
                <c:otherwise>
                    <option value='<c:out value="${priority.key}"/>'><c:out value="${priority.value}"/></option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </select>
    </td>      
    <td class="DataLabel" nowrap><sbm:message key="BPM_Portal_Status.DueDate" />:</td>
    <td class="DataValue">
        <table border="0" cellspacing="0">
            <tr>
                <td><input type="text" class="InptTxt" id="duedate" name="duedate" size="24" value="<%=(((spec.getDueDate() == null) || "".equals(spec.getDueDate())) ? "" : DateTimeUtils.formatDate(bizManage.getDateFormatter(),Long.valueOf(spec.getDueDate())))%>" /></td>     
                <td><a href="javascript://"  onclick="sbm.showCalendar('duedate','<%=bizManage.getJSCalendarDateFormat()%>');" title='<sbm:message key="selectDate"/>'><img src="../css/<c:out value="${bizManage.theme}" />/images/calender.gif" width="16" height="16" border="0" vspace="2" hspace="2"></a></td>
            </tr>
        </table>
    </td>
     <td class="DataValue">
        <input type="button" name="instanceGoButton" value=" <sbm:message key="Search" /> "  class="ResultBtn" onclick="LoadInstanceList(document.IAForm, 'instanceListDiv','<c:out value="${dashboardWidget.dashboardId}"/>','<c:out value="${dashboardWidget.widgetId}"/>','<c:out value="${dashboardWidget.dashboardWidgetId}"/>','<c:out value="${viewmode}"/>')" onmouseover="this.className='ResultBtnHover';" onmouseout="this.className='ResultBtn';" />
      </td>
      <td width="100%">&nbsp;</td>  
    </tr>
</table>
<div id="instanceListDiv_<%=currTime%>"></div>
</form>
