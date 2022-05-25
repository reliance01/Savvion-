Ext.namespace('forms.widgets');
Ext.namespace('forms.widgets.sbm');
Ext.namespace('forms.widgets.yahoo');
Ext.namespace('forms.widgets.Ext');
Ext.namespace('forms.widgets.sbm.textfield');

function FormWidgetPreviewHandler(allWidgets,options){
   this.allWidgets = allWidgets;
   this.formWidgets = new Array();
   if(this.allWidgets.length > 0) this.init(); 	
	
};

FormWidgetPreviewHandler.constructor = FormWidgetPreviewHandler;
FormWidgetPreviewHandler.prototype = {   
	init : function() {
		
	   for (var i=0; i<this.allWidgets.length; i++) {           
                var widgetName = this.allWidgets[i]['widget'];
		var type = this.allWidgets[i]['type'];
                var editable = this.allWidgets[i]['editable'];
                var widgetType = this.allWidgets[i]['type'];
                var bound = this.allWidgets[i]['bound']; 
                var source = this.allWidgets[i]['source']; 
                var target = this.allWidgets[i]['target'];
	        var args = this.allWidgets[i]['args'];
	        args.uuid = widgetName;
		//alert( YAHOO.lang.JSON.stringify(args));
	        var func = eval('forms.widgets.'+type);
		if(func) {
			
			var widget = new func(args);			
			if(widget.preview) widget.preview();
		}
	   }
	}
}

forms.widgets.sbm.textfield = function(args){
	var uuid =  args.uuid;
	var value = '';
	var effects;
	var size = (YAHOO.lang.isUndefined(args.size)) ? 20 : args.size;
	var type = (YAHOO.lang.isUndefined(args.type)) ? "text" : args.type;
	var maxlength = (YAHOO.lang.isUndefined(args.maxlength)) ? 20 : args.maxlength;
	var readonly = (YAHOO.lang.isUndefined(args.readonly)) ? false : args.readonly;    
	var disabled = (YAHOO.lang.isUndefined(args.disabled)) ? false : args.disabled;
	var tabOrder = (YAHOO.lang.isUndefined(args.tabOrder)) ? "" : args.tabOrder;
	var toolTip = (YAHOO.lang.isUndefined(args.toolTip)) ? "" : args.toolTip;
	var validationType = (YAHOO.lang.isUndefined(args.validationType)) ? "none" : args.validationType;
	var validation = (YAHOO.lang.isUndefined(args.validation)) ? "" : args.validation;
	var validationMessage = (YAHOO.lang.isUndefined(args.textfieldRequiredMsg)) ? "Invalid Entry" : args.textfieldRequiredMsg;
	var validationOptions = (YAHOO.lang.isUndefined(args.validation)) ? "{}" : args.validation;
	if(!YAHOO.lang.isUndefined(args.widgetEffect)) effects = args.widgetEffect;
	var validationObject = null;
	
	this.preview = function(){
		var mydiv = document.getElementById(uuid+"_div");
                var innerHTML = "<input name=\"" + uuid + "\" id=\"" + uuid +"\" class=\"textfield\" type=\"text\" size=\""+size+
                "\" maxlength=\""+maxlength+"\" value=\""+value+"\" title=\""+toolTip+"\"";
                
                if(readonly || disabled)
                    innerHTML += "readonly";
                innerHTML += ">"
                if(validationType != "none") 
                    //innerHTML += ("<span class='textfieldRequiredMsg'>" + validationMessage +"</span>");
                    innerHTML += ("<span class='textfieldInvalidFormatMsg'>" + validationMessage +"</span>");
                mydiv.innerHTML = innerHTML;
                // adding spry validation if it is associated with widget
	        if(validationType != "none") { 
			var vOptions = {};
			if(!YAHOO.lang.isUndefined(args.validation['validateOn']))
			  vOptions['validateOn'] = args.validation['validateOn'];
			if(!YAHOO.lang.isUndefined(args.validation['useCharacterMasking']) && args.validation['useCharacterMasking'])
			  vOptions['useCharacterMasking'] = true;
    	                if(!YAHOO.lang.isUndefined(args.validation['isRequired']) && args.validation['isRequired'] == 'true')
			  vOptions['isRequired'] = true;
			validationObject = new Spry.Widget.ValidationTextField(uuid+"_div", validationType,vOptions);
			
		}
    	        if(effects != null){ 
	           sbm.utils.applyEffects(uuid+"_div", effects);       
	        }
	        
	}
	
}





