jmaki.namespace("jmaki.widgets.sbm.browser");

jmaki.widgets.sbm.browser.Widget = function(wargs) {
    var uuid = wargs.uuid;
    var topic = "/sbm/browser/";
    var value = "";
    var scrolling = "auto";
    var frameborder = false;
    var toolTip = "";
    var tabOrder = "";
    var effects = "";

    if (wargs.publish) topic = wargs.publish;
    if (wargs.value) value = wargs.value;

    if (typeof wargs.args != 'undefined') {   
        if(typeof wargs.args.toolTip != 'undefined') toolTip = wargs.args.toolTip;
        if(typeof wargs.args.tabOrder != 'undefined') tabOrder = wargs.args.tabOrder;
        if(typeof wargs.args.scrolling != 'undefined') scrolling = wargs.args.scrolling;
        if(typeof wargs.args.frameborder != 'undefined') frameborder = wargs.args.frameborder;
        //if (typeof wargs.args.widgetEffect != 'undefined') effects = setEffects(wargs.args.widgetEffect);
    }

    if (frameborder == true)
      frameborder = 1;
    else frameborder = 0;

    init(value); // render browser

    function init(_v)
    {
         value = _v;
         if(_v && (typeof _v == 'string'))           
           jmaki.doAjax({ url : _v,
	                       method : 'POST',
	                       asynchronous: false,
	                       callback: function(_req) {
	        var htmlContent = _req.responseText;
	        var mydiv = document.getElementById(uuid);	 
                sbm.utils.setContent(uuid,htmlContent);
		//applyEffects(uuid, effects);	
	        }});
    }

    this.setValue = function(_v) { 
         init(_v);
    }

    this.getValue = function() {
       return value;
    }  
}
