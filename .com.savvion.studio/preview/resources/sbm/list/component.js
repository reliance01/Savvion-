jmaki.namespace("jmaki.widgets.sbm.list");

jmaki.widgets.sbm.list.Widget = function(wargs) {
    // The jMaki framework must have created widget JavaScript object.
    var _widget = this;
    var ie = /MSIE/i.test(navigator.userAgent);
    var uuid = wargs.uuid;
    var topic = "/sbm/list/";
    var effects = null;
    
    var values = (YAHOO.lang.isUndefined(wargs.value)) ? "[{label: '', value: '', selected: false}]" : wargs.value;
    var size = (YAHOO.lang.isUndefined(wargs.args.size)) ? 6 : wargs.args.size;
    var type = (YAHOO.lang.isUndefined(wargs.args.type)) ? "list" : wargs.args.type;
    var maxlength = (YAHOO.lang.isUndefined(wargs.args.maxlength)) ? 20 : wargs.args.maxlength;
    var readonly = (YAHOO.lang.isUndefined(wargs.args.readonly)) ? false : wargs.args.readonly;    
    var disabled = (YAHOO.lang.isUndefined(wargs.args.disabled)) ? false : wargs.args.disabled;
    var tabOrder = (YAHOO.lang.isUndefined(wargs.args.tabOrder)) ? "" : wargs.args.tabOrder;
    var toolTip = (YAHOO.lang.isUndefined(wargs.args.toolTip)) ? "" : wargs.args.toolTip;
    var validationType = (YAHOO.lang.isUndefined(wargs.args.validationType)) ? "none" : wargs.args.validationType;
    var validation = (YAHOO.lang.isUndefined(wargs.args.validation)) ? "" : wargs.args.validation;
    var validationMessage = (YAHOO.lang.isUndefined(wargs.args.validation_message)) ? "Invalid Entry" : wargs.args.validation_message;
    var selectInvalidMsg = (YAHOO.lang.isUndefined(wargs.args.selectInvalidMsg)) ? "Please select a valid option" : wargs.args.selectInvalidMsg;
    var validationOptions = (YAHOO.lang.isUndefined(wargs.args.validation)) ? "" : wargs.args.validation;
    if(!YAHOO.lang.isUndefined(wargs.args.widgetEffect)) effects = wargs.args.widgetEffect;
    var selections = (YAHOO.lang.isUndefined(wargs.args.selections)) ? "" : wargs.args.selections;
    if(!YAHOO.lang.isUndefined(wargs.args.visible) && !wargs.args.visible)
	   sbm.widgets.hide(uuid);
    var validationObject = null;
    
    // if datasource for the widget is service then
    // set widget presentation in the div defined in the widget template file
    if (wargs.args.service) {
	    jmaki.doAjax({ url : wargs.args.service, method : 'POST', asynchronous: false, callback: function(_req) {
	            var rows = _req.responseText;
	            init(rows);}});
    } else init(values); // render list

    // initializes widget with given value
    function init(_v) {
	   if(YAHOO.lang.isUndefined(_v)) return null; 
           if (typeof _v != 'object') _v = eval('(' + _v + ')');
           
	   var widgetContent = "<select class='InptSelect' multiple='true' id ='"+uuid+"' tabindex = '"+tabOrder+"' onChange= \"jmaki.attributes.get('" + uuid + "').onChange();\""+   
	   " onBlur= \"jmaki.attributes.get('" + uuid + "').onBlur()\" onFocus= \"jmaki.attributes.get('" + uuid + "').onFocus();\"";
	   if(disabled) widgetContent += " disabled='true' ";
	   if(size > 3) widgetContent += (" size='"+size+"' ");
	   if(toolTip.length > 0) widgetContent += (" title='"+toolTip+"'");
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
		   widgetContent += "<span class='selectRequiredMsg'>Please make a selection.</span>";
		   widgetContent += "<span class='selectInvalidMsg'>"+selectInvalidMsg+"</span>";
	   }
           mydiv.innerHTML =  widgetContent;    
	   if(validationType != "none"){
		  var vOptions = {};
		  if(!YAHOO.lang.isUndefined(wargs.args.validation['validateOn']))
			  vOptions['validateOn'] = wargs.args.validation['validateOn'];
		  if(!YAHOO.lang.isUndefined(wargs.args.validation['isRequired']) 
		         && wargs.args.validation['isRequired']) {
			  vOptions['isRequired'] = true;
		  } else {
		      vOptions['isRequired'] = false;
		  }
	          vOptions['invalidValue'] = (YAHOO.lang.isUndefined(wargs.args.validation['invalidValue']) ? '-1' :wargs.args.validation['invalidValue'] );                 
		  if(validationObject == null)
		     validationObject = new Spry.Widget.ValidationSelect(uuid+"_div", vOptions);    
          }
          if(effects != null){ 
	         sbm.utils.applyEffects(uuid+"_div", effects);       
	      }
   }

   // sets the value of widget 
   this.setValue = function(_v) {
	   if(!YAHOO.lang.isUndefined(_v) && typeof eval(_v) != 'undefined') {
		   if (typeof _v != 'object') _v = eval('(' + _v + ')');
		   var selectElem = document.getElementById(uuid);		   
		   this.removeAllOptions(uuid);
		   for(var i=0; i < _v.length; i++) 
		   {
		         var _value = _v[i].value;
                         var _label = _v[i].label;
			 if(YAHOO.lang.isUndefined(_label) || _label.length == 0)
			   _label = _value;
                         var _selected = YAHOO.lang.isUndefined(_v[i].selected) ? false : _v[i].selected;
		  	 if(!YAHOO.lang.isUndefined(_value) && !YAHOO.lang.isUndefined(_label)){
                             var index = selectElem.options.length;
			     selectElem.options[selectElem.options.length] = new Option(_label,_value);
			     if(_selected || _selected == 'true')
				selectElem.options[index].selected = true;   
			 } 
	           }
	   }
   }
   	
   // sets the values of list widget 	
   this.setValues = function(_vals) {
          var elements = this.getValue();
          elements = eval(elements);
          // clear out the previous selections
          for(i=0;i<elements.length;i++) {                
              if(elements[i].selected != undefined) elements[i].selected = false;            
          }

          for(iCurrent=0; iCurrent<_vals.length; iCurrent++) {
            var selectedValue = _vals[iCurrent];
            if (selectedValue.value == undefined) continue;
            for(i=0;i<elements.length;i++) {                
                if(elements[i].value == selectedValue.value) {
                    elements[i].selected = true; 
                }
            }
         }
         // render the widget
         init(elements);
    }
    
     // gets the values of list widget
    this.getValues = function() {
	    var val = this.getValue();
	    if (val == undefined) return "[]";
	    val = eval(val);
	    var output = "[";
	    for(i=0;i<val.length;i++) {
		    if(val[i].selected == true) output = output+" {label:'"+val[i].label+"', value:'"+val[i].value+"', selected:true},";
	    }
	    
	    output = output + "]";
	    output = output.replace(",]","]");
	    return output;
    }    
    
    
    // gets the values of list widget
    this.getSelectedOptions = function() {
	    var selectedOptions = new Array();
	    var selectElement = document.getElementById(uuid);
	    for(var j=0; j< selectElement.options.length;j++) {
	       if(selectElement.options[j].selected){
		    var label = (selectElement.options[j].label.length != 0) ? selectElement.options[j].label
		                                          : selectElement.options[j].text;
		    var value = selectElement.options[j].value;
		    selectedOptions[selectedOptions.length] = {label:label,value:value};
	       }
	    }
	    return selectedOptions;
    }    
    
    this.setSelectedValues = function(selectedOptions) {
        // set the selected options 
        try{
             var values = YAHOO.lang.JSON.parse(selectedOptions);             
        }catch(e){}
        if(values.length == 0) return; 
        var selectElement = document.getElementById(uuid);
        for(var j=0; j< selectElement.options.length;j++) 
            selectElement.options[j].selected = false;
        for(var i=0; i<values.length;i++) {
           for(var j=0; j< selectElement.options.length;j++){     
                  if(selectElement.options[j].value == values[i]) {
                      selectElement.options[j].selected = true;
                  }                
             }       
        }         
    }

    this.putSelectedValues = function(selectedOptions) {
        // set the selected options, adding missing options
        try{
             var values = YAHOO.lang.JSON.parse(selectedOptions);             
        }catch(e){}
        if(values.length == 0) return; 
        var needsInit = false;
        var selectElement = document.getElementById(uuid);
        for(var j=0; j< selectElement.options.length;j++) 
            selectElement.options[j].selected = false;
        for(var i=0; i<values.length;i++) {
            var found = false;
            for(var j=0; j< selectElement.options.length;j++){     
                  if(selectElement.options[j].value == values[i]) {
                      selectElement.options[j].selected = true;
                      found = true;
                  }                
            }       
            if (!found) {
              var newOption = new Option(values[i], values[i]);
              newOption.selected = true;
              selectElement.options[selectElement.options.length] = newOption;
              needsInit = true;
            }
        }         
        if (needsInit) {
          init(selectElement.options);
        }
    }

        
    this.getSelectedValues = function(){
        var selectedValues = new Array();
        var selectElement = document.getElementById(uuid);
        for(var i=0; i< selectElement.options.length;i++){     
            if(selectElement.options[i].selected) 
                 selectedValues[selectedValues.length] = selectElement.options[i].value;   
        }
        return selectedValues;        
    }

    function _concat(_v1, _v2)
    {
	   if (_v1 == '[]') return _v2;
	   if (_v2 == '[]') return _v1;
	   _v1 = _v1.replace(/[\n\r\t]/g,'');
	   _v2 = _v2.replace(/[\n\r\t]/g,'');
	   var parts = _v1.split(']');
	   var temp = parts[0];
	   parts = _v2.split('[');
	   var temp2 = parts[1];
	   return (temp+','+temp2);
    }
        
    // gets the value of widget
    this.getValue = function() {
	    var elements = document.getElementById(uuid);
	    //var options = YAHOO.util.Dom.getChildren(element);	    
	    var values = new Array();
		for(i=0;i<elements.length;i++) {
		    var label = (elements.options[i].label.length != 0) ? elements.options[i].label
		                                          : elements.options[i].text;
			var _v = {label:label,value:elements.options[i].value};
			if(elements.options[i].selected == true) _v.selected = true;
			values.push(_v);
		}
		// for backward compatibility it should return as string
		return YAHOO.lang.JSON.stringify(values);		     
    }
    
    
    this.addOptions = function(_opts) {
           if(typeof _opts == 'undefind' || _opts.length == 0) return;
	   if (typeof _opts != 'object') _opts = eval('(' + _opts + ')');
	   var selectElem = document.getElementById(uuid);
	   for(var j=0;j<_opts.length;j++){
		selectElem.options[selectElem.options.length] = new Option(_opts[j].label,_opts[j].value);		 
	   }
       if(Ext.isIE){
         /**
          *    This is an IE bug
          *    BUG: Internet Explorer Fails to Set the innerHTML Property of the Select Object
          */

	      var values = this.getValue();
		  for(var j=0;j<_opts.length;j++)
		  values[values.lenfth] = _opts[j];
		  init(values);		 
	   }
    } 

    this.removeOptions = function() {
	    sbm.util.removeOptions(uuid);
    }

    this.removeAllOptions = function() {
	    sbm.util.removeAllOptions(uuid);
    }

    
    // published events
    this.onChange = function() {jmaki.publish(topic + "onChange", {widgetId: wargs.uuid, value: this.getValue()}); }
    this.onFocus = function() {jmaki.publish(topic + "onFocus", {widgetId: wargs.uuid, value: this.getValue()}); }
    this.onBlur = function() {jmaki.publish(topic + "onBlur", {widgetId: wargs.uuid, value: this.getValue()}); }
}
