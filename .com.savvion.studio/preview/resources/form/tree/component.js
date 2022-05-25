Ext.namespace("Portal.form");
jmaki.namespace("jmaki.widgets.form.tree");

jmaki.widgets.form.tree.Widget = function(wargs) {
   var _widget = this;
   var _wrapper = null;
   var ie = /MSIE/i.test(navigator.userAgent);
   var uuid = wargs.uuid;
   wargs.publish = "/form/tree";
   wargs.subscribe = ["/form/tree", "/tree"];
   
   
   Ext.onReady(function(){
        init({});
   })
   
    
   function init() {      
       _wrapper = new Portal.form.TreeWidget(wargs);
   }

   this.setValue = function(v){
       
   }   
    
   this.getValue = function(){
       
   }   	
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
var treeData = {"root":{"title":"Regions","action":{"message":{"value":"regions"}},"value":"regions","children":[{"title":"Please select a region","action":{"message":{"value":"-1"}},"value":"-1","expanded":true},{"title":"western","action":{"message":{"value":"regions.western"}},"value":"regions.western","children":[{"title":"Please select a state in west","action":{"message":{"value":"-1"}},"value":"-1","expanded":true},{"title":"california","action":{"message":{"value":"regions.western.california"}},"value":"regions.western.california","children":[{"title":"Please select a city in CA","action":{"message":{"value":"-1"}},"value":"-1","expanded":true},{"title":"tiburon","action":{"message":{"value":"regions.western.california.tiburon"}},"value":"regions.western.california.tiburon","expanded":true},{"title":"santa clara","action":{"message":{"value":"regions.western.california.santaclara"}},"value":"regions.western.california.santaclara","expanded":true}],"expanded":true},{"title":"arizona","action":{"message":{"value":"regions.western.arizona"}},"value":"regions.western.arizona","children":[{"title":"Please select a city in AZ","action":{"message":{"value":"-1"}},"value":"-1","expanded":true},{"title":"tucson","action":{"message":{"value":"regions.western.arizona.tucson"}},"value":"regions.western.arizona.tucson","expanded":true},{"title":"phoenix","action":{"message":{"value":"regions.western.arizona.pheonix"}},"value":"regions.western.arizona.pheonix","expanded":true}],"expanded":true}],"expanded":true},{"title":"eastern","action":{"message":{"value":"regions.eastern"}},"value":"regions.eastern","children":[{"title":"Please select a state in east","action":{"message":{"value":"-1"}},"value":"-1","expanded":true},{"title":"illinois","action":{"message":{"value":"regions.eastern.illinois"}},"value":"regions.eastern.illinois","children":[{"title":"Please select a city in IL","action":{"message":{"value":"-1"}},"value":"-1","expanded":true},{"title":"chicago","action":{"message":{"value":"regions.eastern.illinois.chicago"}},"value":"regions.eastern.illinois.chicago","expanded":true},{"title":"champaign","action":{"message":{"value":"regions.eastern.illinois.champagne"}},"value":"regions.eastern.illinois.champagne","expanded":true}],"expanded":true},{"title":"florida","action":{"message":{"value":"regions.eastern.florida"}},"value":"regions.eastern.florida","children":[{"title":"Please select a city in FL","action":{"message":{"value":"-1"}},"value":"-1","expanded":true},{"title":"miami","action":{"message":{"value":"regions.eastern.florida.miami"}},"value":"regions.eastern.florida.miami","expanded":true},{"title":"orlando","action":{"message":{"value":"regions.eastern.florida.orlando"}},"value":"regions.eastern.florida.orlando","expanded":true}],"expanded":true}],"expanded":true}],"expanded":true},"sdeWidgetKey":"Tree"};
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  Portal.form.TreeWidget
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
Portal.form.TreeWidget = Ext.extend(Ext.util.Observable,
{
   width : null,
   height : null,

   constructor: function(config) {                
	 this.config = config;
	 Ext.apply(this, config);
	 this.counter = 0;
	 this.rootVisible = (typeof this.config.args.showRoot != 'undefined' && this.config.args.showRoot) ? true : false;
	 // Ext apply
	 Portal.form.TreeWidget.superclass.constructor.call(this);		
	 this.addEvents('init', 'render');	
	 this.on('createStore', this.createStore, this);
	 this.init();
   },
	
   init: function(){			
     // if tree bound to service
	 // if the source is static
	 this.loadData();	
	
   },
   
   createStore: function(data){
        // convert tree model  
        var treeModel = this.convertTreeModel(data);
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
					var data = YAHOO.lang.JSON.parse(req.responseText);
					this.fireEvent('createStore',data);										
				}
			});
		}   
   },
   
   convertTreeModel: function(data){        	
	var modelConverter = new Portal.form.TreeDataModelConverter(data);
	// alert('output of conversion:'+YAHOO.lang.JSON.stringify(modelConverter.convert()));
	this.treeStore = Ext.create('Ext.data.TreeStore', {
		root: {
		       expanded: true,
		       children: modelConverter.convert()
		   
                }
        });
		
	this.tree = Ext.create('Ext.tree.Panel', {		
		id: this.uuid+'_id',
		animate:true, 
		autoHeight:true,	
        // EXT JS4 Tree widget has height problem
		height: 200,		
		autoWidth: true,
		autoScroll:true,
		store: this.treeStore,
		rootVisible: this.rootVisible,
		renderTo: Ext.get(this.uuid)
	});  
    var needsdoLayout = false;
	if(typeof this.config.args.width != 'undefined' && typeof this.config.args.width != 'auto' && Ext.isNumber(parseInt(this.config.args.width))){
	    needsdoLayout = true;
		this.tree.setWidth(parseInt(this.config.args.width));  
	}
    if(typeof this.config.args.height != 'undefined' && typeof this.config.args.height != 'auto' && Ext.isNumber(parseInt(this.config.args.height))){
	    needsdoLayout = true;
		this.tree.setHeight(parseInt(this.config.args.height)); 
	} 
    if(needsdoLayout) {	
	    this.tree.doLayout();
	}
	
	if(this.args.toolTip.length != 0)
	   Bm.util.setToolTip(this.uuid,this.args.toolTip);		
	
	var uuid = this.uuid;
	this.tree.on('itemclick',function(view, rec, item, index, eventObj){        
           var message = (typeof rec.raw.message != 'undefined') ? rec.raw.message : {};		
	       jmaki.publish('/Ext/tree/onClick',{widgetId:uuid,text:rec.get('text'),message:message});
	});
	
	this.tree.on('itemcollapse',function(node, eOpts){	            
		   jmaki.publish('/Ext/tree/onCollapse',{widgetId:uuid,text:node.data.text});
	});
	
	this.tree.on('itemexpand',function(node, eOpts){          	
		   jmaki.publish('/Ext/tree/onExpand',{widgetId:uuid,text:node.data.text});
	});
   }

	
	
});
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  Data Model Converter
//  Converts jMaki data model to EXT JS Tree data model
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
Portal.form.TreeDataModelConverter = Ext.extend(Ext.util.Observable,
{
   
   constructor: function(config) {                
	this.jMakiData = config;
	this.counter = 0;
	// Ext apply
	Portal.form.TreeDataModelConverter.superclass.constructor.call(this);		
	this.addEvents('init', 'render');	
	this.on('convert', this.convertData, this);
	this.init();
   },
	
   init: function(){		
	//////////////////////////////////////////////////////////////////////
	// Create first level conversion
	//////////////////////////////////////////////////////////////////////	
	this.xTreeModel = {root: {text: this.jMakiData.root.label}}; 
	var childs = this.jMakiData.root.children;	
	var children = new Array();
	for(var i=0; i<childs.length;i++){
	    // for each child in frirst level do the conversion
		node = this.convertNode(childs[i]);
		if(typeof childs[i].children != 'undefined'){
		     node.children = this.createChildren(node,childs[i].children);
		}
		children.push(node);		
	}	
	this.xTreeModel.root.children = children;
   },
   
   convertNode: function(node){   
	var leaf = (typeof node['children'] == 'undefined') ? true: false;	
	var message = {};
	try{
	message = (typeof node['action'] != 'undefined' && typeof node['action']['message'] != 'undefined') ?  node['action']['message'] : {};
	}catch(e){}
	return {
	     text: (typeof node.title == 'undefined')? node.value : node.title,
	     leaf: leaf,
		 message: message,
		 expanded: (typeof node.expanded != 'undefined') ? node.expanded : false
	};
   
   },
   
   createChildren: function(node,children){        
	var childs = new Array();
	for(var i=0; i<children.length; i++){
	     var childNode = this.convertNode(children[i]);
	     if(typeof children[i].children != 'undefined'){
	            childNode.children = this.createChildren(childNode,children[i].children);
	     }	     
	     childs.push(childNode);
	}	
	return childs;  
   },
   
   convert: function(){
        return  this.xTreeModel.root.children;
   }
   
})