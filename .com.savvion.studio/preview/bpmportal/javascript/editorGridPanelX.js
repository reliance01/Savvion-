Bm.util.EditorGridPanelX = function(config) {
    var checkboxSel = Ext.create('Ext.selection.CheckboxModel',{headerWidth: 22, mode: 'SIMPLE'});
    Ext.define('editorGridModel', {
        extend : 'Ext.data.Model',
        fields  : [{name : 'paramName'},{name:'paramValue'}]       
    });
    var editorGridstore = Ext.create('Ext.data.ArrayStore',{
        model   :'editorGridModel',
        data    : config.data                          
    });
    var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
                clicksToEdit: 1
            });          
    this.editorGrid = Ext.create('Ext.grid.Panel',{
        id          : 'urlParamGrid',
        title       : config.title,
        height      : config.height,
        frame       : true,     
        autoScroll : true,
        selModel    : checkboxSel,       
        store       : editorGridstore,
        //enableDragDrop  : true,
        renderTo    : config.renderTo,        
        forceFit    : true,
        plugins     : [ cellEditing ],
        viewConfig: {
            plugins : {
                    ptype: 'gridviewdragdrop',
                    dragText: '1 selected row'
            }
        },
        columns     : [                        
                        { 
                            name          : 'paramName',
                            header      : getLocalizedString('paramName'),
                            width       : 80,
                            dataIndex   : 'paramName',
                            sortable    : true,
                            editor      : {
                                xtype: 'textfield',
                                allowBlank  : false
                            }
                        },
                        {   
                            name          : 'paramValue',
                            header      : getLocalizedString('paramValue'),
                            width       : 160,
                            dataIndex   : 'paramValue',
                            sortable    : true,
                            editor      :  {
                                xtype: 'textfield',
                                allowBlank  : false
                            }
                        }
        ],
        autoExpandColumn    : 'paramValue',
        tbar        : [{
            iconCls : "bmAdd",
            id		: "editorGridX-addRowBtn",
            handler : function(grid){
                var grid = Ext.getCmp('urlParamGrid');
                var sm = checkboxSel;
                var thisStore = editorGridstore;
                var insertAt;
                if(null != thisStore.data) {
                    insertAt = thisStore.data.length;
                }                            
                if(sm.hasSelection()) {
                  var selections = sm.getSelection();
                  var lastSelRecord = selections[selections.length-1];
                  var lastSelIndex = thisStore.indexOf(lastSelRecord);
                  if(lastSelIndex !== -1){
                    insertAt = lastSelIndex + 1;
                  }else{
                    //If Selected record index not found, then new row will be added at the end of grid row cell.
                    insertAt = thisStore.data.length;
                  }
                }          
                var wAttr = Ext.create('editorGridModel', {
                    paramName   : '',
                    paramValue  : ''
                });
                //cellEditing.cancelEdit();
                thisStore.insert(insertAt, wAttr);
                //cellEditing.startEditByPosition({row: 0, column: 0});
            }
            },'-', {
                iconCls : "bmDelete",
                id		: "editorGridX-deleteRowBtn",
                handler : function(){
                    var grid = Ext.getCmp('urlParamGrid');
                    var sm = checkboxSel;
                    var thisStore = grid.store;
                    var sel = sm.getSelection();
                    if(!Ext.isEmpty(config.widgetName) && config.widgetName == '|HeatmapWidget'){                       
                       var selections = sm.getSelection();
                       var isSelected = isValidSelected(selections);
                    }            
                    
                    if(sel.length == 0) {
                        Ext.MessageBox.alert(getLocalizedString("SelectParam"),getLocalizedString("SelectParamMsg"));
                    }else if(isSelected) {
                        Ext.MessageBox.alert(getLocalizedString("heatmapParamDeleteTitle"),getLocalizedString("HeatMapWidgetParamDelete"));
                    } else {
                        for(idx=0; idx < sel.length; idx++) {                                    
                            thisStore.remove(sel[idx]);
                        }
                    }    
                }
            },'-'
        ],
        tools    : [{
                    id      : 'help',
                    qtip    : config.helpMsg,
                    //qtip    : config.toolTipMsg,
                    handler : function(e, toolEl, panel) {
                        //Ext.MessageBox.alert(config.helpTitle, config.helpMsg);
                    }
        }],
        preEditValue : function(r, field){			
			var value = r.data[field];        
			return unescapeHtml(value);			
		},
        listeners: {
            afteredit: function(e){
            }
            ,render: function(g){
              /*var ddrow = new Ext.ux.GridReorderDropTarget(g, {
                copy: false
                ,listeners: {
                  beforerowmove: function(objThis, oldIndex, newIndex, records){
                  }
                  ,afterrowmove: function(objThis, oldIndex, newIndex, records){
                  }
                  ,beforerowcopy: function(objThis, oldIndex, newIndex, records){
                  }
                  ,afterrowcopy: function(objThis, oldIndex, newIndex, records){
                  }
                }
              });*/
              // if you need scrolling, register the grid view's scroller with the scroll manager
              Ext.dd.ScrollManager.register(g.getEditorParent());
            }
            ,beforedestroy: function(g){
              // if you previously registered with the scroll manager, unregister it (if you don't it will lead to problems in IE)
              Ext.dd.ScrollManager.unregister(g.getEditorParent());
            }
          }
    });
}

/* 
Overriding CheckBoxSelectionModel and GridDragZone to fix the issue
where the checkbox selection model was not working due to the drag and 
drop functionality.

Ext.grid.CheckboxSelectionModel.override({
    initEvents: function(){
        Ext.grid.CheckboxSelectionModel.superclass.initEvents.call(this);
        if(this.grid.enableDragDrop || this.grid.enableDrag)
            this.grid.removeListener('rowclick',function  (){});
        this.grid.on('render', function(){
            var view = this.grid.getView();
            view.mainBody.on('mousedown', this.onMouseDown, this);
            Ext.fly(view.innerHd).on('mousedown', this.onHdMouseDown, this);
        }, this);
    }
});

Ext.grid.GridDragZone.override({
    handleMouseDown: function(e, t){
        if(t.className == 'x-grid3-row-checker')
            return false;
        Ext.grid.GridDragZone.superclass.handleMouseDown.apply(this, arguments);
    }
});
*/

function unescapeHtml(value){	
	var result = Ext.util.Format.htmlDecode(value);
	// htmlDecode does not handle  single quote 
	result = result.replace(/\&#039;/g,"'");  	
	return result;
}

function isValidSelected(records){
    var isSelected = false;
    if(!Ext.isEmpty(records)){
        for(i = 0;i< records.length;i++){
            var value = records[i].get('paramName');
            if(!Ext.isEmpty(value)){
                if(value == 'processTemplateName' || value == 'piStatus' || value == 'wsStatus'){
                    isSelected = true;
                    break;
                }
            }
        }
    }
    return isSelected;
}