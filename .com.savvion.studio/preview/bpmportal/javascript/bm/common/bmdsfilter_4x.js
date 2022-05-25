
// The data store containing the list of states
Ext.define('leftParenCmb',{
    extend: 'Ext.form.ComboBox',
    alias: ['widget.leftParen'],
    queryMode : 'local',
    width: 87,
    editable : false,
    disabled : true,
    triggerAction: 'all',
    emptyText : getLocalizedString('SelectOne'),
    name:'leftBracket',
    store: new Ext.data.ArrayStore({
        fields: [
            'value',
            'valueLabel'
        ],
        data: [['(', '('], ['((', '(('], ['(((', '((('],['((((', '(((('],['(((((', '((((('],['', '']]
    }),
    valueField: 'value',
    displayField: 'valueLabel',
    tpl:'<tpl for="."><div class="x-boundlist-item x-combo-list-item">&nbsp;{valueLabel}</div></tpl>',
    listeners : {
        'beforerender' : function(combo) {
            isWsStoreAvailable(Ext.getCmp('wsName'), combo);
        }
    }
});


Ext.define('dsCmb', {
    extend: 'Ext.form.ComboBox',
    alias:['widget.dsCmb'],
    queryMode : 'local',
    disabled : true,
    valueField: 'name',
    displayField: 'valueLabel',
    editable:false,
    emptyText : getLocalizedString('SelectOne'),
    triggerAction: 'all',
    name:'columnName',
    width : 120,
    initComponent:function() {
        // call parent init component
        dsCmb.superclass.initComponent.apply(this, arguments);
        // add custom events
        this.addEvents({'selectDataslot' : true});
    },
    onDataSlotSelect : function(combo,record,index){
        var operatorCmb = this.nextSibling();
        if(operatorCmb){
            operatorCmb.updateOperators(record[0].data.type);
        }
        var parent = this.findParentByType("[xtype='dsFilterRow']");
        if(parent){
            parent.updateValueField(combo,record,index);
        }
    },
    listeners : {
        'select' : function(combo,record,index){
            this.fireEvent('selectDataslot', combo,record,index);
        },
        'selectDataslot' : function(combo,record,index){
            this.onDataSlotSelect(combo,record,index);
        }
        ,
        'beforerender' : function(combo){/*
            var wsCombo = Ext.getCmp('wsName');
            if(!Ext.isEmpty(wsCombo)){
                if(!Ext.isEmpty(wsCombo.store)){
                    var dsStore  = getDataSlotStore(getLoadedStore());
                    combo.bindStore(dsStore);
                    combo.setDisabled(false);
                }
            }*/
        }
    }
});

Ext.define('operCmb', {
    extend: 'Ext.form.ComboBox',
    alias: ['widget.operCmb'],
    queryMode : 'local',
    width    :  120,
    editable : false,
    disabled : true,
    triggerAction: 'all',
    emptyText : getLocalizedString('SelectOne'),
    name:'operator',
    initComponent: function(config){
    this.store = this.getValidStore('STRING');
        Ext.apply(this, config);
        //Call super class
            operCmb.superclass.initComponent.apply(this, arguments);
    },
    valueField: 'value',
    displayField: 'valueLabel',
    listeners : {
        'beforerender' : function(combo){
            isWsStoreAvailable(Ext.getCmp('wsName'), combo);
        }
    },
    updateOperators: function(dsType) {
        //Remove All the items from the store
        this.store.removeAll();
        this.clearValue();
        this.bindStore(this.getValidStore(dsType));
    },
    getValidStore : function(dsType) {
        //Now add the items based on the dsType
        var operatorData = [];
        if(dsType == 'STRING' || dsType == 'URL') { //"=", "<>", "LIKE"
            operatorData = [['=', getLocalizedString('EQUALS')], ['<>', getLocalizedString('NOTEQUALS')], ['LIKE', getLocalizedString('LIKE')]];
        } else if(dsType == 'BOOLEAN') { //"="
            operatorData = [['=', getLocalizedString('EQUALS')]];
        } else if(dsType == 'LONG' || dsType == 'DOUBLE' || dsType == 'DECIMAL' || dsType == 'DATETIME') { //"<", "<=", "=", ">=", ">"
            operatorData = [['<', getLocalizedString('LESSTHAN')], ['<=', getLocalizedString('LESSTHANEQUALS')], ['=', getLocalizedString('EQUALS')], ['>=', getLocalizedString('GREATERTHANEQUALS')],['>', getLocalizedString('GREATERTHAN')]];
        } else { //"=", "<>", "LIKE"
            operatorData = [['=', getLocalizedString('EQUALS')], ['<>', getLocalizedString('NOTEQUALS')], ['LIKE', getLocalizedString('LIKE')]];
        }
        var cstore = new Ext.data.SimpleStore({
            fields: [
                'value',
                'valueLabel'
            ],
            data: operatorData
        });
        return cstore;
    }
});

Ext.define( 'dsValueFld',{
    extend: 'Ext.form.TextField',
    alias: ['widget.dsValue'],
    name:'value',
    flex: 1,
    clearValue : function(){
        this.reset();
    }
});

Ext.define('rightParenCmb', {
    extend : 'Ext.form.ComboBox',
    alias : ['widget.rightParen'],
    queryMode : 'local',
    editable : false,
    disabled : true,
    triggerAction: 'all',
    width    :  87,
    emptyText : getLocalizedString('SelectOne'),
    name:'rightBracket',
    store: new Ext.data.ArrayStore({
        model: Ext.define('rightParentModel', {
                    extend: 'Ext.data.Model',
                    fields: [
                        'value',
                        'valueLabel'
                    ]
                }),
        data: [[')', ')'], ['))', '))'], [')))', ')))'],['))))', '))))'],[')))))', ')))))'],['', '']]
    }),
    valueField: 'value',
    displayField: 'valueLabel',
    tpl:'<tpl for="."><div class="x-boundlist-item x-combo-list-item">&nbsp;{valueLabel}</div></tpl>',
    listeners : {
        'beforerender' : function(combo){
            isWsStoreAvailable(Ext.getCmp('wsName'), combo);
        }
    }

});

Ext.define('logicCmb', {
    extend: 'Ext.form.ComboBox',
    alias : ['widget.logic'],
    queryMode : 'local',
    width    :  87,
    editable : false,
    disabled : true,
    triggerAction: 'all',
    emptyText : getLocalizedString('SelectOne'),
    name:'logic',
    store: new Ext.data.SimpleStore({
       model: Ext.define('logicCmbModel', {
                    extend:'Ext.data.Model',
                    fields: [
                             'value',
                             'valueLabel'
                            ]
       }),
       data: [['and', getLocalizedString('AND')], ['or', getLocalizedString('OR')],['', '']]
    }),
    valueField: 'value',
    displayField: 'valueLabel',
    tpl:'<tpl for="."><div class="x-boundlist-item x-combo-list-item">&nbsp;{valueLabel}</div></tpl>',
    listeners : {
        'beforerender' : function(combo){
            isWsStoreAvailable(Ext.getCmp('wsName'), combo);
        }
    }
});

var getAddButtonCfg = function(rowId) {
    var addButton = Ext.create('Ext.Button',{
        iconCls : 'bmAdd',
        id : 'addDsRowbtn_'+rowId,
        scale:'small',
        border : false,
        disabled : true,
        tooltip:getLocalizedString('AddRow'),
        handler : function(b,e){
            if(!Ext.isEmpty(getVersionCombo().value)){
                var dsFilter = getDsFilterPanel();
                var parent = b.findParentByType("[xtype='dsFilterRow']");
                var indx = dsFilter.items.indexOf(parent);
                dsFilter.insertRow(indx+1);
            }
        },
        listeners : {
        'beforerender' : function(button) {
            isWsStoreAvailable(Ext.getCmp('wsName'), button);
        }
        }
    });
    return addButton;
};

var getDelButtonCfg = function(rowId) {
    var delButton = Ext.create('Ext.Button',{
        iconCls : 'bmDelete',
        id : 'delDsRowBtn_'+ rowId,
        border : false,
        disabled : true,
        //width:'1.5%',
        scale:'small',
        tooltip:getLocalizedString('DeleteRow'),
        handler : function(b,e){
           var dsFilter = getDsFilterPanel();
           var parent = b.findParentByType("[xtype='dsFilterRow']");
           dsFilter.deleteRow(parent);
        },
        listeners : {
        'beforerender' : function(button) {
            isWsStoreAvailable(Ext.getCmp('wsName'), button);
        }}
    });
    return delButton;
};

var getResetButtonCfg = function(rowId) {
    var resetButton = new Ext.Button({
        id:'resetDs_'+rowId,
        text : 'R',
        border : false,
        disabled : true,
        scale:'small',
        tooltip:getLocalizedString('Reset'),
        handler : function(b,e){
               var parent = b.findParentByType("[xtype='dsFilterRow']");
               parent.reset();
        },
        listeners : {
        'beforerender' : function(button) {
            isWsStoreAvailable(Ext.getCmp('wsName'), button);
        }}
    });
    return resetButton;
};

Ext.define('DataSlotFilter', {
    extend : 'Ext.form.Panel',
    alias : 'widget.DataSlotFilter',
    id : 'dataslotFilter',
    overlapHeader : true,
    layout : 'anchor',
    margin : '0 0 0 0',
    title   : getLocalizedString('advanced'),
    collapsible:true,
    collapsed : true,
    titleCollapse : true,
    animCollapse:true,
    autoScroll : true,
    frame: true,
    bodyBorder : false,
    disabled : true,
    initComponent: function(){
        Ext.apply(this, {

        });
        /*
            Following are custom listeners called during their
            respective operations
        */
        this.addEvents(
            'beforeaddrow',
            'afteraddrow',
            'beforedeleterow',
            'afterdeleterow',
            'beforeinsertrow',
            'afterinsertrow'
        );
        DataSlotFilter.superclass.initComponent.apply(this, arguments);
    },
    onRender: function(){
        DataSlotFilter.superclass.onRender.apply(this, arguments);
        /*new Ext.form.TextField({
            renderTo:this.getId(),
            id:'dynamicConditions',
            name:'dynamicConditions',
            hidden:true
        });*/
        /*new Ext.form.TextField({
            renderTo:this.getId(),
            id:'dynamicColumns',
            name:'dynamicColumns',
            hidden:true
        });*/
    },
    reset : function(){
        var count = this.items.length;
        var thisFilter = this;
        this.items.each(function(item,index,length) {
            if(count > 1 && index > 0){
                thisFilter.remove(item);
                 count--;
             }
         });
        if(count == 1){
            Bm.component.rowIdCounter = 1;
            this.addRow();
            var rowId = this.items.get(1).getRowId();
            var dsCombo = Ext.getCmp('dsCmb_'+rowId);

            if(Ext.isEmpty(getVersionCombo().value)){
                disableComponents(dsCombo, true);
            } else {
                disableComponents(dsCombo, false);
            }
        }

    },
    addRow : function(){
        this.fireEvent('beforeaddrow', this);
        var c = getFilterRowCfg();
        this.add(c);
        this.fireEvent('afteraddrow', this, c.rowId);
    },
    deleteRow : function(item){
        this.fireEvent('beforedeleterow', this, item);
        var count = this.items.length;
        if(count > 2){
            this.remove(item);
            count--;
            /*if(count == 3){
                //this.setAutoScroll(false);
            }*/
        }
        if(count == 2) {
            var rowId = this.items.get(1).getRowId();
            var delDsBtn = Ext.getCmp('delDsRowBtn_'+rowId);
            this.items.get(1).remove(7);
            delDsBtn.destroy();
            this.items.get(1).insert(7,getResetButtonCfg(rowId));
        }
        this.fireEvent('afterdeleterow', this);
    },
    insertRow : function(index){
        this.fireEvent('beforeinsertrow', this, index);
        var cfg = getFilterRowCfg();
        this.insert(index,cfg);

        //Bind the store for newly inserting ds combo and enable it.
        var rowId = this.items.get(index).getRowId();
        var dsCombo = Ext.getCmp('dsCmb_'+rowId);
            var wsCombo = Ext.getCmp('wsName');
            if(!Ext.isEmpty(wsCombo)){
                if(!Ext.isEmpty(wsCombo.store)){
                    var dsStore  = getDataSlotStore(getLoadedStore());
                    dsCombo.bindStore(dsStore);
                    dsCombo.setDisabled(false);
                }
            }

        //remove reset button if multiple rows.
        var rowId = this.items.get(1).getRowId();
        var resetBtn = Ext.getCmp('resetDs_'+rowId);
        var count = this.items.length;
        if(count > 2 && !Ext.isEmpty(resetBtn)) {
            this.items.get(1).remove(7);
            resetBtn.destroy();
            this.items.get(1).insert(7, getDelButtonCfg(rowId));
        }
        this.fireEvent('afterinsertrow', this, cfg.rowId);
    }
});


Ext.ns('Bm.component');
Bm.component.rowIdCounter = 0;

Ext.define( 'dsFilterRow', {
    extend: 'Ext.panel.Panel',
    alias : ['widget.dsFilterRow'],
    layout : {
        type:'hbox',
        align: 'middle'
    },
    frame : false,
    defaults: {
        margin : '0 1 0 1'
    },
    style:{marginBottom:'5px'},
    bodyBorder: false,
    bodyStyle: {
        background: 'transparent'
    },
    bodyCls: 'x-docked-noborder-top x-docked-noborder-right x-docked-noborder-bottom x-docked-noborder-left',
    rowId : 0,
    initComponent: function(){
        Ext.apply(this, {

        });
        dsFilterRow.superclass.initComponent.apply(this, arguments);
    },

    getRowId : function() {
        return this.rowId;
    },

    onRender: function(){
        dsFilterRow.superclass.onRender.apply(this, arguments);
    },

    reset: function() {
        this.items.each(function(item,index,length) {
            if(item.getXType() != 'button' && item.getXType() != 'panel' && item.getXType() != 'component'){
                item.clearValue();
            }
        });
        this.restore();
    },

    restore : function(){
        var dsCmb = this.items.get(1);
        if(Ext.isEmpty(getVersionCombo().value)){
            dsCmb.setDisabled(true);
        } else {
            dsCmb.setDisabled(false);
        }
        if(this.items.get(3).getXType() != 'dsValue'){
            var field = {xtype:'dsValue'};
            this.remove(3);
            this.insertField(3, field);
        }
    },

    insertField : function(index, field){
        this.insert(index, field);
        this.doLayout();
    },

    updateValueField : function(combo,record,index){
       this.remove(3,true);
       var cmp = renderValueField({
            flex : 1,
            type:record[0].data.type,
            uitype:record[0].data.uitype,
            data:record[0].data.validValues,
            dateFormat:getLocalizedString('dateFormat'),
            timeFormat:getLocalizedString('timeFormat'),
            decimalSeparator: getLocalizedDecimalSeparator(),
            name:'value',
            id:'dsValue_'+this.rowId
          }
        );
        this.insert(3,cmp);
        this.doLayout();
    }

});

function addRowTitles(){
    var rowTitles  = new Array();
    /*rowTitles.push({xtype:'box',autoEl : {  tag: 'div', html : '   ',cls:'rowhdr'}, cls:'rowhdr',width : 87, flex:1});
    rowTitles.push(getDSTitleCfg());
    rowTitles.push({xtype:'box',autoEl : {  tag: 'div', html :getLocalizedString('operator'),cls:'rowhdr'},width : 87, flex:1});
    rowTitles.push(getDSValueTitleCfg());
    rowTitles.push({xtype:'box',autoEl : {  tag: 'div', html :'',cls:'rowhdr'},width : 87, flex:1});
    rowTitles.push({xtype:'box',autoEl : {  tag: 'div', html :getLocalizedString('logic'),cls:'rowhdr'},width : 87, flex:1});
    rowTitles.push({xtype:'box',autoEl : {  tag: 'div', html :'   ',cls:'rowhdr'},width : '1.5%'});
    rowTitles.push({xtype:'box',autoEl : {  tag: 'div', html :'   ',cls:'rowhdr'},width : '1.5%'});*/
    var rowId = Bm.component.rowIdCounter++;
    var dsFilter = getDsFilterPanel();
    var row = new dsFilterRow({
        id : 'dsHeaderRow',
        style : 'background-color: #EFEFEF; display: none',
        items : rowTitles,
        rowId : rowId
    });
    dsFilter.add(row);
    dsFilter.doComponentLayout();
}

var getDSTitleCfg = function() {
    var cfg;
    if(Ext.isIE) {
        cfg = {
                xtype:'box',
                autoEl : {
                    tag: 'div', html : getLocalizedString('dataslot'),cls:'rowhdr'
                },
                width:'18%', flex:2
        };
    } else {
        cfg = {
                xtype:'box',
                autoEl : {
                    tag: 'div', html : getLocalizedString('dataslot'),cls:'rowhdr'
                },
                width:'13%', flex:2
        };
    }
    return cfg;
};

var getDSValueTitleCfg = function() {
    var cfg;
    if(Ext.isIE) {
        cfg = {
                xtype:'box',
                autoEl : {
                    tag: 'div',
                    html :getLocalizedString('value'),
                    cls:'rowhdr'
                },
                width:'44%', flex:3
        };
    } else {
        cfg = {
                xtype:'box',
                autoEl : {
                    tag: 'div',
                    html :getLocalizedString('value'),
                    cls:'rowhdr'
                },
                width:'58%', flex:3
        };
    }
    return cfg;
};

function getDSFilterHeader(){
    var dsHeader = Ext.getCmp('dsHeaderRow');
    return dsHeader;
}

function getFilterRowCfg(){
    var rowId = Bm.component.rowIdCounter++;
    var dsPanel = getDsFilterPanel();
    var count = getDsFilterPanel().items.length;
    var formItems  = new Array();
    formItems.push(new leftParenCmb({id:'leftParen_'+rowId}));
    var dataslotCmb = new dsCmb(getDsCmbCfg(rowId));
    var operatorCmb = new operCmb({id:'operCmb_'+rowId});
    formItems.push(dataslotCmb);
    formItems.push(operatorCmb);
    formItems.push(new dsValueFld(getDsValueFldCfg(rowId)));
    formItems.push(new rightParenCmb({id:'rightParenCmb_'+rowId}));
    formItems.push(new logicCmb({id:'logicCmb_'+rowId}));
    formItems.push(getAddButtonCfg(rowId));
    if(count == 1){
        formItems.push(getResetButtonCfg(rowId));
    } else{
        formItems.push(getDelButtonCfg(rowId));
    }
    var cfg = {xtype : 'dsFilterRow' , items : formItems, frame:false, rowId : rowId};
    return cfg;
}

var getDsCmbCfg = function(rowId) {
    var cfg;
    if(Ext.isIE) {
        cfg = {
                id:'dsCmb_'+rowId
        };
    } else {
        cfg = {
                id:'dsCmb_'+rowId
        };
    }
    return cfg;
};

var getDsValueFldCfg = function(rowId) {
    var cfg;
    if(Ext.isIE) {
        cfg = {
                id:'txtFld_'+rowId,
                disabled:true,
                flex: 1
        };
    } else {
        cfg = {
                id:'txtFld_'+rowId,
                disabled:true,
                flex : 1
        };
    }
    return cfg;
};

function getDsFilterPanel(){
    /**if(BmFilterView.isQuickSearch){
        return false;
    } **/
    var dsFilter =  Ext.getCmp('dsFilter');
    return dsFilter;
}

function preview(){
    var dsFilterPanel = getDsFilterPanel();
    if(dsFilterPanel){
        var rows = getDsFilterPanel().items;
        var previewStr = '';
        for(var i = 1; i < rows.length; i++){
            var fields = rows.get(i).items;
            for(var j = 0 ; j < fields.length -2; j++ ){
                previewStr = previewStr + getValue(fields.get(j).getId());
                previewStr = previewStr + ' ';
            }
        }
    }
    Ext.Msg.show({title : getLocalizedString('previewExpression'),msg : previewStr, width : 500, height : 100,buttons : Ext.MessageBox.OK});
}

function getValue(id){
    var value = '';
    var ctrl = Ext.getCmp(id);
    if(!Ext.isEmpty(ctrl)){
        value = ctrl.getRawValue();
    }
    return value;
}

function getFieldValue(id){
    var value = '';
    var ctrl = Ext.getCmp(id);
    if(!Ext.isEmpty(ctrl)){
        value = ctrl.getValue();
    }
    return value;
}

var prepareDsFilterJson = function() {
    var json = [];
    var dsFilterPanel = getDsFilterPanel();
    if(dsFilterPanel && Bm.view.isDsFilterPanelExpanded()){
        var rows = dsFilterPanel.items;
        if(rows){
            for(var idx = 1; idx < rows.length; idx++){
                var fields = rows.get(idx).items;
                var fieldJson = {};
                for(var fldIdx = 0 ; fldIdx < fields.length -2; fldIdx++ ){
                    var fld = fields.get(fldIdx);
                    if(fld.getXType() == 'bmdatetime' || fld.getXType() == 'bmdsdatetime'){
                        var value = Ext.encode({'longValue' : '' + getValue(fld.getId()), 'rangeValue' : ''});
                    }else{
                        var value = getFieldValue(fld.getId());
                    }
                    var name = fld.getName();
                        if(!Ext.isEmpty(value)){
                            fieldJson[name] = value;
                            //adding isGlobal
                            if("columnName" == name){
                                var comboStore= fld.store;
                                var isGlobal = false;
                                var isTaskCol = false;
                                var columnType = "String";
                                if(comboStore) {
                                    comboStore.each(
                                            function(record){
                                                if(record.data.name == value) {
                                                    isGlobal = record.data.isGlobal;
                                                    isTaskCol = record.data.isTaskCol;
                                                    columnType = record.data.type;
                                                }
                                            }
                                    );
                                }
                                fieldJson.isGlobal = isGlobal;
                                fieldJson.isTaskCol = isTaskCol;
                                fieldJson.columnType = columnType;
                            }
                        }
                }
                json[idx-1] = fieldJson;
            }
        }
    }
    //var dynConditions = Ext.getCmp('dynamicConditions');
    //if(dynConditions){
    //  dynConditions.setValue(Ext.encode(json));
    //}

    return Ext.encode(json);
};

function prepareDynColJson(records){
    // get task col ds
    var dynColjson = [];
    Ext.getCmp('dynamicColumns').setValue(Ext.encode(dynColjson));
    if(records){
        for(i = 0;i < records.length;i++){
            var colJson = {};
            var name = records[i].data.name;
            var type = records[i].data.type;
            var isGlobal = records[i].data.isGlobal;
            var isTaskCol = records[i].data.isTaskCol;
            if(isGlobal){
                var dsType = 'GDataSlot';
            }else{
                var dsType = 'IDataslot';
            }
            if(isTaskCol){
                colJson = {'columnName' : name,'columnType' : type,'tableAlias':dsType};
                dynColjson.push(colJson);
            }
        }
    }
    Ext.getCmp('dynamicColumns').setValue(Ext.encode(dynColjson));
}

function isWsStoreAvailable(wsCombo,combo){
    if(!Ext.isEmpty(wsCombo) && !Ext.isEmpty(wsCombo.store)){
        combo.setDisabled(false);
    }
}

function disableDsFilterItems() {
    if(getDsFilterPanel() && getDsFilterPanel().items) {
        getDsFilterPanel().reset();
        handleDsFilterCombo("leftParen_");
        handleDsFilterCombo("dsCmb_");
        handleDsFilterCombo("operCmb_");
        handleDsFilterCombo("dsValue_");
        handleDsFilterCombo("rightParenCmb_");
        handleDsFilterCombo("logicCmb_");
        handleDsFilterCombo("addDsRowbtn_");
        handleDsFilterCombo("delDsRowBtn_");
        handleDsFilterCombo("resetDs_");
    }
}

function disableDsFilterPanel() {
    //Advanced Filter Panel is disabled (if it is enabled.) while selecting/changing application.
    var dsFilterPanel = BmFilterView.getDataslotPanel();
    dsFilterPanel.collapse();
    if (!dsFilterPanel.isDisabled()) {
        dsFilterPanel.setDisabled(true);
    }
}

Ext.namespace("Bm.view");
Bm.view.validateDsFilter = function () {
    var isNotEmptyRow = function(rowId) {
        var leftParenthesisFld = Ext.getCmp('leftParen_' + rowId);
        var dsNameCombo = Ext.getCmp('dsCmb_' + rowId);
        var operatorFld = Ext.getCmp('operCmb_' + rowId);
        var dsValueFld = Ext.getCmp('dsValue_' + rowId);
        var operatorFld = Ext.getCmp('operCmb_' + rowId);
        var rightParenthesisFld = Ext.getCmp('rightParenCmb_' + rowId);
        var logicFld = Ext.getCmp('logicCmb_' + rowId);

        if(Ext.isEmpty(leftParenthesisFld.getValue())) {
            if(Ext.isEmpty(dsNameCombo.getValue())) {
                if(Ext.isEmpty(operatorFld.getValue())) {
                    //Checking for this field's existence since it will be created once ds name is selected
                    if(Ext.isEmpty(dsValueFld)) {
                        if(Ext.isEmpty(rightParenthesisFld.getValue())) {
                            if(Ext.isEmpty(logicFld.getValue())) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    };

    var isUrlHasSplChars = function(value) {
        return Bm.VTypes.isUrlHasSplChars(value);
    };

    var isDsValueHasSplChars = function(value) {
        return Bm.VTypes.isValidDsValue(value);
    };

    var isInvalidDsValue = function (dsNameCombo, dsValueFld) {
        var comboStore = dsNameCombo.store;
        var dsName = dsNameCombo.getValue();
        var isError = false;
        if(Ext.isEmpty(dsName)) {
            Bm.component.showMsg(getLocalizedString('error'),
                              getLocalizedString('invalidDsName'),
                              dsNameCombo.getId(),
                              Ext.MessageBox.ERROR);
            return true;
        }
        var dsType = '';
        var dsValue = '';
        if (dsValueFld.getXType() == 'bmdatetime' || dsValueFld.getXType() == 'bmdsdatetime') {
            if(!dsValueFld.isValid()) {
                Bm.component.showMsg(getLocalizedString('error'),
                              getLocalizedString('invalidDate'),
                              dsValueFld.getId(),
                              Ext.MessageBox.ERROR);
                return true;
            }
            dsValue = dsValueFld.getRawValue();
        } else {
            dsValue = dsValueFld.getValue();
        }
        if(Ext.isEmpty(dsValue)) {
            Bm.component.showMsg(getLocalizedString('error'),
                              getLocalizedString('invalidDsValue'),
                              dsValueFld.getId(),
                              Ext.MessageBox.ERROR);
            return true;
        }

        if (comboStore) {
            comboStore.each(
              function (record) {
                  var dsType = record.data.type.toLowerCase();
                  var name = record.data.name;
                  if (dsName == name) {
                      if (dsType == 'url') {
                          if(isUrlHasSplChars(dsValue)){
                              Bm.component.showMsg(getLocalizedString('error'),
                                              getLocalizedString('SpecialCharsNotAllowedUrl'),
                                              dsValueFld.getId(),
                                              Ext.MessageBox.ERROR);
                              isError = true;
                              return false;
                          }
                      } else {
                          if (isDsValueHasSplChars(dsValue)) {
                              Bm.component.showMsg(getLocalizedString('error'),
                                              getLocalizedString('SpecialCharsNotAllowed'),
                                              dsValueFld.getId(),
                                              Ext.MessageBox.ERROR);
                              isError = true;
                              return false;
                          }
                      }
                  }
              }
            );
            if(isError) {
                return true;
            }

            if (isInvalidDsNameValue(dsName, dsValue, dsValueFld)) {
                return true;
            }
        }

        return false;
    };

    var isInvalidDsNameValue = function (dsName, dsValue, dsValueFld) {
        if (dsName == "InstanceID" && !Ext.isNumber(Ext.String.trim(dsValue))) {
            Bm.component.showMsg(getLocalizedString('error'),
                            getLocalizedString('filterAlert5'),
                            dsValueFld.getId(),
                            Ext.MessageBox.ERROR);
            return true;
        }
        if (dsName == "Creator" && Ext.String.trim(dsValue) == "") {
            Bm.component.showMsg(getLocalizedString('error'),
                            getLocalizedString('filterAlert6'),
                            dsValueFld.getId(),
                            Ext.MessageBox.ERROR);
            return true;
        }
        if (dsName == 'ApplicationName' && Ext.String.trim(dsValue) == "") {
            Bm.component.showMsg(getLocalizedString('error'),
                            getLocalizedString('filterAlert11'),
                            dsValueFld.getId(),
                            Ext.MessageBox.ERROR);
            return true;
        }
        return false;
    };

    var isInvalidOperator = function(rowId) {
        var operatorFld = Ext.getCmp('operCmb_' + rowId);
        if(Ext.isEmpty(operatorFld.getValue())) {
            Bm.component.showMsg(getLocalizedString('error'),
                            getLocalizedString('invalidOperator'),
                            'operCmb_' + rowId,
                            Ext.MessageBox.ERROR);
            return true;
        }
        return false;
    };

    var dsFilterPanel = getDsFilterPanel();
    if (dsFilterPanel) {
        var logic = '';
        var leftPCount = 0; // left paranthesis count
        var rightPCount = 0; // right paranthesis count
        var newExpression = false;
        var prevLogic = null;
        var filterRows = dsFilterPanel.items;
        var isError = false;
        var EMPTY = "EMPTY";
        var isDsPanelEmpty = true;
        if (filterRows) {
            Ext.each(filterRows.items, function (item, idx, filterRowItems) {
                if(item && item.getId() != 'dsHeaderRow'){
                    var rowId = item.getRowId();
                    var leftParenthesisFld = Ext.getCmp('leftParen_' + rowId);
                    var dsNameCombo = Ext.getCmp('dsCmb_' + rowId);
                    var operatorFld = Ext.getCmp('operCmb_' + rowId);
                    var dsValueFld = Ext.getCmp('dsValue_' + rowId);
                    var rightParenthesisFld = Ext.getCmp('rightParenCmb_' + rowId);

                    /*1. Empty Row check*/
                    if(isNotEmptyRow(rowId)) {
                        isDsPanelEmpty = false;
                        /*2. DS Value validations*/
                        if (isInvalidDsValue(dsNameCombo, dsValueFld)) {
                            isError = true;
                            return false;
                        }

                        /*3. Operator field validation*/
                        if(isInvalidOperator(rowId)) {
                            isError = true;
                            return false;
                        }

                        /*4. Paranthesis Validations*/

                        // get left paranthesis
                        var leftP = leftParenthesisFld.getValue();
                        // parse the leftparanthesis
                        if (!Ext.isEmpty(leftP)) {
                            newExpression = true;
                            leftPCount = leftPCount + Ext.String.trim(leftP).length;
                        }

                        // get the right paranthesis
                        var rightP = rightParenthesisFld.getValue();
                        // parse the rightparanthesis
                        if (!Ext.isEmpty(rightP)) {
                            rightPCount = rightPCount + Ext.String.trim(rightP).length;
                            //Check to validate if the right parenthesis is added without a left parenthesis
                            if (!newExpression) {
                                Bm.component.showMsg(getLocalizedString('error'),
                                            getLocalizedString('invalidParenthesis'),
                                            rightParenthesisFld.getId(),
                                            Ext.MessageBox.ERROR);
                                isError = true;
                                return false;
                            }
                        }

                        /*5. Logic Field Validation*/
                        var logicFld = Ext.getCmp('logicCmb_' + rowId);
                        var logic = logicFld.getValue();
                        //Checking if previous row has its logical op selected
                        if(EMPTY == prevLogic){
                            Bm.component.showMsg(getLocalizedString('error'),
                                            getLocalizedString('invalidLogic'),
                                            logicFld.getId(),
                                            Ext.MessageBox.ERROR);
                            isError = true;
                            return false;
                        }
                        if(Ext.isEmpty(logic)) {
                           logic = EMPTY;
                        }
                        prevLogic = logic;
                    }
                }
            }, this);

            if(isDsPanelEmpty) {
                return true;
            }

            if(isError) {
                return false;
            }

            /*4. Validating the left and right parentheses count*/
            if (rightPCount != leftPCount) {
                Bm.component.showMsg(getLocalizedString('error'),
                                            getLocalizedString('invalidParenthesis'),
                                            //'dsFilterEnclPanel',
                                            'dsFilter',
                                            Ext.MessageBox.ERROR);
                isError = true;
                return false;
            }

            /*5. Logic Field Validation*/
            //Last Rows's logic field should always be empty
            if(EMPTY != prevLogic) {
                Bm.component.showMsg(getLocalizedString('error'),
                            getLocalizedString('invalidLastRowLogic'),
                            //'dsFilterEnclPanel',
                            'dsFilter',
                            Ext.MessageBox.ERROR);
                isError = true;
                return false;
            }
        }
    }
    return true;
};