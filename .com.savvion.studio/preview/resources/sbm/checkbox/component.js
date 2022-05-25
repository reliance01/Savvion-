jmaki.namespace("jmaki.widgets.sbm.checkbox");
 
jmaki.widgets.sbm.checkbox.Widget = function(wargs) {
    // The jMaki framework must have created widget JavaScript object.
    var _widget = this;
    var ie = /MSIE/i.test(navigator.userAgent);
    var uuid = wargs.uuid;
    var topic = "/sbm/checkbox/";
    var effects = null;
           
    var size = (YAHOO.lang.isUndefined(wargs.args.size)) ? 20 : wargs.args.size;
    var maxlength = (YAHOO.lang.isUndefined(wargs.args.maxlength)) ? 20 : wargs.args.maxlength;
    var readonly = (YAHOO.lang.isUndefined(wargs.args.readonly)) ? false : wargs.args.readonly;    
    var disabled = (YAHOO.lang.isUndefined(wargs.args.disabled)) ? false : wargs.args.disabled;
    disabled = readonly || disabled;
	var toolTip = (YAHOO.lang.isUndefined(wargs.args.toolTip)) ? "" : wargs.args.toolTip;
    var tabOrder = (YAHOO.lang.isUndefined(wargs.args.tabOrder)) ? "" : wargs.args.tabOrder;
    var layout = (YAHOO.lang.isUndefined(wargs.args.layout)) ? "horizontal" : wargs.args.layout.toLowerCase();    
    var value = (YAHOO.lang.isUndefined(wargs.value)) ? "[{label: 'Option 1', value: 'opt1', selected: false}]" : wargs.value;  
    var validation = (YAHOO.lang.isUndefined(wargs.args.validation)) ? "" : wargs.args.validation;
	var required = (YAHOO.lang.isUndefined(wargs.args.validation['isRequired'])) ? false : wargs.args.validation['isRequired'];
    var checkboxRequiredMsg = (YAHOO.lang.isUndefined(wargs.args.checkboxRequiredMsg)) ? "Please select a value!" : wargs.args.checkboxRequiredMsg;
    var checkboxMinSelectionsMsg = (YAHOO.lang.isUndefined(wargs.args.checkboxMinSelectionsMsg))
                           ? "Please select more options" : wargs.args.checkboxMinSelectionsMsg;
    var checkboxMaxSelectionsMsg = (YAHOO.lang.isUndefined(wargs.args.checkboxMaxSelectionsMsg))
                           ? "Max selection exceeded" : wargs.args.checkboxMaxSelectionsMsg;                       
    var validationType = (YAHOO.lang.isUndefined(wargs.args.validationType)) ? "none" : wargs.args.validationType;
    var validationOptions = (YAHOO.lang.isUndefined(wargs.args.validation)) ? "" : wargs.args.validation;
    if(!YAHOO.lang.isUndefined(wargs.args.widgetEffect)) effects = wargs.args.widgetEffect;
    var validationObject = null;
    var vOptions = {};
    if(!YAHOO.lang.isUndefined(wargs.args.visible) && !wargs.args.visible)
	   sbm.widgets.hide(uuid);     
	 
    if (wargs.args.service){
	 jmaki.doAjax({ url : wargs.args.service, method : 'POST',
	        asynchronous: false,callback: function(_req) {
	        var rows = _req.responseText;
	        init(rows);}});
    } else  init(value); // render checkboxes
    
    
    function init(_v){
         var _selected;
         var _val;
         var _label;
         if(YAHOO.lang.isUndefined(_v) || _v == null) return null; 
         if (typeof _v != 'object') _v = eval('(' + _v + ')');     
	 var values = _v;
         var widgetContent = "";  
                      
         
         widgetContent += "<table id=\""+uuid+"\">"
          if(layout == 'horizontal') 
              widgetContent += ("<tr>");
         
         for (var i=0; i < values.length; i++) {                        
            _value = values[i].value;
            _label = values[i].label;
            _selected = (YAHOO.lang.isUndefined(values[i].selected)) ? false : values[i].selected;
            
            if(!YAHOO.lang.isUndefined(_label) &&
                 !YAHOO.lang.isUndefined(_value) && 
                 !YAHOO.lang.isUndefined(_selected)){
                        if(layout == 'vertical') 
                          widgetContent += ("<tr>"); 
                        widgetContent += ("<td><input id=\""+uuid+"_"+i+"\" name=\""+uuid+"_"+i+"\" class=\"checkbox\" title=\""+toolTip+"\"");
                        widgetContent += (" type=\"checkbox\" tabindex=\""+tabOrder+"\"");
                        widgetContent += (" onClick= \"jmaki.attributes.get('" + uuid + "').onClick()\"");
                        widgetContent += (" onFocus= \"jmaki.attributes.get('" + uuid + "').onFocus()\" ");
			            widgetContent += (" onChange= \"jmaki.attributes.get('" + uuid + "').onChange()\" ");
						widgetContent += (" onBlur= \"jmaki.attributes.get('" + uuid + "').onBlur()\" ");
						widgetContent += (" value= \"" + _value + "\"");
                        if(_selected) 
                            widgetContent += ("checked=\"true\"");
                        widgetContent += ("/></td>"); 
                        widgetContent += ("<td><label for=\""+uuid+"_"+i+"\" class=\"DataLabelLft\">" + _label + "</label></td>");                   
                 }                 
         }
         
         if(validationType != "none" && required) {
             widgetContent += ("<span class='checkboxRequiredMsg'>"+checkboxRequiredMsg+"</span>"+
                   "<span class='checkboxMaxSelectionsMsg'>"+checkboxMaxSelectionsMsg+"</span>"+
                   "<span class='checkboxMinSelectionsMsg'>"+checkboxMinSelectionsMsg+"</span>");
         }
         
         var mydiv = document.getElementById(uuid+"_div");
         mydiv.innerHTML =  widgetContent;
         if(disabled) sbm.widgets.disable(uuid);
	 
		if(validationType != "none" && required) {
               var vOptions = {};
	       if(!YAHOO.lang.isUndefined(wargs.args.validation['validateOn']))
			  vOptions['validateOn'] = wargs.args.validation['validateOn'];
	       if(!YAHOO.lang.isUndefined(wargs.args.validation['isRequired']) 
		         && wargs.args.validation['isRequired'] == 'true')
			  vOptions['isRequired'] = true;
	       if(!YAHOO.lang.isUndefined(wargs.args.validation['minSelections']))
			  vOptions['minSelections'] = parseInt(wargs.args.validation['minSelections']);
	       if(!YAHOO.lang.isUndefined(wargs.args.validation['maxSelections']))
			  vOptions['maxSelections'] = parseInt(wargs.args.validation['maxSelections']);	       
		   if(validationObject != null)
		        validationObject.destroy();
		   validationObject = new Spry.Widget.ValidationCheckbox(uuid+"_div",vOptions);
	    
	 }
         if(effects != null){ 
	           sbm.utils.applyEffects(uuid+"_div", effects);       
	 }             
    }

    this.setValue = function(_v) {		
       if(!YAHOO.lang.isUndefined(this.bindingType) && this.bindingType == 'BOOLEAN'){
            var content = "<input id=\""+uuid +"\" name=\""+uuid+"\" type=\"checkbox\" title=\""+toolTip+"\""
				+ " onClick= \"jmaki.attributes.get('" + uuid + "').onClick()\""
			    + " onFocus= \"jmaki.attributes.get('" + uuid + "').onFocus()\" "
				+ " onChange= \"jmaki.attributes.get('" + uuid + "').onChange()\" "
				+ " onBlur= \"jmaki.attributes.get('" + uuid + "').onBlur()\" "				
				+ ((wargs.args.readonly) ? 'readOnly=\"true\"' : '')+  ((disabled) ? 'disabled=\"true\"' : '');
				
				if((typeof _v != 'string' && _v) ||  (typeof _v == 'string' && _v == 'true')) {                  
                   content += ("checked=\"true\"");
				}
				 content += ("/>");
                
		
		if(validationType != "none") {
			content += ("<span class='checkboxRequiredMsg'>"+checkboxRequiredMsg+"</span>"+
			"<span class='checkboxMaxSelectionsMsg'>"+checkboxMaxSelectionsMsg+"</span>"+
			"<span class='checkboxMinSelectionsMsg'>"+checkboxMaxSelectionsMsg+"</span>");
		}
		document.getElementById(uuid+"_div").innerHTML = content; 				
		if(validationType != "none"){
           validationObject.destroy();		
		   validationObject = new Spry.Widget.ValidationCheckbox(uuid+"_div",vOptions);
        }

		 
           

	   }
		  


       else if(!YAHOO.lang.isUndefined(_v) && typeof eval(_v) != 'undefined' )    	    
         init(_v);
    }
    
    this.getValue = function() {



       var values = new Array(); 
       var widgetElement = document.getElementById(uuid+"_div"); 
       var labels = widgetElement.getElementsByTagName("label");       
       if(!YAHOO.lang.isUndefined(this.bindingType) && this.bindingType == 'BOOLEAN'){
		    //alert(document.getElementById(uuid).checked);
            return  document.getElementById(uuid).checked;
	   }
	   
	   for(var j =0; j < labels.length;j++){
             var labelElement = labels[j];
             var id = labelElement.htmlFor;
             var checkBoxElement = document.getElementById(id);
             var label = labelElement.innerHTML;
             var value = label;
             var selected = checkBoxElement.checked;             
             var choice = new Choice(label,value,selected);
             values[values.length] = choice;
       }      
       return YAHOO.lang.JSON.stringify(values);
    }

    this.setSelectedValues = function(selectedOptions) {
	// set the selected options 
        try{
             var values = YAHOO.lang.JSON.parse(selectedOptions);             
        }catch(e){}
        if(values.length == 0) return; 
        
        var widgetElement = document.getElementById(uuid+"_div");
        var labels = widgetElement.getElementsByTagName("label"); 
		var inputs = widgetElement.getElementsByTagName("input");       
        
        for(var j=0; j< labels.length;j++) {
            var labelElement = labels[j];
            var id = labelElement.htmlFor;
            var checkBoxElement = document.getElementById(id);
             checkBoxElement.checked = false; 
        }
		
		for (var i = 0; i < values.length; i++) {
		   for (var j = 0; j < inputs.length; j++) {
		        if(values[i] == inputs[j].value)
		           inputs[j].checked = true;
		   }
		}		
        
    }
        
    this.getSelectedValues = function(){
        var selectedValues = new Array();
        var widgetElement = document.getElementById(uuid+"_div");
        var inputs = widgetElement.getElementsByTagName("input");       
        for(var j =0; j < inputs.length;j++){             
             if(inputs[j].checked) {
                 selectedValues[selectedValues.length] = inputs[j].value;
             }
        }            
        return selectedValues;        
    }

    this.validate = function() {
       if (validationType != "none") {
          validationObject = new Spry.Widget.ValidationCheckbox(uuid+"_div", validationOptions);
          return validationObject.validate();
       }
       return true;
    }

	this.setBindingType = function(type){       
	   this.bindingType = type;
	}

	this.getBindingType = function(){       
	   return this.bindingType;
	}
	
	this.getValidationType = function(){
	   return validationType;
	}

    // published events
    this.onClick = function() {jmaki.publish(topic + "onClick", {widgetId: wargs.uuid, value: this.getValue()}); }
    this.onFocus = function() {jmaki.publish(topic + "onFocus", {widgetId: wargs.uuid, value: this.getValue()}); }
    this.onBlur = function() {jmaki.publish(topic + "onBlur", {widgetId: wargs.uuid, value: this.getValue()}); }
    this.onChange = function() {jmaki.publish(topic + "onChange", {widgetId: wargs.uuid, value: this.getValue()}); }
}
