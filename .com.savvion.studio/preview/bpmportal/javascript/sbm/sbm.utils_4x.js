var bm;
var clickedButton = 'none';

if(!sbm) sbm = {};
sbm.utils = sbm.utils || {};
sbm.widgets = sbm.widgets || {};
Ext.namespace("Bm.util");
Ext.namespace("Bm.validation");
Ext.namespace("BM.Portal");
Ext.namespace("BM.widget");

/**
 *     This function execute the fValidate validation with some other resource validation.
 *     vType specifies the fValidation mode box, label or alert
 */
sbm.utils.beforeFormSubmit = function(vType){
     //------------------------------------------------------------------------------------------------------//
     //    fValidate validation for forms
     //------------------------------------------------------------------------------------------------------//
     
     var errorMode = 4;
     if((vType == undefined) || (vType == ''))
         errorMode = 14;//default setting is box,alert
     if(vType == 'box') errorMode = 4;
     else if(vType == 'label') errorMode = 2;
     else if(vType == 'box+label') errorMode = 10;
     else if(vType == 'alert') errorMode = 0;
     else if(vType == 'alert+label') errorMode = 11;
     else if(vType == 'alert+box+label') errorMode = 28;
     else if(vType == 'alert+box') errorMode = 14;
     
     // do not validate in save, assign or reassign
     if(clickedButton.toLowerCase() != "bizsite_reassigntask" && clickedButton.toLowerCase() != "bizsite_skip")
     {
         // fValidate validation    
         if(!sbm.pValidate(document.forms[0],false,false,false,true,errorMode))
         {
             //if(errorMode == 4)
             //Effect.Grow("errors",{direction:'top-left',duration:3});
             //Effect.Appear("errors",{duration:2});
             return false;
        }
     } 
     
     //-------------------------------------------------------------------------------------------------------//
     //   validation for list dataslot
     //   list dataslot related
     //--------------------------------------------------------------------------------------------------------//
     for(var k=0;k<document.forms[ 0 ].elements.length;k++) {
        if(document.forms[ 0 ].elements[k].name != undefined){  
            if(document.forms[ 0 ].elements[k].name.indexOf("_dyn_val_") != -1) {
                var index = document.forms[ 0 ].elements[k].name.indexOf("_dyn_val_");
                var dsName = document.forms[ 0 ].elements[k].name.substring(9); //9 is length of _dyn_val_
                var dsCtrl = eval("document.forms[ 0 ]."+dsName);
                for(var j=0; j < dsCtrl.length;j++) {
                    dsCtrl.options[j].selected = true;
                }
            }
        }
      }
     
     
     //------------------------------------------------------------------------------------------------------///
     //   resource validation keep this separate from form validation
     //   resource validation happens after form validation //
     ///------------------------------------------------------------------------------------------------------///
     if(clickedButton.toLowerCase() != "bizsite_savetask" && clickedButton.toLowerCase() != "bizsite_reassigntask" && clickedButton.toLowerCase() != "bizsite_skip")
     {
         if(!sbm.utils.validateResources(document.forms[0]))              
              return false;
     }
     
     //-------------------------------------------------------------------------------------------------------///
     //   upload document and xml dataslots...
     //-------------------------------------------------------------------------------------------------------///
     if(!eval(upload()))
     {
             alert("document upload has been failed...");
         return false;
     }
     
     //--------------------------------------------------------------------------------------------------------///
     //  validation must end here...
     //--------------------------------------------------------------------------------------------------------///
     return true;
    
}
//----------------------------------------------------------------------------------------------------------//
/**
 *     This function is executed while form submit and it is used in EXT JS 4.x forms
 *     
 */
sbm.utils.handleFormSubmit = function(){
     //------------------------------------------------------------------------------------------------------//
     //    fValidate validation for forms
     //------------------------------------------------------------------------------------------------------//
     
     //-------------------------------------------------------------------------------------------------------//
     //   validation for list dataslot
     //   list dataslot related
     //--------------------------------------------------------------------------------------------------------//
     for(var k=0;k<document.forms[ 0 ].elements.length;k++) {
        if(document.forms[ 0 ].elements[k].name != undefined){  
            if(document.forms[ 0 ].elements[k].name.indexOf("_dyn_val_") != -1) {
                var index = document.forms[ 0 ].elements[k].name.indexOf("_dyn_val_");
                var dsName = document.forms[ 0 ].elements[k].name.substring(9); //9 is length of _dyn_val_
                var dsCtrl = eval("document.forms[ 0 ]."+dsName);
                for(var j=0; j < dsCtrl.length;j++) {
                    dsCtrl.options[j].selected = true;
                }
            }
        }
      }
     
     
     //------------------------------------------------------------------------------------------------------///
     //   resource validation keep this separate from form validation
     //   resource validation happens after form validation //
     ///------------------------------------------------------------------------------------------------------///
     if(clickedButton.toLowerCase() != "bizsite_savetask" && clickedButton.toLowerCase() != "bizsite_reassigntask" && clickedButton.toLowerCase() != "bizsite_skip")
     {
         if(!sbm.utils.validateResources(document.forms[0]))              
              return false;
     }
     
     //-------------------------------------------------------------------------------------------------------///
     //   upload document and xml dataslots...
     //-------------------------------------------------------------------------------------------------------///
     if(!eval(upload()))
     {
             alert("document upload has been failed...");
         return false;
     }
     
     //--------------------------------------------------------------------------------------------------------///
     //  validation must end here...
     //--------------------------------------------------------------------------------------------------------///
     var createBtn = Ext.getCmp('bizsite_completeTask_CREATE_LABEL_id');    
     if(createBtn != null && createBtn != "undefined"){
        createBtn.disable();
     }
     var completeBtn = Ext.getCmp('bizsite_completeTask_COMPLETE_LABEL_id');
     if(completeBtn != null && completeBtn != "undefined"){
        completeBtn.disable();   
     }     
     return true;
    
}




//----------------------------------------------------------------------------------------------------------//
function addFiid(arg) {
   try {
     var fiidv = document.form.fiid.value;
     if (arg.indexOf('?') > 0)
        arg += '&';
     else
       arg += '?';
     arg += 'fiid='+encodeURIComponent(fiidv);
  } catch(e) {
  }

  return arg;
}
function MM_goToURL() { //v3.0
  
  var i, args=MM_goToURL.arguments; document.MM_returnValue = false;
  for (i=0; i<(args.length-1); i+=2) eval(args[i]+".location='"+addFiid(args[i+1])+"'");
}


function goToCreateCollaboration()
{      
	alert("Action not supported in preview mode");
}

function goToViewCollaboration()
{
	alert("Action not supported in preview mode");
}

function MM_openBrWindow(theURL,winName,features) { 
  window.open(theURL,winName,features);
}

function goToEmail()
{
    alert("Action not supported in preview mode");
}

function goToChat()
{
	alert("Action not supported in preview mode");
}


function goToNotes()
{
	alert("Action not supported in preview mode");
}

/**
 * Used to set the workstep priority.  This is mainly used for the start workstep.
 */
sbm.utils.setPriority = function(elemId,priority){  
    var selectEl = document.getElementById(elemId);
    for(var i=0;i<selectEl.options.length;i++){             
        if(selectEl.options[i].value == priority) {
            selectEl.options[i].selected = true;
            break;
        }
    }    
}

//---------------------------------------------------------------------------------------------------------//
//   resources validation function
//---------------------------------------------------------------------------------------------------------//
sbm.utils.validateResources = function(formElem)
{
    var apt_days = 0;
     var apt_hrs = 0;
     var apt_mins = 0;
     var apt = 0;
     var msg= "";
     var invalid   = new Array();
     var nofocus   = new Array();
     var isValid = true;
         
     for(var k=0;k<document.forms[ 0 ].elements.length;k++) {
         if(document.forms[ 0 ].elements[k].name != undefined){     
             if(document.forms[ 0 ].elements[k].name.indexOf("resources.") != -1 && document.forms[ 0 ].elements[ k ].name == name) {
                 if(document.forms[ 0 ].elements[ k ].value == '') {
                     fieldValid = false;
                     invalid[ invalid.length ] = name;
                 }
             }
             if((document.forms[ 0 ].elements[ k ].name == "APT.days" || document.forms[ 0 ].elements[ k ].name == "bizsite_ActualProcessingTime_days") && parseInt(document.forms[ 0 ].elements[ k ].value) > 0) {
                 apt_days=parseInt(document.forms[ 0 ].elements[ k ].value)*24;
             }
             if((document.forms[ 0 ].elements[ k ].name == "APT.hours" || document.forms[ 0 ].elements[ k ].name == "bizsite_ActualProcessingTime_hours") && parseInt(document.forms[ 0 ].elements[ k ].value) > 0) {
                 apt_hrs=parseInt(document.forms[ 0 ].elements[ k ].value);
             }
             if((document.forms[ 0 ].elements[ k ].name == "bizsite_ActualProcessingTime_minutes" || document.forms[ 0 ].elements[ k ].name == "APT.mins") && parseInt(document.forms[ 0 ].elements[ k ].value) >= 0) {
                 apt_mins=parseInt(document.forms[ 0 ].elements[ k ].value)/60;
             }
             apt=apt_days+apt_hrs+apt_mins;
         }
     }
     
     for(var k=0;k<document.forms[ 0 ].elements.length;k++) {
         if ((document.forms[ 0 ].elements[k] != undefined) && (document.forms[ 0 ].elements[k].name != undefined)){
             if(document.forms[ 0 ].elements[k].name.indexOf("resources.ActualProcessingTime") != -1) {
                 document.forms[ 0 ].elements[k].value = apt;
                 if(apt <= 0) {
                     fieldValid = false;
                     invalid[ invalid.length ] = 'resources.ActualProcessingTime';
                     nofocus[ nofocus.length ] = 'resources.ActualProcessingTime';
                     isValid = false;
                     break;
                 }
             }
         }
     }
     
         if(!isValid && invalid.length > 0 ) 
     {
         msg += "The following fields are invalid:\n\n";
         for( i = 0; i < invalid.length; i++ )
         {
                if(invalid[ i ] != null && invalid[ i ].indexOf("resources.") != -1 ) 
            {
                 if(invalid[ i ].substring(10) == "ActualProcessingTime") 
                     msg += "   - " + "Work Time" + "\n";
                 else
                     msg += "   - " + invalid[ i ].substring(10) + "\n";
            }
            else 
                 msg += "   - " + invalid[ i ] + "\n";          
         }
         msg += "\n\n";
         }
     
     if(!isValid)
     {
         alert(msg);
         return false;
     }
     else
         return true;   
}

sbm.utils.cancel = function(){
     
     if(document.forms[0].elements['bizsite_assigneeName'] != undefined) {
          return sbm.utils.redirectPage();          
          //self.parent.location.replace("/sbm/bpmportal/myhome/bizsite.task");
     } else if(document.forms[0].elements['bizsite_pagetype'] != undefined) {
          var pagetype = document.forms[0].elements['bizsite_pagetype'];
          if(pagetype.value == "start"){
            self.parent.location.replace(getContextPath()+"/bpmportal/myhome/bizsite.app.list");
        }else{
            return sbm.utils.redirectPage();
            //self.parent.location.replace("/sbm/bpmportal/myhome/bizsite.task");
        }                   
     } else {
          self.parent.location.replace(getContextPath()+"/bpmportal/myhome/bizsite.app.list");     
     }
     return false;
}       

sbm.utils.redirectPage = function(){
    if(! document.referrer.match("link=mgt_overview_tasks")){
        //close the tab if page opened on multi tab
        if(!window.parent.formCancel()) {
            return false;
        }
        self.parent.location.replace(getContextPath()+"/bpmportal/myhome/bizsite.task");
    }else{
        self.parent.location.replace(getContextPath()+"/bpmportal/management/tasks_overview.jsp");
    }
    return false;
}


sbm.utils.reset = function(){
     document.forms[0].reset(); 
     FormWidgetHandler.getInstance().resetValues();         
}


 

sbm.renderBusinessObject = function(dataslotName,commandAction){    
	$(dataslotName).innerHTML ="Loading...";
    var params = "dataslotName="+dataslotName+"&commandAction="+commandAction+'&fiid='+((document.getElementById('fiid') != null) ? document.getElementById('fiid').value : '')+'&extJsVer=4';
    sbm.utils.updateContent(dataslotName,'GET',getContextPath()+"/bpmportal/common/businessobjects/"+commandAction+".portal?"+params);    
}

sbm.editBusinessObject = function(dataslotName,index){
    $(dataslotName).innerHTML ="Loading...";
    var params = "dataslotName="+dataslotName+"&commandAction=editBusinessObject&index="+index+'&fiid='+Bm.form.Cache.getFiid()+'&extJsVer=4';
    sbm.utils.updateContent(dataslotName,'GET',getContextPath()+"/bpmportal/common/businessobjects/editBusinessObject.portal?"+ params);
}


sbm.deleteBusinessObject = function(dataslotName,index){
    $(dataslotName).innerHTML ="Loading...";
    var params = "dataslotName="+dataslotName+"&commandAction=deleteBusinessObject&index="+index+'&fiid='+Bm.form.Cache.getFiid();
    sbm.utils.updateContent(dataslotName,'GET',getContextPath()+"/bpmportal/common/businessobjects/deleteBusinessObject.portal?"+ params);
}

sbm.addBusinessObject = function(dataslotName){
    $(dataslotName).innerHTML ="Loading...";
    var params = "dataslotName="+dataslotName+"&commandAction=addBusinessObject"+'&fiid='+Bm.form.Cache.getFiid()+'&extJsVer=4';
    sbm.utils.updateContent(dataslotName,'GET',getContextPath()+"/bpmportal/common/businessobjects/addBusinessObject.portal?"+ params);
}

sbm.resetBusinessObject = function(dataslotName){
    $(dataslotName).innerHTML ="Loading...";
    var params = "dataslotName="+dataslotName+"&commandAction=resetBusinessObject"+'&fiid='+Bm.form.Cache.getFiid()+'&extJsVer=4';
    sbm.utils.updateContent(dataslotName,'GET',getContextPath()+"/bpmportal/common/businessobjects/resetBusinessObject.portal?"+ params);
}


sbm.utils.compare = function(field1,operator,field2){
     var elem1 = document.getElementById(field1);
     if(typeof elem1 == 'undefined' || elem1 == null){
            Ext.Msg.alert("Error","<p>Failed to get element for '"+field1+"' field.</p>");
            return;
     }
     var elem2 = document.getElementById(field2);
     if(typeof elem2 == 'undefined' || elem2 == null){
            Ext.Msg.alert("Error","<p>Failed to get element for '"+field2+"' field.</p>");
            return;
     }

     if(operator != '==' && operator != '!=' && operator != '<' &&  operator != '<=' && operator != '>' &&  operator != '>='){
            Ext.Msg.alert("Error","<p>Operation '"+operator+"' is not supported.</p><p>Supported operations are: '==', '!=', '<', '<=', '>' and '>='.</p>");
            return;
     }

     if(operator == '==') {       
          return (elem1.value == elem2.value);
     }else if(operator == '!='){
          return (elem1.value != elem2.value);  
     }else if(operator == '>'){
          return (parseInt(elem1.value) > parseInt(elem2.value));
     }else if(operator == '>='){
          return (parseInt(elem1.value) >= parseInt(elem2.value));
     }else if(operator == '<'){
          return (parseInt(elem1.value) < parseInt(elem2.value));
     }else if(operator == '<='){
          return (parseInt(elem1.value) <= parseInt(elem2.value));
     }else {
          Ext.Msg.alert("Error","<p>Operation '"+operator+"' is not supported.</p><p>Supported operations are: '==', '!=', '<', '<=', '>' and '>='.</p>");
          return;
     }
}

sbm.utils.compareConstant = function(field1,operator,constant){
      var elem1 = document.getElementById(field1);
     if(typeof elem1 == 'undefined' || elem1 == null){
            Ext.Msg.alert("Error","<p>Failed to get element by "+field1+" id.</p>");
            return;
     }
     
     if(operator != '==' && operator != '!=' && operator != '<' &&  operator != '<=' && operator != '>' &&  operator != '>='){
            Ext.Msg.alert("Error","<p>Operation '"+operator+"' is not supported.</p><p>Supported operations are: '==', '!=', '<', '<=', '>' and '>='.</p>");
            return;
     }

     if(operator == '==') {
          return (elem1.value == constant);
     }else if(operator == '!='){
          return (elem1.value != constant);  
     }else if(operator == '>'){
          return (parseInt(elem1.value) > parseInt(constant));
     }else if(operator == '>='){
          return (parseInt(elem1.value) >= parseInt(constant));
     }else if(operator == '<'){
          return (parseInt(elem1.value) < parseInt(constant));
     }else if(operator == '<='){
          return (parseInt(elem1.value) <= parseInt(constant));
     }
     return;
}

sbm.utils.addData = function(elementId,type,data){
    // adaplet callback for checkbox and radio
    // This function renders a group of checkboxes 
    // This function is the default callback adapter for control type of checkboxes 
    var element = document.getElementById(elementId);
    if(typeof element == 'undefined'){
            Ext.Msg.alert("Error","<p>Failed to get element for '"+elementId+"' element.</p>");
            return;
    }
    var content = '';
    if(type == 'checkbox'){
        for(var i=0; i< data.length;i++){
                 var checkbox =  '<input type="checkbox" class="ApInptChkBox" name="'+elementId+'" value="'+data[i]+'" id="'+data[i]+'">'+data[i]+'<br>\n';
                 content +=checkbox;
        }
   }else if(type == 'radio'){
       for(var i=0; i< data.length;i++){
           var radio =  '<input type="radio" class="ApInptRadio" name="'+elementId+'" value="'+data[i]+'" id="'+data[i]+'">'+data[i]+'<br>\n';
           content += radio;
       }   

   }
    element.innerHTML = content;
}


sbm.widgets.compare = function(widgetId1,operator,widgetId2){
     var widget1 = FormWidgetHandler.getInstance().findWidgetByName(widgetId1);
     if(typeof widget1 == 'undefined' || widget1 == null){
            Ext.Msg.alert("Error","<p>Failed to get element for '"+widgetId1+"' widget.</p>");
            return;
     }
     var widget2 = FormWidgetHandler.getInstance().findWidgetByName(widgetId2);
     if(typeof widget2 == 'undefined' || widget2 == null){
            Ext.Msg.alert("Error","<p>Failed to get element for '"+widgetId2+"' widget.</p>");
            return;
     }

     if(operator != '==' && operator != '!=' && operator != '<' &&  operator != '<=' && operator != '>' &&  operator != '>='){
            Ext.Msg.alert("Error","<p>Operation '"+operator+"' is not supported.</p><p>Supported operations are: '==', '!=', '<', '<=', '>' and '>='.</p>");
            return;
     }

     if(operator == '==') {       
          return (widget1.getValue() == widget2.getValue());
     }else if(operator == '!='){
          return (widget1.getValue() != widget2.getValue());  
     }else if(operator == '>'){
          return (parseInt(widget1.getValue()) > parseInt(widget2.getValue()));
     }else if(operator == '>='){
          return (parseInt(widget1.getValue()) >= parseInt(widget2.getValue()));
     }else if(operator == '<'){
          return (parseInt(widget1.getValue()) < parseInt(widget2.getValue()));
     }else if(operator == '<='){
          return (parseInt(widget1.getValue()) <= parseInt(widget2.getValue()));
     }else {
          Ext.Msg.alert("Error","<p>Operation '"+operator+"' is not supported.</p><p>Supported operations are: '==', '!=', '<', '<=', '>' and '>='.</p>");
          return;
     }
}


sbm.widgets.compareConstant = function(widgetId1,operator,constant){
     var widget1 = FormWidgetHandler.getInstance().findWidgetByName(widgetId1);
     if(typeof widget1 == 'undefined' || widget1 == null){
            Ext.Msg.alert("Error","<p>Failed to get element for '"+widgetId1+"' widget.</p>");
            return;
     }
     if(operator != '==' && operator != '!=' && operator != '<' &&  operator != '<=' && operator != '>' &&  operator != '>='){
            Ext.Msg.alert("Error","<p>Operation '"+operator+"' is not supported.</p><p>Supported operations are: '==', '!=', '<', '<=', '>' and '>='.</p>");
            return;
     }

     if(operator == '==') {
          return (widget1.getValue() == constant);
     }else if(operator == '!='){
          return (widget1.getValue() != constant);  
     }else if(operator == '>'){
          return (parseInt(widget1.getValue()) > parseInt(constant));
     }else if(operator == '>='){
          return (parseInt(widget1.getValue()) >= parseInt(constant));
     }else if(operator == '<'){
          return (parseInt(widget1.getValue()) < parseInt(constant));
     }else if(operator == '<='){
          return (parseInt(widget1.getValue()) <= parseInt(constant));
     }
     return;
}


/**
 *   returns the jmaki widget object for a given widget id
 */ 
sbm.widgets.get = function(widgetName) {
        return FormWidgetHandler.getInstance().findWidgetByName(widgetName);
}
 
 
sbm.widgets.setValue =function(widgetName, value) {      
      var widget = jmaki.getWidget(widgetName);
      if(YAHOO.lang.isUndefined(widget)) {
        sbm.widgets.showMsg('Alert',"'"+widgetName + "' widget not found!"); 
        return;
      }
      try
      {          
          if(widget.setSelectedValue)
             widget.setSelectedValue(value);
          else
             widget.setValue(value);
           
      }catch(e){
        sbm.widgets.showMsg('Error',e);  
      }
}


sbm.widgets.getValue =function(widgetName) {
      var widget = jmaki.getWidget(widgetName);
      if(YAHOO.lang.isUndefined(widget)){ 
          sbm.widgets.showMsg('Alert',"'"+widgetName + "' widget not found!"); 
          return;
      }
      try
      {
          return  widget.getValue();
      }catch(e){
        sbm.widgets.showMsg('Error',e);  
      }
}

sbm.util.show =function(widgetName) {
     YAHOO.util.Dom.setStyle(widgetName,'display','inline');
}

sbm.util.hide =function(widgetName) {
     YAHOO.util.Dom.setStyle(widgetName,'display','none');
}

sbm.widgets.show =function(widgetName) {
     YAHOO.util.Dom.setStyle(widgetName+'_div','display','block');    
}

sbm.widgets.hide =function(widgetName) {
     YAHOO.util.Dom.setStyle(widgetName+'_div','display','none');
}

sbm.widgets.enable =function(widgetName) {
      var wElement = document.getElementById(widgetName+"_div");
      var childs = wElement.getElementsByTagName("input");
      for (var i = 0; i < childs.length; i++)                     
       childs[i].disabled = false;
      var children = wElement.getElementsByTagName("select");
      for (var i = 0; i < children.length; i++)                     
       children[i].disabled = false;
}

sbm.widgets.disable =function(widgetName) {
      var wElement = document.getElementById(widgetName+"_div");
      
      var childs = wElement.getElementsByTagName("input");
      
      if(!YAHOO.lang.isUndefined(childs)){
         for (var i = 0; i < childs.length; i++)                     
       childs[i].disabled = true;
      }
      
      var children = wElement.getElementsByTagName("select");
      if(!YAHOO.lang.isUndefined(children)){
      for (var i = 0; i < children.length; i++)                     
       children[i].disabled = true;
      }
}

sbm.widgets.addOption = function(widgetName,label,value) {
      var formWidget = FormWidgetHandler.getInstance().findFormWidgetByName(widgetName);
      var widget = formWidget.getWidget();
      if(!(formWidget.getWidgetType() == 'sbm.list' || formWidget.getWidgetType() == 'sbm.combobox')){
         sbm.widgets.showMsg('Error',"Can not add option for '"+widgetName + "' widget, supported types are list and combobox."); 
         return; 
      }
      if(YAHOO.lang.isUndefined(label)) {
          sbm.widgets.showMsg('Alert','sbm.widgets.addOption failed, because label is undefined!'); 
      }
      
      if(YAHOO.lang.isUndefined(value)) value = label;
      var select = document.getElementById(widgetName);
      select.options[select.options.length] = new Option(label,value);
}

sbm.widgets.removeOptions = function(widgetName,indexes) {
     var formWidget = FormWidgetHandler.getInstance().findFormWidgetByName(widgetName);
     var widget = formWidget.getWidget();
     if(!(formWidget.getWidgetType() == 'sbm.list' || formWidget.getWidgetType() == 'sbm.combobox')){
         sbm.widgets.showMsg('Error',"Can not add option for '"+widgetName + "' widget, supported types are list and combobox."); 
         return; 
      }
      var select = document.getElementById(widgetName);
      if(indexes != null && indexes.length > 0) {
          for(var i = indexes.length-1; i >= 0; i--){
                 select.remove(indexes[i]);
          }
      }
}

sbm.widgets.removeAllOptions = function(widgetName) {
     var formWidget = FormWidgetHandler.getInstance().findFormWidgetByName(widgetName);
     var widget = formWidget.getWidget();
     if(!(formWidget.getWidgetType() == 'sbm.list' || formWidget.getWidgetType() == 'sbm.combobox')){
         sbm.widgets.showMsg('Error',"Can not add option for '"+widgetName + "' widget, supported types are list and combobox."); 
         return; 
     }
     sbm.util.removeAllOptions(widgetName);
}

sbm.widgets.showTooltip = function(widgetName,title) {
    var tooltip = new Ext.ToolTip({
                           target: widgetName,
                           title: title,
                           plain: true,
                           showDelay: 0,
                           hideDelay: 0.5,
                           trackMouse: true
        });
}

sbm.widgets.showMsg = function(title,message) { 
    Ext.Msg.alert(title,message);
}

 
sbm.adaplet.renderChart = function(p_type ,pt_name, adapletName, chart_type, div_name, caption, Xlabel, Ylabel, width, height)
{
    
    processContextMap = {'_P_TYPE':p_type,'_PT_NAME':pt_name,'_WS_NAME':adapletName,'_CLASS_NAME':'com.savvion.sbm.adapters.db.DBAdapter'};
    inputDataslotsMap = {};
    
    chartMap = {'chartType':chart_type,'width':width,'height':height,'caption':caption,'subcaption':'Powered by CX Process Business Manager','xAxisName':Xlabel,'yAxisName':Ylabel,'numberPrefix':''};
    adapterDWR.renderChart(processContextMap, inputDataslotsMap, chartMap, function(map){
      if(map['exception'] != undefined)
        alert("Exception in adapter execution..."+map['exception']);
      else
      {
     document.getElementById(div_name).innerHTML = map[div_name];
      }
    });
}

sbm.adaplet.renderTable = function (id,list) {	
	document.getElementById(id).innerHTML = 'Please wait while loading...';  
    var fieldsDef = new Array();
    var columns = new Array();
    var data = new Array();
    
    var width = Ext.get(id).getWidth()
    for(var i=0;i < list[0].length;i++) {
           fieldsDef[fieldsDef.length] = {name:list[0][i], type:'string'};
           columns[columns.length] = {id:list[0][i],name:list[0][i], text: list[0][i],header: list[0][i], sortable: true, dataIndex: list[0][i],flex:1};
    }
	for(var i=1; i < list.length;i++){          
		  var _row = {};
          for(var j=0; j < list[1].length;j++) {			                	
				_row[list[0][j]] = (list[i][j] != undefined)  ? (list[i][j]+'').replace(/\n/g, '<br/>') : ' ';
	      }
	      data[data.length] = _row;      
    }
    
    var store = Ext.create('Ext.data.JsonStore', {
            autoDestroy: true,
            storeId: id+'_store',
            fields: columns,            
            data: data          
    });
        
    var grid = Ext.create('Ext.grid.Panel', {
                id: id+'_grid',
                //renderTo: id,
                store: store,               
                autoScroll: true,
                autoWidth: true,
                autoHeight: true,
                layout: 'fit',                              
                columns: columns
   });      
    document.getElementById(id).innerHTML = '';
    grid.render(id);
    grid.doLayout();    
}

/***
 *  Dummy meothds for backward compatibility
 */
function addField( name, type, isMandatory ){}
function addFieldLabel( name,type, isMandatory,label){}


////////////////////////////////////////////////////////
//  Form Panel Extension of Ext.Panel
///////////////////////////////////////////////////////
FormPanel = Ext.extend(Ext.Panel,{
    initComponent : function(){     
        FormPanel.superclass.initComponent.call(this);
    this.bodyContentEl = document.getElementById(this.renderTo +'_body');
    },
       
    createElement : function(name, pnode){      
    if(this[name]){
        pnode.appendChild(this[name].dom);
            return;
        }
         
        if(name === 'bwrap' || this.elements.indexOf(name) != -1){
        if(this[name+'Cfg']){
                this[name] = Ext.fly(pnode).createChild(this[name+'Cfg']);
            }else{
                
        var el = (name == 'body') ?   this.bodyContentEl : document.createElement('div');
                el.className = this[name+'Cls'];        
                this[name] = Ext.get(pnode.appendChild(el));
                
            }
        }       
    }
});

/*
Ext.override(Ext.form.Checkbox, {
    onRender: function(ct, position){
        Ext.form.Checkbox.superclass.onRender.call(this, ct, position);
        if(this.inputValue !== undefined){
            this.el.dom.value = this.inputValue;
        }
        //this.el.addClass('x-hidden');
        this.innerWrap = this.el.wrap({
            //tabIndex: this.tabIndex,
            cls: this.baseCls+'-wrap-inner'
        });
        this.wrap = this.innerWrap.wrap({cls: this.baseCls+'-wrap'});
        this.imageEl = this.innerWrap.createChild({
            tag: 'img',
            src: Ext.BLANK_IMAGE_URL,
            cls: this.baseCls
        });
        if(this.boxLabel){
            this.labelEl = this.innerWrap.createChild({
                tag: 'label',
                htmlFor: this.el.id,
                cls: 'x-form-cb-label',
                html: this.boxLabel
            });
        }
        //this.imageEl = this.innerWrap.createChild({
            //tag: 'img',
            //src: Ext.BLANK_IMAGE_URL,
            //cls: this.baseCls
        //}, this.el);
        if(this.checked){
            this.setValue(true);
        }else{
            this.checked = this.el.dom.checked;
        }
        this.originalValue = this.checked;
    },
    afterRender: function(){
        Ext.form.Checkbox.superclass.afterRender.call(this);
        //this.wrap[this.checked ? 'addClass' : 'removeClass'](this.checkedCls);
        this.imageEl[this.checked ? 'addClass' : 'removeClass'](this.checkedCls);
    },
    initCheckEvents: function(){
        //this.innerWrap.removeAllListeners();
        this.innerWrap.addClassOnOver(this.overCls);
        this.innerWrap.addClassOnClick(this.mouseDownCls);
        this.innerWrap.on('click', this.onClick, this);
        //this.innerWrap.on('keyup', this.onKeyUp, this);
    },
    onFocus: function(e) {
        Ext.form.Checkbox.superclass.onFocus.call(this, e);
        //this.el.addClass(this.focusCls);
        this.innerWrap.addClass(this.focusCls);
    },
    onBlur: function(e) {
        Ext.form.Checkbox.superclass.onBlur.call(this, e);
        //this.el.removeClass(this.focusCls);
        this.innerWrap.removeClass(this.focusCls);
    },
    onClick: function(e){
        if (e.getTarget().htmlFor != this.el.dom.id) {
            if (e.getTarget() !== this.el.dom) {
                this.el.focus();
            }
            if (!this.disabled && !this.readOnly) {
                this.toggleValue();
            }
        }
        //e.stopEvent();
    },
    onEnable: Ext.form.Checkbox.superclass.onEnable,
    onDisable: Ext.form.Checkbox.superclass.onDisable,
    onKeyUp: undefined,
    setValue: function(v) {
        var checked = this.checked;
        this.checked = (v === true || v === 'true' || v == '1' || String(v).toLowerCase() == 'on');
        if(this.rendered){
            this.el.dom.checked = this.checked;
            this.el.dom.defaultChecked = this.checked;
            //this.wrap[this.checked ? 'addClass' : 'removeClass'](this.checkedCls);
            this.imageEl[this.checked ? 'addClass' : 'removeClass'](this.checkedCls);
        }
        if(checked != this.checked){
            this.fireEvent("check", this, this.checked);
            if(this.handler){
                this.handler.call(this.scope || this, this, this.checked);
            }
        }
    },
    getResizeEl: function() {
        //if(!this.resizeEl){
            //this.resizeEl = Ext.isSafari ? this.wrap : (this.wrap.up('.x-form-element', 5) || this.wrap);
        //}
        //return this.resizeEl;
        return this.wrap;
    }
});
Ext.override(Ext.form.Radio, {
    checkedCls: 'x-form-radio-checked'
});*/
//////////////////////////////////////////////////////////
sbm.i18n = sbm.i18n || {};
sbm.i18n.properties = new Object();
sbm.i18n.getLabel = function(labelKey){
    var resources = {};
    for(var key in sbm.i18n.properties){
            resources = sbm.i18n.properties[key];             
    }    
    return (typeof resources[labelKey] != 'undefined')? resources[labelKey] : labelKey;
};



//////////////////////////////////////////////////////////////////////////////////////////////////////////
//   New Event Logic API
//   This API is used in Form Editor's Logic Tab
//
/////////////////////////////////////////////////////////////////////////////////////////////////////////
Ext.namespace("Bm.util");
Ext.namespace("Bm.validation");
Ext.namespace("BM.Portal");
/**
 *   Sets the value of a widget to the given value
 *   @function Bm.util.setValue
 *   @param widgetName (string)
 *   @param value (string)
 */
Bm.util.setValue = function(widgetName,value){
    sbm.widgets.setValue(widgetName,value);
}
/**
 *   Gets the value of a widget 
 *   @function Bm.util.getValue
 *   @param widgetName (string)
 */
Bm.util.getValue = function(widgetName,value){
    return sbm.widgets.getValue(widgetName);
}

/**
 *   Returns the value of a widget  
 *   @function Bm.util.getValue
 *   @param widgetName (string)
 */
Bm.util.isEmpty = function(widgetName){      
    var value = sbm.widgets.getValue(widgetName); 
    return (typeof sbm.widgets.getValue(widgetName) != 'undefined' &&  value.length > 0) ? false : true;
}

/**
 *   Copies the value of source widget to target widget
 *   @function Bm.util.copy
 *    @param sourceWidgetName (string)
 *   @param targetWidgetName (string)
 */
Bm.util.copy = function(sourceWidgetName,targetWidgetName){
    sbm.widgets.setValue(targetWidgetName,sbm.widgets.getValue(sourceWidgetName));
}

Bm.util.csvToJSONArray = function(value) {
  if (value==null) {
    return "[]";
  }
  var arr = (""+value).split(",");
  var json = arr.join("\",\"");
  return "[\""+json+"\"]";
}


Bm.util.isEmptySimple = function(widgetName){      
    var value = Bm.util.getSimpleValue(widgetName); 
    return typeof (value) == 'undefined' || value.replace(/^\s+|\s+$/g, "").length < 1;
}


Bm.util.getSimpleValue = function(widgetName) {
  try {
    var widget = FormWidgetHandler.getInstance().findFormWidgetByName(widgetName);
    if(YAHOO.lang.isUndefined(widget)){ 
      sbm.widgets.showMsg('Alert',"'"+widgetName + "' widget not found!");       
      return;
    }
    var type = widget.getWidgetType();
    if (type=="sbm.textarea" || type=="sbm.textfield" || type=="form.slider") {
      return widget.getWidget().getValue();  
    } else if (type=="sbm.combobox" || type=="sbm.radio") {
      return widget.getWidget().getSelectedValue();
    } else if (type=="sbm.checkbox") {	  
      var _v = new Array();	  
      try {
  	 var values = YAHOO.lang.JSON.parse(widget.getWidget().getValue());
       	 for(var i=0;i<values.length;i++){
	     if(values[i].selected){           	   
	         _v.push(values[i].value);  		 
	     }
	 }
      } catch (err) {
         _v.push(widget.getWidget().getValue());
      }
      return (_v.length !=0) ? _v.join(',') : "";
    } else if (type=="sbm.list") {	  
	  return ""+widget.getWidget().getSelectedValues();
    }
  } catch (err) {
    // ?
  }
}



Bm.util.setSimpleValue = function (widgetName, value) {
  try {
    var widget = FormWidgetHandler.getInstance().findFormWidgetByName(widgetName);
    if(YAHOO.lang.isUndefined(widget)){ 
      sbm.widgets.showMsg('Alert',"'"+widgetName + "' widget not found!");       
      return;
    }
    var type = widget.getWidgetType();
    if (type=="sbm.textarea" || type=="sbm.textfield") {
        if (value==null) value="";
        widget.getWidget().setValue(value);
    } else if (type=="sbm.combobox" || type=="sbm.radio") {
      widget.getWidget().setSelectedValue(value);
    } else if (type=="sbm.checkbox") {	 
	  widget.getWidget().setSelectedValues(Bm.util.csvToJSONArray(value));	  
    } else if (type=="sbm.list") {
      widget.getWidget().putSelectedValues(Bm.util.csvToJSONArray(value));
    } else if (type=="form.slider") {
        if (value==null) value="0";
        widget.getWidget().setValue(parseInt(value));
    }
  } catch (err) {
    // ?
  }
}

Bm.util.clearSimple = function (widgetName, value) {
  try {
    var widget = FormWidgetHandler.getInstance().findFormWidgetByName(widgetName);
    if(YAHOO.lang.isUndefined(widget)){ 
      sbm.widgets.showMsg('Alert',"'"+widgetName + "' widget not found!");       
      return;
    }
    var type = widget.getWidgetType();
    var w = widget.getWidget();
    if (type=="sbm.textarea" || type=="sbm.textfield") {
      w.setValue("");
    } else if (type=="sbm.combobox"){
      w.setSelectedValues('[null]') // unselect all options
  	} else if (type=="sbm.radio") {
      w.setSelectedValues('[null]') // set all radiobuttons to unchecked state
    } else if (type=="sbm.checkbox") {	 
      w.setSelectedValues('[null]') // set all chackboxes to unchecked state
    } else if (type=="sbm.list") {
      w.setSelectedValues('[null]') // unselect all options 
    } else if (type=="form.slider") {
      if (value==null) {
        value="0";
      }
	  w.setValue(0);
    }
  } catch (err) {
    // ?
  }
}

Bm.util.copySimple = function(sourceWidgetName,targetWidgetName) {
     var source = FormWidgetHandler.getInstance().findFormWidgetByName(sourceWidgetName);
     var target = FormWidgetHandler.getInstance().findFormWidgetByName(targetWidgetName);
     if(YAHOO.lang.isUndefined(source)){ 
          sbm.widgets.showMsg('Alert',"'"+sourceWidgetName + "' widget not found!");         
          return;
     }
     if(YAHOO.lang.isUndefined(target)){ 
          sbm.widgets.showMsg('Alert',"'"+targetWidgetName + "' widget not found!"); 
          return;
     }
     Bm.util.setSimpleValue(targetWidgetName, Bm.util.getSimpleValue(sourceWidgetName));
}


Bm.util.simpleCompareConstant = function(widgetId,operator,value2,type) {
  var widget = FormWidgetHandler.getInstance().findFormWidgetByName(widgetId);
  if(YAHOO.lang.isUndefined(widget)){ 
    sbm.widgets.showMsg('Alert',"'"+widgetId + "' widget not found!");       
    return;
  }
  var value1 = Bm.util.getSimpleValue(widgetId);
  if (type=="number") {
    //  TODO: Check about parsing to float
    value1 = parseInt(value1);
    value2 = parseInt(value2);
  }
  switch (operator) {
    case '==': return value1 == value2;
    case '>': return value1 > value2;
    case '<': return value1 < value2;
    case '>=': return value1 >= value2;
    case '<=': return value1 <= value2;
    case '!=': return value1 != value2;
    default:
      sbm.widgets.showMsg('Alert',"Unsupported comparison operation: "+operator);        
  }
  return false;
}

Bm.util.simpleCompare = function(widgetId1,operator,widgetId2,type) {
  var widget2 = FormWidgetHandler.getInstance().findFormWidgetByName(widgetId2);
  if(YAHOO.lang.isUndefined(widget2)){ 
    sbm.widgets.showMsg('Alert',"'"+widgetId2 + "' widget not found!");      
    return;
  }
  var value2 = Bm.util.getSimpleValue(widgetId2);
  return Bm.util.simpleCompareConstant(widgetId1,operator,value2,type);
}


/**
 *   Hides the widget
 *   @function Bm.util.hide
 *   @param widgetName (string) 
 */
Bm.util.hide = function(widgetName){

	if(widgetName == null) return null;	
	var widgetType = Bm.form.Cache.getWidgetType(widgetName);   
     	
	if(widgetType == 'form.button' || widgetType == 'form.tree' ||
	   widgetType == 'form.slider' || widgetType == 'form.grid' || 
	   widgetType == 'form.datetime' || widgetType == 'form.menu'){
	   try{
	   var comp = Ext.getCmp(widgetName+'_id');	   
	   comp.hide();
	   }catch(e){};
	}else if(widgetType == 'sbm.textarea'){
	     var el = document.getElementById(widgetName);
		 el.style.display = 'none';		 
	}else {    
		sbm.util.hide((Bm.form.Cache.isWidget(widgetName)) ? (widgetName + '_div') : widgetName);
	}
}
/**
 *   Shows the widget
 *   @function Bm.util.show
 *   @param widgetName (string) 
 */
Bm.util.show = function(widgetName){

	if(widgetName == null) return null;	
	var widgetType = Bm.form.Cache.getWidgetType(widgetName);   
     	
	if(widgetType == 'form.button' || widgetType == 'form.tree' ||
	   widgetType == 'form.slider' || widgetType == 'form.grid' || 
	   widgetType == 'form.datetime' || widgetType == 'form.menu'){
	   try{
	   var comp = Ext.getCmp(widgetName+'_id');	   
	   comp.show();
	   }catch(e){};
	}else if(widgetType == 'sbm.textarea'){
	     var el = document.getElementById(widgetName);
		 el.style.display = 'block';		 
	}else {    
		sbm.util.show((Bm.form.Cache.isWidget(widgetName)) ? (widgetName + '_div') : widgetName);
	}

}

/**
 *   Shows a pop up message
 *   @function Bm.util.show
 *   @param widgetName (string) 
 */
Bm.util.popupMessage = function(message){
    sbm.widgets.showMsg('',message);
}

/**
 *   Compares the value of a widget with another one
 *   @function Bm.util.compare
 *   @param field1 (string) 
 *   @param operator (string) 
 *   @param field2 (string) 
 *   @param type (string) 
 */
Bm.util.compare = function(field1,operator,field2,type){
   if(type == 'number')
      return Bm.util.compareNumber(field1,operator,field2);
   else      
     return sbm.utils.compare(field1,operator,field2);
}

/**
 *   Compares the value of a widget with a constant value
 *   @function Bm.util.compare
 *   @param field1 (string) 
 *   @param operator (string) 
 *   @param field2 (string) 
 *   @param type (string) 
 */
Bm.util.compareConstant = function(field1,operator,field2,type){    
     return sbm.utils.compareConstant(field1,operator,field2);
}

Bm.util.compareNumber = function(field1,operator,field2){
     var elem1 = document.getElementById(field1);
     if(typeof elem1 == 'undefined' || elem1 == null){
            Ext.Msg.alert("Error","<p>Failed to get element for '"+field1+"' field.</p>");
            return;
     }
     var elem2 = document.getElementById(field2);
     if(typeof elem2 == 'undefined' || elem2 == null){
            Ext.Msg.alert("Error","<p>Failed to get element for '"+field2+"' field.</p>");
            return;
     }

     if(operator != '==' && operator != '!=' && operator != '<' &&  operator != '<=' && operator != '>' &&  operator != '>='){
            Ext.Msg.alert("Error","<p>Operation '"+operator+"' is not supported.</p><p>Supported operations are: '==', '!=', '<', '<=', '>' and '>='.</p>");
            return;
     }  

     if(operator == '==') {       
          return (parseInt(elem1.value) == parseInt(elem2.value));
     }else if(operator == '!='){
          return (parseInt(elem1.value) != parseInt(elem2.value));  
     }else if(operator == '>'){
          return (parseInt(elem1.value) > parseInt(elem2.value));
     }else if(operator == '>='){
          return (parseInt(elem1.value) >= parseInt(elem2.value));
     }else if(operator == '<'){
          return (parseInt(elem1.value) < parseInt(elem2.value));
     }else if(operator == '<='){
          return (parseInt(elem1.value) <= parseInt(elem2.value));
     }else {
          Ext.Msg.alert("Error","<p>Operation '"+operator+"' is not supported.</p><p>Supported operations are: '==', '!=', '<', '<=', '>' and '>='.</p>");
          return;
     }
}

/**
 *   Shows an inline message 
 *   @function Bm.util.inlineMessage
 *   @param field (string) 
 *   @param message (string)  
 */
Bm.util.inlineMessage = function(field,message){      
   var el = {html: message, tag: 'span',id:field+'_inline_message'};                   
   try{
       var sibling = Ext.get(field);       
       Ext.DomHelper.append(field, el);  
   }catch(e){      
       sbm.widgets.showMsg('Error',"Can not add inline message for '"+field + "' element."); 
   }   
}
/**
 *   Evaluates a regular expression 
 *   @function Bm.util.matchRegex
 *   @param value (string) 
 *   @param regExp (string)  
 */

Bm.util.matchRegex = function(value,regExp){
     // apply regular expression
     return value.match(new RegExp(regExp));
}

/**
 *   Retrieves the length of given string parameter 
 *   @function Bm.util.getLength
 *   @param value (string) 
 *   @param regExp (string)  
 */
Bm.util.getLength = function(value){
     // apply regular expression
     return value.length;
}

/**
 *   Executes the adaplet 
 *   @function Bm.util.invokeAdaplet
 *   @param adapletPath (string) 
 *   @param callback (func)  
 */
Bm.util.invokeAdaplet = function(adapletPath, callback){     
	 sbm.adaplet.invoke(adapletPath,callback);
}
Ext.namespace("Bm.adaplet");
/**
 *   Executes the adaplet 
 *   @function Bm.adaplets.invoke
 *   @param adapletPath (string) 
 *   @param callback (func)  
 */
Bm.adaplet.invoke = function(adapletPath, callback){     
	 sbm.adaplet.invoke(adapletPath,callback);
}

/**
 *   Enables the widget by given widgetName 
 *   @function Bm.util.enable
 *   @param widgetName (string) 
 */
Bm.util.enable = function(widgetName){ 
    if(widgetName == null) return null;	
	var widgetType = Bm.form.Cache.getWidgetType(widgetName);    
	if(widgetType == 'form.button' || widgetType == 'form.tree' ||
	   widgetType == 'form.slider' || widgetType == 'form.grid' || 
	   widgetType == 'form.datetime' || widgetType == 'form.menu'){
	   try{
	   var comp = Ext.getCmp(widgetName+'_id');
	   comp.enable();
	   }catch(e){};
	}else if(widgetType == 'sbm.textarea'){
	     var el = document.getElementById(widgetName);
		 el.disabled = false;		 
	}else {    
	   sbm.widgets.enable(widgetName);
	}
}

/**
 *   Disables the widget by given widgetName 
 *   @function Bm.util.disable
 *   @param widgetName (string) 
 */
Bm.util.disable = function(widgetName){   
    
	if(widgetName == null) return null;	
	var widgetType = Bm.form.Cache.getWidgetType(widgetName);   
     	
	if(widgetType == 'form.button' || widgetType == 'form.tree' ||
	   widgetType == 'form.slider' || widgetType == 'form.grid' || 
	   widgetType == 'form.datetime' || widgetType == 'form.menu'){
	   try{
	   var comp = Ext.getCmp(widgetName+'_id');	   
	   comp.disable();
	   }catch(e){};
	}else if(widgetType == 'sbm.textarea'){
	     var el = document.getElementById(widgetName);
		 el.disabled = true;		 
	}else {    
	   sbm.widgets.disable(widgetName);
	}	 
}

/**
 *   Gets the jMaki widget by given widgetName 
 *   @function Bm.util.getWidget
 *   @param widgetName (string) 
 */
Bm.util.getWidget = function(widgetName){     
     return sbm.widgets.get(widgetName);
}


/**
 *   Creates a tooltip for the given element 
 *   @function Bm.util.setToolTip
 *   @param elemId (string) 
 *   @param html (html) 
 */
Bm.util.setToolTip = function(elemId,html){     
    var tip = Ext.create('Ext.tip.ToolTip', {
        target: Ext.get(elemId),
        html: html
    });
}

function WidgetEffects(options){
    this.type = (YAHOO.lang.isUndefined(options.type)) ? 'highlight': options.type;
	//alert(this.type);
    this.duration = (YAHOO.lang.isUndefined(options.duration)) ? 1000 : options.duration;
    this.toggle = (YAHOO.lang.isUndefined(options.toggle)) ? true : options.toggle;
    if(!YAHOO.lang.isUndefined(options.from)) this.from = options.from;
    if(!YAHOO.lang.isUndefined(options.to)) this.to = options.to;
    this.restoreColor = (YAHOO.lang.isUndefined(options.restoreColor)) ? '#FFCC33' : options.restoreColor;
    //this.from = (YAHOO.lang.isUndefined(options.from)) ? '#CCCCCC' : options.from;
    //this.to = (YAHOO.lang.isUndefined(options.to)) ? '#FFCC33' : options.to;
}

WidgetEffects.constructor = WidgetEffects;
WidgetEffects.prototype.toString = function() {
    var message = "type: "+this.type+", duration:"+this.duration+", toggle:"+this.toggle+
          ", from:"+this.from+", to: "+this.to+", restoreColor:"+this.restoreColor;
    sbm.widgets.showMsg('Effects',message);
    
}

Bm.util.checkUUID = function (uuid) { 
	if(uuid == null) return null;
    if (Bm.form.Cache.isWidget(uuid)) {
		return uuid+"_div";
    }
    else {
		return uuid;
	}
}


sbm.utils.applyEffects = function(uuid, options) {
  uuid = Bm.util.checkUUID(uuid);
  var effect = null;
  try {
    var widgetEffects = new  WidgetEffects(options);   
    if(widgetEffects.type == "fade")
        effect = new Spry.Effect.Fade(uuid, {duration: widgetEffects.duration, from: widgetEffects.from, to: widgetEffects.to,toggle:true});
    else if(widgetEffects.type == "appear")
        effect = new Spry.Effect.Fade(uuid, {duration: widgetEffects.duration, from: widgetEffects.from, to: widgetEffects.to,toggle:true});
    else if(widgetEffects.type == "puff") 
        effect = new Spry.Effect.Puff(uuid, {duration: widgetEffects.duration, from: widgetEffects.from, to: widgetEffects.to,toggle:true});	
    else if(widgetEffects.type == "blindup" || widgetEffects.type == "blind")
        effect = new Spry.Effect.Blind(uuid, {duration: widgetEffects.duration, from: widgetEffects.from, to: widgetEffects.to,toggle:true});
    else if(widgetEffects.type == "blinddown")
        effect = new Spry.Effect.Blind(uuid, {duration: widgetEffects.duration, from: widgetEffects.from, to: widgetEffects.to,toggle:true});
    else if(widgetEffects.type == "switchoff")
        effect = new Spry.Effect.Puff(uuid, {duration: widgetEffects.duration, from: widgetEffects.from, to: widgetEffects.to,toggle:true});
    else if(widgetEffects.type == "dropout")
        effect = new Spry.Effect.DropOut(uuid, {duration: widgetEffects.duration, from: widgetEffects.from, to: widgetEffects.to,toggle:true});
    else if(widgetEffects.type == "shake")
        effect = new Spry.Effect.Shake(uuid, {duration: widgetEffects.duration, from: widgetEffects.from, to: widgetEffects.to,toggle:true});
    else if(widgetEffects.type == "squish")
        effect = new Spry.Effect.Squish(uuid, {duration: widgetEffects.duration, from: widgetEffects.from, to: widgetEffects.to,toggle:true});
    else if (widgetEffects.type == "grow") {
		options = {
			"type": "grow",
			"from": "0%",
			"to": "100%"
		};
		effect = new Spry.Effect.Grow(uuid, {
			duration: widgetEffects.duration,
			from: "0%",
			to: "100%",
			toggle: true
		});
	}
	else 
		if (widgetEffects.type == "shrink") 
			effect = new Spry.Effect.Grow(uuid, {
				duration: widgetEffects.duration,
				from: widgetEffects.from,
				to: widgetEffects.to,
				toggle: true
			});
		else 
			if (widgetEffects.type == "pulsate") 
				effect = new Spry.Effect.Pulsate(uuid, {
					duration: widgetEffects.duration,
					from: widgetEffects.from,
					to: widgetEffects.to,
					toggle: true
				});
			else 
				if (widgetEffects.type == "fold") 
					effect = new Spry.Effect.Fold(uuid, {
						duration: widgetEffects.duration,
						from: widgetEffects.from,
						to: widgetEffects.to,
						toggle: true
					});
				else 
					if (widgetEffects.type == "highlight") 
						effect = new Spry.Effect.Highlight(uuid, {
							duration: widgetEffects.duration,
							from: widgetEffects.from,
							to: widgetEffects.to,
							toggle: true
						});
					else 
						if (widgetEffects.type == "slideup" || widgetEffects.type == "slide") 
							effect = new Spry.Effect.Slide(uuid, {
								duration: widgetEffects.duration,
								from: widgetEffects.from,
								to: widgetEffects.to,
								toggle: true
							});
						else 
							if (widgetEffects.type == "slidedown") 
								effect = new Spry.Effect.Slide(uuid, {
									duration: widgetEffects.duration,
									from: widgetEffects.from,
									to: widgetEffects.to,
									toggle: true
								});
							else 
								alert('system doesnt support  ' + widgetEffects.type);
    if(effect.start)effect.start();
  }catch(e){
    alert(e);     
  }
}


Ext.namespace("Bm.effect");

/**
 *   Effects the given function
 *   @function Bm.effect.Fade
 *   @param widgetName (string)
 */
var options = {};
Bm.effect.Fade = function(element) {
	//Effect.Fade(element);
	options = {"type":"fade","from":"100","to":"0","duration":"1000"};
	sbm.utils.applyEffects(element,options);
}
/**
 *   Effects the given function
 *   @function Bm.effect.Appear
 *   @param widgetName (string)
 */
Bm.effect.Appear = function(element) {
	//Effect.Appear(element);
	options = {"type":"appear","from":"0","to":"100","duration":"1000"};
	sbm.utils.applyEffects(element,options);
}
/**
 *   Effects the given function
 *   @function Bm.effect.Puff
 *   @param widgetName (string)
 */
Bm.effect.Puff = function(element) {
	//Effect.Puff(element);
	options = {"type":"puff"};
	sbm.utils.applyEffects(element,options);
}
/**
 *   Effects the given function
 *   @function Bm.effect.BlindUp
 *   @param widgetName (string)
 */
Bm.effect.BlindUp = function(element) {
	//Effect.BlindUp(element);
	options = {"type":"blindup","from":"100%","to":"0%","duration":"1000"};
	sbm.utils.applyEffects(element,options);
}
/**
 *   Effects the given function
 *   @function Bm.effect.BlindDown
 *   @param widgetName (string)
 */
Bm.effect.BlindDown = function(element) {
	//Effect.BlindDown(element);
	options = {"type":"blinddown","from":"0%","to":"100%","duration":"1000"};
	sbm.utils.applyEffects(element,options);
}
/**
 *   Effects the given function
 *   @function Bm.effect.SwitchOff
 *   @param widgetName (string)
 */
Bm.effect.SwitchOff = function(element) {
	//Effect.SwitchOff(element);
	options = {"type":"switchoff"};
	sbm.utils.applyEffects(element,options);
}
/**
 *   Effects the given function
 *   @function Bm.effect.DropOut
 *   @param widgetName (string)
 */
Bm.effect.DropOut = function(element) {
	//Effect.DropOut(element);
	options = {"type":"dropout"};
	sbm.utils.applyEffects(element,options);
}
/**
 *   Effects the given function
 *   @function Bm.effect.Shake
 *   @param widgetName (string)
 */
Bm.effect.Shake = function(element) {
	//Effect.Shake(element);
	options = {"type":"shake"};
	sbm.utils.applyEffects(element,options);
}

/**
 *   Effects the given function
 *   @function Bm.effect.Squish
 *   @param widgetName (string)
 */
Bm.effect.Squish = function(element) {
	//Effect.Squish(element);
	options = {"type":"squish","from":"100%","to":"0%","duration":"1000"};
	sbm.utils.applyEffects(element,options);
}
/**
 *   Effects the given function
 *   @function Bm.effect.Grow
 *   @param widgetName (string)
 */
Bm.effect.Grow = function(element) {
	//Effect.Grow(element);
	options = {"type":"grow","from":"0%","to":"100%"};
	sbm.utils.applyEffects(element,options);
}
/**
 *   Effects the given function
 *   @function Bm.effect.Shrink
 *   @param widgetName (string)
 */
Bm.effect.Shrink = function(element) {
	//Effect.Shrink(element);
	options = {"type":"shrink","from":"100","to":"0"};
	sbm.utils.applyEffects(element,options);
}
/**
 *   Effects the given function
 *   @function Bm.effect.Pulsate
 *   @param widgetName (string)
 */
Bm.effect.Pulsate = function(element) {
	//Effect.Pulsate(element);
	options = {"type":"pulsate"};
	sbm.utils.applyEffects(element,options);
}
/**
 *   Effects the given function
 *   @function Bm.effect.Fold
 *   @param widgetName (string)
 */
Bm.effect.Fold = function(element) {
	//Effect.Fold(element);
	options = {"type":"fold"};
	sbm.utils.applyEffects(element,options);
}



/**
 *   Effects the given function
 *   @function Bm.effect.Fold
 *   @param widgetName (string)
 */
Bm.effect.Highlight = function(element) {
	//Effect.Fold(element);
	options = {"type":"highlight","from":"#ffff99","to":"#ffffff","duration":"1000"};
	sbm.utils.applyEffects(element,options);
}
/**
 *   Effects the given function
 *   @function Bm.effect.SlideDown
 *   @param widgetName (string)
 */
Bm.effect.SlideDown = function(element) {
	options = {"type":"slidedown","from":"0%","to":"100%","duration":"1000"};
	sbm.utils.applyEffects(element,options);

	/*
  element = $(element).cleanWhitespace();
  // SlideDown need to have the content of the element wrapped in a container element with fixed height!
  var oldInnerBottom = element.getStyle('bottom');
  var elementDimensions = element.getDimensions();
  return new Effect.Scale(element, 100, Object.extend({ 
    scaleContent: false, 
    scaleX: false, 
    scaleFrom: window.opera ? 0 : 1,
    scaleMode: {originalHeight: elementDimensions.height, originalWidth: elementDimensions.width},
    restoreAfterFinish: true,
    afterSetup: function(effect) {
      effect.element.makePositioned();
      //effect.element.makePositioned();
      if(window.opera) effect.element.setStyle({top: ''});
      effect.element.makeClipping().setStyle({height: '0px'}).show(); 
    },
    afterUpdateInternal: function(effect) {
      effect.element.setStyle({bottom:
        (effect.dims[0] - effect.element.clientHeight) + 'px' }); 
    },
    afterFinishInternal: function(effect) {
      effect.element.undoClipping().undoPositioned();
      effect.element.undoPositioned().setStyle({bottom: oldInnerBottom}); }
    }, arguments[1] || {})
  );
  */
}
/**
 *   Effects the given function
 *   @function Bm.effect.SlideUp
 *   @param widgetName (string)
 */
Bm.effect.SlideUp = function(element) {
	options = {"type":"slideup","from":"100%","to":"0%","duration":"1000"};
	sbm.utils.applyEffects(element,options);
  /*
  element = $(element).cleanWhitespace();
  var oldInnerBottom = element.getStyle('bottom');
  return new Effect.Scale(element, window.opera ? 0 : 1,
   Object.extend({ scaleContent: false, 
    scaleX: false, 
    scaleMode: 'box',
    scaleFrom: 100,
    restoreAfterFinish: true,
    beforeStartInternal: function(effect) {
      effect.element.makePositioned();
      if(window.opera) effect.element.setStyle({top: ''});
      effect.element.makeClipping().show();
    },  
    afterUpdateInternal: function(effect) {
      effect.element.setStyle({bottom:
        (effect.dims[0] - effect.element.clientHeight) + 'px' });
    },
    afterFinishInternal: function(effect) {
      effect.element.hide().undoClipping().undoPositioned().setStyle({bottom: oldInnerBottom});
      effect.element.undoPositioned();
    }
   }, arguments[1] || {})
  );
  */
}
//----------------------------------------------------------------------------------------------------------//
/**
 *   Validates Document and Xml DATASLOTs present in the form 
 *   @function Bm.util.validate
 *   @param elem (string) 
 */
Bm.util.validate = function(form){
   var el;	
   var invalidWidgets = new Array();
   var widgets = new Array();
   var fields = new Ext.util.HashMap();	
   
   for(var i=0; i<form.elements.length;i++){
   	   el =  form.elements[i]; 
	   if(el.alt == 'xml') fields.add(el.name,{type:'xml',el:el});
	   else if(el.alt == 'document')fields.add(el.name,{type:'document',el:el}); 
	   if(el.alt == 'xml' || el.alt == 'document') widgets.push( el.name)
   }
   var valid = false;
   for(var i=0; i<widgets.length;i++){
  	 	var name = widgets[i];
	 	var label = name;
	 	if(name.indexOf('bizsite_dataslot_') != -1 && name.substr(0,17) == 'bizsite_dataslot_')
         	label = name.substring(17);
	    if(fields.containsKey(name)) {
	 		var field = fields.get(name);
		 	if (field.type == 'xml' && !Bm.util.validateXml(field.el)) {
	 		   invalidWidgets.push({
			   	label: label,
				id: name,
				widgetType: 'xml'
			   });
	 	    }else if(field.type == 'document' && !Bm.util.validateDocument(field.el))
	 		   invalidWidgets.push({
			   	 label:label,
				 id: name,
				 widgetType: 'document'
			   });	 
	    }	
  } 	  
  return {
   	  isValid: (invalidWidgets.length == 0) ? true : false,
	  invalidWidgets:invalidWidgets};
}

/**
 *   Validates Document DATASLOT 
 *   @function Bm.util.validateDocument
 *   @param elem (string) 
 */
Bm.util.validateDocument = function(elem)
{    
	if(typeof elem == 'undefined') return;   
    // check if it has mandatory javascript interface
    if(elem.name.substr(0,17) == 'bizsite_dataslot_') {
         var dsName = elem.name.substring(17);
         var mandatoryCheck = "doccheck_mandatory_"+dsName;
         if(document.getElementById(mandatoryCheck) != undefined){
                 var mElem = document.getElementById(mandatoryCheck);
                 if(mElem.value == "false")
                 {                      
                      return false;
                 }
             }
    }
	return true;
}


/**
 *   Validates XML DATASLOT 
 *   @function Bm.util.validateXml
 *   @param elem (string) 
 */
Bm.util.validateXml = function(elem)
{    
	this.elem = elem;
	if(this.elem == undefined) return false;    
    // check if it has mandatory javascript interface    
	if(this.elem.name.substr(0,17) == 'bizsite_dataslot_') {
             var dsName = this.elem.name.substring(17);
             var mandatoryCheck = "xmlcheck_mandatory_"+dsName;             
			 if(document.getElementById(mandatoryCheck) != undefined) {
                 var mElem = document.getElementById(mandatoryCheck);		 
				 if(mElem.value == "false") {                      
                      return false;
                 }
             }
    }  
    return true;
}

/**
 *   Validates Email Field 
 *   @function Bm.validation.isEmail
 *   @param elem (string) 
 */
Bm.validation.isEmail = function(elem)
{    
	var valid = false;
	var el = document.getElementById(elem);
	if (el == null || typeof el == 'undefined') {
		Ext.Msg.alert("Error", "<p>Failed to get element for '" + field1 + "' field.</p>");
		return valid;
	}
    var emailPatterns = [
        /.+@.+\..+$/i,
        /^\w.+@\w.+\.[a-z]+$/i,
        /^\w[-_a-z~.]+@\w[-_a-z~.]+\.[a-z]{2}[a-z]*$/i,
        /^\w[\w\d]+(\.[\w\d]+)*@\w[\w\d]+(\.[\w\d]+)*\.[a-z]{2,7}$/i
        ];
    if (emailPatterns[0].test(el.value)){
       valid = true;
    } 
    return valid;
}

/**
 *   Validates Url Field 
 *   @function Bm.validation.isUrl
 *   @param elem (string) 
 */
Bm.validation.isUrl = function(elem)
{    
	var valid = false;
	var el = document.getElementById(elem);
	if (el == null || typeof el == 'undefined') {
		Ext.Msg.alert("Error", "<p>Failed to get element for '" + field1 + "' field.</p>");
		return valid;
	}
	var hostOptionalFlag = false;  
    var allowQSFlag = true;  
	var hosts = 'http,http';  
    //hosts = Bm.validation.setArg( hosts, "http" );    
    var front = "^(?:(" + hosts.replace( /\,/g, "|" ) + ")\\:\\/\\/)";    
    var end   = ( allowQSFlag == true ) ? "(\\?.*)?$" : "$";    
    if ( hostOptionalFlag == true ) front += "?";  
    var regex = new RegExp( front + "([\\w-]+\\.?)+((\\:\\d{2,5})?)(\\/[\\w-._]*)*" + end, "i" );    
    if(regex.test(el.value))
    {
        valid = true;
    }
    return valid;
}

/**
 *   Validates Float Field 
 *   @function Bm.validation.isUrl
 *   @param elem (string) 
 */
Bm.validation.isFloat = function(elem)
{    
	var valid = false;
	var el = document.getElementById(elem);
	if (el == null || typeof el == 'undefined') {
		Ext.Msg.alert("Error", "<p>Failed to get element for '" + field1 + "' field.</p>");
		return valid;
	}
		
	if(!isNaN(parseFloat(el.value)))	
    {
        valid = true;
    }
    return valid;
}



Bm.validation.setArg = function( arg, def )
{
    return ( typeof arg == 'undefined' || arg == '' || arg == null ) ? def : arg;
}



//----------------------------------------------------------------------------------------------------------//
/**
 *    Logger Dialog for Debugging
 */
BM.Portal.LoggerDialog = function(enableLogger,div){
	this.enableLogger = false;
        this.visible = false;
	if(!YAHOO.lang.isUndefined(enableLogger) && enableLogger == true)
	this.enableLogger = true;
	this.div = div || "loggerReader";	
	this.init();
}

BM.Portal.LoggerDialog.constructor = YAHOO.LoggerDialog;
BM.Portal.LoggerDialog.prototype = {
	init: function(){
		  
	      if(this.enableLogger){	
		  this.visible = false;
                  this.loggerContainer = document.body.appendChild(document.createElement(this.div));
		  //this.loggerContainer = document.getElementById('BmFooter').appendChild(document.createElement(this.div));
		  this.loggerReader = new YAHOO.widget.LogReader(this.loggerContainer,{draggable:true,
		          outputBuffer:3000,width:"600px",verboseOutput:false,xy:['400','400']});
		  this.loggerWriter = new YAHOO.widget.LogWriter("Forms Logger");
                  this.loggerReader.show();
		  //var pos = YAHOO.util.Dom.getXY('BmFooter');
		  //YAHOO.util.Dom.setXY(this.loggerContainer,pos);
                  
                  /*var handler = this;
		  var keyPressedHandler = function(type, args, obj){                 
                       if(handler.visible) handler.hide();
                       else if(!handler.visible)handler.show();                                               
                  }*/
                  
                  //document.documentElement.focus();
                  document.body.focus();
                  /*this.loggerCloseEvent = new YAHOO.util.KeyListener(document,{alt:true,keys:[38]},{fn:keyPressedHandler});
                  this.loggerCloseEvent.enable();               */
              }
	},
	       
	show: function(){
	        if(this.enableLogger){
		    //YAHOO.util.Dom.setStyle(this.div,'display','block');
                    this.loggerReader.show();
                    this.visible = true;
		}
	},
	
	hide: function(){
		if(this.enableLogger){
		    //YAHOO.util.Dom.setStyle(this.div,'display','none');
                    this.loggerReader.hide();
                    this.visible = false;
		}
	},
	
	log: function(message,level){
		if(this.enableLogger){
		     if(YAHOO.lang.isUndefined(level)) this.loggerWriter.log(message,"debug");
		     else this.loggerWriter.log(message,level);
		}
	},
	
	debug: function(message){
	       if(this.enableLogger)
		this.loggerWriter.log(message,"debug");
	},
	
	error: function(message){
	       if(this.enableLogger)	
		this.loggerWriter.log(message,"error");
	},
	
	info: function(message){
	       if(this.enableLogger)	
		this.loggerWriter.log(message,"info");
	}
}
//-------------------------------------------------------------------------------------------------------------//
// Simplifying the generated JSPs by Form Editor
// Alerts if the assignee name is empty
function AlertReassign()
{
      if (document.form.elements['bizsite_assigneeName'].value == '' )
      {
        alert('Please provide assignee name!')
        document.form.elements['bizsite_assigneeName'].focus();
        return false;
      }
      else
      {
        return true;
      }
}
// Simplifying the generated JSPs by Form Editor
function setCheckBoxStyleForIE()
{
      var isIE = (navigator.appName == "Microsoft Internet Explorer") ? 1 : 0;
      var w_Elements = document.getElementsByTagName("input");
      for ( i=0; i < w_Elements.length; ++i)
      {
          if(isIE && (w_Elements.item(i).getAttribute("type") == "checkbox" || w_Elements.item(i).getAttribute("type") == "radio"))
            w_Elements.item(i).className = "ChkBoxNone";
      }
}
// Simplifying the generated JSPs by Form Editor    
function onSuccess() {
}
// Simplifying the generated JSPs by Form Editor
function openDocAttWin( slotName,sesID, ptname, piname, docurl, docServer, readonly, ismultiline, appendwith, isStart, fiid )
{
      var param;
	  var uploadWnd;
	  param = 'bzsid=' + sesID;
      param += '&pt=' + ptname;
      param += '&pi=' + piname;
      param += '&ds=' + slotName;
      param += '&docurl=' + docurl;
      param += '&readonly=' + readonly;
      param += '&ismultiline=' + ismultiline;
      param += '&appendwith=' + appendwith;
      param += '&isPICreation=' + isStart;
      param += '&fiid=' + fiid;
      uploadWnd = openDocumentPresentation(docServer + '/BizSite.DocAttacher?' + param, isIFrame);
}
//--------------------------------------------------------------------------------------------------------------------//
