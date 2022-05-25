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


forms.widgets.sbm.textarea = function(args){
	var _widget = this;
	var ie = /MSIE/i.test(navigator.userAgent);
	var uuid = args.uuid;
	var topic = "/sbm/textarea/";
	var effects = null;
	var value = "";
	var wrap = 'soft';
	
	var size = (YAHOO.lang.isUndefined(args.size)) ? 20 : args.size;    
	var maxlength = (YAHOO.lang.isUndefined(args.maxlength)) ? 20 : args.maxlength;
	var readonly = (YAHOO.lang.isUndefined(args.readonly)) ? false : args.readonly;    
	var disabled = (YAHOO.lang.isUndefined(args.disabled)) ? false : args.disabled;
	var tabOrder = (YAHOO.lang.isUndefined(args.tabOrder)) ? "" : args.tabOrder;
	var toolTip = (YAHOO.lang.isUndefined(args.toolTip)) ? "" : args.toolTip;
	var rows = (YAHOO.lang.isUndefined(args.rows)) ? "5" : args.rows;
	var cols = (YAHOO.lang.isUndefined(args.cols)) ? "25" : args.cols;
	var validationType = (YAHOO.lang.isUndefined(args.validationType)) ? "none" : args.validationType;
	var validation = (YAHOO.lang.isUndefined(args.validation)) ? "" : args.validation;
	var textfieldRequiredMsg = (YAHOO.lang.isUndefined(args.textfieldRequiredMsg)) ? "Invalid Entry" : args.textfieldRequiredMsg;
	var validationOptions = (YAHOO.lang.isUndefined(args.validation)) ? "" : args.validation;
	if(!YAHOO.lang.isUndefined(args.widgetEffect)) effects = args.widgetEffect;
	var validation_message_minChars = (YAHOO.lang.isUndefined(args.validation_message_minChars)) ? 
        	  "Please enter more characters! " : args.validation_message_minChars;
        var validation_message_maxChars = (YAHOO.lang.isUndefined(args.validation_message_maxChars)) ? 
                 "Maximum number of characters exceeded!" : args.validation_message_maxChars;   
        var validationObject = null;
	 
	var mydiv = document.getElementById(uuid+"_div");
        var widgetContent =  "<textarea id=\""+uuid+"\" name=\""+uuid+"\" class=\"textarea\" cols=\""+cols+"\" rows=\""+rows+"\""+
                  " title=\""+toolTip+"\" value=\""+value+"\"";                  
        if(readonly || disabled)widgetContent += "readonly";
        widgetContent += "></textarea>"
        if(validationType != "none") {
              widgetContent += ("<span class='textareaRequiredMsg'>" + textfieldRequiredMsg +"</span>");
              widgetContent +=  ("<span class='textareaMinCharsMsg'>"+validation_message_minChars+"</span>");
              widgetContent +=  ("<span class='textareaMaxCharsMsg'>"+validation_message_maxChars+"</span>");
        }
        mydiv.innerHTML = widgetContent; 
        if(validationType != "none") {
                var vOptions = {};
		if(!YAHOO.lang.isUndefined(args.validation['validateOn']))
			  vOptions['validateOn'] = args.validation['validateOn'];
		if(!YAHOO.lang.isUndefined(args.validation['isRequired']) 
			  && args.validation['isRequired'] == 'true')
			  vOptions['isRequired'] = true;
		if(!YAHOO.lang.isUndefined(args.validation['minChars']))
			  vOptions['minChars'] = args.validation['minChars'];
	        if(!YAHOO.lang.isUndefined(args.validation['maxChars']))
			  vOptions['maxChars'] = args.validation['maxChars'];
		if(!YAHOO.lang.isUndefined(args.validation['useCharacterMasking']) 
			   && args.validation['useCharacterMasking'] == 'true')
			  vOptions['useCharacterMasking'] = true;
    	        if(!YAHOO.lang.isUndefined(args.validation['isRequired'])  
			  && args.validation['isRequired'] == 'true')
			  vOptions['isRequired'] = true;
		//alert("textarea validation:"+YAHOO.lang.JSON.stringify(vOptions));
		validationObject = new Spry.Widget.ValidationTextarea(uuid+"_div", vOptions);
	}
        if(effects != null){ 
	           sbm.utils.applyEffects(uuid+"_div", effects);       
	}  
}

forms.widgets.sbm.combobox = function(args){ 
    var _widget = this;
    var ie = /MSIE/i.test(navigator.userAgent);
    var uuid = args.uuid;
    var topic = "/sbm/combobox/";
    var effects = null;
     // variables for recursion
    var depth = 0; // for getting depth of tree
    var selectedItem // for getting selected node object;
    var cascade_combos_data = "";
        
    var values = (YAHOO.lang.isUndefined(args.value)) ? "[{label: 'Select One', value: '-1', selected: false}]" : wargs.value;
    var size = (YAHOO.lang.isUndefined(args.size)) ? 1 : args.size;
    var disabled = (YAHOO.lang.isUndefined(args.disabled)) ? false : args.disabled;
    var tabOrder = (YAHOO.lang.isUndefined(args.tabOrder)) ? "" : args.tabOrder;
    var toolTip = (YAHOO.lang.isUndefined(args.toolTip)) ? "" : args.toolTip;
    var validationType = (YAHOO.lang.isUndefined(args.validationType)) ? "none" : args.validationType;
    var validation = (YAHOO.lang.isUndefined(args.validation)) ? "" : args.validation;
    var validationMessage = (YAHOO.lang.isUndefined(args.validation_message)) ? "Invalid Entry" : args.validation_message;
    var selectInvalidMsg = (YAHOO.lang.isUndefined(args.selectInvalidMsg)) ? "Please select a valid option" : args.selectInvalidMsg;
    var validationOptions = (YAHOO.lang.isUndefined(args.validation)) ? "" : args.validation;
    if(!YAHOO.lang.isUndefined(args.widgetEffect)) effects = args.widgetEffect;
    var selections = (YAHOO.lang.isUndefined(args.selections)) ? "" : args.selections;
    
    var cascade = (YAHOO.lang.isUndefined(args.cascade)) ? null : args.cascade;
    var level = (YAHOO.lang.isUndefined(args.level)) ? 0 : args.level;
    var validationObject = null;    
    var _v = [{label: 'Select One', value: '-1', selected: true},
          {label: 'Option1', value: 'option1', selected: false},
	  {label: 'Option2', value: 'option2', selected: false},
	  {label: 'Option3', value: 'option3', selected: false}];
    if(YAHOO.lang.isUndefined(_v)) return;  
    if (typeof _v != 'object') _v = eval(_v);
             
    var widgetContent = "<select style='width:100px' class='combobox' title='"+toolTip+"' id = '" + uuid +"' >";
    
    for(var i=0; i < _v.length; i++) {
                  var _val = _v[i].value;
                  var _label = _v[i].label;
                  if(YAHOO.lang.isUndefined(_label) || _label.length == 0)
			   _label = _val;
                  var _selected = YAHOO.lang.isUndefined(_v[i].selected) ? false : _v[i].selected;
                  //alert("_label:"+_label+"\n_val:"+_val+"\n_selected:"+_selected);
                  if(!YAHOO.lang.isUndefined(_val) && !YAHOO.lang.isUndefined(_label)){
                      	   if(_val.length != 0 && _label.length != 0)
		               widgetContent = widgetContent + "<option value='" + _val + "' " + _selected +"'>"+_label+"</option>";
                  }
    }
	     
    widgetContent += "</select>";
    if(validationType != "none") {
	widgetContent += "<span class='selectRequiredMsg'>"+selectInvalidMsg+"</span>";
	widgetContent += "<span class='selectInvalidMsg'>"+selectInvalidMsg+"</span>";
    }
    // set widget presentation in the div defined in the widget template file
    var mydiv = document.getElementById(uuid+"_div");
    mydiv.innerHTML = widgetContent;
    if(validationType != "none"){
	var vOptions = {};
	if(!YAHOO.lang.isUndefined(args.validation['validateOn'])) vOptions['validateOn'] = args.validation['validateOn'];
	if(!YAHOO.lang.isUndefined(args.validation['isRequired']) && args.validation['isRequired'])
	        vOptions['isRequired'] = true;
	vOptions['invalidValue'] = (YAHOO.lang.isUndefined(args.validation['invalidValue']) ? '-1' :args.validation['invalidValue'] );	
	if(validationObject == null) {
	      //alert(YAHOO.lang.JSON.stringify(vOptions));	    
	       validationObject = new Spry.Widget.ValidationSelect(uuid+"_div", vOptions);    
	}
    }
    if(effects != null){ 
	sbm.utils.applyEffects(uuid+"_div", effects);       
    }
}


forms.widgets.sbm.list = function(args){ 
   var _widget = this;
    var ie = /MSIE/i.test(navigator.userAgent);
    var uuid = args.uuid;
    var topic = "/sbm/list/";
    var effects = null;
    
    var values = (YAHOO.lang.isUndefined(args.value)) ? "[{label: '', value: '', selected: false}]" : args.value;
    var size = (YAHOO.lang.isUndefined(args.size)) ? 6 : args.size;
    var type = (YAHOO.lang.isUndefined(args.type)) ? "list" : args.type;
    var maxlength = (YAHOO.lang.isUndefined(args.maxlength)) ? 20 : args.maxlength;
    var readonly = (YAHOO.lang.isUndefined(args.readonly)) ? false : args.readonly;    
    var disabled = (YAHOO.lang.isUndefined(args.disabled)) ? false : args.disabled;
    var tabOrder = (YAHOO.lang.isUndefined(args.tabOrder)) ? "" : args.tabOrder;
    var toolTip = (YAHOO.lang.isUndefined(args.toolTip)) ? "" : args.toolTip;
    var validationType = (YAHOO.lang.isUndefined(args.validationType)) ? "none" : args.validationType;
    var validation = (YAHOO.lang.isUndefined(args.validation)) ? "" : args.validation;
    var validationMessage = (YAHOO.lang.isUndefined(args.validation_message)) ? "Invalid Entry" : args.validation_message;
    var selectInvalidMsg = (YAHOO.lang.isUndefined(args.selectInvalidMsg)) ? "Please select a valid option" : args.selectInvalidMsg;
    var validationOptions = (YAHOO.lang.isUndefined(args.validation)) ? "" : args.validation;
    if(!YAHOO.lang.isUndefined(args.widgetEffect)) effects = args.widgetEffect;
    var selections = (YAHOO.lang.isUndefined(args.selections)) ? "" : args.selections;
    
    var validationObject = null;	
    
     var _v = [{label: 'Select One', value: '-1', selected: true},
          {label: 'Option1', value: 'option1', selected: false},
	  {label: 'Option2', value: 'option2', selected: false},
	  {label: 'Option3', value: 'option3', selected: false}];
    if(YAHOO.lang.isUndefined(_v)) return null; 
    if (typeof _v != 'object') _v = eval('(' + _v + ')');
           
    var widgetContent = "<select class='list' multiple='true' "+"' id ='"+uuid+"' ";
	   if(disabled) widgetContent += " disabled='true' ";
	   if(size > 3) widgetContent += (" size='"+size+"' ");
	   if(toolTip.length > 0) widgetContent += (" title=='"+toolTip+"'");
	   widgetContent += ">";       
	   
	   
	   for(var i=0; i < _v.length; i++) {
		 var _val = _v[i].value;
                 var _label = _v[i].label;
                 var _selected = "";
		 var selections = "";
		 
		 if(!YAHOO.lang.isUndefined(_val) && !YAHOO.lang.isUndefined(_label)){
                     if(selections == ""){
			     if (typeof _v[i].selected == 'boolean' &&  _v[i].selected == true) 
			          _selected = " selected = 'true' ";
			     
		     }else if (selections.indexOf(_val) != -1) {
			     _selected = " selected = 'true' ";
		     }	
                     if(_val.length == 0 && _label.length > 0) _val = _label;
                     if(_label.length == 0 && _val.length > 0) _label = _val;
                     if( _label.length > 0 && _val.length > 0)
		     widgetContent = widgetContent + "<option value='" + _val + "' " + _selected + "label='"+_label+"'>"+_label+"</option>";
	         }
           }
    	   
	   // set widget presentation in the div defined in the widget template file
           var mydiv = document.getElementById(uuid+"_div");
	   widgetContent += "</select>";
	   if(validationType != "none") {
		   widgetContent += "<span class='selectRequiredMsg'>"+selectInvalidMsg+"</span>";
		   widgetContent += "<span class='selectInvalidMsg'>"+selectInvalidMsg+"</span>";
	   }
           mydiv.innerHTML =  widgetContent;    
	   if(validationType != "none"){
		  var vOptions = {};
		  if(!YAHOO.lang.isUndefined(args.validation['validateOn'])) vOptions['validateOn'] = args.validation['validateOn'];
		  if(!YAHOO.lang.isUndefined(args.validation['isRequired'])&& args.validation['isRequired'])
			  vOptions['isRequired'] = true;
	          vOptions['invalidValue'] = (YAHOO.lang.isUndefined(args.validation['invalidValue']) ? '-1' :args.validation['invalidValue'] );		  
                  if(validationObject == null) validationObject = new Spry.Widget.ValidationSelect(uuid+"_div", vOptions);    
           }
           if(effects != null){ 
	           sbm.utils.applyEffects(uuid+"_div", effects);       
	   }
}

forms.widgets.sbm.radio = function(args){
    var _widget = this;
    var ie = /MSIE/i.test(navigator.userAgent);
    var uuid = args.uuid;
    var topic = "/sbm/radio/";
    var effects = null;
        
    var size = (YAHOO.lang.isUndefined(args.size)) ? 20 : args.size;
    var maxlength = (YAHOO.lang.isUndefined(args.maxlength)) ? 20 : args.maxlength;
    var readonly = (YAHOO.lang.isUndefined(args.readonly)) ? false : args.readonly;    
    var disabled = (YAHOO.lang.isUndefined(args.disabled)) ? false : args.disabled;
    var toolTip = (YAHOO.lang.isUndefined(args.toolTip)) ? "" : args.toolTip;
    var tabOrder = (YAHOO.lang.isUndefined(args.tabOrder)) ? "" : args.tabOrder;
    var layout = (YAHOO.lang.isUndefined(args.layout)) ? "horizontal" : args.layout;        
    var value = (YAHOO.lang.isUndefined(args.value)) ? "[{label: 'Option 1', value: 'opt1', selected: true}]" : args.value;  
    var validation = (YAHOO.lang.isUndefined(args.validation)) ? "" : args.validation;
    var radioRequiredMsg = (YAHOO.lang.isUndefined(args.radioRequiredMsg)) ? "Please select a value!" : args.radioRequiredMsg;
    var validationType = (YAHOO.lang.isUndefined(args.validationType)) ? "none" : args.validationType;
    var validationOptions = (YAHOO.lang.isUndefined(args.validation)) ? "" : args.validation;
    if(!YAHOO.lang.isUndefined(args.widgetEffect)) effects = args.widgetEffect;
    var validationObject = null;
    
    var _selected;
    var _val;
    var _label;
    var _v = [{label: 'Radio1', value: 'radio1', selected: false},
	  {label: 'Radio2', value: 'radio2', selected: false},
	  {label: 'Radio3', value: 'radio3', selected: false}];
    
    
    if (typeof _v != 'object') _v = eval('(' + _v + ')');
    var values = _v;
    var widgetContent = ""; 
          
    widgetContent += "<table>"
    if(layout == 'horizontal') widgetContent += ("<tr>");
    for (var i=0; i < values.length; i++) 
    {
          _value = values[i].value;
          _label = values[i].label;
          _selected = (YAHOO.lang.isUndefined(values[i].selected)) ? false : values[i].selected;
                            
          if(!YAHOO.lang.isUndefined(_label) && !YAHOO.lang.isUndefined(_value) && !YAHOO.lang.isUndefined(_selected)){
                 if(layout == 'vertical') widgetContent += ("<tr>");
                 widgetContent += ("<td><input id=\""+uuid+"\" name=\""+uuid+"\" class=\"radio\" title=\""+toolTip+"\" value=\""+_value+"\"");
                 widgetContent += (" type=\"radio\" ");
                 if(_selected || _selected == 'true') widgetContent += (" checked=\"true\"");
                 widgetContent += ("/></td>");
                 widgetContent += ("<td><label for=\"" + _value +"\" class=\"radio_label\">" + _label + "</label></td>"); 
                 if(layout == 'vertical') widgetContent += ("</tr>");                    
          }                             
    }
    
    if(layout == 'horizontal') widgetContent += ("</tr>");
    for (var i=0; i < values.length; i++) 
              widgetContent += "</table>"             
                    
    if (validationType != "none") {
             widgetContent += "<span class='radioRequiredMsg'>"+radioRequiredMsg+"</span>";
    }        
          
    var mydiv = document.getElementById(uuid+"_div");
    mydiv.innerHTML = widgetContent;
    if(validationType != "none"){
	       var vOptions = {};	  
	        if(!YAHOO.lang.isUndefined(args.validation['validateOn'])) vOptions['validateOn'] = args.validation['validateOn'];	  
		if(!YAHOO.lang.isUndefined(args.validation['isRequired']) && args.validation['isRequired'] == 'true')
			  vOptions['isRequired'] = true;  
		if(!YAHOO.lang.isUndefined(args.validation['defaultValue']))
			  vOptions['defaultValue'] = args.validation['defaultValue'];
		if(!YAHOO.lang.isUndefined(args.validation['invalidValue']))
			  vOptions['invalidValue'] = args.validation['invalidValue'];
                validationObject = new Spry.Widget.ValidationRadio(uuid+"_div", vOptions);    
    }
    if(effects != null){ 
	       sbm.utils.applyEffects(uuid+"_div", effects);       
    }
}

forms.widgets.sbm.checkbox = function(args){
    // The jMaki framework must have created widget JavaScript object.
    var _widget = this;
    var ie = /MSIE/i.test(navigator.userAgent);
    var uuid = args.uuid;
    var topic = "/sbm/checkbox/";
    var effects = null;
           
    var size = (YAHOO.lang.isUndefined(args.size)) ? 20 : args.size;
    var maxlength = (YAHOO.lang.isUndefined(args.maxlength)) ? 20 : args.maxlength;
    var readonly = (YAHOO.lang.isUndefined(args.readonly)) ? false : args.readonly;    
    var disabled = (YAHOO.lang.isUndefined(args.disabled)) ? false : args.disabled;
    var toolTip = (YAHOO.lang.isUndefined(args.toolTip)) ? "" : args.toolTip;
    var tabOrder = (YAHOO.lang.isUndefined(args.tabOrder)) ? "" : args.tabOrder;
    var layout = (YAHOO.lang.isUndefined(args.layout)) ? "horizontal" : args.layout.toLowerCase();    
    var value = (YAHOO.lang.isUndefined(args.value)) ? "[{label: 'Option 1', value: 'opt1', selected: true}]" : args.value;  
    var validation = (YAHOO.lang.isUndefined(args.validation)) ? "" : args.validation;
    var checkboxRequiredMsg = (YAHOO.lang.isUndefined(args.checkboxRequiredMsg)) ? "Please select a value!" : args.checkboxRequiredMsg;
    var checkboxMinSelectionsMsg = (YAHOO.lang.isUndefined(args.checkboxMinSelectionsMsg))
                           ? "Please select more options" : args.checkboxMinSelectionsMsg;
    var checkboxMaxSelectionsMsg = (YAHOO.lang.isUndefined(args.checkboxMaxSelectionsMsg))
                           ? "Max selection exceeded" : args.checkboxMaxSelectionsMsg;                       
    var validationType = (YAHOO.lang.isUndefined(args.validationType)) ? "none" : args.validationType;
    var validationOptions = (YAHOO.lang.isUndefined(args.validation)) ? "" : args.validation;
    if(!YAHOO.lang.isUndefined(args.widgetEffect)) effects = args.widgetEffect;
    var validationObject = null;
    
    var _selected;
    var _val;
    var _label;
     var _v = [{label: 'Checkbox1', value: 'checkbox1', selected: false},
	  {label: 'Checkbox1', value: 'checkbox2', selected: false},
	  {label: 'Checkbox1', value: 'checkbox3', selected: false}];
    
    
    var values = _v;
    var widgetContent = "";  
                      
    widgetContent += "<table>"
    if(layout == 'horizontal') widgetContent += ("<tr>");
         
    for (var i=0; i < values.length; i++) {                        
         _value = values[i].value;
         _label = values[i].label;
         _selected = (YAHOO.lang.isUndefined(values[i].selected)) ? false : values[i].selected;
            
         if(!YAHOO.lang.isUndefined(_label) &&
                 !YAHOO.lang.isUndefined(_value) && 
                 !YAHOO.lang.isUndefined(_selected)){
                        if(layout == 'vertical') 
                          widgetContent += ("<tr>"); 
                        widgetContent += ("<td><input id=\""+uuid+"_"+i+"\" class=\"checkbox\" title=\""+toolTip+"\"");
                        widgetContent += (" type=\"checkbox\" tabindex=\"");
                        widgetContent += (tabOrder + "\" ");                       
                        if(_selected) 
                            widgetContent += ("checked=\"true\"");
                        widgetContent += ("/></td>"); 
                        widgetContent += ("<td><label for=\""+uuid+"_"+i+"\" class=\"checkbox_label\">" + _label + "</label></td>");                   
                 }                 
         }
         
         if(validationType != "none") {
             widgetContent += ("<span class='checkboxRequiredMsg'>"+checkboxRequiredMsg+"</span>"+
                   "<span class='checkboxMaxSelectionsMsg'>"+checkboxMaxSelectionsMsg+"</span>"+
                   "<span class='checkboxMinSelectionsMsg'>"+checkboxMaxSelectionsMsg+"</span>");
         }
         
         var mydiv = document.getElementById(uuid+"_div");
         mydiv.innerHTML =  widgetContent;
         if(validationType != "none") {
               var vOptions = {};
	       if(!YAHOO.lang.isUndefined(args.validation['validateOn']))
			  vOptions['validateOn'] = args.validation['validateOn'];
	       if(!YAHOO.lang.isUndefined(args.validation['isRequired']) && args.validation['isRequired'] == 'true')
			  vOptions['isRequired'] = true;
	       if(!YAHOO.lang.isUndefined(args.validation['minSelections']))
			  vOptions['minSelections'] = parseInt(args.validation['minSelections']);
	       if(!YAHOO.lang.isUndefined(args.validation['maxSelections']))
			  vOptions['maxSelections'] = parseInt(args.validation['maxSelections']);
	       validationObject = new Spry.Widget.ValidationCheckbox(uuid+"_div",vOptions);
	    
	 }
         if(effects != null){ 
	           sbm.utils.applyEffects(uuid+"_div", effects);       
	 } 
}


forms.widgets.Ext.grid = function(args){
   var uuid = args.uuid;
   var container = document.getElementById(args.uuid);
   var ged = Ext.grid.GridEditor;
   var fm = Ext.form;
   var schema = [];
   
   var columns = [
     {id:'title',header: "Title", width: 160, sortable: true, dataIndex: 'title'},      
     {header: "Author", width: 160, sortable: true, dataIndex: 'author'},
     {header: "ISBN #", width: 160, sortable: true, dataIndex: 'isbn'},
     {header: "Description", width: 160, sortable: true, dataIndex: 'description'}
    ];

		  
   var data = [['Book Title 1','Author 1', '4412','A Some long description'],
               ['Book Title 2', 'Author 2', '4413', 'A Some long description']];   
   
   // create the data store
    var store = new Ext.data.SimpleStore({
        fields: [
           {name: 'title'},
           {name: 'author'},
           {name: 'isbn'},
           {name: 'description'}
        ]
    });
   store.loadData(data);
   document.getElementById(uuid + '_div').innerHTML = '';
   
   this.grid = new Ext.grid.EditorGridPanel({
	    id: uuid,	
            store: store,
            columns: columns,
	    width:600,
	    height:300,
	    title:'Ext Data Grid',
	    renderTo: uuid+'_div',
            selModel: new Ext.grid.RowSelectionModel(),
            enableColLock:false,
	    viewConfig: {
	            autoFill: true,
	            forceFit:true,
	            deferEmptyText: false,
	            emptyText: '<div style="{text-align: center}"><span style="font-size: 9pt; font-weight: normal">There are no record.<br/></span></span></div>'
	        },
	    
	    tbar: new Ext.PagingToolbar({
		       store: store,
		       pageSize: 5}),
	    bbar: new Ext.PagingToolbar({
		       store: store,
		       pageSize: 5})
    });
	
   this.grid.render();
}


forms.widgets.Ext.tree = function(args){
   var _widget = this;
   var uuid = args.uuid;
   var counter = 0;
    
   function genId() {
        return args.uuid + "_nid_" + counter++;
   }
   
   
   
   
   var data = { 
     	'root':{
               'id' : 'root',
               'label' : 'Tree Root Node',
               'expanded' : true,
               'children' : [
                     { 'label' : 'Node 1.1'},
                     { 'label' : 'Node 1.2',
                       'id' : 'foo2',
                       'expanded' : true,
                       'children' : [
                           { 'label' : 'Node 3.1',
                             'id' : 'foo'
                           }
                        ]
                    }
                ]
            }
   };
   
   var rootNode;
   document.getElementById(args.uuid+'_div').innerHTML = '';
   
   var tree = new Ext.tree.TreePanel({
                el:args.uuid+'_div',
	        animate:true, 
		autoHeight:true,
		autoScroll:true,
		containerScroll: true,
		collapseFirst: true,
                enableDD:true,
		border:false,
		dropConfig: {appendOnly:true}});
   
   this.buildTree =function(root, parent) {
        var rNode = addNode(root,parent);       
        for (var t=0; root.children && t < root.children.length; t++) {
            var n = root.children[t];
            var lNode = addNode(n,rNode);
           
            //  recursively call this function to add children
            if (typeof n.children != 'undefined') {
                for (var ts =0; n.children && ts < n.children.length; ts++) {
                    this.buildTree(n.children[ts], lNode);
                }
            }  
        }
   }
   
   function addNode(_root,parent){
	var pExpanded = false;            
        if (_root.title && !_root.label) _root.label = _root.title;
        if (typeof _root.expanded != 'undefined' && (_root.expanded == true)) pExpanded = true;  
        
        var nid;
        if (typeof _root.id != 'undefined') nid= _root.id;
        else nid = genId();
        
	var pNode = new Ext.tree.TreeNode({
               text: _root.label,
               expanded : pExpanded,
               draggable:false, // disable root node dragging
               id: nid});

        if (!rootNode) {
            rootNode = pNode;
            tree.setRootNode(pNode);
            parent = pNode
            parent.children = _root.children;
            tree.render(); 
        } else {
           if (typeof parent == 'undefined')parent = tree.getRootNode();
           parent.appendChild(pNode);
        }
	return pNode;
   }
   // build the tree with default node values
   this.buildTree(data.root);
}


