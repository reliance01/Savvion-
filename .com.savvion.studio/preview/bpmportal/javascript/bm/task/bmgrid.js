//Override for specifying minimum column header width
Ext.override(Ext.grid.plugin.HeaderResizer, {
    minColWidth: Bm.Constants.GridHeaderMinSize
});

//Create namespace
Ext.namespace("Bm.grid");

/*
 * Override: Ext.grid.column.Column: renderer method for tooltip.
 */
Ext.override(Ext.grid.column.Column, {
    renderer: function(value, metadata, record, rowIndex, colIndex, store){
        metadata.tdAttr = 'data-qtip="' + value + '"';
        return value;
    }
});

/*
 * Wrapper extension for Ext.grid.RowNumberer
 */
Ext.define('Bm.grid.RowNumberer', {
    extend : 'Ext.grid.RowNumberer',
    alias: 'widget.bmrownumberer',
    header: getLocalizedString('srno'),
    menuDisabled: false,
    hideable: false,
    minWidth: 30,
    maxWidth: 30
});

/*
 * Wrapper extension Ext.toolbar.Paging
 */
Ext.define('Bm.grid.Paging', {
    extend : 'Ext.toolbar.Paging',
    alias: 'widget.bmpagingtoolbar',
    beforePageText: getLocalizedString("beforePageText"),
    nextText : getLocalizedString("nextText"),
    lastText: getLocalizedString("lastText"),
    prevText : getLocalizedString("prevText"),
    firstText : getLocalizedString("firstText"),
    refreshText : getLocalizedString("refreshText"),
    stateful: false,
    stateEvents:["change"],
    /*
        This overridden method will save the state as the array descriped,
        on the events which are configured as stateEvents.
    */
    getState: function() {
        return {lastLoadedPage: this.store.currentPage,
                previousPageSize: this.store.pageSize}
    },
    /*
        This overridden method will be called when the state is available.
    */
    applyState : function(state){
        if (this.store.pageSize != state.previousPageSize) {
            this.store.currentPage = 1;
        } else {
            this.store.currentPage = state.lastLoadedPage;
        }
    },
    listeners: {
        'change': function(paging, pageData, eOpts){
            /* Dynamically serial number column width will be updated based on
            *  the number of digits in maximum page number for the current page.
            */
            var defaultWidth = 30;
            var width = defaultWidth;
            if (!Ext.isEmpty(pageData)) {
                width = (((pageData.toRecord)+"").length) * 10;
            }
            if (width < defaultWidth) {
                width = defaultWidth;
            }
            var cmp = getRowNumberer();
            if(!Ext.isEmpty(cmp)){
                cmp.width = width;
                cmp.minWidth = width;
                cmp.maxWidth = width;
            }
            
            //Refresh search toolbar if configured.
            var searchToolBar = Ext.getCmp('searchtoolbar');
            if(!Ext.isEmpty(searchToolBar)) {
                searchToolBar.doRefresh();
            }            
        }
    }
});

function getRowNumberer(){
    return Ext.getCmp('srNo');
}

/*
 * Wrapper extension for Ext.grid.Panel
 */
Ext.define('Bm.grid.GridPanel', {
	extend : 'Ext.grid.Panel',
    loadMask : true,
    viewConfig: {
    	emptyText: '<div align="center" style="padding: 13px;"><span class="NoRecFound">'+getLocalizedString("noresults")+'</span><div>',
        stripeRows : true,
        deferEmptyText: false,
        enableTextSelection: true  // Allows to make text in Grid panel selectable.
    },
    forceFit: true,
    showTopPagingToolbar : false,
    showGridBottomToolBar: true,
    showBottomPagingToolbar : true,
    enableLocalSearch : true,
    enableStatefulPagination: true,
    statefulPaginationId: null,
    stateful: true,
    reconfig : false,
	toolBarItems: [],
	showTopToolBar:false,
	
    initComponent : function(config) {
        //Override user defined config options.
        Ext.apply(this, config);
        
        //Enabling stateful grid and paging.
        if(Ext.isIE7){
            Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
        }else{
            Ext.state.Manager.setProvider(new Ext.state.LocalStorageProvider());
        }                
        if(this.reconfig){
            Ext.state.Manager.clear(this.id + "_GridState");
            Ext.state.Manager.clear(this.id + "_PageState");
        }
        if (this.stateful) {
            if (Ext.isEmpty(this.stateId)){
                this.stateId = this.id + "_GridState";
            }
        }else{
            this.stateId = null;
        }
        if(this.enableStatefulPagination) {
            /* Preparing default stateId from the Grid Id if stateId is not passed.*/
            if (Ext.isEmpty(this.statefulPaginationId)){
                this.statefulPaginationId = this.id + "_PageState";
            }
        } else {
            this.statefulPaginationId = null;
        }
        
        if(this.columns[0].xtype != 'rownumberer'){
            this.columns = [{xtype : 'bmrownumberer', id: 'srNo', dataIndex: 'srNo'}].concat(this.columns);
        }        
        var dockedItemsArray = new Array();
		if(this.showTopToolBar){
			dockedItemsArray.push({
				xtype: 'toolbar',
				dock: 'top',
				items: this.toolBarItems
			});
		}
        if(this.showTopPagingToolbar) {
            dockedItemsArray.push({
                xtype: 'bmpagingtoolbar',
                itemId: 'toptoolbar',
                store: this.store,
                dock: 'top'
            });
        }

        var bottomGridToolbarArray = new Array();
        if(this.showBottomPagingToolbar) {            
            bottomGridToolbarArray.push({
                xtype: 'bmpagingtoolbar',
                itemId: 'bottompagingtoolbar',
                store: this.store,
                border: false,
                displayInfo: true,
                displayMsg: getLocalizedString('displayMsgText'),
                stateful: this.enableStatefulPagination,
                stateId: this.statefulPaginationId
            });
            bottomGridToolbarArray.push('-');
        }
          
        if(this.enableLocalSearch) {
            bottomGridToolbarArray.push(
                {
                    xtype: 'toolbar',
                    id:'searchtoolbar',
                    border: false,
                    doRefresh : function () {
                        Ext.getCmp('searchField').setValue(""); // Clear Search Text
                        Ext.getCmp('regExpCheckbox').setValue(false); // Uncheck RegEx Checkbox
                        Ext.getCmp('caseSensitiveCheckbox').setValue(false); // Uncheck CaseSensitive Checkbox
                        Ext.getCmp('previousrowbtn').setDisabled(true); // Disable previous row button
                        Ext.getCmp('nextrowbtn').setDisabled(true); // Disable next row button
                    },
                    items: [
                        {
                             xtype: 'textfield',
                             name: 'searchField',
                             id: 'searchField',
                             emptyText:  getLocalizedString('GridSearchText'),
                             hideLabel: true,
                             width: 110,
                             plugins: ['clearbutton'],
                             listeners: {
                                 change: {
                                     fn: this.onTextFieldChange,
                                     scope: this
                                 }
                             }
                        },{
                            xtype: 'button',
                            id: 'previousrowbtn',
                            disabled: true,
                            iconCls: Ext.baseCSSPrefix + 'tbar-page-prev',
                            tooltip: getLocalizedString('PreviousRow'),
                            handler: this.onPreviousClick,
                            scope: this
                        },{
                            xtype: 'button',
                            id: 'nextrowbtn',
                            disabled: true,
                            iconCls: Ext.baseCSSPrefix + 'tbar-page-next',
                            tooltip: getLocalizedString('NextRow'),
                            handler: this.onNextClick,
                            scope: this
                        },{
                            xtype: 'checkbox',
                            id: 'regExpCheckbox',
                            hideLabel: true,
                            handler: this.regExpToggle,
                            scope: this                
                        }, getLocalizedString('RegEx'),
                        {
                            xtype: 'checkbox',
                            id: 'caseSensitiveCheckbox',
                            hideLabel: true,
                            handler: this.caseSensitiveToggle,
                            scope: this
                        }, getLocalizedString('MatchCase'),
                        {
                            xtype: 'statusbar',
                            border: false,
                            defaultText: this.emptyStatusText,
                            name: 'searchStatusBar'
                        }
                     ]
                  }                        
              );
              bottomGridToolbarArray.push('-');              
          }
          
          var gridBottomToolbar = {
                xtype: 'toolbar',
                id: 'gridbottomtoolbar',
                dock: 'bottom',
                items: bottomGridToolbarArray
          }
        
        if(this.showGridBottomToolBar) {
            dockedItemsArray.push(gridBottomToolbar);
        }        
        
        this.dockedItems = dockedItemsArray;
        this.callParent();
    },
    
    /* SearchToolbar: Configuration starts >>>>> */
    
    // afterRender override: it adds textfield and statusbar reference and start monitoring keydown events in textfield input 
    afterRender: function() {
        var me = this;
        me.callParent(arguments);
        if(this.enableLocalSearch) {
            me.textField = me.down('textfield[name=searchField]');
            me.statusBar = me.down('statusbar[name=searchStatusBar]');
        }
    },
    
    // detects html tag
    tagsRe: /<[^>]*>/gm,
    
    // DEL ASCII code
    tagsProtect: '\x0f',
    
    // detects regexp reserved word
    regExpProtect: /\\|\/|\+|\\|\.|\[|\]|\{|\}|\?|\$|\*|\^|\|/gm,
    
    /**
     * @cfg {String} matchCls
     * The matched string css classe.
     */
    matchCls: 'localsearch-match',
    
    defaultStatusText: getLocalizedString('NoMatches'),
    emptyStatusText: '',
    searchStatusText: getLocalizedString('Searching'),
    /**
     * In normal mode it returns the value with protected regexp characters.
     * In regular expression mode it returns the raw value except if the regexp is invalid.
     * @return {String} The value to process or null if the textfield value is blank or invalid.
     * @private
     */
    getSearchValue: function() {
        var me = this,
            value = me.textField.getValue();            
            
        if (value === '') {
            return null;
        }
        if (!me.regExpMode) {
            value = value.replace(me.regExpProtect, function(m) {
                return '\\' + m;
            });
        } else {
            try {
                new RegExp(value);
            } catch (error) {
                me.statusBar.setStatus({
                    text: error.message,
                    iconCls: 'x-status-error'
                });
                return null;
            }
            // this is stupid
            if (value === '^' || value === '$') {
                return null;
            }
        }

        return value;
    },

    /**
     * Finds all strings that matches the searched value in each grid cells.
     * @private
     */
     onTextFieldChange: function() {
         var me = this,
             count = 0;

         me.view.refresh();
         
         me.searchValue = me.getSearchValue();
         me.indexes = [];
         me.currentIndex = null;

         if (me.searchValue !== null) {
             // reset the statusbar
             me.statusBar.setStatus({
                 text: me.searchStatusText,
                 iconCls: ''
             });
             me.searchRegExp = new RegExp(me.searchValue, 'g' + (me.caseSensitive ? '' : 'i'));
             me.store.each(function(record, idx) {
                 var td = Ext.fly(me.view.getNode(idx)).down('td'),
                     cell, matches, cellHTML;
                 while(td) {
                     cell = td.down('.x-grid-cell-inner');
                     matches = cell.dom.innerHTML.match(me.tagsRe);
                     cellHTML = cell.dom.innerHTML.replace(me.tagsRe, me.tagsProtect);
                     
                     //Avoid selecting spaces as &nbsp
                     if(cellHTML.indexOf('&nbsp') ==  -1) {                     
                         // populate indexes array, set currentIndex, and replace wrap matched string in a span
                         cellHTML = cellHTML.replace(me.searchRegExp, function(m) {
                            count += 1;
                            if (Ext.Array.indexOf(me.indexes, idx) === -1) {
                                me.indexes.push(idx);
                            }
                            if (me.currentIndex === null) {
                                me.currentIndex = idx;
                            }
                            return '<span class="' + me.matchCls + '">' + m + '</span>';
                            
                         });
                         // restore protected tags
                         Ext.each(matches, function(match) {
                            cellHTML = cellHTML.replace(me.tagsProtect, match); 
                         });
                         // update cell html
                         cell.dom.innerHTML = cellHTML;
                     }
                     td = td.next();
                 }
             }, me);

             // results found
             if (me.currentIndex !== null) {
                 me.getSelectionModel().select(me.currentIndex);
                 me.statusBar.setStatus({
                     text: count + getLocalizedString('Matches'),
                     iconCls: 'x-status-valid'
                 });
                 Ext.getCmp('previousrowbtn').setDisabled(false);
                 Ext.getCmp('nextrowbtn').setDisabled(false);
             } else {
                 // reset the statusbar
                 me.statusBar.setStatus({
                     text: me.defaultStatusText,
                     iconCls: ''
                 });
                 Ext.getCmp('previousrowbtn').setDisabled(true);
                 Ext.getCmp('nextrowbtn').setDisabled(true);
             }
         } else {
             // reset the statusbar
             me.statusBar.setStatus({
                 text: me.emptyStatusText,
                 iconCls: ''
             });
             Ext.getCmp('previousrowbtn').setDisabled(true);
             Ext.getCmp('nextrowbtn').setDisabled(true);         
         }

         // no results found
         if (me.currentIndex === null) {
             me.getSelectionModel().deselectAll();
         }

         // force textfield focus
         me.textField.focus();         
     },
    
    /**
     * Selects the previous row containing a match.
     * @private
     */   
    onPreviousClick: function() {
        var me = this,
            idx;
            
        if ((idx = Ext.Array.indexOf(me.indexes, me.currentIndex)) !== -1) {
            me.currentIndex = me.indexes[idx - 1] || me.indexes[me.indexes.length - 1];
            me.getSelectionModel().select(me.currentIndex);
         }
    },
    
    /**
     * Selects the next row containing a match.
     * @private
     */    
    onNextClick: function() {
         var me = this,
             idx;
             
         if ((idx = Ext.Array.indexOf(me.indexes, me.currentIndex)) !== -1) {
            me.currentIndex = me.indexes[idx + 1] || me.indexes[0];
            me.getSelectionModel().select(me.currentIndex);
         }
    },
    
    /**
     * Switch to case sensitive mode.
     * @private
     */    
    caseSensitiveToggle: function(checkbox, checked) {
        this.caseSensitive = checked;
        this.onTextFieldChange();
    },
    
    /**
     * Switch to regular expression mode
     * @private
     */
    regExpToggle: function(checkbox, checked) {
        this.regExpMode = checked;
        this.onTextFieldChange();
    }

    /* <<<<< SearchToolbar: Configuration ends  */
});



Bm.grid.Column = Ext.extend(Ext.grid.Column,{

    type : ' ',
    constructor : function(config) {
        Bm.grid.Column.superclass.constructor.call(this, config);

    }
});

Ext.define('Bm.grid.ColumnModel',{
    extend : 'Ext.grid.column.Column'
});

Ext.define('Bm.grid.AppNameColumn',{
    extend : 'Bm.grid.ColumnModel',
    alias : 'widget.bmappnamecolumn',
    id: 'appNameCol', 
    dataIndex: 'appName', 
    header: getLocalizedString('application')
});

Ext.define('Bm.grid.piStatusColumn',{
    extend : 'Bm.grid.ColumnModel',
    alias : 'widget.bmpistatuscolumn',
    id: 'piStatusCol', 
    dataIndex: 'piStatus',
    align:'center',
    header: getLocalizedString('InstanceStatus')
});

Ext.define('Bm.grid.wsStatusColumn',{
    extend : 'Bm.grid.ColumnModel',
    alias : 'widget.bmwsstatuscolumn',
    id: 'wsStatusCol', 
    dataIndex: 'wsStatus',
    align: 'center',
    header: getLocalizedString('WorkstepStatus')
});


Ext.define('Bm.grid.ColumnWrap', {
	extend : 'Ext.grid.column.Column',
	alias : 'widget.bmcolumnwrap',
	tdCls : 'BmGridCellWrap',
	listeners : {
		'beforerender' : function(colwrap, eOpts) {
			if (colwrap.hidden) {
				colwrap.tdCls = '';
			}
		},
		'beforehide' : function(colwrap, eOpts) {
			var idx = colwrap.getIndex();
			var grid = colwrap.ownerCt.ownerCt;
			var rows = Ext.select('.x-grid-row', grid.getEl());
			rows.each(function(row) {
				Ext.get(row.dom.cells[idx]).removeCls('BmGridCellWrap');
			});
		},
		'beforeshow' : function(colwrap, eOpts) {
			var idx = colwrap.getIndex();
			var grid = colwrap.ownerCt.ownerCt;
			var rows = Ext.select('.x-grid-row', grid.getEl());
			rows.each(function(row) {
				Ext.get(row.dom.cells[idx]).addCls('BmGridCellWrap');
			});
		}
	}
});

Bm.grid.ColumnModel.createColumnModel = function(configFromServer, predefinedColumns,additionalConfig) {

    var headerArray;
    if(predefinedColumns != undefined && predefinedColumns != null && predefinedColumns.length > 0){
        headerArray = predefinedColumns;
    } else {
        headerArray = new Array();
    }

    if(configFromServer != undefined && configFromServer.length > 0) {
        for(var i=0;i < configFromServer.length;i++) {
            var name = configFromServer[i]['name'];
            var label = configFromServer[i]['label'];
            var dataIndex = configFromServer[i]['dataIndex'];
            var type = configFromServer[i]['type'];
            var isHiddenFld = configFromServer[i]['hiddenField'];
            var colWidth = configFromServer[i]['size'];
            var renderer = Bm.Tasks.taskDefaultRenderer;
            var sortableFlag = true;
            if(Ext.util.Format.uppercase(type) == 'DOCUMENT'){
                renderer = Bm.Tasks.taskDocumentDataSlotRenderer;
                sortableFlag = false;
            }else if(Ext.util.Format.uppercase(type) == 'XML'){
                renderer = Bm.Tasks.taskXMLDataSlotRenderer;
                sortableFlag = false;
            }else if(Ext.util.Format.uppercase(type) == 'URL'){
                renderer = Bm.Tasks.taskURLDataSlotRenderer;
            }else if(Bm.util.isLocalStore && Ext.util.Format.uppercase(type) == 'DATETIME'){
                sortableFlag = false;
            }


            if(isHiddenFld == 'false'){
                var column = {
                        //id : name,
                        name : name,
                        header : label,
                        dataIndex : dataIndex,
                        type : type,
                        renderer : renderer,
                        sortable : sortableFlag
                    };
                if(colWidth > 0) {
                    column.width = parseInt(colWidth);
                }

                if(additionalConfig != undefined || additionalConfig.length > 0) {
                    var addnlConfigObject =
                        Bm.util.ExtUtil.findConfigRecord('name', name, additionalConfig);
                    var colConfig = Ext.apply(column, addnlConfigObject);
                    //Column are added when hidden is false (boolean) or undefined
                    if(!colConfig.hidden){
                        headerArray.push(colConfig);
                    }
                } else {
                    headerArray.push(column);
                }
            }
        }
    }
    return headerArray;
};

//Utilities method here
Ext.namespace("Bm.util.ExtUtil");
Bm.util.ExtUtil.findConfigRecord = function(paramName, paramValue, configObjectArray) {
    var configObject = null;
    if(configObjectArray != undefined && configObjectArray.length > 0) {
        for(var i=0;i < configObjectArray.length;i++) {
            var name = configObjectArray[i][paramName];
            if(paramValue == name) {
                configObject = configObjectArray[i];
                break;
            }
        }
    }
    return configObject;
}




//Grid data related classes/methods here
Ext.namespace("Bm.data");
Bm.data.JsonReader = Ext.extend(Ext.data.JsonReader,{
    initComponent : function(config) {
        //Override user defined config options
        Ext.apply(this, config);
        Bm.data.JsonReader.superclass.initComponent.call(this);
    }
});

Bm.data.JsonReader.getFields = function(configFromServer,additionalConfig) {
    var fieldArray = new Array();
    if(configFromServer != undefined && configFromServer.length > 0) {
        for(var i=0;i < configFromServer.length;i++) {
            var isHiddenFld = configFromServer[i]['hiddenField'];
                var nameField = configFromServer[i]['dataIndex'];
                fieldArray.push(nameField);
        }
    }
    if(additionalConfig != undefined && additionalConfig.length > 0) {
        fieldArray = additionalConfig;
    }
    return fieldArray;
}

/**
 * Wrapper extension for Ext.data.Store
 */
Ext.define('Bm.data.JSONStore', {
	extend : 'Ext.data.Store',
	autoLoad : false,
	remoteSort: true,
	proxy: {
    	type: 'ajax',
        url: '',
        actionMethods : 'POST',
        reader: {
           type: 'json',
           root: 'dataList',
           totalProperty: 'total'
        }
    },
	constructor: function(config) {
   		Ext.Object.merge(this, config);
   		this.callParent();
   	},
    validateParams: function(store){
        if(Ext.isEmpty(Ext.get(store.storeListenerId))) { 
            Ext.Msg.alert({
                title: getLocalizedString('ExtStoreErrWndTitle'),
                msg: getLocalizedString('ExtStoreConfigListenerIdErrMsg'),
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.ERROR
            }); 
            return false;
        } else if(Ext.isEmpty(store.sortColumnName)) {
            Ext.Msg.alert({
                title: getLocalizedString('ExtStoreErrWndTitle'),
                msg: getLocalizedString('ExtStoreConfigSortColumnErrMsg'),
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.ERROR
            }); 
            return false;
        } else if(Ext.isEmpty(store.sortDirection)) {
            Ext.Msg.alert({
                title: getLocalizedString('ExtStoreErrWndTitle'),
                msg: getLocalizedString('ExtStoreConfigSortDirectionErrMsg'),
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.ERROR
            }); 
            return false;
        }
        return true;
    },

    listeners: {
        beforeload: function(store, options) {
            if (this.validateParams(store)) {
                var fs = Ext.Element.serializeForm(store.storeListenerId);
                var formValues = Ext.urlDecode(fs);
                                
                //For Pagination
                if (Ext.isEmpty(options.params)) {
                    options.params = {};
                }
                options.params['start'] = options.start;
                options.params['limit'] = options.limit;
                options.params['page'] = options.page;
                Ext.apply(options.params, formValues);
                options.params['pageSize'] = store.pageSize;                
                var currentPage = (options.params['start'] / options.params['limit']) + 1;
                /*FIXME: The page should start from 0 for dashboard list grid, since its handled like this in the server. */
                if (store.storeListenerId == 'dbList') {
                    currentPage -= 1; 
                }
                options.params['page'] =  currentPage + "";                

                //For Sorting
                if (!Ext.isEmpty(store.sorters.items)) {
                    options.params[store.sortColumnName] = store.sorters.items[0].property;
                    options.params[store.sortDirection] = store.sorters.items[0].direction.toLowerCase();
                } else if((!Ext.isEmpty(store.defaultSortColValue)) && (!Ext.isEmpty(store.defaultSortDirValue))) {
                    options.params[store.sortColumnName] = store.defaultSortColValue;
                    options.params[store.sortDirection] = store.defaultSortDirValue;
                }
                return true;
            } else {
                return false;
            }           
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

Ext.define('Bm.selection.CheckboxModel', {
    extend : 'Ext.selection.CheckboxModel',
    headerWidth: Bm.Constants.CheckboxModelHeaderWidth,
    checkOnly : Bm.Constants.checkOnlyEnableFlag,
	isFocused: true
});