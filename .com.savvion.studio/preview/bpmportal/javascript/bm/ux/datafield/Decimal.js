Ext.namespace("Bm.ux.datafield");

/**
 * Bm.component.datafield.Decimal Extension Class
 *
 * This will create a decimal field for the CX Process BPM portal pages.
 *  
 * @class Bm.component.datafield.Decimal
 * @extends Ext.form.field.Number
 * @constructor
 */
Ext.define('Bm.ux.datafield.Decimal', {
    extend: 'Ext.form.field.Number',
    alias: ['widget.decimaldatafield'],
    allowDecimals: true,
    enforceMaxLength: true,
    autoStripChars: true,
    invalidCls: 'x-form-invalid-field-without-underline',
    msgTarget: 'side',
    width: 200,
    allowRound: false,
    autoFitErrors: false,
    hideLabel: true,
    plugins: ['clearbuttonx'],
    digitsBefore: 0,    
    styleHtmlContent: true,
    localMessageMap: [],
    messageKeys: [],
    statics: {
        validateInputParams: function(params) {
            var isValid = true;
            if(Ext.isEmpty(params['precision']) ||
                Ext.isEmpty(params['scale']) ||
                (parseInt(params['precision']) < parseInt(params['scale']))) {
                isValid = false;
            } 
            return isValid;
        }
    },
    initComponent: function(config) {
        Ext.apply(this, config);
        var me = this;
        me.setMessageMapKeys();
        me.setDefaultValues();
        me.callParent();
    },
    setMessageMapKeys: function() {
        this.messageKeys[0] = 'decimalFieldEmpty';
        this.messageKeys[1] = 'decimalFieldTooltip';
        this.messageKeys[2] = 'invalidDecimalField';
    },
    setDefaultValues: function() {
        var me = this;
        
        me.messageMap = this.getLocalMessageMap();
        
        if(Ext.isEmpty(me.decimalSeparator)) {
            me.decimalSeparator = '.';
        }
        
        if(!Ext.isEmpty(me.mandatory)) {
            if (me.mandatory == "true") {
                me.allowBlank = false; 
            }
        }
        
        if(!Ext.isEmpty(me.isReadonly)) {
            if (me.isReadonly == "true") {
                me.readOnly = true;
            } else {
                me.readOnly = false;
            }
        }
        
        if(!Ext.isEmpty(me.hasSpinner)) {
            me.hideTrigger = !me.hasSpinner;
        }
        
        if(!Ext.isEmpty(me.spinnerStep)) {
            me.step = parseInt(me.spinnerStep);
        }
        
        if(!Ext.isEmpty(me.value)) {
            me.value = me.value;
        } else {
            me.value = 0;
        }
        
        if(Ext.isEmpty(me.fieldLabel)) {
            me.fieldLabel = me.fieldName;
        }
        
        if(!Ext.isEmpty(me.valueAlign) && me.valueAlign == 'right') {
            me.fieldCls = 'a-form-num-field-right';
            me.clearButtonAlign = 'left';
        }
        
        var precision = parseInt(me.precision) + 2;
        me.name = me.fieldName;
        me.id = me.fieldName;
        me.maxLength = precision;
        me.decimalPrecision = parseInt(me.scale);
        me.renderTo = 'decimalField_' + me.id;
        me.digitsBefore = (precision - me.scale) - 2;
        me.blankText = this.getLocalizedMessage(this.messageKeys[0]);
    },
    getLocalMessageMap: function() {
        this.localMessageMap.decimalFieldTooltip = "Digits before decimal point is {0}<br>Digits after decimal point is {1}";
        this.localMessageMap.invalidDecimalField = "Entered value for {0} is invalid. <br>Digits before decimal point is {1}<br>Digits after decimal point is {2}";
        this.localMessageMap.decimalFieldEmpty = "{0} is mandatory.";
        return this.localMessageMap;
    },
    getLocalizedMessage: function(key, args) {
        var msg = this.messageMap[key];
        if(!this.readOnly && !Ext.isEmpty(args)) {
            for (var ix = 0; ix < args.length; ix++) {
                msg = msg.replace('{' + ix + '}', args[ix]);
            }
        } 
        
        if(Ext.isEmpty(msg)) {
            msg = key;
        }  
        return msg;
    },
    validateDecimalField: function(me, event, eOpts) {
        var digitsAfter = me.decimalPrecision;
        var digitsBefore = (me.maxLength - digitsAfter) - 2;
        var msg;
        var regExpr = new RegExp("^(\\+|\\-)?\\d{0," + digitsBefore + "}(\\" + me.decimalSeparator + "\\d{0," + digitsAfter + "})?$");
        if(!me.readOnly && me.getRawValue().length == 0 && !me.allowBlank) {
            msg = this.getLocalizedMessage(this.messageKeys[0], [me.fieldLabel]);
            me.markInvalid(msg);
        }
        if(!me.readOnly && !regExpr.test(me.getRawValue())) {
            msg = this.getLocalizedMessage(this.messageKeys[2], [me.fieldLabel, digitsBefore, digitsAfter]);
            me.markInvalid(msg);
        }
    },
    handleMouseOverInputField: function(event, htmlElement, object) {
        Ext.getCmp(this.id + '_ttip').show();
    },
    handleMouseOutOfInputField: function(event, htmlElement, object) {
        Ext.getCmp(this.id + '_ttip').hide();
    }, 
    createToolTip: function() {
        Ext.create('Ext.tip.ToolTip', {
            id: this.id + '_ttip',
            target: this.id,
            html: this.getLocalizedMessage(this.messageKeys[1], [this.digitsBefore,this.scale]),
            trackMouse: true
        });
    },
    listeners: {
        'afterrender': function(me, eOpts) {
            if(!me.readOnly) {
                this.createToolTip();
                var bodyEl = me.bodyEl;
                bodyEl.on('mouseover', this.handleMouseOverInputField, this);
                bodyEl.on('mouseout', this.handleMouseOutOfInputField, this);
                me.on('blur', this.validateDecimalField, this);
            }
        }
    }
});

/*
*  This will avoid the rounding off the digits after decimal point based on allowRound config in Bm.ux.datafield.Decimal
*/
Ext.define('Ext.overrides.ux.datafield.Decimal', {
    override: 'Bm.ux.datafield.Decimal',
 
    valueToRaw: function(value) {
        var me = this,
            decimalSeparator = me.decimalSeparator,
            re = new RegExp('(\\d*.\\d{' + me.decimalPrecision + '}).*');
 
        value = me.parseValue(value);
        value = !me.allowRound ? (value + '').replace(re, '$1') : me.fixPrecision(value);
 
        value = Ext.isNumber(value) ? value : parseFloat(String(value).replace(decimalSeparator, '.'));
        value = isNaN(value) ? '' : String(value).replace('.', decimalSeparator);
        return value;
    }
});