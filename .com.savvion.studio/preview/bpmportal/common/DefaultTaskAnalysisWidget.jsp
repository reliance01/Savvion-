<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/bpmportal/tld/bpmportal.tld" prefix="sbm" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.savvion.sbm.bpmportal.bean.UserBean" %>
<%@ page import="com.savvion.sbm.bpmportal.bizsite.api.TaskPageSpec" %>
<%@ page import="com.savvion.sbm.bpmportal.bizsite.api.BizSiteTask" %>
<%@ page import="com.savvion.sbm.bpmportal.bizsite.api.BizSiteColumn" %>
<%@ page import="com.savvion.sbm.bpmportal.bizsite.util.BizSiteConfiguration" %>
<%@ page import="com.savvion.sbm.bpmportal.util.PortalConstants" %>
<%@ page import="com.savvion.sbm.bpmportal.util.DashboardConstants" %>
<%@ page import="com.savvion.sbm.bpmportal.util.PortalUtil" %>
<jsp:useBean id="bizManage" class="com.savvion.sbm.bizmanage.api.BizManageBean" scope="session" />
<sbm:setLocale value="<%= bizManage.getLocale() %>" />
<sbm:setBundle basename="properties/bpmportal" scope="page" />
<%
  Integer pgSize = (Integer)request.getAttribute("pageSize");
    String height = (String)request.getAttribute("height");
    String width = (String)request.getAttribute("width");
    BizSiteTask[] bizTasks = (BizSiteTask[]) request.getAttribute("dataList");
    UserBean userBean = (UserBean) request.getAttribute("userBean");
    TaskPageSpec spec = (TaskPageSpec) request.getAttribute("pageSpec");
    boolean isAppNotSelected = ((spec.getAppName() == null || spec.getAppName().equals("")) ? true : false);
    String type = spec.getType();
    boolean displayCheckBox = true;
    StringBuilder docUrlbuf = new StringBuilder();
    
    docUrlbuf.append( BizSiteConfiguration.getDocumentURI("viewdocument.portal",request) );
    docUrlbuf.append("?");
    docUrlbuf.append("docid=");

    StringBuilder columnDefs = new StringBuilder();
    StringBuilder fieldDefs = new StringBuilder();
    fieldDefs.append("{name: \"bm_flag\"}");
    boolean isCollaborative = false;
    boolean hasDisplayColumn = false;
    if(!isCollaborative)
    displayCheckBox = true;

    if (isAppNotSelected && userBean.isColumnViewable(UserBean.APPLICATION))
    {
    if(columnDefs.length() > 0)
        columnDefs.append(", ");

    fieldDefs.append(", {name: \"appName\"}");
    columnDefs.append("{dataIndex: \"appName\", header: i18n_application_name, renderer: Bm.Dashboard.taskDefaultWrappedRenderer}");
    }

    if(userBean.isColumnViewable(UserBean.INSTANCE))
    {
     if(columnDefs.length() > 0)
        columnDefs.append(", ");

    fieldDefs.append(", {name: \"instance\"}");
    columnDefs.append("{dataIndex: \"instance\", header: i18n_instance, renderer: Bm.Dashboard.taskDefaultWrappedRenderer}");
    }

    if(userBean.isColumnViewable(UserBean.TASK))
    {
    if(columnDefs.length() > 0)
        columnDefs.append(", ");

    fieldDefs.append(", {name: \"task\"}, {name: \"bm_taskLink\"}, {name: \"bm_taskId\"}, {name: \"bm_encodedId\"}");
    columnDefs.append("{dataIndex: \"task\", header: i18n_task, renderer: Bm.Dashboard.taskNameRenderer}");
    }
    /*
    if(userBean.isColumnViewable(UserBean.CREATOR))
    {
    if(columnDefs.length() > 0)
        columnDefs.append(", ");

    fieldDefs.append(", {name: \"creator\"}");
    columnDefs.append("{dataIndex: \"creator\", header: i18n_creator, renderer: Bm.Dashboard.taskDefaultRenderer, align:\"center\"}");
    }*/

    if(userBean.isColumnViewable(UserBean.PRIORITY))
    {
    if(columnDefs.length() > 0)
        columnDefs.append(", ");

    fieldDefs.append(", {name: \"priority\"}");
    columnDefs.append("{dataIndex: \"priority\", header: i18n_priority, renderer: Bm.Dashboard.taskDefaultRenderer, align:\"center\"}");
    }

    if (hasDisplayColumn && bizTasks.length > 0 && !"completed".equals(type))
    {
    BizSiteColumn dataCol = bizTasks[0].getDataColumn();
    while (dataCol != null && dataCol.next())
    {
        if((PortalConstants.DataSlot.MAP.getType().equals(dataCol.getType()) || PortalConstants.DataSlot.LIST.getType().equals(dataCol.getType())))
            continue;

        if(columnDefs.length() > 0)
            columnDefs.append(", ");

        fieldDefs.append(", {name: \"" + dataCol.getId() + "\"}");
        if(PortalConstants.DataSlot.URL.getType().equals(dataCol.getType()))
            columnDefs.append("{dataIndex: \"").append(dataCol.getId()).append("\", header: \"").append(bizManage.getDataslotLabel(spec.getAppName(), dataCol.getId())).append("\", renderer: Bm.Dashboard.taskURLDataSlotRenderer, align:\"center\" }");
        else if(PortalConstants.DataSlot.XML.getType().equals(dataCol.getType()))
            columnDefs.append("{dataIndex: \"").append(dataCol.getId()).append("\", header: \"").append(bizManage.getDataslotLabel(spec.getAppName(), dataCol.getId())).append("\", renderer: Bm.Dashboard.taskXMLDataSlotRenderer, align:\"center\" }");
        else if(PortalConstants.DataSlot.DOCUMENT.getType().equals(dataCol.getType()))
            columnDefs.append("{dataIndex: \"").append(dataCol.getId()).append("\", header: \"").append(bizManage.getDataslotLabel(spec.getAppName(), dataCol.getId())).append("\", renderer: Bm.Dashboard.taskDocumentDataSlotRenderer, align:\"center\" }");
        else
            columnDefs.append("{dataIndex: \"").append(dataCol.getId()).append("\", header: \"").append(bizManage.getDataslotLabel(spec.getAppName(), dataCol.getId())).append("\", renderer: Bm.Dashboard.taskDefaultRenderer, align:\"center\"}");
    }
    }

    if(userBean.isColumnViewable(UserBean.ASSIGNED_DATE))
    {
    if(columnDefs.length() > 0)
        columnDefs.append(", ");

    fieldDefs.append(", {name: \"assignedDate\"}");
    columnDefs.append("{dataIndex: \"assignedDate\", header: i18n_assigned_date, align: \"center\", renderer: Bm.Dashboard.taskDefaultRenderer}");
    }

    if(userBean.isColumnViewable(UserBean.END_DATE))
    {
    if(columnDefs.length() > 0)
        columnDefs.append(", ");

    fieldDefs.append(", {name: \"duedate\"}");
    columnDefs.append("{dataIndex: \"duedate\", header: i18n_due_date, align: \"center\", renderer: Bm.Dashboard.taskDueDateRenderer}");
    }

    String statusStr = spec.getType();
    if("assigned".equals(statusStr)) {
    statusStr = "I_ASSIGNED";
    } else if("available".equals(statusStr)) {
    statusStr = "I_AVAILABLE";
    } else if("completed".equals(statusStr)) {
    statusStr = "I_COMPLETED";
    }
    request.setAttribute("selectedStatus", statusStr);
%>
<script type="text/javascript">
	var i18n_No = "<sbm:message key="srno" />";
    var i18n_application_name = "<sbm:message key="BPM_Portal_Menu.application"/>";
    var i18n_instance = "<sbm:message key="BPM_Portal_Task.Instance" />";
    var i18n_task = "<sbm:message key="BPM_Portal_Task.Task" />";
    var i18n_creator = "<sbm:message key="TD_CreatedBy_Key" />";
    var i18n_priority = "<sbm:message key="TD_Priority_Key" />";
    var i18n_assigned_date = "<sbm:message key="BPM_Portal_Task.AssignedDate" />";
    var i18n_due_date = "<sbm:message key="BPM_Portal_Status.DueDate" />";
    
    Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

    Ext.namespace("Bm.Dashboard");

    Bm.Dashboard.fieldDefs = [<%=fieldDefs.toString()%>];

    Bm.Dashboard.ds = new Ext.data.JsonStore({
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
            fields: Bm.Dashboard.fieldDefs
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

        Bm.Dashboard.storeListener = function(store, options) {
            if(!Ext.isEmpty(Ext.get('TaskAnalysis_Form'))) {
                var fs = Ext.lib.Ajax.serializeForm("TaskAnalysis_Form");
                var formValues = Ext.urlDecode(fs);
                Ext.apply(options.params, formValues);
                
                //set the page value.. - need to convert the following variable into string..
                options.params['page'] = ((options.params['start'] / options.params['limit']) + 1) + "";
                options.params['limit'] = '<%=pgSize%>';
                    
                //the column name that server uses is for sorting is 'col'
                /*if(store.getSortState()) {
                  store.getSortState().direction = store.getSortState().direction.toLowerCase();
                    options.params['col'] = store.getSortState().field;
                }*/
            }
            return true;
        };
        
        Bm.Dashboard.ds.on('beforeload', Bm.Dashboard.storeListener);
        <%if(PortalUtil.self().getConfig().isWidgetAutoRefresh()){%>
            if(!Ext.isEmpty(timerColln)){
                var timerObj = timerColln.getTimer('timer_'+'<c:out value="${dashboardWidget.dashboardWidgetId}"/>'); 
                if(!Ext.isEmpty(timerObj)){
                    Bm.Dashboard.ds.on('load', timerObj.reset);
                }
            }
        <%}%>


    Bm.Dashboard.taskDefaultWrappedRenderer = function(value, metadata, record, rowIndex, colIndex, store){
        if((record.get("bm_flag") & 1) == 1) //if task is overdue
           metadata.css = 'RedTxt';
        else 
           metadata.css = 'smlTxt'; 

        return "<div title='"+value+"'>"+value+"</div>";
    };

    Bm.Dashboard.taskDefaultRenderer = function(value, metadata, record, rowIndex, colIndex, store){
        if((record.get("bm_flag") & 1) == 1) //if task is overdue
           metadata.css = 'RedTxt';
        else 
           metadata.css = 'smlTxt'; 

        return "<div style='white-space:noWrap' title='"+value+"'>"+value+"</div>";
    };

    Bm.Dashboard.taskNameRenderer = function(value, metadata, record, rowIndex, colIndex, store){
        if((record.get("bm_flag") & 1) == 1) //if task is overdue
           metadata.css = 'RedTxt';
        else 
           metadata.css = 'smlTxt'; 

        if((record.get("bm_flag") & 8) == 8) //if task is completed
           return "<div title='"+value+"'>"+value+"#"+record.get("bm_taskId")+"</div>";

        return "<div title='"+value+"'><a href='" + record.get("bm_taskLink") + "' class='ActionLnk' title='"+value+"'>" + value +"#"+record.get("bm_taskId")+"</a></div>";
    };

    Bm.Dashboard.taskDueDateRenderer = function(value, metadata, record, rowIndex, colIndex, store){
        if((record.get("bm_flag") & 1) == 1) //if task is overdue
           metadata.css = 'RedTxt';
        else 
           metadata.css = 'smlTxt'; 

        if((record.get("bm_flag") & 1) == 1) //if task is overdue
           return "<div style='white-space:noWrap' title='"+value+"'><img src='../css/<c:out value="${bizManage.theme}"/>/images/icon_overdue.gif' width='16' height='12' border='0'>" + value+"</div>";

        return  "<div style='white-space:noWrap' title='"+value+"'>" + value+"</div>";
    };

    Bm.Dashboard.taskURLDataSlotRenderer = function(value, metadata, record, rowIndex, colIndex, store){
        if((record.get("bm_flag") & 1) == 1) //if task is overdue
           metadata.css = 'RedTxt';
        else 
           metadata.css = 'smlTxt'; 

        var longStr = value;
        if(longStr.indexOf("www") == 0) 
            longStr = "http://" + longStr;

        var shortStr = longStr;
        if(shortStr.length > 15) {
            shortStr = shortStr.substr(0, 15);
            shortStr += "...";
        }

        return "<div style='white-space:noWrap' title='"+value+"'><a href='#' class='ActionLnk' title='" + longStr + "' onclick=\"MM_openBrWindow('" + longStr + "','SetPagingPopup','resizable=yes,width=750,height=524')\">" + shortStr + "</a></div>";
    };

    Bm.Dashboard.taskXMLDataSlotRenderer = function(value, metadata, record, rowIndex, colIndex, store){
        if((record.get("bm_flag") & 1) == 1) //if task is overdue
           metadata.css = 'RedTxt';
        else 
           metadata.css = 'smlTxt'; 

        return "<div style='white-space:noWrap' title='"+value+"'><a href='#' class='ActionLnk' onclick=\"MM_openBrWindow('../../common/pop_view_xml.jsp?task=" + record.get("bm_encodedId") + "&ds=" + value + "','SetPagingPopup','resizable=yes,width=750,height=524')\"><sbm:message key="View" />... </a></div>";
    };

    Bm.Dashboard.taskDocumentDataSlotRenderer = function(value, metadata, record, rowIndex, colIndex, store){
        if((record.get("bm_flag") & 1) == 1) //if task is overdue
           metadata.css = 'RedTxt';
        else 
           metadata.css = 'smlTxt'; 

        var returnValue = "";
        if(Ext.isArray(value) && value.length > 0) {
           for(var i=0;i<value.length;i++) {
                if(i != 0)
                    returnValue += "<br/>"
                returnValue += "<a href='<%=docUrlbuf.toString()%>" + value[i]['id'] +"' target='_blank'>" + value[i]['name'] + "</a>";
           }
        }
        return "<div style='white-space:noWrap' title='"+returnValue+"'>"+returnValue+"</div>";
    };

    Bm.Dashboard.makeAvailableRenderer = function(value, metadata, record, rowIndex, colIndex, store){
        var returnValue = "";

        if((record.get("bm_flag") & 2) == 2)
            returnValue += "<span class=\'makeAvailableIcon\'/>";
        else 
            returnValue += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";

        if((record.get("bm_flag") & 4) == 4)
            returnValue += "<span class=\'collaborativeIcon\'/>";
        else
            returnValue += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
        return "<div style='white-space:noWrap' title='"+returnValue+"'>"+returnValue+"</div>";
    };

    var checkBoxSelected = function(sm, rowIndex, rowRecord ) {

        var canMakeAvailable = rowRecord.get("bm_flag") & 2;
        var canBulkCompleted = rowRecord.get("bm_flag") & 16;

        if(!canMakeAvailable && document.TAForm.MakeAvailable){ 
            document.TAForm.MakeAvailable.disabled = true;
            document.TAForm.MakeAvailable.className = 'ScrnButtonDis';
        }

        if(!canBulkCompleted){  
            document.TAForm.Complete.disabled = true;
            document.TAForm.Complete.className = 'ScrnButtonDis';
        }
    };

    var taskListColumnModel = new Bm.Column.ColumnModel({
        columns : [
            new Ext.grid.RowNumberer({header: i18n_No, menuDisabled: false, hideable: false}),
            <%= columnDefs.toString() %>
        ]
    });
    
    
    var taskListGrid = new Ext.grid.GridPanel({
            id: 'taskList_<c:out value="${dashboardWidget.dashboardWidgetId}"/>',
            store: Bm.Dashboard.ds,
            cm: taskListColumnModel,
            autoWidth:true,
            height : <%=height%>,
            loadMask: true,
            stripeRows: true,
            autoScroll: true,
            viewConfig: {
                autoFill: true,
                emptyText: '<div style="{text-align: center}"><span class="Info infoIcon"><span style="font-size: 9pt; font-weight: bold"><sbm:message key="dashboardWidget.noData" /></span></span><div>'
            }
    });
    Ext.onReady(function(){
        <% long  currTime = new Date().getTime();%>

                taskListGrid.render('taskListDiv_<%=currTime%>');
                Bm.Dashboard.ds.load({params:{start: 0, limit: <%=pgSize %>}});
    });

    function onGetGridSettingsFailure(){
        Ext.Msg.alert('Error', ErrorMsg);
    }

    function onGetGridSettingsSuccessForTasks( result, request) {
        var gridSettings = Ext.util.JSON.decode(result.responseText);
        flds = gridSettings[0];
        cols = gridSettings[1];
        Bm.Dashboard.ds = new Ext.data.JsonStore({
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
        taskListColumnModel = new Bm.Column.ColumnModel(cols);
        Bm.Dashboard.ds.load({
            params:  {
                start: 0,
                limit: <%=pgSize%>,
                app:document.TAForm.app.value,
                type:document.TAForm.type.value,
                prio:document.TAForm.prio.value
            }, add:false
        }) ;

        taskListGrid.reconfigure(Bm.Dashboard.ds, taskListColumnModel);
        taskListGrid.render();
    }

    function LoadTaskList(theForm,divId, dId, wId, dwId,viewmode) {
        var appName = "";
        if("-1" != theForm.app.value){
            appName = theForm.app.value;
        }
        Ext.Ajax.request({
            url: 'taskwidget.portal',
            success: onGetGridSettingsSuccessForTasks,
            failure: onGetGridSettingsFailure,
            params: {app:appName,action:'getFieldsAndColumns'}
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
<form name="TAForm" id="TaskAnalysis_Form">
<%@ include file="../common/include_form_body.jsp" %>
<table border="0" cellpadding="0" cellspacing="0" class="FilterTblEmboss FilterBarTbl">
<tr>
    <td class="DataLabel" nowrap><sbm:message key="BPM_Portal_Application.Application" />:</td>
    <td class="DataValue">
            <select class="InptSelect" name="app" id="app">
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
    <td class="DataLabel" nowrap><sbm:message key="BPM_Portal_Task.TaskStatus" />:</td>
    <td class="DataValue">
            <select class="InptSelect" name="type" id="type">
                <c:forEach var="stat" items="${status}" varStatus="statusIndex">
                    <c:choose>
                        <c:when test="${stat.key == selectedStatus}">
                            <option value='<c:out value="${stat.key}"/>' selected="selected"><c:out value="${stat.value}"/></option>
                        </c:when>
                        <c:otherwise>
                            <option value='<c:out value="${stat.key}"/>'><c:out value="${stat.value}"/></option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select>
    </td>
    <td class="DataLabel" nowrap><sbm:message key="Priority" />:</td>
    <td class="DataValue">
     <select class="InptSelect" name="prio" id="prio">
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
     <td class="DataValue">
            <input type="button" name="SelectQuery" value=" <sbm:message key="Search" /> "  class="ResultBtn" onclick="LoadTaskList(document.TAForm, 'taskListDiv','<c:out value="${dashboardWidget.dashboardId}"/>','<c:out value="${dashboardWidget.widgetId}"/>','<c:out value="${dashboardWidget.dashboardWidgetId}"/>','<c:out value="${viewmode}"/>')" onmouseover="this.className='ResultBtnHover';" onmouseout="this.className='ResultBtn';" />
      </td>
      <td width="100%">&nbsp;</td>  
    </tr>
</table>
<div id='taskListDiv_<%=currTime%>'></div>
</form>
