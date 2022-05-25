Ext.namespace("Bm.Tasks.Filter");

Bm.Tasks.Filter.initTabs = function(){    
    getFilterContainer().add(BmFilterView.render());
}

Bm.Tasks.doBulkSubmit = function(action) {
    var json = {};
    var reassigneeFld = Ext.getCmp('reassignee');
    var selTaskIds = Bm.Tasks.getSelectedTasks();
    if(selTaskIds.length == 0) {
        Bm.component.showMsg(getLocalizedString('error'),
                              getLocalizedString('noTaskSelected'),
                              'bulkOpPanel',
                              Ext.MessageBox.ERROR);
        return;
    }
    json.taskIds = Ext.encode(selTaskIds);
    json.action = action;
    if("bulkReassignTasks" == action || "bulkAssignTasks" == action) {
        var reassignee = reassigneeFld.getValue();
        if(Ext.isEmpty(reassignee)) {
           Bm.component.showMsg(getLocalizedString('error'),
                              getLocalizedString('invalidReassignPerformer'),
                              'bulkOpPanel',
                              Ext.MessageBox.ERROR);
            return;
        }
        json.performer = reassignee;
    }
    reassigneeFld ? reassigneeFld.setValue('') : '';
    Ext.Ajax.request({
       url: '../myhome/bmTaskAction.portal',
       method : 'GET',
       success: function(response, opts) {
          var obj = Ext.decode(response.responseText);
          var params = getTaskFilterForm().getForm().getValues();
          if(obj.successAttr) {
              refreshGrid({params : params});
              Bm.component.showMsg(getLocalizedString('success'),
                              obj.message,
                              'bulkOpPanel',
                              Ext.MessageBox.INFO);
          } else {
              if(obj.status == 'appError') {
                  refreshGrid({params : params});
                  Bm.component.showMsg(getLocalizedString('warning'),
                              obj.message,
                              'bulkOpPanel',
                              Ext.MessageBox.WARNING);
              } else {
                  Bm.component.showMsg(getLocalizedString('errorTitle'),
                              obj.message,
                              'bulkOpPanel',
                              Ext.MessageBox.ERROR);
              }
          }
       },
       failure: function(response, opts) {
          var result = cb(null, false, response, opts);
          if(result){
            return;
          }
          var msg = response.message;
          Bm.component.showMsg(getLocalizedString('errorTitle'),
                              msg,
                              'bulkOpPanel',
                              Ext.MessageBox.ERROR);
       },
       scope:this,
       params: json //{data: Ext.encode(json)}
    });
}

Bm.Tasks.bulkOpPanel = function(config) {
    var bulkPanel = Ext.create('Ext.form.Panel',{
        title: '',
        id:'bulkOpPanel',
		layout: {
			type: 'hbox',
			pack:'center',
			align:'middle'
		},
        height: 45,
        bodyBorder : false,
        frame : true,
        autoScroll:false,
        bodyCls: 'bmBottomPanelBg',
        items: Bm.Tasks.getBulkOpPanelItems(config)
    });
    getCmdContainer().add(bulkPanel);
}

Bm.Tasks.getBulkOpPanelItems = function(config) {
    var params = config.params;
    var status = params.status;
    var getReassignBtnCfg = function() {
        return [
            Bm.um.search.UserChooserPanel.getSearchUserPanel({tfId: 'reassignee', multiSelection: false, hideLabel: true}),
            {
                xtype:'button',
                text: getLocalizedString('reassign'),
                handler: function() {
                    Bm.Tasks.doBulkSubmit('bulkReassignTasks');
                }
            }
       ]
    };

    var getMakeAvailableBtnCfg = function() {
        return {
            xtype:'button',
            id:'makeAvailableBtn',
            text: getLocalizedString('makeAvailable'),
            handler: function() {
                 Bm.Tasks.doBulkSubmit('bulkMakeAvailable');
            },
            style: {
                marginLeft : '5px'
            }
        }
    };

    var getCompleteBtnCfg = function() {
        return {
            xtype:'button',
            id:'completeBtn',
            text: getLocalizedString('complete'),
            handler: function() {
                    var selectedRows = Bm.Tasks.SelectionModel.getSelection();
                    var tasksWithReqDs = '';
                    for (var i=0; i<selectedRows.length;i++) {
                        var hasReqDs = selectedRows[i]['data']["hasRequiredDataslot"];
                        if(hasReqDs) {
                            var taskName = selectedRows[i]['data']["task"]
                                                + "#"
                                                + selectedRows[i]['data']["bm_taskId"];
                            tasksWithReqDs += '&nbsp;&nbsp;&nbsp;&nbsp;'+ taskName + '<br/>';
                        }
                    }
                    if(Ext.isEmpty(tasksWithReqDs)) {
                      Bm.Tasks.doBulkSubmit('bulkCompleteTasks');
                    } else {
                        var msg = getLocalizedString('confirmReqDsTask')+ '<br/>' + tasksWithReqDs;
                        var handler = function(btn, text, opt){
                            if (btn == 'yes'){
                                Bm.Tasks.doBulkSubmit('bulkCompleteTasks');
                            } else {
                                return;
                            }
                        }
                        Ext.Msg.show({
                           title: getLocalizedString('warning'),
                           msg: msg,
                           closable:false,
                           buttons: Ext.MessageBox.YESNO,
                           fn: handler,
                           animEl: 'completeBtn',
                           icon: Ext.MessageBox.WARNING
                        });
                    }
            },
            style: {
                marginLeft : '5px'
            }
        }
    };

    //var bulkOpMenuItem = [getReassignBtnCfg(), getMakeAvailableBtnCfg()];
    var bulkOpMenuItem = new Array();
    Ext.apply(bulkOpMenuItem,getReassignBtnCfg());    
    bulkOpMenuItem.push(getMakeAvailableBtnCfg());
    
    var getAssignBtnCfg = function() {
        return [
            Bm.um.search.UserChooserPanel.getSearchUserPanel({tfId: 'reassignee', multiSelection: false, hideLabel: true}),
          {
              xtype:'button',
              id:'assignBtn',
              text: getLocalizedString('assign'),
              handler: function() {
                  Bm.Tasks.doBulkSubmit('bulkAssignTasks');
              }
          }
       ]
    };

    if(Bm.Tasks.isMyTasksPage()) {
        //Adding Complete option
        bulkOpMenuItem.push(
             getCompleteBtnCfg()
        );
    }

    var getBulkButtonCfg = function() {
        var btnItems;
        if(Ext.util.Format.lowercase(status) == 'assigned'
                || Ext.util.Format.lowercase(status) == 'delegatedassigned' || status == '') {
          btnItems = bulkOpMenuItem;
        } else {
            btnItems = getAssignBtnCfg();
        }
        return btnItems;
    };
    return getBulkButtonCfg();
};

Bm.Tasks.isMyTasksPage = function() {
    return true;
    //return Ext.getCmp('isMyTasksPage').getValue() == "true" ? true : false;
}

Bm.Tasks.isManager = function() {
    var isManager = BmFilterView.isManager();
    return isManager;
}

Bm.Tasks.getDsFields = function(){
    var dsFields = ['app','version','priority','status','queue', 'isManager', 'startDateConstants','dueDateConstants','defaultApp'
                    ,'defaultVersion', 'defaultFilterId','filters'];
    if(isSelectedFormValuesEnabled()){
        dsFields = dsFields.concat(['selectedApp', 'selectedVersion', 'selectedWorkstep','selectedPriority', 'selectedTaskStatus', 'selectedPerformer', 
                'selectedPerformerType', 'selectedTaskFilter', 'selectedTaskFilter']);
    }
    return dsFields;
}

var BmFilterView = {};
Bm.Tasks.Filter.initFilter = function(config){
    var gridTitle = (config != undefined ? config.gridTitle : 'Tasks');
    if(gridTitle == null || gridTitle == undefined) {
        gridTitle = getLocalizedString('tasks');
    }
    
    var ds = Ext.create('Ext.data.Store', {
        model: Ext.define('test', {
                extend:'Ext.data.Model',
                fields: Bm.Tasks.getDsFields()
                }),
        proxy: {
            type: 'ajax',
            url : '../myhome/bmTaskSearch.portal',
            method:'GET',
            reader: {
                type: 'json',
                root: 'data',
                totalProperty : 'count'
            }
        },

        listeners : {
             'load' : {
                    fn:  function(store, records, successful, operation, options){
                        new Bm.component.Text({
                                id:'isMyTasksPage',
                                hidden : true,
                                renderTo: Ext.getBody(),
                                value: config.isMyTasksPage
                            });
                        BmFilterView = getFilterUI({data:store});
                        Bm.Tasks.Filter.initTabs();
                        var params = getTaskFilterForm().getForm().getValues();
                        params.myTasksPageSize = config.myTasksPageSize;
                        refreshGrid({params : params});
                        config.params = params;
                        Bm.Tasks.bulkOpPanel(config);
                        if(Ext.util.Format.lowercase(params.status) == 'completed' ||
                        	Ext.util.Format.lowercase(params.status) == 'collaborativeassigned'){
                            	Ext.getCmp('bulkOpPanel').removeAll();
                        }

                    }
                },
            'exception' : {
                    fn : function( store, type, action, options, response, arg) {
                            handleStoreLoadException(store, type, action, options, response, arg);
                        }
                    }
            },
            autoLoad : true
    });

}

function setChoosedUserName(userName) {
  var reAssignUserTxt = Ext.getCmp('reassignee');
  if(!Ext.isEmpty(reAssignUserTxt) && !Ext.isEmpty(userName)) {
      reAssignUserTxt.setValue(userName);
  }
}
