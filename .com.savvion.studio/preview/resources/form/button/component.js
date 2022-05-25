Ext.namespace("Portal.form");
jmaki.namespace("jmaki.widgets.form.button");

jmaki.widgets.form.button.Widget = function(wargs) {
    
    var publish = "/yahoo/button";
    var self = this;
    var button;
    var action;
    var buttonGroup;
    var uuid = wargs.uuid; 
    var buttonId = uuid + "_btn";
    var publishSound = false;
    var duallist = false;
    var sourcelist = "";
    var targetlist = "";
	if (typeof wargs.args.duallist != 'undefined') duallist = wargs.args.duallist;
    if (typeof wargs.args.sourcelist != 'undefined') sourcelist = wargs.args.sourcelist;
    if (typeof wargs.args.targetlist != 'undefined') targetlist = wargs.args.targetlist;
    
    
    Ext.onReady(function(){
        init({});
    })
   
    
    function init() {      
       _wrapper = new Portal.form.ButtonWidget(wargs,updateDependent);
    }    
	
	function updateDependent() {	
		jmaki.log("UpdateDependent button");
		if (duallist == false)
		return;
		var srcWidget = jmaki.getWidget(sourcelist);
		jmaki.log("SRC " + srcWidget.getValue());
		var targetWidget = jmaki.getWidget(targetlist);
		jmaki.log("TARGET " + targetWidget);
		var opts = srcWidget.getValues();
		jmaki.log("OPTS " + opts);
		targetWidget.addOptions(opts); 
		srcWidget.removeOptions();
    }   
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////
Portal.form.ButtonWidget = Ext.extend(Ext.util.Observable,
{
   width : null,
   height : null,

   constructor: function(config,widget) {                
	this.config = config;
	this.widget = widget;
	this.type = config.args.type;	
	this.uuid = config.uuid; 		
	Ext.apply(this, config);	
	// Ext apply
	Portal.form.ButtonWidget.superclass.constructor.call(this);		
	this.addEvents('init', 'render');		
	this.init();	
   },
	
   init: function(){		

    this.label = (typeof this.args.label == 'undefined')? '': this.args.label;
	//////////////////////////////////////////////////////////////////////
	// initialize EXT JS Button
	//////////////////////////////////////////////////////////////////////
	var widget = this.widget;
	Ext.create('Ext.Button', {
		text: this.label,
		id: this.uuid+'_id',
		type: (this.type.toLowerCase() == 'button') ? 'button' : 'submit',
		renderTo: Ext.get(this.uuid),
		scope: this,
		handler: function() {                
			jmaki.publish('/form/button/onClick',{widgetId:this.uuid,text:this.label});
			jmaki.publish('/yahoo/button/onClick',{widgetId:this.uuid,text:this.label});
			if(this.type.toLowerCase() == 'button') {
			   	// publish the event to the proper topic	
                if(this.args.duallist) widget.call(arguments);				
				
			}else if(this.type.toLowerCase() == 'reassign'){
			      if(AlertReassign())
				     this.submit();		
			}else {
			        this.submit();		
			}
		}
	});	
	
   },

   submit: function(){        
	var name ='';
	if(this.type.toLowerCase() == 'complete'){
	     name = 'bizsite_completeTask';	     
	}else if(this.type.toLowerCase() == 'save'){
	     name = 'bizsite_saveTask';	     
	}else if(this.type.toLowerCase() == 'reassign') {
	     name = 'bizsite_reassignTask';     
	}else if(this.type.toLowerCase() == 'cancel') {
	     sbm.utils.cancel();
	     return; 
	}else if(this.type.toLowerCase() == 'reset'){
	     sbm.utils.reset();
	     return;     
	}
	var value = name;
	
	
	if(this.type.toLowerCase() == 'complete' || this.type.toLowerCase() == 'save' || this.type.toLowerCase() == 'reassign' || this.type.toLowerCase() == 'submit'){	     			
	     Ext.DomHelper.append('northDiv',  {
		id: name,
		name: name,
		value: value,
		tag: 'input',
		type: 'hidden'
	     });	     
	     
	     //this.onsubmit = new Function('return false');					
	     // The submit method does not invoke the onsubmit event handler. Call the onsubmit event handler directly.
	     if(document.forms[0].onsubmit()){	       
		   if(allWidgets.length > 0)formWidgetHandler.saveDataSlots();		   
		   document.forms[0].submit();	
		 }
	}
	    
   }   
});
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


///////////////////////////////////////////////////////////////////////////////////////////////////////