/**
 *   Handler the opearions of collection of business
 *   
 *   -contextPath: the context path of web application
 *   -dataSlotName: the name of collection of business object dataslot
 *   -renderTo: the id of element which grid will be rendered
 *   -fieldDefs: definition of fileds for column names
 *   -extGridColumnModel: the ext column models
 *   -gridData: the data of grid with respect to grid column model, type is Arrayreader
 **/
function BusinessObjectHandler(contextPath,
                               dataSlotName,
			       businessObjectName,
			       renderTo,
			       fieldDefs,
			       extGridColumnModel,
			       gridData) {
	this.contextPath = contextPath;
	this.dataSlotName = dataSlotName;
	this.businessObjectName = businessObjectName;
	this.renderTo = renderTo;  
	this.fieldDefs = fieldDefs;
	this.extGridColumnModel = extGridColumnModel;
	this.gridData = gridData;
	this.init();
	var div = dataSlotName;	
}

BusinessObjectHandler.constructor = BusinessObjectHandler;
BusinessObjectHandler.prototype = {
   init: function(){
   	        Ext.QuickTips.init();	                  	
		this.gridStore = new Ext.data.Store({
		        proxy: new Ext.data.MemoryProxy(this.gridData),
		        reader: new Ext.data.ArrayReader({},this.fieldDefs)
                });
                this.gridStore.load();
		
		
	        
		var handler = this;
		this.gridPanel = new Ext.grid.GridPanel({
	        store: this.gridStore,
	        cm: this.extGridColumnModel,
	        loadMask: true,
	        autoHeight:true,
	        renderTo: 'businessObjectGrid',
	        //width:600,
                //height:300,
	        title:this.businessObjectName+' List',
	        frame:true,
	        viewConfig: {
	            autoFill: true,
	            forceFit:true,
	            deferEmptyText: false,
	            emptyText: '<div style="{text-align: center}"><span style="font-size: 9pt; font-weight: normal">There are no record.<br/>If you want to Add please click on &quot;Add ' + this.businessObjectName + ' to List&quot; button.</span></span></div>'
	        },
                tbar: [{text:'Add '+this.businessObjectName+' to List' , handler: this.addBusinessObject,scope: this,pressed: true, enableToggle: true},'-',
		       {text:'Reset List', handler: handler.addBusinessObject, pressed: true }],	        
	        bbar: new Ext.PagingToolbar({
		       store: this.gridStore,
		       pageSize: 5})
	   });
	},
	
	toggleDetail: function(btn, pressed){
		    alert("btn:"+btn);
		    var view = grid.getView();
		    view.showPreview = pressed;
		    view.refresh();
	},
	
	addBusinessObject: function(){    
	      $(this.dataSlotName).innerHTML ="Loading...";
	      sbm.utils.updateContent(this.dataSlotName,'GET',"/sbm/bpmportal/common/businessobjects/addBusinessObject.portal?dataslotName="+this.dataSlotName); 
    	}
}
