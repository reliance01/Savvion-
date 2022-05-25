jmaki.namespace("jmaki.widgets.Ext.tree");

jmaki.widgets.Ext.tree.Widget = function(wargs) {
    var _widget = this;
    var publish = "/Ext/tree";
    var subscribe = ["/Ext/tree", "/tree"];
    var counter = 0;
    
    function genId() {
        return wargs.uuid + "_nid_" + counter++;
    }    

    this.tree = new Ext.tree.TreePanel({
                el:wargs.uuid,
	        animate:true, 
		autoHeight:true,
		autoScroll:true,
		containerScroll: true,
		collapseFirst: true,
                enableDD:true,
		border:false,
		dropConfig: {appendOnly:true}});

    // FIXME: this code can be removed for 1.0 release
    var showedModelWarning = false;
    
    function showModelDeprecation() {
        if (!showedModelWarning) {
             jmaki.log("Ext tree widget uses the incorrect data format. Please see " +
                       "<a href='http://wiki.java.net/bin/view/Projects/jMakiTreeDataModel'>" +
                       "http://wiki.java.net/bin/view/Projects/jMakiTreeDataModel</a> for the proper format.");

             showedModelWarning = true;
        }   
    }

    
    this.findNode = function(nid, root) {
        var returnNode;
        if (typeof root == 'undefined') root = _widget.tree.getRootNode();
        if (root.id == nid) {
            returnNode = root;
            return root;
        }
        if (typeof root.childNodes != 'undefined') {
            for (var ts =0; !returnNode && root.childNodes && ts < root.childNodes.length; ts++) {
                returnNode = _widget.findNode(nid, root.childNodes[ts]);
            }
        }
        return returnNode;
    }
    
    this.expandNode = function(e) {
        var nid;
        if (e.message)e = e.message;
        if (e.targetId) nid = e.targetId;
        else nid = e;
        var target = _widget.findNode(nid);
     
        if (target){
             target.expand();
             // expand all parent treenodes
             while (target = target.parent) {
                 if (target.expand)target.expand();
             } 
         }      
    }
    
    this.collapseNode = function(e) {
        var nid;
        if (e.message)e = e.message;
        if (e.targetId) nid = e.targetId;
        else nid = e;
        var target = _widget.findNode(nid);
        if (target){
             target.collapse();
        }     
    }
    
    this.addNodes = function(e, n) {
        var ch;
        if (e.message)e = e.message;
        if (e.value) ch = e.value;
        else ch = n;
        var nid;
        if (e.targetId) nid = e.targetId;
        else nid = e;
        var target = _widget.findNode(nid);
        if (!target)target = _widget.tree.getRootNode();
        if (target && ch){       
         _widget.buildTree(ch, target); 
        }      
    }
    
    this.removeChildren = function(e){
        var nid;
        if (e.message)e = e.message;
        if (e.targetId) nid = e.targetId;
        else nid = e;

        var target = _widget.findNode(nid);
        if (target && target.childNodes && target.childNodes.length){       
            for (var i=target.childNodes.length -1;  i >= 0 ; i--) {
                target.removeChild(target.childNodes[i]);
            }
        }         
    }
           
    this.removeNode = function(e) {
        var nid;
        if (e.message)e = e.message;
        if (e.targetId) nid = e.targetId;
        else nid = e;
        var target = _widget.findNode(nid);
        if (target.parentNode) target.parentNode.removeChild(target,true);  
    }

    
    function doSubscribe(topic, handler) {
        var i = jmaki.subscribe(topic, handler);
        _widget.subs.push(i);
    }
   
    this.destroy = function() {
        for (var i=0; _widget.subs && i < _widget.subs.length; i++) {
            jmaki.unsubscribe(_widget.subs[i]);
        }
    }
   
    this.postLoad = function() {
        _widget.subs = [];
        for (var _i=0; _i < subscribe.length; _i++) {
            doSubscribe(subscribe[_i]  + "/removeNode", _widget.removeNode);
            doSubscribe(subscribe[_i] + "/removeChildren", _widget.removeChildren);
            doSubscribe(subscribe[_i] +"/addNodes", _widget.addNodes);
            doSubscribe(subscribe[_i] + "/expandNode", _widget.expandNode);
            doSubscribe(subscribe[_i]  + "/collapseNode", _widget.collapseNode);
        }      
    }

    function clone(t) {
       var obj = {};
       for (var i in t) {
            obj[i] = t[i];
       }
       return obj;
    }

    function _addNode(_root, parent) {
        var pExpanded = false;            
        if (_root.title && !_root.label) {
                _root.label = _root.title;
        }

        if (typeof _root.expanded != 'undefined' && (_root.expanded == true)) {
            pExpanded = true;  
        }
            
        var nid;
        if (typeof _root.id != 'undefined') nid= _root.id;
        else nid = genId();
        
        var pNode = new Ext.tree.TreeNode({
        text: _root.label,
        expanded : pExpanded,
        draggable:false, // disable root node dragging
        id: nid});

        if (!_widget.root) {
            _widget.root = pNode;
            _widget.tree.setRootNode(pNode);
            parent = pNode
            parent.children = _root.children;
            _widget.tree.render(); 
        } else {
           if (typeof parent == 'undefined')parent = _widget.tree.getRootNode();
           parent.appendChild(pNode);
        }

        if (_root.action || !_root.children){

              var topic = publish + "/onClick";
              var _m ={widgetId : wargs.uuid, topic : publish, type : 'onClick', targetId : nid};
                                

              _m.title = _root.label;
              pNode.addListener("click",function(e){
                  var action = _root.action;
                  _m.event = e;
                  if (action instanceof Array) {
                      for (var _a=0; _a < action.length; _a++) {
                          var payload = clone(_m);
                          if (action[_a].topic) payload.topic = action[_a].topic;
                          else payload.topic = topic;
                          if (action[_a].message) payload.message = action[_a].message;
                          jmaki.publish(payload.topic,payload);
                      }
                  } else {
                      if (_root.action && _root.action.topic) {
                          topic = _m.topic = _root.action.topic;
                      }
                      if (_root.action && _root.action.message) _m.message = _root.action.message;                  
                      jmaki.publish(topic,_m);
                  }
                  });
        }

        pNode.addListener("collapse",function(e){
            var _m ={widgetId : wargs.uuid, topic : publish, type : 'onCollapse', label : _root.label, targetId : nid};  
            jmaki.publish(publish + '/onCollapse',_m);
        });
        pNode.addListener("expand",function(e){
            var _m ={widgetId : wargs.uuid, topic : publish, type : 'onExpand', label : _root.label, targetId : nid};  
            jmaki.publish(publish + '/onExpand',_m);
        });                        
               
        return pNode;
    }
    
        // now build the tree programtically
    this.buildTree = function(root, parent) {

        var rNode = _addNode(root,parent);
       
        for (var t=0; root.children && t < root.children.length; t++) {
            var n = root.children[t];
            var lNode = _addNode(n,rNode);
           
            //  recursively call this function to add children
            if (typeof n.children != 'undefined') {
                for (var ts =0; n.children && ts < n.children.length; ts++) {
                    _widget.buildTree(n.children[ts], lNode);
                }
            }  
        }
   }

   if ( wargs.publish) publish = wargs.publish; 

    if (wargs.subscribe){
        if (typeof wargs.subscribe == "string") {
            subscribe = [];
            subscribe.push(wargs.subscribe);
        } else {
            subscribe = wargs.subscribe;
        }
    }
    
    if (!wargs.value) {
         var callback;
        // default to the service in the widget.json if a value has not been st
        // and if there is no service
        if (typeof wargs.args.service == 'undefined') {
            wargs.service = wargs.widgetDir + "/widget.json";
            callback = function(req) {
                   var obj = eval("(" + req.responseText + ")");
                   var root = obj.root;
		   _widget.buildTree(root);
            }
	} else {
               var _callback = function(req) {
                if (req.responseText =="") {
                    container.innerHTML = "Error loading widget data. No data."
                    return;
                }            
                var jTree = eval("(" + req.responseText + ")");
	        if (!jTree.root) {
             	  showModelDeprecation();
            	  return;
	        }
	        var root = jTree.root;
                _widget.buildTree(root);                    
                }
	}
	if(wargs.args.service)
        var ajax = jmaki.doAjax({url : wargs.args.service, callback : _callback});    
    } else {
       var _s = wargs.widgetDir + "/widget.json";
       jmaki.doAjax({url : _s, callback : function(req) {
              var jTree = eval("(" + req.responseText + ")");
              _widget.buildTree(jTree.value.defaultValue.root);

              },
              onerror : function() {
                container.innerHTML = "Unable to load widget data.";
            }
        });
    }
    this.setValue = function(_v) {
    if (_v.root.title && !_v.root.label) {
          _v.root.label = _v.root.title;
        }
       _widget.buildTree(_v.root);  
    }
    
    this.getValue = function() {
    if (this.title && !this.label) {
           this.label = this.title;
        }
    return this.label;
    }  
	
}
