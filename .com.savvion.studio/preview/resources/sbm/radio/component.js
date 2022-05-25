jmaki.namespace("jmaki.widgets.sbm.radio");

jmaki.widgets.sbm.radio.Widget = function(wargs) {
    // The jMaki framework must have created widget JavaScript object.
    var _widget = this;
    var ie = /MSIE/i.test(navigator.userAgent);
    var uuid = wargs.uuid;
    var topic = "/sbm/radio/";
    var effects = null;
        
    var size = (YAHOO.lang.isUndefined(wargs.args.size)) ? 20 : wargs.args.size;
    var maxlength = (YAHOO.lang.isUndefined(wargs.args.maxlength)) ? 20 : wargs.args.maxlength;
    var readonly = (YAHOO.lang.isUndefined(wargs.args.readonly)) ? false : wargs.args.readonly;        
	var disabled = (YAHOO.lang.isUndefined(wargs.args.disabled)) ? false : wargs.args.disabled;
	disabled = readonly || disabled;
    var toolTip = (YAHOO.lang.isUndefined(wargs.args.toolTip)) ? "" : wargs.args.toolTip;
    var tabOrder = (YAHOO.lang.isUndefined(wargs.args.tabOrder)) ? "" : wargs.args.tabOrder;
    var layout = (YAHOO.lang.isUndefined(wargs.args.layout)) ? "horizontal" : wargs.args.layout.toLowerCase();        
    var value = (YAHOO.lang.isUndefined(wargs.value)) ? "[{label: 'Option 1', value: 'opt1', selected: true},{label: 'Option 2', value: 'opt2', selected: true}]" : wargs.value;  
    var validation = (YAHOO.lang.isUndefined(wargs.args.validation)) ? "" : wargs.args.validation;
    var radioRequiredMsg = (YAHOO.lang.isUndefined(wargs.args.validation['radioRequiredMsg'])) ? "Please select a value!" : wargs.args.validation['radioRequiredMsg'];
    var validationType = (YAHOO.lang.isUndefined(wargs.args.validationType)) ? "none" : wargs.args.validationType;
    var validationOptions = (YAHOO.lang.isUndefined(wargs.args.validation)) ? "" : wargs.args.validation;
    if(!YAHOO.lang.isUndefined(wargs.args.visible) && !wargs.args.visible)
	   sbm.widgets.hide(uuid);
	if(!YAHOO.lang.isUndefined(wargs.args.widgetEffect)) effects = wargs.args.widgetEffect;
    var validationObject = null;
    
    if (wargs.publish) topic = wargs.publish;
    if (wargs.value) value = wargs.value;
    
    if (wargs.args.service){
	 jmaki.doAjax({ url : wargs.args.service, method : 'POST',
	        asynchronous: false,callback: function(_req) {
	        var rows = _req.responseText;
	        init(rows);}});
    } else  init(value); // render radiobuttons
    
    function init(_v) {          
          var _selected;
          var _val;
          var _label;
          if(YAHOO.lang.isUndefined(_v) || _v == null) return null; 
          if (typeof _v != 'object') _v = eval('(' + _v + ')');
          var values = _v;
          var widgetContent = ""; 
          
          widgetContent += "<table>"
          if(layout == 'horizontal') 
              widgetContent += ("<tr>");
          for (var i=0; i < values.length; i++) 
          {
              _value = values[i].value;
              _label = values[i].label;
              _selected = (YAHOO.lang.isUndefined(values[i].selected)) ? false : values[i].selected;
                            
	      if(!YAHOO.lang.isUndefined(_label) &&
                 !YAHOO.lang.isUndefined(_value) && 
                 !YAHOO.lang.isUndefined(_selected)){
                       if(layout == 'vertical') 
                          widgetContent += ("<tr>");
                       
                       widgetContent += ("<td><input id=\""+uuid+"\" name=\""+uuid+"\" class=\"radio\" title=\""+toolTip+"\" value=\""+_value+"\"  tabindex=\""+tabOrder+"\"");
                       widgetContent += (" type=\"radio\" onClick=\"jmaki.attributes.get('" + uuid + "').onClick()\"");
                       widgetContent += (" onFocus=\"jmaki.attributes.get('" + uuid + "').onFocus()\"");
					   widgetContent += (" onChange=\"jmaki.attributes.get('" + uuid + "').onChange()\" ");
					   widgetContent += (" onBlur=\"jmaki.attributes.get('" + uuid + "').onBlur()\" ");
                       if(_selected || _selected == 'true')
                             widgetContent += (" checked=\"true\"");
                       widgetContent += ("/></td>");
                       widgetContent += ("<td><label for=\"" + _value +"\" class=\"DataLabelLft\">" + _label + "</label></td>"); 
                                           
                       if(layout == 'vertical') 
                         widgetContent += ("</tr>");                    
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
          if(disabled) sbm.widgets.disable(uuid);
	  
	  if(validationType != "none"){
	       var vOptions = {};	  
	        if(!YAHOO.lang.isUndefined(wargs.args.validation['validateOn']))
			  vOptions['validateOn'] = wargs.args.validation['validateOn'];	  
		if(!YAHOO.lang.isUndefined(wargs.args.validation['isRequired']) 
		         &&  (wargs.args.validation['isRequired'] == 'true' || wargs.args.validation['isRequired'])){
			  vOptions['isRequired'] = true;  
		}else {vOptions['isRequired'] = false;}
		if(!YAHOO.lang.isUndefined(wargs.args.validation['defaultValue']))
			  vOptions['defaultValue'] = wargs.args.validation['defaultValue'];
		if(!YAHOO.lang.isUndefined(wargs.args.validation['invalidValue']))
			  vOptions['invalidValue'] = wargs.args.validation['invalidValue'];			    
                if(vOptions['isRequired']){
				   validationObject = new Spry.Widget.ValidationRadio(uuid+"_div", vOptions);    
				}
          }
          if(effects != null){ 
	           sbm.utils.applyEffects(uuid+"_div", effects);       
	  }
    }

    this.setValue = function(_v) {
      if(!YAHOO.lang.isUndefined(_v) && typeof eval(_v) != 'undefined' )    	    
         init(_v);
    }

    this.getValue = function() {
	 var values = new Array();    
         var widgetElement = document.getElementById(uuid+"_div"); 
         var inputs = widgetElement.getElementsByTagName("input");       
         for(var j =0; j < inputs.length;j++){
             var input = inputs[j];
             var id = input.getAttribute("id");
	     var value = input.getAttribute("value");
             var labels = widgetElement.getElementsByTagName("label"); 
             var label;
	     for(var i =0; i < labels.length;i++){
		     if(labels[i].getAttribute('for') == value) {
			    label = labels[i].innerHTML;
		            break;
	             }
	     }
	     var selected = input.checked;
	     //alert("label:"+label+"\nvalue:"+value+"\nselected:"+selected);
	     var choice = new Choice(label,value,selected);
             values[values.length] = choice;
        }     
	//alert("uuid:"+uuid+"\n"+YAHOO.lang.JSON.stringify(values))
        return YAHOO.lang.JSON.stringify(values);
    }
    
    this.setSelectedValues = function(selectedOptions) {
        // set the selected options 
        try{
             var values = YAHOO.lang.JSON.parse(selectedOptions);             
        }catch(e){}
        if(values.length == 0) return; 
        
        var widgetElement = document.getElementById(uuid+"_div");
        var inputs = widgetElement.getElementsByTagName("input");       
        for(var j=0; j< inputs.length;j++) {
            var input = inputs[j];
            input.checked = false; 
        }
        
        for(var j =0; j < inputs.length;j++){
             var input = inputs[j];
             for(var i=0 ; i<values.length;i++) {
                  if(values[i] == input.value)
                        input.checked = true;                 
             }
        }
    }
    
     this.setSelectedValue = function(value) {
	var selectedValue ='';
        var widgetElement = document.getElementById(uuid+"_div");
        var inputs = widgetElement.getElementsByTagName("input");       
        for(var j =0; j < inputs.length;j++){
             var radio = inputs[j];             
             if(radio.value == value) {
                 radio.checked = true;
		 break;
             }
        }
    }
    
    this.getSelectedValues = function() {
	var selectedValues = new Array();
        var widgetElement = document.getElementById(uuid+"_div");
        var inputs = widgetElement.getElementsByTagName("input");       
        for(var j =0; j < inputs.length;j++){
             var radio = inputs[j];             
             if(radio.checked) {
                 selectedValues[selectedValues.length] = radio.value;
             }
        }    
        if(formWidgetHandler.enableLogger)
            YAHOO.BM.Logger.info("getSelectedValues for '"+uuid+"'  widget returns :"+selectedValues);
       	return selectedValues;        
    }
    
    this.getSelectedValue = function() {
	var selectedValue ='';
        var widgetElement = document.getElementById(uuid+"_div");
        var inputs = widgetElement.getElementsByTagName("input");       
        for(var j =0; j < inputs.length;j++){
             var radio = inputs[j];             
             if(radio.checked) {
                 selectedValue = radio.value;
		 break;
             }
        }
       	return selectedValue;        
    }
            
    this.validate = function() {
      if (validation_type != "none") {
         var validationObject = new Spry.Widget.ValidationRadio(uuid+"_div", validationOptions);
         return validationObject.validate();
      }
      return true;
    }

    // published events
    this.onClick = function() {jmaki.publish(topic + "onClick", {widgetId: wargs.uuid, value: this.getValue()}); }
    this.onFocus = function() {jmaki.publish(topic + "onFocus", {widgetId: wargs.uuid, value: this.getValue()}); }
    this.onBlur = function() {jmaki.publish(topic + "onBlur", {widgetId: wargs.uuid, value: this.getValue()}); }
    this.onChange = function() {jmaki.publish(topic + "onChange", {widgetId: wargs.uuid, value: this.getValue()}); }
}
