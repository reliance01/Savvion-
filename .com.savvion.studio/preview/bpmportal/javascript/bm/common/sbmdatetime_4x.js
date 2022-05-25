/**
 * @class Ext.ux.form.DateTime
 * @extends Ext.form.Field
 *
 * DateTime field, combination of DateField and TimeField
 *
 * @author      Ing. Jozef Sak?lo?
 * @copyright (c) 2008, Ing. Jozef Sak?lo?
 * @version   2.0
 * @revision  $Id: Ext.ux.form.DateTime.js 813 2010-01-29 23:32:36Z jozo $
 *
 * @license Ext.ux.form.DateTime is licensed under the terms of
 * the Open Source LGPL 3.0 license.  Commercial use is permitted to the extent
 * that the code/component(s) do NOT become part of another Open Source or Commercially
 * licensed development library or toolkit without explicit permission.
 * 
 * <p>License details: <a href="http://www.gnu.org/licenses/lgpl.html"
 * target="_blank">http://www.gnu.org/licenses/lgpl.html</a></p>
 *
 * @forum      22661
 *
 * @donate
 * <form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_blank">
 * <input type="hidden" name="cmd" value="_s-xclick">
 * <input type="hidden" name="hosted_button_id" value="3430419">
 * <input type="image" src="https://www.paypal.com/en_US/i/btn/x-click-butcc-donate.gif" 
 * border="0" name="submit" alt="PayPal - The safer, easier way to pay online.">
 * <img alt="" border="0" src="https://www.paypal.com/en_US/i/scr/pixel.gif" width="1" height="1">
 * </form>
 */
Ext.ns('Ext.ux.form');

Ext.define('Ext.ux.form.SBMDateTime', {
    extend:'Ext.form.FieldContainer',
    mixins: {
        field: 'Ext.form.field.Field'
    },
    alias: ['widget.sbmdatetime'],
    layout: {
        type: 'hbox',
        align: 'stretch'
        
    },
    
    dateFormat:'m/d/y',
    timeFormat:'g:i A',
    /**
     * @cfg {Boolean} otherToNow Set other field to now() if not explicly filled in (defaults to true)
     */
    otherToNow: true,
    
    //minWidth: 155,
    height: 22,
    style: {
    	width : "100%"
    },
    combineErrors: false,
    //msgTarget :'side',

    dateCfg:{},
    timeCfg:{},
    valueCfg:{},
    
    initComponent: function() {
        var me = this;
        me.buildField();
        me.callParent();
        this.dateField = this.down('datefield');
        this.timeField = this.down('timefield');
        this.valueField = this.down('hiddenfield');
        me.initField();
    },

    //@private
    buildField: function(){
        this.items = [
            Ext.apply({
                xtype: 'datefield',
                id: this.id + "-date",
                name : this.name + "-date",
                format: this.dateFormat || Ext.form.DateField.prototype.format,
                //minWidth: 80,
                flex: .55,
                margin: '0 1 0 0',
                submitValue: false,
                listeners : {     
                    'change':function ( dateObj, newValue, oldValue, eOpts ){            
                        		this.ownerCt.onUpdateDate();
                    		 }
                }
            },this.dateCfg),
            Ext.apply({
                xtype: 'timefield',
                id: this.id + "-time",
                name : this.name + "-time",
                format: this.timeFormat || Ext.form.TimeField.prototype.format,
                //minWidth: 75,
                flex: .45,
                margin: '0 0 0 1',
                submitValue: false,
                listeners : {     
                    'change':function ( dateObj, newValue, oldValue, eOpts ){            
            					this.ownerCt.onUpdateTime();
                    		 }
                }
            },this.timeCfg),
            Ext.apply({
                xtype: 'hiddenfield',
                id: this.id + "-datetimevalue",
                name: this.name + "-datetimevalue",
                value: '',
                getValue: function(){
					this.ownerCt.getValue();
				},
				getSubmitData: function(){
					data = {};
					data[this.ownerCt.name] = '' + this.ownerCt.getRawValue()
					return data;
				}
            }, this.valueCfg)
        ]
    },

    getValue: function() {
    	
        var value,date = this.dateField.getSubmitValue(),time = this.timeField.getSubmitValue();
        if(date){
            if(time){
                var format = this.getFormat()
                value = Ext.Date.parse(date + ' ' + time,format)
            }else{
                value = this.dateField.getValue()
            }
        }
        return value
    },

    setValue: function(value){
        this.dateField.setValue(value)
        this.timeField.setValue(value)
        this.valueField.value = this.getRawValue();
    },

    getSubmitData: function(){
        //var value = this.getValue()
        //var format = this.getFormat()
        //return value ? Ext.Date.format(value, format) : null;
    	data = {};
    	return  data[this.name] = '' + this.getRawValue();
    },

    getFormat: function(){
        return (this.dateField.submitFormat || this.dateField.format) + " " + (this.timeField.submitFormat || this.timeField.format)
    },
    
    getRawValue: function() {
    	if(this.getValue()){
    		return '' + this.getValue().getTime();
    	}
    	return '';
    },
    
    initDateValue:function() {
        return this.otherToNow ? new Date() : new Date(1970, 0, 1, 0, 0, 0);
    },

    onUpdateDate:function() {
    	var d = this.dateField.getValue();
    	if(!d){
    		this.timeField.setValue('');
    	}
        if(d && !(d instanceof Date)) {
            d = Ext.Date.parse(d, this.dateField.format);
        }
        if(d && !this.timeField.getValue()) {
        	this.timeField.setValue(this.initDateValue());
        }
        this.valueField.value = this.getRawValue(); 
    },
    
    onUpdateTime:function() {
        var t = this.timeField.getValue();
        if(t && !(t instanceof Date)) {
            t = Ext.Date.parse(t, this.timeField.format);
        }
        if(t && !this.dateField.getValue()) {
        	this.dateField.setValue(this.initDateValue());
        }
        this.valueField.value = this.getRawValue();
    }
    
})

