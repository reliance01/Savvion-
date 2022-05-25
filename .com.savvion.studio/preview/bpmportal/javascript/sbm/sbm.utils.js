var bm;
var clickedButton = 'none';

if(!sbm) sbm = {};
sbm.utils = sbm.utils || {};
sbm.widgets = sbm.widgets || {};
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
     var fiidv = document.myTaskForm.fiid.value;
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
    MM_goToURL('parent',getContextPath()+'/bpmportal/myhome/create_collaboration.jsp');
}

function goToViewCollaboration()
{
    MM_goToURL('parent',getContextPath()+'/bpmportal/myhome/view_collaboration.jsp');
}

function MM_openBrWindow(theURL,winName,features) { 
  window.open(theURL,winName,features);
}

function goToEmail()
{
    MM_openBrWindow(getContextPath()+'/bpmportal/common/pop_task_det_email.jsp','emailuser','scrollbars=yes,resizable=yes,width=600,height=350');
}

function goToChat()
{
    MM_openBrWindow(getContextPath()+'/bpmportal/common/pop_task_det_chat.jsp','chatuser','scrollbars=yes,resizable=yes,width=600,height=350');
}


function goToNotes()
{
    MM_openBrWindow(getContextPath()+'/bpmportal/myhome/processnotes.jsp','Notes','scrollbars=yes,resizable=yes,width=900,height=600');
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
     alert("Action not supported in preview mode");
     return false;
}       

sbm.utils.redirectPage = function(){
    if(! document.referrer.match("link=mgt_overview_tasks")){
        self.parent.location.replace(getContextPath()+"/bpmportal/myhome/bizsite.task");
    }else{
        self.parent.location.replace(getContextPath()+"/bpmportal/management/tasks_overview.jsp");
    }
    return false;
}


sbm.utils.reset = function(){
     document.forms[0].reset();     
}

sbm.utils.applyEffects = function(uuid, effects) {  
  if(uuid.indexOf('_div')> -1) {
  	uuid = uuid.substr(0,uuid.indexOf('_div'));
  }  
  var effect = null;
  try {
    var widgetEffects = new  WidgetEffects(effects);    
    if(widgetEffects.type == "fade")
        effect = new Spry.Effect.DoFade(uuid, {duration: widgetEffects.duration, from: widgetEffects.from, to: widgetEffects.to,toggle:true});
    else if(effects.type == "slide"){
        effect = new Spry.Effect.DoSlide(uuid, {duration: widgetEffects.duration, from: widgetEffects.from, to: widgetEffects.to,toggle:true});
    }else if(effects.type == "blind"){        
		//effect = new Spry.Effect.Blind(uuid, {duration: widgetEffects.duration, from: widgetEffects.from, to: widgetEffects.to,toggle:true});
		effect = new Spry.Effect.Blind(uuid, {toggle:true});
    }else if(effects.type == "grow")
        effect = new Spry.Effect.DoGrow(uuid, {duration: widgetEffects.duration, from: widgetEffects.from, to: widgetEffects.to,toggle:true});
    else if(effects.type == "shake")
        effect = new Spry.Effect.DoShake(uuid, {duration: widgetEffects.duration, from: widgetEffects.from, to: widgetEffects.to,toggle:true});
    else if(effects.type == "highlight")
        effect = new Spry.Effect.Highlight(uuid, {duration: widgetEffects.duration, from: widgetEffects.from, to: widgetEffects.to,toggle:true});
    if(effect.start)effect.start();
  }catch(e){
    alert(e);     
  }
}

sbm.renderBusinessObject = function(dataslotName,commandAction){    
	$(dataslotName).innerHTML ="Loading...";
    var params = "dataslotName="+dataslotName+"&commandAction="+commandAction+'&fiid='+((document.getElementById('fiid') != null) ? document.getElementById('fiid').value : '');
    sbm.utils.updateContent(dataslotName,'GET',getContextPath()+"/bpmportal/common/businessobjects/"+commandAction+".portal?"+params);    
}

sbm.editBusinessObject = function(dataslotName,index){
    $(dataslotName).innerHTML ="Loading...";
    var params = "dataslotName="+dataslotName+"&commandAction=editBusinessObject&index="+index;
    sbm.utils.updateContent(dataslotName,'GET',getContextPath()+"/bpmportal/common/businessobjects/editBusinessObject.portal?"+ params);
}


sbm.deleteBusinessObject = function(dataslotName,index){
    $(dataslotName).innerHTML ="Loading...";
    var params = "dataslotName="+dataslotName+"&commandAction=deleteBusinessObject&index="+index;
    sbm.utils.updateContent(dataslotName,'GET',getContextPath()+"/bpmportal/common/businessobjects/deleteBusinessObject.portal?"+ params);
}

sbm.addBusinessObject = function(dataslotName){
    $(dataslotName).innerHTML ="Loading...";
    var params = "dataslotName="+dataslotName+"&commandAction=addBusinessObject";
    sbm.utils.updateContent(dataslotName,'GET',getContextPath()+"/bpmportal/common/businessobjects/addBusinessObject.portal?"+ params);
}

sbm.resetBusinessObject = function(dataslotName){
    $(dataslotName).innerHTML ="Loading...";
    var params = "dataslotName="+dataslotName+"&commandAction=resetBusinessObject";
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
      var widget = FormWidgetHandler.getInstance().findWidgetByName(widgetName);
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
      var widget = FormWidgetHandler.getInstance().findWidgetByName(widgetName);
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


function WidgetEffects(options){
    this.type = (YAHOO.lang.isUndefined(options.type)) ? 'highlight': options.type;
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
           columns[columns.length] = {header: list[0][i], sortable: true, dataIndex: list[0][i]};
    }
    
    for(var i=1; i < list.length;i++){
          var row = new Array();
          for(var j=0; j < list[1].length;j++) {                
				row[row.length] = (list[i][j] != undefined)  ? (list[i][j]+'').replace(/\n/g, '<br/>') : ' ';                    
          }
          data[data.length] = row;      
    }  
    

    // create the data store
    var store = new Ext.data.SimpleStore({
        fields: fieldsDef
    });
    store.loadData(data);

    // create the Grid
    var grid = new Ext.grid.GridPanel({
        store: store,
        columns:columns,
        stripeRows: true,
        border: true,
        autoScroll: true,
        containerScroll: true,
        autoHeight:true,
        autoWidth:true,
        viewConfig: {
            autoFill: true
        },
        layout:'fit'//,
        //title:'Adaplet Grid'
    });
    
    document.getElementById(id).innerHTML = '';
    grid.render(id);
    //if(!Ext.isIE)
    // grid.getView().fitColumns();
    //grid.getSelectionModel().selectFirstRow(); 
    
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
});
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
/**
 *   Hides the widget
 *   @function Bm.util.hide
 *   @param widgetName (string) 
 */
Bm.util.hide = function(widgetName){    
    sbm.widgets.hide(widgetName);
}
/**
 *   Shows the widget
 *   @function Bm.util.show
 *   @param widgetName (string) 
 */
Bm.util.show = function(widgetName){
    sbm.widgets.show(widgetName);
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

/**
 *   Enables the widget by given widgetName 
 *   @function Bm.util.enable
 *   @param widgetName (string) 
 */
Bm.util.enable = function(widgetName){     
     sbm.widgets.enable(widgetName);
}

/**
 *   Disables the widget by given widgetName 
 *   @function Bm.util.enable
 *   @param widgetName (string) 
 */
Bm.util.disable = function(widgetName){     
     sbm.widgets.disable(widgetName);
}
