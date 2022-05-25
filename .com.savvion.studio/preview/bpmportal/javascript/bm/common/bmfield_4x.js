Ext.namespace("Bm.component");

var getBmComponentCombo = function(config){
    //this.validValues = config.data;
    config.store = Ext.create('Ext.data.ArrayStore',{
        fields: [
            'value',
            'valueLabel'
        ],
        data : config.data
    });
    //Bm.component.Combo.superclass.constructor.call(this, config);
    return Ext.create('Bm.component.Combo',{
        id : config.id,
        name: config.name,
        store: config.store,
        flex : 1
    });
};

/**
  * Bm.component.Combo Extension Class
  *
  *
  * @class Bm.component.Combo
  * @extends Ext.form.ComboBox
  *
  */

Ext.define('Bm.component.Combo',  {
    extend: 'Ext.form.ComboBox',
    alias: ['widget.bmcombofield'],
    editable : false,
    valueField: 'value',
    displayField: 'valueLabel',
    triggerAction: 'all',
    lastQuery: ''
});

/**
  * Bm.component.Text Extension Class
  *
  *
  * @class Bm.component.Text
  * @extends Ext.form.TextField
  * @constructor
  * @param {Object} config Configuration options
  */
Ext.define('Bm.component.Text', {
    extend: 'Ext.form.TextField',
    alias: ['widget.bmtextfield'],
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
/**
  * Bm.component.Number Extension Class
  *
  *
  * @class Bm.component.Number
  * @extends Ext.form.NumberField
  * @constructor
  * @param {Object} config Configuration options
  */
Ext.define('Bm.component.Number',{
    extend: 'Ext.form.NumberField',
    alias: ['widget.bmnumberfield'],
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
/**
  * Bm.component.Decimal Extension Class
  *
  *
  * @class Bm.component.Decimal
  * @extends Ext.form.NumberField
  * @constructor
  * @param {Object} config Configuration options
  */
Ext.define('Bm.component.Decimal',  {
        extend: 'Ext.form.NumberField',
        alias: ['widget.bmdecimalfield'],
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

/**
  * Bm.component.Radio Extension Class
  *
  *
  * @class Bm.component.Number
  * @extends Ext.form.NumberField
  * @constructor
  * @param {Object} config Configuration options
  */

Ext.define('Bm.component.Radio', {
    extend: 'Ext.form.Radio',
    alias: ['widget.bmradiofield'],
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

/**
  * Bm.component.Date Extension Class
  *
  *
  * @class Bm.component.Date
  * @extends Ext.form.DateField
  * @constructor
  * @param {Object} config Configuration options
  */

Ext.define('Bm.component.Date', {
    extend: 'Ext.form.DateField',
    alias: ['widget.bmdatefield'],
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

/**
 * Bm.component.ComboExtensibleWidth Extension Class
 * A class to create combo box that provide extensible width for 
 * drop down list based upon the width of its items. This avoids 
 * the horizontal scroll bar for the item list   
 *
 * @class Bm.component.ComboExtensibleWidth
 * @extends Ext.form.ComboBox
 * @constructor
 * @param {Object} config Configuration options
 */
Ext.define('Bm.component.ComboExtensibleWidth', {
	extend: 'Ext.form.field.ComboBox',
    alias: ['widget.bmcomboextensiblewidth'],
	matchFieldWidth: false,
	/**
     * A patch fix for the ExtJs bug - when a combo box has 
     * matchFieldWidth set to false, the picker height 
     * is not calculated so data in the picker is cut 
     * off without vertical scroll bar.
     */
    alignPicker:  function(width, height) {
		   this.callParent(arguments);
		   if(this.isExpanded && !this.matchFieldWidth) {
			   var picker = this.getPicker();
	           picker.setSize( null, picker.store && 
	                 picker.store.getCount() ? null : 0);
	           //To accommodate the width of vertical scroll bar, 20 pixel
	           //is added to width
	           var width = parseInt(picker.el.dom.offsetWidth) + 20 ;
	           if (width < this.bodyEl.getWidth()){
	           	width = this.bodyEl.getWidth();
	           }
	           picker.setSize( width, picker.store && 
	                   picker.store.getCount() ? null : 0);
	       }
  		}
});

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

Ext.define('Bm.filter.App', {
    extend: 'Bm.component.ComboExtensibleWidth',
    alias: ['widget.bmappfilter'],
    //name : 'application',
    fieldLabel : getLocalizedString('application'),
    autoWidth : true,
    editable : false,
    emptyText : getLocalizedString('SelectOne'),
    valueField: 'value',
    displayField: 'valueLabel',
    queryMode:'local',
    triggerAction: 'all',
    labelAlign : 'right'
});

Ext.define('Bm.filter.Panel',{
    extend: 'Ext.form.FieldContainer',
	alias: 'widget.bmfieldcontainer',
    bodyBorder : false,
    xtype : 'fieldcontainer',
    frame : false,
    margin : '0 0 0 0',
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

Ext.define ( 'Bm.filter.Priority', {
    extend : 'Ext.form.ComboBox',
    alias: ['widget.bmpriorityfilter'],
    fieldLabel : getLocalizedString('priority'),
    autoWidth : true,
    editable : false,
    emptyText : getLocalizedString('SelectOne'),
    valueField: 'value',
    displayField: 'valueLabel',
    queryMode:'local',
    triggerAction: 'all',
    labelAlign : 'right'
 });

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
Ext.reg('bmpriorityfilter', 'Bm.filter.Priority'); // end of extend
*/

/**
 * Bm.filter.Status Extension Class
 *
 *
 * @class Bm.filter.Status
 * @extends Ext.form.ComboBox
 * @constructor
 * @param {Object} config Configuration options
 */
Ext.define ('Bm.filter.Status',{
        extend: 'Ext.form.ComboBox',
        alias: ['widget.bmstatusfilter'],
        fieldLabel : getLocalizedString('status'),
        autoWidth : true,
        editable : false,
        emptyText : getLocalizedString('SelectOne'),
        valueField: 'value',
        displayField: 'valueLabel',
        queryMode:'local',
        triggerAction: 'all',
        labelAlign : 'right'
});

/**
 * Bm.filter.Workstep Extension Class
 *
 *
 * @class Bm.filter.Workstep
 * @extends Ext.form.ComboBox
 * @constructor
 * @param {Object} config Configuration options
 */

Ext.define('Bm.filter.Workstep',{
    extend: 'Ext.form.ComboBox',
    alias: ['widget.bmworkstepfilter'],
    fieldLabel : getLocalizedString('workstep'),
    autoWidth : true,
    disabled : true,
    editable : false,
    emptyText : getLocalizedString('SelectOne'),
    valueField: 'value',
    displayField: 'valueLabel',
    queryMode:'local',
    triggerAction: 'all',
    lastQuery: '',
    labelAlign : 'right'
});

/**
 * Bm.filter.Template Extension Class
 *
 *
 * @class Bm.filter.Template
 * @extends Ext.form.ComboBox
 * @constructor
 * @param {Object} config Configuration options
 */

Ext.define('Bm.filter.Template', {
    extend: 'Bm.component.ComboExtensibleWidth',
    alias: ['widget.bmtemplatefilter'],
    fieldLabel : getLocalizedString('version'),
    //autoWidth : true,
    disabled : true,
    editable : false,
    //lastQuery: '',
    triggerAction: 'all',
    emptyText : getLocalizedString('SelectOne'),
    valueField: 'value',
    displayField: 'valueLabel',
    //queryMode:'local',
    labelAlign : 'right'
});

/**
 * Bm.filter.Performer Extension Class
 *
 *
 * @class Bm.filter.Performer
 * @extends Ext.form.TextField
 * @constructor
 * @param {Object} config Configuration options
 */
Ext.define('Bm.filter.Performer',{
    extend: 'Ext.form.TextField',
    alias: ['widget.bmperformerfilter'],
    fieldLabel : getLocalizedString('performer')
    //autoWidth : true
});

/**
 * Bm.filter.Queue Extension Class
 *
 *
 * @class Bm.filter.Queue
 * @extends Ext.form.ComboBox
 * @constructor
 * @param {Object} config Configuration options
 */

Ext.define('Bm.filter.Queue',{
    extend: 'Ext.form.ComboBox',
    alias: ['widget.bmqueuefilter'],
    fieldLabel : getLocalizedString('queue'),
    editable : false,
    emptyText : getLocalizedString('SelectOne'),
    valueField: 'value',
    displayField: 'valueLabel',
    queryMode:'local',
    triggerAction: 'all',
    labelAlign : 'right'
});

/**
 * Bm.filter.PerformerType Extension Class
 *
 *
 * @class Bm.filter.PerformerType
 * @extends Ext.form.ComboBox
 * @constructor
 * @param {Object} config Configuration options
 */

Ext.define( 'Bm.filter.PerformerType',{
    extend: 'Ext.form.ComboBox',
    alias : 'widget.bmperformertypefilter',
    fieldLabel : getLocalizedString('performerType'),
    //autoWidth : true,
    editable : false,
    emptyText : getLocalizedString('SelectOne'),
    valueField: 'value',
    displayField: 'valueLabel',
    queryMode:'local',
    triggerAction: 'all',
    labelAlign : 'right'
});

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
    Ext.define( 'Bm.filter.DateTime',{
        extend: 'Ext.ux.form.DateTime',
        alias: ['widget.bmdatetime'],
        hiddenFormat: 'U000',
        dateFormat:'m/d/y',
        timeFormat:'g:i A',
        clearValue : function(){
              this.reset();
       },
       labelAlign : 'right'
   });
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

Ext.define( 'Bm.filter.PerformerQueue',{
    extend: 'Ext.Panel',
    alias: ['widget.bmperfqueuefilter'],
    border: false,
    frame : false,
    layout: 'column',
    bodyBorder : false,
    unstyled : true,
    defaults: {      // defaults applied to items
        layout: 'column',
        border: false,
        frame:  false,
        bodyStyle: 'padding:4px'
   }
});
/**
 * Bm.filter.DateFilterPanel Extension Class
 *
 *
 * @class Bm.filter.DateFilterPanel
 * @extends Ext.Panel
 * @constructor
 * @param {Object} config Configuration options
 */

Ext.define ( 'Bm.filter.DateFilterPanel',{
    extend : 'Ext.Panel',
    alias : ['widget.bmdatefilterpanel'],
    border : false,
    frame  :false,
    layout : 'column',
    bodyBorder : false,
    unstyled : true,
    defaults: {      // defaults applied to items
        border: false,
        frame:false
    }
});

/**
 * Bm.filter.DateRange Extension Class
 *
 *
 * @class Bm.filter.DateRange
 * @extends Ext.form.ComboBox
 * @constructor
 * @param {Object} config Configuration options
 */

Ext.define('Bm.filter.DateRange', {
    extend: 'Ext.form.field.ComboBox',
    alias: ['widget.bmdaterangefilter'],
    autoWidth : true,
    editable : false,
    emptyText : getLocalizedString('SelectOne'),
    valueField: 'value',
    displayField: 'valueLabel',
    queryMode:'local',
    triggerAction: 'all',
    labelAlign : 'right'
});

/**
 * Bm.filter.ToggleDateRange Extension Class
 *
 *
 * @class Bm.filter.DateRange
 * @extends Ext.Button
 * @constructor
 * @param {Object} config Configuration options
 */

Ext.define('Bm.filter.ToggleDateRange',{
    extend: 'Ext.Button',
    alias: ['widget.bmdaterangetogglebtn'],
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


/**
 * Bm.filter.DateType Extension Class
 *
 *
 * @class Bm.filter.DateType
 * @extends Ext.form.ComboBox
 * @constructor
 * @param {Object} config Configuration options
 */

Ext.define('Bm.filter.DateType',{
    extend: 'Ext.form.field.ComboBox',
    alias: ['widget.bmdatetypefilter'],
    autoWidth : true,
    editable : false,
    valueField: 'value',
    displayField: 'valueLabel',
    queryMode:'local',
    triggerAction: 'all',
    labelAlign : 'right'
});

/**
 * Bm.component.SplitButton Extension Class
 *
 *
 * @class Bm.component.SplitButton
 * @extends Ext.SplitButton
 * @constructor
 * @param {Object} config Configuration options
 */

Ext.define('Bm.component.SplitButton', {
    extend: 'Ext.SplitButton',
    alias:['widget.bmsplitbutton'],
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
});

Ext.define('Bm.component.Container',{
    extend: 'Ext.Container',
    alias: 'widget.bmcomponentcontainer'
});

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
        xtype: 'bmfieldcontainer',
        id : config.cardLayoutId,
        activeItem: 0,
        bodyBorder : false,
        margin : '0 0 0 4',
        width:160,
        frame : false,
        layout: {
            type:'card',
            deferredRender: false,
            layoutOnCardChange: true
        },
        defaults: {
          hideMode: 'offsets'
        },
        items : config.dynamicFldArray
    };
    var cfg = {
                layout : 'table',
                xtype : 'bmfieldcontainer',
                bodyBorder: false,
                items : [config.fixedFldArray[0],cardLayout]
        }

   return cfg;
}

if(!Ext.isEmpty(Ext.ux.form.SBMDateTime)) {
    Ext.define( 'Bm.filter.DsDateTime',{
        extend: 'Ext.ux.form.SBMDateTime',
        alias: ['widget.bmdsdatetime'],
        hiddenFormat: 'U000',
        dateFormat:'m/d/y',
        timeFormat:'g:i A',
        clearValue : function(){
              this.reset();
       },
       labelAlign : 'right'
   });
}

renderDateField = function(config){
   /* var panelId = config.id;
    config.id = config.id +"_datetimeObject";
    config.dateWidth = 450;
    var dt = new Bm.filter.DateTime(config);
    return new Bm.filter.Panel ({
        id : panelId,
        padding : 0,
        items : [dt]
    });*/
    //config.dateWidth = 450;
    return Ext.create("Bm.filter.DsDateTime", config);
};

renderStringField = function(config){

    if(config.uitype.toLowerCase() == 'string' || config.uitype.toLowerCase() == 'text'){
        return new Bm.component.Text(config);
    }
    if(config.uitype.toLowerCase() == 'combo'){
        return getBmComponentCombo(config);
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
    config.data = [['true',getLocalizedString('true')],['false',getLocalizedString('false')]];
    if(config.uitype.toLowerCase() == 'boolean' || config.uitype == 'radio'){
        return getBmComponentCombo(config);
    }
    if(config.uitype.toLowerCase() == 'combo'){
        return getBmComponentCombo(config);
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
        ///config.panelId = 'datetimepanel_'+config.id;
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

Ext.define ('Bm.filter.Search', {
    extend: 'Ext.form.ComboBox',
    alias: ['widget.bmfiltersearch'],
    fieldLabel : getLocalizedString('filter'),
    hideLabel: true,
    width : 180,
    editable : false,
    emptyText : getLocalizedString('SelectOne'),
    valueField: 'id',
    displayField: 'name',
    queryMode:'local',
    triggerAction: 'all',
    labelAlign : 'right'
});


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
//The changes to the below class also need to be reflected in 
//class 'Bm.component.filefield' in document.jsp.
Ext.define( 'Bm.component.FileUploadField', {
    extend: 'Ext.ux.form.FileUploadField',
    alias: ['widget.bmfileuploadfield'],
    width : 350,
    buttonText: getLocalizedString('FileUpload_Browse'),
    listeners: {
        'change': function(fileField,value,eOpts) {
                    var m = value.match(/(.*)[\/\\]([^\/\\]+\.\w+)$/);
                    if(!Ext.isEmpty(m) && m.length > 1){
                    	Bm.component.FileUploadField.superclass.superclass.setValue.call(this, m[2]);
                    }    
				}
       }
});

/**
 * Bm.component.FileDialogAddBtn Extension Class
 *
 *
 * @class Bm.component.FileDialogAddBtn
 * @extends Ext.Button
 * @constructor
 */

Ext.define ( 'Bm.component.FileDialogAddBtn', {
    extend: 'Ext.Button',
    alias: ['widget.bmfiledialogaddbtn'],
    text : getLocalizedString('FileUpload_Add'),
    iconCls : 'bmAdd'
});

/**
 * Bm.component.FileDialogDeleteBtn Extension Class
 *
 *
 * @class Bm.component.FileDialogDeleteBtn
 * @extends Ext.Button
 * @constructor
 */

Ext.define( 'Bm.component.FileDialogDeleteBtn',{
    extend: 'Ext.Button',
    alias: ['widget.bmfiledialogdeletebtn'],
    tooltipType : 'title',
    tooltip : getLocalizedString('FileUpload_Delete'),
    text : getLocalizedString('FileUpload_Remove'),
    iconCls : 'bmDelete'
});

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

/**
 * Bm.Component.DashboardPanel Extension Class
 *
 *
 * @class Bm.Component.DashboardPanel
 * @extends Ext.Panel
 * @constructor
 */
Ext.define('Bm.Component.DashboardPanel',{
    extend : 'Ext.Panel',
    alias : ['widget.bmdashboardpanel'],
    id : 'dashboardPanel',
    initComponent : function(config) {        
        //Override user defined config options
        Ext.apply(this, config);
        this.callParent();
    }
});