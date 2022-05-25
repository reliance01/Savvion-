
Ext.namespace("Bm.Task");

var pgsize = 50;

var myStore;
var createStore;
var flds;

Bm.Task.taskDefaultRenderer = function(value, metadata, record, rowIndex, colIndex, store){
    if((record.get("bm_flag") & 1) == 1) //if task is overdue
             metadata.tdCls = 'RedTxt';
          else 
             metadata.tdCls = 'smlTxt'; 
              
     return "<div title='" + value +"'>" + value + "</div>";
 };
 
Bm.Task.taskNameRenderer = function(value, metadata, record, rowIndex, colIndex, store){
        if((record.get("bm_flag") & 1) == 1) //if task is overdue
           metadata.tdCls = 'RedTxt';
        else 
           metadata.tdCls = 'smlTxt'; 

        return "<div title='" + value +"'>" + value + "</div>";
    };

Bm.Task.taskDueDateRenderer = function(value, metadata, record, rowIndex, colIndex, store){
        if((record.get("bm_flag") & 1) == 1) //if task is overdue
           metadata.tdCls = 'RedTxt';
        else 
           metadata.tdCls = 'smlTxt'; 

        //if((record.get("bm_flag") & 1) == 1) //if task is overdue
        //   return "<img src='../css/<c:out value="${bizManage.theme}"/>/images/icon_overdue.gif' width='16' height='12' border='0'>" + value;

        return  "<div style='white-space:nowrap' title='" + value +"'>" + value + "</div>";
    };


Bm.Task.storeListener = function(store, options) {
                
    //set the page value.. - need to convert the following variable into string..
    if (Ext.isEmpty(options.params)) {
		options.params = {};
	}            
    options.params['start'] = options.start;
    //options.params['limit'] = options.limit;
    options.params['page'] = options.page;
    options.params['page'] = ((options.params['start'] / options.params['limit']) + 1) + "";
    options.params['limit'] = pgsize;

    //the column name that server uses is for sorting is 'col'
    if (!Ext.isEmpty(store.sorters.items)) {
        options.params['col'] = store.sorters.items[0].property;
        options.params['dir'] = store.sorters.items[0].direction.toLowerCase();
    }   

    return true;
};

    /*
 Ext.define('taskList', {
    extend : 'Ext.grid.GridPanel',
    alias : 'widget.taskList',
    autoScroll : true,
    initComponent:function() {
            Ext.apply(this, {
                id: 'task1List',
                width:450,
                height:300,
                loadMask: true,
                viewConfig: {
                    //autoFill: true,
                    emptyText: '<div align="center"><span class="NoRecFound">'+getLocalizedString("noresults")+'</span><div>'
                }
                //forceFit : true
            });           
        taskList.superclass.initComponent.apply(this, arguments);  
    },
    onRender: function() {          
        //this.store.load({params:{start: 0, limit: pgsize}}); 
        taskList.superclass.onRender.apply(this, arguments);
        this.doLayout();
    }                
});*/
                
            
            
            
