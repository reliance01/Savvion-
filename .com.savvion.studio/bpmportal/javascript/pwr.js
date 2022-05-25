
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
    DWREngine._execute(getRelativeUrl(), 'PAKBizSoloBean', 'removePAKBizSoloBeanFromCache', callback,p0);
}

pwr.isDuplicateFavorite = function(p0, p1, callback)
{
    DWREngine._execute(getRelativeUrl(), 'QueryManager', 'isDuplicateFavorite', callback, p0, p1);
}

pwr.isValidGroup = function(p0, callback) 
{
    DWREngine._execute(getRelativeUrl(), 'GrpValidator', 'isValidGroup', callback, p0);
}

pwr.getProcessTemplateWorksteps = function(p0, p1, callback)
{
    
    DWREngine._execute(getRelativeUrl(), 'pwr', 'getProcessTemplateWorksteps', callback, p0, p1);
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
    DWREngine._execute(getRelativeUrl(), 'GenericValidator', 'isCreditCard', callback, value);
}

pwr.validation.isEmail = function(id, callback)
{
    var value = DWRUtil.getValue(id);
    DWREngine._execute(getRelativeUrl(), 'GenericValidator', 'isEmail', callback, value);
}

pwr.validation.isFloat = function(id, callback)
{
    var value = DWRUtil.getValue(id);
    DWREngine._execute(getRelativeUrl(), 'GenericValidator', 'isFloat', callback, value);
}

pwr.validation.isInRange = function(id, min, max, callback)
{
    var value = DWRUtil.getValue(id);
    DWREngine._execute(getRelativeUrl(), 'GenericValidator', 'isInRange', callback, value, min, max);
}

pwr.validation.isUrl = function(id, callback)
{
    var value = DWRUtil.getValue(id);
    DWREngine._execute(getRelativeUrl(), 'GenericValidator', 'isUrl', callback, value);
}

pwr.validation.matchRegexp = function(id, regexp, callback)
{
    var value = DWRUtil.getValue(id);
    DWREngine._execute(getRelativeUrl(), 'GenericValidator', 'matchRegexp', callback, value, regexp);
}

pwr.validation.maxLength = function(id, max, callback)
{
    var value = DWRUtil.getValue(id);
    DWREngine._execute(getRelativeUrl(), 'GenericValidator', 'maxLength', callback, value, max);
}

pwr.validation.minLength = function(id, min, callback)
{
    var value = DWRUtil.getValue(id);
    DWREngine._execute(getRelativeUrl(), 'GenericValidator', 'minLength', callback, value, min);
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
    return DWRUtil.getValue(element);
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
    DWRUtil.hideById(element);
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
    DWREngine._execute('../../dwr', 'pwr', 'getProcessInstanceList', callback, ptid, piidFragment);
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
sbm.effect = pwr.effect;


