Ext.namespace('Ext.ux');

/**
 * Allows row drag-and-drop ooperations.
 * @class Ext.ux.GridReorderDropTarget
 * @extends Ext.util.Observable
 * @constructor Creates a new GridReorderTarget
 * @param {Ext.grid.GridPanel} grid A grid 
 * @param {Object} config Configuration options
 */
Ext.ux.GridReorderDropTarget = function(grid, config) {
    /**
     * @cfg {Boolean} copy
     * True to copy, false to move
     */
     /**
     * @cfg {Boolean} asGroup
     * true to move/copy the rows for the groups, By default false 
     */
     /**
     * @cfg {Object} listners
     * Some event listners
     */
    this.target = new Ext.dd.DropTarget(grid.getEl(), {
        ddGroup: grid.ddGroup || 'GridDD',
        grid: grid,
        asGroup: false,
        gridDropTarget: this,
        notifyDrop: function(dd, e, data){
            var t = Ext.lib.Event.getTarget(e);
            var rindex = this.grid.getView().findRowIndex(t);
            if (rindex === false) return false;
            if (rindex == data.rowIndex) return false;
            
            var rowsToMove = new Array();
            // get the grouping value of the selected rows
            var groupFieldName    = data.grid.store.groupField;            
            var groupFieldValue = data.selections[0].data[groupFieldName];
            // if the plugin is used for grouping store make a list of rows to move or copy by comparison 
            if(this.asGroup){
                for(i = 0; i < grid.store.getCount(); i++) {
                            if(grid.store.getAt(i).get(groupFieldName) == groupFieldValue)
                            {
                                rowsToMove.push(grid.store.getAt(i));
                            }
                    }
            }
            // else follow the selections 
            else{
                rowsToMove = data.selections;
            }
            // compare the values on for the groups 

            if(rindex === false){
                var onGroupFieldValue = -1;
            } else {
                var onGroupFieldName    = data.grid.store.data.items[rindex].store.groupField;    
                var onGroupFieldValue = data.grid.store.data.items[rindex].data[onGroupFieldName];
            }
            // make sure the drop is allowed or not, if asGroup drop is not allowed on the any row of the group
            if(this.asGroup){
                    // check if the target row and the selected row does not have same 'groupValues'
                    if(groupFieldValue != onGroupFieldValue){
                        var isDropAllowed = true;
                    }
                    else{
                    var isDropAllowed = false;
                    }
            }
            else {
                var isDropAllowed = true;
            }
            if(isDropAllowed){
                if (this.gridDropTarget.fireEvent(this.copy?'beforerowcopy':'beforerowmove', this.gridDropTarget, data.rowIndex, rindex, rowsToMove) === false) return false;
                var ds = this.grid.getStore();
                if (!this.copy) {                        
                    for(i = 0; i < rowsToMove.length; i++) {
                        ds.remove(ds.getById(rowsToMove[i].id));
                        }
                    }
                    ds.insert(rindex,rowsToMove);

                    var sm = this.grid.getSelectionModel();
                    if (sm) sm.selectRecords(rowsToMove);

                    this.gridDropTarget.fireEvent(this.copy?'afterrowcopy':'afterrowmove', this.gridDropTarget, data.rowIndex, rindex, rowsToMove);
                    return true;            
            }
        }            
        ,notifyOver: function(dd, e, data) {
            var t = Ext.lib.Event.getTarget(e);
            var rindex = this.grid.getView().findRowIndex(t);
            if (rindex == data.rowIndex) rindex = false;
            var groupFieldName    = data.grid.store.groupField;            
            var groupFieldValue = data.selections[0].data[groupFieldName];
            if(rindex === false){
                var onGroupFieldValue = -1;
            } else {
                var onGroupFieldName    = data.grid.store.data.items[rindex].store.groupField;    
                var onGroupFieldValue = data.grid.store.data.items[rindex].data[onGroupFieldName];
            }
            // make sure the drop is allowed or not, if asGroup drop is not allowed on the any row of the group
            if(this.asGroup){
                    // check if the target row and the selected row does not have same 'groupValues'
                    if(groupFieldValue != onGroupFieldValue){
                        return this.dropAllowed;
                    }
                    return this.dropNotAllowed;
            }
            else {
                return this.dropAllowed;
            }

        }
    });
    if (config) {
        Ext.apply(this.target, config);
        if (config.listeners) Ext.apply(this,{listeners: config.listeners});
    }

    this.addEvents({
        /**
         * @event beforerowmove
         * Fires before the row is moved. If the handler returns false, the event is cancelled.
         * @param {GridReorderDropTarget} this
         * @param {int} oldIndex
         * @param {int} newIndex
         * @param {Object} records
         */ 
        "beforerowmove": true
        /**
         * @event afterrowmove
         * Fires after the row is moved.
         * @param {GridReorderDropTarget} this
         * @param {int} oldIndex
         * @param {int} newIndex
         * @param {Object} records
         */ 
        ,"afterrowmove": true
        /**
         * @event beforerowcopy
         * Fires before the row is copied. If the handler returns false, the event is cancelled.
         * @param {GridReorderDropTarget} this
         * @param {int} oldIndex
         * @param {int} newIndex
         * @param {Object} records
         */ 
        ,"beforerowcopy": true
        /**
         * @event afterrowcopy
         * Fires after the row is copied.
         * @param {GridReorderDropTarget} this
         * @param {int} oldIndex
         * @param {int} newIndex
         * @param {Object} records
         */ 
        ,"afterrowcopy": true
    });

    Ext.ux.GridReorderDropTarget.superclass.constructor.call(this);
};

Ext.extend(Ext.ux.GridReorderDropTarget, Ext.util.Observable, {
    /**
     * Gets the DropTarget associated with this GridReorderDropTarget
     * @return {Object} this
     */
    getTarget: function() {
        return this.target;
    },
    /**
     * Gets the GridPanel associated with this GridReorderDropTarget
     * @return {Object} this
     */
    getGrid: function() {
        return this.target.grid;
    },
    /**
     * Gets copy
     * @return {Boolean} this
     */
    getCopy: function() {
        return this.target.copy?true:false;
    },
    /**
     * Sets copy
     * @return {Boolean} this
     */
    setCopy: function(b) {
        this.target.copy = b?true:false;
    }
});

/*Ext.grid.RowSelectionModel.override(
{
    // FIX: added this function so it could be overrided in CheckboxSelectionModel
    handleDDRowClick: function(grid, rowIndex, e)
    {
        if(e.button === 0 && !e.shiftKey && !e.ctrlKey) {
            this.selectRow(rowIndex, false);
            grid.view.focusRow(rowIndex);
        }
    },
    
    initEvents: function ()
    {
        if(!this.grid.enableDragDrop && !this.grid.enableDrag){
            this.grid.on("rowmousedown", this.handleMouseDown, this);
        }else{ // allow click to work like normal
            // FIX: made this handler function overrideable
            this.grid.on("rowclick", this.handleDDRowClick, this);
        }

        this.rowNav = new Ext.KeyNav(this.grid.getGridEl(), {
            "up" : function(e){
                if(!e.shiftKey){
                    this.selectPrevious(e.shiftKey);
                }else if(this.last !== false && this.lastActive !== false){
                    var last = this.last;
                    this.selectRange(this.last,  this.lastActive-1);
                    this.grid.getView().focusRow(this.lastActive);
                    if(last !== false){
                        this.last = last;
                    }
                }else{
                    this.selectFirstRow();
                }
            },
            "down" : function(e){
                if(!e.shiftKey){
                    this.selectNext(e.shiftKey);
                }else if(this.last !== false && this.lastActive !== false){
                    var last = this.last;
                    this.selectRange(this.last,  this.lastActive+1);
                    this.grid.getView().focusRow(this.lastActive);
                    if(last !== false){
                        this.last = last;
                    }
                }else{
                    this.selectFirstRow();
                }
            },
            scope: this
        });

        var view = this.grid.view;
        view.on("refresh", this.onRefresh, this);
        view.on("rowupdated", this.onRowUpdated, this);
        view.on("rowremoved", this.onRemove, this);
    }
});

Ext.grid.CheckboxSelectionModel.override(
{
    // FIX: added this function to check if the click occured on the checkbox.
    //      If so, then this handler should do nothing...
    handleDDRowClick: function(grid, rowIndex, e)
    {
        var t = Ext.lib.Event.getTarget(e);
        if (t.className != "x-grid3-row-checker") {
            Ext.grid.CheckboxSelectionModel.superclass.handleDDRowClick.apply(this, arguments);
        }
    }
});

Ext.grid.GridDragZone.override(
{
    getDragData: function (e)
    {
        var t = Ext.lib.Event.getTarget(e);
        var rowIndex = this.view.findRowIndex(t);
        if(rowIndex !== false){
            var sm = this.grid.selModel;
            // FIX: Added additional check (t.className != "x-grid3-row-checker"). It may not
            //      be beautiful solution but it solves my problem at the moment.
            if ( (t.className != "x-grid3-row-checker") && (!sm.isSelected(rowIndex) || e.hasModifier()) ){
                sm.handleMouseDown(this.grid, rowIndex, e);
            }
            return {grid: this.grid, ddel: this.ddel, rowIndex: rowIndex, selections:sm.getSelections()};
        }

        return false;
    }
});     */
