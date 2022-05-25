jmaki.namespace("jmaki.widgets.sbm.combobox");

jmaki.widgets.sbm.combobox.Widget = function(wargs) {
     // The jMaki framework must have created widget JavaScript object.
    var _widget = this;
    var ie = /MSIE/i.test(navigator.userAgent);
    var uuid = wargs.uuid;
    var topic = "/sbm/combobox/";
    var effects = null;
     // variables for recursion
    var depth = 0; // for getting depth of tree
    var selectedItem // for getting selected node object;
    var cascade_combos_data = "";
        
    var values = (YAHOO.lang.isUndefined(wargs.value)) ? "[{label: 'Select One', value: '-1', selected: false}]" : wargs.value;
    var size = (YAHOO.lang.isUndefined(wargs.args.size)) ? 1 : wargs.args.size;
    var readonly = (YAHOO.lang.isUndefined(wargs.args.readonly)) ? false : wargs.args.readonly;
    var disabled = (YAHOO.lang.isUndefined(wargs.args.disabled)) ? false : wargs.args.disabled;
    disabled = readonly || disabled;
    // swapped attribute is introduced for supporting VM issue with a bug fix that uses keys for choices
    var swapped = (YAHOO.lang.isUndefined(wargs.args.swapped)) ? false : wargs.args.swapped;
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
	   
	   
    
    var cascade = (YAHOO.lang.isUndefined(wargs.args.cascade)) ? null : wargs.args.cascade;
    var level = (YAHOO.lang.isUndefined(wargs.args.level)) ? 0 : wargs.args.level;
    var validationObject = null;    
    
    
    if(wargs.args.service){
           jmaki.doAjax({ url : wargs.args.service, method : 'POST', 
                 callback: function(_req){
                      var rows = _req.responseText ;
                      if(cascade == true) {
                          cascade_combos_data = eval("("+rows+")");
                          cascade_combos_data = cascade_combos_data.root;
                          getLevels(cascade_combos_data,0); // sets the depth attribute of the widget
                      }
                      else init(rows);
	                              
                      if(cascade != false && level == 0) {
	                    var choices = getChoices("");
	                    init(choices);
	              } else if (cascade != false && level != 0){
	                    choices = "[{'value':'-1','selected':true,'label':'No options available'}]"
                            init(choices);
                      }}});
   
     } else init(values); // render combobox      
     
     // initializes widget with given value
     function init(_v) {
	     if(YAHOO.lang.isUndefined(_v)) return;  
             if (typeof _v != 'object') _v = eval(_v);
             
             var widgetContent = "<select style='width:100px' class='InptSelect' title='"+toolTip+"' id = '" + uuid +"' tabindex= '"+tabOrder+"' onChange= \"jmaki.attributes.get('" + uuid + "').onChange()\" onBlur= \"jmaki.attributes.get('" + uuid + "').onBlur()\" ";
             //widgetContent += (" size='" + size+"'");
             widgetContent += " onFocus= \"jmaki.attributes.get('" + uuid + "').onFocus()\"";
	     if(disabled) widgetContent += (" disabled='" + disabled+"'");
	     widgetContent += (">");
	     
             for(var i=0; i < _v.length; i++) {
                  if(YAHOO.lang.isUndefined(_v[i])) {
				  sbm.widgets.showMsg('Error','JSON value of \''+uuid+'\' widget is not well defined!');
			          break;
		  }
		  if (swapped == false)
             {
		  var _val = (YAHOO.lang.isUndefined(_v[i].value)) ? '' :  _v[i].value;
                  var _label =(YAHOO.lang.isUndefined(_v[i].label)) ? '' : _v[i].label;
              }
             else
             {
		  var _val = (YAHOO.lang.isUndefined(_v[i].label)) ? '' :  _v[i].value;
                  var _label =(YAHOO.lang.isUndefined(_v[i].value)) ? '' : _v[i].label;
             }    

		  if(YAHOO.lang.isUndefined(_label) || _label.length == 0)
			   _label = _val;
                  var _selected = (YAHOO.lang.isUndefined(_v[i]) || YAHOO.lang.isUndefined(_v[i].selected)) ? false : _v[i].selected;
                  //alert("_label:"+_label+"\n_val:"+_val+"\n_selected:"+_selected);
                  if(!YAHOO.lang.isUndefined(_val) && !YAHOO.lang.isUndefined(_label)){
                      	   if(_val.length != 0 && _label.length != 0)
		               widgetContent = widgetContent + "<option value='" + _val + "' " + _selected + "label='"+_label+"'>"+_label+"</option>";
                  }
             }
	     
             widgetContent += "</select>";
             if(validationType != "none") {
		      widgetContent += "<span class='selectRequiredMsg'>Please make a selection.</span>";
		      widgetContent += "<span class='selectInvalidMsg'>"+selectInvalidMsg+"</span>";
	     }
             // set widget presentation in the div defined in the widget template file
             var mydiv = document.getElementById(uuid+"_div");
             mydiv.innerHTML = widgetContent;
	     if(validationType != "none"){
		    var vOptions = {};
		    if(!YAHOO.lang.isUndefined(wargs.args.validation['validateOn']))
			  vOptions['validateOn'] = wargs.args.validation['validateOn'];
		    if(!YAHOO.lang.isUndefined(wargs.args.validation['isRequired']) 
		         && wargs.args.validation['isRequired'])
			  vOptions['isRequired'] = true;
	            vOptions['invalidValue'] = (YAHOO.lang.isUndefined(wargs.args.validation['invalidValue']) ? '-1' :wargs.args.validation['invalidValue'] );	
		    if(validationObject == null) {
		       //alert(YAHOO.lang.JSON.stringify(vOptions));	    
		       validationObject = new Spry.Widget.ValidationSelect(uuid+"_div", vOptions);    
		    }
	     }
	     if(effects != null){ 
	           sbm.utils.applyEffects(uuid+"_div", effects);       
	     }
    }
    
    // sets the value of widget 
    this.setValue = function(_v) {
	    if(!YAHOO.lang.isUndefined(_v) && typeof eval(_v) != 'undefined' ) {    
                   if (typeof _v != 'object') _v = eval('(' + _v + ')');
		   var selectElem = document.getElementById(uuid);
		   for(var i=0; i<selectElem.options.length;i++){
			   selectElem.remove(i);			   
		   }
		   
		   for(var i=0; i < _v.length; i++) 
		   {
			 if(YAHOO.lang.isUndefined(_v[i])) {
				  sbm.widgets.showMsg('Error','JSON value of \''+uuid+'\' widget is not well defined!');
			          break;
		         }
              if (swapped == false)
                 {
			 var _value = _v[i].value;
                         var _label = _v[i].label;
                 }
                 else
                 {
			 var _value = _v[i].label;
                         var _label = _v[i].value;
                 }

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

   this.getValue = function() {         	    
        var el = document.getElementById(uuid);        
        var selIndex = el.selectedIndex;
        var list = new Array();
                 
        for(i=0;i< el.options.length;i++) {
            var option = {};	
            //if(el.options[i].value != '-1'){
            option['label'] = el.options[i].text;
            option['value'] = el.options[i].value            
            if(el.options[i].selected) option['selected'] = true;
            list[list.length] = option;
            //}
           
        } 
        //alert(YAHOO.lang.JSON.stringify(list));       
	return YAHOO.lang.JSON.stringify(list);       
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
        
    this.getSelectedValues = function(){
        var selectedValues = new Array();
        var selectElement = document.getElementById(uuid);
        for(var i=0; i< selectElement.options.length;i++){     
            if(selectElement.options[i].selected) 
                 selectedValues[selectedValues.length] = selectElement.options[i].value;   
        }
        return selectedValues;        
    }
    
    this.setSelectedValue = function(selectedValue) {
        if(selectedValue.length == 0) return; 
        var selectElement = document.getElementById(uuid);
        for(var j=0; j< selectElement.options.length;j++) 
            selectElement.options[j].selected = false;
        for(var j=0; j< selectElement.options.length;j++){     
                  if(selectElement.options[j].value == selectedValue) {
                      selectElement.options[j].selected = true;
                  }                
        }     
        this.updateDependent();
    }
        
    this.getSelectedValue = function(){
        var selectedValue = '';
	var selectElement = document.getElementById(uuid);
        for(var i=0; i< selectElement.options.length;i++){     
            if(selectElement.options[i].selected) 
                 selectedValue = selectElement.options[i].value;   
        }
        return selectedValue;        
    }
    
    
    function getChoices(selectedNode)
    {
       jmaki.log("getChoices " + selectedNode);
       if (selectedNode == "")
          return formatNode(cascade_combos_data.children);
       jmaki.log("selected Node before call to findNode " + selectedNode);
       findNode(cascade_combos_data, selectedNode);
       jmaki.log("Node is " + selectedItem.title + "name is " + selectedNode);
       return formatNode(selectedItem.children);
    }
    
    function findNode(root, value)
    {  
       if (root.children == undefined)
          return;
       for (var i=0; i<root.children.length; i++)
         {
         if (root.children[i].value == value)
            {
            jmaki.log("root.children[i].value " + root.children[i].value);
            selectedItem = root.children[i];
            }
         // if (root.children[i] != undefined)
         findNode(root.children[i], value)
         }     
    }
    
    function formatNode(node)
    {
    if(YAHOO.lang.isUndefined(node)) return;
    var output = "[";    
    for(var i=0;i<node.length; i++)
    {
	 output = output+" {label:'"+node[i].title+"', value:'"+node[i].value+"'}"; 
	 if(i != (node.length -1)) output = output + ", ";    
    }
    output = output + "]";
    output = output.replace(",]","]");    
    return output;       
    }
 
    function getLevels(root, level) {  
       if (root.children == undefined){
          depth = level-1;
          return;
       }
       for (var i=0; i<root.children.length; i++) {
         	getLevels(root.children[i], level+1)
       }     
    }
        
    function getLevels_org() {
        // Assumes the tree is balanced and traverses the first child of every children node
        var depth = 0;
        var children = cascade_combos_data.children;
        while (children != undefined)
           {
            children = children[0].children;
            depth = depth + 1; 
           }
        return depth - 1; // the first level is actually level 0
    }
       
    function getWidgets(service_name, level, oper) {
      var widgets = jmaki.attributes.keys();
      var widget = "";
      var list = new Array();
      list = [];
      for (i=0; i<jmaki.attributes.keys().length; i++)
	      {
	      widget = widgets[i];
	      widgetObj = jmaki.getWidget(widget);
	      if (widgetObj instanceof jmaki.widgets.sbm.combobox.Widget) 
	         {
	         if (oper == "==" && widgetObj.getLevel() == level && widgetObj.getService() == service_name)
	           list.push(widget);
	         else if (oper == ">" && widgetObj.getLevel() > level && widgetObj.getService() == service_name)
	           list.push(widget);
	         }
	      }
	  jmaki.log("widget at level " + level + " " + list);
	  return list;
    } 
    
    this.getLevel = function() {
    return level;
    }
    
    this.getService = function() {
    return wargs.service;
    }
    
    this.getValues = function() {
        var elements = document.getElementById(uuid);
        var output = "";
        for(i=0;i<elements.length;i++) {
            if(elements.options[i].selected == true) {
                    output = elements.options[i].value; 
            }
        }
        return output;
    }

    this.addOptions = function(_opts) {
        sbm.util.addOptions(uuid, _opts, 'value', 'label');
    } 

    this.removeOptions = function() {
        sbm.util.removeOptions(uuid);
    }

    this.removeAllOptions = function() {
        sbm.util.removeAllOptions(uuid);
    }

    this.validate = function() {
        if (validation_type != "none") {
           var validation_obj = new Spry.Widget.ValidationSelect(uuid+"_div", validationOptions);
           return validation_obj.validate();
        }
        return true;
    }

    this.updateDependent = function() {       
        if(cascade == false)return;
        else
        {
            var selectedValue = this.getValues();
	    if(level < depth)
	    {
	            var widgets = getWidgets(wargs.service, level + 1, "==");
	            for (var i=0; i<widgets.length; i++){
	                jmaki.getWidget(widgets[i]).removeAllOptions();
			if(selectedValue == '-1')
			 jmaki.getWidget(widgets[i]).setValue([{'value':'-1','selected':true,'label':'No options available'}]);
			else jmaki.getWidget(widgets[i]).setValue(getChoices(selectedValue));
		    }
	            widgets = getWidgets(wargs.service, level + 1, ">");
	            for (var i=0; i<widgets.length; i++) {
			jmaki.getWidget(widgets[i]).removeAllOptions();    
	                jmaki.getWidget(widgets[i]).setValue([{'value':'-1','selected':true,'label':'No options available'}]);
		    }
	   }
	   else return;
       }
    }
    
    // published events
    this.onChange = function() { jmaki.publish(topic + "onChange", {widgetId: wargs.uuid, value: this.getValue()}); this.updateDependent();}
    this.onFocus = function() { jmaki.publish(topic + "onFocus", {widgetId: wargs.uuid, value: this.getValue()}); }
    this.onBlur = function() { jmaki.publish(topic + "onBlur", {widgetId: wargs.uuid, value: this.getValue()}); }    
}
