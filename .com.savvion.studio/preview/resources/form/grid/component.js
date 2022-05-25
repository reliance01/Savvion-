Ext.namespace("Portal.form");
jmaki.namespace("jmaki.widgets.form.grid");

/**
 *
 */
jmaki.widgets.form.grid.Widget = function(wargs) {
    var container = document.getElementById(wargs.uuid);
    var filter = "jmaki.filters.tableModelFilter";
    var _widget = this;
	var title = (YAHOO.lang.isUndefined(wargs.args.title)) ? "" : wargs.args.title;
    var publish = "/form/grid";
    var subscribe = ["/form/grid", "/table"];
    var uuid = wargs.uuid;
    var cm;
    var ds;

    var cols = [];
    var cold;
    var schema = [];
    var ged = Ext.grid.GridEditor;
    var fm = Ext.form;
    var data;

    var counter = 0;
    
    Ext.onReady(function(){
        init({});
   })
   
    
    function init() {              
        _wrapper = new Portal.form.GridWidget(wargs);
   }

    
}
//////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  Portal.form.TreeWidget
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
Portal.form.GridWidget = Ext.extend(Ext.util.Observable,
{
   width : null,
   height : null,

   constructor: function(config) {         
	this.config = config;	
	this.title = (typeof config.args.title != 'undefined') ? config.args.title : '';
	Ext.apply(this, config);
	this.counter = 0;
	// Ext apply
	Portal.form.GridWidget.superclass.constructor.call(this);		
	this.addEvents('init', 'render');	
	this.on('createStore', this.createStore, this);
	this.init();
   },
	
   init: function(){		
	//////////////////////////////////////////////////////////////////////
	// load data
	//////////////////////////////////////////////////////////////////////
	this.loadData();
   },
   
   getColumns: function(fields){
	var columns = new Array();
	for(var i=0; i<fields.length;i++){	     
	      var dataIndex = fields[i]['id'];
	      columns.push({id:dataIndex, name:dataIndex, dataIndex: dataIndex});
	}
	return columns;
   
   },
   
   
   createStore: function(data){                   	
	var columns = this.getColumns(data.columns);
	// create store 
	this.store = Ext.create('Ext.data.Store', {
		storeId: this.uuid+'_storeId',
		fields:columns,
		data:{'items':data.rows
		
		},
		proxy: {
			type: 'memory',
			reader: {
			type: 'json',
			root: 'items'
			}
		}
	});
		
	/////////////////////////////////////////////////////////////////////////
	var columnsHeaders = new Array();
	for(var i=0;i<data.columns.length;i++){
	    //alert('label:'+data.columns[i]['label']+'\nid:'+data.columns[i]['id']);
	    columnsHeaders.push({header: data.columns[i]['label'], dataIndex:data.columns[i]['id'],flex: 1});
	}	
	// create grid component
	this.grid = Ext.create('Ext.grid.Panel', {
	        id: this.uuid+'_id',
		title: this.title,
		store:this.store,
		columns: columnsHeaders,		
		autoWidth: true,
		autoHeight: true,		
		minWidth: 400,
		minHeight:300,
		selType: 'rowmodel',		
		listeners: {
		    itemclick: {
				scope: this,
				fn: function(view,rec,item,index,eventObj){					
					// publish the event					
					var record = this.store.getAt(index);	
                    var rowData = {};
					for(var i=0;i<columns.length;i++){
                         var key  = columns[i].dataIndex;							 
						 rowData[key] = (typeof record.raw[key] == 'undefined') ? '' : record.raw[key];
                    }					
					jmaki.publish('/Ext/grid/onSelect',{widgetId:this.uuid,row:rowData,rowData:rowData});				
				}
		    }
		
		},
		renderTo: Ext.get(this.uuid)
	});	
	var needsdoLayout = false;
	if(typeof this.config.args.width != 'undefined' && typeof this.config.args.width != 'auto' && Ext.isNumber(parseInt(this.config.args.width))){
	    needsdoLayout = true;
		this.grid.setWidth(parseInt(this.config.args.width));  
	}
    if(typeof this.config.args.height != 'undefined' && typeof this.config.args.height != 'auto' && Ext.isNumber(parseInt(this.config.args.height))){
	    needsdoLayout = true;
		this.grid.setHeight(parseInt(this.config.args.height)); 
	} 
    if(needsdoLayout) {	
	    this.grid.doLayout();
	}
	
   },
   
   loadData: function(){
        if(typeof this.args.service == 'undefined'){		    
			this.fireEvent('createStore',this.value)
		}else {
		    var conn = new Ext.data.Connection();	
			conn.request({
				url: this.args.service,
				method: 'POST',
				params: {
				   fiid: (document.getElementById('fiid') != null) ? document.getElementById('fiid').value : ''
				},
				scope: this,			
				success: function(req,opt){					    
                    if(!Ext.isEmpty(req.responseText,false)) {				
						try{
						var data = YAHOO.lang.JSON.parse(req.responseText);
						this.fireEvent('createStore',data);										
						}catch(e){
						    try{
							// backward compatibility							
							var _in = eval('(' + req.responseText + ')');							
							this.fireEvent('createStore',_in);		
							}catch(e){
							    Ext.Msg.alert('Error',e);
							}
							
						}
					}
				}
			});
		} 
   }	
});
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
