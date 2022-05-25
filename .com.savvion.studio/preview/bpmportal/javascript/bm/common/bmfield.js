Ext.namespace('Bm.component');


/**
  * Bm.component.Combo Extension Class
  *
  *
  * @class Bm.component.Combo
  * @extends Ext.form.ComboBox
  * @constructor
  * @param {Object} config Configuration options
  */
Bm.component.Combo = function(config){ 
   	this.validValues = config.data;	
	config.store = new Ext.data.SimpleStore({        
        fields: [
            'value',
            'valueLabel'
        ],    
        data : Ext.decode(config.data)
    });
    Bm.component.Combo.superclass.constructor.call(this, config);
 
}; 

Ext.extend(Bm.component.Combo, Ext.form.ComboBox, {
 	mode: 'local',   
    editable : false,    
    valueField: 'value',
    displayField: 'valueLabel',
    triggerAction: 'all',
    lastQuery: ''    
});

Ext.reg('bmcombofield', Bm.component.Combo); // end of extend

/**
  * Bm.component.Text Extension Class
  *
  *
  * @class Bm.component.Text
  * @extends Ext.form.TextField
  * @constructor
  * @param {Object} config Configuration options
  */
Bm.component.Text = Ext.extend(Ext.form.TextField, {
    fieldLabel: '',
    name: '',
    clearValue : function(){
        this.reset();
    },
    initComponent: function() {
        var config = {
            fieldLabel: this.fieldLabel,
            name: this.name,
            xtype: 'textfield',
            allowBlank: false         
        };
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Bm.component.Text.superclass.initComponent.apply(this, arguments);
    }   
    
});
Ext.reg('bmtextfield', Bm.component.Text); // end of extend


/**
  * Bm.component.Number Extension Class
  *
  *
  * @class Bm.component.Number
  * @extends Ext.form.NumberField
  * @constructor
  * @param {Object} config Configuration options
  */
Bm.component.Number = Ext.extend(Ext.form.NumberField, {
    fieldLabel: '',
    name: '', 
    clearValue : function(){
        this.reset();
    },
    initComponent: function() {
        var config = {
            fieldLabel: this.fieldLabel,
            name: this.name,
            xtype: 'numberfield',
            allowBlank: false                
        };
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Bm.component.Number.superclass.initComponent.apply(this, arguments);
    }
});
Ext.reg('bmnumberfield', Bm.component.Number); // end of extend


/**
  * Bm.component.Decimal Extension Class
  *
  *
  * @class Bm.component.Decimal
  * @extends Ext.form.NumberField
  * @constructor
  * @param {Object} config Configuration options
  */
Bm.component.Decimal = Ext.extend(Ext.form.NumberField, {
        clearValue : function(){
            this.reset();
        },
        setValue : function(v){
            v = typeof v == 'number' ? v : String(v).replace(this.decimalSeparator, ".");
            v = isNaN(v) ? '' : String(v).replace(".", this.decimalSeparator);
            //  if you want to ensure that the values being set on the field is also forced to the required number of decimal places.
            // (not extensively tested)
            // v = isNaN(v) ? '' : this.fixPrecision(String(v).replace(".", this.decimalSeparator));
            return Ext.form.NumberField.superclass.setValue.call(this, v);
        },
        fixPrecision : function(value){
            var nan = isNaN(value);
            if(!this.allowDecimals || this.decimalPrecision == -1 || nan || !value){
               return nan ? '' : value;
            }
            return parseFloat(value).toFixed(this.decimalPrecision);
        }
    });


Ext.reg('bmdecimalfield', Bm.component.Decimal); // end of extend

/**
  * Bm.component.Radio Extension Class
  *
  *
  * @class Bm.component.Number
  * @extends Ext.form.NumberField
  * @constructor
  * @param {Object} config Configuration options
  */
Bm.component.Radio = Ext.extend(Ext.form.Radio, {
    fieldLabel: '',
    name: '',
    clearValue : function(){
        this.reset();
    },
    initComponent: function() {
        var config = {
            fieldLabel: this.fieldLabel,
            name: this.name,
            xtype: 'radio',
            allowBlank: false            
        };
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Bm.component.Radio.superclass.initComponent.apply(this, arguments);
    }
});
Ext.reg('bmradiofield', Bm.component.Radio); // end of extend


/**
  * Bm.component.Date Extension Class
  *
  *
  * @class Bm.component.Date
  * @extends Ext.form.DateField
  * @constructor
  * @param {Object} config Configuration options
  */
Bm.component.Date = Ext.extend(Ext.form.DateField, {
    fieldLabel: '',
    name: '',    
    initComponent: function() {
        var config = {
            fieldLabel: this.fieldLabel,
            name: this.name,
            xtype: 'datefield',
            allowBlank: false            
        };
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Bm.component.Date.superclass.initComponent.apply(this, arguments);
    }
});
Ext.reg('bmdatefield', Bm.component.Date); // end of extend

Ext.ns("Bm.filter");

/**
 * Bm.filter.App Extension Class
 *
 *
 * @class Bm.filter.App
 * @extends Ext.form.ComboBox
 * @constructor
 * @param {Object} config Configuration options
 */
Bm.filter.App = Ext.extend(Ext.form.ComboBox,{    
    name : 'application',
    fieldLabel : getLocalizedString('application'),
    autoWidth : true,
    editable : false,
    emptyText : getLocalizedString('SelectOne'),    
    valueField: 'value',
    displayField: 'valueLabel', 
    mode:'local',
    triggerAction: 'all'
});
Ext.reg('bmappfilter', Bm.filter.App); // end of extend

Bm.filter.Panel = Ext.extend(Ext.Panel, {
	layout : 'form',
	border: false,
	bodyBorder : false,
	unstyled : true,
	xtype : 'container',
	frame : false,
	margins : '0 0 0 0',
	initComponent: function(){
		Bm.filter.Panel.superclass.initComponent.call(this);  
	}
});

/**
  *
  *
  * @class Bm.filter.Priority
  * @extends Ext.form.ComboBox
  * @extends Bm.component.SuperboxSelect
  * @constructor
  * @param {Object} config Configuration options
  */
Bm.filter.Priority = Ext.extend(Ext.form.ComboBox,{ 
    fieldLabel : getLocalizedString('priority'),
    autoWidth : true,   
    editable : false,
    emptyText : getLocalizedString('SelectOne'),
    valueField: 'value',
    displayField: 'valueLabel', 
    mode:'local',
    triggerAction: 'all'
 });
 Ext.reg('bmpriorityfilter', Bm.filter.Priority); // end of extend

/**
 * Bm.filter.Priority Extension Class
 *
 *
 * @class Bm.filter.Priority
 * @extends Bm.component.SuperboxSelect
 * @constructor
 * @param {Object} config Configuration options
 
Bm.filter.Priority = Ext.extend(Bm.component.SuperboxSelect,{ 
    fieldLabel : getLocalizedString('priority'),
    xtype : 'bmsuperboxselect'
});
Ext.reg('bmpriorityfilter', Bm.filter.Priority); // end of extend */

/**
 * Bm.filter.Status Extension Class
 *
 *
 * @class Bm.filter.Status
 * @extends Ext.form.ComboBox
 * @constructor
 * @param {Object} config Configuration options
 */
Bm.filter.Status = Ext.extend(Ext.form.ComboBox,{  
    fieldLabel : getLocalizedString('status'),
    autoWidth : true, 
    editable : false,
    emptyText : getLocalizedString('SelectOne'),
    valueField: 'value',
    displayField: 'valueLabel', 
    mode:'local',
    triggerAction: 'all'
})
Ext.reg('bmstatusfilter', Bm.filter.Status); // end of extend


/**
 * Bm.filter.Workstep Extension Class
 *
 *
 * @class Bm.filter.Workstep
 * @extends Ext.form.ComboBox
 * @constructor
 * @param {Object} config Configuration options
 */
Bm.filter.Workstep = Ext.extend(Ext.form.ComboBox,{    
    fieldLabel : getLocalizedString('workstep'),
    autoWidth : true,       
    disabled : true,  
    editable : false,
    emptyText : getLocalizedString('SelectOne'),
    valueField: 'value',
    displayField: 'valueLabel',
    mode:'local',
    triggerAction: 'all',
    lastQuery: ''
});
Ext.reg('bmworkstepfilter', Bm.filter.Workstep); // end of extend


/**
 * Bm.filter.Template Extension Class
 *
 *
 * @class Bm.filter.Template
 * @extends Ext.form.ComboBox
 * @constructor
 * @param {Object} config Configuration options
 */
Bm.filter.Template = Ext.extend(Ext.form.ComboBox,{  
    fieldLabel : getLocalizedString('version'),
    autoWidth : true,
    disabled : true,
    editable : false,
    lastQuery: '',  
    triggerAction: 'all',
    emptyText : getLocalizedString('SelectOne'),
    valueField: 'value',
    displayField: 'valueLabel',    
    mode:'local'    
  
});
Ext.reg('bmtemplatefilter', Bm.filter.Template); // end of extend

/**
 * Bm.filter.Performer Extension Class
 *
 *
 * @class Bm.filter.Performer
 * @extends Ext.form.TextField
 * @constructor
 * @param {Object} config Configuration options
 */
Bm.filter.Performer = Ext.extend(Ext.form.TextField,{    
    fieldLabel : getLocalizedString('performer'),
    autoWidth : true
});
Ext.reg('bmperformerfilter', Bm.filter.Performer); // end of extend

/**
 * Bm.filter.Queue Extension Class
 *
 *
 * @class Bm.filter.Queue
 * @extends Ext.form.ComboBox
 * @constructor
 * @param {Object} config Configuration options
 */
Bm.filter.Queue = Ext.extend(Ext.form.ComboBox,{    
    fieldLabel : getLocalizedString('queue'),
    editable : false,
    emptyText : getLocalizedString('SelectOne'),
    valueField: 'value',
    displayField: 'valueLabel', 
    mode:'local',
    triggerAction: 'all'
});
Ext.reg('bmqueuefilter', Bm.filter.Queue); // end of extend

/**
 * Bm.filter.PerformerType Extension Class
 *
 *
 * @class Bm.filter.PerformerType
 * @extends Ext.form.ComboBox
 * @constructor
 * @param {Object} config Configuration options
 */
Bm.filter.PerformerType = Ext.extend(Ext.form.ComboBox,{  
    fieldLabel : getLocalizedString('performerType'),
    autoWidth : true, 
    editable : false,
    emptyText : getLocalizedString('SelectOne'),
    valueField: 'value',
    displayField: 'valueLabel', 
    mode:'local',
    triggerAction: 'all'
})
Ext.reg('bmperformertypefilter', Bm.filter.PerformerType); // end of extend

/**
 * Bm.form.Date Extension Class
 *
 *
 * @class Bm.filter.DateTime
 * @extends Ext.ux.form.DateTime
 * @constructor
 * @param {Object} config Configuration options
 */
Ext.ns('Ext.ux.form');
if(!Ext.isEmpty(Ext.ux.form.DateTime)) {
  Bm.filter.DateTime = Ext.extend(Ext.ux.form.DateTime,{  
      	autoWidth : true,
      	hiddenFormat: 'U000',
      	dateFormat:'m/d/y',
      	timeFormat:'g:i A',
      	clearValue : function(){
              this.reset();
          }
      });
  Ext.reg('bmdatetime', Bm.filter.DateTime); // end of extend  
}

/**
 * Bm.filter.PerformerQueue Extension Class
 *
 *
 * @class Bm.filter.PerformerQueue
 * @extends Ext.Panel
 * @constructor
 * @param {Object} config Configuration options
 */
Bm.filter.PerformerQueue = Ext.extend(Ext.Panel,{    
	border : false,
	frame:false,
	layout:'column',
	bodyBorder : false,
	unstyled : true,
	defaults: {      // defaults applied to items
        layout: 'column',
        border: false,
        frame:false,
        bodyStyle: 'padding:4px'
    }
});
Ext.reg('bmperfqueuefilter', Bm.filter.PerformerQueue); // end of extend

/**
 * Bm.filter.DateFilterPanel Extension Class
 *
 *
 * @class Bm.filter.DateFilterPanel
 * @extends Ext.Panel
 * @constructor
 * @param {Object} config Configuration options
 */
Bm.filter.DateFilterPanel = Ext.extend(Ext.Panel,{    
	border : false,
	frame:false,
	layout:'column',
	bodyBorder : false,
	unstyled : true,
	
	defaults: {      // defaults applied to items
        border: false,
        frame:false
    }
});
Ext.reg('bmdatefilterpanel', Bm.filter.DateFilterPanel); // end of extend


/**
 * Bm.filter.DateRange Extension Class
 *
 *
 * @class Bm.filter.DateRange
 * @extends Ext.form.ComboBox
 * @constructor
 * @param {Object} config Configuration options
 */
Bm.filter.DateRange = Ext.extend(Ext.form.ComboBox,{
    autoWidth : true,   
    editable : false,
    emptyText : getLocalizedString('SelectOne'),
    valueField: 'value',
    displayField: 'valueLabel', 
    mode:'local',
    triggerAction: 'all'
});
Ext.reg('bmdaterangefilter', Bm.filter.DateRange); // end of extend

/**
 * Bm.filter.ToggleDateRange Extension Class
 *
 *
 * @class Bm.filter.DateRange
 * @extends Ext.Button
 * @constructor
 * @param {Object} config Configuration options
 */
Bm.filter.ToggleDateRange = Ext.extend(Ext.Button,{
    autoWidth : true,
    text:'<b> T <b>',
    toggledOption:'dateField',
    style: {
       marginBottom: '4px',
       marginLeft: '5px'
    },
    getToggledOption: function() {
        return this.toggledOption;
    },
    setToggledOption: function(option) {
        this.toggledOption = option;
    }
});
Ext.reg('bmdaterangetogglebtn', Bm.filter.ToggleDateRange); // end of extend 

/**
 * Bm.filter.DateType Extension Class
 *
 *
 * @class Bm.filter.DateType
 * @extends Ext.form.ComboBox
 * @constructor
 * @param {Object} config Configuration options
 */
Bm.filter.DateType = Ext.extend(Ext.form.ComboBox,{
    autoWidth : true, 
    editable : false,
    valueField: 'value',
    displayField: 'valueLabel', 
    mode:'local',
    triggerAction: 'all'
})
Ext.reg('bmdatetypefilter', Bm.filter.DateType); // end of extend

/**
 * Bm.component.SplitButton Extension Class
 *
 *
 * @class Bm.component.SplitButton
 * @extends Ext.SplitButton
 * @constructor
 * @param {Object} config Configuration options
 */
Bm.component.SplitButton = Ext.extend(Ext.SplitButton,{
   	text: 'Options',
   	menuItems : [],
    menuWidth : 220,
    menuSeparator : false,
    menuHandler : this.defaultHandler,
    initComponent :  function(config) {
        this.menu = new Ext.menu.Menu({
            width: this.menuWidth,
            showSeparator : this.menuSeparator,
            items: this.menuItems
        });
        this.handler = this.menuHandler;
        //Override user defined config options
        Ext.apply(this, config);
        //Call the constructor
        Bm.component.SplitButton.superclass.initComponent.apply(this, arguments);
    },
    defaultHandler : function() {
            //Do nothing
    }
})
Ext.reg('bmsplitbutton', Bm.component.SplitButton); // end of extend

Bm.component.showMsg = function(title,msg,animEl,icon) {
    Ext.Msg.show({
        title: title,
        msg: msg,
        buttons: Ext.Msg.OK,
        animEl: animEl,
        icon: icon
    });
}

Bm.filter.TwoColDynamicFieldCfg = function(config){
    var cardLayout = {
        id : config.cardLayoutId,
        layout : 'card', 
        activeItem: 0,
        hideBorders : true,
        bodyBorder : false,
        margins : '0 0 0 0',
        unstyled : true,   
        frame : false,        
        layoutConfig: {
          deferredRender: false,
          layoutOnCardChange: true,
          forceLayout:true
        },
        defaults: {
          hideMode: 'offsets'
        },
        items : config.dynamicFldArray  
    };
    var cfg = { 
              //columnWidth : .60,   
              layout : 'column',
              xtype : 'container',
              width : 425, 
              margins : '0 0 0 0',   
        items : [{ 
            unstyled : true,   
            frame : false, 
            layout : 'column',
            columnWidth : .48,
            margins : '0 0 0 0',  
            items :[{   
              xtype: 'container',   
              layout: 'form',
              margins : '0 0 0 0',
              items : config.fixedFldArray
            }]  
        }, 
        {
            hideBorders : true,
            bodyBorder : false,
            margins : '0 0 0 0',
            unstyled : true,   
            frame : false,
            columnWidth : .50,
            forceLayout:true,
            items :[{ 
                xtype: 'container', 
                layout: 'form',
                align : 'left',
                width:210,
                forceLayout:true, 
                items : [cardLayout]  
            }]
        }]
    } 
   return cfg; 
}

renderDateField = function(config){
	return new Bm.filter.DateTime(config);
};

renderStringField = function(config){
	
    if(config.uitype.toLowerCase() == 'string' || config.uitype.toLowerCase() == 'text'){
		return new Bm.component.Text(config);
	}
    if(config.uitype.toLowerCase() == 'combo'){
		return new Bm.component.Combo(config);
	}
    if(config.uitype.toLowerCase() == 'radio'){
		return new Bm.component.Radio(config);
	}	
};


renderNumberField = function(config){
	if(config.uitype.toLowerCase() == 'long'){
		config.allowDecimals = false;
		return new Bm.component.Number(config);
	}
	if(config.uitype.toLowerCase() == 'double'){	
		return new Bm.component.Number(config);
	}
    if(config.uitype.toLowerCase() == 'decimal'){
		return new Bm.component.Decimal(config);
	}
};

renderBooleanField = function(config){
    config.data = "[['true',getLocalizedString('true')],['false',getLocalizedString('false')]]";
	if(config.uitype.toLowerCase() == 'boolean' || config.uitype == 'radio'){ 
		return new Bm.component.Combo(config);
	}
    if(config.uitype.toLowerCase() == 'combo'){
		return new Bm.component.Combo(config);
	}	
};

renderUrlField = function(config){
	return new Bm.component.Text(config);
};

renderValueField = function(config){
    if(config.type.toLowerCase() == 'string' ){
		return renderStringField(config);		
	}
	
    if(config.type.toLowerCase() == 'boolean'){
		return renderBooleanField(config);		
	}
		
    if(config.type.toLowerCase() == 'long' || config.type.toLowerCase() == 'double' || config.type.toLowerCase() == 'decimal'){
		return renderNumberField(config);		
	}
	
    if(config.type.toLowerCase() == 'datetime'){
		return renderDateField(config);		
	}
	
    if(config.type.toLowerCase() == 'url'){
        return renderUrlField(config);      
    }
	
    	
	
};

var resizeContainer = function(cmp) {
    cmp.setWidth("100%");
    cmp.syncSize();
    cmp.doLayout();
};

/**
 * Bm.filter.Search Extension Class
 *
 *
 * @class Bm.filter.Search
 * @extends Ext.form.ComboBox
 * @constructor
 * @param {Object} config Configuration options
 */
Bm.filter.Search = Ext.extend(Ext.form.ComboBox,{ 
    fieldLabel : getLocalizedString('filter'),
    autoWidth : true,   
    editable : false,
    emptyText : getLocalizedString('SelectOne'),
    valueField: 'id',
    displayField: 'name', 
    mode:'local',
    triggerAction: 'all'
});
Ext.reg('bmfiltersearch', Bm.filter.Search); // end of extend

Ext.ns("Bm.VTypes");
Ext.apply(Bm.VTypes, Ext.form.VTypes);
Ext.apply(Bm.VTypes, {
    isValidDsValue:  function(v) {
        var dsValRegEx = /[<>"';()&+\,:|]/;
        return dsValRegEx.test(v);
    },
    isUrlHasSplChars:  function(v) {
        var dsValRegEx = /[<>"';()&+\,%|]/;
        return dsValRegEx.test(v);
    }
});

///////*******  File upload part integration starts *****/////

/**
 * Bm.component.FileUploadField Extension Class
 *
 *
 * @class Bm.component.FileUploadField
 * @extends Ext.ux.form.FileUploadField
 * @constructor
 */
Bm.component.FileUploadField = Ext.extend(Ext.ux.form.FileUploadField, {
    width : 260,
    buttonText: getLocalizedString('FileUpload_Browse')
});
Ext.reg('bmfileuploadfield', Bm.component.FileUploadField);

/**
 * Bm.component.FileDialogAddBtn Extension Class
 *
 *
 * @class Bm.component.FileDialogAddBtn
 * @extends Ext.Button
 * @constructor
 */
Bm.component.FileDialogAddBtn = Ext.extend(Ext.Button,{
	text : getLocalizedString('FileUpload_Add'),
	iconCls : 'bmAdd'
});

Ext.reg('bmfiledialogaddbtn', Bm.component.FileDialogAddBtn);


/**
 * Bm.component.FileDialogDeleteBtn Extension Class
 *
 *
 * @class Bm.component.FileDialogDeleteBtn
 * @extends Ext.Button
 * @constructor
 */
Bm.component.FileDialogDeleteBtn = Ext.extend(Ext.Button,{
	tooltipType : 'title',
	tooltip : getLocalizedString('FileUpload_Delete'),
    text : getLocalizedString('FileUpload_Remove'),
	iconCls : 'bmDelete'
});

Ext.reg('bmfiledialogdeletebtn', Bm.component.FileDialogDeleteBtn); 

Bm.component.showMsg = function(title,msg,animEl,icon) {
    Ext.Msg.show({
        title: title,
        msg: msg,
        buttons: Ext.Msg.OK,
        animEl: animEl,
        icon: icon
    });
};

///////*******  File upload part integration end *****/////