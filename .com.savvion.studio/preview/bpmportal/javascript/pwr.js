
function getRelativeUrl() {
    var stringUrl = "'"+self.location+"'";
    if (stringUrl.indexOf('/webapp/') >= 0){
        return "../../../../dwr";
    }
    else if (stringUrl.indexOf('/ebmsapps/') >= 0){
        return "../../../dwr";
    } else if (stringUrl.indexOf('/bpmportal/') >= 0 || stringUrl.indexOf('/BizSolo/') >= 0){
        return "../../dwr";
    }

}


function pwr() { }
function sbm() { }

/*
    BPM Portal specific dwr functions.
*/

pwr.removePakBizSoloBeanFromCache = function(p0, callback)
{
    DWREngine._execute(getRelativeUrl(), 'PAKBizSoloBean', 'removePAKBizSoloBeanFromCache', p0, callback);
}

pwr.isDuplicateFavorite = function(p0, p1, callback)
{
    DWREngine._execute(getRelativeUrl(), 'QueryManager', 'isDuplicateFavorite', p0, p1, callback);
}

pwr.isValidGroup = function(p0, callback) 
{
    DWREngine._execute(getRelativeUrl(), 'GrpValidator', 'isValidGroup', p0, callback);
}

pwr.getProcessTemplateWorksteps = function(p0, p1, callback)
{
    
    DWREngine._execute(getRelativeUrl(), 'pwr', 'getProcessTemplateWorksteps', p0, p1, callback);
}

pwr.getProcessList = function(callback)
{
    DWREngine._execute(getRelativeUrl(), 'Util', 'getProcessList', callback);
}

pwr.sendRequest = function(url, method, params) 
{
    var method1 = method;
    if (method1 == null)
        method1 = 'get';

    if (method1 == 'post')
        url += '?'+params;
    
    var ajax = new Ajax.Request(url,{method: method1,parameters: params, postBody:params});
}
pwr.loadPage = function(url, params, idToUpdate, method, failureJS,completeJS,evalscripts,asynchronous)
{
    var method1 = method;
    if (method1 == null)
        method1 = 'get';
    
    var evaluateScripts = evalscripts;
    if(evaluateScripts == null)
        evaluateScripts = false;

    var async = asynchronous;
    if(async == null)
        async = true;
        
    if (method1 == 'post')
        url += '?'+params;
    	
	if(method1 == 'post'){
				function hasInputChild(elem){
				     var status = false;
					 if(elem.dom.getElementsByTagName('input').length == 1)
					        status = true;
					return status;
					 
				}
                if(typeof params == 'undefined') params = '';
				var northDiv = Ext.get('northDiv');									
				if (northDiv && hasInputChild(northDiv)) {
                    var elem = northDiv.dom.getElementsByTagName('input')[0]				
					var tokenName = elem.getAttribute('name')
					var tokenValue = elem.getAttribute('value');
					if(typeof tokenName != 'undefined' && typeof tokenValue != 'undefined') {
						if(params.length > 0) params = params + '&';
						params = params + encodeURIComponent(tokenName) + "=" + encodeURIComponent(tokenValue);						
					}
					
				}else {								   
					var c = document.cookie + ";",					
					re = /\s?(.*?)=(.*?);/g, matches, name,value;					
					while((matches = re.exec(c)) != null){
						name = matches[1];
						value = matches[2];
            
						if(name == 'mmnnuull-00'){			  
							var tokens = value.split('=');
							if(params.length > 0) params = params + '&'; 
							params = params +  encodeURIComponent(tokens[0]) + "=" + encodeURIComponent(tokens[1]);
							
						}	
					}					
																
				}
	
	}
	
    var ajax = new Ajax.Updater({success: idToUpdate}, url, {method: method1, parameters: params, onFailure: failureJS, onComplete:completeJS, evalScripts:evaluateScripts, asynchronous:async});
}

/*
    Wrapper for Script.aculo.us effects
*/

pwr.effect = Effect;

/*
    Wrapper for dwr enabled server side Apache Commons Validator validations.
*/

pwr.validation = function validation() {}
pwr.validation.isCreditCard = function(id, callback)
{
    var value = DWRUtil.getValue(id);
    DWREngine._execute(getRelativeUrl(), 'GenericValidator', 'isCreditCard', value, callback);
}

pwr.validation.isEmail = function(id, callback)
{
    var value = DWRUtil.getValue(id);
    DWREngine._execute(getRelativeUrl(), 'GenericValidator', 'isEmail', value, callback);
}

pwr.validation.isFloat = function(id, callback)
{
    var value = DWRUtil.getValue(id);
    DWREngine._execute(getRelativeUrl(), 'GenericValidator', 'isFloat', value, callback);
}

pwr.validation.isInRange = function(id, min, max, callback)
{
    var value = DWRUtil.getValue(id);
    DWREngine._execute(getRelativeUrl(), 'GenericValidator', 'isInRange', value, min, max, callback);
}

pwr.validation.isUrl = function(id, callback)
{
    var value = DWRUtil.getValue(id);
    DWREngine._execute(getRelativeUrl(), 'GenericValidator', 'isUrl', value, callback);
}

pwr.validation.matchRegexp = function(id, regexp, callback)
{
    var value = DWRUtil.getValue(id);
    DWREngine._execute(getRelativeUrl(), 'GenericValidator', 'matchRegexp', value, regexp, callback);
}

pwr.validation.maxLength = function(id, max, callback)
{
    var value = DWRUtil.getValue(id);
    DWREngine._execute(getRelativeUrl(), 'GenericValidator', 'maxLength', value, max, callback);
}

pwr.validation.minLength = function(id, min, callback)
{
    var value = DWRUtil.getValue(id);
    DWREngine._execute(getRelativeUrl(), 'GenericValidator', 'minLength', value, min, callback);
}

/*
    Wrapper for dwr utilities.
*/

pwr.util = function util() {}
pwr.util.getField = function(id)
{
    return document.getElementById(id);
}

pwr.util.getText = function(element)
{
    return DWRUtil.getText(element);
}

pwr.util.getValue = function(element)
{
    var elems = document.getElementsByTagName('input');
        for(var i=0; i< elems.length;i++) {
            if(elems[i].getAttribute('name') == element){
               var id = elems[i].getAttribute('id');			   
			   return DWRUtil.getValue(id );
			   
            }
    }	
	return DWRUtil.getValue(element);
}
pwr.util.findElementIdByName = function(element)
{
    var elems = document.getElementsByTagName('input');
        for(var i=0; i< elems.length;i++) {
            if(elems[i].getAttribute('name') == element)	   			   
			   return elems[i].getAttribute('id');            
    }	
}

pwr.util.findElementByName = function(type,element)
{
    var elems;
	var elem = null;
    if(type == 'input') elems = document.getElementsByTagName('input');
	else if(type == 'select') elems = document.getElementsByTagName('select');
	for(var i=0; i< elems.length;i++) {
            if(elems[i].getAttribute('name') == element) {               
			   elem = elems[i];
               break;			   
            }			   
    }
    	
	return elem;
}

pwr.util.getValues = function(element)
{
    var values = new Array();
    var select = pwr.util.getField(element);
    for (var i = 0; i < select.options.length; i++)
        if (select.options[i].selected)
          values[values.length] = select.options[i].value;
    return values;
}

pwr.util.hide = function(element)
{
    //DWRUtil.hideById(element);
	YAHOO.util.Dom.setStyle(element,'display','none');
}

pwr.util.setEnabled = function(element, state)
{
    //DWRUtil.setEnabled(element, state);
    // This method has been derecated from old version of DWRUtil
    //------------------------------------------------------------------//
    var orig = element;
        element = $(element);
        if (element == null)
        {
           alert("setEnabled() can't find an element with id: " + orig + ".");
           return;
        }

        // If we want to get funky and disable divs and spans by changing the font
        // colour or something then we might want to check the element type before
        // we make assumptions, but in the mean time ...
        // if (DWRUtil.isHTMLElement(ele, "input")) { ... }
        element.disabled = !state;
        element.readonly = !state;
        if (DWRUtil._isIE)
        {
            if (state)
            {
                element.style.backgroundColor = "White";
            }
            else
            {
                // This is WRONG but the hack will do for now.
                element.style.backgroundColor = "Scrollbar";
            }
        }
}

pwr.util.setValue = function(element, value)
{
    var elems = document.getElementsByTagName('input');
    for(var i=0; i< elems.length;i++) {
            if(elems[i].getAttribute('name') == element){
               var id = elems[i].getAttribute('id');			   
			   DWRUtil.setValue(id, value);
			   return;			   
            }
    }
	DWRUtil.setValue(element, value);
}

pwr.util.show = function(element)
{
    DWRUtil.showById(element);
}

pwr.util.addOptions = function(element, values, reverseMappingOrValueProperty, textValueProperty)
{
    DWRUtil.addOptions(element, values, reverseMappingOrValueProperty, textValueProperty);
}

pwr.util.removeOptions = function(element, indexes)
{
    var select = pwr.util.getField(element);
    if (indexes != null && indexes.length > 0)
    {
        for (var i = indexes.length-1; i >= 0; i--)
        {
            select.remove(indexes[i]);
        }
        return;
    }
    for (var i = select.options.length-1; i >= 0; i--)
    {
        if (select.options[i].selected)
            select.remove(i);
    }
}

pwr.util.removeAllOptions = function(element)
{
    DWRUtil.removeAllOptions(element);
}

function $N(element) {
  if (arguments.length > 1) {
    for (var i = 0, elements = [], length = arguments.length; i < length; i++)
      elements.push($N(arguments[i]));
    return elements;
  }
  if (typeof element == 'string')
    element = document.getElementsByName(element);
  return Element.extend(element);
}


pwr.getProcessInstanceList = function(ptid, piidFragment, callback)
{
    DWREngine._execute('../../dwr', 'pwr', 'getProcessInstanceList', ptid, piidFragment, callback);
}

pwr.Autocompleter = Class.create();
pwr.Autocompleter.prototype = Object.extend(new Autocompleter.Base(), {
    initialize: function(element, update, DWRFunction, onComplete, options) {
        this.baseInitialize(element, update, options);
        this.DWRFunction           = DWRFunction;
        this.onComplete            = onComplete;
        this.options               = options || {}; // none yet
    },
    getUpdatedChoices: function() {
        var elementValue = pwr.util.getValue(this.element);
        var lastIndex = elementValue.lastIndexOf(',');
        if (lastIndex != -1) {
            elementValue = elementValue.substring(lastIndex+1);
            elementValue = elementValue.replace(/ /,"");
        }
        this.DWRFunction(pwr.util.getValue(this.element),this.onCompleteFunction());
    },
    onCompleteFunction: function() {
        var t=this;
        return function(data) {
            // Do some DWR range selection.
            // This will not work with tokenised autocompletion,
            // so is not really recommended. But I like it
            t.updateChoices(t.makeUnorderedList(data));
        }
    },
    makeUnorderedList: function(data) {
        var html="<ul>";
        for (var i=0;i<data.length;i++) {
            html += "<li>"+data[i]+"</li>";
        }
        html += "</ul>";
        return html;
    }
});

pwr.SourceAutocompleter = Class.create();
pwr.SourceAutocompleter.prototype = Object.extend(new Autocompleter.Base(), {
    initialize: function(sourceElement, element, update, DWRFunction, onComplete, options) {
        this.baseInitialize(element, update, options);
        this.DWRFunction           = DWRFunction;
        this.sourceElement         = sourceElement;
        this.onComplete            = onComplete;
        this.options               = options || {}; // none yet
    },
    getUpdatedChoices: function() {
        var elementValue = pwr.util.getValue(this.element);
        var lastIndex = elementValue.lastIndexOf(',');
        if (lastIndex != -1) {
            elementValue = elementValue.substring(lastIndex+1);
            elementValue = elementValue.replace(/ /,"");
        }
        this.DWRFunction(pwr.util.getValue(this.sourceElement), elementValue, this.onCompleteFunction());
    },
    onCompleteFunction: function() {
        var t=this;
        return function(data) {
            // Do some DWR range selection.
            // This will not work with tokenised autocompletion,
            // so is not really recommended. But I like it
            var results = t.makeUnorderedList(data);
            t.updateChoices(results);
        }
    },
    makeUnorderedList: function(data) {
        var html="<ul>";
        for (var i=0;i<data.length;i++) {
            html += "<li>"+data[i]+"</li>";
        }
        html += "</ul>";
        return html;
    }
});
sbm.util = pwr.util;
sbm.adaplet = function adaplet() {};
sbm.validation = pwr.validation;
sbm.effects = pwr.effect;


