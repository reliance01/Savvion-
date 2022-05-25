function SaveGlobalDsData(theForm, divId,dId,wId,dwId,viewmode) {

    var url = 'widgetupdate.portal';
    var ser = Form.serialize(theForm);

    var params = 'viewmode='+viewmode+'&did='+dId+'&wid='+wId+'&dwid='+dwId+'&action=updateglobalds';
    params += '&'+ser;
    
    var elem = $(divId);
    elem.innerHTML = getLocalizedString('Saving');
    pwr.loadPage(url,params,divId,'post', Prototype.emptyFunction, Prototype.emptyFunction, true);
}

function ResetGlobalDsData(theForm, divId,dId,wId,dwId,viewmode) {  

    var elem = $(divId);
    elem.innerHTML = getLocalizedString('Resetting');

    var url = 'widgetupdate.portal';
    var ser = Form.serialize(theForm);

    var params = 'viewmode='+viewmode+'&did='+dId+'&wid='+wId+'&dwid='+dwId+'&action=resetglobalds';
    params += '&'+ser;
    
    pwr.loadPage(url,params,divId,'post', Prototype.emptyFunction, Prototype.emptyFunction, true);
}

function SaveInfopadData(theForm, divId,dId,wId,dwId,viewmode) {

    var url = 'widgetupdate.portal';
    var ser = Form.serialize(theForm);

    var params = 'viewmode='+viewmode+'&did='+dId+'&wid='+wId+'&dwid='+dwId+'&action=updateinfopad';
    params += '&'+ser;
    
    var elem = $(divId);
    elem.innerHTML = getLocalizedString('Saving');
    pwr.loadPage(url,params,divId,'post');


}

function ResetInfopadData(theForm, divId,dId,wId,dwId,viewmode) {

    var elem = $(divId);
    elem.innerHTML = getLocalizedString('Resetting');

    var url = 'widgetupdate.portal';
    var ser = Form.serialize(theForm);

    var params = 'viewmode='+viewmode+'&did='+dId+'&wid='+wId+'&dwid='+dwId+'&action=resetinfopad';
    params += '&'+ser;
    
    pwr.loadPage(url,params,divId,'post');
}

function RefreshData(theForm, divId, dId, wId, dwId,viewmode, options) {
    var runScripts = false;
    var disableMask = false;

    if(options != null && options.runScripts != null) {
        runScripts =options.runScripts;
    }
    if(options != null && options.disableMask != null) {
        disableMask =options.disableMask;
    }

   var url = 'widgetrenderer.portal';
    
    var params = 'viewmode='+viewmode+'&did='+dId+'&wid='+wId+'&dwid='+dwId+'&action=refreshdata';
    if(theForm != null){
        var ser = Form.serialize(theForm);
        params += '&'+ser;
    }
    
    if(!disableMask) {
        maskEl(dwId);    
    }
    var failureHandler = function() {
        if(!disableMask) {
            unmaskEl(dwId);
        }
    }
    var successHandler = function() {
        if(!disableMask) {
            unmaskEl(dwId);
        }
    }    
    pwr.loadPage(url,params,divId,'get',failureHandler,successHandler,runScripts);
}

function getTools(type,dwid,did,wid,row,column,toolTips,RefreshText,viewMode,layout,config){
    var toolsArray = new Array();
    if(viewMode && ( type == 1 
            || (type == 0 && !Ext.isEmpty(config) && config.name == "OverdueWorkstepWidget") )){
        toolsArray.push({
        	type:'gear', 
        	hideMode:'offsets',
        	tooltip: getLocalizedString('configure'), 
            handler: function(e, targetEl, panelHeader, tool){
                var menu = Ext.getCmp(panelHeader.getId() + "-menu");
                if(menu){
                    if(Ext.isEmpty(menu.editSettingAdded) || !menu.editSettingAdded){
                        if(viewMode && type != 8 && !Ext.isEmpty(config) && config.autoRefresh 
                                && !Ext.isEmpty(config.target) && config.showCounter){
                            if((config.isPct && config.target.toUpperCase() == 'PCT') 
                                    || (!config.isPct && config.target.toUpperCase() == 'SBM') 
                                    || config.target.toUpperCase() == 'ALL'){
                                menu.add({ 
                                    text: getLocalizedString('EditSettings'),
                                    hidden: true,
                                    listeners: {
                                        click: function(menu, item, e, eOpts ){
    		                    	        editWidgetSetting({
    		                    	        	event: e, 
    		                                    targetEl: target, 
    		                                    wPanel: panel, 
    		                                    dashWidgetId: dwid 
    		                                });
    		                            }
    		                        }
    		                    });
                            }
                        }
                    }
                    menu.editSettingAdded = true;
                    menu.showBy(tool, 'tr-bl');
                }
            }
        });  
    }
    toolsArray.push({type:'toggle', hideMode:'offsets', handler: function(e, target, panelHeader){var panel = panelHeader.ownerCt; panel.toggleCollapse();},tooltip:getLocalizedString('Minimize')});
    toolsArray.push({type:'help', hideMode:'offsets', handler: function(e, target, panelHeader){},tooltip:toolTips});
    toolsArray.push({type:'refresh', hideMode:'offsets', handler: function(e, target, panelHeader){refreshHandler(type,did,wid,dwid,column,row,config.category,viewMode, config);},tooltip:RefreshText});
    if(!viewMode) {
        toolsArray.push({type:'close', hideMode:'offsets', handler: function(e, target, panelHeader){removeWidget(dwid,panelHeader.ownerCt,viewMode,layout);}});
    }
    /*if(viewMode && type != 8 && !Ext.isEmpty(config) && config.autoRefresh && !Ext.isEmpty(config.target) && config.showCounter){           
        if((config.isPct && config.target.toUpperCase() == 'PCT') || (!config.isPct && config.target.toUpperCase() == 'SBM') || config.target.toUpperCase() == 'ALL'){
            toolsArray.push({id:'gear', hideMode:'offsets', handler: function(e, target, panel){editWidgetSetting({event: e, targetEl: target, wPanel : panel, dashWidgetId : dwid });},qtip:getLocalizedString('EditSettings')});    
        }
    }*/
    return toolsArray;
}

function getPanelConfig(viewMode, widgetId, title, row, col, tools, listeners){
	var panelConfig = {};
	panelConfig.id = (viewMode == true) ? 'V_portlet_' + widgetId : 'E_portlet_' +  widgetId;
	panelConfig.title = title;
	panelConfig.contentEl = 'column_' + row + '_' + col + '_' + widgetId;
	panelConfig.tools = tools;
	if(viewMode){
		if(!listeners){
			listeners = {};
		}
		listeners.afterrender = function(){setListenerToolDisplay(this);} 
	}
	if(listeners){
		panelConfig.listeners = listeners;  
	}
	return panelConfig;
}

function setListenerToolDisplay(widget){
	var tools = widget.getHeader().getEl().query('.x-tool');
    widget.getEl().on('mouseenter',
       function(event, element, opts){
    	 toggleToolDisplay(tools, 'show');
       }
    );
    widget.getEl().on('mouseleave',
      function(event, element, opts){
    	toggleToolDisplay(tools, 'hide');
      }
    );
    toggleToolDisplay(tools, 'hide');
}

function toggleToolDisplay(tools, option){
  for(var i=0; i<tools.length; i++){
	  var t = Ext.get(tools[i].id);
      if(option == 'hide'){
     	t.hide();
      }else{
     	t.show(); 
      }
  }
}

function getFormattedToolTip(wName,wAppName,desc,wCategory){
    if(wAppName == ''){
        wAppName = 'ALL';
    }
    var values = {wName1:wName,wAppName1:wAppName,desc1:desc,wCategory:wCategory};
    var toolTipTemplate = new Ext.Template(
        '<table cellSpacing=0 cellPadding=0 width="100%" border=0>',
          '<tbody>',
              '<tr>',
                  '<td class=PsvTblInBg><table class="SegDataTbl" cellSpacing="1" cellPadding="4" width="100%" border="0">',
                      '<tbody>',
                          '<tr>',
                              '<td class="SegFieldRt" nowrap="nowrap" valign="top" width="20%">' + getLocalizedString('componentName') + ':</td>',
                              '<td class=SegValLft>{wName1}&nbsp;</td>',
                          '</tr>',
                          '<tr>',
                              '<td class="SegFieldRt" nowrap="nowrap" valign="top" width="20%">' + getLocalizedString('process') + ':</td>',
                              '<td class=SegValLft>{wAppName1}&nbsp;</td>',
                          '</tr>',
                          '<tr>',
                              '<td class="SegFieldRt" nowrap="nowrap" valign="top" width="20%">' + getLocalizedString('category') + ':</td>',
                              '<td class=SegValLft>{wCategory}&nbsp;</td>',
                          '</tr>',
                           '<tr>',
                       '<td class="SegFieldRt" nowrap="nowrap" valign="top" width="20%">' + getLocalizedString('description') + ':</td>',
                       '<td class=SegValLft>{desc1}&nbsp;</td>',
                          '</tr>',                      
                '</tbody>',
                '</td></table>',
            '</tr>',
        '</tbody>',
          '</table>');
    
    return toolTipTemplate.applyTemplate(values);
}

function removeWidget(dashboardWidgetId, panel,viewMode,layout) {
    if(!viewMode){
            if (confirm(getLocalizedString('deleteCompConfirmMessage'))) {
                    panel.ownerCt.remove(panel, true);
                    var par = Ext.urlDecode('formAction=deletecomponent&layout='+layout+'&widgetId='+dashboardWidgetId);                        
                    Ext.Ajax.request({
                        url:'changelayout.portal',
                        params:par,
                        method:'post',
                        success: function(response) { updateLayout(viewMode,layout,'reorder'); }
                    });
                }
        }
        else{
            panel.ownerCt.remove(panel, true);
        }
}

function updateLayout(viewMode,layout,action) {
        var l = "";
        portal = Ext.select(".x-portal").first();
        portal.select('.x-portal-column').each(function(e) {
        var flag = false;
            e.select('.x-portlet').each(function(e) {
                var dwid = Ext.String.trim(e.dom.id.replace(/V_portlet_|E_portlet_/, ''));
                if(Ext.isNumeric(dwid)){
                    l += dwid;
                    l += ",";
                    flag = true;
                }
            });
            l = l.replace(/.?$/, '');
            l += "|";
            if(!flag){
                l += " |";
            }
        });
        l = l.replace(/.?$/, '');
        var par = Ext.urlDecode('viewmode='+viewMode+'&layout='+layout+'&formAction='+action+'&order='+l);
        Ext.Ajax.request({
            url:'changelayout.portal',
            params:par,
            method:'post'
        });
}

function updateGridWidth(panel){
    if(!Ext.isEmpty(panel)){
        var htmlGridElems = panel.getEl().query(".x-grid");
        if(!Ext.isEmpty(htmlGridElems)){
            for(var i=0; i<htmlGridElems.length; i++){
                var grid = Ext.getCmp(htmlGridElems[i].id);
                if(grid.setWidthAsFilterArea){
                    grid.setWidthAsFilterArea();
                }
    	    }
        }    
    }	
}

function setGridWidth(grid, domElementId){
	if(!Ext.isEmpty(domElementId) && !Ext.isEmpty(grid) &&  grid.setWidth){
		var domElement = Ext.getDom(domElementId); 
	    if(!Ext.isEmpty(domElement)){
	    	grid.setWidth(domElement.getWidth());
	    }
	}
}

function manageDrillDown(link,target){

    var wnd;
    if(target=="popup") {
 
        wnd = Ext.create('Ext.Window',{
            autoShow:true,
            title: link,
            closable:true,           
            width:500,
            height:500,
            modal:true,
            layout:'fit', 
            items: [{
                      xtype: 'container',
                      autoEl : {
                          tag: 'iframe',
                          src: link,
                          id: 'iframeId',
                          frameBorder: 0,
                          width: '100%',
                          height: '100%'                      
                      }
                   }]  
        });                   
        wnd.show();
     
    } else if (target=="browser"){
        wnd=window.open(link, "DashboardDrillDownWindow", "height=400,width=500,toolbar=no,scrollbars=yes,menubar=yes, resizable=1");
    }    
}

function EvaluateMetric(theForm, divId, dId, wId, dwId,viewmode,metricId,metricName) {  
    document.getElementById('metricBeingEvaluated').value=metricName;
    var url = 'widgetupdate.portal';
    var params = 'viewmode='+viewmode+'&did='+dId+'&wid='+wId+'&dwid='+dwId+'&action=evaluatemetric&metricid='+metricId;
    pwr.loadPage(url,params,divId,'post','','',true);  
}

function getDashboardWidgets(config){
     var panels = new Array();        
        if(config.colData != null && config.colData != undefined) {
            for( i = 0; i < config.colData.length;i++) {                            
                panels.push({style:'padding:0px 6px 6px 0px',columnWidth: config.colData[i].width, items:config.panelItems[i]});
            }                 
        }        
        return panels;
}

Bm.util.TimerCollection = function(){
   var _instance = null;
   return {
      getInstance : function(){
         if(_instance == null){
            _instance = new function() {
                  this.timerCollection= {};
                  this.add         = function(config){
                     var object = new Bm.util.Timer(config);
                     this.timerCollection[config.id] = object;
                     return this.timerCollection;
                  };
                  this.remove      = function(key){
                     this.timerCollection[key] = null;
                     return this.timerCollection;
                  };
                  this.getTimer     = function(key){
                     return this.timerCollection[key];
                  };
                  this.getTime     = function(key){
                     if(!Ext.isEmpty(this.timerCollection[key])) {
                        return this.timerCollection[key].getTime();
                     } else {
                        return null;
                     }
                  };
                  this.resetTimer  = function(key){
                     if(!Ext.isEmpty(this.timerCollection[key])) {
                        return this.timerCollection[key].reset;
                     }               
                  };
                  this.reset       = function(){
                     this.timerCollection = {};
                     return this.timerCollection;
                  };
                  this.size        = function(){
                     return this.timerCollection.length;
                  };            
            };
         }
         return _instance;
      }            
   };
}();

    
Bm.util.Timer = function(config){
    var id     = config.id;
    var panel  = Ext.getCmp(config.panelId);
    var panelTitle  = panel.title;
    var refreshInterval = config.refreshTime;        
    var refTimeInt = config.refreshTime;
    var refreshTime = config.refreshTime;     
    var autoRefresh = config.autoRefresh;  
    var timerInterval = config.timerInterval;    
    var timerIntervalmSec = timerInterval*1000;
    editMode = false;         
    
    var task   = {
        run     :   function(){            
            timerIntervalmSec = timerInterval*1000;            
            if(refreshTime == 0 && autoRefresh){         
                resetAll();
                refreshHandler(config.widgetType,config.dashId,config.widgetId,
                                    config.dashWidgetId,config.column,config.row, 'savvion', 
                                    config.viewMode, {name: config.widgetName});    //Here the category is always 'CX Process for timer'
            } else {                
                var refreshTimeInMin = refreshTime/60;                
                var seconds = refreshTime%60;                
                seconds = Math.round(seconds.toFixed(2));                
                var displayTimer = AddZero(Math.floor(refreshTimeInMin))+" : "+AddZero(seconds);
                if(config.showCounter){                                    
                   panel.setTitle(panelTitle+'<span style="'+config.msgStyle+'" title=" mm:ss ">( '+config.msg+' '+displayTimer+' )</span>');                                     
                }
                decr = timerInterval;           
                refreshTime-=decr;          
                
            }                                
        },
        
        interval: timerIntervalmSec  
    };    
   
    this.getConfig = function(){
        return config;
    };
    
    var resetAll = function(){                       
        refreshTime = refTimeInt;         
        panel.setTitle(panelTitle);
        if(!Ext.isEmpty(task)) {
            Ext.TaskManager.stop(task);
            Ext.TaskManager.start(task);
        }
    };
    this.reset  = resetAll;
      
    this.getPanel= function(){
        return panel;
    };    
    this.getId= function(){
        return id;
    };
    this.getTask= function(){
        return task;
    };
    this.isAutoRefreshEnabled = function(){
        return autoRefresh;    
    };
    this.enableAutoRefresh = function(){       
        autoRefresh = true;    
        Ext.TaskManager.start(task);                        
    };
    this.getAutoRefreshInterval = function(){
        return refTimeInt;
    };
    this.disableAutoRefresh = function(){
        autoRefresh = false;                
        Ext.TaskManager.stop(task);        
        panel.setTitle(panelTitle);     
    };
    this.setRefreshTime = function(timeInSeconds){
        refreshTime = timeInSeconds;        
        refTimeInt = timeInSeconds;             
    };
    this.setTimerInterval = function(timeInSeconds){
        timerInterval = timeInSeconds;          
    };   
    this.getRefreshTime = function(){
        return refreshTime;    
    };
    this.isEditMode = function(){
        return editMode;
    };
    this.getTimerInterval = function(){
        return timerInterval;
    };
};

function maskEl(panelId) {
    var panel = Ext.getCmp(panelId);
    if(!Ext.isEmpty(panel)) {
        panel.getEl().mask();
    }
}

function unmaskEl(panelId) {
    var panel = Ext.getCmp(panelId);
    if(!Ext.isEmpty(panel)) {
        panel.getEl().unmask();
    }
}


    function editWidgetSetting(config){
        var dashWidgetId = config.dashWidgetId;     
        var timerObj = timerColln.getTimer('timer_'+dashWidgetId);          
        var widPanel = Ext.getCmp(config.dashWidgetId); 
        var intervalMinVal = 1;
        var modified = false;
        var editPanel = new Ext.form.FormPanel({                      
            border      : true,
            frame       : true,             
            id          : 'refSettingPanel_'+dashWidgetId,
            buttonAlign  : 'center',
            bodyStyle:'padding:15px;',
            style:'padding:10px 10px 10px 10px',
            monitorValid:true,
            defaults:{
                labelStyle  : 'width:30%'
            },
            items       : [{
                            xtype       : 'checkbox',
                            fieldLabel  : getLocalizedString('AutoRefresh'),                            
                            id          : 'chkBoxFld_'+dashWidgetId,                        
                            checked     : timerObj.isAutoRefreshEnabled(),
                            listeners: {
                                'check': function(){
                                  modified = true;
                                }
                              }

                      },
                      {     
                            xtype       : 'numberfield',                            
                            fieldLabel  : getLocalizedString('RefreshInterval') +  ' ('+getLocalizedString('secs')+')',
                            id          : 'resetTimeFld_'+dashWidgetId,         
                            allowBlank  : false,                            
                            blankText   : getLocalizedString('RefIntervalBlankMsg'),
                            minValue    : intervalMinVal,
                            minText     : getLocalizedString('RefIntervalMinMsg')+intervalMinVal,                                                                                                       
                            value       : timerObj.getAutoRefreshInterval(),
                            listeners: {
                                'change': function(){
                                  modified = true;
                                }
                              }

                        },
                      
                        {
                            xtype       : 'numberfield',
                            fieldLabel  : getLocalizedString("TimerInterval") + ' ('+getLocalizedString('secs')+')',
                            id          : 'TimerFld_'+dashWidgetId,                                 
                            allowBlank  : false,                            
                            blankText   : getLocalizedString('TimerBlankMsg'),                              
                            minValue    : intervalMinVal,
                            minText     : getLocalizedString('TimerMinMsg')+intervalMinVal,
                            value       : timerObj.getTimerInterval()
                        }],
            buttons     : [{
                            text        : getLocalizedString('Save'),
                            formBind    : true,
                            handler     : function() {           
                            if(!Ext.isEmpty(timerObj)) {                                                    
                                    var refTime = editPanel.findById('resetTimeFld_'+dashWidgetId).getValue();
                                    var autoRef = editPanel.findById('chkBoxFld_'+dashWidgetId).getValue();  
                                    var timerInterval = editPanel.findById('TimerFld_'+dashWidgetId).getValue();                                    
                                                                     
                                     if(modified){
                                     Ext.Msg.show({
                                            title: getLocalizedString('Confirm'),
                                        msg: getLocalizedString('EditWidgetConfirm'),                                       
                                        buttons: {
                                            yes :getLocalizedString('Yes') ,
                                            no : getLocalizedString('No')                                           
                                        },                                      
                                        fn: function(btn){                                          
                                            if(btn === 'yes'){                                     
                                                
                                                var updateConfig = {did : config.dashId,dwid : config.dashWidgetId, wid : config.widgetId,viewmode:true,autoRef : autoRef, refreshInterval : refTime,action : 'updatewidget'};                                                  
                                                updateWidget(updateConfig);

                                            }
                                            else{
                                                //do nothing
                                            }
                                        }
                                    });
                                    }

                                    
                                    if(!Ext.isEmpty(timerInterval)){                                    
                                        timerObj.disableAutoRefresh();                                                    
                                        timerColln.remove('timer_'+dashWidgetId);
                                        var config = timerObj.getConfig();
                                        Ext.apply(config,{timerInterval:timerInterval});                                        
                                        timerColln.add(config);
                                        newtimerObj = timerColln.getTimer('timer_'+dashWidgetId);
                                        newtimerObj.setTimerInterval(timerInterval);                                        
                                        newtimerObj.setRefreshTime(refTime);
                                        timerObj = newtimerObj;                                     
                                    }                            
                                    if(!Ext.isEmpty(refTime)){                                                              
                                        timerObj.setRefreshTime(refTime);                           
                                    }else{                                                          
                                        timerObj.disableAutoRefresh();
                                    }                               
                                    if(autoRef) {                                                                                          
                                        if(!timerObj.isAutoRefreshEnabled()){                                    
                                            timerColln.remove('timer_'+dashWidgetId);
                                            var config = timerObj.getConfig();
                                            Ext.apply(config,{timerInterval:timerInterval});                                            
                                            timerColln.add(config);
                                            newtimerObj = timerColln.getTimer('timer_'+dashWidgetId);
                                            newtimerObj.setTimerInterval(timerInterval);                                            
                                            newtimerObj.setRefreshTime(refTime);                                                                                     
                                            newtimerObj.enableAutoRefresh();                                          
                                        } else{               
                                            timerObj.enableAutoRefresh();                               
                                        }
                                    } else {                                                                    
                                        timerObj.disableAutoRefresh();
                                    }                           
    
                                } 
                            
                                widPanel.remove(editPanel);
                                widPanel.doLayout();
                                timerObj.editMode = false;
                            
                            }
                            },{
                                text        :   getLocalizedString('cancel'),
                                handler     :   function(){
                                    widPanel.remove(editPanel);
                                    widPanel.doLayout();
                                    timerObj.editMode = false;                                      
                                }
                        }]
                });
                
                if(!timerObj.editMode){
                    widPanel.add(editPanel);
                    timerObj.editMode = true;
                    widPanel.doLayout();
                }
    
    }

var timerColln = Bm.util.TimerCollection.getInstance();

function updateWidget(config){
    if(!Ext.isEmpty(config)){           
        Ext.Ajax.request({
            url:'widgetupdate.portal',
            params:{"wid":config.wid,"did":config.did,"dwid":config.dwid,"viewmode":Ext.encode(config.viewmode),"autoRef":Ext.encode(config.autoRef),"refInterval":Ext.encode(config.refreshInterval),"action":config.action},
            method:'post',
            success: function(response) { Ext.Msg.alert('',getLocalizedString('EditWidgetSuccess')) },
            failure: function(response) { Ext.Msg.alert('',getLocalizedString('EditWidgetFailure')+' '+getLocalizedString('OR') + ' '+getLocalizedString('NoPermissionError')) }    
        });
    }   
}

function refreshGraphicalWidgetData(dId,wId,dwId,viewmode) {
        new Ext.data.Connection().request({
            url: 'widgetupdate.portal?did='+dId+'&wid='+wId+'&dwid='+dwId+'&action=refreshdata&viewmode='+viewmode, 
                method: 'GET',
                noCache:true,
                success: function(responseObject) { 
                    var serverInfoResp=responseObject.responseText;
                    setXMLData('widget_'+dwId,Ext.JSON.decode(serverInfoResp));
                },
                failure: function() {
                    Ext.MessageBox.alert('Status', 'Refresh failed');
                },
                disableCaching : true
        });
}
function getObject(objectId) {
    if (Ext.isIE) {
        return Ext.query("object[id="+objectId+"]")[0];
    } else {
        return document[objectId];
    }
}

function setXMLData(objFlash, strXML) {     
    var chartobject = FusionCharts(objFlash);
    if(chartobject != null && strXML["data"] != "") {
        chartobject.setXMLData(unescape(strXML["data"]));
    }
}

function addDashboardPanel(dashboardPanel,viewMode) {
    if(viewMode == true) {
        getResultPanel().removeAll();
        getResultPanel().add(dashboardPanel);
    } else {
        Ext.getCmp('componentCtrl').remove('dashboardPanel');
        Ext.getCmp('componentCtrl').add(dashboardPanel);
    }
}

function prepareStoreParams(options, formVals) {
    //FIXME: doLayout
    var centerContainerCmp = getCenterContainer();
    if(!Ext.isEmpty(centerContainerCmp)) {
        centerContainerCmp.doLayout();
    }

    var formValues = Ext.urlDecode(formVals);
    if (Ext.isEmpty(options.params)){
        options.params = {};
    }
    options.params['start'] = options.start;
    options.params['limit'] = options.limit;
    options.params['action'] = 'jsonData';
    //set the page value.. - need to convert the following variable into string..
    options.params['page'] = ((options.params['start'] / options.params['limit']) + 1) + "";
   
    Ext.apply(options.params, formValues);
    return options;
}

function createViewTopField(options){
	var configOptions = {
            minValue: 5,
            value: 5,
            step: 5,
            blankText: getLocalizedString("viewTopMessage"),
            minText: getLocalizedString("viewTopMinErrorMessage"),
            maxText: getLocalizedString("viewTopMaxErrorMessage"),
            fieldLabel: getLocalizedString("Top"),
            labelSeparator : '',
            //labelWidth : 25,
            labelAlign : 'right',
            labelPad : 0,
            fieldStyle :{
                borderLeft : 'none',
            },
            labelCls : 'x-form-text', 
            labelClsExtra : 'x-form-field',
            labelStyle : 'border-right:none;',
            listeners:{
                'render': function(me, eOpts) {
                    var labelCell = me.getEl().down('.x-field-label-cell');
                    if(!Ext.isEmpty(labelCell)){
                        var labelCellDom = labelCell.dom; 
                        labelCellDom.style.verticalAlign = 'middle';
                        var label = labelCell.down('label');
                	    if(!Ext.isEmpty(label)){
                	    	label.dom.style.width = 'auto';
                	    	labelCellDom.width = label.getWidth();
                	    	label.dom.style.display= 'block';
                	    }
                    }
            	    
                    me.tooltipHtml = Ext.String.format(getLocalizedString("viewTopTooltip"),
                            this.minValue, this.maxValue); 
                    me.createTooltip();
                }
            }
        };
	Ext.apply(configOptions, options);
	return Ext.create('Bm.component.NumField', configOptions);
}

function populateComboItems(domId, items, selectedItemId){
	var domSelect = Ext.getDom(domId);
    if(domSelect != null ){
        for(var key in items){
        	var option = document.createElement('option');
            option.value = key;
            option.text = items[key];
            if(selectedItemId == key){
                option.selected = true;
            }    
            domSelect.add(option);
        }
    }
}

function setValue(domId, value){
    var domText = Ext.getDom(domId);
    domText.value = value;
}

var overdueWorkstepDetailFields = [
                 {name:'instance' }, 
                 {name:'status' }, 
                 {name:'priority' }, 
                 {name:'performer' }, 
                 {name:'assignedDate', type:'date', dateFormat: getLocalizedString('filterdatetimeformat')},
                 {name:'dueDate', type:'date', dateFormat: getLocalizedString('filterdatetimeformat')},
                 {name:'elapsedTime'}];

var overdueWorkstepDetailColumns = [{dataIndex: 'instance', header: getLocalizedString('instance')},
                  {dataIndex: 'status', header: getLocalizedString('status'), width: 50},
                  {dataIndex: 'priority', header: getLocalizedString('priority'), width: 50},
                  {dataIndex: 'performer', header: getLocalizedString('performer')},
                  {dataIndex: 'assignedDate', header: getLocalizedString('assignedDate'), width: 130, renderer: Ext.util.Format.dateRenderer(getLocalizedString('filterdatetimeformat'))},
                  {dataIndex: 'dueDate', header: getLocalizedString('dueDate'), width: 130, renderer: Ext.util.Format.dateRenderer(getLocalizedString('filterdatetimeformat'))},
                  {dataIndex: 'elapsedTime', header: getLocalizedString('elapsedTime'), width: 130, sortable: false}
                  ];

var overdueWorkstepDetailGridFeatures = {
		  ftype: 'filters',
		  autoReload: false, //don't reload automatically
		  local: true, //only filter locally
		  filters: [{
		          type: 'string',
		          dataIndex: 'instance'
		      }, {
		    	  type: 'list',
			      options: [
			          getLocalizedString('W_ACTIVATED'), 
			          getLocalizedString('W_SUSPENDED'),
			          getLocalizedString('W_EVENTACTIVATION_WAIT'), 
			          getLocalizedString('W_PRECONDITION_WAIT'), 
			          getLocalizedString('W_ACTIVATION_WAIT')
			       ],
			       dataIndex: 'status'
		      },{
		        type: 'list',
		        options: [
		            getLocalizedString('PriorityCritical'), 
		            getLocalizedString('PriorityHigh'), 
		            getLocalizedString('PriorityMedium'), 
		            getLocalizedString('PriorityLow')
		        ],
		        dataIndex: 'priority'
		      },{
		          type: 'string',
		          dataIndex: 'performer'
		      },{
		    	  type:'bmdatetimefilter',
		  		  dataIndex: 'assignedDate'
		      },{
		    	  type:'bmdatetimefilter',
		  		  dataIndex: 'dueDate'
		      },{
		          type: 'string',
		          dataIndex: 'elapsedTime'
		      }
		  ]
		};

function drilldownOverdueWorkstep(link){
    Ext.Ajax.request({
        url: link,
        success: function(result, request){
    	    var overdueWorkstepDetails = Ext.JSON.decode(result.responseText);
    	    var appName = overdueWorkstepDetails["processLabel"];
    	    var workstep = overdueWorkstepDetails["workstepLabel"];
    	    
    	    var overdueStore = Ext.create('Ext.data.Store',{
    	    	data : overdueWorkstepDetails,
    	    	fields : overdueWorkstepDetailFields,
    	        proxy: {
    	            type: 'memory',
    	            reader: {
    	                type: 'json',
    	                root: 'workStepDetails'
    	            }
                },
                sorters: [{
                    direction: 'ASC',
                  	property: 'dueDate'
                }]
           });
    	    
    	    var overdueWorstepDetailsExcelExport = function() {
    	        Bm.ux.exp.ExcelExporter.exportGridDataToExcel(overdueWorkstepGrid);
    	    }
    	    
    	    var printTitle = getLocalizedString('overdueInstancesFor') + '  '
    	                      + appName + '_' + workstep;
    	    var overdueWorkstepGrid = Ext.create('Bm.grid.GridPanel',{
    	        id: 'overdueWorkstepGrid',
    	        store: overdueStore,
    	        columns: overdueWorkstepDetailColumns,
    	        features: [overdueWorkstepDetailGridFeatures],
    	        titleX: printTitle,
    	        width:950,
    	        height:300,
    	        exportConfig: [{type: 'excel', callbackFn: overdueWorstepDetailsExcelExport}],
    	        showRefreshBtn: false,
    	        showTopPagingToolbar : false,
    	        showBottomPagingToolbar : false
    	    });
    	    
    	    var win = Ext.create('Ext.Window',{
    	        layout : 'anchor',
    	        resizable : false,
    	        modal:true,
    	        frame: false,
    	        title : getLocalizedString('overdueDetailsFor') + " <I>" + workstep + "</I>",
    	        items :[{
                    xtype: 'container',
                    frame: false,
                    height: 20,
                    padding: '3 0 3 0',
                    layout: {
                        type: 'table',
                        column: 1,
                        tableAttrs: {
               	            style: {
               	                width: '100%'
               	            }
                        },
                        tdAttrs: {
               	            align: 'left',
               	            style: {
               	                width: '100%'
               		        }
                        }
        	        },
                    items: [{
                        xtype: 'label',
                        text: getLocalizedString('application') + ": " + appName,
                        margin: '10 0 0 5'
                    }]
                },
                overdueWorkstepGrid
                ]
    	    });
    	    win.show();            
    	        
    	},
        failure: function(){
    		Ext.Msg.alert('Error', ErrorMsg);
    	}
    });
}