Ext.namespace("Portal.form");
jmaki.namespace("jmaki.widgets.form.slider");

jmaki.widgets.form.slider.Widget = function(wargs) {
    
    var publish = "/form/slider";
    var subscribe = ['/form/slider','/slider'];
    var _wrapper;
    var uuid = wargs.uuid;
    var self = this;
    var sliderType = "H"; //default to horizontal "H"
    var tickSize = 10;  // Slider Tick Size (not pixel)
    var animate = true; //animation on/off flag
    var minValue = 0;   //minimum value (not pixel)
    var maxValue = 100; //maximum value (not pixel)
    var initialValue = 0; //initial value (not pixel)
    var sizeInPixels = 200; //slider size in pixels
    if(!YAHOO.lang.isUndefined(wargs.args.visible) && !wargs.args.visible)
	   sbm.widgets.hide(uuid);
	
    Ext.onReady(function(){
        init({});
    })
   
    
    function init() {             
       _wrapper = new Portal.form.SliderWidget(wargs);
   }
   
   this.setValue = function(_v){
       _wrapper.setValue(_v);
   }
   
   this.getValue = function(){
       return _wrapper.getValue();
   }
   
   this.getValidationType = function(){		
		return 'no_validation';
   }
    
};
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////
Portal.form.SliderWidget = Ext.extend(Ext.util.Observable,
{
   width : null,
   height : null,

   constructor: function(config) {   
	this.config = config;
	this.type = config.args.sliderType;	
	this.uuid = config.uuid; 		   
	this.value = config.value;

	Ext.apply(this, config);	
	Portal.form.SliderWidget.superclass.constructor.call(this);		
	this.addEvents('init');		
	this.init();
	
   },
	
   init: function(){		
	//////////////////////////////////////////////////////////////////////
	// Create Slider
	//////////////////////////////////////////////////////////////////////	
	this.value = (typeof this.value == 'undefined')? '': this.value;
	this.sliderType = (typeof this.args.sliderType == 'undefined')? 'H': this.args.sliderType;
	this.sizeInPixels = (typeof this.args.sizeInPixels == 'undefined')? 200 : this.args.sizeInPixels;
	this.widthInPixels = this.sizeInPixels;
	this.heightInPixels = this.sizeInPixels;
	this.animate = (typeof this.args.animate == 'undefined')? true: this.args.animate;
	this.tickSize = (typeof this.args.tickSize == 'undefined')? 10 : this.args.tickSize;
	this.minValue = (typeof this.args.minValue == 'undefined')? 0: this.args.minValue;
	this.maxValue = (typeof this.args.maxValue == 'undefined')? 100: this.args.maxValue;
	this.initialValue = (typeof this.args.initialValue == 'undefined')? '': this.args.initialValue;
	this.toolTip = (typeof this.args.toolTip == 'undefined')? '': this.args.toolTip;
	if (this.sliderType == 'H' || this.sliderType == 'Horizontal'){
		this.vertical = false;
		this.widthInPixels=this.sizeInPixels;
		this.heightInPixels=0;
	}   else {
		this.vertical = true;
		this.widthInPixels=0;
		this.heightInPixels=this.sizeInPixels;
	}


	Ext.create('Ext.slider.Single', {
	    id: this.uuid+'_id',
		width: this.widthInPixels,
        height:this.heightInPixels,
		value: this.initialValue,
		increment: this.tickSize,
		minValue: this.minValue,
		maxValue: this.maxValue,
		vertical:this.vertical,
		tipText: function(thumb){
            return Ext.String.format('<b>{0}% complete</b>', thumb.value);
        },
		listeners: {
			change: {
				//element: this, //bind to the underlying el property on the panel
				scope: this,
				fn: function(){ jmaki.publish("/yahoo/slider/onChange", {widgetId: this.uuid, value: this.getValue()}); }
			}
		},
		renderTo: Ext.get(this.uuid) 
	});	
	
	if(this.toolTip.length != 0)
	   Bm.util.setToolTip(this.uuid,this.args.toolTip);		
   },
   
   setValue: function(v){         
         Ext.getCmp(this.uuid+'_id').setValue(v);
   },

   getValue: function(){          
	return Ext.getCmp(this.uuid+'_id').getValue();
   }   
});
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////