
// define the namespaces
jmaki.namespace("jmaki.widgets.Ext.menu");

jmaki.widgets.Ext.menu.Widget = function(wargs) {
    var container = document.getElementById(wargs.uuid);
    var self = this;
    var label_items = [];

    // use this topic name as this is what the dcontainer is listening to.
    var publish = "/Ext/menu/onclick";

    var menubar;

    if (wargs.args && wargs.args.topic) {
        publish = wargs.args.topic;
    }
     if ( wargs.publish) {
        publish = wargs.publish;
    }
    
    // pull in the arguments
    if (wargs.value) {
        menu = wargs.value.menu;
        rows = wargs.value.rows;
        init();
    } else if (wargs.args.service) {
            jmaki.doAjax({url: wargs.args.service, callback: function(req) {
        if (req.readyState == 4) {
            if (req.status == 200) {
              var data = eval('(' + req.responseText + ')');
              menu = data.menu;
              init();
          }
        }
      }});
    } else {
        menu = [ 
        {label: 'Some Label',
            menu: [
                {label:'Some Item', action:{message: '1.jsp'}},
                {label:'Some Item 2', href:'http://jmaki.com'}
                ]},

        {label: 'Some Other Label',

            menu: [
                {label:'Some Other Item', action:{topic: '/mytopic', message:'2.jsp'}},
                {label:'Some Other Item 2', href:'http://www.sun.com'}
                ]}
        ];
        init();
    }
    
   function clone(t) {
       var obj = {};
       for (var i in t) {
            obj[i] = t[i];
       }
       return obj;
    }


    function processActions(m, pid, _type, value) {
        if (m) {
            var _topic = publish;
            var _m = {widgetId : wargs.uuid, type : _type, targetId : pid};
            var action = m.action;
            if (!action) _topic = _topic + "/" + _type;
            if (typeof value != "undefined") _m.value= value;

            if (action && action instanceof Array) {
              for (var _a=0; _a < action.length; _a++) {
                  var payload = clone(_m);
                  if (action[_a].topic) payload.topic = action[_a].topic;
                  else payload.topic = publish;
                  if (action[_a].message) payload.message = action[_a].message;
                  jmaki.publish(payload.topic,payload);
              }
            } else {
              if (m.action && m.action.topic) {
                  _topic = _m.topic = m.action.topic;
              }
              if (m.action && m.action.message) _m.message = m.action.message;

              jmaki.publish(_topic,_m);
            }
        }
    }

    function showURL (largs) {
        jmaki.log("largs.targetId " + largs.targetId);
        processActions(largs, largs.targetId);

        if ( largs.href) {
             window.location.href = largs.href;
        }
    }
    
    function init() {
    	    menubar = new Ext.Toolbar({renderTo: wargs.uuid,id:wargs.uuid+'_bar'});
	    for (var i = 0; i < menu.length; i++) {	    	       
              var items = [];
              if(typeof menu[i].menu != "undefined" && menu[i].menu.length != "undefined") {
                for(var ii=0; ii < menu[i].menu.length; ii++){
                	jmaki.log("menu[i]:"+ menu[i]);
                    
                   if (menu[i].menu[ii].href ) {   //support old style for now
                       var _href = menu[i].menu[ii].href;
                   }
                   if(menu[i].menu[ii].id ) menu[i].menu[ii].targetId = menu[i].menu[ii].id;
                   else menu[i].menu[ii].targetId = wargs.uuid + "_menu_" + i+"_"+ii;
                   items.push({text : menu[i].menu[ii].label, targetId: menu[i].menu[ii].targetId, handler : showURL, href : _href, action: menu[i].menu[ii].action});
                }
               
                var menuRow = new Ext.menu.Menu({
                   id: 'mainMenu_'+i,
                   items:items
                });                
                
                menubar.add({	               		
                        //cls: 'x-btn-text-icon bmenu',
                        text : menu[i].label,
                        id : wargs.uuid + '_' + i,
                        menu: menuRow
                });
                menubar.doLayout();           
                
                
             } else if(menu[i].label != "undefined"){                   
             	   if (menu[i].href ) {   //support old style for now
                       var _href = menu[i].href;
                   }
                   if(menu[i].id ) menu[i].targetId = menu[i].id;
                   else menu[i].targetId = wargs.uuid + "_menu_" + i;
                   label_items.push({text : menu[i].label, targetId: menu[i].targetId, href : _href, action: menu[i].action});
                menubar.add(new Ext.Toolbar.Button({
                cls: 'x-btn-text-icon',
                        text : menu[i].label,
                        id : menu[i].targetId,
                        handler : function(items) { 
                            var args;
                            for(var j=0; j<label_items.length; j++) {

                                if(label_items[j].targetId == items.id )   args = label_items[j];
                            }

                            if (args) showURL(args);
                        }
                    }));
             }
             if (i < menu.length -1 ) menubar.addSeparator();        

        }
        
    }
}
