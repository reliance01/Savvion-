Ext.Loader.setConfig({enabled:true});
Ext.Loader.setPath('Ext.ux.DateTimeField', getContextPath()+'/bpmportal/javascript/ext4x/src/ux/DateTimeField.js');
Ext.Loader.setPath('Ext.ux.DateTimePicker', getContextPath()+'/bpmportal/javascript/ext4x/src/ux/DateTimePicker.js');
Ext.namespace("Portal.form");
jmaki.namespace("jmaki.widgets.form.datetime");
jmaki.widgets.form.datetime.Widget = function(wargs) {
    var _widget = this;
    var topic ="/form/datetime";
    var container = document.getElementById(wargs.uuid);
    var displayFormat = "MM/dd/yyyy";
    var _wrapper;
    var uuid = wargs.uuid;
	if(!YAHOO.lang.isUndefined(wargs.args.visible) && !wargs.args.visible)
	   sbm.widgets.hide(uuid);
	
    
    
    Ext.onReady(function(){
        init({});
    })
    
   
    
        
    function init() {             
       _wrapper = new Portal.form.DateTimeWidget(wargs);
       var timeDiv = document.getElementById(wargs.uuid+'_div');
       if (!Ext.isEmpty(timeDiv)) {
           var ptd = timeDiv.parentNode;
           while(!Ext.isEmpty(ptd) && !Ext.isEmpty(ptd.tagName) && ptd.tagName.toLowerCase() !== "td") {
               ptd = ptd.parentNode;
           }
           if (!Ext.isEmpty(ptd) && !Ext.isEmpty(ptd.tagName) && ptd.tagName.toLowerCase() === "td" && !Ext.isEmpty(ptd.align)) {
               timeDiv.align = ptd.align;
           }
       }
   }
   this.setValue = function(_v){       
       _wrapper.setValue(_v);
   }
   
   this.getValue = function(){
       return _wrapper.getValue();
   }
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////
Portal.form.DateTimeWidget = Ext.extend(Ext.util.Observable,
{
   width : null,
   height : null,

   constructor: function(config) {                
	this.config = config;	
	this.displayFormat = config.args.displayFormat;	
	this.dateOnly = (config.args.dateonly) ? true : false;
	this.jsDisplayFormat =(config.args.dateonly) ?  _dateFormats.dateOnly : _dateFormats.date;
	this.required = (typeof config.args.validation != 'undefined' &&
         	typeof config.args.validation['isRequired'] != 'undefined') ? config.args.validation['isRequired']: false;	
	// TO-DO we need to be able to read the date format
	// from widget configuration and then convert the date object to proper format
	//this.jsDisplayFormat = 'm/d/Y';	
	//this.jsDisplayFormat = _dateFormats.date;	
	Ext.apply(this, config);	
	Portal.form.DateTimeWidget.superclass.constructor.call(this);		
	this.addEvents('init');		
	this.init();
	
   },
	
   init: function(){		
	//////////////////////////////////////////////////////////////////////
	// Create Date Picker
	//////////////////////////////////////////////////////////////////////		
	// we must correct the widget namespace from doo to form
	topic = "/dojo/datepicker/onSelect";
	//Ext.create('Ext.form.field.Date', {
    var type = 'Ext.ux.DateTimeField';
    if(this.isDateOnly()) {
        type = 'Ext.form.field.Date';
    }
    Ext.create(type, {
		//title: 'Choose a future date:',
		id: this.uuid+'_id',
		width: 200,
		bodyPadding: 10,
		readOnly: this.args.readonly,
		format: this.jsDisplayFormat,
		allowBlank: !this.required,
		renderTo: Ext.get(this.uuid+'_div'),
		listeners: {
		      change: {				
				scope:this,
				fn: function(){
                   jmaki.publish("/dojo/dropdownDatePicker/onSelect", {widgetId: this.uuid, value: this.getValue()});				
				}
			}
		
		},
		
		items: [{
		    id: this.uuid+'_wrapper_id',
			xtype: 'datefield',
			//vtype:'date'
			format: this.jsDisplayFormat,
			//minDate: new Date(),
			handler: function(picker, date) {
				// do something with the selected date
				jmaki.publish(topic,{widgetId:this.uuid,value:Ext.get(this.uuid+'_id').getValue()});
			}
		}]
	});	
	
	if(this.args.toolTip.length != 0)
	   Bm.util.setToolTip(this.uuid,this.args.toolTip);		
	
	if(typeof this.value != 'undefined' && this.value.length != 0)		
		Ext.getCmp(this.uuid+'_id').setValue(this.value);		
	
	if(typeof this.config.args.service != 'undefined'){
		// invoke the service
		var conn = new Ext.data.Connection();	
			conn.request({
				url: this.config.args.service,
				method: 'POST',
				scope: this,			
				success: function(req,opt){					    
                    if(!Ext.isEmpty(req.responseText,false)) {				
						try{
						   this.setValue(req.responseText)
						}catch(e){
						   Ext.Msg.alert('Error',e);
							
						}
					}
				}
		});
		
		
	}	
	//////////////////////////////////////
	/*Ext.create('Ext.picker.Date', {
	        id: this.uuid+'_id',
		width: 200,
		value: 50,
		increment: 10,
		minValue: 0,
		maxValue: 100,
		renderTo: Ext.get(this.uuid)
	});*/	
	
   },
   
   setValue: function(v){         
        var _v ='';
		if(this.dateOnly) {  
              var _date = Ext.util.Format.date(v, _dateFormats.date);
		      _v = Ext.util.Format.date(_date,  _dateFormats.dateOnly);
		}else _v = v;		
		Ext.getCmp(this.uuid+'_id').setValue(_v);
		   
   },

   getValue: function(){    
	return (this.dateOnly) ? this.convertDate(Ext.getCmp(this.uuid+'_id').getRawValue()) : Ext.util.Format.date(Ext.getCmp(this.uuid+'_id').getRawValue(),this.jsDisplayFormat);
   }, 

   isDateOnly: function(){
      return this.dateOnly;
   },

   convertDate: function(v){
     var _date = Ext.util.Format.date(v, _dateFormats.dateOnly);     
	 return Ext.util.Format.date(_date,  _dateFormats.date);

   }   
});
