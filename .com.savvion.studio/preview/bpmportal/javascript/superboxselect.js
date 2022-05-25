Ext.namespace('Bm.component');

/**
  * Bm.component.SuperboxTooltip Extension Class
  *
  *
  * @class Bm.component.SuperboxTooltip
  * @extends Ext.ToolTip
  * @constructor
  * @param {Object} config Configuration options
  */
Ext.define('Bm.component.SuperboxToolTip', {
    extend: 'Ext.ToolTip', 
    alias : 'widget.bmsuperboxtooltip',
	delegate : 'li.x-superboxselect-item',
	trackMouse : true,    	
    initComponent: function(config) {
        Ext.apply(this, config);
        Bm.component.SuperboxToolTip.superclass.initComponent.apply(this, arguments);
    }
});

/**
  * Bm.component.SuperboxSelect Extension Class
  *
  *
  * @class Bm.component.SuperboxSelect
  * @extends Ext.ux.form.SuperBoxSelect
  * @constructor
  * @param {Object} config Configuration options
  */
Ext.define('Bm.component.SuperboxSelect', {
    extend : 'Ext.ux.form.SuperBoxSelect',
    alias : 'widget.bmsuperboxselect', 
    allowBlank:true,
    xtype:'superboxselect',
    editable:false,
    resizable: true,
    mode: 'local',
    displayField: 'valueLabel',
    displayFieldTpl: '{valueLabel}',
    valueField: 'value',
    emptyText: getLocalizedString('All'),
    superboxToolTip : true,
    superboxToolTipCfg : null,
    initComponent: function(config) {
        Ext.apply(this, config);
        Bm.component.SuperboxSelect.superclass.initComponent.apply(this, arguments);
    },
    onRender : function(ct, position) {
        Bm.component.SuperboxSelect.superclass.onRender.call(this, ct, position);
        if(this.superboxToolTip) {
            var cfg = {
                target : this.wrapEl,
                renderTo : Ext.getBody(),
                listeners : {
                	'beforeshow' : {
                		fn : function(tip) {
                		    var rec = this.findSelectedRecord(tip.triggerElement);
                		    if(rec) {
                                tip.body.dom.innerHTML = rec.get('valueLabel');
                            }
                		},
                		scope : this
                	}
                }
            };
            Ext.apply(cfg, this.superboxToolTipCfg);
            new Bm.component.SuperboxToolTip(cfg);
        }
    }
});
