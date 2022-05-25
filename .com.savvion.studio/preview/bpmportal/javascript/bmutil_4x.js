Ext.namespace("Bm.Column");
Ext.require(['Ext.grid.*']);

Ext.define('Bm.Column.ColumnModel',{
            extend: 'Ext.grid.column.Column',
            defaults: {
                    sortable: true
                }
            }
        ); 

//Fix for IE8 drag-drop animation issue
 if(Ext.isIE) {
    //Override for PCT page while drag-dropping a widget
    //FIXME: ColorAnim
    /*
    var oldDoMethod = Ext.lib.ColorAnim.prototype.doMethod;
    Ext.override(Ext.lib.ColorAnim, {
        doMethod : function(attr, start, end) {
            //Assigning default end color as white, if end is undefined/null
            end = end || [255,255,255];
            return oldDoMethod.apply(this, arguments);
        }
    });*/
    
    
     /*Override for both PCT and dashboard pages while drag-dropping widgets
        after selecting a dashboard.
        The override is needed only for IE since, some elements are still
        retained in the DOM tree even after their destruction
     */
    
    var oldgetXY = Ext.Element.getXY;
    Ext.apply(Ext.Element, {
        getXY  : function(el) {
            if(el.getBoundingClientRect) {
                try {
                    el.getBoundingClientRect();
                } catch(e) {
                    return [el.offsetLeft,el.offsetTop];
                }
            }
            return oldgetXY.apply(this, arguments);
        }
    });
}

Ext.override(Ext.String, {        
    formatArgArray: function(format, args) {
        var formatRe = /\{(\d+)\}/g;
        return format.replace(formatRe, function(m, i) {
            return args[i];
        });
    }
});