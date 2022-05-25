Ext.namespace("Bm.Tasks");

Ext.namespace("Bm.Tasks.Status");
Bm.Tasks.Status.Assigned = "assigned";
Bm.Tasks.Status.Available = "available";
Bm.Tasks.Status.Completed = "completed";
Bm.Tasks.Status.DelegatedAssigned = "delegatedassigned";
Bm.Tasks.Status.DelegatedAvailable = "delegatedavailable";
Bm.Tasks.Status.Collaborative = "collaborativeassigned";

        Bm.Tasks.taskDefaultRenderer = function(value, metadata, record, rowIndex, colIndex, store){
            if((record.get("bm_flag") & 1) == 1) //if task is overdue
               metadata.tdCls = 'RedTxtNoWrap';
            else
               metadata.tdCls = 'SmlTxtNoWrap';
            metadata.tdAttr = 'data-qtip="' + value + '"';
            return value;
        };


        Bm.Tasks.taskDueDateRenderer = function(value, metadata, record, rowIndex, colIndex, store){
            if((record.get("bm_flag") & 1) == 1) //if task is overdue
               metadata.tdCls = 'RedTxtNoWrap';
            else
               metadata.tdCls = 'SmlTxtNoWrap';

             metadata.tdAttr = 'data-qtip="' + value + '"';
             
            if((record.get("bm_flag") & 1) == 1) //if task is overdue
                return "<span class='BmIconOverdueImg'></span>" + value; 
                
            return  "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + value;
        };

        Bm.Tasks.taskURLDataSlotRenderer = function(value, metadata, record, rowIndex, colIndex, store){
            if((record.get("bm_flag") & 1) == 1) //if task is overdue
               metadata.tdCls = 'RedTxt';
            else
               metadata.tdCls = 'SmlTxt';

            var longStr = value;
            if(longStr.indexOf("www") == 0)
                longStr = "http://" + longStr;
            /*
            Fix for CR#RPM00043327 If more number of characters adjust the column content in the given column width by Ext JS itself no need to handle separately.
            var shortStr = longStr;
            if(shortStr.length > 15) {
                shortStr = shortStr.substr(0, 15);
                shortStr += "...";
            }
            */
            metadata.tdAttr = 'data-qtip="' + value + '"';
            return "<a href='#' class='ActionLnk' title='" + longStr + "' onclick=\"MM_openBrWindow('" + longStr + "','SetPagingPopup','resizable=yes,width=750,height=524')\">" + longStr + "</a>";
        };

        Bm.Tasks.taskXMLDataSlotRenderer = function(value, metadata, record, rowIndex, colIndex, store){
            if((record.get("bm_flag") & 1) == 1) //if task is overdue
               metadata.tdCls = 'RedTxt';
            else
               metadata.tdCls = 'SmlTxt';
            var view = getLocalizedString("View");
            metadata.tdAttr = 'data-qtip="' + view + '"';
			var dsLabel = '';
			var taskName = record.get('task') + "#" + record.get("bm_taskId");
			if(Ext.util.Format.lowercase(Bm.Tasks.status) == 'completed' || Ext.util.Format.lowercase(Bm.Tasks.status) == 'collaborativeassigned'){
               dsLabel = Bm.data.ColumnModel[colIndex].header;
            } else {
                dsLabel = Bm.data.ColumnModel[colIndex-1].header;
            }
			return "<a href='#' class='ActionLnk' onclick=\"javascript:showXmlPopup('"+ value +"','"+dsLabel+"','piName','"+record.get('instance')+"','"+taskName+"')\">"+view+"</a>";
			
        };

        Bm.Tasks.taskDocumentDataSlotRenderer = function(value, metadata, record, rowIndex, colIndex, store){
            var dsLabel = '';
            var returnValue = '';
            var taskName = record.get('task') + "#" + record.get("bm_taskId");

            if(Ext.util.Format.lowercase(Bm.Tasks.status) == 'completed' || Ext.util.Format.lowercase(Bm.Tasks.status) == 'collaborativeassigned'){
               dsLabel = Bm.data.ColumnModel[colIndex].header;
            } else {
                dsLabel = Bm.data.ColumnModel[colIndex-1].header;
            }
            var view = getLocalizedString("View");
            metadata.tdAttr = 'data-qtip="' + view + '"';
            returnValue = "<a href='#' class='ActionLnk' onclick=\"javascript:showDocuments('"+ value +"','"+record.get('instance')+"','"+ taskName +"','"+ dsLabel +"')\">"+view+" </a>";
            return returnValue;
        };

        Bm.Tasks.makeAvailableRenderer = function(value, metadata, record, rowIndex, colIndex, store){
            var returnValue = "";
            if((record.get("bm_flag") & 2) == 2)
                returnValue += "<span class=\'makeAvailableIcon\'>&nbsp;</span>";
            else
                returnValue += "&nbsp;&nbsp;";

            if((record.get("bm_flag") & 4) == 4)
                returnValue += "<span class=\"collaborativeIcon\">&nbsp;</span>";
            else
                returnValue += "&nbsp;&nbsp;";
            return returnValue;
        };

        var getButtonEnableCfgOnDeselect = function(sm) {
            /*
                On Deselection, buttons can be shown only if other
                Tasks have them enabled.
            */
            var selectedRows = sm.getSelection();
            var enableMakeAvailable = true;
            for (var i=0; i<selectedRows.length;i++) {
                var canMakeAvailable = selectedRows[i]['data']["canBeMadeAvailable"];
                //If any of the tasks have makeAvailable button hidden, it won't be shown
                if(!canMakeAvailable) {
                   enableMakeAvailable = false;
                }
            }

            return {
                enableMakeAvailable : enableMakeAvailable
            };
        }

        var toggleButtonOnSelect = function(canMakeAvailable) {
            /*
              On Task Selection, if any of the buttons is hidden
              already due to selection of any other tasks, it should not be shown.
            */
            var makeAvailBtn = Ext.getCmp('makeAvailableBtn');
            if(makeAvailBtn) {
                /*
                    If makeAvailable button is hidden, avoid showing it
                */
                if(makeAvailBtn.hidden) {
                   canMakeAvailable = false;
                }
            }
            showOrHideButtons(canMakeAvailable);
        }

        var checkBoxSelected = function(sm, rowIndex, rowRecord ) {
            selectColHeaderChkBox(sm);
            var canMakeAvailable = rowRecord.get("canBeMadeAvailable");
            toggleButtonOnSelect(canMakeAvailable);
        };

        var showOrHideButtons = function(canMakeAvailable) {
            var makeAvailBtn = Ext.getCmp('makeAvailableBtn');
            if(makeAvailBtn){
                if(!canMakeAvailable) {
                    makeAvailBtn.hide();
                } else {
                    makeAvailBtn.show();
                }
            }
        }

        var checkBoxDeselected = function(sm, rowIndex) {
            deselectColHeaderChkBox(sm);
            var cfg = getButtonEnableCfgOnDeselect(sm);
            showOrHideButtons(cfg.enableMakeAvailable);
        };

        var beforeCheckboxSelected = function(sm, rowIndex, keepExisting, rowRecord) {
            if(!keepExisting) {
                //Commented since this fires row selection even when user clicks for Task details page
                //sm.selectRow(rowIndex, true);
                return false;
            }
            return true;
        };

        var checkBoxSelectionChange = function(sm, rowRecordArray, rowIndex) {
            var canMakeAvailable;
            if(!Ext.isEmpty(rowRecordArray) &&  rowRecordArray.length == 1){  // Single Row (Select and DeSelect)
                canMakeAvailable = rowRecordArray[0].get("canBeMadeAvailable");
            } else {  // Multiple Rows (SelectAll and DeSelectAll)
                var cfg = getButtonEnableCfgOnDeselect(sm);
                canMakeAvailable = cfg.enableMakeAvailable;
            }
            showOrHideButtons(canMakeAvailable);
        };

Ext.namespace("Bm.data");
Bm.data.ColumnModel = {};
Bm.data.FieldDef = new Array();
Bm.Tasks.status = '';

var prepareFieldsAndCols = function(response,request,isLocalStore) {
    var headerJson = Ext.decode(response.responseText);
    var headerInfo = headerJson.header;
    var status = request.params.status;
    Bm.Tasks.status = status;
    var hideEndDate = true;
    var hideApplnCol = Ext.isEmpty(request.params.templateName) ? false : true;
    var hideInstanceCol = false;
    var hideTaskCol = false;
    var hideCreatorCol = false;
    var hidePriorityCol = false;
    var hideAssgnDateCol = false;

    var headerArray = new Array();
    //headerArray.push(new Ext.ux.grid.PagingRowNumberer({header: getLocalizedString('srno'), menuDisabled: false, hideable: false, resizable: true, fixed: false})); // TODO :: collaborative
    //Fix for TR#RPM00035898 - Serial number column width is increased to show four digits properly.
    headerArray.push({xtype : 'rownumberer', id: 'srNo', dataIndex: 'srNo', header: getLocalizedString('srno'), menuDisabled: false, hideable: false,  minWidth: 35, maxWidth: 35}); // TODO :: collaborative
    if(Ext.util.Format.lowercase(status) == Bm.Tasks.Status.Assigned){
        var colWidth;
        if (Ext.isIE) {
            colWidth = 53;
        } else {
            colWidth = 42;
        }
        headerArray.push({name:"bm_flag",header:"<span class=\'makeAvailableIcon\'>&nbsp;</span>",sortable:false, width: colWidth, minWidth: colWidth,maxWidth: colWidth, renderer : Bm.Tasks.makeAvailableRenderer , menuDisabled: true, hideable: false, resizable:false});
    }
    if(Ext.util.Format.lowercase(status) == Bm.Tasks.Status.Completed){
        hideEndDate = false;
    }
    var hideDueDate = !hideEndDate;

    if( headerJson.UIDATALIST != undefined && headerJson.UIDATALIST != null) {
        var hiddenColArr = headerJson.UIDATALIST["hiddenColumns"];

        for(var i = 0; i< hiddenColArr.length; i++){
            if("application" == Ext.util.Format.lowercase(hiddenColArr[i])) {
                hideApplnCol = true;
            } else if("instance" == Ext.util.Format.lowercase(hiddenColArr[i])) {
                hideInstanceCol = true;
            } else if("task" == Ext.util.Format.lowercase(hiddenColArr[i])) {
                hideTaskCol = true;
            } else if("creator" == Ext.util.Format.lowercase(hiddenColArr[i])) {
                hideCreatorCol = true;
            } else if("priority" == Ext.util.Format.lowercase(hiddenColArr[i])) {
                hidePriorityCol = true;
            } else if("assigneddate" == Ext.util.Format.lowercase(hiddenColArr[i])) {
                hideAssgnDateCol = true;
            } else if("enddate" == Ext.util.Format.lowercase(hiddenColArr[i])) {
                if(Ext.util.Format.lowercase(status) == Bm.Tasks.Status.Completed) {
                    hideEndDate = true;
                } else {
                    hideDueDate = true;
                }
            }
        }
    }

    if(isLocalStore) {
        Bm.data.ColumnModel = Bm.grid.ColumnModel.createColumnModel(headerInfo, headerArray, [
                {"name":"appName","sortable":true,"hidden":hideApplnCol,width :100,"renderer" : Bm.Tasks.taskDefaultRenderer },
                {"name":"instance","sortable":true,"hidden":hideInstanceCol,width : 150, "renderer" : Bm.Tasks.taskDefaultRenderer},
                {"name":"task","sortable":true,"hidden":hideTaskCol,width :150, "renderer" : Bm.Tasks.taskNameRenderer},
                {"name":"creator","sortable":true,"hidden":hideCreatorCol,width : 100, "renderer" : Bm.Tasks.taskDefaultRenderer},
                {"name":"priority","sortable":true,"hidden":hidePriorityCol,width : 50, "renderer" : Bm.Tasks.taskDefaultRenderer},
                {"name":"assignedDate","sortable":false,"hidden":hideAssgnDateCol,width : 100, "renderer" : Bm.Tasks.taskDefaultRenderer} ,
                {"name":"duedate","hidden":hideDueDate,"sortable":false,width : 100, "renderer" : Bm.Tasks.taskDueDateRenderer},
                {"name":"endDate","hidden":hideEndDate,"sortable":false,width : 100, "renderer" : Bm.Tasks.taskDueDateRenderer}
            ]);
    } else {
        Bm.data.ColumnModel = Bm.grid.ColumnModel.createColumnModel(headerInfo, headerArray, [
                {"name":"appName","sortable":true,"hidden":hideApplnCol,width :150,minWidth:110,"renderer" : Bm.Tasks.taskDefaultRenderer},
                {"name":"instance","sortable":true,"hidden":hideInstanceCol,width : 160, "renderer" : Bm.Tasks.taskDefaultRenderer},
                {"name":"task","sortable":true,"hidden":hideTaskCol,width :160, "renderer" : Bm.Tasks.taskNameRenderer},
                {"name":"creator","sortable":true,"hidden":hideCreatorCol,width : 100, minWidth:80, "renderer" : Bm.Tasks.taskDefaultRenderer},
                {"name":"priority","sortable":true,"hidden":hidePriorityCol,width : 80, minWidth:80, "renderer" : Bm.Tasks.taskDefaultRenderer},
                {"name":"assignedDate","sortable":true,"hidden":hideAssgnDateCol,width : 160, "renderer" : Bm.Tasks.taskDefaultRenderer} ,
                {"name":"duedate","hidden":hideDueDate,"sortable":true,width : 160, "renderer" : Bm.Tasks.taskDueDateRenderer},
                {"name":"endDate","hidden":hideEndDate,"sortable":true,width : 160, "renderer" : Bm.Tasks.taskDueDateRenderer}
            ]);
    }
    Bm.data.ColumnModel.defaultSortable = true;

    Bm.data.FieldDef = Bm.data.JsonReader.getFields(headerInfo, []);

}

function refreshGrid(config){
    var params = config.params;
    if(params){
        var appName = params.application;
        var templateName = params.templateName;
        var status = params.status;
		var params = {'application':appName,'templateName' : templateName,'status' : status, 'action':'getHeader'};
		params = prepareURLParams(params, getTaskFilterForm());
        Ext.Ajax.request({
            url: '../myhome/bmTaskSearch.portal',method:'GET',
            success : function( result, request){
                prepareFieldsAndCols( result, request, Bm.util.isLocalStore);
                if(!(Bm.util.isLocalStore)) {
                    Bm.data.store = Ext.create('Ext.data.Store',{
							proxy : {
								type: 'ajax',
								url : '../myhome/bmTaskSearch.portal',
								actionMethods: 'POST',
								reader : {
									type: 'json',
									root : 'data',
									totalProperty : 'count'
								}
							},
                            fields: Bm.data.FieldDef,
                            remoteSort: true,
                            pageSize:Bm.util.pageSize,
                            listeners: {
                                'load': function(store, records, successful, operation, eOpts){
                                        
                                        disableButtons(false);
                                },
                                'exception' : function( store, type, action, options, response, arg) {
                                        handleStoreLoadException(store, type, action, options, response, arg);
                                        disableButtons(false);
                                },
                                datachanged : function(store){
                                    var pageCount = Math.ceil(store.getTotalCount() / store.pageSize);
                                    if(pageCount <= 0){
                                        pageCount = 1
                                    }
                                    if(store.currentPage > pageCount){
                                        store.currentPage = pageCount;
                                        var start = (pageCount - 1) * store.pageSize;
                                        var i = 0;
                                        store.each( function (record) {
                                            record.index = start + i;
                                            i++;
                                        })
                                    }
                                }
                            }
                        });

                } else {
                    Bm.data.store = new Ext.ux.data.PagingJsonStore({
                    url:'../myhome/bmTaskSearch.portal',
                    root : 'data',
                    totalProperty: 'count',
                    fields : Bm.data.FieldDef,
                    remoteSort: false,
                    pageSize:Bm.util.pageSize,
                    listeners: {
                        'load': function(){
                                disableButtons(false);
                        },
                        'exception' : function( store, type, action, options, response, arg) {
                                handleStoreLoadException(store, type, action, options, response, arg);
                                disableButtons(false);
                        }
                    }
                    });
                }

                var reconfig = false;
                if(params.action == "Search"){
                    //myTasksSortColumn = "duedate";
                    //myTasksSortDirection = "ASC";
                    reconfig = true;
                }

                Bm.data.store.on('beforeload',storeListener);
                Bm.data.store.on('beforeload',disableButtons);
				var appLabelOrValue = Ext.getCmp('application').getLabel();
				var templateLabelOrValue = Ext.getCmp('templateName').getLabel();
                var titlePrefix = Bm.grid.GridPanel.getTitlePrefix(appLabelOrValue, templateLabelOrValue);				
                var taskListGrid = createTaskGrid(reconfig, titlePrefix);
                Bm.data.store.load({params : {start:0,limit:params.myTasksPageSize},
                    scope   : this
                    ,callback: function(records, operation, success) {
                        //This method will create or recreate the task grid.
                        //createTaskGrid();
                        //FIXME - doLayout
                        //getCenterContainer().doLayout();
						var offset={x:0,y:0};
						GetOffset(document.getElementById(taskListGrid.id),offset);
						var tempheight = document.getElementById('southContainer-outerCt').offsetHeight || 0;	
						if(offset.y!=NaN)
						{
							 var ht=window.innerHeight-offset.y-tempheight-5;
							 if(ht<150)
							 {
								taskListGrid.setHeight(150);
							 }
							 else
							 {
								taskListGrid.setHeight(ht);
							 }
						}
                        //To avoid vertical scroll bar for grid in IE7.  
                        //if(!taskListGrid.forceFit){
	            		//   var viewDivElem = taskListGrid.getView().getEl().dom;
	            		   //Check whether vertical scroll bar appeared
	            	       //if(viewDivElem.scrollHeight > viewDivElem.clientHeight){
	                    //      var scrollBarOptions = Ext.getScrollbarSize();
	                    //      var height = scrollBarOptions.height ?  scrollBarOptions.height + 1 : Bm.Constants.Task.DefaultScrollBarHeight;
	                    //      taskListGrid.setHeight(taskListGrid.getHeight() + height);
	            		  //}
                        //}
                    }
                });
                getResultPanel().add(taskListGrid);
                //FIXME - doLayout
                getCenterContainer().doLayout();
                //Bm.data.store.load.defer(5,Bm.data.store,[{params : {start:0,limit:15}}]);  //5ms defer

            },

            failure: handleAjaxException,
            params: params
        });
    }
}

function disableButtons(flag){
    if(Ext.isEmpty(flag)){
        flag = true;
    }
    var searchBtn = Ext.getCmp('searchBtn');
    var nextAvailBtn = Ext.getCmp('nextAvailBtn');
    if(searchBtn && nextAvailBtn){
        if(flag){
            searchBtn.disable();
            nextAvailBtn.disable();
        }else{
            searchBtn.enable();
            nextAvailBtn.enable();
        }
    }
}

function getTaskGrid() {
	return Ext.getCmp('taskList');
}

/* This method destroy the grid if it is rendered and creates the gird again,
 * and this will happen every search operation. This is because completed and
 * collaborative tasks grid should not have CheckBoxModel and others should
 * have it.
 */
function createTaskGrid(reconfig, titlePrefix){
    var taskGrid = getTaskGrid();
    if(!Ext.isEmpty(taskGrid)) {
    	Ext.destroy(taskGrid);
    }
    if (Bm.util.isLocalStore){
        //var taskListGrid = new Ext.grid.GridPanel({
        var taskListGrid = new Bm.grid.GridPanel({
            id: 'taskList',				
            title : getLocalizedString('tasks'),
            store: Bm.data.store,
            columns: Bm.data.ColumnModel,
            selModel: Bm.Tasks.SelectionModel,
            loadMask: true,
            autoHeight:true,
            viewConfig: {stripeRows: true},
            showTopPagingToolbar : false,
            showBottomPagingToolbar : false,
            plugins:[new Bm.plugin.GridViewForceFit()],
           // el: 'taskListDiv',
            viewConfig: {
                //autoFill : true,
                emptyText: '<div align="center"><span class="NoRecFound">'+getLocalizedString("noresults")+'</span><div>'
            },
            forceFit:true,
            reconfig: reconfig,
           dockedItems: [{
                xtype: 'pagingtoolbar',
                itemId: 'toptoolbar',
                store: Bm.data.store,
                dock: 'top',
                displayInfo: true,
                displayMsg:getLocalizedString('displayMsgText')
            }, {
                xtype: 'pagingtoolbar',
                itemId: 'bottomtoolbar',
                store: Bm.data.store,
                dock: 'bottom'
            }
            ]
        });

        return taskListGrid;
    } else {
        /* FIXME: Selection Model - Selection Model is given to the grid conditionally.*/
        if(Ext.util.Format.lowercase(Bm.Tasks.status) == Bm.Tasks.Status.Completed ||
            Ext.util.Format.lowercase(Bm.Tasks.status) == Bm.Tasks.Status.Collaborative){
            //Recreate the grid without selection model.
            Bm.Tasks.SelectionModel = {};
        } else {
            //Create or recreate the grid with selection model.
            Bm.Tasks.SelectionModel = Ext.create('Bm.selection.CheckboxModel');
            Bm.Tasks.SelectionModel.on("selectionchange", checkBoxSelectionChange);
        }

        var defaultColumn = Bm.Constants.Task.DefaultColumns;
        if (Ext.util.Format.lowercase(Bm.Tasks.status) == Bm.Tasks.Status.Assigned) {
            defaultColumn = Bm.Constants.Task.AssignedDefaultColumns;
        }
        
        //This process is to set the width for the task columns based on the given column header.
        if (Bm.data.ColumnModel.length > defaultColumn) {
        // This condition will be true when a dataslot defined as a task column, since default column are 10.
            var minColWidth = Bm.Constants.MinGridDSColumnWidth; 
            var calculatedColWidth;
            for(var i=defaultColumn; i<Bm.data.ColumnModel.length; i++){
                var type = Bm.data.ColumnModel[i]['type'];
                if(type == 'number') {
                    minColWidth = Bm.Constants.NumericDSColumnMinWidth;
                }
                if(!Ext.isEmpty(Bm.data.ColumnModel[i]['width'])) {
                    // Column width is assigned the value in <APP_NAME>*.properties file.
                    calculatedColWidth = Bm.data.ColumnModel[i]['width']; 
                } else {
                    calculatedColWidth = Bm.data.ColumnModel[i]['header'].length * 10;
                }                
                //The column width should not be less than 100 pixels.
                if (calculatedColWidth < minColWidth) {
                    Bm.data.ColumnModel[i]['width'] = minColWidth;
                }
                var label = Bm.data.ColumnModel[i]['header'];
                if(!Ext.isEmpty(label) && label.lastIndexOf(':') == (label.length-1)) {
                    // Replace the last occurence of ':' in dataslot label
                    Bm.data.ColumnModel[i]['header'] = label.substring(0,label.length-1);
                }                 
            }
        }
        
            var taskListGrid = Ext.create('Bm.grid.GridPanel',{
                id: 'taskList',
                titlePrefix: titlePrefix,
                titleSuffix : getLocalizedString('tasks'),
                store: Bm.data.store,
                columns: Bm.data.ColumnModel,
                selModel: Bm.Tasks.SelectionModel,
                exportConfig: [{type: 'excel', callbackFn: ExportTasksDataToExcelFn}],
                reconfig: reconfig                
            });

            //getResultPanel().add(taskListGrid);

            /* FIXME: Dynamic Column disordered - Issue: Task Grid columns are loading dyncamically.
             * One of the new columns will be added between CheckboxModel and RowNumberer columns.
             * WorkAround: Reconfigure method will solve the issue and this is a workaround.
             * This issue is fixed in ExtJS 4.1. Can remove this line when we go for ExtJS 4.1.
             *  */
            //taskListGrid.reconfigure(Bm.data.store,Bm.data.ColumnModel);
            return taskListGrid;
    }
}

function showTask(enc_task, taskId, performer, performerE, loginUser, linkFrom) {
   //alert('show task:'+taskId);
    var url = '../myhome/bizsite.task.show?bizsite_taskNameE='+enc_task+'&action=showframe&skip=false&bizsite_taskId='+ taskId +'&cenabled=false&link=' + linkFrom;
//    if (typeof (taskId) != 'undefined' && taskId > 0)     // check if workitem nummber and add url encode?
//        url += '&fiid=wi'+taskId;
    if(linkFrom == '') {
        var status = Ext.util.Format.lowercase(Bm.Tasks.status);
        if(status === Bm.Tasks.Status.Collaborative) {
            url = '../myhome/task_det.jsp?bizsite_taskNameE='+enc_task+'&isCollaborator=true&cwiId='+taskId+'&cenabled=false&&skip=false&fiid=ci'+taskId;
        } else if (status === Bm.Tasks.Status.DelegatedAssigned || status === Bm.Tasks.Status.DelegatedAvailable){
            url += "&ap=" + performerE + "&isdelegated=true";
        }
        return url;
    } else {
        if(performer == loginUser) {
            return url;
        } else {
            return "javascript:showTaskAssignWi(\"" + enc_task + "\",\"" + taskId + "\",\"" + performer + "\",\"" + loginUser + "\",\"" + linkFrom + "\");";
        }
    }
}

var ExportTasksDataToExcelFn = function() {
    var formValues = prepareParams();
    var grid = getTaskGrid();
    formValues['exportType'] = 'excel';
    formValues['visibleCols'] = Bm.ux.exp.ExcelExporter.prepareHeaderMap(grid);
    formValues['fileName'] = grid.title;
    Bm.ux.exp.ExcelExporter.postRequest('../myhome/bmTaskSearch.portal', formValues);
}

function showTaskAssignWi(enc_task, taskId, performer, loginUser, linkFrom){
	Ext.MessageBox.buttonText.ok='getLocalizedString("ok")';
    Ext.Msg.show({
      title: getLocalizedString('assignWiToSelf'),
      msg: getLocalizedString('assignWiToManager'),
      buttons: Ext.Msg.YESNO,
      fn: function(btn, text) {
         if(btn == 'yes') {
             Ext.Ajax.request({
                url: contextPath+'/bpmportal/ajaxutil.portal',
                success: loadOverviewTask,
                failure: showOverviewTaskFailure,
                params: 'action=assignToMe&taskId='+taskId+'&link='+linkFrom
             });
         }
      },
      icon: Ext.MessageBox.QUESTION
    });
}

var loadOverviewTask = function(result, request) {
    var jsonData = Ext.util.JSON.decode(result.responseText);
    var taskId = jsonData["taskId"];
    var linkFrom = jsonData["link"];
    var encodedTaskName = jsonData["bizsite_taskNameE"];
    window.location = '../myhome/bizsite.task.show?action=showframe&skip=false&&bizsite_taskNameE='+encodedTaskName+'&bizsite_taskId='+ taskId +'&cenabled=false&link=' + linkFrom;
}

var showOverviewTaskFailure = function(result, request) {
    var jsonData = Ext.util.JSON.decode(result.responseText);
    Ext.MessageBox.alert(getLocalizedString('AimError'), jsonData["failureMsg"]);
}

Bm.Tasks.taskNameRenderer = function(value, metadata, record, rowIndex, colIndex, store){
    var taskName = value + "#" + record.get("bm_taskId");
    if(record.get("count") && record.get("count") != 1)
        taskName += " [" + record.get("count") + "]";

    if((record.get("bm_flag") & 8) == 8)  //8 for completed...
        return taskName;

    metadata.tdAttr = 'data-qtip="' + taskName + '"';
    return "<a href='" + showTask(record.get("bm_encodedId"),record.get("bm_taskId"),record.get("performer"),record.get("performerE"),loginUser,returnTo) + "' class='ActionLnk'  onkeydown='addParamkey(this,event);' onMouseDown='addMenuHeaderParam(this,event);'>" + taskName + "</a>";
};

Bm.Tasks.getSelectedTasks = function() {
    var selectedRows = Bm.Tasks.SelectionModel.getSelection();
    var selectedInstances = new Array();

    for (var i=0; i<selectedRows.length;i++) {
        selectedInstances[i] = selectedRows[i]['data']["bm_taskId"];
    }
    return selectedInstances;
}

Bm.Tasks.getNextTask = function(config) {
    var params = prepareDateParams(config.params);
    params = prepareFilterParams(params);
    Ext.Ajax.request({
       url: '../myhome/bmTaskSearch.portal',method:'POST',
       success: function(response, opts) {
                    var responseurl = response.responseText;
                    if(responseurl == '') {
                        var status = '';
                        if(params.action == 'NextAssigned') {
                            status = 'assigned';
                        }
                        window.location = '../myhome/noavailtask.jsp?status='+status;
                    } else {
                        var url = response.responseText;
                        window.location='../myhome/' + url;
                    }
                },
       failure: function(response, opts) {
                    handleAjaxException(response, opts);
                },
       params : params
    });

}

Bm.Tasks.printTasks = function() {
    var grid = getTaskGrid();
    if (!Ext.isEmpty(grid)){
        Ext.ux.Printer.print(grid);
    }
}

function refreshTaskGrid() {
    var taskList = Ext.getCmp('taskList');
    if(!Ext.isEmpty(taskList)) {
        taskList.store.reload();
    }
}
function GetOffset (object, offset) {
	if (!object)
		return;
	offset.x += object.offsetLeft;
	offset.y += object.offsetTop;

	GetOffset (object.offsetParent, offset);
}
window.onresize=function(){
	var offset={x:0,y:0};
	GetOffset(document.getElementById(getTaskGrid().id),offset);
	var tempheight = document.getElementById('southContainer-outerCt').offsetHeight || 0;	
	if(offset.y!=NaN)
	{
		 var ht=window.innerHeight-offset.y-tempheight-5;
		 if(ht<150)
		 {
			getTaskGrid().setHeight(150);
		 }
		 else
		 {
			getTaskGrid().setHeight(ht);
		 }
	}
}

function addParamkey(obj,e)
{
	var charCode = e.witch ? e.witch : e.keyCode;
	if(charCode==13 || charCode==32)
	{
		window.location=obj.href;
	}
}