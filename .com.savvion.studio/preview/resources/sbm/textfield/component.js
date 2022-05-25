jmaki.namespace("jmaki.widgets.sbm.textfield");
jmaki.widgets.sbm.textfield.Widget = function(wargs) {
    // The jMaki framework must have created widget JavaScript object.
    var _widget = this;
    var ie = /MSIE/i.test(navigator.userAgent);
    var uuid = wargs.uuid;
    var topic = "/sbm/textfield/";
    var effects = null;
    var type = "text";
    
    var value = (YAHOO.lang.isUndefined(wargs.value)) ? "" : wargs.value;
    var size = (YAHOO.lang.isUndefined(wargs.args.size)) ? 20 : wargs.args.size;
    var type = (YAHOO.lang.isUndefined(wargs.args.type)) ? "text" : wargs.args.type;
    var maxlength = (YAHOO.lang.isUndefined(wargs.args.maxlength)) ? 20 : wargs.args.maxlength;
    var readonly = (YAHOO.lang.isUndefined(wargs.args.readonly)) ? false : wargs.args.readonly;    
    var disabled = (YAHOO.lang.isUndefined(wargs.args.disabled)) ? false : wargs.args.disabled;
	var required = (YAHOO.lang.isUndefined(wargs.args.required)) ? false : wargs.args.required;
    var tabOrder = (YAHOO.lang.isUndefined(wargs.args.tabOrder)) ? "" : wargs.args.tabOrder;
    var toolTip = (YAHOO.lang.isUndefined(wargs.args.toolTip)) ? "" : wargs.args.toolTip;
    var validationType = (YAHOO.lang.isUndefined(wargs.args.validationType)) ? "none" : wargs.args.validationType;
	var validation = (YAHOO.lang.isUndefined(wargs.args.validation)) ? "" : wargs.args.validation;
    if(!YAHOO.lang.isUndefined(wargs.args.visible) && !wargs.args.visible)
	   sbm.widgets.hide(uuid);
	if(required && validationType == 'none') {
	   validationType = 'custom';   
	}
	if(required)
	   wargs.args.validation['isRequired'] = true;
	
	var validationRequiredMessage = (YAHOO.lang.isUndefined(wargs.args.requiredValidationMsg)) ? "The field is required!" : wargs.args.requiredValidationMsg;
	var validationMessage = (YAHOO.lang.isUndefined(wargs.args.textfieldRequiredMsg)) ? "Invalid Entry" : wargs.args.textfieldRequiredMsg;
    var textfieldMinValueMsg =  (YAHOO.lang.isUndefined(wargs.args.textfieldMinValueMsg)) ? "Lower than minimum value" : wargs.args.textfieldMinValueMsg;
	var textfieldMaxValueMsg =  (YAHOO.lang.isUndefined(wargs.args.textfieldMaxValueMsg)) ? "Exceeded than maximum value" : wargs.args.textfieldMaxValueMsg;
	var textfieldMinCharsMsg =  (YAHOO.lang.isUndefined(wargs.args.textfieldMinCharsMsg)) ? "Lower than minimum characters" : wargs.args.textfieldMinCharsMsg;
	var textfieldMaxCharsMsg =  (YAHOO.lang.isUndefined(wargs.args.textfieldMaxCharsMsg)) ? "Exceeded than maximum characters" : wargs.args.textfieldMaxCharsMsg;
	
	var validationOptions = (YAHOO.lang.isUndefined(wargs.args.validation)) ? "{}" : wargs.args.validation;
    if(!YAHOO.lang.isUndefined(wargs.args.widgetEffect)) effects = wargs.args.widgetEffect;
    var validationObject = null;
    // if datasource for the widget is service then
    // set widget presentation in the div defined in the widget template file
    if (wargs.args.service){
            jmaki.doAjax({ url : wargs.args.service, method : 'POST',onerror: init(), callback: function(_req) {
                    var output = _req.responseText ; 
		    init(output);}});
    } else init(value);
    
    // initializes widget with given value
    function init(value) {		
                var cssClass = "ApInptTxt";		
                if(typeof value == 'undefined') value = "";
		if(type == 'text(view only)') {		       
		       document.getElementById(wargs.uuid+"_div").innerHTML = value;
		       return;       
		}
		
	        
		var mydiv = document.getElementById(wargs.uuid+"_div");
                var innerHTML = "<input name=\"" + uuid + "\" id=\"" + uuid +"\" class=\""+ cssClass + "\" type=\"" + type +"\" size=\""+size+
                "\" maxlength=\""+maxlength+"\" value=\""+value+"\" title=\""+toolTip+"\"  tabindex=\""+tabOrder +
                "\" onChange= \"jmaki.attributes.get('" + uuid + "').onChange()\" "+              
                " onClick= \"jmaki.attributes.get('"+uuid+ "').onClick()\""+
                " onFocus= \"jmaki.attributes.get('" + uuid + "').onFocus()\""+
                " onBlur= \"jmaki.attributes.get('" + uuid + "').onBlur()\"" +
                " onKeyPress= \"jmaki.attributes.get('" + uuid + "').onKeyPress()\""+
                " onKeyUp= \"jmaki.attributes.get('" + uuid + "').onKeyUp()\""+
                " onKeyDown= \"jmaki.attributes.get('" + uuid + "').onKeyDown()\""+
                " onMouseDown= \"jmaki.attributes.get('" + uuid + "').onMouseDown()\"";
                if(type == 'password'){
                   innerHTML+= " autocomplete='off' ";
		}
               if(disabled)
                   innerHTML += " disabled='true' ";
                if(readonly || type == 'Label')
                    innerHTML += "readonly";
                innerHTML += ">"
                if(validationType != "none") 
                    innerHTML += ("<span class='textfieldRequiredMsg'>" + validationRequiredMessage +"</span>");
                    innerHTML += ("<span class='textfieldInvalidFormatMsg'>" + validationMessage +"</span>");
                if(!YAHOO.lang.isUndefined(wargs.args.validation['minValue'])) 
                    //innerHTML += ("<span class='textfieldMinValueMsg'>" + textfieldMinValueMsg +"</span>"); 
		    innerHTML += ("<span class='textfieldMinValueMsg'>" + validationMessage +"</span>");  
                if(!YAHOO.lang.isUndefined(wargs.args.validation['maxValue']))
                    //innerHTML += ("<span class='textfieldMaxValueMsg'>" + textfieldMaxValueMsg +"</span>"); 
		    innerHTML += ("<span class='textfieldMaxValueMsg'>" + validationMessage +"</span>");  
	        if(!YAHOO.lang.isUndefined(wargs.args.validation['minChars']))
                    //innerHTML += ("<span class='textfieldMinCharsMsg'>" + textfieldMinCharsMsg +"</span>"); 
		    innerHTML += ("<span class='textfieldMinCharsMsg'>" + textfieldMinCharsMsg +"</span>"); 
                if(!YAHOO.lang.isUndefined(wargs.args.validation['maxChars'])) 
		    //innerHTML += ("<span class='textfieldMaxCharsMsg'>" + textfieldMaxCharsMsg +"</span>"); 
                    innerHTML += ("<span class='textfieldMaxCharsMsg'>" + textfieldMaxCharsMsg +"</span>");

                mydiv.innerHTML = innerHTML;
                // adding spry validation if it is associated with widget
	        if(validationType != "none") { 
           		var vOptions = {};
			if(!YAHOO.lang.isUndefined(wargs.args.validation['validateOn'])) vOptions['validateOn'] = wargs.args.validation['validateOn'];
			if(!YAHOO.lang.isUndefined(wargs.args.validation['useCharacterMasking']) && wargs.args.validation['useCharacterMasking'])
			      vOptions['useCharacterMasking'] = true;
    	                if(!YAHOO.lang.isUndefined(wargs.args.validation['isRequired']) && wargs.args.validation['isRequired'])
			      vOptions['isRequired'] = true;
			else vOptions['isRequired'] = false;				
                        if(!YAHOO.lang.isUndefined(wargs.args.validation['minValue'])) vOptions['minValue'] = wargs.args.validation['minValue'];
			if(!YAHOO.lang.isUndefined(wargs.args.validation['maxValue'])) vOptions['maxValue'] = wargs.args.validation['maxValue'];
			if(!YAHOO.lang.isUndefined(wargs.args.validation['format'])) {
			     if(validationType != 'phone_number') vOptions['format'] = wargs.args.validation['format'];
				 else vOptions['useCharacterMasking'] = true;
				 // phone number validation format ='(XXX) XXX-XXXX';
			}			
			if(!YAHOO.lang.isUndefined(wargs.args.validation['pattern'])) {
			      if(validationType == "custom") vOptions['format'] = 'custom';
                              if(YAHOO.lang.isUndefined(vOptions['validateOn']))vOptions['validateOn'] = ['blur','change'];
			      if(!YAHOO.lang.isUndefined(wargs.args.validation['regExp']) && wargs.args.validation['regExp'].length != 0) {
				    vOptions['regExp'] = wargs.args.validation['regExp'];				       
			      }
			      if(!YAHOO.lang.isUndefined(wargs.args.validation['pattern']) && wargs.args.validation['pattern'].length != 0)
			          vOptions['pattern'] = wargs.args.validation['pattern'];
			}
                        if(!YAHOO.lang.isUndefined(wargs.args.validation['minChars'])) vOptions['minChars'] = wargs.args.validation['minChars'];
			if(!YAHOO.lang.isUndefined(wargs.args.validation['maxChars'])) vOptions['maxChars'] = wargs.args.validation['maxChars'];
                        //alert(YAHOO.lang.JSON.stringify(vOptions))
			validationObject = new Spry.Widget.ValidationTextField(uuid+"_div", validationType,vOptions);
			
		}
    	        if(effects != null){ 
	           sbm.utils.applyEffects(uuid+"_div", effects);       
	        }
    }

    // sets the value of widget
    this.setValue = function(_v) {        
        if(type == 'text(view only)') 
	  document.getElementById(wargs.uuid+"_div").innerHTML = _v;
	else
	  document.getElementById(uuid).value = _v;
    }

    // gets the value of widget
    this.getValue = function() {
	if(type == 'text(view only)') 
	  return document.getElementById(wargs.uuid+"_div").innerHTML;
	else
           return document.getElementById(uuid).value;
    }

    
    // return the uuid
    this.getId = function () {
      return uuid;
    }
	
	
	// adds validation rules
    this.addUrlValidation = function (vtype) {       
	   new Spry.Widget.ValidationTextField(uuid+"_div", 'url',{isRequired:required,'validateOn':['blur','change']});
    }
	
	// adds number validation rules
	this.addNumberValidation = function(){
	   new Spry.Widget.ValidationTextField(uuid+"_div", 'integer',{isRequired:required,'validateOn':['blur','change']});
	}
	
	// adds decimal/double/bigdecimal validation rules
	this.addDecimalValidation = function(){	   
	   new Spry.Widget.ValidationTextField(uuid+"_div", 'real',{allowNegative:false,isRequired:required,'validateOn':['blur','change']});
	}
	
	this.getValidationType = function(){		
		return validationType;
	}

    
     // published events
    this.onChange = function() {jmaki.publish(topic + "onChange", {widgetId: wargs.uuid, value: this.getValue()}); }
    this.onClick = function() {jmaki.publish(topic + "onClick", {widgetId: wargs.uuid, value: this.getValue()}); }
    this.onFocus = function() {jmaki.publish(topic + "onFocus", {widgetId: wargs.uuid, value: this.getValue()}); }
    this.onBlur = function() {jmaki.publish(topic + "onBlur", {widgetId: wargs.uuid, value: this.getValue()}); }
    this.onKeyPress = function() {jmaki.publish(topic + "onKeyPress", {widgetId: wargs.uuid, value: this.getValue()}); }
    this.onKeyUp = function() {jmaki.publish(topic + "onKeyUp", {widgetId: wargs.uuid, value: this.getValue()}); }
    this.onKeyDown = function() {jmaki.publish(topic + "onKeyDown", {widgetId: wargs.uuid, value: this.getValue()}); }
    this.onMouseDown = function() {jmaki.publish(topic + "onMouseDown", {widgetId: wargs.uuid, value: this.getValue()}); }

}
