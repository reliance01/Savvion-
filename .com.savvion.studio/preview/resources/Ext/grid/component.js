
// define the namespaces
jmaki.namespace("jmaki.widgets.Ext.grid");

/**
 *
 * Wrapper by Greg Murray
 *
 * Find out more about the dataTable at: http://extjs.com/deploy/ext-1.0.1/docs/index.html
 */
jmaki.widgets.Ext.grid.Widget = function(wargs) {
    var container = document.getElementById(wargs.uuid);
    var filter = "jmaki.filters.tableModelFilter";
    var _widget = this;
	var title = (YAHOO.lang.isUndefined(wargs.args.title)) ? "" : wargs.args.title;
    var publish = "/Ext/grid";
    var subscribe = ["/Ext/grid", "/table"];
    var uuid = wargs.uuid;
    var cm;
    var ds;

    var cols = [];
    var cold;
    var schema = [];
    var ged = Ext.grid.GridEditor;
    var fm = Ext.form;
    var data;

    var counter = 0;
    function genId() {
        return wargs.uuid + "_nid_" + counter++;
    }    

    // FIXME: this code can be removed for 1.0 release
    var showedModelWarning = false;
    
    function showModelDeprecation() {
        if (!showedModelWarning) {
             jmaki.log("Ext grid widget uses the incorrect data format. " +
                       "Please see <a href='http://wiki.java.net/bin/view/Projects/jMakiTableDataModel'>" +
                       "http://wiki.java.net/bin/view/Projects/jMakiTableDataModel</a> for the proper format.");
             showedModelWarning = true;
        }   
    }

    if (wargs.args && wargs.args.filter) {
        filter = wargs.args.filter;
    }
    
    if (wargs.publish ) {
	topic = wargs.publish;
     }
     
    if (wargs.subscribe){
        if (typeof wargs.subscribe == "string") {
            subscribe = [];
            subscribe.push(wargs.subscribe);
        } else {
            subscribe = wargs.subscribe;
        }
    }

    this.usMoney = function(value){
        return Ext.util.Format.usMoney(value);
    };
    
    // example of custom renderer function
    this.italic = function(value){
        return '<i>' + value + '</i>';
    }
    
    // example of custom renderer function
    this.change = function(val){
        if(val > 0){
            return '<span style="color:green;">' + val + '</span>';
        }else if(val < 0){
            return '<span style="color:red;">' + val + '</span>';
        }
        return val;
    }
    // example of custom renderer function
    this.pctChange = function(val){
        if(val > 0){
            return '<span style="color:green;">' + val + '%</span>';
        }else if(val < 0){
            return '<span style="color:red;">' + val + '%</span>';
        }
        return val;
    }    
    
    this.formatBoolean = function(value){
        return value ? 'Yes' : 'No';  
    };
    
    this.formatDate = function(value){
        return value.dateFormat('M d, Y');  
    };
    
    this.parseDate = function(value){
        return new Date(Date.parse(value));  
    };
    
    function doSubscribe(topic, handler) {
        var i = jmaki.subscribe(topic, handler);
        _widget.subs.push(i);
    }
    
    this.destroy = function() {
        for (var i=0; _widget.subs && i < _widget.subs.length; i++) {
            jmaki.unsubscribe(subs[i]);
        }
    }    

    function postInit() {
        if (data[0] instanceof Array) {
            showModelDeprecation();
            for (var i=0; i < data.length;i++) {
              _widget.addRow(data[i]);
            }
        } else {
            var rows;
            if (data.rows) rows = data.rows
            else rows = data;
            for (var i=0; i < rows.length;i++) {
              _widget.addRow(rows[i]);
            }
        }

        // track the subscribers so we can later remove them
        _widget.subs = [];

        for (var _i=0; _i < subscribe.length; _i++) {
            doSubscribe(subscribe[_i]  + "/clear", _widget.clear);
            doSubscribe(subscribe[_i]  + "/addRow", _widget.addRow);
            doSubscribe(subscribe[_i]  + "/addRows", _widget.addRows);
            doSubscribe(subscribe[_i]  + "/removeRow", _widget.removeRow);
            doSubscribe(subscribe[_i]  + "/select", _widget.select);
        }
        
	_widget.grid.getSelectionModel().addListener("rowselect",function(e){
            var targetId = _widget.grid.getSelectionModel().getSelected().id;
            var rowData = _widget.grid.getSelectionModel().getSelected().data;
            var _m ={widgetId : wargs.uuid, topic : publish, type : 'onSelect',  targetId : targetId, rowData : rowData};  
            jmaki.publish(publish + "/onSelect",_m);});
	    
    }

    this.init = function(values) {
        if (wargs.args && wargs.args.columns) cold = wargs.args.columns;
        if (data.columns) cold = data.columns;        

	// create the columns and editors
        for (var i = 0 ; i < cold.length; i++){
            var col = {};
            // auto set the index if not specfied.
            if (!cold[i].id) col.dataIndex = i + "";
            else col.dataIndex = cold[i].id;

            var arg = {name : col.dataIndex};

            if (cold[i].type) {
                arg.type =  cold[i].type;
                // default the date format if not specified
                if (arg.type = 'date') {
                    if (cold[i].dateFormat)arg.dateType = cold[i].dateFormat;
                    else arg.dateType = 'n/j h:ia';
                }
            }
            schema.push(arg);
            // mix in everything but the renderer and editor
            for (var ii in cold[i]) {
                if (ii == 'editor') {
                    // create the select for a select
                    if (cold[i].editor.name == 'Field') {
                        var sel = document.createElement("select");
                        var sid = wargs.uuid + "_select_" + i;
                        sel.style.display = 'none';
                        // create the options
                        sel.id = sid;
                        for (var si =0; si < cold[i].editor.values.length; si++) {
                            var op = document.createElement('option');
                            op.setAttribute('value', cold[i].editor.values[si].value);
                            op.appendChild(document.createTextNode(cold[i].editor.values[si].name));
                            sel.appendChild(op);
                        }
                        container.appendChild(sel);
                        col.editor = new ged(new fm[cold[i].editor.name](sel));
                    } else {
                        // create new editor with the mixins
                        col.editor = new ged(new fm[cold[i].editor.name](cold[i].editor));
                    }
                } else if (ii == 'renderer') {
                   var rname = cold[i].renderer;          
                   col.renderer = _widget[rname];          
                } else if (ii == 'label') {    
                   col.header = cold[i].label; 
                } else if (ii == 'title') {    
                   col.header = cold[i].title; 
                } else {
                    col[ii] = cold[i][ii];
                }
            }
            if (!col.sortable) col.sortable = true;
            cols.push(col);
        }  
        var proxy; 
	//if(typeof values != 'undefined')
           proxy = new Ext.data.MemoryProxy(values);        
        //else 
	  // proxy = new Ext.data.MemoryProxy([]); 	
        // create the Data Store
        if (wargs.args.service)
	ds = new Ext.data.Store({
               proxy: proxy,
               url:wargs.args.service,
	       reader: new Ext.data.ArrayReader({id: wargs.uuid}, schema)
        });
	
	else
	ds = new Ext.data.Store({
               proxy: proxy,
               reader: new Ext.data.ArrayReader({id: wargs.uuid}, schema)
        });


        cm = new Ext.grid.ColumnModel(cols); 
	cm.defaultSortable = true;
    var autoFill = Ext.isIE ? false : true;
	var forceFit = Ext.isIE ? false : true;
  
    // create the editor grid
    try{
    this.grid = new Ext.grid.EditorGridPanel({
	    id: uuid+'_grid',	
	    ds: ds,
	    cm: cm,
	    autoScroll: true,
	    containerScroll: true,
	    autoHeight:true,
	    autoWidth:true,
	    layout:'fit',
	    title:title,
	    renderTo: uuid,
	    selModel: new Ext.grid.RowSelectionModel(),
	    // enableColLock:false,
	    viewConfig: {
	            //autoFill: autoFill,
	            //forceFit:forceFit,
	            deferEmptyText: false,
	            emptyText: '<div style="{text-align: center}"><span style="font-size: 9pt; font-weight: normal">There are no record.<br/></span></span></div>'
	    }
	/*,	    
	    tbar: new Ext.PagingToolbar({
		       store: ds,
		       pageSize: 5}),
	    bbar: new Ext.PagingToolbar({
		       store: ds,
		       pageSize: 5})
			   */
        });
    }catch(e){
     alert(e);	    
    	    
    }
	 
	this.grid.render();
        ds.load();
	postInit();
	// auto fit causes error in IE
	//this.grid.getView().fitColumns();
    }


    this.clear = function() {
        ds.removeAll();
    }
    
    this.addRows = function(b, _i){
        var rows;
        if (b.message)b = b.message;
        if (b.value) rows = b.value;
        var _index;
        if (b.index) {
         _index = b.index;
        } else {
            _index = _i;
        }

        var recs = [];
        for (var i=0; i < b.value.length; i++) {
            var targetId;
            var r = b.value[i];
            if (typeof r.id != 'undefined') targetId = r.id;
            else targetId = genId();
            var rec = ds.getById(targetId);
            if (rec) {
                jmaki.log(wargs.uuid  + " : Warning. Attempt to add record to Ext.grid with duplicate row id: " + targetId + ". Autogenerating new id.");
                targetId = genId();
            }
            if (typeof r == 'undefined') return;
            recs.push(new Ext.data.Record(r, targetId));
        }
        
        if (_index && _index < ds.getCount()) {
            // reverse the order of the records otherwise they get put in backwards
            ds.insert(_index, recs.reverse());
        } else {
            ds.add(recs);
            ds.sort( cols[0].id, "ASC");   
        }
    }
    
    this.select = function(b) {
        var index;
        if (b.message)b = b.message;
        if (b.targetId) {
           index = b.targetId;
        } else {
            index = b;
        }
        if (typeof index != 'undefined'){
            var r = ds.getById(index);
            if (r) {
                var recs = [];
                recs.push(r);
                _widget.grid.getSelectionModel().selectRecords(recs);
            }
        }
    }     
 
    this.removeRow = function(b){
        var targetId;
        if (b.message)b = b.message;
        if (b.targetId) {
            targetId = b.targetId;
        } else {
            targetId = b;
        }        
        var record = ds.getById(targetId);
        if (record)ds.remove(record);
    }       
  
    this.addRow = function(b, _i) {
        var r;
        var _index;
        if (b.message)b = b.message;
        if (b.value) {
            r = b.value;
        } else {
            r = b;
        }
        if (b.index) {
            _index = b.index;
        } else {
            _index = _i;
        }
        
        var targetId;
        if (typeof r.id != 'undefined') targetId = r.id;
        else targetId = genId();
        var rec = ds.getById(targetId);
        if (rec) {
            jmaki.log(wargs.uuid  + " : Warning. Attempt to add record to Ext.grid with duplicate row id: " + targetId + ". Autogenerating new id.");
            targetId = genId();
        }
        if (typeof r == 'undefined') return;
        var record = new Ext.data.Record(r, targetId);
        if (_index && _index < 	ds.getTotalCount()) {
            ds.insert(_index, record);
        } else {
            ds.add(record);
            ds.sort( cols[0].id, "ASC");   
        }   
    }
    
    if (wargs.value) {
        // convert value if a jmakiRSS type
        if (wargs.value.dataType == 'jmakiRSS') {
           wargs.value = jmaki.filter(wargs.value, filter);
        }
        if (!wargs.value.rows) {
             showModelDeprecation();
             return;
        }
        data = wargs.value;
        _widget.init();
    } else if (wargs.args.service) {
        jmaki.doAjax({url: wargs.args.service, callback: function(req) {
                var _in = eval('(' + req.responseText + ')');
               // convert value if a jmakiRSS type
               if (_in.dataType == 'jmakiRSS') {
                   data = jmaki.filter(_in, filter);
                } else data = _in;
               _widget.init(data);
        }});
    } else {
        var _s = wargs.widgetDir + "/widget.json";
        jmaki.doAjax({url: _s, asynchronous : false, callback: function(req) {
            var _in = eval('(' + req.responseText + ')');
            if (!_in.rows) {
                 showModelDeprecation();
                 return;
            }
            data = _in.value.defaultValue;
            _widget.init(data);
        }}); 
    }    
}
