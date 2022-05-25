 jmaki.namespace("jmaki.widgets.sbm.image");

jmaki.widgets.sbm.image.Widget = function(wargs) {
    // The jMaki framework must have created widget JavaScript object.
    var uuid = wargs.uuid;
    var topic = "/sbm/image/";
    var alt = "Loading picture";
    var width = 105;
    var height = 97;
    var border = 0;
    var value = "";
    var effects = "";
    
    if (wargs.publish) topic = wargs.publish;
    if (wargs.value) value = wargs.value;
    // process arguments
    if (typeof wargs.args != 'undefined') {
       if(typeof wargs.args.alt != 'undefined') alt = wargs.args.alt;
       if(typeof wargs.args.width != 'undefined') width = wargs.args.width;
       if(typeof wargs.args.height != 'undefined') height = wargs.args.height;
       if(typeof wargs.args.border != 'undefined') border = wargs.args.border;
       //if (typeof wargs.args.widgetEffect != 'undefined') effects = setEffects(wargs.args.widgetEffect);      
    }
    
    init(value); // render image

    function init(_v)
    {
      var mydiv = document.getElementById(uuid+"_div");
      mydiv.innerHTML =  "<img class='image' id= '" + uuid + "' width=" + width + " height=" + height + " alt=" + alt + " border=" + border + " src='" + _v + "'/>";
      //applyEffects(uuid, effects);
    }
  
    this.setValue = function(_v) {
       value = _v;
       init(_v);
    }

    this.getValue = function() {
       return value;
    }

    this.validate = function() {
        return true;
    }

    // published events
}
