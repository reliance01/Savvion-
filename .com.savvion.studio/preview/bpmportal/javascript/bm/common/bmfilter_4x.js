/*
Ext.override(Ext.form.ComboBox, {
    // The default labelWidth for combobox is 100.
    labelWidth: Bm.Constants.Task.ComboBoxLabelWidth,
    // Workaround for TR#RPM00036671 - Fixed in Extjs 4.1 - Need to remove this code when migrating to 4.1
    labelableRenderTpl: [
        '<tpl if="!hideLabel && !(!fieldLabel && hideEmptyLabel)">',
            '<label id="{id}-labelEl" class="{labelCls}"',
                '<tpl if="labelStyle"> style="{labelStyle}"</tpl>>',
                '<tpl if="fieldLabel">{fieldLabel}{labelSeparator}</tpl>',
            '</label>',
        '</tpl>',
        '<div class="{baseBodyCls} {fieldBodyCls}" id="{id}-bodyEl" role="presentation">{subTplMarkup}</div>',
        '<div id="{id}-errorEl" class="{errorMsgCls}" style="display:none"></div>',
        '<div class="{clearCls}" role="presentation"><!-- --></div>',
        {  
            compiled: true,
            disableFormats: true
        }
    ]
});
*/
Ext.namespace("Bm.view");

//On page onload this flag is set to true for setting default values to the components of query panel
var getValsFrmSession = true;

Bm.view.FilterUI = function(config) {
    var ds = config.data;
    if(Ext.isEmpty(ds)){
        //alert("Data store is empty");
        return;
    }
    var values = ds.getRange();
    this.isMgr = values[0].get('isManager');

    var filters = new Array();
    filters.push(getAppFilterCfg(values));
    filters.push(getVersionFilterCfg(values));
    filters.push(getWorkStepFiterCfg(values));
    filters.push(getPerfQueueCfg(values));
    filters.push(getPriorityFilterCfg(values));
    filters.push(getStartDateFilterCfg(values));
    filters.push(getStatusFilterCfg(values));
    filters.push(getDueEndDateFilterCfg(values));

    this.queryPanel = Ext.create('Ext.form.FieldContainer',{
        bodyBorder : false,
        frame: false,
        layout: {
            type: 'table',
            columns: 2,
            tableAttrs: {
	         style: {
	            width: '100%'
	         }
            }
        },
        items: filters,
        region:'north',
        listeners : {
            afterrender : function(combo){
                //setFilterPanelCmps();
            }
        }
    });

    function setFilterPanelCmps(){

    //This function is called on mytasklist page onload to initialize filter components to
    // their previous value or default value.

        var selectedApp =  getSelectedApp(values);
        if(selectedApp == undefined || selectedApp == ""){
            selectedApp = getDefaultApp(values);
        }
        if(selectedApp != undefined ) {
            var combo = getApplicationCombo();
            combo.setValue(selectedApp);
            combo.fireEvent('select');
        }

        var selectedVersion =  getSelectedVersion(values);
        if(selectedVersion == undefined || selectedVersion == ""){
            selectedVersion = getDefaultVersion(values);
        }
        if(selectedVersion != undefined && selectedVersion != "") {
            var combo = getVersionCombo();
            combo.setValue(selectedVersion);
            combo.fireEvent('select');
        }

        var selectedPriority =  getSelectedPriority(values);
        if(selectedPriority != undefined && selectedPriority != "") {
            var combo = getPriorityCombo();
            combo.setValue(selectedPriority);
        }

        loadFilterData(selectedApp,selectedVersion,values);
        var selectedTaskFilter = getSelectedTaskFilter(values);
        if(selectedTaskFilter == undefined || selectedTaskFilter == ""){
            selectedTaskFilter = getDefaultFilter(values);
        }
        if(selectedTaskFilter != undefined) {
            var combo = getFilterCombo();
            combo.setValue(selectedTaskFilter);
        }
		
		//Advanced Filter Panel will get disabled after rendering the Search Panel.
        if(Ext.isEmpty(selectedVersion)) {
            BmFilterView.getDataslotPanel().setDisabled(true);
        }

    }

     var dsFilter = new DataSlotFilter({
        id              : 'dsFilter',
        listeners: {
            afterrender: function(thisElt){
                thisElt.resizer = Ext.create('Ext.resizer.Resizer', {
                    el : thisElt.getEl(),
                    dynamic: true,
                    handles: 's',
                    pinned: false
                });
                thisElt.resizer.on('resizedrag', thisElt.resizeDragHandler, thisElt);
                addRowTitles();
                this.addRow();
            },
            /*
                This is a custom listener which will get fired, once the user adds
                a new Dataslot filter row in advanced search tab.
            */
            afterinsertrow : function(dsFilter) {
                /* FIXME: - doLayout */
                getCenterContainer().doLayout();
            },
            /*
                This is a custom listener which will get fired, once the user deletes
                a Dataslot filter row in advanced search tab.
            */
            afterdeleterow : function(dsFilter) {
                /* FIXME: - doLayout */
                getCenterContainer().doLayout();
            },
            collapse : function(panel) {
                Ext.getCmp('dsPreviewBtn').hide();
                /* FIXME: - doLayout */
                getCenterContainer().doLayout();
            },
            expand : function(panel) {
                Ext.getCmp('dsPreviewBtn').show();
                /* FIXME: - doLayout */
                getCenterContainer().doLayout();
            }
        },
        resizeHandler: function(resizeableObj, width, height, rawWidth, rawHeight){
            dsFilter.setSize(width, height);
            this.doLayout();
        },
        resizeDragHandler: function(resizeableObj, width, height, rawWidth, rawHeight){
            dsFilter.setHeight(height);
            this.doLayout();
        }

    });
    this.dataslotPanel = dsFilter;

    this.getQueryPanel = function() {
        return this.queryPanel;
    };

    this.getDataslotPanel = function() {
        return this.dataslotPanel;
    };


    var prepareButtons = function(config) {
        var buttonsArray = new Array();

        buttonsArray.push(getFilterComboCfg(values));

        buttonsArray.push({
            text : getLocalizedString('Search'),
            id : 'searchBtn',
            handler : function() {
                var taskGrid = getTaskGrid();
                var startDate = getStartDate().getValue();
                var dueDate = getDueDate().getValue();
                if(startDate != '' && dueDate != ''){
                    if(startDate.getTime() > dueDate.getTime()){
                        var title = getLocalizedString('validDateTitle');
                        var message =  getLocalizedString('validDateMsg');
                        Ext.Msg.alert(title, message);
                        return false;
                    }
                }
                if(Bm.view.isDsFilterPanelExpanded()
                            && !Bm.view.validateDsFilter()) {
                    return false;
                }
                var formValues = getTaskFilterForm().getForm().getValues();
                formValues.action = 'Search';
                submitForm({params:formValues});
            }
        });
        buttonsArray.push({
            text : getLocalizedString('NextAvailable'),
            id : 'nextAvailBtn',
            handler : function() {
                var formValues = getTaskFilterForm().getForm().getValues();
                formValues.action = 'NextAvailable';
                submitForm({params:formValues});
            }
        });
        buttonsArray.push({
            text : getLocalizedString('Reset'),
            handler : function() {
                resetForm(getTaskFilterForm().getForm());
            }
        });
        buttonsArray.push({
                text : getLocalizedString('preview'),
                id  : 'dsPreviewBtn',
                hidden  : true,
                handler : function() {
                    preview();
                }
            });
        return buttonsArray;
    }
    this.render = function(config){

        this.tabSearchButtons = prepareButtons(config);
        this.isQuickSearch = false;
        this.tabSearchPanel = [this.queryPanel, this.dataslotPanel];        
        var f = Ext.create('Ext.form.Panel',{
        id:'filterEnclPanel',
        title: getLocalizedString('SearchTasks'),
        //id:'taskFilterPanel',
        collapsible: false, // If its 'true' Ext-js takes care of handlers which doesn't allow us to modify tooltips.
        frame : true,
        bodyBorder : false,
        defaults: {
            // applied to each contained item
            msgTarget: 'side'
        },
        layout : 'anchor',
        items: this.tabSearchPanel,
        labelAlign: 'right',
        buttonAlign: 'center',
        buttons: this.tabSearchButtons,
        minWidth: Bm.Constants.Task.SearchPanelMinWidth,
        listeners : {
            'collapse' : function(panel, eOpts) {
                /* FIXME: - doLayout */
                getCenterContainer().doLayout();
			    if (navigator.appName == 'Microsoft Internet Explorer' ||  !!(navigator.userAgent.match(/Trident/) || navigator.userAgent.match(/rv:11/)) || (typeof $.browser !== "undefined" && $.browser.msie == 1))
				{
					var resizeEvent = window.document.createEvent('UIEvents'); 
					resizeEvent .initUIEvent('resize', true, false, window, 0); 
					window.dispatchEvent(resizeEvent);
				}else{
				    window.dispatchEvent(new Event('resize'));
				}
            },
            'expand' : function(panel, eOpts) {
                /* FIXME: - doLayout */
                getCenterContainer().doLayout();
            },
            afterrender : function(panel){
                setFilterPanelCmps();
                if(getValsFrmSession) {
                    var selectedTaskStatus =  getSelectedTaskStatus(values);
                        var combo = Ext.getCmp('st');  //Cound not getStatusCombo mtd to get task status combo
                    if(selectedTaskStatus != undefined && selectedTaskStatus != "") {
                        combo.setValue(selectedTaskStatus);
                        combo.fireEvent('select');
                    }else{
                        combo.setValue('ASSIGNED');
                    }
                    //panel.doLayout();
                    var selectedPerformer =  getSelectedPerformer(values);
                    var selectedPerformerType =  getSelectedPerformerType(values);
                    if(selectedPerformerType != undefined && selectedPerformerType != "") {
                        var combo = getPerTypeCombo();
                        combo.setValue(selectedPerformerType);
                        onPerformerTypeSelect(combo,selectedPerformerType);

                        if(selectedPerformerType == 'user') {
                            getUserField().setValue(selectedPerformer);
                        } else if(selectedPerformerType == 'queue') {
                            getQueueCombo().setValue(selectedPerformer);
                        } else if(selectedPerformerType == 'creator') {
                            getCreatorField().setValue(selectedPerformer);
                        }

                    }
                    getValsFrmSession = false; //this flag is set to false once complete Form panel is loaded on page onload
                }
                //panel.doLayout(); Commented these line and doLayout() has been called inside the loop as fix for the bug TR#RPM00025938
            }
        },
        tools: [{
             id:'toggle',
             qtip: getLocalizedString('collapse'),
             handler:function(event, toolEl, panel) {
                f.toggleCollapse();
                if(toolEl.attributes[4].value == getLocalizedString('collapse')) {
                    toolEl.attributes[4].value = getLocalizedString('expand');
                } else {
                    toolEl.attributes[4].value = getLocalizedString('collapse');
                }
             }
        }]
    });

        this.filterPanel = f;
        return f;
    };

    this.getFilterPanel = function(){
        return this.filterPanel;
    };

    this.isQuickSearch = function(){
        return this.isQuickSearch;
    }

    this.isManager = function(){
        return this.isMgr;
    }

};

Bm.view.setCardLayoutActiveItem = function(cardLayoutCmp, itemNo) {
    if(cardLayoutCmp) {
        if(typeof cardLayoutCmp.getLayout() == "object") {
            cardLayoutCmp.getLayout().setActiveItem(itemNo);
        }
    }
}

function getStartDateTypeCombo(){
    return Ext.getCmp('startDateType');
}

function getDueDateTypeCombo(){
    return Ext.getCmp('dueDateType');
}

function getEndDateTypeCombo(){
    return Ext.getCmp('endDateType');
}

function resetDateFields() {
   getStartDateTypeCombo().setValue('dateField');
   onDateTypeSelect(getStartDateTypeCombo(), 'dateField');
   getDueDateTypeCombo().setValue('dateField');
     onDateTypeSelect(getDueDateTypeCombo(), 'dateField');
     getEndDateTypeCombo().setValue('dateField');
     onDateTypeSelect(getEndDateTypeCombo(), 'dateField');
}

function resetForm(form){
   resetDateFields();
     var emptyValues = {application : '', templateName : '', priority : '',
                        status : 'ASSIGNED',startDateType : 'dateField',dueDateType: 'dateField',
                        endDateType : 'dateField',
             startDate : '',dueDate: '',endDate: '',
             startDateRange : '',dueDateRange: '',endDateRange: '',
             queue:'',performer : '',creator:''};
     form.setValues(emptyValues);
     getApplicationCombo().setDisabled(false);
     var wsCmb = getWorkStepCombo();
     wsCmb.clearValue();
     wsCmb.setDisabled(true);
     var versionCmb = getVersionCombo();
     versionCmb.setDisabled(true);

     getFilterCombo().setValue('');
     disableDsFilterPanel();
     disableDsFilterItems();
}

function prepareParams(){
    var form = getTaskFilterForm();
    var formValues= form.getForm().getValues();
    formValues.dynamicConditions = prepareDsFilterJson();
  formValues = prepareDateParams(formValues);
  formValues = prepareFilterParams(formValues);
  //formValues = preparePriorityParam(formValues);
  return formValues;
}

var preparePriorityParam = function(formValues) {
    var priority = Bm.view.getKeyValue(formValues, "priority", "");
    if(!Ext.isEmpty(priority) && Ext.isArray(priority)) {
       priority = priority.join(',');
    }
    formValues.priority = priority;
    return formValues;
}

var prepareDateParams = function(formValues) {
   if(!Ext.isEmpty(formValues)) {
       var stDate = Bm.view.getKeyValue(formValues, "startDate", "");
       var stDateRange = Bm.view.getKeyValue(formValues, "startDateRange", "");
       formValues.startDate = Ext.encode({'longValue': stDate,'rangeValue' : stDateRange});
       formValues.startDateRange = null;
       var duDate = Bm.view.getKeyValue(formValues, "dueDate", "");
       var duDateRange = Bm.view.getKeyValue(formValues, "dueDateRange", "");
       formValues.dueDate = null;
       if(duDateRange == 'ALL_OVERDUE'){
            formValues.dueDate = Ext.encode({'longValue': ''+new Date().getTime() , 'rangeValue':''});
       }else{
            formValues.dueDate = Ext.encode({'longValue':duDate, 'rangeValue':duDateRange});
       }
       formValues.dueDateRange = null;
       var enDate = Bm.view.getKeyValue(formValues, "endDate", "");
       var enDateRange = Bm.view.getKeyValue(formValues, "endDateRange", "");
       formValues.completedDate = Ext.encode({'longValue':enDate, 'rangeValue':enDateRange});
       formValues.endDateRange = null;
   }

   return formValues;
}

Bm.view.getKeyValue = function(object, key, defaultValue) {
    if(!Ext.isEmpty(object)) {
       var value = object[key];
       if(!Ext.isEmpty(value)) {
           return value;
       }
    }
    return defaultValue;
}

function submitForm(config){
    var params = config.params;
    var status = params.status;
    Ext.getCmp('bulkOpPanel').removeAll();
    var action = params.action;
    if(action == 'NextAvailable') {
        Bm.Tasks.getNextAvailableTask(config);
    } else {
        if(Ext.util.Format.lowercase(status) == 'completed'
            || Ext.util.Format.lowercase(status) == 'collaborativeassigned') {
            //do nothing since the items are removed  already.
        } else {
            var ct = Ext.getCmp('bulkOpPanel');
            ct.add(Bm.Tasks.getBulkOpPanelItems(config));
            ct.doLayout();
        }
        refreshGrid(config);
    }
}

function storeListener(store,options){
var formValues = prepareParams();

    //For Pagination
    if (Ext.isEmpty(options.params)){
        options.params = {};
    }
    options.params['start'] = options.start;
    options.params['limit'] = options.limit;
    options.params['page'] = options.page;

    Ext.apply(options.params,formValues);
    store.lastOptions = options;
    //options.params['start'] = 4;
    //options.currentPage = 3;
    if(!(Bm.util.isLocalStore)) {
        options.params['page'] = ((options.params['start'] / options.params['limit']) + 1) + "";
    }

    if (!Ext.isEmpty(store.sorters.items)){
        options.params['col'] = store.sorters.items[0].property;
        options.params['dir'] = store.sorters.items[0].direction;
    } 
    // The following else block is commented for fix CR#RPM00041291/PR#RPM00041292 (Penske issue)
    // No need to pass default sort column and direction, taken care in service class
    /*else {
        options.params['dir'] = 'ASC';
        options.params['col'] = 'duedate';
    }*/
    return true;
}

function getTaskFilter(){
    var taskFilter = Ext.getCmp('taskFilter');
    return taskFilter;
}

function getTaskFilterForm(){
    var taskFilterForm = Ext.getCmp('filterEnclPanel');
    return taskFilterForm;
}

function onGetGridSettingsFailure(){
    alert("Error occurred while getting the grid settings ");
}


function getAppFilterCfg(values){
        return {
            xtype:'bmappfilter',
            id : 'app',
            name : 'application',
            store : getAppStore(values),
            listeners: {
                select: { fn:
                    function(combo, value)
                    {
                        var versionCmb = getVersionCombo();
                        versionCmb.clearValue();
                        versionCmb.store.clearFilter();
                        if(Ext.isEmpty(this.value)){
                            var pattern = "(^\s*$)";
                            var re = new RegExp(pattern);
                            versionCmb.store.filter('app',re);
                            versionCmb.setValue('');
                            disableWsCombo();
                        }else{
                            var appValue = this.value;
                            var pattern = "(^"+appValue+"$)|(^\s*$)";
                            var re = new RegExp(pattern);
                            versionCmb.store.filter('app',re) ;
                            versionCmb.setDisabled(false);
                            versionCmb.setValue('');
                            disableWsCombo();
                        }
                        loadFilterData(this.value,'',values);
                        disableDsFilterPanel();
                        disableDsFilterItems();
                    }
                }
            }
        };
}

function getDefaultApp(values){
    if(!Ext.isEmpty(values)){
        var defaultApp = values[0].get('defaultApp');
        if(!Ext.isEmpty(defaultApp) && defaultApp != '-1'){
            return defaultApp;
        }
    }
    return '';
}


 function getSelectedApp(values){
    if(!Ext.isEmpty(values)){
        var selectedApp = values[0].get('selectedApp');
        if(!Ext.isEmpty(selectedApp) && selectedApp != '-1'){
            return selectedApp;
        }
    }
    return '';
}


function getSelectedVersion(values){
    if(!Ext.isEmpty(values)){
        var selectedVersion = values[0].get('selectedVersion');
        if(!Ext.isEmpty(selectedVersion) && selectedVersion != '-1'){
            return selectedVersion;
        }
    }
    return '';
}


function getSelectedWorkstep(values){
    if(!Ext.isEmpty(values)){
        var selectedWorkstep = values[0].get('selectedWorkstep');
        if(!Ext.isEmpty(selectedWorkstep) && getValsFrmSession){
            return selectedWorkstep;
        } else {
            return '';
        }
    }
    return '';
}


function getSelectedPriority(values){
    if(!Ext.isEmpty(values)){
        var selectedPriority = values[0].get('selectedPriority');
        if(!Ext.isEmpty(selectedPriority) && selectedPriority != '-1'){
            return selectedPriority;
        }
    }
    return '';
}

function getSelectedTaskStatus(values){
    if(!Ext.isEmpty(values)){
        var selectedTaskStatus = values[0].get('selectedTaskStatus');
        if(!Ext.isEmpty(selectedTaskStatus) && selectedTaskStatus != '-1'){
            //return Ext.util.Format.lowercase(selectedTaskStatus);
            return selectedTaskStatus;
        }
    }
    return '';
}

function getSelectedPerformerType(values){
    if(!Ext.isEmpty(values)){
        var getSelectedPerformerType = values[0].get('selectedPerformerType');
        if(!Ext.isEmpty(getSelectedPerformerType) && getSelectedPerformerType != '-1'){
            return getSelectedPerformerType;
        }
    }
    return '';
}

function getSelectedPerformer(values){
    if(!Ext.isEmpty(values)){
        var selectedPerformer = values[0].get('selectedPerformer');
        if(!Ext.isEmpty(selectedPerformer) && selectedPerformer != '-1'){
            return selectedPerformer;
        }
    }
    return '';
}

function getSelectedTaskFilter(values){
    if(!Ext.isEmpty(values)){
        var selectedTaskFilter = values[0].get('selectedTaskFilter');
        if(!Ext.isEmpty(selectedTaskFilter) && selectedTaskFilter != '-1'){
            return selectedTaskFilter;
        }
    }
    return '';
}

function disableWsCombo(){
    var wsCmb = getWorkStepCombo();
    if(wsCmb && wsCmb.store){
        wsCmb.clearValue();
        wsCmb.setDisabled(true);
    }
}

function getVersionFilterCfg(values){
                return {
                    xtype : 'bmtemplatefilter',
                    id : 'version',
                    value : getSelectedVersion(values),
                    name : 'templateName',
                    store : getVersionStore(values),
                    listeners: {
                        select: {
                            fn: function(combo, value)
                                {
                                    var wsCmb = getWorkStepCombo();
                                    if(Ext.isEmpty(this.value)){
                                        wsCmb.clearValue();
                                        //wsCmb.setValue('');
                                        wsCmb.setDisabled(true);
                                    }else{
                                        loadWsAndDataslots(this,getSelectedWorkstep(values));
                                    }
                                    loadFilterData(getApplicationCombo().getValue(),this.value,values);
                                    //Advanced filter panel will be enabled after selecting the template version.
                                    if (this.value == ""){ //If the valueLabel is 'All'
                                        disableDsFilterPanel();
                                    } else {
                                        BmFilterView.getDataslotPanel().setDisabled(false);
                                    }
                                    disableDsFilterItems();
                                }
                        }
                    }
                };
}

function getWorkStepFiterCfg(values){
    return {
        id : 'wsName',
        name : 'workStepName',
        xtype : 'bmworkstepfilter'
     };
}

function getPriorityFilterCfg(values){
    return {
        id : 'prio',
        name : 'priority',
        xtype : 'bmpriorityfilter',
        store : getPriorityStore(values),
        /**superboxToolTip : false,
        width : 300**/
        value : ''
    };    
}

function getStatusFilterCfg(values){
    var handleDsPanel = function(statusCmbValue){
        if('collaborativeassigned' == statusCmbValue) {
            disableDsFilterItems();
        }
    };
    return {
        xtype : 'bmstatusfilter',
        id    : 'st',
        name :  'status',
        store : getStatusStore(values),
        listeners: {
        select: { fn:
            function(combo, value)
            {
                var statusCmbValue = Ext.util.Format.lowercase(this.value);
                var userData = [['user',getLocalizedString('user')],
                        ['creator',getLocalizedString('creator')]];
                var userQData = [['user', getLocalizedString('user')],
                                 ['creator',getLocalizedString('creator')],
                         ['queue',getLocalizedString('queue')]
                         ];

                resetDueDateValues();
                resetEndDateValues();
                handleDsPanel(statusCmbValue);
                if('collaborativeassigned' == statusCmbValue) {
                    disableCollaborativeComp();
                    return;
                } else {
                    enableCollaborativeComp();
                    if('available' == statusCmbValue || 'delegatedavailable' == statusCmbValue) {
                         getPerTypeCombo().getStore().loadData(userQData, false);
                         getPerTypeCombo().setValue('queue');
                         onPerformerTypeSelect(getPerTypeCombo(), 'queue');
                    } else {
                        getPerTypeCombo().getStore().loadData(userData, false);
                       getPerTypeCombo().setValue('user');
                       onPerformerTypeSelect(getPerTypeCombo(), 'user');
                    }

                    //checkDelegate(statusCmbValue);
					disableComponents(getApplicationCombo(), false);
                    if('completed' == statusCmbValue) {
                        toggleEndDate(true, true);
                    } else {
                        toggleDueDate(true, true);
                    }
                }
                if(Ext.util.Format.lowercase(statusCmbValue) == 'completed'){
                    getFilterCombo().setDisabled(true);
                }else{
                    getFilterCombo().setDisabled(false);
                }
            }
        }
    }
    };

}

function toggleStartDate(enable) {
    var disable = !enable;
    disableComponents(getStartDateTypeCombo(), disable);
    disableComponents(getStartDateRangeCombo(), disable);
    disableComponents(getStartDate(), disable);
    if(enable) {
        getStartDateTypeCombo().setValue('dateField');
        onDateTypeSelect(getStartDateTypeCombo(), 'dateField');
    }
}

function toggleDueDate(enable, show) {
    var disable = !enable;
    disableComponents(getDueDateTypeCombo(), disable);
    disableComponents(getDueDateRangeCombo(), disable);
    disableComponents(getDueDate(), disable);
    if(show) {
        Bm.view.setCardLayoutActiveItem(getDueEndDateCard(),0);
        getDueDateTypeCombo().setValue('dateField');
        onDateTypeSelect(getDueDateTypeCombo(), 'dateField');
    }
}

function toggleEndDate(enable, show) {
    var disable = !enable;
    disableComponents(getEndDateTypeCombo(), disable);
    disableComponents(getEndDateRangeCombo(), disable);
    disableComponents(getEndDate(), disable);
    if(show) {
        Bm.view.setCardLayoutActiveItem(getDueEndDateCard(), 1);
        getEndDateTypeCombo().setValue('dateField');
        onDateTypeSelect(getEndDateTypeCombo(), 'dateField');
    }
}

function getStartDateFilterCfg(values){
    var config = {};
    config.cardLayoutId = 'startDateCardLayout';
    config.dynamicFldArray = [getStartDateFieldCfg(),
                             getStartDateRangeCfg(values)];
    var dateTypeCfg = {};
    dateTypeCfg.label = getLocalizedString('startDate');
    dateTypeCfg.id = "startDateType";
    config.fixedFldArray = [getDateTypeCfg(dateTypeCfg)];
    return Bm.filter.TwoColDynamicFieldCfg(config);
}

var getDateTypeCfg = function(config) {
    var cfg = {
                fieldLabel : config.label,
                id:config.id,
                name:config.id,
                xtype : 'bmdatetypefilter',
                store : getDateTypeStore(),
                width : 190,
                value:'dateField',
                listeners: {
                     select: { scope:this, fn:onDateTypeSelect}
                }
    };
    return cfg;
}

var onDateTypeSelect = function(combo, value) {
        var cLayout = null;
        if("startDateType" == combo.getId()) {
            cLayout = Ext.getCmp('startDateCardLayout');
            disableComponents(getStartDate(), false);
            getStartDate().setValue('');
            getStartDateRangeCombo().setValue('');
        } else if("dueDateType" == combo.getId()){
            cLayout = Ext.getCmp('dueDateCardLayout');
            getDueDate().setValue('');
            getDueDateRangeCombo().setValue('');
        } else {
            cLayout = Ext.getCmp('endDateCardLayout');
            getEndDate().setValue('');
            getEndDateRangeCombo().setValue('');
        }
        if('dateField' == combo.value) {
            Bm.view.setCardLayoutActiveItem(cLayout, 0);
        } else {
            Bm.view.setCardLayoutActiveItem(cLayout, 1);
        }
}
function resetEndDateValues(){
    var endDateCmp  = getEndDate();
    endDateCmp.setValue('');
    var endDtRangeCmp = getEndDateRangeCombo();
    endDtRangeCmp.setValue('');
}

function resetDueDateValues(){
    var dueDateCmp  = getDueDate();
    dueDateCmp.setValue('');
    var dueDtRangeCmp = getDueDateRangeCombo();
    dueDtRangeCmp.setValue('');
}

function getStartDateFieldCfg(){
    var cfg =new Bm.filter.Panel({
            layout: 'anchor',
            items:[
             {
              id : 'startDate',
              name : 'startDate',
              xtype:'bmdatetime',
              hideLabel:true,
              fieldLabel : getLocalizedString('startDate'),
              dateFormat:getLocalizedString('dateFormat'),
              timeFormat:getLocalizedString('timeFormat'),
              width:150
             }
          ]});
    return cfg;
}

function getStartDateRangeCfg(values){
    var cfg = new Bm.filter.Panel({
            layout: 'anchor',
            autoWidth:true,
            items:[{
                  id:'startDateRangeId',
                    name : 'startDateRange',
                xtype:'bmdaterangefilter',
                hideLabel:true,
                fieldLabel : getLocalizedString('startDateRange'),
              store : getStartDateRangeStore(getStartDateConstants()),
              autoHeight:true,
              width:150
              }]
    });
    return cfg;
}

function getDueEndDateFilterCfg(values) {
    var cardLayout = {
        id : "dueEndDatesCard",
        xtype : 'container',
        layout: {
            type : 'card',
            deferredRender: false,
            layoutOnCardChange: true
        },
        activeItem: 0,
        hideBorders : true,
        bodyBorder : false,
        defaults: {
          hideMode: 'offsets'
        },
        items : [getDueDateFilterCfg(values),
                 getEndDateFilterCfg(values)]
    };
    var cfg = {
              layout : 'column',
              xtype : 'fieldcontainer',
              width:425,
              items : [cardLayout]
    }
   return cfg;
}

function getDueDateFilterCfg(values){
    var config = {};
    config.cardLayoutId = 'dueDateCardLayout';
    config.dynamicFldArray = [getDueDateFieldCfg(),
                             getDueDateRangeCfg(values)];
    var dateTypeCfg = {};
    dateTypeCfg.label = getLocalizedString('dueDate');
    dateTypeCfg.id = "dueDateType";
    config.fixedFldArray = [getDateTypeCfg(dateTypeCfg)];
    return Bm.filter.TwoColDynamicFieldCfg(config);
}

function getDueDateFieldCfg(){
    var cfg =new Bm.filter.Panel({
            items:[
             {
              id : 'dueDate',
              name : 'dueDate',
              xtype:'bmdatetime',
              hideLabel:true,
              fieldLabel : getLocalizedString('dueDate'),
              dateFormat:getLocalizedString('dateFormat'),
              timeFormat:getLocalizedString('timeFormat'),
              width:150
             }
          ]});
    return cfg;
}

function getDueDateRangeCfg(values){
    var cfg = new Bm.filter.Panel({
            items:[{
                  id:'dueDateRangeId',
                  name:'dueDateRange',
                xtype:'bmdaterangefilter',
                hideLabel:true,
                fieldLabel : getLocalizedString('dueDateRange'),
              store : getDueDateRangeStore(getDueDateConstants()),
              autoHeight:true,
              width:150
          }]
    });
    return cfg;
}

function getEndDateFilterCfg(values){
    var config = {};
    config.cardLayoutId = 'endDateCardLayout';
    config.dynamicFldArray = [getEndDateFieldCfg(),
                             getEndDateRangeCfg(values)];
    var dateTypeCfg = {};
    dateTypeCfg.label = getLocalizedString('endDate');
    dateTypeCfg.id = "endDateType";
    config.fixedFldArray = [getDateTypeCfg(dateTypeCfg)];
    return Bm.filter.TwoColDynamicFieldCfg(config);
}

function getEndDateFieldCfg(){
    var cfg =new Bm.filter.Panel({
            items:[
             {
              id : 'endDate',
              name : 'endDate',
              xtype:'bmdatetime',
              hideLabel:true,
              dateFormat:getLocalizedString('dateFormat'),
              timeFormat:getLocalizedString('timeFormat'),
              width:150
             }
          ]});
    return cfg;
}

function getEndDateRangeCfg(values){
    var cfg = new Bm.filter.Panel({
            items:[{
                  id:'endDateRangeId',
                  name:'endDateRange',
                xtype:'bmdaterangefilter',
                hideLabel:true,
              store : getStartDateRangeStore(getStartDateConstants()),
              autoHeight:true,
              width:180
          }]
    });
    return cfg;
}

function getDueEndDateCard() {
  return Ext.getCmp('dueEndDatesCard');
}

function getPerfQueueCfg(values){
   var isManager = values[0].isManager;
   var config = {};
   config.cardLayoutId = 'perfTypeCardLayout';
   config.dynamicFldArray = [{
            xtype: 'bmfieldcontainer',
				items: [{
				id:'dummy',
				bodyBorder : false,				
				hideLabel : true,
				width :0
				}]
        },{
			xtype: 'bmfieldcontainer',
				items: [{
				id:'queuePerf',
				hideLabel : true,
				name : 'queue',
				xtype : 'bmqueuefilter',
				maxWidth : 150,
				store : getQueueStore(values)
			}]
        },{
			xtype: 'bmfieldcontainer',
				items: [{
				id:'user',
				hideLabel : true,
				name : 'performer',
				value : loginUser,
				readOnly : !isManager,
				xtype : 'bmperformerfilter',
				width :95
			}]
        }, {
			  xtype: 'bmfieldcontainer',
				  items: [{
				  id:'creator',
				  hideLabel : true,
				  name : 'creator',
				  xtype:'bmperformerfilter',
				  width :95
			  }]
    }];
    config.fixedFldArray = [getPerformerTypeCfg(values)];
    return Bm.filter.TwoColDynamicFieldCfg(config);
}

var getPerformerTypeCfg = function(values) {
    var cfg = {
                    id: 'performerType',
                    name: 'performerType',
                    xtype : 'bmperformertypefilter',
                    store : getPerformerTypeStore(values),
                    width :190,
                    listeners: {
                        select: { scope:this, fn:onPerformerTypeSelect}
                    }
    };
    return cfg;
}

var onPerformerTypeSelect = function(combo, value) {
        var el = Ext.getCmp('perfTypeCardLayout');
        resetPerfomerTypes();
        if('queue' == combo.value) {
            Bm.view.setCardLayoutActiveItem(el, 1);
        } else if('user' == combo.value) {
            Bm.view.setCardLayoutActiveItem(el, 2);
        } else if('creator' == combo.value) {
            Bm.view.setCardLayoutActiveItem(el, 3);
        } else if('group' == combo.value) {
            Bm.view.setCardLayoutActiveItem(el, 2);
        } else {
            Bm.view.setCardLayoutActiveItem(el, 0);
        }
}

var resetPerfomerTypes = function() {
    if(Bm.Tasks.isManager()){
    var emptyValues = {queue:'', performer : '', creator:''};
    }else{
        var emptyValues = {queue:'',creator:''};
    }
    var panel = getTaskFilterForm();
    if(panel) {
        var form = panel.getForm();
        form.setValues(emptyValues);
    }
}

function loadworkStepCombo(store,selectedWorkstep){


    var wsCombo = getWorkStepCombo();
    if(!Ext.isEmpty(wsCombo)){
        wsCombo.clearValue();
        wsCombo.bindStore(getWsStore(store));
        wsCombo.setDisabled(false);
        wsCombo.setValue(selectedWorkstep);
    }
}

function loadQueueCombo(store){
    var qCombo = getQueueCombo();
    if(!Ext.isEmpty(qCombo)){
        qCombo.clearValue();
        qCombo.bindStore(getQueueStore(store));
        //qCombo.setDisabled(false);
    }
}

function getUserField(){
    var perf = Ext.getCmp('user');
    return perf;
}

function getCreatorField(){
    var creator = Ext.getCmp('creator');
    return creator;
}

function getQueueCombo(){
    var qcombo = Ext.getCmp('queuePerf');
    return qcombo;
}

function getStartDateRangeCombo(){
    var stDateRangeCombo = Ext.getCmp('startDateRangeId');
    return stDateRangeCombo;
}

function getStartDate(){
    var stDateRange = Ext.getCmp('startDate');
    return stDateRange;
}

function getDueDateRangeCombo(){
    var dueDateRangeCombo = Ext.getCmp('dueDateRangeId');
    return dueDateRangeCombo;
}

function getDueDate(){
    var dueDateRange = Ext.getCmp('dueDate');
    return dueDateRange;
}

function getEndDateRangeCombo(){
    return Ext.getCmp('endDateRangeId');
}

function getEndDate(){
    return Ext.getCmp('endDate');
}

function getWorkStepCombo(){
    var wsCombo = Ext.getCmp('wsName');
    return wsCombo;
}

function getVersionCombo(){
    var verCombo = Ext.getCmp('version');
    return verCombo;
}

function getPerTypeCombo(){
    var perTypeCombo = Ext.getCmp('performerType');
    return perTypeCombo;
}

function getApplicationCombo(){
    var appCombo = Ext.getCmp('app');
    return appCombo;
}

function getPriorityCombo(){
    var prioCombo = Ext.getCmp('prio');
    return prioCombo;
}

//This method will return the status combobox component.
function getStatusCombo(){
    return Ext.getCmp('st');    
}

function loadDataSlotCombo(store){
    var dsFilter = BmFilterView.getDataslotPanel();
    var dsStore = getDataSlotStore(store);
        if(dsFilter){
            var count = Ext.getCmp('dsFilter').items.length;
            if(!Ext.isEmpty(dsStore)){
                for(i=1;i<=count;i++){
                    var dsCombo = Ext.getCmp('dsCmb_'+i);
                    if(!Ext.isEmpty(dsCombo)){
                        dsCombo.clearValue();
                        dsCombo.bindStore(dsStore);
                        dsCombo.setDisabled(false);
                    }

                    var leftParenCombo = Ext.getCmp('leftParen_'+i);
                    var operatorCombo = Ext.getCmp('operCmb_'+i);
                    var rightParenCombo = Ext.getCmp('rightParenCmb_'+i);
                    var logicCombo = Ext.getCmp('logicCmb_'+i);
                    disableComponents(leftParenCombo, false);
                    disableComponents(operatorCombo, false);
                    disableComponents(rightParenCombo, false);
                    disableComponents(logicCombo, false);
                    disableComponents(Ext.getCmp('addDsRowbtn_'+i), false);
                    disableComponents(Ext.getCmp('delDsRowBtn_'+i), false);
                    disableComponents(Ext.getCmp('resetDs_'+i), false);
                }
            }
        }
}

/*
Since task column support is enabled for delegated tasks as well, 
below code is not required.

function checkDelegate(value){
    if (value.indexOf('delegate', 0) >= 0) {
        disableComponents(getApplicationCombo(), true);
        //getPriorityCombo().setDisabled(true);
        disableComponents(getVersionCombo(), true);
        disableComponents(getWorkStepCombo(), true);
    } else {
        disableComponents(getApplicationCombo(), false);
        getPriorityCombo().setDisabled(false);
    }
}
*/

function disableComponents(comp,isdisable){
    if(!Ext.isEmpty(comp)){
        if(isdisable && !Ext.isEmpty(comp.store)){
            comp.clearValue();
        }
        comp.setDisabled(isdisable);
    }
}

function handleDsFilterCombo(comp){
    var count = Ext.getCmp('dsFilter').items.length;
    for(i=1;i<=count;i++){
        disableComponents(Ext.getCmp(comp+i), true);
    }
}

function getAppStore(values){
    var appValues = [{value : '',valueLabel : getLocalizedString('All')}];
    appValues = appValues.concat(values[0].get('app'));

    var appStore = Ext.create('Ext.data.Store',{
        model: Ext.define('appStoreModel', {
                extend: 'Ext.data.Model',
                fields: [
                            'value',
                            'valueLabel'
                        ]
            }),
        data : appValues,
        sortInfo: {
            field: 'value',
            direction: 'ASC'
        }
    });
    return appStore;
}

function getVersionStore(values){
    var versionValues = [['','',getLocalizedString('All')]];
    versionValues = versionValues.concat(values[0].get('version'));
    var versionStore = Ext.create('Ext.data.ArrayStore',{
        model : Ext.define('versionStoreModel',{
                    extend: 'Ext.data.Model',
                    fields: [
                                'value',
                                'app',
                                'valueLabel'
                            ]
                    }
                ),
        //sortInfo:{field: 'valueLabel', direction: "ASC"},
        data : versionValues
    });
    return versionStore;
}

function getPriorityStore(values){
    var priorityValues = [{value : '',valueLabel : getLocalizedString('All')}];
    priorityValues = priorityValues.concat(values[0].get('priority'));
    var priorityStore =  Ext.create('Ext.data.Store',{
        model: Ext.define('priorityStoreModel', {
            extend: 'Ext.data.Model',
            fields: [
                        'value',
                        'valueLabel'
                    ]
        }),
        data : priorityValues//values[0].get('priority')
    });
    return priorityStore;
}

function getStatusStore(values){
    var statusValues = values[0].get('status');
    var statusStore = Ext.create('Ext.data.Store',{
        model: Ext.define('statusStoreModel', {
            extend: 'Ext.data.Model',
            fields: [
                        'value',
                        'valueLabel'
                    ]
        }),
        data : statusValues
    });
    return statusStore;
}

function getDateTypeStore() {
    var data = [['dateField', getLocalizedString('dateField')],
              ['dateRange', getLocalizedString('dateRange')]];
    var store = Ext.create('Ext.data.ArrayStore',{
        model: Ext.define('dateTypeStoreModel', {
            extend: 'Ext.data.Model',
            fields: [
                        'value',
                        'valueLabel'
                    ]
        }),
         data : data
      });
    return store;
}

function getPerformerTypeStore(values){
    var isManager = values[0].get('isManager');
    var data;
   //if(isManager) {
        data = [['user', getLocalizedString('user')],
            ['creator', getLocalizedString('creator')],
            ['queue',getLocalizedString('queue')]];
    /*} else {
        data = [['creator', getLocalizedString('creator')],
            ['queue',getLocalizedString('queue')]];
    }*/
    var performerTypeStore = Ext.create('Ext.data.ArrayStore',{
        model: Ext.define('performerTypeStoreModel', {
            extend: 'Ext.data.Model',
            fields: [
                        'value',
                        'valueLabel'
                    ]
        }),
        data : data
    });
    return performerTypeStore;
}

function getWsAndDsStore(selectedWorkstep){

    Ext.define('workstepModel', {
        extend: 'Ext.data.Model',
        fields: ['workstep','dataslotList']
    });

    var workstepStore = Ext.create('Ext.data.Store', {
        model: 'workstepModel',
        proxy : {
            type : 'ajax',
            url : '../myhome/bmTaskSearch.portal',
            reader : {
                type : 'json',
                root : 'data',
                totalProperty : 'count'
            }
        },
        listeners : {
            'load': function(store,records, options){
                    loadworkStepCombo(store,selectedWorkstep);
                    loadDataSlotCombo(store);
                },
            'exception' : function( store, type, action, options, response, arg) {
                handleStoreLoadException(store, type, action, options, response, arg);
            }
        }

    });
    return workstepStore;
}

function getDataSlotStore(wsStore){
    var values = wsStore.getRange();
    if(values == undefined || values[0] == undefined)
        return;
    var dsValues = values[0].raw.dataslotList;
        var dataSlotStore = new Ext.create('Ext.data.Store',{
        fields: [
                 'type',
                 'name',
                 'valueLabel',
                 'template',
                 'uitype',
                 'validValues',
                 'isGlobal',
                 'isTaskCol'
        ],
        data : dsValues,
        listeners : {
            'load': function(store,records, options){
                //prepareDynColJson(records);
            },
            'exception' : function( store, type, action, options, response, arg) {
                handleStoreLoadException(store, type, action, options, response, arg);
            }
        }

    });
    return dataSlotStore;
}

function getWsStore(store){
    var values = store.getRange();
    var wsValues = [['','',getLocalizedString('All')]];
    wsValues = wsValues.concat(values[0].raw.workstep);

    var wsStore = Ext.create('Ext.data.ArrayStore',{
        fields: [
                    'value',
                    'template',
                    'valueLabel'
                ],
        data : wsValues
    });
    return wsStore;
}

var BmDsStore;
function loadWsAndDataslots(parentCombo,selectedWorkstep){
    BmDsStore = getWsAndDsStore(selectedWorkstep);
    var appName = getApplicationCombo().getValue();
    var templateName = parentCombo.getValue();
    if(!Ext.isEmpty(appName) && !Ext.isEmpty(templateName)){
        BmDsStore.load({params:{'application': appName,'templateName':templateName}});
    }
}

function getLoadedStore(){
    return BmDsStore;
}

function getQueueStore(values){
  var queueValues = [{value : '',valueLabel : getLocalizedString('All')}];
    queueValues = queueValues.concat(values[0].get('queue'));
  var queueStore  = Ext.create('Ext.data.Store',{
        model: Ext.define('queueStoreModel', {
            extend: 'Ext.data.Model',
            fields: [
                        'value',
                        'valueLabel'
                    ]
        }),
        data : queueValues
    });
    return queueStore;
}

function getStartDateRangeStore(values){
    //var stDateRangeValues = values[0].get('startDateConstants');
    var stDateRangeValues = values;
    var stDateRangeStore  = Ext.create('Ext.data.Store',{
        model: Ext.define('startDateRangeStoreModel', {
            extend: 'Ext.data.Model',
            fields: [
                        'value',
                        'valueLabel'
                    ]
        }),
        data : stDateRangeValues
    });
    return stDateRangeStore;
}

function getDueDateRangeStore(values){
    var allOverDueOption = [{value:'ALL_OVERDUE',valueLabel : getLocalizedString('ALL_OVERDUE')}];
   // var dueDateRangeValues = values[0].get('dueDateConstants');
   var dueDateRangeValues = values;
    dueDateRangeValues = allOverDueOption.concat(dueDateRangeValues);
    var dueDateRangeStore  = Ext.create('Ext.data.Store',{
        model: Ext.define('dueDateRangeStoreModel', {
            extend: 'Ext.data.Model',
            fields: [
                        'value',
                        'valueLabel'
                    ]
        }),
        data : dueDateRangeValues
    });
    return dueDateRangeStore;
}



function getFilterUI(config){
    var filterUI = new Bm.view.FilterUI(config);
    return filterUI;
}

function cleanFilterDiv() {
    var panel = Ext.getCmp('filterEnclPanel');
    if(panel) {
        var filterPanelEl= panel.el;
        filterPanelEl.remove();
    }
}

function resetDynCol(){
    var dynCol  = Ext.getCmp('dynamicColumns');
    if(dynCol){
        dynCol.setValue('');
    }
}


function disableCollaborativeComp(){
    getPriorityCombo().setDisabled(false);
    disableComponents(getApplicationCombo(), true);
    disableComponents(getVersionCombo(), true);
    disableComponents(getWorkStepCombo(), true);
    disableComponents(getPerTypeCombo(), true);
    toggleStartDate(false);
    toggleDueDate(false, true);
    toggleEndDate(false, false);
}

function enableCollaborativeComp(){
    //getPriorityCombo().setDisabled(true);
    disableComponents(getPerTypeCombo(), false);
    toggleStartDate(true);
    toggleDueDate(true, true);
    toggleEndDate(true, false);
    resetDateFields();
}

function loadFilterData(appName,versionName,values){
  var filterCombo = getFilterCombo();
    if(!Ext.isEmpty(filterCombo)){
        filterCombo.clearValue();
        filterCombo.bindStore(getFilterStore(appName,versionName,values));
        filterCombo.setValue('');
    }
}


function getFilterCombo(){
    var filterCombo = Ext.getCmp('filter');
    return filterCombo;
}

function getFilterComboCfg(values){
    var cfg = {
            id : 'filter',
            value : '',
            name : 'filterId',
            xtype : 'bmfiltersearch',
            store: getFilterStore('','',values),
            displayField:'displayName',
            tpl:'<tpl for="."><div class="x-boundlist-item x-combo-list-item">&nbsp;&nbsp;&nbsp;&nbsp;{displayName}</div></tpl>',
            autoCreate: {tag: "input", type: "text", autocomplete:"off", qtip: getLocalizedString('TaskFilters')},
            listeners : {
                change: {
                    fn: function(combo, value) {
                        if (Ext.isEmpty(this.value)){
                            this.setValue(getLocalizedString('filter.combobox.no.filter'));
                        }
                    }
                }
            }
    }
    return cfg;
}

function getFilterStore(appName,versionName,values){

  var aName = appName;
  if(appName == undefined || appName == null) {
    aName = '';
  }
  var vName = versionName;
  if(versionName == undefined || versionName == null) {
    vName = '';
  }


  var globalFilterStr = '<div class="bm-dropdown-head"><b>'+getLocalizedString('GlobalFilters')+'</b></div>';
  var myFilterStr = '<div class="bm-dropdown-head"><b>'+getLocalizedString('MyFilters')+'</b></div>';

  var filterValues = [{id : '',displayName : getLocalizedString('filter.combobox.no.filter')},{id : '',displayName : globalFilterStr}];
    var globalFilterData = values[0].data.filters.globalFilters;
    var taskFilterData =values[0].data.filters.taskFilters;

    if(globalFilterData != undefined && globalFilterData != null && globalFilterData.length > 0) {
        for(var i=0; i<globalFilterData.length;i++) {
            if(globalFilterData[i].appName == aName && globalFilterData[i].templateName == vName || globalFilterData[i].outOfBoxFilter ) {
              filterValues = filterValues.concat(globalFilterData[i]);
            }
        }
  }
  filterValues = filterValues.concat([{id : '',displayName : myFilterStr}]);

  if(taskFilterData != undefined && taskFilterData != null && taskFilterData.length > 0) {
        for(var i=0; i<taskFilterData.length;i++) {
           if(taskFilterData[i].appName == aName && taskFilterData[i].templateName == vName || taskFilterData[i].outOfBoxFilter ) {
              filterValues = filterValues.concat(taskFilterData[i]);
            }
        }
  }

    var filterStore =  Ext.create('Ext.data.Store',{
            fields: [
                'adminFilter',
                'appName',
                'description',
                'globalFilter',
                'id',
                'displayName',
                'outOfBoxFilter',
                'templateLabel',
                'templateName'
                ],
            data : filterValues
    });

    return filterStore;
}

var prepareFilterParams = function(formValues) {

    if(!Ext.isEmpty(formValues)) {
       var filterValue = getFilterCombo().getValue();
       formValues.filterId = filterValue;
    }

   return formValues;
}


function getDefaultVersion(values){
    if(!Ext.isEmpty(values)){
        var defaultVersion = values[0].get('defaultVersion');
        if(!Ext.isEmpty(defaultVersion) && defaultVersion != '-1'){
            return defaultVersion;
        }
    }
    return '';
}

function getDefaultFilter(values){
    if(!Ext.isEmpty(values)){
        var defaultFilterId = values[0].get('defaultFilterId');
        if(!Ext.isEmpty(defaultFilterId) && defaultFilterId != getLocalizedString('filter.combobox.no.filter')){
            return defaultFilterId;
        }
    }
    return '';
}

function getEndDateConstants(){
    var dateJson = [{"valueLabel":getLocalizedString('TODAY'),"value":"TODAY"},{"valueLabel":getLocalizedString('YESTERDAY'),"value":"YESTERDAY"},
    {"valueLabel":getLocalizedString('TOMORROW'),"value":"TOMORROW"},{"valueLabel":getLocalizedString('LAST_7_DAYS'),"value":"LAST_7_DAYS"},
    {"valueLabel":getLocalizedString('NEXT_7_DAYS'),"value":"NEXT_7_DAYS"},{"valueLabel":getLocalizedString('LAST_30_DAYS'),"value":"LAST_30_DAYS"},
    {"valueLabel":getLocalizedString('LAST_60_DAYS'),"value":"LAST_60_DAYS"},{"valueLabel":getLocalizedString('NEXT_30_DAYS'),"value":"NEXT_30_DAYS"},
    {"valueLabel":getLocalizedString('NEXT_60_DAYS'),"value":"NEXT_60_DAYS"},{"valueLabel":getLocalizedString('LAST_WEEK'),"value":"LAST_WEEK"},
    {"valueLabel":getLocalizedString('THIS_WEEK'),"value":"THIS_WEEK"},{"valueLabel":getLocalizedString('NEXT_WEEK'),"value":"NEXT_WEEK"},
    {"valueLabel":getLocalizedString('LAST_MONTH'),"value":"LAST_MONTH"},{"valueLabel":getLocalizedString('THIS_MONTH'),"value":"THIS_MONTH"},
    {"valueLabel":getLocalizedString('NEXT_MONTH'),"value":"NEXT_MONTH"},{"valueLabel":getLocalizedString('LAST_DAY_OF_MONTH'),"value":"LAST_DAY_OF_MONTH"},
    {"valueLabel":getLocalizedString('PREVIOUS_QUARTER'),"value":"PREVIOUS_QUARTER"},{"valueLabel":getLocalizedString('THIS_QUARTER'),"value":"THIS_QUARTER"},
    {"valueLabel":getLocalizedString('NEXT_QUARTER'),"value":"NEXT_QUARTER"},{"valueLabel":getLocalizedString('FIRST_QUARTER'),"value":"FIRST_QUARTER"},
    {"valueLabel":getLocalizedString('LAST_QUARTER'),"value":"LAST_QUARTER"},{"valueLabel":getLocalizedString('LAST_YEAR'),"value":"LAST_YEAR"},
    {"valueLabel":getLocalizedString('NEXT_YEAR'),"value":"NEXT_YEAR"},{"valueLabel":getLocalizedString('THIS_YEAR'),"value":"THIS_YEAR"}];

   return dateJson;
}

function getStartDateConstants(){
    var dateJson = [{"valueLabel":getLocalizedString('TODAY'),"value":"TODAY"},
    {"valueLabel":getLocalizedString('YESTERDAY'),"value":"YESTERDAY"},
    {"valueLabel":getLocalizedString('LAST_7_DAYS'),"value":"LAST_7_DAYS"},
    {"valueLabel":getLocalizedString('LAST_30_DAYS'),"value":"LAST_30_DAYS"},
    {"valueLabel":getLocalizedString('LAST_60_DAYS'),"value":"LAST_60_DAYS"},
    {"valueLabel":getLocalizedString('LAST_WEEK'),"value":"LAST_WEEK"},
    {"valueLabel":getLocalizedString('THIS_WEEK'),"value":"THIS_WEEK"},
    {"valueLabel":getLocalizedString('LAST_MONTH'),"value":"LAST_MONTH"},
    {"valueLabel":getLocalizedString('THIS_MONTH'),"value":"THIS_MONTH"},
    {"valueLabel":getLocalizedString('PREVIOUS_QUARTER'),"value":"PREVIOUS_QUARTER"},
    {"valueLabel":getLocalizedString('THIS_QUARTER'),"value":"THIS_QUARTER"},
    {"valueLabel":getLocalizedString('FIRST_QUARTER'),"value":"FIRST_QUARTER"},
    {"valueLabel":getLocalizedString('LAST_QUARTER'),"value":"LAST_QUARTER"},
    {"valueLabel":getLocalizedString('LAST_YEAR'),"value":"LAST_YEAR"},
    {"valueLabel":getLocalizedString('THIS_YEAR'),"value":"THIS_YEAR"}];

    return dateJson;
}

function getDueDateConstants(){
    return getEndDateConstants();
}

Bm.view.isDsFilterPanelExpanded = function() {
    var pane = BmFilterView.getDataslotPanel();
    if(pane) {
        return !(pane.collapsed);
    }
    return false;
}