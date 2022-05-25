Ext.namespace("Portal.form");
// define the namespaces
jmaki.namespace("jmaki.widgets.form.menu");

jmaki.widgets.form.menu.Widget = function(wargs) {
    var container = document.getElementById(wargs.uuid);
    var self = this;
    var label_items = [];
    var _wrapper;

    // use this topic name as this is what the dcontainer is listening to.
    var publish = "/form/menu/onclick";

    var menubar;

    if (wargs.args && wargs.args.topic) {
        publish = wargs.args.topic;
    }
     if ( wargs.publish) {
        publish = wargs.publish;
    }
    
    Ext.onReady(function(){
        init({});
   })
   
   /**
    *   Initialize by creating the wrapper object
    */    
   function init() {      
       _wrapper = new Portal.form.MenuWidget(wargs);
   }    
    
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
Portal.form.MenuWidget = Ext.extend(Ext.util.Observable,
{
   width : null,
   height : null,

   constructor: function(config) {                
	this.config = config;
	Ext.apply(this, config);	
	Portal.form.MenuWidget.superclass.constructor.call(this);		
	this.addEvents('init', 'render');		
	this.init();
   },
	
   init: function(){		
	//////////////////////////////////////////////////////////////////////
	// Create Toolbar which contains Menu inside Toolbar
	//////////////////////////////////////////////////////////////////////
	var tb = Ext.create('Ext.toolbar.Toolbar', {
	        id: this.uuid+'_id',
		renderTo: Ext.get(this.uuid),
		autoWidth: true,
		autoHeight: true,
		listeners: {
		    resize: {
			     scope: this,
				 fn: function(comp, adjWidth, adjHeight, eOpts){				     
					 var boxes = Ext.get(this.uuid).query(".x-box-inner");
					 for(var i=0; i<boxes.length; i++ ) {					     
						 if(Ext.get(boxes[i]).getWidth() == 0)
						 Ext.get(boxes[i]).setStyle('width','auto');
					 }
																			
								
				 }
			 
			}
		 
		}
	});
	// render with data model conversion
	this.loadData();
   },
   
   loadData: function(){     
     if(typeof this.args.service == 'undefined'){		    
			// data is static			
			if(typeof this.value != 'undefined' && typeof this.value.menu != 'undefined') 
				this.render(this.value.menu)
			
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
					this.render(data.menu);
					 //this.fireEvent('render',data);										
				}
			});
	 }
   },

   getDefaultData: function(){
        return [ 
        {label: 'Some Label',
            menu: [
                {label:'Some Item', action:{message: '1.jsp'}},
                {label:'Some Item 2', href:'http://jmaki.com'}
                ]},

        {label: 'Some Other Label',

            menu: [
                {label:'Some Other Item', action:{topic: '/mytopic', message:'2.jsp'}},
                {label:'Some Other Item 2', href:'http://www.sun.com'}
                ]}
        ];
   },

   render: function(data){        
	var converter = new Portal.form.MenuDataModelConverter(this.uuid,(typeof data != 'undefined')? data :this.getDefaultData());	
	//alert('output of conversion:'+YAHOO.lang.JSON.stringify(converter.convert()));
	Ext.getCmp(this.uuid+'_id').add(converter.convert());	
   }   
});
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  Data Model Converter
//  Converts jMaki data model to EXT JS Tree data model
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
Portal.form.MenuDataModelConverter = Ext.extend(Ext.util.Observable,
{
   
   constructor: function(uuid,config) {                
	this.jMakiData = config;
	this.uuid = uuid;
	// Ext apply
	Portal.form.MenuDataModelConverter.superclass.constructor.call(this);		
	this.addEvents('init');	
	this.on('convert', this.convertData, this);
	this.init();
   },
	
   init: function(){		
	//////////////////////////////////////////////////////////////////////
	// Create first level conversion
	//////////////////////////////////////////////////////////////////////
	this.xMenuModel = new Array(); 	
	for(var i=0; i<this.jMakiData.length;i++){
	        // for each child in frirst level do the conversion
		menu = this.convertMenu(this.jMakiData[i]);
		if(typeof this.jMakiData[i].menu != 'undefined'){		     
		     menu.menu = this.createChildren(menu,this.jMakiData[i].menu);
		}
		this.xMenuModel.push(menu);		
	}	
	
   },
   
   convertMenu: function(node){		    
    var message = '';
	var href = '';
	var _uuid = this.uuid;
    try{
	   message = (typeof node['action'] != 'undefined' && typeof node['action']['message'] != 'undefined') ?  node['action']['message'] : '';
	   href = (typeof node['href'] != 'undefined' && typeof node['href'] != 'undefined') ?  node['href'] : '';
	}catch(e){};    
	
    return {
	     text: node.label,
		 _href: href,		 
		 message: message,
		 disabled: (typeof node.disabled != 'undefined' && node.disabled) ? true: false,
	     handler: function(item) {			
			if(!item.disabled){
				if(typeof item._href != 'undefined' && item._href.length != 0)
				window.location.href = item._href;
				// Publish jMaki Event
				// This code takes care of event management mechanisms	
				var message = (typeof item.message != 'undefined') ? item.message : {};							
				jmaki.publish("/Ext/menu/onClick",{widgetId: _uuid,value:item.text,message:message});
				
			}
	     }
                  
	};   
   },
   
   createChildren: function(menu,sub_menus){             
	var menus = new Array();
	for(var i=0; i<sub_menus.length; i++){
	     var childNode = this.convertMenu(sub_menus[i]);	     
	     if(typeof sub_menus[i].menu != 'undefined'){
	            childNode.menu = this.createChildren(childNode,sub_menus[i].menu);
	     }	     
	     menus.push(childNode);
	}	
	return menus;  
   },
   
   convert: function(){
        return  this.xMenuModel;
   }
   
})

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////