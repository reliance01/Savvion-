/* Copyright 2007 You may not modify, use, reproduce, or distribute this software except in compliance with the terms of the License at:
 http://developer.sun.com/berkeley_license.html
 $Id: component.js,v 1.0 2007/04/15 19:39:59 gmurray71 Exp $
*/
djd43.require("djd43.widget.DropdownDatePicker");

jmaki.namespace("jmaki.widgets.dojo.dropdowndatepicker");

/**
 * @constructor
*/
jmaki.widgets.dojo.dropdowndatepicker.Widget = function(wargs) {

    var _widget = this;
    var topic ="/dojo/dropdownDatePicker";
    var container = document.getElementById(wargs.uuid);
    var displayFormat = "MM/dd/yyyy";
    
    if (wargs.args && wargs.args.displayFormat) {
        displayFormat = wargs.args.displayFormat;
    }
    
    this.wrapper = djd43.widget.createWidget("DropdownDatePicker", {displayFormat : displayFormat}, container);
	
    var date = new Date();
    if(typeof wargs.value != 'undefined') {
        var value = wargs.value;
        if(/\d{1,2}\/\d{1,2}\/\d{4}/.test(value)) {
            //accept only mm/dd/yyyy
            date = new Date(value);
        }
    }
    if (typeof wargs.topic != 'undefined') {
        topic = wargs.topic;
        jmaki.log("Dojo dropdowndatepicker: widget uses deprecated topic property. Use publish instead. ");
    }
    if (wargs.publish) {
        topic = wargs.publish;
    }
	
    this.wrapper.datePicker.setDate(date);
	
    function formatDate(date){
	    return (date.getMonth()+"/"+date.getDay()+"/"+date.getFullYear());
    }
    
    
    this.getValue = function() {
         //return this.wrapper.datePicker.getValue().replace(/-/g, '/');
	 var date = this.wrapper.datePicker.getDate();
	 return date.format('m/d/Y');
    }
    
    this.setValue = function(_v) {
	if(typeof _v != 'undefined' && _v.length !=0)
	{
	       //accept only mm/dd/yyyy
               date = new Date(_v);
               this.wrapper.datePicker.setDate(date);
	}
    }

    this.dropdowndatepickerEvent = function(date) {
         jmaki.publish(topic + '/onSelect', {widgetId: wargs.uuid, topic : topic, type : 'onSelect', value: _widget.getValue()});
    }


    djd43.event.connect(_widget.wrapper, "onValueChanged", _widget, "dropdowndatepickerEvent" );
	
}
