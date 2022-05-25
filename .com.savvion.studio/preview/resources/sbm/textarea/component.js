jmaki.namespace("jmaki.widgets.sbm.textarea");

jmaki.widgets.sbm.textarea.Widget = function(wargs) {
    // The jMaki framework must have created widget JavaScript object.
    var _widget = this;
    var ie = /MSIE/i.test(navigator.userAgent);
    var uuid = wargs.uuid;
    var topic = "/sbm/textarea/";
    var effects = null;

    var wrap = (YAHOO.lang.isUndefined(wargs.args.wrap)) ? "soft" : wargs.args.wrap;
    var value = (YAHOO.lang.isUndefined(wargs.value)) ? "" : wargs.value;
    var size = (YAHOO.lang.isUndefined(wargs.args.size)) ? 20 : wargs.args.size;    
    var maxlength = (YAHOO.lang.isUndefined(wargs.args.maxlength)) ? 20 : wargs.args.maxlength;
    var readonly = (YAHOO.lang.isUndefined(wargs.args.readonly)) ? false : wargs.args.readonly;    
    var disabled = (YAHOO.lang.isUndefined(wargs.args.disabled)) ? false : wargs.args.disabled;
    var tabOrder = (YAHOO.lang.isUndefined(wargs.args.tabOrder)) ? "" : wargs.args.tabOrder;
    var toolTip = (YAHOO.lang.isUndefined(wargs.args.toolTip)) ? "" : wargs.args.toolTip;
    var rows = (YAHOO.lang.isUndefined(wargs.args.rows)) ? "5" : wargs.args.rows;
    var cols = (YAHOO.lang.isUndefined(wargs.args.cols)) ? "25" : wargs.args.cols;
    var validationType = (YAHOO.lang.isUndefined(wargs.args.validationType)) ? "none" : wargs.args.validationType;
    var validation = (YAHOO.lang.isUndefined(wargs.args.validation)) ? "" : wargs.args.validation;

    var required = (YAHOO.lang.isUndefined(wargs.args.required)) ? false : wargs.args.required;
    var isRequired = (YAHOO.lang.isUndefined(wargs.args.validation['isRequired'])) ? false : wargs.args.validation['isRequired'];
	required = isRequired;
    if(!YAHOO.lang.isUndefined(wargs.args.visible) && !wargs.args.visible)
	   sbm.widgets.hide(uuid);
	  
    var validationRequiredMessage = (YAHOO.lang.isUndefined(wargs.args.requiredValidationMsg)) ? "The field is required!" : wargs.args.requiredValidationMsg;	
    var textfieldRequiredMsg = (YAHOO.lang.isUndefined(wargs.args.textfieldRequiredMsg)) ? "Invalid Entry" : wargs.args.textfieldRequiredMsg;
    // backward compatibility
	if(YAHOO.lang.isUndefined(wargs.args.requiredValidationMsg) && !YAHOO.lang.isUndefined(wargs.args.textfieldRequiredMsg))
	     validationRequiredMessage = textfieldRequiredMsg;
	var validationOptions = (YAHOO.lang.isUndefined(wargs.args.validation)) ? "" : wargs.args.validation;
    if(!YAHOO.lang.isUndefined(wargs.args.widgetEffect)) effects = wargs.args.widgetEffect;
    var validation_message_minChars = (YAHOO.lang.isUndefined(wargs.args.validation_message_minChars)) ? 
           "Please enter more characters! " : wargs.args.validation_message_minChars;
    var validation_message_maxChars = (YAHOO.lang.isUndefined(wargs.args.validation_message_maxChars)) ? 
           "Maximum number of characters exceeded!" : wargs.args.validation_message_maxChars;   
    var validationObject = null;
    
    if (wargs.service){
	   jmaki.doAjax({ url : wargs.service, method : 'POST', callback: function(_req) {
	        var rows = _req.responseText ; 
	        init(rows); }});
    } else init(value);

    function init(_v)
    {
         var mydiv = document.getElementById(uuid+"_div");
         var widgetContent =  "<textarea id=\""+uuid+"\" name=\""+uuid+"\" cols=\""+cols+"\" rows=\""+rows+"\""+
                  " title=\""+toolTip+"\" value=\""+value+"\"  tabindex=\""+tabOrder+"\"  wrap=\""+wrap+"\""+
                  " onChange= \"jmaki.attributes.get('"+uuid+"').onChange()\""+
                  " onKeyPress= \"jmaki.attributes.get('"+uuid +"').onKeyPress()\"";
          if(disabled)
		      widgetContent += " disabled='true' ";
		  if(readonly)
              widgetContent += "readonly";
          widgetContent += ">";
		  if(value.length > 0) widgetContent +=value;
		  widgetContent +="</textarea>"
          if(validationType != "none" && required) {
		      widgetContent += ("<span class='textareaRequiredMsg'>" + validationRequiredMessage +"</span>");
              //widgetContent += ("<span class='textareaRequiredMsg'>" + textfieldRequiredMsg +"</span>");
              widgetContent +=  ("<span class='textareaMinCharsMsg'>"+validation_message_minChars+"</span>");
              widgetContent +=  ("<span class='textareaMaxCharsMsg'>"+validation_message_maxChars+"</span>");
          }
          mydiv.innerHTML = widgetContent; 

          if(required && validationType == "none"){
          	  validationType = 'validate';
          	  wargs.args.validation['isRequired'] = 'true';          	  
          	  wargs.args.validation['validateOn'] = ['change','blur'];
          }


          if(validationType != "none" && required == true) {
            var vOptions = {};
			if(!YAHOO.lang.isUndefined(wargs.args.validation['validateOn']))
				  vOptions['validateOn'] = wargs.args.validation['validateOn'];
			if(!YAHOO.lang.isUndefined(wargs.args.validation['minChars']))
				  vOptions['minChars'] = wargs.args.validation['minChars'];
				if(!YAHOO.lang.isUndefined(wargs.args.validation['maxChars']))
				  vOptions['maxChars'] = wargs.args.validation['maxChars'];
			if(!YAHOO.lang.isUndefined(wargs.args.validation['useCharacterMasking']) 
				   && wargs.args.validation['useCharacterMasking']){				   
				  vOptions['useCharacterMasking'] = true;
			}else { vOptions['useCharacterMasking'] = false;}
			if(!YAHOO.lang.isUndefined(wargs.args.validation['isRequired']) 
				  && wargs.args.validation['isRequired'] == 'true')
				  vOptions['isRequired'] = true;
			 if(!YAHOO.lang.isUndefined(wargs.args.validation['isRequired'])  
				  && wargs.args.validation['isRequired'] == 'false')
				  vOptions['isRequired'] = false;
			//if(typeof wargs.args.validation['hint'] == 'undefined') { vOptions['hint']= ''} else { vOptions['hint'] = wargs.args.validation['hint']; }
 
			//alert("textarea validation:"+YAHOO.lang.JSON.stringify(vOptions));
			validationObject = new Spry.Widget.ValidationTextarea(uuid+"_div", vOptions);
		}
          if(effects != null){ 
	           sbm.utils.applyEffects(uuid+"_div", effects);       
	   }       
    }

    this.setValue = function(_v) {
       document.getElementById(uuid).value = _v;
    }

    this.getValue = function() {
        return document.getElementById(uuid).value;
    }

    this.validate = function() {
       if(validationObject != null) {
           validationObject.validate();
       }
       return true;
    }
	
	this.getValidationType = function(){		
		return validationType;
	}
	
	this.addMaxLengthValidation = function(maxLength){	   
	   if(validationObject != null) validationObject.destroy();
	     validationObject = new Spry.Widget.ValidationTextarea(uuid+"_div", {validationOn:['blur','change'],maxChars:maxLength,isRequired:isRequired});	   
	}
   
    // published events
    this.onChange = function() {jmaki.publish(topic + "onChange", {widgetId: uuid, value: this.getValue()}); }
    this.onKeyPress = function() {jmaki.publish(topic + "onKeyPress", {widgetId: uuid, value: this.getValue()}); }
 }
